package com.cai310.lottery.entity.user;

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
import com.cai310.lottery.common.PayWay;
import com.cai310.lottery.common.UserWay;

/**
 * <b>在线支付.</b>
 * <p>
 * 在线支付是不能更新的, 所有字段都设置了[updatable = false],ifcheck除外.
 * </p>
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "IPSORDER")
@Entity
public class Ipsorder extends IdEntity implements CreateMarkable, UpdateMarkable, Serializable {
	private static final long serialVersionUID = 2822847678386304747L;
	private Long userid;
	private UserWay userWay;
	private PayWay payWay;
	private String userName;
	private String realName;
	private BigDecimal amount;
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	private Boolean ifcheck;
	private Long funddetailId;
	private String memo;
	/** 版本号,用于实现乐观锁 */
	private Integer version;
	/**
	 * @return the userName
	 */
	@Column(nullable = false, updatable = false,length=20)
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the realName
	 */
	@Column(nullable = false, updatable = false,length=20)
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the amount
	 */
	@Column(nullable = false, updatable = false ,precision = 18, scale = 4)
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	/**
	 * @return the ifcheck
	 */
	@Column(nullable = false)
	public Boolean getIfcheck() {
		return ifcheck;
	}

	/**
	 * @param ifcheck the ifcheck to set
	 */
	public void setIfcheck(Boolean ifcheck) {
		this.ifcheck = ifcheck;
	}
	/**
	 * @return the memo
	 */
	@Column(updatable = false)
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
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
	public Long getFunddetailId() {
		return funddetailId;
	}

	public void setFunddetailId(Long funddetailId) {
		this.funddetailId = funddetailId;
	}
	@Column(nullable = false, updatable = false)
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(nullable = false, updatable = false)
	public PayWay getPayWay() {
		return payWay;
	}

	public void setPayWay(PayWay payWay) {
		this.payWay = payWay;
	}
	@Column(nullable = true, updatable = false)
	public UserWay getUserWay() {
		return userWay;
	}

	public void setUserWay(UserWay userWay) {
		this.userWay = userWay;
	}
	///手机为了保持userId一致性
	@Transient
	public Long getUserId(){
		return this.getUserid();
	}

}
