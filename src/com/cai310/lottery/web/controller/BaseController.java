package com.cai310.lottery.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.common.PopuType;
import com.cai310.lottery.entity.user.Media;
import com.cai310.lottery.entity.user.Popu;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.web.sso.SsoLoginHolder;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 控制器基类
 *  
 */
public abstract class BaseController extends ActionSupport {
	private static final long serialVersionUID = -9197093886574884834L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String KEY_REDIRECT_URL = "redirectURL";
	public static final String KEY_SCHEME_TEMP = "schemeTemp";

	protected final Map<String, Object> jsonMap = new HashMap<String, Object>();

	protected Boolean isSuccess;
	protected String msgString;
	protected String redirectURL;
	@Autowired
	@Qualifier("popuCache")
	private Cache popuCache;
	@Autowired
	protected UserEntityManager userManager;

	protected String success() {
		return forward(true, SUCCESS);
	}
	
	protected String faile() {
		return forward(false, LOGIN);
	}

	protected String error() {
		return forward(false, GlobalResults.FWD_ERROR);
	}

	protected String redirectforward(Boolean isSuccess, String msssage, String redirectURL, String forword) {
		this.setIsSuccess(isSuccess);
		this.setMsgString(msssage);
		if (null != redirectURL) {
			try {
				redirectURL = java.net.URLDecoder.decode(redirectURL, "utf-8");
			} catch (UnsupportedEncodingException e) {
				this.logger.warn(e.getMessage(), e);
			}
			this.setRedirectURL(redirectURL);
		}
		return forword;
	}
	
	public UserLogin getUserLogin() {
		UserLogin userLogin = new UserLogin();
		userLogin = userManager.getUserLoginBy(this.getLoginUser().getId());
		return userLogin;
	}

	protected boolean isAjaxRequest() {
		String ajax = Struts2Utils.getParameter("ajax");
		return !"false".equals(ajax) && (Struts2Utils.isAjaxRequest() || "true".equals(ajax));
	}

	protected String forward(boolean success, String forword) {
		if (isAjaxRequest()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", success);
			map.putAll(jsonMap);
			map.putAll(getCommonMsg());
			Struts2Utils.renderJson(map);
			return null;
		}
		
		return forword;
	}

