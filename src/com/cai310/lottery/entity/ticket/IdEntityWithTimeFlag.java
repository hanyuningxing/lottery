package com.cai310.lottery.entity.ticket;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;

@MappedSuperclass
public abstract class IdEntityWithTimeFlag extends IdEntity implements CreateMarkable,UpdateMarkable {

	protected Date createTime;
	protected Date lastModifyTime;
	
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}
