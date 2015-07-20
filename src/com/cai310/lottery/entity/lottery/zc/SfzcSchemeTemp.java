package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.PolymorphismType;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.SfzcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;


@Entity
@org.hibernate.annotations.Entity(polymorphism = PolymorphismType.EXPLICIT)
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SCHEME_TEMP")
public class SfzcSchemeTemp extends SchemeTemp {
	
	private static final long serialVersionUID = 4589728259921178057L;
	
	/** 玩法类型*/
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

	/**
	 * @param playType
	 *            the {@link #playType} to set
	 */
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	/**
	 * 返回复式内容解析后的投注项数组
	 * 
	 * @return
	 * @throws DataException
	 */
	@Transient
	public SfzcCompoundItem[] getCompoundContent() throws DataException {
		if (this.getContent() == null) {
			throw new DataException("方案内容获取为空.");
		}
		String[] itemStrs = null;
		if (playType == PlayType.SFZC9) {
			itemStrs = this.getContent().split(String.valueOf(ZcUtils.getDanMinHitContentSpiltCode()))[1].split(String
					.valueOf(ZcUtils.getContentSpiltCode()));
		} else {
			itemStrs = this.getContent().split(String.valueOf(ZcUtils.getContentSpiltCode()));
		}
		if (itemStrs.length != ZcUtils.getMatchCount(this.getLotteryType())) {
			throw new DataException("方案内容选项个数异常.");
		}
		SfzcCompoundItem[] items = new SfzcCompoundItem[itemStrs.length];
		for (int i = 0; i < itemStrs.length; i++) {
			items[i] = new SfzcCompoundItem(Byte.valueOf(itemStrs[i]).byteValue(), i);
		}
		return items;
	}
}
