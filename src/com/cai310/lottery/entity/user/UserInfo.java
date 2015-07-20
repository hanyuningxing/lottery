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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;

/**
 * 用户个人信息
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "USER_INFO")
public class UserInfo implements CreateMarkable, UpdateMarkable,Serializable {
	private static final long serialVersionUID = 5895486389359222746L;
	
	private Long id;
	/** 真实姓名 */
	private String realName;

	/** 电话号码 */
	private String mobile;

	/** 电子邮箱 */
	private String email;

	/** QQ号码 */
	private String qq;

	/** 联系地址 */
	private String address;

	/** 邮政编码 */
	private String postcode;

	/** 身份证 */
	private String idCard;

	/** 购彩账户 */
	private User user;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	/** 版本号,用于实现乐观锁 */
	private Integer version;
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
	 * @return {@link #name}
	 */
	@Column(length = 20,name="name")
	public String getRealName() {
		return realName;
	}

	/**
	 * @param name the {@link #name} to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return {@link #mobile}
	 */
	@Column(length = 20)
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the {@link #mobile} to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return {@link #email}
	 */
	@Column(length = 30)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the {@link #email} to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return {@link #qq}
	 */
	@Column(length = 20)
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq the {@link #qq} to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return {@link #address}
	 */
	@Column(length = 200)
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the {@link #address} to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return {@link #postcode}
	 */
	@Column(length = 10)
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode the {@link #postcode} to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return {@link #idCard}
	 */
	@Column(length = 20,nullable = true)
	public String getIdCard() {
		return idCard;
	}

	/**
	 * @param idCard the {@link #idCard} to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
