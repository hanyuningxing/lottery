package com.cai310.lottery.web.controller.admin.lottery.keno.ssc;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.keno.KenoSalesManageController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/admin/lottery/keno/ssc")
@Action("sales-manager")
public class SscSalesManagerController extends KenoSalesManageController<SscIssueData, SscScheme> {
	private static final long serialVersionUID = -3200213063563109416L;

	@Override
	public Lottery getLottery() {
		return Lottery.SSC;
	}

	public SscIssueData getIssueData() {
		return this.issueData;
	}

	public SscIssueData getResultIssueData() {
		return this.issueData;
	}

	public SscScheme getScheme() {
		return this.scheme;
	}

	public void setIssueData(SscIssueData issueData) {
		this.issueData = issueData;
	}

	public void setScheme(SscScheme scheme) {
		this.scheme = scheme;
	}

	@Resource(name = "sscKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<SscIssueData, SscScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "sscKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<SscIssueData, SscScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "sscKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<SscIssueData, SscScheme> kenoService) {
		this.kenoService = kenoService;
	}

	public String scheme() throws WebDataException {
	     criteria = new XDetachedCriteria(SscScheme.class,"s");
		if (null != super.queryForm.getSscPlayType()) {
				criteria.add(Restrictions.eq("s.betType", super.queryForm.getSscPlayType()));
		}
		return super.scheme();
	}

}
