package com.cai310.lottery.entity.lottery.keno.ahkuai3;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.entity.lottery.MissDataInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AhKUAI3_MISS")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AhKuai3MissDataInfo extends MissDataInfo {

	private static final long serialVersionUID = 7086397736746568623L;

}
