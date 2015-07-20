package com.cai310.lottery.entity.lottery.zc;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.zc.CombinationBean;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.SfzcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.utils.JsonUtil;

/**
 * 胜负足彩方案实体类.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SFZC_SCHEME")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class SfzcScheme extends Scheme {
	private static final long serialVersionUID = -831961259646415981L;

	/**
	 * 玩法类型
	 * 
	 * @see com.cai310.lottery.support.zc.PlayType
	 */
	private PlayType playType;

	/** 额外的开奖信息 */
	private String extraPrizeMsg;

	/**
	 * @return {@link #playType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.zc.PlayType"),
			@Parameter(name = EnumType.TYPE, value = PlayType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the {@link #playType} to set
	 */
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.SFZC;
	}

	/**
	 * @return {@link #extraPrizeMsg}
	 */
	@Lob
	@Column(insertable = false)
	public String getExtraPrizeMsg() {
		return extraPrizeMsg;
	}

	/**
	 * @param extraPrizeMsg the {@link #extraPrizeMsg} to set
	 */
	public void setExtraPrizeMsg(String extraPrizeMsg) {
		this.extraPrizeMsg = extraPrizeMsg;
	}

	/**
	 * 返回复式内容解析后的投注项数组
	 * 
	 * @return
	 * @throws DataException
	 */
	@Transient
	public SfzcCompoundItem[] getCompoundContent() throws DataException {
		if (this.getContent() == null) {
			throw new DataException("方案内容获取为空.");
		}
		String[] itemStrs = null;
		if (playType == PlayType.SFZC9) {
			itemStrs = this.getContent().split(String.valueOf(ZcUtils.getDanMinHitContentSpiltCode()))[1].split(String
					.valueOf(ZcUtils.getContentSpiltCode()));
		} else {
			itemStrs = this.getContent().split(String.valueOf(ZcUtils.getContentSpiltCode()));
		}
		if (itemStrs.length != ZcUtils.getMatchCount(this.getLotteryType())) {
			throw new DataException("方案内容选项个数异常.");
		}
		SfzcCompoundItem[] items = new SfzcCompoundItem[itemStrs.length];
		for (int i = 0; i < itemStrs.length; i++) {
			items[i] = new SfzcCompoundItem(Byte.valueOf(itemStrs[i]).byteValue(), i);
		}
		return items;
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
			try {
				return getContentString();
			} catch (DataException e) {
				e.printStackTrace();
				return "读取方案错误";
			}
		} else {
			return this.getContent();
		}
	}

	/**
	 * 获取方案显示的方案内容
	 * 
	 * @return
	 * @throws DataException
	 */
	@Transient
	public String getContentString() throws DataException {
		if (this.getMode() == SalesMode.SINGLE) {
			return "----------";
		}
		StringBuilder sb = new StringBuilder();
		for (SfzcCompoundItem item : this.getCompoundContent()) {
			sb.append(item.toBetString()).append("-");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 返回方案最小命中率 主要针对九场模糊设胆
	 * 
	 * @return
	 * @throws DataException
	 */
	@Transient
	public int getDanMinHit() throws DataException {
		int danMinHit = -1;
		if (this.getContent() == null) {
			throw new DataException("方案内容获取为空.");
		}
		if(this.getContent().indexOf(ZcUtils.getDanMinHitContentSpiltCode())!=-1){
			if (!StringUtils.isEmpty(this.getContent().split(String.valueOf(ZcUtils.getDanMinHitContentSpiltCode()))[0])) {
				danMinHit = Integer.valueOf(
						this.getContent().split(String.valueOf(ZcUtils.getDanMinHitContentSpiltCode()))[0]).intValue();
			}
		}
		return danMinHit;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transient
	public Map<String, CombinationBean> getWonCombinationMap() {
		if (StringUtils.isBlank(this.getExtraPrizeMsg()))
			return null;
		Map classMap = new HashMap();
		classMap.put("items", SfzcCompoundItem.class);
		return JsonUtil.getMap4Json(this.getExtraPrizeMsg(), CombinationBean.class);
	}

	@Transient
	@Override
	public Byte getLotteryPlayType() {
		return (byte) getPlayType().ordinal();
	}
	@Transient
	@Override
	public Byte getPlayTypeOrdinal() {
		return (byte) getPlayType().ordinal();
	}
	@Transient
	public String getCompoundContentTextForList() throws DataException {
		StringBuilder sb = new StringBuilder();
		for (SfzcCompoundItem item : this.getCompoundContent()) {
			sb.append(item.toBetString()).append("-");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
