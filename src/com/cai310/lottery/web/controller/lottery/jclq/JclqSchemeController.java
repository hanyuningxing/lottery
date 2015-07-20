package com.cai310.lottery.web.controller.lottery.jclq;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.cache.JclqLocalCache;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.lottery.jclq.JclqSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqSchemeTemp;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeService;
import com.cai310.lottery.service.lottery.jclq.impl.JclqSchemeTempEntityManagerImpl;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqCompoundContent;
import com.cai310.lottery.support.jclq.JclqExtraItem;
import com.cai310.lottery.support.jclq.JclqExtraMatchItem;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqSimplePrizeWork;
import com.cai310.lottery.support.jclq.JclqSingleContent;
import com.cai310.lottery.support.jclq.JclqTicketCombination;
import com.cai310.lottery.support.jclq.JclqUploadType;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jclq.PrizeForecast;
import com.cai310.lottery.support.jclq.TicketItem;
import com.cai310.lottery.support.jclq.TicketItemSingle;
import com.cai310.lottery.support.jclq.TicketSplitCallback;
import com.cai310.lottery.support.jclq.TicketSplitCallbackSingle;
import com.cai310.lottery.support.jclq.JclqUtil;
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

@Namespace("/" + JclqConstant.KEY)
@Action(value = "scheme")
public class JclqSchemeController extends
		SchemeBaseController<JclqScheme, JclqSchemeDTO, JclqSchemeCreateForm, JclqSchemeUploadForm, JclqSchemeTemp> {
	private static final long serialVersionUID = 5783479221989581469L;

	private PlayType playType;

	private PassMode passMode;
	
	private JclqUploadType uploadType;
	
	/** 场次归属的日期 */
	private String matchKey;
	@Autowired
	private JclqSchemeService schemeService;

	@Autowired
	private JclqSchemeEntityManager schemeEntityManager;
	
	@Autowired
	private JclqSchemeTempEntityManagerImpl jclqSchemeTempEntityManagerImpl;

	@Autowired
	private JclqMatchEntityManager matchEntityManager;

	@Autowired
	private JclqLocalCache localCache;
	
	private List<String> spss = new ArrayList<String>();

	private String currentDate;

	/** 场次归属的日期 */
	private String matchDate;

	
	private static final int DAY_SIZE = 10;

	@Override
	protected SchemeService<JclqScheme, JclqSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected JclqSchemeEntityManager getSchemeEntityManager() {
		return schemeEntityManager;
	}
	
	@Override
	protected JclqSchemeTempEntityManagerImpl getSchemeTempEntityManager() {
		return jclqSchemeTempEntityManagerImpl;
	}

	// -------------------------------------------------------
	@Override
	public String list() {
//		if (playType == null)
//			playType = PlayType.SF;
//		if (passMode == null)
//			passMode = PassMode.PASS;
		List<DateTime> dateList = Lists.newArrayList();
		DateTime now = new DateTime();
		dateList.add(now);
		for (int i = 1; i < DAY_SIZE; i++) {
			dateList.add(now.plusDays(-i));
		}
		Struts2Utils.setAttribute("dateList", dateList);
		return super.list();
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
	
	@Override
	protected String doEditNew() throws Exception {
		List<DateTime> dateList = Lists.newArrayList();
		DateTime now = new DateTime();
		dateList.add(now);
		for (int i = 1; i < DAY_SIZE; i++) {
			dateList.add(now.plusDays(-i));
		}
		Struts2Utils.setAttribute("dateList", dateList);
		
		if (playType == null)
			playType = PlayType.SF;
		if (passMode == null)
			passMode = PassMode.PASS;
		
		Map<String, Item[]> itemMap = Maps.newLinkedHashMap();
		Map<String, PlayType> playMap = Maps.newLinkedHashMap();
		if(passMode.equals(PassMode.MIX_PASS)){
			this.playType = PlayType.MIX;
			for (PlayType playType : PlayType.values()) {
				if(playType.equals(PlayType.MIX))continue;
				itemMap.put(playType.name(), playType.getAllItems());
				if (playType == PlayType.SFC) {
					Map<String, Item[]> sfcItemMap = Maps.newLinkedHashMap();
					sfcItemMap.put("客胜", ItemSFC.GUESTS);
					sfcItemMap.put("主胜", ItemSFC.HOMES);
					Struts2Utils.setAttribute("sfcItemMap", sfcItemMap);
					Struts2Utils.setAttribute("BALANCES", ItemSFC.BALANCES);
					Struts2Utils.setAttribute("GUESTS", ItemSFC.GUESTS);
					Struts2Utils.setAttribute("HOMES", ItemSFC.HOMES);
				}else{
					Struts2Utils.setAttribute(playType.name()+"_itemArr",playType.getAllItems());
				}
				Struts2Utils.setAttribute(playType.name()+"_rateMap",localCache.getRateData(playType, PassMode.PASS));
				playMap.put(playType.name(), playType);
			}
		}else{
			if (playType == PlayType.SFC) {
				Map<String, Item[]> sfcItemMap = Maps.newLinkedHashMap();
				sfcItemMap.put("客胜", ItemSFC.GUESTS);
				sfcItemMap.put("主胜", ItemSFC.HOMES);
				Struts2Utils.setAttribute("sfcItemMap", sfcItemMap);
				Struts2Utils.setAttribute("BALANCES", ItemSFC.BALANCES);
				Struts2Utils.setAttribute("GUESTS", ItemSFC.GUESTS);
				Struts2Utils.setAttribute("HOMES", ItemSFC.HOMES);
			}
			itemMap.put(this.playType.name(), this.playType.getAllItems());
			playMap.put(this.playType.name(), this.playType);
		}
		Struts2Utils.setAttribute("itemMap",itemMap);
		Struts2Utils.setAttribute("playMap",playMap);
		
		List<JclqMatch> matchs = findMatchsOfCacheable();
		if (matchs != null && !matchs.isEmpty()) {
			List<String> games = Lists.newArrayList();
			Map<String, List<JclqMatch>> matchMap = Maps.newTreeMap();
			for (JclqMatch m : matchs) {
				if (StringUtils.isNotBlank(m.getGameName())) {
					if (!games.contains(m.getGameName()))
						games.add(m.getGameName());
				}
				DateTime dateTime = JclqUtil.getDateTime(m.getMatchDate());
				String key = dateTime.toString("yyyy年MM月dd日 E",Locale.SIMPLIFIED_CHINESE);

				List<JclqMatch> matchList = matchMap.get(key);
				if (matchList == null)
					matchList = Lists.newArrayList();
				matchList.add(m);
				matchMap.put(key, matchList);
			}
			Struts2Utils.setAttribute("games", games);
			Struts2Utils.setAttribute("matchMap", matchMap);

			Struts2Utils.setAttribute("rateData", localCache.getRateData(playType, passMode));
		}
		return super.doEditNew();
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
			playType = PlayType.RFSF;
		if (passMode == null)
			passMode = PassMode.PASS;

		Struts2Utils.setAttribute("itemArr", playType.getAllItems());
		if (playType == PlayType.SFC) {
			Map<String, Item[]> itemMap = Maps.newLinkedHashMap();
			itemMap.put("客胜", ItemSFC.GUESTS);
			itemMap.put("主胜", ItemSFC.HOMES);
			Struts2Utils.setAttribute("itemMap", itemMap);
			Struts2Utils.setAttribute("BALANCES", ItemSFC.BALANCES);
			Struts2Utils.setAttribute("GUESTS", ItemSFC.GUESTS);
			Struts2Utils.setAttribute("HOMES", ItemSFC.HOMES);
		}

		List<JclqMatch> matchs = findMatchsOfCacheable();
		if (matchs != null && !matchs.isEmpty()) {
			List<String> games = Lists.newArrayList();
			Map<String, List<JclqMatch>> matchMap = Maps.newTreeMap();
			for (JclqMatch m : matchs) {
				if (!m.isEnded())
					continue;
				if (StringUtils.isNotBlank(m.getGameName())) {
					if (!games.contains(m.getGameName()))
						games.add(m.getGameName());
				}

				DateTime dateTime = JclqUtil.getDateTime(m.getMatchDate());
				String key = dateTime.toString("yyyy-MM-dd E",Locale.SIMPLIFIED_CHINESE).replaceAll("星期", "周");

				List<JclqMatch> matchList = matchMap.get(key);
				if (matchList == null)
					matchList = Lists.newArrayList();
				matchList.add(m);
				matchMap.put(key, matchList);
			}
			Struts2Utils.setAttribute("games", games);
			Struts2Utils.setAttribute("matchMap", matchMap);

			Struts2Utils.setAttribute("rateData", localCache.getRateData(playType, passMode));
		}
		return "review";
	}
	
	protected List<JclqMatch> findMatchsOfCacheable() {
		List<JclqMatch> matchList  = null;
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

	@Override
	protected JclqSchemeDTO buildSchemeDTO() throws WebDataException {
		
		//根据单式上传的内容设置投注项到相关list中
		if(createForm.getMode()==SalesMode.SINGLE && createForm.isFileUpload()){
			supportOkoooAnd500wan();
		}else{
			if(createForm.confirmSchemePlayTypeAndPassMode()!=null){
				this.playType = createForm.confirmSchemePlayTypeAndPassMode();
			}
		}
		checkCreateForm();
		
		JclqSchemeDTO dto = super.buildSchemeDTO();
		
		dto.setPlayType(playType);
		
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
	protected JclqSchemeTemp buildSchemeTemp(JclqSchemeDTO schemeDTO) throws WebDataException{
		JclqSchemeTemp schemeTemp = super.buildSchemeTemp(schemeDTO);
		schemeTemp.setPassMode(schemeDTO.getPassMode());
		long passTypeValue = 0;
		for (PassType passType : schemeDTO.getPassTypes()) {
			passTypeValue |= passType.getValue();
		}
		schemeTemp.setPassType(passTypeValue);
		schemeTemp.setPlayType(schemeDTO.getPlayType());
		schemeTemp.setSchemeType(schemeDTO.getSchemeType());
		schemeTemp.setTicketContent(schemeDTO.getTicketContent());
		return schemeTemp;
	}
	
	/**
	 * 构建单式DTO
	 * @param dto
	 * @throws WebDataException
	 */
	protected void buildSchemeDTOofSINGLE(JclqSchemeDTO dto) throws WebDataException{
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
		
		JclqSingleContent singleContent = JsonUtil.getObject4JsonString(dto.getContent(), JclqSingleContent.class);
		List<Integer> multiples = singleContent.getMultiples();
		String[] contents = singleContent.converContent2Arr();
		Integer multiple = createForm.getMultiple();
		PassType passType = createForm.getPassTypes().get(0);		
		
		int maxMatchSize = playType.getMaxMatchSize();
		for(int i=0;i<contents.length;i++){
			if(singleContent.isOptimize()){
				multiple=multiples.get(i);
			}
			String content = contents[i];
			String[] itemArr = content.split(",");
			if(content.indexOf("#")<0 && itemArr.length>passType.getMatchCount()){
				JclqUtil.multiplePassSplitSingle(callback, multiple, maxMatchSize, passType, content, i);
			}else{
				JclqUtil.splitWithMultipleSingle(callback, multiple, passType, content, i);
			}			
		}
		dto.setTicketContent(ticketContentBuilder.toString());
	}
	
	/**
	 * 构建复式DTO
	 * @param dto
	 * @throws WebDataException
	 */
	protected void buildSchemeDTOofCOMPOUND(JclqSchemeDTO dto) throws WebDataException{
		Collections.sort(createForm.getItems());
		final StringBuilder ticketContentBuilder = new StringBuilder();
		TicketSplitCallback callback = new TicketSplitCallback() {
			@Override
			public void handle(List<JclqMatchItem> matchItemList, PassType passType, int multiple) {
				long matchFlag = JclqUtil.chg2flag(matchItemList, createForm.getItems());
				TicketItem ticketItem = new TicketItem(matchFlag, passType, multiple);
				ticketContentBuilder.append(ticketItem.toString()).append(TicketItem.ITEM_AND);
			}
		};
		dto.setPassMode(createForm.getPassMode());
		
		List<List<JclqMatchItem>> itemsListOfMix = null;//混合拆单小复式集合
		List<PassType> passTypeListOfMix = null;//混合拆单小复式的过关方式
		if(PlayType.MIX.equals(this.playType)){
			itemsListOfMix = Lists.newArrayList();
			passTypeListOfMix = Lists.newArrayList();
			List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();//场次胆(不同场次)
			List<JclqMatchItem> unDanList = new ArrayList<JclqMatchItem>();//场次非胆(不同场次)
			Map<String,List<JclqMatchItem>> matchItemMap = Maps.newHashMap();
			List<JclqMatchItem> matchItems = null;
			List<String> matchkeys = Lists.newArrayList();
			String matchkey = null;
			for(JclqMatchItem item: this.createForm.getItems()){
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
				List<List<JclqMatchItem>> itemsOfComp = JclqUtil.spliteMatchOfPlayType(passType.getMatchCount(), danList, unDanList, matchItemMap, this.createForm.getDanMinHit(), this.createForm.getDanMinHit());
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
			JclqUtil.singleSplit(callback, createForm.getMultiple(), createForm.getItems());
			break;
		case SIMPLE_PASS:
			if(PlayType.MIX.equals(this.playType)){
				for(int i=0;i<itemsListOfMix.size();i++){
					List<JclqMatchItem> items = itemsListOfMix.get(i);
					PassType passType = passTypeListOfMix.get(i);
					for(int passMatch : passType.getPassMatchs()){
						List<PassType> passTypes = PassType.findPassTypes(passMatch,passMatch);
						JclqUtil.undanMultiplePassSplit(callback, createForm.getMultiple(), passMatch, Lists.newArrayList(passTypes.get(0)), items);
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
				JclqUtil.splitWithMultiple(callback, createForm.getMultiple(), createForm.getPassTypes().get(0),createForm.getItems());
			}
			break;
		case MULTIPLE_PASS:
			if(PlayType.MIX.equals(this.playType)){
				for(int i=0;i<itemsListOfMix.size();i++){
					List<JclqMatchItem> items = itemsListOfMix.get(i);
					Integer maxMatchSize = null;
					for (JclqMatchItem item : items) {
						if(maxMatchSize==null || maxMatchSize>item.getPlayType().getMaxMatchSize())
							maxMatchSize = item.getPlayType().getMaxMatchSize();
					}
					JclqUtil.undanMultiplePassSplit(callback, createForm.getMultiple(), maxMatchSize, Lists.newArrayList(passTypeListOfMix.get(i)), items);
				}
			}else{
				final List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();
				final List<JclqMatchItem> undanList = new ArrayList<JclqMatchItem>();	
				for (JclqMatchItem item : createForm.getItems()) {
					if (item.isDan())
						danList.add(item);
					else
						undanList.add(item);
				}
				if (danList.size() == 0) {
					JclqUtil.undanMultiplePassSplit(callback, createForm.getMultiple(), playType.getMaxMatchSize(), createForm.getPassTypes(), createForm.getItems());
				} else {
					Integer danMinHit = createForm.getDanMinHit();
					Integer danMaxHit = createForm.getDanMaxHit();
					JclqUtil.danMultiplePassSplit(callback, createForm.getMultiple(), createForm.getPassTypes(), danList, undanList, danMinHit, danMaxHit);
				}
			}
			break;
		default:
			throw new WebDataException("过关方式不合法");
		}

//		List<String> matchKeyList = new ArrayList<String>();
//		for (JclqMatchItem item : createForm.getItems()) {
//			matchKeyList.add(item.getMatchKey());
//		}
//		List<JclqMatch> matchList = matchEntityManager.findMatchs(matchKeyList);
//		Map<String,JclqMatch> matchMap = Maps.newHashMap();
//		for (JclqMatch jclqMatch : matchList) {
//			matchMap.put(jclqMatch.getMatchKey(), jclqMatch);
//		}
//		JclqMatch match = null;
//		for (JclqMatchItem item : createForm.getItems()) {
//			try {
//				match = matchMap.get(item.getMatchKey());
//				if(match==null){
//					throw new WebDataException("没有找到相关的场次{"+item.getMatchKey()+"}");
//				}
//				if (!match.isOpen(item.getPlayType(), PassMode.PASS)){
//					throw new WebDataException("场次{"+match.getMatchKey()+"}玩法{"+item.getPlayType().getText()+"}没有开售.");
//				}
//				if (match.isStop()){
//					throw new WebDataException("场次{"+match.getMatchKey()+"}已经停止销售.");
//				}
//			} catch (DataException e) {
//				throw new WebDataException("场次{"+match.getMatchKey()+"}获取比赛时间出错，无法判断是否已经停止销售.");
//			}
//		}
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
		switch (createForm.getSchemeType()) {
		case SINGLE:
			if (createForm.getItems().size() > JclqConstant.SINGLE_MAX_MATCH_SIZE)
				throw new WebDataException("单关模式不能选择超过" + JclqConstant.SINGLE_MAX_MATCH_SIZE + "场.");

			if (createForm.getPassTypes() == null || createForm.getPassTypes().size() != 1
					|| createForm.getPassTypes().get(0) != PassType.P1)
				throw new WebDataException("过关方式不正确.");
			break;
		case SIMPLE_PASS:
			if(this.playType.equals(PlayType.MIX)){
				for (JclqMatchItem jclqMatchItem : createForm.getItems()) {
					if(null==maxMatchSize||maxMatchSize>jclqMatchItem.getPlayType().getMaxMatchSize())
						maxMatchSize = jclqMatchItem.getPlayType().getMaxMatchSize();
				}
			}else{
				maxMatchSize = playType.getMaxMatchSize();
			}
			if (createForm.getItems().size() > maxMatchSize)
			if (createForm.getItems().size() > playType.getMaxMatchSize())
				throw new WebDataException("[" + playType.getText() + "]普通过关选择不能超过" + playType.getMaxMatchSize() + "场.");

			if (createForm.getPassTypes().size() > 1)
				throw new WebDataException("普通过关模式只能选择一个过关方式.");

			PassType passType = createForm.getPassTypes().get(0);
			if (passType == PassType.P1)
				throw new WebDataException("过关模式不能选择单关过关方式.");
			if (passType.getMatchCount() != createForm.getItems().size())
				throw new WebDataException("选择的场次数目与过关方式不匹配.");
			break;
		case MULTIPLE_PASS:
			for (PassType type : createForm.getPassTypes()) {
				if (type.getUnits() != 1)
					throw new WebDataException("多选过关模式不支持[" + type.getText() + "]过关.");
				if(this.playType.equals(PlayType.MIX)){
					maxMatchSize = null;
					for (JclqMatchItem jclqMatchItem : createForm.getItems()) {
						if(null==maxMatchSize||maxMatchSize>jclqMatchItem.getPlayType().getMaxMatchSize())
							maxMatchSize = jclqMatchItem.getPlayType().getMaxMatchSize();
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
	protected JclqSchemeCreateForm supplementCreateFormData() throws WebDataException{
		this.createForm = super.supplementCreateFormData();
		if(SalesMode.SINGLE.equals(this.createForm.getMode())){
			String schemeIdStr = Struts2Utils.getParameter("tempSchemeId");
			if(schemeIdStr==null){
				throw new WebDataException("操作的方案标识为空.");
			}
			Long schemeId = Long.valueOf(schemeIdStr);
			this.schemeTemp = jclqSchemeTempEntityManagerImpl.getScheme(schemeId);
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
		if (this.uploadType==JclqUploadType.SELECT_MATCH && (createForm.getMatchKeys() == null || createForm.getMatchKeys().isEmpty()))
			throw new WebDataException("未选择投注场次.");
		if(this.uploadType==JclqUploadType.AHEAD){
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
			JclqCompoundContent compoundContent = schemeTemp.getCompoundContent(); 
			firstMatchTime = doShowCompound(compoundContent);
			break;
		case SINGLE:
			JclqSingleContent singleContent= this.schemeTemp.getSingleContent();
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
			JclqCompoundContent compoundContent = scheme.getCompoundContent(); 
			firstMatchTime = doShowCompound(compoundContent);
			break;
		case SINGLE:
			JclqSingleContent singleContent= this.scheme.getSingleContent();
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
	
	private Date doShowCompound(JclqCompoundContent compoundContent){
		List<String> matchKeys = new ArrayList<String>();
		Date firstMatchTime = null;
		List<JclqMatch> matchs = null;
		List<Object[]> selectedMatchList = Lists.newArrayList();//内容选项List
		Map<String,List<JclqMatchItem>> matchItemsOfMatch = Maps.newLinkedHashMap();
		List<JclqMatchItem> matchItemsOfMap = null;
		//方案选择内容信息Object[0]=赛程、Object[1]=选项
		Object[] selectedInfo = null;
		
		matchItemsOfMatch = Maps.newLinkedHashMap();
		for(JclqMatchItem item : compoundContent.getItems()){
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
		for (JclqMatch jclqMatch : matchs) {
			if (firstMatchTime == null || firstMatchTime.after(jclqMatch.getMatchTime())){
				firstMatchTime = jclqMatch.getMatchTime();
			}
			selectedInfo = new Object[2];
			selectedInfo[0] = jclqMatch;
			selectedInfo[1] = matchItemsOfMatch.get(jclqMatch.getMatchKey());
			selectedMatchList.add(selectedInfo);
		}
		Struts2Utils.setAttribute("selectedMatchList", selectedMatchList);
		return firstMatchTime;
	}
	
	private Date doShowSingle(JclqSingleContent singleContent){

		//以下为优化方案的显示方式，单式的则需要另外处理
		List<String> matchKeys = new ArrayList<String>();
		Date firstMatchTime = null;
		List<JclqMatch> matchs = null;
		List<Object[]> selectedMatchList = Lists.newArrayList();//内容选项List
		Map<String,List<JclqMatchItem>> matchItemsOfMatch = Maps.newLinkedHashMap();
		List<JclqMatchItem> matchItemsOfMap = null;
		//方案选择内容信息Object[0]=赛程、Object[1]=选项
		Object[] selectedInfo = null;
		
		matchKeys = singleContent.getMatchkeys();
		matchs = matchEntityManager.findMatchs(matchKeys);
		List<JclqMatchItem> matchItems = singleContent.getItems();
		matchItemsOfMatch = Maps.newLinkedHashMap();
		for(JclqMatchItem item : matchItems){
			matchItemsOfMap = matchItemsOfMatch.get(item.getMatchKey());
			if(matchItemsOfMap==null){
				matchItemsOfMap = Lists.newArrayList();
				matchItemsOfMatch.put(item.getMatchKey(), matchItemsOfMap);
			}
			matchItemsOfMap.add(item);
		}
		for (JclqMatch jclqMatch : matchs) {
			if (firstMatchTime == null || firstMatchTime.after(jclqMatch.getMatchTime())){
				firstMatchTime = jclqMatch.getMatchTime();
			}
			selectedInfo = new Object[2];
			selectedInfo[0] = jclqMatch;
			selectedInfo[1] = matchItemsOfMatch.get(jclqMatch.getMatchKey());
			selectedMatchList.add(selectedInfo);
		}
		Struts2Utils.setAttribute("selectedMatchList", selectedMatchList);
		return firstMatchTime;
	}

	@Override
	public String canViewDetail(JclqScheme scheme, Period period, User user) {
		if (user != null && user.getId().equals(scheme.getSponsorId()))
			return "true";
		switch (scheme.getSecretType()) {
		case FULL_PUBLIC:
			return "true";
		case DRAWN_PUBLIC:
			if (period.isDrawed() && scheme.isUpdateWon())
				return "true";
			else
				return SecretType.DRAWN_PUBLIC.getSecretName();
		}
		return "方案保密";
	}

	@Override
	public String myList() {
		if (playType == null)
			playType = PlayType.SF;
		return super.myList();
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
	
	
	public String viewTicketTempCombination(){
		String returnString = "ticketCombination";
		if(null!=Struts2Utils.getParameter("type")){
			///单式
			returnString="ticketCombination-single";
		}
		try {
			if (this.id != null)
				this.schemeTemp = jclqSchemeTempEntityManagerImpl.getScheme(this.id);
			
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
		
			List<JclqMatchItem> matchItemList = null;
			List<String> matchkeyList = Lists.newArrayList();
			//单复式分别获取
			SalesMode salesMode = this.schemeTemp.getMode();
			JclqSingleContent single = null;
			List<TicketItem> ticketList = null;
			List<TicketItemSingle> ticketSingleList = null;
			int ticketCount = 0;
			switch(salesMode){
			case COMPOUND:
				JclqCompoundContent compound = this.schemeTemp.getCompoundContent();
				matchItemList = compound.getItems();
				for (JclqMatchItem matchItem : matchItemList) {
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
			List<JclqMatch> matchList = matchEntityManager.findMatchs(matchkeyList);
			
			Map<String, JclqMatch> matchMap = Maps.newHashMap();
			for (JclqMatch match : matchList) {
				String matchKey = match.getMatchKey();
				matchMap.put(matchKey, match);
			}
			Struts2Utils.getRequest().setAttribute("matchMap", matchMap);
			
			// 拆票信息
			pagination.setPageSize(5);
			pagination.setTotalCount(ticketCount);
			List<JclqTicketCombination> resultList = new ArrayList<JclqTicketCombination>();
			List<JclqMatchItem> itemList = null;
			int count = 0;
			PassType passType = null;
			int multiple = 0;
			playType = this.schemeTemp.getPlayType();
			for (int i = 0; i < pagination.getTotalCount(); i++) {
				if (count >= pagination.getFirst()) {
					itemList = new ArrayList<JclqMatchItem>();
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
							JclqMatchItem matchItem = new JclqMatchItem();
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
					
					JclqTicketCombination comb = new JclqTicketCombination(this.schemeTemp.getPassMode(),this.schemeTemp.getPlayType(), itemList,
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
		
			List<JclqMatchItem> matchItemList = null;
			List<String> matchkeyList = Lists.newArrayList();
			//单复式分别获取
			SalesMode salesMode = this.scheme.getMode();
			JclqSingleContent single = null;
			List<TicketItem> ticketList = null;
			List<TicketItemSingle> ticketSingleList = null;
			int ticketCount = 0;
			switch(salesMode){
			case COMPOUND:
				JclqCompoundContent compound = this.scheme.getCompoundContent();
				matchItemList = compound.getItems();
				for (JclqMatchItem matchItem : matchItemList) {
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
			List<JclqMatch> matchList = matchEntityManager.findMatchs(matchkeyList);
			Map<String, JclqMatch> matchMap = Maps.newHashMap();
			for (JclqMatch match : matchList) {
				String matchKey = match.getMatchKey();
				matchMap.put(matchKey, match);
			}
			Struts2Utils.getRequest().setAttribute("matchMap", matchMap);

			
			// 出票信息
			List<Map<String, Map<String, Double>>> printAwardList = this.scheme.getPrintAwardList();
			pagination.setPageSize(5);
			pagination.setTotalCount(ticketCount);
			List<JclqTicketCombination> resultList = new ArrayList<JclqTicketCombination>();
			List<JclqMatchItem> itemList = null;
			int count = 0;
			PassType passType = null;
			int multiple = 0;
			playType = this.scheme.getPlayType();
			for (int i = 0; i < pagination.getTotalCount(); i++) {
				boolean won = false;
				if (count >= pagination.getFirst()) {
					itemList = new ArrayList<JclqMatchItem>();
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
							JclqMatchItem matchItem = new JclqMatchItem();
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
						
						for (JclqMatchItem matchItem : itemList) {
							String matchKey = matchItem.getMatchKey();
							JclqMatch match = matchMap.get(matchKey);

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
							JclqSimplePrizeWork prizeWork = new JclqSimplePrizeWork(this.scheme.getPassMode(), multiple, passType, correctList);
							totalPrizeAfterTax = prizeWork.getTotalPrizeAfterTax();
						}
					}
					won = totalPrizeAfterTax != null && totalPrizeAfterTax > 0;
					if (!onlyWon || won) {
						JclqTicketCombination comb = new JclqTicketCombination(this.scheme.getPassMode(),this.scheme.getPlayType(), itemList,
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
			List<JclqMatchItem> items = createForm.getItems();
			if (items == null || items.isEmpty())
				throw new WebDataException("场次内容为空.");
			if (createForm.getPassTypes() == null || createForm.getPassTypes().isEmpty())
				throw new WebDataException("过关方式为空.");
			for (PassType passType : createForm.getPassTypes()) {
				if (passType.getUnits() > 1 && createForm.getPassTypes().size() > 1)
					throw new WebDataException("过关方式不正确.");
			}			

			List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();//场次胆(不同场次)
			List<JclqMatchItem> unDanList = new ArrayList<JclqMatchItem>();//场次非胆(不同场次)
			Map<String,List<JclqMatchItem>> matchItemMap = Maps.newHashMap();
			List<JclqMatchItem> matchItems = null;
			List<String> matchkeys = Lists.newArrayList();
			String matchkey = null;
			for(JclqMatchItem item : items){
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
			for(JclqMatchItem matchItem : danList){
				danOfMatchMap.put(matchItem.getMatchKey(), matchItem.isDan());
			}
			Struts2Utils.setAttribute("danOfMatchMap", danOfMatchMap);
			Struts2Utils.setAttribute("matchItemMap", matchItemMap);
			
			for (JclqMatchItem matchItem : items) {
				if(matchItem.getSps()==null || matchItem.getSps().isEmpty()){
					throw new WebDataException(matchItem.getMatchKey() + "场次SP数据为空.");
				}
			}
			
			List<JclqMatch> matchList = matchEntityManager.findMatchs(matchkeys);
			Collections.sort(matchList, new Comparator<JclqMatch>() {
				public int compare(JclqMatch o1, JclqMatch o2) {
					return o1.getMatchKey().compareTo(o2.getMatchKey());			
				}
			});
			if (matchList == null || matchList.size() != matchkeys.size())
				throw new WebDataException("找不到对应场次的赛事.");
			Struts2Utils.setAttribute("matchs", matchList);
						
			//各场次最小及各玩法的最大赔率
			Map<String,Object[]> minMaxSpOfMatchMap = Maps.newHashMap();
			Map<PlayType,Double> maxSpOfPlayTypeMap = null;
			Iterator<Entry<String,List<JclqMatchItem>>> itorItemsOfMatch = matchItemMap.entrySet().iterator();
			while(itorItemsOfMatch.hasNext()){
				Entry<String,List<JclqMatchItem>> itemsOfMatchEntry = itorItemsOfMatch.next();
				List<JclqMatchItem> itemsOfMatch = itemsOfMatchEntry.getValue();
				Object[] minMaxSpInfo = new Object[2];
				double minSp = 0;
				maxSpOfPlayTypeMap = Maps.newHashMap();
				for(JclqMatchItem item : itemsOfMatch){
					double[] minMaxSp = null;
					minMaxSp = item.findSelectedMinMaxSp();
					if(minSp==0 || minMaxSp[0]<minSp){
						minSp = minMaxSp[0];
					}
					maxSpOfPlayTypeMap.put(item.getPlayType(), minMaxSp[1]);
				}
				minMaxSpInfo[0] = minSp;
				minMaxSpInfo[1] = maxSpOfPlayTypeMap;
				minMaxSpOfMatchMap.put(itemsOfMatchEntry.getKey(), minMaxSpInfo);
			}
			
			PrizeForecast prizeForecast = new PrizeForecast(createForm.getMultiple(), matchItemMap, createForm.getPassTypes(), danList, unDanList, createForm.getDanMinHit(), createForm.getDanMaxHit(), minMaxSpOfMatchMap);
			
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
				case SF:					
				case RFSF:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemSF item : ItemSF.values()) {
						comOkoooCodeList.add(item.getValue());
					}
					break;
				case SFC:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemSFC item : ItemSFC.values()) {
						comOkoooCodeList.add(item.getText());
					}
					break;
				case DXF:
					comOkoooCodeList = new ArrayList<String>();
					for (ItemDXF item : ItemDXF.values()) {
						comOkoooCodeList.add(item.getText());
					}
					break;
				}
				createForm.setComOkoooCodeList(comOkoooCodeList);
			} else if (singleContent.indexOf(":[") > 0) {
				List<String> com500wanCodeList = null;
				switch (playType) {
				case SF:
					com500wanCodeList = new ArrayList<String>();
					for (ItemSF item : ItemSF.values()) {
						com500wanCodeList.add(item.getValue());
					}
					break;
				case DXF:
					com500wanCodeList = new ArrayList<String>();
					for (ItemDXF item : ItemDXF.values()) {
						com500wanCodeList.add(item.getText());
					}
					break;
				}
				createForm.setCom500wanCodeList(com500wanCodeList);
			}
		}
	}
	
	
	// -------------------------------------------------------

	public JclqSchemeCreateForm getCreateForm() {
		return createForm;
	}

	public void setCreateForm(JclqSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.JCLQ;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
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
	
	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	public List<String> getSpss() {
		return spss;
	}

	public void setSpss(List<String> spss) {
		this.spss = spss;
	}
	
	public JclqUploadType getUploadType() {
		return uploadType;
	}

	public void setUploadType(JclqUploadType uploadType) {
		this.uploadType = uploadType;
	}

}
