package com.cai310.lottery.service.lottery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.ChasePlanEntityManager;
import com.cai310.lottery.service.lottery.ChasePlanService;
import com.cai310.lottery.service.user.UserEntityManager;
/**
 * 追号服务实现类.
 * 
 */
@Service("chasePlanServiceImpl")
@Transactional
public class ChasePlanServiceImpl implements ChasePlanService {

	@Autowired
	protected ChasePlanEntityManager chasePlanEntityManager;

	@Autowired
	protected UserEntityManager userEntityManager;
	// TODO:停止追号
	public void stopChasePlan(Long id) {
		ChasePlan chasePlan = chasePlanEntityManager.getChasePlan(id);
		if (chasePlan == null) {
			throw new ServiceException("追号记录不存在.");
		}
		try {
			chasePlan.stop();
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}

		chasePlan = chasePlanEntityManager.saveChasePlan(chasePlan);

		StringBuilder sb = new StringBuilder(50);
		sb.append("停止编号为").append(chasePlan.getId()).append("的追号.");
		
		userEntityManager.cancelPrepayment(chasePlan.getPrepaymentId(), FundDetailType.STOP_CHASE, sb.toString());
	}

}
