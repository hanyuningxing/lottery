package com.cai310.lottery.web.controller.admin.lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SecurityUserHolder;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.ticket.PrintDetail;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.ReflectionUtils;
import com.cai310.utils.Struts2Utils;

/**
 * 方案管理
 * 
 */
@Results( {
		@Result(name = "success-to-list", type = "redirectAction", params = { "actionName", "scheme", "method", "list",
				"queryForm.periodNumber", "${#request.periodNumber}" }),
		@Result(name = "success-to-show", type = "redirectAction", params = { "actionName", "scheme", "method", "show",
				"schemeId", "${schemeId}" }),
		@Result(name = "list", location = "/WEB-INF/content/admin/lottery/scheme-list.ftl"),
		@Result(name = "show", location = "/WEB-INF/content/admin/lottery/scheme-show.ftl"),
		@Result(name = "print_detail", location = "/WEB-INF/content/admin/lottery/print_detail.ftl") })
public abstract class SchemeController<T extends Scheme> extends AdminBaseController {
	private static final long serialVersionUID = 7439640298867865352L;

	public static final int MAX_SCHEME_LIST_PAGE_SIZE = 200;

	protected Class<T> schemeClass;

	protected AdminSchemeQueryForm queryForm;

	protected Pagination pagination = new Pagination(20);

	@Autowired
	protected QueryService queryService;

	protected long[] checkedSchemeIds;

	protected Long schemeId;
	
	protected String schemeNum;
	
	protected String periodNumber;

	protected T scheme;

	/**
	 * @return 方案的服务实例
	 * @see com.cai310.lottery.service.lottery.SchemeService
	 */
	protected abstract SchemeService<T, ?> getSchemeService();

	protected abstract SchemeEntityManager<T> getSchemeEntityManager();

	@Autowired
	protected EventLogManager eventLogManager;

	/**
	 * @return 所属彩种
	 */
	public abstract Lottery getLotteryType();

