package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.cache.CacheConstant;

/**
 * 六场半全场足彩比赛对阵
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "LCZC_MATCH")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_ZC_MATCH_CACHE_REGION)
public class LczcMatch extends ZcMatch {

	private static final long serialVersionUID = -7492179071616276063L;

	/** 主队半场进球数 */
	private Integer halfHomeScore;

	/** 客队半场进球数 */
	private Integer halfGuestScore;

	/**
	 * @return {@link #halfHomeScore}
	 */
	@Column
	public Integer getHalfHomeScore() {
		return halfHomeScore;
	}

	/**
	 * @param halfHomeScore the {@link #halfHomeScore} to set
	 */
	public void setHalfHomeScore(Integer halfHomeScore) {
		this.halfHomeScore = halfHomeScore;
	}

	/**
	 * @return {@link #halfGuestScore}
	 */
	@Column
	public Integer getHalfGuestScore() {
		return halfGuestScore;
	}

	/**
	 * @param halfGuestScore the {@link #halfGuestScore} to set
	 */
	public void setHalfGuestScore(Integer halfGuestScore) {
		this.halfGuestScore = halfGuestScore;
	}
	
	/** 获取比赛结果 */
	@Transient
	public String getHalfResult() {
		if (this.isCancel()) {
			return "取消";
		} else if (this.getHalfHomeScore() == null || this.getHalfGuestScore() == null) {
			return "";
		} else if (this.getHalfHomeScore() > this.getHalfGuestScore()) {
			return "3";
		} else if (this.getHalfHomeScore().equals(this.getHalfGuestScore())) {
			return "1";
		} else if (this.getHalfHomeScore() < this.getHalfGuestScore()) {
			return "0";
		}
		return null;
	}
	
	/** 获取比赛结果 */
	@Transient
	public String getResult() {
		if (this.isCancel()) {
			return "取消";
		} else if (this.getHomeScore() == null || this.getGuestScore() == null) {
			return "";
		} else if (this.getHomeScore() > this.getGuestScore()) {
			return "3";
		} else if (this.getHomeScore().equals(this.getGuestScore())) {
			return "1";
		} else if (this.getHomeScore() < this.getGuestScore()) {
			return "0";
		}
		return null;
	}

}
