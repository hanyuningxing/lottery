package com.cai310.lottery.web.controller.external;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.utils.JsonUtil;

public class ReqParamVisitor{
	private String userName;
	private String password;
	private String amount;//支付金额
	private String userWay;
	private String payWay;
	private String orderId;
	private String userId;
	private String userPwd;
	private String start;
	private String count;
	private String wLotteryId;
	private String state;
	private String type;
	public void visit(String json){
		if(StringUtils.isNotBlank(json)){
			Map<String, Object> map = JsonUtil.getMap4Json(json);
			if(null!=map){
				String userName = null==map.get("userName")?null:String.valueOf(map.get("userName"));
				String password = null==map.get("userPwd")?null:String.valueOf(map.get("userPwd"));
				String amount = null==map.get("amount")?null:String.valueOf(map.get("amount"));
				String userWay = null==map.get("userWay")?null:String.valueOf(map.get("userWay"));
				String payWay = null==map.get("payWay")?null:String.valueOf(map.get("payWay"));
				String orderId = null==map.get("orderId")?null:String.valueOf(map.get("orderId"));
				String userId = null==map.get("userId")?null:String.valueOf(map.get("userId"));
				String userPwd = null==map.get("userPwd")?null:String.valueOf(map.get("userPwd"));
				String start = null==map.get("start")?null:String.valueOf(map.get("start"));
				String count = null==map.get("count")?null:String.valueOf(map.get("count"));
				String wLotteryId = null==map.get("wLotteryId")?null:String.valueOf(map.get("wLotteryId"));
				String state = null==map.get("state")?null:String.valueOf(map.get("state"));
				String type = null==map.get("type")?null:String.valueOf(map.get("type"));
				if(StringUtils.isNotBlank(userName)){
					this.userName = userName.trim();
				}
				if(StringUtils.isNotBlank(password)){
					this.password = password.trim();
				}
				if(StringUtils.isNotBlank(amount)){
					this.amount = amount.trim();
				}
				if(StringUtils.isNotBlank(userWay)){
					this.userWay = userWay.trim();
				}
				if(StringUtils.isNotBlank(payWay)){
					this.payWay = payWay.trim();
				}
				if(StringUtils.isNotBlank(orderId)){
					this.orderId = orderId.trim();
				}
				if(StringUtils.isNotBlank(userId)){
					this.userId = userId.trim();
				}
				if(StringUtils.isNotBlank(userPwd)){
					this.userPwd = userPwd.trim();
				}
				if(StringUtils.isNotBlank(start)){
					this.start = start.trim();
				}
				if(StringUtils.isNotBlank(count)){
					this.count = count.trim();
				}
				if(StringUtils.isNotBlank(wLotteryId)){
					this.wLotteryId = wLotteryId.trim();
				}
				if(StringUtils.isNotBlank(state)){
					this.state = state.trim();
				}
				if(StringUtils.isNotBlank(type)){
					this.type = type.trim();
				}
				
			}
		}
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUserWay() {
		return userWay;
	}
	public void setUserWay(String userWay) {
		this.userWay = userWay;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getwLotteryId() {
		return wLotteryId;
	}
	public void setwLotteryId(String wLotteryId) {
		this.wLotteryId = wLotteryId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	

}
