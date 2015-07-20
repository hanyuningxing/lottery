package com.cai310.lottery.web.controller.admin.info;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.event.NewsInfoUpdateEvent;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoTitleColor;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.MobileInfoType;
import com.cai310.lottery.entity.info.MobileNewsData;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.info.Rule;
import com.cai310.lottery.entity.info.TagsInfoData;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.info.NewsInfoDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.lottery.web.controller.info.InfoBeanForm;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.spring.SpringContextHolder;
import com.cai310.utils.Struts2Utils;
import com.cai310.utils.TemplateGenerator;
import com.google.common.collect.Maps;

public class NewsController extends AdminBaseController {
	private static final long serialVersionUID = -7128890110025210382L;
	@SuppressWarnings("rawtypes")
	private Map<Lottery, KenoManager> kenoManagerMap = Maps.newHashMap();
	@SuppressWarnings("rawtypes")
	private KenoManager getKenoManager(Lottery lotteryType) {
		return kenoManagerMap.get(lotteryType);
	}
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setKenoManagerList(List<KenoManager> kenoManagerList) {
		for (KenoManager manager : kenoManagerList) {
			kenoManagerMap.put(manager.getLottery(), manager);
		}
	}
	@Autowired
	UserEntityManager userManager;
	@Autowired
	private QueryService queryService;
	@Autowired
	protected PeriodEntityManager periodManager;
	/** 新闻 **/
	@Resource
	private NewsInfoDataEntityManager newsInfoDataEntityManager;
	//@Autowired
	//private NewsInfoBuilder newsInfoBuilder;
	
	private Lottery lotteryType;

	private InfoType infoType;

	private InfoSubType subType;

	private Integer isNotice;
	
	private List<TagsInfoData> tagsInfoDatas;
	
	private String channelNoticeNews;
	
	public String getChannelNoticeNews() {
		return channelNoticeNews;
	}
	public void setChannelNoticeNews(String channelNoticeNews) {
		this.channelNoticeNews = channelNoticeNews;
	}

	@Autowired
	@Qualifier("commonEternalCache")
	private Cache commonEternalCache;

	public Cache getCommonEternalCache() {
		return commonEternalCache;
	}
	public void setCommonEternalCache(Cache commonEternalCache) {
		this.commonEternalCache = commonEternalCache;
	}
	
	public String buildChannelNoticeNews() {
		String key = lotteryType+ Constant.channelNoticeNewsCacheKey;
		Element el = commonEternalCache.get(key);
		el = new Element(key, this.channelNoticeNews);
		commonEternalCache.put(el);
		el.getValue();
		commonEternalCache.flush() ;
		return channelNoticeNewsList();
	}
	
	public String channelNoticeNewsList() {
		Map<String,String> map = new HashMap<String,String> ();
		for(Lottery lottery:Lottery.values()){
			String key = lottery+ Constant.channelNoticeNewsCacheKey;
			Element el = commonEternalCache.get(key);
			if(null!=el) {
			channelNoticeNews = (String)el.getValue();
			//刷新到磁盘
			commonEternalCache.flush() ;
			map.put(lottery.toString(), channelNoticeNews);
			}
		}
		Struts2Utils.setAttribute("noticeNewsMap", map);
		return "channel-notice-news-list";
	}
	
	public String deleteChannelNoticeNews() {
		String key = lotteryType+ Constant.channelNoticeNewsCacheKey;
		commonEternalCache.remove(key);
		commonEternalCache.flush() ;
		return channelNoticeNewsList();
	}
	
	public Integer getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(Integer isNotice) {
		this.isNotice = isNotice;
	}

	public InfoSubType getSubType() {
		return subType;
	}

	public void setSubType(InfoSubType subType) {
		this.subType = subType;
	}

	private List<InfoSubType> infoTypeList;

	private Pagination pagination = new Pagination(20);

	private String periodNumber;

	private String createContent;

	private InfoBeanForm infoBeanForm;

	private Long id;

	private InfoState state;

