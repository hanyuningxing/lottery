package com.cai310.lottery.service.lottery.jclq.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dto.lottery.jclq.JclqSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqSchemeMatch;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.MatchNotEndException;
import com.cai310.lottery.service.lottery.impl.SchemeServiceImpl;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeService;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.lottery.support.PrintWonItem;
import com.cai310.lottery.support.PrizeWork;
import com.cai310.lottery.support.jclq.CombinationBean;
import com.cai310.lottery.support.jclq.JclqCompoundContent;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqMultiplePassPrizeWork;
import com.cai310.lottery.support.jclq.JclqPrizeItem;
import com.cai310.lottery.support.jclq.JclqResult;
import com.cai310.lottery.support.jclq.JclqSchemeConverWork;
import com.cai310.lottery.support.jclq.JclqSimplePrizeWork;
import com.cai310.lottery.support.jclq.JclqSingleContent;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jclq.TicketItem;
import com.cai310.lottery.support.jclq.TicketItemSingle;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service("jclqSchemeServiceImpl")
@Transactional
public class JclqSchemeServiceImpl extends SchemeServiceImpl<JclqScheme, JclqSchemeDTO> implements JclqSchemeService {

	@Autowired
	private JclqSchemeEntityManagerImpl schemeManager;

	@Autowired
	private JclqMatchEntityManager matchEntityManager;
	
	@Resource
	protected TicketEntityManager ticketEntityManager;
	
	@Resource
	protected PrintEntityManager printEntityManager;

	@Override
	protected SchemeEntityManager<JclqScheme> getSchemeEntityManager() {
		return schemeManager;
	}

	public void updatePrize(long schemeId) {
		updateResult(schemeId);
	}
	
	public void updateResult(long schemeId) {
		JclqScheme scheme = schemeManager.getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");
		if (scheme.isUpdateWon())
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已更新中奖,不能再更新中奖.");
		else if (scheme.isUpdatePrize())
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已更新奖金,不能再更新中奖.");
		else if (scheme.isPrizeSended())
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已派奖,不能再更新中奖.");
		
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
	}
	
