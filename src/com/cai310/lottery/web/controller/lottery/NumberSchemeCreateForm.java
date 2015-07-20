package com.cai310.lottery.web.controller.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.web.controller.WebDataException;

public abstract class NumberSchemeCreateForm extends SchemeCreateForm {
	/** 方案内容 */
	protected String quick_content;
	/** 方案内容 */
	protected String content;
	
	/** 是否追号 */
	private boolean chase;

	/** 追号的总金额 */
	private Integer totalCostOfChase;

	/** 追号的期数,用于核对chaseMultiples,不保存 */
	private Integer periodSizeOfChase;

	/** 各期追号的倍数,空值表示该期停追号 */
	private List<Integer> multiplesOfChase = new ArrayList<Integer>();

	/** 是否机选追号 */
	private boolean randomOfChase;

	/** 每期机选注数 */
	private Integer randomUnitsOfChase;

	/** 是否有设置胆码 */
	private boolean hasDanOfChase;

	/** 是否中奖后停止追号 */
	private boolean wonStopOfChase;

	/** 中奖金额大于该金额才停止追号 */
	private Integer prizeForWonStopOfChase;
	
	
	//智能追号
	private String startChasePeriodNum; //开始期号
	
	private String startMultiple;		//起始倍数

	private String hasInvested;			 //已经投入
	
	private String expectedHit;			//预计命中
	
	private String allafterlucre;    //全程收益金额
	

	private String  befortermmember;  // 前几期

	private String beforelc;		  // 前几期金额
	
	private String aferlc;			  //之后累计收益不低于
	
	private String all_lucrep_select; //全程收益率

	private String befortermmemberp;	// 期数
	
	private String before_lcp_select;  // 前几期金额
	
	private String aferlcp_select;     //之后收益率不低于
	
	
	public String getAllafterlucre() {
		return allafterlucre;
	}

	public void setAllafterlucre(String allafterlucre) {
		this.allafterlucre = allafterlucre;
	}

	public String getBefortermmember() {
		return befortermmember;
	}

	public void setBefortermmember(String befortermmember) {
		this.befortermmember = befortermmember;
	}

	public String getBeforelc() {
		return beforelc;
	}

	public void setBeforelc(String beforelc) {
		this.beforelc = beforelc;
	}

	public String getAferlc() {
		return aferlc;
	}

	public void setAferlc(String aferlc) {
		this.aferlc = aferlc;
	}

	public String getAll_lucrep_select() {
		return all_lucrep_select;
	}

	public void setAll_lucrep_select(String all_lucrep_select) {
		this.all_lucrep_select = all_lucrep_select;
	}

	public String getBefortermmemberp() {
		return befortermmemberp;
	}

	public void setBefortermmemberp(String befortermmemberp) {
		this.befortermmemberp = befortermmemberp;
	}

	public String getBefore_lcp_select() {
		return before_lcp_select;
	}

	public void setBefore_lcp_select(String before_lcp_select) {
		this.before_lcp_select = before_lcp_select;
	}

	
	

	public String getStartChasePeriodNum() {
		return startChasePeriodNum;
	}

	public void setStartChasePeriodNum(String startChasePeriodNum) {
		this.startChasePeriodNum = startChasePeriodNum;
	}

	public String getStartMultiple() {
		return startMultiple;
	}

	public void setStartMultiple(String startMultiple) {
		this.startMultiple = startMultiple;
	}

	public String getHasInvested() {
		return hasInvested;
	}

	public void setHasInvested(String hasInvested) {
		this.hasInvested = hasInvested;
	}

	public String getExpectedHit() {
		return expectedHit;
	}

	public void setExpectedHit(String expectedHit) {
		this.expectedHit = expectedHit;
	}


	public String getAferlcp_select() {
		return aferlcp_select;
	}

	public void setAferlcp_select(String aferlcp_select) {
		this.aferlcp_select = aferlcp_select;
	}

	
	//智能追号
	

	    
	

