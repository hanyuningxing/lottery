package com.cai310.lottery.web.controller.info;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.info.TagsInfoData;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.info.NewsInfoDataEntityManager;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.Struts2Utils;
import com.ibm.icu.util.Calendar;
@Results( {
	   @Result(name = "index",  location = "/WEB-INF/content/info/news/index.ftl"),
	   @Result(name = "info", location = "/WEB-INF/content/info/news/info.ftl"),
	   @Result(name = "info-list", location = "/WEB-INF/content/info/news/info-list.ftl"),
	   @Result(name = "hot-search-result", location = "/WEB-INF/content/info/news/hot-search-result.ftl"),
	   @Result(name = "tags-search-result", location = "/WEB-INF/content/info/results/tags-search-result.ftl"),
	   @Result(name = "topic-detail", location = "/WEB-INF/content/info/tags/topic-detail.ftl"),
	   @Result(name = "index-newslist", location = "/WEB-INF/content/index/index-news.ftl")
})
public class NewsController extends BaseController {
	private static final long serialVersionUID = -3004288085663395253L;
	/** 新闻**/
	@Autowired
	private NewsInfoDataEntityManager newsInfoDataEntityManager;
	@Autowired
	protected QueryService queryService;
	
	private InfoBeanForm infoBeanForm;
	
	private List<NewsInfoData> allNewsList=new ArrayList<NewsInfoData>();
	
	private Long id;
	/** 类型 **/
	private Lottery lottery;

	private InfoType infoType;
	
	private InfoSubType subType;
	private List<InfoSubType> infoTypeList;
	private List<NewsInfoData> moreNewsInfoDataList;
	private List<TagsInfoData> tagsInfoDataList;

	public List<NewsInfoData> getMoreNewsInfoDataList() {
		return moreNewsInfoDataList;
	}
	public void setMoreNewsInfoDataList(List<NewsInfoData> moreNewsInfoDataList) {
		this.moreNewsInfoDataList = moreNewsInfoDataList;
	}
	public List<InfoSubType> getInfoTypeList() {
		return infoTypeList;
	} 
	public void setInfoTypeList(List<InfoSubType> infoTypeList) {
		this.infoTypeList = infoTypeList;
	}
	private Pagination pagination = new Pagination(20);
	
