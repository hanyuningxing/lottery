package com.cai310.lottery.web.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.cai310.lottery.common.AgentLotteryType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.MessageType;
import com.cai310.lottery.common.PopuType;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.Media;
import com.cai310.lottery.entity.user.Popu;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.lottery.entity.user.agent.AgentRebate;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.service.user.impl.UserWonRankManagerImpl;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.forum.ForumController;
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

@Results({
		@Result(name = UserController.FWD_WELCOME, type = "redirectAction", params = {
				"actionName", "user", "method", "welcome" }),
		@Result(name = UserController.EDIT_SUCCESS, type = "redirectAction", params = {
				"actionName", "user", "method", "edit" }),
		@Result(name = "message", location = "/WEB-INF/content/common/message.ftl") })
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
	private BankInfoForm bankInfoForm;

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
	AgentEntityManager agentEntityManager;
	@Autowired
	protected QueryService queryService;
	private RegForm regForm;
	private UserInfoForm infoForm;
	private BankForm bankForm;
	private PasswordForm pwdForm;
	private EmailValForm emailValForm;
	private String captcha;
	private UserLogin beforeUserLogin;

	public String index() {
		return welcome();
	}

	public String message() {
		return welcome();
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
		userRegistCache.put(new Element(getRegistKey(), Integer
				.valueOf(tryTimes)));
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

		userLoginCache.put(new Element(getLoginTryTimesKey(), Integer
				.valueOf(tryTimes)));

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

	public String userSafeManager() throws WebDataException {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}

		return "safeManager";
	}

	public String updateEmail() {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		try {
			String email = Struts2Utils.getParameter("email");
			if (StringUtils.isBlank(email)) {
				throw new WebDataException("email地址不能为空.");
			} else {
				UserInfo userInfo = loginUser.getInfo();
				userInfo.setEmail(email);
				userManager.saveUserInfo(userInfo);
			}
			return success();
		} catch (Exception e) {
		}
		return faile();
	}

	public String updateQQ() {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		try {
			String qq = Struts2Utils.getParameter("qq");
			if (StringUtils.isNotBlank(qq)) {
				UserInfo userInfo = loginUser.getInfo();
				userInfo.setQq(qq);
				userManager.saveUserInfo(userInfo);
			} else {
				throw new WebDataException("qq号码不能为空.");
			}
			return success();
		} catch (Exception e) {
		}
		return faile();
	}

	public String updatePostcode() {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		try {
			String postcode = Struts2Utils.getParameter("postcode");
			if (StringUtils.isNotBlank(postcode)) {
				UserInfo userInfo = loginUser.getInfo();
				userInfo.setPostcode(postcode);
				userManager.saveUserInfo(userInfo);
			} else {
				throw new WebDataException("邮政编码不能为空.");
			}
			return success();
		} catch (Exception e) {
		}
		return faile();
	}

	public String updateAddress() {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		try {
			String address = Struts2Utils.getParameter("address");
			if (StringUtils.isNotBlank(address)) {
				UserInfo userInfo = loginUser.getInfo();
				userInfo.setAddress(address);
				userManager.saveUserInfo(userInfo);
			} else {
				throw new WebDataException("联系地址不能为空.");
			}
			return success();
		} catch (Exception e) {
		}
		return faile();
	}

	/**
	 * 登录
	 */
	public String login() {
		User loginUser = getLoginUser();
		try {
			if (loginUser != null) {
				throw new WebDataException("你已经登录，请不要重复登录.");
			}
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
				String captcha = Struts2Utils.getParameter("captcha");
				if (StringUtils.isBlank(captcha)) {
					throw new WebDataException("为了您的用户安全，请输入验证码.");
				} else if (!captcha.equals(Struts2Utils.getSession()
						.getAttribute(Constant.LOGIN_CAPTCHA))) {
					this.jsonMap.put("captcha_error", true);
					throw new WebDataException("验证码错误.");
				}
			}

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
			} else {

				addActionMessage("登录成功.");
				Struts2Utils.getRequest().removeAttribute(NEED_CAPTCHA);
				clearLoginTryInfo();
				SsoLoginHolder.login(user.getId());

				// 记录登录信息
				UserLogin userLogin = userManager.getUserLoginBy(user.getId());
				if(null==userLogin || null == userLogin.getUserId()){
					userLogin = new UserLogin();
					userLogin.setUserId(user.getId());
					userLogin.setTryIp(Struts2Utils.getRemoteAddr());
					userLogin.setTryTime(new Date());
				}
				userLogin.setLastLoginIp(userLogin.getTryIp());
				userLogin.setLastLoginTime(userLogin.getTryTime());
				userLogin.setTryIp(Struts2Utils.getRemoteAddr());
				userLogin.setTryTime(new Date());
				userManager.saveUserLogin(userLogin);


				Boolean needRedirect = Boolean.FALSE;
				String redirectUrl = Struts2Utils.getParameter("redirectUrl");
				CookieUtil.getCookieByName(Struts2Utils.getRequest(),
						"redirectUrl");
				if (StringUtils.isBlank(redirectUrl)) {
					needRedirect = Boolean.FALSE;
				} else {
					needRedirect = Boolean.TRUE;
				}
				try{
//					String forumLoginReturn = ForumController.login(userName,password);
//					// 当用户在网站登录成功，但论坛没有此用户时，forumLoginReturn为"",此时在论坛注册此用户
//					
//					 if(forumLoginReturn != null && forumLoginReturn.equals("")) {
//						 String email = user.getUserId().toString() + "@miracle.com";
//						 ForumController.reg(userName, password, email);
//						 forumLoginReturn = ForumController.login(userName,password);
//					 }
//					 this.jsonMap.put("forumLoginReturn", forumLoginReturn);
				}catch(Exception e){
					logger.warn("同步论坛发生错误"+e.getMessage());
				}
				this.jsonMap.put("needRedirect", needRedirect);
				this.jsonMap.put("redirectUrl", redirectUrl);
				return success();
			}
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return faile();
	}

	public String qqLogin() throws Exception {
		String qqId=Struts2Utils.getRequest().getParameter("qqId");
		String nickName = Struts2Utils.getRequest().getParameter("nickName");
		String password = userManager.getRandomPassword();
		User qqUser=userManager.getQqUserById(qqId);
		if (qqUser == null) {
			qqUser=new User();
			User user=userManager.getUserBy(nickName);
			if(user!=null){
				qqUser.setUserName("QQ"+nickName.toLowerCase());
			}else{
				qqUser.setUserName(nickName.toLowerCase());
			}
			qqUser.setQqId(qqId);
			qqUser.setPassword(MD5.md5(password));
			qqUser.setCreateTime(new Date());
			qqUser.setMid(0L);
			qqUser.setUserWay(UserWay.WEB);
			qqUser.setLocked(User.NO_LOCK_STATUS);
			qqUser.setRemainMoney(BigDecimal.ZERO);
			UserInfo info = new UserInfo();
			BankInfo bank = new BankInfo();
			qqUser.setBank(bank);
			qqUser.setInfo(info);
			qqUser = userManager.saveUser(qqUser, info, bank,password);
		}
		if (qqUser.isLocked()) {
			throw new WebDataException("该账户已被锁定,请联系客服.");
		}
		qqUser = SsoLoginHolder.login(qqUser.getId());
		Struts2Utils.setAttribute("loginUser", qqUser);

		UserLogin userLogin = userManager.getUserLoginBy(qqUser.getId());
		userLogin.setUserId(qqUser.getId());
		userLogin.setLastLoginIp(userLogin.getTryIp());
		userLogin.setLastLoginTime(userLogin.getTryTime());
		userLogin.setTryIp(Struts2Utils.getRemoteAddr());
		userLogin.setTryTime(new Date());
		userManager.saveUserLogin(userLogin);

		// 登录
		Boolean needRedirect = Boolean.FALSE;
		String redirectUrl = Struts2Utils.getParameter("redirectUrl");
		CookieUtil.getCookieByName(Struts2Utils.getRequest(), "redirectUrl");
		if (StringUtils.isBlank(redirectUrl)) {
			needRedirect = Boolean.FALSE;
		} else {
			needRedirect = Boolean.TRUE;
		}
//		String forumLoginReturn = ForumController.login(qqUser.getUserName(),
//				password);
//		this.jsonMap.put("forumLoginReturn", forumLoginReturn);
		this.jsonMap.put("needRedirect", needRedirect);
		this.jsonMap.put("redirectUrl", redirectUrl);
		return success();

	}

	public String checkMobileExist() {
		User user = this.getLoginUser();
		if (user == null) {
			this.jsonMap.put("exist", false);
			this.jsonMap.put("msg", "您尚未登录!");
			this.jsonMap.put("url", Struts2Utils.getRequest().getContextPath()
					+ "/index.html");
			return faile();
		}

		if (user.getInfo().getMobile() != null
				&& !user.getInfo().getMobile().trim().equals("")) {
			if(null!=user.getValidatedPhoneNo()&&user.getValidatedPhoneNo()){
				//已经绑定手机
				this.jsonMap.put("exist", true);
				this.jsonMap.put("mobile", user.getInfo().getMobile());
				this.jsonMap.put("msg", "已经进行手机绑定，如需要修改请联系客服！");
				this.jsonMap.put("url", Struts2Utils.getRequest().getContextPath()
						+ "/index.html");
			}else{
				//只是填写了手机号码而没有验证
				this.jsonMap.put("exist", false);
				this.jsonMap.put("mobile", user.getInfo().getMobile());
			}
			
		} else {
			this.jsonMap.put("exist", false);
			this.jsonMap.put("msg", "还未进行手机绑定！");
			this.jsonMap.put("url", Struts2Utils.getRequest().getContextPath()
					+ "/index.html");
		}
		return success();
	}

	public String checkIdCardAndRealNameNotExist() {
		User loginUser = getLoginUser();
		HashMap map = new HashMap();
		try {
			if (loginUser == null) {
				map.put("success", false);
				map.put("msg", "身份信息已经设置，如需修改请联系客服！");
				Struts2Utils.renderJson(map);
			}
			User user = userManager.getUser(loginUser.getId());
			UserInfo info = user.getInfo();
			if (StringUtils.isBlank(info.getIdCard())
					|| StringUtils.isBlank(info.getRealName())) {
				map.put("success", true);
				Struts2Utils.renderJson(map);
			} else {
				map.put("success", false);
				map.put("msg", "身份信息已经设置，如需修改请联系客服！");
				Struts2Utils.renderJson(map);
			}
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return null;
	}

	public String checkIdCardIsExist() {
		User loginUser = getLoginUser();
		try {
			if (loginUser == null) {
				this.jsonMap.put("msg", "你还没有登录，请先登录再进行操作！");
				return faile();
			}
			User user = userManager.getUser(loginUser.getId());
			UserInfo info = user.getInfo();
			if (StringUtils.isBlank(info.getIdCard())) {
				return success();
			} else {
				this.jsonMap.put("msg", "身份信息已经设置，如需修改请联系客服！");
				return faile();
			}
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return faile();
	}

	public String checkRealNameAndMobileIsExist() {
		User loginUser = getLoginUser();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (loginUser == null) {
				map.put("success", false);
				map.put("msg", "你还没有登录，请先登录再进行操作！");
				Struts2Utils.renderJson(map);
				return null;
			}
			UserInfo info = loginUser.getInfo();
			Boolean mobileValidated = loginUser.getValidatedPhoneNo();
			if (mobileValidated != null) {
				map.put("mobileValidated", loginUser.getValidatedPhoneNo());
			} else {
				map.put("mobileValidated", false);
			}

			if (StringUtils.isBlank(info.getRealName())) {
				map.put("success", true);
				Struts2Utils.renderJson(map);
			} else {
				map.put("success", false);
				map.put("msg", "真实姓名已经设置，如需修改请联系客服！");
				Struts2Utils.renderJson(map);
			}
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return null;
	}

	public String writeMobile() {
		User user = this.getLoginUser();
		HashMap map = new HashMap();
		if (user == null) {
			map.put("success", false);
			map.put("msg", "您尚未登录!");
			map.put("url", Struts2Utils.getRequest().getContextPath()
					+ "/index.html");
			Struts2Utils.renderJson(map);
		}
		String mobile = Struts2Utils.getParameter("mobile");
		if (mobile == null || mobile.trim().equals("")) {
			map.put("success", false);
			map.put("msg", "手机号码不能为空！");
			Struts2Utils.renderJson(map);
		}
		try {
			UserInfo userInfo = user.getInfo();
			userInfo.setMobile(mobile);
			userManager.saveUserInfo(userInfo);

			map.put("success", true);
			map.put("msg", "手机号码填写成功！");
			Struts2Utils.renderJson(map);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "手机号码填写失败！");
			Struts2Utils.renderJson(map);
		}
		return null;
	}

	/**
	 * 会员管理首页
	 * 
	 * @return
	 * @throws WebDataException
	 */
	public String userManeger() throws WebDataException {
		try {
			User loginUser = this.getLoginUser();
			if (null != loginUser) {
				String key = getRequestKey() + loginUser.getId();
				Element el = commonQueryCache.get(key);
				if (el == null) {
					XDetachedCriteria criteria = new XDetachedCriteria(
							User.class, "m");

					ProjectionList propList = Projections.projectionList();
					propList.add(Projections.property("id"), "id");
					propList.add(Projections.property("userName"), "userName");
					propList.add(Projections.property("remainMoney"),
							"remainMoney");
					propList.add(Projections.property("consumptionMoney"),
							"consumptionMoney");
					propList.add(Projections.property("createTime"),
							"createTime");
					propList.add(Projections.property("locked"), "locked");
					criteria.setProjection(propList);
					criteria.add(Restrictions.eq("m.agentId", loginUser.getId()));
					criteria.addOrder(Order.desc("m.consumptionMoney"));
					criteria.addOrder(Order.desc("m.id"));
					criteria.setResultTransformer(Transformers
							.aliasToBean(User.class));
					pagination = queryService.findByCriteriaAndPagination(
							criteria, pagination);
					el = new Element(key, this.pagination);
					commonQueryCache.put(el);
				} else {
					pagination = (Pagination) el.getValue();
				}
			} else {
				throw new WebDataException("您还未登录,请登录后再操作.");
			}
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return forward(false, "maneger");
	}

	public static void main(String[] args) {
		double money = 1.75;
		String moneyStr = "" + money;
		if (moneyStr.indexOf(".") != -1) {
			String[] moneArr = moneyStr.split("\\.");
			int i = 0;
		} else {
			// 0
		}
		System.out.print(Integer.valueOf(money + ""));
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
			return GlobalResults.FWD_LOGIN;
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
		String siteId = "hclotto";
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
		//String forumLogoutReturn = ForumController.logout();
//		if (forumLogoutReturn != null) {
//			this.jsonMap.put("forumLogoutReturn", forumLogoutReturn);
//		}
		if (isAjaxRequest()) {
			this.jsonMap.put("sloUrl", url);
			return success();
		} else {
			Struts2Utils.setAttribute("sloUrl", url);
			return redirectforward(Boolean.TRUE, "您已经安全的退出", null, "message");
		}
	}

	/**
	 * 进入注册页面
	 */
	public String register() {
		User loginUser = this.getLoginUser();
		return "register";
	}

	/**
	 * 进入注册页面
	 * 
	 * @throws IOException
	 */
	public String introduction() throws IOException {
		CookieUtil.addReUrlCookie();
		if (StringUtils.isNotBlank(Struts2Utils.getParameter("i"))) {
			try {
				String introductionId = Struts2Utils.getParameter("i");
				User loginUser = userManager.getUser(Long
						.valueOf(introductionId));
				if (null == loginUser) {
					throw new WebDataException("找不到响应的介绍人.");
				}
				CookieUtil.addIntroductionIdCookie(introductionId);
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);// 出错不存储介绍人
			}
		}
		weitePopu();
		Struts2Utils.getResponse().sendRedirect(Constant.BASEPATH + "/");
		return null;
	}

	/**
	 * 进入注册页面
	 */
	public String reg() {
		CookieUtil.addReUrlCookie();
		if (StringUtils.isNotBlank(Struts2Utils.getParameter("i"))) {
			try {
				String introductionId = Struts2Utils.getParameter("i");
				User loginUser = userManager.getUser(Long
						.valueOf(introductionId));
				if (null == loginUser) {
					throw new WebDataException("找不到响应的介绍人.");
				}
				CookieUtil.addIntroductionIdCookie(introductionId);
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);// 出错不存储介绍人
			}
		}
		weitePopu();
		return "reg";
	}

	/**
	 * 注册成功后页面
	 */
	public String regSuccess() {
		User user = (User) SsoLoginHolder.getLoggedUser();
		Struts2Utils.getRequest().setAttribute("user", user);
		return "regSuccess";
	}

	public String welcome() {
		weitePopu();
		return redirectforward(Boolean.TRUE, "欢迎您的到来!",
				Constant.BASEPATH + "/", "message");

	}

	public void weitePopu() {
		try {
			String mid = Struts2Utils.getParameter("mid");
			writeMid(mid);
			String aid = Struts2Utils.getParameter("aid");
			writeAid(aid);
			// 记录
			Popu popu = this.getPopu(PopuType.WEB);
			if (null != popu) {
				userManager.savePopu(popu);
			}
		} catch (Exception e) {
			logger.warn("注册的时候发生媒体来源异常" + e.getMessage());
		}
	}

	public void writeMid(String mid) {
		int SECONDS_PER_YEAR = 60 * 60 * 24 * 365;
		if (StringUtils.isNotBlank(mid)) {
			try {
				Media media = userManager.getMedia(Long.valueOf(mid));
				if (null != media) {
					CookieUtil.addCookie(Struts2Utils.getResponse(), "mid", ""
							+ media.getId(), SECONDS_PER_YEAR);
				} else {
					CookieUtil.addCookie(Struts2Utils.getResponse(), "mid",
							"0", SECONDS_PER_YEAR);
				}
			} catch (Exception e) {
				logger.warn("注册的时候发生媒体来源异常" + e.getMessage());
				CookieUtil.addCookie(Struts2Utils.getResponse(), "mid", "0",
						SECONDS_PER_YEAR);
			}
		}
	}

	public void writeAid(String aid) {
		int SECONDS_PER_YEAR = 60 * 60 * 24 * 365;
		if (StringUtils.isNotBlank(aid)) {
			try {
				CookieUtil.addCookie(Struts2Utils.getResponse(), "aid", ""
						+ Long.valueOf(aid), SECONDS_PER_YEAR);
			} catch (Exception e) {
				logger.warn("注册的时候发生媒体来源异常" + e.getMessage());
				CookieUtil.addCookie(Struts2Utils.getResponse(), "aid", "0",
						SECONDS_PER_YEAR);
			}
		}
	}

	/**
	 * 注册操作
	 */
	final String forbidString = "system,bcmm,admin,管理员";

	public String create() throws WebDataException {
		String fwd = "register";
		String register_type = Struts2Utils.getParameter("register_type");
		try {
			if (regForm == null) {
				throw new WebDataException("注册信息为空.");
			}
			if (StringUtils.isBlank(regForm.getUserName())) {
				throw new WebDataException("用户名为空.");
			}
			String captcha = Struts2Utils.getParameter("captcha");
			if (StringUtils.isBlank(captcha)) {
				throw new WebDataException("请输入验证码.");
			} else if (!captcha.equals(Struts2Utils.getSession().getAttribute(
					Constant.LOGIN_CAPTCHA))) {
				throw new WebDataException("验证码错误.");
			}
			// 判读用户名是否重复
			String userName = java.net.URLDecoder.decode(this.regForm
					.getUserName().trim(), "utf-8");
			User userTemp = userManager.getUserBy(userName);
			if (userTemp != null
					&& userTemp.getUserName().equals(userName.toLowerCase())) {
				throw new WebDataException("注册用户名重复.请更改.");
			}
			User user = new User();
			// //增加用户媒体来源信息
			String mid = CookieUtil.getCookieByName(Struts2Utils.getRequest(),
					"mid");
			if (StringUtils.isNotBlank(mid)) {
				try {
					Media media = userManager.getMedia(Long.valueOf(mid));
					if (null != media) {
						user.setMid(media.getId());
					} else {
						user.setMid(0L);
					}
				} catch (Exception e) {
					logger.warn("注册的时候发生媒体来源异常" + e.getMessage());
				}
			}
			user.setUserWay(UserWay.WEB);
			user.setUserName(userName.toLowerCase());
			user.setLocked(User.NO_LOCK_STATUS);
			user.setRemainMoney(BigDecimal.ZERO);

			// /不是在会员中心指定下属
			fwd = "reg";
			if (StringUtils.isBlank(regForm.getPassword())) {
				throw new WebDataException("密码为空.");
			}
			if (StringUtils.isBlank(regForm.getConfirmPassword())) {
				throw new WebDataException("确认密码为空.");
			}
			if (!regForm.getConfirmPassword().trim()
					.equals(regForm.getPassword().trim())) {
				throw new WebDataException("确认密码和密码不一致.");
			}
			user.setPassword(MD5.md5(regForm.getPassword().trim())
					.toUpperCase());
			UserInfo info = new UserInfo();
			BankInfo bank = new BankInfo();
			user.setBank(bank);
			user.setInfo(info);
			String introductionId = CookieUtil.getIntroductionIdCookie();
			if (StringUtils.isNotBlank(introductionId)) {
				// /有介绍人。代理
				User agentUser = userManager.getUser(Long
						.valueOf(introductionId));
				user.setAgentId(agentUser.getId());
				AgentLotteryType[] agentLotteryTypeArr = AgentLotteryType
						.values();
				List<AgentRebate> agentRebateList = Lists.newArrayList();
				for (AgentLotteryType agentLotteryType : agentLotteryTypeArr) {
					Double rebate = Double.valueOf(0);
					AgentRebate agentAgentRebate = agentEntityManager
							.findAgentRebate(agentUser.getId(),
									agentLotteryType);
					if (null == agentAgentRebate) {
						throw new WebDataException("介绍人帐号返点异常.");
					}
					Double loginAgentRebate = agentAgentRebate.getRebate();
					if (loginAgentRebate - rebate < 0) {
						throw new WebDataException("子帐号返点不能大于本账号，请调整.");
					}
					AgentRebate agentRebate = new AgentRebate();
					agentRebate.setAgentLotteryType(agentLotteryType);
					agentRebate.setRebate(rebate);
					agentRebateList.add(agentRebate);
				}
				user = agentEntityManager.saveAgentUser(agentUser.getId(),
						user, info, bank, agentRebateList, regForm
								.getPassword().trim());
			} else {
				user = userManager.saveUser(user, info, bank,
						regForm.getPassword());
			}

			addActionMessage("注册成功.");

			user = SsoLoginHolder.login(user.getId());
			Struts2Utils.setAttribute("loginUser", user);

			UserLogin userLogin = new UserLogin();
			userLogin.setUserId(user.getId());
			userLogin.setTryIp(Struts2Utils.getRemoteAddr());
			userLogin.setTryTime(new Date());
			userLogin.setLastLoginIp(userLogin.getTryIp());
			userLogin.setLastLoginTime(userLogin.getTryTime());
			userManager.saveUserLogin(userLogin);

			String redirectUrl = CookieUtil.getCookieByName(
					Struts2Utils.getRequest(), "redirectUrl");
			if (StringUtils.isBlank(redirectUrl))
				redirectUrl = null;
			Struts2Utils.setAttribute("redirectUrl", redirectUrl);
			if (isAjaxRequest()) {
				return success();
			} else {
				return redirectforward(Boolean.TRUE, userName, redirectUrl,
						FWD_WELCOME);
			}
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError("网络错误，注册失败，请稍后再试");
		} catch (Exception e) {
			addActionError("网络错误，注册失败，请稍后再试");
			logger.warn(e.getMessage(), e);
		}
		if (StringUtils.isBlank(register_type)) {
			fwd = "reg";
		}
		return forward(false, fwd);
	}

	public String resetPasswd() {
		return "passwd-reset";
	}

	public String resetPasswordByEmail() {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		User loginUser = getLoginUser();
		if (loginUser != null) {
			return redirectforward(Boolean.TRUE, "您已经登录!请先退出", null, "message");
		}
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
			return "user-password-reset-email";
		}
		String userName = Struts2Utils.getParameter("userName");
		String newuserName = null;
		try {
			newuserName = java.net.URLDecoder.decode(userName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			jsonMap.put("success", false);
			jsonMap.put("msg", "重置密码失败，请检查用户名及注册邮箱!");
		}
		String email = Struts2Utils.getParameter("email");
		if (StringUtils.isNotBlank(newuserName)
				&& StringUtils.isNotBlank(email)) {
			try {
				if (userManager.resetPasswordAndSendEmail(newuserName, email)) {
					jsonMap.put("success", true);
					jsonMap.put("msg", "重置密码成功，新密码已发送至邮箱!");
				} else {
					jsonMap.put("success", false);
					jsonMap.put("msg", "重置密码失败，请检查用户名及注册邮箱!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				jsonMap.put("success", false);
				jsonMap.put("msg", "重置密码失败，请检查用户名及注册邮箱!");
			}
		}
		Struts2Utils.renderJson(jsonMap);
		return null;
	}

	public String resetPasswordByPhone() {
		User loginUser = getLoginUser();
		if (loginUser != null) {
			jsonMap.put("msg", "您已经登录!请先退出!");
			return faile();
		}
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
			jsonMap.put("msg", "重置密码失败!");
			return faile();
		}
		String userName = Struts2Utils.getParameter("userName");
		String newuserName = null;
		try {
			newuserName = java.net.URLDecoder.decode(userName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			jsonMap.put("msg", "重置密码失败，请检查用户名及注册手机!");
			return faile();
		}
		String phone = Struts2Utils.getParameter("phone");
		if (StringUtils.isNotBlank(newuserName)
				&& StringUtils.isNotBlank(phone)) {
			try {
				if (userManager.resetPasswordAndSendMessage(newuserName, phone)) {
					jsonMap.put("msg", "重置密码成功，新密码已发送至注册手机!");
					return success();
				} else {
					jsonMap.put("msg", "重置密码失败，请检查用户名及注册手机!");
					return faile();
				}
			} catch (Exception e) {
				e.printStackTrace();
				jsonMap.put("msg", "重置密码失败，请检查用户名及注册手机!");
				return faile();
			}
		}
		return faile();
	}

	public String updatePwd() {
		User user = this.getLoginUser();
		HashMap map = new HashMap();
		if (user == null) {
			map.put("success", false);
			map.put("msg", "您尚未登录!");
			map.put("url", Struts2Utils.getRequest().getContextPath()
					+ "/index.html");
			Struts2Utils.renderJson(map);
		} else {
			try {
				String password = regForm.getOldpassword();
				if (StringUtils.isBlank(password)) {
					map.put("success", false);
					map.put("msg", "旧密码不能为空!");
					Struts2Utils.renderJson(map);
					return null;
				}
				if (!(MD5.md5(password.trim()).equals(user.getPassword()))) {
					map.put("success", false);
					map.put("msg", "旧密码不正确!");
					Struts2Utils.renderJson(map);
					return null;
				}
				user.setPassword(MD5.md5(regForm.getPassword().trim())
						.toUpperCase());

				userManager.saveUser(user);
				Struts2Utils.getRequest().getSession()
						.setAttribute("user", user);
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

	public String updatePasswd() {
		User user = this.getLoginUser();
		if (user == null) {
			this.jsonMap.put("msg", "您尚未登录!");
			this.jsonMap.put("url", Struts2Utils.getRequest().getContextPath()
					+ "/index.html");
			return faile();
		} else {
			try {
				String oldPassword = Struts2Utils.getParameter("oldPassword");
				if (StringUtils.isBlank(oldPassword)) {
					this.jsonMap.put("msg", "旧密码不能为空!");
					return faile();
				}
				if (!(MD5.md5(oldPassword.trim()).equals(user.getPassword()))) {
					this.jsonMap.put("msg", "旧密码不正确!");
					return faile();
				}
				String newPassword = Struts2Utils.getParameter("password")
						.trim();
				user.setPassword(MD5.md5(newPassword).toUpperCase());

				userManager.saveUser(user, oldPassword.trim(), newPassword);
				Struts2Utils.getRequest().getSession()
						.setAttribute("user", user);
				this.jsonMap.put("msg", "密码修改成功!");
			} catch (Exception e) {
				this.jsonMap.put("msg", "密码修改失败!");
				return faile();
			}

		}
		return success();
	}

	public String setIdCardAndRealName() {
		User loginUser = getLoginUser();
		HashMap map = new HashMap();
		try {
			if (loginUser == null) {
				this.jsonMap.put("msg", "您还未登录,请登录后再操作.");
				return faile();
			}
			String idCard = Struts2Utils.getParameter("idCard");
			String realName = Struts2Utils.getParameter("realName");
			if (StringUtils.isBlank(realName)) {
				this.jsonMap.put("msg", "真实姓名不能为空.请填写.");
				return faile();
			}
			if (StringUtils.isBlank(idCard)) {
				this.jsonMap.put("msg", "身份证不能为空.请填写.");
				return faile();
			}
			User user = userManager.getUser(loginUser.getId());
			UserInfo info = user.getInfo();
			if (StringUtils.isBlank(info.getIdCard())
					|| StringUtils.isBlank(info.getRealName())) {
				info.setRealName(realName.trim());
				info.setIdCard(idCard.trim());
				userManager.saveUserInfo(info);
				this.jsonMap.put("msg", "身份信息设置成功.");
				return success();
			} else {
				this.jsonMap.put("msg", "身份信息已经设置，如需修改请联系客服.");
				return faile();
			}
		} catch (Exception e) {
			this.jsonMap.put("msg", "身份信息设置失败！");
			return faile();
		}
	}

	public String setRealName() {
		User loginUser = getLoginUser();
		HashMap map = new HashMap();
		try {
			if (loginUser == null) {
				this.jsonMap.put("msg", "您还未登录,请登录后再操作.");
				return faile();
			}
			String realName = Struts2Utils.getParameter("realName");
			if (StringUtils.isBlank(realName)) {
				this.jsonMap.put("msg", "真实姓名不能为空.请填写.");
				return faile();
			}
			User user = userManager.getUser(loginUser.getId());
			UserInfo info = user.getInfo();
			if (StringUtils.isBlank(info.getRealName())) {
				info.setRealName(realName.trim());
				userManager.saveUserInfo(info);
				this.jsonMap.put("msg", "真实姓名设置成功.");
				return success();
			} else {
				this.jsonMap.put("msg", "真实姓名已经设置，如需修改请联系客服.");
				return faile();
			}
		} catch (Exception e) {
			this.jsonMap.put("msg", "真实姓名设置失败！");
			return faile();
		}
	}

	public String setIdCard() {
		User loginUser = getLoginUser();
		try {
			if (loginUser == null) {
				this.jsonMap.put("msg", "您还未登录,请登录后再操作.");
				return faile();
			}
			String idCard = Struts2Utils.getParameter("idCard");
			if (StringUtils.isBlank(idCard)) {
				this.jsonMap.put("msg", "身份证不能为空.请填写.");
				return faile();
			}
			User user = userManager.getUser(loginUser.getId());
			UserInfo info = user.getInfo();
			if (StringUtils.isBlank(info.getIdCard())) {
				info.setIdCard(idCard.trim());
				userManager.saveUserInfo(info);
				this.jsonMap.put("msg", "身份信息设置成功.");
				return success();
			} else {
				this.jsonMap.put("msg", "身份信息已经设置，如需修改请联系客服.");
				return faile();
			}
		} catch (Exception e) {
			this.jsonMap.put("msg", "身份信息设置失败！");
			return faile();
		}
	}

	public String setNeedValidateWhileTk() {
		try {
			User loginUser = getLoginUser();
			if (loginUser == null) {
				CookieUtil.addReUrlCookie();
				return GlobalResults.FWD_LOGIN;
			}
			String needValidateWhileTk = Struts2Utils
					.getParameter("needTkValidate");
			if (needValidateWhileTk != null && needValidateWhileTk.equals("1")) {
				loginUser.setNeedValidateWhileTk(true);
			}
			if (needValidateWhileTk != null && needValidateWhileTk.equals("0")) {
				loginUser.setNeedValidateWhileTk(false);

			}
			userManager.saveUser(loginUser);
			this.jsonMap.put("isNeedValidateWhileTk",
					loginUser.getNeedValidateWhileTk());
			this.jsonMap.put("msg", "设置成功");
			return success();

		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
			this.jsonMap.put("msg", "设置失败");
		}
		return faile();
	}

	public String setNeedPayPassword() {
		HashMap map = new HashMap();
		try {
			User loginUser = getLoginUser();
			if (loginUser == null) {
				CookieUtil.addReUrlCookie();
				return GlobalResults.FWD_LOGIN;
			}
			String needPayPassword = Struts2Utils
					.getParameter("needPayPassword");

			if (needPayPassword != null && needPayPassword.equals("1")) {
				// 如果支付密码为空，说明此用户是第一次设置支付密码
				if (loginUser.getPayPassword() == null
						|| loginUser.getPayPassword().trim().equals("")) {
					String payPassword = Struts2Utils
							.getParameter("payPassword");
					loginUser.setPayPassword(MD5.md5(payPassword.trim())
							.toUpperCase());
					loginUser.setNeedPayPassword(true);
				} else {
					String oldPayPassword = Struts2Utils
							.getParameter("oldPayPassword");
					String payPassword = Struts2Utils
							.getParameter("payPassword");

					if (!loginUser.getPayPassword().equals(
							MD5.md5(oldPayPassword.trim().toUpperCase()))) {
						map.put("success", false);
						map.put("msg", "设置失败，原支付密码输入不正确");
						Struts2Utils.renderJson(map);
						return null;
					}

					loginUser.setPayPassword(MD5.md5(payPassword.trim())
							.toUpperCase());
					loginUser.setNeedPayPassword(true);
				}

			}

			if (needPayPassword != null && needPayPassword.equals("0")) {
				loginUser.setNeedPayPassword(false);
			}
			userManager.saveUser(loginUser);
			map.put("success", true);
			map.put("isNeedPayPassword", loginUser.getNeedPayPassword());
			map.put("msg", "设置成功");
			Struts2Utils.renderJson(map);
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
			map.put("success", false);
			map.put("msg", "设置失败");
			Struts2Utils.renderJson(map);
		}
		return null;
	}

	/**
	 * 判断用户是否设置了需要支付密码
	 * 
	 * 
	 */
	public String isNeedPayPassword() {
		HashMap map = new HashMap();
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}

		Boolean needPayPassword = loginUser.getNeedPayPassword();
		if (needPayPassword == true) {
			map.put("needPayPassword", true);
			Struts2Utils.renderJson(map);
		} else {
			map.put("needPayPassword", false);
			Struts2Utils.renderJson(map);
		}
		return null;
	}

	public String isFirstTimeSetPayPassword() {
		HashMap map = new HashMap();
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}

		String payPassword = loginUser.getPayPassword();
		if (payPassword == null || payPassword.trim().equals("")) {
			map.put("isFirstTimeSetPayPassword", true);
			Struts2Utils.renderJson(map);
		} else {
			map.put("isFirstTimeSetPayPassword", false);
			Struts2Utils.renderJson(map);
		}
		return null;
	}

	/**
	 * 更新用户个人信息
	 * 
	 * @throws WebDataException
	 */
	public String updateInfo() throws WebDataException {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		try {
			if (loginUser == null)
				throw new WebDataException("您还未登录,请登录后再操作.");

			User user = userManager.getUser(loginUser.getId());

			UserInfo info = user.getInfo();
			if (StringUtils.isNotBlank(regForm.getIdCard())) {
				if (StringUtils.isBlank(info.getIdCard())) {
					info.setIdCard(regForm.getIdCard());
				} else {
					throw new WebDataException("身份证已经填写，如要修改请联系客服.");
				}
			} else {
				throw new WebDataException("身份证不能为空，请修改.");
			}

			if (StringUtils.isNotBlank(regForm.getEmail())) {
				if (StringUtils.isBlank(info.getEmail())) {
					info.setEmail(regForm.getEmail());
				} else {
					throw new WebDataException("EMAIL已经填写，如要修改请联系客服.");
				}
			} else {
				throw new WebDataException("EMAIL不能为空，请修改.");
			}

			if (StringUtils.isNotBlank(regForm.getPhoneNumber())) {
				if (StringUtils.isBlank(info.getMobile())) {
					info.setMobile(regForm.getPhoneNumber());
				} else {
					throw new WebDataException("手机号码已经填写，如要修改请联系客服.");
				}
			} else {
				throw new WebDataException("手机号码不能为空，请修改.");
			}

			if (StringUtils.isNotBlank(regForm.getRealName())) {
				if (StringUtils.isBlank(info.getRealName())) {
					String realName = java.net.URLDecoder.decode(
							regForm.getRealName(), "UTF-8");
					info.setRealName(realName);
				} else {
					throw new WebDataException("手机号码已经填写，如要修改请联系客服.");
				}
			} else {
				throw new WebDataException("手机号码不能为空，请修改.");
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
			return redirectforward(Boolean.TRUE, "账号信息已完善,请充值!",
					Constant.BASEPATH + "/user/fund!" + infoForm.getFrom()
							+ ".action", "message");
		}
		return toValidateAccount();
	}

	/**
	 * 更新用户银行卡信息
	 * 
	 */
	public String updateBankInfo() {
		User loginUser = getLoginUser();
		HashMap map = new HashMap();
		if (loginUser == null) {
			map.put("success", false);
			map.put("msg", "您尚未登录!");
			map.put("url", Struts2Utils.getRequest().getContextPath()
					+ "/index.html");
			Struts2Utils.renderJson(map);
		}
		String bankName = Struts2Utils.getParameter("bankName");
		String bankCard = Struts2Utils.getParameter("bankCard");
		try {
			BankInfo bankInfo = loginUser.getBank();
			bankInfo.setBankName(bankName);
			bankInfo.setBankCard(bankCard);
			userManager.saveBankInfo(bankInfo);
			map.put("success", true);
			map.put("msg", "银行卡信息绑定成功!");
			Struts2Utils.renderJson(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "银行卡信息绑定失败!");
			Struts2Utils.renderJson(map);
		}
		return null;
	}

	public String bindBankCard() {
		User loginUser = getLoginUser();
		HashMap map = new HashMap();
		if (loginUser == null) {
			map.put("success", false);
			map.put("msg", "您尚未登录!");
			map.put("url", Struts2Utils.getRequest().getContextPath()
					+ "/index.html");
			Struts2Utils.renderJson(map);
		}

		if (loginUser.getInfo().getRealName() == null
				|| loginUser.getInfo().getRealName().equals("")) {
			map.put("success", false);
			map.put("msg", "请先完善身份验证信息!");
			map.put("needValidateIdCard", true);
			Struts2Utils.renderJson(map);
			return null;
		}

		try {
			BankInfo bankInfo = loginUser.getBank();
			if (this.getBankInfoForm().getBankName() == null
					|| this.getBankInfoForm().getBankName().equals("")) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，开户银行不能为空！");
				Struts2Utils.renderJson(map);
				return null;
			}

			if (this.getBankInfoForm().getPartBankProvince() == null
					|| this.getBankInfoForm().getPartBankProvince().equals("")) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，开户省份不能为空！");
				Struts2Utils.renderJson(map);
				return null;
			}

			if (this.getBankInfoForm().getBankCard() == null
					|| this.getBankInfoForm().getBankCard().equals("")) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，银行卡号不能为空！");
				Struts2Utils.renderJson(map);
				return null;
			}

			if (this.getBankInfoForm().getUserRealName() == null
					|| this.getBankInfoForm().getUserRealName().trim()
							.equals("")) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，真实姓名不能为空！");
				Struts2Utils.renderJson(map);
				return null;
			}

			if (!loginUser.getInfo().getRealName()
					.equals(this.getBankInfoForm().getUserRealName())) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，核对真实姓名有误！");
				Struts2Utils.renderJson(map);
				return null;
			}

			if (this.getBankInfoForm().getYanzhengma() == null
					|| this.getBankInfoForm().getYanzhengma().trim().equals("")) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，验证码不能为空！");
				Struts2Utils.renderJson(map);
				return null;
			}

			try {
				userManager
						.userRandomMessage(loginUser.getInfo().getMobile(),
								MessageType.YANZHENGMA, Integer.valueOf(this
										.getBankInfoForm().getYanzhengma()));
				userManager.saveBankInfoWith(this.getBankInfoForm(), bankInfo);
			} catch (ServiceException e) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，验证码不正确！");
				Struts2Utils.renderJson(map);
				return null;
			}

			map.put("success", true);
			map.put("msg", "银行卡信息绑定成功!");
			Struts2Utils.renderJson(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "银行卡信息绑定失败!");
			Struts2Utils.renderJson(map);
			return null;
		}
		return null;

	}

	public String bindBankCardOfDialog() {
		User loginUser = getLoginUser();
		HashMap map = new HashMap();
		if (loginUser == null) {
			map.put("success", false);
			map.put("msg", "您尚未登录!");
			map.put("url", Struts2Utils.getRequest().getContextPath()
					+ "/index.html");
			Struts2Utils.renderJson(map);
		}

		if (loginUser.getInfo().getRealName() == null
				|| loginUser.getInfo().getRealName().equals("")) {
			map.put("success", false);
			map.put("msg", "请先完善身份验证信息!");
			map.put("needValidateIdCard", true);
			Struts2Utils.renderJson(map);
			return null;
		}

		try {
			BankInfo bankInfo = loginUser.getBank();
			if (this.getBankInfoForm().getBankName() == null
					|| this.getBankInfoForm().getBankName().equals("")) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，开户银行不能为空！");
				Struts2Utils.renderJson(map);
				return null;
			}

			if (this.getBankInfoForm().getPartBankProvince() == null
					|| this.getBankInfoForm().getPartBankProvince().equals("")) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，开户省份不能为空！");
				Struts2Utils.renderJson(map);
				return null;
			}

			if (this.getBankInfoForm().getBankCard() == null
					|| this.getBankInfoForm().getBankCard().equals("")) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，银行卡号不能为空！");
				Struts2Utils.renderJson(map);
				return null;
			}

			if (this.getBankInfoForm().getUserRealName() == null
					|| this.getBankInfoForm().getUserRealName().trim()
							.equals("")) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，真实姓名不能为空！");
				Struts2Utils.renderJson(map);
				return null;
			}

			if (!loginUser.getInfo().getRealName()
					.equals(this.getBankInfoForm().getUserRealName())) {
				map.put("success", false);
				map.put("msg", "银行卡信息绑定失败，核对真实姓名有误！");
				Struts2Utils.renderJson(map);
				return null;
			}

			userManager.saveBankInfoWith(this.getBankInfoForm(), bankInfo);
			map.put("success", true);
			map.put("msg", "银行卡信息绑定成功!");
			Struts2Utils.renderJson(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "银行卡信息绑定失败!");
			Struts2Utils.renderJson(map);
			return null;
		}
		return null;

	}

	public String deleteBankInfo() {
		User loginUser = getLoginUser();
		HashMap map = new HashMap();
		if (loginUser == null) {
			map.put("success", false);
			map.put("msg", "您尚未登录!");
			map.put("url", Struts2Utils.getRequest().getContextPath()
					+ "/index.html");
			Struts2Utils.renderJson(map);
		}

		BankInfo bankInfo = loginUser.getBank();
		try {
			userManager.deleteBankInfo(bankInfo);
			map.put("success", true);
			map.put("msg", "删除银行信息成功！");
			Struts2Utils.renderJson(map);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "删除银行信息失败！");
			Struts2Utils.renderJson(map);
		}
		return null;
	}

	/**
	 * 用户身份信息认证
	 * 
	 */

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
			userName = java.net.URLDecoder.decode(this.regForm.getUserName()
					.trim(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
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

		return null;
	}

	/**
	 * 检查验证码是否匹配
	 * 
	 * @return
	 */
	public String checkCodeRegAble() {
		String captcha = (String) Struts2Utils.getSession().getAttribute(
				Constant.LOGIN_CAPTCHA);
		Map<String, Object> map = new HashMap<String, Object>();
		if (captcha != null && captcha.equals(this.captcha)) {
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
			userInfoForm
					.setUserName(StringUtils.isBlank(user.getUserName()) ? "&nbsp;"
							: this.getLoginUser().getUserName().trim());

			NumberFormat nf = NumberFormat.getInstance();
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			userInfoForm
					.setDefaultAccountRemainMoney(user == null ? nf
							.format(BigDecimal.ZERO) : nf.format(user
							.getRemainMoney()));

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

	public String checkIsValidatePhoneNo() {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}

		Boolean isValidate = loginUser.getValidatedPhoneNo();

		if (isValidate == null) {
			this.jsonMap.put("isValidate", false);
			this.jsonMap.put("msg", "你还没进行手机验证，请先进行验证！");
			return faile();
		}

		if (isValidate) {
			this.jsonMap.put("isValidate", true);
			this.jsonMap.put("mobile", loginUser.getInfo().getMobile());
			this.jsonMap.put("msg", "已进行手机验证");
		} else {
			this.jsonMap.put("isValidate", false);
			this.jsonMap.put("msg", "你还没进行手机验证，请先进行验证！");
		}

		return success();
	}

	/**
	 * 发送短信验证
	 * 
	 * @return
	 */
	public String toMsgValid() {

		Map<String, Object> result = new HashMap<String, Object>();
		User user = this.getLoginUser();
		if (null == user) {
			result.put("msg", "您还没登录。请先登录。");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
		String mobile = Struts2Utils.getParameter("mobile");

		try {
			// 保存验证信息
			userManager.SendRandomMessage(user, mobile, MessageType.CHECKPHONE);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result.put("msg", e.getMessage());
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
		return success();
	}

	public String sendMsg() {
		User user = this.getLoginUser();
		if (null == user) {
			jsonMap.put("msg", "您还没登录。请先登录。");
			return faile();
		}
		String mobile = user.getInfo().getMobile();
		if (StringUtils.isBlank(mobile)) {
			jsonMap.put("msg", "请完善基本信息手机号，再进行验证。");
			return faile();
		}
		if (null == user.getValidatedPhoneNo() || !user.getValidatedPhoneNo()) {
			jsonMap.put("msg", "你的帐号还没进行手机绑定!请先绑定");
			return faile();
		}
		try {
			// 保存验证信息
			userManager.SendRandomMessage(user, mobile, MessageType.YANZHENGMA);
			jsonMap.put("mobile", mobile);
			return success();
		} catch (Exception e) {
			logger.warn(e.getMessage());
			jsonMap.put("msg", e.getMessage());
			return faile();
		}
	}

	/**
	 * 接收短信验证
	 * 
	 * @return
	 */
	public String msgValid() {
		if (!isAjaxRequest()) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		Map<String, Object> result = new HashMap<String, Object>();

		String msgCode = Struts2Utils.getParameter("msgCode");

		if (StringUtils.isBlank(msgCode)) {
			result.put("msg", "请输入验证码。");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}

		User user = this.getLoginUser();
		String mobile = Struts2Utils.getParameter("mobile");

		if (null != user.getValidatedPhoneNo() && user.getValidatedPhoneNo()) {
			result.put("msg", "你的帐号已经验证，本次验证无效!");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
		try {
			userManager.userRandomMessage(mobile, MessageType.CHECKPHONE,
					Integer.valueOf(msgCode));
			result.put("msg", "短信验证成功");
			result.put("success", true);
		} catch (ServiceException e) {
			result.put("msg", "短信验证失败,请输入正确的验证码!");
			result.put("success", false);
		}
		Struts2Utils.renderJson(result);
		return null;
	}

	public String msgValid_() {
		if (!isAjaxRequest()) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		Map<String, Object> result = new HashMap<String, Object>();

		String msgCode = Struts2Utils.getParameter("msgCode");

		if (StringUtils.isBlank(msgCode)) {
			result.put("msg", "请输入验证码。");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}

		User user = this.getLoginUser();
		String mobile = user.getInfo().getMobile();
		if (StringUtils.isBlank(mobile)) {
			result.put("msg", "请完善基本信息手机号，再进行验证。");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
		if (null == user.getValidatedPhoneNo() || !user.getValidatedPhoneNo()) {
			result.put("msg", "你的帐号还没进行手机绑定！请先绑定");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
		try {
			userManager.userRandomMessage(mobile, MessageType.YANZHENGMA,
					Integer.valueOf(msgCode));
			result.put("msg", "短信验证成功");
			result.put("success", true);
		} catch (ServiceException e) {
			result.put("msg", "短信验证失败,请输入正确的验证码!");
			result.put("success", false);
		}
		Struts2Utils.renderJson(result);
		return null;
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

	@Resource
	private UserWonRankManagerImpl userWonRankEntityManager;

	/**
	 * 盈利排行榜
	 * 
	 * @return
	 */
	private Lottery lottery;// 彩种
	private int ptOrdinal;// 玩法索引
	private Boolean success;// 方案是否成功(流产)
	private Boolean fadan;// 是否为发单(跟单)
	private Integer days;// 时间范围

	public String profitRank() {
		if (lottery == null) {
			lottery = Lottery.JCZQ;
		}
		if (days == null) {
			days = 30;
		}
		if (success == null) {
			success = true;
		}
		if (fadan == null) {
			fadan = true;
		}
		pagination.setPageSize(38);
		pagination = userWonRankEntityManager.findUserWonRank(lottery,
				ptOrdinal, success, fadan, days, pagination);
		String playTypeName = null;
		switch (lottery) {
		case JCZQ:
			playTypeName = com.cai310.lottery.support.jczq.PlayType
					.valueOfOrdinal(ptOrdinal).getText();
			break;
		case JCLQ:
			playTypeName = com.cai310.lottery.support.jclq.PlayType
					.valueOfOrdinal(ptOrdinal).getText();
			break;
		case DCZC:
			playTypeName = com.cai310.lottery.support.dczc.PlayType
					.valueOfOrdinal(ptOrdinal).getText();
			break;
		case SFZC:
			playTypeName = com.cai310.lottery.support.zc.PlayType
					.valueOfOrdinal(ptOrdinal).getText();
			break;
		}
		Struts2Utils.setAttribute("playTypeName", playTypeName);
		return "profit-rank";
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

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public BankInfoForm getBankInfoForm() {
		return bankInfoForm;
	}

	public void setBankInfoForm(BankInfoForm bankInfoForm) {
		this.bankInfoForm = bankInfoForm;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Boolean getFadan() {
		return fadan;
	}

	public void setFadan(Boolean fadan) {
		this.fadan = fadan;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public int getPtOrdinal() {
		return ptOrdinal;
	}

	public void setPtOrdinal(int ptOrdinal) {
		this.ptOrdinal = ptOrdinal;
	}

}
