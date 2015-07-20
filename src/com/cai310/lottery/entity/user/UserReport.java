package com.cai310.lottery.entity.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.PayWay;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.UserWay;

/**
 * <b>用户反馈</b>
 * <p>
 * </p>
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "USER_REPORT")
@Entity
public class UserReport extends IdEntity implements CreateMarkable, UpdateMarkable, Serializable {
	private static final long serialVersionUID = 2822847678386304747L;
	private Long userid;
	private PlatformInfo agentInfo;
	private String report;
	private String answer;
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	/** 版本号,用于实现乐观锁 */
	private Integer version;
	@Column
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	@Column(length=5000)
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	@Version
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Column(length=5000)
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
	@Column
	public PlatformInfo getAgentInfo() {
		return agentInfo;
	}
	public void setAgentInfo(PlatformInfo agentInfo) {
		this.agentInfo = agentInfo;
	}
}
