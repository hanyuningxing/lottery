package com.cai310.lottery.entity.lottery;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cai310.lottery.common.Lottery;

/**
 * 竞彩足球方案实体类.
 * 
 */
@MappedSuperclass
public abstract class CommonScheme extends Scheme {
	private static final long serialVersionUID = 2430623685086244555L;

	public static final String TABLE_NAME = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "COMMON_SCHEME";
	/** 是否已真实出票 */
	private boolean realPrinted;

	/** 真实出票时间 */
	private Date realPrintTime;
	
	/** 撤单时间 */
	private Date cancelTime;
	
	/**
	 * 彩票类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType; 
	/**
	 * @return the {@link #realPrinted}
	 */
	@Column(nullable = false)
	public boolean isRealPrinted() {
		return realPrinted;
	}

	/**
	 * @param realPrinted
	 *            the {@link #realPrinted} to set
	 */
	public void setRealPrinted(boolean realPrinted) {
		this.realPrinted = realPrinted;
	}

	/**
	 * @return the {@link #realPrintTime}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getRealPrintTime() {
		return realPrintTime;
	}

	/**
	 * @param realPrintTime
	 *            the {@link #realPrintTime} to set
	 */
	public void setRealPrintTime(Date realPrintTime) {
		this.realPrintTime = realPrintTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	@Column
	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = this.getLotteryType();
	}
}
