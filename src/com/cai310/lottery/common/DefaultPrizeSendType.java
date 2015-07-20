package com.cai310.lottery.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.utils.LocalUtils;

/**
 * 派发奖金规则类
 */
public class DefaultPrizeSendType implements PrizeSendType {

	private Scheme scheme;

	/** 方案奖金 */
	private BigDecimal schemePrize;

	/** 方案是否盈利 */
	private Boolean profit;

	/** 方案发起人佣金率 */
	private BigDecimal organigerDeductRate;

	/** 方案发起人佣金提成 */
	private BigDecimal organigerDeduct;

	/** 网站提成率 */
	private BigDecimal companyDeductRate;

	/** 网站提成 */
	private BigDecimal companyDeduct;

	/** 所有提成总金额 */
	private BigDecimal totalDeduct;

	/** 扣除所有提成后的可分配金额 */
	private BigDecimal totalReturn;

	private static final String DEFAULT_TEMPLATE_KEY = "template.scheme.cutDesc";
	private static final NumberFormat percentFormat = new DecimalFormat("0%");
	private static final NumberFormat moneyFormat = new DecimalFormat("0.00");

	// *****************************************************************************************

	public DefaultPrizeSendType(Scheme scheme) {
		this.scheme = scheme;
		init();
	}

	public void setscheme(Scheme scheme) {
		this.scheme = scheme;
		init();
	}

	/**
	 * 初始化，计算奖金分配
	 */
	private void init() {
		schemePrize = scheme.getPrizeAfterTax() == null ? BigDecimal.ZERO : scheme.getPrizeAfterTax();
		profit = BigDecimalUtil.valueOf(scheme.getSchemeCost()).compareTo(schemePrize) < 0;// 是否盈利

		// 计算方案发起人佣金率
		switch (scheme.getShareType()) {
		case TOGETHER:// 合买
			if (profit && scheme.getCommissionRate() != null && scheme.getCommissionRate() > 0) {// 方案盈利才可以提成
				BigDecimal commission = BigDecimalUtil.valueOf(scheme.getCommissionRate());
				organigerDeductRate = commission;
			} else {
				organigerDeductRate = BigDecimal.ZERO;
			}
			break;
		case SELF:// 自购
			organigerDeductRate = BigDecimal.ZERO;
			break;
		}
		// 计算方案发起人佣金
		organigerDeduct = BigDecimalUtil.multiply(schemePrize, organigerDeductRate);

		// 计算网站提成
		companyDeductRate = BigDecimal.ZERO;// 网站提成率
		companyDeduct = BigDecimalUtil.multiply(schemePrize, companyDeductRate);

		totalDeduct = organigerDeduct.add(companyDeduct);// 总提成金额：发起人佣金提成+网站提成

		totalReturn = schemePrize.subtract(totalDeduct);// 扣除所有提成后的可分配金额
	}

	// *****************************************************************************************

	/**
	 * @see com.betbase.common.PrizeCutType#getSchemePrize()
	 */
	public BigDecimal getSchemePrize() {
		return schemePrize;
	}

	/**
	 * @see com.betbase.common.PrizeCutType#getProfit()
	 */
	public boolean getProfit() {
		return profit.booleanValue();
	}

	/**
	 * @see com.betbase.common.PrizeCutType#getOrganigerDeductRate()
	 */
	public BigDecimal getOrganigerDeductRate() {
		return organigerDeductRate;
	}

	/**
	 * @see com.betbase.common.PrizeCutType#getOrganigerDeduct()
	 */
	public BigDecimal getOrganigerDeduct() {
		return organigerDeduct;
	}

	/**
	 * @see com.betbase.common.PrizeCutType#getCompanyDeductRate()
	 */
	public BigDecimal getCompanyDeductRate() {
		return companyDeductRate;
	}

	/**
	 * @see com.betbase.common.PrizeCutType#getCompanyDeduct()
	 */
	public BigDecimal getCompanyDeduct() {
		return companyDeduct;
	}

	/**
	 * @see com.betbase.common.PrizeCutType#getTotalDeduct()
	 */
	public BigDecimal getTotalDeduct() {
		return totalDeduct;
	}

	/**
	 * @see com.betbase.common.PrizeCutType#getTotalReturn()
	 */
	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	/**
	 * @see com.betbase.common.PrizeCutType#getPrizeCutDesc()
	 */
	public String getPrizeCutDesc() {
		return getPrizeCutDesc(DEFAULT_TEMPLATE_KEY);
	}

	/**
	 * @see com.betbase.common.PrizeCutType#getPrizeCutDesc(java.lang.String)
	 */
	public String getPrizeCutDesc(String templateKey) {
		List<String> args = new ArrayList<String>();
		args.add(moneyFormat.format(getSchemePrize()));// 总奖金
		args.add(percentFormat.format(getOrganigerDeductRate()));// 方案发起人佣金率
		args.add(moneyFormat.format(getOrganigerDeduct()));// 方案发起佣金提成金额
		args.add(moneyFormat.format(getTotalReturn()));// 扣除所有提成后的可分配金额
		return LocalUtils.getLocalMessage(templateKey, args);
	}
}
