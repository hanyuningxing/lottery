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

@Entity
@Table(name = "football_LetGoalDetail")
public class LetGoalDetail extends IdEntity implements CreateMarkable,UpdateMarkable{
    private Long letGoalId;
    private Float upOdds;
    private Float goal;
    private Float downOdds;
    private Date lastModifyTime;
	private Date createTime;
	public Float getUpOdds() {
		return upOdds;
	}
	public void setUpOdds(Float upOdds) {
		this.upOdds = upOdds;
	}
	public Float getGoal() {
		return goal;
	}
	public void setGoal(Float goal) {
		this.goal = goal;
	}
	public Float getDownOdds() {
		return downOdds;
	}
	public void setDownOdds(Float downOdds) {
		this.downOdds = downOdds;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getLetGoalId() {
		return letGoalId;
	}
	public void setLetGoalId(Long letGoalId) {
		this.letGoalId = letGoalId;
	}
   
}
