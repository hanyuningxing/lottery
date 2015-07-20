package com.cai310.lottery.dto.lottery.welfare36to7;

import com.cai310.lottery.dto.lottery.NumberSchemeDTO;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;

public class Welfare36to7SchemeDTO extends NumberSchemeDTO {
	private static final long serialVersionUID = 2706540002397683953L;
	private Welfare36to7PlayType playType;

	/**
	 * @return the playType
	 */
	public Welfare36to7PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Welfare36to7PlayType playType) {
		this.playType = playType;
	}

}
