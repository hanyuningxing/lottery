package com.cai310.lottery.web.controller.info;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;

/**
 * 后台方案查询表单
 * 
 */
public class PasscountQueryForm {
	private SalesMode mode;

	private com.cai310.lottery.support.zc.PlayType sfzcPlayType;
	
	/** 是否中奖 */
	private Boolean won;

	/**
	 * 方案分享类型
	 * 
	 * @see com.cai310.lottery.common.ShareType
	 */
	private ShareType shareType;

	/**
	 * 方案保密类型
	 * 
	 * @see com.cai310.lottery.common.SecretType
	 */
	private SecretType secretType;

	/**
	 * 方案状态
	 * 
	 * @see com.cai310.lottery.common.SchemeState
	 */
	private SchemeState state;

	/** 期号 */
	private String periodNumber;

	/** 期号ID */
	private long periodId;

	/** 发起人，以逗号隔开多个发起人 */
	private String sponsorNames;

	/** 方案号，以逗号隔开多个方案号 */
	private String schemeNumbers;
	
	private PlPlayType plPlayType;
	
	private Welfare3dPlayType welfare3dPlayType;
	
	private com.cai310.lottery.support.dczc.PlayType dczcPlayType;



	public com.cai310.lottery.support.dczc.PlayType getDczcPlayType() {
		return dczcPlayType;
	}

	public void setDczcPlayType(com.cai310.lottery.support.dczc.PlayType dczcPlayType) {
		this.dczcPlayType = dczcPlayType;
	}

	public Welfare3dPlayType getWelfare3dPlayType() {
		return welfare3dPlayType;
	}

	public void setWelfare3dPlayType(Welfare3dPlayType welfare3dPlayType) {
		this.welfare3dPlayType = welfare3dPlayType;
	}

	public PlPlayType getPlPlayType() {
		return plPlayType;
	}

	public void setPlPlayType(PlPlayType plPlayType) {
		this.plPlayType = plPlayType;
	}

	public SalesMode getMode() {
		return mode;
	}

	public void setMode(SalesMode mode) {
		this.mode = mode;
	}

	public com.cai310.lottery.support.zc.PlayType  getSfzcPlayType() {
		return sfzcPlayType;
	}

	public void setSfzcPlayType(com.cai310.lottery.support.zc.PlayType  sfzcPlayType) {
		this.sfzcPlayType = sfzcPlayType;
	}

	public Boolean getWon() {
		return won;
	}

	public void setWon(Boolean won) {
		this.won = won;
	}

	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	public SecretType getSecretType() {
		return secretType;
	}

	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}

	public SchemeState getState() {
		return state;
	}

	public void setState(SchemeState state) {
		this.state = state;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(long periodId) {
		this.periodId = periodId;
	}

	public String getSponsorNames() {
		return sponsorNames;
	}

	public void setSponsorNames(String sponsorNames) {
		this.sponsorNames = sponsorNames;
	}

	public String getSchemeNumbers() {
		return schemeNumbers;
	}

	public void setSchemeNumbers(String schemeNumbers) {
		this.schemeNumbers = schemeNumbers;
	}

	private Welfare36to7PlayType welfare36to7playType;

	public Welfare36to7PlayType getWelfare36to7playType() {
		return welfare36to7playType;
	}

	public void setWelfare36to7playType(Welfare36to7PlayType welfare36to7playType) {
		this.welfare36to7playType = welfare36to7playType;
	}


}
