package com.cai310.lottery.web.controller.lottery;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.event.NewsInfoUpdateEvent;
import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.ChasePlanEntityManager;
import com.cai310.lottery.service.lottery.ChasePlanService;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.spring.SpringContextHolder;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.Struts2Utils;

public class ChaseController extends LotteryBaseController {
	private static final long serialVersionUID = -1997206686806435584L;
	protected Long id;
	protected ChasePlan chasePlan;

	@Autowired
	protected ChasePlanEntityManager chasePlanEntityManager;

	@Autowired
	protected ChasePlanService chasePlanService;

	@Autowired
	protected QueryService queryService;

	protected Pagination pagination = new Pagination(20);

	public String index() {
		return list();
	}

	/**
	 * 我的追号
	 */
	public String list() {
		try {
			User loggedUser = getLoginUser();
			if (null == loggedUser) {
				CookieUtil.addReUrlCookie();
				throw new WebDataException("您还没有登录。请先登录.");
			}
			XDetachedCriteria criteria = new XDetachedCriteria(ChasePlan.class, "m");
			criteria.add(Restrictions.eq("m.userId", loggedUser.getId()));
			criteria.addOrder(Order.desc("m.id"));
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
	 * 停止追号
	 */
	public String stop() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (this.id == null) {
				addActionError("追号ID不能为空.");
				return list();
			}
			User loggedUser = getLoginUser();
			if (loggedUser == null)
				throw new WebDataException("您还没有登录。请先登录.");

			this.chasePlan = chasePlanEntityManager.getChasePlan(this.id);
			if (this.chasePlan == null) {
				addActionError("追号记录不存在.");
				return list();
			}
			if (!this.chasePlan.getUserId().equals(loggedUser.getId())) {
				addActionError("您无权停止该追号.");
				return list();
			}
			synchronized (Constant.STOPCHASE) {
				chasePlanService.stopChasePlan(this.chasePlan.getId());
			}
			map.put("success", true);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		}
		Struts2Utils.renderJson(map);
		return null;
	}

	/**
	 * @return {@link #id}
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the {@link #id} to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return {@link #chasePlan}
	 */
	public ChasePlan getChasePlan() {
		return chasePlan;
	}

	/**
	 * @return {@link #pagination}
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the {@link #pagination} to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
