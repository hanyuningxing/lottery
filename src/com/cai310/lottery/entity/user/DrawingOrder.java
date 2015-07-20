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
import com.cai310.lottery.common.DrawingOrderState;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "DRAWING_ORDER")
public class DrawingOrder extends IdEntity implements CreateMarkable, UpdateMarkable, Serializable {
	private static final long serialVersionUID = 6420283302235215768L;
	// Fields
	private Long userId;
	private String userName;
	private String realName;
	private BigDecimal money;
	private Date sendtime;
	private DrawingOrderState state;
	
    private String partBankProvince;
	
	private String partBankCity;
	
	private String partBankName;
	
	/** 银行卡开户银行名称 */
	private String bankName;

	/** 银行卡号码 */
	private String bankAccounts;
	private String mobile;
	private Long funddetailId;
	private Long reFunddetailId;
	private Boolean enable;
	private Long checkUserid;
	private String checkUsername;
	private Date checktime;
	private Long confirmUserid;
	private String confirmUsername;
	private Date confirmtime;
	
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	/** 版本号,用于实现乐观锁 */
	private Integer version;

	// Constructors

	/** default constructor */
	public DrawingOrder() {
	}

	// 初始化
	public DrawingOrder(User user, BigDecimal money, BankInfo bankInfo, String mobile, Long funddetailId) {
		this.userId = user.getId();
		this.userName = user.getUserName();
		this.realName = user.getInfo().getRealName();
		this.money = money;
		this.bankAccounts = bankInfo.getBankCard();
		this.bankName = bankInfo.getBankName();
		this.partBankCity = bankInfo.getPartBankCity();
		this.partBankProvince = bankInfo.getPartBankProvince();
		this.partBankName = bankInfo.getPartBankName();
		this.mobile = mobile;
		this.funddetailId = funddetailId;
		this.enable = Boolean.TRUE;
		this.sendtime = new Date();
		state = DrawingOrderState.CHECKING;
	}

	@Column( nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column( nullable = false, length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "money", nullable = false, precision = 18, scale = 4)
	public BigDecimal getMoney() {
		return this.money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sendtime", nullable = false, length = 19)
	public Date getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	@Column( nullable = false, length = 100)
	public String getBankAccounts() {
		return this.bankAccounts;
	}

	public void setBankAccounts(String bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	@Column(name = "mobile", length = 50)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column( nullable = false)
	public Long getFunddetailId() {
		return this.funddetailId;
	}

	public void setFunddetailId(Long funddetailId) {
		this.funddetailId = funddetailId;
	}

	@Column
	public Long getReFunddetailId() {
		return this.reFunddetailId;
	}

	public void setReFunddetailId(Long reFunddetailId) {
		this.reFunddetailId = reFunddetailId;
	}

	@Column(name = "enable", nullable = false)
	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column
	public Long getCheckUserid() {
		return this.checkUserid;
	}

	public void setCheckUserid(Long checkUserid) {
		this.checkUserid = checkUserid;
	}

	@Column(length = 50)
	public String getCheckUsername() {
		return this.checkUsername;
	}

	public void setCheckUsername(String checkUsername) {
		this.checkUsername = checkUsername;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checktime", length = 19)
	public Date getChecktime() {
		return this.checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	@Column
	public Long getConfirmUserid() {
		return this.confirmUserid;
	}

	public void setConfirmUserid(Long confirmUserid) {
		this.confirmUserid = confirmUserid;
	}

	@Column( length = 50)
	public String getConfirmUsername() {
		return this.confirmUsername;
	}

	public void setConfirmUsername(String confirmUsername) {
		this.confirmUsername = confirmUsername;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getConfirmtime() {
		return this.confirmtime;
	}

	public void setConfirmtime(Date confirmtime) {
		this.confirmtime = confirmtime;
	}

	@Column(nullable = false)
	public DrawingOrderState getState() {
		return state;
	}

	public void setState(DrawingOrderState state) {
		this.state = state;
	}

	@Column(nullable = false, length = 20)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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
	@Column(length = 200)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
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
	@Transient
	public BigDecimal getCommission(){
		if(null==this.money)return null;
		BigDecimal commission = this.money.multiply(BigDecimal.valueOf(0.00));
		if(commission.doubleValue()>Double.valueOf(50)){
			commission = BigDecimal.valueOf(50);
		}
		return commission;
	}
	@Transient
	public BigDecimal getDrawingMoney(){
		if(null==this.money)return null;
		BigDecimal commission = getCommission();
		return this.money.subtract(commission);
	}

}