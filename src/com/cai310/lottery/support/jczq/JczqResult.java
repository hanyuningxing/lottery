package com.cai310.lottery.support.jczq;

import java.io.Serializable;

import com.cai310.lottery.support.Item;

/**
 * 竞彩足球开奖结果对象
 * 
 */
public class JczqResult implements Serializable {
	private static final long serialVersionUID = 3493928665282431719L;

	/** 场次key */
	private String matchKey;

	/** 开奖结果 */
	private Item resultItem;

	/** 开奖SP值 */
	private Double resultSp;

	/** 比赛是否 取消 */
	private boolean cancel;

	/**
	 * @return {@link #matchKey}
	 */
	public String getMatchKey() {
		return matchKey;
	}

	/**
	 * @param matchKey the {@link #matchKey} to set
	 */
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	/**
	 * @return {@link #resultItem}
	 */
	public Item getResultItem() {
		return resultItem;
	}

	/**
	 * @param resultItem the {@link #resultItem} to set
	 */
	public void setResultItem(Item resultItem) {
		this.resultItem = resultItem;
	}

	/**
	 * @return {@link #resultSp}
	 */
	public Double getResultSp() {
		return resultSp;
	}

	/**
	 * @param resultSp the {@link #resultSp} to set
	 */
	public void setResultSp(Double resultSp) {
		this.resultSp = resultSp;
	}

	/**
	 * @return {@link #cancel}
	 */
	public boolean isCancel() {
		return cancel;
	}

	/**
	 * @param cancel the {@link #cancel} to set
	 */
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
}
