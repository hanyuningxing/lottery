package com.cai310.lottery.web.controller.admin.lottery.keno.klpk;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkIssueData;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.keno.KenoSalesManageController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/admin/lottery/keno/klpk")
@Action("sales-manager")
public class KlpkSalesManagerController extends
		KenoSalesManageController<KlpkIssueData, KlpkScheme> {
	private static final long serialVersionUID = -3200213063563109416L;

	@Override
	public Lottery getLottery() {
		return Lottery.KLPK;
	}


	public KlpkIssueData getIssueData() {
		return this.issueData;
	}

	public KlpkIssueData getResultIssueData() {
		return this.issueData;
	}

	public KlpkScheme getScheme() {
		return this.scheme;
	}


	public void setIssueData(KlpkIssueData issueData) {
		this.issueData = issueData;
	}

	public void setScheme(KlpkScheme scheme) {
		this.scheme = scheme;
	}

	@Resource(name = "klpkKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<KlpkIssueData, KlpkScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "klpkKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<KlpkIssueData, KlpkScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "klpkKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlpkIssueData, KlpkScheme> kenoService) {
		this.kenoService = kenoService;
	}

	public String scheme() throws WebDataException {
	     criteria = new XDetachedCriteria(KlpkScheme.class,"s");
		if (null != super.queryForm.getKlpkPlayType()) {
				criteria.add(Restrictions.eq("s.betType", super.queryForm.getKlpkPlayType()));
		}
		return super.scheme();
	}
}
