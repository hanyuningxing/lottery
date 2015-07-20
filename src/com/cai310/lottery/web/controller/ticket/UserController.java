package com.cai310.lottery.web.controller.ticket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryUtil;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.entity.lottery.MyScheme;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.ticket.TicketLogger;
import com.cai310.lottery.entity.lottery.ticket.TicketProcess;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.DrawingOrder;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.user.BankForm;
import com.cai310.lottery.web.controller.user.EmailValForm;
import com.cai310.lottery.web.controller.user.PasswordForm;
import com.cai310.lottery.web.controller.user.RegForm;
import com.cai310.lottery.web.controller.user.UserBaseController;
import com.cai310.lottery.web.controller.user.UserInfoForm;
import com.cai310.lottery.web.sso.SsoLoginHolder;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.MD5;
import com.cai310.utils.PropertiesUtil;
import com.cai310.utils.Struts2Utils;
import com.esms.PostMsg;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Results({
	    @Result(name = "welcome", location = "/WEB-INF/content/ticket/welcome.ftl"),
		@Result(name = "login", location = "/WEB-INF/content/ticket/login.ftl"),
		@Result(name = "scheme-list", location = "/WEB-INF/content/ticket/scheme-list.ftl"),
		
		@Result(name = UserController.EDIT_SUCCESS, type = "redirectAction", params = { "actionName", "user", "method",
				"edit" }), @Result(name = "message", location = "/WEB-INF/content/common/message.ftl") })
public class UserController extends UserBaseController {
	private static final long serialVersionUID = 2300904439118054778L;
	public static final String FWD_WELCOME = "reg_success";
	public static final String EDIT_SUCCESS = "edit_success";
	public static final String LOGIN_TRY_TIMES = "LOGIN_TRY_TIMES";
	public static final String REGIST_IP_KEY = "REGIST_IP_KEY";

	public static final String NEED_CAPTCHA = "needCaptcha";
	public static final String REG_SUCCESS = "register_success";
	private static final String TO_VALID_SUCCESS = "reset";
	public static final int SIMPLE_MAX_TRY_TIMES = 3;
	public static final String CYY_INDEX = Constant.BASEPATH + "/";
	private Pagination pagination = new Pagination(20);

	@Autowired
	@Qualifier("userLoginCache")
	private Cache userLoginCache;

	@Autowired
	@Qualifier("userRegistCache")
	private Cache userRegistCache;

	@Autowired
	@Qualifier("commonQueryCache")
	private Cache commonQueryCache;

	@Resource
	protected EventLogManager eventLogManager;

	@Autowired
	protected QueryService queryService;
	private RegForm regForm;
	private UserInfoForm infoForm;
	private BankForm bankForm;
	private PasswordForm pwdForm;
	private EmailValForm emailValForm;
	private String code;
	private UserLogin beforeUserLogin;

	public String index() {
		return welcome();
	}
	public String message() {
		return welcome();
	}

	/**
	 * 进入欢迎页面
	 */
	public String welcome() {
		User loginUser = getLoginUser();
		if (loginUser == null){
			CookieUtil.addReUrlCookie();
			return "login";
		}
		return "welcome";
	}

	private String getLoginTryTimesKey() {
		return LOGIN_TRY_TIMES + Struts2Utils.getSession().getId();
	}
 
	private String getRegistKey() {
		String ip = Struts2Utils.getRemoteAddr();
		if (null != ip) {
			String[] ipkey = ip.split("\\.");
			String key = ipkey[0] + "." + ipkey[1];
			return REGIST_IP_KEY + key;
		}
		return null;
	}

	// ip限制
	private boolean addRegistTimes() {
		int tryTimes = getRegistTimes();
		tryTimes++;
		Log.info("IP:" + getRegistKey());
		userRegistCache.put(new Element(getRegistKey(), Integer.valueOf(tryTimes)));
		if (tryTimes >= SIMPLE_MAX_TRY_TIMES) {
			return false;
		}
		return true;
	}

	// ip限制
	private int getRegistTimes() {
		Element el = userRegistCache.get(getRegistKey());
		if (el != null)
			return ((Integer) el.getValue()).intValue();

		return 0;
	}

	private void addLoginTryTimes() {
		int tryTimes = getLoginTryTimes();
		tryTimes++;

		userLoginCache.put(new Element(getLoginTryTimesKey(), Integer.valueOf(tryTimes)));

		if (tryTimes >= SIMPLE_MAX_TRY_TIMES)
			Struts2Utils.setAttribute(NEED_CAPTCHA, true);
	}

	private int getLoginTryTimes() {
		Element el = userLoginCache.get(getLoginTryTimesKey());
		if (el != null)
			return ((Integer) el.getValue()).intValue();

		return 0;
	}

