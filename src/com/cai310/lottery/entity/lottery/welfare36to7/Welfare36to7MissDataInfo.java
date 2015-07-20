package com.cai310.lottery.entity.lottery.welfare36to7;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.entity.lottery.MissDataInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "WELFARE36To7_MISS")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Welfare36to7MissDataInfo extends MissDataInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4917977088046247411L;
	
}