	protected Map<String, Object> getCommonMsg() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (hasActionErrors())
			map.put("actionErrors", getActionErrors());
		if (hasFieldErrors())
			map.put("fieldErrors", getFieldErrors());
		if (hasActionMessages())
			map.put("actionMessages", getActionMessages());
		return map;
	}

	public User getLoginUser() {
		return SsoLoginHolder.getLoggedUser();
	}
    public String getInfo800String(){
    	if(null!=this.getLoginUser()){
    		String userId=""+this.getLoginUser().getId();//请赋值用户id[数字];
        	String name=""+this.getLoginUser().getUserName();//请赋值用户名称[字符串];
        	String memo=Struts2Utils.getRequest().getRequestURI()+null==Struts2Utils.getRequest().getQueryString()?"":"?"+Struts2Utils.getRequest().getQueryString();//请赋值备注信息,备注可以携带其他你想要的东西[字符串];
        	String infoValue="";//传递的信息

        	try {
    			infoValue=URLEncoder.encode("userId="+userId+"&name="+name+"&memo="+memo,"UTF-8");
    			return infoValue;
    		} catch (UnsupportedEncodingException e) {
    			logger.warn("获取800推广参数出错");
    		}
    	}
    	return null;
    }
	protected String getRequestKey() {
		HttpServletRequest req = Struts2Utils.getRequest();
		StringBuilder sb = new StringBuilder();
		sb.append(req.getMethod()).append(req.getRequestURI()).append(req.getQueryString());
		return sb.toString();
	}

	/**
	 * 检查重复请求
	 * 
	 * @throws WebDataException
	 *             重复请求异常
	 */
	protected void checkRepeatRequest() throws WebDataException {
		final String REQUEST_TOKEN_KEY = "request_token";
		String requestToken = Struts2Utils.getRequest().getParameter(REQUEST_TOKEN_KEY);
		if (StringUtils.isNotBlank(requestToken)) {
			String token = (String) Struts2Utils.getSession().getAttribute(REQUEST_TOKEN_KEY);
			if (token != null && token.equals(requestToken)){
				throw new WebDataException("请不要重复提交");
			}
			Struts2Utils.getSession().setAttribute(REQUEST_TOKEN_KEY, requestToken);
		}
	}

	public String getMsgString() {
		return msgString;
	}

	public void setMsgString(String msgString) {
		this.msgString = msgString;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getRandomStr(){
		return System.currentTimeMillis()+"";
	}
	public static final String POPU_IP_KEY = "POPU_IP_KEY";
	private String getPopuKey() {
		String ip = Struts2Utils.getRemoteAddr();
		if(null!=ip){
			return POPU_IP_KEY + ip;
		}
		return null;
	}
	
	public Popu getPopu(PopuType popuType){
		//记录
		if(null==getPopuKey())return null;
		Element el = popuCache.get(getPopuKey());
		if (el == null){
			popuCache.put(new Element(getPopuKey(), Integer.valueOf(1)));
		}else{
			return null;
		}
		Popu popu = new Popu();
		User loginUser = getLoginUser();
		try{
			popu.setPopuType(popuType);
			String ip = Struts2Utils.getRemoteAddr();
			popu.setIp(ip);
			if (null!=loginUser) {
				popu.setUserId(loginUser.getId());
				popu.setUserName(loginUser.getUserName());
			}
		}catch(Exception e){
			logger.warn("推广记录的时候发生媒体来源异常");
		}
		//增加aid
		String aid = CookieUtil.getCookieByName(Struts2Utils.getRequest(), "aid");
		if(StringUtils.isNotBlank(aid)){
			try{
				popu.setAid(Long.valueOf(aid));
			}catch(Exception e){
				logger.warn("推广记录的时候发生媒体来源异常");
			}
		}else{
			popu.setAid(Long.valueOf(0));
		}
		////增加用户媒体来源信息
		String mid = CookieUtil.getCookieByName(Struts2Utils.getRequest(), "mid");
		if(StringUtils.isNotBlank(mid)){
			try{
				Media media = userManager.getMedia(Long.valueOf(mid));
				if(null!=media){
					popu.setMid(media.getId());
					popu.setMediaName(media.getName());
				}else{
					if (null!=loginUser&&null!=loginUser.getMid()) {
						//判断用户是否有媒体id
						popu.setMid(loginUser.getMid());
						media = userManager.getMedia(loginUser.getMid());
						if(null!=media){
							popu.setMid(media.getId());
							popu.setMediaName(media.getName());
						}else{
							//默认设为0
							popu.setMid(0L);
							popu.setMediaName("网站");
						}
					}else{
						//默认设为0
						popu.setMid(0L);
						popu.setMediaName("网站");
					}
				}
			}catch(Exception e){
				logger.warn("推广记录的时候发生媒体来源异常");
				//默认设为0
				popu.setMid(0L);
				popu.setMediaName("网站");
			}
		}else{
			try{
				if (null!=loginUser&&null!=loginUser.getMid()) {
					//判断用户是否有媒体id
					popu.setMid(loginUser.getMid());
					Media media = userManager.getMedia(loginUser.getMid());
					if(null!=media){
						popu.setMid(media.getId());
						popu.setMediaName(media.getName());
					}else{
						//默认设为0
						popu.setMid(0L);
						popu.setMediaName("网站");
					}
				}else{
					//默认设为0
					popu.setMid(0L);
					popu.setMediaName("网站");
				}
			}catch(Exception e){
				logger.warn("推广记录的时候发生媒体来源异常");
				//默认设为0
				popu.setMid(0L);
				popu.setMediaName("网站");
			}
		}
	return popu;
}

	public Cache getPopuCache() {
		return popuCache;
	}

	public void setPopuCache(Cache popuCache) {
		this.popuCache = popuCache;
	}
}
