package com.cai310.lottery.entity.lottery.keno.ssc;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.entity.lottery.MissDataInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SSC_MISS")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SscMissDataInfo extends MissDataInfo {


	private static final long serialVersionUID = 5281129508050388794L;

	
}