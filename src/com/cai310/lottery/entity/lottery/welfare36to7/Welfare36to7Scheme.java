package com.cai310.lottery.entity.lottery.welfare36to7;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import com.cai310.lottery.Constant;
import com.cai310.lottery.Welfare36to7Constant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.welfare36to7.Welfare36to7CompoundContent;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 彩票方案
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "W36TO7_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class Welfare36to7Scheme extends NumberScheme {
	private static final long serialVersionUID = -5780839277156063483L;
	private Welfare36to7PlayType playType;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}

	/**
	 * @return the betType
	 */

	@Column(nullable = false)
	public Welfare36to7PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param betType the betType to set
	 */
	public void setPlayType(Welfare36to7PlayType playType) {
		this.playType = playType;
	}

	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<Welfare36to7CompoundContent> getCompoundContent() {
		Collection<Welfare36to7CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				Welfare36to7CompoundContent.class);
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
		Collection<Welfare36to7CompoundContent> c = this.getCompoundContent();
		Iterator<Welfare36to7CompoundContent> it = c.iterator();
		Welfare36to7CompoundContent welfare36to7CompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			welfare36to7CompoundContent = it.next();
			if (Welfare36to7PlayType.General.equals(playType)) {
				sb.append(getBetListString(welfare36to7CompoundContent.getWelfare36to7List()));
			} else if (Welfare36to7PlayType.Haocai1.equals(playType)) {
				sb.append(getBetListString(welfare36to7CompoundContent.getHaocai1List()));
			} else if (Welfare36to7PlayType.Haocai2.equals(playType)) {
				sb.append(getBetListString(welfare36to7CompoundContent.getHaocai2List()));
			} else if (Welfare36to7PlayType.Haocai3.equals(playType)) {
				sb.append(getBetListString(welfare36to7CompoundContent.getHaocai3List()));
			} else if (Welfare36to7PlayType.Zodiac.equals(playType)) {
				sb.append(getBetListString(welfare36to7CompoundContent.getZodiacList(), playType));
			} else if (Welfare36to7PlayType.Season.equals(playType)) {
				sb.append(getBetListString(welfare36to7CompoundContent.getSeasonList(), playType));
			} else if (Welfare36to7PlayType.Azimuth.equals(playType)) {
				sb.append(getBetListString(welfare36to7CompoundContent.getAzimuthList(), playType));
			} else {
				return "";
			}
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}

	@Transient
	public String getBetListString(List<String> list) {
		if (list != null && list.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i));
				sb.append(Welfare36to7Constant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length() - Welfare36to7Constant.SEPARATOR_FOR_NUMBER.length());
			return sb.toString();
		}
		return "";
	}

	/**
	 * 把号码转换成通俗的文字
	 * 
	 * @param list 号码
	 * @param playType 玩法
	 * @return 对应的通俗文字
	 */
	@Transient
	private String getBetListString(List<String> list, Welfare36to7PlayType playType) {
		if (list != null && list.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append(playType.toText(Integer.valueOf(list.get(i))));
				sb.append(Welfare36to7Constant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length() - Welfare36to7Constant.SEPARATOR_FOR_NUMBER.length());
			return sb.toString();
		}
		return "";
	}
	@Transient
	@Override
	public Byte getPlayTypeOrdinal() {
		return (byte) getPlayType().ordinal();
	}
	@Transient
	public String getBetTypeString() {
		if (null==this.playType)return "";
		return this.playType.getTypeName();
	}
}
