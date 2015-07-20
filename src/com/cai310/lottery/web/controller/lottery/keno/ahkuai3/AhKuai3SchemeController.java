package com.cai310.lottery.web.controller.lottery.keno.ahkuai3;

import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.ahkuai3.AhKuai3ContentBeanBuilder;
import com.cai310.lottery.support.ahkuai3.AhKuai3PlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoController;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import com.cai310.utils.Struts2Utils;

@Namespace("/ahkuai3")
@Action("scheme")
public class AhKuai3SchemeController extends KenoController<AhKuai3IssueData, AhKuai3Scheme> {

	private static final long serialVersionUID = -9196697551544874070L;

	private AhKuai3PlayType betType = AhKuai3PlayType.HeZhi;

	@Resource(name = "ahkuai3KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<AhKuai3IssueData, AhKuai3Scheme> kenoService) {
		this.kenoService = kenoService;
	}

	@Resource(name = "ahkuai3KenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<AhKuai3IssueData, AhKuai3Scheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "ahkuai3KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<AhKuai3IssueData, AhKuai3Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.AHKUAI3;
	}

	public AhKuai3IssueData getPeriod() {
		return this.period;
	}

	public AhKuai3IssueData getResultIssueData() {
		return this.resultIssueData;
	}

	public AhKuai3Scheme getScheme() {
		return this.scheme;
	}

	public void setPeriod(AhKuai3IssueData issueData) {
		this.period = issueData;
	}

	public void setScheme(AhKuai3Scheme scheme) {
		this.scheme = scheme;
	}

	/**
	 * @return the betType
	 */
	public AhKuai3PlayType getBetType() {
		return betType;
	}

	/**
	 * @param betType
	 *            the betType to set
	 */
	public void setBetType(AhKuai3PlayType betType) {
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

	@Override
	public String getType() {
		if (betType != null) {
			switch (betType) {
			case HeZhi:
			case ThreeTX:
			case ThreeDX:
			case TwoFX:
			case RandomThree:
			case RandomTwo:
			case ThreeLX:
				return "1";
			case TwoDX:
				return "2";
			}
		}
		return "1";
	}

	/**
	 * 调用构造器
	 */
	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		return AhKuai3ContentBeanBuilder.buildCompoundContentBean(this.getCreateForm().getContent(), this.getBetType());
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return null;
	}

	/**
	 * 用户投注
	 */
	public String bet() {
		try {
			User loginUser = getLoginUser();
			if (loginUser == null) {
				throw new WebDataException("您还未登录，请先登录.");
			}
			KenoSchemeDTO schemeDTO = super.buildSchemeDTO();
			schemeDTO.setAhkuai3PlayType(betType);
			AhKuai3Scheme scheme = kenoService.createScheme(schemeDTO);
			addActionMessage("投注成功！");
			if (Struts2Utils.isAjaxRequest()) {
				if (null != scheme) {
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
	public AhKuai3Scheme getKenoScheme() {
		// TODO Auto-generated method stub
		return new AhKuai3Scheme();
	}

}
