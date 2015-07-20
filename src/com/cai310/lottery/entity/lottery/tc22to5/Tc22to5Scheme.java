package com.cai310.lottery.entity.lottery.tc22to5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.Constant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.tc22to5.Tc22to5CompoundContent;

/**
 * 体彩22选5彩票方案
 * 
 */ 
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TC22TO5_SCHEME")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class Tc22to5Scheme extends NumberScheme {
	private static final long serialVersionUID = -5780839277156063483L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.TC22TO5;
	}

	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<Tc22to5CompoundContent> getCompoundContent() {
		Collection<Tc22to5CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				Tc22to5CompoundContent.class);
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
		Collection<Tc22to5CompoundContent> c = this.getCompoundContent();
		Iterator<Tc22to5CompoundContent> it = c.iterator();
		Tc22to5CompoundContent tc22to5CompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			tc22to5CompoundContent = it.next();
			sb.append("<span class=\"rebchar\">选号:");
			if (StringUtils.isNotBlank(getBetListString(tc22to5CompoundContent.getDanList()))) {
				sb.append("(" + getBetListString(tc22to5CompoundContent.getDanList()) + ")");
			}
			sb.append(getBetListString(tc22to5CompoundContent.getBetList()));
			sb.append("</span>");
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	@Override
	public String getCompoundContentDetail(String result) {
		Collection<Tc22to5CompoundContent> c = this.getCompoundContent();
		Iterator<Tc22to5CompoundContent> it = c.iterator();
		Tc22to5CompoundContent tc22to5CompoundContent;
		StringBuffer sb = new StringBuffer();
		List<Integer> resultList = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(result)&&result.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
			String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			for (String num : results) {
				resultList.add(Integer.valueOf(num));
			}
		}
		while (it.hasNext()) {
			tc22to5CompoundContent = it.next();
			sb.append("<span class=\"rebchar\">选号:");
			if (StringUtils.isNotBlank(getBetListString(tc22to5CompoundContent.getDanList()))) {
				sb.append("(" + getBetListString(tc22to5CompoundContent.getDanList(),resultList) + ")");
			}
			sb.append(getBetListString(tc22to5CompoundContent.getBetList(),resultList));
			sb.append("</span>");
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	
	@Transient
	public String getCompoundContentTextForList() {
		Collection<Tc22to5CompoundContent> c = this.getCompoundContent();
		Iterator<Tc22to5CompoundContent> it = c.iterator();
		Tc22to5CompoundContent tc22to5CompoundContent;
		StringBuffer sb = new StringBuffer();
		if (it.hasNext()) {
			tc22to5CompoundContent = it.next();
			sb.append("<span class=\"rebchar\">红球:");
			if (StringUtils.isNotBlank(getBetListString(tc22to5CompoundContent.getDanList()))) {
				sb.append("(" + getBetListString(tc22to5CompoundContent.getDanList()) + ")");
			}
			sb.append(getBetListString(tc22to5CompoundContent.getBetList()));
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
