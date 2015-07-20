package com.cai310.lottery.common.keno;

public enum IssueState {
	/** 状态:预售期 */
	ISSUE_SATE_READY("预售期"),
	/** 状态:当前期 */
	ISSUE_SATE_CURRENT("当前期"),
	/** 状态:截止期 */
	ISSUE_SATE_END("截止期"),
	/** 状态:已更新结果 */
	ISSUE_SATE_RESULT("已更新结果"),
	/** 状态:已更新中奖方案 */
	ISSUE_SATE_ACCOUNT_PRIZE("已更新中奖方案"),
	/** 状态:已派奖 */
	ISSUE_SATE_SEND_PRIZE("已派奖"),
	/** 状态:已全部结束 */
	ISSUE_SATE_FINISH("已全部结束");

	private String stateName;

	public static IssueState[] ISSUE_END_STATE = { ISSUE_SATE_END, ISSUE_SATE_RESULT, ISSUE_SATE_ACCOUNT_PRIZE,
			ISSUE_SATE_SEND_PRIZE, ISSUE_SATE_FINISH };
	
	public static IssueState[] ISSUE_RESULT_STATE={ISSUE_SATE_RESULT, ISSUE_SATE_ACCOUNT_PRIZE,
		ISSUE_SATE_SEND_PRIZE, ISSUE_SATE_FINISH};

	private IssueState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * 获取状态描述
	 * 
	 * @return
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * 获取状态值
	 * 
	 * @return
	 */
	public byte getStateValue() {
		return (byte) this.ordinal();
	}

	/**
	 * 截止状态
	 * 
	 * @return
	 */
	public boolean isEnd() {
		for (IssueState state : ISSUE_END_STATE) {
			if (this.getStateValue() == state.getStateValue()) {
				return true;
			}
		}
		return false;
	}
}
