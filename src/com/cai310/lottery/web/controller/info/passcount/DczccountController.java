package com.cai310.lottery.web.controller.info.passcount;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.dczc.DczcPasscount;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.info.PasscountQueryForm;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.CookieUtil;

@Namespace("/" + DczcConstant.KEY)
@Action("passcount")
@Results({ @Result(name = "index", location = "/WEB-INF/content/info/passcount/index.ftl") })
public class DczccountController extends BaseController {
	private static final long serialVersionUID = -3004288085663395253L;
	protected List<Period> periods;
	protected Period period;
	protected Lottery lotteryType;

	public Lottery getLotteryType() {
		return Lottery.DCZC;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	private Boolean mine;

	public Boolean getMine() {
		return mine;
	}

	public void setMine(Boolean mine) {
		this.mine = mine;
	}

	protected Pagination pagination = new Pagination(20);
	protected Integer menuType;

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	/** 方案状态 */
	private SchemeState state;

	protected PasscountQueryForm queryForm;

	@Autowired
	protected QueryService queryService;

	@Autowired
	protected PeriodEntityManager periodEntityManager;

	public String index() throws WebDataException {
		if (this.queryForm == null) {
			this.queryForm = new PasscountQueryForm();
			this.queryForm.setDczcPlayType(com.cai310.lottery.support.dczc.PlayType.SPF);
		}
		
		if (this.queryForm.getPeriodNumber() == null) {
			List<Period> currentPeriods = periodEntityManager.findCurrentPeriods(getLotteryType());
			if (null != currentPeriods && currentPeriods.size() > 0) {
				period = currentPeriods.get(0);
			}
		} else {
			period = periodEntityManager.loadPeriod(Lottery.DCZC, this.queryForm.getPeriodNumber());
		}

		if (period == null)
			throw new WebDataException("期号不存在.");

		periods = periodEntityManager.getDrawPeriodList(getLotteryType(), 20);

		User user = this.getLoginUser();
		if (null != mine && mine) {
			if (null == user) {
				// 先登录
				CookieUtil.addReUrlCookie();
				return GlobalResults.FWD_LOGIN;
			} else {
				this.getQueryForm().setSponsorNames(user.getUserName());
			}
		}

		this.queryForm.setPeriodId(period.getId());
		this.queryForm.setPeriodNumber(period.getPeriodNumber());

		this.pagination = queryService.findByCriteriaAndPagination(buildListDetachedCriteria(), this.pagination);

		return "index";
	}

	public List<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}

	public PasscountQueryForm getQueryForm() {
		return queryForm;
	}

	public void setQueryForm(PasscountQueryForm queryForm) {
		this.queryForm = queryForm;
	}

	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria criteria = new XDetachedCriteria(DczcPasscount.class, "m");
		if (queryForm != null) {
			if (0 != queryForm.getPeriodId()) {
				criteria.add(Restrictions.eq("m.periodId", queryForm.getPeriodId()));
			}

			if (queryForm.getMode() != null) {
				criteria.add(Restrictions.eq("m.mode", queryForm.getMode()));
			}

			if (queryForm.getShareType() != null) {
				criteria.add(Restrictions.eq("m.shareType", queryForm.getShareType()));
			}

			if (queryForm.getState() != null) {
				criteria.add(Restrictions.eq("m.state", queryForm.getState()));
			}

			if (null != queryForm.getDczcPlayType()) {
				criteria.add(Restrictions.eq("m.playType", queryForm.getDczcPlayType()));
			}

			if (StringUtils.isNotBlank(queryForm.getSponsorNames())) {
				String[] arr = queryForm.getSponsorNames().trim().split(",");
				if (arr.length == 1) {
					criteria.add(Restrictions.eq("m.sponsorName", arr[0]));
				} else if (arr.length == 2) {
					criteria.add(Restrictions.or(Restrictions.eq("m.sponsorName", arr[0]),
							Restrictions.eq("m.sponsorName", arr[1])));
				} else {
					criteria.add(Restrictions.in("m.sponsorName", arr));
				}
			}
			criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
			criteria.addOrder(Order.asc("state"));
			criteria.addOrder(Order.desc("finsh"));
			criteria.addOrder(Order.desc("schemePrize"));
			criteria.addOrder(Order.desc("wonCount"));
			criteria.addOrder(Order.desc("passcount"));
			criteria.addOrder(Order.asc("units"));
		}
		return criteria;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public SchemeState getState() {
		return state;
	}

	public void setState(SchemeState state) {
		this.state = state;
	}
}
