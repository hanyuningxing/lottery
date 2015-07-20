package com.cai310.lottery.entity.lottery.keno.sdel11to5;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX +"SD_EL11TO5_ISSUE_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SdEl11to5IssueData extends KenoPeriod {
	private static final long serialVersionUID = -8586729818322827581L;
	
	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.SDEL11TO5;
	}

}
