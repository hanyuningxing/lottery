package com.cai310.lottery.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "USER_LOGIN")
public class UserLogin extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 5764635371143687986L;

	// Fields
	private Long userId;
	private Boolean active = false;
	private Boolean clear = false;
	private String tryIp;
	private Date tryTime;
	private Date lastLoginTime;
	private String lastLoginIp;
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	public UserLogin() {
	}

	public UserLogin(Long userId, boolean active, String ipAddress) {
		this.userId = userId;
		this.active = active;
		this.clear = active;
		this.tryIp = ipAddress;
		this.tryTime = new Date();
	}

	@Column(nullable = false, updatable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(nullable = false,columnDefinition ="bit(1) default 0")
	public Boolean isActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(nullable = false,columnDefinition ="bit(1) default 0")
	public Boolean isClear() {
		return this.clear;
	}

	public void setClear(Boolean clear) {
		this.clear = clear;
	}

	@Column(nullable = false, length = 50)
	public String getTryIp() {
		return this.tryIp;
	}

	public void setTryIp(String tryIp) {
		this.tryIp = tryIp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getTryTime() {
		return this.tryTime;
	}

	public void setTryTime(Date tryTime) {
		this.tryTime = tryTime;
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

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

}