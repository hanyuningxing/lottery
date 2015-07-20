package com.cai310.lottery.entity.lottery;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cai310.entity.CreateMarkable;

/**
 * 自动跟单任务队列
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AUTO_FOLLOW_QUEUE")
@Entity
public class AutoFollowQueue implements Serializable, CreateMarkable {
	private static final long serialVersionUID = -510090145468474301L;

	/**
	 * 编号
	 * 
	 * @see com.cai310.lottery.entity.lottery.AutoFollowQueueId
	 */
	private AutoFollowQueueId id;

	/** 创建时间 */
	private Date createTime;

	/**
	 * @return the {@link #id}
	 * 
	 */
	@EmbeddedId
	public AutoFollowQueueId getId() {
		return id;
	}

	/**
	 * @param id the {@link #id} to set
	 */
	public void setId(AutoFollowQueueId id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
