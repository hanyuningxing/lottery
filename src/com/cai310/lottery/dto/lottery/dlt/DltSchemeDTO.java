package com.cai310.lottery.dto.lottery.dlt;

import javax.persistence.Column;

import com.cai310.lottery.dto.lottery.NumberSchemeDTO;
import com.cai310.lottery.support.dlt.DltPlayType;

public class DltSchemeDTO extends NumberSchemeDTO {
	private static final long serialVersionUID = 2706540002397683953L;
	private DltPlayType playType;

	/**
	 * @return the playType
	 */

	@Column(nullable = false)
	public DltPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(DltPlayType playType) {
		this.playType = playType;
	}
}
