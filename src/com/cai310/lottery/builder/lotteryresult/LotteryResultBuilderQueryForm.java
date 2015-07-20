package com.cai310.lottery.builder.lotteryresult;

import java.util.Set;

import com.cai310.lottery.common.Lottery;

/**
 * Description: 获奖信息——查询器表单参数<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class LotteryResultBuilderQueryForm {
	private Set<Lottery> lottery;
	public Set<Lottery> getLottery() {
		return lottery;
	}
	public void setLottery(Set<Lottery> lottery) {
		this.lottery = lottery;
	}
}
