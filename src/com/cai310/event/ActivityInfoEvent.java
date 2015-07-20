package com.cai310.event;

import org.springframework.context.ApplicationEvent;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.entity.lottery.Scheme;

/**
 * Description: 中奖消息更新事件<br>
 * 发布中奖事件，用于更新中奖信息HTML；
 * @Copyright Copyright (c) 2011<br>
 * @Company 肇庆优盛科技
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class ActivityInfoEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private Lottery lottery;
	private long periodId;

	public long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(long periodId) {
		this.periodId = periodId;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public ActivityInfoEvent(Lottery lottery, long periodId) {
		super(lottery);
		this.lottery = lottery;
		this.periodId = periodId;
	}
}
