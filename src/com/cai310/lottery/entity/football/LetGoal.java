package com.cai310.lottery.entity.football;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
@Entity
@Table(name = "football_letGoal")
public class LetGoal extends IdEntity implements CreateMarkable,UpdateMarkable{
     /**
	 * 
	 */
	private static final long serialVersionUID = -972398011938497302L;
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
     public LetGoal() {
    	 
     }
//     /**
// 	 * 在没有该比赛。该公司记录的时候生成
// 	 */
//     public LetGoal(LetGoalOddsData letGoalOddsData) {
//     	this.setScheduleID(letGoalOddsData.getScheduleID());
//     	this.setCompanyID(letGoalOddsData.getCompanyID());
//     	
//     	
//     	this.setFirstGoal(letGoalOddsData.getFirstGoal());
//     	this.setFirstUpOdds(letGoalOddsData.getFirstUpOdds());
//     	this.setFirstDownOdds(letGoalOddsData.getFirstDownOdds());
//     	
//     	this.setGoal(letGoalOddsData.getGoal());
//     	this.setUpOdds(letGoalOddsData.getUpOdds());
//     	this.setDownOdds(letGoalOddsData.getDownOdds());
//     	
//     	this.setClosePan(letGoalOddsData.isClosePan());
//     	this.setZouDi(letGoalOddsData.isZouDi());
//     	
//     	this.setModifyTime(new Date());
// 	}
//   ///数据比对的时候用
//     public LetGoal(LetGoal oldLetGoal,LetGoalOddsData letGoalOddsData) {
//     	this.setScheduleID(letGoalOddsData.getScheduleID());
//     	this.setCompanyID(letGoalOddsData.getCompanyID());
//     	
//     	this.setFirstGoal(letGoalOddsData.getFirstGoal());
//     	this.setFirstUpOdds(letGoalOddsData.getFirstUpOdds());
//     	this.setFirstDownOdds(letGoalOddsData.getFirstDownOdds());
//     	
//     	this.setGoal(letGoalOddsData.getGoal());
//     	this.setUpOdds(letGoalOddsData.getUpOdds());
//     	this.setDownOdds(letGoalOddsData.getDownOdds());
//     	
//     	this.setClosePan(letGoalOddsData.isClosePan());
//     	this.setZouDi(letGoalOddsData.isZouDi());
// 	} 
//     ///更新时候使用
//     public void getUpdateLetGoalData(LetGoalOddsData letGoalOddsData) {
//     	this.setScheduleID(letGoalOddsData.getScheduleID());
//     	this.setCompanyID(letGoalOddsData.getCompanyID());
//     	this.setFirstGoal(letGoalOddsData.getFirstGoal());
//     	this.setFirstUpOdds(letGoalOddsData.getFirstUpOdds());
//     	this.setFirstDownOdds(letGoalOddsData.getFirstDownOdds());
//     	this.setGoal(letGoalOddsData.getGoal());
//     	this.setUpOdds(letGoalOddsData.getUpOdds());
//     	this.setDownOdds(letGoalOddsData.getDownOdds());
//     	this.setClosePan(letGoalOddsData.isClosePan());
//     	this.setZouDi(letGoalOddsData.isZouDi());
//     	this.setModifyTime(new Date());
// 	}
//     
//     
//	/**
//	 * 在没有该比赛。该公司记录的时候生成
//	 */
//    public LetGoal(NowLetGoalOddsData nowLetGoalOddsData) {
//    	this.setScheduleID(nowLetGoalOddsData.getScheduleID());
//    	this.setCompanyID(nowLetGoalOddsData.getCompanyID());
//    	
//    	
//    	this.setFirstGoal(nowLetGoalOddsData.getGoal());
//    	this.setFirstUpOdds(nowLetGoalOddsData.getUpOdds());
//    	this.setFirstDownOdds(nowLetGoalOddsData.getDownOdds());
//    	
//    	this.setGoal(nowLetGoalOddsData.getGoal());
//    	this.setUpOdds(nowLetGoalOddsData.getUpOdds());
//    	this.setDownOdds(nowLetGoalOddsData.getDownOdds());
//    	
//    	this.setClosePan(nowLetGoalOddsData.isClosePan());
//    	this.setZouDi(nowLetGoalOddsData.isZouDi());
//    	
//    	this.setModifyTime(new Date());
//	}
//   ///数据比对的时候用
//    public LetGoal(LetGoal oldLetGoal,NowLetGoalOddsData nowLetGoalOddsData) {
//    	this.setScheduleID(nowLetGoalOddsData.getScheduleID());
//    	this.setCompanyID(nowLetGoalOddsData.getCompanyID());
//    	
//    	this.setFirstGoal(oldLetGoal.getFirstGoal());
//    	this.setFirstUpOdds(oldLetGoal.getFirstUpOdds());
//    	this.setFirstDownOdds(oldLetGoal.getFirstDownOdds());
//    	
//    	this.setGoal(nowLetGoalOddsData.getGoal());
//    	this.setUpOdds(nowLetGoalOddsData.getUpOdds());
//    	this.setDownOdds(nowLetGoalOddsData.getDownOdds());
//    	
//    	this.setClosePan(nowLetGoalOddsData.isClosePan());
//    	this.setZouDi(nowLetGoalOddsData.isZouDi());
//	}
//    ///更新时候使用
//    public void getUpdateLetGoalData(NowLetGoalOddsData nowLetGoalOddsData) {
//    	this.setScheduleID(nowLetGoalOddsData.getScheduleID());
//    	this.setCompanyID(nowLetGoalOddsData.getCompanyID());
//    	this.setGoal(nowLetGoalOddsData.getGoal());
//    	this.setUpOdds(nowLetGoalOddsData.getUpOdds());
//    	this.setDownOdds(nowLetGoalOddsData.getDownOdds());
//    	this.setClosePan(nowLetGoalOddsData.isClosePan());
//    	this.setZouDi(nowLetGoalOddsData.isZouDi());
//    	this.setModifyTime(new Date());
//	}
    @Column
 	public Date getCreateTime() {
 		return createTime;
 	}

