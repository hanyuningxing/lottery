package com.cai310.lottery.dto.lottery.welfare3d;

import com.cai310.lottery.dto.lottery.NumberSchemeDTO;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;

public class Welfare3dSchemeDTO extends NumberSchemeDTO {
	private static final long serialVersionUID = 2706540002397683953L;
	private Welfare3dPlayType playType;

	/**
	 * @return the playType
	 */
	public Welfare3dPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Welfare3dPlayType playType) {
		this.playType = playType;
	}
}
