package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 四场进球足彩期数据实体类
 * 
 * 
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SCZC_PERIOD_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SczcPeriodData extends ZcPeriodData {

}