package com.cai310.lottery.entity.user.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.AgentDetailType;
import com.cai310.lottery.common.DrawingOrderState;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.exception.DataException;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AGENT_RELATION")
public class AgentRelation extends IdEntity implements CreateMarkable, UpdateMarkable, Serializable {
	private static final long serialVersionUID = 6420283302225215768L;
	private Long userId;
	private String userName;
	private String realName;
	private Long agentId;
	private Long groupId;
	private Integer pos;///位置关系0开始 会员级别顺序
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	/** 版本号,用于实现乐观锁 */
	private Integer version;

	// Constructors

	/** default constructor */
	public AgentRelation() {
	}
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	/**
	 * @return {@link #version}
	 */
	@Version
	@Column(nullable = false)
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Column
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column
	public Long getAgentId() {
		return agentId;
	}
	
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	@Column
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	@Column
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
	}
	@Column
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}


}