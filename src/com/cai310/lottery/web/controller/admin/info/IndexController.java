package com.cai310.lottery.web.controller.admin.info;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.info.AdImages;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.TradeSuccessScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.info.IndexInfoDataEntityManager;
import com.cai310.lottery.service.info.NewsInfoDataEntityManager;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.jczq.impl.JczqSchemeEntityManagerImpl;
import com.cai310.lottery.support.NumberSaleBean;
import com.cai310.lottery.web.cache.BonusAccumulatorWebCache;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.orm.Pagination;
import com.cai310.utils.DateUtil;
import com.cai310.utils.FileUtils;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.RandomUtils;
import com.cai310.utils.Struts2Utils;
import com.cai310.utils.TemplateGenerator;
import com.cai310.utils.WriteHTMLUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Namespace("/admin/info")
@Action("index")
@Results({ @Result(name = "match-info-edit", location = "/WEB-INF/content/admin/info/match-info-odds.ftl"),
		@Result(name = "index-per", location = "/WEB-INF/content/admin/info/index-per.ftl"),
		@Result(name = "upload-pic", location = "/WEB-INF/content/admin/info/upload-pic.ftl"),
		@Result(name = "static", location = "/WEB-INF/content/admin/info/static.ftl") })
public class IndexController extends AdminBaseController {
	private static final long serialVersionUID = -7128890110025210382L;
	
	@Resource(name="bonusAccumulatorCache")
	
	private BonusAccumulatorWebCache bonusAccumulatorCache;
	
	
	
	@Autowired
	private QueryService queryService;
	
	@Autowired
	private PeriodEntityManager periodEntityManager;
	
	/** 新闻 **/
	@Resource
	private NewsInfoDataEntityManager newsInfoDataEntityManager;
	
	@Resource
	private IndexInfoDataEntityManager indexInfoDataEntityManager;
	
	@Resource
	private JczqSchemeEntityManagerImpl jczqSchemeEntityManagerImpl;

	@Autowired
	protected EventLogManager eventLogManager;

	/** 类型 **/
	private InfoType type;
	private Pagination pagination = new Pagination(20);
	private Lottery lottery;
	private List<NewsInfoData> meinvForecastList = new ArrayList<NewsInfoData>();
	private List<NewsInfoData> zhuanjiaForecastList = new ArrayList<NewsInfoData>();
	private List<NewsInfoData> jcSkillsList = new ArrayList<NewsInfoData>();	
	private Map<String,List<JczqScheme>> topSchemeMap=Maps.newHashMap();		
	private List<TradeSuccessScheme> newestWonSubcriptionList = Lists.newArrayList();
	private List<NewsInfoData> gonggaoList = new ArrayList<NewsInfoData>();
	private Map<String,Object> contents=new HashMap<String,Object>();
	
	private long BonusAccumulator;
	private long yi;
	private long qianwan;
	private long baiwan;
	private long shiwan;
	private long wan;
	
	/**
	 * 后台首页生成
	 * 
	 * @return
	 */
	@Action("/indexView")
	public String indexPer() {
		final long YI = 100000000;//常量亿
		final long QIANWAN = 10000000;//常量千万
		final long BAIWAN = 1000000;//常量百万
		final long SHIWAN = 100000;//常量十万
		final long WAN  = 10000;//常量万		
		try {
			contents.put("base", Constant.BASEPATH);
			
			//累计中奖				
			BonusAccumulator = bonusAccumulatorCache.getBonusAccumulator();
			//取得累计奖金的万（wan）、十万（shiwan）、百万（baiwan）、千万（qianwan）、亿（yi）位上的数
			yi = BonusAccumulator/YI;
			qianwan = (BonusAccumulator%YI)/QIANWAN;
			baiwan = (BonusAccumulator%QIANWAN)/BAIWAN;
			shiwan = (BonusAccumulator%BAIWAN)/SHIWAN;
			wan = (BonusAccumulator%SHIWAN)/WAN;
			
			contents.put("yi", yi);
			contents.put("qianwan", qianwan);
			contents.put("baiwan", baiwan);
			contents.put("shiwan", shiwan);
			contents.put("wan", wan);
			
			//最新中奖方案(取6条数据)	
			DetachedCriteria criteriaForSuccessScheme = DetachedCriteria.forClass(TradeSuccessScheme.class, "s");
			criteriaForSuccessScheme.add(Restrictions.eq("s.state", SchemeState.SUCCESS));
			criteriaForSuccessScheme.add(Restrictions.eq("s.won", true));
			List<Long> jiepiaoList = Lists.newArrayList();
			jiepiaoList.add(Long.valueOf(370));
			jiepiaoList.add(Long.valueOf(504));
			criteriaForSuccessScheme.add(Restrictions.not(Restrictions.in("s.sponsorId", jiepiaoList)));
			criteriaForSuccessScheme.addOrder(Order.desc("id"));
			newestWonSubcriptionList = queryService.findByDetachedCriteria(criteriaForSuccessScheme, 0, 6, true);
			contents.put("newestWonSubcriptionList", newestWonSubcriptionList);
			
			//推介合买
			topSchemeMap.put(Lottery.JCZQ.getKey(), this.jczqSchemeEntityManagerImpl.findTopTogetherScheme(5));
			contents.put("top10Scheme", topSchemeMap);
			
			//公告
			gonggaoList = newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.INFO,null,InfoSubType.GONGGAO, 6, true);
			contents.put("gonggaoList", gonggaoList);
			
			//美女必中团队预测
			meinvForecastList =  newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.FORECAST, null, InfoSubType.MEIYU, 3, true);
			contents.put("meinvForecastList", meinvForecastList);
			
