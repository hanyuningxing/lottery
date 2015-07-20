package com.cai310.lottery.web.controller.ticket.then;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.dto.lottery.NumberSchemeDTO;
import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.dto.lottery.SchemeInfoDTO;
import com.cai310.lottery.dto.lottery.ssq.SsqSchemeDTO;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.MyScheme;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.entity.lottery.ssq.SsqScheme;
import com.cai310.lottery.entity.lottery.ticket.TicketLogger;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.ChasePlanEntityManager;
import com.cai310.lottery.service.lottery.ChasePlanService;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.ssq.impl.SsqSchemeServiceImpl;
import com.cai310.lottery.support.CapacityChaseBean;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.RandomException;
import com.cai310.lottery.support.ssq.SsqContentBeanBuilder;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.ReflectionUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class NumberController<T extends NumberScheme,E extends NumberSchemeDTO> extends
                      TicketBaseController<T, E> {
	private static final long serialVersionUID = 5783479221989581469L;
	@Autowired
	protected ChasePlanEntityManager chasePlanEntityManager;

	@Autowired
	protected ChasePlanService chasePlanService;

	protected Integer playType;
	
	/** 是否追号*/
	protected Boolean isChase=false;
	/** 追号期数*/
	protected Integer periodSizeOfChase;
	/** 追号总金额*/
	protected Integer totalCostOfChase;
	
	protected Boolean wonStopOfChase;
	protected String schemeValue;
	
	protected Class<T> schemeClass;
	@SuppressWarnings("unchecked")
	public NumberController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
	}
	public String getSchemeValue() {
		return schemeValue;
	}

	@Override
	protected SchemeInfoDTO getSchemeMatchDTO(Scheme scheme) throws WebDataException {
		return null;
	}
	public void setSchemeValue(String schemeValue) {
		this.schemeValue = schemeValue;
	}
	public String chaseplanDetail() {
		Map map = Maps.newHashMap();
       	JsonConfig jsonConfig = new JsonConfig();  
		List<Map<String,String>> flag = Lists.newArrayList();
		try {
			check();
			String id =null;
			if(StringUtils.isNotBlank(wParam)){
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				id = null==wParam_map.get("id")?null:String.valueOf(wParam_map.get("id"));
				if(null==id){
					throw new WebDataException("1-追号ID为空.");
				}
				String userId = null==wParam_map.get("userId")?null:String.valueOf(wParam_map.get("userId"));
				String userPwd = null==wParam_map.get("userPwd")?null:String.valueOf(wParam_map.get("userPwd"));
				if (StringUtils.isNotBlank(userId)){
					User user = userManager.getUser(Long.valueOf(userId));
					if (user == null)
							throw new WebDataException("1-找不到用户ID对应的用户.");
					if(user.isLocked())throw new WebDataException("2-帐号被冻结");
					this.user = user;
					checkUser(userPwd,user);
				}else{
					throw new WebDataException("1-用户ID为空.");
				}
				
				if (StringUtils.isBlank(start)){
					throw new WebDataException("9-起始标志为空");
				}
				try{
					Integer.valueOf(start);
				} catch (Exception e) {
					throw new WebDataException("9-起始标志错误");
				}
				if (StringUtils.isBlank(count)){
					throw new WebDataException("10-条数标志为空");
				}
				try{
					Integer.valueOf(count);
				} catch (Exception e) {
					throw new WebDataException("10-条数标志错误");
				}
				ChasePlan chasePlan = this.chasePlanEntityManager.getChasePlan(Long.valueOf(id));
				if (chasePlan == null)
					throw new WebDataException("8-追号记录不存在.");
				XDetachedCriteria criteria = new XDetachedCriteria(schemeClass, "m");
				criteria.add(Restrictions.eq("m.chaseId", chasePlan.getId()));
				criteria.add(Restrictions.eq("m.sponsorId", user.getId()));
				criteria.addOrder(Order.desc("m.id"));
				List<NumberScheme> list = queryService.findByDetachedCriteria(criteria, Integer.valueOf(start), Integer.valueOf(count));
				List<MyScheme> returnList = Lists.newArrayList();
				MyScheme myScheme = null;
				for (NumberScheme numberScheme : list) {
					 myScheme =new MyScheme();
					 PropertyUtils.copyProperties(myScheme, numberScheme);
					 if(numberScheme.getLotteryType().equals(Lottery.PL)){
						 PlScheme plScheme =(PlScheme)numberScheme;
						 myScheme.setPlPlayType(plScheme.getPlayType());
					 }
					 myScheme.setSchemeId(numberScheme.getId());
					 myScheme.setSchemeState(numberScheme.getState());
					 returnList.add(myScheme);
				}
				map.put("chaseplanDetail", returnList);
			}
			jsonConfig.setExcludes(new String[] {
			"createTime"});  
			map.put("processId", "0");
			
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	@Override
	protected E buildSchemeDTO() throws WebDataException {
		if (!this.isChase)
			return super.buildSchemeDTO();
		if(null!=this.buildShareType()&&this.buildShareType().equals(ShareType.TOGETHER))
			throw new WebDataException("6-追号的方案不能为合买方案.");
		if (this.periodSizeOfChase == null)
			throw new WebDataException("6-追号期数不能为空.");
		else if (this.periodSizeOfChase < 2)
			throw new WebDataException("6-追号期数不能小于2.");
		else if (this.periodSizeOfChase > Constant.CHASE_MAX_PERIOD_SIZE)
			throw new WebDataException("6-最多只允许追" + Constant.CHASE_MAX_PERIOD_SIZE + "期.");

		//List<Integer> chaseMultiples = this.createForm.getMultiplesOfChase();
		List<Integer> chaseMultiples = null;
		if (chaseMultiples == null) {
			chaseMultiples = new ArrayList<Integer>(this.periodSizeOfChase);
			for (int i = 0; i < this.periodSizeOfChase; i++) {
				chaseMultiples.add(this.getMultiple());
			}
		} else {
			int size = chaseMultiples.size();
			for (int i = chaseMultiples.size() - 1; i >= 0; i--) {
				Integer mul = chaseMultiples.get(i);
				if (mul != null && mul > 0) {
					size = i + 1;
					break;
				}
			}
			chaseMultiples = chaseMultiples.subList(0, size);
			if (chaseMultiples.size() < 2)
				throw new WebDataException("6-追号期数不能小于2.");
		}
		while (chaseMultiples.get(chaseMultiples.size() - 1) == 0) {
			chaseMultiples.remove(chaseMultiples.size() - 1);
		}

		E schemeDTO = super.buildSchemeDTO();
		schemeDTO.setChase(true);
		schemeDTO.setMultiplesOfChase(chaseMultiples);
		schemeDTO.setRandomOfChase(false);
	    if(null!=wonStopOfChase&&wonStopOfChase){
	    	schemeDTO.setWonStopOfChase(wonStopOfChase);
	    	schemeDTO.setPrizeForWonStopOfChase(0);
	    }
		if (this.totalCostOfChase == null) {
			throw new WebDataException("6-追号总金额不能为空.");
		}
		int costPerMult = schemeDTO.getUnits() * this.unitsMoney;// 单倍方案金额
		int totalCost = 0;
		for (Integer multiple : schemeDTO.getMultiplesOfChase()) {
			if (multiple != null) {
				if (multiple < 0)
					throw new WebDataException("6-追号倍数不能小于0.");
				if (multiple > Constant.MAX_MULTIPLE)
					throw new WebDataException("6-追号倍数不能大于" + Constant.MAX_MULTIPLE + ".");

				totalCost += costPerMult * multiple;
			}
		}
		if (totalCost != this.totalCostOfChase) {
			throw new WebDataException("6-系统计算的追号总金额与提交的追号总金额不一致.");
		}
		schemeDTO.setTotalCostOfChase(totalCost);
		return schemeDTO;
	}


	@Override
	protected void buildReqParamVisitor(
			com.cai310.lottery.web.controller.ticket.then.ReqParamVisitor reqParamVisitor) {
		this.mode = Integer.valueOf(reqParamVisitor.getMode());
		this.periodNumber =reqParamVisitor.getPeriodNumber();
		this.schemeCost = Integer.valueOf(reqParamVisitor.getCost());
		this.playType = Integer.valueOf(reqParamVisitor.getPlayType());
		this.multiple = Integer.valueOf(reqParamVisitor.getMultiple());
		this.units = Integer.valueOf(reqParamVisitor.getUnits());
		this.schemeValue = String.valueOf(reqParamVisitor.getValue());
		this.specialFlag = String.valueOf(reqParamVisitor.getSpecialFlag());
		if(StringUtils.isNotBlank(reqParamVisitor.getIsChase())){
			this.isChase = Boolean.valueOf(reqParamVisitor.getIsChase());
		}
		if(StringUtils.isNotBlank(reqParamVisitor.getPeriodSizeOfChase())){
			this.periodSizeOfChase = Integer.valueOf(reqParamVisitor.getPeriodSizeOfChase());
		}
		if(StringUtils.isNotBlank(reqParamVisitor.getTotalCostOfChase())){
			this.totalCostOfChase = Integer.valueOf(reqParamVisitor.getTotalCostOfChase());
		}
		if(StringUtils.isNotBlank(reqParamVisitor.getTotalCostOfChase())){
			this.totalCostOfChase = Integer.valueOf(reqParamVisitor.getTotalCostOfChase());
		}
		if(StringUtils.isNotBlank(reqParamVisitor.getWonStopOfChase())){
			this.wonStopOfChase = Boolean.valueOf(reqParamVisitor.getWonStopOfChase());
		}
		if(null!=reqParamVisitor.getShareType()){
			this.shareType = Integer.valueOf(reqParamVisitor.getShareType());
		}
		if(null!=reqParamVisitor.getSubscriptionCost()){
			this.subscriptionCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getSubscriptionCost()));
		}
		if(null!=reqParamVisitor.getBaodiCost()){
			this.baodiCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getBaodiCost()));
		}
		if(null!=reqParamVisitor.getCommissionRate()){
			this.commissionRate = Float.valueOf(reqParamVisitor.getCommissionRate());
		}
		if(null!=reqParamVisitor.getMinSubscriptionCost()){
			this.minSubscriptionCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getMinSubscriptionCost()));
		}
		if(null!=reqParamVisitor.getSecretType()){
			this.secretType = Integer.valueOf(reqParamVisitor.getSecretType());
		}
	}


	public Boolean getIsChase() {
		return isChase;
	}


	public void setIsChase(Boolean isChase) {
		this.isChase = isChase;
	}


	public Integer getPeriodSizeOfChase() {
		return periodSizeOfChase;
	}


	public void setPeriodSizeOfChase(Integer periodSizeOfChase) {
		this.periodSizeOfChase = periodSizeOfChase;
	}


	public Integer getTotalCostOfChase() {
		return totalCostOfChase;
	}


	public void setTotalCostOfChase(Integer totalCostOfChase) {
		this.totalCostOfChase = totalCostOfChase;
	}


	public Integer getUnitsMoney() {
		return unitsMoney;
	}


	public void setUnitsMoney(Integer unitsMoney) {
		this.unitsMoney = unitsMoney;
	}


	public Boolean getWonStopOfChase() {
		return wonStopOfChase;
	}


	public void setWonStopOfChase(Boolean wonStopOfChase) {
		this.wonStopOfChase = wonStopOfChase;
	}


	public Integer getPlayType() {
		return playType;
	}


	public void setPlayType(Integer playType) {
		this.playType = playType;
	}
	@Override
	protected List buildMatchList()throws WebDataException{
		return null;
	}
	@Override
	protected List buildMatchResultList()throws WebDataException{
		return null;
	}
	
	

}
