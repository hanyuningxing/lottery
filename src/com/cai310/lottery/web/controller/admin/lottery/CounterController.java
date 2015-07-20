package com.cai310.lottery.web.controller.admin.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.support.CounterComparale;
import com.cai310.lottery.support.OneDayCounter;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.utils.DateUtil;
import com.cai310.utils.ReflectionUtils;

@Namespace("/admin/counter")
@Result(name="success",location="/WEB-INF/content/admin/lottery/counter.ftl")
public abstract class CounterController<S extends Scheme> extends AdminBaseController{
	private static final long serialVersionUID = 3749734818068391707L;

	@Autowired
	private QueryService queryService;
	
	protected List<OneDayCounter> counterList;
	protected Class<S> schemeClass;
	protected Lottery lotteryType;
	protected Date beginTime;
	protected Date endTime;
	protected String periodNumber;
	
	
	/**
	 * 销售报表
	 * @return
	 */
	public String index(){
		return SUCCESS;
	}

	/**
	 * 统计查询
	 * @return
	 */
	@SuppressWarnings("unchecked")
	abstract public DetachedCriteria createCountCriteria();

	
	public String count(){
		if(StringUtils.isBlank(periodNumber)){
			addActionError("期号不能为空！");
			return SUCCESS;
		}
    	schemeClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);

		DetachedCriteria criteria = createCountCriteria();
		
		List<S> schemeList = queryService.findByDetachedCriteria(criteria);
		if(schemeList == null || schemeList.isEmpty()){
			addActionError("没有需要统计的数据！");
			return SUCCESS;
		}
		OneDayCounter counter;
		OneDayCounter counterAll = new OneDayCounter();
		Map<String,OneDayCounter> counterMap = new HashMap<String,OneDayCounter>();
		String strDate = "";
		for(S scheme : schemeList){
			strDate = DateUtil.getFormatDate("yyyy-MM-dd", scheme.getCreateTime());
			counter = counterMap.get(strDate);
			if(counter == null){
				counter = new OneDayCounter();
				counter.setDay(DateUtil.strToDate(strDate, "yyyy-MM-dd"));
			}
			counter.setTotal(counter.getTotal() + 1);
			counterAll.setTotal(counterAll.getTotal() + 1);
			if(scheme.getState() == SchemeState.SUCCESS){
				if(scheme.getSchemeCost() != null){
					counter.setTotalSales(counter.getTotalSales().add(BigDecimalUtil.valueOf(scheme.getSchemeCost())));
				}
				if(scheme.getSchemeCost() != null){
					counterAll.setTotalSales(counterAll.getTotalSales().add(BigDecimalUtil.valueOf(scheme.getSchemeCost())));
				}
				counter.setTotalSuccess(counter.getTotalSuccess() + 1);
				counterAll.setTotalSuccess(counterAll.getTotalSuccess() + 1);
				if(scheme.isWon()){
					counter.setTotalWon(counter.getTotalWon() + 1);
					counterAll.setTotalWon(counterAll.getTotalWon() + 1);
					
					if(scheme.getPrize() != null){
						counter.setTotalPrice(counter.getTotalPrice().add(scheme.getPrize()));
					}
					if(scheme.getPrize() != null){
						counterAll.setTotalPrice(counterAll.getTotalPrice().add(scheme.getPrize()));
					}
					
					if(scheme.getPrizeAfterTax() != null){
						counter.setPrizeAfterTax(counter.getPrizeAfterTax().add(scheme.getPrizeAfterTax()));
					}
					
					if(scheme.getPrizeAfterTax() != null){
						counterAll.setPrizeAfterTax(counterAll.getPrizeAfterTax().add(scheme.getPrizeAfterTax()));
					}
				}
			}
			counterMap.put(strDate, counter);
		}
		counterList = new ArrayList<OneDayCounter>(counterMap.values());
		Collections.sort(counterList, new CounterComparale());
		if(counterList != null){
			counterList.add(counterAll);
		}
		return SUCCESS;
	}
	
	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}

	public abstract Lottery getLotteryType();

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<OneDayCounter> getCounterList() {
		return counterList;
	}

	public void setCounterList(List<OneDayCounter> counterList) {
		this.counterList = counterList;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

}
