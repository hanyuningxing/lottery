package com.cai310.lottery.web.controller.admin.lottery.lczc;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.SalesManageController;

@Namespace("/admin/lottery/lczc")
@Action("sales-manage")
public class LczcSalesManageController extends SalesManageController {
	private static final long serialVersionUID = 2222279895637315134L;

	@Resource(name = "lczcSaleControllerService")
	private SalesControllerService lczcSalesControllerService;

	@Resource
	private PrizeControllerService lczcPrizeControllerService;

	@Override
	protected DoAnalysisService getDoAnalysisService() {
		return null;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.LCZC;
	}

	@Override
	protected PrizeControllerService getPrizeControllerService() {
		return lczcPrizeControllerService;
	}

	@Override
	protected SalesControllerService getSalesControllerService() {
		return lczcSalesControllerService;
	}

}
