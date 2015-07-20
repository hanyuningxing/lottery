package com.cai310.lottery.service.lottery.keno.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.TradeSuccessScheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.lottery.keno.KenoSchemeInfo;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.info.TaskInfoDataEntityManager;
import com.cai310.lottery.service.lottery.ChasePlanService;
import com.cai310.lottery.service.lottery.impl.TradeSuccessSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.utils.ChineseDateUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.ReflectionUtils;
import com.cai310.utils.TemplateGenerator;
import com.ibm.icu.util.Calendar;

public abstract class KenoManagerImpl<I extends KenoPeriod, S extends NumberScheme> implements KenoManager<I, S> {
	
	@Autowired
	protected AgentEntityManager agentEntityManager;
	@Autowired
	protected TaskInfoDataEntityManager taskInfoDataEntityManager;
	@Autowired
	protected TradeSuccessSchemeEntityManagerImpl successSchemeManager;

	@Autowired
	protected TicketThenEntityManager ticketThenEntityManager;
	
	protected KenoService<I, S> kenoService;

	protected KenoPlayer<I, S> kenoPlayer;

	@Autowired
	protected ChasePlanService chasePlanService;

	private Class<S> schemeClass;

	private I currentData;

	private boolean firstTime = true;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private QueryService queryService;

	public void setKenoService(KenoService<I, S> kenoService) {
		this.kenoService = kenoService;
	}

