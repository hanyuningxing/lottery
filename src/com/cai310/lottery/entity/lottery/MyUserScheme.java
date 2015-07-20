package com.cai310.lottery.entity.lottery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.cai310.lottery.common.SchemeState;

public class MyUserScheme implements Serializable{
	private static final long serialVersionUID = -5737037558757289767L;

	/**方案编号**/
	private Long schemeId;
	
	/** 期号 */
	private String periodNumber;

	/** 方案发起人的用户编号 */
	private Long sponsorId;

	/** 方案发起人的用户名 */
	private String sponsorName;
	
	/** 方案金额 **/
	private Integer schemeCost;
	
	/** 方案金额 **/
	private String wonDetail;
	
	/** 税后奖金 */
	private BigDecimal prizeAfterTax;
	
	/** 方案状态 **/
	private SchemeState state;
	
	/** 创建时间 */
	private Date createTime;

	public Long getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	public String getPeriodNumber() {
		return periodNumber;
	}
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	public Long getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public Integer getSchemeCost() {
		return schemeCost;
	}
	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}
	public String getWonDetail() {
		return wonDetail;
	}
	public void setWonDetail(String wonDetail) {
		this.wonDetail = wonDetail;
	}
	public BigDecimal getPrizeAfterTax() {
		return prizeAfterTax;
	}
	public void setPrizeAfterTax(BigDecimal prizeAfterTax) {
		this.prizeAfterTax = prizeAfterTax;
	}
	public SchemeState getState() {
		return state;
	}
	public void setState(SchemeState state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
