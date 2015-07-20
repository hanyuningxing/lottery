package com.cai310.lottery.dto.lottery.zc;

import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.support.zc.PlayType;

public class SfzcSchemeDTO extends SchemeDTO {
	private static final long serialVersionUID = 1702077599053626560L;

	/** 玩法类型 */
	private PlayType playType;

	/**
	 * @return {@link #playType}
	 */
	public PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the {@link #playType} to set
	 */
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

}
