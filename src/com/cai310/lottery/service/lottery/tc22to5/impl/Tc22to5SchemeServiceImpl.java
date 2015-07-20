package com.cai310.lottery.service.lottery.tc22to5.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dto.lottery.tc22to5.Tc22to5SchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Passcount;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Scheme;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5SchemeWonInfo;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5WinUnit;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.tc22to5.PrizeWork;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.NumberSchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.NumberSchemeServiceImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.tc22to5.Tc22to5CompoundContent;

@Service("tc22to5SchemeServiceImpl")
@Transactional
public class Tc22to5SchemeServiceImpl extends NumberSchemeServiceImpl<Tc22to5Scheme, Tc22to5SchemeDTO> {

	@Resource
	protected TicketEntityManager ticketEntityManager;
	
	@Autowired
	private Tc22to5SchemeEntityManagerImpl schemeManager;

	@Autowired
	private Tc22to5PeriodDataEntityManagerImpl periodDataManagerImpl;

	@Override
	protected NumberSchemeEntityManager<Tc22to5Scheme> getSchemeEntityManager() {
		return schemeManager;
	}

	public void updatePrize(long schemeId) {
		Tc22to5Scheme scheme = schemeManager.getScheme(schemeId);
		if (!scheme.isUpdateWon()) {
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]未更新中奖，不能更新奖金！");
		} else if (!scheme.isWon()) {
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]未中奖，不能更新奖金！");
		} else if (scheme.isPrizeSended()) {
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已派奖，不能再更新奖金！");
		}

		Period period = periodManager.getPeriod(scheme.getPeriodId());
		if (!period.isDrawed()) {
			throw new ServiceException("还未开奖，不能执行更新奖金！");
		}

		Tc22to5PeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (periodData.getPrize() == null) {
			throw new ServiceException("未设置中奖奖金！");
		}
		try {
			periodData.getPrize().checkPrize();
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		Tc22to5WinUnit winUnit = null;
		Tc22to5SchemeWonInfo bet = this.schemeManager.getTc22to5SchemeWonInfo(scheme.getId());
		if (bet != null) {
			winUnit = bet.getWinUnit();
		}
		if (winUnit == null || !winUnit.isWon()) {
			throw new ServiceException("数据异常！");
		}
		try {
			scheme.doUpdatePrize(periodData.getPrize().getPrizeItemList(winUnit));
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		scheme = schemeManager.saveScheme(scheme);
	}

	public void updateResult(long schemeId) {
		Tc22to5Scheme scheme = this.schemeManager.getScheme(schemeId);
		if (scheme.isUpdateWon()) {
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已更新中奖，不能再更新！");
		} else if (scheme.isUpdatePrize()) {
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已更新奖金，不能再更新中奖！");
		} else if (scheme.isPrizeSended()) {
			throw new ServiceException("方案[" + scheme.getSchemeNumber() + "]已派奖，不能再派奖！");
		}
		Period period = this.periodManager.getPeriod(scheme.getPeriodId());
		if (!period.isDrawed()) {
			throw new ServiceException("还未开奖，不能执行更新中奖！");
		}
		Tc22to5PeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (null == periodData || StringUtils.isBlank(periodData.getResult())) {
			throw new ServiceException("开奖结果为空！");
		}
		if (periodData.getPrize() == null) {
			throw new ServiceException("未设置中奖奖金！");
		}
		if (scheme.getMode() == SalesMode.COMPOUND) {
			this.updateCompoundResult(scheme, periodData);
		} else {
			this.updateSingleResult(scheme, periodData);
		}
	}

	protected void updateSingleResult(Tc22to5Scheme scheme, Tc22to5PeriodData periodData) {
		PrizeWork prizeWork = new PrizeWork(scheme.getContent(), periodData.getResult(), scheme.getMultiple());
		this.updateResult(prizeWork, scheme, periodData);
	}

	protected void updateCompoundResult(Tc22to5Scheme scheme, Tc22to5PeriodData periodData) {
		PrizeWork prizeWork = new PrizeWork(scheme.getCompoundContent(), periodData.getResult(), scheme.getMultiple());
		this.updateResult(prizeWork, scheme, periodData);
	}

	private void updateResult(PrizeWork prizeWork, Tc22to5Scheme scheme, Tc22to5PeriodData periodData) {
		Tc22to5WinUnit tc22to5WinUnit = prizeWork.getTc22to5WinUnit();
		if (tc22to5WinUnit.isWon()) {
			Tc22to5SchemeWonInfo tc22to5SchemeBet = schemeManager.getTc22to5SchemeWonInfo(scheme.getId());
			if (tc22to5SchemeBet == null) {
				tc22to5SchemeBet = new Tc22to5SchemeWonInfo();
				tc22to5SchemeBet.setSchemeId(scheme.getId());
			}
			tc22to5SchemeBet.setWinUnit(tc22to5WinUnit);
			schemeManager.saveTc22to5SchemeWonInfo(tc22to5SchemeBet);
			try {
				scheme.doUpdateResult(tc22to5WinUnit.getWinItemList());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage(), e);
			}
			Tc22to5Passcount tc22to5Passcount = schemeManager.getTc22to5Passcount(scheme.getId());
			if (tc22to5Passcount == null) {
				tc22to5Passcount = new Tc22to5Passcount();
			}
			super.setSchemePasscountInstance(scheme, tc22to5Passcount);
			tc22to5Passcount.setWinUnit(tc22to5WinUnit);
			schemeManager.saveTc22to5Passcount(tc22to5Passcount);

			
			//出票回查
			PrintInterface printInterface = printEntityManager.findPrintInterfaceBy(scheme.getSchemeNumber(), scheme.getLotteryType());
			if(null!=printInterface){
					List<Long> ticketIds = ticketEntityManager.findTicketIdByPrintInterfaceId(printInterface.getId());
					Ticket ticket = null;
					for (Long id : ticketIds) {
						 ticket = ticketEntityManager.getTicket(id);
						 ticket.setChecked(true);
						 if(SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())){
							///方案成功才更新出票
							PrizeWork ticketPrizeWork = null;
							try {
								if (scheme.getMode() == SalesMode.COMPOUND) {
									ticketPrizeWork = new PrizeWork(getCompoundContent(ticket.getContent()), periodData.getResult(), ticket.getMultiple());
								} else {
									String content = ticket.getContent();
									if(content.indexOf(",")!=-1){
										content = content.replaceAll(",","\r\n");
									}

									ticketPrizeWork = new PrizeWork(content, periodData.getResult(), ticket.getMultiple());
								}
								Tc22to5WinUnit ticketPrizeUnit = ticketPrizeWork.getTc22to5WinUnit();
								if(ticketPrizeUnit.isWon()){
									//
									ticket.setWon(true);
									ticket.setTicket_synchroned(Boolean.FALSE);
									doUpdateTicketPrize(periodData.getPrize().getPrizeItemList(ticketPrizeUnit),ticket);
									ticketEntityManager.saveTicket(ticket);
								}
							} catch (DataException e) {
								e.printStackTrace();
							}
						 }
						
					}
			}
		} else {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
			scheme.setWon(false);
			// 只统计中奖方案，不中奖 的删掉
			Tc22to5Passcount tc22to5Passcount = schemeManager.getTc22to5Passcount(scheme.getId());
			if (tc22to5Passcount != null) {
				schemeManager.deleteTc22to5Passcount(tc22to5Passcount);
			}
		}

		scheme = this.schemeManager.saveScheme(scheme);
	}
	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<Tc22to5CompoundContent> getCompoundContent(String ticketContent) {
		Collection<Tc22to5CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				Tc22to5CompoundContent.class);
		return c;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.TC22TO5;
	}
}
