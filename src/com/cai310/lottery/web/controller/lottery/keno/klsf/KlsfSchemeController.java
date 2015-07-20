package com.cai310.lottery.web.controller.lottery.keno.klsf;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfIssueData;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.klsf.KlsfContentBeanBuilder;
import com.cai310.lottery.support.klsf.KlsfPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoController;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import com.cai310.utils.Struts2Utils;

@Namespace("/klsf")
@Action("scheme")
public class KlsfSchemeController extends KenoController<KlsfIssueData, KlsfScheme> {
	private static final long serialVersionUID = -2252512554058283046L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.KLSF;
	}

	private KlsfPlayType betType = KlsfPlayType.NormalOne;

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

	/**
	 * 用户投注
	 * 
	 * @return
	 */
	public String bet() {
		try {
			KenoSchemeDTO schemeDTO = super.buildSchemeDTO();
			schemeDTO.setKlsfPlayType(betType);
			KlsfScheme scheme = kenoService.createScheme(schemeDTO);
			addActionMessage("投注成功！");
			if (Struts2Utils.isAjaxRequest())
				this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(scheme));
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

	public KlsfIssueData getPeriod() {
		return this.period;
	}

	public KlsfIssueData getResultIssueData() {
		return this.resultIssueData;
	}

	public KlsfScheme getScheme() {
		return this.scheme;
	}

	public void setPeriod(KlsfIssueData issueData) {
		this.period = issueData;
	}

	public void setScheme(KlsfScheme scheme) {
		this.scheme = scheme;
	}

	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		return KlsfContentBeanBuilder.buildCompoundContentBean(this.getCreateForm().getContent(), this.getBetType());
	}
	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return KlsfContentBeanBuilder.buildSingleContentBean(this.getCreateForm().getContent(), this.getBetType());
	}

	/**
	 * @return the betType
	 */
	public KlsfPlayType getBetType() {
		return betType;
	}

	/**
	 * @param betType the betType to set
	 */
	public void setBetType(KlsfPlayType betType) {
		this.betType = betType;
	}

	@Override
	public Integer getLineLimit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getBetTypePrize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public KlsfScheme getKenoScheme() {
		// TODO Auto-generated method stub
		return new KlsfScheme();
	}
}
