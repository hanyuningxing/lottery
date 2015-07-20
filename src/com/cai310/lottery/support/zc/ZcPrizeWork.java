package com.cai310.lottery.support.zc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.lottery.entity.lottery.zc.ZcPeriodData;
import com.cai310.lottery.support.PrizeItem;
import com.cai310.lottery.support.WinItem;

public class ZcPrizeWork implements Serializable {
	private static final long serialVersionUID = -2777503396145258079L;

	// ==========================================================
	private ZcPeriodData periodData;
	private PlayType playType;
	private int lost0;
	private int lost1;
	private int lost2;
	private int lost3;
	private boolean won = false;
	private List<PrizeItem> prizeItems = new ArrayList<PrizeItem>();
	private List<WinItem> winItems = new ArrayList<WinItem>();

	/**
	 * @param correctCombList
	 */
	public ZcPrizeWork(ZcPeriodData periodData, PlayType playType, int lost0, int lost1, int lost2, int lost3) {
		super();
		this.periodData = periodData;
		this.playType = playType;
		this.lost0 = lost0;
		this.lost1 = lost1;
		this.lost2 = lost2;
		this.lost3 = lost3;
		build();
	}

	// ==========================================================

	/** 中奖详情 */
	private StringBuilder wonDetail = new StringBuilder();

	/** 奖金详情 */
	private StringBuilder prizeDetail = new StringBuilder();

	/** 税前奖金 */
	private double prize;

	/** 税后奖金 */
	private double prizeAfterTax;

	// ==========================================================

	private void build() {

		WinItem winItem = null;
		if (this.periodData instanceof SfzcPeriodData) {// 胜负足彩
			SfzcPeriodData sfpd = (SfzcPeriodData) this.periodData;
			BigDecimal utilPrizeUtil = new BigDecimal(0);
			if (this.lost0 > 0) {
				if (playType == PlayType.SFZC14) {
					utilPrizeUtil = BigDecimal.valueOf(sfpd.getFirstPrize_14());
				} else {
					utilPrizeUtil = BigDecimal.valueOf(sfpd.getFirstPrize());
				}
				winItem = new WinItem("一等奖", this.lost0);
				this.winItems.add(winItem);
				this.prizeItems.add(new PrizeItem(winItem, utilPrizeUtil));

				this.won = true;
			}

			if (playType == PlayType.SFZC14 && this.lost1 > 0) {// 14场二等奖

				winItem = new WinItem("二等奖", this.lost1);
				this.winItems.add(winItem);
				this.prizeItems.add(new PrizeItem(winItem, BigDecimal.valueOf(sfpd.getSecondPrize_14())));

				this.won = true;
			}
		} else {// 其他彩种
			if (this.lost0 > 0) {

				winItem = new WinItem("一等奖", this.lost0);
				this.winItems.add(winItem);
				this.prizeItems.add(new PrizeItem(winItem, BigDecimal.valueOf(this.periodData.getFirstPrize())));

				this.won = true;
			}
		}
	}

	// ==========================================================

	/**
	 * @return {@link #lineWonDetail}
	 */
	public StringBuilder getWonDetail() {
		return wonDetail;
	}

	/**
	 * @return {@link #linePrizeDetail}
	 */
	public StringBuilder getPrizeDetail() {
		return prizeDetail;
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

	public int getLost0() {
		return lost0;
	}

	public int getLost1() {
		return lost1;
	}

	public int getLost2() {
		return lost2;
	}

	public int getLost3() {
		return lost3;
	}

	public boolean isWon() {
		return won;
	}

	public List<PrizeItem> getPrizeItems() {
		return prizeItems;
	}

	public List<WinItem> getWinItems() {
		return winItems;
	}

}
