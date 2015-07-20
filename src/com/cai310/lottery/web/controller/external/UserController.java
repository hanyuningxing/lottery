package com.cai310.lottery.web.controller.external;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.ChaseState;
import com.cai310.lottery.common.DrawingOrderState;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.LotteryUtil;
import com.cai310.lottery.common.MessageType;
import com.cai310.lottery.common.MobileInfoType;
import com.cai310.lottery.common.PayWay;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.dto.lottery.PeriodDTO;
import com.cai310.lottery.dto.lottery.PeriodDataDTO;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.user.DrawingDTO;
import com.cai310.lottery.dto.user.UserInfoDTO;
import com.cai310.lottery.entity.info.MobileNewsData;
import com.cai310.lottery.entity.info.Rule;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.MyScheme;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.DrawingOrder;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.Ipsorder;
import com.cai310.lottery.entity.user.Media;
import com.cai310.lottery.entity.user.PhonePopu;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.lottery.entity.user.UserReport;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.info.NewsInfoDataEntityManager;
import com.cai310.lottery.service.lottery.ChasePlanEntityManager;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.impl.KenoServiceImpl;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.user.BankForm;
import com.cai310.lottery.web.controller.user.EmailValForm;
import com.cai310.lottery.web.controller.user.PasswordForm;
import com.cai310.lottery.web.controller.user.RegForm;
import com.cai310.lottery.web.controller.user.UserBaseController;
import com.cai310.lottery.web.controller.user.UserInfoForm;
import com.cai310.lottery.web.controller.user.alipayUtils.AlipayConstant;
import com.cai310.lottery.web.controller.user.alipayUtils.Rsa;
import com.cai310.lottery.web.sso.SsoLoginHolder;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Results( { @Result(name = "index", location = "/WEB-INF/content/ticket/common.jsp") })
public class UserController extends UserBaseController {
	/** 新闻 **/
	@Resource
	private NewsInfoDataEntityManager newsInfoDataEntityManager;

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
	protected TicketThenEntityManager ticketThenEntityManager;
	@SuppressWarnings("rawtypes")
	private Map<Lottery, KenoServiceImpl> kenoServiceMap = Maps.newHashMap();

	@SuppressWarnings("rawtypes")
	private KenoServiceImpl getKenoService(Lottery lotteryType) {
		return kenoServiceMap.get(lotteryType);
	}

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setKenoService(List<KenoServiceImpl> kenoServiceList) {
		for (KenoServiceImpl kenoServiceImpl : kenoServiceList) {
			kenoServiceMap.put(kenoServiceImpl.getLottery(), kenoServiceImpl);
		}
	}

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

	@SuppressWarnings("rawtypes")
	private Map<Lottery, PeriodDataEntityManager> periodDataEntityManagerMap = Maps
			.newHashMap();

	@SuppressWarnings("rawtypes")
	private PeriodDataEntityManager getPeriodDataEntityManager(
			Lottery lotteryType) {
		return periodDataEntityManagerMap.get(lotteryType);
	}

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPeriodDataEntityManagerList(
			List<PeriodDataEntityManager> periodDataEntityManagerList) {
		for (PeriodDataEntityManager manager : periodDataEntityManagerList) {
			periodDataEntityManagerMap.put(manager.getLottery(), manager);
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<Lottery, SchemeEntityManager> schemeEntityManagerMap = Maps
			.newHashMap();

	@SuppressWarnings("rawtypes")
	private SchemeEntityManager getSchemeEntityManager(Lottery lotteryType) {
		return schemeEntityManagerMap.get(lotteryType);
	}

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setSchemeEntityManagerList(
			List<SchemeEntityManager> schemeEntityManagerList) {
		for (SchemeEntityManager manager : schemeEntityManagerList) {
			schemeEntityManagerMap.put(manager.getLottery(), manager);
		}
	}
	@Autowired
	private DczcMatchEntityManager dczcMatchEntityManager;
	@Autowired
	protected JclqMatchEntityManager jclqMatchEntityManager;
	@Autowired
	protected JczqMatchEntityManager jczqMatchEntityManager;
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
	protected ChasePlanEntityManager chasePlanEntityManager;
	@Autowired
	protected PeriodEntityManager periodManager;
	@Autowired
	protected QueryService queryService;
	private RegForm regForm;
	private UserInfoForm infoForm;
	private BankForm bankForm;
	private PasswordForm pwdForm;
	private EmailValForm emailValForm;
	private String code;
	private UserLogin beforeUserLogin;

	protected String wAction;

	protected String wParam;

	protected String wSign;

	protected String wAgent;

	protected Long userId;

	protected String userPwd;

	protected UserWay userWay;

	protected PayWay payWay;

	protected ReqParamVisitor reqParamVisitor;

	protected User user;

	protected PlatformInfo platformInfo;

	private static final String key = "E10ADC3949BA59ABBE56E057F20F883E";
	
	protected TicketPlatformInfo ticketPlatformInfo;

	public void check() throws WebDataException, UnsupportedEncodingException {
		this.wAction = Struts2Utils.getParameter("wAction");
		this.wParam = Struts2Utils.getParameter("wParam");
		this.wSign = Struts2Utils.getParameter("wSign");
		this.wAgent = Struts2Utils.getParameter("wAgent");
		if (StringUtils.isBlank(wAction)) {
			throw new WebDataException("4-请求Id为空");
		}
		if (StringUtils.isBlank(wParam)) {
			throw new WebDataException("5-请求参数为空");
		}
		if (StringUtils.isBlank(wSign)) {
			throw new WebDataException("1-请求密钥为空");
		}
		try {
			reqParamVisitor = new ReqParamVisitor();
			reqParamVisitor.visit(wParam);
			String userWayStr = reqParamVisitor.getUserWay();
			if (StringUtils.isNotBlank(userWayStr)) {
				userWay = UserWay.getUserWayByName(userWayStr);
			}
			String payWayStr = reqParamVisitor.getPayWay();
			if (StringUtils.isNotBlank(payWayStr)) {
				payWay = PayWay.getPayWayByName(payWayStr);
			}

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new WebDataException("5-ReqParam参数解析错误");
		}
		try {
			for (PlatformInfo platformInfoTemp : PlatformInfo.values()) {
				if (this.wAgent.trim().equals(platformInfoTemp.getId().trim()))
					platformInfo = platformInfoTemp;
			}
			if (platformInfo == null)
				throw new WebDataException("1-平台ID不能为空.");

			String param = wAction + wParam + wAgent
					+ platformInfo.getPassword();
			String pwd = SecurityUtil.md5(param.getBytes("UTF-8"))
					.toUpperCase().trim();
			if (!pwd.equals(wSign.trim())) {
				throw new WebDataException("1-请求密钥验证错误");
			}
			String userId = reqParamVisitor.getUserId();
			if (StringUtils.isNotBlank(userId)) {
				User user = userManager.getUser(Long.valueOf(userId));
				if (user == null)
					throw new WebDataException("1-找不到用户ID对应的用户.");
				if (user.isLocked())
					throw new WebDataException("2-帐号被冻结");
				this.user = user;
				checkUser(reqParamVisitor.getUserPwd(), user);
			}
		} catch (WebDataException e) {
			logger.warn(e.getMessage(), e);
			throw new WebDataException(e.getMessage());
		}

	}

	public void checkUser(String userPwd, User user) throws WebDataException {
		String loginUserPwd = SsoLoginHolder.getPhoneLoginUserPwd(user);
		if (StringUtils.isBlank(loginUserPwd))
			throw new WebDataException("1-用户验证密码为空.");
		if (StringUtils.isBlank(userPwd))
			throw new WebDataException("1-验证密码为空.");
		if (!userPwd.trim().equalsIgnoreCase(loginUserPwd.trim())) {
			throw new WebDataException("1-验证密码错误.");
		}
	}

	public String resultPwd() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					String userName = null == wParam_map.get("userName") ? null
							: String.valueOf(wParam_map.get("userName"));
					if (StringUtils.isBlank(userName))
						throw new WebDataException("8-用户名为空");
					User user = userManager.getUserBy(userName);
					if (null == user)
						throw new WebDataException("8-用户名不正确");
					if (user.isLocked())
						throw new WebDataException("10-该账户已被锁定,请联系客服.");
					if (null != user.getInfo()) {
						if (StringUtils.isBlank(user.getInfo().getMobile())) {
							throw new WebDataException("9-请先设置用户个人资料");
						}
					} else {
						throw new WebDataException("9-请先设置用户个人资料");
					}
					String mobile = user.getInfo().getMobile();
					Boolean isSend = userManager.resetPasswordAndSendMessage(user
							.getUserName(), mobile);
					if(isSend){
						user = userManager.getUser(user.getId());
						user.setPassword(SsoLoginHolder.getPhoneLoginUserPwd(user));
						map.put("user", user);
						map.put("processId", "0");
					}
					jsonConfig.setExcludes(new String[] {"gradeInfo", "password", "id",
							"tkpassword", "consumptionMoney", "createTime",
							"lastModifyTime", "lastModifyTime", "agentId",
							"info", "bank" });
 
				} else {
					throw new WebDataException("5-参数错误.");
				}
			} else {
				throw new WebDataException("5-参数错误.");
			}
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String updatePwd() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			if (user == null) {
				throw new WebDataException("8-用户错误.");
			} else {
				if (StringUtils.isNotBlank(this.wParam)) {
					Map<String, Object> wParam_map = JsonUtil
							.getMap4Json(wParam);
					if (null != wParam_map) {
						if (user.isLocked())
							throw new WebDataException("11-该账户已被锁定,请联系客服.");
						String oldPwd = null == wParam_map.get("oldPwd") ? null
								: String.valueOf(wParam_map.get("oldPwd"));
						if (StringUtils.isBlank(oldPwd)) {
							throw new WebDataException("9-旧密码为空.");
						}
						if (!oldPwd.trim().equals(user.getPassword())) {
							throw new WebDataException("9-旧密码不正确.");
						}
						String newPwd = null == wParam_map.get("newPwd") ? null
								: String.valueOf(wParam_map.get("newPwd"));
						if (StringUtils.isBlank(newPwd)) {
							throw new WebDataException("10-新密码为空.");
						}
						user.setPassword(newPwd.trim());
						user = userManager.saveUser(user);
						user.setPassword(SsoLoginHolder
								.getPhoneLoginUserPwd(user));
						map.put("user", user);
						map.put("processId", "0");

						jsonConfig.setExcludes(new String[] {"gradeInfo", "password", "id",
								"tkpassword", "consumptionMoney", "createTime",
								"lastModifyTime", "lastModifyTime", "agentId",
								"info", "bank" });

					} else {
						throw new WebDataException("5-参数错误.");
					}
				} else {
					throw new WebDataException("5-参数错误.");
				}
			}
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	/**
	 * 登录
	 */
	public String login() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String userName = reqParamVisitor.getUserName();
			if (StringUtils.isBlank(userName))
				throw new WebDataException("8-用户名不能为空.");
			userName = java.net.URLDecoder.decode(userName.trim(), "UTF-8");

