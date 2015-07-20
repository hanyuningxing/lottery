package com.cai310.lottery.web.controller.admin.ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.entity.lottery.ticket.ResetTicketSupporterLog;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.lottery.ticket.TicketForm;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.rmi.LotterySupporterEntityManagerRMI;
import com.cai310.lottery.service.rmi.QueryServiceRMIOfTicket;
import com.cai310.lottery.service.rmi.TicketEntityManagerRMI;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.utils.RegexUtils;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;

public class TicketController extends AdminBaseController {
	private static final long serialVersionUID = -7128890110025210382L;
	
	@Autowired
	private LotterySupporterEntityManagerRMI lotterySupporterEntityManager;
	@Autowired
	private QueryServiceRMIOfTicket queryServiceRMIOfTicket;
	@Autowired
	private TicketEntityManagerRMI ticketEntityManager;
	
	@Autowired
	private QueryService queryService;

	
	private Long id;
	private String operName;
	private Integer index;
	private Integer[] supporters = new Integer[Lottery.values().length];	
	private List<LotterySupporter> lotterySupporterList = new ArrayList<LotterySupporter>();
	
	private Lottery lotteryType;//彩种
	private TicketSupporter ticketSupporter;//出票商
	private String periodNumber;//期号
	private String schemeNumber;//方案编号
	private String 	ticketId;//票号
	private String ticketState;//方案状态 1-成功、0-失败、2-委托
	private Pagination pagination = new Pagination(20);
	private String titckForwardURL;//转向URL。供给/resources/struts.xml调用
	private Date dateStar;//开始时间
	private Date dateEnd;//结束时间
	private String ticketIds;//票ID集合字符串
	private String orderNo;//订单号
	private TicketSchemeState state;
	/**
	 * 
	 * @return
	 */
	public String gotoScheme() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null != adminUser) {
			   ///后台用户
					if (StringUtils.isBlank(schemeNumber))throw new WebDataException("方案号为空.");
					Lottery lottery = lotteryType.valueOfSchemeNumber(schemeNumber);
					if (lottery == null)
						throw new ServiceException("方案类别不正确.");
					Long schemeId = lottery.getSchemeId(schemeNumber);
					if (schemeId == null)
						throw new ServiceException("方案号不正确.");
					
					if(lottery.getCategory().equals(LotteryCategory.FREQUENT)){
						//高频
					    titckForwardURL="/"+lottery.getKey()+"/scheme!show.action?id="+schemeId;
					}else{
						titckForwardURL="/admin/lottery/"+lottery.getKey()+"/scheme!show.action?schemeId="+schemeId;
					}
				return "titckForwardURL";
			}
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return this.error();
	}
	
	/**
	 * 获取出票设置信息
	 * @return
	 */
	public String ticketSupporter() {
		List<LotterySupporter> list = lotterySupporterEntityManager.findLotterySupporter();
		for(Lottery l:Lottery.values()){
			LotterySupporter lotterySupporter = new LotterySupporter();
			lotterySupporter.setLotteryType(l);
			for(LotterySupporter ls:list){
				if(ls.getLotteryType()==l){
					lotterySupporter = ls;
					break;
				}
			}
			lotterySupporterList.add(lotterySupporter);
		}		
		return "ticket-supporter";
	}

	/**
	 * 保存彩种出票设置
	 * @return
	 */
	public String saveLotterySupporter() {
		Integer supporterIndex = supporters[index];
		TicketSupporter ticketSupporter = TicketSupporter.values()[supporterIndex];
		Lottery lotteryType = Lottery.values()[index];
		LotterySupporter lotterySupporter = new LotterySupporter();
		lotterySupporter.setId(id);
		lotterySupporter.setTicketSupporter(ticketSupporter);
		lotterySupporter.setLotteryType(lotteryType);
		lotterySupporterEntityManager.saveLotterySupporter(lotterySupporter);
		return this.ticketSupporter();
	}
	
	/**
	 * 修改出票状态
	 * @return
	 */
	public String updateTiketState() {
		LotterySupporter lotterySupporter= lotterySupporterEntityManager.getLotterySupporter(id);
		if(this.id==null)Struts2Utils.renderText("数据库中无此记录!");
		lotterySupporter.setUsable(!lotterySupporter.isUsable());
		lotterySupporterEntityManager.saveLotterySupporter(lotterySupporter);		
		String stateString = lotterySupporter.isUsable()?"开启中":"关闭中";
		Struts2Utils.renderText(stateString);
		return null;		
	}
	 public String cooperateTicketList(){
		 XDetachedCriteria criteria = new XDetachedCriteria(TicketThen.class, "t");
		 criteria.addOrder(Order.desc("t.id"));
			if (lotteryType!=null) {
				criteria.add(Restrictions.eq("t.lotteryType", lotteryType));
			}
			if (StringUtils.isNotBlank(periodNumber)) {
				criteria.add(Restrictions.eq("t.periodNumber", periodNumber));
			}
			
			if (StringUtils.isNotBlank(ticketId)) {
				criteria.add(Restrictions.eq("t.id", Long.valueOf(ticketId)));
			}
			if (state!=null) {
				criteria.add(Restrictions.eq("t.state", state));
			}
			if (StringUtils.isNotBlank(schemeNumber)) {
				criteria.add(Restrictions.eq("t.schemeNumber", schemeNumber));
			}
			if(StringUtils.isNotBlank(orderNo)){
				criteria.add(Restrictions.eq("t.orderId", orderNo.trim()));
			}
		 pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		 return "cooperateTicketList";
	 }
	/**
	 * 查询出票信息列表
	 * @return
	 */
	public String ticketList() {
		XDetachedCriteria criteria = new XDetachedCriteria(Ticket.class, "m");
		ProjectionList propList = Projections.projectionList();
		propList.add(Projections.property("id"), "id");
		propList.add(Projections.property("createTime"), "createTime");
		propList.add(Projections.property("schemeNumber"), "schemeNumber");
		propList.add(Projections.property("lotteryType"), "lotteryType");
		propList.add(Projections.property("periodNumber"), "periodNumber");
		propList.add(Projections.property("mode"), "mode");
		propList.add(Projections.property("schemeCost"), "schemeCost");
		propList.add(Projections.property("ticketFinsh"), "ticketFinsh");
		propList.add(Projections.property("stateCode"), "stateCode");
		propList.add(Projections.property("remoteTicketId"), "remoteTicketId");
		propList.add(Projections.property("sendTime"), "sendTime");
		propList.add(Projections.property("stateCodeMessage"), "stateCodeMessage");
		propList.add(Projections.property("stateModifyTime"), "stateModifyTime");
		propList.add(Projections.property("ticketSupporter"), "ticketSupporter");
		propList.add(Projections.property("pause"), "pause");
		criteria.setProjection(propList);
		criteria.setResultTransformer(Transformers.aliasToBean(TicketForm.class));
		
		if (lotteryType!=null) {
			criteria.add(Restrictions.eq("m.lotteryType", lotteryType));
		}
		if(ticketSupporter!=null){
			criteria.add(Restrictions.eq("m.ticketSupporter", ticketSupporter));
		}
		if (StringUtils.isNotBlank(periodNumber)) {
			criteria.add(Restrictions.eq("m.periodNumber", periodNumber));
		}
		
		if (StringUtils.isNotBlank(ticketId)) {
			criteria.add(Restrictions.eq("m.id", Long.valueOf(ticketId)));
		}
		
		
		if (StringUtils.isNotBlank(ticketState)) {
			if("3".equals(ticketState)){
				criteria.add(Restrictions.eq("m.pause", true));//暂停
			}else if("2".equals(ticketState)){
				criteria.add(Restrictions.eq("m.stateCode", "0"));//委托
			}else if("1".equals(ticketState)){
				criteria.add(Restrictions.eq("m.stateCode", "1"));//成功
			}else{
				criteria.add(Restrictions.or(Restrictions.isNull("m.stateCode"),Restrictions.gt("m.stateCode", "1")));//失败
			}			
		}
		if (StringUtils.isNotBlank(schemeNumber)) {
			criteria.add(Restrictions.eq("m.schemeNumber", schemeNumber));
		}	
		criteria.addOrder(Order.desc("m.id"));
		pagination = queryServiceRMIOfTicket.findByCriteriaAndPagination(criteria, pagination);
		return "list";	
	}
	
	/**
	 * 重置方案出票商
	 */
	public void resetLotterySupporter(){		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			AdminUser adminUser = getAdminUser();
			if (adminUser==null) {
				throw new WebDataException("登录识别失效，请重新登录!");
			}
			if(this.ticketSupporter==null){
				throw new WebDataException("切换的出票商为空，请核实!");
			}
			if(this.ticketId==null || !this.ticketId.matches(RegexUtils.Number)){
				throw new WebDataException("操作的票ID不合法或为空!");
			}
			Ticket ticket = ticketEntityManager.getTicket(Long.parseLong(this.ticketId));
			if(ticket==null){
				throw new WebDataException("不能匹配到相关的票，请核实!");
			}
			if(ticket.getTicketSupporter()==this.ticketSupporter){
				throw new WebDataException("重复设置出票商，请核实!");
			}			
			if(ticket.getStateCode()=="1"){
				throw new WebDataException("该票已经成功出票，不能切换!");
			}
			if(!ticket.isPause()){
				throw new WebDataException("该票不是暂停状态，不能切换!");
			}
			ticketEntityManager.resetTicketSupporter(ticket, ticketSupporter, adminUser.getName());			
			map.put("success", true);
			map.put("msg", ticketSupporter.getSupporterName());
		}catch (WebDataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		Struts2Utils.renderJson(map);
	}
	
	/**
	 * 查看重置出票商日志信息
	 * @return
	 */
	private static Long dateLimit = Long.valueOf(1000) * Long.valueOf(60) * Long.valueOf(60) * Long.valueOf(24)* Long.valueOf(31);
	public String resetTicketSupporterLogList(){
		try{
			Date currentTime = new Date();
			if (dateStar == null){
				dateStar = DateUtils.addDays(currentTime, -3);
			}
			if (dateEnd == null){
				dateEnd = currentTime;
			}				
			if (dateEnd.getTime() <= dateStar.getTime())
				throw new WebDataException("结束时间不能晚于开始时间！");
			if (dateEnd.getTime() - dateStar.getTime() > dateLimit)
				throw new WebDataException("查询日期间隔不能大于31天！");
			
			XDetachedCriteria criteria = new XDetachedCriteria(ResetTicketSupporterLog.class, "m");
			if (StringUtils.isNotBlank(operName)) {
				criteria.add(Restrictions.eq("m.operName", operName));
			}	
			if (StringUtils.isNotBlank(ticketId)) {
				criteria.add(Restrictions.eq("m.ticketId", Long.valueOf(ticketId.trim())));
			}
			if (StringUtils.isNotBlank(schemeNumber)) {
				criteria.add(Restrictions.eq("m.schemeNumber", schemeNumber.trim()));
			}
			criteria.add(Restrictions.ge("m.createTime", dateStar));
			criteria.add(Restrictions.le("m.createTime", dateEnd));
			criteria.addOrder(Order.desc("m.id"));
			pagination = queryServiceRMIOfTicket.findByCriteriaAndPagination(criteria, pagination);
		}catch(Exception e){
			this.addActionMessage(e.getMessage());
		}		
		return "resetTicketSupporterList";
	}
	
	/**
	 * 暂停发送票
	 */
	public void pauseSend(){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			AdminUser adminUser = getAdminUser();
			if (adminUser==null) {
				throw new WebDataException("登录识别失效，请重新登录!");
			}
			if(StringUtil.isEmpty(this.ticketId) || !this.ticketId.matches(RegexUtils.Number)){
				throw new WebDataException("操作的票ID不合法或为空!");
			}
			Ticket ticket = ticketEntityManager.getTicket(Long.parseLong(this.ticketId));
			if(ticket==null){
				throw new WebDataException("不能匹配到相关的票，请核实!");
			}
			if(ticket.isPause()){
				throw new WebDataException("该票已是暂停状态，请核实!");
			}			
			ticketEntityManager.pauseSend(ticket,adminUser.getName());
			map.put("success", true);
			map.put("msg", "暂停操作成功!");
		}catch (WebDataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		Struts2Utils.renderJson(map);
	}
	
	/**
	 * 批量暂停发送票
	 */
	public void pauseSendOfBatch(){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			AdminUser adminUser = getAdminUser();
			if (adminUser==null) {
				throw new WebDataException("登录识别失效，请重新登录!");
			}
			if(StringUtil.isEmpty(this.ticketIds)){
				throw new WebDataException("没有选择你要操作的票!");
			}
			List<Long> ticketIdsOfL = Lists.newArrayList();
			String[] ticketIdArr = this.ticketIds.split(",");
			for(String ticketId : ticketIdArr){
				if(!ticketId.matches(RegexUtils.Number)){
					throw new WebDataException("票ID："+ticketId+"不合法!");
				}
				ticketIdsOfL.add(Long.parseLong(ticketId));
			}
			ticketEntityManager.pauseSendOfBatch(ticketIdsOfL,adminUser.getName());
			map.put("success", true);
			map.put("msg", "批量暂停操作成功!");
		}catch (WebDataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		Struts2Utils.renderJson(map);
	}
	
	/**
	 * 批量重置出票商
	 */
	public void resetTicketSupporterOfBath(){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			AdminUser adminUser = getAdminUser();
			if (adminUser==null) {
				throw new WebDataException("登录识别失效，请重新登录!");
			}
			if(this.ticketSupporter==null){
				throw new WebDataException("切换的出票商为空，请核实!");
			}
			if(StringUtil.isEmpty(this.ticketIds)){
				throw new WebDataException("没有选择你要操作的票!");
			}
			List<Long> ticketIdsOfL = Lists.newArrayList();
			String[] ticketIdArr = this.ticketIds.split(",");
			Ticket ticket = null;
			for(String ticketId : ticketIdArr){
				if(!ticketId.matches(RegexUtils.Number)){
					throw new WebDataException("票ID："+ticketId+"不合法!");
				}
				ticket = ticketEntityManager.getTicket(Long.parseLong(ticketId));
				if(ticket==null){
					throw new WebDataException("ID:"+ticketId+"，不能匹配到相关的票，请核实!");
				}
				if(ticket.getTicketSupporter()==this.ticketSupporter){
					throw new WebDataException("ID:"+ticketId+"，重复设置出票商，请核实!");
				}			
				if(ticket.getStateCode()=="1"){
					throw new WebDataException("ID:"+ticketId+"，该票已经成功出票，不能切换!");
				}
				if(!ticket.isPause()){
					throw new WebDataException("ID:"+ticketId+"不是暂停状态，不能切换!");
				}
				ticketIdsOfL.add(Long.parseLong(ticketId));
			}			
			ticketEntityManager.resetTicketSupporterOfBatch(ticketIdsOfL, ticketSupporter, adminUser.getName());			
			map.put("success", true);
			map.put("msg", ticketSupporter.getSupporterName());
		}catch (WebDataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		Struts2Utils.renderJson(map);
	}

	/**
	 * @return the lotterySupporterList
	 */
	public List<LotterySupporter> getLotterySupporterList() {
		return lotterySupporterList;
	}


	/**
	 * @param lotterySupporterList the lotterySupporterList to set
	 */
	public void setLotterySupporterList(List<LotterySupporter> lotterySupporterList) {
		this.lotterySupporterList = lotterySupporterList;
	}
	
	/**
	 * @return the supporters
	 */
	public Integer[] getSupporters() {
		return supporters;
	}


	/**
	 * @param supporters the supporters to set
	 */
	public void setSupporters(Integer[] supporters) {
		this.supporters = supporters;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the lotteryType
	 */
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lottery the lottery to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}



	/**
	 * @return the ticketSupporter
	 */
	public TicketSupporter getTicketSupporter() {
		return ticketSupporter;
	}

	/**
	 * @param ticketSupporter the ticketSupporter to set
	 */
	public void setTicketSupporter(TicketSupporter ticketSupporter) {
		this.ticketSupporter = ticketSupporter;
	}

	/**
	 * @return the periodNumber
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the periodNumber to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return the schemeNumber
	 */
	public String getSchemeNumber() {
		return schemeNumber;
	}

	/**
	 * @param schemeNumber the schemeNumber to set
	 */
	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}
	

	
	/**
	 * @return the ticketState
	 */
	public String getTicketState() {
		return ticketState;
	}

	/**
	 * @param ticketState the ticketState to set
	 */
	public void setTicketState(String ticketState) {
		this.ticketState = ticketState;
	}

	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getTitckForwardURL() {
		return titckForwardURL;
	}

	public void setTitckForwardURL(String titckForwardURL) {
		this.titckForwardURL = titckForwardURL;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public Date getDateStar() {
		return dateStar;
	}

	public void setDateStar(Date dateStar) {
		this.dateStar = dateStar;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setTicketIds(String ticketIds) {
		this.ticketIds = ticketIds;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public TicketSchemeState getState() {
		return state;
	}

	public void setState(TicketSchemeState state) {
		this.state = state;
	}

}
