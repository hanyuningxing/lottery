package com.cai310.lottery.fetch;

import java.util.ArrayList;

import com.cai310.lottery.fetch.Item;

public class DataModel {
	
	private String period;
	private Integer orderNo;
	private String playType;
	private String groupType;
	//双色球 大乐透  红蓝区  
	private ArrayList<Item> blueAreas = new ArrayList();
	private ArrayList<Item> redAreas = new ArrayList();
	//数字排列  福彩3D
	private ArrayList<Item> items = new ArrayList();
	private ArrayList<Item> groupItems = new ArrayList();
	private ArrayList<Item> baiItems = new ArrayList();
	private ArrayList<Item> shiItems = new ArrayList();
	private ArrayList<Item> geItems = new ArrayList();

	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public ArrayList<Item> getBlueAreas() {
		return blueAreas;
	}
	public void setBlueAreas(ArrayList<Item> blueAreas) {
		this.blueAreas = blueAreas;
	}
	public ArrayList<Item> getRedAreas() {
		return redAreas;
	}
	public void setRedAreas(ArrayList<Item> redAreas) {
		this.redAreas = redAreas;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	public ArrayList<Item> getGroupItems() {
		return groupItems;
	}
	public void setGroupItems(ArrayList<Item> groupItems) {
		this.groupItems = groupItems;
	}
	public ArrayList<Item> getBaiItems() {
		return baiItems;
	}
	public void setBaiItems(ArrayList<Item> baiItems) {
		this.baiItems = baiItems;
	}
	public ArrayList<Item> getShiItems() {
		return shiItems;
	}
	public void setShiItems(ArrayList<Item> shiItems) {
		this.shiItems = shiItems;
	}
	public ArrayList<Item> getGeItems() {
		return geItems;
	}
	public void setGeItems(ArrayList<Item> geItems) {
		this.geItems = geItems;
	}
	
	
}
