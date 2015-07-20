package com.cai310.lottery.web.controller.lottery.jczq;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.entity.lottery.jczq.JczqChasePlan;
import com.cai310.lottery.entity.lottery.jczq.JczqChasePlanDetail;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.jczq.JczqChasePlanDetailEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqChasePlanEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeEntityManager;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.LotteryBaseController;
import com.cai310.orm.Pagination;
import com.cai310.utils.CookieUtil;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + JczqConstant.KEY)
@Action(value = "chasePlanDetail")
public class JczqChasePlanDetailController <T extends JczqScheme> extends LotteryBaseController{
	private static final long serialVersionUID = 4811717959025368240L;

	private Pagination pagination = new Pagination(20);
	
	@Autowired
	private JczqChasePlanDetailEntityManager chasePlanDetailEntityManager;
	
	@Autowired
	private JczqChasePlanEntityManager jczqChasePlanEntityManagerImpl;
	
	@Autowired
	private JczqSchemeEntityManager schemeEntityManager;
	
	
	
	/**
	 * 追号方案详情
	 */
	public String list() {
		try {
			User loginUser = getLoginUser();
			if (null == loginUser) {
				CookieUtil.addReUrlCookie();
				return GlobalResults.FWD_LOGIN;
			}
			
			Long jczqChasePlanId = Long.valueOf(Struts2Utils.getParameter("jczqChasePlanId"));									
			JczqChasePlan plan = jczqChasePlanEntityManagerImpl.getChasePlan(jczqChasePlanId);
			
			if(plan == null)
				throw new  WebDataException("追投方案不存在");
			
			if(plan.getUserId().compareTo(loginUser.getId()) != Integer.valueOf(0))
				throw new  WebDataException("该追投方案属于别的用户");
			
			Integer returnRateLevel = plan.getReturnRateLevel();
			Integer mutiple = plan.getMutiple();
			
			Struts2Utils.setAttribute("returnRateLevel", returnRateLevel);
			Struts2Utils.setAttribute("mutiple", mutiple);
			
			this.pagination = chasePlanDetailEntityManager.getJczqChasePlanDetailBy(jczqChasePlanId, pagination);
			
			@SuppressWarnings("unchecked")
			List<JczqChasePlanDetail> list = pagination.getResult();
			
			boolean isAllWinningStatusUpdate = true;
			
			for(int i=0; i<list.size(); i++) {
				JczqChasePlanDetail detail = list.get(i);
				Long schemeId = detail.getSchemeId();
				if(schemeId != null) {
					JczqScheme scheme = schemeEntityManager.getScheme(schemeId);
					if(scheme != null) {
						if(scheme.getWinningUpdateStatus() == WinningUpdateStatus.NONE)
							isAllWinningStatusUpdate = false;
						detail.setPrizeAfterTax(scheme.getPrizeAfterTax());
						detail.setSchemePrintState(scheme.getSchemePrintState());
						detail.setSchemeCreateTime(scheme.getCreateTime());
					}

				}
				list.set(i, detail);
			}
			Struts2Utils.setAttribute("isAllWinningStatusUpdate", isAllWinningStatusUpdate);
			this.pagination.setResult(list);
			
			return "list";
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
