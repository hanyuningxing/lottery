package com.cai310.lottery.factory;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;

/**
 * 数据模型工厂类
 * 
 */
@Component
public class DataModelFactory {

	public PeriodData getPeriodData(Lottery lotteryType) {
		switch (lotteryType) {
		case SSQ:
			return new SsqPeriodData();
		}
		return null;
	}
}
