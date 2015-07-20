package com.cai310.lottery.entity.lottery.pl;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.Constant;
import com.cai310.lottery.PlConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dlt.DltCompoundContent;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.support.pl.PlCompoundContent;
import com.cai310.lottery.support.pl.PlPlayType;

/**
 * PL彩票方案
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "PL_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class PlScheme extends NumberScheme {
	private static final long serialVersionUID = -5780839277156063483L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.PL;
	}

	private PlPlayType playType;
	/**
	 * @return the playType
	 */
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.pl.PlPlayType"),
			@Parameter(name = EnumType.TYPE, value = PlPlayType.SQL_TYPE) })
     public PlPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(PlPlayType playType) {
		this.playType = playType;
	}

	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<PlCompoundContent> getCompoundContent() {
		Collection<PlCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(this.getContent()),
				PlCompoundContent.class);
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
		Collection<PlCompoundContent> c = this.getCompoundContent();
		Iterator<PlCompoundContent> it = c.iterator();
		PlCompoundContent plCompoundContent;
		StringBuffer sb = new StringBuffer();
		sb.append(playType.getTypeName()+":<br/>");
		while (it.hasNext()) {
			plCompoundContent = it.next();
			if (PlPlayType.P5Direct.equals(playType)) {
				sb.append("第一位："+getBetListString(plCompoundContent.getArea1List()));
				sb.append("<br/>第二位："+getBetListString(plCompoundContent.getArea2List()));
				sb.append("<br/>第三位："+getBetListString(plCompoundContent.getArea3List()));
				sb.append("<br/>第四位："+getBetListString(plCompoundContent.getArea4List()));
				sb.append("<br/>第五位："+getBetListString(plCompoundContent.getArea5List()));
			} else if (PlPlayType.P3Direct.equals(playType)) {
				sb.append("第一位："+getBetListString(plCompoundContent.getArea1List()));
				sb.append("<br/>第二位："+getBetListString(plCompoundContent.getArea2List()));
				sb.append("<br/>第三位："+getBetListString(plCompoundContent.getArea3List()));
			} else if (PlPlayType.Group3.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getGroup3List()));
			} else if (PlPlayType.Group6.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getGroup6List()));
			} else if (PlPlayType.DirectSum.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getDirectSumList()));
			} else if (PlPlayType.GroupSum.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getGroupSumList()));
			}else if (PlPlayType.BaoChuan.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getBaoChuanList()));
			}else if (PlPlayType.P3DirectKuadu.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getDirectKuaduList()));
			}else if (PlPlayType.P3Group3Kuadu.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getG3KuaduList()));
			}else if (PlPlayType.P3Group6Kuadu.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getG6KuaduList()));
			} else {
				return "";
			}
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	@Override
	public String getCompoundContentDetail(String result) {
		Collection<PlCompoundContent> c = this.getCompoundContent();
		Iterator<PlCompoundContent> it = c.iterator();
		PlCompoundContent plCompoundContent;
		StringBuffer sb = new StringBuffer();
		List<Integer> area1ResultList = new ArrayList<Integer>();
		List<Integer> area2ResultList = new ArrayList<Integer>();
		List<Integer> area3ResultList = new ArrayList<Integer>();
		List<Integer> area4ResultList = new ArrayList<Integer>();
		List<Integer> area5ResultList = new ArrayList<Integer>();
		
		List<Integer> p3ResultList = new ArrayList<Integer>();
		List<Integer> p3SumResultList = new ArrayList<Integer>();
		List<Integer> p3KuaduResultList = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(result)&&result.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
			String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			area1ResultList.add(Integer.valueOf(results[0]));
			area2ResultList.add(Integer.valueOf(results[1]));
			area3ResultList.add(Integer.valueOf(results[2]));
			area4ResultList.add(Integer.valueOf(results[3]));
			area5ResultList.add(Integer.valueOf(results[4]));
			
			Integer num1 = Integer.valueOf(results[0]);
			Integer num2 = Integer.valueOf(results[1]);
			Integer num3 = Integer.valueOf(results[2]);
			p3ResultList.add(num1);
			p3ResultList.add(num2);
			p3ResultList.add(num3);
			
			Integer bigTemp = Math.max(num1, num2);//算出最大的
			Integer bigNum = Math.max(bigTemp, num3);//算出最大的
			Integer smallTemp = Math.min(num1, num2);//算出最小的
			Integer smallNum = Math.min(smallTemp, num3);//算出最小的
			///跨度
			Integer kuadu = bigNum - smallNum ;
			p3KuaduResultList.add(kuadu);
			///和值
			Integer sum = num1 + num2 + num3;
			p3SumResultList.add(sum);
		}
		sb.append(playType.getTypeName()+":<br/>");
		while (it.hasNext()) {
			plCompoundContent = it.next();
			if (PlPlayType.P5Direct.equals(playType)) {
				sb.append("第一位："+getBetListString(plCompoundContent.getArea1List(), area1ResultList));
				sb.append("<br/>第二位："+getBetListString(plCompoundContent.getArea2List(), area2ResultList));
				sb.append("<br/>第三位："+getBetListString(plCompoundContent.getArea3List(), area3ResultList));
				sb.append("<br/>第四位："+getBetListString(plCompoundContent.getArea4List(), area4ResultList));
				sb.append("<br/>第五位："+getBetListString(plCompoundContent.getArea5List(), area5ResultList));
			} else if (PlPlayType.P3Direct.equals(playType)) {
				sb.append("第一位："+getBetListString(plCompoundContent.getArea1List(), area1ResultList));
				sb.append("<br/>第二位："+getBetListString(plCompoundContent.getArea2List(), area2ResultList));
				sb.append("<br/>第三位："+getBetListString(plCompoundContent.getArea3List(), area3ResultList));
			} else if (PlPlayType.Group3.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getGroup3List(), p3ResultList));
			} else if (PlPlayType.Group6.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getGroup6List(), p3ResultList));
			} else if (PlPlayType.DirectSum.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getDirectSumList(), p3SumResultList));
			} else if (PlPlayType.GroupSum.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getGroupSumList(), p3SumResultList));
			}else if (PlPlayType.BaoChuan.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getBaoChuanList(), p3ResultList));
			}else if (PlPlayType.P3DirectKuadu.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getDirectKuaduList(), p3KuaduResultList));
			}else if (PlPlayType.P3Group3Kuadu.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getG3KuaduList(), p3KuaduResultList));
			}else if (PlPlayType.P3Group6Kuadu.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getG6KuaduList(), p3KuaduResultList));
			} else {
				return "";
			}
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	public String getCompoundContentTextForList() {
		Collection<PlCompoundContent> c = this.getCompoundContent();
		Iterator<PlCompoundContent> it = c.iterator();
		PlCompoundContent plCompoundContent;
		StringBuffer sb = new StringBuffer();
		sb.append(playType.getTypeName()+":<br/>");
		if (it.hasNext()) {
			plCompoundContent = it.next();
			if (PlPlayType.P5Direct.equals(playType)) {
				sb.append("第一位："+getBetListString(plCompoundContent.getArea1List()));
				sb.append("<br/>第二位："+getBetListString(plCompoundContent.getArea2List()));
				sb.append("<br/>第三位："+getBetListString(plCompoundContent.getArea3List()));
				sb.append("<br/>第四位："+getBetListString(plCompoundContent.getArea4List()));
				sb.append("<br/>第五位："+getBetListString(plCompoundContent.getArea5List()));
			} else if (PlPlayType.P3Direct.equals(playType)) {
				sb.append("第一位："+getBetListString(plCompoundContent.getArea1List()));
				sb.append("<br/>第二位："+getBetListString(plCompoundContent.getArea2List()));
				sb.append("<br/>第三位："+getBetListString(plCompoundContent.getArea3List()));
			} else if (PlPlayType.Group3.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getGroup3List()));
			} else if (PlPlayType.Group6.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getGroup6List()));
			} else if (PlPlayType.DirectSum.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getDirectSumList()));
			} else if (PlPlayType.GroupSum.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getGroupSumList()));
			}else if (PlPlayType.BaoChuan.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getBaoChuanList()));
			}else if (PlPlayType.P3DirectKuadu.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getDirectKuaduList()));
			}else if (PlPlayType.P3Group3Kuadu.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getG3KuaduList()));
			}else if (PlPlayType.P3Group6Kuadu.equals(playType)) {
				sb.append(getBetListString(plCompoundContent.getG6KuaduList()));
			} else {
				return "";
			}
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
		}
		if (it.hasNext()) {
			sb.append(Constant.SCHEME_SEPARATOR_HTML);
			sb.append("...");
		}
		return sb.toString().length() == 0 ? null : sb.toString().trim();
	}
	@Transient
	@Override
	public Byte getLotteryPlayType() {
		if (getPlayType() == PlPlayType.P5Direct)
			return 1;
		else
			return 0;
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
	//-------------------------
	
}
