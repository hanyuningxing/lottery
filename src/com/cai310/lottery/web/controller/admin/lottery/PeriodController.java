package com.cai310.lottery.web.controller.admin.lottery;

import java.rmi.ConnectException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SalesState;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.GameManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.rmi.RemoteDataQueryRMIOfTicket;
import com.cai310.lottery.ticket.protocol.response.dto.IssueInfo;
import com.cai310.lottery.utils.liangcai.LiangCaiUtil;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;
import com.opensymphony.xwork2.Preparable;

@Results( {
		@Result(name = "success", type = "redirectAction", params = { "actionName", "period", "method", "edit", "id",
				"${id}" }), @Result(name = "list", location = "/WEB-INF/content/admin/lottery/period-list.ftl"),
		@Result(name = "editNew", location = "/WEB-INF/content/admin/lottery/period-editNew.ftl"),
		@Result(name = "edit", location = "/WEB-INF/content/admin/lottery/period-edit.ftl") })
public abstract class PeriodController extends AdminBaseController implements Preparable {
	private static final long serialVersionUID = 8612043426244564977L;

	protected Long id;

	protected Period period;

	protected Pagination pagination = new Pagination(20);

	protected PeriodSales singlePeriodSales;
	
	protected String periodNumber;

	protected PeriodSales compoundPeriodSales;
	@Autowired
	protected RemoteDataQueryRMIOfTicket remoteDataQuery;
	@Autowired
	protected PeriodEntityManager periodManager;

	@Autowired
	protected QueryService queryService;
	
	@Autowired
	protected GameManager gameManager;

	public abstract Lottery getLotteryType();

	// ----------------------------------------------------------------------
	public void prepare() throws Exception {
	}

	public String index() {
		return list();
	}
	/**
	 * 取的赛事颜色
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	public String gameColors() throws WebDataException {
		Map<String, Object> map = new HashMap<String, Object>();
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			throw new WebDataException("你还没有登录！");
		}
		map.put("gameColors",gameManager.getGameColor());
		Struts2Utils.renderJson(map);
		return null;
	}
	public String list() {
		XDetachedCriteria criteria = new XDetachedCriteria(Period.class);
		criteria.add(Restrictions.eq("lotteryType", getLotteryType()));
		criteria.addOrder(Order.desc("id"));
		criteria.setCacheable(true);
		criteria.setCacheRegion(CacheConstant.H_PERIOD_QUERY_CACHE_REGION);
		pagination = queryService.findByCriteriaAndPagination(criteria, pagination);
		return "list";
	}
	private IssueInfo remoteIssue;
	private String gameIssue;
	private boolean advance=Boolean.FALSE;
	/**
	 * 同步远程数据
	 */
    public void updateRemoteIssueData(){
    	
    	remoteIssue = remoteDataQuery.getIssueInfo(getLotteryType(),null);
    }
    public String checkRemoteIssueData(){
    	Map<String, Object> data = new HashMap<String, Object>();
    	remoteIssue = remoteDataQuery.getIssueInfo(getLotteryType(),null);
    	Boolean sameIssue = Boolean.FALSE;
    	
    	if(null!=remoteIssue){
    		if(StringUtils.isNotBlank(gameIssue)){
	    		if(gameIssue.trim().equals(remoteIssue.getGameIssue().trim())){
	    			sameIssue = Boolean.TRUE;
	    		}
    		}
    	}
    	data.put("sameIssue",sameIssue);
		Struts2Utils.renderJson(data);
		return null;
    }
	public String editNew() {
		try {
			period = new Period();
			singlePeriodSales = new PeriodSales();
			compoundPeriodSales = new PeriodSales();
			if(!advance){
				remoteIssue=LiangCaiUtil.LiangFecthPeriodNumber(getLotteryType());
	//		    updateRemoteIssueData();
				if(null!=remoteIssue){
					period.setPeriodNumber(remoteIssue.getGameIssue());
					period.setEndedTime(remoteIssue.getEndTime());
					period.setPrizeTime(remoteIssue.getEndTime());
					singlePeriodSales.setEndJoinTime(remoteIssue.getSingleEndTime());
					singlePeriodSales.setSelfEndInitTime(remoteIssue.getSingleEndTime());
					singlePeriodSales.setShareEndInitTime(remoteIssue.getSingleEndTime());
					compoundPeriodSales.setEndJoinTime(remoteIssue.getCompoundEndTime());
					compoundPeriodSales.setSelfEndInitTime(remoteIssue.getCompoundEndTime());
					compoundPeriodSales.setShareEndInitTime(remoteIssue.getCompoundEndTime());
				}
			}
			return "editNew";
		}catch (ServiceException e) {
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	public String edit() throws Exception {
		try {
			period = periodManager.getPeriod(id);
			singlePeriodSales = periodManager.getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.SINGLE));
			compoundPeriodSales = periodManager.getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.COMPOUND));

