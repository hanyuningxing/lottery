package com.cai310.lottery.web.controller.admin.lottery.welfare3d;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.NumberSalesControllerService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.NumberSalesManageController;

@Namespace("/admin/lottery/welfare3d")
@Action("sales-manage")
public class Welfare3dSalesManageController extends NumberSalesManageController {
	private static final long serialVersionUID = 5654083015883394256L;
	
	@Resource
	private PrizeControllerService welfare3dPrizeControllerService;
	@Resource(name = "welfare3dSaleControllerService")
	private SalesControllerService welfare3dSaleControllerService;

	@Override
	protected DoAnalysisService getDoAnalysisService() {
		// TODO: 返回DoAnalysisService实例
		throw new RuntimeException("未注入DoAnalysisService实例.");
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE3D;
	}

	@Override
	protected PrizeControllerService getPrizeControllerService() {
		return welfare3dPrizeControllerService;
	}

	@Override
	protected NumberSalesControllerService getSalesControllerService() {
		return (NumberSalesControllerService)welfare3dSaleControllerService;
	}

}
