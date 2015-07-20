package com.cai310.lottery.entity.lottery.keno.klpk;

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
import com.cai310.lottery.KlpkConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.KenoScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.klpk.KlpkCompoundContent;
import com.cai310.lottery.support.klpk.KlpkPlayType;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX +"KLPK_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KlpkScheme extends KenoScheme {
	private static final long serialVersionUID = -119332898078428893L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.KLPK;
	}
	/** 玩法 **/
	private KlpkPlayType betType;

	@Column(name = "betType")
	public KlpkPlayType getBetType() {
		return betType;
	}

	public void setBetType(KlpkPlayType betType) {
		this.betType = betType;
	}
	/**
	 * 返回内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<KlpkCompoundContent> getCompoundContent() {
		Collection<KlpkCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				KlpkCompoundContent.class);
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
		Collection<KlpkCompoundContent> c = this.getCompoundContent();
		Iterator<KlpkCompoundContent> it = c.iterator();
		KlpkCompoundContent klpkCompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			klpkCompoundContent = it.next();
			sb.append("["+betType.getTypeName()+"]:");
			sb.append(getBetListStringMethod(klpkCompoundContent.getBetList(),klpkCompoundContent.getBetDanList()));
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
