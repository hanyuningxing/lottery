package com.cai310.lottery.web.controller.admin.lottery.keno.sdel11to5;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.keno.KenoSalesManageController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/admin/lottery/keno/sdel11to5")
@Action("sales-manager")
public class SdEl11to5SalesManagerController extends
		KenoSalesManageController<SdEl11to5IssueData, SdEl11to5Scheme> {
	private static final long serialVersionUID = -3200213063563109416L;

	@Override
	public Lottery getLottery() {
		return Lottery.SDEL11TO5;
	}


	public SdEl11to5IssueData getIssueData() {
		return this.issueData;
	}

	public SdEl11to5IssueData getResultIssueData() {
		return this.issueData;
	}

	public SdEl11to5Scheme getScheme() {
		return this.scheme;
	}


	public void setIssueData(SdEl11to5IssueData issueData) {
		this.issueData = issueData;
	}

	public void setScheme(SdEl11to5Scheme scheme) {
		this.scheme = scheme;
	}

	@Resource(name = "sdel11to5KenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<SdEl11to5IssueData, SdEl11to5Scheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "sdel11to5KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<SdEl11to5IssueData, SdEl11to5Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "sdel11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<SdEl11to5IssueData, SdEl11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}

	public String scheme() throws WebDataException {
	     criteria = new XDetachedCriteria(SdEl11to5Scheme.class,"s");
		if (null != super.queryForm.getSdEl11to5PlayType()) {
				criteria.add(Restrictions.eq("s.betType", super.queryForm.getSdEl11to5PlayType()));
		}
		return super.scheme();
	}
}
