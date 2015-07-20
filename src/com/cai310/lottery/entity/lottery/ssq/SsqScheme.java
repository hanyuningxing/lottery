package com.cai310.lottery.entity.lottery.ssq;

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
import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ssq.SsqCompoundContent;

/**
 * 双色球彩票方案
 * 
 */ 
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SSQ_SCHEME")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class SsqScheme extends NumberScheme {
	private static final long serialVersionUID = -5780839277156063483L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.SSQ;
	}

	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<SsqCompoundContent> getCompoundContent() {
		Collection<SsqCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				SsqCompoundContent.class);
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
		Collection<SsqCompoundContent> c = this.getCompoundContent();
		Iterator<SsqCompoundContent> it = c.iterator();
		SsqCompoundContent ssqCompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			ssqCompoundContent = it.next();
			sb.append("<span class=\"rebchar\">红球:");
			if (StringUtils.isNotBlank(getBetListString(ssqCompoundContent.getRedDanList()))) {
				sb.append("(" + getBetListString(ssqCompoundContent.getRedDanList()) + ")");
			}
			sb.append(getBetListString(ssqCompoundContent.getRedList()));
			sb.append("</span>");
			sb.append("<br/>");
			sb.append("<span class=\"bluechar\">篮球:");
			sb.append(getBetListString(ssqCompoundContent.getBlueList()));
			sb.append("</span>");
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	@Override
	public String getCompoundContentDetail(String result) {
		Collection<SsqCompoundContent> c = this.getCompoundContent();
		Iterator<SsqCompoundContent> it = c.iterator();
		SsqCompoundContent ssqCompoundContent;
		StringBuffer sb = new StringBuffer();
		List<Integer> redResultList = new ArrayList<Integer>();
		List<Integer> buleResultList = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(result)&&result.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
			String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			String[] redResults = (String[]) ArrayUtils.subarray(results, 0, 6);
			String[] buleResults= (String[]) ArrayUtils.subarray(results, 6, 7);
			for (String num : redResults) {
				redResultList.add(Integer.valueOf(num));
			}
			for (String num : buleResults) {
				buleResultList.add(Integer.valueOf(num));
			}
		}
		while (it.hasNext()) {
			ssqCompoundContent = it.next();
			sb.append("<span class=\"rebchar\">红球:");
			if (StringUtils.isNotBlank(getBetListString(ssqCompoundContent.getRedDanList()))) {
				sb.append("(" + getBetListString(ssqCompoundContent.getRedDanList(),redResultList) + ")");
			}
			sb.append(getBetListString(ssqCompoundContent.getRedList(),redResultList));
			sb.append("</span>");
			sb.append("<br/>");
			sb.append("<span class=\"bluechar\">篮球:");
			sb.append(getBetListString(ssqCompoundContent.getBlueList(),buleResultList));
			sb.append("</span>");
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	
	@Transient
	public String getCompoundContentTextForList() {
		Collection<SsqCompoundContent> c = this.getCompoundContent();
		Iterator<SsqCompoundContent> it = c.iterator();
		SsqCompoundContent ssqCompoundContent;
		StringBuffer sb = new StringBuffer();
		if (it.hasNext()) {
			ssqCompoundContent = it.next();
			sb.append("<span class=\"rebchar\">红球:");
			if (StringUtils.isNotBlank(getBetListString(ssqCompoundContent.getRedDanList()))) {
				sb.append("(" + getBetListString(ssqCompoundContent.getRedDanList()) + ")");
			}
			sb.append(getBetListString(ssqCompoundContent.getRedList()));
			sb.append("</span>");
			sb.append("<br/>");
			sb.append("<span class=\"bluechar\">篮球:");
			String blueList = getBetListString(ssqCompoundContent.getBlueList());
			if(blueList.length()>26){
				sb.append(blueList.substring(0,26)).append("<br/>").append(blueList.substring(26,blueList.length()));
			}else{
				sb.append(getBetListString(ssqCompoundContent.getBlueList()));
			}
			
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
