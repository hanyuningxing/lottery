package com.cai310.lottery.web.controller.lottery.keno.ssc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SscConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.ssc.SscContentBeanBuilder;
import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoController;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import com.cai310.utils.Struts2Utils;

@Namespace("/ssc")
@Action("scheme")
public class SscSchemeController extends KenoController<SscIssueData, SscScheme> {
	private static final long serialVersionUID = -2252512554058283046L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SSC;
	}
	
	private SscPlayType betType = SscPlayType.DirectThree;

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

	private boolean hasDan = false;

	public boolean isHasDan() {
		return hasDan;
	}

	public void setHasDan(boolean hasDan) {
		this.hasDan = hasDan;
	}

	public SscIssueData getPeriod() {
		return this.period;
	}

	public SscIssueData getResultIssueData() {
		return this.resultIssueData;
	}

	public SscScheme getScheme() {
		return this.scheme;
	}

	public void setPeriod(SscIssueData issueData) {
		this.period = issueData;
	}

	public void setScheme(SscScheme scheme) {
		this.scheme = scheme;
	}

	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		return SscContentBeanBuilder.buildCompoundContentBean(this.getCreateForm().getContent(), this.getBetType());
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return SscContentBeanBuilder.buildSingleContentBean(this.getCreateForm().getContent(), this.getBetType());
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
			schemeDTO.setSscPlayType(betType);
			SscScheme scheme = kenoService.createScheme(schemeDTO);
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
	/**
	 * 快速投注
	 * 
	 * @return
	 */
	public String quick_create() {
		try {
			checkRepeatRequest();
			User loginUser = getLoginUser();
			if (loginUser == null) {
				throw new WebDataException("您还未登录，请先登录.");
			}
			KenoSchemeDTO schemeDTO = quick_buildSchemeDTO();
			schemeDTO.setSscPlayType(betType);
			SscScheme scheme = kenoService.createScheme(schemeDTO);
			addActionMessage("投注成功！");
			if (Struts2Utils.isAjaxRequest())
				if (null != scheme) {
					if (null != scheme.getChaseId()) {
						this.jsonMap.put(KEY_REDIRECT_URL, getChaseUrl(scheme));
					}else {
						this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(scheme));
					}
				}else {
					this.jsonMap.put(KEY_REDIRECT_URL, getChaseUrl());
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
	public SscPlayType getBetType() {
		return betType;
	}

	/**
	 * @param betType
	 *            the betType to set
	 */
	public void setBetType(SscPlayType betType) {
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

	public String getType() {
		if (betType != null) {
			switch (betType) {
			case DirectOne:
			case DirectTwo:
			case DirectThree:
			case DirectFive:
			case ThreeGroup3:
			case ThreeGroup6:
			case DirectThreeSum:
			case GroupThreeSum:
			case DirectTwoSum:
			case GroupTwoSum:
			case AllFive:
			case GroupTwo:
				return "1";
			case BigSmallDoubleSingle:
				String betStr = createForm.getContent().split(":")[1].trim();
				String[] betArr = betStr.split(SscConstant.SEPARATOR_FOR_);
				int area4 = 1;
				int area5 = 1;
				//大小双单 2 1 4 5
				List<String> area4List = Arrays.asList(betArr[0].split(SscConstant.SEPARATOR_FOR_NUMBER));
				List<String> area5List = Arrays.asList(betArr[1].split(SscConstant.SEPARATOR_FOR_NUMBER));
				if((area4List.contains("2")||area4List.contains("1"))&&(area4List.contains("4")||area4List.contains("5"))) {
					area4 =2;
				}
				if((area5List.contains("2")||area5List.contains("1"))&&(area5List.contains("4")||area5List.contains("5"))) {
					area5 =2;
				}
				return String.valueOf(area4*area5);
			}
		}
		return "1";
	}
	@Override
	public SscScheme getKenoScheme() {
		// TODO Auto-generated method stub
		return new SscScheme();
	}
}
