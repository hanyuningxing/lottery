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
@Table(name = "football_standardDetail")
public class StandardDetail extends IdEntity implements CreateMarkable,UpdateMarkable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7789872465714675410L;
	private Long standardId;
    private Float homeWin;
    private Float standoff;
    private Float guestWin;
    private Date lastModifyTime;
   	private Date createTime;
	 @Column public Long getStandardId() {
		return standardId;
	}
	 @Column public void setStandardId(Long standardId) {
		this.standardId = standardId;
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
