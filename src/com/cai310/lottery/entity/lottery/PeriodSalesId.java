package com.cai310.lottery.entity.lottery;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cai310.lottery.common.SalesMode;

/**
 * 销售表复合主键
 * 
 */
@Embeddable
public class PeriodSalesId implements Serializable {

	private static final long serialVersionUID = 6621601528818060645L;

	/**
	 * 期次ID
	 */
	private Long periodId;

	/**
	 * 销售模式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 */
	private SalesMode salesMode;

	public PeriodSalesId() {
	}

	public PeriodSalesId(Long periodId, SalesMode salesMode) {
		this.periodId = periodId;
		this.salesMode = salesMode;
	}

	public PeriodSalesId(Long periodId) {
		this.periodId = periodId;
	}

	public PeriodSalesId(SalesMode salesMode) {
		this.salesMode = salesMode;
	}

	/**
	 * @return #{@link periodId}
	 */
	@Column(name = "periodId", nullable = false)
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId the periodId to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	/**
	 * @return #{@link salesMode}
	 */
	@Column(name = "salesMode", nullable = false)
	public SalesMode getSalesMode() {
		return salesMode;
	}

	/**
	 * @param salesMode the salesMode to set
	 */
	public void setSalesMode(SalesMode salesMode) {
		this.salesMode = salesMode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