	/**
	 * 单式方案更新中奖结果(包括优化方案)
	 * @param scheme
	 */
	@SuppressWarnings("unchecked")
	protected void doUpdateResultOfSINGLE(JclqScheme scheme){
		if (scheme.getState() == SchemeState.REFUNDMENT) {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
			scheme.setWon(false);
			scheme.setWonDetail("");
			scheme.setPrize(BigDecimal.ZERO);
			scheme.setPrizeAfterTax(BigDecimal.ZERO);
			scheme.setPrizeDetail("");
		} else {		
			final String lineSeq = "\r\n";//行信息分隔符
			final JclqSingleContent singleContent = scheme.getSingleContent();
			final List<String> matchKeys = singleContent.getMatchkeys();
			final List<String> playTypes = singleContent.getPlayTypes();//混合投中使用
			String[] contentArr = singleContent.converContent2Arr();		
			List<List<JclqMatchItem>> contentList = Lists.newArrayList();//方案集合<赛程集合>
			List<JclqMatchItem> itemList = null;//赛程集合
			PlayType playType = scheme.getPlayType();
			
			for (int index=0;index<contentArr.length;index++) {
				String[] ordinalArr = contentArr[index].split(",");
				String[] playTypeArr = null;
				if(playType==PlayType.MIX){
					if(playTypes.size()==contentArr.length){
						String playTypeStr = playTypes.get(index);
						if(StringUtil.isEmpty(playTypeStr)){
							throw new ServiceException("第"+(index+1)+"注的内容相对应的玩法集合为空");
						}else{
							playTypeArr = playTypeStr.split(",");
						}
					}else{
						throw new ServiceException("投注内容数与相对应的玩法集合数不一致");
					}
				}
				
				itemList = Lists.newArrayList();
				for(int i = 0; i < ordinalArr.length; i++){
					JclqMatchItem matchItem = new JclqMatchItem();
					String ordinalStr = ordinalArr[i];
					if ("#".equals(ordinalStr))
						continue;
	
					int ordinal = Integer.parseInt(ordinalStr);
					if(playType==PlayType.MIX){
						matchItem.setPlayType(PlayType.valueOfName(playTypeArr[i]));					
					}else{
						matchItem.setPlayType(playType);
					}
					matchItem.setValue(0x1<<ordinal);
					matchItem.setMatchKey(matchKeys.get(i));
					itemList.add(matchItem);
				}
				contentList.add(itemList);
			}
			
			//获取赛程的开奖结果数据		
			List<JclqMatch> mathList = matchEntityManager.findMatchs(matchKeys);
			Map<String, JclqResult> drawdMatchMap = Maps.newHashMap();	
			List<JclqMatchItem> matchItems = singleContent.getItems();
			Map<String,List<JclqMatchItem>> selectedItemsOfMatch = Maps.newHashMap();
			for(JclqMatchItem selectedItem : matchItems){
				List<JclqMatchItem> items = selectedItemsOfMatch.get(selectedItem.getMatchKey());
				if(items==null){
					items = Lists.newArrayList();
					items.add(selectedItem);
					selectedItemsOfMatch.put(selectedItem.getMatchKey(), items);
				}else{
					items.add(selectedItem);
				}
			}
			
			for (JclqMatch match : mathList) {
				String matchKey = match.getMatchKey();
				if (!match.isEnded() && !match.isCancel())
					throw new ServiceException("方案["+ scheme.getSchemeNumber() +"]-[" + matchKey + "]未结束赛事，不能更新中奖");
				
				if(PlayType.MIX.equals(playType)){
					List<JclqMatchItem> items = selectedItemsOfMatch.get(matchKey);
					for (JclqMatchItem matchItemT : items) {
						JclqResult result = new JclqResult();
						result.setMatchKey(matchKey);
						result.setCancel(match.isCancel());	
						Item rs = match.getResult(matchItemT.getPlayType());						
						if (!match.isCancel() && rs == null)
							throw new ServiceException("[" + matchKey + "]未填开奖结果.");
						result.setResultItem(rs);
						drawdMatchMap.put(matchKey+matchItemT.getPlayType(), result);
						
					}
				}else{
					JclqResult result = new JclqResult();
					result.setMatchKey(matchKey);
					result.setCancel(match.isCancel());	
					Item rs = match.getResult(playType);
					if (!match.isCancel() && rs == null)
						throw new ServiceException("[" + matchKey + "]未填开奖结果.");
					result.setResultItem(rs);
					drawdMatchMap.put(matchKey, result);
				}
			}
			
			//出票赔率 Map<matchKey, Map<item, Double>>
			List<Map<String, Map<String, Double>>> printAwardList = null;
			List<TicketItemSingle> ticketList=null;
			try {
				ticketList = scheme.getSingleTicketList();
				if(ticketList==null || ticketList.isEmpty()){
					throw new ServiceException("方案拆票内容为空");
				}
				if(scheme.getState() == SchemeState.CANCEL){//撤销(流产)取用发起时的赔率作为奖金计算依据
					List<JclqMatchItem> selectedMatchItems = singleContent.getItems();
					Map<String,List<String>> selectedMatchItemMap = Maps.newHashMap();
					for(JclqMatchItem matchItem : selectedMatchItems){
						selectedMatchItemMap.put(matchItem.getMatchKey(), matchItem.getSps());
					}
					printAwardList = Lists.newArrayList();
					for(TicketItemSingle ticket : ticketList){
						Map<String, Map<String, Double>> matchAwardMap = Maps.newHashMap();
						List<JclqMatchItem> itemsOfContent = contentList.get(ticket.getIndex());
						Map<String, Double> itemAwardMap = Maps.newHashMap();
						for(JclqMatchItem matchItem : itemsOfContent){
							Item[] itemsOfPlayType = matchItem.getPlayType().getAllItems();
							Item selectedItem = null;
							for(Item item : itemsOfPlayType){
								if(matchItem.hasSelect(item)){
									selectedItem = item;
									break;
								}
							}
							String sp = null;
							try{
								 sp = selectedMatchItemMap.get(matchItem.getMatchKey()).get(selectedItem.ordinal());
							}catch(Exception e){
								sp = "0";
							}
							itemAwardMap.put(selectedItem.getValue(), Double.valueOf(sp));
							matchAwardMap.put(matchItem.getMatchKey(), itemAwardMap);
						}
						printAwardList.add(matchAwardMap);
					}					
				}else if(scheme.isRealPrinted()){	
					printAwardList = scheme.getPrintAwardList();
				}else{
					throw new MatchNotEndException("出票SP值未更新.");
				}					
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
			
			//开始更新中奖结果
			PassType passType = scheme.getPassTypeList().get(0);
			int passMatchCount = passType.getMatchCount();
			StringBuilder wonDetail = new StringBuilder();
			StringBuilder prizeDetail = new StringBuilder();
			double totalPrize = 0.0d;
			double totalPrizeAfterTax = 0.0d;
			//final Map<String, CombinationBean> combinationMap = new HashMap<String, CombinationBean>();// 存放中奖的组合
			final Map<Integer, CombinationBean> ticket_combinationMap = Maps.newLinkedHashMap();// 用于出票回查
			for (int i=0;i<ticketList.size();i++) {
				List<List<JclqMatchItem>> contentItemLists = null;//各单式方案选择场次列表的集合
				TicketItemSingle ticketItem = ticketList.get(i);
				List<JclqMatchItem> contentItemList = contentList.get(ticketItem.getIndex());//各方案选择的场次
				
				//是否为普通过关，不是则拆解为单式进行中奖处理
				if(contentItemList.size()>passMatchCount){
					@SuppressWarnings("rawtypes")
					JclqSchemeConverWork converWork = new JclqSchemeConverWork(passMatchCount,Lists.newArrayList(),contentItemList,-1,-1);
					contentItemLists = converWork.getResultList();
				}else{
					contentItemLists = Lists.newArrayList();
					contentItemLists.add(contentItemList);
				}
				
				Map<String, Map<String, Double>> matchAwardMap = printAwardList.get(i);
				if(matchAwardMap==null)throw new ServiceException("出票SP值未更新.");
				//对方案进行中奖验证
				for(List<JclqMatchItem> selectedItemList:contentItemLists){
					List<JclqMatchItem> correctCombList = new ArrayList<JclqMatchItem>();//每个方案过关场次
					for(JclqMatchItem selectedItem : selectedItemList){
						JclqResult result = null;
						if(PlayType.MIX.equals(playType)){
							result = drawdMatchMap.get(selectedItem.getMatchKey()+selectedItem.getPlayType());
						}else{
							result = drawdMatchMap.get(selectedItem.getMatchKey());
						}
						Item item = result.getResultItem();
						if(!selectedItem.hasSelect(item)){//没有猜中
							break;
						}
						//获取出票方返回的对应赔率
						Double resultSp = Double.valueOf(""+matchAwardMap.get(selectedItem.getMatchKey()).get(item.getValue()));
						
						if (resultSp-Double.valueOf(0)==0)
							throw new ServiceException("第" + (selectedItem.getMatchKey()) + "场开奖SP值为0.");
						result.setResultSp(resultSp);
						correctCombList.add(selectedItem);
					}
					if (correctCombList.size() == passMatchCount) {//猜中场次等于过关场次即为中奖
						Collections.sort(correctCombList);
						JclqPrizeItem prizeItem = new JclqPrizeItem(drawdMatchMap, correctCombList,ticketItem.getMultiple());
	
						wonDetail.append(prizeItem.getLineWonDetail()).append(lineSeq);
						prizeDetail.append(prizeItem.getLinePrizeDetail()).append(lineSeq);
						
						totalPrize += prizeItem.getPrize();
						totalPrizeAfterTax += prizeItem.getPrizeAfterTax();
						
						CombinationBean combination = new CombinationBean();
						combination.setPassTypeOrdinal(passType.ordinal());
						combination.setItems(correctCombList);
						combination.setPrize(prizeItem.getPrize());
						combination.setPrizeAfterTax(prizeItem.getPrizeAfterTax());
	
						//combinationMap.put(combination.generateKey(), combination);
						ticket_combinationMap.put(ticketItem.getIndex(), combination);
					}
				}
			}
			
			if (totalPrize > 0) {
				wonDetail.delete(wonDetail.length() - lineSeq.length(), wonDetail.length());// 删除最后一个分隔符
				prizeDetail.delete(prizeDetail.length() - lineSeq.length(), prizeDetail.length());// 删除最后一个分隔符
				try {
					scheme.doUpdateResult(wonDetail.toString());
					scheme.doUpdatePrize(BigDecimal.valueOf(totalPrize), BigDecimal.valueOf(totalPrizeAfterTax), prizeDetail.toString());
					//出票回查
					if(null!=ticket_combinationMap&&!ticket_combinationMap.isEmpty()){
						PrintInterface printInterface = printEntityManager.findPrintInterfaceBy(scheme.getSchemeNumber(), scheme.getLotteryType());
						if(null!=printInterface){
							List<Long> ticketIds = ticketEntityManager.findTicketIdByPrintInterfaceId(printInterface.getId());
							Ticket ticket = null;
							CombinationBean combination = null;
							Integer ticketIndex = null;
							for (Long id : ticketIds) {
								 ticket = ticketEntityManager.getTicket(id);
								 ticket.setChecked(true);
								 if(null!=ticket.getTicketIndex()&&SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())){
										///方案成功才更新出票
									for (Map.Entry<Integer,CombinationBean> combinationMap: ticket_combinationMap.entrySet()) {
										combination = combinationMap.getValue();
										ticketIndex = combinationMap.getKey();
										if(ticketIndex.equals(ticket.getTicketIndex())){
											//
											ticket.setWon(true);
											ticket.setTotalPrize(combination.getPrize());
											ticket.setTotalPrizeAfterTax(combination.getPrizeAfterTax());
											ticket.setWonDetail(PassType.values()[combination.getPassTypeOrdinal()].getText()+":"+combination.getPrize()+"元，税后奖金"+combination.getPrizeAfterTax()+"元");
											ticket.setTicket_synchroned(Boolean.FALSE);
										}
									}
								 }
								 ticketEntityManager.saveTicket(ticket);
							}
							
						}
						
					}
					
				} catch (DataException e) {
					throw new ServiceException(e.getMessage());
				}
				//scheme.setExtraPrizeMsg(JclqUtil.genExtraPrizeMsg(combinationMap));//额外的中奖信息(暂时未使用该信息字段)
			} else {
				scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
				scheme.setWon(false);
				scheme.setWonDetail("");
				scheme.setPrize(BigDecimal.ZERO);
				scheme.setPrizeAfterTax(BigDecimal.ZERO);
				scheme.setPrizeDetail("");
			}
		}
		scheme = schemeManager.saveScheme(scheme);
	}
	
