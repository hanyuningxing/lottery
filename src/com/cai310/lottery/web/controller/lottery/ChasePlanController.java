package com.cai310.lottery.web.controller.lottery;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.dto.lottery.ChasePlanDetailDTO;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.ChasePlanEntityManager;
import com.cai310.lottery.service.lottery.ChasePlanService;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.ReflectionUtils;

public abstract class ChasePlanController<T extends NumberScheme> extends LotteryBaseController {
	private static final long serialVersionUID = -1997206686806435584L;
	protected Long id;
	protected ChasePlan chasePlan;

	@Autowired
	protected ChasePlanEntityManager chasePlanEntityManager;

	@Autowired
	protected ChasePlanService chasePlanService;

	protected Class<T> schemeClass;
	
	protected List<ChasePlanDetailDTO> chasePlanDetailList;

	public List<ChasePlanDetailDTO> getChasePlanDetailList() {
		return chasePlanDetailList;
	}


	public void setChasePlanDetailList(List<ChasePlanDetailDTO> chasePlanDetailList) {
		this.chasePlanDetailList = chasePlanDetailList;
	}

	@Autowired
	protected QueryService queryService;

	protected Pagination pagination = new Pagination(20);

	@SuppressWarnings("unchecked")
	public ChasePlanController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
	}


	/**
	 * 追号详情
	 */
	public String show() {
		try {
			if (this.id == null)
				throw new WebDataException("追号ID不能为空.");

			User loggedUser = getLoginUser();
			if (loggedUser == null)
				throw new WebDataException("您还没有登录。请先登录.");
			chasePlan = this.chasePlanEntityManager.getChasePlan(this.getId());
			if (this.chasePlan == null)
				throw new WebDataException("追号记录不存在.");
			XDetachedCriteria criteria = new XDetachedCriteria(schemeClass, "m");
			criteria.add(Restrictions.eq("m.chaseId", this.getId()));
			criteria.add(Restrictions.eq("m.sponsorId", loggedUser.getId()));
			criteria.addOrder(Order.desc("m.id"));
			this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);

			return "fwd_chase_list";
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

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
