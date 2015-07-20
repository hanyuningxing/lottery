package com.cai310.lottery.entity.lottery.keno.klsf;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import com.cai310.lottery.Constant;
import com.cai310.lottery.KlsfConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.KenoScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.klsf.KlsfCompoundContent;
import com.cai310.lottery.support.klsf.KlsfPlayType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX +"KLSF_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KlsfScheme extends KenoScheme {

	private static final long serialVersionUID = -7690842849288610758L;

	/** 玩法 **/
	private KlsfPlayType betType;

	@Column(name = "betType")
	public KlsfPlayType getBetType() {
		return betType;
	}

	public void setBetType(KlsfPlayType betType) {
		this.betType = betType;
	}

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.KLSF;
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
	/**
	 * 返回内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<KlsfCompoundContent> getCompoundContent() {
		Collection<KlsfCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				KlsfCompoundContent.class);
		return c;
	}
	@Transient
	public String getCompoundContentText() {
		Collection<KlsfCompoundContent> c = this.getCompoundContent();
		Iterator<KlsfCompoundContent> it = c.iterator();
		KlsfCompoundContent klsfCompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			klsfCompoundContent = it.next();
			if (KlsfPlayType.NormalOne.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBetList()));
			}else if (KlsfPlayType.RedOne.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBetList()));
			}else if (KlsfPlayType.RandomTwo.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBetList()));
			}else if (KlsfPlayType.ConnectTwoGroup.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBetList()));
			}else if (KlsfPlayType.ConnectTwoDirect.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBet1List(),false));
				sb.append(getBetListString(klsfCompoundContent.getBet2List(),true));
			}else if (KlsfPlayType.RandomThree.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBetList()));
			}else if (KlsfPlayType.ForeThreeGroup.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBetList()));
			}else if (KlsfPlayType.ForeThreeDirect.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBet1List(),false));
				sb.append(getBetListString(klsfCompoundContent.getBet2List(),false));
				sb.append(getBetListString(klsfCompoundContent.getBet3List(),true));
			}else if (KlsfPlayType.RandomFour.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBetList()));
			}else if (KlsfPlayType.RandomFive.equals(betType)) {
				sb.append("["+betType.getTypeName()+"]:");
				sb.append(getBetListString(klsfCompoundContent.getBetList()));
			}else {
				return "";
			}
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	/**
	 * 把号码转换成通俗的文字
	 * 
	 * @param list 号码
	 * @param playType 玩法
	 * @return 对应的通俗文字
	 */
	@Transient
	public String getBetListString(List<String> list) {
		if (list != null && list.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i));
				sb.append(KlsfConstant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length() - KlsfConstant.SEPARATOR_FOR_NUMBER.length());
			return sb.toString();
		}
		return "";
	}
	
	@Transient
	private String getBetListString(List<String> list, Boolean last) {
		if (list != null && list.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i));
				sb.append(KlsfConstant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length() - KlsfConstant.SEPARATOR_FOR_NUMBER.length());
			if (!last) {
				sb.append(KlsfConstant.SEPARATOR_FOR_);
			}
			return sb.toString();
		}
		return "";
	}
	@Transient
	@Override
	public Byte getPlayTypeOrdinal() {
		return (byte) this.getBetType().ordinal();
	}
}
