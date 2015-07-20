package com.cai310.lottery.web.controller.ticket.then;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.utils.JsonUtil;

public class ReqParamVisitor{
	private String playType;
	private String type;
	private String passType;
	private String mode;
	private String periodNumber;
	private String cost;
	private String multiple;
	private String units;
	private String value;
	private String orderId;
	private String createDate;
	private String matchDate;
	private String userId;
	private String userPwd;
	private String specialFlag;
	private String isChase;//是否追号
	private String periodSizeOfChase; //追号期数
	private String totalCostOfChase;
	private String wonStopOfChase;
	
	private String shareType;//分享类型
	private String subscriptionCost; //加入金额
	private String baodiCost;//保底金额
	private String commissionRate;//佣金
	private String minSubscriptionCost;//最小认购
	private String secretType;
	/** 委托方方案号 一个方案号对应一个或者多个订单号 */
	private String outOrderNumber;
	public void visit(String json){
		if(StringUtils.isNotBlank(json)){
			Map<String, Object> map = JsonUtil.getMap4Json(json);
			if(null!=map){
				String playType = null==map.get("playType")?null:String.valueOf(map.get("playType"));
				String type = null==map.get("type")?null:String.valueOf(map.get("type"));
				String passType = null==map.get("passType")?null:String.valueOf(map.get("passType"));
				String mode = null==map.get("mode")?null:String.valueOf(map.get("mode"));
				String periodNumber = null==map.get("periodNumber")?null:String.valueOf(map.get("periodNumber"));
				String cost = null==map.get("cost")?null:String.valueOf(map.get("cost"));
				String multiple = null==map.get("multiple")?null:String.valueOf(map.get("multiple"));
				String units = null==map.get("units")?null:String.valueOf(map.get("units"));
				String value = null==map.get("value")?null:String.valueOf(map.get("value"));
				String orderId = null==map.get("orderId")?null:String.valueOf(map.get("orderId"));
				String createDate = null==map.get("createDate")?null:String.valueOf(map.get("createDate"));
				String matchDate = null==map.get("matchDate")?null:String.valueOf(map.get("matchDate"));
				String userId = null==map.get("userId")?null:String.valueOf(map.get("userId"));
				String userPwd = null==map.get("userPwd")?null:String.valueOf(map.get("userPwd"));
				String specialFlag = null==map.get("specialFlag")?null:String.valueOf(map.get("specialFlag"));
				String isChase = null==map.get("isChase")?null:String.valueOf(map.get("isChase"));
				String periodSizeOfChase = null==map.get("periodSizeOfChase")?null:String.valueOf(map.get("periodSizeOfChase"));
				String totalCostOfChase = null==map.get("totalCostOfChase")?null:String.valueOf(map.get("totalCostOfChase"));
				String wonStopOfChase = null==map.get("wonStopOfChase")?null:String.valueOf(map.get("wonStopOfChase"));
				
				String shareType = null==map.get("shareType")?null:String.valueOf(map.get("shareType"));
				String subscriptionCost = null==map.get("subscriptionCost")?null:String.valueOf(map.get("subscriptionCost"));
				String baodiCost = null==map.get("baodiCost")?null:String.valueOf(map.get("baodiCost"));
				String commissionRate = null==map.get("commissionRate")?null:String.valueOf(map.get("commissionRate"));
				String minSubscriptionCost = null==map.get("minSubscriptionCost")?null:String.valueOf(map.get("minSubscriptionCost"));
				String secretType = null==map.get("secretType")?null:String.valueOf(map.get("secretType"));
				
				String outOrderNumber = null==map.get("outOrderNumber")?null:String.valueOf(map.get("outOrderNumber"));
				if(StringUtils.isNotBlank(outOrderNumber)){
					this.outOrderNumber = outOrderNumber.trim();
				}
				if(StringUtils.isNotBlank(shareType)){
					this.shareType = shareType.trim();
				}
				if(StringUtils.isNotBlank(subscriptionCost)){
					this.subscriptionCost = subscriptionCost.trim();
				}
				if(StringUtils.isNotBlank(baodiCost)){
					this.baodiCost = baodiCost.trim();
				}
				if(StringUtils.isNotBlank(commissionRate)){
					this.commissionRate = commissionRate.trim();
				}
				if(StringUtils.isNotBlank(minSubscriptionCost)){
					this.minSubscriptionCost = minSubscriptionCost.trim();
				}
				
				if(StringUtils.isNotBlank(playType)){
					this.playType = playType.trim();
				}
				if(StringUtils.isNotBlank(type)){
					this.type = type.trim();
				}
				if(StringUtils.isNotBlank(passType)){
					this.passType = passType.trim();
				}
				if(StringUtils.isNotBlank(mode)){
					this.mode = mode.trim();
				}
				if(StringUtils.isNotBlank(periodNumber)){
					this.periodNumber = periodNumber.trim();
				}
				if(StringUtils.isNotBlank(cost)){
					this.cost = cost.trim();
				}
				if(StringUtils.isNotBlank(multiple)){
					this.multiple = multiple.trim();
				}
				if(StringUtils.isNotBlank(units)){
					this.units = units.trim();
				}
				if(StringUtils.isNotBlank(value)){
					this.value = value.trim();
				}
				if(StringUtils.isNotBlank(orderId)){
					this.orderId = orderId.trim();
				}
				if(StringUtils.isNotBlank(createDate)){
					this.createDate = createDate.trim();
				}
				if(StringUtils.isNotBlank(matchDate)){
					this.matchDate = matchDate.trim();
				}
				if(StringUtils.isNotBlank(userId)){
					this.userId = userId.trim();
				}
				if(StringUtils.isNotBlank(isChase)){
					this.isChase = isChase.trim();
				}
				if(StringUtils.isNotBlank(periodSizeOfChase)){
					this.periodSizeOfChase = periodSizeOfChase.trim();
				}
				if(StringUtils.isNotBlank(totalCostOfChase)){
					this.totalCostOfChase = totalCostOfChase.trim();
				}
				if(StringUtils.isNotBlank(userPwd)){
					this.userPwd = userPwd.trim();
				}
				if(StringUtils.isNotBlank(specialFlag)){
					this.specialFlag = specialFlag.trim();
				}
				if(StringUtils.isNotBlank(wonStopOfChase)){
					this.wonStopOfChase = wonStopOfChase.trim();
				}
				if(StringUtils.isNotBlank(secretType)){
					this.secretType = secretType.trim();
				}
			}
		}
	}
	public void visit(Attribute node){
			
	}
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPassType() {
		return passType;
	}
	public void setPassType(String passType) {
		this.passType = passType;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getPeriodNumber() {
		return periodNumber;
	}
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsChase() {
		return isChase;
	}
	public void setIsChase(String isChase) {
		this.isChase = isChase;
	}
	public String getPeriodSizeOfChase() {
		return periodSizeOfChase;
	}
	public void setPeriodSizeOfChase(String periodSizeOfChase) {
		this.periodSizeOfChase = periodSizeOfChase;
	}
	public String getTotalCostOfChase() {
		return totalCostOfChase;
	}
	public void setTotalCostOfChase(String totalCostOfChase) {
		this.totalCostOfChase = totalCostOfChase;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getSpecialFlag() {
		return specialFlag;
	}
	public void setSpecialFlag(String specialFlag) {
		this.specialFlag = specialFlag;
	}
	public String getWonStopOfChase() {
		return wonStopOfChase;
	}
	public void setWonStopOfChase(String wonStopOfChase) {
		this.wonStopOfChase = wonStopOfChase;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getSubscriptionCost() {
		return subscriptionCost;
	}
	public void setSubscriptionCost(String subscriptionCost) {
		this.subscriptionCost = subscriptionCost;
	}
	public String getBaodiCost() {
		return baodiCost;
	}
	public void setBaodiCost(String baodiCost) {
		this.baodiCost = baodiCost;
	}
	public String getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}
	public String getMinSubscriptionCost() {
		return minSubscriptionCost;
	}
	public void setMinSubscriptionCost(String minSubscriptionCost) {
		this.minSubscriptionCost = minSubscriptionCost;
	}
	public String getSecretType() {
		return secretType;
	}
	public void setSecretType(String secretType) {
		this.secretType = secretType;
	}
	public String getOutOrderNumber() {
		return outOrderNumber;
	}
	public void setOutOrderNumber(String outOrderNumber) {
		this.outOrderNumber = outOrderNumber;
	}
	

}
