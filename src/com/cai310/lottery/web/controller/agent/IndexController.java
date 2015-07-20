package com.cai310.lottery.web.controller.agent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.AgentLotteryType;
import com.cai310.lottery.common.AgentUserType;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.Media;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.lottery.entity.user.agent.AgentRebate;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.user.UserBaseController;
import com.cai310.orm.Pagination;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.MD5;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Results({
	@Result(name = "reg", location = "/WEB-INF/content/user/memberManager.ftl"),
	@Result(name = "link", location = "/WEB-INF/content/user/copyRegLink.ftl"),
	@Result(name = "groupInfoSum", location = "/WEB-INF/content/user/groupInfoSum.ftl"),
	@Result(name = "groupInfo", location = "/WEB-INF/content/user/groupInfo.ftl")})
public class IndexController extends UserBaseController {
	@Autowired
	private QueryService queryService;
	@Autowired
	AgentEntityManager agentEntityManager;
	@Autowired
	@Qualifier("agentQueryCache")
	private Cache agentQueryCache;
	private Pagination pagination = new Pagination(20);
	public List<AgentLotteryType> agentLotteryTypeList;
	public List<AgentRebate> agentRebateList=Lists.newArrayList();
	public List<AgentRebate> myAgentRebateList=Lists.newArrayList();
	private AgentUserType agentUserType=AgentUserType.GROUP;
	private List<String> limit_item;
	private AgentLotteryType agentLotteryType;
	public String index(){
		return "index";
	}
    public String rule(){
    	return "rule";
    }
    public String seeAgentRebate(){
    	String fwd = "seeAgentRebate";
    	User loginUser = this.getLoginUser();
		if (null == loginUser) {
			addActionMessage("您还没有登录，请先登录！");
			return fwd;
		}
		AgentLotteryType[] agentLotteryTypeArr = AgentLotteryType.values();
		String userId = Struts2Utils.getParameter("userId");
		if (null == userId) {
			addActionMessage("用户ID为空！");
			return fwd;
		}
		User user = userManager.getUser(Long.valueOf(userId));
		if (null == user) {
			addActionMessage("用户为空！");
			return fwd;
		}
		Struts2Utils.setAttribute("userName", user.getUserName());
		Struts2Utils.setAttribute("userId", user.getId());
		
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
		    if(!agentEntityManager.isUserGroup(user.getId(), loginUser.getId())){
		    	addActionMessage("登陆账户没权限操作！");
				return fwd;
		    }
			for (AgentLotteryType agentLotteryType : agentLotteryTypeArr) {
				AgentRebate agentRebate = agentEntityManager.findAgentRebate(user.getId(), agentLotteryType);
				agentRebateList.add(agentRebate);
				AgentRebate myAgentRebate = agentEntityManager.findAgentRebate(loginUser.getId(), agentLotteryType);
				myAgentRebateList.add(myAgentRebate);
			}
			return fwd;
		}
		return fwd;
    }
    public String oprAgentRebate(){
    	String fwd = "oprAgentRebate";
    	User loginUser = this.getLoginUser();
		if (null == loginUser) {
			addActionMessage("您还没有登录，请先登录！");
			return fwd;
		}
		AgentLotteryType[] agentLotteryTypeArr = AgentLotteryType.values();
		String userId = Struts2Utils.getParameter("userId");
		if (null == userId) {
			addActionMessage("用户ID为空！");
			return fwd;
		}
		User user = userManager.getUser(Long.valueOf(userId));
		if (null == user) {
			addActionMessage("用户为空！");
			return fwd;
		}
		Struts2Utils.setAttribute("userName", user.getUserName());
		Struts2Utils.setAttribute("userId", user.getId());
		
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
		    if(!agentEntityManager.isUserAgent(user.getId(), loginUser.getId())){
		    	addActionMessage("登陆账户没权限操作！");
				return fwd;
		    }
		    agentLotteryTypeList = Lists.newArrayList();
			for (AgentLotteryType agentLotteryType : agentLotteryTypeArr) {
				AgentRebate agentRebate = agentEntityManager.findAgentRebate(user.getId(), agentLotteryType);
				agentRebateList.add(agentRebate);
				AgentRebate myAgentRebate = agentEntityManager.findAgentRebate(loginUser.getId(), agentLotteryType);
				AgentLotteryType agentLotteryType_new = AgentLotteryType.getAgentLotteryType(myAgentRebate,agentLotteryType.name(),agentRebate.getRebate());
			    agentLotteryTypeList.add(agentLotteryType_new);
				myAgentRebateList.add(myAgentRebate);
			}
			return  fwd;
		}
		try{
			List<AgentRebate> oprRebateList = Lists.newArrayList();
			for (AgentLotteryType agentLotteryType : agentLotteryTypeArr) {
				String keyName = agentLotteryType.name();
				String value = Struts2Utils.getParameter(keyName);
				Double rebate = Double.valueOf(value);
				AgentRebate agentRebate = agentEntityManager.findAgentRebate(user.getId(), agentLotteryType);
				AgentRebate myAgentRebate = agentEntityManager.findAgentRebate(loginUser.getId(), agentLotteryType);
				if(myAgentRebate.getRebate()-rebate<0){
					throw new WebDataException("子帐号返点不能大于本账号，请调整.");
				}
				if(agentRebate.getRebate()-rebate>0){
					throw new WebDataException("不能小于原来返点，请调整.");
				}
				agentRebate.setRebate(rebate);
				oprRebateList.add(agentRebate);
			}
			agentEntityManager.updateAgentRebate(oprRebateList);
			this.jsonMap.put("msg", "修改成功.");
		    return forward(true, fwd);
		} catch (WebDataException e) {
			this.jsonMap.put("msg", e.getMessage());
		}  catch (ServiceException e) {
			this.jsonMap.put("msg", "操作失败。请联系客服");
		} catch (Exception e) {
			this.jsonMap.put("msg", "操作失败请联系客服");
			logger.warn(e.getMessage(), e);
		}
		return forward(false, fwd);
    }
	public String reg(){
		String type = Struts2Utils.getParameter("type");
		String fwd = "reg";
		AgentLotteryType[] agentLotteryTypeArr = AgentLotteryType.values();
		User loginUser = this.getLoginUser();
		if (null == loginUser) {
			addActionMessage("您还没有登录，请先登录！");
			return fwd;
		}
		if(StringUtils.isNotBlank(type))return "link";
		if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
				String rebate = Struts2Utils.getParameter("rebate");
				agentLotteryTypeList = Lists.newArrayList();
				for (AgentLotteryType agentLotteryType : agentLotteryTypeArr) {
					AgentRebate agentRebate = agentEntityManager.findAgentRebate(loginUser.getId(), agentLotteryType);
					agentRebateList.add(agentRebate);
					AgentLotteryType agentLotteryType_new = AgentLotteryType.getAgentLotteryType(agentRebate, agentLotteryType.name(), StringUtils.isNotBlank(rebate)?4.5:null);
					agentLotteryTypeList.add(agentLotteryType_new);
				}
				return forward(true, fwd);
		}
		
		agentLotteryTypeList = Lists.newArrayList();
		for (AgentLotteryType agentLotteryType : agentLotteryTypeArr) {
			String keyName = agentLotteryType.name();
			String value = Struts2Utils.getParameter(keyName);

			AgentRebate agentRebate = agentEntityManager.findAgentRebate(loginUser.getId(), agentLotteryType);
			AgentLotteryType agentLotteryType_new = AgentLotteryType.getAgentLotteryType(agentRebate, agentLotteryType.name(), StringUtils.isNotBlank(value)?Double.valueOf(value):null);
			agentLotteryTypeList.add(agentLotteryType_new);
		}
		// 登录操作
		try {
			String userName = Struts2Utils.getParameter("userName");
			if (StringUtils.isBlank(userName)) {
				throw new WebDataException("用户名为空.");
			}
			Struts2Utils.setAttribute("userName", userName);
			
			String captcha = Struts2Utils.getParameter("captcha");
			if (StringUtils.isBlank(captcha)) {
				throw new WebDataException("请输入验证码.");
			} else if (!captcha.equals(Struts2Utils.getSession().getAttribute(Constant.LOGIN_CAPTCHA))) {
				throw new WebDataException("验证码错误.");
			}
			//判读用户名是否重复
			userName = java.net.URLDecoder.decode(userName.trim(), "utf-8");
			User userTemp = userManager.getUserBy(userName);
			if (userTemp != null && userTemp.getUserName().equals(userName.toLowerCase())) {
				throw new WebDataException("注册用户名重复.请更改.");
			}
			User user = new User();
			////增加用户媒体来源信息
			String mid = CookieUtil.getCookieByName(Struts2Utils.getRequest(), "mid");
			if(StringUtils.isNotBlank(mid)){
				try{
					Media media = userManager.getMedia(Long.valueOf(mid));
					if(null!=media){
						user.setMid(media.getId());
					}else{
						user.setMid(0L);
					}
				}catch(Exception e){
					logger.warn("注册的时候发生媒体来源异常"+e.getMessage());
				}
			}
			user.setUserWay(UserWay.AGENT);
			user.setUserName(userName.toLowerCase());
			user.setLocked(User.NO_LOCK_STATUS);
			user.setRemainMoney(BigDecimal.ZERO);
			
			List<AgentRebate> agentRebateList = Lists.newArrayList();
			for (AgentLotteryType agentLotteryType : agentLotteryTypeArr) {
				String keyName = agentLotteryType.name();
				String value = Struts2Utils.getParameter(keyName);
				Double rebate = Double.valueOf(value);
				AgentRebate agentAgentRebate = agentEntityManager.findAgentRebate(loginUser.getId(), agentLotteryType);
				if(null ==agentAgentRebate){
					throw new WebDataException("本帐号返点异常.");
				}
				Double loginAgentRebate = agentAgentRebate.getRebate();
				if(loginAgentRebate-rebate<0){
					throw new WebDataException("子帐号返点不能大于本账号，请调整.");
				}
				AgentRebate agentRebate = new AgentRebate();
				agentRebate.setAgentLotteryType(agentLotteryType);
				agentRebate.setRebate(rebate);
				agentRebateList.add(agentRebate);
			}
			String pwd = "123456";
			user.setPassword(MD5.md5(pwd).toUpperCase());
			user.setAgentId(loginUser.getId());
			UserInfo info = new UserInfo();
			BankInfo bank = new BankInfo();
			user.setBank(bank);
			user.setInfo(info);
			user = agentEntityManager.saveAgentUser(loginUser.getId(),user, info, bank,agentRebateList,pwd);
			
			
			UserLogin userLogin = new UserLogin();
			userLogin.setUserId(user.getId());
			userLogin.setTryIp(Struts2Utils.getRemoteAddr());
			userLogin.setTryTime(new Date());
			userLogin.setLastLoginIp(userLogin.getTryIp());
			userLogin.setLastLoginTime(userLogin.getTryTime());
			userManager.saveUserLogin(userLogin);
			
			addActionMessage("注册成功.");
			return forward(true, fwd);
		} catch (WebDataException e) {
			addActionMessage(e.getMessage());
		}  catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return forward(false, fwd);
	}
	
	public String findAgentGroupInfoSum(){
		String fwd = "groupInfoSum";
		try{
			User loginUser = getLoginUser();
			if (loginUser == null) {
				addActionMessage("您还没有登录，请先登录！");
				return forward(false, fwd);
			}
			String key = getRequestKey() + loginUser.getId();
			Element el = agentQueryCache.get(key);
			String userName = Struts2Utils.getParameter("userName");
			Struts2Utils.setAttribute("userName", userName);
			User agentUser = loginUser;//要查询下属的用户
			String userId = Struts2Utils.getParameter("userId");
			if(StringUtils.isNotBlank(userId)){
				if(!agentEntityManager.isUserGroup(Long.valueOf(userId), loginUser.getUserId())){
					addActionMessage("您当前登录的账户无权查看！");
					return forward(false, fwd);
				}
				User user = userManager.getUser(Long.valueOf(userId));
				if(null==user){
					addActionMessage("您当前登录的账户无权查看！");
					return forward(false, fwd);
				}
				Struts2Utils.setAttribute("userName", user.getUserName());
				agentUser = user;
			}
			Date start = null;
			Date end = null;
			String startTimeStr = Struts2Utils.getParameter("dateStart");
			String endTimeStr = Struts2Utils.getParameter("dateEnd");
			if(StringUtils.isNotBlank(startTimeStr)) {
				start = DateUtil.strToDate(startTimeStr,"yyyy-MM-dd");
				Struts2Utils.setAttribute("dateStart", startTimeStr);
			}
			if(StringUtils.isNotBlank(endTimeStr)) {
				end = DateUtil.strToDate(endTimeStr,"yyyy-MM-dd");
				Struts2Utils.setAttribute("dateEnd", endTimeStr);
			}
			if (el == null) {
				if(StringUtils.isNotBlank(userName)){
					User user = userManager.getUserBy(userName.trim());
					if(null!=user){
						Struts2Utils.setAttribute("userName", user.getUserName());
						pagination = agentEntityManager.findAgentGroupInfoSum(agentUser.getUserId(),user.getId(), pagination,agentUserType,start,end);
					}else{
						addActionMessage("找不到用户名所对应的用户！");
						pagination = agentEntityManager.findAgentGroupInfoSum(agentUser.getUserId(),null, pagination,agentUserType,start,end);
					}
				}else{
					pagination = agentEntityManager.findAgentGroupInfoSum(agentUser.getUserId(),null, pagination,agentUserType,start,end);
				}
				el = new Element(key, this.pagination);
				agentQueryCache.put(el);
			} else {
				pagination = (Pagination) el.getValue();
			}
			return forward(true, fwd);
		}catch (ServiceException e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		} catch (Exception e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		}
		return forward(false, fwd);
	}
	public String findAgentGroupInfo(){
		String fwd = "groupInfo";
		try{
			User loginUser = getLoginUser();
			if (loginUser == null) {
				addActionMessage("您还没有登录，请先登录！");
				return forward(false, fwd);
			}
			String key = getRequestKey() + loginUser.getId();
			Element el = agentQueryCache.get(key);
			String userId = Struts2Utils.getParameter("userId");
			if (userId == null) {
				addActionMessage("用户Id为空！");
				return forward(false, fwd);
			}
			Struts2Utils.setAttribute("userId", userId);
			User agentUser = userManager.getUser(Long.valueOf(userId));
			if (agentUser == null) {
				addActionMessage("用户为空！");
				return forward(false, fwd);
			}
			String userName = Struts2Utils.getParameter("userName");
			Struts2Utils.setAttribute("userName", userName);
			String dateStartStr = Struts2Utils.getParameter("dateStart");
			Struts2Utils.setAttribute("dateStart", dateStartStr);
			Date dateStart=null;
			
			String fromHour = Struts2Utils.getParameter("fromHour");
			if(StringUtils.isNotBlank(fromHour)){
				Struts2Utils.setAttribute("fromHour", Integer.valueOf(fromHour));
			}
			String dateEndStr = Struts2Utils.getParameter("dateEnd");
			Struts2Utils.setAttribute("dateEnd", dateEndStr);
			Date dateEnd=null;
			
			String toHour = Struts2Utils.getParameter("toHour");
			if(StringUtils.isNotBlank(toHour)){
				Struts2Utils.setAttribute("toHour", Integer.valueOf(toHour));
			}
			if(StringUtils.isNotBlank(dateStartStr)){
				try{
					dateStart = DateUtil.strToDate(dateStartStr,"yyyy-MM-dd");
					dateStart = DateUtils.addHours(dateStart, Integer.valueOf(fromHour));
				}catch(Exception e){
					addActionMessage("开始时间错误！");
					return forward(false, fwd);
				}
			}
			if(StringUtils.isNotBlank(dateEndStr)){
				try{
					dateEnd = DateUtil.strToDate(dateEndStr,"yyyy-MM-dd");
					dateEnd = DateUtils.addHours(dateEnd, Integer.valueOf(toHour));
				}catch(Exception e){
					addActionMessage("开始时间错误！");
					return forward(false, fwd);
				}
			}
			if(null!=dateStart&&null!=dateEnd){
				if (dateEnd.getTime() - dateStart.getTime() > Long.valueOf(1000) * Long.valueOf(60) * Long.valueOf(60) * Long.valueOf(24)
						*dateLimit){
					addActionMessage("结束时间减去开始时间不能大于"+dateLimit+"天！");
					return forward(false, fwd);
				}
			}
			
			if (el == null) {
				Map elementMap = Maps.newHashMap();
				if(StringUtils.isNotBlank(userName)){
					User user = userManager.getUserBy(userName.trim());
					if(null!=user){
						Struts2Utils.setAttribute("userName", user.getUserName());
						pagination = agentEntityManager.findAgentGroupInfo(agentUser.getUserId(),user.getId(),dateStart,dateEnd, pagination,agentLotteryType);
					}else{
						addActionMessage("找不到用户名所对应的用户！");
						pagination = agentEntityManager.findAgentGroupInfo(agentUser.getUserId(),null,dateStart,dateEnd, pagination,agentLotteryType);
					}
				}else{
					pagination = agentEntityManager.findAgentGroupInfo(agentUser.getUserId(),null,dateStart,dateEnd, pagination,agentLotteryType);
				}
				elementMap.put("pagination", pagination);
				el = new Element(key, elementMap);
				agentQueryCache.put(el);
			} else {
				Map elementMap = (HashMap) el.getValue();
				pagination = (Pagination) elementMap.get("pagination");
				//agentGroupInfo = (AgentGroupInfo) elementMap.get("agentGroupInfo");
			}
			return forward(true, fwd);
		}catch (ServiceException e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		} catch (Exception e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		}
		return forward(false, fwd);
	}
	
	public String findAgentPersonInfoSum(){
		String fwd = "personInfoSum";
		try{
			User loginUser = getLoginUser();
			if (loginUser == null) {
				addActionMessage("您还没有登录，请先登录！");
				return forward(false, fwd);
			}
			String key = getRequestKey() + loginUser.getId();
			Element el = agentQueryCache.get(key);
			String userName = Struts2Utils.getParameter("userName");
			Struts2Utils.setAttribute("userName", userName);
			String userId = Struts2Utils.getParameter("userId");
			Struts2Utils.setAttribute("userId", userId);
			User agentUser = loginUser;//要查询下属的用户
			if(StringUtils.isNotBlank(userId)){
				if(!agentEntityManager.isUserGroup(Long.valueOf(userId), loginUser.getUserId())){
					addActionMessage("您当前登录的账户无权查看！");
					return forward(false, fwd);
				}
				User user = userManager.getUser(Long.valueOf(userId));
				if(null==user){
					addActionMessage("您当前登录的账户无权查看！");
					return forward(false, fwd);
				}
				Struts2Utils.setAttribute("userName", user.getUserName());
				agentUser = user;
			}
			 
			if (el == null) {
//					if(StringUtils.isNotBlank(userName)){
//						User user = userManager.getUserBy(userName.trim());
//						if(null!=user){
//							Struts2Utils.setAttribute("userName", user.getUserName());
//							pagination = agentEntityManager.findAgentPersonInfoSum(agentUser.getUserId(),user.getId(), pagination,agentUserType);
//						}else{
//							addActionMessage("找不到用户名所对应的用户！");
//							pagination = agentEntityManager.findAgentPersonInfoSum(agentUser.getUserId(),null, pagination,agentUserType);
//						}
//					}else{
//						pagination = agentEntityManager.findAgentPersonInfoSum(agentUser.getUserId(),null, pagination,agentUserType);
//					}
//				el = new Element(key, this.pagination);
				agentQueryCache.put(el);
			} else {
				pagination = (Pagination) el.getValue();
			}
			return forward(true, fwd);
		}catch (ServiceException e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		} catch (Exception e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		}
		return forward(false, fwd);
	}
	private static Long dateLimit = Long.valueOf(63);
	public String findAgentPersonInfo(){
		String fwd = "personInfo";
		try{
			User loginUser = getLoginUser();
			if (loginUser == null) {
				addActionMessage("您还没有登录，请先登录！");
				return forward(false, fwd);
			}
			String key = getRequestKey() + loginUser.getId();
			Element el = agentQueryCache.get(key);
			String userId = Struts2Utils.getParameter("userId");
			if (userId == null) {
				addActionMessage("用户Id为空！");
				return forward(false, fwd);
			}
			Struts2Utils.setAttribute("userId", userId);
			User agentUser = userManager.getUser(Long.valueOf(userId));
			if (agentUser == null) {
				addActionMessage("用户为空！");
				return forward(false, fwd);
			}
			String userName = Struts2Utils.getParameter("userName");
			Struts2Utils.setAttribute("userName", userName);
			String dateStartStr = Struts2Utils.getParameter("dateStart");
			Struts2Utils.setAttribute("dateStart", dateStartStr);
			Date dateStart=null;
			
			String fromHour = Struts2Utils.getParameter("fromHour");
			if(StringUtils.isNotBlank(fromHour)){
				Struts2Utils.setAttribute("fromHour", Integer.valueOf(fromHour));
			}
			String dateEndStr = Struts2Utils.getParameter("dateEnd");
			Struts2Utils.setAttribute("dateEnd", dateEndStr);
			Date dateEnd=null;
			
			String toHour = Struts2Utils.getParameter("toHour");
			if(StringUtils.isNotBlank(toHour)){
				Struts2Utils.setAttribute("toHour", Integer.valueOf(toHour));
			}
			if(StringUtils.isNotBlank(dateStartStr)){
				try{
					dateStart = DateUtil.strToDate(dateStartStr,"yyyy-MM-dd");
					dateStart = DateUtils.addHours(dateStart, Integer.valueOf(fromHour));
				}catch(Exception e){
					addActionMessage("开始时间错误！");
					return forward(false, fwd);
				}
			}
			if(StringUtils.isNotBlank(dateEndStr)){
				try{
					dateEnd = DateUtil.strToDate(dateEndStr,"yyyy-MM-dd");
					dateEnd = DateUtils.addHours(dateEnd, Integer.valueOf(toHour));
				}catch(Exception e){
					addActionMessage("开始时间错误！");
					return forward(false, fwd);
				}
			}
			if(null!=dateStart&&null!=dateEnd){
				if (dateEnd.getTime() - dateStart.getTime() > Long.valueOf(1000) * Long.valueOf(60) * Long.valueOf(60) * Long.valueOf(24)
						*dateLimit){
					addActionMessage("结束时间减去开始时间不能大于"+dateLimit+"天！");
					return forward(false, fwd);
				}
			}
			
//			if (el == null) {
//				Map elementMap = Maps.newHashMap();
//				if(StringUtils.isNotBlank(userName)){
//					User user = userManager.getUserBy(userName.trim());
//					if(null!=user){
//						Struts2Utils.setAttribute("userName", user.getUserName());
//						pagination = agentEntityManager.findAgentPersonInfo(agentUser.getUserId(),user.getId(),null==dateStart?null:DateUtil.getHours(dateStart),null==dateEnd?null:DateUtil.getHours(dateEnd), pagination,agentLotteryType);
//						agentPersonInfo = agentEntityManager.countAgentPersonInfo(agentUser.getUserId(),user.getId(),null==dateStart?null:DateUtil.getHours(dateStart),null==dateEnd?null:DateUtil.getHours(dateEnd),agentLotteryType);
//					}else{
//						addActionMessage("找不到用户名所对应的用户！");
//						pagination = agentEntityManager.findAgentPersonInfo(agentUser.getUserId(),null,null==dateStart?null:DateUtil.getHours(dateStart),null==dateEnd?null:DateUtil.getHours(dateEnd), pagination,agentLotteryType);
//						agentPersonInfo = agentEntityManager.countAgentPersonInfo(agentUser.getUserId(),null,null==dateStart?null:DateUtil.getHours(dateStart),null==dateEnd?null:DateUtil.getHours(dateEnd),agentLotteryType);
//					}
//				}else{
//					pagination = agentEntityManager.findAgentPersonInfo(agentUser.getUserId(),null,null==dateStart?null:DateUtil.getHours(dateStart),null==dateEnd?null:DateUtil.getHours(dateEnd), pagination,agentLotteryType);
//					agentPersonInfo = agentEntityManager.countAgentPersonInfo(agentUser.getUserId(),null,null==dateStart?null:DateUtil.getHours(dateStart),null==dateEnd?null:DateUtil.getHours(dateEnd),agentLotteryType);
//				}
//				elementMap.put("pagination", pagination);
//				elementMap.put("agentPersonInfo", agentPersonInfo);
//				el = new Element(key, elementMap);
//				agentQueryCache.put(el);
//			} else {
//				Map elementMap = (HashMap) el.getValue();
//				pagination = (Pagination) elementMap.get("pagination");
//				agentPersonInfo = (AgentPersonInfo) elementMap.get("agentPersonInfo");
//			}
//			return forward(true, fwd);
		}catch (ServiceException e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		} catch (Exception e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		}
		return forward(false, fwd);
	}
	public List<AgentLotteryType> getAgentLotteryTypeList() {
		return agentLotteryTypeList;
	}
	public void setAgentLotteryTypeList(List<AgentLotteryType> agentLotteryTypeList) {
		this.agentLotteryTypeList = agentLotteryTypeList;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public void setAgentUserType(AgentUserType agentUserType) {
		this.agentUserType = agentUserType;
	}
	public List<AgentRebate> getAgentRebateList() {
		return agentRebateList;
	}
	public void setAgentRebateList(List<AgentRebate> agentRebateList) {
		this.agentRebateList = agentRebateList;
	}
	public List<String> getLimit_item() {
		return limit_item;
	}
	public void setLimit_item(List<String> limit_item) {
		this.limit_item = limit_item;
	}
	public List<AgentRebate> getMyAgentRebateList() {
		return myAgentRebateList;
	}
	public void setMyAgentRebateList(List<AgentRebate> myAgentRebateList) {
		this.myAgentRebateList = myAgentRebateList;
	}
	public AgentLotteryType getAgentLotteryType() {
		return agentLotteryType;
	}
	public void setAgentLotteryType(AgentLotteryType agentLotteryType) {
		this.agentLotteryType = agentLotteryType;
	}
	public AgentUserType getAgentUserType() {
		return agentUserType;
	}
}
