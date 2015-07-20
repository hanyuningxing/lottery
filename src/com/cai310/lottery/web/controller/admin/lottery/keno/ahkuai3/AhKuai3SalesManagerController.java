package com.cai310.lottery.web.controller.admin.lottery.keno.ahkuai3;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.keno.KenoSalesManageController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/admin/lottery/keno/ahkuai3")
@Action("sales-manager")
public class AhKuai3SalesManagerController extends KenoSalesManageController<AhKuai3IssueData, AhKuai3Scheme> {

	private static final long serialVersionUID = 5721988978840284541L;
	
	public AhKuai3IssueData getIssueData() {
		return this.issueData;
	}

	public AhKuai3IssueData getResultIssueData() {
		return this.issueData;
	}

	public AhKuai3Scheme getScheme() {
		return this.scheme;
	}

	public void setIssueData(AhKuai3IssueData issueData) {
		this.issueData = issueData;
	}

	public void setScheme(AhKuai3Scheme scheme) {
		this.scheme = scheme;
	}
	
	@Resource(name="ahkuai3KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<AhKuai3IssueData, AhKuai3Scheme> kenoService) {
		this.kenoService=kenoService;
	}
	@Resource(name="ahkuai3KenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<AhKuai3IssueData, AhKuai3Scheme> kenoManager) {
		this.kenoManager=kenoManager;
	}
	@Resource(name="ahkuai3KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<AhKuai3IssueData, AhKuai3Scheme> kenoPlayer) {
		this.kenoPlayer=kenoPlayer;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.AHKUAI3;
	}
	public String scheme() throws WebDataException {
	     criteria = new XDetachedCriteria(AhKuai3Scheme.class,"s");
		if (null != super.queryForm.getAhKuai3PlayType()) {
				criteria.add(Restrictions.eq("s.betType", super.queryForm.getAhKuai3PlayType()));
		}
		return super.scheme();
	}
}
