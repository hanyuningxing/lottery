package com.cai310.lottery.web.controller.admin.security;

import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.AdminEventLogType;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.security.SecurityEntityManager;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;

@Action("log")
public class EventLogController extends AdminBaseController {
	private static final long serialVersionUID = -5503816860106812446L;

	public static final int MAX_PAGE_SIZE = 200;

	private Long id;

	protected Pagination pagination = new Pagination(20);
	private EventLogType logType;
	private Lottery lotteryType;
	private EventLog eventLog;
	private Date beginTime;
	private Date endTime;

	@Autowired
	protected QueryService queryService;

	@Autowired
	protected SecurityEntityManager securityEntityManager;

	public String index() {
		return list();
	}

	public String list() {
		try {
			if (pagination.getPageSize() > MAX_PAGE_SIZE)
				pagination.setPageSize(MAX_PAGE_SIZE);

			this.pagination = queryService.findByCriteriaAndPagination(buildListDetachedCriteria(), this.pagination);

			return "list";
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria criteria = new XDetachedCriteria(EventLog.class,"m");
		if (this.logType != null)
			criteria.add((Restrictions.eq("m.logType", logType.getLogType())));
		if (this.lotteryType != null)
			criteria.add((Restrictions.eq("m.lotteryType", lotteryType)));
		// 不查询出具体日志信息
		ProjectionList propList = Projections.projectionList();
		propList.add(Projections.property("id"), "id");
		propList.add(Projections.property("logType"), "logType");
		propList.add(Projections.property("lotteryType"), "lotteryType");
		propList.add(Projections.property("startTime"), "startTime");
		propList.add(Projections.property("periodNumber"), "periodNumber");
		propList.add(Projections.property("operator"), "operator");
		propList.add(Projections.property("normalFinish"), "normalFinish");
		criteria.setProjection(propList);
		criteria.setResultTransformer(Transformers.aliasToBean(EventLogForm.class));
		return criteria;
	}

	public String show() {
		try {
			if (id == null)
				throw new WebDataException("日志编号为空.");
			eventLog = securityEntityManager.getEventLog(id);
			if (eventLog == null)
				throw new WebDataException("日志不存在.");

			return "show";
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}

	/**
	 * @return the {@link #id}
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
	 * @return the {@link #beginTime}
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the {@link #beginTime} to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the {@link #endTime}
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the {@link #endTime} to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	 * @return the logType
	 */
	public EventLogType getLogType() {
		return logType;
	}

	/**
	 * @param logType the logType to set
	 */
	public void setLogType(EventLogType logType) {
		this.logType = logType;
	}

	/**
	 * @return the eventLog
	 */
	public EventLog getEventLog() {
		return eventLog;
	}

	/**
	 * @param eventLog the eventLog to set
	 */
	public void setEventLog(EventLog eventLog) {
		this.eventLog = eventLog;
	}

	/**
	 * @return the lotteryType
	 */
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the lotteryType to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
}
