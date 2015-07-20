package com.cai310.lottery.web.controller.admin.lottery.welfare36to7;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.NumberSalesControllerService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.NumberSalesManageController;

@Namespace("/admin/lottery/welfare36to7")
@Action("sales-manage")
public class Welfare36to7SalesManageController extends NumberSalesManageController {
	private static final long serialVersionUID = 2222279895637315134L;
	@Resource(name = "welfare36to7SaleControllerService")
	private SalesControllerService welfare36to7SaleControllerService;

	@Resource(name = "welfare36to7PrizeControllerServiceImpl")
	private PrizeControllerService prizeControllerService;

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}

	@Override
	protected NumberSalesControllerService getSalesControllerService() {
		return (NumberSalesControllerService)welfare36to7SaleControllerService;
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
