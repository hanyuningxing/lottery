package com.cai310.lottery.web.controller.admin.fund;

import java.math.BigDecimal;
import java.util.Date;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.common.UserWay;

public class CountFundForm {

	/** 用户编号 */
	private Long userId;

	/** 用户名 */
	private String userName;

	/** 用户类型 */
	private UserWay userWay;
	/** 消费金额 */
	private BigDecimal useCost=BigDecimal.valueOf(0);
	/** 充值金额 */
	private BigDecimal payCost=BigDecimal.valueOf(0);
	/** 提款金额 */
	private BigDecimal drawCost=BigDecimal.valueOf(0);
	/** 中奖金额（包括方案佣金） */
	private BigDecimal bonusCost=BigDecimal.valueOf(0);
	/** 活动金额 */
	private BigDecimal actCost=BigDecimal.valueOf(0);
	/** 佣金金额 */
	private BigDecimal rebateCost=BigDecimal.valueOf(0);
	/** 后台加金额 */
	private BigDecimal adminInCost=BigDecimal.valueOf(0);
	/** 后台减金额 */
	private BigDecimal adminOutCost=BigDecimal.valueOf(0);
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public UserWay getUserWay() {
		return userWay;
	}
	public void setUserWay(UserWay userWay) {
		this.userWay = userWay;
	}
	public BigDecimal getUseCost() {
		return useCost;
	}
	public void setUseCost(BigDecimal useCost) {
		this.useCost = useCost;
	}
	public BigDecimal getPayCost() {
		return payCost;
	}
	public void setPayCost(BigDecimal payCost) {
		this.payCost = payCost;
	}
	public BigDecimal getDrawCost() {
		return drawCost;
	}
	public void setDrawCost(BigDecimal drawCost) {
		this.drawCost = drawCost;
	}
	public BigDecimal getBonusCost() {
		return bonusCost;
	}
	public void setBonusCost(BigDecimal bonusCost) {
		this.bonusCost = bonusCost;
	}
	public BigDecimal getActCost() {
		return actCost;
	}
	public void setActCost(BigDecimal actCost) {
		this.actCost = actCost;
	}
	public BigDecimal getAdminInCost() {
		return adminInCost;
	}
	public void setAdminInCost(BigDecimal adminInCost) {
		this.adminInCost = adminInCost;
	}
	public BigDecimal getAdminOutCost() {
		return adminOutCost;
	}
	public void setAdminOutCost(BigDecimal adminOutCost) {
		this.adminOutCost = adminOutCost;
	}
	public BigDecimal getRebateCost() {
		return rebateCost;
	}
	public void setRebateCost(BigDecimal rebateCost) {
		this.rebateCost = rebateCost;
	}
	
}
