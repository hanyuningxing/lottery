package com.cai310.lottery.web.sso;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import com.cai310.lottery.common.AspMD5;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.spring.SpringContextHolder;
import com.cai310.utils.Struts2Utils;

public class SsoLoginHolder {

	private static long TIME_2004_1_1 = -1;

	private final static String validationKey = "728h3892";

	private final static String ENCODING = "UTF-8";

	private final static String ssoCookieName = "ZCLOGIN";

	private final static String LOGIN_USER = "ssoUser";

	private final static String SERVERNAME_REGEX = "[^\\.]*\\.(cn|com|org)$";

	private final static int CookieTimeOut = 40;

	private static UserEntityManager userManager;
	public static UserEntityManager getUserManager() {
		if (userManager == null) {
			userManager = (UserEntityManager) SpringContextHolder.getBean("userEntityManager");
		}
		return userManager;
	}
	/**
	 * 在配置文件配置一个实例，把userManager注入进来
	 */
	public void setUserManager(UserEntityManager userManager) {
		SsoLoginHolder.userManager = userManager;
	}

	static {
		Calendar cal = Calendar.getInstance();
		cal.set(2004, 0, 1, 0, 0, 0);
		TIME_2004_1_1 = cal.getTimeInMillis();
	}

	public static User getLoggedUser() {
		HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
		User user = getUserFromCookie(request);
		if (user != null) {
			addLoginCookie(request, response, user);
			user = userManager.getUser(user.getId());
		}
		return user;
	}


	public static User login(Long id) {
		User user = getUserManager().getUser(id);
		HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
		addLoginCookie(request, response, user);
		reFlushLoginUser(request);
		return user;
	}
	public static void addLoginCookie(HttpServletRequest request, HttpServletResponse response, User user_) {
		Cookie cookie = getLoginCookie(user_);
		String serverName = request.getServerName();
		if (StringUtils.isNotBlank(serverName)) {
			Pattern p = Pattern.compile(SERVERNAME_REGEX);
			Matcher m = p.matcher(serverName);
			if (m.find()) {
				cookie.setDomain(m.group());
			}
		}
		response.addCookie(cookie);
	}

	/**
	 * 获取登录用户的Cookie
	 * 
	 * @param userInfo
	 * @return
	 */
	public static Cookie getLoginCookie(User user) {
		AspMD5 digest = new AspMD5();
		String key = digest.calcMD5(user.getPassword());
		long now = System.currentTimeMillis();
		int expires = (int) ((now - TIME_2004_1_1) / 1000);
		String val_prefix = user.getId() + "|" + expires + "|";
		String val = val_prefix + digest.calcMD5(val_prefix + key + validationKey);
		Cookie cookie = generateLoginCookie();
		cookie.setValue(val);
		cookie.setMaxAge(expires);
		cookie.setPath("/");
		return cookie;
	}
	public static String getPhoneLoginUserPwd(User user) {
		AspMD5 digest = new AspMD5();
		String val = digest.calcMD5(user.getId() + user.getPassword() + validationKey);
		return val;
	}

	/**
	 * 生成供登录用的Cookie
	 * 
	 * @return
	 */
	private static Cookie generateLoginCookie() {
		Cookie cookie = new Cookie(ssoCookieName, null);
		cookie.setPath("/");
		return cookie;
	}

	/**
	 * 根据Cookie获取用户信息
	 * 
	 * @param request
	 * @return
	 */
	public static User getUserFromCookie(HttpServletRequest request) {
		User user = null;
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; i++) {
			if (ssoCookieName.equals(cookies[i].getName())) {
				try {
					String cookie_val = java.net.URLDecoder.decode(cookies[i].getValue(), ENCODING);
					if (StringUtils.isNotBlank(cookie_val)) {
						String[] vals = StringUtils.split(cookie_val, '|');
						if (vals.length == 3 && checkCookieTime(vals[1])) {
							String userId = vals[0];
							user = getUserFromSession(request);
							if (user != null && user.getId() != Integer.parseInt(userId)) {
								user = null;
							}

							if (user == null) {
								user = getUserManager().getUser(Long.valueOf((userId)));
							}

							if (checkCookieMd5(user, vals[1], vals[2])) {
								request.getSession(true).setAttribute(LOGIN_USER, user);
								return user;
							} else {
								return null;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}

		return user;
	}

	/**
	 * 刷新登录用户的信息
	 * 
	 * @param request
	 */
	public static void reFlushLoginUser(HttpServletRequest request) {
		HttpSession sess = request.getSession(false);
		if (sess.getAttribute(LOGIN_USER) instanceof User) {
			User user = (User) sess.getAttribute(LOGIN_USER);
			if (user != null) {
				user = getUserManager().getUser(user.getId());
				request.getSession(true).setAttribute(LOGIN_USER, user);
			}
		}

	}

	/**
	 * 从Session里取用户信息
	 * 
	 * @param request
	 * @return
	 */
	private static User getUserFromSession(HttpServletRequest request) {
		 HttpSession sess = request.getSession(false);
		 if (sess == null)return null;
		 if (sess.getAttribute(LOGIN_USER) instanceof User) {
			 return (User) sess.getAttribute(LOGIN_USER);
		 }
		 return null;
	}

	/**
	 * 验证Cookie是否过期
	 * 
	 * @param timeval
	 * @return
	 */
	private static boolean checkCookieTime(String timeval) {
		int secs = Integer.parseInt(timeval) + CookieTimeOut * 60;
		long now = (System.currentTimeMillis() - TIME_2004_1_1) / 1000;
		if (secs > now) {
			return true;
		}
		return false;
	}

	/**
	 * 验证Cookie是否合法
	 * 
	 * @param userInfo
	 * @param timeval
	 * @param md5
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkCookieMd5(User user, String timeval, String md5) throws NoSuchAlgorithmException {
		if (user != null) {
			AspMD5 digest = new AspMD5();
			String msg = user.getId() + "|" + timeval + "|" + digest.calcMD5(user.getPassword()) + validationKey;
			if (md5.equals(digest.calcMD5(msg))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 退出登录，清除Cookie
	 * 
	 * @param request
	 * @param response
	 */
	public static void logout() {
		HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
		User user_ = getUserFromCookie(request);
		if (user_ != null) {
			Cookie cookie = generateLoginCookie();
			cookie.setMaxAge(0);
			response.addCookie(cookie);

			String serverName = request.getServerName();
			if (StringUtils.isNotBlank(serverName)) {
				Pattern p = Pattern.compile(SERVERNAME_REGEX);
				Matcher m = p.matcher(serverName);
				if (m.find()) {
					Cookie cookie2 = generateLoginCookie();
					cookie2.setMaxAge(0);
					cookie2.setDomain(m.group());
					response.addCookie(cookie2);
				}
			}
		}
	}

	

}
