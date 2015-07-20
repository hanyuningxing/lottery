package com.cai310.lottery.web.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.mortbay.log.Log;

import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.web.sso.SsoLoginHolder;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.Struts2Utils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


/**
 * 过滤用户超时操作
 * @author TOSHIBA
 *
 */
public class AuthorityInterceptor extends AbstractInterceptor  {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest req = Struts2Utils.getRequest();
		HttpServletResponse resp = Struts2Utils.getResponse();
		String path = req.getServletPath();
		
		String[] filterPaths = new String[]{
				"/agent/index.action",
				"/agent/index!reg.action",
				"/agent/index!findAgentGroupInfoSum.action",
				"/agent/index!findAgentGroupInfo.action",
				"/agent/index!findAgentPersonInfoSum.action",
				"/agent/index!findAgentPersonInfo.action",
				"/user/fund!drawingPer.action",
				"/user/fund!payPer.action",
				"/user/fund!drawing.action",
				"/user/fund!list.action",
				"/user/fund!drawingList.action",
				"/user/scheme!list.action",
				"/lottery/chase!list.action",
				"/lottery/auto!list.action",
				"/lottery/auto!autoNew.action",
				"/user/user!toValidateAccount.action",
				"/user/user!resetPasswd.action",
				"/user/user!updateInfo.action",
				"/user/user!userCenter.action", 
				"/user/user!updatePwd.action"}; 
		User user = SsoLoginHolder.getLoggedUser();
		String host = Struts2Utils.getRequest().getScheme() + "://" + Struts2Utils.getRequest().getServerName()+ ":"
		+ Struts2Utils.getRequest().getServerPort();// " http://190.10.2.33:7001";
		String url = host+Struts2Utils.getRequest().getContextPath() + "/user/user!login.action";
		for(String fiterPath:filterPaths){
			if(path.equals(fiterPath)&&null==user){
				CookieUtil.addCookie(resp, "redirectUrl", path, 0);
				resp.sendRedirect(url);
				return null;
			}
			
		}
		return  invocation.invoke();
	}

}
