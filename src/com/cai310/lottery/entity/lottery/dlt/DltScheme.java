package com.cai310.lottery.entity.lottery.dlt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.Constant;
import com.cai310.lottery.DltConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dlt.DltCompoundContent;
import com.cai310.lottery.support.dlt.DltPlayType;

/**
 * 双色球彩票方案
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "DLT_SCHEME")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class DltScheme extends NumberScheme {
	private static final long serialVersionUID = -5780839277156063483L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.DLT;
	}

	private DltPlayType playType;

	/**
	 * @return the playType
	 */

	@Column(nullable = false)
	public DltPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(DltPlayType playType) {
		this.playType = playType;
	}

	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<DltCompoundContent> getCompoundContent() {
		Collection<DltCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				DltCompoundContent.class);
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
		Collection<DltCompoundContent> c = this.getCompoundContent();
		Iterator<DltCompoundContent> it = c.iterator();
		DltCompoundContent dltCompoundContent;
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			dltCompoundContent = it.next();
			if (DltPlayType.General.equals(playType)||DltPlayType.GeneralAdditional.equals(playType)) {
				sb.append("<span class=\"rebchar\">前区:");
				if (StringUtils.isNotBlank(getBetListString(dltCompoundContent.getRedDanList()))) {
					sb.append("(" + getBetListString(dltCompoundContent.getRedDanList()) + ")");
				}
				sb.append(getBetListString(dltCompoundContent.getRedList()));
				sb.append("</span>");
				sb.append("<br/>");
				sb.append("<span class=\"bluechar\">后区:");
				if (StringUtils.isNotBlank(getBetListString(dltCompoundContent.getBlueDanList()))) {
					sb.append("(" + getBetListString(dltCompoundContent.getBlueDanList()) + ")");
				}
				sb.append(getBetListString(dltCompoundContent.getBlueList()));
				sb.append("</span>");
			} else if (DltPlayType.Select12to2.equals(playType)) {
				sb.append("<span class=\"bluechar\">生肖乐:");
				if (StringUtils.isNotBlank(getBetListString(dltCompoundContent.getBlueDanList()))) {
					sb.append("(" + getBetListString(dltCompoundContent.getBlueDanList()) + ")");
				}
				sb.append(getBetListString(dltCompoundContent.getBlueList()));
				sb.append("</span>");
				
			}
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	public String getCompoundContentTextForList() {
		Collection<DltCompoundContent> c = this.getCompoundContent();
		Iterator<DltCompoundContent> it = c.iterator();
		DltCompoundContent dltCompoundContent;
		StringBuffer sb = new StringBuffer();
		if (it.hasNext()) {
			dltCompoundContent = it.next();
			if (DltPlayType.General.equals(playType)||DltPlayType.GeneralAdditional.equals(playType)) {
				sb.append("<span class=\"rebchar\">前区:");
				if (StringUtils.isNotBlank(getBetListString(dltCompoundContent.getRedDanList()))) {
					sb.append("(" + getBetListString(dltCompoundContent.getRedDanList()) + ")");
				}
				sb.append(getBetListString(dltCompoundContent.getRedList()));
				sb.append("</span>");
				sb.append("<br/>");
				sb.append("<span class=\"bluechar\">后区:");
				if (StringUtils.isNotBlank(getBetListString(dltCompoundContent.getBlueDanList()))) {
					sb.append("(" + getBetListString(dltCompoundContent.getBlueDanList()) + ")");
				}
				sb.append(getBetListString(dltCompoundContent.getBlueList()));
				sb.append("</span>");
			} else if (DltPlayType.Select12to2.equals(playType)) {
				sb.append("<span class=\"bluechar\">生肖乐:");
				if (StringUtils.isNotBlank(getBetListString(dltCompoundContent.getBlueDanList()))) {
					sb.append("(" + getBetListString(dltCompoundContent.getBlueDanList()) + ")");
				}
				sb.append(getBetListString(dltCompoundContent.getBlueList()));
				sb.append("</span>");
				
			}
		}
		if (it.hasNext()) {
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
			sb.append("...");
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	
	@Transient
	@Override
	public String getCompoundContentDetail(String result) {
		Collection<DltCompoundContent> c = this.getCompoundContent();
		Iterator<DltCompoundContent> it = c.iterator();
		DltCompoundContent dltCompoundContent;
		StringBuffer sb = new StringBuffer();
		List<Integer> redResultList = new ArrayList<Integer>();
		List<Integer> buleResultList = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(result)&&result.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
			String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			String[] redResults = (String[]) ArrayUtils.subarray(results, 0, 5);
			String[] buleResults= (String[]) ArrayUtils.subarray(results, 5, 7);
			for (String num : redResults) {
				redResultList.add(Integer.valueOf(num));
			}
			for (String num : buleResults) {
				buleResultList.add(Integer.valueOf(num));
			}
		}
		while (it.hasNext()) {
			dltCompoundContent = it.next();
			if (DltPlayType.General.equals(playType)||DltPlayType.GeneralAdditional.equals(playType)) {
				sb.append("<span class=\"rebchar\">前区:");
				if (StringUtils.isNotBlank(getBetListString(dltCompoundContent.getRedDanList()))) {
					sb.append("(" + getBetListString(dltCompoundContent.getRedDanList(),redResultList) + ")");
				}
				sb.append(getBetListString(dltCompoundContent.getRedList(),redResultList));
				sb.append("</span>");
				sb.append("<br/>");
				sb.append("<span class=\"bluechar\">后区:");
				if (StringUtils.isNotBlank(getBetListString(dltCompoundContent.getBlueDanList()))) {
					sb.append("(" + getBetListString(dltCompoundContent.getBlueDanList(),buleResultList) + ")");
				}
				sb.append(getBetListString(dltCompoundContent.getBlueList(),buleResultList));
				sb.append("</span>");
			} else if (DltPlayType.Select12to2.equals(playType)) {
				sb.append("<span class=\"bluechar\">生肖乐:");
				if (StringUtils.isNotBlank(getBetListString(dltCompoundContent.getBlueDanList()))) {
					sb.append("(" + getBetListString(dltCompoundContent.getBlueDanList(),buleResultList) + ")");
				}
				sb.append(getBetListString(dltCompoundContent.getBlueList(),buleResultList));
				sb.append("</span>");
				
			}
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
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
