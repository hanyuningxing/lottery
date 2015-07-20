package com.cai310.lottery.web.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.DrawingOrderState;
import com.cai310.lottery.common.DrawingWay;
import com.cai310.lottery.common.ExternalNetworkType;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.MessageType;
import com.cai310.lottery.common.PayWay;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.DrawingOrder;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.Ipsorder;
import com.cai310.lottery.entity.user.LuckDetail;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.support.SysExecutorUtils;
import com.cai310.lottery.support.Executable;
import com.cai310.lottery.support.ExecuteException;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.user.alipayUtils.AlipayConstant;
import com.cai310.lottery.web.controller.user.alipayUtils.Rsa;
import com.cai310.lottery.web.controller.user.alipayUtils.TradeVisitor;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.MD5;
import com.cai310.utils.NumUtils;
import com.cai310.utils.Struts2Utils;
import com.cai310.utils.alipay.config.AlipayConfig;
import com.cai310.utils.alipay.services.AlipayService;
import com.cai310.utils.alipay.util.AlipayNotify;
import com.cai310.utils.alipay.util.AlipaySubmit;
import com.google.common.collect.Maps;
import com.yeepay.Configuration;
import com.yeepay.PaymentForOnlineService;
import com.yeepay.QueryResult;

@Result(name = "message", location = "/WEB-INF/content/common/message.ftl")
public class FundController extends UserBaseController {
	private static final long serialVersionUID = -3004288085663395253L;
	private FundDetailType fundType;
	private DrawingOrderState drawingState;
	private int timeFrame;
	private Pagination pagination = new Pagination(20);
	private CommTraceForm commTraceForm;
	private DrawingForm drawingForm;
	private boolean canActivity;
	private String lotteryNo;
	
	private int countPay = 0;
	private int countIn = 0;
	private int sumPay = 0;
	private int sumIn = 0;
	private String ajaxContainer = "";
	@Autowired
	private QueryService queryService;
	@Autowired
	@Qualifier("commonQueryCache")
	private Cache commonQueryCache;
	
	
	public CommTraceForm getCommTraceForm() {
		return commTraceForm;
	}

	public String index() {
		User loginUser = getLoginUser();
		Struts2Utils.setAttribute("loginUser", loginUser);
		return list();
	}

	public String list() {
		return "list";
	}
	
	public String listTable() {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		ajaxContainer = "1";
		Struts2Utils.setAttribute("ajaxContainer", ajaxContainer);
		XDetachedCriteria criteria = new XDetachedCriteria(FundDetail.class, "m");
		criteria.add(Restrictions.eq("m.userId", loginUser.getId()));
	
		String startTimeStr = Struts2Utils.getParameter("startTime");
		String endTimeStr = Struts2Utils.getParameter("endTime");
		Date startTime = null;
		Date endTime = null;
		if(StringUtils.isNotBlank(startTimeStr)) {
			startTime = DateUtil.strToDate(startTimeStr);
			criteria.add(Restrictions.ge("m.createTime", startTime));
		}
		if(StringUtils.isNotBlank(endTimeStr)) {
			endTime = DateUtil.strToDate(endTimeStr);
			criteria.add(Restrictions.le("m.createTime", endTime));
		}
		
		
			
		if (fundType != null)
			criteria.add(Restrictions.eq("m.type", fundType));

		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 0);
		c.add(Calendar.MINUTE, 0);
		c.add(Calendar.SECOND, 0);
		switch (timeFrame) {
		case 0:
			c.add(Calendar.DAY_OF_MONTH, -7);// 7天前
			break;
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
			c.add(Calendar.DAY_OF_MONTH, 0);// 
			break;
		case 5:
			c.add(Calendar.DAY_OF_MONTH, -2);// 2天前
			break;
		case 6:
			c.add(Calendar.DAY_OF_MONTH, -3);// 3天前
			break;
		case 7:
			c.add(Calendar.DAY_OF_MONTH, -4);// 4天前
			break;
		case 8:
			c.add(Calendar.DAY_OF_MONTH, -5);// 5天前
			break;
		case 9:
			c.add(Calendar.DAY_OF_MONTH, -6);// 6天前
			break;
		default:
			c.add(Calendar.DAY_OF_MONTH, -1);// 1天前
		}
			
		if(startTime==null && endTime==null) {
			if(timeFrame != 4) {
				criteria.add(Restrictions.ge("m.createTime", c.getTime()));
			} else {
				Date date = new Date();
				String str = DateUtil.dateToStr(date);					
				str = str.substring(0, 10) + " 00:00";
				date = DateUtil.strToDate(str);
				criteria.add(Restrictions.ge("m.createTime", date));
				
			}
				
		}
			
		criteria.addOrder(Order.desc("m.id"));
			
