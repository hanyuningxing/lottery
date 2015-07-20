package com.cai310.lottery.web.controller.admin.lottery.jclq;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.SalesManageController;

@Namespace("/admin/lottery/" + JclqConstant.KEY)
@Action("sales-manage")
public class JclqSalesManageController extends SalesManageController {
	private static final long serialVersionUID = -7278981858569566645L;

	@Resource(name = "jclqSaleControllerService")
	private SalesControllerService salesControllerService;

	@Resource(name = "jclqPrizeControllerService")
	private PrizeControllerService prizeControllerService;

	@Override
	public Lottery getLotteryType() {
		return Lottery.JCLQ;
	}

	@Override
	protected DoAnalysisService getDoAnalysisService() {
		// TODO:未注入相应DoAnalysisService服务实例
		throw new RuntimeException("未注入相应服务实例.");
	}

	@Override
	protected PrizeControllerService getPrizeControllerService() {
		return prizeControllerService;
	}

	@Override
	protected SalesControllerService getSalesControllerService() {
		return salesControllerService;
	}

	@Override
	protected Set<SaleWorkerCmd> getPrizeProcessCmds() {
		Set<SaleWorkerCmd> prizeProcessCmds = super.getPrizeProcessCmds();
		prizeProcessCmds.remove(SaleWorkerCmd.UpdatePrize);
		return prizeProcessCmds;
	}

}