	private void clearLoginTryInfo() {
		userLoginCache.remove(getLoginTryTimesKey());
	}
	private static final Comparator<MyScheme> MY_SCHEME_COMPARATOR = new Comparator<MyScheme>() {

		@Override
		public int compare(MyScheme o1, MyScheme o2) {
			if (o1 == null || o1.getCreateTime() == null)
				return -1;
			if (o2 == null || o2.getCreateTime() == null)
				return 1;
			return o2.getCreateTime().compareTo(o1.getCreateTime());
		}
	};
	@SuppressWarnings("rawtypes")
	private Map<Lottery, SchemeEntityManager> schemeEntityManagerMap = Maps.newHashMap();
	private Lottery lotteryType;
	private TicketSchemeState state;
	private int timeFrame;
	@SuppressWarnings("rawtypes")
	private SchemeEntityManager getSchemeEntityManager(Lottery lotteryType) {
		return schemeEntityManagerMap.get(lotteryType);
	}

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setSchemeEntityManagerList(List<SchemeEntityManager> schemeEntityManagerList) {
		for (SchemeEntityManager manager : schemeEntityManagerList) {
			schemeEntityManagerMap.put(manager.getLottery(), manager);
		}
	}
	public Lottery[] getWebLotteryType() {
		return LotteryUtil.getWebLotteryList();
	}
	private FundDetailType fundType;
	public String fund_count() {
		try{
			User loginUser = getLoginUser();
			if (loginUser == null) {
				CookieUtil.addReUrlCookie();
				return "login";
			}
			String key = getRequestKey() + loginUser.getId();
			Element el = commonQueryCache.get(key);
			if (el == null) {
				XDetachedCriteria criteria = new XDetachedCriteria(FundDetail.class, "m");
				DetachedCriteria criteria1 =  DetachedCriteria.forClass(FundDetail.class,"m1");
				criteria.add(Restrictions.eq("m.userId", loginUser.getId()));
				criteria1.add(Restrictions.eq("m1.userId", loginUser.getId()));
				if (null != from&&null != to){
				    String fromStr = DateUtil.getFormatDate("yyyy-MM-dd", from);
				    from = DateUtil.strToDate(fromStr+" "+DATE_FORMAT.format(fromHour)+":"+DATE_FORMAT.format(fromMin));
				    String toStr = DateUtil.getFormatDate("yyyy-MM-dd", to);
				    to = DateUtil.strToDate(toStr+" "+DATE_FORMAT.format(toHour)+":"+DATE_FORMAT.format(toMin));
					if (to.getTime() <= from.getTime())
						throw new WebDataException("结束时间不能少于开始时间！");
					if (to.getTime() - from.getTime() > dateLimit)
						throw new WebDataException("结束时间减去开始时间不能大于31天！");
					criteria.add(Restrictions.ge("m.createTime",from));
					criteria.add(Restrictions.le("m.createTime",to));
					
					criteria1.add(Restrictions.ge("m1.createTime",from));
					criteria1.add(Restrictions.le("m1.createTime",to));
				}else{
					Calendar c = Calendar.getInstance();
					c.add(Calendar.HOUR_OF_DAY, 0);
					c.add(Calendar.MINUTE, 0);
					c.add(Calendar.SECOND, 0);
					switch (timeFrame) {
					case 1:
						c.add(Calendar.DAY_OF_MONTH, -7);// 7天前
						break;
					case 2:
						c.add(Calendar.DAY_OF_MONTH, -15);// 15天前
						break;
					case 3:
						c.add(Calendar.MONTH, -1);// 1个月前
						break;
					default:
						c.add(Calendar.DAY_OF_MONTH, -1);// 1天前
					}
					criteria.add(Restrictions.ge("m.createTime", c.getTime()));
					criteria1.add(Restrictions.ge("m1.createTime", c.getTime()));
				}
				ProjectionList prop = Projections.projectionList();
				prop.add(Projections.groupProperty("m.mode"), "mode");
				prop.add(Projections.groupProperty("m.type"), "type");
				prop.add(Projections.sum("m.money"), "money");
				prop.add(Projections.property("m.mode"), "mode");
				prop.add(Projections.property("m.type"), "type");
				criteria.setProjection(prop);
				criteria.setResultTransformer(Transformers.aliasToBean(FundDetail.class));
				pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
				el = new Element(key, this.pagination);
				commonQueryCache.put(el);
				
				criteria1.addOrder(Order.desc("m1.id"));
				List<FundDetail> list = queryService.findByDetachedCriteria(criteria1);
				if(null!=list&&!list.isEmpty()){
					FundDetail fundDetail = list.get(0);
					Struts2Utils.setAttribute("resultMoney", fundDetail.getResultMoney());
					el = new Element("resultMoney", fundDetail.getResultMoney());
					commonQueryCache.put(el);
				}
				
			} else {
				pagination = (Pagination) el.getValue();
				el = commonQueryCache.get("resultMoney");
				if (el != null) {
					Struts2Utils.setAttribute("resultMoney", el.getValue());
				}
			}
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return "fund-count";
	}
	public String fund_list() {
		try{
			User loginUser = getLoginUser();
			if (loginUser == null) {
				CookieUtil.addReUrlCookie();
				return "login";
			}
			String key = getRequestKey() + loginUser.getId();
			Element el = commonQueryCache.get(key);
			if (el == null) {
				XDetachedCriteria criteria = new XDetachedCriteria(FundDetail.class, "m");
				criteria.add(Restrictions.eq("m.userId", loginUser.getId()));
	
				if (fundType != null)
					criteria.add(Restrictions.eq("m.type", fundType));
				if (null != from&&null != to){
				    String fromStr = DateUtil.getFormatDate("yyyy-MM-dd", from);
				    from = DateUtil.strToDate(fromStr+" "+DATE_FORMAT.format(fromHour)+":"+DATE_FORMAT.format(fromMin));
				    String toStr = DateUtil.getFormatDate("yyyy-MM-dd", to);
				    to = DateUtil.strToDate(toStr+" "+DATE_FORMAT.format(toHour)+":"+DATE_FORMAT.format(toMin));
					if (to.getTime() <= from.getTime())
						throw new WebDataException("结束时间不能少于开始时间！");
					if (to.getTime() - from.getTime() > dateLimit)
						throw new WebDataException("结束时间减去开始时间不能大于31天！");
					criteria.add(Restrictions.ge("m.createTime",from));
					criteria.add(Restrictions.le("m.createTime",to));
				}else{
					Calendar c = Calendar.getInstance();
					c.add(Calendar.HOUR_OF_DAY, 0);
					c.add(Calendar.MINUTE, 0);
					c.add(Calendar.SECOND, 0);
					switch (timeFrame) {
					case 1:
						c.add(Calendar.DAY_OF_MONTH, -7);// 7天前
						break;
					case 2:
						c.add(Calendar.DAY_OF_MONTH, -15);// 15天前
						break;
					case 3:
						c.add(Calendar.MONTH, -1);// 1个月前
						break;
					default:
						c.add(Calendar.DAY_OF_MONTH, -1);// 1天前
					}
					criteria.add(Restrictions.ge("m.createTime", c.getTime()));
				}
				criteria.addOrder(Order.desc("m.id"));
	
				pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
				el = new Element(key, this.pagination);
				commonQueryCache.put(el);
			} else {
				pagination = (Pagination) el.getValue();
			}
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return "fund-list";
	}
	private Date from_prize;
	private Integer fromHour_prize;
	private Integer fromMin_prize;
	private Date to_prize;
	private Integer toHour_prize;
	private Integer toMin_prize;
	
	
	private Date from;
	private Integer fromHour;
	private Integer fromMin;
	private Date to;
	private Integer toHour;
	private Integer toMin;
	private String orderId;
	public static final NumberFormat DATE_FORMAT = new DecimalFormat("00");
	private static Long dateLimit = Long.valueOf(1000) * Long.valueOf(60) * Long.valueOf(60) * Long.valueOf(24)
	* Long.valueOf(31);
	@SuppressWarnings("unchecked")
	public String list() {
		User loginUser = getLoginUser();
		if (loginUser == null){
			CookieUtil.addReUrlCookie();
			return "login";
		}
        try{
			String key = getRequestKey() + loginUser.getId();
			Element el = commonQueryCache.get(key);
			if (el == null) {
				SchemeQueryDTO dto = new SchemeQueryDTO();
				if (null != from&&null != to){
				    String fromStr = DateUtil.getFormatDate("yyyy-MM-dd", from);
				    from = DateUtil.strToDate(fromStr+" "+DATE_FORMAT.format(fromHour)+":"+DATE_FORMAT.format(fromMin));
				    String toStr = DateUtil.getFormatDate("yyyy-MM-dd", to);
				    to = DateUtil.strToDate(toStr+" "+DATE_FORMAT.format(toHour)+":"+DATE_FORMAT.format(toMin));
					if (to.getTime() <= from.getTime())
						throw new WebDataException("结束时间不能少于开始时间！");
					if (to.getTime() - from.getTime() > dateLimit)
						throw new WebDataException("结束时间减去开始时间不能大于31天！");
					dto.setStartTime(from);
					dto.setEndTime(to);
				}else{
					Calendar c = Calendar.getInstance();
					c.add(Calendar.HOUR_OF_DAY, 0);
					c.add(Calendar.MINUTE, 0);
					c.add(Calendar.SECOND, 0);
					switch (timeFrame) {
					case 1:
						c.add(Calendar.DAY_OF_MONTH, -7);// 7天前
						break;
					case 2:
						c.add(Calendar.DAY_OF_MONTH, -15);// 15天前
						break;
					case 3:
						c.add(Calendar.MONTH, -1);// 1个月前
						break;
					default:
						c.add(Calendar.DAY_OF_MONTH, -1);// 1天前
					}
					dto.setStartTime(c.getTime());
				}
				if(StringUtils.isNotBlank(orderId))dto.setOrderId(Long.valueOf(orderId.trim()));
				if(null!=state){
					List<org.hibernate.criterion.Criterion> restrictions = Lists.newArrayList();
					if(state.equals(TicketSchemeState.FAILD)){
						/** 方案不成功的状态 */
	
						SchemeState[] UNSUCCESS = { SchemeState.CANCEL, SchemeState.REFUNDMENT ,SchemeState.UNFULL};
						restrictions.add(Restrictions.or(Restrictions.in("m.state", UNSUCCESS), 
								         				 Restrictions.and(
								         						  Restrictions.eq("m.state", SchemeState.FULL), 
								         						  Restrictions.eq("m.schemePrintState", SchemePrintState.FAILED))));
						
					}else if(state.equals(TicketSchemeState.FULL)){
						restrictions.add(Restrictions.and(Restrictions.or(
								                                     Restrictions.eq("m.schemePrintState", SchemePrintState.PRINT), 
								                                     Restrictions.eq("m.schemePrintState", SchemePrintState.UNPRINT))
								          ,Restrictions.eq("m.state", SchemeState.FULL)));
					}else if(state.equals(TicketSchemeState.SUCCESS)){
						restrictions.add(Restrictions.or(Restrictions.eq("m.state", SchemeState.SUCCESS), 
		         				 Restrictions.and(
		         						  Restrictions.eq("m.state", SchemeState.FULL), 
		         						  Restrictions.eq("m.schemePrintState", SchemePrintState.SUCCESS))));
					}
					dto.setRestrictions(restrictions);
				}
				
				if (lotteryType == null) {
					List<MyScheme> allList = Lists.newArrayList();
					for (Lottery lottery : getWebLotteryType()) {
						pagination = getSchemeEntityManager(lottery).findMyScheme(loginUser.getId(), dto, pagination);
						if (pagination != null && pagination.getResult() != null && !pagination.getResult().isEmpty()) {
							allList.addAll(pagination.getResult());
							Collections.sort(allList, MY_SCHEME_COMPARATOR);
						}
					}
					pagination.setResult(allList);
					pagination.setTotalCount(allList.size());
				} else {
					pagination = getSchemeEntityManager(lotteryType).findMyScheme(loginUser.getId(), dto, pagination);
					
				}
				el = new Element(key, this.pagination);
				commonQueryCache.put(el);
			} else {
				pagination = (Pagination) el.getValue();
			}
        } catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return forward(false, "scheme-list");
	}
	private Map<Lottery,Map<TicketSchemeState,Scheme>> countMap;
	@SuppressWarnings("unchecked")
	public String countList() {
		User loginUser = getLoginUser();
		if (loginUser == null){
			CookieUtil.addReUrlCookie();
			return "login";
		}
        try{
			String key = getRequestKey() + loginUser.getId();
			Element el = commonQueryCache.get(key);
			if (el == null) {
				SchemeQueryDTO dto = new SchemeQueryDTO();
				if ((null != from&&null != to)||(null != from_prize&&null != to_prize)){
					 if(null != from&&null != to){
					    String fromStr = DateUtil.getFormatDate("yyyy-MM-dd", from);
					    from = DateUtil.strToDate(fromStr+" "+DATE_FORMAT.format(fromHour)+":"+DATE_FORMAT.format(fromMin));
					    String toStr = DateUtil.getFormatDate("yyyy-MM-dd", to);
					    to = DateUtil.strToDate(toStr+" "+DATE_FORMAT.format(toHour)+":"+DATE_FORMAT.format(toMin));
						if (to.getTime() <= from.getTime())
							throw new WebDataException("结束时间不能少于开始时间！");
						if (to.getTime() - from.getTime() > dateLimit)
							throw new WebDataException("结束时间减去开始时间不能大于31天！");
						dto.setStartTime(from);
						dto.setEndTime(to);
					 }
					 if (null != from_prize&&null != to_prize){
						    String fromStr_prize = DateUtil.getFormatDate("yyyy-MM-dd", from_prize);
						    from_prize = DateUtil.strToDate(fromStr_prize+" "+DATE_FORMAT.format(fromHour_prize)+":"+DATE_FORMAT.format(fromMin_prize));
						    String toStr_prize = DateUtil.getFormatDate("yyyy-MM-dd", to_prize);
						    to_prize = DateUtil.strToDate(toStr_prize+" "+DATE_FORMAT.format(toHour_prize)+":"+DATE_FORMAT.format(toMin_prize));
							if (to_prize.getTime() <= from_prize.getTime())
								throw new WebDataException("结束时间不能少于开始时间！");
							if (to_prize.getTime() - from_prize.getTime() > dateLimit)
								throw new WebDataException("结束时间减去开始时间不能大于31天！");
							dto.setStartTime_prize(from_prize);
							dto.setEndTime_prize(to_prize);
					 }
				}else{
					Calendar c = Calendar.getInstance();
					c.add(Calendar.HOUR_OF_DAY, 0);
					c.add(Calendar.MINUTE, 0);
					c.add(Calendar.SECOND, 0);
					switch (timeFrame) {
					case 1:
						c.add(Calendar.DAY_OF_MONTH, -7);// 7天前
						break;
					case 2:
						c.add(Calendar.DAY_OF_MONTH, -15);// 15天前
						break;
					case 3:
						c.add(Calendar.MONTH, -1);// 1个月前
						break;
					default:
						c.add(Calendar.DAY_OF_MONTH, -1);// 1天前
					}
					dto.setStartTime(c.getTime());
				}
				if(null!=state){
					List<org.hibernate.criterion.Criterion> restrictions = Lists.newArrayList();
					if(state.equals(TicketSchemeState.FAILD)){
						/** 方案不成功的状态 */

						SchemeState[] UNSUCCESS = { SchemeState.CANCEL, SchemeState.REFUNDMENT ,SchemeState.UNFULL};
						restrictions.add(Restrictions.or(Restrictions.in("m.state", UNSUCCESS), 
								         				 Restrictions.and(
								         						  Restrictions.eq("m.state", SchemeState.FULL), 
								         						  Restrictions.eq("m.schemePrintState", SchemePrintState.FAILED))));
						
					}else if(state.equals(TicketSchemeState.FULL)){
						restrictions.add(Restrictions.and(Restrictions.or(
								                                     Restrictions.eq("m.schemePrintState", SchemePrintState.PRINT), 
								                                     Restrictions.eq("m.schemePrintState", SchemePrintState.UNPRINT))
								          ,Restrictions.eq("m.state", SchemeState.FULL)));
					}else if(state.equals(TicketSchemeState.SUCCESS)){
						restrictions.add(Restrictions.or(Restrictions.eq("m.state", SchemeState.SUCCESS), 
		         				 Restrictions.and(
		         						  Restrictions.eq("m.state", SchemeState.FULL), 
		         						  Restrictions.eq("m.schemePrintState", SchemePrintState.SUCCESS))));
					}
					dto.setRestrictions(restrictions);
				}
				countMap = Maps.newHashMap();
				if(null==lotteryType){
					Lottery[] lotteryArr = getWebLotteryType();
					for (Lottery lottery : lotteryArr) {
						Map<TicketSchemeState,Scheme> map = Maps.newHashMap();
						List<Scheme> list = getSchemeEntityManager(lottery).countMyScheme(loginUser.getId(), dto, pagination);
						for (Scheme scheme : list) {
							if(SchemeState.REFUNDMENT.equals(scheme.getState())||SchemeState.CANCEL.equals(scheme.getState())||SchemeState.UNFULL.equals(scheme.getState())){
								oprCountMap(map,TicketSchemeState.FAILD,scheme);
							}else if(scheme.getState().equals(SchemeState.FULL)){
								if(scheme.getSchemePrintState().equals(SchemePrintState.SUCCESS)){
									oprCountMap(map,TicketSchemeState.SUCCESS,scheme);
								}else if(scheme.getSchemePrintState().equals(SchemePrintState.PRINT)||scheme.getSchemePrintState().equals(SchemePrintState.UNPRINT)){
									oprCountMap(map,TicketSchemeState.FULL,scheme);
								}else if(scheme.getSchemePrintState().equals(SchemePrintState.FAILED)){
									oprCountMap(map,TicketSchemeState.FAILD,scheme);
								}
							}else if(scheme.getState().equals(SchemeState.SUCCESS)){
								oprCountMap(map,TicketSchemeState.SUCCESS,scheme);
							}
						}
						countMap.put(lottery, map);
						
					}
				}else{
					List<Scheme> list = getSchemeEntityManager(lotteryType).countMyScheme(loginUser.getId(), dto, pagination);
					Map<TicketSchemeState,Scheme> map = Maps.newHashMap();
					for (Scheme scheme : list) {
						if(SchemeState.REFUNDMENT.equals(scheme.getState())||SchemeState.CANCEL.equals(scheme.getState())||SchemeState.UNFULL.equals(scheme.getState())){
							oprCountMap(map,TicketSchemeState.FAILD,scheme);
						}else if(scheme.getState().equals(SchemeState.FULL)){
							if(scheme.getSchemePrintState().equals(SchemePrintState.SUCCESS)){
								oprCountMap(map,TicketSchemeState.SUCCESS,scheme);
							}else if(scheme.getSchemePrintState().equals(SchemePrintState.PRINT)||scheme.getSchemePrintState().equals(SchemePrintState.UNPRINT)){
								oprCountMap(map,TicketSchemeState.FULL,scheme);
							}else if(scheme.getSchemePrintState().equals(SchemePrintState.FAILED)){
								oprCountMap(map,TicketSchemeState.FAILD,scheme);
							}
						}else if(scheme.getState().equals(SchemeState.SUCCESS)){
							oprCountMap(map,TicketSchemeState.SUCCESS,scheme);
						}
					}
					countMap.put(lotteryType, map);
				}
				el = new Element(key, countMap);
				commonQueryCache.put(el);
			} else {
				countMap = (Map<Lottery,Map<TicketSchemeState,Scheme>>) el.getValue();
			}
        } catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return forward(false, "scheme-count-list");
	}
	private void oprCountMap(Map<TicketSchemeState,Scheme> countMap,TicketSchemeState ticketSchemeState,Scheme scheme){
		if(null==countMap.get(ticketSchemeState)){
			scheme.setTicketSchemeState(ticketSchemeState);
			countMap.put(ticketSchemeState, scheme);
		}else{
			Scheme schemeTemp = countMap.get(ticketSchemeState);
			scheme.setUnits(scheme.getUnits()+schemeTemp.getUnits());
			scheme.setVersion(null==scheme.getVersion()?0:scheme.getVersion()+(null==schemeTemp.getVersion()?0:schemeTemp.getVersion()));
			scheme.setSchemeCost(scheme.getSchemeCost()+schemeTemp.getSchemeCost());
			scheme.setPrize(null==scheme.getPrize()?BigDecimal.valueOf(0):scheme.getPrize().add(null==schemeTemp.getPrize()?BigDecimal.valueOf(0):schemeTemp.getPrize()));
			scheme.setPrizeAfterTax(null==scheme.getPrizeAfterTax()?BigDecimal.valueOf(0):scheme.getPrizeAfterTax().add(null==schemeTemp.getPrizeAfterTax()?BigDecimal.valueOf(0):schemeTemp.getPrizeAfterTax()));
			scheme.setTicketSchemeState(ticketSchemeState);
			countMap.put(ticketSchemeState, scheme);
		}
	}
	public String logger_list(){
		User loginUser = getLoginUser();
		if (loginUser == null){
			CookieUtil.addReUrlCookie();
			return "login";
		}

		String key = getRequestKey() + loginUser.getId();
		Element el = commonQueryCache.get(key);
		if (el == null) {
			XDetachedCriteria criteria = new XDetachedCriteria(TicketLogger.class, "m");
			if(null!=this.lotteryType)
				criteria.add(Restrictions.eq("m.lotteryType", this.lotteryType));
			criteria.add(Restrictions.eq("m.userId", loginUser.getId()));
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR_OF_DAY, 0);
			c.add(Calendar.MINUTE, 0);
			c.add(Calendar.SECOND, 0);
			c.add(Calendar.DAY_OF_MONTH, -1);// 1天前
			criteria.add(Restrictions.ge("createTime", c.getTime()));

			criteria.addOrder(Order.desc("m.id"));

			pagination = queryService.findByCriteriaAndPagination(criteria, pagination);

			el = new Element(key, this.pagination);
			commonQueryCache.put(el);
		} else {
			pagination = (Pagination) el.getValue();
		}

		return "logger-list";
	}
	public String process_list(){
		User loginUser = getLoginUser();
		if (loginUser == null){
			CookieUtil.addReUrlCookie();
			return "login";
		}

		String key = getRequestKey() + loginUser.getId();
		Element el = commonQueryCache.get(key);
		if (el == null) {
			XDetachedCriteria criteria = new XDetachedCriteria(TicketProcess.class, "m");
			if(null!=this.lotteryType)
				criteria.add(Restrictions.eq("m.lotteryType", this.lotteryType));
			criteria.add(Restrictions.eq("m.userId", loginUser.getId()));
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR_OF_DAY, 0);
			c.add(Calendar.MINUTE, 0);
			c.add(Calendar.SECOND, 0);
			switch (timeFrame) {
			case 1:
				c.add(Calendar.DAY_OF_MONTH, -7);// 7天前
				break;
			case 2:
				c.add(Calendar.DAY_OF_MONTH, -15);// 15天前
				break;
			case 3:
				c.add(Calendar.MONTH, -1);// 1个月前
				break;
			default:
				c.add(Calendar.DAY_OF_MONTH, -1);// 1天前
			}
			criteria.add(Restrictions.ge("createTime", c.getTime()));

			criteria.addOrder(Order.desc("m.id"));

			pagination = queryService.findByCriteriaAndPagination(criteria, pagination);

			el = new Element(key, this.pagination);
			commonQueryCache.put(el);
		} else {
			pagination = (Pagination) el.getValue();
		}

		return "process-list";
	}
	/**
	 * 登录
	 */
	public String login() {
		User loginUser = getLoginUser();
		if (loginUser != null) {
			return redirectforward(Boolean.TRUE, "您已经登录!请先退出", null, "message");
		}
		String fwd;
		String type = Struts2Utils.getRequest().getParameter("type");
		if ("quick_login".equals(type)) {
			fwd = "quick-login";
		} else {
			fwd = "login";
		}
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
			int tryTimes = getLoginTryTimes();
			if (tryTimes >= SIMPLE_MAX_TRY_TIMES) {
				if (Struts2Utils.isAjaxRequest()) {
					Map<String, Object> jsonMap = new HashMap<String, Object>();
					jsonMap.put("success", false);
					jsonMap.put("need_captcha", true);
					Struts2Utils.renderJson(jsonMap);
					return null;
				} else {
					Struts2Utils.setAttribute(NEED_CAPTCHA, true);
				}
			}
			return fwd;
		}

