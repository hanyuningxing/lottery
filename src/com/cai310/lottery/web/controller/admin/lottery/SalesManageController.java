package com.cai310.lottery.web.controller.admin.lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

import com.cai310.event.ActivityInfoEvent;
import com.cai310.event.WonSchemeUpdateEvent;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SalesState;
import com.cai310.lottery.common.SecurityUserHolder;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.exception.ControllerException;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.CurrentWorkHelper;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.service.lottery.impl.AbstractControllerService;
import com.cai310.lottery.support.WorkCallBack;
import com.cai310.lottery.utils.DwrUtil;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.spring.SpringContextHolder;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;
import com.opensymphony.xwork2.Preparable;

@Result(name = "index", location = "/WEB-INF/content/admin/lottery/sales-manage.ftl")
public abstract class SalesManageController extends AdminBaseController implements Preparable {
	private static final long serialVersionUID = -5219072023169537277L;

	public static final String FWD_SALE_MANAGPeriodSales = "common_sale_manage";

	protected Map<SaleWorkerCmd, Boolean> singleSaleCmdMap;
	protected Map<SaleWorkerCmd, Boolean> polySaleCmdMap;
	protected Map<SaleWorkerCmd, Boolean> prizeCmdMap;
	protected Map<SaleWorkerCmd, Boolean> otherCmdMap;
	protected Map<SaleWorkerCmd, Boolean> afterFinishCmdMap;

	protected List<Object> textArgs = new ArrayList<Object>();
	protected Map<String, Object> tempMap = new HashMap<String, Object>();

	@Resource
	private PeriodEntityManager periodManager;

	@Resource
	private EventLogManager eventLogManager;

	protected abstract SalesControllerService getSalesControllerService();

	protected abstract PrizeControllerService getPrizeControllerService();

	protected abstract DoAnalysisService getDoAnalysisService();

	public abstract Lottery getLotteryType();

	protected Period period;
	protected PeriodSales singleSales;
	protected PeriodSales compoundSales;

	public void prepare() throws Exception {

	}

	public String index() {
		String periodIdStr = Struts2Utils.getRequest().getParameter("id");
		if (StringUtils.isNotBlank(periodIdStr)) {
			periodIdStr = periodIdStr.trim();
			if (periodIdStr.matches(RegexUtils.Number)) {
				init(Integer.parseInt(periodIdStr));
			} else {
				addActionError("参数不对!");
			}
		} else {
			addActionError("参数不对!");
		}
		return "index";
	}

