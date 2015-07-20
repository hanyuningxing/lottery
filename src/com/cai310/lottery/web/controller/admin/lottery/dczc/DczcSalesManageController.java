package com.cai310.lottery.web.controller.admin.lottery.dczc;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.SalesManageController;

@Namespace("/admin/lottery/" + DczcConstant.KEY)
@Action("sales-manage")
public class DczcSalesManageController extends SalesManageController {
	private static final long serialVersionUID = -8251549219140388868L;

	@Resource(name = "dczcSaleControllerService")
	private SalesControllerService salesControllerService;

	@Resource(name = "dczcPrizeControllerService")
	private PrizeControllerService prizeControllerService;

	@Override
	public Lottery getLotteryType() {
		return Lottery.DCZC;
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
