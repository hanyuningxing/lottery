package com.cai310.lottery.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

import net.sf.json.JSONObject;

import com.fivestars.interfaces.bbs.client.Client;
import com.fivestars.interfaces.bbs.util.XMLHelper;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.web.sso.SsoLoginHolder;
import com.cai310.utils.MD5;
import com.cai310.utils.Struts2Utils;

public class DzcServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doAll(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doAll(req, resp);
	}

	private void doAll(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Log.info("kk77>#1");
			String action = req.getParameter("action");
			String ucRet = "";
			if("login".equalsIgnoreCase(action)){
				Log.info("kk77>#2.1");
				User user = (User) SsoLoginHolder.getLoggedUser();
				String password = (String)Struts2Utils.getSession().getAttribute("password");
				Log.info("--------------->>>>password end");	
				Log.info("kk77>#2.1bb:" + user);
				ucRet = login(user.getUserName(), password);
				map.put("msg", "login");
				Log.info("kk77>#2.2");
			}else{
				Log.info("kk77>#3.1 > " + action );
				ucRet = logout();
				map.put("msg", "loginout");
				Log.info("kk77>#3.2");
			}
			
			map.put("result", "success");
			map.put("ucret", ucRet);
			map.put("dzurl", exactUrlFromJs(ucRet));
		} catch (Exception e) {
			map.put("result", "failure");
			Log.warn("kk77>#99 >",e);
		}

		try {
			String result = JSONObject.fromObject(map).toString();
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();
			out.print(result);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String register(String username, String password, String email) {
		System.out.println("[UC-Register >>>]" + username + "/" + password
				+ "/" + email);
		Client client = new Client();
		String result = client.uc_user_register(username, password, email);
		System.out.println("[UC-Register o-Result]" + result);
		return result;
	}

	public String login(String username, String password) {
		System.out.println("[UC-Login >>>]" + username + "/" + password);
		Client client = new Client();
		String result = client.uc_user_login(username, password);
		System.out.println("[UC-Login o-Result (*******)]" + result);
		LinkedList<String> rs = XMLHelper.uc_unserialize(result);
		if (rs.size() > 0) {
			int uid = Integer.parseInt(rs.get(0));
			if (uid > 0) {
				result = client.uc_user_synlogin(uid);
			} else if (uid == -1) {
				Log.info("============[UC-Login ERROR]用户不存在,或者被删除=============");

			} else if (uid == -2) {
				Log.info("============[UC-Login ERROR]密码错=============");
			} else {
				Log.info("============[UC-Login ERROR]未定义=============");
			}

		} else {
			Log.info("============[UC-Login ERROR]login fail=============");
			return "";
		}
		Log.info("============[UC-Login o-Result]============="+ result);
		return result;
	}

	public String logout() {
		Log.info("============[UC-logout >>>]=============");
		Client client = new Client();
		String result = client.uc_user_synlogout();
		SsoLoginHolder.logout();
		Struts2Utils.getSession().setAttribute("user", null);
		Struts2Utils.getSession().setAttribute("password", null);
		Log.info("============[UC-logout o-Result]============="+result);
		return result;
	}

	/**
	 * 从Js里面抽出url
	 * 
	 * @return
	 */
	private String exactUrlFromJs(String js) {
		if (js == null || js.trim().equals("")) {
			return "";
		}
		String url = "";
		int idxOfSrc = js.indexOf("src=\"");
		if (idxOfSrc > 0) {

			int idxEndOfSrc = js.indexOf("\"", idxOfSrc + 5);
			url = js.substring(idxOfSrc + 5, idxEndOfSrc);
		}
		return url;
	}
}
