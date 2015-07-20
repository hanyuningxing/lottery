package com.cai310.lottery.service.lottery.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.AfterFinishFlag;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SalesState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.SaleAnalyse;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dScheme;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.exception.ControllerException;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SaleAnalyseEntityManager;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class SaleControllerServiceImpl extends AbstractControllerService implements SalesControllerService {
	@Autowired
	protected SaleAnalyseEntityManager saleAnalyseEntityManager;
	
	public void beginSaleWork(PeriodSalesId periodSalesId) {

		SaleWorkerCmd cmd = SaleWorkerCmd.BeginSale;

		workHelper.logCmdStart(cmd, periodSalesId.getPeriodId(), periodSalesId.getSalesMode());

		getPeriodManager().doBeginSales(periodSalesId);

		workHelper.logCmdSuccess();
	}

	public void closePeriodWork(long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.CloseIssue;

		workHelper.logCmdStart(cmd, periodId);

		Integer unUpdateWonNum = getSchemeManager().statUnUpdateWon(periodId);
		if (unUpdateWonNum != null && unUpdateWonNum > 0) {
			workHelper.logWarn("还有" + unUpdateWonNum + "个方案未执行【" + SaleWorkerCmd.UpdateResult.getCmdName() + "】操作。");
			return;
		}
		Integer unUpdatePrizeNum = getSchemeManager().statUnUpdatePrize(periodId);
		if (unUpdatePrizeNum != null && unUpdatePrizeNum > 0) {
			workHelper.logWarn("还有" + unUpdatePrizeNum + "个方案未执行【" + SaleWorkerCmd.UpdatePrize.getCmdName() + "】操作。");
			return;
		}
		Integer unDelivePrizeNum = getSchemeManager().statUnDelivePrize(periodId);
		if (unDelivePrizeNum != null && unDelivePrizeNum > 0) {
			workHelper.logWarn("还有" + unDelivePrizeNum + "个方案未执行【" + SaleWorkerCmd.DelivePrize.getCmdName() + "】操作。");
			return;
		}

		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("state", PeriodState.Finished);
		getPeriodManager().updatePeriod(updateMap, periodId);// 关闭本期

		workHelper.logCmdSuccess();
	}

	public synchronized void baodiWork(PeriodSalesId saleId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.Baodi;

		workHelper.logCmdStart(cmd, saleId.getPeriodId(), saleId.getSalesMode());

		// TODO:检查保底
		List<Long> schemeIdList = getSchemeManager().findSchemeIdOfHasBaodiAndUnReserved(saleId.getPeriodId(),
				saleId.getSalesMode());
		Period period = getPeriodManager().getPeriod(saleId.getPeriodId());
		if (schemeIdList != null && !schemeIdList.isEmpty()) {
			StringBuffer msg = new StringBuffer(50);
			int totalCount = schemeIdList.size();
			int count = 0;
			Date date;
			long runTime;

			for (Long schemeId : schemeIdList) {
				count++;
				date = new Date();

				msg.setLength(0);
				msg.append("正在处理方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(schemeId).append("]...");
				workHelper.logInfo(msg.toString());
				getSchemeService().runBaodi(schemeId);// 对一个方案进行【保底】操作

				runTime = (new Date()).getTime() - date.getTime();// 操作耗时，单位毫秒
				msg.setLength(0);
				msg.append("方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(schemeId).append("]处理完成，耗时").append(runTime).append("毫秒，还剩").append(
						totalCount - count).append("个方案未处理。");
				workHelper.logInfo(msg.toString());
			}
		} else {
			workHelper.logWarn("找不到可以执行【" + cmd.getCmdName() + "】操作的方案。");
		}

		// 检查是否还有方案未执行
		List<Map<String, Object>> statList = getSchemeManager().statUnRunBaodi(saleId.getPeriodId(),
				saleId.getSalesMode());
		if (statList != null && !statList.isEmpty()) {
			if (statList.size() > 2)
				throw new ControllerException("统计未执行【" + cmd.getCmdName() + "】的方案，统计结果不正常！");
			int reservedNum = 0;
			int UnReservedNum = 0;
			for (Map<String, Object> map : statList) {
				if ((Boolean) map.get("reserved")) {
					reservedNum = (Integer) map.get("count");
				} else {
					UnReservedNum = (Integer) map.get("count");
				}
			}
			int totalNum = reservedNum + UnReservedNum;
			if (totalNum > 0) {
				workHelper.logWarn("还剩" + totalNum + "个方案未执行，其中" + reservedNum + "个方案被保留。");
				return; // 还有方案未执行，退出！
			}
		}

		workHelper.logInfo("正在更新销售标记...");
		getPeriodManager().updatePeriodSalesState(saleId, SalesState.BAODI_EXECUTED);

		workHelper.logCmdSuccess();
	}

	public void commitPaymentWork(PeriodSalesId periodSalesId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.CommitPayment;

		workHelper.logCmdStart(cmd, periodSalesId.getPeriodId(), periodSalesId.getSalesMode());
		Period period = getPeriodManager().getPeriod(periodSalesId.getPeriodId());
		List<Long> idList = getSchemeManager().findSchemeIdOfUnCommitAndUnReserved(periodSalesId.getPeriodId(),
				periodSalesId.getSalesMode());
		if (idList != null && !idList.isEmpty()) {
			StringBuffer msg = new StringBuffer(50);
			int totalCount = idList.size();
			int count = 0;
			Date date;
			long runTime;

			for (Long schemeId : idList) {
				count++;
				date = new Date();

				msg.setLength(0);
				msg.append("正在处理方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(schemeId).append("]...");
				workHelper.logInfo(msg.toString());

				getSchemeService().commitOrCancelTransaction(schemeId);// 执行完成交易（交易成功或撤销）

				runTime = (new Date()).getTime() - date.getTime();// 操作耗时，单位毫秒
				msg.setLength(0);
				msg.append("方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(schemeId).append("]处理完成，耗时").append(runTime).append("毫秒，还剩").append(
						totalCount - count).append("个方案未处理。");
				workHelper.logInfo(msg.toString());
			}
		} else {
			workHelper.logWarn("找不到可以执行【" + cmd.getCmdName() + "】操作的方案。");
		}

		// 检查是否还有方案未执行
		List<Map<String, Object>> statList = getSchemeManager().statUnCommit(periodSalesId.getPeriodId(),
				periodSalesId.getSalesMode());
		if (statList != null && !statList.isEmpty()) {
			if (statList.size() > 2)
				throw new ControllerException("统计未执行【" + cmd.getCmdName() + "】的方案，统计结果不正常！");
			int reservedNum = 0;
			int UnReservedNum = 0;
			for (Map<String, Object> map : statList) {
				if ((Boolean) map.get("reserved")) {
					reservedNum = (Integer) map.get("count");
				} else {
					UnReservedNum = (Integer) map.get("count");
				}
			}
			int totalNum = reservedNum + UnReservedNum;
			if (totalNum > 0) {
				workHelper.logWarn("还剩" + totalNum + "个方案未执行，其中" + reservedNum + "个方案被保留。");
				return; // 还有方案未执行，退出！
			}
		}

		PeriodSales periodSale = getPeriodManager().getPeriodSales(periodSalesId);
		if (periodSale.canEndComminPayment()) {
			getPeriodManager().endComminPayment(periodSalesId);// 结束完成交易
		} else {
			workHelper.logWarn("未执行保底，还不能结束【" + cmd.getCmdName() + "】。");
			return;
		}

		workHelper.logCmdSuccess();

	}

	public void endSaleWork(PeriodSalesId periodSalesId) {

		SaleWorkerCmd cmd = SaleWorkerCmd.EndSale;

		workHelper.logCmdStart(cmd, periodSalesId.getPeriodId(), periodSalesId.getSalesMode());

		getPeriodManager().doEndSales(periodSalesId);

		workHelper.logCmdSuccess();
	}

	public void pauseSaleWork(Long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.PauseSale;

		workHelper.logCmdStart(cmd, periodId);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", PeriodState.Paused);
		getPeriodManager().updatePeriod(map, periodId);

		workHelper.logCmdSuccess();
	}

	public void resumeSaleWork(Long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.ResumeSale;

		workHelper.logCmdStart(cmd, periodId);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", PeriodState.Started);
		getPeriodManager().updatePeriod(map, periodId);

		workHelper.logCmdSuccess();
	}

	public void closePeriodWork(Long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.CloseIssue;

		workHelper.logCmdStart(cmd, periodId);
		Integer unUpdateWonNum = getSchemeManager().statUnUpdateWon(periodId);
		if (unUpdateWonNum != null && unUpdateWonNum > 0) {
			workHelper.logWarn("还有" + unUpdateWonNum + "个方案未执行【" + SaleWorkerCmd.UpdateResult.getCmdName() + "】操作。");
			return;
		}
		Integer unUpdatePrizeNum = getSchemeManager().statUnUpdatePrize(periodId);
		if (unUpdatePrizeNum != null && unUpdatePrizeNum > 0) {
			workHelper.logWarn("还有" + unUpdatePrizeNum + "个方案未执行【" + SaleWorkerCmd.UpdatePrize.getCmdName() + "】操作。");
			return;
		}
		Integer unDelivePrizeNum = getSchemeManager().statUnDelivePrize(periodId);
		if (unDelivePrizeNum != null && unDelivePrizeNum > 0) {
			workHelper.logWarn("还有" + unDelivePrizeNum + "个方案未执行【" + SaleWorkerCmd.DelivePrize.getCmdName() + "】操作。");
			return;
		}

		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("state", PeriodState.Finished);
		getPeriodManager().updatePeriod(updateMap, periodId);// 关闭本期

		workHelper.logCmdSuccess();
	}

	public synchronized void hideIssueWork(long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.HideIssue;

		workHelper.logCmdStart(cmd, periodId);

		getPeriodManager().hideCurrentPeriod(periodId);

		workHelper.logCmdSuccess();
	}
	public synchronized void showIssueWork(long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.ShowIssue;

		workHelper.logCmdStart(cmd, periodId);

		getPeriodManager().showPeriodCurrent(periodId);

		workHelper.logCmdSuccess();
	}

	// ******************** can run work related logic ********************
	public boolean canRun(Long periodId, SaleWorkerCmd cmd) {
		Period period = getPeriodManager().getPeriod(periodId);
		if (period != null) {
			Set<SaleWorkerCmd> cmds = getCanRunCmds(period);
			if (cmds.contains(cmd)) {
				return true;
			}
			return false;

		}
		return false;
	}

	public boolean canRun(Long periodId, SalesMode salesMode, SaleWorkerCmd cmd) {
		Period period = getPeriodManager().getPeriod(periodId);
		if (period != null) {
			if (salesMode == null) {
				Set<SaleWorkerCmd> cmds = getCanRunCmds(period);
				if (cmds.contains(cmd)) {
					return true;
				}
				return false;
			}
			PeriodSales periodSales = getPeriodManager().getPeriodSales(new PeriodSalesId(periodId, salesMode));
			if (periodSales != null) {
				Set<SaleWorkerCmd> cmds = getCanRunCmds(periodSales);
				if (cmds.contains(cmd)) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public Set<SaleWorkerCmd> getCanRunCmds(Period period) {
		Set<SaleWorkerCmd> cmds = new HashSet<SaleWorkerCmd>(0);
		if (period.isOnSale()) {
			cmds.add(SaleWorkerCmd.PauseSale);
		} else if (period.isPaused()) {
			cmds.add(SaleWorkerCmd.ResumeSale);
		} else {
			cmds.addAll(getAfterSaleCanRunCmds(period));
			if (period.isFinished()) {
				cmds.addAll(getAfterFinishCanRunCmds(period));
			}
		}
		return cmds;
	}

	public Set<SaleWorkerCmd> getAfterSaleCanRunCmds(Period period) {
		Set<SaleWorkerCmd> cmds = new HashSet<SaleWorkerCmd>(0);

		PeriodSales periodSales = getPeriodManager()
				.getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.SINGLE));
		if (!periodSales.isSaleFinished()) {
			return cmds;
		}
		periodSales = getPeriodManager().getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.COMPOUND));
		if (!periodSales.isSaleFinished()) {
			return cmds;
		}

		if (period.isCurrent()) {
			List<Period> currentIssues = getPeriodManager().findCurrentPeriods(getLotteryType());
			if (currentIssues != null && currentIssues.size() > 1) {
				cmds.add(SaleWorkerCmd.HideIssue);
			}
		}else{
			cmds.add(SaleWorkerCmd.ShowIssue);
		}

		if (!period.isResultUpdate()) {
			cmds.add(SaleWorkerCmd.UpdateResult);
		} else if (!period.isPrizeUpdate()) {
			cmds.add(SaleWorkerCmd.UpdateResult);
			cmds.add(SaleWorkerCmd.UpdatePrize);
		} else if (!period.isPrizeDelived()) {
			cmds.add(SaleWorkerCmd.UpdateResult);
			cmds.add(SaleWorkerCmd.UpdatePrize);
			cmds.add(SaleWorkerCmd.DelivePrize);
		} else if (!period.isFinished()) {
			cmds.add(SaleWorkerCmd.UpdateResult);
			cmds.add(SaleWorkerCmd.UpdatePrize);
			cmds.add(SaleWorkerCmd.DelivePrize);
			cmds.add(SaleWorkerCmd.CloseIssue);
		}
		return cmds;
	}

	public Set<SaleWorkerCmd> getAfterFinishCanRunCmds(Period period) {
		Set<SaleWorkerCmd> cmds = new HashSet<SaleWorkerCmd>(0);
		//TODO:这里可以增加一些期结束后的操作
		if (!period.checkAfterFinishFlags(AfterFinishFlag.SaleAnalyse.getFlagValue())) {
			cmds.add(SaleWorkerCmd.SaleAnalyse);
		}
		return cmds;
	}

	public Set<SaleWorkerCmd> getCanRunCmds(PeriodSales periodSales) {
		Set<SaleWorkerCmd> cmds = new HashSet<SaleWorkerCmd>(0);
		switch (periodSales.getState()) {

		case NOT_BEGUN:// 未开售
			cmds.add(SaleWorkerCmd.BeginSale);
			break;
		case ON_SALE:// 在售
			cmds.add(SaleWorkerCmd.EndSale);
			break;
		case UN_PRINTED_ENDED:// 未出票截止
			cmds.add(SaleWorkerCmd.EndSale);
			cmds.add(SaleWorkerCmd.CommitPayment);
			break;
		case SALE_ENDED:// 全部截止
			cmds.add(SaleWorkerCmd.Baodi);
			cmds.add(SaleWorkerCmd.CommitPayment);
			break;
		case BAODI_EXECUTED:// 已执行保底
			cmds.add(SaleWorkerCmd.CommitPayment);
			break;
		case PAYMENT_COMMITTED:// 已完成交易
			cmds.add(SaleWorkerCmd.UpdateResult);
			cmds.add(SaleWorkerCmd.UpdatePrize);
			cmds.add(SaleWorkerCmd.DelivePrize);
			break;
		}
		return cmds;
	}
    /**
     * 销售统计
     */
	public void saleAnalyseWork(long periodId) {

		try {
				SaleWorkerCmd cmd = SaleWorkerCmd.SaleAnalyse;
		
				workHelper.logCmdStart(cmd, periodId);

				Period period = getPeriodManager().getPeriod(periodId);
				List<Long> schemeIdList = getSchemeManager().findSchemeIdOfSaleAnalyse(periodId);
				
				StringBuffer msg = new StringBuffer(50);
				int totalCount = schemeIdList.size();
				int count = 0;
				Date date;
				long runTime;

				
				
				SfzcScheme sfzcScheme = null;
				PlScheme plScheme = null;
				Welfare3dScheme welfare3dScheme = null;
				JczqScheme jczqScheme = null;
				JclqScheme jclqScheme = null;
				DltScheme dltScheme = null;
				DczcScheme dczcScheme = null;
				
				Map<String, SaleAnalyse> saleAnalyeMap = new HashMap<String, SaleAnalyse>();
				SaleAnalyse saleAnalyse = null;
				Integer playType = null;
				List<Subscription> subscriptionList;
				Scheme scheme=null;
				Set<Long> updateSchemeSet = Sets.newHashSet();
				for (Long schemeId : schemeIdList) {
					scheme = getSchemeManager().getScheme(schemeId);
					count++;
					date = new Date();

					msg.setLength(0);
					msg.append("正在处理方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(schemeId).append("]...");
					workHelper.logInfo(msg.toString());
					
					
					if(Lottery.SFZC.equals(scheme.getLotteryType())){
						sfzcScheme=(SfzcScheme)scheme;
						playType=sfzcScheme.getPlayType().ordinal();
					}else if(Lottery.PL.equals(scheme.getLotteryType())){
						plScheme=(PlScheme)scheme;
						playType=plScheme.getPlayType().ordinal();
					}else if(Lottery.WELFARE3D.equals(scheme.getLotteryType())){
						welfare3dScheme=(Welfare3dScheme)scheme;
						playType=welfare3dScheme.getPlayType().ordinal();
					}else if(Lottery.JCZQ.equals(scheme.getLotteryType())){
						jczqScheme=(JczqScheme)scheme;
						playType=jczqScheme.getPlayType().ordinal();
					}else if(Lottery.JCLQ.equals(scheme.getLotteryType())){
						jclqScheme=(JclqScheme)scheme;
						playType=jclqScheme.getPlayType().ordinal();
					}else if(Lottery.DLT.equals(scheme.getLotteryType())){
						dltScheme=(DltScheme)scheme;
						playType=dltScheme.getPlayType().ordinal();
					}else if(Lottery.DCZC.equals(scheme.getLotteryType())){
						dczcScheme=(DczcScheme)scheme;
						playType=dczcScheme.getPlayType().ordinal();
					}
					
					/////key规则。如果没有playType key=userId_ 如果有key=userId_0或者userId_1或者。。。。
					saleAnalyse = saleAnalyeMap.get(scheme.getSponsorId()+"_"+(null==playType?"":playType));
					if(null==saleAnalyse){
						saleAnalyse=getNewSaleAnalyseMethod(scheme,period,playType);
					}
					updateSchemeSet.add(scheme.getId());
					//增加
				    saleAnalyse.addSchemeCount(1);
					saleAnalyse.addSchemeCost(scheme.getSchemeCost());
					saleAnalyse.addSchemeWonPrize(scheme.getPrize());
					if(SchemeState.SUCCESS.equals(scheme.getState())){
						//成功的方案
					   saleAnalyse.addSchemeSuccessCount(1);
					   saleAnalyse.addSchemeSuccessCost(scheme.getSchemeCost());
					   saleAnalyse.addSchemeSuccessWonPrize(scheme.getPrize());
					}else if(SchemeState.CANCEL.equals(scheme.getState())){
						//失败的方案
					   saleAnalyse.addSchemeCancelCount(1);
					   saleAnalyse.addSchemeCancelCost(scheme.getSchemeCost());
					   saleAnalyse.addSchemeCancelWonPrize(scheme.getPrize());
					}else{
						//数据错误
					}
					saleAnalyeMap.put(scheme.getSponsorId()+"_"+(null==playType?"":playType), saleAnalyse);
					/////加入处理
					subscriptionList=getSchemeManager().findSubscriptionsOfScheme(scheme.getId(), null);
					for (Subscription subscription : subscriptionList) {
							saleAnalyse = saleAnalyeMap.get(subscription.getUserId()+"_"+(null==playType?"":playType));
							if(null==saleAnalyse){
								saleAnalyse=getNewSaleAnalyseMethod(subscription,period,playType);
							}
							setSubscriptionMethod(saleAnalyse,scheme,subscription);
							saleAnalyeMap.put(subscription.getUserId()+"_"+(null==playType?"":playType), saleAnalyse);
					}
					runTime = (new Date()).getTime() - date.getTime();// 操作耗时，单位毫秒
					msg.setLength(0);
					msg.append("方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(schemeId).append("]处理完成，耗时").append(runTime).append("毫秒，还剩").append(
							totalCount - count).append("个方案未处理。");
					workHelper.logInfo(msg.toString());
				}
				Set<String> keySet = saleAnalyeMap.keySet();
				workHelper.logInfo("准备更新统计条数="+keySet.size());
				List<SaleAnalyse> updateSaleAnalyse = Lists.newArrayList();
				for (String key : keySet) {
					updateSaleAnalyse.add(saleAnalyeMap.get(key));
				}
				saleAnalyseEntityManager.updateSaleAnalyse(updateSaleAnalyse, updateSchemeSet, period.getLotteryType());
				//List a=getSchemeManager().findSubscriptionOfSaleAnalyse(schemeIdList);
			   
		} catch (DataException e) {
			e.printStackTrace();
			workHelper.logInfo("执行销量统计出错"+e.getMessage());
		}
	}
	public SaleAnalyse getNewSaleAnalyseMethod(Subscription subscription,Period period,Integer playType){
		SaleAnalyse saleAnalyse = new SaleAnalyse();
	     ////公共属性
		saleAnalyse.setPeriodId(period.getId());
		saleAnalyse.setLottery(period.getLotteryType());
		saleAnalyse.setPeriodNumber(period.getPeriodNumber());
		saleAnalyse.setPlayType(playType);
		saleAnalyse.setUserId(subscription.getUserId());
		saleAnalyse.setUserName(subscription.getUserName());
		
		Date endTime = subscription.getCreateTime();
		saleAnalyse.setEndedTime(endTime);
		saleAnalyse.setYearNum(DateUtil.getYear(endTime));
		saleAnalyse.setQuarterNum(DateUtil.getQuarter(endTime));
		saleAnalyse.setMonthNum(DateUtil.getMonth(endTime));
		saleAnalyse.setWeekNum(DateUtil.getWeek(endTime));
		saleAnalyse.setSendCurrency(Boolean.FALSE);
		return saleAnalyse;
	}
	public SaleAnalyse getNewSaleAnalyseMethod(Scheme scheme,Period period,Integer playType){
		SaleAnalyse saleAnalyse = new SaleAnalyse();
	     ////公共属性
		saleAnalyse.setPeriodId(period.getId());
		saleAnalyse.setLottery(period.getLotteryType());
		saleAnalyse.setPeriodNumber(period.getPeriodNumber());
		saleAnalyse.setPlayType(playType);
		saleAnalyse.setUserId(scheme.getSponsorId());
		saleAnalyse.setUserName(scheme.getSponsorName());
		Date endTime = scheme.getCommitTime();
		saleAnalyse.setEndedTime(endTime);
		saleAnalyse.setYearNum(DateUtil.getYear(endTime));
		saleAnalyse.setQuarterNum(DateUtil.getQuarter(endTime));
		saleAnalyse.setMonthNum(DateUtil.getMonth(endTime));
		saleAnalyse.setWeekNum(DateUtil.getWeek(endTime));
		
		saleAnalyse.setSendCurrency(Boolean.FALSE);
		
		return saleAnalyse;
	}
	public void setSubscriptionMethod(SaleAnalyse saleAnalyse,Scheme scheme,Subscription subscription) throws DataException{
		saleAnalyse.addSubscriptionCount(1);
		saleAnalyse.addSubscriptionCost(subscription.getCost());
		saleAnalyse.addSubscriptionWonPrize(subscription.getBonus());
		if(SchemeState.SUCCESS.equals(scheme.getState())){
			//成功的方案
			if(SubscriptionState.NORMAL.equals(subscription.getState())){
				//成功的加入
			   saleAnalyse.addSubscriptionSuccessCount(1);
			   saleAnalyse.addSubscriptionSuccessCost(subscription.getCost());
			   saleAnalyse.addSubscriptionSuccessWonPrize(subscription.getBonus());
			}else if(SubscriptionState.CANCEL.equals(subscription.getState())){
				//失败的加入
			   saleAnalyse.addSubscriptionCancelCount(1);
			   saleAnalyse.addSubscriptionCancelCost(subscription.getCost());
			   saleAnalyse.addSubscriptionCancelWonPrize(subscription.getBonus());
			}else{
				//数据错误
			}
		}else if(SchemeState.CANCEL.equals(scheme.getState())){
			//失败的方案
			saleAnalyse.addSubscriptionCancelCount(1);
			saleAnalyse.addSubscriptionCancelCost(subscription.getCost());
			saleAnalyse.addSubscriptionCancelWonPrize(subscription.getBonus());
		}else{
			//数据错误
		}
	}
}