 	public void setCreateTime(Date createTime) {
 		this.createTime = createTime;
 	}
 	@Column
 	public Date getLastModifyTime() {
 		return lastModifyTime;
 	}

 	public void setLastModifyTime(Date lastModifyTime) {
 		this.lastModifyTime = lastModifyTime;

 	}
 	@Column
	public Lottery getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
	@Column
	public Long getMatchId() {
		return matchId;
	}
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	@Column
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Column
	public Float getFirstGoal() {
		return firstGoal;
	}
	public void setFirstGoal(Float firstGoal) {
		this.firstGoal = firstGoal;
	}
	@Column
	public Float getFirstUpOdds() {
		return firstUpOdds;
	}
	public void setFirstUpOdds(Float firstUpOdds) {
		this.firstUpOdds = firstUpOdds;
	}
	@Column
	public Float getFirstDownOdds() {
		return firstDownOdds;
	}
	public void setFirstDownOdds(Float firstDownOdds) {
		this.firstDownOdds = firstDownOdds;
	}
	@Column
	public Float getGoal() {
		return goal;
	}
	public void setGoal(Float goal) {
		this.goal = goal;
	}
	@Column
	public Float getUpOdds() {
		return upOdds;
	}
	public void setUpOdds(Float upOdds) {
		this.upOdds = upOdds;
	}
	@Column
	public Float getDownOdds() {
		return downOdds;
	}
	public void setDownOdds(Float downOdds) {
		this.downOdds = downOdds;
	}
	@Column
	public Short getResult() {
		return result;
	}
	public void setResult(Short result) {
		this.result = result;
	}
	public int changeHashCode() {
		int returnValue = 17;
		if(null!=goal){
			returnValue = 37 * returnValue + Float.floatToIntBits(goal);
		}
		if(null!=upOdds){
			returnValue = 37 * returnValue + Float.floatToIntBits(upOdds);
		}
		if(null!=downOdds){
			returnValue = 37 * returnValue + Float.floatToIntBits(downOdds);
		}
		return returnValue;
	}
}