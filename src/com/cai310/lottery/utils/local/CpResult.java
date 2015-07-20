package com.cai310.lottery.utils.local;

import java.util.List;

public class CpResult{
	private Integer Code;
	private List<CpTicket> Data;
	public Integer getCode() {
		return Code;
	}
	public void setCode(Integer code) {
		Code = code;
	}
	public List<CpTicket> getData() {
		return Data;
	}
	public void setData(List<CpTicket> data) {
		Data = data;
	}
}
