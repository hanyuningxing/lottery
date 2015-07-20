package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.zc.LczcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;

/**
 * 六场半全场足彩方案实体类.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "LCZC_SCHEME")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class LczcScheme extends Scheme {
	private static final long serialVersionUID = -831961259646415981L;

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.LCZC;
	}

	/**
	 * 返回复式内容解析后的投注项数组
	 * 
	 * @return
	 * @throws DataException
	 */
	@Transient
	public LczcCompoundItem[] getCompoundContent() throws DataException {
		if (this.getContent() == null) {
			throw new DataException("方案内容获取为空.");
		}
		String[] itemStrs = null;

		itemStrs = this.getContent().split(String.valueOf(ZcUtils.getContentSpiltCode()));

		if (itemStrs.length != ZcUtils.getMatchCount(this.getLotteryType()) * 2) {
			throw new DataException("方案内容选项个数异常.");
		}
		LczcCompoundItem[] items = new LczcCompoundItem[itemStrs.length];
		for (int i = 0; i < itemStrs.length; i++) {
			items[i] = new LczcCompoundItem(Byte.valueOf(itemStrs[i]).byteValue());
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
		for (LczcCompoundItem item : this.getCompoundContent()) {
			sb.append(item.toBetString()).append("-");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	@Transient
	public String getCompoundContentTextForList() throws DataException {
		StringBuilder sb = new StringBuilder();
		for (LczcCompoundItem item : this.getCompoundContent()) {
			sb.append(item.toBetString()).append("-");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

}
