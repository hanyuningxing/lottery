package com.cai310.lottery.web.controller.ticket.then;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.cache.JclqLocalCache;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.MatchNameUtil;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.dto.lottery.JcMixMatchDTO;
import com.cai310.lottery.dto.lottery.SchemeInfoDTO;
import com.cai310.lottery.dto.lottery.SchemeMatchDTO;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.lottery.ZcMatchDTO;
import com.cai310.lottery.dto.lottery.ZcMatchResultDTO;
import com.cai310.lottery.dto.lottery.jclq.JclqSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqCompoundContent;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqSimplePrizeWork;
import com.cai310.lottery.support.jclq.JclqTicketCombination;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jclq.SchemeType;
import com.cai310.lottery.support.jclq.TicketItem;
import com.cai310.lottery.support.jclq.TicketSplitCallback;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.SchemeBaseController;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ibm.icu.util.Calendar;

public class JclqController extends
                      TicketBaseController<JclqScheme, JclqSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;

	@Autowired
	private JclqLocalCache jclqLocalCache;

	@Autowired
	protected JclqMatchEntityManager jclqMatchEntityManager;
	/** 胜负 0 */

	/** 让分胜负  1*/

	/** 胜分差  2*/

	/** 大小分  3*/
	/** 混合==4 */
	private Integer playType;
    //com.cai310.lottery.support.jclq.PassType支持多选过关
	private String passType;
	/** 单关 ==0*/
	/** 普通过关 ==1*/
	/** 多选过关 ==2*/
	private Integer schemeType;
	///{"danMaxHit":0,"danMinHit":0,"items":[{"dan":false,"matchKey":"20111218-019","value":3},{"dan":false,"matchKey":"20111218-021","value":2}]}
    private String schemeValue;
	@Autowired
	private JclqSchemeService schemeService;

	@Autowired
	private JclqSchemeEntityManager schemeEntityManager;

	@Autowired
	private JclqMatchEntityManager matchEntityManager;

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
	protected SchemeService<JclqScheme, JclqSchemeDTO> getSchemeService() {
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
	protected List<JclqMatchItem> buildJclqMatchItemList(Map<String, Object> value) throws WebDataException{
		try{
			String[] items = JsonUtil.getStringArray4Json(String.valueOf(value.get("items")));
			final List<JclqMatchItem> correctList = Lists.newArrayList();
			Map<String, Object> map;
			PlayType playType = buildPlayType();
			Set<String> matchKeyList = Sets.newHashSet();
			String matchKey = null;
			for (String itemStr : items) {
				map = JsonUtil.getMap4Json(itemStr);
				JclqMatchItem item = new JclqMatchItem();
				matchKey = String.valueOf(map.get("matchKey"));
				item.setMatchKey(matchKey);
				matchKeyList.add(matchKey);
				item.setDan(Boolean.valueOf(String.valueOf(map.get("dan"))));
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
		case DXF:
			for (String str : list) {
				flag+=0x1 <<ItemDXF.valueOfValue(str).ordinal();
			}
			return flag;
		case RFSF:
			for (String str : list) {
				flag+=0x1 << ItemRFSF.valueOfValue(str).ordinal();
			}
			return flag;
		case SF:
			for (String str : list) {
				flag+=0x1 << ItemSF.valueOfValue(str).ordinal();
			}
			return flag;
		case SFC:
			for (String str : list) {
				ItemSFC isfc =   ItemSFC.valueOfValue(str);
				if(null==isfc)isfc =   ItemSFC.valueOfName(str);
				flag+=0x1 << isfc.ordinal();
			}
			return flag;
		default:
			throw new RuntimeException("5-玩法不正确.");
		}
	}
	
	@Override
	protected JclqSchemeDTO buildSchemeDTO() throws WebDataException {
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
		final List<JclqMatchItem> correctList = buildJclqMatchItemList(map);
			
			
		
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

		JclqSchemeDTO dto = super.buildSchemeDTO();
		PlayType playType = buildPlayType();
		dto.setPlayType(playType);
		
		SchemeType schemeType = buildSchemeType();
		dto.setSchemeType(schemeType);
		
		List<PassType> passTypes = buildPassType();
		dto.setPassTypes(passTypes);

	
		
		final StringBuilder ticketContentBuilder = new StringBuilder();
		TicketSplitCallback callback = new TicketSplitCallback() {

			@Override
			public void handle(List<JclqMatchItem> matchItemList, PassType passType, int multiple) {
				long matchFlag = JclqUtil.chg2flag(matchItemList, correctList);
				TicketItem ticketItem = new TicketItem(matchFlag, passType, multiple);
				ticketContentBuilder.append(ticketItem.toString()).append(TicketItem.ITEM_AND);
			}
		};
		switch (schemeType) {
		case SINGLE:
			dto.setPassMode(PassMode.SINGLE);

			JclqUtil.singleSplit(callback, getMultiple(), correctList);
			break;
		case SIMPLE_PASS:
			dto.setPassMode(PassMode.PASS);

			JclqUtil.splitWithMultiple(callback, getMultiple(), passTypes.get(0),
					correctList);
			break;
		case MULTIPLE_PASS:
			dto.setPassMode(PassMode.PASS);

			final List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();
			final List<JclqMatchItem> undanList = new ArrayList<JclqMatchItem>();
			Integer maxMatchSize = null;
			if(playType.equals(PlayType.MIX)){
				for (JclqMatchItem jclqMatchItem : correctList) {
					if(null==maxMatchSize||maxMatchSize>jclqMatchItem.getPlayType().getMaxMatchSize()){
						maxMatchSize = jclqMatchItem.getPlayType().getMaxMatchSize();
					}
					if (jclqMatchItem.isDan()){
						danList.add(jclqMatchItem);
					}else{
						undanList.add(jclqMatchItem);
					}
				}
			}else{
				maxMatchSize = playType.getMaxMatchSize();
			}
			if (danList.size() == 0) {
				JclqUtil.undanMultiplePassSplit(callback, getMultiple(), maxMatchSize,
						passTypes, correctList);
			} else {
				if (danMinHit == null || danMinHit <= 0)
					danMinHit = danList.size();
				if (danMaxHit == null || danMaxHit <= 0)
					danMaxHit = danList.size();
				JclqUtil.danMultiplePassSplit(callback, getMultiple(),passTypes, danList,
						undanList, danMinHit, danMaxHit);
			}
			break;
		}
		/////////cyy，检查场次是否截至
		List<String> matchKeyList = new ArrayList<String>();
		Map<String,JclqMatchItem> matchItemMap = Maps.newHashMap();
		for (JclqMatchItem item : correctList) {
			matchKeyList.add(item.getMatchKey());
			matchItemMap.put(item.getMatchKey(), item);
		}
		Date endTime = null;
		if(null!=dto.getPlayType()&&PlayType.MIX.equals(dto.getPlayType())){
	    	dto.setPassMode(PassMode.MIX_PASS);
	    	/////////cyy，检查场次是否截至
	    	
			List<JclqMatch> matchList = matchEntityManager.findMatchs(matchKeyList);
			for (JclqMatch jclqMatch : matchList) {
				JclqMatchItem item = matchItemMap.get(jclqMatch.getMatchKey());
				if (!jclqMatch.isOpen(item.getPlayType(), PassMode.PASS))
					throw new WebDataException("场次{"+jclqMatch.getMatchKey()+"}已经停止销售.");
				try {
					if (jclqMatch.isStop())
						throw new WebDataException("5-场次{"+jclqMatch.getMatchKey()+"}已经停止销售.");
					if (endTime == null || endTime.after(jclqMatch.getTicketOfficialEndTime()))
						endTime = jclqMatch.getTicketOfficialEndTime();
				} catch (DataException e) {
					logger.warn(e.getMessage());
					throw new WebDataException("5-场次{"+jclqMatch.getMatchKey()+"}获取出票时间出错.");
				}
			}
		}else{
				/////////cyy，检查场次是否截至
				List<JclqMatch> matchList = matchEntityManager.findMatchs(matchKeyList);
				if(matchList.isEmpty())throw new WebDataException("5-场次{"+matchKeyList+"}已经停止销售.");
				for (JclqMatch jclqMatch : matchList) {
					if (!jclqMatch.isOpen(dto.getPlayType(), dto.getPassMode()))
						throw new WebDataException("5-场次{"+jclqMatch.getMatchKey()+"}已经停止销售.");
					try {
						if (jclqMatch.isStop())
							throw new WebDataException("5-场次{"+jclqMatch.getMatchKey()+"}已经停止销售.");
						if (endTime == null || endTime.after(jclqMatch.getTicketOfficialEndTime()))
							endTime = jclqMatch.getTicketOfficialEndTime();
					} catch (DataException e) {
						logger.warn(e.getMessage());
						throw new WebDataException("5-场次{"+jclqMatch.getMatchKey()+"}获取出票时间出错.");
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
		final List<JclqMatchItem> correctList = buildJclqMatchItemList(map);
		if (correctList == null || correctList.isEmpty())
			throw new WebDataException("5-投注内容不能为空.");

		switch (buildSchemeType()) {
		case SINGLE:
			if (correctList.size() > JclqConstant.SINGLE_MAX_MATCH_SIZE)
				throw new WebDataException("5-单关模式不能选择超过" + JclqConstant.SINGLE_MAX_MATCH_SIZE + "场.");

			if (buildPassType() == null || buildPassType().size() != 1
					|| buildPassType().get(0) != PassType.P1)
				throw new WebDataException("5-过关方式不正确.");
			break;
		case SIMPLE_PASS:
			Integer maxMatchSize = null;
			if(buildPlayType().equals(PlayType.MIX)){
				for (JclqMatchItem jclqMatchItem : correctList) {
					if(null==maxMatchSize||maxMatchSize>jclqMatchItem.getPlayType().getMaxMatchSize())
						maxMatchSize = jclqMatchItem.getPlayType().getMaxMatchSize();
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
					for (JclqMatchItem jclqMatchItem : correctList) {
						if(null==maxMatchSize||maxMatchSize>jclqMatchItem.getPlayType().getMaxMatchSize())
							maxMatchSize = jclqMatchItem.getPlayType().getMaxMatchSize();
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
		final List<JclqMatchItem> correctList = buildJclqMatchItemList(map);
		
		PassType minPassType = buildPassType().get(0);// 最小的过关方式

		final List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();
		final List<JclqMatchItem> unDanList = new ArrayList<JclqMatchItem>();
		for (JclqMatchItem item : correctList) {
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

		JclqCompoundContent content = new JclqCompoundContent();
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

	public void setSchemeService(JclqSchemeService schemeService) {
		this.schemeService = schemeService;
	}

	@Override
	protected Lottery getLottery() {

		return Lottery.JCLQ;
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
		List<JclqMatch> matchList = jclqMatchEntityManager.findMatchsOfTicketUnEnd();
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
			PlayType[] playTypeArr = new PlayType[]{PlayType.RFSF,PlayType.SF};
			Map<String, Map<String,Map<String, RateItem>>> allRate = Maps.newHashMap();
			for (PlayType mixPlayType : playTypeArr) {
				Map<String, RateItem> rateMap= null;
				Map<String,Map<String, RateItem>> oldRateMap= null;
				String matchKey= null;
				Map<String, Map<String, RateItem>> rate = jclqLocalCache.getRateData(mixPlayType, PassMode.PASS);
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
			for (JclqMatch jclqMatch : matchList) {
				zcMatchDTO = new JcMixMatchDTO();
				zcMatchDTO.setCancel(jclqMatch.isCancel());
				zcMatchDTO.setEnded(jclqMatch.isEnded());
				zcMatchDTO.setGameColor(jclqMatch.getGameColor());
				zcMatchDTO.setGameName(MatchNameUtil.getShotName(jclqMatch.getGameName()));
				if(PlayType.DXF.equals(playType)){
					if(null==jclqMatch.getSingleTotalScore()){
						zcMatchDTO.setHandicap(Float.valueOf(0));
					}else{
						zcMatchDTO.setHandicap(Float.valueOf(jclqMatch.getSingleTotalScore()));
					}
				}else{
					if(null==jclqMatch.getSingleTotalScore()){
						zcMatchDTO.setHandicap(Float.valueOf(0));
					}else{
						zcMatchDTO.setHandicap(Float.valueOf(jclqMatch.getSingleHandicap()));
					}
				}
				zcMatchDTO.setHomeTeamName(jclqMatch.getHomeTeamName());
				zcMatchDTO.setMatchKey(jclqMatch.getMatchKey());
				zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(jclqMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
				try {
					zcMatchDTO.setEndSaleTime(com.cai310.utils.DateUtil.dateToStr(jclqMatch.getWebOfficialEndTime(), "yyyy-MM-dd HH:mm:ss"));
				} catch (DataException e1) {
					logger.warn("{"+jclqMatch.getMatchKey()+"}获取比赛结束时间错误");
				}
				Map<String, Boolean> open = Maps.newHashMap();
				for (PlayType mixPlayType : playTypeArr) {
					open.put(mixPlayType.ordinal()+"", true);
					if(!jclqMatch.isOpen(mixPlayType, PassMode.PASS)){
						open.put(mixPlayType.ordinal()+"", false);
					}
					try {
						if(new Date().after(jclqMatch.getWebOfficialEndTime())){
							open.put(mixPlayType.ordinal()+"", false);
						}
					} catch (DataException e) {
						logger.warn("{"+jclqMatch.getMatchKey()+"}获取比赛结束时间错误");
						open.put(mixPlayType.ordinal()+"", false);
						continue;
					}
				}
				zcMatchDTO.setMixOpen(open);
				if(null!=allRate.get(jclqMatch.getMatchKey())){
					zcMatchDTO.setMixSp(allRate.get(jclqMatch.getMatchKey()));
				}
				list.add(zcMatchDTO);
			}
			return list;
		}else{
		ZcMatchDTO zcMatchDTO = null;
		Map<String, Map<String, RateItem>> rate = jclqLocalCache.getRateData(playType, passMode);
		for (JclqMatch jclqMatch : matchList) {
			zcMatchDTO = new ZcMatchDTO();
			zcMatchDTO.setCancel(jclqMatch.isCancel());
			zcMatchDTO.setEnded(jclqMatch.isEnded());
			zcMatchDTO.setGameColor(jclqMatch.getGameColor());
			zcMatchDTO.setGameName(MatchNameUtil.getShotName(jclqMatch.getGameName()));
			zcMatchDTO.setGuestTeamName(jclqMatch.getGuestTeamName());
			if(PlayType.DXF.equals(playType)){
				if(null==jclqMatch.getSingleTotalScore()){
					zcMatchDTO.setHandicap(Float.valueOf(0));
				}else{
					zcMatchDTO.setHandicap(Float.valueOf(jclqMatch.getSingleTotalScore()));
				}
			}else{
				if(null==jclqMatch.getSingleTotalScore()){
					zcMatchDTO.setHandicap(Float.valueOf(0));
				}else{
					zcMatchDTO.setHandicap(Float.valueOf(jclqMatch.getSingleHandicap()));
				}
			}
			zcMatchDTO.setHomeTeamName(jclqMatch.getHomeTeamName());
			zcMatchDTO.setMatchKey(jclqMatch.getMatchKey());
			zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(jclqMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			try {
				zcMatchDTO.setEndSaleTime(com.cai310.utils.DateUtil.dateToStr(jclqMatch.getWebOfficialEndTime(), "yyyy-MM-dd HH:mm:ss"));
			} catch (DataException e1) {
				logger.warn("{"+jclqMatch.getMatchKey()+"}获取比赛结束时间错误");
			}
			
			zcMatchDTO.setOpen(true);
			if(!jclqMatch.isOpen(playType, passMode)){
				zcMatchDTO.setOpen(false);
			}
			try {
				if(new Date().after(jclqMatch.getWebOfficialEndTime())){
					zcMatchDTO.setOpen(false);
				}
			} catch (DataException e) {
				logger.warn("{"+jclqMatch.getMatchKey()+"}获取比赛结束时间错误");
				zcMatchDTO.setOpen(false);
				continue;
			}
			if(null!=rate.get(jclqMatch.getMatchKey())){
				zcMatchDTO.setSp(rate.get(jclqMatch.getMatchKey()));
			}
			list.add(zcMatchDTO);
		}
		return list;
		}
	}

	@Override
	protected SchemeInfoDTO getSchemeMatchDTO(Scheme scheme) throws WebDataException {
		SchemeInfoDTO schemeInfoDTO = new SchemeInfoDTO();
		JclqScheme jclqScheme = (JclqScheme) scheme;
		JclqCompoundContent compoundContent = jclqScheme.getCompoundContent();
        
		Map<String, JclqMatch> matchMap = Maps.newLinkedHashMap();
		List<String> matchKeys = Lists.newArrayList();
		for (JclqMatchItem matchItem : compoundContent.getItems()) {
			matchKeys.add(matchItem.getMatchKey());
		}
		List<JclqMatch> matchs = matchEntityManager.findMatchs(matchKeys);
		for (JclqMatch jclqMatch : matchs) {
			matchMap.put(jclqMatch.getMatchKey(), jclqMatch);
		}
		StringBuffer sb = new StringBuffer();
		int danSize = 0;
		List<SchemeMatchDTO> list = Lists.newArrayList();
		PlayType playType = jclqScheme.getPlayType();
		for (JclqMatchItem matchItem : compoundContent.getItems()) {
			if(PlayType.MIX.equals(jclqScheme.getPlayType())){
				playType = matchItem.getPlayType();
			}
			JclqMatch jclqMatch = matchMap.get(matchItem.getMatchKey());
			SchemeMatchDTO schemeMatchDTO = new SchemeMatchDTO();
			schemeMatchDTO.setMatchKey(jclqMatch.getMatchKeyText());
			schemeMatchDTO.setDan(Boolean.FALSE);
			schemeMatchDTO.setCancel(jclqMatch.isCancel());
			schemeMatchDTO.setEnded(jclqMatch.isEnded());
			schemeMatchDTO.setGameColor(jclqMatch.getGameColor());
			schemeMatchDTO.setGameName(MatchNameUtil.getShotName(jclqMatch.getGameName()));
			schemeMatchDTO.setGuestTeamName(jclqMatch.getGuestTeamName());
			schemeMatchDTO.setHomeTeamName(jclqMatch.getHomeTeamName());
			schemeMatchDTO.setHomeScore(jclqMatch.getHomeScore());
			schemeMatchDTO.setGuestScore(jclqMatch.getGuestScore());
			schemeMatchDTO.setPlayType(playType.ordinal());
			schemeMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(jclqMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			if (matchItem.isDan()) {
				schemeMatchDTO.setDan(Boolean.TRUE);
				danSize++;
			}
			if(PlayType.DXF.equals(playType)){
				if(null==jclqMatch.getSingleTotalScore()){
					schemeMatchDTO.setHandicap(Float.valueOf(0));
				}else{
					schemeMatchDTO.setHandicap(Float.valueOf(jclqMatch.getSingleTotalScore()));
				}
			}else{
				if(null==jclqMatch.getSingleTotalScore()){
					schemeMatchDTO.setHandicap(Float.valueOf(0));
				}else{
					schemeMatchDTO.setHandicap(Float.valueOf(jclqMatch.getSingleHandicap()));
				}
			}
			sb.setLength(0);
			for (Item item : playType.getAllItems()) {
				if ((matchItem.getValue() & (0x1 << item.ordinal())) > 0) {
					if(null==jclqMatch){
						sb.append(item.getText()).append(",");
					}else{
						if(null!=jclqMatch.getResult(playType)&&item.equals(jclqMatch.getResult(playType))){
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
			if(null!=jclqMatch.getResult(playType)){
				schemeMatchDTO.setResult(jclqMatch.getResult(playType).getText());
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
		schemeInfoDTO.setPassTypeText(jclqScheme.getPassTypeText());
		return schemeInfoDTO;
	
	}

	@Override
	protected List buildMatchResultList() throws WebDataException {
		List list = Lists.newArrayList();
		List<JclqMatch> matchList = jclqMatchEntityManager.findMatchs(Integer.valueOf(this.matchDate));
		ZcMatchResultDTO zcMatchDTO=null;
		for (JclqMatch jclqMatch : matchList) {
			zcMatchDTO = new ZcMatchResultDTO();
			zcMatchDTO.setCancel(jclqMatch.isCancel());
			zcMatchDTO.setEnded(jclqMatch.isEnded());
			zcMatchDTO.setGameColor(jclqMatch.getGameColor());
			zcMatchDTO.setGameName(MatchNameUtil.getShotName(jclqMatch.getGameName()));
			zcMatchDTO.setGuestTeamName(jclqMatch.getGuestTeamName());
			if(null==jclqMatch.getSingleHandicap()){
				zcMatchDTO.setHandicap(Float.valueOf(0));
			}else{
				zcMatchDTO.setHandicap(Float.valueOf(jclqMatch.getSingleHandicap()));
			}
			if(null==jclqMatch.getSingleTotalScore()){
				zcMatchDTO.setTotalScore(Float.valueOf(0));
			}else{
				zcMatchDTO.setTotalScore(Float.valueOf(jclqMatch.getSingleTotalScore()));
			}
			zcMatchDTO.setHomeTeamName(jclqMatch.getHomeTeamName());
			zcMatchDTO.setMatchKey(jclqMatch.getMatchKey());
			zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(jclqMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			zcMatchDTO.setHomeScore(jclqMatch.getHomeScore());
			zcMatchDTO.setGuestScore(jclqMatch.getGuestScore());
			StringBuffer resultSb = new StringBuffer();
			StringBuffer spSb = new StringBuffer();
			if(jclqMatch.isCancel()||jclqMatch.isEnded()){
				for (PlayType playType : PlayType.values()) {
					if(playType.equals(PlayType.MIX))continue;
					Item item = jclqMatch.getResult(playType);
					resultSb.append(null==item?item:item.getText()).append("|");
					Double sp = jclqMatch.getResultSp(playType);
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
