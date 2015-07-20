package com.cai310.lottery.web.controller.admin.lottery;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.event.LotteryResultUpdateEvent;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.info.IndexInfoDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.dlt.impl.DltMissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlMissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.seven.impl.SevenMissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqMissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5MissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7MissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dMissDataControllerServiceImpl;
import com.cai310.lottery.service.rmi.RemoteDataQueryRMIOfTicket;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.spring.SpringContextHolder;
import com.cai310.utils.Struts2Utils;

public abstract class PeriodAndDataController extends PeriodController {
	@Resource
	protected IndexInfoDataEntityManager indexInfoDataEntityManager;
	
	@Autowired
	protected RemoteDataQueryRMIOfTicket remoteDataQuery;
	
	@Resource
	protected DltMissDataControllerServiceImpl dltMissDataControllerServiceImpl;
	@Resource
	protected SsqMissDataControllerServiceImpl ssqMissDataControllerServiceImpl;
	@Resource
	protected Welfare3dMissDataControllerServiceImpl welfare3dMissDataControllerServiceImpl;
	@Resource
	protected PlMissDataControllerServiceImpl plMissDataControllerServiceImpl;
	@Resource
	protected SevenMissDataControllerServiceImpl sevenMissDataControllerServiceImpl;
	@Resource
	protected Tc22to5MissDataControllerServiceImpl tc22to5MissDataControllerServiceImpl;
	@Resource
	protected Welfare36to7MissDataControllerServiceImpl welfare36to7MissDataControllerServiceImpl;

	private static final long serialVersionUID = -3706703513472829136L;

	private PeriodData periodData;

	protected abstract PeriodDataEntityManager getPeriodDataEntityManager();
	
	private Integer num1;
	private Integer num2;
	private Integer num3;
	private Integer num4;
	private Integer num5;
	private Integer num6;
	private Integer num7;
	private Integer num8;
	protected abstract void getResultNum()throws Exception;
	
	protected abstract void setResultNum()throws Exception;
	
	protected abstract String getResultNumber() throws Exception;

	@Override
	public void prepareUpdate() throws Exception {
		super.prepareUpdate();
		if (period != null) {
			periodData = getPeriodDataEntityManager().getPeriodData(period.getId());
			if (periodData == null) {
				periodData = getPeriodDataEntityManager().getEntityInstance();
				periodData.setPeriodId(period.getId());
			}
			if(null!=periodData){
				//periodData.setResult(remoteDataQuery.getIssueResult(getLotteryType(),period.getPeriodNumber()));
				this.setResultNum();
			}
		}
	}

	@Override
	public String edit() throws Exception {
		super.edit();
		if (period != null && period.isDrawed()) {
			periodData = getPeriodDataEntityManager().getPeriodData(period.getId());
			if (periodData == null) {
				periodData = getPeriodDataEntityManager().getEntityInstance();
				
			}
			if(null!=periodData){
				this.setResultNum();
			}
		}
		return doEdit();
	}

	public String fetchAwardData() {
		period = periodManager.getPeriod(id);
		Map<String, Object> result = new HashMap<String, Object>();
		String awardData =remoteDataQuery.fetchAwardData(period.getLotteryType(),period.getPeriodNumber());
		result.put("awardData", awardData);
		Struts2Utils.renderJson(result);
		return null;
	}

