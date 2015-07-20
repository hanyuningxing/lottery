package com.cai310.lottery.entity.football;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.Lottery;
@Entity
@Table(name = "football_totalScore")
public class TotalScore extends IdEntity implements CreateMarkable,UpdateMarkable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7431119254534439792L;
	/** 彩种类型 */
   private Lottery lotteryType;
   private Long matchId;
   private Long companyId;
   private Float firstGoal;
   private Float firstUpOdds;
   private Float firstDownOdds;
   private Float goal;
   private Float upOdds;
   private Float downOdds;
   private Short result;
   private Date lastModifyTime;
   private Date createTime;
   public TotalScore() {
  	 
   }
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
	 @Column public Float getFirstGoal() {
		return firstGoal;
	}
	 @Column public void setFirstGoal(Float firstGoal) {
		this.firstGoal = firstGoal;
	}
	 @Column public Float getFirstUpOdds() {
		return firstUpOdds;
	}
	 @Column public void setFirstUpOdds(Float firstUpOdds) {
		this.firstUpOdds = firstUpOdds;
	}
	 @Column public Float getFirstDownOdds() {
		return firstDownOdds;
	}
	 @Column public void setFirstDownOdds(Float firstDownOdds) {
		this.firstDownOdds = firstDownOdds;
	}
	 @Column public Float getGoal() {
		return goal;
	}
	 @Column public void setGoal(Float goal) {
		this.goal = goal;
	}
	 @Column public Float getUpOdds() {
		return upOdds;
	}
	 @Column public void setUpOdds(Float upOdds) {
		this.upOdds = upOdds;
	}
	 @Column public Float getDownOdds() {
		return downOdds;
	}
	 @Column public void setDownOdds(Float downOdds) {
		this.downOdds = downOdds;
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
