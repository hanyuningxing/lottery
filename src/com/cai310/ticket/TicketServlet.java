package com.cai310.ticket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.utils.JsonUtil;

public class TicketServlet extends HttpServlet {
	/**
	 * 
	 * 
	 */
	protected String wAction;
	
	protected String wLotteryId;
	
	protected String wParam;
	
	protected String wSign;
	
	protected String wAgent;
	
	@Autowired
	protected PeriodEntityManager periodManager;
	private static final long serialVersionUID = 8897416698107668179L;
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void check(HttpServletRequest request) {
		this.wAction=request.getParameter("wAction");
		this.wParam=request.getParameter("wParam");
		this.wSign=request.getParameter("wSign");
		this.wAgent=request.getParameter("wAgent");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.check(request);
		String wLotteryId = null;
		if(StringUtils.isNotBlank(wParam)){
			if (wParam.contains("%")){
				wParam = URLDecoder.decode(wParam,"UTF-8");
			}
			
			java.util.Map<String, Object> map = JsonUtil.getMap4Json(wParam);
			if(null!=map){
				 wLotteryId = String.valueOf(map.get("wLotteryId"));
			}
		}
		StringBuffer url = new StringBuffer();

		try{
			
			if(StringUtils.isNotBlank(wAction)){
				if("100".equals(wAction.trim())){
					url.append("/ticket/then/ticket!time.action");
				}else if("103".equals(wAction.trim())){
					url.append("/external/user!findCurrentPeriodsTicket.action");
				}else if("106".equals(wAction.trim())){
					url.append("/ticket/then/ticket!remainMoneyTicket.action");
				}else{
					url.append("/ticket/then/");
					if(StringUtils.isNotBlank(wLotteryId)){
							Lottery lottery = Lottery.values()[Integer.valueOf(wLotteryId.trim())];
							if(null!=lottery){
								url.append(lottery.getKey());
								if(StringUtils.isNotBlank(wAction)){
									if("101".equals(wAction.trim())){
										url.append("!createTicket.action");
									}else if("102".equals(wAction.trim())){
										url.append("!query.action");
									}else if("104".equals(wAction.trim())){
										url.append("!matchTicket.action");
									}else if("105".equals(wAction.trim())){
										url.append("!resultTicket.action");
									}else if("107".equals(wAction.trim())){
										url.append("!award.action");
									}else if("108".equals(wAction.trim())){
										url.append("!prizeTicket.action");
									}else if("109".equals(wAction.trim())){
										url.append("!resultInfoTicket.action");
									}else{
										throw new Exception();
									}
								}else{
									///没有请求ID
									throw new Exception();
								}
							}else{
								///没有平台
								throw new Exception();
							}
						}else{
							///没有平台
							throw new Exception();
						}
					}
				}
		}catch(Exception e){
			RequestDispatcher rd = request.getRequestDispatcher("/ticket/then/ticket!index.action");
			rd.forward(request,response);
		}
		if(StringUtils.isBlank(url.toString())){
			RequestDispatcher rd = request.getRequestDispatcher("/ticket/then/ticket!index.action");
			rd.forward(request,response);
		}
		RequestDispatcher rd = request.getRequestDispatcher(url.toString());
		rd.forward(request,response);
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		String is = "${}";
		System.out.println(URLDecoder.decode(is,"UTF-8"));
	}
	public String getwAction() {
		return wAction;
	}
	public void setwAction(String wAction) {
		this.wAction = wAction;
	}
	public String getwLotteryId() {
		return wLotteryId;
	}
	public void setwLotteryId(String wLotteryId) {
		this.wLotteryId = wLotteryId;
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
}
