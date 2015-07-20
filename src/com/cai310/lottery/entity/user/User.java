package com.cai310.lottery.entity.user;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.SecurityLevel;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.exception.DataException;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "USER")
public class User extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 5919843711612214128L;
	
    public static final boolean NO_LOCK_STATUS = false;
	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 用户名 */
	private String userName;
	
	/**QQ标识ID号*/
	private String qqId;

	/** 账户密码 */
	private String password;

	/** 提款密码 */
	private String tkpassword;
	
	
	/** 支付密码 */
	private String payPassword;
	
	/** 提款是否需要验证 */
	private Boolean needValidateWhileTk = false;
	
	/** 是否需要设置支付密码*/
	private Boolean needPayPassword = false;

	/** 账户是否被锁定 */
	private boolean locked;

	/** 账户余额 */
	private BigDecimal remainMoney;
	
	/** 消费总额 */
	private BigDecimal consumptionMoney;
	
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	/**
	 * 用户信息
	 * 
	 * @see com.cai310.lottery.entity.user.BankInfo
	 */
	private UserInfo info;
	
	/** 用户战绩信息*/
	private UserGrade gradeInfo;
	
	/** 上级用户ID */
	private Long agentId;
	//媒体ID。记录用户的来源
	private Long mid;
	
	private Boolean validatedPhoneNo;
	
	private UserWay userWay;
	/**
	 * 银行卡信息
	 * 
	 * @see com.cai310.lottery.entity.user.BankInfo
	 */
	private BankInfo bank;
	
	/**账户积分*/
	private BigDecimal remainScore;

	@Column(nullable = false, length = 20, updatable = false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	@Column(nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = true, length = 32)
	public String getTkpassword() {
		return this.tkpassword;
	}

	public void setTkpassword(String tkpassword) {
		this.tkpassword = tkpassword;
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

	/**
	 * @return {@link #remainMoney}
	 */
	@Column(precision = 18, scale = 4, columnDefinition ="decimal(18,4) default '0'")
	public BigDecimal getRemainMoney() {
		return remainMoney;
	}

	/**
	 * @param remainMoney the {@link #remainMoney} to set
	 */
	public void setRemainMoney(BigDecimal remainMoney) {
		this.remainMoney = remainMoney;
	}

	/**
	 * @return {@link #consumptionMoney}
	 */
	@Column(precision = 18, scale = 4, columnDefinition ="decimal(18,4) default 0")
	public BigDecimal getConsumptionMoney() {
		return consumptionMoney;
	}

	/**
	 * @param consumptionMoney the {@link #consumptionMoney} to set
	 */
	public void setConsumptionMoney(BigDecimal consumptionMoney) {
		this.consumptionMoney = consumptionMoney;
	}

	/**
	 * @return {@link #locked}
	 */
	@Column(nullable = false, columnDefinition ="bit(1) default 0")
	public boolean isLocked() {
		return locked;
	}

	/**
	 * @param locked the {@link #locked} to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return {@link #bank}
	 */
	@OneToOne(optional = true, mappedBy = "user")
	public BankInfo getBank() {
		return bank;
	}
	/**
	 * @param bank the {@link #bank} to set
	 */
	public void setBank(BankInfo bank) {
		this.bank = bank;
	}
	
	@OneToOne(optional = true, mappedBy = "user")
	public UserInfo getInfo() {
		return info;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
	}
	
	@OneToOne(optional = true, mappedBy = "user")
	public UserGrade getGradeInfo() {
		return gradeInfo;
	}

	public void setGradeInfo(UserGrade gradeInfo) {
		this.gradeInfo = gradeInfo;
	}
	
	
	/* --------------------- logic method --------------------- */

	

	/**
	 * 增加账户余额
	 * 
	 * @param money 增加的金额
	 * @throws DataException
	 */
	public void addMoney(BigDecimal money) throws DataException {
		if (money == null || money.doubleValue() <= 0)
			throw new DataException("增加的金额不能为空或小于等于零.");

		this.remainMoney = (this.remainMoney == null) ? money : this.remainMoney.add(money);
	}

	/**
	 * 扣除账户余额
	 * 
	 * @param money 扣除的金额
	 * @throws DataException
	 */
	public void subtractMoney(BigDecimal money) throws DataException {
		if (money == null || money.doubleValue() <= 0)
			throw new DataException("扣除的金额不能为空或小于等于零.");
		if (this.remainMoney == null || this.remainMoney.doubleValue() == 0)
			throw new DataException("账户余额为零,不能扣除金额.");
		if (this.remainMoney.compareTo(money) < 0)
			throw new DataException("扣除的金额大于账户余额.");

		this.remainMoney = this.remainMoney.subtract(money);
	}

	/**
	 * 减少用户消费金额
	 * 
	 * @param money 减少的消费金额
	 * @throws DataException
	 */
	public void subtractConsumptionMoney(BigDecimal money) throws DataException {
		if (money == null || money.doubleValue() <= 0)
			throw new DataException("减少的消费金额不能为空或小于等于零.");
		else if (this.consumptionMoney == null || this.consumptionMoney.doubleValue() == 0)
			//先处理数据。以前没有统计的。现在进来这里报错。先设置为0。
			this.consumptionMoney = BigDecimal.valueOf(0);
			//throw new DataException("消费金额为零,不能减少消费金额.");
		else if (this.consumptionMoney.compareTo(money) < 0)
			//先处理数据。以前没有统计的。现在进来这里报错。先设置为0。
			this.consumptionMoney = BigDecimal.valueOf(0);
			//throw new DataException("减少消费金额大于用户消费金额.");

		this.consumptionMoney = this.consumptionMoney.subtract(money);
	}
	/**
	 * 判读是不是改用户的上级用户
	 * 
	 * @param money 增加的消费金额
	 * @throws DataException
	 */
	public void addConsumptionMoney(BigDecimal money) throws DataException {
		if (money == null || money.doubleValue() <= 0)
			throw new DataException("增加的消费金额不能为空或小于等于零.");

		this.consumptionMoney = (this.consumptionMoney == null) ? money : this.consumptionMoney.add(money);
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
	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
    @Column
	public Long getMid() {
		return mid;
	}
    
	public void setMid(Long mid) {
		this.mid = mid;
	}
	@Column(nullable = true)
	public UserWay getUserWay() {
		return userWay;
	}

	public void setUserWay(UserWay userWay) {
		this.userWay = userWay;
	}
	//手机为了一致性
	@Transient
	public Long getUserId(){
		return this.getId();
	}
	//手机为了一致性
	@Transient
	public String getUserPwd(){
		return this.getPassword();
	}
	@Column(columnDefinition ="bit(1) default 0")
	public Boolean getValidatedPhoneNo() {
		return validatedPhoneNo;
	}

	public void setValidatedPhoneNo(Boolean validatedPhoneNo) {
		this.validatedPhoneNo = validatedPhoneNo;
	}
	@Column(columnDefinition ="bit(1) default 0")
	public Boolean getNeedValidateWhileTk() {
		return needValidateWhileTk;
	}

	public void setNeedValidateWhileTk(Boolean needValidateWhileTk) {
		this.needValidateWhileTk = needValidateWhileTk;
	}
	@Column
	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	@Column(columnDefinition ="bit(1) default 0")
	public Boolean getNeedPayPassword() {
		return needPayPassword;
	}

	public void setNeedPayPassword(Boolean needPayPassword) {
		this.needPayPassword = needPayPassword;
	}
	@Column
	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}
	
	@Column(precision = 18, scale = 4, columnDefinition ="decimal(18,4) default '0'")
	public BigDecimal getRemainScore() {
		return remainScore;
	}

	public void setRemainScore(BigDecimal remainScore) {
		this.remainScore = remainScore;
	}

	@Transient
	public SecurityLevel getSecurityLevel() {
		int i = 0;
		
		if(this.validatedPhoneNo!=null && this.getValidatedPhoneNo()) {
			i++;
		}
		if(this.getInfo().getIdCard()!=null) {
			i++;
		}
		if(this.getBank().getBankCard()!=null) {
			i++;
		}
		if(this.getNeedValidateWhileTk()!=null && this.getNeedValidateWhileTk()) {
			i++;
		}
		if(this.getNeedPayPassword()!=null && this.getNeedPayPassword()) {
			i++;
		}
		return SecurityLevel.valueOfOrdinal(i);
	}
	
}