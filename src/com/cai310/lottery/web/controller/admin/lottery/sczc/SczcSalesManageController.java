package com.cai310.lottery.web.controller.admin.lottery.sczc;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.SalesManageController;

@Namespace("/admin/lottery/sczc")
@Action("sales-manage")
public class SczcSalesManageController extends SalesManageController {
	private static final long serialVersionUID = 2222279895637315134L;

	@Resource(name = "sczcSaleControllerService")
	private SalesControllerService sczcSalesControllerService;

	@Resource
	private PrizeControllerService sczcPrizeControllerService;

	@Override
	protected DoAnalysisService getDoAnalysisService() {
		return null;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.SCZC;
	}

	@Override
	protected PrizeControllerService getPrizeControllerService() {
		return sczcPrizeControllerService;
	}

	@Override
	protected SalesControllerService getSalesControllerService() {
		return sczcSalesControllerService;
	}

}