	public String changeState() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new DataException("权限不足！");
			}
			if (null == id) {
				throw new DataException("缺失Id！");
			}
			if (null == state) {
				throw new DataException("缺失状态！");
			}
			NewsInfoData newsInfoData = newsInfoDataEntityManager.getNewsInfoData(id);
			newsInfoData.setState(state);
			newsInfoDataEntityManager.saveNewsInfoData(newsInfoData, adminUser);
			map.put("success", true);
			map.put("msg", "修改成功");
			createNewIndexFile();
		} catch (DataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		Struts2Utils.renderJson(map);
		return null;
	}

	/**
	 * 取得子类型
	 * 
	 * @return
	 */
	public String selectInfoSubType() {
		Map<String, Object> data = new HashMap<String, Object>();
		infoTypeList = InfoSubType.getInfoSubTypeBy(infoType, lotteryType);
		List tagsInfoDatas = newsInfoDataEntityManager.loadTagsInfoDatas(-1, lotteryType);

		data.put("infoTypeList", buildInfoSubType(infoTypeList));
		data.put("tagsInfoDatas",buildTagsType(tagsInfoDatas));

		Struts2Utils.renderJson(data);
		return null;
	}
	/**
	 * 取得手机彩钟
	 * 
	 * @return
	 */
	public String selectMobileSubType() {
		Map<String, Object> data = new HashMap<String, Object>();
		List periodList = new ArrayList();
		if(lotteryType.getCategory().equals(LotteryCategory.FREQUENT)){
			KenoPeriod kenoPeriod = this.getKenoManager(lotteryType).getCurrentData();
			if(null!=kenoPeriod){
				Map<String, Object> infoTypeMap = new HashMap<String, Object>();
				infoTypeMap.put("obj", kenoPeriod.getId());
				infoTypeMap.put("name", kenoPeriod.getPeriodNumber());
				periodList.add(infoTypeMap);
			}
		}else{
			List<Period> list = periodManager.findCurrentPeriods(lotteryType, false);
			for (Period period : list) {
					Map<String, Object> infoTypeMap = new HashMap<String, Object>();
					infoTypeMap.put("obj", period.getId());
					infoTypeMap.put("name", period.getPeriodNumber());
					periodList.add(infoTypeMap);
			}
		}
		if(lotteryType.equals(Lottery.PL)){
			List tyepList = new ArrayList();
			Map<String, Object> infoTypeMap = new HashMap<String, Object>();
			infoTypeMap.put("obj", "0");
			infoTypeMap.put("name", "排列三");
			tyepList.add(infoTypeMap);
			infoTypeMap = new HashMap<String, Object>();
			infoTypeMap.put("obj", "1");
			infoTypeMap.put("name", "排列五");
			tyepList.add(infoTypeMap);

			data.put("tyepList",tyepList);
		}
		data.put("periodList", periodList);
		Struts2Utils.renderJson(data);
		return null;
	}

	private List buildInfoSubType(List<InfoSubType> infoTypeList) {
		List list = new ArrayList();
		for (InfoSubType infoSubType : infoTypeList) {
			Map<String, Object> infoTypeMap = new HashMap<String, Object>();
			infoTypeMap.put("obj", infoSubType);
			infoTypeMap.put("name", infoSubType.getTypeName());
			list.add(infoTypeMap);
		}
		return list;
	}
	private List buildTagsType(List<TagsInfoData> tagsInfoDatas) {
		List list = new ArrayList();
		for (TagsInfoData tag : tagsInfoDatas) {
			Map<String, Object> tagsTypeMap = new HashMap<String, Object>();
			tagsTypeMap.put("obj", tag.getId()+":"+tag.getTags());
			tagsTypeMap.put("name", tag.getTags());
			list.add(tagsTypeMap);
		}
		return list;
	}
	public String newsInfoList() {
		XDetachedCriteria criteria = new XDetachedCriteria(NewsInfoData.class, "m");
		if (null != this.getInfoType()) {
			criteria.add(Restrictions.eq("m.type", this.getInfoType()));
		}
		if (null != this.getLotteryType()) {
			criteria.add(Restrictions.eq("m.lotteryType", this.getLotteryType()));
		}
		if (null != this.isNotice) {
			criteria.add(Restrictions.eq("m.isNotice", this.getIsNotice()));
		}
		criteria.addOrder(Order.asc("m.level"));
		criteria.addOrder(Order.desc("m.id"));
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "list";
	}
	public String ruleList() {
		XDetachedCriteria criteria = new XDetachedCriteria(Rule.class, "m");
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "rule-list";
	}
	private Map<String,String> playTypeMap;
	private String playType;
	private String ruleStr;
	public String editRule() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == this.getLotteryType()) {
			this.setLotteryType(Lottery.SSQ);
		}
		this.setPlayTypeMap(this.getPlayTypeBy(lotteryType));
		Rule rule = null;
		if(null==id){
			//新建
			rule = new Rule();
		}else{
			rule = newsInfoDataEntityManager.getRule(Long.valueOf(id));
		}
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
			this.playType=""+rule.getPlayType();
			this.setRuleStr(rule.getRule());
			this.setLotteryType(rule.getLotteryType());
			if(null!=lotteryType){
				this.setPlayTypeMap(this.getPlayTypeBy(lotteryType));
			}
			this.setId(rule.getId());
		    return "rule-edit";
		}
		try{
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new DataException("权限不足！");
			}
			if (StringUtils.isBlank(ruleStr)) {
					throw new WebDataException("内容为空");
			}
			rule.setPlayType(Integer.valueOf(playType));
			rule.setLotteryType(lotteryType);
			rule.setRule(ruleStr);
			newsInfoDataEntityManager.saveRule(rule, adminUser);
			addActionError("操作成功");
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		return ruleList();
	}
	/**
	 * 取得玩法
	 * 
	 * @return
	 */
	public String selectPlayType() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("playTypeList", getPlayTypeBy(this.lotteryType));
		
		Struts2Utils.renderJson(data);
		return null;
	}
	public static Map<String,String> getPlayTypeBy(Lottery lottery){
		return new Rule().getPlayTypeBy(lottery);
	}
	public String edit() {
		if (null == this.getInfoType()) {
			this.setInfoType(InfoType.FORECAST);
		}
		if (null == this.getLotteryType()) {
			this.setLotteryType(Lottery.SSQ);
		}
		infoTypeList = InfoSubType.getInfoSubTypeBy(infoType, lotteryType);
		tagsInfoDatas = newsInfoDataEntityManager.loadTagsInfoDatas(-1,lotteryType);

		return "edit";
	}

	public String loadNewsInfo() {
		if (null == infoBeanForm || null == infoBeanForm.getId()) {
			return newsInfoList();
		}
		NewsInfoData newsInfoData = newsInfoDataEntityManager.getNewsInfoData(infoBeanForm.getId());
		if (null == newsInfoData) {
			addActionError("实体为空");
			return newsInfoList();
		}
		String content = newsInfoData.getContent();
		
		
		infoBeanForm.setLastModifyTime(new Date());
		infoBeanForm.setContent(content);
		infoBeanForm.setLevel(newsInfoData.getLevel());
		infoBeanForm.setState(newsInfoData.getState());
		infoBeanForm.setTitle(newsInfoData.getTitle());
		infoBeanForm.setShortTitle(newsInfoData.getShortTitle());
		infoBeanForm.setTitleLink(newsInfoData.getTitleLink());
		infoBeanForm.setTitleType(newsInfoData.getTitleType());
		infoBeanForm.setTitleTypeLink(newsInfoData.getTitleTypeLink());
		infoBeanForm.setType(newsInfoData.getType());
		infoBeanForm.setLotteryType(newsInfoData.getLotteryType());
		infoBeanForm.setClickNum(newsInfoData.getClickNum());
		infoBeanForm.setAuthor(newsInfoData.getAuthor());
		infoBeanForm.setKeywords(newsInfoData.getKeywords());
		infoBeanForm.setDescription(newsInfoData.getDescription());
		infoBeanForm.setTitleColor(null == newsInfoData.getTitleColor() ? InfoTitleColor.BLACK : newsInfoData
				.getTitleColor());
		infoBeanForm.setSubType(newsInfoData.getSubType());
		infoBeanForm.setIsNotice(newsInfoData.getIsNotice());
		//infoBeanForm.setTags(null==newsInfoData.getTags()?"":newsInfoData.getTags());
		if(null!=newsInfoData.getTags()){
			String[] tags = newsInfoData.getTags().trim().split("#");
			if(tags.length==1){
				infoBeanForm.setTags1(tags[0]);
			}else if(tags.length==2){
				infoBeanForm.setTags1(tags[0]);
				infoBeanForm.setTags2(tags[1]);
			}else if(tags.length==3){
				infoBeanForm.setTags1(tags[0]);
				infoBeanForm.setTags2(tags[1]);
				infoBeanForm.setTags3(tags[2]);
			}
		}
		
		
		infoTypeList = InfoSubType.getInfoSubTypeBy(newsInfoData.getType(), newsInfoData.getLotteryType());
		this.setInfoType(newsInfoData.getType());
		
		
		tagsInfoDatas = newsInfoDataEntityManager.loadTagsInfoDatas(-1,newsInfoData.getLotteryType());
		
		return "edit";
	}

	public String newsInfoSave() {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new DataException("权限不足！");
			}
			if (null == infoBeanForm) {
				throw new DataException("消息实体为空.");
			}
			if (StringUtils.isBlank(infoBeanForm.getTitle())) {
				throw new WebDataException("标题为空");
			}
			if (StringUtils.isBlank(infoBeanForm.getContent())) {
				if (StringUtils.isBlank(infoBeanForm.getTitleLink())) {
					throw new WebDataException("内容为空");
				}
			}
			infoBeanForm.setTitle(htmlEncode(infoBeanForm.getTitle()));
			if (null == infoBeanForm.getType()) {
				throw new WebDataException("类型为空");
			}
			if (null == infoBeanForm.getState()) {
				infoBeanForm.setState(InfoState.CHECKING);
			}
			 
			infoBeanForm.setAuthor(adminUser.getLoginName());
			
			if (null == infoBeanForm.getLevel()) {
				throw new WebDataException("首页显示为空");
			}
			String content = null==infoBeanForm.getContent()?"":infoBeanForm.getContent();
			String regx = "<a.*?_mce_href=\"(.*?)\".*?>";
			Pattern p = Pattern.compile(regx);	
			Matcher m = p.matcher(content);
			while(m.find()){
				content = content.replaceAll("_mce_href=\""+m.group(1)+"\"", "");
			}
			if (null == infoBeanForm.getId() || Long.valueOf("-1").equals(infoBeanForm.getId())) {
				// 新建
				NewsInfoData infoBeanFormTemp = new NewsInfoData();
				infoBeanFormTemp.setLastModifyTime(new Date());
				infoBeanFormTemp.setContent(content); 
				infoBeanFormTemp.setLevel(infoBeanForm.getLevel());
				infoBeanFormTemp.setState(infoBeanForm.getState());
				infoBeanFormTemp.setTitle(infoBeanForm.getTitle());
				infoBeanFormTemp.setShortTitle(infoBeanForm.getShortTitle());
				infoBeanFormTemp.setTitleLink(infoBeanForm.getTitleLink());
				// infoBeanFormTemp.setTitleType(infoBeanForm.getTitleType());
				// infoBeanFormTemp.setTitleTypeLink(infoBeanForm.getTitleTypeLink());
				infoBeanFormTemp.setTitleColor(null == infoBeanForm.getTitleColor() ? InfoTitleColor.BLACK
						: infoBeanForm.getTitleColor());
				infoBeanFormTemp.setSubType(infoBeanForm.getSubType());
				infoBeanFormTemp.setType(infoBeanForm.getType());
				infoBeanFormTemp.setLotteryType(infoBeanForm.getLotteryType());
				infoBeanFormTemp.setAuthor(htmlEncode(infoBeanForm.getAuthor()));
				infoBeanFormTemp.setClickNum(Long.valueOf(0));
				infoBeanFormTemp.setKeywords(htmlEncode(infoBeanForm.getKeywords()));
				infoBeanFormTemp.setDescription(htmlEncode(infoBeanForm.getDescription()));
				infoBeanFormTemp.setIsNotice(infoBeanForm.getIsNotice());
				//infoBeanFormTemp.setTags(infoBeanForm.getTags());
//				String tag = "";
//				if(StringUtils.isNotBlank(infoBeanForm.getTags1())&&StringUtils.isNotEmpty(infoBeanForm.getTags1()))
//					tag+=infoBeanForm.getTags1()+"#";
//				if(StringUtils.isNotBlank(infoBeanForm.getTags2())&&StringUtils.isNotEmpty(infoBeanForm.getTags2()))
//					tag+=infoBeanForm.getTags2()+"#";
//				if(StringUtils.isNotBlank(infoBeanForm.getTags3())&&StringUtils.isNotEmpty(infoBeanForm.getTags3()))
//					tag+=infoBeanForm.getTags3()+"#";
//				if(StringUtils.isNotBlank(tag)&&StringUtils.isNotEmpty(tag))
//					infoBeanFormTemp.setTags(tag.trim().substring(0,tag.length()-1));
				newsInfoDataEntityManager.saveNewsInfoData(infoBeanFormTemp, adminUser);
				map.put("success", true);
				map.put("msg", "新建成功");
			} else {
				// 修改
				NewsInfoData infoBeanFormTemp = newsInfoDataEntityManager.getNewsInfoData(infoBeanForm.getId());
				infoBeanFormTemp.setLastModifyTime(new Date());
				infoBeanFormTemp.setLotteryType(infoBeanForm.getLotteryType());
				infoBeanFormTemp.setContent(content);
				infoBeanFormTemp.setLevel(infoBeanForm.getLevel());
				infoBeanFormTemp.setTitle(infoBeanForm.getTitle());
				infoBeanFormTemp.setShortTitle(infoBeanForm.getShortTitle());
				infoBeanFormTemp.setAuthor(adminUser.getName());
				infoBeanFormTemp.setTitleLink(infoBeanForm.getTitleLink());
				infoBeanFormTemp.setSubType(infoBeanForm.getSubType());
				// infoBeanFormTemp.setTitleType(infoBeanForm.getTitleType());
				// infoBeanFormTemp.setTitleTypeLink(infoBeanForm.getTitleTypeLink());
				infoBeanFormTemp.setTitleColor(null == infoBeanForm.getTitleColor() ? InfoTitleColor.BLACK
						: infoBeanForm.getTitleColor());
				infoBeanFormTemp.setType(infoBeanForm.getType());
				infoBeanFormTemp.setAuthor(htmlEncode(infoBeanForm.getAuthor()));
				infoBeanFormTemp.setKeywords(htmlEncode(infoBeanForm.getKeywords()));
				infoBeanFormTemp.setDescription(htmlEncode(infoBeanForm.getDescription()));
				infoBeanFormTemp.setIsNotice(infoBeanForm.getIsNotice());
				//infoBeanFormTemp.setTags(infoBeanForm.getTags());
//				String tag = "";
//				if(StringUtils.isNotBlank(infoBeanForm.getTags1())&&StringUtils.isNotEmpty(infoBeanForm.getTags1()))
//					tag+=infoBeanForm.getTags1()+"#";
//				if(StringUtils.isNotBlank(infoBeanForm.getTags2())&&StringUtils.isNotEmpty(infoBeanForm.getTags2()))
//					tag+=infoBeanForm.getTags2()+"#";
//				if(StringUtils.isNotBlank(infoBeanForm.getTags3())&&StringUtils.isNotEmpty(infoBeanForm.getTags3()))
//					tag+=infoBeanForm.getTags3()+"#";
//				if(StringUtils.isNotBlank(tag)&&StringUtils.isNotEmpty(tag))
//					infoBeanFormTemp.setTags(tag.trim().substring(0,tag.length()-1));
//				else infoBeanFormTemp.setTags("");
				newsInfoDataEntityManager.saveNewsInfoData(infoBeanFormTemp, adminUser);
				map.put("success", true);
				map.put("msg", "修改成功");
				createNewIndexFile();
				// zhuhui add 2011-03-21 发布信息静态化事件
				SpringContextHolder.publishEvent(new NewsInfoUpdateEvent(infoBeanFormTemp));
			}
		} catch (DataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		Struts2Utils.renderJson(map);
		return null;

	}
	
	/**
	 * 手机公告列表
	 * @return
	 */
	public String mobileIndex(){
		
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new DataException("权限不足！");
			}
			XDetachedCriteria criteria = new XDetachedCriteria(MobileNewsData.class, "m");
			if(null!=infoBeanForm&&null!=infoBeanForm.getState()){
				criteria.add(Restrictions.eq("m.state",infoBeanForm.getState()));
			}
			if(null!=infoBeanForm&&null!=infoBeanForm.getMobileInfoType()){
				criteria.add(Restrictions.eq("m.mobileInfoType",infoBeanForm.getMobileInfoType()));
			}
			criteria.addOrder(Order.desc("m.id"));
			pagination = queryService.findByCriteriaAndPagination(criteria, pagination);

		} catch (DataException e) {
			e.printStackTrace();
		}
		
		return "mobile-list";
	}
	/**
	 * 更改手机公告状态
	 * @return
	 */
	public String changeMobileNewsState(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new DataException("权限不足！");
			}
			if (null == id) {
				throw new DataException("缺失Id！");
			}
			if (null == state) {
				throw new DataException("缺失状态！");
			}
			MobileNewsData mobileNewsData = newsInfoDataEntityManager.getMobileNewsData(id);
			mobileNewsData.setState(state);
			newsInfoDataEntityManager.saveMobileNewsData(mobileNewsData, adminUser);
			map.put("success", true);
			map.put("msg", "修改成功");
			
		} catch (DataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		Struts2Utils.renderJson(map);
		return null;
	}
	/**
	 * 编辑手机公告
	 * @param source
	 * @return
	 */
	private Map<String,Long> periodMap=Maps.newHashMap();
	private Map<String,Integer> lotteryPlayTypeMap=Maps.newHashMap();
	
	/**
	 * 保存手机公告信息
	 * @return
	 */
	public String mobileNewsSave(){
		try {
			
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("权限不足！");
			}
			if (null == infoBeanForm) {
				throw new WebDataException("消息实体为空.");
			}
			if (StringUtils.isBlank(infoBeanForm.getTitle())) {
				throw new WebDataException("标题为空");
			}
			if (null == infoBeanForm.getState()) {
				infoBeanForm.setState(InfoState.CHECKING);
			}
			if(MobileInfoType.ZJJH.equals(this.infoBeanForm.getMobileInfoType())){
				if(StringUtils.isBlank(infoBeanForm.getAnalyse())){
					throw new WebDataException("复式推介不能为空");
				}
				if(StringUtils.isBlank(infoBeanForm.getSingleAnalyse())){
					throw new WebDataException("单式推介不能为空");
				}
				if(infoBeanForm.getAnalyse().indexOf(",")==-1){
					throw new WebDataException("复式推介号码请用逗号(,)分隔");
				}
				if(infoBeanForm.getSingleAnalyse().indexOf(",")==-1){
					throw new WebDataException("单式推介号码请用逗号(,)分隔");
				}
				if(null!=infoBeanForm.getLotteryType()){
					if(infoBeanForm.getLotteryType().equals(Lottery.SSQ)||infoBeanForm.getLotteryType().equals(Lottery.DLT)){
						if(infoBeanForm.getAnalyse().indexOf("|")==-1){
							throw new WebDataException("复式推介区间请用(|)分隔");
						}
						if(infoBeanForm.getSingleAnalyse().indexOf("|")==-1){
							throw new WebDataException("单式推介区间请用(|)分隔");
						}
					}
				}
			}
			if (null == infoBeanForm.getId() || Long.valueOf("-1").equals(infoBeanForm.getId())) {
				MobileNewsData mobileNewsData = new MobileNewsData();
				mobileNewsData.setTitle(infoBeanForm.getTitle());
				mobileNewsData.setAnalyse(infoBeanForm.getAnalyse());
				mobileNewsData.setMobileInfoType(infoBeanForm.getMobileInfoType());
				mobileNewsData.setState(infoBeanForm.getState());
				mobileNewsData.setLotteryType(infoBeanForm.getLotteryType());
				mobileNewsData.setLastContent(infoBeanForm.getLastContent().trim());
				mobileNewsData.setSingleAnalyse(infoBeanForm.getSingleAnalyse());
				mobileNewsData.setLotteryType(infoBeanForm.getLotteryType());
				mobileNewsData.setContent(infoBeanForm.getContent().trim());
				mobileNewsData.setLotteryPlayType(infoBeanForm.getLotteryPlayType());
				if(null!=infoBeanForm.getLotteryType()&&null!=infoBeanForm.getPeriodId()){
					if(infoBeanForm.getLotteryType().getCategory().equals(LotteryCategory.FREQUENT)){
						KenoPeriod kenoPeriod = this.getKenoManager(infoBeanForm.getLotteryType()).getKenoPeriod(infoBeanForm.getPeriodId());
						if(null==kenoPeriod){
							throw new WebDataException("找不到对应的期号");
						}
					}else{
						Period period = periodManager.getPeriod(infoBeanForm.getPeriodId());
						if(null==period){
							throw new WebDataException("找不到对应的期号");
						}
					}
					mobileNewsData.setPeriodId(infoBeanForm.getPeriodId());
				}
				newsInfoDataEntityManager.saveMobileNewsData(mobileNewsData, adminUser);
				Struts2Utils.setAttribute("mobileNewsData", mobileNewsData);
				 this.addActionMessage("新增成功");
			}else{
				MobileNewsData mobileNewsData = newsInfoDataEntityManager.getMobileNewsData(infoBeanForm.getId());
				mobileNewsData.setId(infoBeanForm.getId());
				mobileNewsData.setTitle(infoBeanForm.getTitle());
				mobileNewsData.setAnalyse(infoBeanForm.getAnalyse());
				mobileNewsData.setMobileInfoType(infoBeanForm.getMobileInfoType());
				mobileNewsData.setLotteryType(infoBeanForm.getLotteryType());
				mobileNewsData.setState(infoBeanForm.getState());
				mobileNewsData.setLastContent(infoBeanForm.getLastContent().trim());
				mobileNewsData.setSingleAnalyse(infoBeanForm.getSingleAnalyse());
				mobileNewsData.setLotteryType(infoBeanForm.getLotteryType());
				mobileNewsData.setContent(infoBeanForm.getContent().trim());
				mobileNewsData.setLotteryPlayType(infoBeanForm.getLotteryPlayType());
				if(null!=infoBeanForm.getLotteryType()&&null!=infoBeanForm.getPeriodId()){
					if(infoBeanForm.getLotteryType().getCategory().equals(LotteryCategory.FREQUENT)){
						KenoPeriod kenoPeriod = this.getKenoManager(infoBeanForm.getLotteryType()).getKenoPeriod(infoBeanForm.getPeriodId());
						if(null==kenoPeriod){
							throw new WebDataException("找不到对应的期号");
						}
					}else{
						Period period = periodManager.getPeriod(infoBeanForm.getPeriodId());
						if(null==period){
							throw new WebDataException("找不到对应的期号");
						}
					}
					mobileNewsData.setPeriodId(infoBeanForm.getPeriodId());
				}
				newsInfoDataEntityManager.saveMobileNewsData(mobileNewsData, adminUser);
				Struts2Utils.setAttribute("mobileNewsData", mobileNewsData);
                this.addActionMessage("修改成功");
			}
			
		} catch (WebDataException e) {
			this.addActionError(e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage());
			this.addActionError("操作出错");
		}
		return editMobileNews();
	}
	/**
	 * 编辑手机公告
	 * @param source
	 * @return
	 */
	public String editMobileNews() {
		MobileNewsData mobileNewsData = null;
		if(null!=infoBeanForm&&null!=infoBeanForm.getId()){
			mobileNewsData = newsInfoDataEntityManager.getMobileNewsData(infoBeanForm.getId());
		}
		if(null!=mobileNewsData){
			try {
				PropertyUtils.copyProperties(this.infoBeanForm, mobileNewsData);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(null!=infoBeanForm&&null!=infoBeanForm.getLotteryType()){
			if(infoBeanForm.getLotteryType().getCategory().equals(LotteryCategory.FREQUENT)){
				if(null!=infoBeanForm.getPeriodId()){
					KenoPeriod period = this.getKenoManager(infoBeanForm.getLotteryType()).getKenoPeriod(infoBeanForm.getPeriodId());
					if(null!=period){
						periodMap.put(period.getPeriodNumber(), period.getId());
					}
				}
				KenoPeriod period = this.getKenoManager(infoBeanForm.getLotteryType()).getCurrentData();
				if(null!=period){
					periodMap.put(period.getPeriodNumber(), period.getId());
				}
			}else{
				if(null!=infoBeanForm.getPeriodId()){
					Period period = periodManager.getPeriod(infoBeanForm.getPeriodId());
					if(null!=period){
						periodMap.put(period.getPeriodNumber(), period.getId());
					}
				}
				List<Period> periodList = periodManager.findCurrentPeriods(infoBeanForm.getLotteryType(), false);
				if(null!=periodList&&!periodList.isEmpty()){
					for (Period period : periodList) {
						periodMap.put(period.getPeriodNumber(), period.getId());
					}
				}
			}
			if(infoBeanForm.getLotteryType().equals(Lottery.PL)){
				lotteryPlayTypeMap.put("排列三", 0);
				lotteryPlayTypeMap.put("排列五", 1);
			}else{
				lotteryPlayTypeMap.put("常规玩法", 0);
			}
		}
		return "mobile-edit";
	}
	/**
	 * 加载手机公告信息
	 * @return
	 */
	public String loadMobileNews(){
		if (null == infoBeanForm || null == infoBeanForm.getId()) {
			return newsInfoList();
		}
		MobileNewsData mobileNewsData = newsInfoDataEntityManager.getMobileNewsData(infoBeanForm.getId());
		if (null == mobileNewsData) {
			addActionError("实体为空");
			return mobileIndex();
		}
		if(null!=mobileNewsData){
			try {
				PropertyUtils.copyProperties(this.infoBeanForm, mobileNewsData);
				if(null!=mobileNewsData.getLotteryType()){
					this.setLotteryType(mobileNewsData.getLotteryType());
					if(mobileNewsData.getLotteryType().getCategory().equals(LotteryCategory.FREQUENT)){
						if(null!=mobileNewsData.getPeriodId()){
							KenoPeriod period = this.getKenoManager(mobileNewsData.getLotteryType()).getKenoPeriod(mobileNewsData.getPeriodId());
							if(null!=period){
								periodMap.put(period.getPeriodNumber(), period.getId());
							}
						}
						KenoPeriod period = this.getKenoManager(mobileNewsData.getLotteryType()).getCurrentData();
						if(null!=period){
							periodMap.put(period.getPeriodNumber(), period.getId());
						}
					}else{
						if(null!=mobileNewsData.getPeriodId()){
							Period period = periodManager.getPeriod(mobileNewsData.getPeriodId());
							if(null!=period){
								periodMap.put(period.getPeriodNumber(), period.getId());
							}
						}
						List<Period> periodList = periodManager.findCurrentPeriods(mobileNewsData.getLotteryType(), false);
						if(null!=periodList&&!periodList.isEmpty()){
							for (Period period : periodList) {
								periodMap.put(period.getPeriodNumber(), period.getId());
							}
						}
					}
					if(mobileNewsData.getLotteryType().equals(Lottery.PL)){
						lotteryPlayTypeMap.put("排列三", 0);
						lotteryPlayTypeMap.put("排列五", 1);
					}else{
						lotteryPlayTypeMap.put("常规玩法", 0);
					}
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "mobile-edit";
	}
	
	public String delNews(){
		if (null != infoBeanForm || null != infoBeanForm.getId()) {
			newsInfoDataEntityManager.delMobileNewsData(infoBeanForm.getId());
		}
		return mobileIndex();
	}
	
	public String tagsInfoToSave(){
		return "tags-edit";
	}
	/**
	 * 保存标签页信息
	 * @return
	 */
	public String tagsInfoSave(){
		try {
			
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new DataException("权限不足！");
			}
			if (null == infoBeanForm) {
				throw new DataException("消息实体为空.");
			}
			if (StringUtils.isBlank(infoBeanForm.getTags())) {
				throw new WebDataException("标题为空");
			}
			
			if (null == infoBeanForm.getId() || Long.valueOf("-1").equals(infoBeanForm.getId())) {
				String[] tags = infoBeanForm.getTags().split("\r\n|\n");
				for(String tag:tags){
					if(StringUtils.isNotBlank(tag)&&StringUtils.isNotEmpty(tag)){
						TagsInfoData tagsInfoData = new TagsInfoData();
						tagsInfoData.setLotteryType(infoBeanForm.getLotteryType());
						tagsInfoData.setTags(tag.trim());
						tagsInfoData.setLevel(infoBeanForm.getLevel());
						newsInfoDataEntityManager.saveTagsInfoData(tagsInfoData, adminUser);
					}
				}
				//Struts2Utils.setAttribute("tagsInfoData", tagsInfoData);
			}else{
				TagsInfoData tagsInfoData = new TagsInfoData();
				tagsInfoData.setId(infoBeanForm.getId());
				tagsInfoData.setLotteryType(infoBeanForm.getLotteryType());
				tagsInfoData.setTags(infoBeanForm.getTags().trim());
				tagsInfoData.setLevel(infoBeanForm.getLevel());
				newsInfoDataEntityManager.saveTagsInfoData(tagsInfoData, adminUser);
				Struts2Utils.setAttribute("tagsInfoData", tagsInfoData);

			}
			
		} catch (DataException e) {
			e.printStackTrace();
		} catch (WebDataException e) {
			e.printStackTrace();
		}
		return "tags-edit";
	}
	/**
	 * 标签页列表
	 * @return
	 */
	public String tagsIndex(){
		
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new DataException("权限不足！");
			}
			XDetachedCriteria criteria = new XDetachedCriteria(TagsInfoData.class, "m");
			if(null!=lotteryType){
				criteria.add(Restrictions.eq("m.lotteryType",lotteryType));
			}
			criteria.addOrder(Order.desc("m.id"));
			pagination = queryService.findByCriteriaAndPagination(criteria, pagination);

		} catch (DataException e) {
			e.printStackTrace();
		}
		
		return "tags-list";
	}
	/**
	 * 加载标签信息
	 * @return
	 */
	public String loadTagInfo(){
		if (null == infoBeanForm || null == infoBeanForm.getId()) {
			return newsInfoList();
		}
		TagsInfoData tagsInfoData = newsInfoDataEntityManager.getTagsInfoData(infoBeanForm.getId());
		if (null == tagsInfoData) {
			addActionError("实体为空");
			return mobileIndex();
		}
		Struts2Utils.setAttribute("tagsInfoData", tagsInfoData);
		return "tags-edit";
	}
	
	public String delTags(){
		if (null != infoBeanForm || null != infoBeanForm.getId()) {
			newsInfoDataEntityManager.delTagsInfoData(infoBeanForm.getId());
		}
		return tagsIndex();
	}
	public static String htmlEncode(String source) {
		if (source == null) {
			return "";
		}
		String html = "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			switch (c) {
			case '<':
				buffer.append("&lt;");
				break;
			case '>':
				buffer.append("&gt;");
				break;
			case '&':
				buffer.append("&amp;");
				break;
			case '"':
				buffer.append("&quot;");
				break;
			case '/':
				buffer.append("");
				break;
			case '%':
				buffer.append("");
				break;
			case 10:
			case 13:
				break;
			default:
				buffer.append(c);
			}
		}
		html = buffer.toString();
		return html;
	}

	public void createNewIndexFile() throws DataException {
		Lottery[] lotteryArr = Lottery.values();
		InfoType[] infoTypeArr = InfoType.values();
		List<NewsInfoData> newsList = null;
		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("base", Constant.BASEPATH);
		for (Lottery lottery : lotteryArr) {
			for (InfoType infoType : infoTypeArr) {
				newsList = newsInfoDataEntityManager.getNewsInfoDataBy(infoType, lottery, 10, true);
				if (null != newsList) {
					contents.put(lottery.toString() + "_" + infoType.toString(), newsList);
					contents.put("RIGHT_" + infoType.toString(), newsList);
				}
			}
			contents.put("lottery", lottery);
			createNewsFile(contents, "lottery_right.ftl", lottery.getKey() + "_right.html");
		}

		// zhuhui motify 2011-03-21 去掉首页栏目信息生成
		// createNewsFile(contents, "index-content.ftl", "index-content.html");
	}

	/**
	 * 生成静态文件工具类
	 * 
	 * @param contents
	 * @param sourcefile
	 * @param destinationFileName
	 * @throws DataException
	 */
	public void createNewsFile(Map<String, Object> contents, String sourcefile, String destinationFileName)
			throws DataException {
		try {
			TemplateGenerator tg = new TemplateGenerator(Constant.ROOTPATH + "/WEB-INF/content/info/news");
			tg.create(sourcefile, contents, destinationFileName, Constant.ROOTPATH + "/WEB-INF/content/info/news");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return the createContent
	 */
	public String getCreateContent() {
		return createContent;
	}

	/**
	 * @param createContent
	 *            the createContent to set
	 */
	public void setCreateContent(String createContent) {
		this.createContent = createContent;
	}

	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination
	 *            the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public InfoBeanForm getInfoBeanForm() {
		return infoBeanForm;
	}

	public void setInfoBeanForm(InfoBeanForm infoBeanForm) {
		this.infoBeanForm = infoBeanForm;
	}

	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public InfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(InfoType infoType) {
		this.infoType = infoType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InfoState getState() {
		return state;
	}

	public void setState(InfoState state) {
		this.state = state;
	}

	public List<InfoSubType> getInfoTypeList() {
		return infoTypeList;
	}

	public void setInfoTypeList(List<InfoSubType> infoTypeList) {
		this.infoTypeList = infoTypeList;
	}
	
	
	private String params;
	
	public String publicTag(){
		//try {
			ArrayList tagList = new ArrayList();
			if(StringUtils.isNotBlank(params)&&StringUtils.isNotEmpty(params)){
				if("cool".equals(params)){
					ArrayList<TagsInfoData> datas = (ArrayList<TagsInfoData>) newsInfoDataEntityManager.loadTagsInfoDatas(2, lotteryType);
					for(TagsInfoData data:datas){
						String[] items = new String[2];
						items[1]="/tag/"+data.getId()+".htm";
						items[0]=data.getTags();
						tagList.add(items);
					}
				}else if("hot".equals(params)){
					ArrayList<TagsInfoData> datas = (ArrayList<TagsInfoData>) newsInfoDataEntityManager.loadTagsInfoDatas(0, lotteryType);
					for(TagsInfoData data:datas){
						String[] items = new String[2];
						items[1]="/tag/"+data.getId()+".htm";
						items[0]=data.getTags();
						tagList.add(items);
					}
				}else{
					String[] param = params.split("#");
					for(String pr:param){
						String[] args = pr.split(",");
						tagList.add(args);
					}
				}
				if(tagList.size()>0){
					//newsInfoBuilder.createIndexTagsFile(tagList,lotteryType);
				}
			}
//		} catch (DataException e) {
//			e.printStackTrace();
//		}
		return "index-tagword";

	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public List<TagsInfoData> getTagsInfoDatas() {
		return tagsInfoDatas;
	}

	public void setTagsInfoDatas(List<TagsInfoData> tagsInfoDatas) {
		this.tagsInfoDatas = tagsInfoDatas;
	}
	public Map<String, String> getPlayTypeMap() {
		return playTypeMap;
	}
	public void setPlayTypeMap(Map<String, String> playTypeMap) {
		this.playTypeMap = playTypeMap;
	}
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public String getRuleStr() {
		return ruleStr;
	}
	public void setRuleStr(String ruleStr) {
		this.ruleStr = ruleStr;
	}
	public Map<String, Long> getPeriodMap() {
		return periodMap;
	}
	public void setPeriodMap(Map<String, Long> periodMap) {
		this.periodMap = periodMap;
	}
	public Map<String, Integer> getLotteryPlayTypeMap() {
		return lotteryPlayTypeMap;
	}
	public void setLotteryPlayTypeMap(Map<String, Integer> lotteryPlayTypeMap) {
		this.lotteryPlayTypeMap = lotteryPlayTypeMap;
	}
	

}