			String password = reqParamVisitor.getPassword();
			if (StringUtils.isBlank(password))
				throw new WebDataException("9-密码不能为空.");
			password = password.trim();
			userName = userName.trim();
			// 转化为小写用户名 进行查询 zhuhui motify 2011-05-04
			User user = userManager.getUserBy(userName);
			if (user == null)
				throw new WebDataException("10-用户名错误.");
			if (user.isLocked())
				throw new WebDataException("11-该账户已被锁定,请联系客服.");

			if (!password.trim().equalsIgnoreCase(user.getPassword())) {
				throw new WebDataException("12-用户名或密码错误.");
			}
			user.setPassword(SsoLoginHolder.getPhoneLoginUserPwd(user));
			map.put("user", user);
			map.put("processId", "0");

			jsonConfig.setExcludes(new String[] {"gradeInfo", "password", "id",
					"tkpassword", "consumptionMoney", "createTime",
					"lastModifyTime", "lastModifyTime", "agentId",
					"info", "bank" });

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	/**
	 * 注册
	 */
	public String reg() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String userName = reqParamVisitor.getUserName();
			if (StringUtils.isBlank(userName))
				throw new WebDataException("8-用户名不能为空.");
			userName = java.net.URLDecoder.decode(userName.trim(), "UTF-8");

