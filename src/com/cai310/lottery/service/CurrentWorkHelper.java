package com.cai310.lottery.service;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.utils.DwrUtil;

public class CurrentWorkHelper implements Serializable {
	private static final long serialVersionUID = 4457213225959958258L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 当前正在执行的操作
	 */
	private EventLog currentWork = null;

	/**
	 * 用于向客户端向正在执行操作的客户端推送信息的辅助类
	 */
	private DwrUtil currentDwrUtil = null;

	// ******************** currentRunWork ********************
	public synchronized EventLog getCurrentWork() {
		return this.currentWork;
	}

	public synchronized boolean pushCurrentWork(EventLog currentWork, DwrUtil dwrUtil) {
		if (this.currentWork != null)
			return false;
		this.currentWork = currentWork;
		this.currentDwrUtil = dwrUtil;
		return true;
	}

	public synchronized void removeCurrentWork() {
		this.currentWork = null;
		this.currentDwrUtil = null;
	}

	// ******************** 日志相关 ********************
	/**
	 * 输出普通信息
	 */
	public void logInfo(String msg) {
		logger.info(msg);
		if (currentDwrUtil != null) {
			currentDwrUtil.sendInfoMsg(msg);
		}
	}

	/**
	 * 输出警告信息
	 */
	public void logWarn(String msg) {
		logger.warn(msg);
		if (currentDwrUtil != null) {
			currentDwrUtil.sendWarnMsg(msg);
		}
	}

	/**
	 * 输出错误信息
	 */
	public void logError(String msg, Throwable t) {
		logger.error(msg, t);
		if (currentDwrUtil != null) {
			currentDwrUtil.sendErrorMsg(msg + "异常信息：" + t.getLocalizedMessage());
		}
	}

	/**
	 * 打印日志：操作准备开始执行
	 * 
	 * @param cmd
	 * @param period
	 */
	public void logCmdReadyStart(SaleWorkerCmd cmd, long periodId) {
		logCmdStart(cmd, periodId, null);
	}

	/**
	 * 
	 * @param cmd
	 * @param periodId
	 */
	public void logCmdStart(SaleWorkerCmd cmd, long periodId) {
		logCmdStart(cmd, periodId, null);
	}

	/**
	 * 打印日志：操作准备开始执行
	 * 
	 * @param cmd
	 * @param period
	 */
	public void logCmdStart(SaleWorkerCmd cmd, long periodId, SalesMode salesMode) {
		StringBuffer sb = new StringBuffer(50);
		sb.append("开始执行【").append(cmd.getCmdName()).append("】操作");
		if (salesMode != null) {
			sb.append(salesMode.getModeName());
		}
		sb.append("，期编号：").append(periodId);
		logInfo(sb.toString());
	}

	/**
	 * 打印日志：操作执行成功
	 */
	public void logCmdSuccess() {
		logInfo("操作执行成功。");
	}

}
