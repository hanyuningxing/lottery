package com.cai310.lottery.entity.lottery.keno.ssc;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import net.sf.json.JSONArray;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.KenoScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ssc.SscCompoundContent;
import com.cai310.lottery.support.ssc.SscPlayType;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX +"SSC_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SscScheme extends KenoScheme {

	private static final long serialVersionUID = -3346346419350630111L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.SSC;
	}
	/** 玩法 **/
	private SscPlayType betType;

	@Column(name = "betType")
	public SscPlayType getBetType() {
		return betType;
	}

	public void setBetType(SscPlayType betType) {
		this.betType = betType;
	}
	/**
	 * 返回内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<SscCompoundContent> getCompoundContent() {
		Collection<SscCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				SscCompoundContent.class);
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
		Collection<SscCompoundContent> c = this.getCompoundContent();
		Iterator<SscCompoundContent> it = c.iterator();
		SscCompoundContent compoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			compoundContent = it.next();
			switch (betType) {
			case DirectOne:	
				sb.append("["+betType.getTypeName()+"]:")
				.append(getBetListString(compoundContent.getArea5List()));
				break;
			case DirectTwo:
				sb.append("["+betType.getTypeName()+"]:")
				.append("十:"+getBetListString(compoundContent.getArea4List()))
				.append(" 个:"+getBetListString(compoundContent.getArea5List()));
				break;
			case DirectThree:
				sb.append("["+betType.getTypeName()+"]:")
				.append("百:"+getBetListString(compoundContent.getArea3List()))
				.append(" 十:"+getBetListString(compoundContent.getArea4List()))
				.append(" 个:"+getBetListString(compoundContent.getArea5List()));
				break;
			case DirectFive:
				sb.append("["+betType.getTypeName()+"]:")
				.append("万:"+getBetListString(compoundContent.getArea1List()))
				.append(" 千:"+getBetListString(compoundContent.getArea2List()))
				.append(" 百:"+getBetListString(compoundContent.getArea3List()))
				.append(" 十:"+getBetListString(compoundContent.getArea4List()))
				.append(" 个:"+getBetListString(compoundContent.getArea5List()));
				break;
			case AllFive:
				sb.append("["+betType.getTypeName()+"]:")
				.append("万:"+getBetListString(compoundContent.getArea1List()))
				.append(" 千:"+getBetListString(compoundContent.getArea2List()))
				.append(" 百:"+getBetListString(compoundContent.getArea3List()))
				.append(" 十:"+getBetListString(compoundContent.getArea4List()))
				.append(" 个:"+getBetListString(compoundContent.getArea5List()));
				break;
			case DirectFour:
				sb.append("["+betType.getTypeName()+"]:")
				.append(" 千:"+getBetListString(compoundContent.getArea2List()))
				.append(" 百:"+getBetListString(compoundContent.getArea3List()))
				.append(" 十:"+getBetListString(compoundContent.getArea4List()))
				.append(" 个:"+getBetListString(compoundContent.getArea5List()));
				break;
			case RANDOMONE:
				sb.append("["+betType.getTypeName()+"]:")
				.append("万:"+getBetListString(compoundContent.getArea1List()))
				.append(" 千:"+getBetListString(compoundContent.getArea2List()))
				.append(" 百:"+getBetListString(compoundContent.getArea3List()))
				.append(" 十:"+getBetListString(compoundContent.getArea4List()))
				.append(" 个:"+getBetListString(compoundContent.getArea5List()));
				break;
			case RANDOMTWO:
				sb.append("["+betType.getTypeName()+"]:")
				.append("万:"+getBetListString(compoundContent.getArea1List()))
				.append(" 千:"+getBetListString(compoundContent.getArea2List()))
				.append(" 百:"+getBetListString(compoundContent.getArea3List()))
				.append(" 十:"+getBetListString(compoundContent.getArea4List()))
				.append(" 个:"+getBetListString(compoundContent.getArea5List()));
				break;
			case GroupTwo:
				sb.append("["+betType.getTypeName()+"]:")
				.append(getBetListStringMethod(compoundContent.getGroupTwoList(),compoundContent.getBetDanList()));
				break;
			case DirectTwoSum:
				sb.append("["+betType.getTypeName()+"]:")
				.append(getBetListString(compoundContent.getDirectSumList()));
				break;
			case GroupTwoSum:
				sb.append("["+betType.getTypeName()+"]:")
				.append(getBetListString(compoundContent.getGroupSumList()));
				break;
			case ThreeGroup3:
				sb.append("["+betType.getTypeName()+"]:")
				.append(getBetListStringMethod(compoundContent.getGroup3List(),compoundContent.getBetDanList()));
				break;
			case ThreeGroup6:
				sb.append("["+betType.getTypeName()+"]:")
				.append(getBetListStringMethod(compoundContent.getGroup6List(),compoundContent.getBetDanList()));
				break;
			case GroupThreeSum:
				sb.append("["+betType.getTypeName()+"]:")
				.append(getBetListString(compoundContent.getGroupSumList()));
				break;
			case DirectThreeSum:
				sb.append("["+betType.getTypeName()+"]:")
				.append(getBetListString(compoundContent.getDirectSumList()));
				break;
			case BigSmallDoubleSingle:
				sb.append("["+betType.getTypeName()+"]:")
				.append(betType.toText(compoundContent.getArea4List()))
				.append("|"+betType.toText(compoundContent.getArea5List()));
				break;
			default:
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