		List<FundDetail> list = queryService.findByDetachedCriteria(criteria);
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getMode()==FundMode.OUT) {
				countPay++;
				sumPay += list.get(i).getMoney().intValue();
			}
			if(list.get(i).getMode()==FundMode.IN) {
				countIn++;
				sumIn += list.get(i).getMoney().intValue();
			}
		}	
			
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "list_table";
	}

	/**
	 * 提款订单列表
	 */
	public String drawingList() {
	
		return "drawing-list";
	}
	
	public String drawingListTable() {
		User loginUser = getLoginUser();
		if (loginUser == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		
			XDetachedCriteria criteria = new XDetachedCriteria(DrawingOrder.class, "m");
			criteria.add(Restrictions.eq("m.userId", loginUser.getId()));

			if (drawingState != null)
				criteria.add(Restrictions.eq("m.state", drawingState));
			if(timeFrame!=-1){
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
			}
			criteria.addOrder(Order.desc("m.id"));
			pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
			

		return "drawing-list-table";
	}

	/**
	 * 提款准备
	 * 
	 * @return
	 * @throws WebDataException
	 * @throws Exception
	 */

	public String drawingPer() throws WebDataException {
		User user = getLoginUser();
		if (null == user) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		if (user.isLocked()) {
			throw new WebDataException("该账户已被锁定,请联系客服.");
		}

		this.setDrawingForm(new DrawingForm());

		BankInfo bankInfo = user.getBank();
		// 用户默认银行账户不为空
		if (null != bankInfo.getBankCard() && !bankInfo.getBankCard().trim().equals("")) {
			this.getDrawingForm().setBankCard(bankInfo.getBankCard());
			this.getDrawingForm().setUserRealName(user.getInfo().getRealName());
			this.getDrawingForm().setBankName(bankInfo.getBankName());
			this.getDrawingForm().setPartBankName(bankInfo.getPartBankName());
		} else {
			return redirectforward(Boolean.TRUE, "请先绑定银行卡帐号再进行操作!", Constant.BASEPATH
					+ "/user/fund!tkManager.action", "message");
		}

		

		this.getDrawingForm().setResultMoney(user.getRemainMoney());

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);

		this.getDrawingForm().setDefaultAccountRemainMoney(
				user == null ? nf.format(BigDecimal.ZERO) : nf.format(user.getRemainMoney()));
		return "drawing-per";
	}
	
	public String tkManager() throws WebDataException {
		
		User user = getLoginUser();
		if (null == user) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		if (user.isLocked()) {
			throw new WebDataException("该账户已被锁定,请联系客服.");
		}

		this.setDrawingForm(new DrawingForm());

		BankInfo bankInfo = user.getBank();

		if (null != bankInfo.getBankCard()) {
			this.getDrawingForm().setBankCard(bankInfo.getBankCard());
			this.getDrawingForm().setUserRealName(user.getInfo().getRealName());
			this.getDrawingForm().setBankName(bankInfo.getBankName());
			this.getDrawingForm().setPartBankName(bankInfo.getPartBankName());

		}
		return "tkManager";		
	}
	
	public String tkNavigator() {
		return "tkNavigator";
	}
	
	/**
	 * 提款
	 * 
	 * @throws WebDataException
	 */
	public String drawing() throws WebDataException {
		try {
			super.checkRepeatRequest();
		} catch (WebDataException w) {
			return drawingPer();
		}
		User user = getLoginUser();
		if (user == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) { // 转向提款页面
			return drawingPer();
		}
		if (null == this.getDrawingForm()) {
			this.addActionMessage("提款实体为空");
			return forward(false, drawingPer());
		}

		user = userManager.getUser(user.getId());
		this.getDrawingForm().setResultMoney(user.getRemainMoney());
		
		String partBankProvince = this.getDrawingForm().getPartBankProvince();
		String partBankCity = this.getDrawingForm().getPartBankCity();
		String partBankName = this.getDrawingForm().getPartBankName();
		String bankName = this.getDrawingForm().getBankName();
		String bankCard = this.getDrawingForm().getBankCard();

		String drawingMoney = null;
		drawingMoney = this.getDrawingForm().getDrawingMoney();

		BankInfo bankInfo = user.getBank();
		if (null == bankInfo) {
			bankInfo = new BankInfo();
			bankInfo.setBankCard(bankCard);
			bankInfo.setBankName(bankName);
			bankInfo.setPartBankCity(partBankCity);
			bankInfo.setPartBankName(partBankName);
			bankInfo.setPartBankProvince(partBankProvince);
			bankInfo.setUser(user);
			user.setBank(bankInfo);
		}else{
			bankInfo.setBankCard(bankCard);
			bankInfo.setBankName(bankName);
			bankInfo.setPartBankCity(partBankCity);
			bankInfo.setPartBankName(partBankName);
			bankInfo.setPartBankProvince(partBankProvince);
			bankInfo.setUser(user);
			user.setBank(bankInfo);
		}
		if (StringUtils.isBlank(drawingMoney)) {
			this.addActionMessage("请您输入提款金额.");
			return forward(false, drawingPer());
		}
		DetachedCriteria criteria =  DetachedCriteria.forClass(LuckDetail.class,"m");
		criteria.add(Restrictions.eq("m.userId", user.getId()));
		List<LuckDetail> luckDetailList = queryService.findByDetachedCriteria(criteria);
		if(null!=luckDetailList&&!luckDetailList.isEmpty()){
			BigDecimal in = BigDecimal.valueOf(0);
			BigDecimal out = BigDecimal.valueOf(0);
			for (LuckDetail luckDetail : luckDetailList) {
				if(FundMode.IN.equals(luckDetail.getMode())){
					in = in.add(luckDetail.getMoney());
				}
				if(FundMode.OUT.equals(luckDetail.getMode())){
					out = out.add(luckDetail.getMoney());
				}
			}
			if(in.compareTo(out)>0){
				this.addActionMessage("赠送金额不能提现.");
				return forward(false, drawingPer());
			}
		}
		user = userManager.getUser(user.getId());
		if (StringUtils.isBlank(user.getInfo().getRealName())) {
			this.addActionError("该账号真实姓名为空,请先设置帐号，再进行提现操作!");
			return forward(false, drawingPer());
		}
		BigDecimal money = null;
		try {
			money = BigDecimalUtil.valueOf(Double.valueOf(drawingMoney));
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("提取金额格式出错");
			return forward(false, drawingPer());
		}
		if (money.doubleValue() <= Double.valueOf(0)) {
			this.addActionError("提取金额不能少于等于0");
			return forward(false, "drawing-per");
		}
		if (money.doubleValue() > user.getRemainMoney().doubleValue()) {
			this.addActionError("提取金额不能超过余额");
			return forward(false, drawingPer());
		}
		
		synchronized (Constant.DRAWING) {
			DrawingOrder drawingOrder = userManager.oprDrawingOrder(user, money, bankInfo, null);
			if (null == drawingOrder) {
				this.addActionMessage("操作失败");
				return forward(false, drawingPer());
			}
		}
		this.addActionMessage("操作成功，请查看您的提款列表");
		this.drawingForm = null;
		return forward(true, drawingList());
	}

	/**
	 * 提款
	 * 
	 * @throws WebDataException
	 */
	public String drawingWithTheDefaultBank() throws WebDataException {
		try {
			super.checkRepeatRequest();
		} catch (WebDataException w) {
			return drawingPer();
		}
		User user = getLoginUser();
		if (user == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) { // 转向提款页面
			return drawingPer();
		}
	

		user = userManager.getUser(user.getId());
		this.getDrawingForm().setResultMoney(user.getRemainMoney());
		

		String drawingMoney = Struts2Utils.getParameter("drawingMoney");
		String userRealName = Struts2Utils.getParameter("userRealName");
		String payPassword = Struts2Utils.getParameter("payPassword");
		String yanzhengma = Struts2Utils.getParameter("yanzhengma");
		BankInfo bankInfo = user.getBank();
		if(StringUtils.isNotBlank(payPassword)) {
			payPassword = MD5.md5(payPassword.trim()).toUpperCase();
		}
		if (StringUtils.isBlank(drawingMoney)) {
			this.addActionMessage("请您输入提款金额.");
			return forward(false, drawingPer());
		}
		
		if(StringUtils.isBlank(userRealName)) {
			this.addActionMessage("请您输入真实姓名.如未进行身份验证，请先进行验证！");
			return forward(false, drawingPer());
		} else if(!userRealName.trim().equals(user.getInfo().getRealName())) {
			this.addActionMessage("核实真实姓名有误，请输入正确的真实姓名.如未进行身份验证，请先进行验证！");
			return forward(false, drawingPer());
		}
		
		if(user.getNeedPayPassword()!=null && user.getNeedPayPassword()) {
			if(StringUtils.isBlank(payPassword)) {
				this.addActionMessage("请您输入支付密码.");
				return forward(false, drawingPer());
			} else if(!payPassword.trim().equals(user.getPayPassword())) {
				this.addActionMessage("支付密码不正确.");
				return forward(false, drawingPer());
			}
		}
		
		if(user.getNeedValidateWhileTk()!=null && user.getNeedValidateWhileTk()) {
			if(StringUtils.isBlank(yanzhengma)) {
				this.addActionMessage("请您输入短信验证码.");
				return forward(false, drawingPer());
			}
			
			try {
				userManager.userRandomMessage(user.getInfo().getMobile(), MessageType.YANZHENGMA, Integer.parseInt(yanzhengma.trim()));					
			} catch (ServiceException e) {
				this.addActionMessage("验证码不正确.");
				return forward(false, drawingPer());
			}
		}
		
		DetachedCriteria criteria =  DetachedCriteria.forClass(LuckDetail.class,"m");
		criteria.add(Restrictions.eq("m.userId", user.getId()));
		List<LuckDetail> luckDetailList = queryService.findByDetachedCriteria(criteria);
		if(null!=luckDetailList&&!luckDetailList.isEmpty()){
			BigDecimal in = BigDecimal.valueOf(0);
			BigDecimal out = BigDecimal.valueOf(0);
			for (LuckDetail luckDetail : luckDetailList) {
				if(FundMode.IN.equals(luckDetail.getMode())){
					in = in.add(luckDetail.getMoney());
				}
				if(FundMode.OUT.equals(luckDetail.getMode())){
					out = out.add(luckDetail.getMoney());
				}
			}
			if(in.compareTo(out)>0){
				this.addActionMessage("赠送金额不能提现.");
				return forward(false, drawingPer());
			}
		}
		user = userManager.getUser(user.getId());
		if (StringUtils.isBlank(user.getInfo().getRealName())) {
			this.addActionError("该账号真实姓名为空,请先设置帐号，再进行提现操作!");
			return forward(false, drawingPer());
		}
		BigDecimal money = null;
		try {
			money = BigDecimalUtil.valueOf(Double.valueOf(drawingMoney));
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("提取金额格式出错");
			return forward(false, drawingPer());
		}
		if (money.doubleValue() <= Double.valueOf(0)) {
			this.addActionError("提取金额不能少于等于0");
			return forward(false, "drawing-per");
		}
		if (money.doubleValue() > user.getRemainMoney().doubleValue()) {
			this.addActionError("提取金额不能超过余额");
			return forward(false, drawingPer());
		}
		
		synchronized (Constant.DRAWING) {
			DrawingOrder drawingOrder = userManager.oprDrawingOrder(user, money, bankInfo, null);
			if (null == drawingOrder) {
				this.addActionMessage("操作失败");
				return forward(false, drawingPer());
			}
		}
		this.addActionMessage("操作成功，请查看您的提款列表");
		this.drawingForm = null;
		return forward(true, drawingList());
	}

	
	/**
	 * 提款
	 * 
	 * @throws WebDataException
	 * @throws IOException
	 */
	public String payPer() throws WebDataException, IOException {

		User user = getLoginUser();
		if (user == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}
		UserInfo info = user.getInfo();
		if(StringUtils.isBlank(info.getRealName())) {		
			return redirectforward(Boolean.TRUE, "请先完善帐号信息再进行操作!", Constant.BASEPATH
					+ "/user/user!userSafeManager.action", "message");
		}

		

		BankInfo bankInfo = user.getBank();
		UserInfo userInfo = user.getInfo();
		this.setDrawingForm(new DrawingForm());
		// 用户默认银行账户不为空
		if (null != bankInfo) {
			this.getDrawingForm().setBankCard(bankInfo.getBankCard());
			this.getDrawingForm().setBankName(bankInfo.getBankName());
			this.getDrawingForm().setPartBankCity(bankInfo.getPartBankCity());
			this.getDrawingForm().setPartBankName(bankInfo.getPartBankName());
			this.getDrawingForm().setPartBankProvince(bankInfo.getPartBankProvince());
		}
		if (null != userInfo) {
			this.getDrawingForm().setUserRealName(userInfo.getRealName());
		}
		Struts2Utils.getRequest().setAttribute("userName", user.getUserName());
		
		return "pay-per-quick";		
	}

	/**
	 * *功能：发起订单——通讯流水，和支付宝通讯
	 * @throws IOException
	 * @throws WebDataException
	 */
	public String commTraceMarker() throws WebDataException, IOException {
		 String payWay = Struts2Utils.getParameter("payWay");
		 if(StringUtils.isBlank(payWay)){
			 this.addActionError("支付信息不能为空,请修改.");
			 return payPer();
		 }
		 Struts2Utils.setAttribute("payWay", payWay);
		 if(PayWay.YEEPAY.equals(PayWay.values()[Integer.valueOf(payWay)])){
			 return commTraceMarker_yeepay(PayWay.YEEPAY);
		 }else if(PayWay.ALIPAY.equals(PayWay.values()[Integer.valueOf(payWay)])){
			 return commTraceMarker_alipay(PayWay.ALIPAY);
		 }else{
			 this.addActionError("支付信息不能为空,请修改.");
			 return payPer();
		 }
	}
	public String commTraceMarker_alipay(PayWay payWay)
			throws WebDataException, IOException {
		logger.info("====================支付宝 尝试支付 ");
		User user = getLoginUser();
		if (user == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}

		if (null == commTraceForm) {
			this.addActionError("支付信息不能为空,请修改.");
			return payPer();
		}
		if (StringUtils.isBlank(commTraceForm.getAmount())) {
			this.addActionError("支付金额不能为空,请修改.");
			return payPer();
		}
		if (StringUtils.isBlank(commTraceForm.getBankName())) {
			this.addActionError("支付方式不能为空,请修改.");
			return payPer();
		}
		BigDecimal amount = BigDecimal.valueOf(0);
		Ipsorder ipsorder = new Ipsorder();
		try {
			ipsorder.setAmount(BigDecimalUtil.valueOf(Double
					.valueOf(commTraceForm.getAmount())));
			amount = ipsorder.getAmount();
		} catch (Exception e) {
			this.addActionError("支付金额出错");
			return payPer();
		}
		ipsorder.setUserWay(UserWay.WEB);
		ipsorder.setPayWay(payWay);
		ipsorder.setIfcheck(false);
		ipsorder.setUserid(user.getId());
		ipsorder.setUserName(user.getUserName());
		ipsorder.setRealName(null == user.getInfo()
				|| null == user.getInfo().getRealName() ? "" : user.getInfo()
				.getRealName());
		ipsorder.setMemo(null);

		Date now = new Date();
		

		// 提交至支付宝前 操作 生成订单
		ipsorder = userManager.saveIpsorder(ipsorder);
		
		
		////////////////////////////////////请求参数//////////////////////////////////////
		
		//支付类型
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url = AlipayConfig.notify_url; // 
		//需http://格式的完整路径，不能加?id=123这类自定义参数
		
		//页面跳转同步通知页面路径
		String return_url = AlipayConfig.return_url; // 
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
		
		//卖家支付宝帐户
		String seller_email = AlipayConfig.sellerEmail;
		//必填
		
		//商户订单号
		String out_trade_no =ipsorder.getId() + "";
		//商户网站订单系统中唯一订单号，必填
		
		//订单名称
		String subject =  "qiu310";
		//必填
		
		//付款金额
		String total_fee = ipsorder.getAmount() + "";
		//必填
		
		//订单描述
		
		String body = "qiu310:"+out_trade_no+"";
		// 默认支付方式
		String defaultbank = "";
		String paymethod = "";// 赋值:bankPay(网银);cartoon(卡通)//
		// directPay(余额)
		// 必填
				// 默认网银
		if (StringUtils.isNotBlank(commTraceForm.getBankName())) {
			String bankName = getSendName(commTraceForm.getBankName());
			if ("ALIPAY".equals(bankName.trim())) {
				Log.info("<<<<<<<<<<<<支付宝ALIPAY<<<<<<<<<<<<<<<<<<<");
				paymethod = "directPay";
			} else {
				paymethod = "bankPay";
				defaultbank = bankName;
			}
		}
		// 必填，银行简码请参考接口技术文档

		// 商品展示地址
		String show_url = "";
		// 需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html

		// 防钓鱼时间戳
		String anti_phishing_key = "";
		// 若要使用请调用类文件submit中的query_timestamp函数

		// 客户端的IP地址
		String exter_invoke_ip = "";
		// 非局域网的外网IP地址，如：221.0.0.1

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("paymethod", paymethod);
		sParaTemp.put("defaultbank", defaultbank);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

		// 建立请求
		String ItemUrl = AlipaySubmit.buildRequest(sParaTemp);
		NumberFormat nf = new DecimalFormat("0000000000");
		Struts2Utils.setAttribute("orderNoFormat", nf.format(ipsorder.getId()));
		commTraceForm.setCustomOrderNo(ipsorder.getId().toString());
		commTraceForm.setAlipayUrl(ItemUrl);
		commTraceForm.setBankPic(commTraceForm.getBankName());
		commTraceForm.setUserName(user.getUserName());
		commTraceForm.setPayWay(payWay.ordinal());
		this.addActionMessage("选择支付成功,系统会自动转向支付平台");
		//return forward(true, "to-pay");
		return "to-pay-quick";
		
	}
	/**
	 * *功能：发起订单——通讯流水，和易宝通讯
	 * @throws IOException
	 * @throws WebDataException
	 */
	private String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}
	public String commTraceMarker_yeepay(PayWay payWay) throws WebDataException, IOException {
		logger.info("====================易宝 尝试支付 ");
		User user = getLoginUser();
		if (user == null) {
			CookieUtil.addReUrlCookie();
			return GlobalResults.FWD_LOGIN;
		}

		if (null == commTraceForm) {
			this.addActionError("支付信息不能为空,请修改.");
			return payPer();
		}
		if (StringUtils.isBlank(commTraceForm.getAmount())) {
			this.addActionError("支付金额不能为空,请修改.");
			return payPer();
		}
		if (StringUtils.isBlank(commTraceForm.getBankName())) {
			this.addActionError("支付方式不能为空,请修改.");
			return payPer();
		}

		Ipsorder ipsorder = new Ipsorder();
		try {
			ipsorder.setAmount(BigDecimalUtil.valueOf(Double.valueOf(commTraceForm.getAmount())));
		} catch (Exception e) {
			this.addActionError("支付金额出错");
			return payPer();
		}
		ipsorder.setIfcheck(false);
		ipsorder.setPayWay(payWay);
		ipsorder.setUserid(user.getId());
		ipsorder.setUserName(user.getUserName());
		ipsorder.setRealName(null==user.getInfo()?null:user.getInfo().getRealName());
		ipsorder.setMemo(null);
		// 提交至支付宝前 操作 生成订单
		ipsorder = userManager.saveIpsorder(ipsorder);
		String keyValue = formatString(Configuration.getInstance().getValue("keyValue"));// 商家密钥
		String nodeAuthorizationURL = formatString(Configuration.getInstance().getValue("yeepayCommonReqURL"));// 交易请求地址
		// 商家设置用户购买商品的支付信息
		String p0_Cmd = formatString("Buy");// 在线支付请求，固定值 ”Buy”
		String p1_MerId = formatString(Configuration.getInstance().getValue("p1_MerId")); 		// 商户编号
		String p2_Order= formatString(""+ipsorder.getId());					// 商户订单号
		String p3_Amt = formatString(""+ipsorder.getAmount());// 支付金额
		String p4_Cur = formatString("CNY");// 交易币种
		String p5_Pid = formatString("www.cai310.com");// 商品名称
		String  p6_Pcat = formatString("www.cai310.com");// 商品种类
		String 	  p7_Pdesc = formatString("www.cai310.com");// 商品描述
	    //String host = formatString(Configuration.getInstance().getValue("returnUrl"));
		String host ="http://www.cai310.com";
		String 	  p8_Url = formatString(host+"/user/fund!yeePayReturn.action");// 商户接收支付成功数据的地址
		String 	  p9_SAF = formatString("0"); // 需要填写送货信息 0：不需要  1:需要
		String 	  pa_MP = formatString("");// 商户扩展信息
		String bankName = getYeePaySendName(commTraceForm.getBankName());
		String pd_FrpId	= formatString(bankName);// 支付通道编码
		// 银行编号必须大写
		pd_FrpId = pd_FrpId.toUpperCase();
		String 	  pr_NeedResponse = formatString("1"); // 默认为"1"，需要应答机制
	    String 	  hmac = formatString("");// 交易签名串
	    
	    // 获得MD5-HMAC签名
	    hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd,
				p1_MerId,p2_Order,p3_Amt,p4_Cur,p5_Pid,p6_Pcat,p7_Pdesc,
				p8_Url,p9_SAF,pa_MP,pd_FrpId,pr_NeedResponse,keyValue);
	    Map<String,String> params = new HashMap<String,String>();
		params.put("p0_Cmd", p0_Cmd);
		params.put("p1_MerId", p1_MerId);
		params.put("p2_Order", p2_Order);
		params.put("p3_Amt", p3_Amt);
		params.put("p4_Cur", p4_Cur);
		params.put("p5_Pid", p5_Pid);
		params.put("p6_Pcat", p6_Pcat);
		params.put("p7_Pdesc", p7_Pdesc);
		params.put("p8_Url", p8_Url);
		params.put("p9_SAF", p9_SAF);
		params.put("pa_MP", pa_MP);
		params.put("pd_FrpId", pd_FrpId);
		params.put("pr_NeedResponse", pr_NeedResponse);
		params.put("hmac", hmac);

        StringBuffer sb = new StringBuffer();
        sb.append(nodeAuthorizationURL);
        Boolean first = true;
        for (String key : params.keySet()) {
			String value=params.get(key);
			if(first){
				sb.append("?").append(key).append("=").append(value);
				first = false;
			}else{
				sb.append("&").append(key).append("=").append(value);
			}
		}
		
		NumberFormat nf = new DecimalFormat("0000000000");
		Struts2Utils.setAttribute("orderNoFormat", nf.format(ipsorder.getId()));
		commTraceForm.setCustomOrderNo(ipsorder.getId().toString());
		commTraceForm.setAlipayUrl(sb.toString());
		commTraceForm.setBankPic(commTraceForm.getBankName());
		commTraceForm.setUserName(user.getUserName());
		commTraceForm.setPayWay(payWay.ordinal());
		this.addActionMessage("选择支付成功,系统会自动转向支付平台");
		return forward(true, "to-pay");
	}
	
	/**
	 * *功能：易宝回调通知页面
	 * 
	 * @author chen
	 */
	public String yeePayReturn() throws IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String keyValue   = formatString(Configuration.getInstance().getValue("keyValue"));   // 商家密钥
		String r0_Cmd 	  = formatString(request.getParameter("r0_Cmd")); // 业务类型
		String p1_MerId   = formatString(Configuration.getInstance().getValue("p1_MerId"));   // 商户编号
		String r1_Code    = formatString(request.getParameter("r1_Code"));// 支付结果
		String r2_TrxId   = formatString(request.getParameter("r2_TrxId"));// 易宝支付交易流水号
		String r3_Amt     = formatString(request.getParameter("r3_Amt"));// 支付金额
		String r4_Cur     = formatString(request.getParameter("r4_Cur"));// 交易币种
		String r5_Pid     = new String(formatString(request.getParameter("r5_Pid")).getBytes("iso-8859-1"),"gbk");// 商品名称
		String r6_Order   = formatString(request.getParameter("r6_Order"));// 商户订单号
		String r7_Uid     = formatString(request.getParameter("r7_Uid"));// 易宝支付会员ID
		String r8_MP      = new String(formatString(request.getParameter("r8_MP")).getBytes("iso-8859-1"),"gbk");// 商户扩展信息
		String r9_BType   = formatString(request.getParameter("r9_BType"));// 交易结果返回类型
		String hmac       = formatString(request.getParameter("hmac"));// 签名数据
		boolean isOK = false;
		// 校验返回数据包
		isOK = PaymentForOnlineService.verifyCallback(hmac,p1_MerId,r0_Cmd,r1_Code, 
				r2_TrxId,r3_Amt,r4_Cur,r5_Pid,r6_Order,r7_Uid,r8_MP,r9_BType,keyValue);
		if(isOK) {
			//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
			logger.debug("<br>交易成功!<br>商家订单号:" + r6_Order + "<br>支付金额:" + r3_Amt + "<br>易宝支付交易流水号:" + r2_TrxId);
			if(r1_Code.equals("1")) {
				// 下面页面输出是测试时观察结果使用
				// 为了保证不被重复调用，或重复执行数据库更新程序，请判断该笔交易状态是否是订单未处理状态
				if(StringUtils.isBlank(r6_Order)){
					this.addActionError("订单号为空.");
					Struts2Utils.getResponse().getWriter().print("ERROR");return forward(false, "user-payFail");
				}
				Ipsorder ipsorder = userManager.getIpsorder(Long.valueOf(r6_Order));
				if(null==ipsorder){
					this.addActionError("找不到订单.");
					Struts2Utils.getResponse().getWriter().print("ERROR");return forward(false, "user-payFail");
				}
				User user = userManager.getUser(ipsorder.getUserid());
				if(null==user){
					this.addActionError("找不到用户.");
					Struts2Utils.getResponse().getWriter().print("ERROR");return forward(false, "user-payFail");
				}
				if (Double.valueOf(r3_Amt)
						- ipsorder.getAmount().doubleValue() != 0) {
					this.addActionError("处理金额错误.");
					Struts2Utils.getResponse().getWriter().print("ERROR");return forward(false, "user-payFail");
				}
				synchronized (Constant.Epay) {
					try{
						userManager.confirmIps(ipsorder, user, null);
						return forward(true, "user-paySuccess");
					}catch(ServiceException e){
						this.addActionError(e.getMessage());
						Struts2Utils.getResponse().getWriter().print("ERROR");return forward(false, "user-payFail");
					}catch(Exception e){
						this.addActionError("找不到用户.");
						Struts2Utils.getResponse().getWriter().print("ERROR");return forward(false, "user-payFail");
					}
				}
			}else{
				this.addActionError("交易失败.");
				Struts2Utils.getResponse().getWriter().print("ERROR");return forward(false, "user-payFail");
			}
		} else {
			logger.debug("交易签名被篡改!");
			this.addActionError("签名验证失败.");
			Struts2Utils.getResponse().getWriter().print("ERROR");return forward(false, "user-payFail");
		}
	}
	
	
	public void alipayNotify() throws IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String order_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		logger.warn("支付宝返回结果获取订单号 ======================================================"+order_no);
		logger.warn("支付宝订单号 ======================================================"+trade_no);
		logger.warn("支付宝返回结果获取状态 ======================================================"+trade_status);
		logger.warn("支付宝返回结果获取总金额 ======================================================"+total_fee);
		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			// 为了保证不被重复调用，或重复执行数据库更新程序，请判断该笔交易状态是否是订单未处理状态
			if (StringUtils.isBlank(order_no)) {
				Struts2Utils.getResponse().getWriter().print("fail");
				return;
			}
			Ipsorder ipsorder = userManager.getIpsorder(Long
					.valueOf(order_no));
			if (null == ipsorder) {
				Struts2Utils.getResponse().getWriter().print("fail");
				return;
			}
			if (Double.valueOf(total_fee)
					- ipsorder.getAmount().doubleValue() != 0) {
				Struts2Utils.getResponse().getWriter().print("fail");
				return;
			}
			User user = userManager.getUser(ipsorder.getUserid());
			if (null == user) {
				Struts2Utils.getResponse().getWriter().print("fail");
				return;
			}
			logger.warn("ok ======================================================"+order_no);
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				synchronized (Constant.Epay) {
					try {
						userManager.confirmIps(ipsorder, user, null);
					} catch (ServiceException e) {
						Struts2Utils.getResponse().getWriter().print("fail");
					} catch (Exception e) {
						Struts2Utils.getResponse().getWriter().print("fail");
					}
				}
				//注意：
				//该种交易状态只在两种情况下出现
				//1、开通了普通即时到账，买家付款成功后。
				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				synchronized (Constant.Epay) {
					try {
						userManager.confirmIps(ipsorder, user, null);
					} catch (ServiceException e) {
						Struts2Utils.getResponse().getWriter().print("fail");
					} catch (Exception e) {
						Struts2Utils.getResponse().getWriter().print("fail");
					}
				}
				//注意：
				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
			}

			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				
			Struts2Utils.getResponse().getWriter().print("success");//请不要修改或删除
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			Struts2Utils.getResponse().getWriter().print("fail");
		}
