package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.support.zc.PlayType;

/**
 * 胜负足彩方案过关信息类.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SFZC_PASSCOUNT")
@Entity
public class SfzcPasscount extends ZcPasscount {

	private static final long serialVersionUID = -2425610312588917882L;

	/**
	 * 玩法类型
	 * 
	 * @see com.cai310.lottery.support.zc.PlayType
	 */
	private PlayType playType;

	/**
	 * @return {@link #playType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.zc.PlayType"),
			@Parameter(name = EnumType.TYPE, value = PlayType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	@Transient
	public Lottery getLotteryType() {
		return Lottery.SFZC;
	}
	
	/**
	 * @return 是否中奖
	 */
	@Override
	@Transient
	public boolean isWon() {
		if(this.playType==PlayType.SFZC14){
			return this.getLost0()>0||this.getLost1()>0?true:false;
		}else if(this.playType==PlayType.SFZC9){
			return this.getLost0()>0?true:false;
		}
		return false;
	}
	
	/**
	 * @return 方案号
	 */
	@Transient
	public String getSchemeNumber() {
		return this.getLotteryType().getSchemeNumber(this.getSchemeId());
	}

}