	/**
	 * 复式方案更新中奖结果
	 * @param scheme
	 */
	protected void doUpdateResultOfCOMPOUND(JclqScheme scheme){
		JclqCompoundContent compoundConent = scheme.getCompoundContent();
		List<JclqMatchItem> itemList = compoundConent.getItems();

		List<String> matchKeys = Lists.newArrayList();
		for (JclqMatchItem item : itemList) {
			matchKeys.add(item.getMatchKey());
		}

		List<JclqMatch> mathList = matchEntityManager.findMatchs(matchKeys);
		Map<String, JclqMatch> drawdMatchMap = Maps.newHashMap();
		for (JclqMatch match : mathList) {
			String matchKey = match.getMatchKey();
			if (!match.isEnded() && !match.isCancel())
				throw new ServiceException("[" + matchKey + "]未结束.");
			Item rs = null;
			if(PlayType.MIX.equals(scheme.getPlayType())){
				for (JclqMatchItem matchItem : itemList) {
					if(matchItem.getMatchKey().equals(match.getMatchKey())){
						 rs = match.getResult(matchItem.getPlayType());	
					}
				}
			}else{
				 rs = match.getSingleResult(scheme.getPlayType());
			}
			if (!match.isCancel() && rs == null)
				throw new ServiceException("[" + matchKey + "]未填开奖结果.");
			drawdMatchMap.put(matchKey, match);
		}

		PrizeWork prizeWork;
		List<JcWonMatchItem> correctList = Lists.newArrayList();
		List<PassType> passTypeList = scheme.getPassTypeList();
		switch (scheme.getPassMode()) {
		case SINGLE:
		case PASS:
		case MIX_PASS:
			if (scheme.getState() == SchemeState.CANCEL || scheme.getState() == SchemeState.REFUNDMENT) {
				prizeWork = null;
			} else {
				List<TicketItem> ticketList;
				List<Map<String, Map<String, Double>>> printAwardList;
				try {
					ticketList = scheme.getTicketList();
					if(ticketList==null || ticketList.isEmpty()){
						throw new ServiceException("方案拆票内容为空");
					}					
					if(scheme.getState() == SchemeState.CANCEL){//撤销(流产)取用发起时的赔率作为奖金计算依据
						List<JclqMatchItem> selectedMatchItems = compoundConent.getItems();
						Map<String,List<String>> selectedMatchItemMap = Maps.newHashMap();
						for(JclqMatchItem matchItem : selectedMatchItems){
							selectedMatchItemMap.put(matchItem.getMatchKey(), matchItem.getSps());
						}
						printAwardList = Lists.newArrayList();
						Map<String, Map<String, Double>> matchAwardMap = null;
						List<JclqMatchItem> ticketMatchItems = null;
						for(TicketItem ticketItem : ticketList){
							matchAwardMap = Maps.newHashMap();
							ticketMatchItems = Lists.newArrayList();
							if (ticketItem.getMatchFlag() == 0) {
								ticketMatchItems = selectedMatchItems;
							} else {
								for (int p = 0; p < itemList.size(); p++) {
									if ((ticketItem.getMatchFlag() & (0x1l << p)) > 0)
										ticketMatchItems.add(itemList.get(p));
								}
							}
							Map<String, Double> itemAwardMap = null;
							for(JclqMatchItem matchItem : ticketMatchItems){
								Item[] itemsOfPlayType = matchItem.getPlayType().getAllItems();
								itemAwardMap = Maps.newHashMap();
								String sp = null;
								for(Item item : itemsOfPlayType){
									if(matchItem.hasSelect(item)){
										if (matchItem.getSps().size() == 0) {
											return;
										}
										sp=matchItem.getSps().get(item.ordinal());
										itemAwardMap.put(item.getValue(), Double.valueOf(sp));
									}
								}
								matchAwardMap.put(matchItem.getMatchKey(), itemAwardMap);
							}
							printAwardList.add(matchAwardMap);
						}
					}else if(scheme.isRealPrinted()){	
						printAwardList = scheme.getPrintAwardList();
					}else{
						throw new MatchNotEndException("出票SP值未更新.");
					}
				}catch (DataException e) {
					throw new ServiceException(e.getMessage(), e);
				}
				if (printAwardList.size() != ticketList.size())
					throw new ServiceException("出票SP值数据异常.");

				if (ticketList.size() > 1) {
					for (Map<String, Map<String, Double>> map : printAwardList) {
						if (map == null)
							throw new MatchNotEndException("出票SP值未更新.");
					}

					prizeWork = new JclqMultiplePassPrizeWork(scheme.getPlayType(), scheme.getPassMode(), itemList,
							ticketList, printAwardList, drawdMatchMap);
				} else {
					Map<String, Map<String, Double>> matchPrintAwardMap = null;
					if (printAwardList != null && printAwardList.size() == 1) {
						matchPrintAwardMap = printAwardList.get(0);
					} else {
						throw new MatchNotEndException("出票SP值未更新.");
					}

					for (int i = 0; i < itemList.size(); i++) {
						JclqMatchItem matchItem = itemList.get(i);
						String matchKey = matchItem.getMatchKey();

						JcWonMatchItem wonItem = new JcWonMatchItem();
						wonItem.setMatchKey(matchKey);
						wonItem.setSelectCount(matchItem.selectedCount());

						JclqMatch match = drawdMatchMap.get(matchKey);
						boolean won = false;
						if (match.isCancel()) {
							wonItem.setAward(1d);
							wonItem.setCancel(true);
							wonItem.setAward(1d);
							won = true;
						} else if (match.isEnded()) {
							Map<String, Double> printAwardMap = matchPrintAwardMap.get(matchKey);
							Double referenceValue = Double.valueOf(""+printAwardMap.get(JclqConstant.REFERENCE_VALUE_KEY));
							Item rs = null;
							if(PlayType.MIX.equals(scheme.getPlayType())){
								 rs = match.getPassResult(matchItem.getPlayType(), referenceValue);	
							}else{
								 rs = match.getPassResult(scheme.getPlayType(), referenceValue);
							}
							if (matchItem.hasSelect(rs)) {
								Double printAward = Double.valueOf(""+printAwardMap.get(rs.getValue()));
								if (printAward == null || printAward <= 0)
									throw new MatchNotEndException("出票SP值未更新.");

								wonItem.setAward(printAward);
								wonItem.setResultItem(rs);
								won = true;
							}
						}
						if (won)
							correctList.add(wonItem);
					}

					if (passTypeList.size() > 1)
						throw new ServiceException("过关类型异常.");

					prizeWork = new JclqSimplePrizeWork(scheme.getPassMode(), scheme.getMultiple(), scheme
							.getPassTypeList().get(0), correctList);
				}
			}
			break;
		default:
			throw new ServiceException("过关模式不合法.");
		}

		if (prizeWork != null && prizeWork.isWon()) {
			try {
				scheme.doUpdateResult(prizeWork.getWonDetail().toString());
				scheme.doUpdatePrize(BigDecimal.valueOf(prizeWork.getTotalPrize()),
						BigDecimal.valueOf(prizeWork.getTotalPrizeAfterTax()), prizeWork.getPrizeDetail().toString());
				//出票回查
				if(null!=prizeWork.getPrintWonItemList()&&!prizeWork.getPrintWonItemList().isEmpty()){
					PrintInterface printInterface = printEntityManager.findPrintInterfaceBy(scheme.getSchemeNumber(), scheme.getLotteryType());
					if(null!=printInterface){
						List<Long> ticketIds = ticketEntityManager.findTicketIdByPrintInterfaceId(printInterface.getId());
						Ticket ticket = null;
						for (Long id : ticketIds) {
							 ticket = ticketEntityManager.getTicket(id);
							 ticket.setChecked(true);
							 if(null!=ticket.getTicketIndex()&&SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())){
									///方案成功才更新出票
								for (PrintWonItem printWonItem: prizeWork.getPrintWonItemList()) {
									if(printWonItem.getIndex().equals(ticket.getTicketIndex())){
										//
										ticket.setWon(true);
										ticket.setTotalPrize(printWonItem.getTotalPrize());
										ticket.setTotalPrizeAfterTax(printWonItem.getTotalPrizeAfterTax());
										ticket.setWonDetail(printWonItem.getWonDetail());
										ticket.setTicket_synchroned(Boolean.FALSE);
									}
								}
							 }
							 ticketEntityManager.saveTicket(ticket);
						}
						
					}
					
				}
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
		} else {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
			scheme.setWon(false);
			scheme.setWonDetail("");
			scheme.setPrize(BigDecimal.ZERO);
			scheme.setPrizeAfterTax(BigDecimal.ZERO);
			scheme.setPrizeDetail("");
		}
		scheme = getSchemeEntityManager().saveScheme(scheme);
	}

