package com.cai310.lottery.entity.lottery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;

import javax.persistence.Transient;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.utils.DateUtil;

public class MyScheme implements Serializable {
	private static final long serialVersionUID = -790565814001940005L;
	/** 方案注数（单倍注数） */
	private Integer units;

	/** 倍数 */
	private Integer multiple;
	
	private WinningUpdateStatus winningUpdateStatus;

	/** 方案金额 */
	private Integer schemeCost;

	/** 彩种类型 */
	private Lottery lotteryType;

	/** 认购金额 */
	private BigDecimal myCost;

	/** 总分红 */
	private BigDecimal myBonus;

	/** 方案ID */
	private Long schemeId;

	/** 期号 */
	private String periodNumber;

	private Long sponsorId;

	private String sponsorName;

	/**
	 * 方案分享类型
	 * 
	 * @see com.cai310.lottery.common.ShareType
	 */
	private ShareType shareType;
	
	
	private SchemeState schemeState;

	private boolean sendToPrint;

	private BigDecimal prize;

	private BigDecimal prizeAfterTax;
	/** 是否中奖 */
	private Boolean won;

	/** 方案出票状态 */
	private SchemePrintState schemePrintState;

	private Date createTime;
	
	private String createTimeStr;
	///理论开奖时间按
	private String resultTimeStr;
	
	private PlPlayType plPlayType;

	private PlayType zcPlayType;
	/**
	 * @return the schemePrintState
	 */
	public SchemePrintState getSchemePrintState() {
		if (schemePrintState == null)
			return SchemePrintState.UNPRINT;
		return schemePrintState;
	}

	/**
	 * @param schemePrintState the schemePrintState to set
	 */
	public void setSchemePrintState(SchemePrintState schemePrintState) {
		this.schemePrintState = schemePrintState;
	}

	/**
	 * @return {@link #lotteryType}
	 */
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the {@link #lotteryType} to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	 * @return {@link #myCost}
	 */
	public BigDecimal getMyCost() {
		return myCost;
	}

	/**
	 * @param myCost the {@link #myCost} to set
	 */
	public void setMyCost(BigDecimal myCost) {
		this.myCost = myCost;
	}

	/**
	 * @return {@link #myBonus}
	 */
	public BigDecimal getMyBonus() {
		return myBonus;
	}

	/**
	 * @param myBonus the {@link #myBonus} to set
	 */
	public void setMyBonus(BigDecimal myBonus) {
		this.myBonus = myBonus;
	}

	/**
	 * @return {@link #schemeId}
	 */
	public Long getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId the {@link #schemeId} to set
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return {@link #periodNumber}
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the {@link #periodNumber} to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return {@link #sponsorId}
	 */
	public Long getSponsorId() {
		return sponsorId;
	}

	/**
	 * @param sponsorId the {@link #sponsorId} to set
	 */
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	/**
	 * @return {@link #sponsorName}
	 */
	public String getSponsorName() {
		return sponsorName;
	}

	/**
	 * @param sponsorName the {@link #sponsorName} to set
	 */
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	/**
	 * @return {@link #schemeCost}
	 */
	public Integer getSchemeCost() {
		return schemeCost;
	}

	/**
	 * @param schemeCost the {@link #schemeCost} to set
	 */
	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	/**
	 * @return {@link #schemeState}
	 */
	public SchemeState getSchemeState() {
		return schemeState;
	}

	/**
	 * @param schemeState the {@link #schemeState} to set
	 */
	public void setSchemeState(SchemeState schemeState) {
		this.schemeState = schemeState;
	}

	/**
	 * @return {@link #sendToPrint}
	 */
	public boolean isSendToPrint() {
		return sendToPrint;
	}

	/**
	 * @param sendToPrint the {@link #sendToPrint} to set
	 */
	public void setSendToPrint(boolean sendToPrint) {
		this.sendToPrint = sendToPrint;
	}

	/**
	 * @return {@link #prize}
	 */
	public BigDecimal getPrize() {
		return prize;
	}

	/**
	 * @param prize the {@link #prize} to set
	 */
	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	/**
	 * @return {@link #prizeAfterTax}
	 */
	public BigDecimal getPrizeAfterTax() {
		return prizeAfterTax;
	}

	/**
	 * @param prizeAfterTax the {@link #prizeAfterTax} to set
	 */
	public void setPrizeAfterTax(BigDecimal prizeAfterTax) {
		this.prizeAfterTax = prizeAfterTax;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	// ---------------------------------------
	/**
	 * @return 方案号
	 */
	public String getSchemeNumber() {
		return this.getLotteryType().getSchemeNumber(this.getSchemeId());
	}
	public Boolean getWon() {
		return won;
	}

	public void setWon(Boolean won) {
		this.won = won;
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

	public WinningUpdateStatus getWinningUpdateStatus() {
		return winningUpdateStatus;
	}

	public void setWinningUpdateStatus(WinningUpdateStatus winningUpdateStatus) {
		this.winningUpdateStatus = winningUpdateStatus;
	}

	public String getCreateTimeStr() {
		return DateUtil.dateToStr(this.createTime,"yyyy-MM-dd HH:mm:ss");
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public PlPlayType getPlPlayType() {
		return plPlayType;
	}

	public void setPlPlayType(PlPlayType plPlayType) {
		this.plPlayType = plPlayType;
	}

	public PlayType getZcPlayType() {
		return zcPlayType;
	}

	public void setZcPlayType(PlayType zcPlayType) {
		this.zcPlayType = zcPlayType;
	}

	public String getResultTimeStr() {
		return resultTimeStr;
	}

	public void setResultTimeStr(String resultTimeStr) {
		this.resultTimeStr = resultTimeStr;
	}
    public Integer getPlayTypeOrdinal(){
    	if(null!=plPlayType){
    		return plPlayType.ordinal();
    	}
    	if(null!=zcPlayType){
    		return zcPlayType.ordinal();
    	}
    	return 0;
    }
	@Transient
	public BigDecimal getZoomSchemeCost() {
		return BigDecimal.valueOf(schemeCost);
	}
	@Transient
	public BigDecimal getZoomMyCost() {
		return myCost;
	}

	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}


}
