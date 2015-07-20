package com.cai310.lottery.web.controller.admin.lottery.pl;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.DoAnalysisService;
import com.cai310.lottery.service.lottery.NumberSalesControllerService;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.web.controller.admin.lottery.NumberSalesManageController;

@Namespace("/admin/lottery/pl")
@Action("sales-manage")
public class PlSalesManageController extends NumberSalesManageController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3941641675725998173L;
	// @Autowired
	// private DoAnalysisService ssqDoAnalysisService;
	@Resource
	private PrizeControllerService plPrizeControllerService;
	@Resource(name = "plSaleControllerService")
	private SalesControllerService plSaleControllerService;

	@Override
	protected DoAnalysisService getDoAnalysisService() {
		// return ssqDoAnalysisService;
		return null;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.PL;
	}

	@Override
	protected PrizeControllerService getPrizeControllerService() {
		return plPrizeControllerService;
	}

	@Override
	protected NumberSalesControllerService getSalesControllerService() {
		return (NumberSalesControllerService)plSaleControllerService;
	}

}
