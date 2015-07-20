package com.cai310.lottery.service.lottery.dczc.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinRecordType;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dto.lottery.dczc.DczcSchemeDTO;
import com.cai310.lottery.dto.lottery.jclq.JclqSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dczc.DczcPasscount;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeService;
import com.cai310.lottery.service.lottery.dczc.MatchNotEndException;
import com.cai310.lottery.service.lottery.impl.SchemeServiceImpl;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.CombinationBean;
import com.cai310.lottery.support.dczc.DczcCompoundContent;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.DczcMultiplePassPrizeWork;
import com.cai310.lottery.support.dczc.DczcMultiplePasscountWork;
import com.cai310.lottery.support.dczc.DczcNormalPassPrizeWork;
import com.cai310.lottery.support.dczc.DczcNormalPasscountWork;
import com.cai310.lottery.support.dczc.DczcPasscountWork;
import com.cai310.lottery.support.dczc.DczcPrizeItem;
import com.cai310.lottery.support.dczc.DczcPrizeWork;
import com.cai310.lottery.support.dczc.DczcResult;
import com.cai310.lottery.support.dczc.DczcSingleContent;
import com.cai310.lottery.support.dczc.DczcUtils;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.utils.JsonUtil;

@Service("dczcSchemeServiceImpl")
@Transactional
public class DczcSchemeServiceImpl extends SchemeServiceImpl<DczcScheme, DczcSchemeDTO> implements DczcSchemeService {

	@Autowired
	private DczcSchemeEntityManagerImpl schemeManager;

	@Autowired
	private DczcMatchEntityManager matchEntityManager;
	
	@Resource
	private DczcSchemeEntityManagerImpl dczcSchemeEntityManagerImpl;
		
	
	@Override
	protected SchemeEntityManager<DczcScheme> getSchemeEntityManager() {
		return schemeManager;
	}

	public void updatePrize(long schemeId) {
		updateResult(schemeId);
		
	}

	public void updateResult(long schemeId) {
		DczcScheme scheme = schemeManager.getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");
		if (scheme.isUpdateWon())
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已更新中奖,不能再更新中奖.");
		else if (scheme.isUpdatePrize())
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已更新奖金,不能再更新中奖.");
		else if (scheme.isPrizeSended())
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已派奖,不能再更新中奖.");

		Period period = this.periodManager.getPeriod(scheme.getPeriodId());
		if (!period.isDrawed())
			throw new ServiceException("还未开奖,不能执行更新中奖.");

		switch (scheme.getMode()) {
		case COMPOUND:
			doUpdateResultOfCOMPOUND(scheme);
			break;
		case SINGLE:
			doUpdateResultOfSINGLE(scheme);
			break;
		default:
			throw new RuntimeException("数据异常：投注方式不合法.");
		}
		//更新过关统计
		try{
		updatePassCount(period.getId());
		}catch(Exception e){
			Log.info("更新过关统计错误.");
			e.printStackTrace();
			
		}
	}

