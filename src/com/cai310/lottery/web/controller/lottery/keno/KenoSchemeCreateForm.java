package com.cai310.lottery.web.controller.lottery.keno;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.web.controller.WebDataException;

public class KenoSchemeCreateForm {
	
	/** 方案内容 */
	protected String quick_content;
	
	protected transient Logger logger = LoggerFactory.getLogger(getClass());
	/** 期ID */
	private Long periodId; 

	/** 方案描述 */
	private String description;

	/** 方案注数（单倍注数） */
	private Integer units;

	/** 方案倍数 */
	private Integer multiple;

	/** 方案金额 */
	private Integer schemeCost;

	/** 每注金额 */
	public static int unitsMoney = 2;

	/** 方案内容 */
	private String content;

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

	/** 是否中奖后停止追号 */
	private boolean wonStopOfChase;
	
	/** 出号后放弃*/
	private boolean outNumStop;
	/** 追号开始期ID */
	@SuppressWarnings("unused")
	private Long startChasePeriodId; 
	
	
	
	//智能追号
	private String startChasePeriodNum; //开始期号
	
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

	public String getAferlcp_select() {
		return aferlcp_select;
	}

	public void setAferlcp_select(String aferlcp_select) {
		this.aferlcp_select = aferlcp_select;
	}



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
	
	
	public Long getStartChasePeriodId() {
		return startChasePeriodId;
	}

	public void setStartChasePeriodId(Long startChasePeriodId) {
		this.startChasePeriodId = startChasePeriodId;
	}

	public boolean isOutNumStop() {
		return outNumStop;
	}

	public void setOutNumStop(boolean outNumStop) {
		this.outNumStop = outNumStop;
	}
	
	/**
	 * @return 快速发起方案的内容
	 * @throws WebDataException 
	 */
	protected String buildContent() throws WebDataException {
		if(null==this.getQuick_content())throw new WebDataException("投注内容为空");
		return this.getUnits()+":"+this.quick_content;
	}

	/** 中奖金额大于该金额才停止追号 */
	private Integer prizeForWonStopOfChase;
	
	/** 单复式 **/
	private SalesMode salesMode=SalesMode.COMPOUND;
	
	/**
	 * 方案保密类型
	 * 
	 * @see com.cai310.lottery.common.SecretType
	 */
	private SecretType secretType;

	/**
	 * 方案认购许可类型
	 * 
	 * @see com.cai310.lottery.common.SubscriptionLicenseType
	 */
	private SubscriptionLicenseType subscriptionLicenseType;

	/** 方案认购密码 */
	private String subscriptionPassword;
	

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Integer getSchemeCost() {
		return schemeCost;
	}

	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isChase() {
		return chase;
	}

	public void setChase(boolean chase) {
		this.chase = chase;
	}

	public Integer getTotalCostOfChase() {
		return totalCostOfChase;
	}

	public void setTotalCostOfChase(Integer totalCostOfChase) {
		this.totalCostOfChase = totalCostOfChase;
	}

	public Integer getPeriodSizeOfChase() {
		return periodSizeOfChase;
	}

	public void setPeriodSizeOfChase(Integer periodSizeOfChase) {
		this.periodSizeOfChase = periodSizeOfChase;
	}

	public List<Integer> getMultiplesOfChase() {
		return multiplesOfChase;
	}

	public void setMultiplesOfChase(List<Integer> multiplesOfChase) {
		this.multiplesOfChase = multiplesOfChase;
	}

	public boolean isRandomOfChase() {
		return randomOfChase;
	}

	public void setRandomOfChase(boolean randomOfChase) {
		this.randomOfChase = randomOfChase;
	}

	public Integer getRandomUnitsOfChase() {
		return randomUnitsOfChase;
	}

	public void setRandomUnitsOfChase(Integer randomUnitsOfChase) {
		this.randomUnitsOfChase = randomUnitsOfChase;
	}

	public boolean isWonStopOfChase() {
		return wonStopOfChase;
	}

	public void setWonStopOfChase(boolean wonStopOfChase) {
		this.wonStopOfChase = wonStopOfChase;
	}

	public Integer getPrizeForWonStopOfChase() {
		return prizeForWonStopOfChase;
	}

	public void setPrizeForWonStopOfChase(Integer prizeForWonStopOfChase) {
		this.prizeForWonStopOfChase = prizeForWonStopOfChase;
	}

	public int getUnitsMoney() {
		return unitsMoney;
	}

	/**
	 * @return the salesMode
	 */
	public SalesMode getSalesMode() {
		return salesMode;
	}

	/**
	 * @param salesMode the salesMode to set
	 */
	public void setSalesMode(SalesMode salesMode) {
		this.salesMode = salesMode;
	}

	/**
	 * @return the subscriptionLicenseType
	 */
	public SubscriptionLicenseType getSubscriptionLicenseType() {
		return subscriptionLicenseType;
	}

	/**
	 * @param subscriptionLicenseType the subscriptionLicenseType to set
	 */
	public void setSubscriptionLicenseType(SubscriptionLicenseType subscriptionLicenseType) {
		this.subscriptionLicenseType = subscriptionLicenseType;
	}

	/**
	 * @return the secretType
	 */
	public SecretType getSecretType() {
		return secretType;
	}

	/**
	 * @param secretType the secretType to set
	 */
	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}

	/**
	 * @return the subscriptionPassword
	 */
	public String getSubscriptionPassword() {
		return subscriptionPassword;
	}

	/**
	 * @param subscriptionPassword the subscriptionPassword to set
	 */
	public void setSubscriptionPassword(String subscriptionPassword) {
		this.subscriptionPassword = subscriptionPassword;
	}
	public String getQuick_content() {
		return quick_content;
	}

	public void setQuick_content(String quick_content) {
		this.quick_content = quick_content;
	}

	

	
}
