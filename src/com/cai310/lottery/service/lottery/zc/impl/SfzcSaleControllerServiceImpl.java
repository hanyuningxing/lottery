package com.cai310.lottery.service.lottery.zc.impl;

import java.util.HashSet;
import java.util.Set;

import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.service.lottery.impl.SaleControllerServiceImpl;

/**
 * 暂时未使用到，在spring中定义
 * 
 * 
 * 
 */
public class SfzcSaleControllerServiceImpl extends SaleControllerServiceImpl {

	public Set<SaleWorkerCmd> getCanRunCmds(Period period) {
		Set<SaleWorkerCmd> cmds = new HashSet<SaleWorkerCmd>(0);
		if (period.isOnSale())
			cmds.add(SaleWorkerCmd.PauseSale);
		else if (period.isPaused())
			cmds.add(SaleWorkerCmd.ResumeSale);

		else{
			cmds.addAll(getAfterSaleCanRunCmds(period));

			if (period.isFinished())
				cmds.addAll(getAfterFinishCanRunCmds(period));
		}
		return cmds;
	}

}
