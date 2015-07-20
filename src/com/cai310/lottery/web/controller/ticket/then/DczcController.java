package com.cai310.lottery.web.controller.ticket.then;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.persistence.Transient;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Constant;
import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.MatchNameUtil;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.dto.lottery.SchemeInfoDTO;
import com.cai310.lottery.dto.lottery.SchemeMatchDTO;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.lottery.ZcMatchDTO;
import com.cai310.lottery.dto.lottery.ZcMatchResultDTO;
import com.cai310.lottery.dto.lottery.dczc.DczcSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.dczc.DczcSpInfo;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.fetch.ValueItem;
import com.cai310.lottery.fetch.dczc.DczcAbstractFetchParser;
import com.cai310.lottery.fetch.dczc.DczcContextHolder;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.dczc.ItemBF;
import com.cai310.lottery.support.dczc.ItemSPF;
import com.cai310.lottery.support.dczc.ItemZJQS;
import com.cai310.lottery.support.dczc.ItemSXDS;
import com.cai310.lottery.support.dczc.ItemBQQSPF;
import com.cai310.lottery.support.dczc.DczcCompoundContent;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.PassMode;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.support.dczc.DczcCompoundContent;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.utils.DateUtil;
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

public class DczcController extends
                      TicketBaseController<DczcScheme, DczcSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;
 
	/** 胜平负==0 */
	/** 进球数==1 */
	/** 比分==2 */
	/** 半全场==3 */
	private Integer playType;
    //com.cai310.lottery.support.dczc.PassType支持多选过关
	private String passType;
	/** 普通过关 ==0*/
	/** 多选过关 ==1*/
	private Integer passMode;
	///{"danMaxHit":0,"danMinHit":0,"items":[{"dan":false,"lineId":"019","value":3},{"dan":false,"lineId":"021","value":1}]}
    private String schemeValue;
	@Autowired
	private DczcSchemeService schemeService;

	@Autowired
	private DczcSchemeEntityManager schemeEntityManager;

	@Autowired
	private DczcMatchEntityManager matchEntityManager;

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
	@Autowired
	@Qualifier("dczcMatchCache")
	private Cache matchCache;
	@SuppressWarnings("unchecked")
	protected List<DczcMatch> findMatchsOfCacheable(Long periodId) {
		Element el = matchCache.get(periodId);
		if (el == null) {
			List<DczcMatch> matchs = matchEntityManager.findMatchs(periodId);
			if (matchs != null)
				matchCache.put(new Element(periodId, matchs));

			return matchs;
		} else {
			return (List<DczcMatch>) el.getValue();
		}
	}
	@Override
    protected List buildMatchList() throws WebDataException{
		List list = Lists.newArrayList();
    	Period period = periodManager.loadPeriod(this.getLottery(), this.periodNumber);
    	if (period == null)
			throw new WebDataException("期数据不存在.");
    	
    	List<DczcMatch> matchs = findMatchsOfCacheable(period.getId());
		if (matchs == null || matchs.isEmpty())
			throw new WebDataException("对阵不存在.");
		Map<Integer, DczcMatch> matchMap = new HashMap<Integer, DczcMatch>();
		for (DczcMatch m : matchs) {
			matchMap.put(m.getLineId(), m);
		}
		Map<Integer, DczcSpInfo> infoMap = new HashMap<Integer, DczcSpInfo>();
		{
			FetchDataBean fdb = DczcContextHolder.getRateData(period.getPeriodNumber(), buildPlayType());
			if (fdb != null && fdb.getDataBlock() != null && fdb.getDataBlock().getData() != null) {
				SortedMap<String, Map<String, ValueItem>> data = fdb.getDataBlock().getData();
				for (Entry<String, Map<String, ValueItem>> entry : data.entrySet()) {
					String lineKey = entry.getKey();
					Integer lineId = DczcAbstractFetchParser.getLineId(lineKey);
					Map<String, ValueItem> itemMap = entry.getValue();
					Map<String, Double> spMap = Maps.newHashMap();
					for (Entry<String, ValueItem> itemEntry : itemMap.entrySet()) {
						spMap.put(itemEntry.getKey(), itemEntry.getValue().getValue());
					}
					DczcSpInfo dczcSpInfo = new DczcSpInfo();
					dczcSpInfo.setPeriodId(period.getId());
					dczcSpInfo.setPeriodNumber(period.getPeriodNumber());
					dczcSpInfo.setPlayType(buildPlayType());
					dczcSpInfo.setLineId(lineId);
					dczcSpInfo.setContent(spMap);
					infoMap.put(dczcSpInfo.getLineId(), dczcSpInfo);
				}
			} else {
				List<DczcSpInfo> dczcSpInfoList = matchEntityManager.getDczcSpInfo(buildPlayType(), period.getPeriodNumber());
				for (DczcSpInfo sp : dczcSpInfoList) {
					infoMap.put(sp.getLineId(), sp);
				}
			}
		}
		DczcMatch dczcMatch = null;
		ZcMatchDTO zcMatchDTO = null;
		for (Integer lineId : matchMap.keySet()) {
			zcMatchDTO = new ZcMatchDTO();
			dczcMatch = matchMap.get(lineId);
			zcMatchDTO.setCancel(dczcMatch.isCancel());
			zcMatchDTO.setEnded(dczcMatch.isEnded());
			zcMatchDTO.setGameColor(dczcMatch.getGameColor());
			zcMatchDTO.setGameName(MatchNameUtil.getShotName(dczcMatch.getGameName()));
			zcMatchDTO.setGuestTeamName(dczcMatch.getGuestTeamName());
			zcMatchDTO.setHandicap(Float.valueOf(dczcMatch.getHandicap()));
			zcMatchDTO.setHomeTeamName(dczcMatch.getHomeTeamName());
			zcMatchDTO.setMatchKey(""+(dczcMatch.getLineId()+1));
			zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(dczcMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			zcMatchDTO.setEndSaleTime(com.cai310.utils.DateUtil.dateToStr(dczcMatch.getEndSaleTime(SalesMode.COMPOUND), "yyyy-MM-dd HH:mm:ss"));
			zcMatchDTO.setOpen(true);
			if(new Date().after(dczcMatch.getEndSaleTime(SalesMode.COMPOUND))){
				zcMatchDTO.setOpen(false);
			}
			DczcSpInfo dczcSpInfo = infoMap.get(lineId);
			if(null!=dczcSpInfo&&null!=dczcSpInfo.getContent()){
				Map<String, RateItem> spMap = new HashMap<String, RateItem>();
				Map<String, Double> matchSpMap = dczcSpInfo.getContent();
				for (String key: matchSpMap.keySet()) {
					RateItem rateItem = new RateItem();
					rateItem.setChg(0);
					rateItem.setKey(key);
					rateItem.setValue(Double.valueOf(""+matchSpMap.get(key)));
					spMap.put(key, rateItem);
				}
				zcMatchDTO.setSp(spMap);
			}
			list.add(zcMatchDTO);
		}
		return list;
    }
	@Override
	protected SchemeService<DczcScheme, DczcSchemeDTO> getSchemeService() {
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
			 if(null!=map.get("danMinHit")){
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
			 if(null!=map.get("danMaxHit")){
				 danMaxHit = Integer.valueOf(String.valueOf(map.get("danMaxHit")));
			 }
	    	 return danMaxHit;
		 }catch(Exception e){
				logger.warn("最大命中解析错误."+e.getMessage());
				throw new WebDataException("5-最大命中解析错误.");
		 }
    }
	@SuppressWarnings("unchecked")
	protected List<DczcMatchItem> buildDczcMatchItemList(Map<String, Object> value) throws WebDataException{
		try{
			String[] items = JsonUtil.getStringArray4Json(String.valueOf(value.get("items")));
			final List<DczcMatchItem> correctList = Lists.newArrayList();
			Map<String, Object> map;
			PlayType playType = buildPlayType();
			Integer lineId = null;
			for (String itemStr : items) {
				map = JsonUtil.getMap4Json(itemStr);
				DczcMatchItem item = new DczcMatchItem();
				lineId = (Integer.valueOf(String.valueOf(map.get("lineId")))-1);///因为单场这边是重0开始。客户发起是1开始
				item.setLineId(lineId);
				if(null!=map.get("dan")){
					item.setDan(Boolean.valueOf(String.valueOf(map.get("dan"))));
				}
				item.setValue(getBetValue(playType,String.valueOf(map.get("value"))));
				
				correctList.add(item);
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
		}else{
			list.add(value);
		}
		switch (playType) {
		case SPF:
			for (String str : list) {
				flag+=0x1 <<ItemSPF.valueOfValue(str).ordinal();
			}
			return flag;
		case ZJQS:
			for (String str : list) {
				flag+=0x1 << ItemZJQS.valueOfValue(str).ordinal();
			}
			return flag;
		case BF:
			for (String str : list) {
				flag+=0x1 << ItemBF.valueOfValue(str).ordinal();
			}
			return flag;
		case BQQSPF:
			for (String str : list) {
				flag+=0x1 << ItemBQQSPF.valueOfValue(str).ordinal();
			}
			return flag;
		case SXDS:
			for (String str : list) {
				flag+=0x1 << ItemSXDS.valueOfValue(str).ordinal();
			}
			return flag;
		default:
			throw new RuntimeException("玩法不正确.");
		}
	}
	
	public PassMode buildPassMode() throws WebDataException{
	   try{
		    return PassMode.values()[passMode];
	    }catch(Exception e){
			logger.warn("投注方式解析错误."+e.getMessage());
			throw new WebDataException("5-投注方式解析错误.");
		}
	}
	protected void checkCreateFormOfCOMPOUND() throws WebDataException {
		checkCreateForm();
		Map<String, Object> map;
		try{
			map = JsonUtil.getMap4Json(schemeValue);
		}catch(Exception e){
			logger.warn("投注内容分析错误."+e.getMessage());
			throw new WebDataException("5-投注内容分析错误.");
		}
		Integer danMinHit = null==buildDanMinHit(map)?0:buildDanMinHit(map);
		Integer danMaxHit =  null==buildDanMaxHit(map)?0:buildDanMaxHit(map);
		final List<DczcMatchItem> correctList = buildDczcMatchItemList(map);
		
		
		if (correctList == null || correctList.isEmpty())
			throw new WebDataException("投注内容不能为空.");
		PlayType playType = this.buildPlayType();
		List<PassType> passTypeList = this.buildPassType();
		switch (buildPassMode()) {
		case NORMAL:
			if (correctList.size() > playType.getMaxMatchSize())
				throw new WebDataException("[" + playType.getText() + "]普通过关选择不能超过" + playType.getMaxMatchSize() + "场.");

			if (passTypeList.size() > 1)
				throw new WebDataException("普通过关模式只能选择一个过关方式.");

			PassType passType = passTypeList.get(0);
			if (passType != PassType.P1 && passType.getMatchCount() != correctList.size())
				throw new WebDataException("选择的场次数目与过关方式不匹配.");
			break;
		case MULTIPLE:
			for (PassType type : passTypeList) {
				if (!type.isForMultipleMode())
					throw new WebDataException("多选过关模式不支持[" + type.getText() + "]过关.");
				if (type.getMatchCount() > playType.getMaxMatchSize())
					throw new WebDataException("过关方式不正确.");
			}
			break;
		default:
			throw new WebDataException("过关模式不合法.");
		}
		
	}
	
	
	@Override
	protected DczcSchemeDTO buildSchemeDTO() throws WebDataException {
		checkCreateForm();

		switch (buildSalesMode()) {
		case COMPOUND:
			checkCreateFormOfCOMPOUND();
			break;
		case SINGLE:
			throw new WebDataException("投注方式不合法.");
		default:
			throw new WebDataException("投注方式不合法.");
		}

		DczcSchemeDTO dto = super.buildSchemeDTO();
		/////////cyy，检查场次是否截至
		Map<String, Object> map;
		try{
			map = JsonUtil.getMap4Json(schemeValue);
		}catch(Exception e){
			logger.warn("投注内容分析错误."+e.getMessage());
			throw new WebDataException("5-投注内容分析错误.");
		}
		final List<DczcMatchItem> correctList = buildDczcMatchItemList(map);
		List<Integer> lineIdList = new ArrayList<Integer>();
		for (DczcMatchItem item : correctList) {
			lineIdList.add(item.getLineId());
		}
		Date endTime = null;
		Period period = periodManager.loadPeriod(this.getLottery(), this.periodNumber);
		List<DczcMatch> matchList = matchEntityManager.findMatchs(period.getId(), lineIdList);
		if(matchList.isEmpty())throw new WebDataException("5-场次{"+lineIdList+"}已经停止销售.");
		for (DczcMatch dczcMatch : matchList) {
			try {
				if (dczcMatch.checkSaleEnd(DczcConstant.COMPOUND_AHEAD_END_NORMAL_PASS_MODE))
					throw new WebDataException("5-场次{"+(dczcMatch.getLineId()+1)+"}已经停止销售.");
			} catch (WebDataException e) {
				logger.warn(e.getMessage());
				throw new WebDataException("5-场次{"+(dczcMatch.getLineId()+1)+"}已经停止销售..");
			}
		}
		dto.setOfficialEndTime(endTime);		
		dto.setPlayType(buildPlayType());
		dto.setPassMode(buildPassMode());
		dto.setPassTypes(buildPassType());
		return dto;
	}

	protected void checkCreateForm() throws WebDataException {
		if (buildPlayType() == null)
			throw new WebDataException("5-玩法类型不能为空.");

		if (buildPassMode() == null)
			throw new WebDataException("5-过关玩法不能为空.");
		if (buildPassType() == null || buildPassType().isEmpty())
			throw new WebDataException("5-过关方式不能为空.");
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
		Integer danMinHit = null==buildDanMinHit(map)?0:buildDanMinHit(map);
		Integer danMaxHit =  null==buildDanMaxHit(map)?0:buildDanMaxHit(map);
		final List<DczcMatchItem> correctList = buildDczcMatchItemList(map);
		PlayType playType = this.buildPlayType();
		List<PassType> passTypeList = this.buildPassType();
		
		Collections.sort(correctList);
		PassType minPassType = passTypeList.get(0);// 最小的过关方式

		final List<DczcMatchItem> danList = new ArrayList<DczcMatchItem>();
		final List<DczcMatchItem> unDanList = new ArrayList<DczcMatchItem>();
		for (DczcMatchItem item : correctList) {
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
		for (PassType passType : passTypeList) {
			for (final int needs : passType.getPassMatchs()) {
				units += UnitsCountUtils.countUnits(needs, danList, danMinHit, danMaxHit, unDanList);
				if (units > Constant.MAX_UNITS)
					throw new WebDataException("5-复式方案单倍注数不能大于" + Constant.MAX_UNITS + "注.");
			}
		}

		DczcCompoundContent content = new DczcCompoundContent();
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
	public String getSchemeValue() {
		return schemeValue;
	}

	public void setSchemeValue(String schemeValue) {
		this.schemeValue = schemeValue;
	}

	public void setSchemeService(DczcSchemeService schemeService) {
		this.schemeService = schemeService;
	}
	public Integer getPassMode() {
		return passMode;
	}

	public void setPassMode(Integer passMode) {
		this.passMode = passMode;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	@Override
	protected Lottery getLottery() {

		return Lottery.DCZC;
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
			this.passMode = Integer.valueOf(reqParamVisitor.getType());
		}
		if(null!=reqParamVisitor.getPassType()){
			this.passType = String.valueOf(reqParamVisitor.getPassType());
		}
		if(null!=reqParamVisitor.getMode()){
			this.mode = Integer.valueOf(reqParamVisitor.getMode());
		}
		if(null!=reqParamVisitor.getPeriodNumber()){
			this.periodNumber =reqParamVisitor.getPeriodNumber();
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
	protected SchemeInfoDTO getSchemeMatchDTO(Scheme scheme)
			throws WebDataException {
		SchemeInfoDTO schemeInfoDTO = new SchemeInfoDTO();
		DczcScheme dczcScheme = (DczcScheme) scheme;
		DczcCompoundContent compoundContent = dczcScheme.getCompoundContent();
        
		Map<Integer, DczcMatch> matchMap = Maps.newLinkedHashMap();
		List<Integer> matchKeys = Lists.newArrayList();
		for (DczcMatchItem matchItem : compoundContent.getItems()) {
			matchKeys.add(matchItem.getLineId());
		}
		List<DczcMatch> matchs = matchEntityManager.findMatchs(scheme.getPeriodId(), matchKeys);
		for (DczcMatch dczcMatch : matchs) {
			matchMap.put(dczcMatch.getLineId(), dczcMatch);
		}
		StringBuffer sb = new StringBuffer();
		int danSize = 0;
		List<SchemeMatchDTO> list = Lists.newArrayList();
		for (DczcMatchItem matchItem : compoundContent.getItems()) {
			DczcMatch dczcMatch = matchMap.get(matchItem.getLineId());
			SchemeMatchDTO schemeMatchDTO = new SchemeMatchDTO();
			schemeMatchDTO.setMatchKey(""+(matchItem.getLineId()+1));
			schemeMatchDTO.setDan(Boolean.FALSE);
			schemeMatchDTO.setCancel(dczcMatch.isCancel());
			schemeMatchDTO.setEnded(dczcMatch.isEnded());
			schemeMatchDTO.setGameColor(dczcMatch.getGameColor());
			
			schemeMatchDTO.setGameName(MatchNameUtil.getShotName(dczcMatch.getGameName()));
			schemeMatchDTO.setGuestTeamName(dczcMatch.getGuestTeamName());
			schemeMatchDTO.setHomeTeamName(dczcMatch.getHomeTeamName());
			schemeMatchDTO.setHomeHalfScore(dczcMatch.getHalfHomeScore());
			schemeMatchDTO.setHomeScore(dczcMatch.getFullHomeScore());
			schemeMatchDTO.setGuestHalfScore(dczcMatch.getHalfGuestScore());
			schemeMatchDTO.setGuestScore(dczcMatch.getFullGuestScore());
			
			
			
			schemeMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(dczcMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			
			if (matchItem.isDan()) {
				schemeMatchDTO.setDan(Boolean.TRUE);
				danSize++;
			}
			if(null==dczcMatch.getHandicap()){
				schemeMatchDTO.setHandicap(Float.valueOf(0));
			}else{
				schemeMatchDTO.setHandicap(Float.valueOf(dczcMatch.getHandicap()));
			}
			sb.setLength(0);
			for (Item item : dczcScheme.getPlayType().getAllItems()) {
				if ((matchItem.getValue() & (0x1 << item.ordinal())) > 0) {
					if(null==dczcMatch){
						sb.append(item.getText()).append(",");
					}else{
						if(null!=dczcMatch.getResult(dczcScheme.getPlayType())&&item.equals(dczcMatch.getResult(dczcScheme.getPlayType()))){
							sb.append("<font color='#ff0000'>"+item.getText()+"</font>").append(",");
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
			if(null!=dczcMatch.getResult(dczcScheme.getPlayType())){
				schemeMatchDTO.setResult(dczcMatch.getResult(dczcScheme.getPlayType()).getText());
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
		schemeInfoDTO.setPassTypeText(dczcScheme.getPassTypeText());
		return schemeInfoDTO;
	}

	@Override
	protected List buildMatchResultList() throws WebDataException {
		List list = Lists.newArrayList();
		List<DczcMatch> matchList = matchEntityManager.findMatchs(this.periodNumber.trim());
		ZcMatchResultDTO zcMatchDTO=null;
		for (DczcMatch dczcMatch : matchList) {
			zcMatchDTO = new ZcMatchResultDTO();
			zcMatchDTO.setCancel(dczcMatch.isCancel());
			zcMatchDTO.setEnded(dczcMatch.isEnded());
			zcMatchDTO.setGameColor(dczcMatch.getGameColor());
			zcMatchDTO.setGameName(MatchNameUtil.getShotName(dczcMatch.getGameName()));
			zcMatchDTO.setGuestTeamName(dczcMatch.getGuestTeamName());
			if(null==dczcMatch.getHandicap()){
				zcMatchDTO.setHandicap(Float.valueOf(0));
			}else{
				zcMatchDTO.setHandicap(Float.valueOf(dczcMatch.getHandicap()));
			}
			zcMatchDTO.setHomeTeamName(dczcMatch.getHomeTeamName());
			zcMatchDTO.setMatchKey(""+(dczcMatch.getLineId()+1));
			zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(dczcMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			zcMatchDTO.setHomeHalfScore(dczcMatch.getHalfHomeScore());
			zcMatchDTO.setHomeScore(dczcMatch.getFullHomeScore());
			zcMatchDTO.setGuestHalfScore(dczcMatch.getHalfGuestScore());
			zcMatchDTO.setGuestScore(dczcMatch.getFullGuestScore());
			StringBuffer resultSb = new StringBuffer();
			StringBuffer spSb = new StringBuffer();
			if(dczcMatch.isCancel()||dczcMatch.isEnded()){
				for (PlayType playType : PlayType.values()) {
					Item item = dczcMatch.getResult(playType);
					resultSb.append(null==item?item:item.getText()).append("|");
					Double sp = dczcMatch.getResultSp(playType);
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
