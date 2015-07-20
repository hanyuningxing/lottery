package com.cai310.lottery.web.controller.admin.lottery.keno.el11to5;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.keno.KenoSalesManageController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/admin/lottery/keno/el11to5")
@Action("sales-manager")
public class El11to5SalesManagerController extends
		KenoSalesManageController<El11to5IssueData, El11to5Scheme> {
	private static final long serialVersionUID = -3200213063569416L;

	@Override
	public Lottery getLottery() {
		return Lottery.EL11TO5;
	}



	public El11to5IssueData getIssueData() {
		return this.issueData;
	}

	public El11to5IssueData getResultIssueData() {
		return this.issueData;
	}

	public El11to5Scheme getScheme() {
		return this.scheme;
	}



	public void setIssueData(El11to5IssueData issueData) {
		this.issueData = issueData;
	}

	public void setScheme(El11to5Scheme scheme) {
		this.scheme = scheme;
	}

	@Resource(name = "el11to5KenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<El11to5IssueData, El11to5Scheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "el11to5KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<El11to5IssueData, El11to5Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "el11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<El11to5IssueData, El11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	public String scheme() throws WebDataException {
	     criteria = new XDetachedCriteria(El11to5Scheme.class,"s");
		if (null != super.queryForm.getEl11to5PlayType()) {
				criteria.add(Restrictions.eq("s.betType", super.queryForm.getEl11to5PlayType()));
		}
		return super.scheme();
	}
}