	private void initPeriod(long periodId) {
		period = periodManager.getPeriod(periodId);
		singleSales = periodManager.getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.SINGLE));
		compoundSales = periodManager.getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.COMPOUND));
	}

	/**
	 * 初始化数据
	 * 
	 * @param periodId
	 */
	protected void init(long periodId) {
		try {
			initPeriod(periodId);
		} catch (Exception ex) {
			String errMsg = "获取期数据出错！";
			logger.warn(errMsg, ex);
			addActionError(errMsg);
		}
		if (period != null) {

			Set<SaleWorkerCmd> issueCmds = getSalesControllerService().getCanRunCmds(period);
			Set<SaleWorkerCmd> singleSaleCmds = getSalesControllerService().getCanRunCmds(singleSales);
			Set<SaleWorkerCmd> polySaleCmds = getSalesControllerService().getCanRunCmds(compoundSales);
			singleSaleCmdMap = new LinkedHashMap<SaleWorkerCmd, Boolean>();
			for (SaleWorkerCmd cmd : getSaleProcessCmds(singleSales)) {
				singleSaleCmdMap.put(cmd, singleSaleCmds.contains(cmd));
			}
			polySaleCmdMap = new LinkedHashMap<SaleWorkerCmd, Boolean>();
			for (SaleWorkerCmd cmd : getSaleProcessCmds(compoundSales)) {
				polySaleCmdMap.put(cmd, polySaleCmds.contains(cmd));
			}
			
			prizeCmdMap = new LinkedHashMap<SaleWorkerCmd, Boolean>();
			for (SaleWorkerCmd cmd : getPrizeProcessCmds()) {
				prizeCmdMap.put(cmd, issueCmds.contains(cmd));
			}
			otherCmdMap = new LinkedHashMap<SaleWorkerCmd, Boolean>();
			for (SaleWorkerCmd cmd : getOtherCmds()) {
				otherCmdMap.put(cmd, issueCmds.contains(cmd));
			}
			afterFinishCmdMap = new LinkedHashMap<SaleWorkerCmd, Boolean>();
			for (SaleWorkerCmd cmd : getAfterFinishCmds()) {
				afterFinishCmdMap.put(cmd, issueCmds.contains(cmd));
			}
		} else {
			addActionError("期数据不存在!");
		}
	}

	/**
	 * @return 销售流程相关操作
	 */
	protected Set<SaleWorkerCmd> getSaleProcessCmds(PeriodSales issueSale) {
		Set<SaleWorkerCmd> saleProcessCmds = new LinkedHashSet<SaleWorkerCmd>();
		saleProcessCmds.add(SaleWorkerCmd.BeginSale);
		saleProcessCmds.add(SaleWorkerCmd.EndSale);
		saleProcessCmds.add(SaleWorkerCmd.CommitPayment);
		return saleProcessCmds;
	}

	/**
	 * @return 开奖流程相关操作
	 */
	protected Set<SaleWorkerCmd> getPrizeProcessCmds() {
		Set<SaleWorkerCmd> prizeProcessCmds = new LinkedHashSet<SaleWorkerCmd>();
		prizeProcessCmds.add(SaleWorkerCmd.UpdateResult);
		prizeProcessCmds.add(SaleWorkerCmd.UpdatePrize);
		prizeProcessCmds.add(SaleWorkerCmd.DelivePrize);
		prizeProcessCmds.add(SaleWorkerCmd.CloseIssue);
		return prizeProcessCmds;
	}

	/**
	 * @return 其他相关操作
	 */
	protected Set<SaleWorkerCmd> getOtherCmds() {
		Set<SaleWorkerCmd> otherCmds = new LinkedHashSet<SaleWorkerCmd>();
		otherCmds.add(SaleWorkerCmd.PauseSale);
		otherCmds.add(SaleWorkerCmd.ResumeSale);
		otherCmds.add(SaleWorkerCmd.HideIssue);
		otherCmds.add(SaleWorkerCmd.ShowIssue);
		return otherCmds;
	}

	/**
	 * @return 结束后操作
	 */
	protected Set<SaleWorkerCmd> getAfterFinishCmds() {
		Set<SaleWorkerCmd> cmds = new LinkedHashSet<SaleWorkerCmd>();
		// TODO:这里可以增加一些期结束后的相关操作
		//cmds.add(SaleWorkerCmd.SaleAnalyse);
		return cmds;
	}

	/**
	 * 执行操作 使用DWR，通过javascript调用此方法，启用DWR推模式，在执行操作过程中，不断向页面输出操作信息
	 * 
	 * @param periodId
	 * @param lotteryType
	 * @param cmd
	 */
	public void runWork(int periodId, String salesModeName, String cmd) {
		SalesMode salesMode = null;
		if (StringUtils.isNotBlank(salesModeName)) {
			salesMode = SalesMode.valueOf(salesModeName);
		}
		Util util = new Util(WebContextFactory.get().getScriptSession());// WebContextFactory.get().getScriptSession()获取与当前页面绑定的ScriptSession，用于从服务器向客户端推送信息
		DwrUtil dwrUtil = new DwrUtil(util);
		Date startTime = new Date();
		try {

			if (StringUtils.isNotBlank(cmd)) {
				SaleWorkerCmd workCmd = SaleWorkerCmd.valueOfIgnoreCase(cmd);
				if (workCmd != null) {

					// *********** 初始化数据 ***********
					dwrUtil.sendInfoMsg("正在初始化数据...");
					init(periodId);
					if (hasActionErrors()) {
						for (Object errorMsg : getActionErrors()) {
							dwrUtil.sendErrorMsg(errorMsg.toString());
						}
						dwrUtil.sendErrorMsg("初始化数据失败！");
						return;
					}

					// *********** 判断是否允许执行操作 ***********
					boolean canRun = false;
					try {
						canRun = getSalesControllerService().canRun(period.getId(), salesMode, workCmd);
						if (canRun) {
							checkCanRun(salesMode, workCmd);
						}
					} catch (DataException ex) {
						dwrUtil.sendErrorMsg(ex.getMessage());
						return;
					} catch (Exception ex) {
						dwrUtil.sendErrorMsg("判断是否允许执行操作出错退出。");
						logger.warn("判断是否允许执行操作出错。", ex);
						return;
					}
					if (!canRun) {
						dwrUtil.sendErrorMsg("当期操作不允许执行!");
						return;
					}

					AdminUser adminUesr = SecurityUserHolder.getCurrentUser();
					if (adminUesr == null) {
						dwrUtil.sendErrorMsg("您还未登录!");
						return;
					}
					
					
					EventLog workLog = new EventLog(getLotteryType(), period.getId(), period.getPeriodNumber(), workCmd
							.getEventLogType().getLogType(), adminUesr.getLoginName(),"");

					// *********** 把操作设为当前操作 ***********
					CurrentWorkHelper workHelper = AbstractControllerService.getCurrentWorkHelper(getLotteryType());
					boolean pushSuccess = workHelper.pushCurrentWork(workLog, dwrUtil);
					if (!pushSuccess) {
						dwrUtil.sendErrorMsg("当前正在执行【"
								+ SaleWorkerCmd.valueOfStatus(workHelper.getCurrentWork().getLogType())
								+ "】操作，请稍候再试...");
						return;
					}

					// *********** 保存操作日志 ***********
					dwrUtil.sendInfoMsg("正在创建操作日志...");
					try {
						// 保存操作日志
						workLog.setStartTime(new Date());
						workLog = eventLogManager.saveEventLog(workLog);
					} catch (Exception ex) {
						logger.warn("创建操作日志出错。", ex);
						dwrUtil.sendErrorMsg("创建操作日志出错。");
						workHelper.removeCurrentWork();// 移除当前操作
						return;
					}
					dwrUtil.sendInfoMsg("操作日志创建成功，日志编号：" + workLog.getId());

					// *********** 执行具体操作 ***********
					try {
						WorkCallBack workCallBack = getWorkCallBack(periodId, salesMode, workCmd);
						if (workCallBack == null) {
							throw new Exception("找不到相应的操作！");
						}
						workCallBack.run();// 执行操作
					} catch (ControllerException ex) {
						dwrUtil.sendErrorMsg(ex.getMessage());
						workLog.setNormalFinish(Boolean.FALSE);
					} catch (Exception ex) {
						logger.warn("执行【" + workCmd.getCmdName() + "】操作出错。", ex);
						dwrUtil.sendErrorMsg(ex.getMessage());
						workLog.setNormalFinish(Boolean.FALSE);
					} finally {
						dwrUtil.sendInfoMsg("正在更新操作日志...");
						tempMap.clear();
						tempMap.put("doneTime", new Date());
						tempMap.put("normalFinish",
								(workLog.getNormalFinish() == null) ? Boolean.TRUE : workLog.getNormalFinish());
						tempMap.put("msg", dwrUtil.getAllMsg());
						try {
							eventLogManager.updateEventLog(tempMap, workLog.getId());
						} catch (Exception ex) {
							String errorMsg = "更新操作日志出错。";
							logger.warn(errorMsg, ex);
							dwrUtil.sendErrorMsg(errorMsg);
						}
						workHelper.removeCurrentWork();// 无论操作是否成功，必须移除当前操作，这样其他操作才能执行
					}
				} else {// 操作类型不对
					dwrUtil.sendErrorMsg("操作类型不对");
				}
			} else {// 操作类型为空
				dwrUtil.sendErrorMsg("操作类型为空");
			}
		} finally {
			// *********** 重新获取可执行的操作类型，更新页面按钮的可用状态 ***********
			try {
				initPeriod(periodId);
			} catch (Exception ex) {
				String errorMsg = "重新获取期数据出错。";
				logger.warn(errorMsg, ex);
				dwrUtil.sendErrorMsg(errorMsg);
			}
			if (period != null) {
				// 获取可执行的操作类型
				Set<SaleWorkerCmd> issueCmds = getSalesControllerService().getCanRunCmds(period);
				Set<SaleWorkerCmd> singleSaleCmds = getSalesControllerService().getCanRunCmds(singleSales);
				Set<SaleWorkerCmd> polySaleCmds = getSalesControllerService().getCanRunCmds(compoundSales);
				List<String> list = new ArrayList<String>();
				for (SaleWorkerCmd saleWorkerCmd : getSaleProcessCmds(singleSales)) {
					if (singleSaleCmds.contains(saleWorkerCmd)) {
						list.add(getSinglePrefix() + saleWorkerCmd.toString());
					}
				}
				for (SaleWorkerCmd saleWorkerCmd : getSaleProcessCmds(compoundSales)) {
					if (polySaleCmds.contains(saleWorkerCmd)) {
						list.add(getPolyPrefix() + saleWorkerCmd.toString());
					}
				}
				for (SaleWorkerCmd saleWorkerCmd : getPrizeProcessCmds()) {
					if (issueCmds.contains(saleWorkerCmd)) {
						list.add(getPrizePrefix() + saleWorkerCmd.toString());
					}
				}
				for (SaleWorkerCmd saleWorkerCmd : getOtherCmds()) {
					if (issueCmds.contains(saleWorkerCmd)) {
						list.add(getOtherPrefix() + saleWorkerCmd.toString());
					}
				}
				for (SaleWorkerCmd saleWorkerCmd : getAfterFinishCmds()) {
					if (issueCmds.contains(saleWorkerCmd)) {
						list.add(getAfterFinishPrefix() + saleWorkerCmd.toString());
					}
				}
				if (!list.isEmpty()) {
					dwrUtil.updateButtonState(list.toArray(new String[0]));// 更新操作按钮可用状态
				}

				Map<String, String> statusMap = new HashMap<String, String>();
				statusMap.put(getIssueStatusMark(), period.getState().getStateName());
				SalesState state = singleSales.getState();
				if (state != null) {
					statusMap.put(getSingleStatusMark(), state.getStateName());
				}
				state = compoundSales.getState();
				if (state != null) {
					statusMap.put(getPolyStatusMark(), state.getStateName());
				}
				dwrUtil.updateStatusText(statusMap);// 更新页面销售状态显示
			}

			dwrUtil.callWorkEnd(new Date().getTime() - startTime.getTime());// 通知页面，操作结束
		}
	}

	/**
	 * 检查是否允许执行操作
	 * 
	 * @param periodId
	 * @param lotteryType
	 * @param workCmd
	 * @throws DataException
	 */
	protected void checkCanRun(SalesMode salesMode, SaleWorkerCmd workCmd) throws DataException {
		if (workCmd == SaleWorkerCmd.EndSale) {
			PeriodSales periodSale = getPeriodSale(salesMode);
			Date now = new Date();
			if (now.before(periodSale.getSelfEndInitTime())) {
				throw new DataException("还未到自购发起截止时间！");
			} else if (now.before(periodSale.getShareEndInitTime())) {
				throw new DataException("还未到合买发起截止时间！");
			} else if (now.before(periodSale.getEndJoinTime())) {
				throw new DataException("还未到认购截止时间！");
			}
		}
		if (workCmd == SaleWorkerCmd.CommitPayment&&null!=period) {
			Date now = new Date();
			if (now.before(period.getEndedTime())) {
				throw new DataException("还未到官方截止时间！");
			} 
		}
	}

	/**
	 * 获取执行操作的回调
	 * 
	 * @param periodId
	 *            期编号
	 * @param lotteryType
	 *            彩种号
	 * @param workCmd
	 *            操作类型
	 * @return 执行操作的回调
	 */
	protected WorkCallBack getWorkCallBack(final long periodId, final SalesMode salesMode, final SaleWorkerCmd workCmd) {
		switch (workCmd) {
		case BeginSale:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().beginSaleWork(new PeriodSalesId(periodId, salesMode));
				}
			};
		case PauseSale:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().pauseSaleWork(periodId);
				}
			};
		case ResumeSale:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().resumeSaleWork(periodId);
				}
			};
		case EndSale:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().endSaleWork(new PeriodSalesId(periodId, salesMode));
				}
			};
		case Baodi:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().baodiWork(new PeriodSalesId(periodId, salesMode));
				}
			};
		case CommitPayment:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().commitPaymentWork(new PeriodSalesId(periodId, salesMode));
					
				}
			};
		case HideIssue:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().hideIssueWork(periodId);
				}
			};
		case ShowIssue:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().showIssueWork(periodId);
				}
			};
		case UpdateResult:
			return new WorkCallBack() {
				public void run() {
					try {
						getPrizeControllerService().updateResultWork(periodId);
					} catch (Exception e) {
						e.printStackTrace();
						logger.warn("<<<<<<<<<<<<<<<<<<更新中奖出错<<<<<<<<<<<",  e);
					}
				}
			};
		case UpdatePrize:
			return new WorkCallBack() {
				public void run() {
					getPrizeControllerService().updatePrizeWork(periodId);
				}
			};
		case DelivePrize:
			return new WorkCallBack() {
				public void run() {
					getPrizeControllerService().delivePrizeWork(periodId);
					
					try{
						///////更新统计信息
						getSalesControllerService().saleAnalyseWork(periodId);
					} catch(Exception e) {
						e.printStackTrace();
						logger.warn("<<<<<<<<<<<<<<<<<<更新统计信息出错<<<<<<<<<<<",  e);
					}
				}
			};
		case CloseIssue:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().closePeriodWork(periodId);
				}
			};
		case SaleAnalyse:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().saleAnalyseWork(periodId);
				}
			};
		default:
			return null;
		}
	}

	private PeriodSales getPeriodSale(SalesMode salesMode) {
		if (singleSales != null && singleSales.getId().getSalesMode() == salesMode) {
			return singleSales;
		} else if (compoundSales != null && compoundSales.getId().getSalesMode() == salesMode) {
			return compoundSales;
		}
		return null;
	}

	// ************************ getter or setter method ************************

	public Period getPeriod() {
		return period;
	}

	public PeriodSales getSingleSales() {
		return singleSales;
	}

	public PeriodSales getCompoundSales() {
		return compoundSales;
	}

	public Map<SaleWorkerCmd, Boolean> getSingleSaleCmdMap() {
		return singleSaleCmdMap;
	}

	public Map<SaleWorkerCmd, Boolean> getPolySaleCmdMap() {
		return polySaleCmdMap;
	}

	public Map<SaleWorkerCmd, Boolean> getPrizeCmdMap() {
		return prizeCmdMap;
	}

	public Map<SaleWorkerCmd, Boolean> getOtherCmdMap() {
		return otherCmdMap;
	}

	public Map<SaleWorkerCmd, Boolean> getAfterFinishCmdMap() {
		return afterFinishCmdMap;
	}

	public static final String SinglePrefix = "single_";
	public static final String PolyPrefix = "poly_";
	public static final String PrizePrefix = "prizePrefix_";
	public static final String OtherPrefix = "other_";
	public static final String AfterFinishPrefix = "afterFinish_";
	public static final String IssueStatusMark = "issueStatusText";
	public static final String SingleStatusMark = "singleStatusText";
	public static final String PolyStatusMark = "polyStatusText";
	public static final Set<String> statusMarks = new HashSet<String>(3);
	static {
		statusMarks.add(IssueStatusMark);
		statusMarks.add(SingleStatusMark);
		statusMarks.add(PolyStatusMark);
	}

	public String getSinglePrefix() {
		return SinglePrefix;
	}

	public String getPolyPrefix() {
		return PolyPrefix;
	}

	public String getPrizePrefix() {
		return PrizePrefix;
	}

	public String getOtherPrefix() {
		return OtherPrefix;
	}

	public String getAfterFinishPrefix() {
		return AfterFinishPrefix;
	}

	public String getIssueStatusMark() {
		return IssueStatusMark;
	}

	public String getSingleStatusMark() {
		return SingleStatusMark;
	}

	public String getPolyStatusMark() {
		return PolyStatusMark;
	}

	public Set<String> getStatusMarks() {
		return statusMarks;
	}

	// *************************************************

}
