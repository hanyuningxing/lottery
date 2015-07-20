package com.cai310.lottery.entity.football;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.entity.IdEntity;
import com.cai310.lottery.cache.CacheConstant;
@Entity
@Table(name = "football_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_LETGOAL_CACHE_REGION)
public class Company extends IdEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3495524742155208254L;
	private String name_cn;
	private String name_e;
	private String name_short;
	private Boolean isLetgoal;
	private Boolean isTotalScore;
	private Boolean isStandard;
	private Long win310Id;
	 @Column public String getName_cn() {
		return name_cn;
	}
	 @Column public void setName_cn(String name_cn) {
		this.name_cn = name_cn;
	}
	 @Column public String getName_e() {
		return name_e;
	}
	 @Column public void setName_e(String name_e) {
		this.name_e = name_e;
	}
	 @Column public String getName_short() {
		return name_short;
	}
	 @Column public void setName_short(String name_short) {
		this.name_short = name_short;
	}
	 @Column public Boolean getIsLetgoal() {
		return isLetgoal;
	}
	 @Column public void setIsLetgoal(Boolean isLetgoal) {
		this.isLetgoal = isLetgoal;
	}
	 @Column public Boolean getIsTotalScore() {
		return isTotalScore;
	}
	 @Column public void setIsTotalScore(Boolean isTotalScore) {
		this.isTotalScore = isTotalScore;
	}
	 @Column public Boolean getIsStandard() {
		return isStandard;
	}
	 @Column public void setIsStandard(Boolean isStandard) {
		this.isStandard = isStandard;
	}
	 @Column public Long getWin310Id() {
		return win310Id;
	}
	public void setWin310Id(Long win310Id) {
		this.win310Id = win310Id;
	}

	
	
	
}
