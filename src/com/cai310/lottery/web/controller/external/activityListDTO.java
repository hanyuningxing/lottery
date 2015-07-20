package com.cai310.lottery.web.controller.external;

import java.util.Date;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;

public class activityListDTO { 
	private List<activityDTO> activityListDTO;
	public activityListDTO(){
		activityListDTO = Lists.newArrayList();
		Date now = new Date();
	}
	public List<activityDTO> getActivityListDTO() {
		return activityListDTO;
	}
	public void setActivityListDTO(List<activityDTO> activityListDTO) {
		this.activityListDTO = activityListDTO;
	}
	
}
