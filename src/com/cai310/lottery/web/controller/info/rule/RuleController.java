package com.cai310.lottery.web.controller.info.rule;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.web.controller.BaseController;
public class RuleController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4561478180419508172L;
	private Lottery lottery;
	private Integer type;

	public String index() {
		if(null!=lottery){
			return lottery.getKey()+(null==type?"":"-"+type);
		}else{
			return null;
		}
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
