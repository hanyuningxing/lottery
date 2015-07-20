package com.cai310.lottery.entity.football;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.Lottery;
@Entity
@Table(name = "football_standard")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Standard extends IdEntity implements CreateMarkable,UpdateMarkable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -647839172297382914L;
	/** 彩种类型 */
	private Lottery lotteryType;
    private Long matchId;
    private Long companyId;
    private Float firstHomeWin;
    private Float firstStandoff;
    private Float firstGuestWin;
    private Float homeWin;
    private Float standoff;
    private Float guestWin;
    private Short result;
    private Date lastModifyTime;
	private Date createTime;
	
	 @Column public Lottery getLotteryType() {
		return lotteryType;
	}
	 @Column public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
	 @Column public Long getMatchId() {
		return matchId;
	}
	 @Column public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	 @Column public Long getCompanyId() {
		return companyId;
	}
	 @Column public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	 @Column public Float getFirstHomeWin() {
		return firstHomeWin;
	}
	 @Column public void setFirstHomeWin(Float firstHomeWin) {
		this.firstHomeWin = firstHomeWin;
	}
	 @Column public Float getFirstStandoff() {
		return firstStandoff;
	}
	 @Column public void setFirstStandoff(Float firstStandoff) {
		this.firstStandoff = firstStandoff;
	}
	 @Column public Float getFirstGuestWin() {
		return firstGuestWin;
	}
	 @Column public void setFirstGuestWin(Float firstGuestWin) {
		this.firstGuestWin = firstGuestWin;
	}
	 @Column public Float getHomeWin() {
		return homeWin;
	}
	 @Column public void setHomeWin(Float homeWin) {
		this.homeWin = homeWin;
	}
	 @Column public Float getStandoff() {
		return standoff;
	}
	 @Column public void setStandoff(Float standoff) {
		this.standoff = standoff;
	}
	 @Column public Float getGuestWin() {
		return guestWin;
	}
	 @Column public void setGuestWin(Float guestWin) {
		this.guestWin = guestWin;
	}
	 @Column public Short getResult() {
		return result;
	}
	 @Column public void setResult(Short result) {
		this.result = result;
	}
	 @Column public Date getLastModifyTime() {
		return lastModifyTime;
	}
	 @Column public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	 @Column public Date getCreateTime() {
		return createTime;
	}
	 @Column public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
