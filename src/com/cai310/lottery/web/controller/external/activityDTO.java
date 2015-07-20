package com.cai310.lottery.web.controller.external;

public class activityDTO {
	private String picUrl;
	private String wapUrl;
	private String title;
	private String startDate;
	private String endDate;
	private String clientUrl;
	public activityDTO(){
		
	}
	public activityDTO(String picUrl,String wapUrl,String title,String startDate,String endDate,String clientUrl){
		this.picUrl=picUrl;
		this.wapUrl=wapUrl;
		this.title=title;
		this.startDate=startDate;
		this.endDate=endDate;
		this.clientUrl=clientUrl;
	}
	
	
	public String getClientUrl() {
		return clientUrl;
	}
	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getWapUrl() {
		return wapUrl;
	}
	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
