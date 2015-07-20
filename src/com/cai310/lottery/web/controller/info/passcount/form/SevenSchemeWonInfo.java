package com.cai310.lottery.web.controller.info.passcount.form;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;
public class SevenSchemeWonInfo{

	private static final long serialVersionUID = 4272390436858509156L;
	private Integer id;
	private Lottery lottery;//彩种
	private Integer playType;//玩法
	private Long schemeId;
	private String schemeNum;
	private Integer firstWinUnits;
	private Integer secondWinUnits;
	private Integer thirdWinUnits;
	private Integer fourthWinUnits;
	private Integer fifthWinUnits;
	private Integer sixthWinUnits;
	private Integer sevenWinUnits;

	/**
	 * 方案分享类型
	 * 
	 * @see com.cai310.lottery.common.ShareType
	 */
	private ShareType shareType;
	
	/** 期编号 */
	private Long periodId;
	
	/** 方案发起人的用户编号 */
	private Long sponsorId;

	/** 方案发起人的用户名 */
	private String sponsorName;
	
	/** 方案注数（单倍注数） */
	private Integer units;

	/** 倍数 */
	private Integer multiple;

	/** 方案金额 */
	private Integer schemeCost;
	
	/**方案状态*/
	private SchemeState state;
	
	/**
	 * 方案投注的方式
	 */
	private SalesMode mode;

	/**
	 * @return the schemeId
	 */
	public Long getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId the schemeId to set
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @param schemeNum the schemeNum to set
	 */
	public void setSchemeNum(String schemeNum) {
		this.schemeNum = schemeNum;
	}
	/**
	 * @return the periodId
	 */
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId the periodId to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	/**
	 * @return the sponsorId
	 */
	public Long getSponsorId() {
		return sponsorId;
	}

	/**
	 * @param sponsorId the sponsorId to set
	 */
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	/**
	 * @return the sponsorName
	 */
	public String getSponsorName() {
		return sponsorName;
	}

	/**
	 * @param sponsorName the sponsorName to set
	 */
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	/**
	 * @return the units
	 */
	public Integer getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(Integer units) {
		this.units = units;
	}

	/**
	 * @return the multiple
	 */
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return the schemeCost
	 */
	public Integer getSchemeCost() {
		return schemeCost;
	}

	/**
	 * @param schemeCost the schemeCost to set
	 */
	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	/**
	 * @return the state
	 */
	public SchemeState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(SchemeState state) {
		this.state = state;
	}

	/**
	 * @return the mode
	 */
	public SalesMode getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}

	/**
	 * @return the lottery
	 */
	public Lottery getLottery() {
		return lottery;
	}

	/**
	 * @param lottery the lottery to set
	 */
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
    public String getSchemeNum(){
    	return lottery.getSchemeNumber(schemeId);
    }
    public Integer getBetUnits(){
    	return this.units*this.multiple;
    }


	/**
	 * @return the playType
	 */
	public Integer getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Integer playType) {
		this.playType = playType;
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
    public Boolean getFirstWinUnitsWon(){
	   if(this.firstWinUnits>0||this.firstWinUnits>0){
			return true;
		}else{
			return false;
		}
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
	public Boolean getSecondWinUnitsWon(){
		   if(this.secondWinUnits>0||this.secondWinUnits>0){
				return true;
			}else{
				return false;
			}
	}
	/**
	 * @return the thirdWinUnits
	 */
	public Integer getThirdWinUnits() {
		return thirdWinUnits;
	}

	/**
	 * @param thirdWinUnits the thirdWinUnits to set
	 */
	public void setThirdWinUnits(Integer thirdWinUnits) {
		this.thirdWinUnits = thirdWinUnits;
	}
	public Boolean getThirdWinUnitsWon(){
		   if(this.thirdWinUnits>0||this.thirdWinUnits>0){
				return true;
			}else{
				return false;
			}
	}
	/**
	 * @return the fourthWinUnits
	 */
	public Integer getFourthWinUnits() {
		return fourthWinUnits;
	}

	/**
	 * @param fourthWinUnits the fourthWinUnits to set
	 */
	public void setFourthWinUnits(Integer fourthWinUnits) {
		this.fourthWinUnits = fourthWinUnits;
	}
	public Boolean getFourthWinUnitsWon(){
		   if(this.fourthWinUnits>0||this.fourthWinUnits>0){
				return true;
			}else{
				return false;
			}
	}
	/**
	 * @return the fifthWinUnits
	 */
	public Integer getFifthWinUnits() {
		return fifthWinUnits;
	}

	/**
	 * @param fifthWinUnits the fifthWinUnits to set
	 */
	public void setFifthWinUnits(Integer fifthWinUnits) {
		this.fifthWinUnits = fifthWinUnits;
	}
	public Boolean getFifthWinUnitsWon(){
		   if(this.fifthWinUnits>0||this.fifthWinUnits>0){
				return true;
			}else{
				return false;
			}
	}
	/**
	 * @return the sixthWinUnits
	 */
	public Integer getSixthWinUnits() {
		return sixthWinUnits;
	}

	/**
	 * @param sixthWinUnits the sixthWinUnits to set
	 */
	public void setSixthWinUnits(Integer sixthWinUnits) {
		this.sixthWinUnits = sixthWinUnits;
	}
	public Boolean getSixthWinUnitsWon(){
		   if(this.sixthWinUnits>0||this.sixthWinUnits>0){
				return true;
			}else{
				return false;
			}
	}

	/**
	 * @return the shareType
	 */
	public ShareType getShareType() {
		return shareType;
	}

	/**
	 * @param shareType the shareType to set
	 */
	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}
	
	public Boolean getCanSee() {
		if(null==this.shareType)return false;
		if(this.shareType.equals(ShareType.SELF))return false;
		if(this.shareType.equals(ShareType.TOGETHER))return true;
		return true;
	}
	public Boolean getWon() {
		if(null!=firstWinUnits||null!=secondWinUnits||null!=thirdWinUnits
				||null!=fourthWinUnits||null!=fifthWinUnits||null!=sixthWinUnits||null!=sevenWinUnits){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public Integer getSevenWinUnits() {
		return sevenWinUnits;
	}

	public void setSevenWinUnits(Integer sevenWinUnits) {
		this.sevenWinUnits = sevenWinUnits;
	}
	
	public Boolean getSevenWinUnitsWon(){
		   if(this.sevenWinUnits>0||this.sevenWinUnits>0){
				return true;
			}else{
				return false;
			}
	}
}
