package com.cai310.lottery.entity.info;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.Lottery;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "MATCH_INFO")
public class MatchInfo extends IdEntity implements CreateMarkable,UpdateMarkable {

	private static final long serialVersionUID = -6380516365102466395L;

	private Lottery lottery;

	private Long periodId;

	private String periodNumber;
	
	private Integer lineId;

	private Date createTime;

	private Date lastModifyTime;

	private OddsData oddsData;

	@Column(nullable=false)
	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	@Column
	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	@Embedded
	public OddsData getOddsData() {
		return oddsData;
	}

	public void setOddsData(OddsData oddsData) {
		this.oddsData = oddsData;
	}

	@Column
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;

	}

	@Column
	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	@Column
	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

}
