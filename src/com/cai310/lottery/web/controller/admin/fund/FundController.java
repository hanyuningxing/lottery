package com.cai310.lottery.web.controller.admin.fund;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.DrawingOrderState;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.entity.lottery.SaleAnalyse;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.user.DrawingOrder;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.Ipsorder;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.activity.AvtivityConfig;
import com.cai310.lottery.service.rmi.QueryServiceRMIOfTicket;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.lottery.web.controller.admin.user.UserForm;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.ExcelUtil;
import com.cai310.utils.MessageUtil;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FundController extends AdminBaseController {
	private static final long serialVersionUID = -7128890110025210382L;

	@Autowired
	UserEntityManager userManager;
	@Autowired
	private QueryService queryService;
	@Autowired
	private QueryServiceRMIOfTicket queryServiceRMIOfTicket;
	@Autowired
	protected TicketThenEntityManager ticketThenEntityManager;
	private Pagination pagination = new Pagination(20);
	private User user;
	private DrawingOrder drawingOrder;
	private Long id;
	private String userName;
	private String oprMoney;
	private Short oprType;
	private String remark;
	private FundDetailType fundType;
	private DrawingOrderState drawingState;
	private int timeFrame;
	private Boolean locked;
	private String islocked;
	
	private String adminMsg;

	public String getAdminMsg() {
		return adminMsg;
	}

	public void setAdminMsg(String adminMsg) {
		this.adminMsg = adminMsg;
	}
	/**
	 * 
	 * @return
	 */
	public String oprRebate() {
		String fwd = "user-rebate";
//		AdminUser adminUser = getAdminUser();
//		if (null == adminUser) {
//			addActionError("权限不足！");
//			return fwd;
//		}
//		String userId = Struts2Utils.getParameter("userId");
//		if (StringUtils.isBlank(userId)) {
//			addActionError("被操作用户ID为空.");
//			return forward(false, fwd);
//		}
//		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
//			User user = userManager.getUser(Long.valueOf(userId));
//			String moneyStr = ""+user.getRebate();
//			if(moneyStr.indexOf(".")!=-1){
//				String[] moneArr = moneyStr.split("\\.");
//				if(moneArr.length!=2){
//					addActionError("返点出错.");
//					return forward(false, fwd);
//				}
//				Struts2Utils.getRequest().setAttribute("rebate1", Integer.valueOf(moneArr[0]));
//				Struts2Utils.getRequest().setAttribute("rebate2", Integer.valueOf(moneArr[1]));
//			}else{
//				Struts2Utils.getRequest().setAttribute("rebate1", Integer.valueOf(0));
//				Struts2Utils.getRequest().setAttribute("rebate2", Integer.valueOf(0));
//			}
//			NumberFormat nf = NumberFormat.getInstance();
//			nf.setMinimumFractionDigits(2);
//			nf.setMaximumFractionDigits(2);
//			Struts2Utils.getRequest().setAttribute("opr_user_name", user.getUserName());
//			Struts2Utils.getRequest().setAttribute("opr_user_id", user.getId());
//			return forward(true, fwd);
//		}
//
//		// 登录操作
//		try {
//			User user = userManager.getUser(Long.valueOf(userId));
//			User agentUser = userManager.getUser(user.getAgentId());
//			
//			
//			String rebate1 = Struts2Utils.getParameter("rebate1");
//			String rebate2 = Struts2Utils.getParameter("rebate2");
//			if(StringUtils.isBlank(rebate1)){
//				addActionError("返点1还没设置.");
//				return forward(false, fwd);
//			}
//			if(StringUtils.isBlank(rebate2)){
//				addActionError("返点2还没设置.");
//				return forward(false, fwd);
//			}
//			Double rebate = Double.valueOf(rebate1.trim()+"."+rebate2.trim());
//			if(null!=agentUser){
//				if(null ==agentUser.getRebate()){
//					addActionError("帐号返点异常.");
//					return forward(false, fwd);
//				}
//				if(null ==agentUser.getRebate()){
//					addActionError("帐号返点异常.");
//					return forward(false, fwd);
//				}
//				if(agentUser.getRebate()-rebate<0){
//					addActionError("子帐号返点不能大于本账号，请调整.");
//					return forward(false, fwd);
//				}
//			}
//			DetachedCriteria criteria = DetachedCriteria.forClass(User.class, "m");
//			criteria.add(Restrictions.eq("m.agentId", user.getId()));
//			List<User> underUserList = this.queryService.findByDetachedCriteria(criteria);
//			if(null!=underUserList&&!underUserList.isEmpty()){
//				Double underUserRebate = Double.valueOf(0);
//				for (User underUser : underUserList) {
//					if(underUser.getRebate()-underUserRebate>0){
//						underUserRebate = underUser.getRebate();
//					}
//				}
//				if(underUserRebate - rebate>0){
//					addActionError("帐号返点不能小于子账号，请调整.");
//					return forward(false, fwd);
//				}
//			}
//			user.setRebate(rebate);
//			user = userManager.saveUser(user);
//			addActionMessage("修改成功.");
//			return success();
//		} catch (ServiceException e) {
//			addActionError(e.getMessage());
//		} catch (Exception e) {
//			addActionError(e.getMessage());
//			logger.warn(e.getMessage(), e);
//		}
		return forward(false, fwd);
	}
	/**
	 * 用户列表
	 * 
	 * @return
	 */
	public String userList() {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return "user-list";
		}
		XDetachedCriteria criteria = new XDetachedCriteria(User.class, "m");
		
		ProjectionList propList = Projections.projectionList();
		propList.add(Projections.property("id"), "id");
		propList.add(Projections.property("userName"), "userName");
		propList.add(Projections.property("remainMoney"), "remainMoney");
		propList.add(Projections.property("consumptionMoney"), "consumptionMoney");
		propList.add(Projections.property("createTime"), "createTime");
		propList.add(Projections.property("locked"), "locked");
		criteria.setProjection(propList);
		if (StringUtils.isNotBlank(islocked)) {
			if ("true".equals(islocked)) {
				criteria.add(Restrictions.eq("m.locked", true));
			} else if ("false".equals(islocked)) {
				criteria.add(Restrictions.eq("m.locked", false));
			}
		}
		if (StringUtils.isNotBlank(this.getUserName())) {
			criteria.add(Restrictions.like("m.userName", this.getUserName()));
		}
		criteria.addOrder(Order.desc("m.consumptionMoney"));
		criteria.addOrder(Order.desc("m.id"));
		criteria.setResultTransformer(Transformers.aliasToBean(User.class));
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "user-list";
	}

	/**
	 * 
	 * @return
	 */
	public String userInfo() {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return "user-list";
		}
		if (null == this.getId()) {
			addActionError("缺少id！");
			return "user-list";
		}
		user = userManager.getUser(this.getId());

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		UserForm userForm = new UserForm();
		Struts2Utils.getRequest().setAttribute("userForm", userForm);
		return "user-info";
	}
	/**
	 * 
	 * @return
	 */
	public String userMoney() {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return "user-list";
		}
		if (null == this.getId()) {
			addActionError("缺少id！");
			return "user-list";
		}
		user = userManager.getUser(this.getId());
		if (null == user) {
			addActionError("缺少user！");
			return "user-list";
		}
		return "user-money";
	}

	/**
	 * 
	 * @return
	 * @throws WebDataException
	 */
	public String oprMoney() throws WebDataException {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			this.addActionMessage("权限不足！");
			return userMoney();
		}
		if (StringUtils.isBlank(this.getOprMoney())) {
			this.addActionMessage("操作资金为空，请填写！");
			return userMoney();
		}
		BigDecimal oprMoneyBD = null;
		try {
			if (null == this.getId()) {
				this.addActionMessage("用户Id错误。请联系管理员");
				return userMoney();
			}
			user = userManager.getUser(this.getId());
			if (null == this.getUser()) {
				this.addActionMessage("用户Id错误。请联系管理员");
				return userMoney();
			}
			if (null == oprMoney || BigDecimalUtil.valueOf(0).equals(oprMoney)) {
				this.addActionMessage("操作资金错误，请重新填写！");
				return userMoney();
			}

			try {
				oprMoneyBD = BigDecimalUtil.valueOf(Double.valueOf(this.getOprMoney()));
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionMessage("金额格式出错,请改写");
				return userMoney();
			}
			FundMode mode = null;
			if (null != this.getOprType() && Short.valueOf("0").equals(this.getOprType())) {
				mode = FundMode.IN;
			} else if (null != this.getOprType() && Short.valueOf("1").equals(this.getOprType())) {
				mode = FundMode.OUT;
			}
			synchronized (Constant.OPRMONEY) {
				checkRepeatRequest();
				this.ticketThenEntityManager.adminOperUserMoney(user.getId(), mode, oprMoneyBD,StringUtils.isBlank(this.getRemark()) ? "无备注" : this.getRemark(),adminUser);
			}
			this.addActionMessage("操作成功");
			return userMoney();
		} catch (WebDataException e) {
			this.addActionMessage(e.getMessage());
			return userMoney();
		} catch (ServiceException e) {
			this.addActionMessage(e.getMessage());
			return userMoney();
		} catch (Exception e) {
			logger.warn(e.getMessage());
			this.addActionMessage("操作错误。请联系管理员");
			return userMoney();
		}
	}

	/**
	 * 资金明细
	 * 
	 * @return
	 */
	public String fundetailList() {
		XDetachedCriteria criteria = new XDetachedCriteria(FundDetail.class,
				"m");
		if (StringUtils.isNotBlank(this.getUserName())) {

			criteria.add(Restrictions.like("m.userName", this.getUserName()
					.trim()));
		}
		if (fundType != null)
			criteria.add(Restrictions.eq("m.type", fundType));
		try {
			if (null == dateStar && null == dateEnd) {
				dateEnd = new Date();
				dateStar = DateUtil.calDate(dateEnd, 0, 0, -7);
			} else {
				Date date0_00 = new Date();
				if (dateEnd.after(date0_00))
					throw new WebDataException("结束时间不能大于当天！");
				if (dateEnd.getTime() <= dateStar.getTime())
					throw new WebDataException("结束时间不能少于开始时间！");
				if (dateEnd.getTime() - dateStar.getTime() > dataTimeLimit)
					throw new WebDataException("结束时间减去开始时间不能大于120天！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage(e.getMessage());
			return "fundetail-list";
		}

		criteria.add(Restrictions.ge("m.createTime", dateStar));
		criteria.add(Restrictions.le("m.createTime", dateEnd));
		criteria.addOrder(Order.desc("m.id"));

		if (StringUtils.isNotBlank(Struts2Utils.getParameter("downLoadExcel"))) {
			LinkedList<String> biaotou = Lists.newLinkedList();
			biaotou.add("编号");
			biaotou.add("用户名");
			biaotou.add("模式");
			biaotou.add("金额");
			biaotou.add("用户余额");
			biaotou.add("类型");
			biaotou.add("方案号");
			biaotou.add("拆分方案号");
			biaotou.add("备注");
			biaotou.add("时间");
			LinkedList<String> col = Lists.newLinkedList();
			col.add("id");
			col.add("userName");
			col.add("modeString");
			col.add("money");
			col.add("resultMoney");
			col.add("typeString");
			col.add("remarkSchemeNumber");
			col.add("remarkTicketIds");
			col.add("remarkString");
			col.add("createTime");
			try {
				return ExcelUtil.excel("fund", biaotou, criteria, col);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pagination = queryService.findByCriteriaAndPagination(criteria,
				pagination);
		return "fundetail-list";
	}

	/**
	 * 提款订单列表
	 */
	public String drawingList() {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			this.addActionMessage("权限不足！");
			return "drawing-list";
		}
		XDetachedCriteria criteria = new XDetachedCriteria(DrawingOrder.class, "m");
		if (StringUtils.isNotBlank(this.getUserName())) {

			criteria.add(Restrictions.like("m.userName", "%" + this.getUserName().trim() + "%"));
		}
		if (drawingState != null)
			criteria.add(Restrictions.eq("m.state", drawingState));

		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 0);
		c.add(Calendar.MINUTE, 0);
		c.add(Calendar.SECOND, 0);
		switch (timeFrame) {
		case 1:
			c.add(Calendar.DAY_OF_MONTH, -15);// 15天前
			break;
		case 2:
			c.add(Calendar.MONTH, -1);// 1个月前
			break;
		case 3:
			c.add(Calendar.MONTH, -3);// 3个月前
			break;
		default:
			c.add(Calendar.DAY_OF_MONTH, -7);// 7天前
		}
		criteria.add(Restrictions.ge("sendtime", c.getTime()));

		criteria.addOrder(Order.desc("m.id"));

		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);

		return "drawing-list";
	}

	/**
	 * 得到需要审核的提款订单(财务)
	 * 
	 * @return
	 */
	public String passDrawingList() {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			this.addActionMessage("权限不足！");
			return "pass-drawing-list";
		}
		XDetachedCriteria criteria = new XDetachedCriteria(DrawingOrder.class, "m");
		if (StringUtils.isNotBlank(this.getUserName())) {

			criteria.add(Restrictions.like("m.userName", this.getUserName().trim()));
		}
		criteria.add(Restrictions.eq("m.state", DrawingOrderState.PASSCHECK));

		criteria.addOrder(Order.desc("m.id"));

		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);

		return "pass-drawing-list";
	}

	/**
	 * 财务确认订单
	 * 
	 * @return
	 */
	public String passkDrawing() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				this.addActionMessage("权限不足！");
				return passDrawingList();
			}
			if (null == this || null == this.getId()) {
				this.addActionMessage("提现订单为空！");
				return passDrawingList();
			}
			drawingOrder = userManager.getDrawingOrder(this.getId());
			if (null == drawingOrder || null == drawingOrder.getId()) {
				this.addActionMessage("提现订单为空！");
				return passDrawingList();
			}
			synchronized (Constant.DRAWING) {
				if (!DrawingOrderState.PASS.equals(drawingOrder.getState())) {
					drawingOrder.setState(DrawingOrderState.PASS);
					drawingOrder.setConfirmtime(new Date());
					drawingOrder.setConfirmUserid(adminUser.getId());
					drawingOrder.setConfirmUsername(adminUser.getUsername());
					userManager.saveAdminDrawingOrder(adminUser, drawingOrder);
				}
			}
			this.addActionMessage("确认提现订单成功！");
			//发送短信通知用户提现订单成功
			String mobile = userManager.getUserBy(drawingOrder.getUserName()).getInfo().getMobile();
			if(StringUtils.isNotBlank(mobile)){
				List<String> num = Lists.newArrayList(); 
				num.add(mobile);
				String content = "尊敬的球客用户" + drawingOrder.getUserName() + ",您的提现已经汇出,请注意查收！谢谢继续支持我们,祝您中大奖【www.qiu310.com】.";
				MessageUtil.sendMessage(num, content);
			}
			
			return passDrawingList();
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage("确认提现订单错误！");
			return passDrawingList();
		}
	}

	/**
	 * 财务删除订单
	 * 
	 * @return
	 */
	public String deleteDrawing() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				this.addActionMessage("权限不足！");
				return passDrawingList();
			}
			if (null == this || null == this.getId()) {
				this.addActionMessage("提现订单为空！");
				return passDrawingList();
			}
			drawingOrder = userManager.getDrawingOrder(this.getId());
			if (null == drawingOrder || null == drawingOrder.getId()) {
				this.addActionMessage("提现订单为空！");
				return passDrawingList();
			}
			synchronized (Constant.DRAWING) {
				if (!DrawingOrderState.FAILCHECK.equals(drawingOrder.getState())) {
					drawingOrder=userManager.saveDrawingOrder(drawingOrder);
					userManager.deleteDrawingOrder(adminUser, drawingOrder);
				}
			}
			this.addActionMessage("退还提现订单成功");
			return passDrawingList();
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage("退还提现订单错误！");
			return passDrawingList();
		}
	}
	/**
	 * 充值订单
	 * 
	 * @return
	 */
	private Byte ifcheck;

	public String ipsList() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				this.addActionMessage("权限不足！");
				return "ips-list";
			}
			XDetachedCriteria criteria = new XDetachedCriteria(Ipsorder.class, "m");
			if (StringUtils.isNotBlank(this.getUserName())) {

				criteria.add(Restrictions.like("m.userName", "%" + this.getUserName().trim() + "%"));
			}
			if (ifcheck != null)
				criteria.add(Restrictions.eq("m.ifcheck", ifcheck));

			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR_OF_DAY, 0);
			c.add(Calendar.MINUTE, 0);
			c.add(Calendar.SECOND, 0);
			switch (timeFrame) {
			case 1:
				c.add(Calendar.DAY_OF_MONTH, -15);// 15天前
				break;
			case 2:
				c.add(Calendar.MONTH, -1);// 1个月前
				break;
			case 3:
				c.add(Calendar.MONTH, -3);// 3个月前
				break;
			default:
				c.add(Calendar.DAY_OF_MONTH, -7);// 7天前
			}
			criteria.add(Restrictions.ge("createTime", c.getTime()));

			criteria.addOrder(Order.desc("m.id"));
			pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
			return "ips-list";
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage("订单查询出错");
			return "ips-list";
		}
	}

	public String confirmIps() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("权限不足！");
			}
			if (null == this || null == this.getId()) {
				throw new WebDataException("充值订单为空！");
			}
			Ipsorder ipsorder = userManager.getIpsorder(this.getId());
			if (null == ipsorder || null == ipsorder.getId()) {
				throw new WebDataException("充值订单为空！");
			}
			User user = userManager.getUserBy(ipsorder.getUserName());
			if (user == null)
				throw new WebDataException("订单缺用户名");
			userManager.confirmIps(ipsorder, user, adminUser);
			map.put("success", true);
			map.put("msg", "操作成功");
		} catch (WebDataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		Struts2Utils.renderJson(map);
		return null;
	}

	/**
	 * 财务汇总
	 * 
	 * @return
	 */
	private Date dateStar;
	private Date dateEnd;
	private Lottery lotteryType;
	private TicketSupporter ticketSupporter;
	private List<FundDetail> resultList;
	private List<FundDetail> inResultList;
	private List<FundDetail> outResultList;
	private List<SaleAnalyse> saleAnalyseResultList;
	private List<Ticket> ticketResultList;
	private static Long dateLimit = Long.valueOf(1000) * Long.valueOf(60) * Long.valueOf(60) * Long.valueOf(24)
			* Long.valueOf(31);
	private static Long dataTimeLimit= Long.valueOf(1000) * Long.valueOf(60) * Long.valueOf(60) * Long.valueOf(24)
	* Long.valueOf(30)*Long.valueOf(4);
	public String countFund() throws WebDataException {
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向
			return "count_fund";
		}
		try {
			getFundList();
			FundMode fundMode = null;
			Map<Long,CountFundForm> countMap = Maps.newHashMap();
			CountFundForm countFundForm = null;
			Long webSiteTempId = -1L;
			for (FundDetail fundDetail : resultList) {
				User user = userManager.getUser(fundDetail.getUserId());
				if(user.getUserWay().equals(UserWay.WEB)){
					countFundForm = countMap.get(webSiteTempId);
					if(null==countFundForm){
						countFundForm = new CountFundForm();
						countFundForm.setUserId(webSiteTempId);
						countFundForm.setUserName("网站");
						countFundForm.setUserWay(user.getUserWay());
						countMap.put(webSiteTempId, countFundForm);
					}
				}else{
					countFundForm = countMap.get(fundDetail.getUserId());
					if(null==countFundForm){
						countFundForm = new CountFundForm();
						countFundForm.setUserId(fundDetail.getUserId());
						countFundForm.setUserName(fundDetail.getUserName());
						countFundForm.setUserWay(user.getUserWay());
						countMap.put(fundDetail.getUserId(), countFundForm);
					}
				}
				fundMode = fundDetail.getMode();
				fundDetail.getType().countFund(countFundForm,fundMode,fundDetail.getMoney());
			}
//			DetachedCriteria criteria = DetachedCriteria.forClass(User.class, "m");
//			criteria.add(Restrictions.in("m.id", countMap.keySet()));
//			List<User> userList = queryService.findByDetachedCriteria(criteria);
//			for (User user : userList) {
//				countFundForm = countMap.get(user.getId());
//				countFundForm.setUserWay(user.getUserWay());
//				
//				countMap.put(user.getId(), countFundForm);
//			}
			Struts2Utils.setAttribute("countMap", countMap);
			return "count_fund";
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage(e.getMessage());
			return "count_fund";
		}
	}

	private BigDecimal payTotal = BigDecimalUtil.valueOf(0);
	private BigDecimal betTotal = BigDecimalUtil.valueOf(0);
	private BigDecimal bounsTotal = BigDecimalUtil.valueOf(0);
	private BigDecimal drawingTotal = BigDecimalUtil.valueOf(0);
	private BigDecimal adminInTotal = BigDecimalUtil.valueOf(0);
	private BigDecimal adminOutTotal = BigDecimalUtil.valueOf(0);

	public String fundBalance() throws WebDataException {
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向
			return "fund_balance";
		}
		try {
			getFundList();

			FundDetailType fundDetailType = null;
			for (FundDetail fundDetail : resultList) {
				fundDetailType = fundDetail.getType();
				if (null != fundDetailType && null != fundDetail.getMoney()) {
					if (FundDetailType.PAY.equals(fundDetailType) || FundDetailType.ADMINPAY.equals(fundDetailType)) {
						// /充值总额=PAY("在线充值")+ADMINPAY("后台通过充值");
						payTotal = payTotal.add(fundDetail.getMoney());
					} else if (FundDetailType.SUBSCRIPTION.equals(fundDetailType)
							|| FundDetailType.BAODI.equals(fundDetailType)
							|| FundDetailType.CHASE.equals(fundDetailType)) {
						// /投注总金额=SUBSCRIPTION("认购")+BAODI("保底")+CHASE("追号")-CANCEL_SUBSCRIPTION("撤销认购")
						// /-CANCEL_BAODI("撤销保底")-CANCEL_SCHEME("方案撤单")-REFUNDMENT_SCHEME("方案退款")-STOP_CHASE("停止追号"),
						betTotal = betTotal.add(fundDetail.getMoney());
					} else if (FundDetailType.CANCEL_SUBSCRIPTION.equals(fundDetailType)
							|| FundDetailType.CANCEL_BAODI.equals(fundDetailType)
							|| FundDetailType.CANCEL_SCHEME.equals(fundDetailType)
							|| FundDetailType.REFUNDMENT_SCHEME.equals(fundDetailType)
							|| FundDetailType.STOP_CHASE.equals(fundDetailType)) {
						// /投注总金额=SUBSCRIPTION("认购")+BAODI("保底")+CHASE("追号")-CANCEL_SUBSCRIPTION("撤销认购")
						// /-CANCEL_BAODI("撤销保底")-CANCEL_SCHEME("方案撤单")-REFUNDMENT_SCHEME("方案退款")-STOP_CHASE("停止追号"),
						betTotal = betTotal.subtract(fundDetail.getMoney());
					} else if (FundDetailType.SCHEME_COMMISSION.equals(fundDetailType)
							|| FundDetailType.SCHEME_BONUS.equals(fundDetailType)) {
						// /中奖总金额=SCHEME_COMMISSION("方案佣金")+SCHEME_BONUS("奖金分红")
						bounsTotal = bounsTotal.add(fundDetail.getMoney());
					} else if (FundDetailType.DRAWING.equals(fundDetailType)) {
						// /提款总金额=DRAWING("用户提款")-DRAWINGFAIL("提款失败返还资金")
						drawingTotal = drawingTotal.add(fundDetail.getMoney());
					} else if (FundDetailType.DRAWINGFAIL.equals(fundDetailType)) {
						// /提款总金额=DRAWING("用户提款")-DRAWINGFAIL("提款失败返还资金")
						drawingTotal = drawingTotal.subtract(fundDetail.getMoney());
					} else if (FundDetailType.ADMIN_OPR.equals(fundDetailType)) {
						// 后台操作资金
						if (null != fundDetail.getMode()) {
							if (FundMode.IN.equals(fundDetail.getMode())) {
								// 添加
								adminInTotal = adminInTotal.add(fundDetail.getMoney());
							} else if (FundMode.OUT.equals(fundDetail.getMode())) {
								// /扣除
								adminOutTotal = adminOutTotal.add(fundDetail.getMoney());
							}
						}
					}
				}

			}
			return "fund_balance";
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage(e.getMessage());
			return "count_fund";
		}
	}

	private void getFundList() throws WebDataException {
		if (null == dateStar)
			throw new WebDataException("开始时间不能为空！");
		if (null == dateEnd)
			throw new WebDataException("结束时间不能为空！");
		String dateStr0_00 = DateUtil.getFormatDate("yyyy-MM-dd", new Date());
		Date date0_00 = DateUtil.strToDate(dateStr0_00, "yyyy-MM-dd");
		if (date0_00.getTime() <= dateEnd.getTime())
			throw new WebDataException("结束时间不能大于等于当前天！");
		if (dateEnd.getTime() <= dateStar.getTime())
			throw new WebDataException("结束时间不能少于开始时间！");
		if (dateEnd.getTime() - dateStar.getTime() > dateLimit)
			throw new WebDataException("结束时间减去开始时间不能大于31天！");
		DetachedCriteria criteria = DetachedCriteria.forClass(FundDetail.class, "m");
		criteria.add(Restrictions.ge("m.createTime", dateStar));
		criteria.add(Restrictions.le("m.createTime", dateEnd));
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.groupProperty("m.mode"), "mode");
		prop.add(Projections.groupProperty("m.type"), "type");
		prop.add(Projections.groupProperty("m.userId"), "userId");
		prop.add(Projections.groupProperty("m.userName"), "userName");
		prop.add(Projections.sum("m.money"), "money");
		prop.add(Projections.property("m.mode"), "mode");
		prop.add(Projections.property("m.type"), "type");
		prop.add(Projections.property("m.userId"), "userId");
		prop.add(Projections.property("m.userName"), "userName");
		criteria.setProjection(prop);
		criteria.setResultTransformer(Transformers.aliasToBean(FundDetail.class));
		resultList = queryService.findByDetachedCriteria(criteria);
	}

	public String userSubscription() throws WebDataException {
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向
			return "user_subscription";
		}
		try {
			getSubscriptionList();
			return "user_subscription";
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage(e.getMessage());
			return "user_subscription";
		}
	}

	/**
	 * 普通彩票统计
	 */
	private SubscriptionForm subscriptionForm;

	private void getSubscriptionList() throws WebDataException {
		if (null == dateStar)
			throw new WebDataException("开始时间不能为空！");
		String dateStr0_00 = DateUtil.getFormatDate("yyyy-MM-dd", new Date());
		Date date0_00 = DateUtil.strToDate(dateStr0_00, "yyyy-MM-dd");
		if (null == dateEnd)
			dateEnd = date0_00;
		if (dateEnd.getTime() <= dateStar.getTime())
			throw new WebDataException("结束时间不能晚于开始时间！");		
		if (dateEnd.getTime() - dateStar.getTime() > dateLimit)
			throw new WebDataException("查询日期间隔不能大于31天！");

		DetachedCriteria criteria = DetachedCriteria.forClass(
				SaleAnalyse.class, "m");
		if (null != subscriptionForm) {
			Lottery lottery = subscriptionForm.getLotteryType();
			if (lottery!=null) {
				if(lottery==Lottery.SFZC){
					criteria.add(Restrictions.or(Restrictions.eq("m.lottery", Lottery.SFZC),Restrictions.or(Restrictions.eq("m.lottery", Lottery.SCZC),Restrictions.eq("m.lottery", Lottery.LCZC))));
				}else{
					criteria.add(Restrictions.eq("m.lottery", lottery));
				}
			}
			if (StringUtils.isNotBlank(subscriptionForm.getUserName())) {
				criteria.add(Restrictions.eq("m.userName", subscriptionForm.getUserName().trim()));
			}
		}
		criteria.add(Restrictions.ge("m.createTime", dateStar));
		criteria.add(Restrictions.le("m.createTime", dateEnd));
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.groupProperty("m.userName"),"userName");
		prop.add(Projections.groupProperty("m.lottery"),"lottery");
		prop.add(Projections.sum("m.subscriptionCost"),"subscriptionCost");
		prop.add(Projections.sum("m.subscriptionSuccessCost"),"subscriptionSuccessCost");
		prop.add(Projections.sum("m.subscriptionSuccessWonPrize"),"subscriptionSuccessWonPrize");
		prop.add(Projections.property("m.userName"),"userName");
		prop.add(Projections.property("m.lottery"),"lottery");
		criteria.setProjection(prop);
		criteria.addOrder(Order.asc("m.lottery"));
		criteria.setResultTransformer(Transformers
				.aliasToBean(SaleAnalyse.class));
		saleAnalyseResultList = queryService.findByDetachedCriteria(criteria);
	}
	// 支付宝延迟到账处理

	public String epay() {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return "user-list";
		}
		return "user-epaycheck";
	}

	public String epayCheck() throws WebDataException {
		checkRepeatRequest();

		String order_no = Struts2Utils.getParameter("order_no");
		if (null == order_no)
			throw new WebDataException("订单号不能为空！");
		String trade_no = Struts2Utils.getParameter("trade_no");
		if (null == trade_no)
			throw new WebDataException("交易号不能为空！");
		String total_fee = Struts2Utils.getParameter("total_fee");
		if (null == total_fee)
			throw new WebDataException("交易金额不能为空！");
		synchronized (Constant.Epay) {
			//充值
		}
		return epay();
	}
	
	public String ticketDetail(){
		try{
			if (dateStar == null)
				throw new WebDataException("开始时间不能为空！");
			String dateStr0_00 = DateUtil.getFormatDate("yyyy-MM-dd", new Date());
			Date date0_00 = DateUtil.strToDate(dateStr0_00, "yyyy-MM-dd");
			if (dateEnd == null)
				dateEnd = date0_00;
			if (dateEnd.getTime() <= dateStar.getTime())
				throw new WebDataException("结束时间不能晚于开始时间！");
			if (dateEnd.getTime() - dateStar.getTime() > dateLimit)
				throw new WebDataException("查询日期间隔不能大于31天！");

			DetachedCriteria criteria = DetachedCriteria.forClass(Ticket.class, "m");
			if (lotteryType!=null) {
				if(lotteryType==Lottery.SFZC){
					criteria.add(Restrictions.or(Restrictions.eq("m.lotteryType", Lottery.SFZC),Restrictions.or(Restrictions.eq("m.lotteryType", Lottery.SCZC),Restrictions.eq("m.lotteryType", Lottery.LCZC))));
				}else{
					criteria.add(Restrictions.eq("m.lotteryType", lotteryType));
				}
			}
			if(ticketSupporter!=null){
				criteria.add(Restrictions.eq("m.ticketSupporter", ticketSupporter));
			}
			if(!StringUtils.isEmpty(this.userName)){
				if(!this.userName.equals("网站")){
					User user = userManager.getUserBy(this.userName);
					criteria.add(Restrictions.eq("m.sponsorId", user.getId()));
				}
			}
			criteria.add(Restrictions.ge("m.stateModifyTime", dateStar));
			criteria.add(Restrictions.le("m.stateModifyTime", dateEnd));
			criteria.add(Restrictions.eq("m.stateCode", "1"));
			ProjectionList prop = Projections.projectionList();
			prop.add(Projections.groupProperty("m.ticketSupporter"),"ticketSupporter");
			prop.add(Projections.groupProperty("m.lotteryType"),"lotteryType");
			prop.add(Projections.sum("m.schemeCost"),"schemeCost");
			prop.add(Projections.sum("m.totalPrizeAfterTax"),"totalPrizeAfterTax");
			prop.add(Projections.property("m.ticketSupporter"),"ticketSupporter");
			prop.add(Projections.property("m.lotteryType"),"lotteryType");
			criteria.setProjection(prop);
			criteria.addOrder(Order.asc("m.lotteryType"));
			criteria.setResultTransformer(Transformers.aliasToBean(Ticket.class));
			List<Ticket> ticketResult = queryServiceRMIOfTicket.findByDetachedCriteria(criteria);
			if(this.userName.equals("网站")){
				for (Ticket ticket : ticketResult) {
					User user = this.userManager.getUser(ticket.getSponsorId());
					if(!user.getUserWay().getName().equals("接票")){
						ticketResultList.add(ticket);
					}
				}
			}else{
				ticketResultList=ticketResult;
			}
		}catch(Exception e){
			this.addActionMessage(e.getMessage());
		}
		return "ticket-detail";
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOprMoney() {
		return oprMoney;
	}

	public void setOprMoney(String oprMoney) {
		this.oprMoney = oprMoney;
	}

	public Short getOprType() {
		return oprType;
	}

	public void setOprType(Short oprType) {
		this.oprType = oprType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public DrawingOrder getDrawingOrder() {
		return drawingOrder;
	}

	public void setDrawingOrder(DrawingOrder drawingOrder) {
		this.drawingOrder = drawingOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FundDetailType getFundType() {
		return fundType;
	}

	public void setFundType(FundDetailType fundType) {
		this.fundType = fundType;
	}

	public DrawingOrderState getDrawingState() {
		return drawingState;
	}

	public void setDrawingState(DrawingOrderState drawingState) {
		this.drawingState = drawingState;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(int timeFrame) {
		this.timeFrame = timeFrame;
	}

	public Boolean isLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the ifcheck
	 */
	public Byte getIfcheck() {
		return ifcheck;
	}

	/**
	 * @param ifcheck
	 *            the ifcheck to set
	 */
	public void setIfcheck(Byte ifcheck) {
		this.ifcheck = ifcheck;
	}

	/**
	 * @return the dateStar
	 */
	public Date getDateStar() {
		return dateStar;
	}

	/**
	 * @param dateStar
	 *            the dateStar to set
	 */
	public void setDateStar(Date dateStar) {
		this.dateStar = dateStar;
	}

	/**
	 * @return the dateEnd
	 */
	public Date getDateEnd() {
		return dateEnd;
	}

	/**
	 * @param dateEnd
	 *            the dateEnd to set
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * @return the payTotal
	 */
	public BigDecimal getPayTotal() {
		return payTotal;
	}

	/**
	 * @param payTotal
	 *            the payTotal to set
	 */
	public void setPayTotal(BigDecimal payTotal) {
		this.payTotal = payTotal;
	}

	/**
	 * @return the betTotal
	 */
	public BigDecimal getBetTotal() {
		return betTotal;
	}

	/**
	 * @param betTotal
	 *            the betTotal to set
	 */
	public void setBetTotal(BigDecimal betTotal) {
		this.betTotal = betTotal;
	}

	/**
	 * @return the bounsTotal
	 */
	public BigDecimal getBounsTotal() {
		return bounsTotal;
	}

	/**
	 * @param bounsTotal
	 *            the bounsTotal to set
	 */
	public void setBounsTotal(BigDecimal bounsTotal) {
		this.bounsTotal = bounsTotal;
	}

	/**
	 * @return the drawingTotal
	 */
	public BigDecimal getDrawingTotal() {
		return drawingTotal;
	}

	/**
	 * @param drawingTotal
	 *            the drawingTotal to set
	 */
	public void setDrawingTotal(BigDecimal drawingTotal) {
		this.drawingTotal = drawingTotal;
	}

	/**
	 * @return the adminInTotal
	 */
	public BigDecimal getAdminInTotal() {
		return adminInTotal;
	}

	/**
	 * @param adminInTotal
	 *            the adminInTotal to set
	 */
	public void setAdminInTotal(BigDecimal adminInTotal) {
		this.adminInTotal = adminInTotal;
	}

	/**
	 * @return the adminOutTotal
	 */
	public BigDecimal getAdminOutTotal() {
		return adminOutTotal;
	}

	/**
	 * @param adminOutTotal
	 *            the adminOutTotal to set
	 */
	public void setAdminOutTotal(BigDecimal adminOutTotal) {
		this.adminOutTotal = adminOutTotal;
	}

	/**
	 * @return the subscriptionForm
	 */
	public SubscriptionForm getSubscriptionForm() {
		return subscriptionForm;
	}

	/**
	 * @param subscriptionForm
	 *            the subscriptionForm to set
	 */
	public void setSubscriptionForm(SubscriptionForm subscriptionForm) {
		this.subscriptionForm = subscriptionForm;
	}



	/**
	 * @return the islocked
	 */
	public String getIslocked() {
		return islocked;
	}

	/**
	 * @param islocked
	 *            the islocked to set
	 */
	public void setIslocked(String islocked) {
		this.islocked = islocked;
	}

	/**
	 * @return the locked
	 */
	public Boolean getLocked() {
		return locked;
	}

	public List<FundDetail> getResultList() {
		return resultList;
	}

	public void setResultList(List<FundDetail> resultList) {
		this.resultList = resultList;
	}

	public List<FundDetail> getInResultList() {
		return inResultList;
	}

	public void setInResultList(List<FundDetail> inResultList) {
		this.inResultList = inResultList;
	}

	public List<FundDetail> getOutResultList() {
		return outResultList;
	}

	public void setOutResultList(List<FundDetail> outResultList) {
		this.outResultList = outResultList;
	}

	public List<SaleAnalyse> getSaleAnalyseResultList() {
		return saleAnalyseResultList;
	}

	public void setSaleAnalyseResultList(List<SaleAnalyse> saleAnalyseResultList) {
		this.saleAnalyseResultList = saleAnalyseResultList;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public List<Ticket> getTicketResultList() {
		return ticketResultList;
	}

	public TicketSupporter getTicketSupporter() {
		return ticketSupporter;
	}

	public void setTicketSupporter(TicketSupporter ticketSupporter) {
		this.ticketSupporter = ticketSupporter;
	}
	
	
}
