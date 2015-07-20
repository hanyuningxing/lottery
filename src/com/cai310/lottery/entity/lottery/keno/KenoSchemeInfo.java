package com.cai310.lottery.entity.lottery.keno;

import java.math.BigDecimal;

/**
 * 前台展示
 * @author Administrator
 *
 */
public class KenoSchemeInfo {
	/** 玩法名 */
	private String betTypeString;
	/** 期编号 */
	private Long periodId;
	/** 期号 */
	private String periodNumber;

	/** 方案发起人的用户编号 */
	private Long sponsorId;

	/** 方案发起人的用户名 */
	private String sponsorName;
	
	/** 方案金额 */
	private Integer schemeCost;
	
	/** 税前奖金 */
	private BigDecimal prize;

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
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

	public BigDecimal getPrize() {
		return prize;
	}

	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((betTypeString == null) ? 0 : betTypeString.hashCode());
		result = prime * result
				+ ((periodId == null) ? 0 : periodId.hashCode());
		result = prime * result
				+ ((periodNumber == null) ? 0 : periodNumber.hashCode());
		result = prime * result + ((prize == null) ? 0 : prize.hashCode());
		result = prime * result
				+ ((schemeCost == null) ? 0 : schemeCost.hashCode());
		result = prime * result
				+ ((sponsorId == null) ? 0 : sponsorId.hashCode());
		result = prime * result
				+ ((sponsorName == null) ? 0 : sponsorName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KenoSchemeInfo other = (KenoSchemeInfo) obj;
		if (betTypeString == null) {
			if (other.betTypeString != null)
				return false;
		} else if (!betTypeString.equals(other.betTypeString))
			return false;
		if (periodId == null) {
			if (other.periodId != null)
				return false;
		} else if (!periodId.equals(other.periodId))
			return false;
		if (periodNumber == null) {
			if (other.periodNumber != null)
				return false;
		} else if (!periodNumber.equals(other.periodNumber))
			return false;
		if (prize == null) {
			if (other.prize != null)
				return false;
		} else if (!prize.equals(other.prize))
			return false;
		if (schemeCost == null) {
			if (other.schemeCost != null)
				return false;
		} else if (!schemeCost.equals(other.schemeCost))
			return false;
		if (sponsorId == null) {
			if (other.sponsorId != null)
				return false;
		} else if (!sponsorId.equals(other.sponsorId))
			return false;
		if (sponsorName == null) {
			if (other.sponsorName != null)
				return false;
		} else if (!sponsorName.equals(other.sponsorName))
			return false;
		return true;
	}

	public String getBetTypeString() {
		return betTypeString;
	}

	public void setBetTypeString(String betTypeString) {
		this.betTypeString = betTypeString;
	}
}
