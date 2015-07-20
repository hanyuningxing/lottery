package com.cai310.lottery.entity.lottery.jczq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.entity.IdEntity;

/**
 * 竞彩足球方案所选择的比赛场次
 * 
 */
@Table(name = JczqSchemeMatch.TABLE_NAME)
@Entity
public class JczqSchemeMatch extends IdEntity {
	private static final long serialVersionUID = -7252913036254858708L;

	public static final String TABLE_NAME = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "JCZQ_SCHEME_MATCH";

	/** 方案编号 */
	private Long schemeId;

	/** 比赛场次标识 */
	private String matchKey;

	/**
	 * @return the {@link #schemeId}
	 */
	@Column(nullable = false)
	public Long getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId
	 *            the {@link #schemeId} to set
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return the {@link #matchKey}
	 */
	@Column(nullable = false, length = 12)
	public String getMatchKey() {
		return matchKey;
	}

	/**
	 * @param matchKey
	 *            the {@link #matchKey} to set
	 */
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
}
