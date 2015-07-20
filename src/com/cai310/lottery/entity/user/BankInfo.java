package com.cai310.lottery.entity.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 银行卡信息
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "USER_BANK_INFO")
public class BankInfo implements CreateMarkable, UpdateMarkable, Serializable {
	private static final long serialVersionUID = -6038223448159787776L;
	private Long id;
	private String partBankProvince;
	
	private String partBankCity;
	
	private String partBankName;
	
	/** 银行卡开户银行名称 */
	private String bankName;

	/** 银行卡号码 */
	private String bankCard;

	/** 购彩账户 */
	private User user;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	@Id
	@Column(name = "id")
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = { @Parameter(name = "property", value = "user") })
	@GeneratedValue(generator = "generator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return {@link #user}
	 */
	@OneToOne(optional = false)
	@PrimaryKeyJoinColumn
	public User getUser() {
		return user;
	}

	/**
	 * @param user the {@link #user} to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return {@link #bankName}
	 */
	@Column(length = 200)
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the {@link #bankName} to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return {@link #bankCard}
	 */
	@Column(length = 30)
	public String getBankCard() {
		return bankCard;
	}

	/**
	 * @param bankCard the {@link #bankCard} to set
	 */
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	@Column(length = 30)
	public String getPartBankProvince() {
		return partBankProvince;
	}

	public void setPartBankProvince(String partBankProvince) {
		this.partBankProvince = partBankProvince;
	}
	@Column(length = 30)
	public String getPartBankCity() {
		return partBankCity;
	}

	public void setPartBankCity(String partBankCity) {
		this.partBankCity = partBankCity;
	}
	@Column(length = 200)
	public String getPartBankName() {
		return partBankName;
	}

	public void setPartBankName(String partBankName) {
		this.partBankName = partBankName;
	}

}
