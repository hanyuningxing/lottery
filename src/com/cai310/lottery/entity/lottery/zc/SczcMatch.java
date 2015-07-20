package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.cache.CacheConstant;

/**
 * 四场进球足彩比赛对阵
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SCZC_MATCH")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_ZC_MATCH_CACHE_REGION)
public class SczcMatch extends ZcMatch {

	private static final long serialVersionUID = 7653329172814379910L;
	
	/** 获取比赛结果 */
	@Transient
	public String getHomeResult() {
		if (this.isCancel()) {
			return "取消";
		} else if (this.getHomeScore() == null) {
			return "";
		} else if (this.getHomeScore()>=3){
		    return "3";
		}else if(this.getHomeScore()>=0){
			return String.valueOf(this.getHomeScore());
		}
		return null;
	}
	
	/** 获取比赛结果 */
	@Transient
	public String getGuestResult() {
		if (this.isCancel()) {
			return "取消";
		} else if (this.getGuestScore() == null) {
			return "";
		} else if (this.getGuestScore()>=3){
		    return "3";
		}else if(this.getGuestScore()>=0){
			return String.valueOf(this.getGuestScore());
		}
		return null;
	}

}
