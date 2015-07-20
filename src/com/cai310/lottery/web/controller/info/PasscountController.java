package com.cai310.lottery.web.controller.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.SchemePasscount;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.ReflectionUtils;

@Action("passcount")
@Results({ @Result(name = "index", location = "/WEB-INF/content/info/passcount/index.ftl") })
public abstract class PasscountController<T extends SchemePasscount, P extends PeriodData> extends BaseController {
	private static final long serialVersionUID = -3004288085663395253L;

	protected List<Period> periods;
	protected Lottery lotteryType;

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	protected Period period;
	protected P periodData;

	public P getPeriodData() {
		return periodData;
	}

	public void setPeriodData(P periodData) {
		this.periodData = periodData;
	}

	protected Class<T> passcountClass;

	protected Class<P> periodClass;
	private Boolean mine;

	public Boolean getMine() {
		return mine;
	}

	public void setMine(Boolean mine) {
		this.mine = mine;
	}

	protected Pagination pagination = new Pagination(20);

	protected Map<String, String> userWinRecordMap = new HashMap<String, String>();

	public Map<String, String> getUserWinRecordMap() {
		return userWinRecordMap;
	}

	public void setUserWinRecordMap(Map<String, String> userWinRecordMap) {
		this.userWinRecordMap = userWinRecordMap;
	}

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

	public abstract PeriodDataEntityManagerImpl<P> getPeriodDataEntityManagerImpl();

	@Autowired
	protected PeriodEntityManager periodEntityManager;

	@Autowired
	protected QueryService queryService;

	@SuppressWarnings("unchecked")
	public PasscountController() {
		this.passcountClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
		this.periodClass = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
	}

	public abstract Lottery getLotteryType();

	public abstract XDetachedCriteria addDetachedCriteria(XDetachedCriteria criteria);

	public String index() throws WebDataException {
		this.lotteryType = getLotteryType();
		if (null == this.queryForm) {
			this.queryForm = new PasscountQueryForm();
		}

		if (this.queryForm.getPeriodNumber() == null) {
			periodData = getPeriodDataEntityManagerImpl().getNewestResultPeriodData();
			period = periodEntityManager.getPeriod(periodData.getPeriodId());
		} else {

			period = periodEntityManager.loadPeriod(getLotteryType(), this.queryForm.getPeriodNumber());
			periodData = getPeriodDataEntityManagerImpl().getPeriodData(period.getId());
		}

		if (period == null)
			throw new WebDataException("期号不存在.");
		if (periodData == null)
			throw new WebDataException("期数据不存在.");

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
		XDetachedCriteria criteria = new XDetachedCriteria(this.passcountClass, "m");
		if (queryForm != null) {
			if (0 != queryForm.getPeriodId())
				criteria.add(Restrictions.eq("m.periodId", queryForm.getPeriodId()));
			if (queryForm.getMode() != null)
				criteria.add(Restrictions.eq("m.mode", queryForm.getMode()));

			if (queryForm.getShareType() != null)
				criteria.add(Restrictions.eq("m.shareType", queryForm.getShareType()));

			if (queryForm.getState() != null)
				criteria.add(Restrictions.eq("m.state", queryForm.getState()));

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
		}
		this.addDetachedCriteria(criteria);
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
