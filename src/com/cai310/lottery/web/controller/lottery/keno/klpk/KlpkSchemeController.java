package com.cai310.lottery.web.controller.lottery.keno.klpk;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkIssueData;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.klpk.KlpkContentBeanBuilder;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoController;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import com.cai310.utils.Struts2Utils;

@Namespace("/klpk")
@Action("scheme")
public class KlpkSchemeController extends KenoController<KlpkIssueData, KlpkScheme>{

	private static final long serialVersionUID = 7996791849645656211L;
	
	private KlpkPlayType betType=KlpkPlayType.RandomOne;
	
	@Resource(name="klpkKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlpkIssueData, KlpkScheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Resource(name="klpkKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<KlpkIssueData, KlpkScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}
	@Resource(name="klpkKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<KlpkIssueData, KlpkScheme> kenoPlayer) {
		this.kenoPlayer=kenoPlayer;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.KLPK;
	}
	
	public KlpkIssueData getPeriod() {
		return this.period;
	}
	
	public KlpkIssueData getResultIssueData() {
		return this.resultIssueData;
	}
	
	public KlpkScheme getScheme() {
		return this.scheme;
	}
	
	public void setPeriod(KlpkIssueData issueData) {
		this.period = issueData;
	}

	public void setScheme(KlpkScheme scheme) {
		this.scheme = scheme;
	}
	
	@Override
	public Integer getLineLimit() {
		return this.getBetType().getLineLimit();
	}

	@Override
	public Integer getBetTypePrize() {
		return this.getBetType().getPrize();
	}

	@Override
	public String getType() {
		return "1";
	}

	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		return KlpkContentBeanBuilder.buildCompoundContentBean(this.getCreateForm().getContent(), this.getBetType());
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return KlpkContentBeanBuilder.buildSingleContentBean(this.getCreateForm().getContent(), this.getBetType());
	}

	public KlpkPlayType getBetType() {
		return betType;
	}

	public void setBetType(KlpkPlayType betType) {
		this.betType = betType;
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
			schemeDTO.setKlpkPlayType(betType);
			KlpkScheme scheme = kenoService.createScheme(schemeDTO);
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
	@Override
	public KlpkScheme getKenoScheme() {
		// TODO Auto-generated method stub
		return new KlpkScheme();
	}
}
