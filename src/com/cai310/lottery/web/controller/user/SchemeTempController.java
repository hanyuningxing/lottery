package com.cai310.lottery.web.controller.user;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.CommonSchemeTemp;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.Struts2Utils;

public class SchemeTempController extends UserBaseController {
	
	private static final long serialVersionUID = -2171931771381143316L;


	
	private Pagination pagination = new Pagination(20);
	
	private Lottery lotteryType;
	
	
	@Autowired
	private QueryService queryService;
	
	public String list() {
		User loginUser = getLoginUser();
		if (loginUser == null)
			return GlobalResults.FWD_LOGIN;
		return "list";
	}
	
	public String listTable(){
		User loginUser = getLoginUser();
		if (loginUser == null)
			return GlobalResults.FWD_LOGIN;
		
		XDetachedCriteria criteria = new XDetachedCriteria(CommonSchemeTemp.class, "m");
		if(this.lotteryType != null) {
			criteria.add(Restrictions.eq("m.lotteryType", lotteryType));
		}
		criteria.add(Restrictions.eq("m.sponsorId", loginUser.getId()));
		String startTimeStr = Struts2Utils.getParameter("startTime");
		String endTimeStr = Struts2Utils.getParameter("endTime");
		
		if(StringUtils.isNotBlank(startTimeStr)) {
			Date startTime = DateUtil.strToDate(startTimeStr);
			criteria.add(Restrictions.gt("m.createTime", startTime));
		}
		if(StringUtils.isNotBlank(endTimeStr)) {
			Date endTime = DateUtil.strToDate(endTimeStr);
			criteria.add(Restrictions.lt("m.createTime", endTime));
		}
		criteria.addOrder(Order.desc("createTime"));
		this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);
		
		return "list_table";
	}
	

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
	
	
}