			String password = reqParamVisitor.getPassword();
			if (StringUtils.isBlank(password))
				throw new WebDataException("11-密码不能为空.");
			User userTemp = userManager.getUserBy(userName);
			if (userTemp != null
					&& userTemp.getUserName().equals(userName.toLowerCase())) {
				throw new WebDataException("8-注册用户名重复.请更改.");
			}
			password = password.trim();
			userName = userName.trim();
			int len = userName.getBytes().length;
			if (len < 3 || len > 16)
				throw new WebDataException("9-用户名不合法(3-16个字符,1个汉字相当两个字符).");
			else if (!userName.matches(RegexUtils.LetterAndNumberAndChinese))
				throw new WebDataException("10-用户名必须由英文字母、数字或中文组成.");
			User user = new User();
			String mobile = null;
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					String mid = null == wParam_map.get("mid") ? null : String
							.valueOf(wParam_map.get("mid"));
					if (StringUtils.isNotBlank(mid)) {
						Media media = userManager.getMedia(Long.valueOf(mid));
						if (null != media) {
							user.setMid(media.getId());
						} else {
							user.setMid(0L);
						}
					} else {
						user.setMid(0L);
					}
					mobile = null == wParam_map.get("mobile") ? null : String
							.valueOf(wParam_map.get("mobile"));
					if (StringUtils.isBlank(mobile)) {
						throw new WebDataException("12-手机号码不能为空.");
					}
					mobile = mobile.trim();
					if (!mobile.matches(RegexUtils.Number))
						throw new WebDataException("12-手机号码输入有误.");
					if (mobile.length() < 7)
						throw new WebDataException("12-手机号码输入有误.");
				} else {
					throw new WebDataException("5-参数错误.");
				}
			} else {
				throw new WebDataException("5-参数错误.");
			}
			if (null != this.getUserWay()) {
				user.setUserWay(userWay);
			} else {
				user.setUserWay(UserWay.WEB);
			}
			user.setUserName(userName.toLowerCase());
			user.setLocked(User.NO_LOCK_STATUS);
			user.setRemainMoney(BigDecimal.ZERO);

			String introductionId = CookieUtil.getIntroductionIdCookie();
			if (StringUtils.isBlank(introductionId)) {
				introductionId = Constant.SITE_BAODI_USER_ID + "";// /如果没有就吧他转为网站保底账户的ID
			}
			User introductionIdUser = userManager.getUser(Long
					.valueOf(introductionId));
			if (null == introductionIdUser) {
				throw new WebDataException("7-找不到响应的介绍人.");
			}
			user.setPassword(password.toUpperCase());
			user.setAgentId(introductionIdUser.getId());
			UserInfo info = new UserInfo();
			BankInfo bank = new BankInfo();
			if (StringUtils.isBlank(info.getMobile())) {
				info.setMobile(mobile);
			}
			user.setBank(bank);
			user.setInfo(info);
			user = userManager.saveUser(user, info, bank);
			user.setPassword(SsoLoginHolder.getPhoneLoginUserPwd(user));
			map.put("user", user);
			map.put("processId", "0");

			jsonConfig.setExcludes(new String[] {"gradeInfo", "password", "id",
					"tkpassword", "consumptionMoney", "createTime",
					"lastModifyTime", "lastModifyTime", "agentId",
					"info", "bank" });

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
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

		String data = "SiteId=" + siteId + "&ServiceId=" + serviceId
				+ "&TraceNo=" + trace;
		String md5 = MD5.md5((data + key).toLowerCase()).toLowerCase();
		String url = "http://www.cai310.net/portal/" + serviceId + ".htm?"
				+ data + "&SignType=MD5&Sign=" + md5 + "&jsoncallback=?";
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
	String getOrderInfo(Ipsorder ipsorder) {
		String strOrderInfo = "partner=" + "\""+AlipayConstant.PARTNER+"\"";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "\""+AlipayConstant.SELLER+"\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\""+ipsorder.getId()+"\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"充值\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"充值\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\""+ipsorder.getAmount()+"\"";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "\"http://www.qiu310.com/user/fund!alipayPhoneReturn.action\"";

		return strOrderInfo;
	}

	String getSignType() {
		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
		return getSignType;
	}
	String sign(String signType, String content) {
		
		return Rsa.sign(content, AlipayConstant.RSA_PRIVATE);
	}
	public String ipsOrder() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			if (user == null) {
				throw new WebDataException("1-找不到相应用户");
			}
			String amount = reqParamVisitor.getAmount();
			if (StringUtils.isBlank(amount)) {
				throw new WebDataException("8-支付金额不能为空,请修改..");
			}
			Ipsorder ipsorder = new Ipsorder();
			try {
				ipsorder.setAmount(BigDecimalUtil.valueOf(
						Double.valueOf(amount)).abs());
			} catch (Exception e) {
				throw new WebDataException("8-支付金额错误,请修改..");
			}
			// Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
			// if (null != wParam_map) {
			// try {
			// String isSend = null == wParam_map.get("isSend") ? null
			// : String.valueOf(wParam_map.get("isSend"));
			// if (StringUtils.isNotBlank(isSend)) {
			// ipsorder.setIsSend(Boolean.valueOf(isSend));
			// }
			// } catch (Exception e) {
			// throw new WebDataException("9-赠送选项错误..");
			// }
			// } else {
			//
			// }
			// 目前手机接口只有支付宝
			ipsorder.setPayWay(payWay);
			ipsorder.setUserWay(userWay);
			ipsorder.setIfcheck(false);
			ipsorder.setUserid(user.getId());
			ipsorder.setUserName(user.getUserName());
			ipsorder.setRealName(null == user.getInfo()
					|| null == user.getInfo().getRealName() ? "" : user
					.getInfo().getRealName());
			ipsorder.setMemo(null);
			// 提交至支付宝前 操作 生成订单

			Date now = new Date();

			ipsorder = userManager.saveIpsorder(ipsorder);
			if (PayWay.ALIPAY_PHONE.equals(payWay)) {
				
				
				
				
				
				String orderInfo = getOrderInfo(ipsorder);
				// 这里根据签名方式对订单信息进行签名
				String signType = getSignType();
				String strsign = sign(signType, orderInfo);
				strsign = URLEncoder.encode(strsign, "UTF-8");
				String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
						+ getSignType();

				ipsorder.setMemo(info);



				
				
				
				HttpServletRequest httpServletRequest = Struts2Utils
						.getRequest();
				String host = "http://www.qiu310.com/user/fund!alipayPhoneReturn.action";
				StringBuffer memo = new StringBuffer();
				memo.append("partner=\"" + AlipayConstant.PARTNER + "\"");
				memo.append("&");
				memo.append("seller=\"" + AlipayConstant.SELLER + "\"");
				memo.append("&");
				memo.append("out_trade_no=\"" + ipsorder.getId() + "\"");
				memo.append("&");
				memo.append("subject=\"充值\"");
				memo.append("&");
				memo.append("body=\"充值\"");
				memo.append("&");
				memo.append("total_fee=\"" + ipsorder.getAmount() + "\"");
				memo.append("&");
				memo.append("notify_url=\"" + host + "\"");
				memo.append("&");
				memo.append("sign=\""
						+ java.net.URLEncoder.encode(Rsa.sign(memo.toString(),
								AlipayConstant.RSA_PRIVATE), "UTF-8") + "\"");
				memo.append("&");
				memo.append("sign_type=\"RSA\"");
				//ipsorder.setMemo(memo.toString());
			}
			ipsorder = userManager.saveIpsorder(ipsorder);
			jsonConfig.setExcludes(new String[] { "userid", "createTime",
					"lastModifyTime", "ifcheck", "funddetailId", "realName" });

			map.put("ipsorder", ipsorder);
			map.put("processId", "0");

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String comfirmIpsOrder() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String orderId = reqParamVisitor.getOrderId();
			if (StringUtils.isBlank(orderId)) {
				throw new WebDataException("8-找不到的订单");
			}
			Ipsorder ipsorder;
			try {
				ipsorder = userManager.getIpsorder(Long.valueOf(orderId));
			} catch (Exception e) {
				throw new WebDataException("8-查询订单发生错误");
			}
			alipaySingleTradeQuery(ipsorder);
			map.put("processId", "0");

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String chaseList() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String count = reqParamVisitor.getCount();
			String start = reqParamVisitor.getStart();
			String wLotteryId = reqParamVisitor.getwLotteryId();
			String userId = reqParamVisitor.getUserId();
			String state = reqParamVisitor.getState();
			if (StringUtils.isBlank(userId)) {
				throw new WebDataException("8-用户ID为空");
			}

			if (StringUtils.isBlank(start)) {
				throw new WebDataException("9-起始标志为空");
			}
			try {
				Integer.valueOf(start);
			} catch (Exception e) {
				throw new WebDataException("9-起始标志错误");
			}
			if (StringUtils.isBlank(count)) {
				throw new WebDataException("10-条数标志为空");
			}
			try {
				Integer.valueOf(count);
			} catch (Exception e) {
				throw new WebDataException("10-条数标志错误");
			}

			String key = getRequestKey() + wLotteryId + count + start + state;
			Element el = commonQueryCache.get(key);
			if (el == null) {
				ChaseState chaseState = null;
				if (StringUtils.isNotBlank(state)) {
					List<org.hibernate.criterion.Criterion> restrictions = Lists
							.newArrayList();
					try {
						for (ChaseState chaseStateTemp : ChaseState.values()) {
							if (state.trim().equals(chaseStateTemp.name()))
								chaseState = chaseStateTemp;
						}
					} catch (Exception e) {
						throw new WebDataException("11-追号状态错误");
					}
				}
				Lottery lotteryType = null;
				if (StringUtils.isNotBlank(wLotteryId)) {
					try {
						lotteryType = Lottery.values()[Integer
								.valueOf(wLotteryId.trim())];
					} catch (Exception e) {
						throw new WebDataException("12-彩种错误");
					}
					if (null == lotteryType) {
						throw new WebDataException("12-彩种错误");
					}
				}
				List<ChasePlan> list = chasePlanEntityManager.findChasePlan(
						Long.valueOf(userId), Integer.valueOf(start), Integer
								.valueOf(count), chaseState, lotteryType);

				el = new Element(key, list);
				commonQueryCache.put(el);
				map.put("myChasePlan", list);
				map.put("totalCount", list.size());
			} else {
				List<ChasePlan> list = (List<ChasePlan>) el.getValue();
				map.put("myChasePlan", list);
				map.put("totalCount", list.size());
			}
			jsonConfig.setExcludes(new String[] { "createTime", "randomUnits",
					"transactionId", "prepaymentId", "dan", "yetChaseSize",
					"content", "random", "hasDan", "won", "multipleList",
					"lastModifyTime", "capacityProfitContent",
					"capacityProfitContent", "playType", "units", "mode",
					"content", "schemeCost", "outNumStop", "capacityProfit" });

			map.put("processId", "0");

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String fundList() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String count = reqParamVisitor.getCount();
			String start = reqParamVisitor.getStart();
			String wLotteryId = reqParamVisitor.getwLotteryId();
			String userId = reqParamVisitor.getUserId();
			String type = reqParamVisitor.getType();
			if (StringUtils.isBlank(userId)) {
				throw new WebDataException("8-用户ID为空");
			}

			if (StringUtils.isBlank(start)) {
				throw new WebDataException("9-起始标志为空");
			}
			try {
				Integer.valueOf(start);
			} catch (Exception e) {
				throw new WebDataException("9-起始标志错误");
			}
			if (StringUtils.isBlank(count)) {
				throw new WebDataException("10-条数标志为空");
			}
			try {
				Integer.valueOf(count);
			} catch (Exception e) {
				throw new WebDataException("10-条数标志错误");
			}

			String key = getRequestKey() + wLotteryId + count + start + type
					+ "fundList";
			Element el = commonQueryCache.get(key);
			if (el == null) {
				List<FundDetailType> fundDetailTypeList = Lists.newArrayList();
				if (StringUtils.isNotBlank(type)) {
					try {
						for (FundDetailType fundDetailType : FundDetailType
								.values()) {
							if (type.trim().equals(
									fundDetailType.getSuperTypeName())) {
								fundDetailTypeList.add(fundDetailType);
							}
						}
					} catch (Exception e) {
						throw new WebDataException("11-资金类型错误");
					}
				}
				Lottery lotteryType = null;
				if (StringUtils.isNotBlank(wLotteryId)) {
					try {
						lotteryType = Lottery.values()[Integer
								.valueOf(wLotteryId.trim())];
					} catch (Exception e) {
						throw new WebDataException("12-彩种错误");
					}
					if (null == lotteryType) {
						throw new WebDataException("12-彩种错误");
					}
				}
				DetachedCriteria criteria = DetachedCriteria.forClass(
						FundDetail.class, "m");
				criteria.add(Restrictions.eq("m.userId", user.getId()));

				if (!fundDetailTypeList.isEmpty())
					criteria.add(Restrictions.in("m.type", fundDetailTypeList));
				criteria.addOrder(Order.desc("id"));
				List list = queryService.findByDetachedCriteria(criteria,
						Integer.valueOf(start), Integer.valueOf(count), true);
				el = new Element(key, list);
				commonQueryCache.put(el);
				map.put("fundList", list);
				map.put("totalCount", list.size());
			} else {
				List list = (List) el.getValue();
				map.put("fundList", list);
				map.put("totalCount", list.size());
			}
			jsonConfig.setExcludes(new String[] { "createTime", "version",
					"remark", "lastModifyTime" });

			map.put("processId", "0");

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String userInfo() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String userId = reqParamVisitor.getUserId();
			if (StringUtils.isBlank(userId)) {
				throw new WebDataException("8-用户ID为空");
			}
			User user = userManager.getUser(Long.valueOf(userId));
			if (null == user)
				throw new WebDataException("8-用户ID错误");
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					String update = null == wParam_map.get("update") ? null
							: String.valueOf(wParam_map.get("update"));
					String v = null == wParam_map.get("v") ? null
							: String.valueOf(wParam_map.get("v"));//如果v=空的时候。身份证和真实姓名为必须
					UserInfoDTO userInfoDTO = new UserInfoDTO();
					if (StringUtils.isNotBlank(update)) {
						if ("0".equals(update.trim())) {
							// 查询
							UserInfo info = user.getInfo();
							BankInfo bank = user.getBank();
							if (null != info) {
								userInfoDTO.setRealName(info.getRealName());
								userInfoDTO.setMobile(info.getMobile());
								userInfoDTO.setIdCard(info.getIdCard());
							}
							if (null != bank) {
								userInfoDTO.setBankCard(bank.getBankCard());
								userInfoDTO.setBankName(bank.getBankName());
								userInfoDTO.setPartBankCity(bank
										.getPartBankCity());
								userInfoDTO.setPartBankName(bank
										.getPartBankName());
								userInfoDTO.setPartBankProvince(bank
										.getPartBankProvince());
							}
						}
						if ("1".equals(update.trim())) {
							// 更新
							UserInfo info = user.getInfo();
							BankInfo bank = user.getBank();
							if (null == info) {
								info = new UserInfo();
							}
							if (null == bank) {
								bank = new BankInfo();
							}

							String realName = null == wParam_map
									.get("realName") ? null : String
									.valueOf(wParam_map.get("realName"));
							if (StringUtils.isBlank(realName))
								throw new WebDataException("9-真实姓名为空");
							realName = realName.trim();
							int len = realName.getBytes().length;
							if (len < 3 || len > 16)
								throw new WebDataException(
										"9-真实姓名不合法(3-16个字符,1个汉字相当两个字符).");
							if (!realName.matches(RegexUtils.LetterAndChinese))
								throw new WebDataException(
										"9-真实姓名必须由英文字母或中文组成.");
							if (StringUtils.isBlank(info.getRealName())) {
								info.setRealName(realName);
							}
							if (StringUtils.isBlank(v)){
								//如果v=空的时候。身份证和真实姓名为必须
								String idcard = null == wParam_map.get("idcard") ? null
										: String.valueOf(wParam_map.get("idcard"));
								if (StringUtils.isBlank(idcard))
									throw new WebDataException("10-身份证为空");
								idcard = idcard.trim();
								if (!idcard.matches(RegexUtils.IdCard))
									throw new WebDataException("10-身份证错误.");
	
								if (StringUtils.isBlank(info.getIdCard())) {
									info.setIdCard(idcard);
								}
								String mobile = null == wParam_map.get("mobile") ? null
										: String.valueOf(wParam_map.get("mobile"));
								if (StringUtils.isBlank(mobile))
									throw new WebDataException("11-电话号码为空");
								mobile = mobile.trim();
								if (!mobile.matches(RegexUtils.Number))
									throw new WebDataException("11-电话号码错误.");
								if (StringUtils.isBlank(info.getMobile())) {
									info.setMobile(mobile);
								}
							}
							String bankCard = null == wParam_map
									.get("bankCard") ? null : String
									.valueOf(wParam_map.get("bankCard"));
							if (StringUtils.isBlank(bankCard))
								throw new WebDataException("12-银行帐号为空");
							bankCard = bankCard.trim();
							if (!bankCard.matches(RegexUtils.Number))
								throw new WebDataException("12-银行帐号错误.");

							if (StringUtils.isBlank(bank.getBankCard())) {
								bank.setBankCard(bankCard);
							}
							String bankName = null == wParam_map
									.get("bankName") ? null : String
									.valueOf(wParam_map.get("bankName"));
							if (StringUtils.isBlank(bankName))
								throw new WebDataException("12-开户银行为空");
							bankName = bankName.trim();
							if (!bankName.matches(RegexUtils.LetterAndChinese))
								throw new WebDataException(
										"12-开户银行必须由英文字母或中文组成.");

							if (StringUtils.isBlank(bank.getBankName())) {
								bank.setBankName(bankName);
							}

							String partBankProvince = null == wParam_map
									.get("partBankProvince") ? null
									: String.valueOf(wParam_map
											.get("partBankProvince"));
							if (StringUtils.isBlank(partBankProvince))
								throw new WebDataException("12-开户银行省份为空");
							partBankProvince = partBankProvince.trim();
							if (!partBankProvince
									.matches(RegexUtils.LetterAndChinese))
								throw new WebDataException(
										"12-开户银行省份必须由英文字母或中文组成.");

							if (StringUtils.isBlank(bank.getPartBankProvince())) {
								bank.setPartBankProvince(partBankProvince);
							}

							String partBankCity = null == wParam_map
									.get("partBankCity") ? null : String
									.valueOf(wParam_map.get("partBankCity"));
							if (StringUtils.isBlank(partBankCity))
								throw new WebDataException("12-开户银行城市为空");
							partBankCity = partBankCity.trim();
							if (!partBankCity
									.matches(RegexUtils.LetterAndChinese))
								throw new WebDataException(
										"12-开户银行城市必须由英文字母或中文组成.");

							if (StringUtils.isBlank(bank.getPartBankCity())) {
								bank.setPartBankCity(partBankCity);
							}

							String partBankName = null == wParam_map
									.get("partBankName") ? null : String
									.valueOf(wParam_map.get("partBankName"));
							if (StringUtils.isBlank(partBankName))
								throw new WebDataException("12-开户银行支行为空");
							partBankName = partBankName.trim();
							if (!partBankName
									.matches(RegexUtils.LetterAndChinese))
								throw new WebDataException(
										"12-开户银行支行必须由英文字母或中文组成.");

							if (StringUtils.isBlank(bank.getPartBankName())) {
								bank.setPartBankName(partBankName);
							}

							user = userManager.saveUser(user, info, bank);

							info = user.getInfo();
							bank = user.getBank();
							if (null != info) {
								userInfoDTO.setRealName(info.getRealName());
								userInfoDTO.setMobile(info.getMobile());
								userInfoDTO.setIdCard(info.getIdCard());
							}
							if (null != bank) {
								userInfoDTO.setBankCard(bank.getBankCard());
								userInfoDTO.setBankName(bank.getBankName());
								userInfoDTO.setPartBankCity(bank
										.getPartBankCity());
								userInfoDTO.setPartBankName(bank
										.getPartBankName());
								userInfoDTO.setPartBankProvince(bank
										.getPartBankProvince());
							}

						}
					} else {
						throw new WebDataException("5-ReqParam参数解析错误");
					}
					map.put("userInfo", userInfoDTO);
					map.put("processId", "0");
				}
			} else {
				throw new WebDataException("5-ReqParam参数解析错误");
			}

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String drawing() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String userId = reqParamVisitor.getUserId();
			if (StringUtils.isBlank(userId)) {
				throw new WebDataException("8-用户ID为空");
			}
			User user = userManager.getUser(Long.valueOf(userId));
			if (null == user)
				throw new WebDataException("8-用户ID错误");
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					String update = null == wParam_map.get("update") ? null
							: String.valueOf(wParam_map.get("update"));
					UserInfoDTO userInfoDTO = new UserInfoDTO();
					if (StringUtils.isNotBlank(update)) {
						if ("0".equals(update.trim())) {
							// 查询
							String count = reqParamVisitor.getCount();
							String start = reqParamVisitor.getStart();
							String type = reqParamVisitor.getType();
							if (StringUtils.isBlank(userId)) {
								throw new WebDataException("8-用户ID为空");
							}
							if (StringUtils.isBlank(start)) {
								throw new WebDataException("9-起始标志为空");
							}
							try {
								Integer.valueOf(start);
							} catch (Exception e) {
								throw new WebDataException("9-起始标志错误");
							}
							if (StringUtils.isBlank(count)) {
								throw new WebDataException("10-条数标志为空");
							}
							try {
								Integer.valueOf(count);
							} catch (Exception e) {
								throw new WebDataException("10-条数标志错误");
							}
							String key = getRequestKey() + count + start + type;
							Element el = commonQueryCache.get(key);
							List<DrawingDTO> drawingDTOList = Lists
									.newArrayList();
							if (el == null) {
								DrawingOrderState drawingState = null;
								if (StringUtils.isNotBlank(type)) {
									try {
										for (DrawingOrderState drawingOrderStateTemp : DrawingOrderState
												.values()) {
											if (type.trim().equals(
													drawingOrderStateTemp
															.name()))
												drawingState = drawingOrderStateTemp;
										}
									} catch (Exception e) {
										throw new WebDataException("11-提款状态错误");
									}
								}

								XDetachedCriteria criteria = new XDetachedCriteria(
										DrawingOrder.class, "m");
								criteria.add(Restrictions.eq("m.userId", user
										.getId()));

								if (drawingState != null)
									criteria.add(Restrictions.eq("m.state",
											drawingState));
								List<DrawingOrder> list = queryService
										.findByDetachedCriteria(criteria,
												Integer.valueOf(start), Integer
														.valueOf(count), true);
								el = new Element(key, list);
								commonQueryCache.put(el);
								for (DrawingOrder drawingOrder : list) {
									DrawingDTO drawingDTO = new DrawingDTO();
									drawingDTO.setCreateTime(DateUtil
											.dateToStr(drawingOrder
													.getCreateTime(),
													"yyyy-MM-dd HH:mm:ss"));
									drawingDTO
											.setMoney(drawingOrder.getMoney());
									drawingDTO.setEnable(drawingOrder
											.getEnable());
									drawingDTO
											.setState(drawingOrder.getState());
									drawingDTOList.add(drawingDTO);
								}
							} else {
								List<DrawingOrder> list = (List<DrawingOrder>) el
										.getValue();
								for (DrawingOrder drawingOrder : list) {
									DrawingDTO drawingDTO = new DrawingDTO();
									drawingDTO.setCreateTime(DateUtil
											.dateToStr(drawingOrder
													.getCreateTime(),
													"yyyy-MM-dd HH:mm:ss"));
									drawingDTO
											.setMoney(drawingOrder.getMoney());
									drawingDTO.setEnable(drawingOrder
											.getEnable());
									drawingDTO
											.setState(drawingOrder.getState());
									drawingDTOList.add(drawingDTO);
								}
							}
							map.put("drawingList", drawingDTOList);
							map.put("processId", "0");
						}
						if ("1".equals(update.trim())) {
							// 提款
							UserInfo info = user.getInfo();
							BankInfo bank = user.getBank();
							if (null != info) {
								if (StringUtils.isBlank(info.getRealName())
										|| StringUtils
												.isBlank(info.getIdCard())) {
									throw new WebDataException("9-请先设置用户个人资料");
								}
							} else {
								throw new WebDataException("9-请先设置用户个人资料");
							}
							if (null != bank) {
								if (StringUtils.isBlank(bank.getBankCard())
										|| StringUtils.isBlank(bank
												.getBankCard())) {
									throw new WebDataException("9-请先设置用户个人资料");
								}
							} else {
								throw new WebDataException("9-请先设置用户个人资料");
							}
							String drawingMoney = reqParamVisitor.getAmount();
							BigDecimal money = null;
							try {
								money = BigDecimalUtil.valueOf(Double
										.valueOf(drawingMoney));
							} catch (Exception e) {
								throw new WebDataException("10-提取金额格式出错");
							}
							if (money.doubleValue() <= Double.valueOf(0)) {
								throw new WebDataException("10-提取金额不能少于等于0");
							}
							if (money.doubleValue() > user.getRemainMoney()
									.doubleValue()) {
								throw new WebDataException("10-提取金额不能超过余额");
							}
							synchronized (Constant.DRAWING) {
								DrawingOrder drawingOrder = userManager
										.oprDrawingOrder(user, money, bank,
												null);
								if (null == drawingOrder) {
									throw new WebDataException("11-提取失败");
								}
								DrawingDTO drawingDTO = new DrawingDTO();
								drawingDTO.setCreateTime(DateUtil.dateToStr(
										drawingOrder.getCreateTime(),
										"yyyy-MM-dd HH:mm:ss"));
								drawingDTO.setMoney(drawingOrder.getMoney());
								drawingDTO.setEnable(drawingOrder.getEnable());
								drawingDTO.setState(drawingOrder.getState());
								map.put("drawingOrder", drawingDTO);
								map.put("processId", "0");
							}
						}
					}
				} else {
					throw new WebDataException("5-ReqParam参数解析错误");
				}
			} else {
				throw new WebDataException("5-ReqParam参数解析错误");
			}
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String userDetails() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String userId = reqParamVisitor.getUserId();
			if (StringUtils.isBlank(userId)) {
				throw new WebDataException("8-用户ID为空");
			}
			User user = userManager.getUser(Long.valueOf(userId));
			if (null == user)
				throw new WebDataException("8-用户ID错误");
			map.put("user", user);
			map.put("processId", "0");

			jsonConfig.setExcludes(new String[] { "password", "id",
					"tkpassword", "consumptionMoney", "createTime",
					"lastModifyTime", "lastModifyTime", "agentId", "info",
					 "bank" });

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String ipsList() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			List<PayWay> list = Lists.newArrayList();
			for (PayWay payWay : PayWay.values()) {
				list.add(payWay);
			}
			map.put("payWay", list);
			map.put("processId", "0");
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String userCheckPhone() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			if (null == user) {
				throw new WebDataException("8-用户错误");
			}
			Date now = new Date();
			map.put("checked", false);
			map.put("processId", "0");

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String sendRandomMessage() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				String mobile = null == wParam_map.get("mobile") ? null
						: String.valueOf(wParam_map.get("mobile"));
				if (StringUtils.isBlank(mobile)) {
					throw new WebDataException("8-电话号码为空");
				}
				mobile = mobile.trim();
				if (!mobile.matches(RegexUtils.Number))
					throw new WebDataException("8-电话号码错误.");
				String messageTypeStr = null == wParam_map.get("messageType") ? null
						: String.valueOf(wParam_map.get("messageType"));
				if (StringUtils.isBlank(messageTypeStr)) {
					throw new WebDataException("9-活动类型为空");
				}
				MessageType messageType = null;
				for (MessageType messageTypeTemp : MessageType.values()) {
					if (messageTypeTemp.name().equals(messageTypeStr))
						messageType = messageTypeTemp;
				}
				if (null == messageType)
					throw new WebDataException("9-活动类型错误");
				if (null == user) {
					throw new WebDataException("10-用户为空");
				}
				try {
					//发送验证码
				} catch (ServiceException e) {
					throw new ServiceException("11-" + e.getMessage());
				} catch (Exception e) {
					throw new WebDataException("11-验证码发送错误");
				}
				map.put("processId", "0");
			}
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String useRandomMessage() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				String mobile = null == wParam_map.get("mobile") ? null
						: String.valueOf(wParam_map.get("mobile"));
				if (StringUtils.isBlank(mobile)) {
					throw new WebDataException("8-电话号码为空");
				}
				String messageTypeStr = null == wParam_map.get("messageType") ? null
						: String.valueOf(wParam_map.get("messageType"));
				if (StringUtils.isBlank(messageTypeStr)) {
					throw new WebDataException("9-活动类型为空");
				}
				MessageType messageType = null;
				for (MessageType messageTypeTemp : MessageType.values()) {
					if (messageTypeTemp.name().equals(messageTypeStr))
						messageType = messageTypeTemp;
				}
				if (null == messageType)
					throw new WebDataException("9-活动类型错误");
				String randomWord = null == wParam_map.get("randomWord") ? null
						: String.valueOf(wParam_map.get("randomWord"));
				if (StringUtils.isBlank(randomWord)) {
					throw new WebDataException("10-验证码为空");
				}
				try {
					//发送验证码
				} catch (Exception e) {
					throw new WebDataException("10-验证码验证错误");
				}
				map.put("processId", "0");
			}
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String phonePopu() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();

			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					PhonePopu phonePopu = new PhonePopu();
					String model = null == wParam_map.get("model") ? null
							: String.valueOf(wParam_map.get("model"));
					if (StringUtils.isBlank(model)) {
						throw new WebDataException("8-型号为空");
					}
					phonePopu.setModel(model);
					String sdk = null == wParam_map.get("sdk") ? null : String
							.valueOf(wParam_map.get("sdk"));
					if (StringUtils.isBlank(sdk)) {
						throw new WebDataException("8-sdk为空");
					}
					phonePopu.setSdk(sdk);
					String number = null == wParam_map.get("number") ? null
							: String.valueOf(wParam_map.get("number"));
					phonePopu.setNumber(number);
					String imei = null == wParam_map.get("imei") ? null
							: String.valueOf(wParam_map.get("imei"));
					if (StringUtils.isBlank(imei)) {
						throw new WebDataException("8-imei为空");
					}
					phonePopu.setImei(imei);

					String imsi = null == wParam_map.get("imsi") ? null
							: String.valueOf(wParam_map.get("imsi"));
					if (StringUtils.isBlank(imsi)) {
						throw new WebDataException("8-imsi为空");
					}
					phonePopu.setImsi(imsi);

					String mid = null == wParam_map.get("mid") ? null : String
							.valueOf(wParam_map.get("mid"));
					if (StringUtils.isNotBlank(mid)) {
						Media media = userManager.getMedia(Long.valueOf(mid));
						if (null != media) {
							phonePopu.setMid(media.getId());
							phonePopu.setMediaName(media.getName());
						}
					}
					phonePopu = userManager.savePhonePopu(phonePopu);
					jsonConfig.setExcludes(new String[] { "createTime" });

					map.put("phonePopu", phonePopu);
				}
				map.put("processId", "0");
			}
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String newver() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			NewverDTO newverDTO = new NewverDTO();
			map.put("newver", newverDTO);
			map.put("processId", "0");
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public static void main(String[] args) {
		Map map = Maps.newHashMap();
		activityListDTO activityListDTO = new activityListDTO();
		map.put("activityList", activityListDTO);
		map.put("processId", "0");
	}

	public String activity() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			activityListDTO activityListDTO = new activityListDTO();
			map.put("activityList", activityListDTO.getActivityListDTO());
			map.put("processId", "0");
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String report() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			if (null == user)
				throw new WebDataException("8-用户ID错误");
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					String report = null == wParam_map.get("report") ? null
							: String.valueOf(wParam_map.get("report"));
					// 处理意见返回
					UserReport userReport = new UserReport();
					userReport.setUserid(user.getId());
					if (null != this.platformInfo) {
						userReport.setAgentInfo(platformInfo);

					}
					if (report.length() > 1500) {
						throw new WebDataException("9-最大长度不能超过500字");
					}
					userReport.setReport(report);
					userReport.setAnswer(null);
					userManager.saveUserReport(userReport);
				}
				map.put("processId", "0");
			}
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String rule() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					XDetachedCriteria criteria = new XDetachedCriteria(
							Rule.class, "m");

					String wLotteryId = null == wParam_map.get("wLotteryId") ? null
							: String.valueOf(wParam_map.get("wLotteryId"));
					String playTypeStr = null == wParam_map.get("playType") ? null
							: String.valueOf(wParam_map.get("playType"));

					Lottery lottery = null;
					try {
						lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
					} catch (Exception e) {
						throw new WebDataException("8-彩种错误");
					}
					criteria.add(Restrictions.eq("m.lotteryType", lottery));
					Integer playType = null;
					try {
						playType = Integer.valueOf(playTypeStr);
					} catch (Exception e) {
						throw new WebDataException("9-玩法错误");
					}
					criteria.add(Restrictions.eq("m.lotteryType", lottery));
					criteria.add(Restrictions.eq("m.playType", playType));

					List list = queryService.findByDetachedCriteria(criteria);

					if (null != list && !list.isEmpty()) {
						Rule rule = (Rule) list.get(0);
						map.put("rule", rule);
						jsonConfig.setExcludes(new String[] { "id",
								"createTime", "lastModifyTime" });

					}
				}
			}
			map.put("processId", "0");
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	/**
	 * 
	 */
	public String resultList() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		StringBuffer sb = new StringBuffer();
		try {
			check();
			Lottery[] lotteryArr = LotteryUtil.getPhoneLotteryList();
			List<PeriodDataDTO> resultList = Lists.newArrayList();
			for (Lottery lottery : lotteryArr) {
				if (lottery.getCategory().equals(LotteryCategory.FREQUENT)) {
					Class<KenoPeriod> cls = this.getKenoService(lottery)
							.getIssueDataClass();
					DetachedCriteria criteria = DetachedCriteria.forClass(cls);
					criteria.add(Restrictions.gt("state",
							IssueState.ISSUE_SATE_RESULT));
					criteria.add(Restrictions.isNotNull("results"));
					criteria.addOrder(Order.desc("id"));
					List<KenoPeriod> list = this.getKenoService(lottery)
							.findKenoPeriodByCriteria(criteria, 1);
					if (null != list && !list.isEmpty()) {
						KenoPeriod kenoPeriod = list.get(0);
						if (null != kenoPeriod) {
							PeriodDataDTO periodDataDTO = new PeriodDataDTO();
							periodDataDTO.setLotteryType(kenoPeriod
									.getLotteryType());
							periodDataDTO.setPeriodId(kenoPeriod.getId());
							periodDataDTO.setPeriodNumber(kenoPeriod
									.getPeriodNumber());
							periodDataDTO.setPeriodTitle(kenoPeriod
									.getLotteryType().getTitle());
							periodDataDTO.setPrizeTime(DateUtil.dateToStr(
									kenoPeriod.getPrizeTime(),
									"yyyy-MM-dd HH:mm:ss"));
							periodDataDTO.setResult(kenoPeriod.getResults());
							resultList.add(periodDataDTO);
						}
					}
				} else {
					if (Lottery.DCZC.equals(lottery)){
						List<Period> periodList = this.periodManager.findCurrentPeriods(true);//因为没有多期开售
						if(null==periodList||periodList.isEmpty()){
							periodList = this.periodManager.findOldPeriods(lottery, 1, true);
						}
						if(null!=periodList&&!periodList.isEmpty()){
							Period period = periodList.get(0);
							if(null==period){
								this.periodManager.findOldPeriods(lottery, 1, true);
							}
							List<DczcMatch> matchList = dczcMatchEntityManager.findMatchs(period.getId());
							for (DczcMatch dczcMatch : matchList) {
								if(dczcMatch.isEnded()&&null!=dczcMatch.getFullHomeScore()&&null!=dczcMatch.getGuestTeamName()){
									PeriodDataDTO periodDataDTO = new PeriodDataDTO();
									periodDataDTO.setLotteryType(period.getLotteryType());
									periodDataDTO.setPeriodId(period.getId());
									periodDataDTO.setPeriodNumber(period
											.getPeriodNumber());
									periodDataDTO.setPeriodTitle(period
											.getLotteryType().getTitle());
									periodDataDTO.setPrizeTime(DateUtil.dateToStr(
											dczcMatch.getLastModifyTime(),
											"yyyy-MM-dd HH:mm:ss"));
									String result = null == dczcMatch.getResult(com.cai310.lottery.support.dczc.PlayType.SPF)?"":dczcMatch.getResult(com.cai310.lottery.support.dczc.PlayType.SPF).getText();
									periodDataDTO.setResult(dczcMatch.getHomeTeamName()+dczcMatch.getFullHomeScore()+":"+dczcMatch.getFullGuestScore()+dczcMatch.getGuestTeamName()+"-"+result);
									resultList.add(periodDataDTO);
									break;
								}
							}
						}
					}else if (Lottery.JCZQ.equals(lottery)){
						JczqMatch jczqMatch = jczqMatchEntityManager.findLastMatchsOfEnd();
						if(jczqMatch.isEnded()&&null!=jczqMatch.getFullHomeScore()&&null!=jczqMatch.getGuestTeamName()){
									Period period = periodManager.getPeriod(jczqMatch.getPeriodId());
									if(null!=period){
									    PeriodDataDTO periodDataDTO = new PeriodDataDTO();
										periodDataDTO.setLotteryType(period.getLotteryType());
										periodDataDTO.setPeriodId(jczqMatch.getId());
										periodDataDTO.setPeriodNumber(""+jczqMatch.getMatchDate());
										periodDataDTO.setPeriodTitle(period
												.getLotteryType().getTitle());
										periodDataDTO.setPrizeTime(DateUtil.dateToStr(
												jczqMatch.getLastModifyTime(),
												"yyyy-MM-dd HH:mm:ss"));
										String result = null == jczqMatch.getResult(com.cai310.lottery.support.jczq.PlayType.SPF)?"":jczqMatch.getResult(com.cai310.lottery.support.jczq.PlayType.SPF).getText();
										periodDataDTO.setResult(jczqMatch.getHomeTeamName()+jczqMatch.getFullHomeScore()+":"+jczqMatch.getFullGuestScore()+jczqMatch.getGuestTeamName()+"-"+result);
										resultList.add(periodDataDTO);
									}
						}
					}else if (Lottery.JCLQ.equals(lottery)){
						JclqMatch jclqMatch = jclqMatchEntityManager.findLastMatchsOfEnd();
						if(jclqMatch.isEnded()&&null!=jclqMatch.getHomeScore()&&null!=jclqMatch.getGuestTeamName()){
									Period period = periodManager.getPeriod(jclqMatch.getPeriodId());
									if(null!=period){
									    PeriodDataDTO periodDataDTO = new PeriodDataDTO();
										periodDataDTO.setLotteryType(period.getLotteryType());
										periodDataDTO.setPeriodId(jclqMatch.getId());
										periodDataDTO.setPeriodNumber(""+jclqMatch.getMatchDate());
										periodDataDTO.setPeriodTitle(period
												.getLotteryType().getTitle());
										periodDataDTO.setPrizeTime(DateUtil.dateToStr(
												jclqMatch.getLastModifyTime(),
												"yyyy-MM-dd HH:mm:ss"));
										String result = null==jclqMatch.getResult(com.cai310.lottery.support.jclq.PlayType.SF)?"":jclqMatch.getResult(com.cai310.lottery.support.jclq.PlayType.SF).getText();
										periodDataDTO.setResult(jclqMatch.getHomeTeamName()+jclqMatch.getHomeScore()+":"+jclqMatch.getGuestScore()+jclqMatch.getGuestTeamName()+"-"+result);
										resultList.add(periodDataDTO);
									}
						}
					}else{
						PeriodData periodData = (PeriodData) this
								.getPeriodDataEntityManager(lottery)
								.getNewestResultPeriodData();
						if (null != periodData
								&& StringUtils.isNotBlank(periodData.getResult())) {
							Period period = periodManager.getPeriod(periodData
									.getPeriodId());
							if (null != period) {
								PeriodDataDTO periodDataDTO = new PeriodDataDTO();
								periodDataDTO.setLotteryType(period
										.getLotteryType());
								periodDataDTO.setPeriodId(period.getId());
								periodDataDTO.setPeriodNumber(period
										.getPeriodNumber());
								periodDataDTO.setPeriodTitle(period
										.getLotteryType().getTitle());
								periodDataDTO.setPrizeTime(DateUtil.dateToStr(
										period.getPrizeTime(),
										"yyyy-MM-dd HH:mm:ss"));
								periodDataDTO.setResult(periodData.getResult());
								resultList.add(periodDataDTO);
							}
						}
					}
				}
			}
			Collections.sort(resultList, new Comparator<PeriodDataDTO>() {
				public int compare(PeriodDataDTO o1, PeriodDataDTO o2) {
					Integer io2=o2.getLotteryType().getCategory().ordinal();
					Integer io1=o1.getLotteryType().getCategory().ordinal();
					return io2.compareTo(io1);
				}
			});
			map.put("processId", "0");
			map.put("totalCount", resultList.size());
			map.put("resultList", resultList);
		} catch (WebDataException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		}
		renderJson(map, jsonConfig);
		return null;
	}

	/**
	 * 
	 */
	public String findCurrentPeriods() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		StringBuffer sb = new StringBuffer();
		List<Map<String, String>> flag = Lists.newArrayList();
		try {
			check();
			Lottery lottery = null;
			if (StringUtils.isNotBlank(wParam)) {
				Map<String, Object> wParamMap = JsonUtil.getMap4Json(wParam);
				if (null != wParamMap) {
					String wLotteryId = null == wParamMap.get("wLotteryId") ? null
							: String.valueOf(wParamMap.get("wLotteryId"));
					if (StringUtils.isNotBlank(wLotteryId)) {
						lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
					}
				}
			}
			List<Period> currentPeriods = null;
			if (null == lottery) {
				currentPeriods = periodManager.findCurrentPeriods(true);
			} else {
				currentPeriods = periodManager
						.findCurrentPeriods(lottery, true);
			}
			List<PeriodDTO> periodDTOList = Lists.newArrayList();
			Map<Lottery, Period> currentMap = Maps.newHashMap();
			for (Period period : currentPeriods) {
				PeriodDTO periodDTO = new PeriodDTO();
				if (period.isOnSale() || period.isPaused()) {
					if (null == currentMap.get(period.getLotteryType())) {
						currentMap.put(period.getLotteryType(), period);
					} else {
						continue;
					}
					periodDTO.setPeriodNumber(period.getPeriodNumber());
					periodDTO.setEndedTime(DateUtil.dateToStr(period
							.getEndedTime(), "yyyy-MM-dd HH:mm:ss"));
					periodDTO.setLotteryType(period.getLotteryType());
					periodDTO
							.setPeriodTitle(period.getLotteryType().getTitle());
					periodDTO.setPrizeTime(DateUtil.dateToStr(period
							.getPrizeTime(), "yyyy-MM-dd HH:mm:ss"));
					periodDTO.setState(period.getState());
					for (PeriodSales periodSales : periodManager
							.findPeriodSales(period.getId())) {
						if (SalesMode.SINGLE.equals(periodSales.getId()
								.getSalesMode())) {
							periodDTO.setEndJoinTime_single(DateUtil.dateToStr(
									periodSales.getEndJoinTime(),
									"yyyy-MM-dd HH:mm:ss"));
							periodDTO.setSelfEndInitTime_single(DateUtil
									.dateToStr(
											periodSales.getSelfEndInitTime(),
											"yyyy-MM-dd HH:mm:ss"));
							periodDTO.setShareEndInitTime_single(DateUtil
									.dateToStr(periodSales
											.getShareEndInitTime(),
											"yyyy-MM-dd HH:mm:ss"));
						}
						if (SalesMode.COMPOUND.equals(periodSales.getId()
								.getSalesMode())) {
							periodDTO.setEndJoinTime_compound(DateUtil
									.dateToStr(periodSales.getEndJoinTime(),
											"yyyy-MM-dd HH:mm:ss"));
							periodDTO.setSelfEndInitTime_compound(DateUtil
									.dateToStr(
											periodSales.getSelfEndInitTime(),
											"yyyy-MM-dd HH:mm:ss"));
							periodDTO.setShareEndInitTime_compound(DateUtil
									.dateToStr(periodSales
											.getShareEndInitTime(),
											"yyyy-MM-dd HH:mm:ss"));
						}
					}
					if (period.getLotteryType().equals(Lottery.PL)) {
						periodDTO.setPlayType("P5Direct");
						periodDTOList.add(periodDTO);
						PeriodDTO periodDTONew = new PeriodDTO();
						PropertyUtils.copyProperties(periodDTONew, periodDTO);
						periodDTONew.setPlayType("P3Direct");
						periodDTOList.add(periodDTONew);
					} else if (period.getLotteryType().equals(Lottery.SFZC)) {
						periodDTO.setPlayType("SFZC9");
						periodDTOList.add(periodDTO);
						PeriodDTO periodDTONew = new PeriodDTO();
						PropertyUtils.copyProperties(periodDTONew, periodDTO);
						periodDTONew.setPlayType("SFZC14");
						periodDTOList.add(periodDTONew);
					} else {
						periodDTOList.add(periodDTO);
					}
				}
			}
			List<Lottery> kenoPeriods = Lists.newArrayList();
			if (null == lottery) {
				kenoPeriods.add(Lottery.EL11TO5);
			} else {
				if (Lottery.EL11TO5.equals(lottery)) {
					kenoPeriods.add(lottery);
				}
			}

			for (Lottery kenoLottery : kenoPeriods) {
				KenoPeriod kenoPeriod = this.getKenoManager(kenoLottery)
						.getCurrentData();
				PeriodDTO periodDTO = new PeriodDTO();
				if (null != kenoPeriod
						&& (IssueState.ISSUE_SATE_CURRENT.equals(kenoPeriod
								.getState()) || IssueState.ISSUE_SATE_READY
								.equals(kenoPeriod.getState()))) {
					periodDTO.setPeriodNumber(kenoPeriod.getPeriodNumber());
					periodDTO.setEndedTime(DateUtil.dateToStr(kenoPeriod
							.getEndedTime(), "yyyy-MM-dd HH:mm:ss"));
					periodDTO.setLotteryType(kenoPeriod.getLotteryType());
					periodDTO.setPeriodTitle(kenoPeriod.getLotteryType()
							.getTitle());
					periodDTO.setPrizeTime(DateUtil.dateToStr(kenoPeriod
							.getPrizeTime(), "yyyy-MM-dd HH:mm:ss"));
					periodDTO.setState(PeriodState.Started);
					int eheadTime = -3;// 提前3分钟截止
					periodDTO.setSelfEndInitTime_compound(DateUtil.dateToStr(
							DateUtils.addMinutes(kenoPeriod.getEndedTime(),
									eheadTime), "yyyy-MM-dd HH:mm:ss"));
					periodDTOList.add(periodDTO);
				}
			}
			map.put("processId", "0");

			map.put("periodList", periodDTOList);
		} catch (WebDataException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		}
		renderJson(map, jsonConfig);
		return null;
	}
	
	public void checkTicket() throws WebDataException, UnsupportedEncodingException {
		this.wAction = Struts2Utils.getParameter("wAction");
		this.wParam = Struts2Utils.getParameter("wParam");
		this.wSign = Struts2Utils.getParameter("wSign");
		this.wAgent = Struts2Utils.getParameter("wAgent");
		if (StringUtils.isBlank(wAction)) {
			throw new WebDataException("4-请求Id为空");
		}
		if (StringUtils.isBlank(wParam)) {
			throw new WebDataException("5-请求参数为空");
		}
		if (StringUtils.isBlank(wSign)) {
			throw new WebDataException("1-请求密钥为空");
		}
		
		if (wParam.contains("%")){
			wParam = URLDecoder.decode(wParam,"UTF-8");
		}
		
		try {
			reqParamVisitor = new ReqParamVisitor();
			reqParamVisitor.visit(wParam);
			String userWayStr = reqParamVisitor.getUserWay();
			if (StringUtils.isNotBlank(userWayStr)) {
				userWay = UserWay.getUserWayByName(userWayStr);
			}
			String payWayStr = reqParamVisitor.getPayWay();
			if (StringUtils.isNotBlank(payWayStr)) {
				payWay = PayWay.getPayWayByName(payWayStr);
			}

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new WebDataException("5-ReqParam参数解析错误");
		}
		try {
			try{
				ticketPlatformInfo = ticketThenEntityManager.getTicketPlatformInfo(Long.valueOf(this.wAgent.trim()));
				if(null==ticketPlatformInfo){
					throw new WebDataException("1-平台号为空");
				}
			}catch(Exception e){
				logger.warn(e.getMessage(), e);
				throw new WebDataException("1-平台号错误");
			}
			String param = wAction+wParam+wAgent+ticketPlatformInfo.getPassword();
			String pwd = SecurityUtil.md5(param.getBytes("UTF-8")).toUpperCase().trim();
			if(!pwd.equals(wSign.trim())){
				throw new WebDataException("1-请求密钥验证错误");
			}
			String userId = reqParamVisitor.getUserId();
			if (StringUtils.isNotBlank(userId)) {
				User user = userManager.getUser(Long.valueOf(userId));
				if (user == null)
					throw new WebDataException("1-找不到用户ID对应的用户.");
				if (user.isLocked())
					throw new WebDataException("2-帐号被冻结");
				this.user = user;
				checkUser(reqParamVisitor.getUserPwd(), user);
			}
		} catch (WebDataException e) {
			logger.warn(e.getMessage(), e);
			throw new WebDataException(e.getMessage());
		}

	}
	/**
	 * 
	 */
	public String findCurrentPeriodsTicket() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		StringBuffer sb = new StringBuffer();
		List<Map<String, String>> flag = Lists.newArrayList();
		try {
			checkTicket();
			
			if (wParam.contains("%")){
				wParam = URLDecoder.decode(wParam,"UTF-8");
			}
			
			Lottery lottery = null;
			if (StringUtils.isNotBlank(wParam)) {
				Map<String, Object> wParamMap = JsonUtil.getMap4Json(wParam);
				if (null != wParamMap) {
					String wLotteryId = null == wParamMap.get("wLotteryId") ? null
							: String.valueOf(wParamMap.get("wLotteryId"));
					if (StringUtils.isNotBlank(wLotteryId)) {
						lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
					}
				}
			}
			List<Period> currentPeriods = null;
			if (null == lottery) {
				currentPeriods = periodManager.findCurrentPeriods(true);
			} else {
				currentPeriods = periodManager
						.findCurrentPeriods(lottery, true);
			}
			List<PeriodDTO> periodDTOList = Lists.newArrayList();
			Map<Lottery, Period> currentMap = Maps.newHashMap();
			for (Period period : currentPeriods) {
				PeriodDTO periodDTO = new PeriodDTO();
				if (period.isOnSale() || period.isPaused()) {
					if (null == currentMap.get(period.getLotteryType())) {
						currentMap.put(period.getLotteryType(), period);
					} else {
						continue;
					}
					periodDTO.setPeriodNumber(period.getPeriodNumber());
					periodDTO.setEndedTime(DateUtil.dateToStr(period
							.getEndedTime(), "yyyy-MM-dd HH:mm:ss"));
					periodDTO.setLotteryType(period.getLotteryType());
					periodDTO
							.setPeriodTitle(period.getLotteryType().getTitle());
					periodDTO.setPrizeTime(DateUtil.dateToStr(period
							.getPrizeTime(), "yyyy-MM-dd HH:mm:ss"));
					periodDTO.setState(period.getState());
					for (PeriodSales periodSales : periodManager
							.findPeriodSales(period.getId())) {
						if (SalesMode.SINGLE.equals(periodSales.getId()
								.getSalesMode())) {
							periodDTO.setEndJoinTime_single(DateUtil.dateToStr(
									periodSales.getEndJoinTime(),
									"yyyy-MM-dd HH:mm:ss"));
							periodDTO.setSelfEndInitTime_single(DateUtil
									.dateToStr(
											periodSales.getSelfEndInitTime(),
											"yyyy-MM-dd HH:mm:ss"));
							periodDTO.setShareEndInitTime_single(DateUtil
									.dateToStr(periodSales
											.getShareEndInitTime(),
											"yyyy-MM-dd HH:mm:ss"));
						}
						if (SalesMode.COMPOUND.equals(periodSales.getId()
								.getSalesMode())) {
							periodDTO.setEndJoinTime_compound(DateUtil
									.dateToStr(periodSales.getEndJoinTime(),
											"yyyy-MM-dd HH:mm:ss"));
							periodDTO.setSelfEndInitTime_compound(DateUtil
									.dateToStr(
											periodSales.getSelfEndInitTime(),
											"yyyy-MM-dd HH:mm:ss"));
							periodDTO.setShareEndInitTime_compound(DateUtil
									.dateToStr(periodSales
											.getShareEndInitTime(),
											"yyyy-MM-dd HH:mm:ss"));
						}
					}
					if (period.getLotteryType().equals(Lottery.PL)) {
						periodDTO.setPlayType("P5Direct");
						periodDTOList.add(periodDTO);
						PeriodDTO periodDTONew = new PeriodDTO();
						PropertyUtils.copyProperties(periodDTONew, periodDTO);
						periodDTONew.setPlayType("P3Direct");
						periodDTOList.add(periodDTONew);
					} else if (period.getLotteryType().equals(Lottery.SFZC)) {
						periodDTO.setPlayType("SFZC9");
						periodDTOList.add(periodDTO);
						PeriodDTO periodDTONew = new PeriodDTO();
						PropertyUtils.copyProperties(periodDTONew, periodDTO);
						periodDTONew.setPlayType("SFZC14");
						periodDTOList.add(periodDTONew);
					} else {
						periodDTOList.add(periodDTO);
					}
				}
			}
			List<Lottery> kenoPeriods = Lists.newArrayList();
			if (null == lottery) {
				kenoPeriods.add(Lottery.SDEL11TO5);
				kenoPeriods.add(Lottery.SSC);
				kenoPeriods.add(Lottery.AHKUAI3);
				kenoPeriods.add(Lottery.GDEL11TO5);
				kenoPeriods.add(Lottery.EL11TO5);
				kenoPeriods.add(Lottery.XJEL11TO5);
			} else {
				if (Lottery.SDEL11TO5.equals(lottery)
						|| Lottery.SSC.equals(lottery)
						||Lottery.AHKUAI3.equals(lottery)||Lottery.GDEL11TO5.equals(lottery)||Lottery.EL11TO5.equals(lottery)||Lottery.XJEL11TO5.equals(lottery)) {
					kenoPeriods.add(lottery);
				}
			}

			for (Lottery kenoLottery : kenoPeriods) {
				KenoPeriod kenoPeriod = this.getKenoManager(kenoLottery)
						.getCurrentData();
				PeriodDTO periodDTO = new PeriodDTO();
				if (null != kenoPeriod
						&& (IssueState.ISSUE_SATE_CURRENT.equals(kenoPeriod
								.getState()) || IssueState.ISSUE_SATE_READY
								.equals(kenoPeriod.getState()))) {
					periodDTO.setPeriodNumber(kenoPeriod.getPeriodNumber());
					periodDTO.setEndedTime(DateUtil.dateToStr(kenoPeriod
							.getEndedTime(), "yyyy-MM-dd HH:mm:ss"));
					periodDTO.setLotteryType(kenoPeriod.getLotteryType());
					periodDTO.setPeriodTitle(kenoPeriod.getLotteryType()
							.getTitle());
					periodDTO.setPrizeTime(DateUtil.dateToStr(kenoPeriod
							.getPrizeTime(), "yyyy-MM-dd HH:mm:ss"));
					periodDTO.setState(PeriodState.Started);
					int eheadTime = -3;// 提前3分钟截止
					periodDTO.setSelfEndInitTime_compound(DateUtil.dateToStr(
							DateUtils.addMinutes(kenoPeriod.getEndedTime(),
									eheadTime), "yyyy-MM-dd HH:mm:ss"));
					periodDTOList.add(periodDTO);
				}
			}
			map.put("processId", "0");

			map.put("periodList", periodDTOList);
		} catch (WebDataException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		}
		renderJson(map, jsonConfig);
		return null;
	}
	public void alipaySingleTradeQuery(Ipsorder ipsorder)
			throws WebDataException {
//		if (null == ipsorder) {
//			throw new WebDataException("8-找不到的订单!");
//		}
//		String orderNo = "" + ipsorder.getId();
//
//		String paygateway = "https://www.alipay.com/cooperate/gateway.do?"; // 支付接口(不可修改)
//		String service = "single_trade_query";// 支付宝查询服务--单笔查询服务(不可修改)
//		String sign_type = "MD5";// 加密机制(不可修改)
//		String input_charset = AlipayConfig.charSet; // 页面编码(不可修改)
//		// partner和key提取方法：登陆签约支付宝账户--->点击“商家服务”就可以看到
//		String partner = AlipayConfig.partnerID; // 支付宝合作伙伴id (账户内提取)
//		String key = AlipayConfig.key; // 支付宝安全校验码(账户内提取)
//		String ItemUrl = Payment.CreateUrl(paygateway, service, partner,
//				sign_type, orderNo, key, input_charset);
//		Map<String, Object> result = new HashMap<String, Object>();
//		SAXReader reader = new SAXReader();
//		Document doc = null;
//		try {
//			doc = reader.read(new URL(ItemUrl).openStream());
//		} catch (Exception e) {
//			throw new WebDataException("9-网络错误支付宝!");
//		}
//		List<Node> list = doc.selectNodes("//response/trade/*");
//		Map<String, String> response = new HashMap<String, String>();
//		for (Node node : list) {
//			response.put(node.getName(), node.getText());
//		}
//		String out_trade_no = response.get("out_trade_no");
//		String total_fee = response.get("total_fee");
//		String trade_no = response.get("trade_no");
//		String trade_status = response.get("trade_status");
//		if (StringUtils.isBlank(total_fee) || StringUtils.isBlank(trade_no)) {
//			throw new WebDataException("10-该订单还未交易完成!");
//		}
//		if (!"TRADE_FINISHED".equals(trade_status)
//				|| "TRADE_SUCCESS".equals(trade_status)) {
//			throw new WebDataException("11-该订单充值不成功,请稍候操作!");
//		}
//		if (!orderNo.equals(out_trade_no)) {
//			throw new WebDataException("12-该订单号不正确!");
//		}
//		synchronized (Constant.Epay) {
//			if ("TRADE_FINISHED".equals(trade_status)
//					|| "TRADE_SUCCESS".equals(trade_status)) {
//				// 表示交易成功（TRADE_FINISHED/TRADE_SUCCESS）
//				if (trade_status.equals("TRADE_FINISHED")
//						|| trade_status.equals("TRADE_SUCCESS")) {
//					// 为了保证不被重复调用，或重复执行数据库更新程序，请判断该笔交易状态是否是订单未处理状态
//					if (StringUtils.isBlank(out_trade_no)) {
//						throw new WebDataException("13-订单号为空!");
//					}
//					User user = userManager.getUser(ipsorder.getUserid());
//					if (null == user) {
//						throw new WebDataException("14-找不到用户!");
//					}
//					if (Double.valueOf(total_fee)
//							- ipsorder.getAmount().doubleValue() != 0) {
//						throw new WebDataException("14-处理金额错误!");
//					}
//					synchronized (Constant.Epay) {
//						try {
//							userManager.confirmIps(ipsorder, user, null);
//						} catch (Exception e) {
//							throw new WebDataException("15-充值发生错误!");
//						}
//					}
//				} else {
//					// /失败转发
//					throw new WebDataException("16-交易失败!");
//				}
//			}
//		}
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

	public String schemeList() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String count = reqParamVisitor.getCount();
			String start = reqParamVisitor.getStart();
			String wLotteryId = reqParamVisitor.getwLotteryId();
			String userId = reqParamVisitor.getUserId();
			String state = reqParamVisitor.getState();
			if (StringUtils.isBlank(userId)) {
				throw new WebDataException("8-用户ID为空");
			}

			if (StringUtils.isBlank(start)) {
				throw new WebDataException("9-起始标志为空");
			}
			try {
				Integer.valueOf(start);
			} catch (Exception e) {
				throw new WebDataException("9-起始标志错误");
			}
			if (StringUtils.isBlank(count)) {
				throw new WebDataException("10-条数标志为空");
			}
			try {
				Integer.valueOf(count);
			} catch (Exception e) {
				throw new WebDataException("10-条数标志错误");
			}
			String winningStatus = null;
			Boolean won = null;
			String wonStr = null;
			WinningUpdateStatus winningUpdateStatus = null;
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					winningStatus = null == wParam_map
							.get("winningUpdateStatus") ? null : String
							.valueOf(wParam_map.get("winningUpdateStatus"));
					if (StringUtils.isNotBlank(winningStatus)) {
						try {
							for (WinningUpdateStatus winningUpdateStatusTemp : WinningUpdateStatus
									.values()) {
								if (winningStatus.trim().equals(
										winningUpdateStatusTemp.name()))
									winningUpdateStatus = winningUpdateStatusTemp;
							}
						} catch (Exception e) {
							throw new WebDataException("12-方案中奖状态错误");
						}
					}
					wonStr = null == wParam_map.get("won") ? null : String
							.valueOf(wParam_map.get("won"));
					if (StringUtils.isNotBlank(wonStr)) {
						try {
							won = Boolean.valueOf(wonStr);
						} catch (Exception e) {
							throw new WebDataException("13-方案中奖错误");
						}

					}
				}
			}
			String key = getRequestKey() + wLotteryId + count + start + state
					+ won + wonStr + winningUpdateStatus + winningStatus;
			Element el = commonQueryCache.get(key);
			if (el == null) {
				List<org.hibernate.criterion.Criterion> restrictions = Lists
						.newArrayList();
				SchemeQueryDTO dto = new SchemeQueryDTO();
				if (null != winningUpdateStatus) {
					restrictions.add(Restrictions.eq("m.winningUpdateStatus",
							winningUpdateStatus));
				}
				if (null != won) {
					dto.setWon(won);
				}
				if (StringUtils.isNotBlank(state)) {
					TicketSchemeState ticketSchemeState = null;
					try {
						for (TicketSchemeState ticketSchemeStateTemp : TicketSchemeState
								.values()) {
							if (state.trim().equals(
									ticketSchemeStateTemp.name()))
								ticketSchemeState = ticketSchemeStateTemp;
						}
					} catch (Exception e) {
						throw new WebDataException("11-方案状态错误");
					}
					if (ticketSchemeState.equals(TicketSchemeState.FAILD)) {
						/** 方案不成功的状态 */

						SchemeState[] UNSUCCESS = { SchemeState.CANCEL,
								SchemeState.REFUNDMENT, SchemeState.UNFULL };
						restrictions.add(Restrictions.or(Restrictions.in(
								"m.state", UNSUCCESS), Restrictions.and(
								Restrictions.eq("m.state", SchemeState.FULL),
								Restrictions.eq("m.schemePrintState",
										SchemePrintState.FAILED))));

					} else if (ticketSchemeState.equals(TicketSchemeState.FULL)) {
						restrictions.add(Restrictions.and(Restrictions.or(
								Restrictions.eq("m.schemePrintState",
										SchemePrintState.PRINT), Restrictions
										.eq("m.schemePrintState",
												SchemePrintState.UNPRINT)),
								Restrictions.eq("m.state", SchemeState.FULL)));
					} else if (ticketSchemeState
							.equals(TicketSchemeState.SUCCESS)) {
						restrictions.add(Restrictions.or(Restrictions.eq(
								"m.state", SchemeState.SUCCESS), Restrictions
								.and(Restrictions.eq("m.state",
										SchemeState.FULL), Restrictions.eq(
										"m.schemePrintState",
										SchemePrintState.SUCCESS))));
					}
				}
				dto.setRestrictions(restrictions);
				pagination = new Pagination(Integer.valueOf(start)
						+ Integer.valueOf(count));
				if (StringUtils.isBlank(wLotteryId)) {
					List<MyScheme> allList = Lists.newArrayList();
					for (Lottery lottery : Lottery.values()) {
						pagination = getSchemeEntityManager(lottery)
								.findMyScheme(user.getId(), dto, pagination);
						if (pagination != null
								&& pagination.getResult() != null
								&& !pagination.getResult().isEmpty()) {
							allList.addAll(pagination.getResult());
							Collections.sort(allList, MY_SCHEME_COMPARATOR);
						}
					}
					pagination.setResult(allList);
					pagination.setTotalCount(allList.size());
				} else {
					Lottery lotteryType = null;
					try {
						lotteryType = Lottery.values()[Integer
								.valueOf(wLotteryId.trim())];
					} catch (Exception e) {
						throw new WebDataException("12-彩种错误");
					}
					if (null == lotteryType) {
						throw new WebDataException("12-彩种错误");
					}
					pagination = getSchemeEntityManager(lotteryType)
							.findMyScheme(user.getId(), dto, pagination);

				}
				el = new Element(key, this.pagination);
				commonQueryCache.put(el);
			} else {
				pagination = (Pagination) el.getValue();
			}
			List<MyScheme> mySchemeList = Lists.newArrayList();
			if (null != pagination.getResult()) {
				List<MyScheme> schemeList = pagination.getResult();
				long pos = 0;
				for (MyScheme scheme : schemeList) {
					pos++;
					if (pos > Long.valueOf(start)) {
						mySchemeList.add(scheme);
						if (mySchemeList.size() == Integer.valueOf(count))
							break;
					}
				}
			}
			jsonConfig.setExcludes(new String[] { "createTime" });
			map.put("totalCount", mySchemeList.size());
			map.put("myScheme", mySchemeList);
			map.put("processId", "0");

			renderJson(map, jsonConfig);
			return null;
		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public void renderJson(Map map, JsonConfig jsonConfig) {
		String jsonString = JSONObject.fromObject(map, jsonConfig).toString();
		Struts2Utils.render("application/json", jsonString);
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

	public String infoList() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			String count = reqParamVisitor.getCount();
			String start = reqParamVisitor.getStart();
			String wLotteryId = reqParamVisitor.getwLotteryId();
			String type = reqParamVisitor.getType();
			if (StringUtils.isBlank(start)) {
				throw new WebDataException("9-起始标志为空");
			}
			try {
				Integer.valueOf(start);
			} catch (Exception e) {
				throw new WebDataException("9-起始标志错误");
			}
			if (StringUtils.isBlank(count)) {
				throw new WebDataException("10-条数标志为空");
			}
			try {
				Integer.valueOf(count);
			} catch (Exception e) {
				throw new WebDataException("10-条数标志错误");
			}
			if (StringUtils.isBlank(type)) {
				throw new WebDataException("11-类型为空");
			}
			try {
				Integer.valueOf(type);
			} catch (Exception e) {
				throw new WebDataException("11-类型错误");
			}
			String key = getRequestKey() + wLotteryId + count + start + type;
			Element el = commonQueryCache.get(key);
			if (el == null) {
				DetachedCriteria criteria = DetachedCriteria.forClass(
						MobileNewsData.class, "m");
				Lottery lotteryType = null;
				if (StringUtils.isNotBlank(wLotteryId)) {
					try {
						lotteryType = Lottery.values()[Integer
								.valueOf(wLotteryId.trim())];
					} catch (Exception e) {
						throw new WebDataException("12-彩种错误");
					}
					if (null == lotteryType) {
						throw new WebDataException("12-彩种错误");
					}
					criteria
							.add(Restrictions.eq("m.lotteryType", user.getId()));
				}
				try {
					MobileInfoType mobileInfoType = MobileInfoType.values()[Integer
							.valueOf(type)];
					if (null == mobileInfoType) {
						throw new WebDataException("11-类型错误");
					}
					criteria.add(Restrictions.eq("m.mobileInfoType",
							mobileInfoType));
				} catch (Exception e) {
					throw new WebDataException("11-类型错误");
				}
				criteria.addOrder(Order.desc("id"));
				List list = queryService.findByDetachedCriteria(criteria,
						Integer.valueOf(start), Integer.valueOf(count), true);
				el = new Element(key, list);
				commonQueryCache.put(el);
				map.put("totalCount", list.size());
				map.put("infoList", list);
			} else {
				List list = (List) el.getValue();
				map.put("totalCount", list.size());
				map.put("infoList", list);
			}
			jsonConfig.setExcludes(new String[] { "state", "lastModifyTime",
					"mobileInfoType", "createTime", "content", "lastContent" });
			map.put("processId", "0");

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
	}

	public String infoDetails() {
		// 登录操作
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			check();
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					String id = null == wParam_map.get("id") ? null : String
							.valueOf(wParam_map.get("id"));
					MobileNewsData mobileNewsData = newsInfoDataEntityManager
							.getMobileNewsData(Long.valueOf(id));
					map.put("newsInfoData", mobileNewsData);
				}
			}
			jsonConfig.setExcludes(new String[] { "state", "lastModifyTime",
					"mobileInfoType", "createTime" });

			map.put("processId", "0");

		} catch (WebDataException e) {
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			}
			map.put("processId", processId);

			map.put("errorMsg", "系统内部错误");
		}
		renderJson(map, jsonConfig);
		return null;
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

	public String getwAction() {
		return wAction;
	}

	public void setwAction(String wAction) {
		this.wAction = wAction;
	}

	public String getwParam() {
		return wParam;
	}

	public void setwParam(String wParam) {
		this.wParam = wParam;
	}

	public String getwSign() {
		return wSign;
	}

	public void setwSign(String wSign) {
		this.wSign = wSign;
	}

	public String getwAgent() {
		return wAgent;
	}

	public void setwAgent(String wAgent) {
		this.wAgent = wAgent;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public UserWay getUserWay() {
		return userWay;
	}

	public void setUserWay(UserWay userWay) {
		this.userWay = userWay;
	}

	public PayWay getPayWay() {
		return payWay;
	}

	public void setPayWay(PayWay payWay) {
		this.payWay = payWay;
	}

	public PlatformInfo getPlatformInfo() {
		return platformInfo;
	}

	public void setPlatformInfo(PlatformInfo platformInfo) {
		this.platformInfo = platformInfo;
	}

}
