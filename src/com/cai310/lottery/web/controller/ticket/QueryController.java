package com.cai310.lottery.web.controller.ticket;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.service.rmi.QueryServiceRMIOfTicket;
import com.cai310.lottery.service.rmi.TicketEntityManagerRMI;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Action("query")
public class QueryController extends BaseController {

	private static final long serialVersionUID = 5741625897396579892L;
	
	@Autowired
	private QueryServiceRMIOfTicket queryServiceRMIOfTicket;
	@Autowired
	private TicketEntityManagerRMI ticketEntityManager;
	private String printIdList;

    public String index(){
    	HttpServletResponse response = Struts2Utils.getResponse();
    	response.setContentType("text/xml");
    	response.setCharacterEncoding("UTF-8");
    	response.setHeader("pragma", "no-cache");
    	List<Long> ids = Lists.newArrayList();
    	if(StringUtils.isNotBlank(printIdList)){
    		if(printIdList.indexOf(",")!=-1){
    			String[] arr = printIdList.split(",");
    			for (String id : arr) {
    				try {
    					ids.add(Long.valueOf(id));
    				} catch (Exception e) {
    					logger.warn("查询出票id出错---"+id+e.getMessage());
    				}
				}
    		}else{
    			try {
					ids.add(Long.valueOf(printIdList));
				} catch (Exception e) {
					logger.warn("查询出票id出错---"+printIdList+e.getMessage());
				}
    		}
    	}
    	try {
			response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
			response.getOutputStream().println("<zcsmsg>");
			response.getOutputStream().println("<tickets>");
			List<Ticket>  ticketList= ticketEntityManager.findTicketByIds(ids);
			if(null!=ticketList&&!ticketList.isEmpty()){
				for (Ticket ticket : ticketList) {
					if(ticket.isWon()){
						response.getOutputStream().println("<ticket id=\""+ticket.getId()+"\" prize=\""+ticket.getTotalPrizeAfterTax()+"\" />");
					}
				}
			}
			response.getOutputStream().println("</tickets>");
			response.getOutputStream().println("</zcsmsg>");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			response.flushBuffer();
		} catch (IOException e) {
			logger.warn("查询出票输出出错---"+printIdList+e.getMessage());
		}finally{
			try {
				response.getOutputStream().close();
			} catch (IOException e) {
				logger.warn("查询出票关闭出错---"+printIdList+e.getMessage());
			}
		}
		return null;

    }
	public String getPrintIdList() {
		return printIdList;
	}

	public void setPrintIdList(String printIdList) {
		this.printIdList = printIdList;
	}
	public String getTicketCodeBySchemeNumber(){
		try{
			Map map = Maps.newHashMap();
	       	JsonConfig jsonConfig = new JsonConfig();  
			XDetachedCriteria criteria = new XDetachedCriteria(Ticket.class, "m");
			String schemeNumber = Struts2Utils.getParameter("schemeNumber");
			if (StringUtils.isNotBlank(schemeNumber)) {
				criteria.add(Restrictions.eq("m.schemeNumber", schemeNumber));
				criteria.addOrder(Order.desc("m.id"));
				List<Ticket> list = queryServiceRMIOfTicket.findByDetachedCriteria(criteria);
				List<String> idList = Lists.newArrayList();
				for (Ticket ticket : list) {
					if(null==ticket.getTicketSupporter()){
						idList.add(TicketSupporter.RLYG.getSupporterName()+":"+ticket.getLotteryType().getKey()+ticket.getId());
					}else{
						if(TicketSupporter.RLYG.equals(ticket.getTicketSupporter())){
							idList.add(TicketSupporter.RLYG.getSupporterName()+":"+ticket.getLotteryType().getKey()+ticket.getId());
						}else{
							idList.add(ticket.getTicketSupporter().getSupporterName()+":"+ticket.getId());
						}
					}
				}
				map.put("idList", idList);
				renderJson(map,jsonConfig);
				return null;
			}	
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}
	public void renderJson(Map map,JsonConfig jsonConfig){
	    	String jsonString =JSONObject.fromObject(map,jsonConfig).toString();
	    	Struts2Utils.render("application/json", jsonString);
	}
    
}
