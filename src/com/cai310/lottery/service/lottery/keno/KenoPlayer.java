package com.cai310.lottery.service.lottery.keno;

import java.util.Date;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;

public interface KenoPlayer< I extends KenoPeriod, S extends NumberScheme> {

	/**
	 * 读取系统当前时间（用户于同步官方时间）
	 * 
	 * @return 系统当前时间
	 */
	public Date getDateNow();

	/**
	 * 读取提前开售的天数
	 * 
	 * @return
	 */
	public int getBeforeSaleDays();

	/**
	 * 一天最大的期数
	 * 
	 * @return
	 */
	public int getMaxPeriod();

	/**
	 * 组装期号
	 * 
	 * @param day 当天时间
	 * @param period 当天销售的期数
	 * @return
	 */
	public String issueNumberAssembly(Date day, int period);

	/**
	 * 据期号计算开始时间
	 * 
	 * @param issueNumber 要计算的期号
	 * @return
	 */
	public Date getBeginTimeByIssueNumber(String issueNumber);

	/**
	 * 创建新期号
	 * 
	 * @param issueNumber 期号
	 * @return
	 */
	public I createNowIssueData(String issueNumber);

	/**
	 * 读取提前开售与截止的时间(单位:分钟)
	 * 
	 * @return 提前开售与截止的时间
	 */
	public int getBeforeTime();  
	/**
	 * 读取提前开售与截止的时间(单位:秒)
	 * 
	 * @return 提前开售与截止的时间
	 */
	public int getBeforeSecondsTime();
	

	//	
	/**
	 * 计算方案中奖金额
	 * 
	 * @param scheme 需计算的方案
	 * @param results 开奖结果
	 * @throws DataException 
	 */
	public void calculatePrice(S scheme, String results) throws DataException;
	
	public boolean calculatePrice(SalesMode salesMode, String content, Integer multiple, String betType, String results) throws DataException;

	public void resetPrice(S scheme,String results,Ticket ticket) throws DataException;
	/**
	 * 读取每期的间隔时间（以分钟计）
	 * 
	 * @return
	 */
	public int getPeriodMinutes();

	/**
	 * 根据期号计算截止时间
	 * 
	 * @param issueNumber
	 * @return
	 */
	public Date getEndTimeByIssueNumber(String issueNumber);

}
