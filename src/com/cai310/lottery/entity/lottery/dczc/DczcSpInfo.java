package com.cai310.lottery.entity.lottery.dczc;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;

import com.cai310.entity.IdEntity;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.utils.JsonUtil;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "DCZC_SPINFO")
public class DczcSpInfo extends IdEntity {

	private static final long serialVersionUID = -142631419885084992L;

	private Long periodId;

	/** 期号 **/
	private String periodNumber;

	/** sp值信息 **/
	private String spContent;

	/**
	 * 玩法类型
	 * 
	 * @see com.cai310.lottery.support.dczc.PlayType
	 */
	private PlayType playType;

	/** 场次编号 */
	private Integer lineId;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	@SuppressWarnings("unchecked")
	@Transient
	public Map<String, Double> getContent() {
		if (StringUtils.isNotBlank(this.spContent)) {
			return (Map<String, Double>) JsonUtil.getMap4Json(this.spContent);
		}
		return new HashMap<String, Double>();
	}

	@Transient
	public void setContent(Map<String, Double> spMap) {
		if (spMap != null) {
			this.spContent = JsonUtil.getJsonString4JavaPOJO(spMap);
		} else {
			this.spContent = null;
		}
	}

	@Column
	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	@Column(length = 20)
	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	@Column(length = 1000)
	public String getSpContent() {
		return spContent;
	}

	public void setSpContent(String spContent) {
		this.spContent = spContent;
	}

	@Column
	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	@Column
	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	@Version
	@Column(nullable = false)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
