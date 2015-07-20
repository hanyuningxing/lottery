package com.cai310.lottery.entity.lottery.keno;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.keno.StatisticsType;

/**
 * 状态
 */
@MappedSuperclass
public abstract class Statistics extends IdEntity {

	private static final long serialVersionUID = 3640500889885173764L;
	/** 统计类型 */
	private StatisticsType type;
	/** 时间标志 */
	private String timeObject;
	/** 总方案数 */
	private int allSchemeNum;
	/** 总销售 */
	private BigDecimal allSale;
	/** 总奖金 */
	private BigDecimal allPrize;
	/** 创建时间 */
	private Date createTime;

	@Column(name = "type")
	public StatisticsType getType() {
		return type;
	}

	public void setType(StatisticsType type) {
		this.type = type;
	}

	@Column(name = "timeObject")
	public String getTimeObject() {
		return timeObject;
	}

	public void setTimeObject(String timeObject) {
		this.timeObject = timeObject;
	}

	@Column(name = "allSchemeNum")
	public int getAllSchemeNum() {
		return allSchemeNum;
	}

	public void setAllSchemeNum(int allSchemeNum) {
		this.allSchemeNum = allSchemeNum;
	}

	@Column(name = "allSale")
	public BigDecimal getAllSale() {
		return allSale;
	}

	public void setAllSale(BigDecimal allSale) {
		this.allSale = allSale;
	}

	@Column(name = "allPrize")
	public BigDecimal getAllPrize() {
		return allPrize;
	}

	public void setAllPrize(BigDecimal allPrize) {
		this.allPrize = allPrize;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