	/**
	 * 抓取开奖号码（体彩，福彩）
	 * @return
	 * @throws Exception
	 */
	public String fetchWinningNumber() throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		String resultNumber=getResultNumber();
		if(StringUtils.isNotBlank(resultNumber)){
			result.put("success", true);
			result.put("msg", "抓取开奖号码成功");
			result.put("winningNumber", resultNumber);
			result.put("lottery", getLotteryType().getKey());
			Struts2Utils.renderJson(result);
		}else{
			result.put("success", false);
			result.put("msg", "抓取开奖号码失败");
			Struts2Utils.renderJson(result);
		}
		return null;
	}
	
	@Override
	public String update() {
		try {
			
			super.update();
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("权限不足！");
			}
			
			if ("true".equalsIgnoreCase(Struts2Utils.getParameter("updatePeriodData"))&&null!=period&&period.isDrawed()) {
				if (periodData == null)
					throw new WebDataException("分表数据为空.");
				periodData.setLastModifyTime(new Date());
				getResultNum();
				periodData = getPeriodDataEntityManager().save(periodData);
				period = periodManager.getPeriod(periodData.getPeriodId());
				eventLogManager.saveSimpleEventLog(period,getLotteryType(), adminUser, EventLogType.SetResult, "开奖号码为："+num1+","+num2+","+num3+","+num4+","+num5+","+num6+","+num7+",");
				addActionMessage("更新开奖数据成功.");
				updateInfo(period,adminUser);
			}else{
				PeriodDataEntityManager periodDataEntityManager = getPeriodDataEntityManager();
				if(null!=periodDataEntityManager){
				      PeriodData periodData = periodDataEntityManager.getPeriodData(period.getId());
				      if(null!=periodData){
				    	  periodData.setResult(null);
				    	  periodData.setLastModifyTime(new Date());
				    	  periodDataEntityManager.save(periodData);
				    	  eventLogManager.saveSimpleEventLog(period,getLotteryType(), adminUser, EventLogType.UpdatePeriod, "把本期设为非开奖期");
				      }
				}
			}
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
	public void updateInfo(Period period,AdminUser adminUser) throws DataException {
		if(null!=period){
			////数字彩更新最新开奖
			indexInfoDataEntityManager.makeShuTopWon(period.getLotteryType());
			indexInfoDataEntityManager.makeShuNewResult();
			indexInfoDataEntityManager.makeZcNewResult();
			////更新遗漏
//			if(Lottery.SSQ.equals(period.getLotteryType())){
//				ssqMissDataControllerServiceImpl.updateMissData();
//			}else if(Lottery.DLT.equals(period.getLotteryType())){
//				dltMissDataControllerServiceImpl.updateMissData();
//			}else if(Lottery.WELFARE3D.equals(period.getLotteryType())){
//				welfare3dMissDataControllerServiceImpl.updateMissData();
//			}else if(Lottery.PL.equals(period.getLotteryType())){
//				plMissDataControllerServiceImpl.updateMissData();
//			}else if(Lottery.SEVEN.equals(period.getLotteryType())){
//				sevenMissDataControllerServiceImpl.updateMissData();
//			}else if(Lottery.TC22TO5.equals(period.getLotteryType())){
//				tc22to5MissDataControllerServiceImpl.updateMissData();
//			}else if(Lottery.WELFARE36To7.equals(period.getLotteryType())){
//				welfare36to7MissDataControllerServiceImpl.updateMissData();
//			}
			///////更新首页开奖信息
			EventLog eventLog = new EventLog();
			eventLog.setStartTime(new Date() );
			eventLog.setDoneTime(new Date());
			eventLog.setMsg("更新中奖结果");
			eventLog.setNormalFinish(true);
			eventLog.setLotteryType(null);
			SpringContextHolder.publishEvent(new LotteryResultUpdateEvent(eventLog));

		}
	}
	public PeriodData getPeriodData() {
		return periodData;
	}

	public void setPeriodData(PeriodData periodData) {
		this.periodData = periodData;
	}

	/**
	 * @return the num1
	 */
	public Integer getNum1() {
		return num1;
	}

	/**
	 * @param num1 the num1 to set
	 */
	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	/**
	 * @return the num2
	 */
	public Integer getNum2() {
		return num2;
	}

	/**
	 * @param num2 the num2 to set
	 */
	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	/**
	 * @return the num3
	 */
	public Integer getNum3() {
		return num3;
	}

	/**
	 * @param num3 the num3 to set
	 */
	public void setNum3(Integer num3) {
		this.num3 = num3;
	}

	/**
	 * @return the num4
	 */
	public Integer getNum4() {
		return num4;
	}

	/**
	 * @param num4 the num4 to set
	 */
	public void setNum4(Integer num4) {
		this.num4 = num4;
	}

	/**
	 * @return the num5
	 */
	public Integer getNum5() {
		return num5;
	}

	/**
	 * @param num5 the num5 to set
	 */
	public void setNum5(Integer num5) {
		this.num5 = num5;
	}

	/**
	 * @return the num6
	 */
	public Integer getNum6() {
		return num6;
	}

	/**
	 * @param num6 the num6 to set
	 */
	public void setNum6(Integer num6) {
		this.num6 = num6;
	}

	/**
	 * @return the num7
	 */
	public Integer getNum7() {
		return num7;
	}

	/**
	 * @param num7 the num7 to set
	 */
	public void setNum7(Integer num7) {
		this.num7 = num7;
	}

	public Integer getNum8() {
		return num8;
	}

	public void setNum8(Integer num8) {
		this.num8 = num8;
	}

}
