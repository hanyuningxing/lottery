package com.cai310.lottery.web.controller.lottery.jczq;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.cache.JczqLocalCache;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.lottery.jczq.JczqSchemeDTO;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqSchemeTemp;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.jczq.JczqChasePlanDetailEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeService;
import com.cai310.lottery.service.lottery.jczq.impl.JczqSchemeTempEntityManagerImpl;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqCompoundContent;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqSimplePrizeWork;
import com.cai310.lottery.support.jczq.JczqSingleContent;
import com.cai310.lottery.support.jczq.JczqTicketCombination;
import com.cai310.lottery.support.jczq.JczqUploadType;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.PlayTypeWeb;
import com.cai310.lottery.support.jczq.PrizeForecast;
import com.cai310.lottery.support.jczq.TicketItem;
import com.cai310.lottery.support.jczq.TicketItemSingle;
import com.cai310.lottery.support.jczq.TicketSplitCallback;
import com.cai310.lottery.support.jczq.TicketSplitCallbackSingle;
import com.cai310.lottery.utils.DateUtil;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.SchemeBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ibm.icu.util.Calendar;

@Namespace("/" + JczqConstant.KEY)
@Action(value = "scheme")
public class JczqSchemeController extends
		SchemeBaseController<JczqScheme, JczqSchemeDTO, JczqSchemeCreateForm, JczqSchemeUploadForm, JczqSchemeTemp>{
	@Autowired
	protected PeriodEntityManager periodManager;
	
	@Autowired
	private JczqChasePlanDetailEntityManager jczqChasePlanDetailEntityManager;
	
	private static final long serialVersionUID = 5783479221989581469L;
 
	private PlayType playType;	

	private PlayTypeWeb playTypeWeb;

	private PassMode passMode;

	private JczqUploadType uploadType;

	@Autowired
	private JczqSchemeService schemeService;

	@Autowired
	private JczqSchemeEntityManager schemeEntityManager;
	
	@Autowired
	private JczqSchemeTempEntityManagerImpl jczqSchemeTempEntityManagerImpl;
	
	@Autowired
	private JczqMatchEntityManager matchEntityManager;

	@Autowired
	private JczqLocalCache localCache;

	private String currentDate;

	private static final int DAY_SIZE = 10;
	
	private List<String> spss = new ArrayList<String>();
	
	/** 场次归属的日期 */
	private String matchDate;
	
	/** 场次归属的日期 */
	private String matchKey;
	
 
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

	@Override
	protected JczqSchemeEntityManager getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected JczqSchemeTempEntityManagerImpl getSchemeTempEntityManager() {
		return jczqSchemeTempEntityManagerImpl;
	}	

	/**
	 * 获取竞九赛程
	 * @return
	 */
	public String matchNine(){
		this.playType = PlayType.SPF;
		this.passMode = PassMode.PASS;
		this.salesMode = SalesMode.SINGLE;
		this.preparePeriods(true);
		List<JczqMatch> matchs = matchEntityManager.findMatchsOfZc();
		Struts2Utils.setAttribute("matchs", matchs);
		Struts2Utils.setAttribute("itemArr", PlayType.SPF.getAllItems());
		Struts2Utils.setAttribute("currDate", new Date());
		
		Struts2Utils.setAttribute("rqspfRateData", localCache.getRateData(PlayType.RQSPF, passMode));
		Struts2Utils.setAttribute("spfRateData", localCache.getRateData(PlayType.SPF, passMode));
		
		super.getPageInfo();
		
		return "match-NINE";
	}
	
	public String test() {		
		XDetachedCriteria criteria = new XDetachedCriteria(this.schemeClass, "m");
		criteria.add(Restrictions.eq("m.lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("m.mode", SalesMode.COMPOUND));
		criteria.add(Restrictions.eq("m.won", true));
		criteria.add(Restrictions.gt("m.createTime", com.cai310.utils.DateUtil.strToDate("2013-10-08 0:00")));
		criteria.add(Restrictions.eq("m.schemePrintState", SchemePrintState.SUCCESS));
		List<JczqScheme> list= queryService.findByDetachedCriteria(criteria);
		for (JczqScheme jczqScheme : list) {
			schemeService.updateResult(jczqScheme.getId());
		}
		return "wonListTable";
	}
	
	
	public String wonListTable() {				
		XDetachedCriteria criteria = super.buildWonListQueryCriteria();

		if(this.getPlayType()!=null)
		criteria.add(Restrictions.eq("m.playType", this.getPlayType()));
		criteria.add(Restrictions.eq("m.lotteryType", this.getLotteryType()));
			
		this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);
		
		return "wonListTable";
	}
	
	@Override
	protected String doEditNew() throws Exception {
		List<DateTime> dateList = Lists.newArrayList();
		DateTime now = new DateTime();
		dateList.add(now);
		for (int i = 1; i < DAY_SIZE; i++) {
			dateList.add(now.plusDays(-i));
		}
		Struts2Utils.setAttribute("dateList", dateList);
		
		if (playType == null){
			playType = PlayType.SPF;
		}
		if (passMode == null){
			passMode = PassMode.PASS;
		}
		
		Map<String, Item[]> itemMap = Maps.newLinkedHashMap();
		Map<String, PlayType> playMap = Maps.newLinkedHashMap();
		if(passMode.equals(PassMode.MIX_PASS)){
			this.playType = PlayType.MIX;			
			for (PlayType playType : PlayType.values()) {
				if(playType.equals(PlayType.MIX))continue;
				itemMap.put(playType.name(), playType.getAllItems());
				if (playType == PlayType.BF) {
					Map<String, Item[]> bfItemMap = Maps.newLinkedHashMap();
					bfItemMap.put("胜", ItemBF.WINS);
					bfItemMap.put("平", ItemBF.DRAWS);
					bfItemMap.put("负", ItemBF.LOSES);
					Struts2Utils.setAttribute("bfItemMap", bfItemMap);
					Struts2Utils.setAttribute("itemColspan", ItemBF.WINS.length);
				}else{
					Struts2Utils.setAttribute(playType.name()+"_itemArr",playType.getAllItems());
				}
				Struts2Utils.setAttribute(playType.name()+"_rateMap",localCache.getRateData(playType, PassMode.PASS));
				playMap.put(playType.name(), playType);
			} 			
		}else{
			switch(playType){
			case SPF:
			case RQSPF:
				PlayType playType = PlayType.SPF;
				itemMap.put(playType.name(), playType.getAllItems());
				playMap.put(playType.name(), playType);
				playType = PlayType.RQSPF;
				itemMap.put(playType.name(), playType.getAllItems());
				playMap.put(playType.name(), playType);
				break;
			case JQS:
			case BQQ:
				itemMap.put(this.playType.name(), this.playType.getAllItems());
				playMap.put(this.playType.name(), this.playType);
				break;
			case BF:
				Map<String, Item[]> bfItemMap = Maps.newLinkedHashMap();
				bfItemMap.put("胜", ItemBF.WINS);
				bfItemMap.put("平", ItemBF.DRAWS);
				bfItemMap.put("负", ItemBF.LOSES);
				Struts2Utils.setAttribute("bfItemMap", bfItemMap);
				itemMap.put(this.playType.name(), this.playType.getAllItems());
				Struts2Utils.setAttribute("itemColspan", ItemBF.WINS.length);
				playMap.put(this.playType.name(), this.playType);
				break;
			}
		}
		Struts2Utils.setAttribute("itemMap",itemMap);
		Struts2Utils.setAttribute("playMap",playMap);
		
		//是否第一次追投
		Integer isFirstChased = null;
		if(StringUtils.isNotBlank(Struts2Utils.getParameter("isFirstChased")))
			isFirstChased = Integer.valueOf(Struts2Utils.getParameter("isFirstChased"));
		
			
		//追投方案详情ID
		Long jczqChasePlanDetailId = null;
		if(StringUtils.isNotBlank(Struts2Utils.getParameter("jczqChasePlanDetailId")))
			jczqChasePlanDetailId = Long.valueOf(Struts2Utils.getParameter("jczqChasePlanDetailId"));
		
		//回报率等级
		Integer returnRateLevel = null;
		if(StringUtils.isNotBlank(Struts2Utils.getParameter("returnRateLevel")))
			returnRateLevel = Integer.valueOf(Struts2Utils.getParameter("returnRateLevel"));
		
		if(isFirstChased != null && isFirstChased==1)
			Struts2Utils.setAttribute("isFirstChased", true);
		else
			Struts2Utils.setAttribute("isFirstChased", false);
		
		if(returnRateLevel != null)
			Struts2Utils.setAttribute("returnRateLevel", returnRateLevel);
		
		if(jczqChasePlanDetailId != null)
			Struts2Utils.setAttribute("jczqChasePlanDetailId", jczqChasePlanDetailId);
		
		List<JczqMatch> matchs = findMatchsOfCacheable();
		if (matchs != null && !matchs.isEmpty()) {
			List<String> games = Lists.newArrayList();
			int handicapCount = 0;
			int unHandicapCount = 0;
			Map<String, List<JczqMatch>> matchMap = Maps.newTreeMap();
			TreeMap<Double, String> fangjianglvAndMatchKeyMap = Maps.newTreeMap();
			
			for (JczqMatch m : matchs) {
				
				if(this.playTypeWeb != null  && (this.playTypeWeb.equals(PlayTypeWeb.DGP) || this.playTypeWeb.equals(PlayTypeWeb.YP))) {					
					Double fanjianglv = getFanJiangLvOf(m);
					if(fanjianglv != null) {
						while(fangjianglvAndMatchKeyMap.containsKey(fanjianglv)) {
							fanjianglv = fanjianglv - 0.000000000001;
						}
						fangjianglvAndMatchKeyMap.put(fanjianglv, m.getMatchKey());
					}						
					
				}
				
				if (StringUtils.isNotBlank(m.getGameName())) {
					if (!games.contains(m.getGameName()))
						games.add(m.getGameName());
				}

				if (m.getHandicap() != null && m.getHandicap() != 0)
					handicapCount++;
				else
					unHandicapCount++;
				
				DateTime dateTime = JczqUtil.getDateTime(m.getMatchDate());
				String key = dateTime.toString("yyyy年MM月dd日 E",Locale.SIMPLIFIED_CHINESE);

				List<JczqMatch> matchList = matchMap.get(key);
				if (matchList == null){
					matchList = Lists.newArrayList();
					matchMap.put(key, matchList);
				}
				matchList.add(m);
			}
			Struts2Utils.setAttribute("today", DateUtil.getDate());
			Struts2Utils.setAttribute("fangjianglvAndMatchKeyMap", fangjianglvAndMatchKeyMap);
			Struts2Utils.setAttribute("games", games);
			Struts2Utils.setAttribute("handicapCount", handicapCount);
			Struts2Utils.setAttribute("unHandicapCount", unHandicapCount);
			Struts2Utils.setAttribute("matchMap", matchMap);
			
			//2013-7-6 胜平负与让球胜平负合并投注,同时取赔率
			if(playType==PlayType.SPF || playType==PlayType.RQSPF){
				Struts2Utils.setAttribute("rqspfRateData", localCache.getRateData(PlayType.RQSPF, passMode));
				Struts2Utils.setAttribute("spfRateData", localCache.getRateData(PlayType.SPF, passMode));
			}else{
				Struts2Utils.setAttribute("rateData", localCache.getRateData(playType, passMode));
			}
		}
		return super.doEditNew();
	}
	//取得某场比赛的返奖率
	private Double getFanJiangLvOf(JczqMatch m) {
		Double fanjianglv =  null;
		try {
			if(m.getHandicap() == 1) {
				Double zhuBuBaiSp = localCache.getRateData(PlayType.RQSPF, PassMode.PASS).get(m.getMatchKey()).get(ItemSPF.WIN.name()).getValue();//主不败SP
				Double keShengSp =  localCache.getRateData(PlayType.SPF, PassMode.PASS).get(m.getMatchKey()).get(ItemSPF.LOSE.name()).getValue();//客胜SP	
				if(zhuBuBaiSp != null && keShengSp != null)
					fanjianglv = (zhuBuBaiSp*keShengSp)/(zhuBuBaiSp + keShengSp);//返奖率			
					
			} else if(m.getHandicap() == -1) {
				Double zhuShengSp = localCache.getRateData(PlayType.RQSPF, PassMode.PASS).get(m.getMatchKey()).get(ItemSPF.LOSE.name()).getValue();//主胜SP
				Double keBuBaiSp = localCache.getRateData(PlayType.SPF, PassMode.PASS).get(m.getMatchKey()).get(ItemSPF.WIN.name()).getValue();//客不败SP
				if(zhuShengSp != null && keBuBaiSp != null)
					fanjianglv = (zhuShengSp*keBuBaiSp)/(zhuShengSp + keBuBaiSp);//返奖率			
			}
		} catch(Exception e) {
			fanjianglv =  null;
		}
		return fanjianglv;
	}
	
	// -------------------------------------------------------
	@Override
	public String list() {
		List<DateTime> dateList = Lists.newArrayList();
		DateTime now = new DateTime();
		dateList.add(now);
		for (int i = 1; i < DAY_SIZE; i++) {
			dateList.add(now.plusDays(-i));
		}
		Struts2Utils.setAttribute("dateList", dateList);
		return super.list();
	}
 
	@Override
	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria c = super.buildListDetachedCriteria();
		if (playType != null) {
			c.add(Restrictions.eq("m.playType", playType));
		}

		Date date = null;
		if (StringUtils.isNotBlank(currentDate)) {
			try {
				date = DateUtils.parseDate(currentDate, new String[] { "yyyy-MM-dd" });
			} catch (ParseException e) {
			}
			if (date == null) {
				currentDate = null;
			}
		}
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		if (date != null) {
			startTime.setTime(date);
			endTime.setTime(date);
		} else {
			startTime.add(Calendar.DATE, -DAY_SIZE + 1);
		}
		startTime.set(Calendar.HOUR, 0);
		startTime.set(Calendar.MINUTE, 0);
		startTime.set(Calendar.SECOND, 0);
		startTime.set(Calendar.MILLISECOND, 0);
		endTime.set(Calendar.HOUR, 0);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND, 0);
		endTime.set(Calendar.MILLISECOND, 0);
		endTime.add(Calendar.DATE, 1);
		c.add(Restrictions.ge("m.createTime", startTime.getTime()));
		c.add(Restrictions.lt("m.createTime", endTime.getTime()));

		return c;
	}

	@Override
	protected SchemeQueryDTO buildMySchemeQueryDTO() {
		SchemeQueryDTO queryDTO = super.buildMySchemeQueryDTO();
		if (playType != null) {
			queryDTO.setPlayType(playType.name());
		}
		return queryDTO;
	}
	
	public String review() throws Exception {
		List<DateTime> dateList = Lists.newArrayList();
		DateTime now = new DateTime();
		now = now.plusDays(-1);
		if(null==this.matchDate)this.matchDate=now.toString("yyyyMMdd");
		dateList.add(now);
		for (int i = 1; i < DAY_SIZE; i++) {
			dateList.add(now.plusDays(-i));
		}
		Struts2Utils.setAttribute("dateList", dateList);
		
		if (playType == null)
			playType = PlayType.SPF;
		if (passMode == null)
			passMode = PassMode.PASS;

		Struts2Utils.setAttribute("itemArr", playType.getAllItems());
		if (playType == PlayType.BF) {
			Map<String, Item[]> itemMap = Maps.newLinkedHashMap();
			itemMap.put("胜", ItemBF.WINS);
			itemMap.put("平", ItemBF.DRAWS);
			itemMap.put("负", ItemBF.LOSES);
			Struts2Utils.setAttribute("itemMap", itemMap);
			Struts2Utils.setAttribute("itemColspan", ItemBF.WINS.length);
		}
		String endTime = Struts2Utils.getParameter("endTime");
		String startTime = Struts2Utils.getParameter("startTime");
		List<JczqMatch> matchs = findMatchsOfCacheable();
		if(StringUtils.isNotBlank(startTime)&&StringUtils.isNotBlank(endTime)){
			List<Integer> needMatchDate = Lists.newArrayList();
			try{
				Date startDate = com.cai310.utils.DateUtil.strToDate(startTime,"yyyy-MM-dd");
				Date endDate = com.cai310.utils.DateUtil.strToDate(endTime,"yyyy-MM-dd");
				Integer endDateInt = Integer.valueOf(DateUtil.getDate(endDate));
				Struts2Utils.setAttribute("startTime", com.cai310.utils.DateUtil.dateToStr(startDate,"yyyy-MM-dd"));
				endDate = startDate;
				needMatchDate.add(Integer.valueOf(DateUtil.getDate(startDate)));
				for (int i = 0; i < 10; i++) {
					endDate = DateUtil.addDate(endDate, 1);
					needMatchDate.add(Integer.valueOf(DateUtil.getDate(endDate)));
					if(endDateInt.compareTo(Integer.valueOf(DateUtil.getDate(endDate)))<=0){
						break;
					}
				}
				Struts2Utils.setAttribute("endTime", com.cai310.utils.DateUtil.dateToStr(endDate,"yyyy-MM-dd"));
				if(!needMatchDate.isEmpty()){
					matchs = Lists.newArrayList();
					for (Integer matchDate : needMatchDate) {
						matchs.addAll(matchEntityManager.findMatchs(matchDate));
					}
				}
			}catch(Exception e){
				
			}
			
		}
		if (matchs != null && !matchs.isEmpty()) {
			List<String> games = Lists.newArrayList();
			int handicapCount = 0;
			int unHandicapCount = 0;
			Map<String, List<JczqMatch>> matchMap = Maps.newTreeMap();
			for (JczqMatch m : matchs) {
				if (!m.isEnded())
					continue;
				if (StringUtils.isNotBlank(m.getGameName())) {
					if (!games.contains(m.getGameName()))
						games.add(m.getGameName());
				}

				if (m.getHandicap() != null && m.getHandicap() != 0)
					handicapCount++;
				else
					unHandicapCount++;

				DateTime dateTime = JczqUtil.getDateTime(m.getMatchDate());
				String key = dateTime.toString("yyyy-MM-dd E",Locale.SIMPLIFIED_CHINESE).replaceAll("星期", "周");

				List<JczqMatch> matchList = matchMap.get(key);
				if (matchList == null)
					matchList = Lists.newArrayList();
				matchList.add(m);
				matchMap.put(key, matchList);
			}
			Struts2Utils.setAttribute("games", games);
			Struts2Utils.setAttribute("handicapCount", handicapCount);
			Struts2Utils.setAttribute("unHandicapCount", unHandicapCount);
			Struts2Utils.setAttribute("matchMap", matchMap);

			Struts2Utils.setAttribute("rateData", localCache.getRateData(playType, passMode));
		}
		return "review";
	}
	
	public String matchOdds(){
		HttpServletResponse response = Struts2Utils.getResponse();
    	response.setContentType("text/xml");
    	response.setCharacterEncoding("UTF-8");
    	response.setHeader("pragma", "no-cache");
    	Map<String, Map<String, RateItem>> rate = localCache.getRateData(this.playType, PassMode.PASS);
    	Map<String, RateItem> map;
    	Map<String, Map<String, RateItem>> matchRate = Maps.newHashMap();
    	String[] arr =  this.matchKey.split(",");
		for (String key : rate.keySet()) {
			for (int i = 0; i < arr.length; i++) {
				if(key.trim().equals(arr[i])){
					map = rate.get(key);
					matchRate.put(key, map);
				}
			}
		}
    	
    	try {
    		String jsonString = JSONObject.fromObject(matchRate).toString();
			response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
			response.getOutputStream().println("<odds>"+jsonString+"</odds>");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			response.flushBuffer();
		} catch (IOException e) {
			logger.warn("查询赔率输出出错---"+this.matchKey);
		}finally{
			try {
				response.getOutputStream().close();
			} catch (IOException e) {
				logger.warn("查询赔率关闭出错---"+this.matchKey);
			}
		}
		return null;
	}
	
	protected List<JczqMatch> findMatchsOfCacheable() {
		List<JczqMatch> matchList  = null;
		if(null==matchDate) {
			matchList = localCache.getMatchList();
		}else {
			matchList = matchEntityManager.findMatchs(Integer.valueOf(matchDate));
		}
		if (matchList == null || matchList.isEmpty()) {
			matchList = matchEntityManager.findMatchsOfUnEnd();
		}
		return matchList;
	}
	
	//判断追号详情ID是否正确
	private boolean isValidJczqChasePlanDetailId(long jczqChasePlanDetailId) {
		if(jczqChasePlanDetailEntityManager.getJczqChasePlanDetailBy(jczqChasePlanDetailId) != null)
			return true;
		else
			return false;
	}
	
	
	@Override
	protected JczqSchemeDTO buildSchemeDTO() throws WebDataException {
		
		//根据单式上传的内容设置投注项到相关list中
		if(createForm.getMode()==SalesMode.SINGLE && createForm.isFileUpload()){
			createForm.setPlayType(playType);
			supportOkoooAnd500wan();
		}else{
			if(createForm.confirmSchemePlayTypeAndPassMode()!=null){
				this.playType = createForm.confirmSchemePlayTypeAndPassMode();
			}
		}
		checkCreateForm();
		
		JczqSchemeDTO dto = super.buildSchemeDTO();
		
		Long jczqChasePlanDetailId = createForm.getJczqChasePlanDetailId();
		if(jczqChasePlanDetailId != null && isValidJczqChasePlanDetailId(jczqChasePlanDetailId)) {
			dto.setJczqChasePlanDetailId(createForm.getJczqChasePlanDetailId());
		}			
		
		dto.setPlayType(playType);
		dto.setPlayTypeWeb(this.playTypeWeb);
		if(this.playTypeWeb==null){
			dto.setPlayTypeWeb(PlayTypeWeb.playType2Web(playType));
		}
		
		dto.setSchemeType(createForm.getSchemeType());
		dto.setPassTypes(createForm.getPassTypes());
		
		switch (createForm.getMode()) {
		case COMPOUND:
			checkCreateFormOfCOMPOUND();
			buildSchemeDTOofCOMPOUND(dto);
			break;
		case SINGLE:
			if(!createForm.isOptimize()){
				checkCreateFormOfSINGLE();
			}
			buildSchemeDTOofSINGLE(dto);
			break;
		default:
			throw new WebDataException("投注方式不合法.");
		}
		return dto;
	}
	
	/**
	 * 构建免费保存方案实体
	 */
	@Override
	protected JczqSchemeTemp buildSchemeTemp(JczqSchemeDTO schemeDTO) throws WebDataException{
		JczqSchemeTemp schemeTemp = super.buildSchemeTemp(schemeDTO);
		schemeTemp.setPassMode(schemeDTO.getPassMode());
		long passTypeValue = 0;
		for (PassType passType : schemeDTO.getPassTypes()) {
			passTypeValue |= passType.getValue();
		}
		schemeTemp.setPassType(passTypeValue);
		schemeTemp.setPlayType(schemeDTO.getPlayType());
		schemeTemp.setPlayTypeWeb(schemeDTO.getPlayTypeWeb());
		schemeTemp.setSchemeType(schemeDTO.getSchemeType());
		schemeTemp.setTicketContent(schemeDTO.getTicketContent());
		return schemeTemp;
	}


	
	/**
	 * 构建单式DTO
	 * @param dto
	 * @throws WebDataException
	 */
	protected void buildSchemeDTOofSINGLE(JczqSchemeDTO dto) throws WebDataException{
		dto.setOptimize(createForm.isOptimize());
		dto.setPassMode(createForm.getPassMode());
		
		final StringBuilder ticketContentBuilder = new StringBuilder();
		TicketSplitCallbackSingle callback = new TicketSplitCallbackSingle() {
			@Override
			public void handle(int index, PassType passType, int multiple) {
				TicketItemSingle ticketItem = new TicketItemSingle(index, passType, multiple);
				ticketContentBuilder.append(ticketItem.toString()).append(TicketItem.ITEM_AND);
			}
		};
		
		JczqSingleContent singleContent = JsonUtil.getObject4JsonString(dto.getContent(), JczqSingleContent.class);
		List<Integer> multiples = singleContent.getMultiples();
		String[] contents = singleContent.converContent2Arr();
		PassType passType = createForm.getPassTypes().get(0);		
		
		int maxMatchSize = playType.getMaxMatchSize();
		Integer multOfScheme = createForm.getMultiple();//方案倍投
		
		if(singleContent.isOptimize()){
			Integer multiple = null;//投注项注数
			boolean hhCheck = true;//混合过关单式
			for(int m=0;m<multOfScheme;m++){
				for(int i=0;i<contents.length;i++){
					multiple=multiples.get(i);
					///混合过关单式
					if(hhCheck&&singleContent.isHh()){
						passType = PassType.P2_1;
						if(singleContent.getPlayTypes().get(0).split(",")[0]
									.equals(singleContent.getPlayTypes().get(0).split(",")[1])){
							dto.setPlayType(PlayType.valueOfName(singleContent.getPlayTypes().get(0).split(",")[0]));
						}else{
							dto.setPlayType(PlayType.MIX);
						}
						dto.setPassTypes(Lists.newArrayList(passType));
						hhCheck = false;
					}
					///混合过关单式
					String content = contents[i];
					String[] itemArr = content.split(",");
					if(content.indexOf("#")<0 && itemArr.length>passType.getMatchCount()){
						JczqUtil.multiplePassSplitSingle(callback, multiple, maxMatchSize, passType, content, i);
					}else{
						JczqUtil.splitWithMultipleSingle(callback, multiple, passType, content, i);
					}			
				}
			}
		}else{
			for(int i=0;i<contents.length;i++){				
				String content = contents[i];
				String[] itemArr = content.split(",");
				if(content.indexOf("#")<0 && itemArr.length>passType.getMatchCount()){
					JczqUtil.multiplePassSplitSingle(callback, multOfScheme, maxMatchSize, passType, content, i);
				}else{
					JczqUtil.splitWithMultipleSingle(callback, multOfScheme, passType, content, i);
				}			
			}
		}
		
		dto.setTicketContent(ticketContentBuilder.toString());
	}
		
	/**
	 * 构建复式DTO
	 * @param dto
	 * @throws WebDataException
	 */
	protected void buildSchemeDTOofCOMPOUND(JczqSchemeDTO dto) throws WebDataException{
		Collections.sort(createForm.getItems());
		final StringBuilder ticketContentBuilder = new StringBuilder();
		TicketSplitCallback callback = new TicketSplitCallback() {
			@Override
			public void handle(List<JczqMatchItem> matchItemList, PassType passType, int multiple) {
				long matchFlag = JczqUtil.chg2flag(matchItemList, createForm.getItems());
				TicketItem ticketItem = new TicketItem(matchFlag, passType, multiple);
				ticketContentBuilder.append(ticketItem.toString()).append(TicketItem.ITEM_AND);
			}
		};
		dto.setPassMode(createForm.getPassMode());
		
		List<List<JczqMatchItem>> itemsListOfMix = null;//混合拆单小复式集合
		List<PassType> passTypeListOfMix = null;//混合拆单小复式的过关方式
		if(PlayType.MIX.equals(this.playType)){
			itemsListOfMix = Lists.newArrayList();
			passTypeListOfMix = Lists.newArrayList();
			List<JczqMatchItem> danList = new ArrayList<JczqMatchItem>();//场次胆(不同场次)
			List<JczqMatchItem> unDanList = new ArrayList<JczqMatchItem>();//场次非胆(不同场次)
			Map<String,List<JczqMatchItem>> matchItemMap = Maps.newHashMap();
			List<JczqMatchItem> matchItems = null;
			List<String> matchkeys = Lists.newArrayList();
			String matchkey = null;
			for(JczqMatchItem item: this.createForm.getItems()){
				matchkey = item.getMatchKey();
				matchItems = matchItemMap.get(matchkey);
				if(matchItems!=null){
					matchItems.add(item);
				}else{
					matchItems = Lists.newArrayList();
					matchItems.add(item);
					matchItemMap.put(matchkey, matchItems);
				}
				if(!matchkeys.contains(matchkey)){
					matchkeys.add(matchkey);
					if(item.isDan()){
						danList.add(item);
					}else{
						unDanList.add(item);
					}
				}
			}
			for (PassType passType : this.createForm.getPassTypes()) {
				List<List<JczqMatchItem>> itemsOfComp = JczqUtil.spliteMatchOfPlayType(passType.getMatchCount(), danList, unDanList, matchItemMap, this.createForm.getDanMinHit(), this.createForm.getDanMinHit());
				itemsListOfMix.addAll(itemsOfComp);
				for(int i=0;i<itemsOfComp.size();i++){
					passTypeListOfMix.add(passType);
				}				
			}			
		}
		
		switch (createForm.getSchemeType()) {
		case SINGLE:
			dto.setPassMode(PassMode.SINGLE);
            if(createForm.getItems().size()>1)	throw new WebDataException("单关赛事只能选择一场比赛.");//y由于出票上只能出一场比赛
			JczqUtil.singleSplit(callback, createForm.getMultiple(), createForm.getItems());
			break;
		case SIMPLE_PASS:
			if(PlayType.MIX.equals(this.playType)){
				for(int i=0;i<itemsListOfMix.size();i++){
					List<JczqMatchItem> items = itemsListOfMix.get(i);
					PassType passType = passTypeListOfMix.get(i);
					for(int passMatch : passType.getPassMatchs()){
						List<PassType> passTypes = PassType.findPassTypes(passMatch,passMatch);
						JczqUtil.undanMultiplePassSplit(callback, createForm.getMultiple(), passMatch, Lists.newArrayList(passTypes.get(0)), items);
					}					
				}
				String[] ticketContents = ticketContentBuilder.toString().split(TicketItem.ITEM_AND);
				ticketContentBuilder.setLength(0);
				List<String> tempList = Lists.newArrayList();
				for(String ticketContent:ticketContents){
					if(!tempList.contains(ticketContent)){
						tempList.add(ticketContent);
						ticketContentBuilder.append(ticketContent).append(TicketItem.ITEM_AND);
					}
				}				
			}else{
				JczqUtil.splitWithMultiple(callback, createForm.getMultiple(), createForm.getPassTypes().get(0),createForm.getItems());
			}
			break;
		case MULTIPLE_PASS:
			if(PlayType.MIX.equals(this.playType)){
				for(int i=0;i<itemsListOfMix.size();i++){
					List<JczqMatchItem> items = itemsListOfMix.get(i);
					Integer maxMatchSize = null;
					for (JczqMatchItem item : items) {
						if(maxMatchSize==null || maxMatchSize>item.getPlayType().getMaxMatchSize())
							maxMatchSize = item.getPlayType().getMaxMatchSize();
					}
					JczqUtil.undanMultiplePassSplit(callback, createForm.getMultiple(), maxMatchSize, Lists.newArrayList(passTypeListOfMix.get(i)), items);
				}
			}else{
				final List<JczqMatchItem> danList = new ArrayList<JczqMatchItem>();
				final List<JczqMatchItem> undanList = new ArrayList<JczqMatchItem>();	
				for (JczqMatchItem item : createForm.getItems()) {
					if (item.isDan())
						danList.add(item);
					else
						undanList.add(item);
				}
				if (danList.size() == 0) {
					JczqUtil.undanMultiplePassSplit(callback, createForm.getMultiple(), playType.getMaxMatchSize(), createForm.getPassTypes(), createForm.getItems());
				} else {
					Integer danMinHit = createForm.getDanMinHit();
					Integer danMaxHit = createForm.getDanMaxHit();
					JczqUtil.danMultiplePassSplit(callback, createForm.getMultiple(), createForm.getPassTypes(), danList, undanList, danMinHit, danMaxHit);
				}
			}
			break;
		default:
			throw new WebDataException("过关方式不合法");
		}
		
		if (ticketContentBuilder.length() > 0){
			ticketContentBuilder.delete(ticketContentBuilder.length() - TicketItem.ITEM_AND.length(),
					ticketContentBuilder.length());
		}else{
			throw new WebDataException("投注拆票出现错误。请联系客服.");
		}
		dto.setTicketContent(ticketContentBuilder.toString());
	}
	

	protected void checkCreateForm() throws WebDataException {
		if (createForm == null)
			throw new WebDataException("表单数据为空.");

		if (playType == null)
			throw new WebDataException("玩法类型不能为空.");

		if (createForm.getSchemeType() == null)
			throw new WebDataException("方案类型不能为空.");
		if (createForm.getPassTypes() == null || createForm.getPassTypes().isEmpty())
			throw new WebDataException("过关方式不能为空.");
	}

	protected void checkCreateFormOfCOMPOUND() throws WebDataException {
		if (createForm.getItems() == null || createForm.getItems().isEmpty())
			throw new WebDataException("投注内容不能为空.");

		Integer maxMatchSize = null;
		int selectedMatchSize = createForm.countMatchSize();
		switch (createForm.getSchemeType()) {
		case SINGLE:
			if (selectedMatchSize > JczqConstant.SINGLE_MAX_MATCH_SIZE)
				throw new WebDataException("单关模式不能选择超过" + JczqConstant.SINGLE_MAX_MATCH_SIZE + "场.");

			if (createForm.getPassTypes() == null || createForm.getPassTypes().size() != 1
					|| createForm.getPassTypes().get(0) != PassType.P1)
				throw new WebDataException("过关方式不正确.");
			break;
		case SIMPLE_PASS:
			if(this.playType.equals(PlayType.MIX)){
				for (JczqMatchItem jczqMatchItem : createForm.getItems()) {
					if(null==maxMatchSize||maxMatchSize>jczqMatchItem.getPlayType().getMaxMatchSize())
						maxMatchSize = jczqMatchItem.getPlayType().getMaxMatchSize();
				}
			}else{
				maxMatchSize = playType.getMaxMatchSize();
			}
			if (selectedMatchSize > maxMatchSize)
				throw new WebDataException("[" + playType.getText() + "]普通过关选择不能超过" + maxMatchSize + "场.");

			if (createForm.getPassTypes().size() > 1)
				throw new WebDataException("普通过关模式只能选择一个过关方式.");

			PassType passType = createForm.getPassTypes().get(0);
			if (passType == PassType.P1)
				throw new WebDataException("过关模式不能选择单关过关方式.");
			if (passType.getMatchCount() != selectedMatchSize)
				throw new WebDataException("选择的场次数目与过关方式不匹配.");
			break;
		case MULTIPLE_PASS:
			for (PassType type : createForm.getPassTypes()) {
				if (type.getUnits() != 1)
					throw new WebDataException("多选过关模式不支持[" + type.getText() + "]过关.");
				if(this.playType.equals(PlayType.MIX)){
					maxMatchSize = null;
					for (JczqMatchItem jczqMatchItem : createForm.getItems()) {
						if(null==maxMatchSize||maxMatchSize>jczqMatchItem.getPlayType().getMaxMatchSize())
							maxMatchSize = jczqMatchItem.getPlayType().getMaxMatchSize();
					}
					if (type.getMatchCount() > maxMatchSize)
						throw new WebDataException("过关方式不正确.");
				}else{
					if (type.getMatchCount() > playType.getMaxMatchSize())
						throw new WebDataException("过关方式不正确.");
				}
			}
			break;
		default:
			throw new WebDataException("过关模式不合法.");
		}
	}
	
	@Override
	protected JczqSchemeCreateForm supplementCreateFormData() throws WebDataException{
		this.createForm = super.supplementCreateFormData();
		if(SalesMode.SINGLE.equals(this.createForm.getMode())){
			String schemeIdStr = Struts2Utils.getParameter("tempSchemeId");
			if(schemeIdStr==null){
				throw new WebDataException("操作的方案标识为空.");
			}
			Long schemeId = Long.valueOf(schemeIdStr);
			this.schemeTemp = jczqSchemeTempEntityManagerImpl.getScheme(schemeId);
			if(schemeTemp==null){
				throw new WebDataException("保存的方案 id["+schemeIdStr+"]未能找到.");
			}
			this.createForm.setContent(schemeTemp.getSingleContent().getContent());
		}
		return this.createForm;
	}
	
	protected void checkCreateFormOfSINGLE() throws WebDataException {
		if (createForm.getPassMode() != PassMode.PASS)
			throw new WebDataException("单式只支持普通过关模式.");
		if (this.uploadType==JczqUploadType.SELECT_MATCH && (createForm.getMatchKeys() == null || createForm.getMatchKeys().isEmpty()))
			throw new WebDataException("未选择投注场次.");
		if(this.uploadType==JczqUploadType.AHEAD){
			this.createForm.setAheadOfUploadContent(true);
		}		
		if (createForm.getPassTypes().size() > 1)
			throw new WebDataException("普通过关模式只能选择一个过关方式.");
		PassType passType = createForm.getPassTypes().get(0);
		if (passType.getUnits() != 1)
			throw new WebDataException("单式不支持此过关方式[" + passType.getText() + "].");
		else if (passType.getMatchCount() > playType.getMaxMatchSize())
			throw new WebDataException("不支持此过关方式[" + passType.getText() + "].");

		if("true".equals(Struts2Utils.getParameter("createFormTempOfSingle"))){return;}
		
		if (createForm.getCodes() == null || createForm.getCodes().isEmpty())
			throw new WebDataException("格式转换字符不能为空.");

		int codeSize = playType.getAllItems().length;
		if (createForm.getCodes().size() != codeSize)
			throw new WebDataException("格式转换字符的数目不对.");

		int codeStrLen = 0;
		Set<String> codeSet = new HashSet<String>();
		for (String code : createForm.getCodes()) {
			if (code == null)
				throw new WebDataException("格式转换字符不能为空.");
			if (!code.matches(RegexUtils.UpperLetterAndNumber))
				throw new WebDataException("格式转换字符只能由大写英文字母和数字组成.");

			if (codeStrLen == 0) {
				codeStrLen = code.length();
				if (codeStrLen > 2)
					throw new WebDataException("格式转换字符的长度只允许最多两个字符.");
			} else if (code.length() != codeStrLen)
				throw new WebDataException("格式转换字符的长度必须相同.");

			if (codeSet.contains(code))
				throw new WebDataException("格式转换字符不能重复.");

			codeSet.add(code);
		}
	}
	
	@Override
	protected String doShowTemp() throws WebDataException {
		Date firstMatchTime = null;			
		switch (schemeTemp.getMode()) {
		case COMPOUND:
			JczqCompoundContent compoundContent = schemeTemp.getCompoundContent(); 
			firstMatchTime = doShowCompound(compoundContent);
			break;
		case SINGLE:
			JczqSingleContent singleContent= this.schemeTemp.getSingleContent();
			firstMatchTime = doShowSingle(singleContent);
			break;
		default:
			throw new ServiceException("投注方式不正确.");
		}
		//加入剩余时间
		if(firstMatchTime.after(new Date())){
			StringBuffer timeStr = new StringBuffer();
			long day = DateUtil.getDifference(new Date(),firstMatchTime,Calendar.DATE);
			long hour = DateUtil.getDifference(new Date(),firstMatchTime,Calendar.HOUR)%24;
			long minute = DateUtil.getDifference(new Date(),firstMatchTime,Calendar.MINUTE)%60;
			timeStr.append(day).append("天").append(hour).append("小时").append(minute).append("分钟");
			Struts2Utils.setAttribute("remainTime", timeStr);
		}
		Struts2Utils.setAttribute("firstMatchTime", firstMatchTime);
		return super.doShowTemp();
	}
	
	@Override
	protected String doShow() throws WebDataException {
		Date firstMatchTime = null;
		switch (scheme.getMode()) {
		case COMPOUND:
			JczqCompoundContent compoundContent = scheme.getCompoundContent(); 
			firstMatchTime = doShowCompound(compoundContent);
			break;
		case SINGLE:
			JczqSingleContent singleContent= this.scheme.getSingleContent();
			firstMatchTime = doShowSingle(singleContent);
			break;
		default:
			throw new ServiceException("投注方式不正确.");
		}
		String timeStr = com.cai310.lottery.utils.DateUtil.remainTime(firstMatchTime);
		Struts2Utils.setAttribute("remainTime", timeStr);
		Struts2Utils.setAttribute("firstMatchTime", firstMatchTime);		
		
		if(PassMode.MIX_PASS.equals(scheme.getPassMode())){
			return "showMix";
		}else{
			return super.doShow();
		}
	}
	
	private Date doShowCompound(JczqCompoundContent compoundContent){
		List<String> matchKeys = new ArrayList<String>();
		Date firstMatchTime = null;
		List<JczqMatch> matchs = null;
		List<Object[]> selectedMatchList = Lists.newArrayList();//内容选项List
		Map<String,List<JczqMatchItem>> matchItemsOfMatch = Maps.newLinkedHashMap();
		List<JczqMatchItem> matchItemsOfMap = null;
		//方案选择内容信息Object[0]=赛程、Object[1]=选项
		Object[] selectedInfo = null;
		
		matchItemsOfMatch = Maps.newLinkedHashMap();
		for(JczqMatchItem item : compoundContent.getItems()){
			matchItemsOfMap = matchItemsOfMatch.get(item.getMatchKey());
			if(matchItemsOfMap==null){
				matchItemsOfMap = Lists.newArrayList();
				matchItemsOfMatch.put(item.getMatchKey(), matchItemsOfMap);
			}
			matchItemsOfMap.add(item);
			if(!matchKeys.contains(item.getMatchKey())){
				matchKeys.add(item.getMatchKey());
			}
		}
		matchs = matchEntityManager.findMatchs(matchKeys);
		for (JczqMatch jczqMatch : matchs) {
			if (firstMatchTime == null || firstMatchTime.after(jczqMatch.getMatchTime())){
				firstMatchTime = jczqMatch.getMatchTime();
			}
			selectedInfo = new Object[2];
			selectedInfo[0] = jczqMatch;
			selectedInfo[1] = matchItemsOfMatch.get(jczqMatch.getMatchKey());
			selectedMatchList.add(selectedInfo);
		}
		Struts2Utils.setAttribute("selectedMatchList", selectedMatchList);
		return firstMatchTime;
	}
	
	
	private Date doShowSingle(JczqSingleContent singleContent){

		//以下为优化方案的显示方式，单式的则需要另外处理
		List<String> matchKeys = new ArrayList<String>();
		Date firstMatchTime = null;
		List<JczqMatch> matchs = null;
		List<Object[]> selectedMatchList = Lists.newArrayList();//内容选项List
		Map<String,List<JczqMatchItem>> matchItemsOfMatch = Maps.newLinkedHashMap();
		List<JczqMatchItem> matchItemsOfMap = null;
		//方案选择内容信息Object[0]=赛程、Object[1]=选项
		Object[] selectedInfo = null;
		
		matchKeys = singleContent.getMatchkeys();
		matchs = matchEntityManager.findMatchs(matchKeys);
		List<JczqMatchItem> matchItems = singleContent.getItems();
		matchItemsOfMatch = Maps.newLinkedHashMap();
		for(JczqMatchItem item : matchItems){
			matchItemsOfMap = matchItemsOfMatch.get(item.getMatchKey());
			if(matchItemsOfMap==null){
				matchItemsOfMap = Lists.newArrayList();
				matchItemsOfMatch.put(item.getMatchKey(), matchItemsOfMap);
			}
			matchItemsOfMap.add(item);
		}
		for (JczqMatch jczqMatch : matchs) {
			if (firstMatchTime == null || firstMatchTime.after(jczqMatch.getMatchTime())){
				firstMatchTime = jczqMatch.getMatchTime();
			}
			selectedInfo = new Object[2];
			selectedInfo[0] = jczqMatch;
			selectedInfo[1] = matchItemsOfMatch.get(jczqMatch.getMatchKey());
			selectedMatchList.add(selectedInfo);
		}
		Struts2Utils.setAttribute("selectedMatchList", selectedMatchList);
		return firstMatchTime;
	}


	@Override
	public String myList() {
		if (playType == null)
			playType = PlayType.SPF;
		return super.myList();
	}
	
	public String viewTicketTempCombination(){
		String returnString = "ticketCombination";
		if(null!=Struts2Utils.getParameter("type")){
			///单式
			returnString="ticketCombination-single";
		}
		try {
			if (this.id != null)
				this.schemeTemp = jczqSchemeTempEntityManagerImpl.getScheme(this.id);
			
			if (this.schemeTemp == null)
				throw new WebDataException("方案不存在.");
            
			// 该 玩法选项
			Map<String, Item> itemMap = Maps.newHashMap();
			if(this.schemeTemp.getPassMode().equals(PassMode.MIX_PASS)){
            	returnString="ticketCombination-mix";
            }else{
    			for (Item optionItem : this.schemeTemp.getPlayType().getAllItems()) {
    				itemMap.put(optionItem.getValue(), optionItem);
    			}
            }
			Struts2Utils.getRequest().setAttribute("itemMap", itemMap);
		
			List<JczqMatchItem> matchItemList = null;
			List<String> matchkeyList = Lists.newArrayList();
			//单复式分别获取
			SalesMode salesMode = this.schemeTemp.getMode();
			JczqSingleContent single = null;
			List<TicketItem> ticketList = null;
			List<TicketItemSingle> ticketSingleList = null;
			int ticketCount = 0;
			switch(salesMode){
			case COMPOUND:
				JczqCompoundContent compound = this.schemeTemp.getCompoundContent();
				matchItemList = compound.getItems();
				for (JczqMatchItem matchItem : matchItemList) {
					matchkeyList.add(matchItem.getMatchKey());
				}
				ticketList = this.schemeTemp.getTicketList();
				ticketCount = ticketList.size();
				break;
			case SINGLE:
				single = this.schemeTemp.getSingleContent();
				matchkeyList = single.getMatchkeys();
				ticketSingleList = this.schemeTemp.getSingleTicketList();
				ticketCount = ticketSingleList.size();
				break;
			default:
				throw new DataException("方案购买方式不合法");
			}			
			// 加载对应的对阵
			List<JczqMatch> matchList = matchEntityManager.findMatchs(matchkeyList);
			
			Map<String, JczqMatch> matchMap = Maps.newHashMap();
			for (JczqMatch match : matchList) {
				String matchKey = match.getMatchKey();
				matchMap.put(matchKey, match);
			}
			Struts2Utils.getRequest().setAttribute("matchMap", matchMap);
			
			// 拆票信息
			pagination.setPageSize(5);
			pagination.setTotalCount(ticketCount);
			List<JczqTicketCombination> resultList = new ArrayList<JczqTicketCombination>();
			List<JczqMatchItem> itemList = null;
			int count = 0;
			PassType passType = null;
			int multiple = 0;
			playType = this.schemeTemp.getPlayType();
			for (int i = 0; i < pagination.getTotalCount(); i++) {
				if (count >= pagination.getFirst()) {
					itemList = new ArrayList<JczqMatchItem>();
					// 获取选择场次内容(单复式分别获取)
					switch(salesMode){
					case COMPOUND:
						TicketItem ticketItem = ticketList.get(i);
						passType = ticketItem.getPassType();
						multiple = ticketItem.getMultiple();
						if (ticketItem.getMatchFlag() == 0) {
							itemList = matchItemList;
						} else {
							for (int p = 0; p < matchItemList.size(); p++) {
								if ((ticketItem.getMatchFlag() & (0x1l << p)) > 0)
									itemList.add(matchItemList.get(p));
							}
						}
						break;
					case SINGLE:
						final List<String> playTypes = single.getPlayTypes();//混合投中使用
						String[] playTypeStr=null;
						if(playType==PlayType.MIX){
							playTypeStr = playTypes.get(i).split(",");
						}
						final List<String> matchKeys = single.getMatchkeys();
						String[] contentArr = single.converContent2Arr();
						TicketItemSingle ticketItemSingle = ticketSingleList.get(i);
						passType = ticketItemSingle.getPassType();
						multiple = ticketItemSingle.getMultiple();
						String[] ordinalArr = contentArr[ticketItemSingle.getIndex()].split(",");
						for(int k = 0; k < ordinalArr.length; k++){
							JczqMatchItem matchItem = new JczqMatchItem();
							String ordinalStr = ordinalArr[k];
							if ("#".equals(ordinalStr))
								continue;

							int ordinal = Integer.parseInt(ordinalStr);
							if(playType==PlayType.MIX){
								matchItem.setPlayType(PlayType.valueOfName(playTypeStr[k]));					
							}else{
								matchItem.setPlayType(playType);
							}
							matchItem.setValue(0x1<<ordinal);
							matchItem.setMatchKey(matchKeys.get(k));
							itemList.add(matchItem);
						}
						break;
					}
					
					JczqTicketCombination comb = new JczqTicketCombination(this.schemeTemp.getPassMode(),this.schemeTemp.getPlayType(), itemList,
							passType, multiple, null, null);
					resultList.add(comb);
					if (resultList.size() == pagination.getPageSize())
						break;
				}
				count++;
			}
			pagination.setResult(resultList);
			return returnString;
		} catch (WebDataException e) {
			addActionError(e.getMessage());
			Struts2Utils.getRequest().setAttribute("error", e.getMessage());
		} catch (DataException e) {
			addActionError(e.getMessage());
			Struts2Utils.getRequest().setAttribute("error", "网络忙！请稍后再试");
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
			Struts2Utils.getRequest().setAttribute("error", "网络忙！请稍后再试");
		}

		return "combination-error";
	}

	public String viewTicketCombination() {
		String returnString = "ticketCombination";
		if(null!=Struts2Utils.getParameter("type")){
			///单式
			returnString="ticketCombination-single";
		}
		try {
			if (this.id != null)
				this.scheme = getSchemeEntityManager().getScheme(this.id);
			else {
				String schemeNumber = Struts2Utils.getRequest().getParameter("schemeNumber");
				if (StringUtils.isBlank(schemeNumber))
					throw new WebDataException("方案号为空.");
				this.scheme = getSchemeEntityManager().getSchemeBy(schemeNumber);
			}
			if (this.scheme == null)
				throw new WebDataException("方案不存在.");
            
//			if (this.scheme.getPassMode() == PassMode.SINGLE)
//				throw new WebDataException("只有过关投注才有[票面奖金明细]!");

			boolean onlyWon = "true".equals(Struts2Utils.getRequest().getParameter("onlyWon"));// 仅查看中奖的

			// 是否允许查看
			this.period = this.periodEntityManager.getPeriod(scheme.getPeriodId());
			String view = canViewDetail(scheme, period, getLoginUser());
			if (!"true".equalsIgnoreCase(view))
				throw new WebDataException(view);

			// 该 玩法选项
			Map<String, Item> itemMap = Maps.newHashMap();
			if(this.scheme.getPassMode().equals(PassMode.MIX_PASS)){
            	returnString="ticketCombination-mix";
            }else{
    			for (Item optionItem : this.scheme.getPlayType().getAllItems()) {
    				itemMap.put(optionItem.getValue(), optionItem);
    			}
            }
			Struts2Utils.getRequest().setAttribute("itemMap", itemMap);
		
			List<JczqMatchItem> matchItemList = null;
			List<String> matchkeyList = Lists.newArrayList();
			//单复式分别获取
			SalesMode salesMode = this.scheme.getMode();
			JczqSingleContent single = null;
			List<TicketItem> ticketList = null;
			List<TicketItemSingle> ticketSingleList = null;
			int ticketCount = 0;
			switch(salesMode){
			case COMPOUND:
				JczqCompoundContent compound = this.scheme.getCompoundContent();
				matchItemList = compound.getItems();
				for (JczqMatchItem matchItem : matchItemList) {
					matchkeyList.add(matchItem.getMatchKey());
				}
				ticketList = this.scheme.getTicketList();
				ticketCount = ticketList.size();
				break;
			case SINGLE:
				single = this.scheme.getSingleContent();				
				matchkeyList = single.getMatchkeys();
				ticketSingleList = this.scheme.getSingleTicketList();
				ticketCount = ticketSingleList.size();
				break;
			default:
				throw new DataException("方案购买方式不合法");
			}			
			// 加载对应的对阵
			List<JczqMatch> matchList = matchEntityManager.findMatchs(matchkeyList);
			Map<String, JczqMatch> matchMap = Maps.newHashMap();
			for (JczqMatch match : matchList) {
				String matchKey = match.getMatchKey();
				matchMap.put(matchKey, match);
			}
			Struts2Utils.getRequest().setAttribute("matchMap", matchMap);

			
			// 出票信息
			List<Map<String, Map<String, Double>>> printAwardList = this.scheme.getPrintAwardList();
			pagination.setPageSize(5);
			pagination.setTotalCount(ticketCount);
			List<JczqTicketCombination> resultList = new ArrayList<JczqTicketCombination>();
			List<JczqMatchItem> itemList = null;
			int count = 0;
			PassType passType = null;
			int multiple = 0;
			playType = this.scheme.getPlayType();
			for (int i = 0; i < pagination.getTotalCount(); i++) {
				boolean won = false;
				if (count >= pagination.getFirst()) {
					itemList = new ArrayList<JczqMatchItem>();
					// 获取选择场次内容(单复式分别获取)
					switch(salesMode){
					case COMPOUND:
						TicketItem ticketItem = ticketList.get(i);
						passType = ticketItem.getPassType();
						multiple = ticketItem.getMultiple();
						if (ticketItem.getMatchFlag() == 0) {
							itemList = matchItemList;
						} else {
							for (int p = 0; p < matchItemList.size(); p++) {
								if ((ticketItem.getMatchFlag() & (0x1l << p)) > 0)
									itemList.add(matchItemList.get(p));
							}
						}
						break;
					case SINGLE:
						final List<String> playTypes = single.getPlayTypes();//混合投中使用
						String[] playTypeStr = null;
						final List<String> matchKeys = single.getMatchkeys();
						String[] contentArr = single.converContent2Arr();
						TicketItemSingle ticketItemSingle = ticketSingleList.get(i);
						passType = ticketItemSingle.getPassType();
						multiple = ticketItemSingle.getMultiple();
						String[] ordinalArr = contentArr[ticketItemSingle.getIndex()].split(",");
						if(playType==PlayType.MIX){
							playTypeStr = playTypes.get(ticketItemSingle.getIndex()).split(",");
						}
						for(int k = 0; k < ordinalArr.length; k++){
							JczqMatchItem matchItem = new JczqMatchItem();
							String ordinalStr = ordinalArr[k];
							if ("#".equals(ordinalStr))
								continue;

							int ordinal = Integer.parseInt(ordinalStr);
							if(playType==PlayType.MIX){
								matchItem.setPlayType(PlayType.valueOfName(playTypeStr[k]));					
							}else{
								matchItem.setPlayType(playType);
							}
							matchItem.setValue(0x1<<ordinal);
							matchItem.setMatchKey(matchKeys.get(k));
							itemList.add(matchItem);
						}
						break;
					}
					

					// 检查出票、装载出票SP值与更新中奖
					Double totalPrizeAfterTax = null;
					Map<String, Map<String, Double>> matchPrintAwardMap = (printAwardList != null) ? printAwardList.get(i) : null;
					boolean printed = matchPrintAwardMap != null && !matchPrintAwardMap.isEmpty();// 检查出票
					if (printed) {
						// 更新中奖
						List<JcWonMatchItem> correctList = new ArrayList<JcWonMatchItem>();
						
						for (JczqMatchItem matchItem : itemList) {
							String matchKey = matchItem.getMatchKey();
							JczqMatch match = matchMap.get(matchKey);

							Map<String, Double> awardMap = matchPrintAwardMap.get(matchKey);

							if (match.isCancel()) {
								JcWonMatchItem wonItem = new JcWonMatchItem();
								wonItem.setMatchKey(matchKey);
								wonItem.setSelectCount(matchItem.selectedCount());
								wonItem.setCancel(true);
								wonItem.setAward(1d);
								correctList.add(wonItem);
								continue;
							} else if (match.isEnded()) {
								Item rs = null;
								if(this.scheme.getPassMode().equals(PassMode.MIX_PASS)){
									 rs = match.getResult(matchItem.getPlayType());
								}else{
									 rs = match.getResult(this.scheme.getPlayType());
								}
								if (rs != null) {
									if (matchItem.hasSelect(rs)) {
										JcWonMatchItem wonItem = new JcWonMatchItem();
										wonItem.setMatchKey(matchKey);
										wonItem.setSelectCount(matchItem.selectedCount());
										wonItem.setResultItem(rs);
										Double award = Double.valueOf(""+awardMap.get(rs.getValue()));
//										if(this.scheme.getPassMode().equals(PassMode.SINGLE)){
//											award=award*2;
//										}
										wonItem.setAward(award);
										correctList.add(wonItem);
										continue;
									}
								}
							}
						}
						if (correctList.size() >= passType.getPassMatchs()[0]) {
							JczqSimplePrizeWork prizeWork = new JczqSimplePrizeWork(this.scheme.getPassMode(), multiple, passType, correctList);
							totalPrizeAfterTax = prizeWork.getTotalPrizeAfterTax();
						}
					}
					won = totalPrizeAfterTax != null && totalPrizeAfterTax > 0;
					if (!onlyWon || won) {
						JczqTicketCombination comb = new JczqTicketCombination(this.scheme.getPassMode(),this.scheme.getPlayType(), itemList,
								passType, multiple, matchPrintAwardMap, totalPrizeAfterTax);
						resultList.add(comb);
						if (resultList.size() == pagination.getPageSize())
							break;
					}
				}
				if (!onlyWon || won)
					count++;
			}
			pagination.setResult(resultList);
			return returnString;
		} catch (WebDataException e) {
			addActionError(e.getMessage());
			Struts2Utils.getRequest().setAttribute("error", e.getMessage());
		} catch (DataException e) {
			addActionError(e.getMessage());
			Struts2Utils.getRequest().setAttribute("error", "网络忙！请稍后再试");
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
			Struts2Utils.getRequest().setAttribute("error", "网络忙！请稍后再试");
		}

		return "combination-error";
	}
		

	/**
	 * 新的奖金预测(兼容混合过关)
	 * @return
	 */
	public String prizeForecast(){
		try {			
			if (playType == null)
				throw new WebDataException("玩法类型为空.");
			if (createForm == null)
				throw new WebDataException("表单为空.");
			if (createForm.getPeriodId() == null)
				throw new WebDataException("期ID为空.");
			if (createForm.getMultiple() == null || createForm.getMultiple() < 1)
				throw new WebDataException("倍数不正确.");
			List<JczqMatchItem> items = createForm.getItems();
			if (items == null || items.isEmpty())
				throw new WebDataException("场次内容为空.");
			if (createForm.getPassTypes() == null || createForm.getPassTypes().isEmpty())
				throw new WebDataException("过关方式为空.");
			for (PassType passType : createForm.getPassTypes()) {
				if (passType.getUnits() > 1 && createForm.getPassTypes().size() > 1)
					throw new WebDataException("过关方式不正确.");
			}			

			List<JczqMatchItem> danList = new ArrayList<JczqMatchItem>();//场次胆(不同场次)
			List<JczqMatchItem> unDanList = new ArrayList<JczqMatchItem>();//场次非胆(不同场次)
			Map<String,List<JczqMatchItem>> matchItemMap = Maps.newHashMap();
			List<JczqMatchItem> matchItems = null;
			List<String> matchkeys = Lists.newArrayList();
			String matchkey = null;
			for(JczqMatchItem item : items){
				matchkey = item.getMatchKey();
				matchItems = matchItemMap.get(matchkey);
				if(matchItems!=null){
					matchItems.add(item);
				}else{
					matchItems = Lists.newArrayList();
					matchItems.add(item);
					matchItemMap.put(matchkey, matchItems);
				}
				if(!matchkeys.contains(matchkey)){
					matchkeys.add(matchkey);
					if(item.isDan()){
						danList.add(item);
					}else{
						unDanList.add(item);
					}
				}
			}
			Map<String,Boolean> danOfMatchMap = Maps.newHashMap();
			for(JczqMatchItem matchItem : danList){
				danOfMatchMap.put(matchItem.getMatchKey(), matchItem.isDan());
			}
			Struts2Utils.setAttribute("danOfMatchMap", danOfMatchMap);
			Struts2Utils.setAttribute("matchItemMap", matchItemMap);
			
			for (JczqMatchItem matchItem : items) {
				if(matchItem.getSps()==null || matchItem.getSps().isEmpty()){
					throw new WebDataException(matchItem.getMatchKey() + "场次SP数据为空.");
				}
			}
			
			List<JczqMatch> matchList = matchEntityManager.findMatchs(matchkeys);
			Collections.sort(matchList, new Comparator<JczqMatch>() {
				public int compare(JczqMatch o1, JczqMatch o2) {
					return o1.getMatchKey().compareTo(o2.getMatchKey());			
				}
			});
			if (matchList == null || matchList.size() != matchkeys.size())
				throw new WebDataException("找不到对应场次的赛事.");
			Struts2Utils.setAttribute("matchs", matchList);
			
			Map<String,List<Item>> excludeMap_max = excludePrizeItem(matchkeys, matchItemMap);
			
			//各场次最小及各玩法的最大赔率
			Map<String,Object[]> minMaxSpOfMatchMap = Maps.newHashMap();
			Map<String,Double> maxSpOfMatchMap = Maps.newHashMap();
			Map<PlayType,Double> maxSpOfPlayTypeMap = null;
			Iterator<Entry<String,List<JczqMatchItem>>> itorItemsOfMatch = matchItemMap.entrySet().iterator();
			while(itorItemsOfMatch.hasNext()){
				Entry<String,List<JczqMatchItem>> itemsOfMatchEntry = itorItemsOfMatch.next();
				List<JczqMatchItem> itemsOfMatch = itemsOfMatchEntry.getValue();
				Object[] minMaxSpInfo = new Object[2];
				double minSp = 0;
				maxSpOfPlayTypeMap = Maps.newHashMap();
				double maxSpOfMatch = 0.0d;
				for(JczqMatchItem item : itemsOfMatch){
					double[] minMaxSp = null;
					if(excludeMap_max==null || excludeMap_max.isEmpty()){
						minMaxSp = item.findSelectedMinMaxSp(null);
					}else{
						minMaxSp = item.findSelectedMinMaxSp(excludeMap_max.get(item.getMatchKey()+item.getPlayType()));
					}
					if(minSp==0 || minMaxSp[0]<minSp){
						minSp = minMaxSp[0];
					}
					maxSpOfPlayTypeMap.put(item.getPlayType(), minMaxSp[1]);
					maxSpOfMatch+=minMaxSp[1];
				}
				minMaxSpInfo[0] = minSp;
				minMaxSpInfo[1] = maxSpOfPlayTypeMap;
				minMaxSpOfMatchMap.put(itemsOfMatchEntry.getKey(), minMaxSpInfo);
				maxSpOfMatchMap.put(itemsOfMatchEntry.getKey(), maxSpOfMatch);
			}
			
			//对非胆场次进行最大最小奖金排序
			Map<String,Double> minSpMap = Maps.newHashMap();
			Map<String,Double> maxSpMap = Maps.newHashMap();
			for(JczqMatchItem matchItem : unDanList){
				matchkey = matchItem.getMatchKey();
				Object[] minMaxSpOfMatch = minMaxSpOfMatchMap.get(matchkey);
				minSpMap.put(matchkey, (Double)minMaxSpOfMatch[0]);
				maxSpMap.put(matchkey, maxSpOfMatchMap.get(matchkey));
			}
			List<Map.Entry<String, Double>> minSps = new ArrayList<Map.Entry<String, Double>>(minSpMap.entrySet());
			Collections.sort(minSps, new Comparator<Map.Entry<String, Double>>() {   
			    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
			    	if(o1.getValue()>o2.getValue()) return 1;
			    	else if(o1.getValue()==o2.getValue()) return 0;
			    	else return -1;
			        
			    }
			    
			});
			List<String> minSpSortList = Lists.newArrayList();
			for (int i = 0; i < minSps.size(); i++) {
				Map.Entry<String, Double> entry = minSps.get(i);
				minSpSortList.add(entry.getKey());
			}
			
			List<Map.Entry<String, Double>> maxSps = new ArrayList<Map.Entry<String, Double>>(minSpMap.entrySet());
			Collections.sort(maxSps, new Comparator<Map.Entry<String, Double>>() {   
			    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
			    	if(o1.getValue()<o2.getValue()) return 1;
			    	else if(o1.getValue()==o2.getValue()) return 0;
			    	else return -1;
			    }
			});
			List<String> maxSpSortList = Lists.newArrayList();
			for (int i = 0; i < maxSps.size(); i++) {
				Map.Entry<String, Double> entry = maxSps.get(i);
				maxSpSortList.add(entry.getKey());
			}
			
			PrizeForecast prizeForecast = new PrizeForecast(minSpSortList,maxSpSortList,createForm.getMultiple(), matchItemMap, createForm.getPassTypes(), danList, unDanList, createForm.getDanMinHit(), createForm.getDanMaxHit(), minMaxSpOfMatchMap, excludeMap_max);
			
			Struts2Utils.setAttribute("prizeForecast", prizeForecast);
			return "prize-forecast";
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}
	
	/**
	 * 胜平负、让球胜平负、比分、半全场   最大排它开奖
	 * @param passCount
	 * @param danList
	 * @param unDanList
	 * @return 
	 */
	private Map<String,List<Item>> excludePrizeItem(final List<String> matchkeys, final Map<String,List<JczqMatchItem>> matchItemMap){		
		
		//验证是否有中奖排它性可能(是否包含胜平负、让球胜平负)
		Set<String> fitMatchKeySets = Sets.newHashSet();
		List<String> matchKeysOfFit = Lists.newArrayList();
		List<JczqMatchItem> items = null;
		for(Entry<String,List<JczqMatchItem>> entry : matchItemMap.entrySet()){
			items = entry.getValue();
			for(JczqMatchItem item : items){
				if(PlayType.SPF.equals(item.getPlayType()) || PlayType.RQSPF.equals(item.getPlayType()) || PlayType.BF.equals(item.getPlayType()) || PlayType.BQQ.equals(item.getPlayType())){
					if(!matchKeysOfFit.contains(item.getMatchKey())){
						matchKeysOfFit.add(item.getMatchKey());
					}else{
						fitMatchKeySets.add(item.getMatchKey());
					}
				}
			}
		}
		if(fitMatchKeySets.isEmpty())return null;
		List<String> fitMatchKeys = Lists.newArrayList(fitMatchKeySets);
		
		
		//获取赛程的让球信息 homeToGuest = true(主让客)
		List<JczqMatch> matchs = matchEntityManager.findMatchs(fitMatchKeys);
		Map<String,Boolean> handicapOfMatchMap = JczqUtil.getHandicpOfMatchs(matchs);
		
		Map<String,List<Item>> excludeMap_max = JczqUtil.findExcludeMapOfReal(fitMatchKeys, handicapOfMatchMap, matchItemMap);
		
		//获取每个赛程最大的sp值项用于排除其他选项(混合过关)
		for(String matchkey : fitMatchKeys){
			List<JczqMatchItem> matchItems = matchItemMap.get(matchkey);
			double maxSP = 0;
			Item maxItem = null;
			PlayType playType = null;
			for(JczqMatchItem matchItem : matchItems){
				Object[] objs = matchItem.findSelectedMaxItemAndSp();
				if(maxSP<(Double)objs[1]){
					maxSP = (Double)objs[1];
					maxItem = (Item)objs[0];
					playType = matchItem.getPlayType();
				}
			}

			if(playType.equals(PlayType.BF)){
				ItemBF itembf = (ItemBF)maxItem;
				if(Arrays.binarySearch(ItemBF.WINS,itembf)>-1){
					List<Item> itemspfs = excludeMap_max.get(matchkey+PlayType.SPF);
					if(itemspfs==null){
						itemspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.SPF, itemspfs);
					}
					itemspfs.add(ItemSPF.DRAW);
					itemspfs.add(ItemSPF.LOSE);
					List<Item> itemrqspfs = excludeMap_max.get(matchkey+PlayType.RQSPF);
					if(itemrqspfs==null){
						itemrqspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.RQSPF, itemrqspfs);
					}
					if(itembf.getMoreScore()>1){
						itemrqspfs.add(ItemSPF.DRAW);
						itemrqspfs.add(ItemSPF.LOSE);
					}					
					List<Item> itembqq = excludeMap_max.get(matchkey+PlayType.BQQ);
					if(itembqq==null){
						itembqq = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.BQQ, itembqq);
					}
					if(itembf.getValue().endsWith("0")){
						itembqq.add(ItemBQQ.LOSE_WIN);
					}
					itembqq.addAll(Arrays.asList(ItemBQQ.LOSES));
					itembqq.addAll(Arrays.asList(ItemBQQ.DRAWS));
				}else if(Arrays.binarySearch(ItemBF.DRAWS,itembf)>-1){
					List<Item> itemspfs = excludeMap_max.get(matchkey+PlayType.SPF);
					if(itemspfs==null){
						itemspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.SPF, itemspfs);
					}
					itemspfs.add(ItemSPF.WIN);
					itemspfs.add(ItemSPF.LOSE);
					List<Item> itemrqspfs = excludeMap_max.get(matchkey+PlayType.RQSPF);
					if(itemrqspfs==null){
						itemrqspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.RQSPF, itemrqspfs);
					}
					if(handicapOfMatchMap.get(matchkey)){
						itemrqspfs.add(ItemSPF.WIN);
						itemrqspfs.add(ItemSPF.DRAW);
					}else{
						itemrqspfs.add(ItemSPF.DRAW);
						itemrqspfs.add(ItemSPF.LOSE);
					}
					List<Item> itembqq = excludeMap_max.get(matchkey+PlayType.BQQ);
					if(itembqq==null){
						itembqq = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.BQQ, itembqq);
					}
					if(itembf.getValue().equals("00")){
						itembqq.add(ItemBQQ.LOSE_DRAW);
						itembqq.add(ItemBQQ.WIN_DRAW);
					}
					itembqq.addAll(Arrays.asList(ItemBQQ.WINS));
					itembqq.addAll(Arrays.asList(ItemBQQ.LOSES));
				}else if(Arrays.binarySearch(ItemBF.LOSES,itembf)>-1){
					List<Item> itemspfs = excludeMap_max.get(matchkey+PlayType.SPF);
					if(itemspfs==null){
						itemspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.SPF, itemspfs);
					}
					itemspfs.add(ItemSPF.WIN);
					itemspfs.add(ItemSPF.DRAW);
					List<Item> itemrqspfs = excludeMap_max.get(matchkey+PlayType.RQSPF);
					if(itemrqspfs==null){
						itemrqspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.RQSPF, itemrqspfs);
					}
					if(handicapOfMatchMap.get(matchkey)){
						itemrqspfs.add(ItemSPF.WIN);
						itemrqspfs.add(ItemSPF.DRAW);
					}else{
						itemrqspfs.add(ItemSPF.DRAW);
						itemrqspfs.add(ItemSPF.LOSE);
					}
					itemrqspfs.add(ItemSPF.WIN);
					itemrqspfs.add(ItemSPF.DRAW);
					List<Item> itembqq = excludeMap_max.get(matchkey+PlayType.BQQ);
					if(itembqq==null){
						itembqq = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.BQQ, itembqq);
					}
					if(itembf.getValue().startsWith("0")){
						itembqq.add(ItemBQQ.WIN_LOSE);
					}
					itembqq.addAll(Arrays.asList(ItemBQQ.WINS));
					itembqq.addAll(Arrays.asList(ItemBQQ.DRAWS));
				}
			}else if(playType.equals(PlayType.BQQ)){			
				ItemBQQ itembqq = (ItemBQQ)maxItem;
				if(itembqq.getValue().startsWith("0")){//半场为负，则比分客队就不应该没进球
					List<Item> itembf = excludeMap_max.get(matchkey+PlayType.BF);
					if(itembf==null){
						itembf = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.BF, itembf);
					}
					itembf.addAll(Arrays.asList(ItemBF.GUESTBALL_0));
				}else if(itembqq.getValue().startsWith("3")){//半场为负，则比分主队就不应该没进球
					List<Item> itembf = excludeMap_max.get(matchkey+PlayType.BF);
					if(itembf==null){
						itembf = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.BF, itembf);
					}
					itembf.addAll(Arrays.asList(ItemBF.HOMEBALL_0));
				}
				
				if(Arrays.binarySearch(ItemBQQ.WINS,itembqq)>-1){
					List<Item> itemspfs = excludeMap_max.get(matchkey+PlayType.SPF);
					if(itemspfs==null){
						itemspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.SPF, itemspfs);
					}
					itemspfs.add(ItemSPF.DRAW);
					itemspfs.add(ItemSPF.LOSE);
					List<Item> itemrqspfs = excludeMap_max.get(matchkey+PlayType.RQSPF);
					if(itemrqspfs==null){
						itemrqspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.RQSPF, itemrqspfs);
					}
					if(handicapOfMatchMap.get(matchkey)!=null && !handicapOfMatchMap.get(matchkey)){
						itemrqspfs.add(ItemSPF.DRAW);
						itemrqspfs.add(ItemSPF.LOSE);
					}				
					List<Item> itembf = excludeMap_max.get(matchkey+PlayType.BF);
					if(itembf==null){
						itembf = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.BF, itembf);
					}
					itembf.addAll(Arrays.asList(ItemBF.LOSES));
					itembf.addAll(Arrays.asList(ItemBF.DRAWS));
				}else if(Arrays.binarySearch(ItemBQQ.DRAWS,itembqq)>-1){
					List<Item> itemspfs = excludeMap_max.get(matchkey+PlayType.SPF);
					if(itemspfs==null){
						itemspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.SPF, itemspfs);
					}
					itemspfs.add(ItemSPF.WIN);
					itemspfs.add(ItemSPF.LOSE);
					if(handicapOfMatchMap.get(matchkey)!=null){
						List<Item> itemrqspfs = excludeMap_max.get(matchkey+PlayType.RQSPF);
						if(itemrqspfs==null){
							itemrqspfs = Lists.newArrayList();
							excludeMap_max.put(matchkey+PlayType.RQSPF, itemrqspfs);
						}
						if(handicapOfMatchMap.get(matchkey)){
							itemrqspfs.add(ItemSPF.WIN);
							itemrqspfs.add(ItemSPF.DRAW);
						}else{
							itemrqspfs.add(ItemSPF.DRAW);
							itemrqspfs.add(ItemSPF.LOSE);
						}
					}										
					List<Item> itembf = excludeMap_max.get(matchkey+PlayType.BF);
					if(itembf==null){
						itembf = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.BF, itembf);
					}
					itembf.addAll(Arrays.asList(ItemBF.LOSES));
					itembf.addAll(Arrays.asList(ItemBF.WINS));
				}else if(Arrays.binarySearch(ItemBQQ.LOSES,itembqq)>-1){
					List<Item> itemspfs = excludeMap_max.get(matchkey+PlayType.SPF);
					if(itemspfs==null){
						itemspfs = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.SPF, itemspfs);
					}
					itemspfs.add(ItemSPF.WIN);
					itemspfs.add(ItemSPF.DRAW);
					if(handicapOfMatchMap.get(matchkey)!=null){
						List<Item> itemrqspfs = excludeMap_max.get(matchkey+PlayType.RQSPF);
						if(itemrqspfs==null){
							itemrqspfs = Lists.newArrayList();
							excludeMap_max.put(matchkey+PlayType.RQSPF, itemrqspfs);
						}
						if(handicapOfMatchMap.get(matchkey)){
							itemrqspfs.add(ItemSPF.WIN);
							itemrqspfs.add(ItemSPF.DRAW);
						}
					}					
					List<Item> itembf = excludeMap_max.get(matchkey+PlayType.BF);
					if(itembf==null){
						itembf = Lists.newArrayList();
						excludeMap_max.put(matchkey+PlayType.BF, itembf);
					}
					itembf.addAll(Arrays.asList(ItemBF.DRAWS));
					itembf.addAll(Arrays.asList(ItemBF.WINS));
				}
			}else if(playType.equals(PlayType.RQSPF)){
				ItemSPF itemrqspf = (ItemSPF)maxItem;
				if(itemrqspf.equals(ItemSPF.WIN)){
					if(handicapOfMatchMap.get(matchkey)){
						List<Item> itembqq = excludeMap_max.get(matchkey+PlayType.BQQ);
						if(itembqq==null){
							itembqq = Lists.newArrayList();
							excludeMap_max.put(matchkey+PlayType.BQQ, itembqq);
						}
						itembqq.addAll(Arrays.asList(ItemBQQ.LOSES));
					}
				}else if(itemrqspf.equals(ItemSPF.LOSE)){
					if(!handicapOfMatchMap.get(matchkey)){
						List<Item> itembqq = excludeMap_max.get(matchkey+PlayType.BQQ);
						if(itembqq==null){
							itembqq = Lists.newArrayList();
							excludeMap_max.put(matchkey+PlayType.BQQ, itembqq);
						}
						itembqq.addAll(Arrays.asList(ItemBQQ.WINS));
						itembqq.addAll(Arrays.asList(ItemBQQ.DRAWS));
					}
				}
				
			}
		}
		return excludeMap_max;
	}
	
	
	/**
	 * 计算单式单倍注数
	 * 
	 */
	@Override
	public String calcSingleBetUnits() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			checkCreateForm();
			supportOkoooAnd500wan();
			checkCreateFormOfSINGLE();
			int units = createForm.countSingleUnits();
			String matchKeys = StringUtil.listToString(createForm.getMatchKeys());
			resultMap.put("success", true);
			resultMap.put("units", units);
			resultMap.put("matchKeys", matchKeys);
		} catch (WebDataException e) {
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		} catch (DataException e) {
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		} catch (Exception e) {
			logger.warn("计算单式单倍注数出错.", e);
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}
		Struts2Utils.renderJson(resultMap);
		return null;
	}
	
	/**
	 * 相关网站的上传格式解析,不符合各网站格式的为标准格式
	 * @throws WebDataException
	 */
	protected void supportOkoooAnd500wan() throws WebDataException {
		String singleContent;
		try {
			singleContent = createForm.getSingleSchemeContent();
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
		if (StringUtils.isNotBlank(singleContent)) {
			if (singleContent.indexOf('→') > 0) {
				List<String> comOkoooCodeList = null;
				switch (playType) {
				case SPF:
				case RQSPF:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemSPF item : ItemSPF.values()) {
						comOkoooCodeList.add(item.getValue());
					}
					break;
				case JQS:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemJQS item : ItemJQS.values()) {
						comOkoooCodeList.add(item.getText());
					}
					break;
				case BF:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemBF item : ItemBF.values()) {
						comOkoooCodeList.add(item.getText());
					}
					break;
				case BQQ:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemBQQ item : ItemBQQ.values()) {
						comOkoooCodeList.add(item.getValue().replaceAll("(\\d)(\\d)", "$1-$2"));
					}
					break;
				}
				createForm.setComOkoooCodeList(comOkoooCodeList);
			} else if (singleContent.indexOf(":[") > 0) {
				List<String> com500wanCodeList = null;
				switch (playType) {
				case SPF:
					com500wanCodeList = new ArrayList<String>();
					for (ItemSPF item : ItemSPF.values()) {
						com500wanCodeList.add(item.getValue());
					}
					break;
				case BF:
					com500wanCodeList = new ArrayList<String>();
					for (ItemBF item : ItemBF.values()) {
						com500wanCodeList.add(item.getText());
					}
					break;
				}
				createForm.setCom500wanCodeList(com500wanCodeList);
			}
		}
	}
	
	
	public List<String> getSpss() {
		return spss;
	}

	public void setSpss(List<String> spss) {
		this.spss = spss;
	}
	// -------------------------------------------------------

	public JczqSchemeCreateForm getCreateForm() {
		return createForm;
	}

	public void setCreateForm(JczqSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.JCZQ;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public PlayTypeWeb getPlayTypeWeb() {
		return playTypeWeb;
	}

	public void setPlayTypeWeb(PlayTypeWeb playTypeWeb) {
		this.playTypeWeb = playTypeWeb;
	}

	public PassMode getPassMode() {
		return passMode;
	}

	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	public JczqUploadType getUploadType() {
		return uploadType;
	}

	public void setUploadType(JczqUploadType uploadType) {
		this.uploadType = uploadType;
	}


	/**
	 * 下面的代码将用于查看属性是否注入正常
	 */
//	public void addActionError(String anErrorMessage) {
//		String s = anErrorMessage;
//		System.out.println(s);
//	}
//
//	public void addActionMessage(String aMessage) {
//		String s = aMessage;
//		System.out.println(s);
//
//	}
//
//	public void addFieldError(String fieldName, String errorMessage) {
//		String s = errorMessage;
//		String f = fieldName;
//		System.out.println(s);
//		System.out.println(f);
//
//	}

}
