package com.cai310.lottery.entity.lottery.sevenstar;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SevenstarConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.seven.SevenCompoundContent;
import com.cai310.lottery.support.sevenstar.SevenstarCompoundContent;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 彩票方案
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SEVENSTAR_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class SevenstarScheme extends NumberScheme {
	private static final long serialVersionUID = -5780839277156063483L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.SEVENSTAR;
	}

	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<SevenstarCompoundContent> getCompoundContent() {
		Collection<SevenstarCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				SevenstarCompoundContent.class);
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
		Collection<SevenstarCompoundContent> c = this.getCompoundContent();
		Iterator<SevenstarCompoundContent> it = c.iterator();
		SevenstarCompoundContent sevenstarCompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			sevenstarCompoundContent = it.next();
			sb.append(getBetListString(sevenstarCompoundContent.getArea1List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea2List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea3List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea4List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea5List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea6List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea7List(), true));
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	public String getCompoundContentTextForList() {
		Collection<SevenstarCompoundContent> c = this.getCompoundContent();
		Iterator<SevenstarCompoundContent> it = c.iterator();
		SevenstarCompoundContent sevenstarCompoundContent;
		StringBuffer sb = new StringBuffer();
		if (it.hasNext()) {
			sevenstarCompoundContent = it.next();
			sb.append(getBetListString(sevenstarCompoundContent.getArea1List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea2List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea3List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea4List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea5List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea6List(), false));
			sb.append(getBetListString(sevenstarCompoundContent.getArea7List(), true));
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		if (it.hasNext()) {
			sb.append("<br/>");
			sb.append("...");
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	private String getBetListString(List<String> list, Boolean last) {
		if (list != null && list.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i));
				sb.append(SevenstarConstant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length() - SevenstarConstant.SEPARATOR_FOR_NUMBER.length());
			if (!last) {
				sb.append(SevenstarConstant.SEPARATOR_FOR_);
			}
			return sb.toString();
		}
		return "";
	}
}
