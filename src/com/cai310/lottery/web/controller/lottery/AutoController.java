package com.cai310.lottery.web.controller.lottery;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.AutoFollowState;
import com.cai310.lottery.common.AutoFollowType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryUtil;
import com.cai310.lottery.entity.lottery.AutoFollowDetail;
import com.cai310.lottery.entity.lottery.AutoFollowOrder;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.AutoFollowEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.sso.SsoLoginHolder;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.Struts2Utils;

public class AutoController extends LotteryBaseController {
	private static final long serialVersionUID = 6240571640284332544L;

	@Autowired
	UserEntityManager userManager;

	@Autowired
	protected AutoFollowEntityManager autoFollowEntityManager;

	private User user;

	private Lottery lotteryType;
	
	private Lottery[] webLotteryType;
	
	private Integer lotteryPlayType;

	private AutoForm autoForm;

	private AutoFollowOrder entity;
	@Autowired
	protected QueryService queryService;

	protected Pagination pagination = new Pagination(20);

	// 进入首页
	public String index() throws Exception {
		return list();
	}

	/**
	 * 我的跟单
	 */
	public String rules() {
		return "rules";
	}

	/**
	 * 我的跟单
	 */
	public String list() {
		try {
			User loggedUser = getLoginUser();
			if (null == loggedUser) {
				CookieUtil.addReUrlCookie();
				throw new WebDataException("您还没有登录。请先登录.");
			}
			XDetachedCriteria criteria = new XDetachedCriteria(AutoFollowOrder.class, "m");
			criteria.add(Restrictions.eq("m.followUserId", loggedUser.getId()));
			criteria.addOrder(Order.desc("m.id"));
			if (null != this.getAutoForm() && null != autoForm.getLotteryType()) {
				criteria.add(Restrictions.eq("m.lotteryType", autoForm.getLotteryType()));
				if (autoForm.getLotteryPlayType() != null)
					criteria.add(Restrictions.eq("m.lotteryPlayType", autoForm.getLotteryPlayType()));
			}
			this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);
			return "list";
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	/**
	 * 跟单详情
	 */
	public String detailList() {
		try {
			User loggedUser = getLoginUser();
			if (null == loggedUser) {
				CookieUtil.addReUrlCookie();
				throw new WebDataException("您还没有登录。请先登录.");
			}
			if (null == autoForm || null == autoForm.getId()) {
				this.addActionMessage("跟单实体为空");
				return list();
			}
			XDetachedCriteria criteria = new XDetachedCriteria(AutoFollowDetail.class, "m");
			criteria.add(Restrictions.eq("m.followOrderId", autoForm.getId()));
			criteria.addOrder(Order.desc("m.id"));
			this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);
			return "detail-list";
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	private String numberFormatMethod(BigDecimal value) {
		if (null == value)
			return null;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		return nf.format(value);

	}

	public String autoNew() throws Exception {
		try {
			user = SsoLoginHolder.getLoggedUser();
			if (null == user) {
				CookieUtil.addReUrlCookie();
				throw new WebDataException("您还没有登录。请先登录.");
			}
			String userId = Struts2Utils.getParameter("userId");
			if(StringUtils.isNotBlank(userId)){
				try{
					User followUser = this.userManager.getUser(Long.valueOf(userId));
					if(null!=followUser){
						this.autoForm = new AutoForm();
						this.autoForm.setSponsorUserName(followUser.getUserName());
					}
				}catch(Exception e){
					logger.warn(e.getMessage(), e);
				}
			}
			return "new";
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}

	public String doNew() throws Exception {
		try {
			user = SsoLoginHolder.getLoggedUser();
			if (null == user) {
				CookieUtil.addReUrlCookie();
				throw new WebDataException("您还没有登录。请先登录.");
			}
			if (null == autoForm) {
				this.addActionMessage("跟单实体为空");
				return forward(false, "new");
			}
			if (null == autoForm.getLotteryType()) {
				this.addActionMessage("设置彩种为空");
				return forward(false, "new");
			}
			if (null == autoForm.getPeriodMaxFollowCost()) {
				this.addActionMessage("请设置跟单上限");
				return forward(false, "new");
			}
			if (StringUtils.isBlank(autoForm.getSponsorUserName())) {
				this.addActionMessage("跟单人用户名为空,请填写");
				return forward(false, "new");
			}
			User sponsor = userManager.getUserBy(autoForm.getSponsorUserName());
			if (null == sponsor || null == sponsor.getId()) {
				this.addActionMessage("找不到跟单人,请您重新填写");
				return forward(false, "new");
			}
			// 判断是否有跟单
			if (sponsor.getId().equals(user.getId())) {
				this.addActionMessage("跟单人不能是本人");
				return forward(false, "new");
			}

			autoForm.setSponsorUserId(sponsor.getId());
			autoForm.setFollowUserId(user.getId());
			autoForm.setFollowUserName(user.getUserName());
			if (null == autoForm.getFollowType()) {
				this.addActionMessage("请设置跟单种类");
				return forward(false, "new");
			}
			if (AutoFollowType.FUND.equals(autoForm.getFollowType())) {
				if (null == autoForm.getFollowCost()) {
					this.addActionMessage("跟单金额必须输入数字");
					return forward(false, "new");
				}
				try {
					BigDecimal followCostTemp = BigDecimalUtil.valueOf(Double.valueOf(autoForm.getFollowCost()));
					BigDecimal periodMaxFollowCostTemp = BigDecimalUtil.valueOf(Double.valueOf(autoForm
							.getPeriodMaxFollowCost()));
					if (followCostTemp.subtract(periodMaxFollowCostTemp).doubleValue() > Double.valueOf("0")) {
						this.addActionMessage("跟单金额必须少于跟单上限");
						return forward(false, "new");
					}
					autoForm.setFollowCostBigDecimal(followCostTemp);
					autoForm.setPeriodMaxFollowCostBigDecimal(periodMaxFollowCostTemp);
				} catch (Exception e) {
					e.printStackTrace();
					this.addActionMessage("设置跟单或者跟单上限金额出错");
					return forward(false, "new");
				}
				autoForm.setFollowType(AutoFollowType.FUND);
			} else if (AutoFollowType.PERCEND.equals(autoForm.getFollowType())) {
				if (null == autoForm.getFollowPercent()) {
					this.addActionMessage("跟单比例必须输入数字");
					return forward(false, "new");
				}
				try {
					BigDecimal periodMaxFollowCostTemp = BigDecimalUtil.valueOf(Double.valueOf(autoForm
							.getPeriodMaxFollowCost()));
					Integer followPercentTemp = Integer.valueOf(autoForm.getFollowPercent());
					if (followPercentTemp > 99 || followPercentTemp < 1) {
						this.addActionMessage("跟单比例必须输入1-99之间数字");
						return forward(false, "new");
					}
					autoForm.setPeriodMaxFollowCostBigDecimal(periodMaxFollowCostTemp);
					autoForm.setFollowPercentInteger(followPercentTemp);
				} catch (Exception e) {
					e.printStackTrace();
					this.addActionMessage("设置跟单比例跟单上限金额出错");
					return forward(false, "new");
				}

				autoForm.setFollowType(AutoFollowType.PERCEND);
			} else {
				this.addActionMessage("请设置跟单种类");
				return forward(false, "new");
			}
			if (null == autoForm.getPeriodMaxFollowCostBigDecimal()) {
				this.addActionMessage("请设置跟单上限");
				return forward(false, "new");
			}
			if (null == autoForm.getId() || Long.valueOf("-1").equals(autoForm.getId())) {
				XDetachedCriteria criteria = new XDetachedCriteria(AutoFollowOrder.class, "m");
				criteria.add(Restrictions.eq("m.sponsorUserId", sponsor.getId()));
				criteria.add(Restrictions.eq("m.followUserId", user.getId()));
				criteria.add(Restrictions.eq("m.lotteryType", autoForm.getLotteryType()));
				if (autoForm.getLotteryPlayType() != null)
					criteria.add(Restrictions.eq("m.lotteryPlayType", autoForm.getLotteryPlayType()));
				List l = queryService.findByDetachedCriteria(criteria);
				if (null != l && !l.isEmpty()) {
					this.addActionMessage("您已经有设置改彩种跟单人跟单,请您修改该跟单");
					return forward(false, "new");
				}
				// 新增
				entity = new AutoFollowOrder();
				entity.setId(null);
				entity.setFollowCost(autoForm.getFollowCostBigDecimal());
				entity.setFollowPercent(autoForm.getFollowPercentInteger());
				entity.setFollowType(autoForm.getFollowType());
				entity.setFollowUserId(autoForm.getFollowUserId());
				entity.setFollowUserName(autoForm.getFollowUserName());
				entity.setLotteryType(autoForm.getLotteryType());
				entity.setLotteryPlayType(autoForm.getLotteryPlayType());
				entity.setPeriodMaxFollowCost(autoForm.getPeriodMaxFollowCostBigDecimal());
				entity.setSponsorUserId(autoForm.getSponsorUserId());
				entity.setSponsorUserName(autoForm.getSponsorUserName());
				entity.setState(AutoFollowState.RUNNING);
				// 保存用户并放入成功信息.
				entity = autoFollowEntityManager.saveAutoFollowOrder(entity);
				autoForm.setId(entity.getId());
				autoForm.setFollowCost(numberFormatMethod(entity.getFollowCost()));
				autoForm.setFollowPercent(entity.getFollowPercent() == null ? "" : entity.getFollowPercent().toString());
				autoForm.setPeriodMaxFollowCost(numberFormatMethod(entity.getPeriodMaxFollowCost()));
				autoForm.setFollowType(entity.getFollowType());
				autoForm.setFollowUserId(entity.getFollowUserId());
				autoForm.setFollowUserName(entity.getFollowUserName());
				autoForm.setLotteryType(entity.getLotteryType());
				autoForm.setLotteryPlayType(entity.getLotteryPlayType());
				autoForm.setSponsorUserId(entity.getSponsorUserId());
				autoForm.setSponsorUserName(entity.getSponsorUserName());
				this.addActionMessage("新增自动跟单成功");
				return forward(true, "new");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage("设置跟单出错");
			return forward(false, "new");
		}
		return forward(false, "new");
	}

	public String edit() throws Exception {
		user = SsoLoginHolder.getLoggedUser();
		if (null == user) {
			CookieUtil.addReUrlCookie();
			throw new WebDataException("您还没有登录。请先登录.");
		}
		if (null == autoForm) {
			this.addActionMessage("跟单实体为空");
			return list();
		}
		entity = autoFollowEntityManager.getAutoFollowOrder(autoForm.getId());
		if (null == entity) {
			CookieUtil.addReUrlCookie();
			throw new WebDataException("实体错误.");
		}
		autoForm = new AutoForm();
		autoForm.setId(entity.getId());
		autoForm.setFollowCost(numberFormatMethod(entity.getFollowCost()));
		autoForm.setFollowPercent(entity.getFollowPercent() == null ? "" : entity.getFollowPercent().toString());
		autoForm.setPeriodMaxFollowCost(numberFormatMethod(entity.getPeriodMaxFollowCost()));
		autoForm.setFollowType(entity.getFollowType());
		autoForm.setFollowUserId(entity.getFollowUserId());
		autoForm.setFollowUserName(entity.getFollowUserName());
		autoForm.setLotteryType(entity.getLotteryType());
		autoForm.setLotteryPlayType(entity.getLotteryPlayType());
		autoForm.setSponsorUserId(entity.getSponsorUserId());
		autoForm.setSponsorUserName(entity.getSponsorUserName());

		return list();
	}

	public String save() throws Exception {
		try {
			user = SsoLoginHolder.getLoggedUser();
			if (null == user) {
				CookieUtil.addReUrlCookie();
				throw new WebDataException("您还没有登录。请先登录.");
			}
			if (null == autoForm) {
				this.addActionMessage("跟单实体为空");
				return list();
			}
			if (null == autoForm.getLotteryType()) {
				this.addActionMessage("设置彩种为空");
				return list();
			}
			if (null == autoForm.getPeriodMaxFollowCost()) {
				this.addActionMessage("请设置跟单上限");
				return list();
			}
			if (StringUtils.isBlank(autoForm.getSponsorUserName())) {
				this.addActionMessage("跟单人用户名为空,请填写");
				return list();
			}
			User sponsor = userManager.getUserBy(autoForm.getSponsorUserName());
			if (null == sponsor || null == sponsor.getId()) {
				this.addActionMessage("找不到跟单人,请您重新填写");
				return list();
			}
			// 判断是否有跟单
			if (sponsor.getId().equals(user.getId())) {
				this.addActionMessage("跟单人不能是本人");
				return list();
			}

			autoForm.setSponsorUserId(sponsor.getId());
			autoForm.setFollowUserId(user.getId());
			autoForm.setFollowUserName(user.getUserName());
			if (null == autoForm.getFollowType()) {
				this.addActionMessage("请设置跟单种类");
				return list();
			}
			if (AutoFollowType.FUND.equals(autoForm.getFollowType())) {
				if (null == autoForm.getFollowCost()) {
					this.addActionMessage("跟单金额必须输入数字");
					return list();
				}
				try {
					BigDecimal followCostTemp = BigDecimalUtil.valueOf(Double.valueOf(autoForm.getFollowCost()));
					BigDecimal periodMaxFollowCostTemp = BigDecimalUtil.valueOf(Double.valueOf(autoForm
							.getPeriodMaxFollowCost()));
					if (followCostTemp.subtract(periodMaxFollowCostTemp).doubleValue() > Double.valueOf("0")) {
						this.addActionMessage("跟单金额必须少于跟单上限");
						return list();
					}
					autoForm.setFollowCostBigDecimal(followCostTemp);
					autoForm.setPeriodMaxFollowCostBigDecimal(periodMaxFollowCostTemp);
				} catch (Exception e) {
					e.printStackTrace();
					this.addActionMessage("设置跟单或者跟单上限金额出错");
					return list();
				}
				autoForm.setFollowType(AutoFollowType.FUND);
			} else if (AutoFollowType.PERCEND.equals(autoForm.getFollowType())) {
				if (null == autoForm.getFollowPercent()) {
					this.addActionMessage("跟单比例必须输入数字");
					return list();
				}
				try {
					BigDecimal periodMaxFollowCostTemp = BigDecimalUtil.valueOf(Double.valueOf(autoForm
							.getPeriodMaxFollowCost()));
					Integer followPercentTemp = Integer.valueOf(autoForm.getFollowPercent());
					if (followPercentTemp > 99 || followPercentTemp < 1) {
						this.addActionMessage("跟单比例必须输入1-99之间数字");
						return list();
					}
					autoForm.setPeriodMaxFollowCostBigDecimal(periodMaxFollowCostTemp);
					autoForm.setFollowPercentInteger(followPercentTemp);
				} catch (Exception e) {
					e.printStackTrace();
					this.addActionMessage("设置跟单比例跟单上限金额出错");
					return list();
				}

				autoForm.setFollowType(AutoFollowType.PERCEND);
			} else {
				this.addActionMessage("请设置跟单种类");
				return list();
			}
			if (null == autoForm.getPeriodMaxFollowCostBigDecimal()) {
				this.addActionMessage("请设置跟单上限");
				return list();
			}
			if (null == autoForm.getId() || Long.valueOf("-1").equals(autoForm.getId())) {
				this.addActionMessage("请选择修改跟单");
				return list();
			} else {
				// 修改

				entity = autoFollowEntityManager.getAutoFollowOrder(autoForm.getId());
				if (null == entity && null == entity.getId()) {
					this.addActionMessage("自动跟单实体为空");
				}
				if (!user.getId().equals(entity.getFollowUserId())) {
					this.addActionMessage("权限不足");
				}
				XDetachedCriteria criteria = new XDetachedCriteria(AutoFollowOrder.class, "m");
				criteria.add(Restrictions.eq("m.sponsorUserId", sponsor.getId()));
				criteria.add(Restrictions.eq("m.followUserId", user.getId()));
				criteria.add(Restrictions.eq("m.lotteryType", autoForm.getLotteryType()));
				if (autoForm.getLotteryPlayType() != null)
					criteria.add(Restrictions.eq("m.lotteryPlayType", autoForm.getLotteryPlayType()));
				List l = queryService.findByDetachedCriteria(criteria);
				if (null != l && !l.isEmpty()) {
					AutoFollowOrder autoFollowOrderTemp = (AutoFollowOrder) l.get(0);
					if (!entity.getId().equals(autoFollowOrderTemp.getId())) {
						this.addActionMessage("您已经有设置改彩种跟单人跟单,请您修改该跟单");
						return list();
					}
				}
				entity.setFollowCost(autoForm.getFollowCostBigDecimal());
				entity.setFollowPercent(autoForm.getFollowPercentInteger());
				entity.setFollowType(autoForm.getFollowType());
				entity.setFollowUserId(autoForm.getFollowUserId());
				entity.setFollowUserName(autoForm.getFollowUserName());
				entity.setLotteryType(autoForm.getLotteryType());
				entity.setLotteryPlayType(autoForm.getLotteryPlayType());
				entity.setPeriodMaxFollowCost(autoForm.getPeriodMaxFollowCostBigDecimal());
				entity.setSponsorUserId(autoForm.getSponsorUserId());
				entity.setSponsorUserName(autoForm.getSponsorUserName());
				entity = autoFollowEntityManager.saveAutoFollowOrder(entity);
				autoForm.setId(entity.getId());
				autoForm.setFollowCost(numberFormatMethod(entity.getFollowCost()));
				autoForm.setFollowPercent(entity.getFollowPercent() == null ? "" : entity.getFollowPercent().toString());
				autoForm.setPeriodMaxFollowCost(numberFormatMethod(entity.getPeriodMaxFollowCost()));
				autoForm.setFollowType(entity.getFollowType());
				autoForm.setFollowUserId(entity.getFollowUserId());
				autoForm.setFollowUserName(entity.getFollowUserName());
				autoForm.setLotteryType(entity.getLotteryType());
				autoForm.setLotteryPlayType(entity.getLotteryPlayType());
				autoForm.setSponsorUserId(entity.getSponsorUserId());
				autoForm.setSponsorUserName(entity.getSponsorUserName());
				this.addActionMessage("修改自动更单成功");
				return list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage("设置跟单出错");
			return list();
		}
	}

	public String setSate() throws Exception {
		try {
			user = SsoLoginHolder.getLoggedUser();
			if (null == user) {
				CookieUtil.addReUrlCookie();
				throw new WebDataException("您还没有登录。请先登录.");
			}
			if (null == autoForm || null == autoForm.getId()) {
				this.addActionMessage("跟单实体为空");
				return list();
			}
			entity = autoFollowEntityManager.getAutoFollowOrder(autoForm.getId());
			if (null == entity && null == entity.getId()) {
				this.addActionMessage("自动跟单实体为空");
				return list();
			}
			if (!user.getId().equals(entity.getFollowUserId())) {
				this.addActionMessage("权限不足");
				return list();
			}
			if (null == entity.getState()) {
				this.addActionMessage("设置跟单出错");
				return list();
			}
			if (AutoFollowState.RUNNING.equals(entity.getState())) {
				entity.setState(AutoFollowState.STOPED);
				autoFollowEntityManager.saveAutoFollowOrder(entity);
				this.addActionMessage("停用自动更单成功");
				return list();
			} else if (AutoFollowState.STOPED.equals(entity.getState())) {
				entity.setState(AutoFollowState.RUNNING);
				entity = autoFollowEntityManager.saveAutoFollowOrder(entity);
				this.addActionMessage("启用自动更单成功");
				autoForm.setFollowType(entity.getFollowType());
				return list();
			} else {
				this.addActionMessage("设置跟单出错");
				return list();
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.addActionMessage("设置跟单出错");
			return list();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	/**
	 * @return the autoForm
	 */
	public AutoForm getAutoForm() {
		return autoForm;
	}

	/**
	 * @param autoForm the autoForm to set
	 */
	public void setAutoForm(AutoForm autoForm) {
		this.autoForm = autoForm;
	}

	/**
	 * @return the webLotteryType
	 */
	public Lottery[] getWebLotteryType() {
		return LotteryUtil.getWebLotteryList();
	}

	/**
	 * @param webLotteryType the webLotteryType to set
	 */
	public void setWebLotteryType(Lottery[] webLotteryType) {
		this.webLotteryType = webLotteryType;
	}

	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Integer getLotteryPlayType() {
		return lotteryPlayType;
	}

	public void setLotteryPlayType(Integer lotteryPlayType) {
		this.lotteryPlayType = lotteryPlayType;
	}
}
