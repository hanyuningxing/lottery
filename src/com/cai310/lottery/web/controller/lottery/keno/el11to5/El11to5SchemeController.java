package com.cai310.lottery.web.controller.lottery.keno.el11to5;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.el11to5.El11to5ContentBeanBuilder;
import com.cai310.lottery.support.el11to5.El11to5PlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoController;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import com.cai310.utils.Struts2Utils;

@Namespace("/el11to5")
@Action("scheme")
public class El11to5SchemeController extends KenoController<El11to5IssueData, El11to5Scheme> {
	private static final long serialVersionUID = -2252512554058283046L;

	@Override
	public Lottery getLotteryType() { 
		return Lottery.EL11TO5;
	}
	private El11to5PlayType betType = El11to5PlayType.RandomFive;
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


	public El11to5IssueData getPeriod() {
		return this.period;
	}

	public El11to5IssueData getResultIssueData() {
		return this.resultIssueData;
	}

	public El11to5Scheme getScheme() {
		return this.scheme;
	}

	public void setPeriod(El11to5IssueData issueData) {
		this.period = issueData;
	}

	public void setScheme(El11to5Scheme scheme) {
		this.scheme = scheme;
	}
	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		return El11to5ContentBeanBuilder.buildCompoundContentBean(this.getCreateForm().getContent(), this.getBetType());
	}
	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return El11to5ContentBeanBuilder.buildSingleContentBean(this.getCreateForm().getContent(), this.getBetType());
	}
	
	/**
	 * 用户投注
	 * 
	 * @return
	 */
	public String bet() {
		try {
			User loginUser = getLoginUser();
			if (loginUser == null) {
				throw new WebDataException("您还未登录，请先登录.");
			}
			KenoSchemeDTO schemeDTO = super.buildSchemeDTO();
			schemeDTO.setEl11to5PlayType(betType);
			El11to5Scheme scheme = kenoService.createScheme(schemeDTO);
			addActionMessage("投注成功！");
			if (Struts2Utils.isAjaxRequest()) {
				 if(null!=scheme) {
					this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(scheme));
				} else {
					 this.jsonMap.put(KEY_REDIRECT_URL, getChaseUrl());
				 }
			}
			return this.success();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return this.error();
	}
	
	/**
	 * @return the betType
	 */
	public El11to5PlayType getBetType() {
		return betType;
	}

	/**
	 * @param betType the betType to set
	 */
	public void setBetType(El11to5PlayType betType) {
		this.betType = betType;
	}

	@Override
	public Integer getLineLimit() {
		return this.getBetType().getLineLimit();
	}
	@Override
	public Integer getBetTypePrize() {
		return this.getBetType().getPrize();
	}
	
	public String getType(){
		if(betType!=null){
			switch(betType){
			case NormalOne:
			case RandomTwo:
			case RandomThree:
			case RandomFour:
			case RandomFive:
			case RandomSix:
			case RandomSeven:
			case RandomEight:
				return "1";
			case ForeTwoGroup:
			case ForeTwoDirect:
			case ForeThreeGroup:
			case ForeThreeDirect:
				return "2";
			}
		}
		return "1";
	}
	@Override
	public El11to5Scheme getKenoScheme() {
		// TODO Auto-generated method stub
		return new El11to5Scheme();
	}
}
