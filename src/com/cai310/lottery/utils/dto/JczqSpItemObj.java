package com.cai310.lottery.utils.dto;

import java.util.List;
public  class JczqSpItemObj{
	private int index;
	private List <JczqSpItem> awardList;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public List<JczqSpItem> getAwardList() {
		return awardList;
	}
	public void setAwardList(List<JczqSpItem> awardList) {
		this.awardList = awardList;
	}
	
}
