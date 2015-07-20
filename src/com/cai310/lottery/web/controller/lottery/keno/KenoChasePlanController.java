package com.cai310.lottery.web.controller.lottery.keno;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SdEl11to5Constant;
import com.cai310.lottery.common.ChaseDetailState;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.SecurityUserHolder;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.dto.lottery.ChasePlanDetailDTO;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;
import com.cai310.lottery.web.controller.lottery.LotteryBaseController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.Struts2Utils;
import com.ibm.icu.util.Calendar;

/**
 * 高频彩追号基类
 */
public abstract class KenoChasePlanController<I extends KenoPeriod, S extends NumberScheme> extends ChasePlanController {
	private static final long serialVersionUID = -7123991959872456815L;
	protected KenoService<I, S> kenoService;
	public abstract void setKenoService(KenoService<I, S> kenoService);
	protected I period;

	public String show() {
		try {
			if (this.id == null)
				throw new WebDataException("追号ID不能为空.");

			User loggedUser = getLoginUser();
			if (loggedUser == null)
				throw new WebDataException("您还没有登录。请先登录.");
			chasePlan = this.chasePlanEntityManager.getChasePlan(this.getId());
			if (this.chasePlan == null)
				throw new WebDataException("追号记录不存在.");
			super.setChasePlanDetailList(chasePlanDetailList(chasePlan));
			
			XDetachedCriteria criteria = new XDetachedCriteria(kenoService.getSchemeClass(), "m");
			criteria.add(Restrictions.eq("m.chaseId", this.getId()));
			criteria.add(Restrictions.eq("m.sponsorId", loggedUser.getId()));
			criteria.addOrder(Order.desc("m.id"));
			this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);

			return "fwd_chase_list";
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return null;
	}

	public List<ChasePlanDetailDTO> chasePlanDetailList(ChasePlan chasePlan) {
		List<ChasePlanDetailDTO> chasePlanDetailList = new ArrayList<ChasePlanDetailDTO>();
		// Pattern pattern = Pattern.compile("\\[(.*?)\\]");

		String[] multArr = chasePlan.getMultiples().split(ChasePlan.MULTIPLE_SEPARATOR);
		period = kenoService.findIssueDataById(chasePlan.getCurPeriodId());

		Long curPeriodNum = Long.valueOf(period.getPeriodNumber().replace("-", ""));

		for (String multipleStr : multArr) {
			if (!chasePlan.isChased(multipleStr)) {
				curPeriodNum++;
				Integer multiple = 0;
				ChasePlanDetailDTO chasePlanDetailDTO = new ChasePlanDetailDTO();
				if (chasePlan.isNotChase(multipleStr)) {
					chasePlanDetailDTO.setState(ChaseDetailState.SKIP);
				} else {
					multiple = Integer.valueOf(multipleStr);
					chasePlanDetailDTO.setState(ChaseDetailState.RUNNING);
				}
				chasePlanDetailDTO.setCurPeriodNum(curPeriodNum);
				chasePlanDetailDTO.setMultiple(multiple);
				chasePlanDetailDTO.setCost(multiple * chasePlan.getSchemeCost());
				chasePlanDetailList.add(chasePlanDetailDTO);
				chasePlanDetailDTO.setKenoCost(BigDecimal.valueOf(multiple * chasePlan.getSchemeCost()));
			}
		}
		return chasePlanDetailList;
	}
}
