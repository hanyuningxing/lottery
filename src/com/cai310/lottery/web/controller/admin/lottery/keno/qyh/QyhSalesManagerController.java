package com.cai310.lottery.web.controller.admin.lottery.keno.qyh;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhIssueData;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.keno.KenoSalesManageController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/admin/lottery/keno/qyh")
@Action("sales-manager")
public class QyhSalesManagerController extends
		KenoSalesManageController<QyhIssueData, QyhScheme> {
	private static final long serialVersionUID = -3200213063563109416L;

	@Override
	public Lottery getLottery() {
		return Lottery.QYH;
	}


	public QyhIssueData getIssueData() {
		return this.issueData;
	}

	public QyhIssueData getResultIssueData() {
		return this.issueData;
	}

	public QyhScheme getScheme() {
		return this.scheme;
	}

	public void setIssueData(QyhIssueData issueData) {
		this.issueData = issueData;
	}

	public void setScheme(QyhScheme scheme) {
		this.scheme = scheme;
	}

	@Resource(name = "qyhKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<QyhIssueData, QyhScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "qyhKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<QyhIssueData, QyhScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "qyhKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<QyhIssueData, QyhScheme> kenoService) {
		this.kenoService = kenoService;
	}
	
	public String scheme() throws WebDataException {
	     criteria = new XDetachedCriteria(QyhScheme.class,"s");
		if (null != super.queryForm.getQyhPlayType()) {
				criteria.add(Restrictions.eq("s.betType", super.queryForm.getQyhPlayType()));
		}
		return super.scheme();
	}

}
