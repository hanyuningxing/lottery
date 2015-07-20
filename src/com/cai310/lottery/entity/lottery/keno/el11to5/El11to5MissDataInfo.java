package com.cai310.lottery.entity.lottery.keno.el11to5;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.entity.lottery.MissDataInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "EL11TO5_MISS")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class El11to5MissDataInfo extends MissDataInfo {

	private static final long serialVersionUID = -104858517145084015L;

	
	
}