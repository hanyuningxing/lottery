package com.cai310.lottery.entity.lottery.keno.gdel11to5;

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
import com.cai310.lottery.support.gdel11to5.GdEl11to5CompoundContent;
import com.cai310.lottery.support.gdel11to5.GdEl11to5PlayType;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX +"GD_EL11TO5_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GdEl11to5Scheme extends KenoScheme {
	private static final long serialVersionUID = -119332898078428893L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.GDEL11TO5;
	}
	/** 玩法 **/
	private GdEl11to5PlayType betType;

	@Column(name = "betType")
	public GdEl11to5PlayType getBetType() {
		return betType;
	}

	public void setBetType(GdEl11to5PlayType betType) {
		this.betType = betType;
	}
	/**
	 * 返回内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<GdEl11to5CompoundContent> getCompoundContent() {
		Collection<GdEl11to5CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				GdEl11to5CompoundContent.class);
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
		Collection<GdEl11to5CompoundContent> c = this.getCompoundContent();
		Iterator<GdEl11to5CompoundContent> it = c.iterator();
		GdEl11to5CompoundContent gdel11to5CompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			gdel11to5CompoundContent = it.next();
			if (GdEl11to5PlayType.NormalOne.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else if (GdEl11to5PlayType.RandomTwo.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else if (GdEl11to5PlayType.ForeTwoGroup.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else if (GdEl11to5PlayType.ForeTwoDirect.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBet1List(),1));
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBet2List(),2));
			}else if (GdEl11to5PlayType.RandomThree.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else if (GdEl11to5PlayType.ForeThreeGroup.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else if (GdEl11to5PlayType.ForeThreeDirect.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBet1List(),1));
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBet2List(),2));
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBet3List(),3));
			}else if (GdEl11to5PlayType.RandomFour.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else if (GdEl11to5PlayType.RandomFive.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else if (GdEl11to5PlayType.RandomSix.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else if (GdEl11to5PlayType.RandomSeven.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else if (GdEl11to5PlayType.RandomEight.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(gdel11to5CompoundContent.getBetList(),gdel11to5CompoundContent.getBetDanList()));
			}else {
				return "";
			}
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	public String getBetTypeString() {
		if (null==this.betType)return "";
		return this.betType.getTypeName();
	}
	@Transient
	@Override
	public Byte getPlayTypeOrdinal() {
		return (byte) this.getBetType().ordinal();
	}
}
