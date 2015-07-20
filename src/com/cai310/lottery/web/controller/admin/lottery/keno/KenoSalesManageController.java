package com.cai310.lottery.web.controller.admin.lottery.keno;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.lottery.ticket.PrintDetail;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.lottery.web.controller.admin.lottery.AdminSchemeQueryForm;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.Struts2Utils;

@Results({
	@Result(name = "save", type = "redirectAction", location = "sales-manager!list.action"),
	@Result(name = "success", location = "/WEB-INF/content/admin/lottery/keno/sales-manager.ftl"),
	@Result(name = "input", location = "/WEB-INF/content/admin/lottery/keno/sales-manager-input.ftl"),
	@Result(name = "chase", location = "/WEB-INF/content/admin/lottery/keno/sales-manager-chase.ftl"),
	@Result(name = "scheme", location = "/WEB-INF/content/admin/lottery/keno/sales-manager-scheme.ftl"),
	@Result(name = "chase-detail", location = "/WEB-INF/content/admin/lottery/keno/sales-manager-chase-detail.ftl"),
	@Result(name = "config", location = "/WEB-INF/content/admin/lottery/keno/sales-manager-config.ftl"),
	@Result(name = "print_detail", location = "/WEB-INF/content/admin/lottery/print_detail.ftl") 
})
public abstract class KenoSalesManageController<I extends KenoPeriod, S extends NumberScheme> extends
		AdminBaseController {
	private static final long serialVersionUID = 8981793102226523639L;

	private Pagination pagination = new Pagination(20);
	protected AdminSchemeQueryForm queryForm;

	protected KenoService<I, S> kenoService;
	protected KenoManager<I, S> kenoManager;
	protected KenoPlayer<I, S> kenoPlayer;
	@Autowired
	protected QueryService queryService;
	
	protected String schemeNum;
	
	protected String periodNumber;
	
	protected ChasePlan chasePlan;
	protected I issueData;
	protected I resultIssueData;
	protected S scheme;
	protected IssueState state;
	protected String pause;
	protected List<I> issueDataList;
	@Autowired
	protected EventLogManager eventLogManager;
	
	protected Long id;

	public abstract void setKenoService(KenoService<I, S> kenoService);

	public abstract void setKenoManager(KenoManager<I, S> kenoManager);

	public abstract void setKenoPlayer(KenoPlayer<I, S> kenoPlayer);

	public abstract Lottery getLottery();
	public Lottery getLotteryType(){
		return getLottery();
	}
	/**
	 * 设置全部结束
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	public String setIssueClose(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
			}
			if (this.id == null) {
				throw new DataException("期号ID不能为空.");
			}
			kenoService.updateIssueState(this.getId(), IssueState.ISSUE_SATE_FINISH);
			I issueData = kenoService.findIssueDataById(this.getId());
			//同传统彩的期实体传数据
			Period period = new Period();
			period.setId(issueData.getId());
			period.setPeriodNumber(issueData.getPeriodNumber());
			// 记录操作日志
			eventLogManager.saveSimpleEventLog(period,getLottery(), adminUser, EventLogType.CloseIssue, "期号为ID："+this.getId());
			map.put("success", true);
			map.put("msg", "期号设置结束成功");
		} catch (DataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "期号设置结束发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}
	public String cancelScheme(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
			}
			if (this.id == null) {
				throw new DataException("方案ID不能为空.");
			}
			S s=kenoService.cancelScheme(id, adminUser);
			// 记录操作日志
			eventLogManager.saveSimpleEventLog(null,getLottery(), adminUser, EventLogType.CancelScheme, "方案为ID："+s.getId()+"方案号为+"+s.getSchemeNumber());
			map.put("success", true);
			map.put("msg", "撤单退款成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "撤单退款发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}
	
	
	
	/**
	 * 强制出票
	 */
	public String batchForcePrint() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
			}
			if (this.id == null) {
				throw new DataException("方案ID不能为空.");
			}
			S s = kenoService.forcePrint(this.id);
			// 记录操作日志
			eventLogManager.saveSimpleEventLog(null,getLottery(), adminUser, EventLogType.ForcePrint, "方案为ID："+s.getId()+"方案号为+"+s.getSchemeNumber());
			map.put("success", true);
			map.put("msg", "强制出票成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "强制出票发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
		
	}

 
	
	
	/**
	 * 设置期时间
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	private Date dateStar;
	private Date dateEnd;
	private Byte oprType;
	private Integer dateMin;
	public String oprIssueTime(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(null==dateStar)throw new WebDataException("开始时间不能为空！");
			if(null==dateEnd)throw new WebDataException("结束时间不能为空！");
			if(dateEnd.getTime()<=dateStar.getTime())throw new WebDataException("结束时间不能少于开始时间！");
			if(dateEnd.getTime()-dateStar.getTime()>1000*60*60*24*3)throw new WebDataException("结束时间减去开始时间不能大于3天！");
			if(null==oprType)throw new WebDataException("操作类型不能为空！");
			if(null==dateMin)throw new WebDataException("操作时间不能为空！");
			if(0>=dateMin)throw new WebDataException("操作时间不能小于0！");
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
			}
			if(Byte.valueOf("0").equals(oprType)){
				dateMin=dateMin;
			}else if(Byte.valueOf("1").equals(oprType)){
				dateMin=-dateMin;
			}else{
				throw new WebDataException("操作类型出错！");
			}
			kenoService.oprIssueTime(dateStar, dateEnd, dateMin);
			// 记录操作日志
			eventLogManager.saveSimpleEventLog(null,getLottery(), adminUser, EventLogType.SetTime, "开始时间为："+dateStar+"结束时间为"+dateEnd+"时间为【"+dateMin+"】分钟");
			map.put("success", true);
			map.put("msg", "期号时间设置结束成功");
		} catch (WebDataException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (ServiceException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "期号时间设置发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}

	/**
	 * 期号管理
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	public String list() throws WebDataException {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			throw new WebDataException("你还没有登录！");
		}
		Class<I> cls = kenoService.getIssueDataClass();
		XDetachedCriteria criteria = new XDetachedCriteria(cls,"s");
		if (queryForm != null) {
			if (StringUtils.isNotBlank(queryForm.getPeriodNumber()))
				criteria.add(Restrictions.eq("s.periodNumber", queryForm.getPeriodNumber()));
			
			if (queryForm.getIssueState() != null)
				criteria.add(Restrictions.eq("s.state", queryForm.getIssueState()));
		}
		criteria.addOrder(Order.desc("s.id"));
		pagination = kenoService.findByCriteriaAndPagination(criteria, pagination);
		pause = kenoService.findPauseOrStart();
		return SUCCESS;
	}
	
	public String saleConfig(){
		pause = kenoService.findPauseOrStart();
		return "config";
	}

	/**
	 * 显示更新期数据页
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	public String edit() throws WebDataException {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			throw new WebDataException("你还没有登录！");
		}
		if (issueData != null && issueData.getId() != null) {
			issueData = kenoService.findIssueDataById(issueData.getId());
		}
		return INPUT;
	}

	/**
	 * 更新开奖号码
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	public String updateResults() throws WebDataException {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			throw new WebDataException("你还没有登录！");
		}
		if (issueData != null && issueData.getId() != null) {
			if (!StringUtils.isBlank(issueData.getResults())) {
				String results = issueData.getResults().trim();
				boolean result=false;
				if(Lottery.KLSF.equals(getLottery())){
					result = results.matches("\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2}");
				}else if(Lottery.EL11TO5.equals(getLottery())||Lottery.SDEL11TO5.equals(getLottery())||Lottery.XJEL11TO5.equals(getLottery())){
					result = results.matches("\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2}");
				}else if(Lottery.SSC.equals(getLottery())){
					result = results.matches("\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2}");
				}else if(Lottery.QYH.equals(getLottery())){
					result = results.matches("\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2},\\d{1,2}");
				}
				if (!result) {
					addActionError("开奖号码格式不正确!");
					return error();
				}
				kenoService.updateResults(issueData);
				addActionMessage("操作成功.");
				I issueDataTemp = kenoService.findIssueDataById(issueData.getId());
				//同传统彩的期实体传数据
				Period period = new Period();
				period.setId(issueDataTemp.getId());
				period.setPeriodNumber(issueDataTemp.getPeriodNumber());
				// 记录操作日志
				eventLogManager.saveSimpleEventLog(period,getLottery(), adminUser, EventLogType.SetResult, "期ID为"+issueData.getId()+"开奖号码为"+results+"期实体为"+issueData.toString());
			}
		}
		return "save";
	}

	/**
	 * 暂停/启动销售
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	public String pause() throws WebDataException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
			}
			kenoService.pauseOrStartLottery();
			pause = kenoService.findPauseOrStart();
			String alertString="";
			if("true".equals(pause)){
				///停止了
				alertString="暂停";
				addActionMessage("操作成功.");
				// 记录操作日志
				eventLogManager.saveSimpleEventLog(null,getLottery(), adminUser, EventLogType.PauseSale, "");
			}else if("false".equals(pause)){
				///启动了
				alertString="启动";
				addActionMessage("操作成功.");
				// 记录操作日志
				eventLogManager.saveSimpleEventLog(null,getLottery(), adminUser, EventLogType.ResumeSale, "");
			}
			map.put("success", true);
			map.put("msg", alertString+"销售成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "暂停/启动销售发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}

	/**
	 * 追号管理
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	public String chase() throws WebDataException {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			throw new WebDataException("你还没有登录！");
		}
//		Class<C> cls = kenoService.getChasePlanClass();
		XDetachedCriteria criteria = new XDetachedCriteria(ChasePlan.class,"s");
		if (queryForm != null) {
			if (StringUtils.isNotBlank(queryForm.getFirstPeriodNumber())){
				I issueData = kenoService.findByIssueNumber(queryForm.getFirstPeriodNumber());
				if(issueData != null){
					criteria.add(Restrictions.eq("s.firstPeriodId", issueData.getId()));
				}
			}

			if (queryForm.getChaseState() != null)
				criteria.add(Restrictions.eq("s.state", queryForm.getChaseState()));
			
			if (queryForm.getWon() != null)
				criteria.add(Restrictions.eq("s.won", queryForm.getWon()));
			
			if (queryForm.getWonStop() != null)
				criteria.add(Restrictions.eq("s.wonStop", queryForm.getWonStop()));
			
			if (StringUtils.isNotBlank(queryForm.getPeriodNumber())){
				I issueData = kenoService.findByIssueNumber(queryForm.getPeriodNumber());
				if(issueData != null){
					criteria.add(Restrictions.eq("s.curPeriodId", issueData.getId()));
				}
			}

			if (StringUtils.isNotBlank(queryForm.getSponsorNames())) {
				String[] arr = queryForm.getSponsorNames().trim().split(",");
				if (arr.length == 1) {
					criteria.add(Restrictions.eq("s.userName", arr[0]));
				} else if (arr.length == 2) {
					criteria.add(Restrictions.or(Restrictions.eq("s.userName", arr[0]), Restrictions.eq(
							"s.userName", arr[1])));
				} else {
					criteria.add(Restrictions.in("s.userName", arr));
				}
			}

		}
		pagination = kenoService.findByCriteriaAndPagination(criteria, pagination);
		return "chase";
	}

	/**
	 * 显示追号详细
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	public String chaseDetail() throws WebDataException {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			throw new WebDataException("你还没有登录！");
		}
		if (chasePlan != null && chasePlan.getId() != null) {
			chasePlan = kenoService.findChasePlanById(chasePlan.getId());
		}
		return "chase-detail";
	}

	/**
	 * 方案管理
	 * 
	 * @return
	 * @throws WebDataException 
	 */
	
	protected XDetachedCriteria criteria = null;
	
	public String scheme() throws WebDataException {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			throw new WebDataException("你还没有登录！");
		} 
		Class<S> cls = kenoService.getSchemeClass();
		if (queryForm != null) {
			if (StringUtils.isNotBlank(queryForm.getPeriodNumber()))
				criteria.add(Restrictions.eq("s.periodNumber", queryForm.getPeriodNumber()));

			
			if (queryForm.getState() != null)
				criteria.add(Restrictions.eq("s.state", queryForm.getState()));
			if (queryForm.getTicketState() != null)
				criteria.add(Restrictions.eq("s.schemePrintState", queryForm.getTicketState()));
			
			if (queryForm.getPrizeSended() != null)
				criteria.add(Restrictions.eq("s.cut", queryForm.getPrizeSended()));
			
			if (queryForm.getWon() != null)
				criteria.add(Restrictions.eq("s.won", queryForm.getWon()));

			if (StringUtils.isNotBlank(queryForm.getSponsorNames())) {
				String[] arr = queryForm.getSponsorNames().trim().split(",");
				if (arr.length == 1) {
					criteria.add(Restrictions.eq("s.sponsorName", arr[0]));
				} else if (arr.length == 2) {
					criteria.add(Restrictions.or(Restrictions.eq("s.sponsorName", arr[0]), Restrictions.eq(
							"s.sponsorName", arr[1])));
				} else {
					criteria.add(Restrictions.in("s.sponsorName", arr));
				}
			}

			if (StringUtils.isNotBlank(queryForm.getSchemeNumbers())) {
				String[] arr = queryForm.getSchemeNumbers().trim().split(",");
				List<Long> idList = new ArrayList<Long>();
				for (String s : arr) {
					Long id = getLottery().getSchemeId(s);
					if (id != null)
						idList.add(id);
				}
				if (idList.size() == 1) {
					criteria.add(Restrictions.eq("s.id", idList.get(0)));
				} else if (idList.size() == 2) {
					criteria.add(Restrictions.or(Restrictions.eq("s.id", idList.get(0)), Restrictions.eq("s.id", idList
							.get(1))));
				} else if (idList.size() > 2) {
					criteria.add(Restrictions.in("s.id", idList));
				}
			}
		}
		criteria.addOrder(Order.desc("s.id"));
		pagination = kenoService.findByCriteriaAndPagination(criteria, pagination);
		return "scheme";
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<I> getIssueDataList() {
		return issueDataList;
	}

	public void setIssueDataList(List<I> issueDataList) {
		this.issueDataList = issueDataList;
	}

	public IssueState getState() {
		return state;
	}

	public void setState(IssueState state) {
		this.state = state;
	}

	public String getPause() {
		return pause;
	}

	public void setPause(String pause) {
		this.pause = pause;
	}

	public AdminSchemeQueryForm getQueryForm() {
		return queryForm;
	}

	public void setQueryForm(AdminSchemeQueryForm queryForm) {
		this.queryForm = queryForm;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dateStar
	 */
	public Date getDateStar() {
		return dateStar;
	}

	/**
	 * @param dateStar the dateStar to set
	 */
	public void setDateStar(Date dateStar) {
		this.dateStar = dateStar;
	}

	/**
	 * @return the dateEnd
	 */
	public Date getDateEnd() {
		return dateEnd;
	}

	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * @return the oprType
	 */
	public Byte getOprType() {
		return oprType;
	}

	/**
	 * @param oprType the oprType to set
	 */
	public void setOprType(Byte oprType) {
		this.oprType = oprType;
	}

	/**
	 * @return the dateMin
	 */
	public Integer getDateMin() {
		return dateMin;
	}

	/**
	 * @param dateMin the dateMin to set
	 */
	public void setDateMin(Integer dateMin) {
		this.dateMin = dateMin;
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
	/**
	 * 统计销售总量跟中奖金额
	 * @return
	 * @throws WebDataException 
	 */
	public String stat() throws WebDataException{
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			throw new WebDataException("你还没有登录！");
		}
		String startDate ="";
		String endDate = "";
		if(StringUtils.isNotBlank(queryForm.getStartDate())){
			startDate = queryForm.getStartDate();
		}
		if(StringUtils.isNotBlank(queryForm.getEndDate())){
			endDate = queryForm.getEndDate();
		}
		List list = kenoService.moneyAnalye(startDate, endDate);
		HashMap result = new HashMap();
		if(null!=list&&list.size()>0){
			result.put("totalSales", (BigDecimal)list.get(0));
			result.put("totalHits", (BigDecimal)list.get(1));
			result.put("success", true);
		}else{		
			result.put("totalSales", "0");
			result.put("totalHits", "0");
			result.put("success", false);
		}
		Struts2Utils.renderJson(result);
		return null;
	}
	
}
