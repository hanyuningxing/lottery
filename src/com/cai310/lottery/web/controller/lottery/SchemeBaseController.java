package com.cai310.lottery.web.controller.lottery;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.event.UserNewestLogSupport;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.BaodiState;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.SecurityUserHolder;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.lottery.SchemeUploadDTO;
import com.cai310.lottery.dto.lottery.SubscribeDTO;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.lottery.Baodi;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.info.NewsInfoDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.SchemeTempEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Executable;
import com.cai310.lottery.support.ExecuteException;
import com.cai310.lottery.support.ExecutorUtils;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.utils.CommonUtil;
import com.cai310.lottery.utils.DateUtil;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.ChineseDateUtil;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.ReflectionUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.ibm.icu.util.Calendar;

/**
 * 方案相关控制器基类
 * 
 */
@Results({
		@Result(name = "success", type = "redirectAction", params = { "actionName", "scheme", "method", "show", "id",
				"${id}" }),
		@Result(name = "subscription", location = "/WEB-INF/content/scheme/subscriptionList-list.ftl"),
		@Result(name = "wonListTable", location = "/WEB-INF/content/scheme/wonList-table.ftl"),
		@Result(name = "wonList", location = "/WEB-INF/content/scheme/wonList.ftl")})
public abstract class SchemeBaseController<T extends Scheme, E extends SchemeDTO, CF extends SchemeCreateForm, UF extends SchemeUploadForm, TT extends SchemeTemp>
		extends LotteryBaseController {
	private static final long serialVersionUID = -8500121032293869956L;

	protected Long id;
	protected T scheme;
	protected TT schemeTemp;
	protected List<Period> periods;
	protected Period period;
	protected PeriodSales singlePeriodSales;
	protected PeriodSales compoundPeriodSales;

	protected CF createForm;
	protected UF uploadForm;
	protected SubscribeForm subscribeForm;
	protected SchemeQueryForm queryForm;
	protected boolean freeSave;
	private boolean aheadOfUpload;
	protected Pagination pagination = new Pagination(20);
	
	private int timeFrame;
	/**
	 * 方案投注的方式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 */
	protected SalesMode salesMode;

	/**
	 * @return 方案的服务实例
	 * @see com.cai310.lottery.service.lottery.SchemeService
	 */
	protected abstract SchemeService<T, E> getSchemeService();
	
	protected abstract SchemeEntityManager<T> getSchemeEntityManager();

	/**免费保存方案实体管理类，由子类确定*/
	protected SchemeTempEntityManager<TT> getSchemeTempEntityManager(){return null;};

	/**
	 * @return 所属彩种
	 */
	public abstract Lottery getLotteryType();
	
	
	
	/**
	 * 销售期相关实体管理实例
	 * 
	 * @see com.cai310.lottery.service.lottery.PeriodEntityManager
	 */
	@Autowired
	protected PeriodEntityManager periodEntityManager;
	
	@Autowired
	protected QueryService queryService;

	@Autowired
	protected UserEntityManager userEntityManager;

	protected Class<T> schemeClass;
	protected Class<E> schemeDTOClass;
	protected Class<TT> schemeTempClass;

	@Autowired
	@Qualifier("schemeQueryCache")
	protected Cache schemeQueryCache;

	@Autowired
	@Qualifier("commonEternalCache")
	private Cache commonEternalCache;

	public Cache getCommonEternalCache() {
		return commonEternalCache;
	}

	public void setCommonEternalCache(Cache commonEternalCache) {
		this.commonEternalCache = commonEternalCache;
	}

	@SuppressWarnings("unchecked")
	public SchemeBaseController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
		this.schemeDTOClass = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
		this.schemeTempClass = ReflectionUtils.getSuperClassGenricType(getClass(), 4);
	}

	private List<NewsInfoData> forecastList = new ArrayList<NewsInfoData>();
	private List<NewsInfoData> skillsList = new ArrayList<NewsInfoData>();
	private List<NewsInfoData> resultList = new ArrayList<NewsInfoData>();
	private List<NewsInfoData> infoList = new ArrayList<NewsInfoData>();

	private Map<String, String> noticeNewsMap = new HashMap<String, String>();
	
	
	@Resource
	protected NewsInfoDataEntityManager newsInfoDataEntityManager;
	protected List<Subscription> newestWonSubcriptionList = Lists.newArrayList();
	protected List<NewsInfoData> gonggaoList = Lists.newArrayList();
	protected List<NewsInfoData> newsList = Lists.newArrayList();

	/* ---------------------- 控制器方法 ---------------------- */
	/**
	 * 进入首页
	 */
	public String index() {
		return list();
	}

	/**
	 * 加载当前期数据
	 */
	public void loadCrrPeriod() {
		this.preparePeriods(true);
		if (salesMode == null)
			salesMode = SalesMode.COMPOUND;

		String shareTypeStr = Struts2Utils.getParameter("shareType");
		ShareType shareType = null;
		try {
			shareType = ShareType.valueOf(shareTypeStr);
		} catch (Exception e) {
		}
		if (shareType == null)
			shareType = ShareType.SELF;

		Struts2Utils.setAttribute("shareType", shareType);
	}

	/**
	 * 历史数据
	 * 
	 */
	protected void preparePeriodsOfList(int periodSize) {
		if (null == this.periods) {
			this.periods = new ArrayList<Period>();
			List<Period> currentPeriods = periodEntityManager.findCurrentPeriods(getLotteryType());
			if (currentPeriods != null)
				this.periods.addAll(currentPeriods);

		}
		int oldSize = periodSize - this.periods.size();
		if (oldSize > 0) {
			List<Period> oldPeriods = periodEntityManager.findOldPeriods(getLotteryType(), oldSize, true);
			if (oldPeriods != null)
				this.periods.addAll(oldPeriods);
		}
		Collections.sort(this.periods, new Comparator<Period>() {
			public int compare(Period o1, Period o2) {
				return o2.getId().compareTo(o1.getId());
			}
		});
	}

	protected SalesMode getDefaultSalesModeForList() {
		return null;
	}

	/**
	 * 加载预测跟技巧新闻
	 */
	protected void loadNewsList() {
		//未开启，新版暂不用中....
		if(true)return;
		// 预测
		XDetachedCriteria criteria = new XDetachedCriteria(NewsInfoData.class);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("type", InfoType.FORECAST));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.setCacheable(true);
		criteria.addOrder(Order.desc("id"));
		Pagination pg = new Pagination(10);
		pg = queryService.findByCriteriaAndPagination(criteria, pg);
		forecastList = pg.getResult();
		// 技巧
		criteria = new XDetachedCriteria(NewsInfoData.class);
		pg = new Pagination(10);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("type", InfoType.SKILLS));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.setCacheable(true);
		criteria.addOrder(Order.desc("id"));
		pg = queryService.findByCriteriaAndPagination(criteria, pg);
		skillsList = pg.getResult();
		// 开奖结果
		criteria = new XDetachedCriteria(NewsInfoData.class);
		pg = new Pagination(10);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("type", InfoType.NOTICE));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		criteria.setCacheable(true);
		pg = queryService.findByCriteriaAndPagination(criteria, pg);
		resultList = pg.getResult();
		// 新闻
		criteria = new XDetachedCriteria(NewsInfoData.class);
		pg = new Pagination(10);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("type", InfoType.INFO));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		criteria.setCacheable(true);
		pg = queryService.findByCriteriaAndPagination(criteria, pg);

		for (Lottery lottery : Lottery.values()) {
			String key = lottery + Constant.channelNoticeNewsCacheKey;
			Element el = commonEternalCache.get(key);
			if (null != el) {
				String channelNoticeNews = (String) el.getValue();
				noticeNewsMap.put(lottery.toString(), channelNoticeNews);
			}
		}
		infoList = pg.getResult();
	}
	/**
	 * 中奖方案查询
	 */
	public String wonList() {
		this.preparePeriodsOfList(10);
		if(this.periods != null) {
			this.period = this.periods.get(0);
		}
		List<Date> days = new ArrayList(7);
		Date today = new Date();
		for(int i=0; i<7; i++) {
			days.add(DateUtil.addDate(today, -i));
		}
		Struts2Utils.setAttribute("days", days);
		return "wonList";
	}
	
	/**
	 * 构建中奖方案查询封装对象
	 */
	protected XDetachedCriteria buildWonListQueryCriteria(){
		this.preparePeriodsOfList(10);
		String periodId = Struts2Utils.getParameter("periodId");
		if (StringUtils.isNotBlank(periodId)) {
			Period periodTemp = periodEntityManager.getPeriod(Long.valueOf(periodId));
			if (null != periodTemp)
				this.period = periodTemp;
		} else if(this.periods != null && !this.periods.isEmpty()) {
			this.period = this.periods.get(0);
		}
		
		XDetachedCriteria criteria = new XDetachedCriteria(this.schemeClass, "m");
		
		String sponsorNameOrSchemeNum = Struts2Utils.getParameter("sponsorNameOrSchemeNum");
		Lottery lotteryType = this.getLotteryType();
		if(StringUtils.isNotBlank(sponsorNameOrSchemeNum) && Lottery.isSchemeNumber(sponsorNameOrSchemeNum, lotteryType)) {			 
			Long schemeId = lotteryType.getSchemeId(sponsorNameOrSchemeNum);
			criteria.add(Restrictions.eq("m.id", schemeId));
		} else {
			if(StringUtils.isNotBlank(sponsorNameOrSchemeNum)){
				criteria.add(Restrictions.eq("m.sponsorName", sponsorNameOrSchemeNum));
			}
			criteria.add(Restrictions.eq("m.periodId", this.period.getId()));
			
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR_OF_DAY, 0);
			c.add(Calendar.MINUTE, 0);
			c.add(Calendar.SECOND, 0);
			if(timeFrame!=7) {
				c.add(Calendar.DAY_OF_MONTH, -timeFrame);								
				
				Date date =  c.getTime();
				String dateStr = com.cai310.utils.DateUtil.dateToStr(date, "yyyy-MM-dd");
				Date startDate = com.cai310.utils.DateUtil.strToDate(dateStr + " 00:00");				
				Date endDate = com.cai310.utils.DateUtil.strToDate(dateStr + " 23:59");
				
				criteria.add(Restrictions.gt("m.prizeSendTime", startDate));
				criteria.add(Restrictions.lt("m.prizeSendTime", endDate));
			}
				criteria.add(Restrictions.eq("m.prizeSended", true));
				criteria.addOrder(Order.desc("prizeSendTime"));
		}
		return criteria;
	}
	
	/**
	 * 进入方案列表
	 */
	public String list() {
		try {
			this.preparePeriods(true);
			this.preparePeriodsOfList(10);
			this.loadNewsList();
			String key = getRequestKey();

			if (queryForm != null) {
				if (null != queryForm.getChooseType()) {
					if (Integer.valueOf(2).equals(queryForm.getChooseType())) {
						// 我的方案
						User user = this.getLoginUser();
						if (user == null) {
							CookieUtil.addReUrlCookie();
							this.addActionMessage("您还未登录，请登陆后操作.");
							return GlobalResults.FWD_LOGIN;
						}
						key = key + "&userId=" + user.getId();
					}
				}
			}
			String periodNumber = Struts2Utils.getRequest().getParameter("periodNumber");
			if (StringUtils.isNotBlank(periodNumber)) {
				Period periodTemp = periodEntityManager.getPeriod(Long.valueOf(periodNumber));
				if (null != periodTemp)
					this.period = periodTemp;
			}
			Element el = schemeQueryCache.get(key);
			if (el == null) {
				this.pagination = queryService
						.findByCriteriaAndPagination(buildListDetachedCriteria(), this.pagination);
				el = new Element(key, this.pagination);
				this.schemeQueryCache.put(el);
			} else {
				this.pagination = (Pagination) el.getValue();
			}
			loadCrrPeriod();// /加载当前期数据
			return "list";
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	/**
	 * 构建方案查询封装对象
	 */
	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria criteria = new XDetachedCriteria(this.schemeClass, "m");

		criteria.add(Restrictions.eq("m.periodId", this.period.getId()));

		criteria.add(Restrictions.eq("m.shareType", ShareType.TOGETHER));

		if (queryForm != null) {
			if (StringUtils.isNotBlank(queryForm.getSponsorName())) {
				if (!"用户名或方案号".equals(queryForm.getSponsorName().trim())) {
					criteria.add(Restrictions.or(Restrictions.eq("m.sponsorName", queryForm.getSponsorName()),
							Restrictions.eq("m.sponsorName", queryForm.getSponsorName())));
				}
			}
			if (StringUtils.isNotBlank(queryForm.getSchemeNumber())) {
				Long schemeId = this.period.getLotteryType().getSchemeId(queryForm.getSchemeNumber());
				if (schemeId != null)
					criteria.add(Restrictions.eq("m.id", schemeId));
			}
			if (null != queryForm.getCostType()) {
				queryForm.setMinMaxCost();
			}
			if (null != queryForm.getChooseType()) {
				if (Integer.valueOf(1).equals(queryForm.getChooseType())) {
					// 以保底方案
					criteria.add(Restrictions.eq("m.sendToPrint", true));
				} else if (Integer.valueOf(2).equals(queryForm.getChooseType())) {
					// 我的方案
					User user = this.getLoginUser();
					if (user != null) {
						criteria.add(Restrictions.eq("m.sponsorId", user.getId()));
					}
				}
			}
			if (queryForm.getMinSchemeCost() != null)
				criteria.add(Restrictions.ge("m.schemeCost", queryForm.getMinSchemeCost()));

			if (queryForm.getMaxSchemeCost() != null)
				criteria.add(Restrictions.le("m.schemeCost", queryForm.getMaxSchemeCost()));

			if (queryForm.getSchemeState() != null)
				criteria.add(Restrictions.eq("m.state", queryForm.getSchemeState()));

			if (queryForm.getOrderType() != null) {
				criteria.addOrder(Order.desc("m.orderPriority"));
				switch (queryForm.getOrderType()) {
				case CREATE_TIME_DESC:
					criteria.addOrder(Order.desc("m.id"));
					break;
				case CREATE_TIME_ASC:
					criteria.addOrder(Order.asc("m.id"));
					break;
				case PROCESS_RATE_DESC:
					criteria.addOrder(Order.desc("m.progressRate"));
					break;
				case PROCESS_RATE_ASC:
					criteria.addOrder(Order.asc("m.progressRate"));
					break;
				case SCHEME_COST_DESC:
					criteria.addOrder(Order.desc("m.schemeCost"));
					break;
				case SCHEME_COST_ASC:
					criteria.addOrder(Order.asc("m.schemeCost"));
					break;
				}
				criteria.addOrder(Order.asc("m.state"));
			} else {
				criteria.addOrder(Order.asc("m.state"));
				criteria.addOrder(Order.desc("m.orderPriority"));
				criteria.addOrder(Order.desc("m.progressRate"));
				criteria.addOrder(Order.desc("m.id"));
			}
		} else {
			criteria.addOrder(Order.asc("m.state"));
			criteria.addOrder(Order.desc("m.orderPriority"));
			criteria.addOrder(Order.desc("m.progressRate"));
			criteria.addOrder(Order.desc("m.id"));
		}

		return criteria;
	}

	/**
	 * 准备销售期数据
	 * 
	 * @param onlyOnSale
	 *            是否只显示在售的
	 */
	protected void preparePeriods(boolean onlyOnSale) {
		// 所有在售期

		List<Period> currentPeriods = periodEntityManager.findCurrentPeriods(getLotteryType());

		if (getLotteryType() == Lottery.DCZC) {
			currentPeriods.addAll(periodEntityManager.getDrawPeriodList(Lottery.DCZC, 20));
		}

		if (currentPeriods != null && !currentPeriods.isEmpty()) {
			this.periods = new ArrayList<Period>();
			String periodNumber = Struts2Utils.getRequest().getParameter("periodNumber");
			for (Period p : currentPeriods) {
				if (onlyOnSale && !(p.isOnSale() || p.isPaused()))
					continue;

				this.periods.add(p);
				if (this.period == null && StringUtils.isNotBlank(periodNumber)
						&& p.getPeriodNumber().equals(periodNumber))
					this.period = p;
			}

			if (this.periods.isEmpty())
				this.periods.add(currentPeriods.get(currentPeriods.size() - 1));
			if (this.period == null)
				this.period = this.periods.get(0);
			Period firstOnSalePeriod = null;
			for (Period p : periods) {
				if (p.isCurrent()) {
					// /当前销售
					if (firstOnSalePeriod == null)
						firstOnSalePeriod = p;
					p.setPeriodNumberDisplay(2);
				} else {
					p.setPeriodNumberDisplay(0);
				}
			}
			for (Period p : periods) {
				if (null != firstOnSalePeriod && p.getId().equals(firstOnSalePeriod.getId())) {
					p.setPeriodNumberDisplay(1);
				}
			}
			Collections.sort(periods, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((Period) o1).getId() > ((Period) o2).getId() ? 1 : 0;
				}
			});
			if (this.period == null && !this.periods.isEmpty())
				this.period = (firstOnSalePeriod != null) ? firstOnSalePeriod : this.periods.get(0);

			if (this.period != null) {
				this.singlePeriodSales = periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(),
						SalesMode.SINGLE));
				this.compoundPeriodSales = periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(),
						SalesMode.COMPOUND));
			}
		}
	}

	/**
	 * 进入我的投注
	 */
	public String myList() {
		try {
			User loginUser = getLoginUser();
			if (loginUser == null)
				throw new WebDataException("您还未登录，请先登录.");

			String key = getRequestKey() + loginUser.getId();
			Element el = schemeQueryCache.get(key);
			if (el == null) {
				pagination = getSchemeEntityManager().findMyScheme(loginUser.getId(), buildMySchemeQueryDTO(),
						pagination);
				el = new Element(key, this.pagination);
				schemeQueryCache.put(el);
			} else {
				pagination = (Pagination) el.getValue();
			}
			loadCrrPeriod();// /加载当前期数据
			this.loadNewsList();
			return "myList";
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}

	protected SchemeQueryDTO buildMySchemeQueryDTO() {
		SchemeQueryDTO dto = new SchemeQueryDTO();
		if (queryForm != null) {
			if (StringUtils.isNotBlank(queryForm.getPeriodNumber()))
				dto.setPeriodNumber(queryForm.getPeriodNumber());
			dto.setWon(queryForm.isWon());
			dto.setState(queryForm.getSchemeState());
		}
		return dto;
	}

	/**
	 * 进入添加新方案
	 */
	public String editNew() {
		try {
			if (getLotteryType() == Lottery.DCZC) {
				this.preparePeriods(false);
			} else {
				this.preparePeriods(true);

			}
			this.loadNewsList();
			if (this.period == null)
				throw new WebDataException("当前没有在售期次.");

			if (salesMode == null)
				salesMode = SalesMode.COMPOUND;

			String shareTypeStr = Struts2Utils.getParameter("shareType");
			ShareType shareType = null;
			try {
				shareType = ShareType.valueOf(shareTypeStr);
			} catch (Exception e) {
			}
			if (shareType == null)
				shareType = ShareType.SELF;

			Struts2Utils.setAttribute("shareType", shareType);
			Struts2Utils.setAttribute("currDate", new Date());

			this.getPageInfo();
			
			return doEditNew();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	protected String doEditNew() throws Exception {
		switch (salesMode) {
		case COMPOUND:
			return "editNewCompound";
		case SINGLE:
			return "editNewSingle";
		default:
			throw new WebDataException("投注方式不合法.");
		}
	}
	
	/**
	 * 获取投注页面相关的信息
	 */
	protected void getPageInfo(){
		gonggaoList = newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.INFO, Lottery.JCZQ, InfoSubType.GONGGAO, 3, false);
		newsList = newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.INFO, Lottery.JCZQ, null, 5, false);
		Struts2Utils.setAttribute("gonggaoList", gonggaoList);
		Struts2Utils.setAttribute("newsList", newsList);
	}

	/**
	 * 保存新方案
	 */
	public String create() {
		try {
			checkRepeatRequest();
			
			User loginUser = getLoginUser();
			if (loginUser == null) {
				throw new WebDataException("您还未登录，请先登录.");
			}
			
			final E schemeDTO = buildSchemeDTO();
			
			if(freeSave){//免费保存
				TT schemeTemp = buildSchemeTemp(schemeDTO);
				getSchemeTempEntityManager().saveScheme(schemeTemp);
				addActionMessage("方案免费保存成功！");
				if (Struts2Utils.isAjaxRequest()){
					this.jsonMap.put(KEY_SCHEME_TEMP,true);
					this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(schemeTemp));
				}
			}else{//真实购买
				ExecutorUtils.exec(new Executable() {
					public void run() throws ExecuteException {
						scheme = getSchemeService().createScheme(schemeDTO);
					}
				}, 3);

				addActionMessage("发起方案成功！");
				UserNewestLogSupport.submitScheme(scheme);

				if (Struts2Utils.isAjaxRequest()){
					this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
				}else {
					this.id = scheme.getId();
					this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
				}
			}
			
			return success();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}
	
	/**
	 * 构建免费保存的方案实体
	 * 可由子类重载-覆写补充相关信息以达到实际需要
	 * @return
	 * @throws WebDataException
	 */
	protected TT buildSchemeTemp(E schemeDTO) throws WebDataException{
		TT schemeTemp;
		try {
			schemeTemp = this.schemeTempClass.newInstance();
		} catch (InstantiationException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("创建数据传输对象发生异常.");
		} catch (IllegalAccessException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("创建数据传输对象发生异常.");
		}
		//获取需要保存的属性
		schemeTemp.setContent(schemeDTO.getContent());
		schemeTemp.setLotteryType(getLotteryType());
		schemeTemp.setMode(schemeDTO.getMode());
		schemeTemp.setMultiple(schemeDTO.getMultiple());
		schemeTemp.setPeriodId(schemeDTO.getPeriodId());
		Period period = periodEntityManager.getPeriod(schemeDTO.getPeriodId());
		if(period==null){
			throw new WebDataException("获取不到相关销售期号.");
		}
		schemeTemp.setPeriodNumber(period.getPeriodNumber());
		schemeTemp.setSchemeCost(schemeDTO.getSchemeCost());
		schemeTemp.setSecretType(schemeDTO.getSecretType());
		schemeTemp.setShareType(ShareType.SELF);
		schemeTemp.setSponsorId(schemeDTO.getSponsorId());
		User user = userEntityManager.getUser(schemeDTO.getSponsorId());
		if(user==null){
			throw new WebDataException("获取不到相关用户信息.");
		}
		schemeTemp.setSponsorName(user.getUserName());
		schemeTemp.setUnits(schemeDTO.getUnits());
		schemeTemp.setProgressRate(0.0f);
		schemeTemp.setMinSubscriptionCost(new BigDecimal(1));
		
		return schemeTemp;
	}
	
	/**
	 * 显示免费保存的临时方案
	 */
	public String showTemp() {
		try {
			if (this.id != null)
				this.schemeTemp = getSchemeTempEntityManager().getScheme(this.id);
			
			if (this.schemeTemp == null)
				throw new WebDataException("方案不存在.");

			this.period = this.periodEntityManager.getPeriod(schemeTemp.getPeriodId());
			if (this.period == null){
				throw new WebDataException("销售期不存在.");
			}

			if (this.period.isOnSale()) {
				PeriodSales periodSales = this.periodEntityManager.getPeriodSales(new PeriodSalesId(
						this.period.getId(), this.schemeTemp.getMode()));
				if (periodSales.isOnSale()) {
					Date endSubscribeTime = periodSales.getEndJoinTime();
					handleCanSubscribe(endSubscribeTime);
				}
			}
			Struts2Utils.setAttribute("canViewDetail", true);
			Struts2Utils.setAttribute("canSubscribe", true);
			loadCrrPeriod();// /加载当前期数据
			return doShowTemp();
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}
	
	

	/**
	 * 显示方案
	 */
	public String show() {
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

			this.period = this.periodEntityManager.getPeriod(scheme.getPeriodId());
			if (this.period == null){
				throw new WebDataException("销售期不存在.");
			}

			if (this.scheme.isCanSubscribe() && this.period.isOnSale()) {
				PeriodSales periodSales = this.periodEntityManager.getPeriodSales(new PeriodSalesId(
						this.period.getId(), this.scheme.getMode()));
				if (periodSales.isOnSale()) {
					Date endSubscribeTime = periodSales.getEndJoinTime();
					handleCanSubscribe(endSubscribeTime);
				}
			}

			User user = getLoginUser();

			if (this.scheme.getSubscriptionLicenseType() == SubscriptionLicenseType.PASSWORD_LICENSE
					&& (user == null || !user.getId().equals(scheme.getSponsorId()))) {
				Struts2Utils.setAttribute("needPassword", true);
			}

			boolean canViewDetail = "true".equalsIgnoreCase(canViewDetail(scheme, period, user));
			Struts2Utils.setAttribute("canViewDetail", canViewDetail);

			// 获取发起人认购的总金额
			BigDecimal sponsorSubscribedCost = getSchemeEntityManager().countSubscribedCost(scheme.getId(),
					scheme.getSponsorId());
			if (sponsorSubscribedCost == null)
				sponsorSubscribedCost = BigDecimal.ZERO;
			double sponsorPercent = sponsorSubscribedCost.doubleValue() * 100 / scheme.getSchemeCost();
			Struts2Utils.setAttribute("sponsorSubscribedCost", sponsorSubscribedCost);
			Struts2Utils.setAttribute("sponsorPercent", sponsorPercent);

			if (user != null) {
				// 获取当前登录用户认购的总金额和还可以认购的总金额
				BigDecimal userSubscribedCost = getSchemeEntityManager().countSubscribedCost(scheme.getId(),
						user.getId());
				Struts2Utils.setAttribute("userSubscribedCost", userSubscribedCost);

				BigDecimal account = userEntityManager.getUser(user.getId()).getRemainMoney();
				double canSubscribedCost = 0.0d;
				if (account != null) {
					canSubscribedCost = Math.min(account.doubleValue(), scheme.getRemainingCost().doubleValue());
				}
				Struts2Utils.setAttribute("canSubscribedCost", canSubscribedCost);

				Struts2Utils.setAttribute("loggedUser", user);
				Struts2Utils.setAttribute("account", account);				
			}
			boolean isSaleEnded = getSchemeService().isSaleEnded(scheme.getId());
			boolean canCancelScheme = scheme.isCanCancel() && !isSaleEnded;
			Struts2Utils.setAttribute("canCancelScheme", canCancelScheme);
			
			loadCrrPeriod();// /加载当前期数据
			return doShow();
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}
		

	protected void handleCanSubscribe(Date endSubscribeTime) throws WebDataException {
		if (endSubscribeTime.getTime() > System.currentTimeMillis()) {
			Map<String, Integer> timerMap = CommonUtil.getTimerMap(endSubscribeTime);
			Struts2Utils.setAttribute("timerMap", timerMap);
			Struts2Utils.setAttribute("canSubscribe", true);
		}
	}
	public String canViewDetail(T scheme, Period period, User user) {
		AdminUser adminUser = getAdminUser();
		if (null != adminUser) {
			return "true";
		}
		if (user != null && user.getId().equals(scheme.getSponsorId()))
			return "true";
		switch (scheme.getSecretType()) {
		case FULL_PUBLIC:
			return "true";
		case DRAWN_PUBLIC:
			if (period.isDrawed())
				return "true";
			else
				return SecretType.DRAWN_PUBLIC.getSecretName();
		}
		return "方案保密";
	}
	public AdminUser getAdminUser() {
			Object o = SecurityUserHolder.getCurrentUser();
			if (o instanceof AdminUser) {
				return (AdminUser) o;
			} else {
				return null;
			}
	}

	/**
	 * 可在子类重载此方法，加载其他信息
	 */
	protected String doShow() throws WebDataException {		
		return "show";
	}
	
	/**
	 * 计算方案剩余的加入时间
	 * @param endTime
	 */
	protected void remainTimeOfScheme(Date endTime){
		if(endTime!=null && endTime.after(new Date())){
			StringBuffer timeStr = new StringBuffer();
			long day = DateUtil.getDifference(new Date(),endTime,Calendar.DATE);
			long hour = DateUtil.getDifference(new Date(),endTime,Calendar.HOUR)%24;
			long minute = DateUtil.getDifference(new Date(),endTime,Calendar.MINUTE)%60;
			timeStr.append(day).append("天").append(hour).append("小时").append(minute).append("分钟");
			Struts2Utils.setAttribute("remainTime", timeStr);
		}
	}
	
	
	
	/**
	 * 可在子类重载此方法，加载其他信息
	 */
	protected String doShowTemp() throws WebDataException {
		return "showTemp";
	}

	public String subscribe() {
		try {
			final SubscribeDTO dto = buildSubscribeDTO();

			ExecutorUtils.exec(new Executable() {
				public void run() throws ExecuteException {
					scheme = getSchemeService().subscribe(dto);
				}
			}, 3);

			addActionMessage("操作成功！");
			if (Struts2Utils.isAjaxRequest())
				this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
			else
				this.id = this.scheme.getId();

			return success();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ExecuteException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}

	/**真实购买方案地址*/
	protected String getSchemeUrl(T scheme) {
		return Struts2Utils.getRequest().getContextPath() + "/" + scheme.getLotteryType().getKey()
				+ "/scheme-" + scheme.getSchemeNumber()+".html";
	}
	
	/**免费保存方案地址*/
	protected String getSchemeUrl(SchemeTemp scheme) {
		return Struts2Utils.getRequest().getContextPath() + "/" + scheme.getLotteryType().getKey()
				+ "/scheme!showTemp.action?id=" + scheme.getId();
	}

	/**
	 * 进入上传方案
	 */
	public String editUpload() {
		try {
			if (id != null)
				scheme = getSchemeEntityManager().getScheme(id);
			else {
				String schemeNumber = Struts2Utils.getRequest().getParameter("schemeNumber");
				if (StringUtils.isBlank(schemeNumber))
					throw new WebDataException("方案号为空.");
				scheme = getSchemeEntityManager().getSchemeBy(schemeNumber);
			}

			if (scheme == null)
				throw new WebDataException("方案不存在.");
			else if (scheme.isUploaded())
				throw new WebDataException("方案内容已上传,不能再上传方案.");

			this.period = this.periodEntityManager.getPeriod(scheme.getPeriodId());
			if (!this.period.isOnSale())
				throw new WebDataException("已截止销售,不能上传方案.");

			PeriodSales periodSales = this.periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(),
					this.scheme.getMode()));
			if (!periodSales.isOnSale())
				throw new WebDataException("已截止销售,不能上传方案.");

			Date endTime;
			switch (this.scheme.getShareType()) {
			case TOGETHER:
				endTime = periodSales.getShareEndInitTime();
				break;
			case SELF:
				endTime = periodSales.getSelfEndInitTime();
				break;
			default:
				throw new WebDataException("方案的分享类型不合法.");
			}
			if (System.currentTimeMillis() > endTime.getTime())
				throw new WebDataException("已过销售截止时间,不能上传方案.");

			return "editUpload";
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	/**
	 * 上传方案
	 */
	public String upload() {
		try {
			SchemeUploadDTO dto = buildSchemeUploadDTO();
			this.scheme = this.getSchemeService().uploadScheme(dto);

			addActionMessage("上传方案成功！");

			if (Struts2Utils.isAjaxRequest())
				this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
			else {
				this.id = scheme.getId();
				this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
			}
			return success();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		}

		return error();
	}

	/**
	 * 计算单式注数
	 */
	public String calcSingleBetUnits() {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (this.createForm == null)
				throw new WebDataException("表单数据为空.");
			ContentBean bean = this.createForm.buildSingleContentBean();
			int betUnits = bean.getUnits();

			map.put("success", true);
			map.put("betUnits", betUnits);
		} catch (DataException e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			this.logger.warn("计算注数发生异常！", e);
			map.put("success", false);
			map.put("msg", "计算注数发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}

	/**
	 * 计算单式注数(先发起后上传使用)
	 */
	public String calcAheadOfSingleBetUnits() {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (this.uploadForm == null)
				throw new WebDataException("表单数据为空.");
			ContentBean bean = this.uploadForm.buildSingleContentBean();
			int betUnits = bean.getUnits();

			map.put("success", true);
			map.put("betUnits", betUnits);
		} catch (DataException e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			this.logger.warn("计算注数发生异常！", e);
			map.put("success", false);
			map.put("msg", "计算注数发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}

	/**
	 * 构建方案上传数据传输对象
	 * 
	 * @return 方案上传数据传输对象
	 * @throws WebDataException
	 */
	protected SchemeUploadDTO buildSchemeUploadDTO() throws WebDataException {
		if (this.uploadForm == null)
			throw new WebDataException("表单数据为空.");

		if (this.uploadForm.getSchemeId() == null)
			throw new WebDataException("方案ID为空.");

		SchemeUploadDTO dto = new SchemeUploadDTO();
		dto.setSchemeId(this.uploadForm.getSchemeId());

		T uploadScheme = this.getSchemeEntityManager().getScheme(dto.getSchemeId());
		if (uploadScheme == null)
			throw new WebDataException("方案不存在.");
		else if (uploadScheme.isUploaded())
			throw new WebDataException("方案内容已上传,不能再上传方案.");

		try {
			ContentBean bean;
			switch (uploadScheme.getMode()) {
			case COMPOUND:
				bean = this.uploadForm.buildCompoundContentBean();
				break;
			case SINGLE:
				bean = this.uploadForm.buildSingleContentBean();
				break;
			default:
				throw new WebDataException("方案投注方式不合法.");
			}
			if (bean.getUnits() == null || !bean.getUnits().equals(uploadScheme.getUnits()))
				throw new WebDataException("上传的方案注数不对.");

			dto.setUploadContent(bean.getContent());
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}

		return dto;
	}

	/**
	 * 撤销方案
	 */
	public String cancel() {
		try {
			if (this.id == null)
				throw new WebDataException("方案ID不能为空.");

			User loggedUser = getLoginUser();
			if (loggedUser == null)
				throw new WebDataException("您还未登录,请登录后再操作.");

			this.scheme = getSchemeEntityManager().getScheme(id);
			if (this.scheme == null)
				throw new WebDataException("方案不存在.");
			else if (!loggedUser.getId().equals(scheme.getSponsorId()))
				throw new WebDataException("您非方案发起人,不能撤销方案.");

			this.getSchemeService().cancelSchemeBySponsor(this.scheme.getId());

			addActionMessage("撤销方案成功！");
			if (Struts2Utils.isAjaxRequest()) {
				this.scheme = getSchemeEntityManager().getScheme(this.scheme.getId());
				this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
			} else {
				this.id = this.scheme.getId();
			}

			return success();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}

	/**
	 * 构建方案创建数据传输对象
	 * 
	 * @return 方案创建数据传输对象
	 * @throws WebDataException
	 */
	protected E buildSchemeDTO() throws WebDataException {
		if (this.createForm == null)
			throw new WebDataException("表单数据为空.");

		E schemeDTO;
		try {
			schemeDTO = this.schemeDTOClass.newInstance();
		} catch (InstantiationException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("创建数据传输对象发生异常.");
		} catch (IllegalAccessException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("创建数据传输对象发生异常.");
		}

		User user = getLoginUser();
		if (user == null)
			throw new WebDataException("您还未登录,请登录后再操作.");
		schemeDTO.setSponsorId(user.getId());

		if (this.createForm.getPeriodId() == null)
			throw new WebDataException("期ID为空.");
		schemeDTO.setPeriodId(this.createForm.getPeriodId());

		if (this.createForm.getMode() == null)
			throw new WebDataException("方案投注方式为空.");
		schemeDTO.setMode(this.createForm.getMode());

		if (this.createForm.getSecretType() == null)
			throw new WebDataException("方案保密方式为空.");
		schemeDTO.setSecretType(this.createForm.getSecretType());

		if (this.createForm.getSubscriptionLicenseType() == null)
			schemeDTO.setSubscriptionLicenseType(SubscriptionLicenseType.PUBLIC_LICENSE);
		else if (this.createForm.getSubscriptionLicenseType() == SubscriptionLicenseType.PASSWORD_LICENSE) {
			if (StringUtils.isBlank(this.createForm.getSubscriptionPassword()))
				throw new WebDataException("认购密码为空.");
			String pwd = this.createForm.getSubscriptionPassword().trim().toLowerCase();
			if (!pwd.matches("[a-z0-9]{6,10}"))
				throw new WebDataException("密码必须由6至10个英文字母或数字组成.");

			schemeDTO.setSubscriptionPassword(pwd);
			schemeDTO.setSubscriptionLicenseType(SubscriptionLicenseType.PASSWORD_LICENSE);
		} else {
			schemeDTO.setSubscriptionLicenseType(this.createForm.getSubscriptionLicenseType());
		}

		if (StringUtils.isNotBlank(this.createForm.getDescription())) {
			String desc = this.createForm.getDescription().trim();
			if (desc.getBytes().length > Scheme.DESCRIPTION_MAX_LENGTH)
				throw new WebDataException("方案描述内容长度不能超过" + Scheme.DESCRIPTION_MAX_LENGTH + "个字符,一个汉字占两个字符.");
			// 这里不过滤内容，在显示的时候进行过滤后再显示
			schemeDTO.setDescription(desc);
		}

		if (this.createForm.getSchemeCost() == null || this.createForm.getSchemeCost() < 2)
			throw new WebDataException("方案金额为空或不合法.");
		schemeDTO.setSchemeCost(this.createForm.getSchemeCost());

		if (this.createForm.getMultiple() == null || this.createForm.getMultiple() < 1)
			throw new WebDataException("方案倍数为空或不合法.");
		schemeDTO.setMultiple(this.createForm.getMultiple());

		if (this.createForm.getUnits() == null || this.createForm.getUnits() < 1)
			throw new WebDataException("方案注数为空或不合法.");
		else {
			if (this.createForm.getMode() == SalesMode.SINGLE && this.createForm.getUnits() > Constant.MAX_SINGLE_UNITS) {
				throw new WebDataException("单式方案注数不能大于" + Constant.MAX_SINGLE_UNITS + "注.");
			}
//			if (this.createForm.getMode() == SalesMode.COMPOUND && this.createForm.getUnits() > Constant.MAX_UNITS)
//				throw new WebDataException("复式方案注数不能大于" + Constant.MAX_UNITS + "注.");
		}
		schemeDTO.setUnits(this.createForm.getUnits());

		int cost = schemeDTO.getUnits() * schemeDTO.getMultiple() * this.createForm.getUnitsMoney();
		if (cost != schemeDTO.getSchemeCost())
			throw new WebDataException("根据注数计算出来的金额与提交的方案金额不一致.");

		if(this.freeSave){
			//免费保存
		}else if (this.createForm.getShareType() == null){
			throw new WebDataException("方案分享方式为空.");
		}else {
			switch (this.createForm.getShareType()) {
			case TOGETHER:
				if (this.createForm.getCommissionRate() != null) {
					if (this.createForm.getCommissionRate() < 0
							|| this.createForm.getCommissionRate() > Constant.COMMISSION_MAX_RATE)
						throw new WebDataException("佣金率不能小于0或者大于" + (Constant.COMMISSION_MAX_RATE * 100) + "%.");

					schemeDTO.setCommissionRate(this.createForm.getCommissionRate());
				}

				if (this.createForm.getMinSubscriptionCost() != null) {
					BigDecimal minSubscriptionCost = this.createForm.getMinSubscriptionCost().setScale(
							Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);
					if (minSubscriptionCost.doubleValue() != this.createForm.getMinSubscriptionCost().doubleValue())
						throw new WebDataException("最低认购金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

					schemeDTO.setMinSubscriptionCost(minSubscriptionCost);
				}

				BigDecimal totalCost = BigDecimal.ZERO;
				if (this.createForm.getSubscriptionCost() == null)
					throw new WebDataException("方案认购金额为空.");
				else {
					BigDecimal subscriptionCost = this.createForm.getSubscriptionCost().setScale(
							Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);
					if (subscriptionCost.doubleValue() != this.createForm.getSubscriptionCost().doubleValue())
						throw new WebDataException("认购金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

					if (subscriptionCost.doubleValue() > schemeDTO.getSchemeCost())
						throw new WebDataException("认购金额不能大于方案金额.");

					BigDecimal sponsorMinSubscriptionCost = BigDecimalUtil.valueOf(schemeDTO.getSchemeCost()
							* Constant.SPONSOR_MIN_SUBSCRIPTION_PERCENT);
					if (subscriptionCost.compareTo(sponsorMinSubscriptionCost) < 0)
						throw new WebDataException("发起人必须认购方案金额的" + Constant.SPONSOR_MIN_SUBSCRIPTION_PERCENT * 100
								+ "%(即" + Constant.MONEY_FORMAT.format(sponsorMinSubscriptionCost) + "元)以上.");
					if (schemeDTO.getMinSubscriptionCost() != null
							&& schemeDTO.getSchemeCost() < schemeDTO.getMinSubscriptionCost().doubleValue())
						throw new WebDataException("方案金额不能小于设置的最小认购金额.");
					if (schemeDTO.getMinSubscriptionCost() != null
							&& subscriptionCost.doubleValue() < schemeDTO.getMinSubscriptionCost().doubleValue())
						throw new WebDataException("认购方案金额不能小于设置的最小认购金额.");

					schemeDTO.setSponsorSubscriptionCost(subscriptionCost);
					totalCost = totalCost.add(subscriptionCost);
				}

				if (this.createForm.getBaodiCost() != null) {
					BigDecimal baodiCost = this.createForm.getBaodiCost().setScale(Constant.COST_MIN_UNITS.getScale(),
							BigDecimal.ROUND_DOWN);
					if (baodiCost.doubleValue() != this.createForm.getBaodiCost().doubleValue())
						throw new WebDataException("保底金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

					if (baodiCost.doubleValue() > schemeDTO.getSchemeCost())
						throw new WebDataException("保底金额不能大于方案金额.");

					if (schemeDTO.getMinSubscriptionCost() != null
							&& baodiCost.doubleValue() < schemeDTO.getMinSubscriptionCost().doubleValue())
						throw new WebDataException("保底方案金额不能小于设置的最小认购金额.");

					schemeDTO.setSponsorBaodiCost(baodiCost);
					totalCost = totalCost.add(baodiCost);
				}

				if (totalCost.doubleValue() > schemeDTO.getSchemeCost())
					throw new WebDataException("认购金额与保底金额之和不能大于方案金额.");

				break;
			case SELF:
				break;
			default:
				throw new WebDataException("方案分享方式不合法.");
			}
			schemeDTO.setShareType(this.createForm.getShareType());
		}

		if (!this.createForm.isAheadOfUploadContent()) {	
			ContentBean contentBean;
			try {
				if(this.createForm.isFromFreeSave()){
					this.createForm = this.supplementCreateFormData();
				}				
				contentBean = this.createForm.buildContentBean();
			} catch (DataException e) {
				throw new WebDataException(e.getMessage());
			}
			if (contentBean == null || StringUtils.isBlank(contentBean.getContent()))
				throw new WebDataException("方案内容为空.");
			else if (!schemeDTO.getUnits().equals(contentBean.getUnits()))
				throw new WebDataException("根据方案内容计算出来的注数与提交的注数不一致.");

			schemeDTO.setContent(contentBean.getContent());
		}

		return schemeDTO;
	}
	
	/**
	 * 由系统对Form进行相关数据的补充,比如由系统保留的数据
	 * @return
	 * @throws WebDataException
	 */
	protected CF supplementCreateFormData()throws WebDataException{
		return this.createForm;
	}

	/**
	 * 构建认购数据传输对象
	 * 
	 * @return 认购数据传输对象
	 * @throws WebDataException
	 */
	protected SubscribeDTO buildSubscribeDTO() throws WebDataException {
		if (this.subscribeForm == null)
			throw new WebDataException("认购表单数据为空.");

		User user = getLoginUser();
		if (user == null)
			throw new WebDataException("您还未登录,请登录后再操作.");

		SubscribeDTO dto = new SubscribeDTO();
		dto.setUserId(user.getId());

		if (this.id == null)
			throw new WebDataException("方案ID为空.");
		dto.setSchemeId(this.id);

		if (this.subscribeForm.getSubscriptionCost() == null && this.subscribeForm.getBaodiCost() == null)
			throw new WebDataException("认购金额和保底金额都为空.");

		if (this.subscribeForm.getSubscriptionCost() != null) {
			BigDecimal subscriptionCost = this.subscribeForm.getSubscriptionCost().setScale(
					Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);
			if (subscriptionCost.doubleValue() != this.subscribeForm.getSubscriptionCost().doubleValue())
				throw new WebDataException("认购金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

			dto.setSubscriptionCost(subscriptionCost);
		}

		if (this.subscribeForm.getBaodiCost() != null) {
			BigDecimal baodiCost = this.subscribeForm.getBaodiCost().setScale(Constant.COST_MIN_UNITS.getScale(),
					BigDecimal.ROUND_DOWN);
			if (baodiCost.doubleValue() != this.subscribeForm.getBaodiCost().doubleValue())
				throw new WebDataException("保底金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

			dto.setBaodiCost(baodiCost);
		}

		dto.setPassword(this.subscribeForm.getPassword());
		dto.setWay(SubscriptionWay.NORMAL);
		return dto;
	}

	/**
	 * 认购列表
	 */
	public String subscriptionList() {
		try {
			if (this.id == null)
				throw new WebDataException("方案ID不能为空.");

			this.scheme = getSchemeEntityManager().getScheme(id);
			if (this.scheme == null)
				throw new WebDataException("方案不存在.");
			
			
			XDetachedCriteria criteria = new XDetachedCriteria(Subscription.class, "m");
			User loggedUser = getLoginUser();
			//查询用户自身认购
			String type = Struts2Utils.getParameter("type");			
			if(type!=null && "self".equals(type.toLowerCase())){
				if(loggedUser == null){
					throw new WebDataException("您还未登录,请登录后再操作.");
				} 
				criteria.add(Restrictions.eq("m.userId", loggedUser.getId()));
			}
			criteria.add(Restrictions.eq("m.lotteryType", this.scheme.getLotteryType()));
			criteria.add(Restrictions.eq("m.schemeId", this.scheme.getId()));
			criteria.add(Restrictions.eq("m.state", SubscriptionState.NORMAL));
			criteria.addOrder(Order.asc("m.id"));

			pagination.setPageSize(20);
			this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);

			
			if (loggedUser != null) {
				Struts2Utils.setAttribute("loggedUser", loggedUser);

				boolean isSaleEnded = getSchemeService().isSaleEnded(scheme.getId());
				boolean canCancelScheme = loggedUser.getId().equals(scheme.getSponsorId()) && scheme.isCanCancel()
						&& !isSaleEnded;

				Struts2Utils.setAttribute("canCancelScheme", canCancelScheme);
				Struts2Utils.setAttribute("canCancelSubscription", scheme.isCanCancelSubscription() && !isSaleEnded);
			}

			return GlobalResults.FWD_SUBSCRIPTION_LIST;
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}

	/**
	 * 撤销认购
	 */
	public String cancelSubscription() {
		try {
			if (this.id == null)
				throw new WebDataException("认购ID不能为空.");

			User user = getLoginUser();
			if (user == null)
				throw new WebDataException("您还未登录,请登录后再操作.");

			Subscription subscription = getSchemeEntityManager().getSubscription(this.id);
			if (subscription == null)
				throw new WebDataException("认购记录不存在.");
			else if (!subscription.getUserId().equals(user.getId()))
				throw new WebDataException("您无权撤销该认购.");

			this.getSchemeService().cancelSubscription(this.id);

			addActionMessage("撤销认购成功！");
			if (Struts2Utils.isAjaxRequest()) {
				this.scheme = getSchemeEntityManager().getScheme(subscription.getSchemeId());
				this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
			} else {
				this.id = subscription.getSchemeId();
			}

			return success();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}

	public String baodiList() {
		try {
			if (this.id == null)
				throw new WebDataException("方案ID不能为空.");

			this.scheme = getSchemeEntityManager().getScheme(id);
			if (this.scheme == null)
				throw new WebDataException("方案不存在.");

			this.period = this.periodEntityManager.getPeriod(scheme.getPeriodId());
			if (this.period == null)
				throw new WebDataException("销售期不存在.");

			XDetachedCriteria criteria = new XDetachedCriteria(Baodi.class, "m");
			criteria.add(Restrictions.eq("m.lotteryType", this.scheme.getLotteryType()));
			criteria.add(Restrictions.eq("m.schemeId", this.scheme.getId()));
			criteria.add(Restrictions.eq("m.state", BaodiState.NORMAL));
			criteria.addOrder(Order.asc("m.id"));

			pagination.setPageSize(20);
			this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);

			User loggedUser = getLoginUser();
			if (loggedUser != null) {
				Struts2Utils.setAttribute("loggedUser", loggedUser);

				if (scheme.isCanSubscribe()) {
					boolean isSaleEnded = getSchemeService().isSaleEnded(scheme.getId());
					Struts2Utils.setAttribute("canTransfer", !isSaleEnded);
				}
			}
			return GlobalResults.FWD_BAODI_LIST;
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}

	public String transferSubscription() {
		try {
			if (this.id == null)
				throw new WebDataException("保底ID不能为空.");

			User user = getLoginUser();
			if (user == null)
				throw new WebDataException("您还未登录,请登录后再操作.");

			Baodi baodi = getSchemeEntityManager().getBaodi(this.id);
			if (baodi == null)
				throw new WebDataException("保底记录不存在.");
			else if (!baodi.getUserId().equals(user.getId()))
				throw new WebDataException("您无权执行该操作.");

			this.getSchemeService().baodiTransferSubscription(baodi.getId());

			addActionMessage("保底转认购成功！");
			if (Struts2Utils.isAjaxRequest()) {
				this.scheme = getSchemeEntityManager().getScheme(baodi.getSchemeId());
				this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
			} else {
				this.id = baodi.getSchemeId();
			}

			return success();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}

	public String download() {
		try {
			if (this.id != null)
				this.scheme = getSchemeEntityManager().getScheme(this.id);
			else {
				String schemeNumber = Struts2Utils.getRequest().getParameter("schemeNumber");
				if (StringUtils.isBlank(schemeNumber))
					throw new WebDataException("方案号为空.");
				this.scheme = getSchemeEntityManager().getSchemeBy(schemeNumber);
			}
			return GlobalResults.SINGLE_DOWNLOAD;
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	/**
	 * 取得可以追号的总期数
	 * 
	 * @return
	 */
	protected int count; // 查询条数
	protected Integer cost;
	protected Integer multiple;
	public static final NumberFormat ISSUE_FORMAT = new DecimalFormat("000");

	/**
	 * 取得可以追号的期数
	 * 
	 * @return
	 */
	public String canChasePeriod() {
		Map<String, Object> data = new HashMap<String, Object>();
		if (0 == count) {
			count = 5;
		}
		if (null == multiple || 0 == multiple) {
			multiple = 1;
		}
		int year = 0;
		int nowIssue;
		Period period = periodEntityManager.getPeriod(id);
		String nowPeriod = period.getPeriodNumber();
		if (Lottery.DLT.equals(this.getLotteryType())) {
			year = Integer.valueOf("20" + nowPeriod.substring(0, 2));
			nowIssue = Integer.valueOf(nowPeriod.substring(2));
		} else {
			year = Integer.valueOf(nowPeriod.substring(0, 4));
			nowIssue = Integer.valueOf(nowPeriod.substring(4));
		}
		int totalIssue = ChineseDateUtil.getIssueSize(this.getLotteryType(), year);
		List<Period> canChasePeriods = new ArrayList<Period>();
		int outSidePos = 0;
		for (int i = 0; i < count; i++) {
			period = new Period();
			if ((nowIssue + i) > totalIssue) {
				outSidePos++;
				period.setPeriodNumber(putPeriodNumber(year + 1, (outSidePos)));
			} else {
				period.setPeriodNumber(putPeriodNumber(year, (nowIssue + i)));
			}
			canChasePeriods.add(period);
		}

		data.put("chase_ul", chaseIssue(canChasePeriods, cost, multiple));
		Struts2Utils.renderJson(data);
		return null;
	}

	private String putPeriodNumber(int year, int issue) {
		String issueStr = ISSUE_FORMAT.format(issue);
		String yearStr = "";
		if (Lottery.DLT.equals(this.getLotteryType())) {
			yearStr = String.valueOf(year).substring(2, 4);
		} else {
			yearStr = String.valueOf(year);
		}
		return yearStr + issueStr;
	}

	private String chaseIssue(List<Period> canChasePeriod, Integer cost, Integer multiple) {
		StringBuffer sb = new StringBuffer();
		Period period;
		String itemId;
		for (int i = 0; i < canChasePeriod.size(); i++) {
			period = canChasePeriod.get(i);
			sb.append("<li>");
			itemId = "chase_item_" + i;
			sb.append(getNumString(i + 1));
			sb.append("&nbsp;<input id=\"checkbox_" + itemId + "\" type=\"checkbox\" checked=\"checked\" />");
			if (StringUtils.isNotBlank(period.getPeriodNumber())) {
				sb.append("&nbsp;第" + period.getPeriodNumber().trim() + "期");
			}
			sb.append("&nbsp;<input id=\"multiple_"
					+ itemId
					+ "\" type=\"text\" name=\"createForm.multiplesOfChase\" value=\""
					+ multiple
					+ "\" size=\"2\" style=\"IME-MODE: disabled;\" onkeydown=\"number_check(this,event,null)\" onkeyup=\"chgChaseMultiple(this)\" oncontextmenu=\"return false;\" autocomplete=\"off\"/>倍");
			sb.append("&nbsp;<span id=\"cost_" + itemId + "\" style=\"color:#F00\">" + cost + "</span>元");
			sb.append("</li>");
		}
		return sb.toString();
	}

	public String canCapacityChaseIssue() {
		Map<String, Object> data = new HashMap<String, Object>();
		int year = 0;
		int nowIssue;
		Period period = periodEntityManager.getPeriod(id);
		String nowPeriod = period.getPeriodNumber();
		if (Lottery.DLT.equals(this.getLotteryType())) {
			year = Integer.valueOf("20" + nowPeriod.substring(0, 2));
			nowIssue = Integer.valueOf(nowPeriod.substring(2));
		} else {
			year = Integer.valueOf(nowPeriod.substring(0, 4));
			nowIssue = Integer.valueOf(nowPeriod.substring(4));
		}
		int totalIssue = ChineseDateUtil.getIssueSize(this.getLotteryType(), year);
		int outSidePos = 0;
		StringBuffer periodList = new StringBuffer();
		for (int i = 0; i <= 120; i++) {
			period = new Period();
			if ((nowIssue + i) > totalIssue) {
				outSidePos++;
				periodList.append(putPeriodNumber(year + 1, (outSidePos)));
			} else {
				periodList.append(putPeriodNumber(year, (nowIssue + i)));
			}
			periodList.append("|");
		}

		data.put("periodList", periodList.toString());
		Struts2Utils.renderJson(data);
		return null;
	}

	private String getNumString(int i) {
		if (i < 10)
			return "0" + i;
		return "" + i;
	}

	/**
	 * 玩法介绍
	 */
	public String introduction() throws WebDataException {
		return "introduction";
	}

	/**
	 * 合买规则
	 */
	public String protocol() throws WebDataException {
		return "protocol";
	}

	/**
	 * 合买方案列表
	 */
	public String subList() {
		try {
			User user = this.getLoginUser();
			Struts2Utils.setAttribute("loggedUser",user);
			this.preparePeriods(true);
			this.preparePeriodsOfList(10);
			String key = getRequestKey();
			if (queryForm != null) {
				if (null != queryForm.getChooseType()) {
					if (Integer.valueOf(2).equals(queryForm.getChooseType())) {
						// 我的方案
						if (user==null) {
							CookieUtil.addReUrlCookie();
							this.addActionMessage("您还未登录，请登陆后操作.");
							return GlobalResults.FWD_LOGIN;
						}
						key = key + "&userId=" + user.getId();
					}
				}
			}
			// 2011-4-22
			String periodNumber = Struts2Utils.getRequest().getParameter("periodNumber");
			if (StringUtils.isNotBlank(periodNumber)) {
				Period periodTemp = periodEntityManager.getPeriod(Long.valueOf(periodNumber));
				if (null != periodTemp)
					this.period = periodTemp;
			}
			Element el = schemeQueryCache.get(key);
			if (el == null) {
				this.pagination = queryService.findByCriteriaAndPagination(buildFilterListDetachedCriteria(),
						this.pagination);
				el = new Element(key, this.pagination);
				this.schemeQueryCache.put(el);
			} else {
				this.pagination = (Pagination) el.getValue();
			}
			loadCrrPeriod();// /加载当前期数据
			Struts2Utils.setAttribute("endInitTime", compoundPeriodSales.getShareEndInitTime());
			return "subscription";
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	/**
	 * 构建方案查询封装对象 过滤是否显示保密方案条件
	 */
	protected XDetachedCriteria buildFilterListDetachedCriteria() {
		XDetachedCriteria criteria = new XDetachedCriteria(this.schemeClass, "m");
		criteria.add(Restrictions.eq("m.periodId", this.period.getId()));
		
		criteria.add(Restrictions.eq("m.shareType", ShareType.TOGETHER));
		if (queryForm != null) {
			// 判断是否显示保密方案
			if (queryForm != null && queryForm.getSecretType() != null) {
				if (queryForm.getSecretType() != SecretType.FULL_PUBLIC) {
					// 勾选上
					criteria.add(Restrictions.ne("m.secretType", SecretType.FULL_PUBLIC));
					Struts2Utils.setAttribute("flag", 1);
				}

			} else {
				Struts2Utils.setAttribute("flag", 0);
			}
			if (StringUtils.isNotBlank(queryForm.getSponsorName())) {
					///修改默认不查
					if(!"用户名或方案号".equals(queryForm.getSponsorName().trim())){
						Long schemeId = this.period.getLotteryType().getSchemeId(queryForm.getSponsorName().trim());
						if (schemeId != null){
							criteria.add(Restrictions.eq("m.id", schemeId));
						}else{
							criteria.add(Restrictions.eq("m.sponsorName", queryForm.getSponsorName().trim()));
						}
					}
			}
			if (null != queryForm.getCostType()) {
				queryForm.setMinMaxCost();
			}
			if (null != queryForm.getChooseType()) {
				if (Integer.valueOf(1).equals(queryForm.getChooseType())) {
					// 以保底方案
					criteria.add(Restrictions.eq("m.sendToPrint", true));
				} else if (Integer.valueOf(2).equals(queryForm.getChooseType())) {
					// 我的方案
					User user = this.getLoginUser();
					if (user != null) {
						criteria.add(Restrictions.eq("m.sponsorId", user.getId()));
					}
				}
			}
			if (queryForm.getMinSchemeCost() != null)
				criteria.add(Restrictions.ge("m.schemeCost", queryForm.getMinSchemeCost()));

			if (queryForm.getMaxSchemeCost() != null)
				criteria.add(Restrictions.le("m.schemeCost", queryForm.getMaxSchemeCost()));

			if (queryForm.getSchemeState() != null){
				criteria.add(Restrictions.eq("m.state", queryForm.getSchemeState()));
			}
		}
		if (queryForm != null && queryForm.getOrderType() != null) {
			switch (queryForm.getOrderType()) {
			case CREATE_TIME_DESC:
				criteria.addOrder(Order.desc("m.id"));
				break;
			case CREATE_TIME_ASC:
				criteria.addOrder(Order.asc("m.id"));
				break;
			case PROCESS_RATE_DESC:
				criteria.addOrder(Order.desc("m.progressRate"));
				break;
			case PROCESS_RATE_ASC:
				criteria.addOrder(Order.asc("m.progressRate"));
				break;
			case SCHEME_COST_DESC:
				criteria.addOrder(Order.desc("m.schemeCost"));
				break;
			case SCHEME_COST_ASC:
				criteria.addOrder(Order.asc("m.schemeCost"));
				break;
			}
		} else {
			criteria.addOrder(Order.desc("m.orderPriority"));
			criteria.addOrder(Order.asc("m.state"));
			criteria.addOrder(Order.desc("m.progressRate"));
		}
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 0);
		c.add(Calendar.MINUTE, 0);
		c.add(Calendar.SECOND, 0);
		c.add(Calendar.DAY_OF_MONTH, -10);	
		criteria.add(Restrictions.gt("m.createTime", c.getTime()));
		
		criteria.setMaxResults(50);
		criteria.addOrder(Order.desc("m.id"));
		return criteria;
	}

	/* ---------------------- getter and setter method ---------------------- */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return {@link #subscribeForm}
	 */
	public SubscribeForm getSubscribeForm() {
		return subscribeForm;
	}

	/**
	 * @param subscribeForm
	 *            the {@link #subscribeForm} to set
	 */
	public void setSubscribeForm(SubscribeForm subscribeForm) {
		this.subscribeForm = subscribeForm;
	}

	/**
	 * @return {@link #periods}
	 */
	public List<Period> getPeriods() {
		return periods;
	}

	/**
	 * @return {@link #period}
	 */
	public Period getPeriod() {
		return period;
	}

	public PeriodSales getSinglePeriodSales() {
		return singlePeriodSales;
	}

	public PeriodSales getCompoundPeriodSales() {
		return compoundPeriodSales;
	}

	/**
	 * @return {@link #scheme}
	 */
	public T getScheme() {
		return scheme;
	}
	
	

	public TT getSchemeTemp() {
		return schemeTemp;
	}

	public void setSchemeTemp(TT schemeTemp) {
		this.schemeTemp = schemeTemp;
	}

	/**
	 * @return {@link #pagination}
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination
	 *            the {@link #pagination} to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	/**
	 * @return {@link #queryForm}
	 */
	public SchemeQueryForm getQueryForm() {
		return queryForm;
	}

	/**
	 * @param queryForm
	 *            the {@link #queryForm} to set
	 */
	public void setQueryForm(SchemeQueryForm queryForm) {
		this.queryForm = queryForm;
	}

	/**
	 * @return {@link #salesMode}
	 */
	public SalesMode getSalesMode() {
		return salesMode;
	}

	/**
	 * @param salesMode
	 *            the {@link #salesMode} to set
	 */
	public void setSalesMode(SalesMode salesMode) {
		this.salesMode = salesMode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public void setScheme(T scheme) {
		this.scheme = scheme;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public List<NewsInfoData> getForecastList() {
		return forecastList;
	}

	public void setForecastList(List<NewsInfoData> forecastList) {
		this.forecastList = forecastList;
	}

	public List<NewsInfoData> getSkillsList() {
		return skillsList;
	}

	public void setSkillsList(List<NewsInfoData> skillsList) {
		this.skillsList = skillsList;
	}

	public List<NewsInfoData> getResultList() {
		return resultList;
	}

	public void setResultList(List<NewsInfoData> resultList) {
		this.resultList = resultList;
	}

	public List<NewsInfoData> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<NewsInfoData> infoList) {
		this.infoList = infoList;
	}

	public Map<String, String> getNoticeNewsMap() {
		return noticeNewsMap;
	}

	public void setNoticeNewsMap(Map<String, String> noticeNewsMap) {
		this.noticeNewsMap = noticeNewsMap;
	}

	public void setFreeSave(boolean freeSave) {
		this.freeSave = freeSave;
	}

	public boolean isAheadOfUpload() {
		return aheadOfUpload;
	}

	public void setAheadOfUpload(boolean aheadOfUpload) {
		this.aheadOfUpload = aheadOfUpload;
	}

	public int getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(int timeFrame) {
		this.timeFrame = timeFrame;
	}
		

}
