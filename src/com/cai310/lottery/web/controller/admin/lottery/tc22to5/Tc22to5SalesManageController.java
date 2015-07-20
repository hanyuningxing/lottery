package com.cai310.lottery.web.controller.admin.lottery.tc22to5;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.NumberSalesControllerService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.NumberSalesManageController;

@Namespace("/admin/lottery/tc22to5")
@Action("sales-manage")
public class Tc22to5SalesManageController extends NumberSalesManageController {
	private static final long serialVersionUID = 2222279895637315134L;
	// @Autowired
	// private DoAnalysisService tc22to5DoAnalysisService;

	@Resource
	private PrizeControllerService tc22to5PrizeControllerService;

	@Resource(name = "tc22to5SaleControllerService")
	private SalesControllerService tc22to5SalesControllerService;

	@Override
	protected DoAnalysisService getDoAnalysisService() {
		// return tc22to5DoAnalysisService;
		return null;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.TC22TO5;
	}

	@Override
	protected PrizeControllerService getPrizeControllerService() {
		return tc22to5PrizeControllerService;
	}

	@Override
	protected NumberSalesControllerService getSalesControllerService() {
		return (NumberSalesControllerService) tc22to5SalesControllerService;
	}

}