	@SuppressWarnings("unchecked")
	public SchemeController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
	}

	public String index() {
		return list();
	}

	public String list() {
		try {
			if (this.pagination.getPageSize() > MAX_SCHEME_LIST_PAGE_SIZE)
				this.pagination.setPageSize(MAX_SCHEME_LIST_PAGE_SIZE);

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
		XDetachedCriteria criteria = new XDetachedCriteria(this.schemeClass, "m");
		if (queryForm != null) {
			if (StringUtils.isNotBlank(queryForm.getPeriodNumber()))
				criteria.add(Restrictions.eq("m.periodNumber", queryForm.getPeriodNumber()));
            if(Lottery.SFZC.equals(getLotteryType())){
            	if(null!=queryForm.getSfzcPlayType()){
            		criteria.add(Restrictions.eq("m.playType", queryForm.getSfzcPlayType()));
            	}
            }
            if(Lottery.WELFARE36To7.equals(getLotteryType())){
            	if(null!=queryForm.getWelfare36to7playType()){
            		criteria.add(Restrictions.eq("m.playType", queryForm.getWelfare36to7playType()));
            	}
            }
            if(Lottery.PL.equals(getLotteryType())){
            	if(null!=queryForm.getPlPlayType()){
            		if(Integer.valueOf(0).equals(queryForm.getPlPlayType())){
            			//pl5
            			criteria.add(Restrictions.eq("m.playType", PlPlayType.P5Direct));
            		}else if(Integer.valueOf(1).equals(queryForm.getPlPlayType())){
            			//pl3
            			criteria.add(Restrictions.not(Restrictions.eq("m.playType", PlPlayType.P5Direct)));
            		}
            		
            	}
            }
            if(Lottery.JCLQ.equals(this.getLotteryType()) || Lottery.JCZQ.equals(this.getLotteryType())){
            	criteria.add(Restrictions.eq("m.lotteryType", this.getLotteryType()));
            }
			if (queryForm.getMode() != null)
				criteria.add(Restrictions.eq("m.mode", queryForm.getMode()));
			if(queryForm.getTicketState()!=null){
				criteria.add(Restrictions.eq("m.schemePrintState", queryForm.getTicketState()));
			}
			if (queryForm.getShareType() != null)
				criteria.add(Restrictions.eq("m.shareType", queryForm.getShareType()));

			if (queryForm.getSecretType() != null)
				criteria.add(Restrictions.eq("m.SecretType", queryForm.getSecretType()));

			if (queryForm.getState() != null)
				criteria.add(Restrictions.eq("m.state", queryForm.getState()));

			if (queryForm.getHasBaodi() != null && queryForm.getHasBaodi())
				criteria.add(Restrictions.gt("m.baodiCost", 0));

			if (queryForm.getPrizeSended() != null)
				criteria.add(Restrictions.eq("m.prizeSended", queryForm.getPrizeSended()));

			if (queryForm.getReserved() != null && queryForm.getReserved())
				criteria.add(Restrictions.eq("m.reserved", queryForm.getReserved()));

			if (queryForm.getUpdateWon() != null) {
				if (queryForm.getUpdateWon())
					criteria.add(Restrictions.ge("m.winningUpdateStatus", WinningUpdateStatus.WINNING_UPDATED));
				else
					criteria.add(Restrictions.eq("m.winningUpdateStatus", WinningUpdateStatus.NONE));
			}

			if (queryForm.getWon() != null)
				criteria.add(Restrictions.eq("m.won", queryForm.getWon()));

			if (queryForm.getOrderPriority() != null)
				criteria.add(Restrictions.eq("m.orderPriority", queryForm.getOrderPriority()));

			if (StringUtils.isNotBlank(queryForm.getSponsorNames())) {
				String[] arr = queryForm.getSponsorNames().trim().split(",");
				if (arr.length == 1) {
					criteria.add(Restrictions.eq("m.sponsorName", arr[0]));
				} else if (arr.length == 2) {
					criteria.add(Restrictions.or(Restrictions.eq("m.sponsorName", arr[0]), Restrictions.eq(
							"m.sponsorName", arr[1])));
				} else {
					criteria.add(Restrictions.in("m.sponsorName", arr));
				}
			}

			if (StringUtils.isNotBlank(queryForm.getSchemeNumbers())) {
				String[] arr = queryForm.getSchemeNumbers().trim().split(",");
				List<Long> idList = new ArrayList<Long>();
				for (String s : arr) {
					Long id = getLotteryType().getSchemeId(s);
					if (id != null)
						idList.add(id);
				}
				if (idList.size() == 1) {
					criteria.add(Restrictions.eq("m.id", idList.get(0)));
				} else if (idList.size() == 2) {
					criteria.add(Restrictions.or(Restrictions.eq("m.id", idList.get(0)), Restrictions.eq("m.id", idList
							.get(1))));
				} else if (idList.size() > 2) {
					criteria.add(Restrictions.in("m.id", idList));
				}
			}

			if (queryForm.getOrder() != null) {
				String prop = "m." + queryForm.getOrder().getPropName();
				Order order;
				if (queryForm.isDescOrder())
					order = Order.desc(prop);
				else
					order = Order.asc(prop);
				criteria.addOrder(order);
			}
		}

		return criteria;
	}

	/**
	 * 恢复默认排序
	 */
	public String keepDefault() {
		return changeOrderPriority(Constant.ORDER_PRIORITY_DEFAULT, EventLogType.keepDefault);
	}

	/**
	 * 置顶
	 */
	public String keepTop() {
		return changeOrderPriority(Constant.ORDER_PRIORITY_TOP, EventLogType.keepTop);
	}

	/**
	 * 置底
	 */
	public String keepBottom() {
		return changeOrderPriority(Constant.ORDER_PRIORITY_BOTTOM, EventLogType.keepBottom);
	}

	/**
	 * 修改排序等级
	 * 
	 * @param orderLevel
	 */
	protected String changeOrderPriority(final byte orderPriority, final EventLogType eventLogType) {
		return runBatchWork(new Work() {
			public String run(long schemeId, AdminUser adminUesr) {
				getSchemeEntityManager().changeOrderPriority(schemeId, orderPriority);
				return "方案[#" + schemeId + "]修改排序等级成功.";
			}

			public EventLogType getEventLogType() {
				return eventLogType;
			}
		});
	}

	/**
	 * 修改保底状态
	 * 
	 * @param reserved 是否保留
	 */
	protected String changeReserved(final boolean reserved, final EventLogType eventLogType) {
		return runBatchWork(new Work() {
			public String run(long schemeId, AdminUser adminUesr) {
				getSchemeEntityManager().changeReserved(schemeId, reserved);
				return "方案[#" + schemeId + "]修改保留状态成功.";
			}

			public EventLogType getEventLogType() {
				return eventLogType;
			}
		});
	}

	/**
	 * 置为保留
	 */
	public String reserved() {
		return changeReserved(true, EventLogType.reserved);
	}

	/**
	 * 取消保留
	 */
	public String cancelReserved() {
		return changeReserved(false, EventLogType.cancelReserved);
	}

	/**
	 * 重置方案为未开奖
	 */
	public String resetUnUpdateWon() {
		return runBatchWork(new Work() {
			public String run(long schemeId, AdminUser adminUesr) {
				getSchemeEntityManager().resetUnUpdateWon(schemeId);
				return "方案[#" + schemeId + "]重置为未开奖状态成功.";
			}

			public EventLogType getEventLogType() {
				return EventLogType.ResetUnUpdateWon;
			}
		});
	}
	
	
	

	/**
	 * 重置方案为已更新中奖
	 */
	public String resetPriceUpdated() {
		return runBatchWork(new Work() {
			public String run(long schemeId, AdminUser adminUesr) {
				getSchemeEntityManager().resetPriceUpdated(schemeId);
				return "方案[#" + schemeId + "]重置为未开奖状态成功.";
			}

			public EventLogType getEventLogType() {
				return EventLogType.ResetUnUpdateWon;
			}
		});
	}

	
	/**
	 * 批量撤单
	 */
	public String batchCancelScheme() {
		return runBatchWork(getCancelSchemeWork());
	}

	/**
	 * 批量强制撤单
	 */
	public String batchForceCancelScheme() {
		return runBatchWork(getForceCancelSchemeWork());
	}

	/**
	 * 批量退款
	 */
	public String batchRefundment() {
		return runBatchWork(getRefundmentWork());
	}
	
	/**
	 * 批量出票
	 */
	public String batchForcePrint() {
		return runBatchWork(getForcePrintWork());
	}

	protected Work getForcePrintWork() {
		return new Work() {
			public String run(long schemeId, AdminUser adminUesr) {
				getSchemeService().forcePrint(schemeId, adminUesr.getId());
				return "方案[#" + schemeId + "]强制出票成功.";
			}

			public EventLogType getEventLogType() {
				return EventLogType.ForcePrint;
			}
		};
	}
	
	/**
	 * 批量操作
	 * 
	 * @param work 具体操作
	 */
	public String runBatchWork(Work work) {
		try {
			if (this.checkedSchemeIds == null || this.checkedSchemeIds.length == 0)
				throw new WebDataException("请选择您要操作的方案！");

			AdminUser adminUesr = SecurityUserHolder.getCurrentUser();
			StringBuilder workMsg = new StringBuilder();
			Date startTime = new Date();
			boolean normalFinish = true;
			int totalCount = checkedSchemeIds.length;
			int runCount = 0;
			int successCount = 0;
			try {
				for (long schemeId : checkedSchemeIds) {
					runCount++;
					try {
						String msg = work.run(schemeId, adminUesr);
						if (StringUtils.isNotBlank(msg)) {
							addActionMessage(msg);
							workMsg.append(msg).append("\r\n");
						}
						successCount++;
					} catch (ServiceException e) {
						addActionMessage(e.getMessage());
					}
				}
			} catch (RuntimeException e) {
				normalFinish = false;
				logger.warn(e.getMessage(), e);
				addActionMessage(e.getMessage());
			} finally {
				StringBuffer sb = new StringBuffer(50);
				sb.append("总共：").append(totalCount).append("，执行了：").append(runCount).append("，成功：")
						.append(successCount);
				addActionMessage(sb.toString());
				workMsg.append("\r\n").append(sb);

				// 记录操作日志
				EventLog eventLog = new EventLog();
				eventLog.setStartTime(startTime);
				eventLog.setDoneTime(new Date());
				eventLog.setLogType(work.getEventLogType().getLogType());
				eventLog.setMsg(workMsg.toString());
				eventLog.setNormalFinish(normalFinish);
				eventLog.setOperator(adminUesr.getName());
				eventLog.setLotteryType(getLotteryType());
				eventLog.setIp(Struts2Utils.getRemoteAddr());
				eventLogManager.saveEventLog(eventLog);
			}

			String periodNumber = Struts2Utils.getParameter("periodNumber");
			if (StringUtils.isNotBlank(periodNumber))
				Struts2Utils.setAttribute("periodNumber", periodNumber);

			String schemeDetail=Struts2Utils.getParameter("schemeDetail");
			if(StringUtils.isNotBlank(schemeDetail)){
				String schemeId = Struts2Utils.getParameter("schemeId");
				if (StringUtils.isNotBlank(schemeId))
					Struts2Utils.setAttribute("schemeId", schemeId);
				return forward(true,"success-to-show");
			}else{
				return forward(true, "success-to-list");
			}
		} catch (WebDataException e) {
			addActionMessage(e.getMessage());
		}

		return error();
	}

	/**
	 * 操作接口
	 * 
	 */
	interface Work {
		/**
		 * 执行操作
		 * 
		 * @param schemeId 方案ID
		 * @param adminUesr 当前的操作员
		 * @return 操作结果信息
		 */
		String run(long schemeId, AdminUser adminUesr);

		/**
		 * @return 事件类型
		 */
		EventLogType getEventLogType();
	}

	public String show() {
		try {
			if (schemeId == null)
				throw new WebDataException("方案ID不能为空.");

			scheme = getSchemeEntityManager().getScheme(schemeId);

			if (scheme == null)
				throw new WebDataException("方案不存在.");
            
			Struts2Utils.setAttribute("contentText", getSchemeContentText());

			Struts2Utils.setAttribute("subscriptionList", getSchemeEntityManager().findSubscriptionsOfScheme(
					scheme.getId(), SubscriptionState.NORMAL));

			Struts2Utils.setAttribute("baodiList", getSchemeEntityManager().findNormalBaodi(scheme.getId()));

			return "show";
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		}

		return GlobalResults.FWD_ERROR;
	}
	public String printDetail() {
			try {
					if (StringUtils.isBlank(schemeNum)){
						throw new WebDataException("方案号不能为空.");
					}
					XDetachedCriteria criteria = new XDetachedCriteria(PrintDetail.class, "m");
					criteria.add(Restrictions.eq("m.schemeNum",schemeNum.trim()));
					this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);
		
					return "print_detail";
			} catch (ServiceException e) {
				addActionError(e.getMessage());
			} catch (Exception e) {
				addActionError(e.getMessage());
				logger.warn(e.getMessage(), e);
			}
			return GlobalResults.FWD_ERROR;
	}
	protected String getSchemeContentText() {
		return scheme.getContentText();
	}

	protected String runWork(Work work) {
		try {
			if (schemeId == null)
				throw new WebDataException("方案ID不能为空.");

			AdminUser adminUesr = SecurityUserHolder.getCurrentUser();
			try {
				Date startTime = new Date();

				String workMsg = work.run(schemeId, adminUesr);

				addActionError(workMsg);

				// 记录操作日志
				EventLog eventLog = new EventLog();
				eventLog.setStartTime(startTime);
				eventLog.setDoneTime(new Date());
				eventLog.setLogType(work.getEventLogType().getLogType());
				eventLog.setMsg(workMsg);
				eventLog.setNormalFinish(true);
				eventLog.setOperator(adminUesr.getName());
				eventLog.setLotteryType(getLotteryType());
				eventLog.setIp(Struts2Utils.getRemoteAddr());
				eventLogManager.saveEventLog(eventLog);
			} catch (Exception e) {
				this.logger.warn("操作发生异常.", e);
				addActionMessage(e.getMessage());
			}

			return forward(true, "success-to-show");
		} catch (WebDataException e) {
			addActionMessage(e.getMessage());
		}
		return error();
	}

	public String cancelScheme() {
		return runWork(getCancelSchemeWork());
	}

	public String forceCancelScheme() {
		return runWork(getForceCancelSchemeWork());
	}

	public String refundment() {
		return runWork(getRefundmentWork());
	}

	protected Work getCancelSchemeWork() {
		return new Work() {

			public EventLogType getEventLogType() {
				return EventLogType.CancelScheme;
			}

			public String run(long schemeId, AdminUser adminUesr) {
				getSchemeService().cancelSchemeByAdmin(schemeId, adminUesr.getId());
				return "方案[#" + schemeId + "]撤单成功.";
			}
		};
	}

	protected Work getForceCancelSchemeWork() {
		return new Work() {

			public EventLogType getEventLogType() {
				return EventLogType.CancelScheme;
			}

			public String run(long schemeId, AdminUser adminUesr) {
				getSchemeService().forceCancelScheme(schemeId, adminUesr.getId());
				return "方案[#" + schemeId + "]撤单成功.";
			}
		};
	}

	protected Work getRefundmentWork() {
		return new Work() {
			public String run(long schemeId, AdminUser adminUesr) {
				getSchemeService().refundment(schemeId);
				return "方案[#" + schemeId + "]退款成功.";
			}

			public EventLogType getEventLogType() {
				return EventLogType.refundment;
			}
		};
	}

	// =================================================================

	/**
	 * @return the {@link #queryForm}
	 */
	public AdminSchemeQueryForm getQueryForm() {
		return queryForm;
	}

	/**
	 * @param queryForm the {@link #queryForm} to set
	 */
	public void setQueryForm(AdminSchemeQueryForm queryForm) {
		this.queryForm = queryForm;
	}

	/**
	 * @return the {@link #pagination}
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

	/**
	 * @return the {@link #checkedSchemeIds}
	 */
	public long[] getCheckedSchemeIds() {
		return checkedSchemeIds;
	}

	/**
	 * @param checkedSchemeIds the {@link #checkedSchemeIds} to set
	 */
	public void setCheckedSchemeIds(long[] checkedSchemeIds) {
		this.checkedSchemeIds = checkedSchemeIds;
	}

	/**
	 * @return {@link #schemeId}
	 */
	public Long getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId the {@link #schemeId} to set
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return {@link #scheme}
	 */
	public T getScheme() {
		return scheme;
	}

	/**
	 * @return the schemeNum
	 */
	public String getSchemeNum() {
		return schemeNum;
	}

	/**
	 * @param schemeNum the schemeNum to set
	 */
	public void setSchemeNum(String schemeNum) {
		this.schemeNum = schemeNum;
	}

	/**
	 * @return the periodNumber
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the periodNumber to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
}
