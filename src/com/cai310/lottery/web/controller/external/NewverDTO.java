package com.cai310.lottery.web.controller.external;

public class NewverDTO {
	private Boolean hasNew=Boolean.TRUE;
	private Integer version=6;
	private String name="客户端5.06";
	private String url="http://qiu310.com/download/qiu310.apk";
	private String info="1. 增加了竞彩足球胜平负,让球胜平负 混合投注功能。2.修复混合投注显示的bug";
	public Boolean getHasNew() {
		return hasNew;
	}
	public void setHasNew(Boolean hasNew) {
		this.hasNew = hasNew;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}
