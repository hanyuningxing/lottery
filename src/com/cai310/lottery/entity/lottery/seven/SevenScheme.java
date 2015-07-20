package com.cai310.lottery.entity.lottery.seven;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.seven.SevenCompoundContent;
import com.cai310.lottery.support.seven.SevenCompoundContent;

/**
 * 彩票方案
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SEVEN_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class SevenScheme extends NumberScheme {
	private static final long serialVersionUID = -5780839277156063483L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.SEVEN;
	}

	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<SevenCompoundContent> getCompoundContent() {
		Collection<SevenCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				SevenCompoundContent.class);
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
		Collection<SevenCompoundContent> c = this.getCompoundContent();
		Iterator<SevenCompoundContent> it = c.iterator();
		SevenCompoundContent sevenCompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			sevenCompoundContent = it.next();
			sb.append("<span class=\"rebchar\">投注号码:");
			if (StringUtils.isNotBlank(getBetListString(sevenCompoundContent.getDanList()))) {
				sb.append("(" + getBetListString(sevenCompoundContent.getDanList()) + ")");
			}
			sb.append(getBetListString(sevenCompoundContent.getBallList()));
			sb.append("</span>");
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	@Override
	public String getCompoundContentDetail(String result) {
		Collection<SevenCompoundContent> c = this.getCompoundContent();
		Iterator<SevenCompoundContent> it = c.iterator();
		SevenCompoundContent sevenCompoundContent;
		StringBuffer sb = new StringBuffer();
		List<Integer> resultList = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(result)&&result.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
			String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			for (String num : results) {
				resultList.add(Integer.valueOf(num));
			}
		}
		while (it.hasNext()) {
			sevenCompoundContent = it.next();
			sb.append("<span class=\"rebchar\">投注号码:");
			if (StringUtils.isNotBlank(getBetListString(sevenCompoundContent.getDanList()))) {
				sb.append("(" + getBetListString(sevenCompoundContent.getDanList(),resultList) + ")");
			}
			sb.append(getBetListString(sevenCompoundContent.getBallList(),resultList));
			sb.append("</span>");
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	
	@Transient
	public String getCompoundContentTextForList() {
		Collection<SevenCompoundContent> c = this.getCompoundContent();
		Iterator<SevenCompoundContent> it = c.iterator();
		SevenCompoundContent sevenCompoundContent;
		StringBuffer sb = new StringBuffer();
		if (it.hasNext()) {
			sevenCompoundContent = it.next();
			sb.append("<span class=\"rebchar\">红球:");
			if (StringUtils.isNotBlank(getBetListString(sevenCompoundContent.getDanList()))) {
				sb.append("(" + getBetListString(sevenCompoundContent.getDanList()) + ")");
			}
			sb.append(getBetListString(sevenCompoundContent.getBallList()));
			sb.append("</span>");
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		if (it.hasNext()) {
			sb.append("<br/>");
			sb.append("...");
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	
}