		// 登录操作
		try {
			String userName = Struts2Utils.getParameter("userName");
			if (StringUtils.isBlank(userName))
				throw new WebDataException("用户名不能为空.");
			userName = java.net.URLDecoder.decode(userName.trim(), "UTF-8");

			String password = Struts2Utils.getParameter("password");
			if (StringUtils.isBlank(password))
				throw new WebDataException("密码不能为空.");

			String ipAddress = Struts2Utils.getRemoteAddr();
			logger.info("用户[{}]尝试登录网站,来源IP：{}", userName, ipAddress);

			int tryTimes = getLoginTryTimes();
			if (tryTimes >= SIMPLE_MAX_TRY_TIMES) {
				this.jsonMap.put("need_captcha", true);
				Struts2Utils.setAttribute(NEED_CAPTCHA, true);
				String captcha = Struts2Utils.getParameter("captcha");
				if (StringUtils.isBlank(captcha)) {
					throw new WebDataException("为了您的用户安全，请输入验证码.");
				} else if (!captcha.equals(Struts2Utils.getSession().getAttribute(Constant.LOGIN_CAPTCHA))) {
					throw new WebDataException("验证码错误.");
				}
			}

			// 转化为小写用户名 进行查询 zhuhui motify 2011-05-04
			User user = userManager.getUserBy(userName);

			if (user == null) {
				addLoginTryTimes();
				throw new WebDataException("用户名或密码错误.");
			} else {
				if (null == user.getInfo()) {
					user.setInfo(new UserInfo());
					userManager.saveUser(user);
				}
				if (null == user.getBank()) {
					user.setBank(new BankInfo());
					userManager.saveUser(user);
				}
			}
			if (user.isLocked())
				throw new WebDataException("该账户已被锁定,请联系客服.");

			String pwd = MD5.md5(password.trim()).toUpperCase();
			if (!pwd.equalsIgnoreCase(user.getPassword())) {
				addActionError("用户名或密码错误.");
				addLoginTryTimes();
				return forward(false, fwd);
			} else {
				addActionMessage("登录成功.");
				Struts2Utils.getRequest().removeAttribute(NEED_CAPTCHA);
				clearLoginTryInfo();
				SsoLoginHolder.login(user.getId());

				String redirectUrl = CookieUtil.getCookieByName(Struts2Utils.getRequest(), "redirectUrl");
				if (StringUtils.isBlank(redirectUrl))
					redirectUrl = null;
				Struts2Utils.setAttribute("redirectUrl", redirectUrl);
				if (isAjaxRequest()) {
					jsonMap.put("redirectUrl", redirectUrl);
					return success();
				} else {
					return redirectforward(Boolean.TRUE, "欢迎您回来," + userName, redirectUrl, "message");
				}
			}
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return forward(false, fwd);
	}
	
