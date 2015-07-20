package com.cai310.lottery.entity.lottery.seven;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.entity.lottery.MissDataInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SEVEN_MISS")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SevenMissDataInfo extends MissDataInfo {

	private static final long serialVersionUID = -6940024647174099892L;
	
	
}
