package com.cai310.lottery.entity.lottery.keno.qyh;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.Constant;
import com.cai310.lottery.QyhConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.KenoScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.qyh.QyhCompoundContent;
import com.cai310.lottery.support.qyh.QyhPlayType;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX +"QYH_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QyhScheme extends KenoScheme {

	private static final long serialVersionUID = -3346346419350630111L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.QYH;
	}
	/** 玩法 **/
	private QyhPlayType betType;

	@Column(name = "betType")
	public QyhPlayType getBetType() {
		return betType;
	}

	public void setBetType(QyhPlayType betType) {
		this.betType = betType;
	}
	/**
	 * 返回内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<QyhCompoundContent> getCompoundContent() {
		Collection<QyhCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				QyhCompoundContent.class);
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
		Collection<QyhCompoundContent> c = this.getCompoundContent();
		Iterator<QyhCompoundContent> it = c.iterator();
		QyhCompoundContent el11to5CompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			el11to5CompoundContent = it.next();
			if (QyhPlayType.RandomOne.equals(betType)||QyhPlayType.RoundOne.equals(betType)||QyhPlayType.DirectOne.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(el11to5CompoundContent.getBetList(),el11to5CompoundContent.getBetDanList()));
			}else if (betType.equals(QyhPlayType.RandomTwo)
					|| betType.equals(QyhPlayType.RandomThree)
					|| betType.equals(QyhPlayType.RandomFour)
					|| betType.equals(QyhPlayType.RandomFive)
					|| betType.equals(QyhPlayType.RandomSix)
					|| betType.equals(QyhPlayType.RandomSeven)
					|| betType.equals(QyhPlayType.RandomEight)
					|| betType.equals(QyhPlayType.RandomNine)
					|| betType.equals(QyhPlayType.RandomTen)
					||betType.equals(QyhPlayType.RoundTwo)
					|| betType.equals(QyhPlayType.RoundThree)
					|| betType.equals(QyhPlayType.RoundFour)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(el11to5CompoundContent.getBetList(),el11to5CompoundContent.getBetDanList()));
			}else if (QyhPlayType.DirectTwo.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(el11to5CompoundContent.getBet1List(),1));
				sb.append(getBetListStringMethod(el11to5CompoundContent.getBet2List(),2));
			}else if (QyhPlayType.DirectThree.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListStringMethod(el11to5CompoundContent.getBet1List(),1));
				sb.append(getBetListStringMethod(el11to5CompoundContent.getBet2List(),2));
				sb.append(getBetListStringMethod(el11to5CompoundContent.getBet3List(),3));
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