			return doEdit();
		} catch (ServiceException e) {
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	protected String doEdit() {
		return "edit";
	}

	public String create() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("权限不足！");
			}
			if (period == null)
				throw new WebDataException("期数据为空.");
			if (singlePeriodSales == null)
				throw new WebDataException("单式销售配置为空.");
			if (compoundPeriodSales == null)
				throw new WebDataException("复式销售配置为空.");

			if (StringUtils.isBlank(period.getPeriodNumber()))
				throw new WebDataException("期号不能为空.");
			else if (period.getPeriodNumber().length() > 20)
				throw new WebDataException("期号长度不能超过20个字符.");
			else if (!period.getPeriodNumber().matches(RegexUtils.Number))
				throw new WebDataException("期号只能由数字组成.");

			if (singlePeriodSales.getShareEndInitTime() == null)
				throw new WebDataException("单式合买发起截止时间为空.");
			if (singlePeriodSales.getSelfEndInitTime() == null)
				throw new WebDataException("单式自购发起截止时间为空.");
			if (singlePeriodSales.getEndJoinTime() == null)
				throw new WebDataException("单式认购截止时间为空.");

			if (compoundPeriodSales.getShareEndInitTime() == null)
				throw new WebDataException("复式合买发起截止时间为空.");
			if (compoundPeriodSales.getSelfEndInitTime() == null)
				throw new WebDataException("复式自购发起截止时间为空.");
			if (compoundPeriodSales.getEndJoinTime() == null)
				throw new WebDataException("复式认购截止时间为空.");

			period.setState(PeriodState.Inited);
			period.setLotteryType(getLotteryType());
			singlePeriodSales.setLotteryType(period.getLotteryType());
			singlePeriodSales.setState(SalesState.NOT_BEGUN);
			singlePeriodSales.setId(new PeriodSalesId(SalesMode.SINGLE));
			compoundPeriodSales.setLotteryType(period.getLotteryType());
			compoundPeriodSales.setState(SalesState.NOT_BEGUN);
			compoundPeriodSales.setId(new PeriodSalesId(SalesMode.COMPOUND));
			period = periodManager.createNewPeriod(period, singlePeriodSales, compoundPeriodSales);

			id = period.getId();
			addActionMessage("操作成功.");
			// 记录操作日志
			eventLogManager.saveSimpleEventLog(period,getLotteryType(), adminUser, EventLogType.NewPeriod, "期Id:【"+id+"】,期实体："+period.toString());
			return success();
		} catch (WebDataException e) {
			addActionMessage(e.getMessage());
		} catch (ServiceException e) {
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}

	public void prepareUpdate() throws Exception {
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotBlank(id) && id.matches(RegexUtils.Number)) {
			period = periodManager.getPeriod(Long.valueOf(id));
			if (period != null) {
				singlePeriodSales = periodManager.getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.SINGLE));
				compoundPeriodSales = periodManager
						.getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.COMPOUND));
			}
		}
	}

	public String update() throws Exception {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("权限不足！");
			}
			if ("true".equalsIgnoreCase(Struts2Utils.getParameter("updatePeriod"))) {
				if (period == null)
					throw new WebDataException("期数据为空.");
				if (period.getId() == null)
					throw new WebDataException("期ID为空.");

				if (StringUtils.isBlank(period.getPeriodNumber()))
					throw new WebDataException("期号不能为空.");
				else if (period.getPeriodNumber().length() > 20)
					throw new WebDataException("期号长度不能超过20个字符.");
				else if (!period.getPeriodNumber().matches(RegexUtils.Number))
					throw new WebDataException("期号只能由数字组成.");

				period = periodManager.savePeriod(period);
				addActionMessage("更新期数据成功.");
			}

			if ("true".equalsIgnoreCase(Struts2Utils.getParameter("updateSinglePeriodSales"))) {
				if (singlePeriodSales == null)
					throw new WebDataException("单式销售配置为空.");
				if (singlePeriodSales.getId() == null)
					throw new WebDataException("单式销售配置ID为空.");

				if (singlePeriodSales.getShareEndInitTime() == null)
					throw new WebDataException("单式合买发起截止时间为空.");
				if (singlePeriodSales.getSelfEndInitTime() == null)
					throw new WebDataException("单式自购发起截止时间为空.");
				if (singlePeriodSales.getEndJoinTime() == null)
					throw new WebDataException("单式认购截止时间为空.");

				singlePeriodSales = periodManager.savePeriodSales(singlePeriodSales);
				addActionMessage("更新单式销售配置成功.");
			}

			if ("true".equalsIgnoreCase(Struts2Utils.getParameter("updateCompoundPeriodSales"))) {
				if (compoundPeriodSales == null)
					throw new WebDataException("复式销售配置为空.");
				if (compoundPeriodSales.getId() == null)
					throw new WebDataException("复式销售配置ID为空.");

				if (compoundPeriodSales.getShareEndInitTime() == null)
					throw new WebDataException("复式合买发起截止时间为空.");
				if (compoundPeriodSales.getSelfEndInitTime() == null)
					throw new WebDataException("复式自购发起截止时间为空.");
				if (compoundPeriodSales.getEndJoinTime() == null)
					throw new WebDataException("复式认购截止时间为空.");

				compoundPeriodSales = periodManager.savePeriodSales(compoundPeriodSales);
				addActionMessage("更新复式销售配置成功.");
			}

			period = periodManager.getPeriod(period.getId());
			singlePeriodSales = periodManager.getPeriodSales(singlePeriodSales.getId());
			compoundPeriodSales = periodManager.getPeriodSales(compoundPeriodSales.getId());
			// 记录操作日志
			eventLogManager.saveSimpleEventLog(period,getLotteryType(), adminUser, EventLogType.UpdatePeriod, "期Id:【"+id+"】,期实体："+period.toString()+"单式实体："+singlePeriodSales.toString()+"复式实体："+compoundPeriodSales.toString());
			this.id = period.getId();
			return success();
		} catch (WebDataException e) {
			addActionMessage(e.getMessage());
		} catch (ServiceException e) {
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}

	// ----------------------------------------------------------------------
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
	 * @return {@link #period}
	 */
	public Period getPeriod() {
		return period;
	}

	/**
	 * @param period the {@link #period} to set
	 */
	public void setPeriod(Period period) {
		this.period = period;
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

	/**
	 * @return {@link #singlePeriodSales}
	 */
	public PeriodSales getSinglePeriodSales() {
		return singlePeriodSales;
	}

	/**
	 * @param singlePeriodSales the {@link #singlePeriodSales} to set
	 */
	public void setSinglePeriodSales(PeriodSales singlePeriodSales) {
		this.singlePeriodSales = singlePeriodSales;
	}

	/**
	 * @return {@link #compoundPeriodSales}
	 */
	public PeriodSales getCompoundPeriodSales() {
		return compoundPeriodSales;
	}

	/**
	 * @param compoundPeriodSales the {@link #compoundPeriodSales} to set
	 */
	public void setCompoundPeriodSales(PeriodSales compoundPeriodSales) {
		this.compoundPeriodSales = compoundPeriodSales;
	}
	public IssueInfo getRemoteIssue() {
		return remoteIssue;
	}

	public void setRemoteIssue(IssueInfo remoteIssue) {
		this.remoteIssue = remoteIssue;
	}

	public String getGameIssue() {
		return gameIssue;
	}

	public void setGameIssue(String gameIssue) {
		this.gameIssue = gameIssue;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public boolean isAdvance() {
		return advance;
	}

	public void setAdvance(boolean advance) {
		this.advance = advance;
	}

}
