package com.cai310.lottery.dto.lottery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.support.jclq.JclqMatchItem;

/**
 * 
 */
public class SchemeInfoDTO implements Serializable {
	
	private static final long serialVersionUID = -1025920991877401793L;
	/** 场次编号 */
	private String passTypeText;
	
	/** 胆码最小命中数 */
	private Integer danMinHit;

	/** 胆码最大命中数 */
	private Integer danMaxHit;
	
	/** 选择的场次内容 */
	private List<SchemeMatchDTO> items;

	public String getPassTypeText() {
		return passTypeText;
	}

	public void setPassTypeText(String passTypeText) {
		this.passTypeText = passTypeText;
	}

	public Integer getDanMinHit() {
		return danMinHit;
	}

	public void setDanMinHit(Integer danMinHit) {
		this.danMinHit = danMinHit;
	}

	public Integer getDanMaxHit() {
		return danMaxHit;
	}

	public void setDanMaxHit(Integer danMaxHit) {
		this.danMaxHit = danMaxHit;
	}

	public List<SchemeMatchDTO> getItems() {
		return items;
	}

	public void setItems(List<SchemeMatchDTO> items) {
		this.items = items;
	}
	
}
