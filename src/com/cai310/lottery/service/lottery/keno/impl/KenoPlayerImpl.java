package com.cai310.lottery.service.lottery.keno.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.lottery.keno.KenoSysConfig;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;

public abstract class KenoPlayerImpl<I extends KenoPeriod, S extends NumberScheme> implements
		KenoPlayer<I, S> {

	protected KenoService<I, S> kenoService;

	public abstract void setKenoService(KenoService<I, S> kenoService);

	public abstract Lottery getLottery();

	/**
	 * 读取系统当前时间（用户于同步官方时间）
	 * 
	 * @return 系统当前时间
	 */
	public Date getDateNow() {
		return new Date();
	}

	/**
	 * 读取提前开售的天数
	 * 
	 * @return
	 */
	public int getBeforeSaleDays() {
		return 3;
	}

	/**
	 * 读取提前开售与截止的时间(单位:分钟)
	 * 
	 * @return 提前开售与截止的时间
	 */
	public abstract int getBeforeTime() ;
	

	/**
	 * 是否暂销售
	 * 
	 * @return
	 */
	public boolean isPause() {
		KenoSysConfig config = kenoService.getSysConfigByKey(this.getLottery().getKey().concat(KenoSysConfig.PAUSE));
		if (config == null) {
			return false;
		}
		String value = config.getKeyValue();
		if ("true".equals(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 读取每天最大期号
	 */
	public abstract int getMaxPeriod();

	/**
	 * 读取每期的间隔时间（以分钟计）
	 * 
	 * @return
	 */
	public abstract int getPeriodMinutes();

	public Date getEndTimeByIssueNumber(String issueNumber) {
		return DateUtils.addMinutes(this.getBeginTimeByIssueNumber(issueNumber), this.getPeriodMinutes());
	}


}
