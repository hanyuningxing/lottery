package com.cai310.lottery.web.controller.lottery.keno.qyh;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhIssueData;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.qyh.QyhContentBeanBuilder;
import com.cai310.lottery.support.qyh.QyhPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoController;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import com.cai310.utils.Struts2Utils;

@Namespace("/qyh")
@Action("scheme")
public class QyhSchemeController extends KenoController<QyhIssueData, QyhScheme> {
	private static final long serialVersionUID = -2252512554058283046L;

	@Override
	public Lottery getLotteryType() { 
		return Lottery.QYH;
	}
	private QyhPlayType betType = QyhPlayType.RandomFive;
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

	
	public QyhIssueData getPeriod() {
		return this.period;
	}

	public QyhIssueData getResultIssueData() {
		return this.resultIssueData;
	}

	public QyhScheme getScheme() {
		return this.scheme;
	}

	public void setPeriod(QyhIssueData issueData) {
		this.period = issueData;
	}

	public void setScheme(QyhScheme scheme) {
		this.scheme = scheme;
	}
	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		return QyhContentBeanBuilder.buildCompoundContentBean(this.getCreateForm().getContent(), this.getBetType());
	}
	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return QyhContentBeanBuilder.buildSingleContentBean(this.getCreateForm().getContent(), this.getBetType());
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
			schemeDTO.setQyhPlayType(betType);
			QyhScheme scheme = kenoService.createScheme(schemeDTO);
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
	public QyhPlayType getBetType() {
		return betType;
	}

	/**
	 * @param betType the betType to set
	 */
	public void setBetType(QyhPlayType betType) {
		this.betType = betType;
	}
    
	public String getType(){
		if(betType!=null){
			switch(betType){
			case RandomOne:
			case RandomTwo:
			case RandomThree:
			case RandomFour:
			case RandomFive:
			case RandomSix:
			case RandomSeven:
			case RandomEight:
			case RandomNine:
			case RandomTen:
				return "1";
			case RoundFour:
			case RoundOne:
			case RoundThree:
			case RoundTwo:
				return "2";
			case DirectOne:
			case DirectTwo:
			case DirectThree:
				return "3";
			}
		}
		return "1";
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
	public QyhScheme getKenoScheme() {
		// TODO Auto-generated method stub
		return new QyhScheme();
	}
}
