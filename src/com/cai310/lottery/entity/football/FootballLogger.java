package com.cai310.lottery.entity.football;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.Lottery;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "football_logger")
@Entity
public class FootballLogger extends IdEntity implements CreateMarkable{

	private static final long serialVersionUID = 2560091269922347708L;
	
	/** 创建时间 */
	private Date createTime;	
	/** 彩种 */
	private Lottery lotteryType;
	/** 请求内容 */
	private String reqContent;
	/** 返回或者报错内容 */
	private String respContent;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return {@link #lotteryType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(updatable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the {@link #lotteryType} to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
	@Lob
	@Column(updatable = false)
	public String getReqContent() {
		return reqContent;
	}

	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}
	@Lob
	@Column
	public String getRespContent() {
		return respContent;
	}

	public void setRespContent(String respContent) {
		this.respContent = respContent;
	}
}
