package com.cai310.lottery.common.miss;

public interface MissDataFilter {
	
	public boolean accect(String results);
	
	public String getFieldName();
	
}