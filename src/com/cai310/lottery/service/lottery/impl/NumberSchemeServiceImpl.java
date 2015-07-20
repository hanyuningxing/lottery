package com.cai310.lottery.service.lottery.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.ChaseState;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.PrepaymentType;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.common.TransactionType;
import com.cai310.lottery.common.WinRecordType;
import com.cai310.lottery.dto.lottery.NumberSchemeDTO;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.ChasePlanDetail;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.user.Prepayment;
import com.cai310.lottery.entity.user.Transaction;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.ChasePlanDetailEntityManager;
import com.cai310.lottery.service.lottery.ChasePlanEntityManager;
import com.cai310.lottery.service.lottery.ChasePlanService;
import com.cai310.lottery.service.lottery.NumberSchemeEntityManager;
import com.cai310.lottery.service.lottery.NumberSchemeService;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.support.PrizeItem;
import com.cai310.lottery.utils.BigDecimalUtil;

@Transactional
public abstract class NumberSchemeServiceImpl<T extends NumberScheme, E extends NumberSchemeDTO> extends
		SchemeServiceImpl<T, E> implements NumberSchemeService<T, E> {

	@Autowired
	protected ChasePlanEntityManager chasePlanEntityManager;
	@Autowired
	protected ChasePlanDetailEntityManager chasePlanDetailEntityManager;
	@Autowired
	protected ChasePlanService chasePlanServiceImpl;
	@Autowired
	protected PrintEntityManager printEntityManager;
	@Autowired
	protected UserEntityManager userEntityManager;

	protected T doSelf(T scheme, E schemeDTO, User user) {
		 //用户来源
		PlatformInfo platform = PlatformInfo.WEB;
        if(null!=schemeDTO.getPlatform()){
        	platform = schemeDTO.getPlatform();
        }
		// 是否追号
		if (!schemeDTO.isChase())
			return super.doSelf(scheme, schemeDTO, user);

		Lottery lotteryType = scheme.getLotteryType();

		ChasePlan chasePlan = new ChasePlan();
		chasePlan.setPlatform(platform);
		chasePlan.setLotteryType(lotteryType);
		chasePlan.setState(ChaseState.RUNNING);
		chasePlan.setUserId(user.getId());
		chasePlan.setUserName(user.getUserName());
		chasePlan.setTotalCost(schemeDTO.getTotalCostOfChase());
		chasePlan.setRandom(schemeDTO.isRandomOfChase());
		chasePlan.setRandomUnits(schemeDTO.getRandomUnitsOfChase());
		chasePlan.setHasDan(schemeDTO.isHasDanOfChase());
		chasePlan.setDan(schemeDTO.getDanOfChase());
		chasePlan.setWonStop(schemeDTO.isWonStopOfChase());
		chasePlan.setPrizeForWonStop(schemeDTO.getPrizeForWonStopOfChase());
		// zhuhui add 2011-05-30
		chasePlan.setOutNumStop(false);
		chasePlan.setUnits(schemeDTO.getUnits());
		chasePlan.setMode(schemeDTO.getMode());
		chasePlan.setContent(schemeDTO.getContent());
		chasePlan.setSchemeCost(schemeDTO.getSchemeCost());
		chasePlan.setCapacityProfit(schemeDTO.getCapacityProfit());

		try {
			chasePlan.setMultiples(schemeDTO.getMultiplesOfChase());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		

		StringBuilder sb = new StringBuilder(20);
		sb.append("发起【").append(lotteryType.getLotteryName()).append("】追号.").append("已冻结金额：")
				.append(BigDecimalUtil.valueOf(chasePlan.getTotalCost()) + " 元 ").append(" 其中：");

		Transaction tran = userManager.createTransaction(TransactionType.CHASE, sb.toString());
		Prepayment chasePrepayment = userManager.createPrepayment(tran.getId(), user.getId(),
				BigDecimalUtil.valueOf(chasePlan.getTotalCost()), PrepaymentType.CHASE, FundDetailType.CHASE,
				sb.toString(),platform,this.getLotteryType());

		chasePlan.setTransactionId(tran.getId());
		chasePlan.setPrepaymentId(chasePrepayment.getId());

		try {
			chasePlan.chase(scheme.getPeriodId(), scheme.getSchemeCost());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}

		chasePlan = chasePlanEntityManager.saveChasePlan(chasePlan);
		scheme.setChaseId(chasePlan.getId());

		BigDecimal subscriptionCost = BigDecimalUtil.valueOf(scheme.getSchemeCost());
		try {
			scheme.subscription(subscriptionCost);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		scheme = getSchemeEntityManager().saveScheme(scheme);

		sb.setLength(0);
		sb.append("认购【").append(scheme.getLotteryType().getLotteryName()).append("】").append(scheme.getPeriodNumber())
				.append("期").append("方案[").append(scheme.getSchemeNumber()).append("],认购金额从追号预付款(追号冻结资金)中扣除. 其中：");
		Prepayment subscriptionPrepayment = userManager.transferPrepayment(scheme.getTransactionId(),
				chasePlan.getPrepaymentId(), subscriptionCost, PrepaymentType.SUBSCRIPTION,
				FundDetailType.SUBSCRIPTION, sb.toString(),platform,this.getLotteryType());

		saveSubscription(scheme, subscriptionPrepayment.getId(), user, subscriptionCost, SubscriptionWay.INITIATE,platform);

		return scheme;
	}

	public void handleChasePlan(long chasePlanId, long periodId) {
		ChasePlan chasePlan = chasePlanEntityManager.getChasePlan(chasePlanId);
		T lastScheme = getSchemeEntityManager().getLastChaseScheme(chasePlan.getId());
		if (chasePlan.isWonStop() && !lastScheme.isUpdateWon()) {
			throw new ServiceException("方案未更新中奖，不能确认是否中奖，请更新中奖并派奖后再执行。");
		}
		boolean canChase = true;
		if (chasePlan.isWonStop() && lastScheme.isWon() && lastScheme.getState() == SchemeState.SUCCESS) {
			if (!lastScheme.isPrizeSended()) {
				throw new ServiceException("方案未派奖，不能最后确认是否中奖，请派奖后再执行。");
			}
			if (chasePlan.getPrizeForWonStop() != null && chasePlan.getPrizeForWonStop() >= 0) {
				if (lastScheme.getPrize().doubleValue() >= chasePlan.getPrizeForWonStop()) {
					canChase = false;
				}
			}
		}
		if (!canChase) {
			chasePlanServiceImpl.stopChasePlan(chasePlan.getId());// 中奖后停止追号
		} else {
			// 获取将要追号的销售期，并判断是否允许发起追号方案
			Period period = periodManager.getPeriod(periodId);

			Integer multiple = chasePlan.getNextMultiple();
			if (multiple == null || multiple <= 0) {
				try {
					chasePlan.skip(period.getId());
				} catch (DataException e) {
					throw new ServiceException(e.getMessage(), e);
				}
				chasePlanEntityManager.saveChasePlan(chasePlan);
				return;
			}

			if (!period.isOnSale()) {
				throw new ServiceException("销售期[#" + period.getId() + "]非在售状态，不能追号。");
			} else if (period.isPaused()) {
				throw new ServiceException("销售期[#" + period.getId() + "]暂停销售中，不能追号。");
			} else {
				PeriodSales periodSales = periodManager.getPeriodSales(new PeriodSalesId(period.getId(), lastScheme
						.getMode()));
				Date now = new Date();
				if (!periodSales.isOnSale() || now.after(periodSales.getSelfEndInitTime())
						|| now.after(periodSales.getEndJoinTime())) {

					throw new ServiceException("当前无法发起方案，不能追号。");
				}
			}

			// 创建新方案交易记录
			Transaction tran = userManager.createTransaction(TransactionType.SCHEME, "追号生成方案");

			// 从旧方案复制生成新方案
			T newScheme = getSchemeEntityManager().cloneNewScheme(lastScheme);
			try {
				newScheme.reset(period, chasePlan, tran.getId());// 重置方案
			} catch (DataException e) {
				throw new ServiceException(e.getMessage(), e);
			}
			newScheme.setId(null);// 确认
			newScheme = getSchemeEntityManager().saveScheme(newScheme);

			// 从追号预付款中扣除新追号方案的方案金额生成新的预付款
			StringBuffer memo = new StringBuffer(50);
			memo.append("自购追号，新追号方案[").append(newScheme.getSchemeNumber()).append("]，所需费用从追号预扣款（追号冻结资金）中转移。其中：");

			Prepayment prepayment = userManager.transferPrepayment(tran.getId(),
					chasePlan.getPrepaymentId(), BigDecimalUtil.valueOf(newScheme.getSchemeCost()),
					PrepaymentType.CHASE, FundDetailType.CHASE, memo.toString(),chasePlan.getPlatform(),this.getLotteryType());

			// 修改重置认购对象，保存新的认购对象
			List<Subscription> joinList = getSchemeEntityManager().findSubscriptionsOfScheme(lastScheme.getId(), null);
			Subscription oldSubscrt = joinList.get(0);
			Subscription newSubscrt = getSchemeEntityManager().cloneNewSubscription(oldSubscrt);
			newSubscrt.reset(newScheme, prepayment.getId());
			newSubscrt = getSchemeEntityManager().saveSubscription(newSubscrt);

			// 更新追号数据
			try {
				chasePlan.chase(newScheme.getPeriodId(), newScheme.getSchemeCost());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage(), e);
			}
			chasePlan = chasePlanEntityManager.saveChasePlan(chasePlan);

			// /////////////////////////////////////////////追号详情///////////////////////////////////////////////////////
			ChasePlanDetail chasePlanDetail = new ChasePlanDetail();
			chasePlanDetail.setAllCount(chasePlan.getChasedCost());
		}
	}

	@Override
	protected abstract NumberSchemeEntityManager<T> getSchemeEntityManager();

	@Override
	public WinRecordType getWinRecord(T Scheme) {
		BigDecimal prizeAfterTax = Scheme.getPrizeAfterTax();
		Integer schemeCost = Scheme.getSchemeCost();
		if (Scheme.getState() == SchemeState.SUCCESS) {
			if (Scheme.getPrizeAfterTax().doubleValue() >= Constant.FiftyMillion.doubleValue()) {
				return WinRecordType.TUANZHANG;
			}
			if (prizeAfterTax.doubleValue() >= Constant.FiveMillion.doubleValue()) {
				return WinRecordType.YINGZHANG;
			}
			if (prizeAfterTax.doubleValue() >= Constant.OneMillion.doubleValue()) {
				return WinRecordType.LIANZHANG;
			}
			if ((prizeAfterTax.doubleValue() >= Constant.OneThousands.doubleValue())
					|| (prizeAfterTax.doubleValue() >= Constant.FiveHundreds.doubleValue() && (prizeAfterTax
							.doubleValue() / schemeCost) >= 10)) {
				return WinRecordType.PAIZHANG;
			}
		} else {
			// 流产
			if ((prizeAfterTax.doubleValue() >= Constant.TwoThousands.doubleValue())
					|| (prizeAfterTax.doubleValue() >= Constant.OneThousands.doubleValue() && (prizeAfterTax
							.doubleValue() / schemeCost) >= 20)) {
				return WinRecordType.PAIZHANG;
			}
		}

		return null;
	}
	
	public void doUpdateTicketPrize(List<PrizeItem> list,Ticket ticket) throws DataException {
		if (list == null || list.isEmpty()) {
			throw new DataException("中奖信息为空！");
		}

		BigDecimal totalPrize = BigDecimal.ZERO;// 总奖金
		BigDecimal totalPrizeAfterTax = BigDecimal.ZERO;// 税后总奖金

		VariableString varPrizeLineText = new VariableString(Constant.PRIZE_LINE_TMPLATE, null);

		StringBuilder sb = new StringBuilder();

		BigDecimal prizeAfterTax;
		for (PrizeItem item : list) {
			varPrizeLineText.setVar("PRIZENAME", item.getWinItem().getWinName());
			varPrizeLineText.setVar("WINUNITS", item.getWinItem().getWinUnit());
			varPrizeLineText.setVar("UNIT_PRIZE", Constant.numFmt.format(item.getUnitPrize()));
			prizeAfterTax = item.getUnitPrizeAfterTax();
			varPrizeLineText.setVar("UNIT_PRIZE_TAXED", Constant.numFmt.format(prizeAfterTax));
			sb.append(varPrizeLineText.toString());

			totalPrize = totalPrize.add(BigDecimalUtil.multiply(item.getUnitPrize(),
					BigDecimalUtil.valueOf(item.getWinItem().getWinUnit())));
			totalPrizeAfterTax = totalPrizeAfterTax.add(BigDecimalUtil.multiply(prizeAfterTax,
					BigDecimalUtil.valueOf(item.getWinItem().getWinUnit())));
		}
		ticket.setTotalPrize(totalPrize.doubleValue());
		ticket.setTotalPrizeAfterTax(totalPrizeAfterTax.doubleValue());
		ticket.setWonDetail(sb.toString());
	}
}