	public String info() {
		try {
			if(null!=this.getId()){
				NewsInfoData newsInfoData = newsInfoDataEntityManager.getNewsInfoData(this.getId());
				if(null==newsInfoData){
					return error();
				}
				newsInfoData.setClickNum(newsInfoData.getClickNum()+1);
				newsInfoDataEntityManager.saveNewsInfoData(newsInfoData);
				infoBeanForm = new InfoBeanForm();
				infoBeanForm.setLastModifyTime(newsInfoData.getLastModifyTime());
				infoBeanForm.setContent(newsInfoData.getContent());
				infoBeanForm.setLevel(newsInfoData.getLevel());
				infoBeanForm.setState(newsInfoData.getState());
				infoBeanForm.setTitle(newsInfoData.getTitle());
				infoBeanForm.setShortTitle(newsInfoData.getShortTitle());
				infoBeanForm.setTitleTypeLink(newsInfoData.getTitleLink());
				infoBeanForm.setTitleType(newsInfoData.getTitleType());
				infoBeanForm.setTitleTypeLink(newsInfoData.getTitleTypeLink());
				infoBeanForm.setType(newsInfoData.getType());
				infoBeanForm.setId(newsInfoData.getId());
				infoBeanForm.setCreateTime(newsInfoData.getCreateTime());
				infoBeanForm.setLotteryType(newsInfoData.getLotteryType());
				infoBeanForm.setClickNum(newsInfoData.getClickNum());
				infoBeanForm.setAuthor(newsInfoData.getAuthor());
				infoBeanForm.setKeywords(newsInfoData.getKeywords());
				infoBeanForm.setSubType(newsInfoData.getSubType());
				infoBeanForm.setIsNotice(newsInfoData.getIsNotice());
				infoBeanForm.setDescription(newsInfoData.getDescription());
				if(StringUtils.isNotBlank(newsInfoData.getKeywords())) {
					moreNewsInfoDataList = this.findMoreNewsInfoDataListByKeyWords(newsInfoData.getId(),newsInfoData.getKeywords(),0,12);
				}
				if(null!=newsInfoData.getLotteryType()) {
					allNewsList = findHotNewsInfoDataList(newsInfoData.getLotteryType(),0,6);
				} else {
					allNewsList = findHotNewsInfoDataList(null,0,6);
				}
				if(StringUtils.isNotBlank(newsInfoData.getTags())&&StringUtils.isNotEmpty(newsInfoData.getTags())){
					Lottery lottery = null;
					//if(null!=newsInfoData.getLotteryType())
						//lottery = newsInfoData.getLotteryType();					
					//tagsInfoDataList = findTagsInfoDataList(newsInfoData.getTags(),lottery,0,5);
					String[] tagsDatas = newsInfoData.getTags().trim().split("#");
					HashMap map = new HashMap();
					for(int i=0;i<tagsDatas.length;i++){
						String[] tag = tagsDatas[i].trim().split(":");
						map.put(tag[0], tag[1]);
					}
					Struts2Utils.setAttribute("map", map);
				}
			}
			return "info";
		} catch (ServiceException e) {
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		
		return error();
	}
	
	//根据点击量获取热点新闻
	private List<NewsInfoData> findHotNewsInfoDataList(Lottery lottery,int firstResult, int maxResults){
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		criteria.add(Restrictions.eq("state",InfoState.NORMAL));
		if(null!=lottery) {
			//彩种咨询频道显
			criteria.add(Restrictions.eq("lotteryType", lottery));
		} else {
			//咨询首页面显示 公告
			criteria.add(Restrictions.eq("isNotice", NewsInfoData.NOTICE));
		}
		criteria.addOrder(Order.desc("id"));
	    return queryService.findByDetachedCriteria(criteria, firstResult, maxResults,true);
	}
	
	//根据关键字获取类似新闻
	private List<NewsInfoData> findMoreNewsInfoDataListByKeyWords(Long selfId,String keyWords,int firstResult, int maxResults){
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		criteria.add(Restrictions.or(Restrictions.like("title", "%"+keyWords+"%"), Restrictions.like("shortTitle", "%"+keyWords+"%")));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.add(Restrictions.ne("id",selfId.longValue()));
		criteria.addOrder(Order.desc("id"));
	    return queryService.findByDetachedCriteria(criteria,firstResult, maxResults,true);
	}
	 
	
	public String index() {
		XDetachedCriteria criteria = new XDetachedCriteria(NewsInfoData.class);
		String indexflag = Struts2Utils.getRequest().getParameter("indexflag");
		if(null!=this.lottery){
			criteria.add(Restrictions.eq("lotteryType", this.lottery));
		}
		if(null!=this.getInfoType()){
			criteria.add(Restrictions.eq("type", this.getInfoType()));
		}
		if(null!=subType){
			criteria.add(Restrictions.eq("subType", subType));
		}
		criteria.setCacheable(true);
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		infoTypeList = InfoSubType.getInfoSubTypeBy(this.infoType,  this.lottery);
		allNewsList = findHotNewsInfoDataList(null,0,6);
		if(indexflag!=null && indexflag.equals("1")) {
			return "index-newslist";
		}
		return "index";
	}
	
	/**
	 * 取得子类型
	 * 
	 * @return
	 */
	public String selectInfoSubType() {
		Map<String, Object> data = new HashMap<String, Object>();
		infoTypeList = InfoSubType.getInfoSubTypeBy(this.infoType,  this.lottery);
		data.put("infoTypeList",buildInfoSubType(infoTypeList));
		Struts2Utils.renderJson(data);
		return null;
	}
	private List buildInfoSubType(List<InfoSubType> infoTypeList){
		List list = new ArrayList();
		for (InfoSubType infoSubType : infoTypeList) {
			Map<String, Object> infoTypeMap = new HashMap<String, Object>();
			infoTypeMap.put("obj",infoSubType);
			infoTypeMap.put("name",infoSubType.getTypeName());
			list.add(infoTypeMap);
		}
		return list;
	}
	
	public String infoList() {
		try {
			if(null==infoType)throw new WebDataException("新闻类型出错");
			if(null==lottery)throw new WebDataException("新闻彩种出错");
			pagination=newsInfoDataEntityManager.getNewsInfoDataBy(infoType, lottery, pagination);
			return "info-list";
		} catch (WebDataException e) {
			addActionMessage(e.getMessage());
		} catch (ServiceException e) {
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return error();
	}
	
	
	public String hotSearch(){
		XDetachedCriteria criteria = new XDetachedCriteria(NewsInfoData.class);
		criteria.setCacheable(true);
		if(null!=this.lottery){
			criteria.add(Restrictions.eq("lotteryType", this.lottery));
		}
		if(null!=infoBeanForm&&null!=infoBeanForm.getKeywords()&&!"".equals(infoBeanForm.getKeywords())){
			try {
				String keyWords = java.net.URLDecoder.decode(infoBeanForm.getKeywords().trim(), "utf-8");
				criteria.add(Restrictions.or(Restrictions.like("title", "%"+keyWords+"%"), Restrictions.like("shortTitle", "%"+keyWords+"%")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(null!=infoBeanForm&&null!=infoBeanForm.getFiterType()&&!"".equals(infoBeanForm.getFiterType())){
			String filterType = infoBeanForm.getFiterType();
			Calendar c = Calendar.getInstance();
			if("1".equals(filterType)){
				c.add(Calendar.DATE, -1);
				criteria.add(Restrictions.gt("createTime", c.getTime()));
			}else if("2".equals(filterType)){
				c.add(Calendar.DATE, -7);
				criteria.add(Restrictions.gt("createTime", c.getTime()));
			}else if("3".equals(filterType)){
				c.add(Calendar.MONTH, -1);
				criteria.add(Restrictions.gt("createTime", c.getTime()));
			}
		}
		criteria.addOrder(Order.desc("createTime"));
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		allNewsList = findHotNewsInfoDataList(null,0,6);
		Struts2Utils.setAttribute("keyWord", infoBeanForm.getKeywords());
		return "hot-search-result";
	}
	
	
	public String tagsSearch(){
		TagsInfoData tagsInfoData = null;
		if(null!=id)
			tagsInfoData = newsInfoDataEntityManager.getTagsInfoData(id);
		if(null==tagsInfoData){
			return "tags-search-result";
		}
		StringBuffer hql = new StringBuffer("from NewsInfoData where 1=1 and state=").append(InfoState.NORMAL.ordinal());
		if(null!=tagsInfoData.getTags()){
			String[] tags = tagsInfoData.getTags().trim().split("\\s+");
			hql.append(" and (");
			for(int i=0;i<tags.length;i++){
				hql.append("tags like '%").append(tags[i]).append("%' ");
				if(i<tags.length-1)
					hql.append("or ");
			}
			hql.append(")");
		}
		hql.append(" order by createTime desc");
		pagination = queryService.findByHql(hql.toString(),null,pagination,null);
		List result = pagination.getResult();
		ArrayList tResult = new ArrayList();
		if(null!=result&&result.size()>0){
			for(int i=0;i<result.size();i++){
				NewsInfoData nif = (NewsInfoData)result.get(i);
				String[] tagsDatas = nif.getTags().trim().split("#");
				HashMap map = new HashMap();
				for(int j=0;j<tagsDatas.length;j++){
					String[] tag = tagsDatas[j].trim().split(":");
					map.put(tag[0], tag[1]);
				}
				tResult.add(map);
			}
		}
		Struts2Utils.setAttribute("results", tResult);

		allNewsList = findHotNewsInfoDataList(null,0,6);
		Struts2Utils.setAttribute("keyWord", tagsInfoData.getTags());
		return "tags-search-result";
	}
	
	public String topicDetail(){
		List<NewsInfoData> topicsList = newsInfoDataEntityManager.loadTopics(InfoType.TOPICS, new InfoSubType[]{InfoSubType.SXZT,InfoSubType.JGZT,InfoSubType.HDZT,InfoSubType.LTZT}, null);
		Struts2Utils.setAttribute("topicsList", topicsList);
		return "topic-detail";
	}
	
	/**
	 * @return the infoBeanForm
	 */
	public InfoBeanForm getInfoBeanForm() {
		return infoBeanForm;
	}

	/**
	 * @param infoBeanForm the infoBeanForm to set
	 */
	public void setInfoBeanForm(InfoBeanForm infoBeanForm) {
		this.infoBeanForm = infoBeanForm;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public List<NewsInfoData> getAllNewsList() {
		return allNewsList;
	}
	public void setAllNewsList(List<NewsInfoData> allNewsList) {
		this.allNewsList = allNewsList;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public Lottery getLottery() {
		return lottery;
	}
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}
	
	public InfoSubType getSubType() {
		return subType;
	}
	public void setSubType(InfoSubType subType) {
		this.subType = subType;
	} 
	public InfoType getInfoType() {
		return infoType;
	}
	public void setInfoType(InfoType infoType) {
		this.infoType = infoType;
	}
	public List<TagsInfoData> getTagsInfoDataList() {
		return tagsInfoDataList;
	}
	public void setTagsInfoDataList(List<TagsInfoData> tagsInfoDataList) {
		this.tagsInfoDataList = tagsInfoDataList;
	}
	
	
}
