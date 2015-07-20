package com.cai310.lottery.web.controller.info.passcount.form;

import java.math.BigDecimal;
import java.text.NumberFormat;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
public class ZcSchemeWonInfo{

	private static final long serialVersionUID = 4272390436858509156L;
	private Integer id;
	private Lottery lottery;//彩种
	private Integer playType;//玩法
	private Long schemeId;
	private String schemeNum;
	private Integer lost0;
	private Integer lost1;
	private Integer lost2;
	private Integer lost3;
	private Integer passcount;
	
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
	
	/** 方案奖金 */
	private BigDecimal prize;
	
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
	 * @return the lost0
	 */
	public Integer getLost0() {
		return lost0;
	}

	/**
	 * @param lost0 the lost0 to set
	 */
	public void setLost0(Integer lost0) {
		this.lost0 = lost0;
	}

	/**
	 * @return the lost1
	 */
	public Integer getLost1() {
		return lost1;
	}

	/**
	 * @param lost1 the lost1 to set
	 */
	public void setLost1(Integer lost1) {
		this.lost1 = lost1;
	}

	/**
	 * @return the lost2
	 */
	public Integer getLost2() {
		return lost2;
	}

	/**
	 * @param lost2 the lost2 to set
	 */
	public void setLost2(Integer lost2) {
		this.lost2 = lost2;
	}

	/**
	 * @return the lost3
	 */
	public Integer getLost3() {
		return lost3;
	}

	/**
	 * @param lost3 the lost3 to set
	 */
	public void setLost3(Integer lost3) {
		this.lost3 = lost3;
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
    public String getLost0Per(){
    	NumberFormat nf = NumberFormat.getPercentInstance();
    	nf.setMaximumFractionDigits(2);
    	nf.setMinimumFractionDigits(2);
    	return nf.format(Double.valueOf(this.lost0)/(Double.valueOf(this.units)*Double.valueOf(this.multiple)));
    }
    public String getLost1Per(){
    	NumberFormat nf = NumberFormat.getPercentInstance(); 
    	nf.setMaximumFractionDigits(2);
    	nf.setMinimumFractionDigits(2);
    	return nf.format(Double.valueOf(this.lost1)/(Double.valueOf(this.units)*Double.valueOf(this.multiple)));
    }
    public Boolean getWon(){
    	if(null==this.getLottery())return false;
    	if(Lottery.SFZC.equals(this.getLottery())){
    		if(null==this.getPlayType())return false;
    		if(Integer.valueOf(0).equals(this.getPlayType())){
    			//14
    			if(this.lost0>0||this.lost1>0){
    				return true;
    			}else{
    				return false;
    			}
			}else if(Integer.valueOf(1).equals(this.getPlayType())){
				//9
				if(this.lost0>0){
    				return true;
    			}else{
    				return false;
    			}
			}
    	}else if(Lottery.SCZC.equals(this.getLottery())){
    		if(this.lost0>0){
				return true;
			}else{
				return false;
			}
    	}if(Lottery.LCZC.equals(this.getLottery())){
    		if(this.lost0>0){
				return true;
			}else{
				return false;
			}
    	}
    	return false;
    }
    public Boolean getLost0Won(){
    	if(this.lost0>0||this.lost0>0){
			return true;
		}else{
			return false;
		}
    }
    public Boolean getLost1Won(){
    	if(this.lost1>0||this.lost1>0){
			return true;
		}else{
			return false;
		}
    }
	/**
	 * @return the prize
	 */
	public BigDecimal getPrize() {
		return prize;
	}

	/**
	 * @param prize the prize to set
	 */
	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	/**
	 * @return the passcount
	 */
	public Integer getPasscount() {
		return passcount;
	}

	/**
	 * @param passcount the passcount to set
	 */
	public void setPasscount(Integer passcount) {
		this.passcount = passcount;
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
	
}