	@Override
	protected JclqScheme newSchemeInstance(JclqSchemeDTO schemeDTO) {
		JclqScheme scheme = super.newSchemeInstance(schemeDTO);

		scheme.setPlayType(schemeDTO.getPlayType());
		scheme.setSchemeType(schemeDTO.getSchemeType());
		scheme.setPassMode(schemeDTO.getPassMode());
		scheme.setTicketContent(schemeDTO.getTicketContent());

		long passTypeValue = 0;
		for (PassType passType : schemeDTO.getPassTypes()) {
			passTypeValue |= passType.getValue();
		}
		scheme.setPassType(passTypeValue);

		return scheme;
	}
	@Override
	public void ticketOther(Scheme jclqScheme){
		JclqScheme scheme = (JclqScheme) jclqScheme;
		SalesMode salesMode = scheme.getMode();
		switch (salesMode){
		case COMPOUND:
			JclqCompoundContent compoundContent = scheme.getCompoundContent();
			if (compoundContent != null) {
				List<String> matchKeys = Lists.newArrayList();
				String matchKey = null;
				for (JclqMatchItem matchItem : compoundContent.getItems()) {
					matchKey = matchItem.getMatchKey();
					if(!matchKeys.contains(matchKey)){
						JclqSchemeMatch schemeMatch = new JclqSchemeMatch();
						schemeMatch.setMatchKey(matchKey);
						schemeMatch.setSchemeId(scheme.getId());
						schemeManager.saveSchemeMatch(schemeMatch);
						matchKeys.add(matchKey);
					}
				}
			}
			break;
		case SINGLE:
			List<String> matchkeys = scheme.getSingleContent().getMatchkeys();
			if(matchkeys!=null){
				for(String matchkey:matchkeys){
					JclqSchemeMatch schemeMatch = new JclqSchemeMatch();
					schemeMatch.setMatchKey(matchkey);
					schemeMatch.setSchemeId(scheme.getId());
					schemeManager.saveSchemeMatch(schemeMatch);
				}
			}
			break;
		default:
			throw new ServiceException("投注方式不合法.");
		}
	}
	@Override
	public JclqScheme createScheme(JclqSchemeDTO schemeDTO) {
		JclqScheme scheme = super.createScheme(schemeDTO);
		SalesMode salesMode = scheme.getMode();
		switch (salesMode){
		case COMPOUND:
			JclqCompoundContent compoundContent = scheme.getCompoundContent();
			if (compoundContent != null) {
				List<String> matchKeys = Lists.newArrayList();
				String matchKey = null;
				for (JclqMatchItem matchItem : compoundContent.getItems()) {
					matchKey = matchItem.getMatchKey();
					if(!matchKeys.contains(matchKey)){
						JclqSchemeMatch schemeMatch = new JclqSchemeMatch();
						schemeMatch.setMatchKey(matchKey);
						schemeMatch.setSchemeId(scheme.getId());
						schemeManager.saveSchemeMatch(schemeMatch);
						matchKeys.add(matchKey);
					}
				}
			}
			break;
		case SINGLE:
			List<String> matchkeys = scheme.getSingleContent().getMatchkeys();
			if(matchkeys!=null){
				for(String matchkey:matchkeys){
					JclqSchemeMatch schemeMatch = new JclqSchemeMatch();
					schemeMatch.setMatchKey(matchkey);
					schemeMatch.setSchemeId(scheme.getId());
					schemeManager.saveSchemeMatch(schemeMatch);
				}
			}
			break;
		default:
			throw new ServiceException("投注方式不合法.");
		}

		return scheme;
	}

