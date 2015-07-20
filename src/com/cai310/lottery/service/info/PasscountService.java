package com.cai310.lottery.service.info;

import com.cai310.lottery.entity.lottery.Period;

/**
 *  过关统计接口
 * 
 */
@SuppressWarnings("unchecked") 
public interface PasscountService {
	public void createPasscountXml(Period period) throws Exception;

}
