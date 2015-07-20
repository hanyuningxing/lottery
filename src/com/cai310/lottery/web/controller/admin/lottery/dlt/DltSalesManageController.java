package com.cai310.lottery.web.controller.admin.lottery.dlt;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.NumberSalesControllerService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.NumberSalesManageController;

@Namespace("/admin/lottery/dlt")
@Action("sales-manage")
public class DltSalesManageController extends NumberSalesManageController {
	private static final long serialVersionUID = 2222279895637315134L;
	// @Autowired
	// private DoAnalysisService dltDoAnalysisService;

	@Resource(name = "dltPrizeControllerService")
	private PrizeControllerService dltPrizeControllerService;

	@Resource(name = "dltSaleControllerService")
	private SalesControllerService dltSalesControllerService;

	@Override
	protected DoAnalysisService getDoAnalysisService() {
		// return dltDoAnalysisService;
		return null;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.DLT;
	}

	@Override
	protected PrizeControllerService getPrizeControllerService() {
		return dltPrizeControllerService;
	}

	@Override
	protected NumberSalesControllerService getSalesControllerService() {
		return (NumberSalesControllerService) dltSalesControllerService;
	}

}