	/* -------------------- other method -------------------- */

	public abstract Map<String, String> getDanMap() throws DataException;

	public abstract void setUnitsAndContent(Integer units, String content);

	/* -------------- getter and setter method -------------- */

	/**
	 * @return {@link #chase}
	 */
	public boolean isChase() {
		return chase;
	}

	/**
	 * @param chase the {@link #chase} to set
	 */
	public void setChase(boolean chase) {
		this.chase = chase;
	}

	/**
	 * @return {@link #totalCostOfChase}
	 */
	public Integer getTotalCostOfChase() {
		return totalCostOfChase;
	}

	/**
	 * @param totalCostOfChase the {@link #totalCostOfChase} to set
	 */
	public void setTotalCostOfChase(Integer totalCostOfChase) {
		this.totalCostOfChase = totalCostOfChase;
	}

	/**
	 * @return {@link #periodSizeOfChase}
	 */
	public Integer getPeriodSizeOfChase() {
		return periodSizeOfChase;
	}

	/**
	 * @param periodSizeOfChase the {@link #periodSizeOfChase} to set
	 */
	public void setPeriodSizeOfChase(Integer periodSizeOfChase) {
		this.periodSizeOfChase = periodSizeOfChase;
	}

	/**
	 * @return {@link #multiplesOfChase}
	 */
	public List<Integer> getMultiplesOfChase() {
		return multiplesOfChase;
	}

	/**
	 * @param multiplesOfChase the {@link #multiplesOfChase} to set
	 */
	public void setMultiplesOfChase(List<Integer> multiplesOfChase) {
		this.multiplesOfChase = multiplesOfChase;
	}

	/**
	 * @return {@link #randomOfChase}
	 */
	public boolean isRandomOfChase() {
		return randomOfChase;
	}

	/**
	 * @param randomOfChase the {@link #randomOfChase} to set
	 */
	public void setRandomOfChase(boolean randomOfChase) {
		this.randomOfChase = randomOfChase;
	}

	/**
	 * @return {@link #randomUnitsOfChase}
	 */
	public Integer getRandomUnitsOfChase() {
		return randomUnitsOfChase;
	}

	/**
	 * @param randomUnitsOfChase the {@link #randomUnitsOfChase} to set
	 */
	public void setRandomUnitsOfChase(Integer randomUnitsOfChase) {
		this.randomUnitsOfChase = randomUnitsOfChase;
	}

	/**
	 * @return {@link #hasDanOfChase}
	 */
	public boolean isHasDanOfChase() {
		return hasDanOfChase;
	}

	/**
	 * @param hasDanOfChase the {@link #hasDanOfChase} to set
	 */
	public void setHasDanOfChase(boolean hasDanOfChase) {
		this.hasDanOfChase = hasDanOfChase;
	}

	/**
	 * @return {@link #wonStopOfChase}
	 */
	public boolean isWonStopOfChase() {
		return wonStopOfChase;
	}

	/**
	 * @param wonStopOfChase the {@link #wonStopOfChase} to set
	 */
	public void setWonStopOfChase(boolean wonStopOfChase) {
		this.wonStopOfChase = wonStopOfChase;
	}

	/**
	 * @return {@link #prizeForWonStopOfChase}
	 */
	public Integer getPrizeForWonStopOfChase() {
		return prizeForWonStopOfChase;
	}

	/**
	 * @param prizeForWonStopOfChase the {@link #prizeForWonStopOfChase} to set
	 */
	public void setPrizeForWonStopOfChase(Integer prizeForWonStopOfChase) {
		this.prizeForWonStopOfChase = prizeForWonStopOfChase;
	}
	/**
	 * @return {@link #content}
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return 快速发起方案的内容
	 * @throws WebDataException 
	 */
	protected abstract String buildContent() throws WebDataException;

	public String getQuick_content() {
		return quick_content;
	}

	public void setQuick_content(String quick_content) {
		this.quick_content = quick_content;
	}

}
