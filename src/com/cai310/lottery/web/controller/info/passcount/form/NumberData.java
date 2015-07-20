package com.cai310.lottery.web.controller.info.passcount.form;


public class NumberData {

	private Long firstWinUnit;
	
	private Long firstPrize;
	private Long secondWinUnit;
	private Long secondPrize;

	private Long prizePool;
	private Long totalSales;
	private String updateTime;
	private String startTime;
	private String endTime;
	private String result;

	/**
	 * @return the firstPrize
	 */
	public Long getFirstPrize() {
		return firstPrize;
	}

	/**
	 * @param firstPrize
	 *            the firstPrize to set
	 */
	public void setFirstPrize(Long firstPrize) {
		this.firstPrize = firstPrize;
	}

	/**
	 * @return the prizePool
	 */
	public Long getPrizePool() {
		return prizePool;
	}

	public Long getFirstWinUnit() {
		return firstWinUnit;
	}

	public void setFirstWinUnit(Long firstWinUnit) {
		this.firstWinUnit = firstWinUnit;
	}

	public Long getSecondWinUnit() {
		return secondWinUnit;
	}

	public void setSecondWinUnit(Long secondWinUnit) {
		this.secondWinUnit = secondWinUnit;
	}

	public Long getSecondPrize() {
		return secondPrize;
	}

	public void setSecondPrize(Long secondPrize) {
		this.secondPrize = secondPrize;
	}

	
	/**
	 * @param prizePool
	 *            the prizePool to set
	 */
	public void setPrizePool(Long prizePool) {
		this.prizePool = prizePool;
	}

	/**
	 * @return the totalSales
	 */
	public Long getTotalSales() {
		return totalSales;
	}

	/**
	 * @param totalSales
	 *            the totalSales to set
	 */
	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

}