//		Log.info("<<<<<<<<<<<<<<<<<<<<<支付宝通知alipayNotify [1次]");
//		String key = AlipayConfig.key;
//		HttpServletRequest request = Struts2Utils.getRequest();
//		// 获取支付宝GET过来反馈信息
//		Map params = this.getAlipayRequestParams(request.getParameterMap());
//		// 判断responsetTxt是否为ture，生成的签名结果mysign与获得的签名结果sign是否一致
//		// responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
//		// mysign与sign不等，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
//		String mysign = AlipayNotify.GetMysign(params, key);
//		String responseTxt = AlipayNotify.Verify(request
//				.getParameter("notify_id"));
//		String sign = request.getParameter("sign");
//		// 获取支付宝的通知返回参数
//		String trade_no = request.getParameter("trade_no"); // 支付宝交易号
//		String order_no = request.getParameter("out_trade_no"); // 获取订单号
//		String total_fee = request.getParameter("total_fee"); // 获取总金额
//		String subject = new String(request.getParameter("subject").getBytes(
//				"ISO-8859-1"), "UTF-8");// 商品名称、订单名称
//		String body = "";
//		if (request.getParameter("body") != null) {
//			body = new String(request.getParameter("body").getBytes(
//					"ISO-8859-1"), "UTF-8");// 商品描述、订单备注、描述
//		}
//		String buyer_email = request.getParameter("buyer_email"); // 买家支付宝账号
//		String trade_status = request.getParameter("trade_status"); // 交易状态
//		String partner = request.getParameter("partner"); // 合作方ID
//		String message = "";
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		// 校验支付宝签名
//		Log.info("<<<<<<<<<<<<<<<<<<<<<支付宝通知 [out_trade_no]" + trade_no
//				+ "[out_trade_no]" + order_no);
//		Log.info("<<<<<<<<<<<<<<<<<<<<<支付宝通知 [sign]" + sign + "[mysign]"
//				+ mysign);
//		Log.info("<<<<<<<<<<<<<<<<<<<<<支付宝通知 [notify_id]"
//				+ request.getParameter("notify_id"));
//		Log.info("<<<<<<<<<<<<<<<<<<<<<支付宝通知 [responseTxt]" + responseTxt);
//		if (mysign.equals(sign) && responseTxt.equals("true")) {
//			// 表示交易成功（TRADE_FINISHED/TRADE_SUCCESS）
//			if (trade_status.equals("TRADE_FINISHED")
//					|| trade_status.equals("TRADE_SUCCESS")) {
//				// 为了保证不被重复调用，或重复执行数据库更新程序，请判断该笔交易状态是否是订单未处理状态
//				if (StringUtils.isBlank(order_no)) {
//					this.addActionError("订单号为空.");
//					Struts2Utils.getResponse().getWriter().print("ERROR");
//					return;
//				}
//				Ipsorder ipsorder = userManager.getIpsorder(Long
//						.valueOf(order_no));
//				if (null == ipsorder) {
//					this.addActionError("找不到订单.");
//					Struts2Utils.getResponse().getWriter().print("ERROR");
//					return;
//				}
//				User user = userManager.getUser(ipsorder.getUserid());
//				if (null == user) {
//					this.addActionError("找不到用户.");
//					Struts2Utils.getResponse().getWriter().print("ERROR");
//					return;
//				}
//				synchronized (Constant.Epay) {
//					try {
//						userManager.confirmIps(ipsorder, user, null);
//					} catch (ServiceException e) {
//						this.addActionError(e.getMessage());
//						Struts2Utils.getResponse().getWriter().print("ERROR");
//						return;
//					} catch (Exception e) {
//						this.addActionError("找不到用户.");
//						Struts2Utils.getResponse().getWriter().print("ERROR");
//						return;
//					}
//				}
//			} else {
//				// /失败转发
//				this.addActionError("交易失败.");
//				Struts2Utils.getResponse().getWriter().print("ERROR");
//				return;
//			}
//		} else {
//			// /失败转发
//			this.addActionError("签名验证失败.");
//			Struts2Utils.getResponse().getWriter().print("ERROR");
//			return;
//		}
		return;
	}
	/**
	 * *功能：手机支付宝回调通知页面
	 * 
	 * @author zhuhui
	 */
	public static void main(String[] args) {
		
	}
	public String alipayPhoneReturn() throws IOException {
		logger.warn("支付宝返回结果=====================================================");
		
		HttpServletRequest request = Struts2Utils.getRequest();
		// 获取支付宝GET过来反馈信息
		String notify_data = request.getParameter("notify_data");
		logger.warn("支付宝返回结果notify_data ======================================================"+notify_data);
		String sign = request.getParameter("sign");
		logger.warn("支付宝返回结果sign ======================================================"+sign);
		logger.warn("支付宝返回结果docheck ======================================================"+Rsa.doCheck("notify_data="+notify_data, sign, AlipayConstant.RSA_ALIPAY_PUBLIC));
		if(Rsa.doCheck("notify_data="+notify_data, sign, AlipayConstant.RSA_ALIPAY_PUBLIC)){
			 if(StringUtils.isNotBlank(notify_data)){
					Document doc = null;
					try {
						doc = DocumentHelper.parseText(notify_data);
					} catch (DocumentException e1) {
						logger.warn("支付宝返回结果解析xml出错 ======================================================");
						Struts2Utils.getResponse().getWriter().print("ERROR");return null;
					}
					TradeVisitor tradeVisitor=new TradeVisitor();
					doc.accept(tradeVisitor);

					String order_no = tradeVisitor.getOut_trade_no(); // 获取订单号
					String trade_status = tradeVisitor.getTrade_status(); // 状态
					String total_fee = tradeVisitor.getTotal_fee(); // 获取总金额
					logger.warn("支付宝返回结果获取订单号 ======================================================"+order_no);
					logger.warn("支付宝返回结果获取状态 ======================================================"+trade_status);
					logger.warn("支付宝返回结果获取总金额 ======================================================"+total_fee);
					// 表示交易成功（TRADE_FINISHED/TRADE_SUCCESS）
					if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
						// 为了保证不被重复调用，或重复执行数据库更新程序，请判断该笔交易状态是否是订单未处理状态
						logger.warn("支付宝处理开始 ======================================================");
						if(StringUtils.isBlank(order_no)){
							logger.warn("支付宝处理order_no为空 ======================================================");
							Struts2Utils.getResponse().getWriter().print("ERROR");return null;
						}
						Ipsorder ipsorder = userManager.getIpsorder(Long.valueOf(order_no));
						if(null==ipsorder){
							logger.warn("支付宝处理order_no找不到订单 ======================================================");
							Struts2Utils.getResponse().getWriter().print("ERROR");return null;
						}
						User user = userManager.getUser(ipsorder.getUserid());
						if(null==user){
							logger.warn("支付宝处理找不到用户 ======================================================");
							Struts2Utils.getResponse().getWriter().print("ERROR");return null;
						}
						if(Double.valueOf(total_fee)-ipsorder.getAmount().doubleValue()!=0){
							logger.warn("支付宝处理金额错误 ======================================================");
							Struts2Utils.getResponse().getWriter().print("ERROR");return null;
						}
						synchronized (Constant.Epay) {
							try{
								userManager.confirmIps(ipsorder, user, null);
								Struts2Utils.getResponse().getWriter().print("success");
								return null;
							}catch(ServiceException e){
								logger.warn("支付宝处理错误 ======================================================"+e.getMessage());
								Struts2Utils.getResponse().getWriter().print("ERROR");return null;
							}catch(Exception e){
								logger.warn("支付宝处理错误 ======================================================"+e.getMessage());
								Struts2Utils.getResponse().getWriter().print("ERROR");return null;
							}
						}
					} else {
						logger.warn("支付宝没交易成功 ======================================================");
						Struts2Utils.getResponse().getWriter().print("ERROR");return null;
					}
			 }else{
				 Struts2Utils.getResponse().getWriter().print("ERROR");return null;
			 }
		}else{
			Struts2Utils.getResponse().getWriter().print("ERROR");return null;
		}
	}
	/**
	 * *功能：回调通知页面
	 * 
	 * @author zhuhui
	 */
	public String alipayReturn() throws IOException {
		logger
		.error("支付宝 ======================================================");
//		HttpServletRequest request = Struts2Utils.getRequest();
//		//获取支付宝GET过来反馈信息
//		Map<String,String> params = new HashMap<String,String>();
//		Map requestParams = request.getParameterMap();
//		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = (String) iter.next();
//			String[] values = (String[]) requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i]
//						: valueStr + values[i] + ",";
//			}
//			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			params.put(name, valueStr);
//		}
//		
//		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//		//商户订单号
//
//		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//		logger
//		.error("支付宝 ======================================out_trade_no================"+out_trade_no);
//		//支付宝交易号
//
//		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
//		logger
//		.error("支付宝 ======================================trade_no================"+trade_no);
//		//交易状态
//		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
//
//		logger
//		.error("支付宝 ======================================trade_status================"+trade_status);
//		String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
//		logger
//		.error("支付宝 ======================================total_fee================"+total_fee);
//		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
//		
//		//计算得出通知验证结果
//		boolean verify_result = AlipayNotify.verify(params);
//		logger
//		.error("支付宝 ======================================verify_result================"+verify_result);
//		if(verify_result){//验证成功
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//请在这里加上商户的业务逻辑程序代码
//			// 为了保证不被重复调用，或重复执行数据库更新程序，请判断该笔交易状态是否是订单未处理状态
//			if (StringUtils.isBlank(out_trade_no)) {
//				Struts2Utils.getResponse().getWriter().print("fail");
//			}
//			Ipsorder ipsorder = userManager.getIpsorder(Long
//					.valueOf(out_trade_no));
//			if (null == ipsorder) {
//				Struts2Utils.getResponse().getWriter().print("fail");
//			}
//			if (Double.valueOf(total_fee)
//					- ipsorder.getAmount().doubleValue() != 0) {
//				Struts2Utils.getResponse().getWriter().print("fail");
//			}
//			User user = userManager.getUser(ipsorder.getUserid());
//			if (null == user) {
//				Struts2Utils.getResponse().getWriter().print("fail");
//			}
//			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
//			
//			
//
//		//////////////////////////////////////////////////////////////////////////////////////////
//			
//			
//			
//			if(trade_status.equals("TRADE_FINISHED")){
//				//判断该笔订单是否在商户网站中已经做过处理
//					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//					//如果有做过处理，不执行商户的业务程序
//				synchronized (Constant.Epay) {
//					try {
//						userManager.confirmIps(ipsorder, user, null);
//					} catch (ServiceException e) {
//						Struts2Utils.getResponse().getWriter().print("fail");
//					} catch (Exception e) {
//						Struts2Utils.getResponse().getWriter().print("fail");
//					}
//				}
//				//注意：
//				//该种交易状态只在两种情况下出现
//				//1、开通了普通即时到账，买家付款成功后。
//				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
//			} else if (trade_status.equals("TRADE_SUCCESS")){
//				//判断该笔订单是否在商户网站中已经做过处理
//					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//					//如果有做过处理，不执行商户的业务程序
//				synchronized (Constant.Epay) {
//					try {
//						userManager.confirmIps(ipsorder, user, null);
//					} catch (ServiceException e) {
//						Struts2Utils.getResponse().getWriter().print("fail");
//					} catch (Exception e) {
//						Struts2Utils.getResponse().getWriter().print("fail");
//					}
//				}
//				//注意：
//				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
//			}
//
//			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
//				
//			Struts2Utils.getResponse().getWriter().print("success");//请不要修改或删除
//			//////////////////////////////////////////////////////////////////////////////////////////
//		}else{//验证失败
//			Struts2Utils.getResponse().getWriter().print("fail");
//		}
//	    return null;
		return forward(false, "user-payFail");
	}


	private Map getAlipayRequestParams(Map requestParams) throws UnsupportedEncodingException {
		Map params = new HashMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			params.put(name, valueStr);
		}
		return params;
	}

	// 页面 后台
	// ICBC 中国工商银行 // ICBCB2C 中国工商银行
	// CMBBANK 招商银行 // CMB 招商银行
	// CCB 中国建设银行 // CCB 中国建设银行
	// BOC 中国银行 BOCB2C 中国银行
	// ABC 中国农业银行 // ABC 中国农业银行
	// COMM 交通银行 // COMM 交通银行
	// SPDB 上海浦东发展银行 // SPDB 上海浦东发展银行
	// GDB 广东发展银行 // GDB 广东发展银行
	// CITIC 中信银行 // CITIC 中信银行
	// CEB 中国光大银行 // CEBBANK 光大银行
	// CIB 兴业银行 // CIB 兴业银行
	// SDB 深圳发展银行 // SDB 深圳发展银行
	// CMBC 中国民生银行 // CMBC 中国民生银行
	// HZCM 杭州银行 HZCBB2C 杭州银行
	// NBBANK 宁波银行 NBBANK 宁波银行
	// SPABANK 平安银行 SPABANK 平安银行
	// SHBANK 上海银行 SHBANK 上海银行
	// ENV_ICBC 中国工商银行企业 ICBCBTB 中国工商银行(B2B)
	// ENV_CCB 中国建设银行企业 CCBBTB 中国建设银行(B2B)
	// ENV_ABC 中国农业银行企业 ABCBTB 中国农业银行(B2B)
	// ENV_SPDB 上海浦东发展银行企业 // SPDBB2B 上海浦东发展银行(B2B)

	private static String getSendName(String name) {
		if (StringUtils.isNotBlank(name)) {
			if ("ICBC".equals(name)) {
				return "ICBCB2C";
			} else if ("CMBBANK".equals(name)) {
				return "CMB";
			} else if ("CCB".equals(name)) {
				return "CCB";
			} else if ("ABC".equals(name)) {
				return "ABC";
			} else if ("COMM".equals(name)) {
				return "COMM";
			} else if ("SPDB".equals(name)) {
				return "SPDB";
			} else if ("GDB".equals(name)) {
				return "GDB";
			} else if ("CITIC".equals(name)) {
				return "CITIC";
			} else if ("CEB".equals(name)) {
				return "CEBBANK";
			} else if ("CIB".equals(name)) {
				return "CIB";
			} else if ("SDB".equals(name)) {
				return "SDB";
			} else if ("CMBC".equals(name)) {
				return "CMBC";
			} else if ("ENV_SPDB".equals(name)) {
				return "SPDBB2B";
			} else if ("BOC".equals(name)) {
				return "BOCB2C";
			} else if ("HZCM".equals(name)) {
				return "HZCBB2C";
			} else if ("NBBANK".equals(name)) {
				return "NBBANK";
			} else if ("SPABANK".equals(name)) {
				return "SPABANK";
			} else if ("SHBANK".equals(name)) {
				return "SHBANK";
			} else if ("ENV_ICBC".equals(name)) {
				return "ICBCBTB";
			} else if ("ENV_CCB".equals(name)) {
				return "CCBBTB";
			} else if ("ENV_ABC".equals(name)) {
				return "ABCBTB";
			}
		}
		// 默认支付宝
		return "ALIPAY";
	}
	/**
	 * ICBC-NET-B2C  工商银行  
CMBCHINA-NET-B2C  招商银行  
ABC-NET-B2C  中国农业银行  
CCB-NET-B2C  建设银行  
BCCB-NET-B2C  北京银行  
BOCO-NET-B2C  交通银行  
CIB-NET-B2C  兴业银行  
NJCB-NET-B2C  南京银行  
CMBC-NET-B2C  中国民生银行  
CEB-NET-B2C  光大银行  
BOC-NET-B2C  中国银行  
PINGANBANK-NET  平安银行  
CBHB-NET-B2C  渤海银行  
HKBEA-NET-B2C  东亚银行  
NBCB-NET-B2C  宁波银行  
ECITIC-NET-B2C  中信银行(需要证书才能连接到银行)  
SDB-NET-B2C  深圳发展银行  
GDB-NET-B2C  广发银行  
SHB-NET-B2C  上海银行  
SPDB-NET-B2C  上海浦东发展银行  
POST-NET-B2C  中国邮政  
BJRCB-NET-B2C  北京农村商业银行  
HXB-NET-B2C 华夏银行（此功能默认不开通，如需开通请与易宝支付销售人员联系）  
CZ-NET-B2C  浙商银行  

	 * @param name
	 * @return
	 */
	private static String getYeePaySendName(String name) {
		if (StringUtils.isNotBlank(name)) {
			if ("ICBC".equals(name)) {
				return "ICBC-NET-B2C";
			} else if ("CMBBANK".equals(name)) {
				return "CMBCHINA-NET-B2C";
			} else if ("CCB".equals(name)) {
				return "CCB-NET-B2C";
			} else if ("ABC".equals(name)) {
				return "ABC-NET-B2C";
			} else if ("COMM".equals(name)) {
				return "BOCO-NET-B2C";
			} else if ("SPDB".equals(name)) {
				return "SPDB-NET-B2C";
			} else if ("GDB".equals(name)) {
				return "GDB-NET-B2C";
			} else if ("CITIC".equals(name)) {
				return "ECITIC-NET-B2C";
			} else if ("CEB".equals(name)) {
				return "CEB-NET-B2C";
			} else if ("CIB".equals(name)) {
				return "CIB-NET-B2C";
			} else if ("SDB".equals(name)) {
				return "SDB-NET-B2C";
			} else if ("CMBC".equals(name)) {
				return "CMBC-NET-B2C";
			} else if ("ENV_SPDB".equals(name)) {
				return "SPDB-NET-B2C";
			} else if ("BOC".equals(name)) {
				return "BOC-NET-B2C";
			} else if ("HZCM".equals(name)) {
				return "CZ-NET-B2C";
			} else if ("NBBANK".equals(name)) {
				return "NBCB-NET-B2C";
			} else if ("SPABANK".equals(name)) {
				return "PINGANBANK-NET";
			} else if ("SHBANK".equals(name)) {
				return "SHB-NET-B2C";
			} else if ("ENV_ICBC".equals(name)) {
				return "ICBC-NET-B2C";
			} else if ("ENV_CCB".equals(name)) {
				return "CCB-NET-B2C";
			} else if ("ENV_ABC".equals(name)) {
				return "ABC-NET-B2C";
			}
		}
		// 默认工行
		return "ICBC-NET-B2C";
	}
	/**
	 * @return {@link #fundType}
	 */
	public FundDetailType getFundType() {
		return fundType;
	}

	/**
	 * @param fundType
	 *            the {@link #fundType} to set
	 */
	public void setFundType(FundDetailType fundType) {
		this.fundType = fundType;
	}

	/**
	 * @return {@link #timeFrame}
	 */
	public int getTimeFrame() {
		return timeFrame;
	}

	/**
	 * @param timeFrame
	 *            the {@link #timeFrame} to set
	 */
	public void setTimeFrame(int timeFrame) {
		this.timeFrame = timeFrame;
	}

	/**
	 * @return {@link #pagination}
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination
	 *            the {@link #pagination} to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	/**
	 * @return the drawingState
	 */
	public DrawingOrderState getDrawingState() {
		return drawingState;
	}

	/**
	 * @param drawingState
	 *            the drawingState to set
	 */
	public void setDrawingState(DrawingOrderState drawingState) {
		this.drawingState = drawingState;
	}

	/**
	 * @return the commonQueryCache
	 */
	public Cache getCommonQueryCache() {
		return commonQueryCache;
	}

	/**
	 * @param commonQueryCache
	 *            the commonQueryCache to set
	 */
	public void setCommonQueryCache(Cache commonQueryCache) {
		this.commonQueryCache = commonQueryCache;
	}

	public String checkIpsData() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			User loginUser = getLoginUser();
			if (loginUser == null) {
				throw new WebDataException("您还没有登录.");
			}
			if (null == commTraceForm) {
				throw new WebDataException("支付信息不能为空,请修改.");
			}
			if (StringUtils.isBlank(commTraceForm.getAmount())) {
				throw new WebDataException("支付金额不能为空,请修改.");
			}
			if (StringUtils.isBlank(commTraceForm.getBankName())) {
				throw new WebDataException("支付方式不能为空,请修改.");
			}
			try {
				BigDecimalUtil.valueOf(Double.valueOf(commTraceForm.getAmount()));
			} catch (Exception e) {
				throw new WebDataException("支付金额出错");
			}
			if (Double.valueOf(commTraceForm.getAmount()) < Double.valueOf("10")) {
				throw new WebDataException("支付金额必须是10元以上");
			}
			map.put("success", true);
		} catch (WebDataException e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "支付出错，请刷新页面再试");
		}
		Struts2Utils.renderJson(map);
		return null;
	}

	

	public void setCommTraceForm(CommTraceForm commTraceForm) {
		this.commTraceForm = commTraceForm;
	}

	public DrawingForm getDrawingForm() {
		return drawingForm;
	}

	public String getLotteryNo() {
		return lotteryNo;
	}

	public void setLotteryNo(String lotteryNo) {
		this.lotteryNo = lotteryNo;
	}

	private boolean hasRecharge;

	public boolean isHasRecharge() {
		return hasRecharge;
	}

	public void setHasRecharge(boolean hasRecharge) {
		this.hasRecharge = hasRecharge;
	}

	public boolean isCanActivity() {
		return canActivity;
	}

	public void setCanActivity(boolean canActivity) {
		this.canActivity = canActivity;
	}

	public void setDrawingForm(DrawingForm drawingForm) {
		this.drawingForm = drawingForm;
	}
	public static String CreateUrl(String paygateway, String service, String partner, String sign_type,
        	String out_trade_no, String key, String input_charset) {
    	
        Map params = new HashMap();
        params.put("service", service);
        params.put("partner", partner);
        params.put("out_trade_no", out_trade_no);
        params.put("_input_charset", input_charset);
     

        String prestr = "";

        prestr = prestr + key;
        //System.out.println("prestr=" + prestr);

        String sign = MD5.md5(getContent(params, key));

        String parameter = "";
        parameter = parameter + paygateway;
        //System.out.println("prestr="  + parameter);
        List keys = new ArrayList(params.keySet());
        for (int i = 0; i < keys.size(); i++) {
          	String value =(String) params.get(keys.get(i));
            if(value == null || value.trim().length() ==0){
            	continue;
            }
            try {
                parameter = parameter + keys.get(i) + "="
                    + URLEncoder.encode(value, input_charset) + "&";
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }

        parameter = parameter + "sign=" + sign + "&sign_type=" + sign_type;
        
        return parameter;

    }
	private static String getContent(Map params, String privateKey) {
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);

			if (i == keys.size() - 1) {
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr + privateKey;
	}
	public String alipaySingleTradeQuery() throws WebDataException {
			if (!isAjaxRequest()) {
				throw new WebDataException("非法URL请求!");
			}
			Map<String, Object> result = new HashMap<String, Object>();
			String orderNo = Struts2Utils.getParameter("orderNo");
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
			logger.warn("支付宝 out_trade_no======================================================"+out_trade_no);
			logger.warn("支付宝 total_fee======================================================"+total_fee);
			logger.warn("支付宝 trade_no======================================================"+trade_no);
			logger.warn("支付宝 trade_status======================================================"+trade_status);
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
				Ipsorder ipsorder = userManager.getIpsorder(Long
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
	
	public String yeepaySingleTradeQuery() throws WebDataException {
		if (!isAjaxRequest()) {
			throw new WebDataException("非法URL请求!");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		String orderNo = Struts2Utils.getParameter("orderNo");

		Ipsorder ipsorder = userManager.getIpsorder(Long.valueOf(orderNo));
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
				if (Double.valueOf(r3_Amt)
						- ipsorder.getAmount().doubleValue() != 0) {
					result.put("msg", "处理金额错误!");
					result.put("success", false);
					return forward(false, "user-payFail");
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

	public int getCountPay() {
		return countPay;
	}

	public void setCountPay(int countPay) {
		this.countPay = countPay;
	}

	public int getCountIn() {
		return countIn;
	}

	public void setCountIn(int countIn) {
		this.countIn = countIn;
	}

	public int getSumPay() {
		return sumPay;
	}

	public void setSumPay(int sumPay) {
		this.sumPay = sumPay;
	}

	public int getSumIn() {
		return sumIn;
	}

	public void setSumIn(int sumIn) {
		this.sumIn = sumIn;
	}

}
