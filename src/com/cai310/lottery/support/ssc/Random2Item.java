package com.cai310.lottery.support.ssc;

import java.io.Serializable;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.SelectedCount;

public class Random2Item implements SelectedCount, Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int selectedNum;
	public int selectedCount() {
		return selectedNum;
	}
	public int getSelectedNum() {
		return selectedNum;
	}
	public void setSelectedNum(int selectedNum) {
		this.selectedNum = selectedNum;
	}
}
