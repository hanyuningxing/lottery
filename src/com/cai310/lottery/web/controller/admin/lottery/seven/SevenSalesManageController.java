package com.cai310.lottery.web.controller.admin.lottery.seven;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.NumberSalesControllerService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.NumberSalesManageController;

@Namespace("/admin/lottery/seven")
@Action("sales-manage")
public class SevenSalesManageController extends NumberSalesManageController {
	private static final long serialVersionUID = 2222279895637315134L;
	@Resource(name = "sevenSaleControllerService")
	private SalesControllerService sevenSaleControllerService;

	@Resource(name = "sevenPrizeControllerService")
	private PrizeControllerService prizeControllerService;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVEN;
	}

	@Override
	protected NumberSalesControllerService getSalesControllerService() {
		return (NumberSalesControllerService)sevenSaleControllerService;
	}

	@Override
	protected DoAnalysisService getDoAnalysisService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PrizeControllerService getPrizeControllerService() {
		return prizeControllerService;
	}

}