	/**
	 * 复式更新中奖
	 * 
	 * @param scheme 方案对象
	 */
	protected void doUpdateResultOfCOMPOUND(DczcScheme scheme) {
		Map<Integer, DczcMatch> matchMap = matchEntityManager.findMatchMap(scheme.getPeriodId());

		DczcCompoundContent compoundContent = scheme.getCompoundContent();

		int danSize = 0;
		final List<DczcMatchItem> danCorrectList = new ArrayList<DczcMatchItem>();
		final List<DczcMatchItem> unDanCorrectList = new ArrayList<DczcMatchItem>();
		final Map<Integer, DczcResult> resultMap = new HashMap<Integer, DczcResult>();

		for (DczcMatchItem item : compoundContent.getItems()) {
			if (item.isDan())
				danSize++;

			DczcMatch match = matchMap.get(item.getLineId());
			if (match == null)
				throw new ServiceException("数据异常：投注的场次不存在或被删除.");
			if (!match.isCancel() && !match.isEnded())
				throw new MatchNotEndException("方案[" + scheme.getSchemeNumber() + "]中包含还没有结束的赛事，不能更新中奖.");

			DczcResult result = new DczcResult();
			result.setLineId(match.getLineId());
			result.setCancel(match.isCancel());
			Double resultSp = match.getResultSp(scheme.getPlayType());
			if (resultSp == null)
				throw new ServiceException("第" + (match.getLineId() + 1) + "场开奖SP值为空.");
			if (resultSp -Double.valueOf(0)==0)
				throw new ServiceException("第" + (match.getLineId() + 1) + "场开奖SP值为0.");
			result.setResultSp(resultSp);
			if (match.isCancel()) {
				if (item.isDan())
					danCorrectList.add(item);
				else
					unDanCorrectList.add(item);
			} else {
				Item resultItem = match.getResult(scheme.getPlayType());
				if (resultItem == null)
					throw new ServiceException("第" + (match.getLineId() + 1) + "场开奖结果为空.");
				result.setResultItem(resultItem);

				int v = 1 << resultItem.ordinal();
				if ((item.getValue() & v) > 0) {
					if (item.isDan())
						danCorrectList.add(item);
					else
						unDanCorrectList.add(item);
				}
			}
			resultMap.put(match.getLineId(), result);
		}

		DczcPrizeWork prizeWork;
		switch (scheme.getPassMode()) {
		case NORMAL:
			prizeWork = new DczcNormalPassPrizeWork(resultMap, scheme.getPassTypes().get(0), unDanCorrectList);
			break;
		case MULTIPLE:
			int danMinHit;// 胆码最小命中数
			if (compoundContent.getDanMinHit() != null)
				danMinHit = compoundContent.getDanMinHit();
			else
				danMinHit = danSize;

			int danMaxHit;// 胆码最大命中数
			if (compoundContent.getDanMaxHit() != null)
				danMaxHit = compoundContent.getDanMaxHit();
			else
				danMaxHit = danSize;

			prizeWork = new DczcMultiplePassPrizeWork(resultMap, scheme.getPassTypes(), danCorrectList,
					unDanCorrectList, danMinHit, danMaxHit);

			break;
		default:
			throw new ServiceException("过关模式不合法.");
		}

		if (prizeWork.isWon()) {
			try {
				scheme.doUpdateResult(prizeWork.getWonDetail().toString());
				scheme.doUpdatePrize(BigDecimal.valueOf(prizeWork.getTotalPrize() * scheme.getMultiple()), BigDecimal
						.valueOf(prizeWork.getTotalPrizeAfterTax() * scheme.getMultiple()), prizeWork.getPrizeDetail()
						.toString());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
			scheme.setExtraPrizeMsg(DczcUtils.genExtraPrizeMsg(prizeWork.getCombinationMap()));
		} else {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
			scheme.setWon(false);
		}

		scheme = schemeManager.saveScheme(scheme);
	}
	
	public void updatePassCount(long periodId) {
		List<Long> idList = getSchemeEntityManager().findSchemeIdOfSaleAnalyse(periodId);
		if (idList != null && !idList.isEmpty()) {
			for (Long schemeId : idList) {
				DczcScheme scheme = schemeManager.getScheme(schemeId);
				if (scheme == null)
					throw new ServiceException("方案不存在.");
				switch (scheme.getMode()) {
				case COMPOUND:
					//复式过关统计				
					doPasscountOfCOMPOUND(scheme);
					break;
				case SINGLE:
					//单式过关统计
					///doPasscountOfSINGLE(scheme);
					break;
				default:
					throw new RuntimeException("数据异常：投注方式不合法.");
				}	
			}
		}
	}
	/**
	 * 复式更新过关统计
	 * 
	 * @param scheme 方案对象
	 */
	protected void doPasscountOfCOMPOUND(DczcScheme scheme) {
		Map<Integer, DczcMatch> matchMap = matchEntityManager.findMatchMap(scheme.getPeriodId());

		DczcCompoundContent compoundContent = scheme.getCompoundContent();

		int danSize = 0;
		//胆码命中列表（是命中的）
		final List<DczcMatchItem> danCorrectList = new ArrayList<DczcMatchItem>();
		//拖码命中列表（是命中的）
		final List<DczcMatchItem> unDanCorrectList = new ArrayList<DczcMatchItem>();
		final Map<Integer, DczcResult> resultMap = new HashMap<Integer, DczcResult>();
		int betCount, wonCount;
		
		for (DczcMatchItem item : compoundContent.getItems()) {		
			if (item.isDan())
				danSize++;

			DczcMatch match = matchMap.get(item.getLineId());
			if (match == null)
				throw new ServiceException("数据异常：投注的场次不存在或被删除.");
			DczcResult result = new DczcResult();
			result.setLineId(match.getLineId());
			result.setCancel(match.isCancel());
			if (match.isCancel()) {
				///比赛取消，算是命中
				if (item.isDan())
					danCorrectList.add(item);
				else
					unDanCorrectList.add(item);
			} else {
				Item resultItem = match.getResult(scheme.getPlayType());
				if(null==resultItem){
					//还没有开奖，算是命中
					if (item.isDan())
						danCorrectList.add(item);
					else
						unDanCorrectList.add(item);
				}else{
					result.setResultItem(resultItem);
					int v = 1 << resultItem.ordinal();
					if ((item.getValue() & v) > 0) {
					    ///场次命中，算是命中
						if (item.isDan())
							danCorrectList.add(item);
						else
							unDanCorrectList.add(item);
					}
				}
			}
			resultMap.put(match.getLineId(), result);
		}
		
		DczcPasscountWork passcountWork;
		int totalCorrectSize= 0;

		switch (scheme.getPassMode()) {
		case NORMAL:
			passcountWork = new DczcNormalPasscountWork(scheme.getPassTypes().get(0), unDanCorrectList,compoundContent.getItems(),null==scheme.getMultiple()?1:scheme.getMultiple());
			totalCorrectSize = unDanCorrectList.size();
			betCount = passcountWork.getBetCount();
			wonCount = passcountWork.getWonCount();
			break;
		case MULTIPLE:
			
			int danMinHit;// 胆码最小命中数
			if (compoundContent.getDanMinHit() != null)
				danMinHit = compoundContent.getDanMinHit();
			else
				danMinHit = danSize;

			int danMaxHit;// 胆码最大命中数
			if (compoundContent.getDanMaxHit() != null)
				danMaxHit = compoundContent.getDanMaxHit();
			else
				danMaxHit = danSize;

			passcountWork = new DczcMultiplePasscountWork(scheme.getPassTypes(), danCorrectList,
					unDanCorrectList, danMinHit, danMaxHit,compoundContent.getItems(),null==scheme.getMultiple()?1:scheme.getMultiple());
		
			totalCorrectSize =  danCorrectList.size() + unDanCorrectList.size();

			betCount = passcountWork.getBetCount();
			wonCount = passcountWork.getWonCount();
			break;
		default:
			throw new ServiceException("过关模式不合法.");
		}
		
		if (passcountWork.isWon()) {
			// 保存过关统计信息
			DczcPasscount entity = this.schemeManager.getDczcSchemeWonInfo(scheme.getId());
			if (entity == null) {
				entity = new DczcPasscount();
			}
			 
			super.setSchemePasscountInstance(scheme, entity);
			
			entity.setPassType(scheme.getPassType());
			entity.setBetCount(passcountWork.getBetCount());
			entity.setWonCount(passcountWork.getWonCount());
			entity.setPasscount(totalCorrectSize);
			entity.setPasscount(scheme.getSelectedLineIds().size());
			entity.setFinsh(scheme.getWinningUpdateStatus());
			schemeManager.saveDczcSchemeWonInfo(entity);
		} else {
			//只统计中奖方案，不中奖 的删掉
			DczcPasscount entity = schemeManager.getDczcSchemeWonInfo(scheme.getId());
			if (entity != null) {
				schemeManager.deleteDczcSchemeWonInfo(entity);
			}
			
		}
		
	}

	/**
	 * 单式更新过关统计
	 * 
	 * @param scheme 方案对象
	 */
	protected void doPasscountOfSINGLE(DczcScheme scheme) {
		Map<Integer, DczcMatch> matchMap = matchEntityManager.findMatchMap(scheme.getPeriodId());

		DczcSingleContent singleContent = scheme.getSingleContent();
		Map<Integer, DczcResult> resultMap = new HashMap<Integer, DczcResult>();
		int betCount=0, wonCount=0;
		for (Integer lineId : singleContent.getLineIds()) {
			DczcMatch match = matchMap.get(lineId);
			if (match == null)
				throw new ServiceException("数据异常：投注的场次不存在或被删除.");

			DczcResult result = new DczcResult();
			result.setLineId(match.getLineId());
			result.setCancel(match.isCancel());
			if (!match.isCancel()) {
				Item resultItem = match.getResult(scheme.getPlayType());
				result.setResultItem(resultItem);
			}
			resultMap.put(match.getLineId(), result);
		}

		PassType passType = scheme.getPassTypes().get(0);

		StringBuilder wonDetail = new StringBuilder();
		StringBuilder prizeDetail = new StringBuilder();
		double totalPrize = 0.0d;
		double totalPrizeAfterTax = 0.0d;
		final String lineSeq = "\r\n";// 各个中奖组合中奖信息的分隔符
		final Map<String, CombinationBean> combinationMap = new HashMap<String, CombinationBean>();// 存放中奖的组合
		String[] contentArr = singleContent.getContent().split("\r\n");
		for (String content : contentArr) {
			List<DczcMatchItem> correctCombList = new ArrayList<DczcMatchItem>();
			String[] ordinalArr = content.split(",");
			betCount=ordinalArr.length;
			for (int i = 0; i < ordinalArr.length; i++) {
				String ordinalStr = ordinalArr[i];
				if ("#".equals(ordinalStr))
					continue;

				int ordinal = Integer.parseInt(ordinalStr);

				int lineId = singleContent.getLineIds().get(i);
				DczcResult result = resultMap.get(lineId);
				Item item = scheme.getPlayType().getAllItems()[ordinal];
                if(null==result||null==result.getResultItem()){
                	//没有开奖
                	DczcMatchItem matchItem = new DczcMatchItem();
    				matchItem.setLineId(lineId);
    				matchItem.setValue(1 << item.ordinal());
    				correctCombList.add(matchItem);
                }else{
                	if (result.getResultItem() != item)
    					break;
                	DczcMatchItem matchItem = new DczcMatchItem();
    				matchItem.setLineId(lineId);
    				matchItem.setValue(1 << item.ordinal());
    				correctCombList.add(matchItem);
                }
			}
			if (correctCombList.size() == passType.getMatchCount()) {
				///////中奖////////
				wonCount++;
				Collections.sort(correctCombList);
				CombinationBean combination = new CombinationBean();
				combination.setPassTypeOrdinal(passType.ordinal());
				combination.setItems(correctCombList);
				combinationMap.put(combination.generateKey(), combination);
			}			
		}
        ///////////////////////保存过关信息
		
	}
	
	/**
	 * 单式更新中奖
	 * 
	 * @param scheme 方案对象
	 */
	protected void doUpdateResultOfSINGLE(DczcScheme scheme) {
		Map<Integer, DczcMatch> matchMap = matchEntityManager.findMatchMap(scheme.getPeriodId());

		DczcSingleContent singleContent = scheme.getSingleContent();
		Map<Integer, DczcResult> resultMap = new HashMap<Integer, DczcResult>();
		for (Integer lineId : singleContent.getLineIds()) {
			DczcMatch match = matchMap.get(lineId);
			if (match == null)
				throw new ServiceException("数据异常：投注的场次不存在或被删除.");
			if (!match.isCancel() && !match.isEnded())
				throw new MatchNotEndException("方案[" + scheme.getSchemeNumber() + "]中包含还没有结束的赛事，不能更新中奖.");

			DczcResult result = new DczcResult();
			result.setLineId(match.getLineId());
			result.setCancel(match.isCancel());
			Double resultSp = match.getResultSp(scheme.getPlayType());
			if (resultSp == null)
				throw new ServiceException("第" + (match.getLineId() + 1) + "场开奖SP值为空.");
			if (resultSp-Double.valueOf(0)==0)
				throw new ServiceException("第" + (match.getLineId() + 1) + "场开奖SP值为0.");
			result.setResultSp(resultSp);
			if (!match.isCancel()) {
				Item resultItem = match.getResult(scheme.getPlayType());
				if (resultItem == null)
					throw new ServiceException("第" + (match.getLineId() + 1) + "场开奖结果为空.");
				result.setResultItem(resultItem);
			}
			resultMap.put(match.getLineId(), result);
		}

		PassType passType = scheme.getPassTypes().get(0);

		StringBuilder wonDetail = new StringBuilder();
		StringBuilder prizeDetail = new StringBuilder();
		double totalPrize = 0.0d;
		double totalPrizeAfterTax = 0.0d;
		final String lineSeq = "\r\n";// 各个中奖组合中奖信息的分隔符
		final Map<String, CombinationBean> combinationMap = new HashMap<String, CombinationBean>();// 存放中奖的组合
		String[] contentArr = singleContent.getContent().split("\r\n");
		for (String content : contentArr) {
			List<DczcMatchItem> correctCombList = new ArrayList<DczcMatchItem>();
			String[] ordinalArr = content.split(",");
			for (int i = 0; i < ordinalArr.length; i++) {
				String ordinalStr = ordinalArr[i];
				if ("#".equals(ordinalStr))
					continue;

				int ordinal = Integer.parseInt(ordinalStr);

				int lineId = singleContent.getLineIds().get(i);
				DczcResult result = resultMap.get(lineId);

				Item item = scheme.getPlayType().getAllItems()[ordinal];
				if (result.getResultItem() != item)
					break;

				DczcMatchItem matchItem = new DczcMatchItem();
				matchItem.setLineId(lineId);
				matchItem.setValue(1 << item.ordinal());
				correctCombList.add(matchItem);
			}
			if (correctCombList.size() == passType.getMatchCount()) {
				Collections.sort(correctCombList);

				DczcPrizeItem prizeItem = new DczcPrizeItem(resultMap, correctCombList);

				wonDetail.append(prizeItem.getLineWonDetail()).append(lineSeq);
				prizeDetail.append(prizeItem.getLinePrizeDetail()).append(lineSeq);
				totalPrize += prizeItem.getPrize();
				totalPrizeAfterTax += prizeItem.getPrizeAfterTax();

				CombinationBean combination = new CombinationBean();
				combination.setPassTypeOrdinal(passType.ordinal());
				combination.setItems(correctCombList);
				combination.setPrize(prizeItem.getPrize());
				combination.setPrizeAfterTax(prizeItem.getPrizeAfterTax());

				combinationMap.put(combination.generateKey(), combination);
			}
		}

		if (totalPrize > 0) {
			wonDetail.delete(wonDetail.length() - lineSeq.length(), wonDetail.length());// 删除最后一个分隔符
			prizeDetail.delete(prizeDetail.length() - lineSeq.length(), prizeDetail.length());// 删除最后一个分隔符
			try {
				scheme.doUpdateResult(wonDetail.toString());
				scheme.doUpdatePrize(BigDecimal.valueOf(totalPrize * scheme.getMultiple()), BigDecimal
						.valueOf(totalPrizeAfterTax * scheme.getMultiple()), prizeDetail.toString());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
			scheme.setExtraPrizeMsg(DczcUtils.genExtraPrizeMsg(combinationMap));
		} else {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
			scheme.setWon(false);
		}

		scheme = schemeManager.saveScheme(scheme);
	}

	@Override
	public DczcScheme newSchemeInstance(DczcSchemeDTO schemeDTO) {
		DczcScheme scheme = super.newSchemeInstance(schemeDTO);

		scheme.setPlayType(schemeDTO.getPlayType());
		scheme.setPassMode(schemeDTO.getPassMode());

		int passTypeValue = 0;
		for (PassType passType : schemeDTO.getPassTypes()) {
			passTypeValue |= passType.getValue();
		}
		scheme.setPassType(passTypeValue);

		return scheme;
	}

	@Override
	protected void checkConformPeriodSubscriptionConfig(DczcScheme scheme) {
		super.checkConformPeriodSubscriptionConfig(scheme);

		Date endTime = DczcUtils.getMatchEndTime(scheme.getMode(), scheme.getPassMode());
		List<Integer> endLineIds = matchEntityManager.findEndedMatchLineIds(scheme.getPeriodId(), endTime);
		List<Integer> lineIds = scheme.getSelectedLineIds();
		for (Integer endLineId : endLineIds) {
			if (lineIds.contains(endLineId))
				throw new ServiceException("第" + (endLineId + 1) + "场赛事已经截止销售.");
		}

	}

	@Override
	public boolean isSaleEnded(Long schemeId) {
		boolean isSaleEnded = super.isSaleEnded(schemeId);
		if (!isSaleEnded) {
			DczcScheme scheme = getSchemeEntityManager().getScheme(schemeId);
			Date endTime = DczcUtils.getMatchEndTime(scheme.getMode(), scheme.getPassMode());
			List<Integer> endLineIds = matchEntityManager.findEndedMatchLineIds(scheme.getPeriodId(), endTime);
			List<Integer> lineIds = scheme.getSelectedLineIds();
			for (Integer endLineId : endLineIds) {
				if (lineIds.contains(endLineId))
					return true;
			}
			return false;
		}
		return isSaleEnded;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void checkConformPeriodInitConfig(Period period, DczcSchemeDTO schemeDTO) {
		super.checkConformPeriodInitConfig(period, schemeDTO);

		// 增加对场次销售时间的验证
		Map<Integer, DczcMatch> matchMap = matchEntityManager.findMatchMap(schemeDTO.getPeriodId());
		int aheadMinuteEnd;
		switch (schemeDTO.getMode()) {
		case COMPOUND:
			switch (schemeDTO.getPassMode()) {
			case NORMAL:
				aheadMinuteEnd = DczcConstant.COMPOUND_AHEAD_END_NORMAL_PASS_MODE;
				break;
			case MULTIPLE:
				aheadMinuteEnd = DczcConstant.COMPOUND_AHEAD_END_MULTIPLE_PASS_MODE;
				break;
			default:
				throw new ServiceException("过关模式不正确.");
			}
			Map classMap = new HashMap();
			classMap.put("items", DczcMatchItem.class);
			DczcCompoundContent content = JsonUtil.getObject4JsonString(schemeDTO.getContent(),
					DczcCompoundContent.class, classMap);
			for (DczcMatchItem item : content.getItems()) {
				DczcMatch match = matchMap.get(item.getLineId());
				if (match.checkSaleEnd(aheadMinuteEnd))
					throw new ServiceException("第" + (item.getLineId() + 1) + "场赛事已经截止销售.");
			}
			break;
		case SINGLE:
			aheadMinuteEnd = DczcConstant.SINGLE_AHEAD_END;
			DczcSingleContent singleContent = JsonUtil.getObject4JsonString(schemeDTO.getContent(),
					DczcSingleContent.class);
			for (Integer lineId : singleContent.getLineIds()) {
				DczcMatch match = matchMap.get(lineId);
				if (match.checkSaleEnd(aheadMinuteEnd))
					throw new ServiceException("第" + (lineId + 1) + "场赛事已经截止销售.");
			}
			break;
		default:
			throw new ServiceException("投注方式不正确.");
		}
	}
	public boolean handleTransaction(Long schemeId) {
		DczcScheme scheme = getSchemeEntityManager().getScheme(schemeId);

		//Date endTime = DczcUtils.getMatchEndTime(scheme.getMode(), scheme.getPassMode());
		//cyy-c 2011-05-30 把完成交易设在官方截至时间
		int aheadEndMinute = 5;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, aheadEndMinute);
		Date endTime = calendar.getTime();
		
		List<Integer> endLineIds = matchEntityManager.findEndedMatchLineIds(scheme.getPeriodId(), endTime);
		boolean anyEnded = false;// 方案是否包含一场已结束的赛事标识
		List<Integer> lineIds = scheme.getSelectedLineIds();
		for (Integer endLineId : endLineIds) {
			if (lineIds.contains(endLineId)) {
				anyEnded = true;
				break;
			}
		}
		if (anyEnded) {
			commitOrCancelTransaction(scheme.getId());
			return true;
		}

		return false;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.DCZC;
	}
	@Override
	public Byte getPlayType(DczcSchemeDTO schemeDTO){
		return Byte.valueOf(String.valueOf(schemeDTO.getPlayType().ordinal()));
	}
	@Override
	public WinRecordType getWinRecord(DczcScheme Scheme) {
		BigDecimal prizeAfterTax = Scheme.getPrizeAfterTax();
		Integer schemeCost = Scheme.getSchemeCost();
		if (Scheme.getState() == SchemeState.SUCCESS) {
			if (Scheme.getPrizeAfterTax().doubleValue() >= Constant.FiftyMillion.doubleValue()) {
				return WinRecordType.TUANZHANG;
			}
			if (prizeAfterTax.doubleValue() >= Constant.FiveMillion.doubleValue()) {
				return WinRecordType.YINGZHANG;
			}
			if (prizeAfterTax.doubleValue() >= Constant.OneMillion.doubleValue()) {
				return WinRecordType.LIANZHANG;
			}
			if ((prizeAfterTax.doubleValue() >= Constant.OneThousands.doubleValue())
					|| (prizeAfterTax.doubleValue() >= Constant.FiveHundreds.doubleValue() && (prizeAfterTax
							.doubleValue() / schemeCost) >= 10)) {
				return WinRecordType.PAIZHANG;
			}
		} else {
			// 流产
			if ((prizeAfterTax.doubleValue() >= Constant.TwoThousands.doubleValue())
					|| (prizeAfterTax.doubleValue() >= Constant.OneThousands.doubleValue() && (prizeAfterTax
							.doubleValue() / schemeCost) >= 20)) {
				return WinRecordType.PAIZHANG;
			}
		}

		return null;
	}
}
