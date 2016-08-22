package com.cai310.lottery.web.controller.admin.user;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.DrawingOrderState;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PayWay;
import com.cai310.lottery.common.PopuType;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.DrawingOrder;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.Ipsorder;
import com.cai310.lottery.entity.user.Media;
import com.cai310.lottery.entity.user.PhonePopu;
import com.cai310.lottery.entity.user.Popu;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.UserReport;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.orm.Page;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.MD5;
import com.cai310.utils.Struts2Utils;
import com.cai310.utils.alipay.services.AlipayService;
import com.google.common.collect.Maps;
import com.yeepay.PaymentForOnlineService;
import com.yeepay.QueryResult;

@Result(name = "save", type = "redirectAction", location="user!acceptTicketUserList")
public class UserController extends AdminBaseController {
	private static final long serialVersionUID = -7128890110025210382L;

	@Autowired
	UserEntityManager userManager;
	@Autowired
	private QueryService queryService;	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	protected TicketThenEntityManager ticketThenEntityManager;
	private Page<User> page = new Page<User>(40);
	private Pagination pagination = new Pagination(20);
	private User user;
	private DrawingOrder drawingOrder;
	private Long id;
	private User entity;
	private TicketPlatformInfo ticketPlatform;
	private String userName;
	private String oprMoney;
	private Short oprType;
	private String remark;
	private FundDetailType fundType;
	private DrawingOrderState drawingState;
	private int timeFrame;
	private String locked;
	private String islocked;
	private String idCard;
	private String email;
	private String phoneNo;
	private String adminMsg;
	private List<String> checkedLotteryIds; // 页面中钩选的彩种id列表

	public String getAdminMsg() {
		return adminMsg;
	}

