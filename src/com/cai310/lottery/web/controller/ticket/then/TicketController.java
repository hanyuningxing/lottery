package com.cai310.lottery.web.controller.ticket.then;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.SchemeInfoDTO;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.ticket.TicketLogger;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Executable;
import com.cai310.lottery.support.ExecuteException;
import com.cai310.lottery.support.ExecutorUtils;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;
import com.cai310.utils.DateUtil;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Maps;
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/ticket/common.jsp")
	}) 
public class TicketController extends TicketBaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7222044467256039697L;
	private String periodNumber;
	/**
	 * @throws IOException 
	 * 
	 */
	public String remainMoneyTicket() {
		Map map = Maps.newHashMap();
		ReqParamVisitor reqParamVisitor = new ReqParamVisitor();
		JsonConfig jsonConfig = new JsonConfig();  
		try {
			this.wAction=Struts2Utils.getParameter("wAction");
			this.wParam=Struts2Utils.getParameter("wParam");
			this.wSign=Struts2Utils.getParameter("wSign");
			this.wAgent=Struts2Utils.getParameter("wAgent");
			if(StringUtils.isBlank(wAction)){
				throw new WebDataException("4-请求Id为空");
			}
			if(StringUtils.isBlank(wSign)){
				throw new WebDataException("1-请求密钥为空");
			}
			if(StringUtils.isBlank(wAgent)){
				throw new WebDataException("1-请求平台号为空");
			}
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
			if(null!=this.ticketPlatformInfo){
				ticketPlatformInfo = this.ticketThenEntityManager.getTicketPlatformInfo(ticketPlatformInfo.getId());
				map.put("wAgent", ticketPlatformInfo.getId());
				map.put("remainMoney", ticketPlatformInfo.getRemainMoney());
			}
			map.put("processId", "0");
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				map.put("errorMsg", e.getMessage());
			}else{
				map.put("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				map.put("errorMsg", e.getMessage());
			}else{
				map.put("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				map.put("errorMsg", e.getMessage());
			}else{
				map.put("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		}
		renderJson(map,jsonConfig);
		return null;
	}
	public String time(){
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();  
		try {
			map.put("time", DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
			map.put("processId", "0");
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				map.put("errorMsg", e.getMessage());
			}else{
				map.put("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				map.put("errorMsg", e.getMessage());
			}else{
				map.put("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	public String index() throws IOException {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();  
		TicketLogger ticketLogger =null;
		StringBuffer sb = new StringBuffer();
		try {
			check();
			map.put("processId", "0");
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				map.put("errorMsg", e.getMessage());
			}else{
				map.put("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				map.put("errorMsg", e.getMessage());
			}else{
				map.put("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				map.put("errorMsg", e.getMessage());
			}else{
				map.put("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	public String getPeriodNumber() {
		return periodNumber;
	}
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	@Override
	protected SchemeService getSchemeService() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected Lottery getLottery() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void buildReqParamVisitor(ReqParamVisitor reqParamVisitor) {
		this.orderId = reqParamVisitor.getOrderId().trim();
		this.mode = Integer.valueOf(reqParamVisitor.getMode());
		this.periodNumber = String.valueOf(reqParamVisitor.getPeriodNumber());
		this.schemeCost = Integer.valueOf(reqParamVisitor.getCost());
		this.multiple = Integer.valueOf(reqParamVisitor.getMultiple());
		this.units = Integer.valueOf(reqParamVisitor.getUnits());
		if(null!=reqParamVisitor.getShareType()){
			this.shareType = Integer.valueOf(reqParamVisitor.getShareType());
		}
		if(null!=reqParamVisitor.getSubscriptionCost()){
			this.subscriptionCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getSubscriptionCost()));
		}
		if(null!=reqParamVisitor.getBaodiCost()){
			this.baodiCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getBaodiCost()));
		}
		if(null!=reqParamVisitor.getCommissionRate()){
			this.commissionRate = Float.valueOf(reqParamVisitor.getCommissionRate());
		}
		if(null!=reqParamVisitor.getMinSubscriptionCost()){
			this.minSubscriptionCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getMinSubscriptionCost()));
		}
		if(null!=reqParamVisitor.getSecretType()){
			this.secretType = Integer.valueOf(reqParamVisitor.getSecretType());
		}
		
	}
	@Override
	protected List buildMatchList() throws WebDataException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected SchemeInfoDTO getSchemeMatchDTO(Scheme scheme)
			throws WebDataException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected List buildMatchResultList() throws WebDataException {
		// TODO Auto-generated method stub
		return null;
	}
}
