package com.cai310.lottery.web.controller.admin.security;

import java.util.Date;

import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;

public class EventLogForm {

		/** 彩种类型 */
		private Lottery lotteryType;
		private Long id;
		private Long periodId;
		private String periodNumber;
		private Byte logType;
		private Date startTime;
		private Date doneTime;
		private Boolean normalFinish;
		private String msg;
		private String operator;
		private String ip;
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

		public Long getPeriodId() {
			return this.periodId;
		}

		public void setPeriodId(Long periodId) {
			this.periodId = periodId;
		}

		public String getPeriodNumber() {
			return this.periodNumber;
		}

		public void setPeriodNumber(String periodNumber) {
			this.periodNumber = periodNumber;
		}

		public Byte getLogType() {
			return this.logType;
		}

		public void setLogType(Byte cmdType) {
			this.logType = cmdType;
		}
		public Date getStartTime() {
			return this.startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		public Date getDoneTime() {
			return this.doneTime;
		}

		public void setDoneTime(Date doneTime) {
			this.doneTime = doneTime;
		}

		public Boolean getNormalFinish() {
			return this.normalFinish;
		}

		public void setNormalFinish(Boolean normalFinish) {
			this.normalFinish = normalFinish;
		}

		public String getMsg() {
			return this.msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getOperator() {
			return this.operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}
		
		/**
		 * @return the ip
		 */
		public String getIp() {
			return ip;
		}

		/**
		 * @param ip the ip to set
		 */
		public void setIp(String ip) {
			this.ip = ip;
		}
		

		// =================================================

		public EventLogType getEventLogType() {
			return EventLogType.valueOf(this.getLogType());
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

}