	@Override
	protected void checkConformPeriodSubscriptionConfig(JclqScheme scheme) {
		super.checkConformPeriodSubscriptionConfig(scheme);

		List<JclqMatch> matchList = matchEntityManager.findMatchsOfUnEnd();
		List<String> keyList = Lists.newArrayList();
		if (matchList != null && !matchList.isEmpty()) {
			for (JclqMatch m : matchList) {
				keyList.add(m.getMatchKey());
			}
		}

		switch (scheme.getMode()) {
		case COMPOUND:
			JclqCompoundContent content = scheme.getCompoundContent();
			for (JclqMatchItem item : content.getItems()) {
				if (!keyList.contains(item.getMatchKey()))
					throw new ServiceException("第" + (item.getMatchKey()) + "场赛事已经截止销售.");
			}
			break;
		case SINGLE:
			JclqSingleContent singleContent = JsonUtil.getObject4JsonString(scheme.getContent(),
					JclqSingleContent.class);
			List<String> matchKeys = singleContent.getMatchkeys();
			for(String matchKey : matchKeys){
				if (!keyList.contains(matchKey)) {
					throw new ServiceException("第" + (matchKey) + "场赛事已经截止销售.");
				}
			}
			break;
		default:
			throw new ServiceException("投注方式不正确.");
		}
	}