	public void setAdminMsg(String adminMsg) {
		this.adminMsg = adminMsg;
	}
	public String userReportList() throws UnsupportedEncodingException{
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return "media-list";
		}
		XDetachedCriteria criteria = new XDetachedCriteria(UserReport.class, "m");
		criteria.addOrder(Order.desc("m.id"));
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "userReport-list";
	}
	private PopuType popuType;
	public String popuList() throws UnsupportedEncodingException{
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return "media-list";
		}
		XDetachedCriteria criteria = new XDetachedCriteria(Popu.class, "m");
		if (null!=popuType) {
			criteria.add(Restrictions.like("m.popuType", popuType));
		}
		criteria.addOrder(Order.desc("m.id"));
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "popu-list";
	}
	public String phonePopuList() throws UnsupportedEncodingException{
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return "media-list";
		}
		XDetachedCriteria criteria = new XDetachedCriteria(PhonePopu.class, "m");
		criteria.addOrder(Order.desc("m.id"));
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "phonePopu-list";
	}
    /**
     * 媒体列表
     */
	private String mediaName;
	public String mediaList() throws UnsupportedEncodingException{
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return "media-list";
		}
		XDetachedCriteria criteria = new XDetachedCriteria(Media.class, "m");
		if (StringUtils.isNotBlank(mediaName)) {
			this.setMediaName(new String(mediaName.getBytes("ISO8859-1"),"UTF-8"));
			criteria.add(Restrictions.like("m.name", "%"+mediaName+"%"));
		}
		criteria.addOrder(Order.desc("m.id"));
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "media-list";
	}
	/**
    * 媒体保存
	 * @throws UnsupportedEncodingException 
    */
	public String saveMedia() throws UnsupportedEncodingException{
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return mediaList();
		}
		String contact = Struts2Utils.getParameter("contact");
		if(StringUtils.isBlank(mediaName)){
			addActionError("媒体名称为空！");
			return mediaList();
		}
		Media media = userManager.getMediaBy(mediaName);
		if(null!=media){
			addActionError("媒体名称已经存在。请修改！");
			return mediaList();
		}
		media = new Media();
		media.setContact(contact);
		media.setName(mediaName);
		this.setMediaName(null);
		userManager.saveMedia(media);
		addActionError("新建媒体成功！");
		return mediaList();
	}
	/**
	 * 用户列表
	 * 
	 * @return
	 */
	private List<Media> mediaList;
	public String userList() {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return "user-list";
		}
		XDetachedCriteria mediaCriteria = new XDetachedCriteria(Media.class, "m");
		mediaList = queryService.findByDetachedCriteria(mediaCriteria);
		
		XDetachedCriteria criteria = new XDetachedCriteria(User.class, "m");
		
		ProjectionList propList = Projections.projectionList();
		propList.add(Projections.property("id"), "id");
		propList.add(Projections.property("userName"), "userName");
		propList.add(Projections.property("remainMoney"), "remainMoney");
		propList.add(Projections.property("consumptionMoney"), "consumptionMoney");
		propList.add(Projections.property("createTime"), "createTime");
		propList.add(Projections.property("locked"), "locked");
		propList.add(Projections.property("mid"), "mid");
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
	 * 新增用户页面
	 * @return
	 */
	private List<TicketPlatformInfo> ticketPlatformInfo;
	public String acceptTicketUserList() throws Exception {
		XDetachedCriteria criteria=new XDetachedCriteria(TicketPlatformInfo.class,"t");
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "acceptTicketUser";
	}
	
	public String searchUser() throws Exception{
		XDetachedCriteria criteria = new XDetachedCriteria(TicketPlatformInfo.class, "t");
		String userName = "";
		if (StringUtils.isNotBlank(Struts2Utils.getParameter("userName"))) {
			userName = Struts2Utils.getParameter("userName");
			criteria.add(Restrictions.like("t.platformName", "%" + userName.trim() + "%"));
		}
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "acceptTicketUser";
	}
	
	public String save() throws Exception{
		if (Long.valueOf("-1").equals(entity.getId())) {
			User userTemp = userManager.getUserBy(entity.getUserName());
			if (userTemp != null
					&& userTemp.getUserName().equals(entity.getUserName().toLowerCase())) {
				this.addActionMessage("注册用户名重复.请更改.");
				return "save";
			}
			if(StringUtils.isBlank(entity.getUserName())){
				this.addActionMessage("用户名不能为空");
				return "save";
			}
			// 新增
			String limitIp=Struts2Utils.getParameter("entity.ipLimit");
			entity.setId(null);
			entity.setUserName(entity.getUserName());
			entity.setPassword(MD5.md5(entity.getPassword()));
			entity.setCreateTime(new Date());
			entity.setMid(0L);
			entity.setUserWay(UserWay.TICKET);
			entity.setLocked(User.NO_LOCK_STATUS);
			entity.setRemainMoney(BigDecimal.ZERO);
			UserInfo info = new UserInfo();
			BankInfo bank = new BankInfo();
			info.setMobile(entity.getInfo().getMobile());
			entity.setBank(bank);
			entity.setInfo(info);
			entity = userManager.saveUser(entity, info, bank);
			TicketPlatformInfo platformInfo=new TicketPlatformInfo();
			platformInfo.setUserId(entity.getId());
			platformInfo.setPassword(MD5.md5(entity.getPassword()));
			platformInfo.setCreateTime(new Date());
			platformInfo.setLocked(User.NO_LOCK_STATUS);
			platformInfo.setRemainMoney(BigDecimal.ZERO);
			platformInfo.setConsumptionMoney(BigDecimal.ZERO);
			platformInfo.setLimitIp(limitIp);
			platformInfo.setPlatformName(entity.getUserName());
			platformInfo.setOpenLotterys(listToString(checkedLotteryIds));
			ticketPlatform=userManager.saveTicketPlatformInfo(platformInfo);
			return "save";
		} else {
			// 修改
			User userTemp = userManager.getUser(entity.getId());
			TicketPlatformInfo ticketPlatform=userManager.getTicketPlatformInfo(entity.getId());
			if (null == userTemp || null == userTemp.getId()) {
				this.addActionMessage("用户实体为空");
				return "save";
			}
			String mobile=Struts2Utils.getParameter("entity.info.mobile");
			String ipLimit=Struts2Utils.getParameter("entity.ipLimit");
			userTemp.setLocked(entity.isLocked());
			userTemp.getInfo().setMobile(mobile);
			ticketPlatform.setLimitIp(ipLimit);
			ticketPlatform.setOpenLotterys(listToString(checkedLotteryIds));
			userManager.saveTicketPlatformInfo(ticketPlatform);
			userManager.saveUser(userTemp,userTemp.getInfo(),userTemp.getBank());
			return "save";
		}
	}
	
	/**
	 * @Description:把list转换为一个用逗号分隔的字符串
	 */
	private String listToString(List list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + ",");
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}

	public String edit() throws Exception {
		XDetachedCriteria criteria=new XDetachedCriteria(TicketPlatformInfo.class,"t");
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		entity = userManager.getUser(entity.getId());
		ticketPlatform =userManager.getTicketPlatformInfo(entity.getId());
		return "acceptTicketUser";
	}	
	
	/**
	 * 检查用户名是否唯一
	 * 
	 * @return
	 */
	public String checkUserRegAble() {
		String userName = this.getUserName();
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
		XDetachedCriteria criteria = new XDetachedCriteria(User.class, "m");
		criteria.add(Restrictions.eq("m.userWay", UserWay.AGENT));
		List<User> agentList = queryService.findByDetachedCriteria(criteria);
		Struts2Utils.getRequest().setAttribute("agentList", agentList);
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
	public String oprUser() {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			this.addActionMessage("权限不足！");
			return userInfo();
		}
		if (StringUtils.isBlank(this.getIslocked())) {
			this.addActionMessage("选择是否可用错误");
			return userInfo();
		}
		if (null == this.getId()) {
			this.addActionMessage("用户Id错误。请联系管理员");
			return userInfo();
		}

		user = userManager.getUser(this.getId());
		String userWayStr = Struts2Utils.getParameter("userWay");
		UserWay userWay = UserWay.getUserWayByName(userWayStr);
		Double rebate = null;
		if(null!=userWay){
			user.setUserWay(userWay);
			if(UserWay.TICKET.equals(userWay)){
				String ip = Struts2Utils.getParameter("ip");
			}
			if(UserWay.AGENT.equals(userWay)){
				String rebateStr = Struts2Utils.getParameter("rebate");
				String agentUser = Struts2Utils.getParameter("agentUser");
				if(StringUtils.isNotBlank(agentUser)){
					user.setAgentId(Long.valueOf(agentUser));
				}
				rebate=Double.valueOf(rebateStr);
			}
		}
		user.setLocked(Boolean.valueOf(this.getIslocked()));
		userManager.saveUser(user, adminUser,rebate);
		this.addActionMessage("操作成功");
		return userInfo();
	}

	/**
	 * 资金明细
	 * 
	 * @return
	 */
	public String fundetailList() {
		XDetachedCriteria criteria = new XDetachedCriteria(FundDetail.class, "m");
		if (StringUtils.isNotBlank(this.getUserName())) {

			criteria.add(Restrictions.like("m.userName", "%" + this.getUserName().trim() + "%"));
		}
		if (fundType != null)
			criteria.add(Restrictions.eq("m.type", fundType));

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
		criteria.add(Restrictions.ge("m.createTime", c.getTime()));

		criteria.addOrder(Order.desc("m.id"));

		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
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
	 * 得到需要审核的提款订单
	 * 
	 * @return
	 */
	public String checkDrawingList() {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			this.addActionMessage("权限不足！");
			return "check-drawing-list";
		}
		XDetachedCriteria criteria = new XDetachedCriteria(DrawingOrder.class, "m");
		if (StringUtils.isNotBlank(this.getUserName())) {

			criteria.add(Restrictions.like("m.userName", this.getUserName().trim()));
		}
		criteria.add(Restrictions.eq("m.state", DrawingOrderState.CHECKING));

		criteria.addOrder(Order.desc("m.id"));

		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);

		return "check-drawing-list";
	}

	/**
	 * 客服确认订单交给财务
	 * 
	 * @return
	 */
	public String checkDrawing() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				this.addActionMessage("权限不足！");
				return checkDrawingList();
			}
			if (null == this || null == this.getId()) {
				this.addActionMessage("退款订单为空！");
				return checkDrawingList();
			}
			drawingOrder = userManager.getDrawingOrder(this.getId());
			if (null == drawingOrder || null == drawingOrder.getId()) {
				this.addActionMessage("退款订单为空！");
				return checkDrawingList();
			}
			synchronized (Constant.DRAWING) {
				if (!DrawingOrderState.PASSCHECK.equals(drawingOrder.getState())) {
					drawingOrder.setState(DrawingOrderState.PASSCHECK);
					drawingOrder.setChecktime(new Date());
					drawingOrder.setCheckUserid(adminUser.getId());
					drawingOrder.setCheckUsername(adminUser.getUsername());
					userManager.saveAdminDrawingOrder(adminUser, drawingOrder);
				}
			}
			this.addActionMessage("确认退款订单成功！");
			return checkDrawingList();
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage("确认退款订单错误！");
			return checkDrawingList();
		}
	}

	/**
	 * 删除订单退还资金
	 * 
	 * @return
	 */
	public String backDrawing() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				this.addActionMessage("权限不足！");
				return checkDrawingList();
			}
			if (null == this || null == this.getId()) {
				this.addActionMessage("退款订单为空！");
				return checkDrawingList();
			}
			drawingOrder = userManager.getDrawingOrder(this.getId());
			if (null == drawingOrder || null == drawingOrder.getId()) {
				this.addActionMessage("退款订单为空！");
				return checkDrawingList();
			}

			if (drawingOrder.getState() != DrawingOrderState.FAILCHECK) {
				drawingOrder=userManager.saveDrawingOrder(drawingOrder);
				userManager.deleteDrawingOrder(adminUser, drawingOrder);
				this.addActionMessage("退还退款订单成功");
			} else {
				this.addActionMessage("退还退款订单错误");
			}

			return checkDrawingList();
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage("退还退款订单错误！");
			return checkDrawingList();
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
			if (ifcheck != null){
				///06-27 xxxx-c 修改bug ifcheck是boolean类型。页面传过来的数值是1 0，要转换一下
				criteria.add(Restrictions.eq("m.ifcheck", Byte.valueOf("1").equals(ifcheck)?true:false));
			}
				

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
            case 4:
                c.add(Calendar.DAY_OF_MONTH, -1);// 1天前
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
			if(PayWay.ALIPAY.equals(ipsorder.getPayWay())){
				return alipaySingleTradeQuery(ipsorder);
			}else if(PayWay.YEEPAY.equals(ipsorder.getPayWay())){
				return yeepaySingleTradeQuery(ipsorder);
			}else if(PayWay.ALIPAY_PHONE.equals(ipsorder.getPayWay())){
				return alipayPhoneTradeQuery(ipsorder);
			}
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
	public String alipayPhoneTradeQuery(Ipsorder ipsorder) throws WebDataException {
//		String orderNo = ""+ipsorder.getId();
//		String paygateway = "https://www.alipay.com/cooperate/gateway.do?"; // 支付接口(不可修改)
//		String service = "single_trade_query";// 支付宝查询服务--单笔查询服务(不可修改)
//		String sign_type = "MD5";// 加密机制(不可修改)
//		String input_charset = AlipayConfig.charSet; // 页面编码(不可修改)
//		// partner和key提取方法：登陆签约支付宝账户--->点击“商家服务”就可以看到
//		String partner = AlipayConstant.PARTNER; // 支付宝合作伙伴id (账户内提取)
//		String key = AlipayConstant.RSA_ALIPAY_PUBLIC; // 支付宝安全校验码(账户内提取)
//
//		String ItemUrl = Payment.CreateUrl(paygateway, service, partner, sign_type, orderNo, key, input_charset);
//
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		SAXReader reader = new SAXReader();
//		Document doc = null;
//		try {
//			doc = reader.read(new URL(ItemUrl).openStream());
//		} catch (Exception e) {
//			result.put("msg", "网络错误!");
//			result.put("success", false);
//			Struts2Utils.renderJson(result);
//			return null;
//		}
//		List<Node> list = doc.selectNodes("//response/trade/*");
//		Map<String, String> response = new HashMap<String, String>();
//		for (Node node : list) {
//			response.put(node.getName(), node.getText());
//		}
//		String out_trade_no = response.get("out_trade_no");
//		String total_fee = response.get("total_fee");
//		String trade_no = response.get("trade_no");
//		String trade_status = response.get("trade_status");
//		if (StringUtils.isBlank(total_fee) || StringUtils.isBlank(trade_no)) {
//			result.put("msg", "该订单还未交易完成.....");
//			result.put("success", false);
//			Struts2Utils.renderJson(result);
//			return null;
//		}
//		if (!"TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
//			result.put("msg", "该订单充值不成功,请稍候操作!");
//			result.put("success", false);
//			Struts2Utils.renderJson(result);
//			return null;
//		}
//		if (!orderNo.equals(out_trade_no)) {
//			result.put("msg", "该订单号不正确!");
//			result.put("success", false);
//			Struts2Utils.renderJson(result);
//			return null;
//		}
//
//		synchronized (Constant.Epay) {
//			ipsorder = userManager.getIpsorder(Long.valueOf(out_trade_no));
//			if(null==ipsorder){
//				result.put("msg", "该订单不正确!");
//				result.put("success", false);
//				Struts2Utils.renderJson(result);
//				return null;
//			}
//			if ("TRADE_FINISHED".equals(trade_status)
//					|| "TRADE_SUCCESS".equals(trade_status)) {
//				// 表示交易成功（TRADE_FINISHED/TRADE_SUCCESS）
//				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
//					// 为了保证不被重复调用，或重复执行数据库更新程序，请判断该笔交易状态是否是订单未处理状态
//					if(StringUtils.isBlank(out_trade_no)){
//						result.put("msg", "订单号为空!");
//						result.put("success", false);
//						Struts2Utils.renderJson(result);
//						return null;
//					}
//					User user = userManager.getUser(ipsorder.getUserid());
//					if(null==user){
//						result.put("msg", "找不到用户!");
//						result.put("success", false);
//						Struts2Utils.renderJson(result);
//						return null;
//					}
//					synchronized (Constant.Epay) {
//						try{
//							userManager.confirmIps(ipsorder, user, null);
//							result.put("msg", "该订单已经充值成功!");
//							result.put("success", true);
//							Struts2Utils.renderJson(result);
//							return null;
//						}catch(ServiceException e){
//							result.put("msg", e.getMessage());
//							result.put("success", false);
//							Struts2Utils.renderJson(result);
//							return null;
//						}catch(Exception e){
//							result.put("msg", "支付发生错误");
//							result.put("success", false);
//							Struts2Utils.renderJson(result);
//							return null;
//						}
//					}
//				} else {
//					///失败转发
//					result.put("msg", "交易失败.");
//					result.put("success", false);
//					Struts2Utils.renderJson(result);
//					return null;
//				}
//			}
//		}
//		result.put("msg", "充值失败!");
//		result.put("success", false);
//		Struts2Utils.renderJson(result);
		return null;
	}
	private String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}

	public String alipaySingleTradeQuery(Ipsorder ipsorder)
			throws WebDataException {
		if (!isAjaxRequest()) {
			throw new WebDataException("非法URL请求!");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		String orderNo = Struts2Utils.getParameter("id");
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
	    sParaTemp.put("out_trade_no", orderNo);
		        
		Map<String, String> response = Maps.newHashMap();
		try {
			response = AlipayService.single_trade_query_my(sParaTemp);
		} catch (Exception e1) {
			result.put("msg", "充值失败!");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
	    String out_trade_no = response.get("out_trade_no");
		String total_fee = response.get("total_fee");
	    String trade_no = response.get("trade_no");
		String trade_status = response.get("trade_status");
		if (StringUtils.isBlank(total_fee) || StringUtils.isBlank(trade_no)) {
			result.put("msg", "该订单还未交易完成.....");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
		if (!("TRADE_FINISHED".equals(trade_status)
				|| "TRADE_SUCCESS".equals(trade_status))) {
			result.put("msg", "该订单充值不成功,请稍候操作!");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
		if (!orderNo.equals(out_trade_no)) {
			result.put("msg", "该订单号不正确!");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}

		synchronized (Constant.Epay) {
			 ipsorder = userManager.getIpsorder(Long
					.valueOf(out_trade_no));
			if (null == ipsorder) {
				result.put("msg", "该订单不正确!");
				result.put("success", false);
				Struts2Utils.renderJson(result);
				return null;
			}
			if ("TRADE_FINISHED".equals(trade_status)
					|| "TRADE_SUCCESS".equals(trade_status)) {
				// 表示交易成功（TRADE_FINISHED/TRADE_SUCCESS）
				if (trade_status.equals("TRADE_FINISHED")
						|| trade_status.equals("TRADE_SUCCESS")) {
					// 为了保证不被重复调用，或重复执行数据库更新程序，请判断该笔交易状态是否是订单未处理状态
					if (StringUtils.isBlank(out_trade_no)) {
						result.put("msg", "订单号为空!");
						result.put("success", false);
						Struts2Utils.renderJson(result);
						return null;
					}
					User user = userManager.getUser(ipsorder.getUserid());
					if (null == user) {
						result.put("msg", "找不到用户!");
						result.put("success", false);
						Struts2Utils.renderJson(result);
						return null;
					}
					if (Double.valueOf(total_fee)
							- ipsorder.getAmount().doubleValue() != 0) {
						result.put("msg", "处理金额错误!");
						result.put("success", false);
						Struts2Utils.renderJson(result);
						return null;
					}
					synchronized (Constant.Epay) {
						try {
							userManager.confirmIps(ipsorder, user, null);
							result.put("msg", "该订单已经充值成功!");
							result.put("success", true);
							Struts2Utils.renderJson(result);
							return null;
						} catch (ServiceException e) {
							result.put("msg", e.getMessage());
							result.put("success", false);
							Struts2Utils.renderJson(result);
							return null;
						} catch (Exception e) {
							result.put("msg", "支付发生错误");
							result.put("success", false);
							Struts2Utils.renderJson(result);
							return null;
						}
					}
				} else {
					// /失败转发
					result.put("msg", "交易失败.");
					result.put("success", false);
					Struts2Utils.renderJson(result);
					return null;
				}
			}
		}
		result.put("msg", "充值失败!");
		result.put("success", false);
		Struts2Utils.renderJson(result);
		return null;
	}

	public String yeepaySingleTradeQuery(Ipsorder ipsorder) throws WebDataException {
		Map<String, Object> result = new HashMap<String, Object>();
		if(null==ipsorder){
			result.put("msg", "该订单不正确!");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
		String p2_Order= formatString(""+ipsorder.getId());		  			// 商家要查询的交易定单号
		String r1_Code;
		String r6_Order;
		String r3_Amt;
		String r2_TrxId;
		String rb_PayStatus; 
		try {
			QueryResult qr = PaymentForOnlineService.queryByOrder(p2_Order);	// 调用后台外理查询方法
			r1_Code = qr.getR1_Code();
			r6_Order = qr.getR6_Order();
			r3_Amt = qr.getR3_Amt();
			r2_TrxId = qr.getR2_TrxId();
			rb_PayStatus = qr.getRb_PayStatus();
		} catch(Exception e) {
			logger.warn("易宝查询订单失败"+e.getMessage());
			result.put("msg", "易宝查询订单失败!");
			result.put("success", false);
			Struts2Utils.renderJson(result);
			return null;
		}
		synchronized (Constant.Epay) {
			ipsorder = userManager.getIpsorder(ipsorder.getId());
			if(null==ipsorder){
				result.put("msg", "该订单不正确!");
				result.put("success", false);
				Struts2Utils.renderJson(result);
				return null;
			}
			//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
			logger.debug("<br>交易成功!<br>商家订单号:" + r6_Order + "<br>支付金额:" + r3_Amt + "<br>易宝支付交易流水号:" + r2_TrxId);
			if(r1_Code.equals("1")&&StringUtils.isNotBlank(rb_PayStatus)&&"SUCCESS".equals(rb_PayStatus.trim())) {
				// 下面页面输出是测试时观察结果使用
				// 为了保证不被重复调用，或重复执行数据库更新程序，请判断该笔交易状态是否是订单未处理状态
				if(StringUtils.isBlank(r6_Order)){
					result.put("msg", "订单号为空!");
					result.put("success", false);
					Struts2Utils.renderJson(result);
					return null;
				}
				ipsorder = userManager.getIpsorder(Long.valueOf(r6_Order));
				if(null==ipsorder){
					result.put("msg", "找不到订单!");
					result.put("success", false);
					Struts2Utils.renderJson(result);
					return null;
				}
				User user = userManager.getUser(ipsorder.getUserid());
				if(null==user){
					result.put("msg", "找不到用户!");
					result.put("success", false);
					Struts2Utils.renderJson(result);
					return null;
				}
				synchronized (Constant.Epay) {
					try{
						userManager.confirmIps(ipsorder, user, null);
						result.put("msg", "支付成功,请查看您的余额");
						result.put("success",true);
						Struts2Utils.renderJson(result);
						return null;
					}catch(ServiceException e){
						result.put("msg", e.getMessage());
						result.put("success", false);
						Struts2Utils.renderJson(result);
						return null;
					}catch(Exception e){
						result.put("msg", "系统错误!");
						result.put("success", false);
						Struts2Utils.renderJson(result);
						return null;
					}
				}
			}else{
				result.put("msg", "交易失败!");
				result.put("success", false);
				Struts2Utils.renderJson(result);
				return null;
			}
			
		}
	}
	private boolean isNullInfo(User user) {
		// 手机号码 、邮箱为空 //身份证
		if (null != user.getInfo()) {

			if (null == user.getInfo().getIdCard()) {
				return false;
			}
		}
		return true;
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

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public List<Media> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<Media> mediaList) {
		this.mediaList = mediaList;
	}

	public PopuType getPopuType() {
		return popuType;
	}

	public void setPopuType(PopuType popuType) {
		this.popuType = popuType;
	}

	public Page<User> getPage() {
		return page;
	}

	public void setPage(Page<User> page) {
		this.page = page;
	}

	public User getEntity() {
		return entity;
	}

	public void setEntity(User entity) {
		this.entity = entity;
	}

	public List<TicketPlatformInfo> getTicketPlatformInfo() {
		return ticketPlatformInfo;
	}

	public void setTicketPlatformInfo(List<TicketPlatformInfo> ticketPlatformInfo) {
		this.ticketPlatformInfo = ticketPlatformInfo;
	}

	public TicketPlatformInfo getTicketPlatform() {
		return ticketPlatform;
	}

	public void setTicketPlatform(TicketPlatformInfo ticketPlatform) {
		this.ticketPlatform = ticketPlatform;
	}

	public List<String> getCheckedLotteryIds() {
		return checkedLotteryIds;
	}

	public void setCheckedLotteryIds(List<String> checkedLotteryIds) {
		this.checkedLotteryIds = checkedLotteryIds;
	}



}
