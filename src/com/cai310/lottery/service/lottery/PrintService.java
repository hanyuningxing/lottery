package com.cai310.lottery.service.lottery;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.Scheme;

/**
 * 打印服务接口
 * 
 */
public interface PrintService<T extends Scheme> {

	Lottery getLotteryType();

	void sendToPrint(T scheme);
}