	/**
	 * 登录会员中心首页
	 * 
	 * @return
	 * @throws WebDataException
	 */
	public String userCenter() throws WebDataException {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return "login";
		}
		if (null != loginUser) {
			UserInfoForm userInfoForm = new UserInfoForm();
			// 用户账户信息
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			Struts2Utils.setAttribute("userInfoForm", userInfoForm);
		} else {
			throw new WebDataException("您还未登录,请登录后再操作.");
		}
		return "index";
	}

	/**
	 * 退出登录
	 */
	public String logout() {
		SsoLoginHolder.logout();
		// 生成‘单点登录-退出’的URL。Add By Peter
		String siteId = "cai310.com";
		String serviceId = "logoutNotify";
		String key = "55662233";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String trace = sdf.format(new Date());
		if (trace.length() > 16) {
			trace = trace.substring(0, 16);
		}

		String data = "SiteId=" + siteId + "&ServiceId=" + serviceId + "&TraceNo=" + trace;
		String md5 = MD5.md5((data + key).toLowerCase()).toLowerCase();
		String url = "http://www.cai310.net/portal/" + serviceId + ".htm?" + data + "&SignType=MD5&Sign=" + md5
				+ "&jsoncallback=?";
		this.jsonMap.put("sliUrl", url);

		Struts2Utils.getSession().setAttribute("user", null);
		Struts2Utils.getSession().setAttribute("password", null);
		if (isAjaxRequest()) {
			this.jsonMap.put("sloUrl", url);
			return success();
		} else {
			Struts2Utils.setAttribute("sloUrl", url);
			return redirectforward(Boolean.TRUE, "您已经安全的退出", null, "message");
		}
	}
	public String resetPasswd() {
		return "passwd-reset";
	}

	public String updatePwd() {
		User user = this.getLoginUser();
		HashMap map = new HashMap();
		if (user == null) {
			map.put("success", false);
			map.put("msg", "您尚未登录!");
			map.put("url", Struts2Utils.getRequest().getContextPath() + "/index.shtml");
			Struts2Utils.renderJson(map);
		} else {
			try {
				String password = (String) Struts2Utils.getSession().getAttribute("password");
				user.setPassword(MD5.md5(regForm.getPassword().trim()).toUpperCase());

				userManager.saveUser(user);
				Struts2Utils.getRequest().getSession().setAttribute("user", user);
				map.put("success", true);
				map.put("msg", "密码修改成功!");
				Struts2Utils.renderJson(map);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", false);
				map.put("msg", "密码修改失败!");
				Struts2Utils.renderJson(map);
			}

		}
		return null;
	}

	//
	/**
	 * 更新用户个人信息
	 * 
	 * @throws WebDataException
	 */
	public String updateInfo() throws WebDataException {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return "login";
		}
		try {
			if (loginUser == null)
				throw new WebDataException("您还未登录,请登录后再操作.");

			// 更新论坛用户信息
			try {
				String password = (String) Struts2Utils.getSession().getAttribute("password");
			} catch (Exception e) {

				e.printStackTrace();
			}

			User user = userManager.getUser(loginUser.getId());

			UserInfo info = user.getInfo();

			
			if (StringUtils.isNotBlank(regForm.getIdCard())&&StringUtils.isBlank(info.getIdCard())) {
				info.setIdCard(regForm.getIdCard());
			}
			if (StringUtils.isNotBlank(regForm.getEmail())&&StringUtils.isBlank(info.getEmail())) {
				info.setEmail(regForm.getEmail().toLowerCase());
			}
			if (StringUtils.isNotBlank(regForm.getPhoneNumber())&&StringUtils.isBlank(info.getMobile())) {
				info.setMobile(regForm.getPhoneNumber());
			}
			if (StringUtils.isNotBlank(regForm.getRealName())&&StringUtils.isBlank(info.getRealName())) {
				String realName = java.net.URLDecoder.decode(regForm.getRealName(), "UTF-8");
				info.setRealName(realName);
			}
			userManager.saveUserInfo(info);

		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		addActionMessage("修改资料成功.");

		if (null != infoForm && null != infoForm.getFrom()) {
			return redirectforward(Boolean.TRUE, "账号信息已完善,请充值!", Constant.BASEPATH + "/user/fund!" + infoForm.getFrom()
					+ ".action", "message");
		}
		return toValidateAccount();
	}

	/**
	 * 检查旧密码是否唯一
	 * 
	 * @return
	 */
	public String checkOldPasswordAble() {
		User user = this.getLoginUser();

		if (user == null) {
			try {
				throw new WebDataException("您尚未登录!");
			} catch (WebDataException e) {
				e.printStackTrace();
			}
		}
		String oldPassword = MD5.md5(regForm.getOldpassword());
		String password = user.getPassword();
		HashMap map = new HashMap();
		if (oldPassword.equals(password)) {
			map.put("success", true);
			Struts2Utils.renderJson(map);
		} else {
			map.put("success", false);
			Struts2Utils.renderJson(map);
		}

		return null;
	}

	/**
	 * 检查用户名是否唯一
	 * 
	 * @return
	 */
	public String checkUserRegAble() {
		User user = this.getLoginUser();
		String userName = null;
		try {
			userName = java.net.URLDecoder.decode(this.regForm.getUserName().trim(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (user != null && user.getUserName().equals(userName.toLowerCase())) {
			map.put("success", true);
			Struts2Utils.renderJson(map);
		} else {
			user = userManager.getUserBy(userName);

			if (user == null) {
				map.put("success", true);
				map.put("userName", userName);
				Struts2Utils.renderJson(map);
			} else {
				map.put("success", false);
				map.put("msg", "registered");
				Struts2Utils.renderJson(map);
			}
		}

		return null;
	}

	/**
	 * 检查验证码是否匹配
	 * 
	 * @return
	 */
	public String checkCodeRegAble() {
		String captcha = (String) Struts2Utils.getSession().getAttribute(Constant.LOGIN_CAPTCHA);
		Map<String, Object> map = new HashMap<String, Object>();
		if (captcha != null && code.equals(captcha)) {
			map.put("success", true);
			Struts2Utils.renderJson(map);
		} else {
			map.put("success", false);
			Struts2Utils.renderJson(map);
		}
		return null;
	}

	/**
	 * 查看是否登录
	 */
	public String checkLogin() {
		User user = this.getLoginUser();
		if (null != user) {
			UserInfoForm userInfoForm = new UserInfoForm();
			if (StringUtils.isBlank(user.getUserName())) {
				user = userManager.getUser(user.getId());
				user.setUserName("" + user.getId());
				userManager.saveUser(user);
			}
			userInfoForm.setUserName(StringUtils.isBlank(user.getUserName()) ? "&nbsp;" : this.getLoginUser()
					.getUserName().trim());

			NumberFormat nf = NumberFormat.getInstance();
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			userInfoForm.setDefaultAccountRemainMoney(user == null ? nf.format(BigDecimal.ZERO) : nf
					.format(user.getRemainMoney()));

			Struts2Utils.getRequest().getSession().setAttribute("user", user);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", true);
			map.put("info", userInfoForm);
			Struts2Utils.renderJson(map);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			map.put("info", "");
			map.put("lastlogin_time", DateUtil.dateToStr(new Date()));
			Struts2Utils.renderJson(map);
		}
		return null;
	}

	/**
	 * 转向账号设置页面
	 * 
	 * @return
	 * @throws WebDataException
	 */
	public String toValidateAccount() throws WebDataException {
		User loginUser = getLoginUser();
		if (loginUser == null)
			throw new WebDataException("您还未登录,请登录后再操作.");

		User user = userManager.getUser(this.getLoginUser().getId());
		if (user.getInfo() == null) {
			UserInfo info = new UserInfo();
			userManager.saveUser(user, info, null);
		}

		if (user.getBank() == null) {
			BankInfo bank = new BankInfo();
			userManager.saveUser(user, null, bank);
		}
		Struts2Utils.getRequest().setAttribute("user", user);
		return TO_VALID_SUCCESS;
	}

	

	
	
	

	/**
	 * 判断日期
	 * 
	 * @param oldDate
	 * @param day
	 * @return
	 */
	private boolean getBeforeAfterDay(Date oldDate, Date curDate, int day) {
		Calendar creDate = Calendar.getInstance();
		creDate.setTime(oldDate);
		int year = creDate.get(Calendar.YEAR);
		int month = creDate.get(Calendar.MONTH);
		int oldDay = creDate.get(Calendar.DAY_OF_MONTH);
		int newDay = oldDay + day;
		creDate.set(Calendar.YEAR, year);
		creDate.set(Calendar.MONTH, month);
		creDate.set(Calendar.DAY_OF_MONTH, newDay);

		if (creDate.getTime().after(curDate))
			return true;
		else
			return false;
	}

	/**
	 * 设置短信接口信息
	 * 
	 * @return
	 */
	private PostMsg setPostMsg() {
		HashMap map = PropertiesUtil.readProperties("msg-config.properties");
		String username = (String) map.get("username");
		String password = (String) map.get("password");
		String cmIP = (String) map.get("cmIP");
		int cmPort = Integer.parseInt((String) map.get("cmPort"));
		String wsIP = (String) map.get("wsIP");
		int wsPort = Integer.parseInt((String) map.get("wsPort"));

		PostMsg msg = new PostMsg(username, password);
		msg.getCmHost().setHost(cmIP, cmPort);
		msg.getWsHost().setHost(wsIP, wsPort);
		return msg;
	}

	/**
	 * @return {@link #regForm}
	 */
	public RegForm getRegForm() {
		return regForm;
	}

	/**
	 * @param regForm
	 *            the {@link #regForm} to set
	 */
	public void setRegForm(RegForm regForm) {
		this.regForm = regForm;
	}

	/**
	 * @return {@link #infoForm}
	 */
	public UserInfoForm getInfoForm() {
		return infoForm;
	}

	/**
	 * @param infoForm
	 *            the {@link #infoForm} to set
	 */
	public void setInfoForm(UserInfoForm infoForm) {
		this.infoForm = infoForm;
	}

	/**
	 * @return {@link #bankForm}
	 */
	public BankForm getBankForm() {
		return bankForm;
	}

	/**
	 * @param bankForm
	 *            the {@link #bankForm} to set
	 */
	public void setBankForm(BankForm bankForm) {
		this.bankForm = bankForm;
	}

	/**
	 * @return {@link #pwdForm}
	 */
	public PasswordForm getPwdForm() {
		return pwdForm;
	}

	/**
	 * @param pwdForm
	 *            the {@link #pwdForm} to set
	 */
	public void setPwdForm(PasswordForm pwdForm) {
		this.pwdForm = pwdForm;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public EmailValForm getEmailValForm() {
		return emailValForm;
	}

	public void setEmailValForm(EmailValForm emailValForm) {
		this.emailValForm = emailValForm;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public Lottery getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public int getTimeFrame() {
		return timeFrame;
	}
	public void setTimeFrame(int timeFrame) {
		this.timeFrame = timeFrame;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public TicketSchemeState getState() {
		return state;
	}
	public void setState(TicketSchemeState state) {
		this.state = state;
	}
	public Integer getFromHour() {
		return fromHour;
	}
	public void setFromHour(Integer fromHour) {
		this.fromHour = fromHour;
	}
	public Integer getFromMin() {
		return fromMin;
	}
	public void setFromMin(Integer fromMin) {
		this.fromMin = fromMin;
	}
	public Integer getToHour() {
		return toHour;
	}
	public void setToHour(Integer toHour) {
		this.toHour = toHour;
	}
	public Integer getToMin() {
		return toMin;
	}
	public void setToMin(Integer toMin) {
		this.toMin = toMin;
	}

	public FundDetailType getFundType() {
		return fundType;
	}
	public void setFundType(FundDetailType fundType) {
		this.fundType = fundType;
	}
	public Date getFrom_prize() {
		return from_prize;
	}
	public void setFrom_prize(Date from_prize) {
		this.from_prize = from_prize;
	}
	public Integer getFromHour_prize() {
		return fromHour_prize;
	}
	public void setFromHour_prize(Integer fromHour_prize) {
		this.fromHour_prize = fromHour_prize;
	}
	public Integer getFromMin_prize() {
		return fromMin_prize;
	}
	public void setFromMin_prize(Integer fromMin_prize) {
		this.fromMin_prize = fromMin_prize;
	}
	public Date getTo_prize() {
		return to_prize;
	}
	public void setTo_prize(Date to_prize) {
		this.to_prize = to_prize;
	}
	public Integer getToHour_prize() {
		return toHour_prize;
	}
	public void setToHour_prize(Integer toHour_prize) {
		this.toHour_prize = toHour_prize;
	}
	public Integer getToMin_prize() {
		return toMin_prize;
	}
	public void setToMin_prize(Integer toMin_prize) {
		this.toMin_prize = toMin_prize;
	}
	public Map<Lottery, Map<TicketSchemeState, Scheme>> getCountMap() {
		return countMap;
	}
	public void setCountMap(Map<Lottery, Map<TicketSchemeState, Scheme>> countMap) {
		this.countMap = countMap;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	

}
