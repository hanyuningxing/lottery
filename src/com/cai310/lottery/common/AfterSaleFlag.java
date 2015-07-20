package com.cai310.lottery.common;

/**
 * 售后状态
 */
public enum AfterSaleFlag {
	/** 已更新开奖结果 */
	RESULT_UPDATED(1 << 2),

	/** 已更新奖金 */
	PRIZE_UPDATED(1 << 3),

	/** 已派奖 */
	PRIZE_DELIVED(1 << 4);

	/** 标志值 */
	private final int flagValue;

	private AfterSaleFlag(int flagValue) {
		this.flagValue = flagValue;
	}

	public int getFlagValue() {
		return flagValue;
	}

	/**
	 * 根据标识值获取返奖状态
	 * 
	 * @param flagValue
	 * @return
	 */
	public static String getFlagText(int flagValue) {
		if ((AfterSaleFlag.PRIZE_DELIVED.flagValue & flagValue) > 0) {
			return "已派奖";
		} else if ((AfterSaleFlag.PRIZE_UPDATED.flagValue & flagValue) > 0) {
			return "已更新奖金";
		} else if ((AfterSaleFlag.RESULT_UPDATED.flagValue & flagValue) > 0) {
			return "已更新中奖";
		} else {
			return "未开奖";
		}
	}

	/**
	 * 根据标识值获取返奖状态
	 * 
	 * @param flagValue
	 * @return
	 */
	public static String getFlagHtml(int flagValue) {
		String text;
		String color;
		if ((AfterSaleFlag.PRIZE_DELIVED.flagValue & flagValue) > 0) {
			color = "red";
			text = "已派奖";
		} else if ((AfterSaleFlag.PRIZE_UPDATED.flagValue & flagValue) > 0) {
			color = "blue";
			text = "已更新奖金";
		} else if ((AfterSaleFlag.RESULT_UPDATED.flagValue & flagValue) > 0) {
			color = "green";
			text = "已更新中奖";
		} else {
			color = "gray";
			text = "未开奖";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<font color=\"").append(color).append("\">").append(text).append("</font>");
		return sb.toString();
	}

	/**
	 * 根据标志值获取返奖状态
	 * 
	 * @param flagValue
	 * @return
	 */
	public static AfterSaleFlag valueOfFlagValue(int flagValue) {
		for (AfterSaleFlag af : AfterSaleFlag.values()) {
			if (af.flagValue == flagValue) {
				return af;
			}
		}
		return null;
	}
}
