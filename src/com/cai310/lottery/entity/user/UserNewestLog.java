package com.cai310.lottery.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.UserNewestType;

/**
 * 用户最新动态日志
 * @author jack
 *
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "USER_NEWEST_LOG")
public class UserNewestLog extends IdEntity implements CreateMarkable{

	private static final long serialVersionUID = 6319518278712770014L;
	
	private Long userId;
	private String userName;
	private UserNewestType newestType;
	private String issueNumber;
	private Lottery lottery;
	private String playTypeName;
	private String schemeNumber;
	private SalesMode saleMode;//单、复
	private ShareType shareType;//合买、代购
	private float money;
		
	@Column
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.UserNewestType"),
			@Parameter(name = EnumType.TYPE, value = UserNewestType.SQL_TYPE) })
	@Column(nullable = false)
	public UserNewestType getNewestType() {
		return newestType;
	}

	public void setNewestType(UserNewestType newestType) {
		this.newestType = newestType;
	}

	@Column
	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false)
	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	@Column
	public String getPlayTypeName() {
		return playTypeName;
	}

	public void setPlayTypeName(String playTypeName) {
		this.playTypeName = playTypeName;
	}

	@Column
	public String getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SalesMode"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(name = "sales_mode", nullable = false, updatable = false)
	public SalesMode getSaleMode() {
		return saleMode;
	}

	public void setSaleMode(SalesMode saleMode) {
		this.saleMode = saleMode;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.ShareType"),
			@Parameter(name = EnumType.TYPE, value = ShareType.SQL_TYPE) })
	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	@Column
	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	/** 创建时间 */
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
