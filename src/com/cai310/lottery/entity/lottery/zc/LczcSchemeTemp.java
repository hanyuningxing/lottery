package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.PolymorphismType;

import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.zc.LczcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;


@Entity
@org.hibernate.annotations.Entity(polymorphism = PolymorphismType.EXPLICIT)
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SCHEME_TEMP")
public class LczcSchemeTemp extends SchemeTemp {

	private static final long serialVersionUID = 7225059047153362375L;
	
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
		if (itemStrs.length != ZcUtils.getMatchCount(this.getLotteryType())) {
			throw new DataException("方案内容选项个数异常.");
		}
		LczcCompoundItem[] items = new LczcCompoundItem[itemStrs.length];
		for (int i = 0; i < itemStrs.length; i++) {
			items[i] = new LczcCompoundItem(Byte.valueOf(itemStrs[i]).byteValue());
		}
		return items;
	}

}