			//砖家必发日职
			zhuanjiaForecastList = newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.FORECAST, null, InfoSubType.ZHUANYU, 3, true);
			contents.put("zhuanjiaForecastList", zhuanjiaForecastList);
			
			//技巧
			jcSkillsList = newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.SKILLS, Lottery.JCZQ, null, 8, true);
			contents.put("jcSkillsList", jcSkillsList);
		
			DetachedCriteria criteria = DetachedCriteria.forClass(AdImages.class);
			criteria.addOrder(Order.asc("pos"));
			adImagesList = queryService.findByDetachedCriteria(criteria,true);			
			contents.put("adImagesList", adImagesList);
			
			return "index-per";
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return error();
	}
	
	
    public PeriodData getNewPeriodData(Lottery lottery,Class periodDataClass,PeriodDataEntityManagerImpl periodDataEntityManagerImpl){
    	//读js文件
		String dir = "/html/js/data/" + lottery.getKey() + "/";
		String period_data = WriteHTMLUtil.readFile(dir, "period_data.js");
		PeriodData newestPeriodData = null;
		if(StringUtils.isNotBlank(period_data)){
			try{
					Map<String, Object> map = JsonUtil.getMap4Json(period_data);
					String periodData = String.valueOf(map.get("periodData"));
					newestPeriodData = (PeriodData)JsonUtil.getObject4JsonString(periodData,periodDataClass);
			}catch(Exception e){
					logger.warn(lottery.getLotteryName()+"最新开奖文件解析错误");
			}
		}
		if(null==newestPeriodData){
			newestPeriodData = periodDataEntityManagerImpl.getNewestResultPeriodData();
		}
		return newestPeriodData;
    }
    
    
    public Period getNewResultPeriod(Lottery lottery,Long id){
    	//读js文件
		String dir = "/html/js/data/" + lottery.getKey() + "/";
		String period_data = WriteHTMLUtil.readFile(dir, "period_data.js");
		Period newestPeriod = null;
		if(StringUtils.isNotBlank(period_data)){
			try{
					Map<String, Object> map = JsonUtil.getMap4Json(period_data);
					String periodData = String.valueOf(map.get("period"));
					newestPeriod = (Period)JsonUtil.getObject4JsonString(periodData,Period.class);
			}catch(Exception e){
					logger.warn(lottery.getLotteryName()+"最新开奖文件解析错误");
			}
		}
		if(null==newestPeriod){
			newestPeriod = periodEntityManager.getPeriod(id);
		}
		return newestPeriod;
    }
    
    
    public NumberSaleBean getNewPeriod(Lottery lottery){
    	//读js文件
		String dir = "/html/js/data/" + lottery.getKey() + "/";
		String period_data = WriteHTMLUtil.readFile(dir, "sale_period.js");
		NumberSaleBean numberSaleBean = null;
		if(StringUtils.isNotBlank(period_data)){
			try{
					Map<String, Object> map = JsonUtil.getMap4Json(period_data);
					String numberSaleBeanData = String.valueOf(map.get("numberSaleBean"));
					numberSaleBean = JsonUtil.getObject4JsonString(numberSaleBeanData,NumberSaleBean.class);
			}catch(Exception e){
					logger.warn(lottery.getLotteryName()+"最新开奖文件解析错误");
			}
		}
		if(null==numberSaleBean){
			List<Period> currentPeriods = periodEntityManager.findCurrentPeriods(lottery);
			if (currentPeriods != null && !currentPeriods.isEmpty()) {
				for (Period period : currentPeriods) {
					if(period.isOnSale()&&period.isCurrent()){
						PeriodSales compoundPeriodSale = periodEntityManager.getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.COMPOUND));
						if(compoundPeriodSale.isOnSale()){
							numberSaleBean = new NumberSaleBean();
							numberSaleBean.setId(period.getId());
							numberSaleBean.setLotteryType(period.getLotteryType());
							numberSaleBean.setPeriodNumber(period.getPeriodNumber());
							numberSaleBean.setSelfEndInitTime(DateUtil.dateToStr(compoundPeriodSale.getSelfEndInitTime(),"yyyy-MM-dd HH:mm:ss"));
						}
					}
				}
			}
			
		}
		return numberSaleBean;
    }
    
    
	public String staticIndex() {
		return "static";
	}

	private List<AdImages> adImagesList;
	private File pic1_file;
	private String pic1_word;
	private String pic1_link;

	private File pic2_file;
	private String pic2_word;
	private String pic2_link;

	private File pic3_file;
	private String pic3_word;
	private String pic3_link;

	private File pic4_file;
	private String pic4_word;
	private String pic4_link;
	
	private File pic5_file;
	private String pic5_word;
	private String pic5_link;

	private File pic6_file;
	private String pic6_word;
	private String pic6_link;

	public String uploadPic() {
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
			DetachedCriteria criteria = DetachedCriteria.forClass(AdImages.class);
			criteria.addOrder(Order.asc("pos"));
			adImagesList = queryService.findByDetachedCriteria(criteria);
			return "upload-pic";
		} else {
			try {
				if (null != pic1_file && (StringUtils.isNotBlank(pic1_word)) && (StringUtils.isNotBlank(pic1_link))) {
					uploadPicMethod(1, pic1_file, pic1_word, pic1_link);
					// 记录操作日志
					eventLogManager.saveSimpleEventLog(null, getLottery(), adminUser, EventLogType.Index_Admin, "上传图片：{" + pic4_file
							+ "}");
				}
				if (null != pic2_file && (StringUtils.isNotBlank(pic2_word)) && (StringUtils.isNotBlank(pic2_link))) {
					uploadPicMethod(2, pic2_file, pic2_word, pic2_link);
					// 记录操作日志
					eventLogManager.saveSimpleEventLog(null, getLottery(), adminUser, EventLogType.Index_Admin, "上传图片：{" + pic4_file
							+ "}");
				}
				if (null != pic3_file && (StringUtils.isNotBlank(pic3_word)) && (StringUtils.isNotBlank(pic3_link))) {
					uploadPicMethod(3, pic3_file, pic3_word, pic3_link);
					// 记录操作日志
					eventLogManager.saveSimpleEventLog(null, getLottery(), adminUser, EventLogType.Index_Admin, "上传图片：{" + pic4_file
							+ "}");
				}
				if (null != pic4_file && (StringUtils.isNotBlank(pic4_word)) && (StringUtils.isNotBlank(pic4_link))) {
					uploadPicMethod(4, pic4_file, pic4_word, pic4_link);
					// 记录操作日志
					eventLogManager.saveSimpleEventLog(null, getLottery(), adminUser, EventLogType.Index_Admin, "上传图片：{" + pic4_file
							+ "}");
				}
				
				if (null != pic5_file && (StringUtils.isNotBlank(pic5_word)) && (StringUtils.isNotBlank(pic5_link))) {
					uploadPicMethod(5, pic5_file, pic5_word, pic5_link);
					// 记录操作日志
					eventLogManager.saveSimpleEventLog(null, getLottery(), adminUser, EventLogType.Index_Admin, "上传图片：{" + pic5_file
							+ "}");
				}
				
				if (null != pic6_file && (StringUtils.isNotBlank(pic6_word)) && (StringUtils.isNotBlank(pic6_link))) {
					uploadPicMethod(6, pic6_file, pic6_word, pic6_link);
					// 记录操作日志
					eventLogManager.saveSimpleEventLog(null, getLottery(), adminUser, EventLogType.Index_Admin, "上传图片：{" + pic6_file
							+ "}");
				}
				
				
				addActionError("上传成功");
				DetachedCriteria criteria = DetachedCriteria.forClass(AdImages.class);
				criteria.addOrder(Order.asc("pos"));
				adImagesList = queryService.findByDetachedCriteria(criteria);
			} catch (DataException e) {
				addActionError(e.getMessage());
				this.logger.warn(e.getMessage());
			}
			return "upload-pic";
		}
	}
	
	
	public String deletePic() {
		try {
			String id = Struts2Utils.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				indexInfoDataEntityManager.deleteAdPic(Long.valueOf(id));
				eventLogManager.saveSimpleEventLog(null, getLottery(), adminUser, EventLogType.Index_Admin, "删除图片：{" +id 
						+ "}");
			}
			addActionError("删除成功");
			
		} catch (DataException e) {
			addActionError(e.getMessage());
			this.logger.warn(e.getMessage());
		}
		return uploadPic();
	}
	
	
	public void uploadPicMethod(Integer pos, File file, String word, String link) throws DataException {
		if (null == file)
			throw new DataException("广告" + pos + "上传的文件为空");
		if (StringUtils.isBlank(word))
			throw new DataException("广告" + pos + "连接为空");
		if (StringUtils.isBlank(link))
			throw new DataException("广告" + pos + "连接为空");
		String fileName = uploadMethod(file);
		AdImages adImages = new AdImages();
		adImages.setPos(pos);
		adImages.setName(fileName);
		adImages.setLink(link);
		adImages.setWord(word);
		indexInfoDataEntityManager.uploadAdPic(adImages);
		
		createAdImagesFile();
	}
	
	
	public void createAdImagesFile() throws DataException {
		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("base", Constant.BASEPATH);
		DetachedCriteria criteria = DetachedCriteria.forClass(AdImages.class);
		criteria.addOrder(Order.asc("pos"));
		List<AdImages> adImagesList = queryService.findByDetachedCriteria(criteria);
		contents.put("adImagesList", adImagesList);
		try {
			TemplateGenerator tg = new TemplateGenerator(Constant.ROOTPATH + "/WEB-INF/content/admin/info");
			tg.create("index-adImages.ftl", contents, "index-adImages.html", Constant.ROOTPATH + "/html/info/adImages");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
	}
	
	
	private String uploadMethod(File uploadFile) throws DataException {
		String context = Constant.ROOTPATH;
		String fileName = "" + System.currentTimeMillis() + RandomUtils.generateLowerString(6) + ".jpg";
		File fileTemp = new File(FileUtils.combinePath(context, FileUtils.path));
		if (!fileTemp.isDirectory()) {
			fileTemp.mkdirs();
		}
		File file = new File(FileUtils.combinePath(context, FileUtils.path + fileName));
		try {
			if (!FileUtils.isImage(uploadFile)) {
				throw new DataException("上传的文件不是图片");
			}
			FileUtils.copy(uploadFile, file);
		} catch (IOException e) {
			throw new DataException("上传图片出错");
		}
		return FileUtils.path + fileName;
	}

	public String makeIndex() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new DataException("权限不足！");
			}
			this.indexPer();			
			createIndexFile(contents, "/WEB-INF/content/admin/info/index-per.ftl", "index.html");
			
			// 记录操作日志
			eventLogManager.saveSimpleEventLog(null, getLottery(), adminUser, EventLogType.Index_Admin, "首页生成");
			addActionMessage("生成首页成功!");
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("生成首页失败!");
		}
		return staticIndex();
	}
	
	public void makeIndexTask() {
		try {
			this.indexPer();
			createIndexFile(contents, "/WEB-INF/content/admin/info/index-per.ftl", "index.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成静态文件工具类
	 * @param contents
	 * @param sourcefile
	 * @param destinationFileName
	 * @throws DataException
	 */
	private void createIndexFile(Map<String,Object> contents,String sourcefile,String destinationFileName)throws DataException{
		try {
			TemplateGenerator tg=new TemplateGenerator(true);

			tg.create(sourcefile, contents, destinationFileName, Constant.ROOTPATH+"/");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
	}


	/**
	 * @return the type
	 */
	public InfoType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(InfoType type) {
		this.type = type;
	}

	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public File getPic1_file() {
		return pic1_file;
	}

	public void setPic1_file(File pic1_file) {
		this.pic1_file = pic1_file;
	}

	public String getPic1_word() {
		return pic1_word;
	}

	public void setPic1_word(String pic1_word) {
		this.pic1_word = pic1_word;
	}

	public String getPic1_link() {
		return pic1_link;
	}

	public void setPic1_link(String pic1_link) {
		this.pic1_link = pic1_link;
	}

	public List<AdImages> getAdImagesList() {
		return adImagesList;
	}

	public void setAdImagesList(List<AdImages> adImagesList) {
		this.adImagesList = adImagesList;
	}

	public File getPic2_file() {
		return pic2_file;
	}

	public void setPic2_file(File pic2_file) {
		this.pic2_file = pic2_file;
	}

	public String getPic2_word() {
		return pic2_word;
	}

	public void setPic2_word(String pic2_word) {
		this.pic2_word = pic2_word;
	}

	public String getPic2_link() {
		return pic2_link;
	}

	public void setPic2_link(String pic2_link) {
		this.pic2_link = pic2_link;
	}

	public File getPic3_file() {
		return pic3_file;
	}

	public void setPic3_file(File pic3_file) {
		this.pic3_file = pic3_file;
	}

	public String getPic3_word() {
		return pic3_word;
	}

	public void setPic3_word(String pic3_word) {
		this.pic3_word = pic3_word;
	}

	public String getPic3_link() {
		return pic3_link;
	}

	public void setPic3_link(String pic3_link) {
		this.pic3_link = pic3_link;
	}

	public File getPic4_file() {
		return pic4_file;
	}

	public void setPic4_file(File pic4_file) {
		this.pic4_file = pic4_file;
	}

	public String getPic4_word() {
		return pic4_word;
	}

	public void setPic4_word(String pic4_word) {
		this.pic4_word = pic4_word;
	}

	public String getPic4_link() {
		return pic4_link;
	}

	public void setPic4_link(String pic4_link) {
		this.pic4_link = pic4_link;
	}
	
	public File getPic5_file() {
		return pic5_file;
	}

	public void setPic5_file(File pic5_file) {
		this.pic5_file = pic5_file;
	}

	public String getPic5_word() {
		return pic5_word;
	}

	public void setPic5_word(String pic5_word) {
		this.pic5_word = pic5_word;
	}

	public String getPic5_link() {
		return pic5_link;
	}

	public void setPic5_link(String pic5_link) {
		this.pic5_link = pic5_link;
	}

	public File getPic6_file() {
		return pic6_file;
	}

	public void setPic6_file(File pic6_file) {
		this.pic6_file = pic6_file;
	}

	public String getPic6_word() {
		return pic6_word;
	}

	public void setPic6_word(String pic6_word) {
		this.pic6_word = pic6_word;
	}

	public String getPic6_link() {
		return pic6_link;
	}

	public void setPic6_link(String pic6_link) {
		this.pic6_link = pic6_link;
	}

	public List<NewsInfoData> getGonggaoList() {
		return gonggaoList;
	}
	public void setGonggaoList(List<NewsInfoData> gonggaoList) {
		this.gonggaoList = gonggaoList;
	}
	public List<TradeSuccessScheme> getNewestWonSubcriptionList() {
		return newestWonSubcriptionList;
	}
	public void setNewestWonSubcriptionList(List<TradeSuccessScheme> newestWonSubcriptionList) {
		this.newestWonSubcriptionList = newestWonSubcriptionList;
	}	
	public List<NewsInfoData> getMeinvForecastList() {
		return meinvForecastList;
	}
	public void setMeinvForecastList(List<NewsInfoData> meinvForecastList) {
		this.meinvForecastList = meinvForecastList;
	}
	
	public List<NewsInfoData> getZhuanjiaForecastList() {
		return zhuanjiaForecastList;
	}
	public void setZhuanjiaForecastList(List<NewsInfoData> zhuanjiaForecastList) {
		this.zhuanjiaForecastList = zhuanjiaForecastList;
	}
	public long getYi() {
		return yi;
	}	
	public long getQianwan() {
		return qianwan;
	}
	public long getBaiwan() {
		return baiwan;
	}
	public long getShiwan() {
		return shiwan;
	}
	public long getWan() {
		return wan;
	}
	public Map<String, List<JczqScheme>> getTopSchemeMap() {
		return topSchemeMap;
	}


	public List<NewsInfoData> getJcSkillsList() {
		return jcSkillsList;
	}


	public void setJcSkillsList(List<NewsInfoData> jcSkillsList) {
		this.jcSkillsList = jcSkillsList;
	}	
}
