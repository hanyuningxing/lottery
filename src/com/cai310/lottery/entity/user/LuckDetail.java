package com.cai310.lottery.entity.user;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.LuckDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.Struts2Utils;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

/**
 * <b>资金明细.</b>
 * <p>
 * 资金明细是不能更新的, 所有字段都设置了[updatable = false].
 * </p>
 * 
 */
@Table(name =com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "LUCK_DETAIL")
@Entity
public class LuckDetail extends IdEntity implements CreateMarkable, Serializable {
	private static final long serialVersionUID = -5229478943870811148L;
	/** 版本号,用于实现乐观锁 */
	private Integer version;
	/** 用户ID */
	private Long userId;
	/** 用户名 */
	private String userName;

	/** 此次操作涉及的金额 */
	private BigDecimal money;

	/** 备注 */
	private String remark;

	/**
	 * 表示资金是进帐还是出帐
	 * 
	 * @see com.cai310.lottery.common.FundMode
	 */
	private FundMode mode;

	/**
	 * 资金明细类型
	 * 
	 * @see com.cai310.lottery.common.LuckDetailType
	 */
	private LuckDetailType type;
	
	/** 创建时间 */
	private Date createTime;

	public LuckDetail() {

	}

	public LuckDetail(User user, BigDecimal money, String remark, FundMode mode,
			LuckDetailType type) {
		this.setUserId(user.getId());
		this.setUserName(user.getUserName());
		this.setCreateTime(new Date());
		this.setMode(mode);
		this.setMoney(money);
		this.setRemark(remark);
		this.setType(type);
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
	 * @return {@link #userId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the {@link #userId} to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return {@link #userName}
	 */
	@Column(nullable = false, updatable = false,length=20)
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the {@link #userName} to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return {@link #money}
	 */
	@Column(nullable = false, updatable = false, precision = 18, scale = 4)
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * @param money the {@link #money} to set
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	/**
	 * @return {@link #remark}
	 */
	@Column(nullable = false, updatable = false, length = 500)
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the {@link #remark} to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return {@link #mode}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.FundMode"),
			@Parameter(name = EnumType.TYPE, value = FundMode.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public FundMode getMode() {
		return mode;
	}

	/**
	 * @param mode the {@link #mode} to set
	 */
	public void setMode(FundMode mode) {
		this.mode = mode;
	}

	/**
	 * @return {@link #type}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.LuckDetailType"),
			@Parameter(name = EnumType.TYPE, value = LuckDetailType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public LuckDetailType getType() {
		return type;
	}

	/**
	 * @param type the {@link #type} to set
	 */
	public void setType(LuckDetailType type) {
		this.type = type;
	}
	@Transient
	public String getTypeString(){
		return this.type.getTypeName();
	}
	@Transient
	public String getModeString(){
		return this.mode.getTypeName();
	}
	@Transient
	public String getCreateTimeStr(){
		return DateUtil.dateToStr(this.createTime,"yyyy-MM-dd HH:mm:ss");
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
}
