package com.cai310.lottery.entity.football;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
@Entity
@Table(name = "football_totalScoreDetail")
public class TotalScoreDetail extends IdEntity implements CreateMarkable,UpdateMarkable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8660319116840593734L;
	private Long totalScoreId;
    private Float upOdds;
    private Float goal;
    private Float downOdds;
    private Date lastModifyTime;
   	private Date createTime;
	 @Column public Long getTotalScoreId() {
		return totalScoreId;
	}
	 @Column public void setTotalScoreId(Long totalScoreId) {
		this.totalScoreId = totalScoreId;
	}
	 @Column public Float getUpOdds() {
		return upOdds;
	}
	 @Column public void setUpOdds(Float upOdds) {
		this.upOdds = upOdds;
	}
	 @Column public Float getGoal() {
		return goal;
	}
	 @Column public void setGoal(Float goal) {
		this.goal = goal;
	}
	 @Column public Float getDownOdds() {
		return downOdds;
	}
	 @Column public void setDownOdds(Float downOdds) {
		this.downOdds = downOdds;
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
