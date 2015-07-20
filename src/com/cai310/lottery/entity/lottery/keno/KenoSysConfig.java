package com.cai310.lottery.entity.lottery.keno;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统设置表
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX +"KENO_SYS_CONFIG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KenoSysConfig implements Serializable {
	
	private static final long serialVersionUID = -6762918794305631414L;

	/** 暂停标致 */
	public static final String PAUSE = "_Pause";

	/** 提前开售天数 */
	public final static String BEFORE_SALE_DAYS = "_BeforeSaleDays";

	/** 提前开售与截止的时间 */
	public final static String BEFORE_SALE_TIMES = "_BeforeSaleTimes";

	/** 第天最大期号 */
	public final static String MAX_PERIOD = "_MaxPeriod";

	/** 每期的间隔时间（以分钟计） */
	public final static String PERIOD_MINUTES = "_PeriodMinutes";

	/** 名称 */
	private String keyName;
	/** 值 */
	private String keyValue;
	/** 备注 */
	private String remark;

	@Id
	@Column(name = "keyName", nullable = false)
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Column(name = "keyValue", nullable = false)
	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
