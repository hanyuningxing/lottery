package com.cai310.lottery.support.jclq;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.Constant;
import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.utils.CommonUtil;

public class JclqPrizeItem implements Serializable {
	private static final long serialVersionUID = 849706025155315832L;

	/** 乘号字符 */
	public static final char multiply = '×';
	public static final NumberFormat CANCEL_SP_FORMAT = new DecimalFormat("0");
	/** 金额 格式化对象 */
	public static final NumberFormat MONEY_FORMAT = new DecimalFormat("0.00");
	
	/** SP值 格式化对象 */
	public static final NumberFormat SP_FORMAT = new DecimalFormat("0.00");
	// ==========================================================

	/** 开奖结果 */
	private final Map<String, JclqResult> resultMap;

	/** 命中的场次组合 */
	private final List<JclqMatchItem> correctCombList;
	/** 倍数 */
	private final Integer multiple;
	// ==========================================================

	/**
	 * @param correctCombList
	 */
	public JclqPrizeItem(Map<String, JclqResult> resultMap, List<JclqMatchItem> correctCombList,Integer multiple) {
		super();
		this.resultMap = resultMap;
		this.correctCombList = correctCombList;
		this.multiple = multiple;
		build();
	}

	// ==========================================================

	/** 中奖详情 */
	private StringBuilder lineWonDetail = new StringBuilder();

	/** 奖金详情 */
	private StringBuilder linePrizeDetail = new StringBuilder();

	/** 税前奖金 */
	private double prize;

	/** 税后奖金 */
	private double prizeAfterTax;

	// ==========================================================

	private void build() {
		String passText = JclqConstant.PASS_TEXT[correctCombList.size() - 1];
		lineWonDetail.append(passText).append(':');
		linePrizeDetail.append(passText).append(':');

		prize = 1.0d;// 税前奖金
		boolean isOnlyOneMatch = correctCombList.size() == 1;// 是否单场（单关）
		boolean refundment = false;// 是否退款
		for (JclqMatchItem item : correctCombList) {
			JclqResult result = resultMap.get(item.getMatchKey());
			lineWonDetail.append('[').append(result.getMatchKey()).append(']');
			linePrizeDetail.append('[').append(result.getMatchKey()).append(']');

			double spValue;
			if (result.isCancel()) {
				int selectedCount = item.selectedCount();
				spValue = selectedCount * JclqConstant.CANCEL_MATCH_RESULT_SP;

				StringBuilder spText = new StringBuilder();
				for (int i = 0; i < selectedCount; i++) {
					spText.append(CANCEL_SP_FORMAT.format(JclqConstant.CANCEL_MATCH_RESULT_SP)).append('+');
				}
				spText.deleteCharAt(spText.length() - 1);// 删除最后一个'+'

				if (selectedCount > 1) {
					linePrizeDetail.append('(').append(spText).append(')');
				} else {
					linePrizeDetail.append(spText);
				}
				if (isOnlyOneMatch)
					refundment = true;
			} else {
				spValue = result.getResultSp();
				linePrizeDetail.append(SP_FORMAT.format(spValue));
			}
			prize *= spValue;
			linePrizeDetail.append(multiply);
		}
		linePrizeDetail.deleteCharAt(linePrizeDetail.length() - 1);// 删除最后一个乘号
		// 总帐
		if (refundment) {
			prize *= 2.0d;
			linePrizeDetail.append(multiply).append("2元(退款)=");
		} else {
			prize *= 2.0d;
			prize = CommonUtil.bankRoundPrize(prize);
			// 倍数
			prize*=multiple;
			linePrizeDetail.append(multiply).append(multiple).append("倍");
			// 倍数
			linePrizeDetail.append(multiply).append("2元").append("=");
		}
		linePrizeDetail.append(MONEY_FORMAT.format(prize)).append("元");

		if (!refundment) {
			prizeAfterTax = Constant.countPrizeAfterTax(prize);
			if (prizeAfterTax < prize)
				linePrizeDetail.append("(税后").append(MONEY_FORMAT.format(prizeAfterTax)).append("元)");
		} else {
			prizeAfterTax = prize;
		}

		// 补足2元
		if (prize < 2.0d && isOnlyOneMatch) {
			prize = 2.0d;
			linePrizeDetail.append("(补足2元)");
		}
		if (prizeAfterTax < 2.0d && isOnlyOneMatch) {
			prizeAfterTax = 2.0d;
		}
	}

	// ==========================================================

	/**
	 * @return {@link #lineWonDetail}
	 */
	public StringBuilder getLineWonDetail() {
		return lineWonDetail;
	}

	/**
	 * @return {@link #linePrizeDetail}
	 */
	public StringBuilder getLinePrizeDetail() {
		return linePrizeDetail;
	}

	/**
	 * @return {@link #prize}
	 */
	public double getPrize() {
		return prize;
	}

	/**
	 * @return {@link #prizeAfterTax}
	 */
	public double getPrizeAfterTax() {
		return prizeAfterTax;
	}

}
