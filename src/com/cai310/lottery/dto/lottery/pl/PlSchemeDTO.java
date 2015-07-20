package com.cai310.lottery.dto.lottery.pl;

import com.cai310.lottery.dto.lottery.NumberSchemeDTO;
import com.cai310.lottery.support.pl.PlPlayType;

public class PlSchemeDTO extends NumberSchemeDTO {
	private static final long serialVersionUID = 2706540002397683953L;
	private PlPlayType playType;

	/**
	 * @return the playType
	 */
	public PlPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(PlPlayType playType) {
		this.playType = playType;
	}
}
