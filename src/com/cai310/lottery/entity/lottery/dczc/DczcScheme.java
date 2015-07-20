package com.cai310.lottery.entity.lottery.dczc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.CombinationBean;
import com.cai310.lottery.support.dczc.DczcCompoundContent;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.DczcSingleContent;
import com.cai310.lottery.support.dczc.PassMode;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.orm.Pagination;
import com.cai310.utils.CombCallBack;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MathUtils;

/**
 * 单场方案实体类.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "DCZC_SCHEME")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_SCHEME_CACHE_REGION)
public class DczcScheme extends Scheme {
	private static final long serialVersionUID = -831961259646415981L;

	/** 过关方式 */
	private int passType;

	/**
	 * 玩法类型
	 * 
	 * @see com.cai310.lottery.support.dczc.PlayType
	 */
	private PlayType playType;

	/**
	 * 过关模式
	 * 
	 * @see com.cai310.lottery.support.dczc.PassMode
	 * */
	private PassMode passMode;

	/** 额外的开奖信息 */
	private String extraPrizeMsg;

	/**
	 * @return {@link #playType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.dczc.PlayType"),
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

	/**
	 * @return {@link #passType}
	 */
	@Column(nullable = false, updatable = false)
	public int getPassType() {
		return passType;
	}

	/**
	 * @param passType the {@link #passType} to set
	 */
	public void setPassType(int passType) {
		this.passType = passType;
	}

	/**
	 * @return {@link #passMode}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.dczc.PassMode"),
			@Parameter(name = EnumType.TYPE, value = PassMode.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public PassMode getPassMode() {
		return passMode;
	}

	/**
	 * @param passMode the {@link #passMode} to set
	 */
	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
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

	@Transient
	public List<PassType> getPassTypes() {
		return PassType.getPassTypes(passType);
	}

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.DCZC;
	}

	@Override
	public String toPrintString() {
		return this.getContent();
	}

	@SuppressWarnings("unchecked")
	@Transient
	public DczcCompoundContent getCompoundContent() {
		if (StringUtils.isBlank(this.getContent()))
			return null;

		Map classMap = new HashMap();
		classMap.put("items", DczcMatchItem.class);
		return JsonUtil.getObject4JsonString(this.getContent(), DczcCompoundContent.class, classMap);
	}

	@Transient
	public DczcSingleContent getSingleContent() {
		if (StringUtils.isBlank(this.getContent()))
			return null;

		return JsonUtil.getObject4JsonString(this.getContent(), DczcSingleContent.class);
	}

	@Transient
	protected Map<String, String> getWonCombinationMap() {
		if (StringUtils.isBlank(this.getExtraPrizeMsg()))
			return null;

		Map<String, String> map = new HashMap<String, String>();
		String[] arr = this.getExtraPrizeMsg().split(";");
		for (String str : arr) {
			String[] arr2 = str.split(":");
			map.put(arr2[0], arr2[1]);
		}
		return map;
	}

	@Transient
	public Pagination getCombinations(Pagination pagination) {
		switch (this.getMode()) {
		case COMPOUND:
			return getCombinationsOfCOMPOUND(pagination);
		case SINGLE:
			return getCombinationsOfSINGLE(pagination);
		default:
			throw new RuntimeException("投注方式不合法");
		}
	}

	@Transient
	public Pagination getCombinationsOfCOMPOUND(final Pagination pagination) {
		final Map<String, String> wonCombinationMap = getWonCombinationMap();

		DczcCompoundContent compoundContent = getCompoundContent();
		final List<DczcMatchItem> danItemList = new ArrayList<DczcMatchItem>();
		final List<DczcMatchItem> unDanItemList = new ArrayList<DczcMatchItem>();
		for (DczcMatchItem item : compoundContent.getItems()) {
			if (item.isDan())
				danItemList.add(item);
			else
				unDanItemList.add(item);
		}

		final int danSize = danItemList.size();
		final int unDanSize = unDanItemList.size();

		int danMinHit;// 胆码最小命中数
		if (compoundContent.getDanMinHit() != null)
			danMinHit = compoundContent.getDanMinHit();
		else
			danMinHit = danSize;

		int danMaxHit;// 胆码最大命中数
		if (compoundContent.getDanMaxHit() != null)
			danMaxHit = compoundContent.getDanMaxHit();
		else
			danMaxHit = danSize;

		final List<CombinationBean> combinationBeanList = new ArrayList<CombinationBean>();
		List<PassType> passTypes = this.getPassTypes();
		final int[] countArr = { 0 };
		for (final PassType passType : passTypes) {// 循环所选的各个过关方式
			final int n = passType.getMatchCount();// 此过关方式所需要的场次数目
			int danEnd = Math.min(n, danMaxHit);
			for (int danHit = danMinHit; danHit <= danEnd; danHit++) {
				MathUtils.efficientComb(danSize, danHit, new CombCallBack() {

					public boolean callback(boolean[] comb, int m) {
						final List<DczcMatchItem> danCombList = new ArrayList<DczcMatchItem>();
						int pos = 0;
						for (int i = 0; i < danSize; i++) {
							if (comb[i] == true) {
								danCombList.add(danItemList.get(i));
								pos++;
								if (pos == m)
									break;
							}
						}

						MathUtils.efficientComb(unDanSize, n - m, new CombCallBack() {
							public boolean callback(boolean[] comb2, int m2) {
								if (countArr[0] >= pagination.getFirst()) {
									if (pagination == null || pagination.getPageSize() <= 0
											|| pagination.getPageSize() > combinationBeanList.size()) {
										List<DczcMatchItem> unDanCombList = new ArrayList<DczcMatchItem>();
										int pos2 = 0;
										for (int i = 0; i < unDanSize; i++) {
											if (comb2[i] == true) {
												unDanCombList.add(unDanItemList.get(i));
												pos2++;
												if (pos2 == m2)
													break;
											}
										}

										List<DczcMatchItem> combList = new ArrayList<DczcMatchItem>();
										combList.addAll(danCombList);
										combList.addAll(unDanCombList);
										Collections.sort(combList);// 按lineId排序

										CombinationBean combinationBean = new CombinationBean();
										combinationBean.setPassTypeOrdinal(passType.ordinal());
										combinationBean.setItems(combList);
										if (wonCombinationMap != null) {
											String prizeMsg = wonCombinationMap.get(combinationBean.generateKey());
											if (StringUtils.isNotBlank(prizeMsg)) {
												String[] prizeArr = prizeMsg.split("_");
												combinationBean.setPrize(Double.valueOf(prizeArr[0]));
												combinationBean.setPrize(Double.valueOf(prizeArr[1]));
											}
										}
										combinationBeanList.add(combinationBean);
									}
								}

								countArr[0] += 1;
								return false;
							}
						});

						return false;
					}

				});
			}
		}
		pagination.setTotalCount(countArr[0]);
		pagination.setResult(combinationBeanList);
		return pagination;
	}

	@Transient
	public Pagination getCombinationsOfSINGLE(final Pagination pagination) {
		PassType passType = getPassTypes().get(0);
		List<CombinationBean> combinationBeanList = new ArrayList<CombinationBean>();
		Map<String, String> wonCombinationMap = getWonCombinationMap();
		DczcSingleContent singleContent = getSingleContent();
		String[] contentArr = singleContent.getContent().split("\r\n");
		int end;
		if (pagination.getPageSize() > 0) {
			end = pagination.getFirst() + pagination.getPageSize();
			if (end > contentArr.length)
				end = contentArr.length;
		} else {
			end = contentArr.length;
		}
		for (int i = pagination.getFirst(); i < end; i++) {
			String content = contentArr[i];
			String[] ordinalArr = content.split(",");

			List<DczcMatchItem> itemList = new ArrayList<DczcMatchItem>();
			for (int j = 0; j < ordinalArr.length; j++) {
				String ordinalStr = ordinalArr[j];
				if ("#".equals(ordinalStr))
					continue;

				int ordinal = Integer.parseInt(ordinalStr);

				int lineId = singleContent.getLineIds().get(j);
				DczcMatchItem item = new DczcMatchItem();
				item.setLineId(lineId);
				item.setValue(1 << ordinal);
				itemList.add(item);
			}
			Collections.sort(itemList);

			CombinationBean comb = new CombinationBean();
			comb.setPassTypeOrdinal(passType.ordinal());
			comb.setItems(itemList);
			if (wonCombinationMap != null) {
				String prizeMsg = wonCombinationMap.get(comb.generateKey());
				if (StringUtils.isNotBlank(prizeMsg)) {
					String[] prizeArr = prizeMsg.split("_");
					comb.setPrize(Double.valueOf(prizeArr[0]));
					comb.setPrize(Double.valueOf(prizeArr[1]));
				}
			}
			combinationBeanList.add(comb);
		}

		pagination.setTotalCount(contentArr.length);
		pagination.setResult(combinationBeanList);
		return pagination;
	}

	/**
	 * @return 获取选择的场次序号集合
	 */
	@Transient
	public List<Integer> getSelectedLineIds() {
		switch (this.getMode()) {
		case COMPOUND:
			DczcCompoundContent compoundContent = getCompoundContent();
			List<Integer> lineIds = new ArrayList<Integer>();
			for (DczcMatchItem item : compoundContent.getItems()) {
				lineIds.add(item.getLineId());
			}
			return lineIds;
		case SINGLE:
			DczcSingleContent singleContent = getSingleContent();
			return singleContent.getLineIds();
		default:
			throw new RuntimeException("投注方式不合法");
		}
	}

	@Transient
	public String getContentText() {
		Item[] allItem = getPlayType().getAllItems();

		final String seq = "\r\n";
		final char seq2 = ',';

		StringBuilder sb = new StringBuilder();

		sb.append("玩法类型：").append(getPlayType().getText()).append(seq);
		sb.append("过关模式：").append(getPassMode().getText()).append(seq);

		List<PassType> passTypes = getPassTypes();
		sb.append("过关方式：");
		for (PassType passType : passTypes) {
			sb.append(passType.getText()).append(seq2);
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(seq);

		if (isCompoundMode()) {
			DczcCompoundContent content = getCompoundContent();
			if ((content.getDanMinHit() != null && content.getDanMinHit() > 0)
					|| (content.getDanMaxHit() != null && content.getDanMaxHit() > 0)) {
				sb.append("模糊设胆：");
				if (content.getDanMinHit() != null && content.getDanMinHit() > 0)
					sb.append("胆码至少中").append(content.getDanMinHit()).append("场").append(seq2);
				if (content.getDanMaxHit() != null && content.getDanMaxHit() > 0)
					sb.append("胆码至多中").append(content.getDanMaxHit()).append("场").append(seq2);
				sb.deleteCharAt(sb.length() - 1);
				sb.append(seq);
			}
			for (DczcMatchItem matchItem : content.getItems()) {
				if (matchItem.isDan())
					sb.append("[胆]-");
				sb.append("第[").append(matchItem.getLineId() + 1).append("]场：");
				for (Item item : allItem) {
					if ((matchItem.getValue() & (1 << item.ordinal())) > 0) {
						sb.append(item.getText()).append(seq2);
					}
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(seq);
			}
		} else {
			DczcSingleContent singleContent = getSingleContent();
			String[] contentArr = singleContent.getContent().split("\r\n");
			for (String content : contentArr) {
				String[] ordinalArr = content.split(",");
				for (int i = 0; i < ordinalArr.length; i++) {
					String ordinalStr = ordinalArr[i];
					if (!"#".equals(ordinalStr)) {
						int ordinal = Integer.parseInt(ordinalStr);
						Item item = allItem[ordinal];
						sb.append("[").append(singleContent.getLineIds().get(i) + 1).append("]").append(item.getText())
								.append(seq2);
					}
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(seq);
			}
		}
		return sb.toString();
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
	public String getPassTypeText() {
		return PassType.getPassTypeText(this.getPassType());
	}
}
