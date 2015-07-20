package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.cache.CacheConstant;

/**
 * 胜负足彩比赛对阵
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SFZC_MATCH")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_ZC_MATCH_CACHE_REGION)
public class SfzcMatch extends ZcMatch {
	private static final long serialVersionUID = -2778587894833115033L;
	
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
