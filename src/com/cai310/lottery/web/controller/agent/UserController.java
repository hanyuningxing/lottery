package com.cai310.lottery.web.controller.agent;

import java.util.Calendar;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.user.RegForm;
import com.cai310.lottery.web.controller.user.UserBaseController;
import com.cai310.lottery.web.sso.SsoLoginHolder;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.MD5;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;

@Results({
	@Result(name = "fund-list", location = "/WEB-INF/content/user/agentFundList.ftl")})
public class UserController extends UserBaseController {
	private FundDetailType fundType;
	private RegForm regForm;
	private int timeFrame;
	private Pagination pagination = new Pagination(20);
	@Autowired
	@Qualifier("commonQueryCache")
	private Cache commonQueryCache;
	@Autowired
	private QueryService queryService;
	@Autowired
	AgentEntityManager agentEntityManager;
	private Long userId;
	public String fundList() {
		String fwd = "fund-list";
		try{
			User loginUser = getLoginUser();
			if (loginUser == null) {
				addActionMessage("您还没有登录，请先登录！");
				return forward(false, fwd);
			}
			String key = getRequestKey() + loginUser.getId();
			Element el = commonQueryCache.get(key);
			if (el == null) {
				XDetachedCriteria criteria = new XDetachedCriteria(FundDetail.class, "m");
				
				if(null==userId){
					criteria.add(Restrictions.eq("m.userId", loginUser.getId()));
				}else{
					//查看用户是否是登陆的下属用户
					if(agentEntityManager.isUserGroup(userId, loginUser.getId())){
						criteria.add(Restrictions.eq("m.userId",userId));
					}else{
						addActionMessage("您的登陆账号无法查看该用户资金明细！");
						return forward(false, fwd);
					}
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
					c.add(Calendar.DAY_OF_MONTH, -1);// 1天前
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
				criteria.add(Restrictions.ge("m.createTime", c.getTime()));
	
				criteria.addOrder(Order.desc("m.id"));
	
				pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
				el = new Element(key, this.pagination);
				commonQueryCache.put(el);
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
	
	public String updatePwd() {
		String fwd = "pwd";
		User user = this.getLoginUser();
		try {
			    if (user == null) throw new WebDataException("您还没有登录，请先登录！");
			    if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
					return forward(true, fwd);
				}
				String password = regForm.getOldpassword();
				if(StringUtils.isBlank(password)) throw new WebDataException("旧密码不能为空!");
				if(StringUtils.isBlank(regForm.getConfirmPassword())) throw new WebDataException("确认密码不能为空!");
				if(StringUtils.isBlank(regForm.getPassword())) throw new WebDataException("新密码不能为空!");
				if(!regForm.getPassword().trim().equals(regForm.getConfirmPassword().trim())) throw new WebDataException("新密码和确认密码不相同!");
				if(!(MD5.md5(password.trim()).equals(user.getPassword())))throw new WebDataException("旧密码不正确!");
				if(regForm.getPassword().trim().length()<6)throw new WebDataException("新密码长度不能少于6位!");
				user.setPassword(MD5.md5(regForm.getPassword().trim()).toUpperCase());

				userManager.saveUser(user);
				SsoLoginHolder.login(user.getId());
				addActionError("密码修改成功!");
				return forward(true, fwd);
		}catch (WebDataException e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}catch (ServiceException e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		} catch (Exception e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		}
		return forward(false, fwd);
	}

	//
	/**
	 * 更新用户个人信息
	 * 
	 * @throws WebDataException
	 */
	public String updateInfo() throws WebDataException {
		String fwd = "info";
		User loginUser = getLoginUser();
		try {
			if (loginUser == null)
				throw new WebDataException("您还未登录,请登录后再操作.");

			if ("GET".equals(Struts2Utils.getRequest().getMethod())) {// 转向登录页面
				User user = userManager.getUser(this.getLoginUser().getId());
				if (user.getInfo() == null) {
					UserInfo info = new UserInfo();
					userManager.saveUser(user, info, null);
				}

				if (user.getBank() == null) {
					BankInfo bank = new BankInfo();
					userManager.saveUser(user, null, bank);
				}
				Struts2Utils.getRequest().setAttribute("user", user);
				return forward(true, fwd);
			}
			User user = userManager.getUser(loginUser.getId());
			UserInfo info = user.getInfo();
			String email  =  regForm.getEmail();
			if (StringUtils.isNotBlank(email)) {
				if(StringUtils.isBlank(info.getEmail())){
					email = email.trim();
					if (!email.matches(RegexUtils.Email))
						throw new WebDataException("EMAIL格式错误.");
					info.setEmail(email);
				}else{
					throw new WebDataException("EMAIL已经填写，如要修改请联系客服.");
				}
			}else{
				throw new WebDataException("EMAIL不能为空，请修改.");
			}
			String idcard = regForm.getIdCard();
			if (StringUtils.isNotBlank(idcard)) {
				if(StringUtils.isBlank(info.getIdCard())){
					idcard = idcard.trim();
					if (!idcard.matches(RegexUtils.IdCard))
						throw new WebDataException("身份证格式错误.");
					info.setIdCard(idcard);
				}else{
					throw new WebDataException("身份证已经填写，如要修改请联系客服.");
				}
			}else{
				throw new WebDataException("身份证不能为空，请修改.");
			}
			
			String mobile = regForm.getPhoneNumber();
			
			if (StringUtils.isNotBlank(mobile)) {
				if(StringUtils.isBlank(info.getMobile())){
					mobile = mobile.trim();
					if (!mobile.matches(RegexUtils.Number))
						throw new WebDataException("手机号码输入有误.");
					if (mobile.length()<11)
						throw new WebDataException("手机号码输入有误.");
					info.setMobile(mobile);
				}else{
					throw new WebDataException("手机号码已经填写，如要修改请联系客服.");
				}
			}else{
				throw new WebDataException("手机号码不能为空，请修改.");
			}
			
			

			String realName = regForm.getRealName();
			if (StringUtils.isNotBlank(realName)) {
				if(StringUtils.isBlank(info.getRealName())){
					mobile = mobile.trim();
					int len = realName.getBytes().length;
					if (len < 3 || len > 16)
						throw new WebDataException("真实姓名不合法(3-16个字符,1个汉字相当两个字符).");
					if (!realName.matches(RegexUtils.LetterAndChinese))
						throw new WebDataException("真实姓名必须由英文字母或中文组成.");
					info.setRealName(realName);
				}else{
					throw new WebDataException("真实姓名已经填写，如要修改请联系客服.");
				}
			}else{
				throw new WebDataException("真实姓名不能为空，请修改.");
			}
			userManager.saveUserInfo(info);
			user = userManager.getUser(this.getLoginUser().getId());
			Struts2Utils.getRequest().setAttribute("user", user);
			addActionMessage("操作成功!");
			return forward(true, fwd);
		}catch (WebDataException e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}catch (ServiceException e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		} catch (Exception e) {
			addActionError("网络故障。请稍后再试");
			logger.warn(e.getMessage(), e);
		}
		return forward(false, fwd);
	}
	
	public FundDetailType getFundType() {
		return fundType;
	}
	public void setFundType(FundDetailType fundType) {
		this.fundType = fundType;
	}
	public int getTimeFrame() {
		return timeFrame;
	}
	public void setTimeFrame(int timeFrame) {
		this.timeFrame = timeFrame;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public RegForm getRegForm() {
		return regForm;
	}

	public void setRegForm(RegForm regForm) {
		this.regForm = regForm;
	}

}
