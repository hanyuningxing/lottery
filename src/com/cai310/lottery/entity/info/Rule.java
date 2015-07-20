package com.cai310.lottery.entity.info;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.Lottery;
import com.google.common.collect.Maps;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "RULE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rule extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 1L;
	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;
	
	// /创建时间
	private Date createTime;

	// /修改时间
	private Date lastModifyTime;
	//标签
	private String rule;	
	//级别
	private Integer playType;

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

	/**
	 * @return {@link #lotteryType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = true)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the {@link #lotteryType} to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
	@Lob
	@Column
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	@Column
	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}
	@Transient
	public Map<String,String> getPlayTypeBy(Lottery lottery){
		Map<String,String> playType = Maps.newHashMap();
		if(lottery.equals(Lottery.SFZC)){
			playType.put("0", "胜负彩");
			playType.put("1", "任选9场");
		}else if(lottery.equals(Lottery.PL)){
			playType.put("0", "排列三");
			playType.put("1", "排列五");
		}else{
			playType.put("0", "常规");
		}
		return playType;
	}
	
}