	@Override
	public boolean isSaleEnded(Long schemeId) {
		boolean isSaleEnded = super.isSaleEnded(schemeId);

		if (!isSaleEnded) {
			List<JclqMatch> matchList = matchEntityManager.findMatchsOfUnEnd();
			List<String> keyList = Lists.newArrayList();
			if (matchList != null && !matchList.isEmpty()) {
				for (JclqMatch m : matchList) {
					keyList.add(m.getMatchKey());
				}
			}
			JclqScheme scheme = getSchemeEntityManager().getScheme(schemeId);
			switch (scheme.getMode()) {
			case COMPOUND:
				JclqCompoundContent content = scheme.getCompoundContent();
				for (JclqMatchItem item : content.getItems()) {
					if (!keyList.contains(item.getMatchKey())) {
						isSaleEnded = true;
						break;
					}
				}
				break;
			case SINGLE:
				JclqSingleContent singleContent = JsonUtil.getObject4JsonString(scheme.getContent(),
						JclqSingleContent.class);
				List<String> matchKeys = singleContent.getMatchkeys();
				for(String matchKey : matchKeys){
					if (!keyList.contains(matchKey)) {
						isSaleEnded = true;
						break;
					}
				}
				break;
			default:
				throw new ServiceException("投注方式不正确.");
			}
		}
		return isSaleEnded;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void checkConformPeriodInitConfig(Period period, JclqSchemeDTO schemeDTO) {
		super.checkConformPeriodInitConfig(period, schemeDTO);

		List<JclqMatch> matchList = matchEntityManager.findMatchsOfUnEnd();
		Map<String,JclqMatch> keyMap = Maps.newHashMap();
		List<String> keyList = Lists.newArrayList();
		if (matchList != null && !matchList.isEmpty()) {
			for (JclqMatch m : matchList) {
				keyMap.put(m.getMatchKey(),m);
				keyList.add(m.getMatchKey());
			}
		}
		Date endTime = null;
		switch (schemeDTO.getMode()) {
		case COMPOUND:
			@SuppressWarnings("rawtypes")
			Map classMap = new HashMap();
			classMap.put("items", JclqMatchItem.class);
			JclqCompoundContent content = JsonUtil.getObject4JsonString(schemeDTO.getContent(), JclqCompoundContent.class, classMap);
			for (JclqMatchItem item : content.getItems()) {
				endTime = this.getOfficialEndTimeAndV(item.getMatchKey(), keyMap, keyList, endTime, item.getPlayType());					
			}
			schemeDTO.setOfficialEndTime(endTime);
			break;
		case SINGLE:
			@SuppressWarnings("rawtypes")
			Map classMapS = new HashMap();
			classMapS.put("items", JclqMatchItem.class);
			JclqSingleContent singleContent = JsonUtil.getObject4JsonString(schemeDTO.getContent(),
					JclqSingleContent.class,classMapS);
			for (JclqMatchItem item : singleContent.getItems()) {
				endTime = this.getOfficialEndTimeAndV(item.getMatchKey(), keyMap, keyList, endTime, item.getPlayType());					
			}
			schemeDTO.setOfficialEndTime(endTime);
			break;
		default:
			throw new ServiceException("投注方式不正确.");
		}
	}
	
	/**
	 * 验证场次在售及获取最早的官方截止时间
	 * @param matchKey
	 * @param keyMap
	 * @return
	 */
	private Date getOfficialEndTimeAndV(String matchKey,final Map<String,JclqMatch> keyMap,List<String> keyList, Date endTime, PlayType playType){		
		if (!keyList.contains(matchKey))
			throw new ServiceException("第" + (matchKey) + "场赛事已经截止销售.");
		JclqMatch match = keyMap.get(matchKey);
		try {
			if (!match.isOpen(playType, PassMode.PASS)){
				throw new ServiceException("场次{"+match.getMatchKey()+"}玩法{"+playType.getText()+"}没有开售.");
			}
			if (match.isStop()){
				throw new ServiceException("场次{"+match.getMatchKey()+"}已经停止销售.");
			}
			if (endTime == null || endTime.after(match.getTicketOfficialEndTime()))
				endTime = match.getTicketOfficialEndTime();
		} catch (DataException e) {
			throw new ServiceException("获取比赛场次时间错误.");
		}
		return endTime;
	}
	
	public boolean handleTransaction(JclqScheme scheme,List<String> endLineIds) {
		boolean anyEnded = false;// 方案是否包含一场已结束的赛事标识
		List<String> matchKeys = scheme.getSelectedMatchKeys();
		for (String endLineId : endLineIds) {
			if (matchKeys.contains(endLineId)) {
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
		return Lottery.JCLQ;
	}
	@Override
	public Byte getPlayType(JclqSchemeDTO schemeDTO){
		return Byte.valueOf(String.valueOf(schemeDTO.getPlayType().ordinal()));
	}
}
