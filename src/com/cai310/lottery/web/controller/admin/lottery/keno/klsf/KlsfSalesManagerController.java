package com.cai310.lottery.web.controller.admin.lottery.keno.klsf;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfIssueData;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfScheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.admin.lottery.keno.KenoSalesManageController;

@Namespace("/admin/lottery/keno/klsf")
@Action("sales-manager")
public class KlsfSalesManagerController extends KenoSalesManageController<KlsfIssueData, KlsfScheme> {
	private static final long serialVersionUID = -3200213063569416L;

	@Override
	public Lottery getLottery() {
		return Lottery.KLSF;
	}

//	public KlsfChasePlan getChasePlan() {
//		return this.chasePlan;
//	}

	public KlsfIssueData getIssueData() {
		return this.issueData;
	}

	public KlsfIssueData getResultIssueData() {
		return this.issueData;
	}

	public KlsfScheme getScheme() {
		return this.scheme;
	}


	public void setIssueData(KlsfIssueData issueData) {
		this.issueData = issueData;
	}

	public void setScheme(KlsfScheme scheme) {
		this.scheme = scheme;
	}

	@Resource(name = "klsfKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<KlsfIssueData, KlsfScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "klsfKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<KlsfIssueData, KlsfScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "klsfKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlsfIssueData, KlsfScheme> kenoService) {
		this.kenoService = kenoService;
	}

}
