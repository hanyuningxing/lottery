package com.cai310.lottery.web.controller.info.passcount.form;


public class LczcData{
	private Integer firstPrize;
    private Integer firstWinUnits;
    private Integer secondPrize;
    private Integer secondWinUnits;
    private Long prizePool;
    private Long totalSales;
    private String updateTime;
	private String startTime;
	private String endTime;
	private LczcMatch[] zcMatchs;
	/**
	 * @return the firstPrize
	 */
	public Integer getFirstPrize() {
		return firstPrize;
	}
	/**
	 * @param firstPrize the firstPrize to set
	 */
	public void setFirstPrize(Integer firstPrize) {
		this.firstPrize = firstPrize;
	}
	/**
	 * @return the firstWinUnits
	 */
	public Integer getFirstWinUnits() {
		return firstWinUnits;
	}
	/**
	 * @param firstWinUnits the firstWinUnits to set
	 */
	public void setFirstWinUnits(Integer firstWinUnits) {
		this.firstWinUnits = firstWinUnits;
	}
	/**
	 * @return the secondPrize
	 */
	public Integer getSecondPrize() {
		return secondPrize;
	}
	/**
	 * @param secondPrize the secondPrize to set
	 */
	public void setSecondPrize(Integer secondPrize) {
		this.secondPrize = secondPrize;
	}
	/**
	 * @return the secondWinUnits
	 */
	public Integer getSecondWinUnits() {
		return secondWinUnits;
	}
	/**
	 * @param secondWinUnits the secondWinUnits to set
	 */
	public void setSecondWinUnits(Integer secondWinUnits) {
		this.secondWinUnits = secondWinUnits;
	}
	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
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
	 * @param startTime the startTime to set
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
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the zcMatchs
	 */
	public LczcMatch[] getZcMatchs() {
		return zcMatchs;
	}
	/**
	 * @param zcMatchs the zcMatchs to set
	 */
	public void setZcMatchs(LczcMatch[] zcMatchs) {
		this.zcMatchs = zcMatchs;
	}
	/**
	 * @return the prizePool
	 */
	public Long getPrizePool() {
		return prizePool;
	}
	/**
	 * @param prizePool the prizePool to set
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
	 * @param totalSales the totalSales to set
	 */
	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}
	
}