	public void setKenoPlayer(KenoPlayer<I, S> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@SuppressWarnings("unchecked")
	public KenoManagerImpl() {
		super();
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(getClass(), 2);
	}

	/**
	 * 销售流程
	 * 
	 * @throws DataException
	 */
	int test=0;
	public void sale() throws DataException {
		test++;
		System.out.println("=========：[" + this.getLottery().getLotteryName() + "]调度任务开启预售期=========="+test);
		createIssueData(); // 开启预售期

		System.out.println("=========：[" + this.getLottery().getLotteryName() + "]调度任务开售当前期=========="+test);
		openCurrentIssue(); // 开售当前期

		System.out.println("=========：[" + this.getLottery().getLotteryName() + "]调度任务生成当前期时间文件=========="+test);
		asyncTime();// 生成当前期时间文件

		System.out.println("=========：[" + this.getLottery().getLotteryName() + "]调度任务截止当前期和过期的期号=========="+test);
		endIssue(); // 截止当前期和过期的期号
		
		System.out.println("=========：[" + this.getLottery().getLotteryName() + "]调度任务更新开奖结果=========="+test);
		updateResultData(); // 更新开奖结果
		
		System.out.println("=========：[" + this.getLottery().getLotteryName() + "]调度任务更新中奖方案=========="+test);
		updatePrizeScheme(); // 更新中奖方案
		
		System.out.println("=========：[" + this.getLottery().getLotteryName() + "]调度任务派奖=========="+test);
		sendPrize(); // 派奖
		
		System.out.println("=========：[" + this.getLottery().getLotteryName() + "]调度任务全部结束=========="+test);
		closeIssue(); // 全部结束
		
		System.out.println("=========：[" + this.getLottery().getLotteryName() + "]调度任务全部结束完毕=========="+test);

		
		if (firstTime) {
			makeResultFile();
			firstTime = false;
		}

	}

	/**
	 * 开启预售期
	 */
	protected void createIssueData() {
		Date beginTime = null;
		Long preIssueId = null;
		I lastIssueData = kenoService.getLastIssue();
		if (lastIssueData != null) {
			beginTime = lastIssueData.getStartTime();
			preIssueId = lastIssueData.getId();
		} else {
			beginTime = kenoPlayer.getDateNow();
		}
		String issueNumber = null;
		I issueData = null;
		Date endTime = ChineseDateUtil.calSaleDay(kenoPlayer.getDateNow(), kenoPlayer.getBeforeSaleDays());
		for (Date day = beginTime; day.before(endTime); day = DateUtils.addDays(day, 1)) {
			if (!ChineseDateUtil.isSaleDay(day)) {
				continue;
			}
			for (int period = 1; period <= kenoPlayer.getMaxPeriod(); period++) {
				issueNumber = kenoPlayer.issueNumberAssembly(day, period); // 组合期号
				issueData = kenoService.findByIssueNumber(issueNumber); // 根据期号加载数据
				if (issueData == null) {
					issueData = kenoPlayer.createNowIssueData(issueNumber);
					issueData.setPeriodNumber(issueNumber);
					issueData.setStartTime(kenoPlayer.getBeginTimeByIssueNumber(issueNumber));
					issueData.setEndedTime(kenoPlayer.getEndTimeByIssueNumber(issueNumber));
					issueData.setPrizeTime(kenoPlayer.getEndTimeByIssueNumber(issueNumber));
					issueData.setState(IssueState.ISSUE_SATE_READY);
					issueData.setPrevPreriodId(preIssueId);
					issueData = kenoService.saveIssueData(issueData);
					preIssueId = issueData.getId();
				}
			}
		}
	}

	/**
	 * 开售当前期
	 */
	protected void openCurrentIssue() {
		Date dateNow = kenoPlayer.getDateNow();
		int beforeTime = kenoPlayer.getBeforeTime();
		I period = kenoService.findCurrentData(dateNow, beforeTime);
		if (period == null) {
			logger.error("=======：当前期没有数据=============");
			return;
		}
		if (currentData != null) {
			if (period.getId().longValue() == currentData.getId().longValue()) {// 已经是当前期,跳过不执行
				return;
			}
		}
		this.currentData = period;// 保证无论以下是否执行有异常,一定要保证有当前期(由于用乐观锁防止第二类数据列新丢失,以下执行可能出错,但是没关系,下次执行会重新更新)
		if (period.getState() != IssueState.ISSUE_SATE_CURRENT) {
			kenoService.updateIssueState(period.getId(), IssueState.ISSUE_SATE_CURRENT);
			logger.info("=========：[" + period.getPeriodNumber() + "]期上升为当前期==========");
		}
	}

	/**
	 * 截止当前期和过期的期号
	 */ 
	protected void endIssue() {
		Date dateNow = kenoPlayer.getDateNow();
		// cyy-c 2011-04-14 时时彩 撤销时间延长
		if(this.getLottery()==Lottery.SSC) { 
			dateNow = DateUtils.addSeconds(dateNow, -40);
		} else {
			dateNow = DateUtils.addSeconds(dateNow, 10);
		}
		List<I> issueDataList = kenoService.findCanCloseIssue(dateNow);
		List<S> schemeList = null;
		if (issueDataList != null && !issueDataList.isEmpty()) {
			for (I issueData : issueDataList) {
				endKenoPeriod(issueData);
			}
		}
	}
	public I endKenoPeriod(I issueData){
		issueData = kenoService.findIssueDataById(issueData.getId());
		if (issueData.getPrevPreriodId() != null) {
			logger.info("==========：处理过期未追的追号==========");
			kenoService.doChasePlan(issueData.getPrevPreriodId(), null);// 对上期未追号的追号进行处理.
		}
		logger.info("==========：处理非中后停追的追号==========");
		// cyy-c 2011-04-14增加未出票撤单功能
		// 读取本期中的投注方案
		List<S> schemeList = null;
		schemeList = kenoService.getSchemeByIssueNumber(issueData.getPeriodNumber().trim());
		if (schemeList != null && !schemeList.isEmpty()) {
			for (S scheme : schemeList) {
				if(scheme.getState().compareTo(SchemeState.SUCCESS)!=0){
					kenoService.commitOrCancelTransaction(scheme.getId());
				}
			}
		}
		kenoService.doChasePlan(issueData.getId(), false);
		issueData.setState(IssueState.ISSUE_SATE_END);
		logger.info("==========：[" + issueData.getPeriodNumber() + "]期上升为截止期==========");
		return kenoService.saveIssueData(issueData);
	}
	/**
	 * 更新开奖结果(主要用于自动读取)
	 */
	protected void updateResultData() {
		List<I> list = kenoService.getCanOpenResults();
		boolean hasupdate = false;
		if (list != null && !list.isEmpty()) {
			for (I issueData : list) {
				try {  
				    String results = autoGetResultData(issueData.getPeriodNumber());
					if (StringUtils.isNotBlank(results)) {
						issueData.setResults(results);
						issueData.setPrizeTime(new Date());
						issueData.setState(IssueState.ISSUE_SATE_RESULT);
						kenoService.saveIssueData(issueData);
						hasupdate = true;
						logger.info("==========：[" + issueData.getPeriodNumber() + "]期上升为开奖期==========");
					}
				} catch (Exception e) {
					e.printStackTrace();
					taskInfoDataEntityManager.updateTaskInfoData(
							TaskState.EXCEPTION,
							TaskType.Keno,
							TaskType.Keno.getTypeName() + "出现错误：" + getLottery().getLotteryName() + "期号为："
									+ issueData.getPeriodNumber() + "捉取开奖号码出错==========" + e.getMessage());
					continue;
				}
			}
		}
		if (hasupdate) {
			makeResultFile();
			
			
		}
	}

	protected void makeResultFile() {
		DetachedCriteria c = DetachedCriteria.forClass(kenoService.getIssueDataClass());
		c.add(Restrictions.in("state", IssueState.ISSUE_RESULT_STATE));
		c.addOrder(Order.desc("id"));
		List<I> list = queryService.findByDetachedCriteria(c, 0, 10);
		List mapList = new ArrayList();
		if (list != null && !list.isEmpty()) {
			for (I issue : list) {
				Map data = new HashMap();
				data.put("id", issue.getId());
				data.put("periodNumber", issue.getPeriodNumber());
				data.put("prizeTime", DateFormatUtils.format(issue.getEndedTime(), "yyyy-MM-dd HH:mm"));
				data.put("result", issue.getResultFormat());
				mapList.add(data);
			}

			String dir = "/keno/" + getLottery().getKey() + "/";
			String fileName = "resultList.xml";
			String value = JSONArray.fromObject(mapList).toString();
			com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
		}
	}

	/**
	 * 自动读取开奖号码
	 * 
	 * @param trim
	 * @return
	 */
	protected abstract String autoGetResultData(String issueNumber) throws DataException;
	protected abstract SchemeDao<S> getSchemeDao();
	/**
	 * 更新中奖方案
	 * 
	 * @throws DataException
	 */
	protected void updatePrizeScheme() throws DataException {
		Date dateNow = kenoPlayer.getDateNow();
		// 读取要更新中奖的期号
		List<I> issueDataList = kenoService.getCanOpenPrize(dateNow);
		List<S> schemeList = null;
		String results = null;
		if (issueDataList != null && !issueDataList.isEmpty()) {
			for (I issueData : issueDataList) {
				updatePeriodPrize(issueData, kenoPlayer);
			}
		}
	}
	public void updatePeriodPrize(I issueData,KenoPlayer kenoPlayer) throws DataException{
		issueData = kenoService.findIssueDataById(issueData.getId());
		String results = issueData.getResults();
		// 读取本期中的投注方案
		List<S> schemeList = null;
		schemeList = kenoService.getSchemeByIssueNumber(issueData.getPeriodNumber().trim());
		if (schemeList != null && !schemeList.isEmpty()) {
			for (S scheme : schemeList) {
					if (!scheme.isWon()) {
						kenoPlayer.calculatePrice(scheme, results);
						if (scheme.isWon()) {
							scheme.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
						}else{
							scheme.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
						}
						kenoService.saveScheme(scheme);
					}
			}
		}

		logger.info("==========：处理开号后停追的追号==========");
		// 找出期间所有设置了 开号后停追的追号
		// 判断投注是否中奖
		// 停追
		List<ChasePlan> chasePlanList = kenoService.getOutNumStopChasePlan(true, issueData);
		if (chasePlanList != null && !chasePlanList.isEmpty()) {
			for (ChasePlan chasePlan : chasePlanList) {
				// 开号后停止 且 未进行追号记录
				if (chasePlan.isOutNumStop() && chasePlan.getChasedSize() < 0) {
					boolean isWon = kenoPlayer.calculatePrice(chasePlan.getMode(), chasePlan.getContent(),
							chasePlan.getNextMultiple(), chasePlan.getPlayType(), results);
					if (isWon) {
						logger.info("==========开号后停追的追号ID======已停====" + chasePlan.getId());
						synchronized (Constant.STOPCHASE) {
							kenoService.stopChasePlan(chasePlan.getId(), "开号停追");
						}
					}
				}
			}
		}

		logger.info("==========：处理中后停追的追号==========");
		kenoService.doChasePlan(issueData.getId(), true);

		// 更新为已更新中奖方案
		kenoService.updateIssueState(issueData.getId(), IssueState.ISSUE_SATE_ACCOUNT_PRIZE);
		logger.info("==========：[" + issueData.getPeriodNumber() + "]期上升为更新中奖状态==========");
	}
	/**
	 * 派奖
	 */
	protected void sendPrize() {
		Date dateNow = kenoPlayer.getDateNow();
		// 读取要更新中奖的期号
		List<I> issueDataList = kenoService.getCanSendPrize(dateNow);
		if (issueDataList != null && !issueDataList.isEmpty()) {
			for (I issueData : issueDataList) {
				this.sendPrize(issueData);
			}
		}

	}
	public void sendPrize(I issueData){
		// 读取本期中的投注方案
		issueData = kenoService.findIssueDataById(issueData.getId());
		List<S>  schemeList = kenoService.findUnSendPrize(issueData.getId());
		if (schemeList != null && !schemeList.isEmpty()) {
			for (S scheme : schemeList) {
				if (scheme.isWon() && !scheme.isPrizeSended()&&scheme.getState().equals(com.cai310.lottery.common.SchemeState.SUCCESS)&&SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())) {
					// 对已经计算方案但尚未派奖的方案进行派奖
					kenoService.sendUserPrice(scheme);
				}else{
					scheme.setPrizeSended(true);
					// zhuhui motify by 2011-05-03 增加 派送奖金时间
					scheme.setPrizeSendTime(new Date());
					// xxxc motify by 2012-09-03 增加 派送奖金状态
					scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
					this.kenoService.saveScheme(scheme);
				}
				//出票同步
				synchronizationTicket(scheme);
			}
		}
		schemeList = kenoService.findUnSendPrize(issueData.getId());
		Boolean allSend = true;
		if (schemeList != null && !schemeList.isEmpty()) {
			for (S scheme : schemeList) {
				if (scheme.isWon() && !scheme.isPrizeSended()&&scheme.getState().equals(com.cai310.lottery.common.SchemeState.SUCCESS)&&SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())) {
					// 对已经计算方案但尚未派奖的方案进行派奖
					allSend = false;
				}
			}
			
		}
		if(allSend){
			// 更新为已派奖方案
			kenoService.updateIssueState(issueData.getId(), IssueState.ISSUE_SATE_SEND_PRIZE);
			logger.info("==========：[" + issueData.getPeriodNumber() + "]期上升为派奖状态==========");
		}

	}
	private void synchronizationTicket(Scheme scheme){
		if (scheme == null)throw new ServiceException("方案不存在.");
		if(null!=scheme.getOrderId()){
			TicketThen ticketThen = this.ticketThenEntityManager.getTicketThenByScheme(scheme);
			if(null==ticketThen)throw new ServiceException("出票队列不存在.");
			//接票方案
			if(scheme.isWon()){
				ticketThen.setWon(true);
				ticketThen.setWonDetail(scheme.getPrizeDetail());
				ticketThen.setPrizeTime(new Date());
				ticketThen.setTotalPrize(scheme.getPrize().doubleValue());
				ticketThen.setTotalPrizeAfterTax(scheme.getPrizeAfterTax());
			}
			this.ticketThenEntityManager.saveTicketThen(ticketThen);
		}
	}
	/**
	 * 全部结束
	 */
	protected void closeIssue() {
		// 更新为已结束
		Date dateNow = kenoPlayer.getDateNow();
		// 读取要更新已结束的期号
		List<I> issueDataList = kenoService.getCanCloseIssue(dateNow);
		if (issueDataList != null && !issueDataList.isEmpty()) {
			for (I issueData : issueDataList) {
				// 更新为已结束
				//kenoService.endAll(issueData);
				
			    ///把事务分拆
				List<S>  unAgentAnalyseSchemeList =  kenoService.getUnAgentAnalyseScheme(issueData.getId());
				if (unAgentAnalyseSchemeList != null && !unAgentAnalyseSchemeList.isEmpty()) {
					for (S kenoScheme : unAgentAnalyseSchemeList) {
						kenoService.agentAnalyse(kenoScheme.getId());
					}
				}	
				unAgentAnalyseSchemeList =  kenoService.getUnAgentAnalyseScheme(issueData.getId());
				if(unAgentAnalyseSchemeList.isEmpty()){
					kenoService.updateIssueState(issueData.getId(), IssueState.ISSUE_SATE_FINISH);
					logger.info("==========：[" + issueData.getPeriodNumber() + "]期上升为全部结束==========");
					issueDataCounter(issueData.getId());
					//zhuhui mofify 2011-09-08  减少数据库负载
					logger.debug("=========：[" + this.getLottery().getLotteryName() + "]调度遗漏生成==========");
					updateMissData(); // 遗漏生成
					logger.debug("=========：[" + this.getLottery().getLotteryName() + "]调度生成右边信息==========");
					writeRightContent();
				}
			}
			//同步出票金额
			ticketThenEntityManager.updateTicketPlatformInfoPrize();
		}
	}

	/**
	 * 期数据统计
	 * 
	 * @param period
	 */
	protected void issueDataCounter(Long id) {
		I issueData = kenoService.findIssueDataById(id);
		// 统计总方案数
		Integer allSchemeCount = kenoService.calAllSchemeCount(id);

		// 统计成功案数
		Integer allSuccessSchemeCount = kenoService.calSuccessSchemeCount(id);

		// 统计总销量
		Integer allSale = kenoService.calAllSaleCount(id);

		// 总中奖数
		Integer allPrize = kenoService.calAllPrizeCount(id);
		issueData.setAllSchemeCount(allSchemeCount);
		issueData.setAllSuccessSchemeCount(allSuccessSchemeCount);
		issueData.setAllSale(allSale);
		issueData.setAllPrize(allPrize);

		// ///销售统计//////
		try {

			kenoService.saleAnalye(issueData);
		} catch (Exception e) {
			e.printStackTrace();
			taskInfoDataEntityManager.updateTaskInfoData(
					TaskState.EXCEPTION,
					TaskType.Keno,
					TaskType.Keno.getTypeName() + "出现错误：" + getLottery().getLotteryName() + "期号为："
							+ issueData.getPeriodNumber() + "销售统计出错==========" + e.getMessage());
		}

		kenoService.saveIssueData(issueData);
	}


	public I getCurrentData() {
		return this.currentData;
	}
	public I getKenoPeriod(Long id){
		return kenoService.findIssueDataById(id);
	}

	/**
	 * 同步服务器时间
	 * 
	 * @return
	 */

	public void asyncTime() {
		Map<String, Object> data = new HashMap<String, Object>();
		Date dateNow = kenoPlayer.getDateNow();
		int beforeTime = kenoPlayer.getBeforeTime(); //分
		int beforeSecondsTime = kenoPlayer.getBeforeSecondsTime(); //秒
		I issueData = this.currentData;
		if (issueData != null) {
			I LastResultIssueData = kenoService.getLastResultIssueData();
			if (null != LastResultIssueData) {
				data.put("lastResultIssue", LastResultIssueData.getPeriodNumber());
				data.put("lastIssueId", LastResultIssueData.getId());
				data.put("lastResult", LastResultIssueData.getResultFormat());
				data.put("lastResultTime",
						DateFormatUtils.format(LastResultIssueData.getPrizeTime(), "yyyy-MM-dd HH:mm"));
			}
			Calendar c = Calendar.getInstance();
			c.setTime(issueData.getEndedTime());
			c.add(Calendar.MINUTE, beforeTime * -1);
			c.add(Calendar.SECOND, beforeSecondsTime * -1);

			data.put("endTime", issueData.getEndedTime().getTime());
			data.put("beforeTime", beforeTime);
			data.put("periodId", issueData.getId());
			data.put("issueNumber", issueData.getPeriodNumber());
			data.put("stateValue", issueData.getState().getStateValue());
			data.put("stateName", issueData.getState().getStateName());
			data.put("resultTime", DateFormatUtils.format(issueData.getEndedTime(), "MM/dd/yyyy HH:mm:ss"));
			data.put("nowTime", DateFormatUtils.format(new Date(), "MM/dd/yyyy HH:mm:ss"));
			data.put("leftTime", DateFormatUtils.format(c.getTime(), "MM/dd/yyyy HH:mm:ss"));
		}
		String dir = "/keno/" + getLottery().getKey() + "/";
		String fileName = "time.js";
		String value = JSONObject.fromObject(data).toString();
		com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
	}

	/**
	 * 生成右边文件
	 * 
	 * @return
	 */
	public void writeRightContent() {

		Map<String, Object> data = new HashMap<String, Object>();
		// //最新开奖
		DetachedCriteria criteria = DetachedCriteria.forClass(kenoService.getIssueDataClass());
		criteria.add(Restrictions.in("state", IssueState.ISSUE_RESULT_STATE));
		criteria.addOrder(Order.desc("id"));
		List<I> iList = queryService.findByDetachedCriteria(criteria, 0, 10,true);
		if (null != iList && !iList.isEmpty()) {
			data.put("newResult", iList.get(0));
			data.put("periodMinute", kenoPlayer.getPeriodMinutes());
			data.put("base", Constant.BASEPATH);
			try {
				createResultFile(data, "new-result-keno.ftl", this.getLottery().getKey() + "-result.html");
			} catch (DataException e) {
				this.logger.error(e.getMessage());
			}
		}
		data.put("resultList", iList);

		// //最新中奖
		criteria = DetachedCriteria.forClass(kenoService.getSchemeClass());
		criteria.add(Restrictions.eq("won", Boolean.TRUE));
		// zhuhui add 2011-05-18避免派奖时间为空的数据排到前面
		criteria.add(Restrictions.isNotNull("prizeSendTime"));
		criteria.addOrder(Order.desc("prizeSendTime"));
		List<S> sList = queryService.findByDetachedCriteria(criteria, 0, 6,true);
		List<KenoSchemeInfo> wInfoList = new ArrayList<KenoSchemeInfo>();
		for (S s : sList) {
			KenoSchemeInfo kenoSchemeInfo = new KenoSchemeInfo();
			if (Lottery.EL11TO5.equals(getLottery())) {
				kenoSchemeInfo.setBetTypeString(((El11to5Scheme) s).getBetTypeString());
			}
			if (Lottery.SDEL11TO5.equals(getLottery())) {
				kenoSchemeInfo.setBetTypeString(((SdEl11to5Scheme) s).getBetTypeString());
			}
			if (Lottery.QYH.equals(getLottery())) {
				kenoSchemeInfo.setBetTypeString(((QyhScheme) s).getBetTypeString());
			}
			if (Lottery.SSC.equals(getLottery())) {
				kenoSchemeInfo.setBetTypeString(((SscScheme) s).getBetTypeString());
			}
			if (Lottery.GDEL11TO5.equals(getLottery())) {
				kenoSchemeInfo.setBetTypeString(((GdEl11to5Scheme) s).getBetTypeString());
			}
			if(Lottery.AHKUAI3.equals(getLottery())){
				kenoSchemeInfo.setBetTypeString(((AhKuai3Scheme) s).getBetTypeString());
			}
			if(Lottery.XJEL11TO5.equals(getLottery())){
				kenoSchemeInfo.setBetTypeString(((XjEl11to5Scheme) s).getBetTypeString());
			}
			kenoSchemeInfo.setSponsorName(s.getSponsorName());
			kenoSchemeInfo.setPrize(s.getPrize());
			wInfoList.add(kenoSchemeInfo);
		}
		data.put("newWon", wInfoList);

		// 日榜
		data.put("todayWon", getWonInfoByTime(DateUtil.getTodayDate(new Date()), null));
		// 周榜
		data.put("weekWon", getWonInfoByTime(DateUtil.getFirstDateOfWeek(new Date()), null));
		// 月榜
		data.put("monthWon", getWonInfoByTime(DateUtil.getMonthFirstDate(new Date()), null));
		// 累积中奖
		criteria = DetachedCriteria.forClass(kenoService.getSchemeClass());
		data.put("sumWon", getWonInfoByTime(null, null));

		String dir = "/keno/" + getLottery().getKey() + "/";
		String fileName = "info.js";
		String value = JSONObject.fromObject(data).toString();
		com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
	}

	public List<KenoSchemeInfo> getWonInfoByTime(Date start, Date end) {
		DetachedCriteria criteria = DetachedCriteria.forClass(kenoService.getSchemeClass());
		if (start != null) {
			criteria.add(Restrictions.gt("prizeSendTime", start));
		}
		if (end != null) {
			criteria.add(Restrictions.lt("prizeSendTime", end));
		}

		criteria.add(Restrictions.eq("won", Boolean.TRUE));
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.groupProperty("sponsorName"));
		prop.add(Projections.sum("prize"), "sumPrize");
		criteria.addOrder(Order.desc("sumPrize"));
		criteria.setProjection(prop);
		List<Object[]> sumList = queryService.findByDetachedCriteria(criteria, 0, 6);
		List<KenoSchemeInfo> sumWonInfoList = new ArrayList<KenoSchemeInfo>();
		for (Object[] object : sumList) {
			KenoSchemeInfo kenoSchemeInfo = new KenoSchemeInfo();
			kenoSchemeInfo.setSponsorName((String) object[0]);
			kenoSchemeInfo.setPrize((BigDecimal) object[1]);
			sumWonInfoList.add(kenoSchemeInfo);
		}
		Collections.sort(sumWonInfoList, new Comparator<KenoSchemeInfo>() {
			public int compare(KenoSchemeInfo o1, KenoSchemeInfo o2) {
				return o2.getPrize().doubleValue() > (o1.getPrize().doubleValue()) ? 1 : 0;
			}
		});
		return sumWonInfoList;
	}

	public void createResultFile(Map<String, Object> contents, String sourcefile, String destinationFileName)
			throws DataException {
		try {
			TemplateGenerator tg = new TemplateGenerator(Constant.ROOTPATH + "/WEB-INF/content/info/results");
			tg.create(sourcefile, contents, destinationFileName, Constant.ROOTPATH + "/html/info/results");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
	}


	public abstract void updateMissData();

	public void saveTradeSuccessScheme(S scheme){
		Long successSchemeId = null;
		TradeSuccessScheme successScheme = successSchemeManager.getScheme(getLottery(), scheme.getId());
		if(successScheme == null){
			successScheme = new TradeSuccessScheme();
		}else{
			successSchemeId = successScheme.getId();
		}
		successScheme.setLotteryType(getLottery());
		successScheme.setSchemeId(scheme.getId());
		try {
			BeanUtils.copyProperties(successScheme, scheme);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		successScheme.setId(successSchemeId);
		if(scheme.getPrize()!=null){
			successScheme.setSchemePrize(scheme.getPrize());
		}
		if(scheme.getPrizeAfterTax()!=null){
			successScheme.setSchemePrizeAfterTax(scheme.getPrizeAfterTax());
		}	
		successSchemeManager.saveScheme(successScheme);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public S getScheme(Long id) {
		return getSchemeDao().get(id);
	}
	public void ticketSynchroned()throws DataException {
		DetachedCriteria criteria=DetachedCriteria.forClass(kenoService.getSchemeClass(), "scheme");
		criteria.add(Restrictions.eq("scheme.scheme_synchroned", Boolean.FALSE));
		criteria.add(Restrictions.eq("scheme.won", Boolean.TRUE));
		criteria.add(Restrictions.eq("scheme.schemePrintState", SchemePrintState.SUCCESS));
		criteria.add(Restrictions.eq("scheme.state", SchemeState.SUCCESS));
		criteria.add(Restrictions.eq("scheme.winningUpdateStatus", WinningUpdateStatus.PRICE_UPDATED));
		criteria.add(Restrictions.eq("scheme.prizeSended", Boolean.TRUE));
		/********************/
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.strToDate("20141208", "yyyyMMdd"));
		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date date=cal.getTime();
		criteria.add(Restrictions.gt("scheme.createTime", date));
		/*******************************/
		List<S> schemeList=queryService.findByDetachedCriteria(criteria);
		if(!schemeList.isEmpty()){
			for(S scheme:schemeList){
				String periodNumber=scheme.getPeriodNumber();
				DetachedCriteria criteria_issue=DetachedCriteria.forClass(kenoService.getIssueDataClass(), "issue");
				criteria_issue.add(Restrictions.eq("issue.periodNumber", periodNumber));
				List<I> issueList=queryService.findByDetachedCriteria(criteria_issue);
				String results=issueList.get(0).getResults();
				Long schemeId=scheme.getId();
				DetachedCriteria criteria_ticket=DetachedCriteria.forClass(Ticket.class, "ticket");
				criteria_ticket.add(Restrictions.eq("ticket.printinterfaceId", schemeId));
				criteria_ticket.add(Restrictions.eq("ticket.won", Boolean.FALSE));
				criteria_ticket.add(Restrictions.isNotNull("ticket.remoteTicketId"));
				List<Ticket> ticketList=queryService.findByDetachedCriteria(criteria_ticket);
				for(Ticket ticket:ticketList){
					kenoPlayer.resetPrice(scheme, results, ticket);
				}
				scheme.setScheme_synchroned(Boolean.TRUE);
				kenoService.saveScheme(scheme);
			}
		}
	}
	
//	public void ticketSynchroned()throws DataException {
//	DetachedCriteria criteria_ticket=DetachedCriteria.forClass(Ticket.class, "ticket");
//	
//	Calendar cal_s = Calendar.getInstance();
//	cal_s.setTime(DateUtil.strToDate("20150207", "yyyyMMdd"));
//	cal_s.set(Calendar.HOUR_OF_DAY, 16);
//	cal_s.set(Calendar.MINUTE, 0);
//	cal_s.set(Calendar.SECOND, 0);
//	Date date_s=cal_s.getTime();
//	
//	Calendar cal_l = Calendar.getInstance();
//	cal_l.setTime(DateUtil.strToDate("20150207", "yyyyMMdd"));
//	cal_l.set(Calendar.HOUR_OF_DAY,19);
//	cal_l.set(Calendar.MINUTE, 0);
//	cal_l.set(Calendar.SECOND, 0);
//	Date date_l=cal_l.getTime();
//	
//	criteria_ticket.add(Restrictions.between("ticket.stateModifyTime",date_s,date_l));
//	criteria_ticket.add(Restrictions.eq("ticket.lotteryType", Lottery.SDEL11TO5));
//	List<Ticket> ticketList=queryService.findByDetachedCriteria(criteria_ticket);
//	List<Ticket> sendTicket=Lists.newArrayList();
//	for(Ticket ticket:ticketList){
//		String periodNumber=ticket.getPeriodNumber();
//		DetachedCriteria criteria_issue=DetachedCriteria.forClass(kenoService.getIssueDataClass(), "issue");
//		criteria_issue.add(Restrictions.eq("issue.periodNumber", periodNumber));
//		List<I> issueList=queryService.findByDetachedCriteria(criteria_issue);
//		String results=issueList.get(0).getResults();
//		
//		DetachedCriteria criteria=DetachedCriteria.forClass(kenoService.getSchemeClass(), "scheme");
//		criteria.add(Restrictions.eq("scheme.id", ticket.getPrintinterfaceId()));
//		List<S> schemeList=queryService.findByDetachedCriteria(criteria);
//		
//		 SdEl11to5PrizeWork ticketPrizeWork = null;
//			try {
//				if (ticket.getMode() == SalesMode.COMPOUND) {
//					ticketPrizeWork = new SdEl11to5PrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
//							((SdEl11to5Scheme) schemeList.get(0)).getBetType());
//				} else {
//					String content = ticket.getContent();
//					if(content.indexOf(",")!=-1){
//						content = content.replaceAll(",","\r\n");
//					}
//					ticketPrizeWork = new SdEl11to5PrizeWork(content, ticket.getMultiple(),results,
//							((SdEl11to5Scheme) schemeList.get(0)).getBetType());
//				}
//				SdEl11to5WinUnits ticketPrizeUnit = ticketPrizeWork.getSdEl11to5Win();
//				if(ticketPrizeUnit.isWon()){
////					ticket.setWon(true);
////					ticket.setTicket_synchroned(Boolean.FALSE);
////					ticket.setTotalPrize(ticketPrizeUnit.getPrize());
////					ticket.setTotalPrizeAfterTax(ticketPrizeUnit.getPrizeAfterTax());
////					ticket.setWonDetail(ticketPrizeUnit.getPrizeDetail());
////					ticketEntityManager.saveTicket(ticket);
//				}else{
//					ticket.setWon(false);
//					ticket.setTicket_synchroned(Boolean.FALSE);
//					ticket.setTotalPrize(0.00);
//					ticket.setTotalPrizeAfterTax(0.00);
//					ticket.setWonDetail(null);
//					ticketEntityManager.saveTicket(ticket);
//					sendTicket.add(ticket);
//					System.out.println("重置");
//				}
//			} catch (DataException e) {
//				e.printStackTrace();
//			}
//	}
//	try {
//		if(!sendTicket.isEmpty()){
//			CpResult cpResult = Win310Util.ResetPrize(sendTicket);
//			for (CpTicket cpTicket : cpResult.getData()) {
//				try {
//					Ticket ticket = this.ticketEntityManager.getTicket(Long.valueOf(cpTicket.getTicketId()));
//					if (cpTicket.getIsSuccess()) {
//						logger.error(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}更新奖金成功，返回代码：" + cpTicket.getCode());
//						ticket.setStateModifyTime(new Date());
//						ticket.setTicket_synchroned(Boolean.TRUE);
//						ticketEntityManager.saveTicket(ticket);
//					} else {
//						ticket.setStateCodeMessage(""+cpTicket.getCode());
//						ticket.setTicket_synchroned(Boolean.FALSE);
//						ticketEntityManager.saveTicket(ticket);
//						logger.error(TicketSupporter.CAI310.getSupporterName() + "{" + ticket.getId() + "}更新奖金出错，返回代码：" + cpTicket.getCode());
//					}
//				} catch (Exception e) {
//					logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票｛" + cpTicket.getTicketId() + "｝更新奖金出错" + e.getMessage());
//					continue;
//				}
//			}
//	}
//	} catch (IOException e) {
//		logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票更新奖金出错" + e.getMessage());
//		e.printStackTrace();
//	} catch (DocumentException e) {
//		logger.error(TicketSupporter.CAI310.getSupporterName() + "彩票更新奖金出错" + e.getMessage());
//		e.printStackTrace();
//	}
//	
//}
///**
// * 返回复式内容解析后的集合
// * 
// * @return
// */
//@SuppressWarnings("unchecked")
//@Transient
//public Collection<SdEl11to5CompoundContent> getCompoundContent(String ticketContent) {
//	Collection<SdEl11to5CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
//			SdEl11to5CompoundContent.class);
//	return c;
//}
}
