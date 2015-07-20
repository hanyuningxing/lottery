package com.cai310.lottery.entity.lottery.keno.ahkuai3;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AH_KUAI3_ISSUE_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AhKuai3IssueData extends KenoPeriod {

	private static final long serialVersionUID = 3560774628093948505L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.AHKUAI3;
	}

}
