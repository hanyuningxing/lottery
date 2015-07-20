package com.cai310.lottery.web.controller.lottery.keno;

import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.dto.lottery.NumberSchemeDTO;
import com.cai310.lottery.support.ahkuai3.AhKuai3PlayType;
import com.cai310.lottery.support.el11to5.El11to5PlayType;
import com.cai310.lottery.support.gdel11to5.GdEl11to5PlayType;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.lottery.support.klsf.KlsfPlayType;
import com.cai310.lottery.support.qyh.QyhPlayType;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.lottery.support.xjel11to5.XjEl11to5PlayType;

public class KenoSchemeDTO extends NumberSchemeDTO {
	/** 期编号 */
	private Long periodId;

	/** 方案发起人的用户编号 */
	private Long sponsorId;

	/** 方案描述 */
	private String description;

	/** 方案注数（单倍注数） */
	private Integer units;

	/** 方案倍数 */
	private Integer multiple;

	/** 方案金额 */
	private Integer schemeCost;

	/** 每注金额 */
	private int unitsMoney;

	private El11to5PlayType el11to5PlayType;

	private SdEl11to5PlayType sdel11to5PlayType;

	private GdEl11to5PlayType gdel11to5PlayType;
	
	private XjEl11to5PlayType xjEl11to5PlayType;

	public AhKuai3PlayType getAhkuai3PlayType() {
		return ahkuai3PlayType;
	}

	public void setAhkuai3PlayType(AhKuai3PlayType ahkuai3PlayType) {
		this.ahkuai3PlayType = ahkuai3PlayType;
	}

	private AhKuai3PlayType ahkuai3PlayType;
	
	private KlsfPlayType klsfPlayType;

	private QyhPlayType qyhPlayType;

	private SscPlayType sscPlayType;

	private KlpkPlayType klpkPlayType;
	/**
	 * 方案投注的方式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 */
	private SalesMode mode;

	/** 是否追号 */
	private boolean chase;

	/** 追号的总金额 */
	private Integer totalCostOfChase;

	/** 各期追号的倍数,空值表示该期停追号 */
	private List<Integer> multiplesOfChase = new ArrayList<Integer>();

	/** 是否机选追号 */
	private boolean randomOfChase;

	/** 每期机选注数 */
	private Integer randomUnitsOfChase;

	/** 是否中奖后停止追号 */
	private boolean wonStopOfChase;

	/** 出号后放弃 */
	private boolean outNumStop;
	/** 追号开始期ID */
	private Long startChasePeriodId;

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

	/** 中奖金额大于该金额才停止追号 */
	private Integer prizeForWonStopOfChase;

	/** 方案内容 */
	private String content;

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

	private int beforeTime;

	private String capacityProfit;

	public String getCapacityProfit() {
		return capacityProfit;
	}

	public void setCapacityProfit(String capacityProfit) {
		this.capacityProfit = capacityProfit;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	/**
	 * @return the secretType
	 */
	public SecretType getSecretType() {
		return secretType;
	}

	public SscPlayType getSscPlayType() {
		return sscPlayType;
	}

	public void setSscPlayType(SscPlayType sscPlayType) {
		this.sscPlayType = sscPlayType;
	}

	/**
	 * @param secretType
	 *            the secretType to set
	 */
	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}

	/**
	 * @return the subscriptionLicenseType
	 */
	public SubscriptionLicenseType getSubscriptionLicenseType() {
		return subscriptionLicenseType;
	}

	/**
	 * @param subscriptionLicenseType
	 *            the subscriptionLicenseType to set
	 */
	public void setSubscriptionLicenseType(
			SubscriptionLicenseType subscriptionLicenseType) {
		this.subscriptionLicenseType = subscriptionLicenseType;
	}

	/**
	 * @return the subscriptionPassword
	 */
	public String getSubscriptionPassword() {
		return subscriptionPassword;
	}

	/**
	 * @param subscriptionPassword
	 *            the subscriptionPassword to set
	 */
	public void setSubscriptionPassword(String subscriptionPassword) {
		this.subscriptionPassword = subscriptionPassword;
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

	public SalesMode getMode() {
		return mode;
	}

	public void setMode(SalesMode mode) {
		this.mode = mode;
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

	public void setUnitsMoney(int unitsMoney) {
		this.unitsMoney = unitsMoney;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the el11to5PlayType
	 */
	public El11to5PlayType getEl11to5PlayType() {
		return el11to5PlayType;
	}

	/**
	 * @param el11to5PlayType
	 *            the el11to5PlayType to set
	 */
	public void setEl11to5PlayType(El11to5PlayType el11to5PlayType) {
		this.el11to5PlayType = el11to5PlayType;
	}

	/**
	 * @return the beforeTime
	 */
	public int getBeforeTime() {
		return beforeTime;
	}

	/**
	 * @param beforeTime
	 *            the beforeTime to set
	 */
	public void setBeforeTime(int beforeTime) {
		this.beforeTime = beforeTime;
	}

	public SdEl11to5PlayType getSdel11to5PlayType() {
		return sdel11to5PlayType;
	}

	public void setSdel11to5PlayType(SdEl11to5PlayType sdel11to5PlayType) {
		this.sdel11to5PlayType = sdel11to5PlayType;
	}

	public QyhPlayType getQyhPlayType() {
		return qyhPlayType;
	}

	public void setQyhPlayType(QyhPlayType qyhPlayType) {
		this.qyhPlayType = qyhPlayType;
	}

	public KlsfPlayType getKlsfPlayType() {
		return klsfPlayType;
	}

	public void setKlsfPlayType(KlsfPlayType klsfPlayType) {
		this.klsfPlayType = klsfPlayType;
	}

	public GdEl11to5PlayType getGdel11to5PlayType() {
		return gdel11to5PlayType;
	}

	public void setGdel11to5PlayType(GdEl11to5PlayType gdel11to5PlayType) {
		this.gdel11to5PlayType = gdel11to5PlayType;
	}

	public KlpkPlayType getKlpkPlayType() {
		return klpkPlayType;
	}

	public void setKlpkPlayType(KlpkPlayType klpkPlayType) {
		this.klpkPlayType = klpkPlayType;
	}

	public XjEl11to5PlayType getXjEl11to5PlayType() {
		return xjEl11to5PlayType;
	}

	public void setXjEl11to5PlayType(XjEl11to5PlayType xjEl11to5PlayType) {
		this.xjEl11to5PlayType = xjEl11to5PlayType;
	}

}
