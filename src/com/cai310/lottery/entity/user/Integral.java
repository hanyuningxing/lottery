package com.cai310.lottery.entity.user;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;

/**
 *  积分
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "INTEGRAL")
public class Integral extends IdEntity implements CreateMarkable,  UpdateMarkable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3925784916639338717L;
	private String playName;
	private float bouns;
	private float scoreRate;
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	private String parent;
	/**
	 * @return the createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}
	public float getBouns() {
		return bouns;
	}

	public void setBouns(float bouns) {
		this.bouns = bouns;
	}

	public float getScoreRate() {
		return scoreRate;
	}

	public void setScoreRate(float scoreRate) {
		this.scoreRate = scoreRate;
	}

	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	
}
