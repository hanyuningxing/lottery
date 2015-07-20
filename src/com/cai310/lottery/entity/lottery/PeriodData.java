package com.cai310.lottery.entity.lottery;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.UpdateMarkable;

@MappedSuperclass
public abstract class PeriodData implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 7546915007293796953L;
	/** 期ID */
	protected Long periodId;
	/** 版本号 */
	protected Integer version;

	protected String result; // 文本开奖结果
	/** 修改时间 */
	protected Date lastModifyTime;

	/** 创建时间 */
	private Date createTime;

	@Id
	@Column(name = "periodId", nullable = false)
	public Long getPeriodId() {
		return this.periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	@Version
	@Column(name = "version", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastModifyTime", length = 23)
	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the result
	 */
	@Column(name = "result", length = 50)
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	

}
