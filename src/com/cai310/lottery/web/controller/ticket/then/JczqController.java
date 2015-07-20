package com.cai310.lottery.web.controller.ticket.then;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.cache.JczqLocalCache;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.MatchNameUtil;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.dto.lottery.JcMixMatchDTO;
import com.cai310.lottery.dto.lottery.SchemeInfoDTO;
import com.cai310.lottery.dto.lottery.SchemeMatchDTO;
import com.cai310.lottery.dto.lottery.ZcMatchDTO;
import com.cai310.lottery.dto.lottery.ZcMatchResultDTO;
import com.cai310.lottery.dto.lottery.jczq.JczqSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqCompoundContent;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqSingleContent;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.SchemeType;
import com.cai310.lottery.support.jczq.TicketItem;
import com.cai310.lottery.support.jczq.TicketSplitCallback;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class JczqController extends
                      TicketBaseController<JczqScheme, JczqSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;
	

	@Autowired
	protected JczqMatchEntityManager jczqMatchEntityManager;
	

	@Autowired
	private JczqLocalCache jczqLocalCache;
 
	/** 胜平负==0 */
	/** 进球数==1 */
	/** 比分==2 */
	/** 半全场==3 */
	/** 混合==4 */
	/** 让球==5 */
	private Integer playType;
    //com.cai310.lottery.support.jczq.PassType支持多选过关
	private String passType;
	/** 单关 ==0*/
	/** 普通过关 ==1*/
	/** 多选过关 ==2*/
	private Integer schemeType;
	///{"danMaxHit":0,"danMinHit":0,"items":[{"dan":false,"playType":1,"matchKey":"20111218-019","value":3},{"dan":false,"playType":1,"matchKey":"20111218-021","value":2}]}
    private String schemeValue;
	@Autowired
	private JczqSchemeService schemeService;

	@Autowired
	private JczqSchemeEntityManager schemeEntityManager;

	@Autowired
	private JczqMatchEntityManager matchEntityManager;

	private String currentDate;

	private static final int DAY_SIZE = 10;
	
	/** 场次归属的日期 */
	private String matchDate;
	
 
	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	@Override
	protected SchemeService<JczqScheme, JczqSchemeDTO> getSchemeService() {
		return schemeService;
	}
	protected PlayType buildPlayType() throws WebDataException{
        try{
        	return PlayType.values()[playType];
        }catch(Exception e){
			logger.warn("玩法解析错误."+e.getMessage());
			throw new WebDataException("5-玩法解析错误.");
		}
    }
	protected SchemeType buildSchemeType() throws WebDataException{
	    try{
	    	return SchemeType.values()[schemeType];
	    }catch(Exception e){
			logger.warn("投注方式解析错误."+e.getMessage());
		     throw new WebDataException("5-投注方式解析错误.");
		}
    }
	protected List<PassType> buildPassType() throws WebDataException{
		try{
			List<PassType> list = Lists.newArrayList();
	    	if(passType.indexOf(",")==-1){
	    		list.add(PassType.values()[Integer.valueOf(passType)]);
	    	}else{
	    		String[] arr = passType.split(",");
	    		for (int i = 0; i < arr.length; i++) {
	    			list.add(PassType.values()[Integer.valueOf(arr[i])]);
				}
	    	}
	    	return list;
        }catch(Exception e){
			logger.warn("过关玩法解析错误."+e.getMessage());
			throw new WebDataException("5-过关玩法解析错误.");
		}
    	
    }
	protected Integer buildDanMinHit(Map<String, Object> map) throws WebDataException{
		 try{
			 Integer danMinHit =  0;
			 if(null!=map.get("danMinHit")&&StringUtils.isNotBlank(String.valueOf(map.get("danMinHit")))){
				 danMinHit = Integer.valueOf(String.valueOf(map.get("danMinHit")));
			 }
	    	 return danMinHit;
		 }catch(Exception e){
				logger.warn("最小命中解析错误."+e.getMessage());
				throw new WebDataException("5-最小命中解析错误.");
		 }
    }
	protected Integer buildDanMaxHit(Map<String, Object> map) throws WebDataException{
		try{
			 Integer danMaxHit =  0;
			 if(null!=map.get("danMaxHit")&&StringUtils.isNotBlank(String.valueOf(map.get("danMaxHit")))){
				 danMaxHit = Integer.valueOf(String.valueOf(map.get("danMaxHit")));
			 }
	    	 return danMaxHit;
		 }catch(Exception e){
				logger.warn("最大命中解析错误."+e.getMessage());
				throw new WebDataException("5-最大命中解析错误.");
		 }
    }
	@SuppressWarnings("unchecked")
	protected List<JczqMatchItem> buildJczqMatchItemList(Map<String, Object> value) throws WebDataException{
		try{
			String[] items = JsonUtil.getStringArray4Json(String.valueOf(value.get("items")));
			final List<JczqMatchItem> correctList = Lists.newArrayList();
			Map<String, Object> map;
			PlayType playType = buildPlayType();
			Set<String> matchKeyList = Sets.newHashSet();
			String matchKey = null;
			for (String itemStr : items) {
				map = JsonUtil.getMap4Json(itemStr);
				JczqMatchItem item = new JczqMatchItem();
				matchKey = String.valueOf(map.get("matchKey"));
				item.setMatchKey(matchKey);
				matchKeyList.add(matchKey);
				if(null!=map.get("dan")){
					item.setDan(Boolean.valueOf(String.valueOf(map.get("dan"))));
				}
				if(playType.equals(PlayType.MIX)){
					PlayType playTypeBet =  PlayType.values()[Integer.valueOf(String.valueOf(map.get("playTypeItem")))];
					item.setValue(getBetValue(playTypeBet,String.valueOf(map.get("value"))));	
					item.setPlayType(playTypeBet);
				}else{
					item.setValue(getBetValue(playType,String.valueOf(map.get("value"))));	
					item.setPlayType(playType);
				}
				
				correctList.add(item);
			}
			if(correctList.size()!=matchKeyList.size()){
				logger.warn("投注内容分场次重复.");
				throw new WebDataException("5-投注内容分场次重复.");
			}
			Collections.sort(correctList);
			return correctList;
		}catch(Exception e){
			logger.warn("投注内容分析错误."+e.getMessage());
			throw new WebDataException("5-投注内容分析错误.");
		}
    }
	
	@Transient
	public Integer getBetValue(PlayType playType,String value) {
		Integer flag=0;
		List<String> list = Lists.newArrayList();
		if(value.indexOf("^")!=-1){
			String[] arr = value.split("\\^");
			for (String str : arr) {
				list.add(str);
			}
		}else if(value.indexOf(",")!=-1){
			String[] arr = value.split(",");
			for (String str : arr) {
				list.add(str);
			}
		}else{
			list.add(value);
		}
		switch (playType) {
		case SPF:
		case RQSPF:
			for (String str : list) {
				flag+=0x1 <<ItemSPF.valueOfValue(str).ordinal();
			}
			return flag;
		case JQS:
			for (String str : list) {
				flag+=0x1 << ItemJQS.valueOfValue(str).ordinal();
			}
			return flag;
		case BF:
			for (String str : list) {
				flag+=0x1 << ItemBF.valueOfValue(str).ordinal();
			}
			return flag;
		case BQQ:
			for (String str : list) {
				flag+=0x1 << ItemBQQ.valueOfValue(str).ordinal();
			}
			return flag;
		default:
			throw new RuntimeException("玩法不正确.");
		}
	}
	
	@Override
	protected JczqSchemeDTO buildSchemeDTO() throws WebDataException {
		checkCreateForm();
		Map<String, Object> map;
		try{
			map = JsonUtil.getMap4Json(schemeValue);
		}catch(Exception e){
			logger.warn("投注内容分析错误."+e.getMessage());
			throw new WebDataException("5-投注内容分析错误.");
		}
		Integer danMinHit = buildDanMinHit(map);
		Integer danMaxHit = buildDanMaxHit(map);
		final List<JczqMatchItem> correctList = buildJczqMatchItemList(map);
			
			
		
		SalesMode salesMode = buildSalesMode();
		switch (salesMode) {
		case COMPOUND:
			checkCreateFormOfCOMPOUND(map);
			break;
		case SINGLE:
			throw new WebDataException("5-目前不支持单式投注.");
		default:
			throw new WebDataException("5-投注方式不合法.");
		}

		JczqSchemeDTO dto = super.buildSchemeDTO();
		PlayType playType = buildPlayType();
		dto.setPlayType(playType);
		
		SchemeType schemeType = buildSchemeType();
		dto.setSchemeType(schemeType);
		
		List<PassType> passTypes = buildPassType();
		dto.setPassTypes(passTypes);

	
		
		final StringBuilder ticketContentBuilder = new StringBuilder();
		TicketSplitCallback callback = new TicketSplitCallback() {

			@Override
			public void handle(List<JczqMatchItem> matchItemList, PassType passType, int multiple) {
				long matchFlag = JczqUtil.chg2flag(matchItemList, correctList);
				TicketItem ticketItem = new TicketItem(matchFlag, passType, multiple);
				ticketContentBuilder.append(ticketItem.toString()).append(TicketItem.ITEM_AND);
			}
		};
		switch (schemeType) {
		case SINGLE:
			dto.setPassMode(PassMode.SINGLE);

			JczqUtil.singleSplit(callback, getMultiple(), correctList);
			break;
		case SIMPLE_PASS:
			dto.setPassMode(PassMode.PASS);

			JczqUtil.splitWithMultiple(callback, getMultiple(), passTypes.get(0),
					correctList);
			break;
		case MULTIPLE_PASS:
			dto.setPassMode(PassMode.PASS);

			final List<JczqMatchItem> danList = new ArrayList<JczqMatchItem>();
			final List<JczqMatchItem> undanList = new ArrayList<JczqMatchItem>();
			Integer maxMatchSize = null;
			if(playType.equals(PlayType.MIX)){
				for (JczqMatchItem jczqMatchItem : correctList) {
					if(null==maxMatchSize||maxMatchSize>jczqMatchItem.getPlayType().getMaxMatchSize()){
						maxMatchSize = jczqMatchItem.getPlayType().getMaxMatchSize();
					}
					if (jczqMatchItem.isDan()){
						danList.add(jczqMatchItem);
					}else{
						undanList.add(jczqMatchItem);
					}
				}
			}else{
				maxMatchSize = playType.getMaxMatchSize();
			}
			if (danList.size() == 0) {
				JczqUtil.undanMultiplePassSplit(callback, getMultiple(), maxMatchSize,
						passTypes, correctList);
			} else {
				if (danMinHit == null || danMinHit <= 0)
					danMinHit = danList.size();
				if (danMaxHit == null || danMaxHit <= 0)
					danMaxHit = danList.size();
				JczqUtil.danMultiplePassSplit(callback, getMultiple(),passTypes, danList,
						undanList, danMinHit, danMaxHit);
			}
			break;
		}
		Date endTime = null;
		if(null!=dto.getPlayType()&&PlayType.MIX.equals(dto.getPlayType())){
	    	dto.setPassMode(PassMode.MIX_PASS);
	    	/////////cyy，检查场次是否截至
	    	List<String> matchKeyList = new ArrayList<String>();
			Map<String,JczqMatchItem> matchItemMap = Maps.newHashMap();
			for (JczqMatchItem item : correctList) {
				matchKeyList.add(item.getMatchKey());
				matchItemMap.put(item.getMatchKey(), item);
			}
			List<JczqMatch> matchList = matchEntityManager.findMatchs(matchKeyList);
			if(matchList.isEmpty())throw new WebDataException("5-场次{"+matchKeyList+"}已经停止销售.");
			for (JczqMatch jczqMatch : matchList) {
				JczqMatchItem item = matchItemMap.get(jczqMatch.getMatchKey());
				if (!jczqMatch.isOpen(item.getPlayType(), PassMode.PASS))
					throw new WebDataException("场次{"+jczqMatch.getMatchKey()+"}已经停止销售.");
				try {
					if (jczqMatch.isStop())
						throw new WebDataException("5-场次{"+jczqMatch.getMatchKey()+"}已经停止销售.");
					if (endTime == null || endTime.after(jczqMatch.getTicketOfficialEndTime()))
						endTime = jczqMatch.getTicketOfficialEndTime();
				} catch (DataException e) {
					logger.warn(e.getMessage());
					throw new WebDataException("5-场次{"+jczqMatch.getMatchKey()+"}获取出票时间出错.");
				}
			}
	    }else{
			/////////cyy，检查场次是否截至
			List<String> matchKeyList = new ArrayList<String>();
			for (JczqMatchItem item : correctList) {
				matchKeyList.add(item.getMatchKey());
			}
			List<JczqMatch> matchList = matchEntityManager.findMatchs(matchKeyList);
			if(matchList.isEmpty())throw new WebDataException("5-场次{"+matchKeyList+"}已经停止销售.");
			for (JczqMatch jczqMatch : matchList) {
				if (!jczqMatch.isOpen(dto.getPlayType(), dto.getPassMode()))
					throw new WebDataException("5-场次{"+jczqMatch.getMatchKey()+"}已经停止销售.");
				try {
					if (jczqMatch.isStop())
						throw new WebDataException("5-场次{"+jczqMatch.getMatchKey()+"}已经停止销售.");
					if (endTime == null || endTime.after(jczqMatch.getTicketOfficialEndTime()))
						endTime = jczqMatch.getTicketOfficialEndTime();
				} catch (DataException e) {
					logger.warn(e.getMessage());
					throw new WebDataException("5-场次{"+jczqMatch.getMatchKey()+"}获取出票时间出错.");
				}
			}
	    }
		dto.setOfficialEndTime(endTime);
		if (ticketContentBuilder.length() > 0)
			ticketContentBuilder.delete(ticketContentBuilder.length() - TicketItem.ITEM_AND.length(),
					ticketContentBuilder.length());
		dto.setTicketContent(ticketContentBuilder.toString());
		return dto;
	}

	protected void checkCreateForm() throws WebDataException {
		if (buildPlayType() == null)
			throw new WebDataException("5-玩法类型不能为空.");

		if (buildSchemeType() == null)
			throw new WebDataException("5-方案类型不能为空.");
		if (buildPassType() == null || buildPassType().isEmpty())
			throw new WebDataException("5-过关方式不能为空.");
	}

	protected void checkCreateFormOfCOMPOUND(Map<String, Object> map) throws WebDataException {
		final List<JczqMatchItem> correctList = buildJczqMatchItemList(map);
		if (correctList == null || correctList.isEmpty())
			throw new WebDataException("5-投注内容不能为空.");

		switch (buildSchemeType()) {
		case SINGLE:
			if (correctList.size() > JczqConstant.SINGLE_MAX_MATCH_SIZE)
				throw new WebDataException("5-单关模式不能选择超过" + JczqConstant.SINGLE_MAX_MATCH_SIZE + "场.");

			if (buildPassType() == null || buildPassType().size() != 1
					|| buildPassType().get(0) != PassType.P1)
				throw new WebDataException("5-过关方式不正确.");
			break;
		case SIMPLE_PASS:
			Integer maxMatchSize = null;
			if(buildPlayType().equals(PlayType.MIX)){
				for (JczqMatchItem jczqMatchItem : correctList) {
					if(null==maxMatchSize||maxMatchSize>jczqMatchItem.getPlayType().getMaxMatchSize())
						maxMatchSize = jczqMatchItem.getPlayType().getMaxMatchSize();
				}
			}else{
				maxMatchSize = buildPlayType().getMaxMatchSize();
			}
			
			if (correctList.size() > maxMatchSize)
				throw new WebDataException("[" + buildPlayType().getText() + "]普通过关选择不能超过" + maxMatchSize + "场.");
			
			if (buildPassType().size() > 1)
				throw new WebDataException("5-普通过关模式只能选择一个过关方式.");

			PassType passType = buildPassType().get(0);
			if (passType == PassType.P1)
				throw new WebDataException("5-过关模式不能选择单关过关方式.");
			if (passType.getMatchCount() != correctList.size())
				throw new WebDataException("5-选择的场次数目与过关方式不匹配.");
			break;
		case MULTIPLE_PASS:
			for (PassType type : buildPassType()) {
				if (type.getUnits() != 1)
					throw new WebDataException("5-多选过关模式不支持[" + type.getText() + "]过关.");
				maxMatchSize = null;
				if(buildPlayType().equals(PlayType.MIX)){
					for (JczqMatchItem jczqMatchItem : correctList) {
						if(null==maxMatchSize||maxMatchSize>jczqMatchItem.getPlayType().getMaxMatchSize())
							maxMatchSize = jczqMatchItem.getPlayType().getMaxMatchSize();
					}
				}else{
					maxMatchSize = buildPlayType().getMaxMatchSize();
				}
				
				if (type.getMatchCount() > maxMatchSize)
					throw new WebDataException("5-过关方式不正确.");
			}
			break;
		default:
			throw new WebDataException("5-过关模式不合法.");
		}
	}

	// ----------------------------------------------------

	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		// 在Controller那边做一些数据验证
		Map<String, Object> map;
		try{
			map = JsonUtil.getMap4Json(schemeValue);
		}catch(Exception e){
			logger.warn("投注内容分析错误."+e.getMessage());
			throw new WebDataException("5-投注内容分析错误.");
		}
		Integer danMinHit = buildDanMinHit(map);
		Integer danMaxHit = buildDanMaxHit(map);
		final List<JczqMatchItem> correctList = buildJczqMatchItemList(map);
		
		PassType minPassType = buildPassType().get(0);// 最小的过关方式

		final List<JczqMatchItem> danList = new ArrayList<JczqMatchItem>();
		final List<JczqMatchItem> unDanList = new ArrayList<JczqMatchItem>();
		for (JczqMatchItem item : correctList) {
			if (item.isDan())
				danList.add(item);
			else
				unDanList.add(item);
		}

		// 检查设胆数目
		if (danList.size() > minPassType.getMatchCount()) {
			throw new WebDataException("5-设置的胆码不能大于最小的过关场次.");
		}

		if (danMinHit == null || danMinHit <= 0)
			danMinHit = danList.size();
		else if (danMinHit > danList.size())
			throw new WebDataException("5-模糊设胆不正确.");

		if (danMinHit + unDanList.size() < minPassType.getMatchCount())
			throw new WebDataException("5-模糊设胆不正确.");

		if (danMaxHit == null || danMaxHit <= 0)
			danMaxHit = danList.size();

		int units = 0;
		for (PassType passType : buildPassType()) {
			for (final int needs : passType.getPassMatchs()) {
				units += UnitsCountUtils.countUnits(needs, danList, danMinHit, danMaxHit, unDanList);
				if (units > Constant.MAX_UNITS)
					throw new WebDataException("5-复式方案单倍注数不能大于" + Constant.MAX_UNITS + "注.");
			}
		}

		JczqCompoundContent content = new JczqCompoundContent();
		content.setItems(correctList);
		content.setDanMinHit(danMinHit);
		content.setDanMaxHit(danMaxHit);

		return new ContentBean(units, JSONObject.fromObject(content).toString());
	}

	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		throw new WebDataException("5-竞彩足球目前暂时不支持单式投注.");
	}

	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public Integer getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(Integer schemeType) {
		this.schemeType = schemeType;
	}

	public String getSchemeValue() {
		return schemeValue;
	}

	public void setSchemeValue(String schemeValue) {
		this.schemeValue = schemeValue;
	}

	public void setSchemeService(JczqSchemeService schemeService) {
		this.schemeService = schemeService;
	}

	@Override
	protected Lottery getLottery() {

		return Lottery.JCZQ;
	}

	@Override
	protected void buildReqParamVisitor(ReqParamVisitor reqParamVisitor) {
		if(null!=reqParamVisitor.getOrderId()){
			this.orderId = reqParamVisitor.getOrderId().trim();
		}
		if(null!=reqParamVisitor.getPlayType()){
			this.playType = Integer.valueOf(reqParamVisitor.getPlayType());
		}
		if(null!=reqParamVisitor.getPlayType()){
			this.mode = Integer.valueOf(reqParamVisitor.getPlayType());
		}
		if(null!=reqParamVisitor.getPassType()){
			this.passType = String.valueOf(reqParamVisitor.getPassType());
		}
		if(null!=reqParamVisitor.getMode()){
			this.mode = Integer.valueOf(reqParamVisitor.getMode());
		}
		List<Period> periodList = periodManager.findCurrentPeriods(getLottery(),true);
		if(null!=periodList&&!periodList.isEmpty()){
			this.periodNumber = periodList.get(0).getPeriodNumber();
		}
		if(null!=reqParamVisitor.getCost()){
			this.schemeCost = Integer.valueOf(reqParamVisitor.getCost());
		}
		if(null!=reqParamVisitor.getMultiple()){
			this.multiple = Integer.valueOf(reqParamVisitor.getMultiple());
		}
		if(null!=reqParamVisitor.getUnits()){
			this.units = Integer.valueOf(reqParamVisitor.getUnits());
		}
		if(null!=reqParamVisitor.getValue()){
			this.schemeValue = String.valueOf(reqParamVisitor.getValue());
		}
		if(null!=reqParamVisitor.getType()){
			this.schemeType = Integer.valueOf(reqParamVisitor.getType());
		}
		if(null!=reqParamVisitor.getShareType()){
			this.shareType = Integer.valueOf(reqParamVisitor.getShareType());
		}
		if(null!=reqParamVisitor.getSubscriptionCost()){
			this.subscriptionCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getSubscriptionCost()));
		}
		if(null!=reqParamVisitor.getBaodiCost()){
			this.baodiCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getBaodiCost()));
		}
		if(null!=reqParamVisitor.getCommissionRate()){
			this.commissionRate = Float.valueOf(reqParamVisitor.getCommissionRate());
		}
		if(null!=reqParamVisitor.getMinSubscriptionCost()){
			this.minSubscriptionCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getMinSubscriptionCost()));
		}
		if(null!=reqParamVisitor.getSecretType()){
			this.secretType = Integer.valueOf(reqParamVisitor.getSecretType());
		}
	}
	@Override
	protected List buildMatchList()throws WebDataException{
		List list = Lists.newArrayList();
		List<JczqMatch> matchList = jczqMatchEntityManager.findMatchsOfTicketUnEnd();
		PlayType playType =this.buildPlayType();
		SchemeType schemeType = this.buildSchemeType();
		PassMode passMode = null;
		if(SchemeType.SINGLE.equals(schemeType)){
			passMode = PassMode.SINGLE;
		}else{
			passMode = PassMode.PASS;
		}
		if(playType.equals(PlayType.MIX)){
			JcMixMatchDTO zcMatchDTO = null;
			PlayType[] playTypeArr = new PlayType[]{PlayType.RQSPF,PlayType.SPF};
			Map<String, Map<String,Map<String, RateItem>>> allRate = Maps.newHashMap();
			for (PlayType mixPlayType : playTypeArr) {
				Map<String, RateItem> rateMap= null;
				Map<String,Map<String, RateItem>> oldRateMap= null;
				String matchKey= null;
				Map<String, Map<String, RateItem>> rate = jczqLocalCache.getRateData(mixPlayType, PassMode.PASS);
				for (Map.Entry<String,Map<String,RateItem>> m : rate.entrySet()) {
					rateMap = m.getValue();
					matchKey = m.getKey();
					oldRateMap = allRate.get(matchKey);
					if(null==oldRateMap){
						oldRateMap = Maps.newHashMap();
					}
					oldRateMap.put(mixPlayType.name()+"", rateMap);
					allRate.put(matchKey, oldRateMap);
				}
			}
			for (JczqMatch jczqMatch : matchList) {
				zcMatchDTO = new JcMixMatchDTO();
				zcMatchDTO.setCancel(jczqMatch.isCancel());
				zcMatchDTO.setEnded(jczqMatch.isEnded());
				zcMatchDTO.setGameColor(jczqMatch.getGameColor());
				zcMatchDTO.setGameName(MatchNameUtil.getShotName(jczqMatch.getGameName()));
				zcMatchDTO.setGuestTeamName(jczqMatch.getGuestTeamName());
				if(null==jczqMatch.getHandicap()){
					zcMatchDTO.setHandicap(Float.valueOf(0));
				}else{
					///客户端还没开发好让球胜平负屏蔽让球
					//zcMatchDTO.setHandicap(Float.valueOf(0));
					zcMatchDTO.setHandicap(Float.valueOf(jczqMatch.getHandicap()));
				}
				zcMatchDTO.setHomeTeamName(jczqMatch.getHomeTeamName());
				zcMatchDTO.setMatchKey(jczqMatch.getMatchKey());
				zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(jczqMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
				try {
					zcMatchDTO.setEndSaleTime(com.cai310.utils.DateUtil.dateToStr(jczqMatch.getWebOfficialEndTime(), "yyyy-MM-dd HH:mm:ss"));
				} catch (DataException e1) {
					logger.warn("{"+jczqMatch.getMatchKey()+"}获取比赛结束时间错误");
				}
				Map<String, Boolean> open = Maps.newHashMap();
				for (PlayType mixPlayType : playTypeArr) {
					open.put(mixPlayType.ordinal()+"", true);
					if(!jczqMatch.isOpen(mixPlayType, PassMode.PASS)){
						open.put(mixPlayType.ordinal()+"", false);
					}
					try {
						if(new Date().after(jczqMatch.getWebOfficialEndTime())){
							open.put(mixPlayType.ordinal()+"", false);
						}
					} catch (DataException e) {
						logger.warn("{"+jczqMatch.getMatchKey()+"}获取比赛结束时间错误");
						open.put(mixPlayType.ordinal()+"", false);
						continue;
					}
				}
				zcMatchDTO.setMixOpen(open);
				if(null!=allRate.get(jczqMatch.getMatchKey())){
					zcMatchDTO.setMixSp(allRate.get(jczqMatch.getMatchKey()));
				}
				list.add(zcMatchDTO);
			}
			return list;
		}else{
			ZcMatchDTO zcMatchDTO = null;
			Map<String, Map<String, RateItem>> rate = jczqLocalCache.getRateData(playType, passMode);
			for (JczqMatch jczqMatch : matchList) {
				zcMatchDTO = new ZcMatchDTO();
				zcMatchDTO.setCancel(jczqMatch.isCancel());
				zcMatchDTO.setEnded(jczqMatch.isEnded());
				zcMatchDTO.setGameColor(jczqMatch.getGameColor());
				zcMatchDTO.setGameName(MatchNameUtil.getShotName(jczqMatch.getGameName()));
				zcMatchDTO.setGuestTeamName(jczqMatch.getGuestTeamName());
				if(null==jczqMatch.getHandicap()){
					zcMatchDTO.setHandicap(Float.valueOf(0));
				}else{
					///客户端还没开发好让球胜平负屏蔽让球
					//zcMatchDTO.setHandicap(Float.valueOf(0));
					zcMatchDTO.setHandicap(Float.valueOf(jczqMatch.getHandicap()));
				}
				zcMatchDTO.setHomeTeamName(jczqMatch.getHomeTeamName());
				zcMatchDTO.setMatchKey(jczqMatch.getMatchKey());
				zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(jczqMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
				try {
					zcMatchDTO.setEndSaleTime(com.cai310.utils.DateUtil.dateToStr(jczqMatch.getWebOfficialEndTime(), "yyyy-MM-dd HH:mm:ss"));
				} catch (DataException e1) {
					logger.warn("{"+jczqMatch.getMatchKey()+"}获取比赛结束时间错误");
				}
				zcMatchDTO.setOpen(true);
				if(!jczqMatch.isOpen(playType, passMode)){
					zcMatchDTO.setOpen(false);
				}
				try {
					if(new Date().after(jczqMatch.getWebOfficialEndTime())){
						zcMatchDTO.setOpen(false);
					}
				} catch (DataException e) {
					logger.warn("{"+jczqMatch.getMatchKey()+"}获取比赛结束时间错误");
					zcMatchDTO.setOpen(false);
					continue;
				}
				if(null!=rate.get(jczqMatch.getMatchKey())){
					zcMatchDTO.setSp(rate.get(jczqMatch.getMatchKey()));
				}
				list.add(zcMatchDTO);
			}
			return list;
		}
		
	}
	@Override
	protected SchemeInfoDTO getSchemeMatchDTO(Scheme scheme) throws WebDataException {
		SchemeInfoDTO schemeInfoDTO = new SchemeInfoDTO();
		JczqScheme jczqScheme = (JczqScheme) scheme;
		if(jczqScheme.isCompoundMode()){
			JczqCompoundContent compoundContent = jczqScheme.getCompoundContent();
	        
			Map<String, JczqMatch> matchMap = Maps.newLinkedHashMap();
			List<String> matchKeys = Lists.newArrayList();
			for (JczqMatchItem matchItem : compoundContent.getItems()) {
				matchKeys.add(matchItem.getMatchKey());
			}
			List<JczqMatch> matchs = matchEntityManager.findMatchs(matchKeys);
			for (JczqMatch jczqMatch : matchs) {
				matchMap.put(jczqMatch.getMatchKey(), jczqMatch);
			}
			StringBuffer sb = new StringBuffer();
			int danSize = 0;
			List<SchemeMatchDTO> list = Lists.newArrayList();
			PlayType playType = jczqScheme.getPlayType();
			for (JczqMatchItem matchItem : compoundContent.getItems()) {
				if(PlayType.MIX.equals(jczqScheme.getPlayType())){
					playType = matchItem.getPlayType();
				}
				JczqMatch jczqMatch = matchMap.get(matchItem.getMatchKey());
				SchemeMatchDTO schemeMatchDTO = new SchemeMatchDTO();
				schemeMatchDTO.setMatchKey(jczqMatch.getMatchKeyText());
				schemeMatchDTO.setDan(matchItem.isDan());
				schemeMatchDTO.setCancel(jczqMatch.isCancel());
				schemeMatchDTO.setEnded(jczqMatch.isEnded());
				schemeMatchDTO.setGameColor(jczqMatch.getGameColor());
				schemeMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(jczqMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
				schemeMatchDTO.setPlayType(playType.ordinal());
				
				schemeMatchDTO.setGameName(MatchNameUtil.getShotName(jczqMatch.getGameName()));
				schemeMatchDTO.setGuestTeamName(jczqMatch.getGuestTeamName());
				schemeMatchDTO.setHomeTeamName(jczqMatch.getHomeTeamName());
				schemeMatchDTO.setHomeHalfScore(jczqMatch.getHalfHomeScore());
				schemeMatchDTO.setHomeScore(jczqMatch.getFullHomeScore());
				schemeMatchDTO.setGuestHalfScore(jczqMatch.getHalfGuestScore());
				schemeMatchDTO.setGuestScore(jczqMatch.getFullGuestScore());
				if (matchItem.isDan()) {
					schemeMatchDTO.setDan(Boolean.TRUE);
					danSize++;
				}
				if(null==jczqMatch.getHandicap()){
					schemeMatchDTO.setHandicap(Float.valueOf(0));
				}else{
					schemeMatchDTO.setHandicap(Float.valueOf(jczqMatch.getHandicap()));
				}
				sb.setLength(0);
				for (Item item : playType.getAllItems()) {
					if ((matchItem.getValue() & (0x1 << item.ordinal())) > 0) {
						if(null==jczqMatch){
							sb.append(item.getText()).append(",");
						}else{
							if(null!=jczqMatch.getResult(playType)&&item.equals(jczqMatch.getResult(playType))){
								sb.append("<font color='#aaccdd'>"+item.getText()+"</font>").append(",");
								//命中
							}else{
								sb.append(item.getText()).append(",");
							}
							
						}
						
					}
				}
				if (sb.length() > 0) {
					sb.deleteCharAt(sb.length() - 1);
				}
				schemeMatchDTO.setBet(sb.toString());
				if(null!=jczqMatch.getResult(playType)){
					schemeMatchDTO.setResult(jczqMatch.getResult(playType).getText());
				}
				list.add(schemeMatchDTO);
			}
			schemeInfoDTO.setItems(list);
			if (compoundContent.getDanMinHit() != null && compoundContent.getDanMinHit() > 0) {
				schemeInfoDTO.setDanMinHit(compoundContent.getDanMinHit());
			}
			if (compoundContent.getDanMaxHit() != null && compoundContent.getDanMaxHit() > 0
					&& compoundContent.getDanMaxHit() < danSize) {
				schemeInfoDTO.setDanMaxHit(compoundContent.getDanMaxHit());
			}
			schemeInfoDTO.setPassTypeText(jczqScheme.getPassTypeText());
			return schemeInfoDTO;
		}else{
			///
			JczqSingleContent jczqSingleContent = jczqScheme.getSingleContent();
	        
			Map<String, JczqMatch> matchMap = Maps.newLinkedHashMap();
			List<String> matchKeys = jczqSingleContent.getMatchkeys();
			List<JczqMatch> matchs = matchEntityManager.findMatchs(matchKeys);
			for (JczqMatch jczqMatch : matchs) {
				matchMap.put(jczqMatch.getMatchKey(), jczqMatch);
			}
			StringBuffer sb = new StringBuffer();
			int danSize = 0;
			List<SchemeMatchDTO> list = Lists.newArrayList();
			PlayType playType = jczqScheme.getPlayType();
			for (JczqMatchItem matchItem : jczqSingleContent.getItems()) {
				if(PlayType.MIX.equals(jczqScheme.getPlayType())){
					playType = matchItem.getPlayType();
				}
				JczqMatch jczqMatch = matchMap.get(matchItem.getMatchKey());
				SchemeMatchDTO schemeMatchDTO = new SchemeMatchDTO();
				schemeMatchDTO.setMatchKey(jczqMatch.getMatchKeyText());
				schemeMatchDTO.setDan(matchItem.isDan());
				schemeMatchDTO.setCancel(jczqMatch.isCancel());
				schemeMatchDTO.setEnded(jczqMatch.isEnded());
				schemeMatchDTO.setGameColor(jczqMatch.getGameColor());
				schemeMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(jczqMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
				schemeMatchDTO.setPlayType(playType.ordinal());
				
				schemeMatchDTO.setGameName(MatchNameUtil.getShotName(jczqMatch.getGameName()));
				schemeMatchDTO.setGuestTeamName(jczqMatch.getGuestTeamName());
				schemeMatchDTO.setHomeTeamName(jczqMatch.getHomeTeamName());
				schemeMatchDTO.setHomeHalfScore(jczqMatch.getHalfHomeScore());
				schemeMatchDTO.setHomeScore(jczqMatch.getFullHomeScore());
				schemeMatchDTO.setGuestHalfScore(jczqMatch.getHalfGuestScore());
				schemeMatchDTO.setGuestScore(jczqMatch.getFullGuestScore());
				if (matchItem.isDan()) {
					schemeMatchDTO.setDan(Boolean.TRUE);
					danSize++;
				}
				if(null==jczqMatch.getHandicap()){
					schemeMatchDTO.setHandicap(Float.valueOf(0));
				}else{
					schemeMatchDTO.setHandicap(Float.valueOf(jczqMatch.getHandicap()));
				}
				schemeMatchDTO.setBet(jczqScheme.getContentText());
				if(null!=jczqMatch.getResult(playType)){
					schemeMatchDTO.setResult(jczqMatch.getResult(playType).getText());
				}
				list.add(schemeMatchDTO);
			}
			schemeInfoDTO.setDanMinHit(0);
			schemeInfoDTO.setDanMaxHit(0);
			schemeInfoDTO.setPassTypeText(jczqScheme.getPassTypeText());
			return schemeInfoDTO;
		}
	
	}

	@Override
	protected List buildMatchResultList() throws WebDataException {
		List list = Lists.newArrayList();
		List<JczqMatch> matchList = jczqMatchEntityManager.findMatchs(Integer.valueOf(this.matchDate));
		ZcMatchResultDTO zcMatchDTO=null;
		for (JczqMatch jczqMatch : matchList) {
				zcMatchDTO = new ZcMatchResultDTO();
				zcMatchDTO.setCancel(jczqMatch.isCancel());
				zcMatchDTO.setEnded(jczqMatch.isEnded());
				zcMatchDTO.setGameColor(jczqMatch.getGameColor());
				zcMatchDTO.setGameName(MatchNameUtil.getShotName(jczqMatch.getGameName()));
				zcMatchDTO.setGuestTeamName(jczqMatch.getGuestTeamName());
				if(null==jczqMatch.getHandicap()){
					zcMatchDTO.setHandicap(Float.valueOf(0));
				}else{
					zcMatchDTO.setHandicap(Float.valueOf(jczqMatch.getHandicap()));
				}
				zcMatchDTO.setHomeTeamName(jczqMatch.getHomeTeamName());
				zcMatchDTO.setMatchKey(jczqMatch.getMatchKey());
				zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(jczqMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
				zcMatchDTO.setHomeHalfScore(jczqMatch.getHalfHomeScore());
				zcMatchDTO.setHomeScore(jczqMatch.getFullHomeScore());
				zcMatchDTO.setGuestHalfScore(jczqMatch.getHalfGuestScore());
				zcMatchDTO.setGuestScore(jczqMatch.getFullGuestScore());
				StringBuffer resultSb = new StringBuffer();
				StringBuffer spSb = new StringBuffer();
				if(jczqMatch.isCancel()||jczqMatch.isEnded()){
					for (PlayType playType : PlayType.values()) {
						if(playType.equals(PlayType.MIX))continue;
						Item item = jczqMatch.getResult(playType);
						resultSb.append(null==item?item:item.getText()).append("|");
						Double sp = jczqMatch.getResultSp(playType);
						spSb.append(sp).append("|");
					}
					if(resultSb.indexOf("|")!=-1){
						zcMatchDTO.setResult(resultSb.delete(resultSb.length()-1, resultSb.length()).toString());
					}
					if(spSb.indexOf("|")!=-1){
						zcMatchDTO.setResultSp(spSb.delete(spSb.length()-1, spSb.length()).toString());
					}
				}
				list.add(zcMatchDTO);
			
		}
		return list;
	}
}
