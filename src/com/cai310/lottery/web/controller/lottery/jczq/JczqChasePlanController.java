package com.cai310.lottery.web.controller.lottery.jczq;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.jczq.JczqChasePlan;
import com.cai310.lottery.entity.lottery.jczq.JczqChasePlanDetail;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.jczq.JczqChasePlanDetailEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqChasePlanEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeEntityManager;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.LotteryBaseController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + JczqConstant.KEY)
@Action(value = "chasePlan")
public class JczqChasePlanController extends LotteryBaseController{
	private static final long serialVersionUID = -6573346504450841638L;

	@Autowired
	private QueryService queryService;
	
	@Autowired
	private JczqChasePlanDetailEntityManager chasePlanDetailEntityManager;
	
	private Pagination pagination = new Pagination(20);
	
	@Autowired
	private JczqChasePlanEntityManager chasePlanEntityManager;
	
	
	@Autowired
	private JczqSchemeEntityManager schemeEntityManager;
	
	public String index() {
		return list();
	}

	/**
	 * 我的追号
	 */
	public String list() {
		try {
			User loginUser = getLoginUser();
			if (null == loginUser) {
				CookieUtil.addReUrlCookie();
				throw new WebDataException("您还没有登录。请先登录.");
			}
			
			this.pagination = findJczqChasePlansByUserId(loginUser.getId());//已分页
			
			@SuppressWarnings("unchecked")
			List<JczqChasePlan> jczqChasePlans = pagination.getResult();
			for(int i=0; i<jczqChasePlans.size(); i++) {
				JczqChasePlan plan = jczqChasePlans.get(i);
				Long jczqChasePlanId =  plan.getId();
				List<JczqChasePlanDetail> jczqChasePlanDetails = chasePlanDetailEntityManager.getJczqChasePlanDetailByJczqChasePlanId(jczqChasePlanId);
				Double prizeAfterTaxSum = 0.00;
				for(JczqChasePlanDetail detail : jczqChasePlanDetails) {
					Long schemeId = detail.getSchemeId();
					if(schemeId != null) {
						JczqScheme scheme = schemeEntityManager.getScheme(schemeId);
						if(scheme!=null && scheme.getPrizeAfterTax()!=null)
							prizeAfterTaxSum += scheme.getPrizeAfterTax().doubleValue();
					}
				}
				plan.setTotalPrize(BigDecimal.valueOf(prizeAfterTaxSum));
				jczqChasePlans.set(i, plan);
			}
			
			this.pagination.setResult(jczqChasePlans);
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
	 * 
	 * @param id 用户ID
	 * @return Pagination 用户的追投计划(已分页)
	 */
	private Pagination findJczqChasePlansByUserId(long id) {
		XDetachedCriteria criteria = new XDetachedCriteria(JczqChasePlan.class, "m");
		criteria.add(Restrictions.eq("m.userId", id));
		criteria.addOrder(Order.desc("m.id"));
		return queryService.findByCriteriaAndPagination(criteria, this.pagination);
	}
	
	/**
	 * 
	 * 增加追投计划
	 */
	public String add() {
		try {
			User loginUser = getLoginUser();
			if (null == loginUser) {
				CookieUtil.addReUrlCookie();
				throw new WebDataException("您还没有登录。请先登录.");
			}
			Integer returnRateLevel = Integer.valueOf(Struts2Utils.getParameter("returnRate"));//回报率等级
			Integer mutiple = Integer.valueOf(Struts2Utils.getParameter("mutiple"));//起始倍数
			String chasePlanName = Struts2Utils.getParameter("chasePlanName");//追投计划名称									
			
			//新增追投计划
			JczqChasePlan plan = this.addJczqChasePlanForUser(loginUser, returnRateLevel, mutiple, chasePlanName);
			
			//为新增的追投计划增加一条追投详情记录
			this.addJzcqChasePlanDetailFor(plan);
			
			this.jsonMap.put("msg", "恭喜您，追号计划制定成功！");
			return success();
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return faile();
	}
	
	/**
	 * 
	 * @param user 需要增加追投计划的用户
	 * @param returnRateLevel 返奖率
	 * @param mutiple 起始倍数
	 * @param chasePlanName 追投计划名称
	 * @return JczqChasePlan 新增的追投计划
	 */
	private JczqChasePlan addJczqChasePlanForUser(
		User user,
		Integer returnRateLevel,
		Integer mutiple,
		String chasePlanName
	) {
		JczqChasePlan plan = new JczqChasePlan();
		plan.setLotteryType(Lottery.JCZQ);
		plan.setChasePlanName(chasePlanName);
		plan.setUserId(user.getId());
		plan.setUserName(user.getUserName());
		plan.setReturnRateLevel(returnRateLevel);
		plan.setMutiple(mutiple);
		plan.setCreateTime(new Date());
		chasePlanEntityManager.saveChasePlan(plan);
		return plan;
	}
	
	/**
	 * 为某个追投计划新增一条追投详情记录
	 * @param plan 追投计划
	 * @return JczqChasePlanDetail 追投详情
	 */
	private JczqChasePlanDetail addJzcqChasePlanDetailFor(JczqChasePlan plan) {
		JczqChasePlanDetail detail = new JczqChasePlanDetail();		
		detail.setCreateTime(new Date());
		detail.setReturnRateLevel(plan.getReturnRateLevel());
		detail.setMutiple(plan.getMutiple());
		detail.setJczqChasePlanId(plan.getId());
		detail.setLotteryType(Lottery.JCZQ);
		detail.setHasPay(false);
		detail.setUserId(plan.getUserId());
		detail.setUserName(plan.getUserName());
		chasePlanDetailEntityManager.save(detail);
		return detail;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
