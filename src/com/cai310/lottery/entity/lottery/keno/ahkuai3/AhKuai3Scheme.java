package com.cai310.lottery.entity.lottery.keno.ahkuai3;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.KenoScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ahkuai3.AhKuai3CompoundContent;
import com.cai310.lottery.support.ahkuai3.AhKuai3PlayType;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AH_KUAI3_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AhKuai3Scheme extends KenoScheme {

	private static final long serialVersionUID = 4259081223800586419L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.AHKUAI3;
	}

	/** 玩法 */
	private AhKuai3PlayType betType;

	@Column(name = "betType")
	public AhKuai3PlayType getBetType() {
		return betType;
	}

	public void setBetType(AhKuai3PlayType betType) {
		this.betType = betType;
	}

	/**
	 * 返回内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<AhKuai3CompoundContent> getCompoundContent() {
		Collection<AhKuai3CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()), 
				AhKuai3CompoundContent.class);
		return c;
	}

	/**
	 * 后台方案管理的方案内容
	 * 
	 * @return
	 * @throws DataException
	 */
	@Transient
	@Override
	public String getContentText() {
		if (isCompoundMode()) {
			return getCompoundContentText();
		} else {
			return this.getContent();
		}
	}
	
	@Transient
	public String getCompoundContentText() {
		Collection<AhKuai3CompoundContent> c = this.getCompoundContent();
		Iterator<AhKuai3CompoundContent> it = c.iterator();
		AhKuai3CompoundContent ahkuai3CompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			ahkuai3CompoundContent = it.next();
			if (AhKuai3PlayType.HeZhi.equals(betType)) {
				sb.append("[" + betType.getTypeName() + "]:");
				sb.append(getBetListStringMethod(ahkuai3CompoundContent.getBetList(), ahkuai3CompoundContent.getBetDanList()));
			} else if (AhKuai3PlayType.RandomTwo.equals(betType)) {
				sb.append("[" + betType.getTypeName() + "]:");
				sb.append(getBetListStringMethod(ahkuai3CompoundContent.getBetList(), ahkuai3CompoundContent.getBetDanList()));
			} else if (AhKuai3PlayType.ThreeTX.equals(betType)) {
				sb.append("[" + betType.getTypeName() + "]:");
				sb.append("三同号通选");
			} else if (AhKuai3PlayType.ThreeDX.equals(betType)) {
				sb.append("[" + betType.getTypeName() + "]:");
				sb.append(getBetListStringMethod(ahkuai3CompoundContent.getBetList(), ahkuai3CompoundContent.getBetDanList()));
			} else if (AhKuai3PlayType.RandomThree.equals(betType)) {
				sb.append("[" + betType.getTypeName() + "]:");
				sb.append(getBetListStringMethod(ahkuai3CompoundContent.getBetList(), ahkuai3CompoundContent.getBetDanList()));
			} else if (AhKuai3PlayType.ThreeLX.equals(betType)) {
				sb.append("[" + betType.getTypeName() + "]:");
				sb.append("三连号通选");
			} else if (AhKuai3PlayType.TwoFX.equals(betType)) {
				sb.append("[" + betType.getTypeName() + "]:");
				sb.append(getBetListStringMethod(ahkuai3CompoundContent.getBetList(), ahkuai3CompoundContent.getBetDanList()));
			}else if (AhKuai3PlayType.TwoDX.equals(betType)) {
				sb.append("[" + betType.getTypeName() + "]:");
				sb.append(getBetListStringMethod2(ahkuai3CompoundContent.getBetList(), ahkuai3CompoundContent.getDisList()));
			} else {
				return "";
			}
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}

	/**
	 * 返回玩法名字
	 * 
	 * @return
	 */
	@Transient
	public String getBetTypeString() {
		if (null == this.betType)
			return "";
		return this.betType.getTypeName();
	}

	/**
	 * 投注方式的枚举位置
	 */
	@Transient
	@Override
	public Byte getPlayTypeOrdinal() {
		return (byte) this.getBetType().ordinal();
	}

}
