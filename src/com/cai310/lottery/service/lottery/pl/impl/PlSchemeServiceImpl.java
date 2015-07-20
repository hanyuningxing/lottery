package com.cai310.lottery.service.lottery.pl.impl;

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
import com.cai310.lottery.dto.lottery.pl.PlSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.pl.PlPasscount;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.entity.lottery.pl.PlSchemeWonInfo;
import com.cai310.lottery.entity.lottery.pl.PlWinUnit;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.pl.PrizeWork;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.NumberSchemeEntityManager;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.impl.NumberSchemeServiceImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.pl.PlCompoundContent;

@Service("plSchemeServiceImpl")
@Transactional
public class PlSchemeServiceImpl extends NumberSchemeServiceImpl<PlScheme, PlSchemeDTO> {

	@Autowired
	private PlSchemeEntityManagerImpl schemeManager;
	@Resource
	protected TicketEntityManager ticketEntityManager;
	@Autowired
	private PlPeriodDataEntityManagerImpl periodDataManagerImpl;

	@Override
	protected NumberSchemeEntityManager<PlScheme> getSchemeEntityManager() {
		return schemeManager;
	}
	
	@Resource
	protected PrintEntityManager printEntityManager;
	protected PlScheme newSchemeInstance(PlSchemeDTO schemeDTO) {
		PlScheme scheme = super.newSchemeInstance(schemeDTO);
		// //附加
		scheme.setPlayType(schemeDTO.getPlayType());
		return scheme;
	}

	public void updatePrize(long schemeId) {
		PlScheme scheme = schemeManager.getScheme(schemeId);
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

		PlPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (null == periodData || StringUtils.isBlank(periodData.getResult())) {
			throw new ServiceException("开奖结果为空！");
		}
		if (periodData.getPrize() == null) {
			throw new ServiceException("未设置中奖奖金！");
		}
		try {
			periodData.getPrize().checkPrize();
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		PlWinUnit winUnit = null;
		PlSchemeWonInfo bet = this.schemeManager.getSchemeBet(scheme.getId());
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
		PlScheme scheme = this.schemeManager.getScheme(schemeId);
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
		PlPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (StringUtils.isBlank(periodData.getResult())) {
			throw new ServiceException("开奖结果为空！");
		}

		if (scheme.getMode() == SalesMode.COMPOUND) {
			this.updateCompoundResult(scheme, periodData);
		} else {
			this.updateSingleResult(scheme, periodData);
		}
	}

	protected void updateSingleResult(PlScheme scheme, PlPeriodData periodData) {
		PrizeWork prizeWork = null;
		try {
			prizeWork = new PrizeWork(scheme.getContent(), periodData.getResult(), scheme.getMultiple(),
					scheme.getPlayType());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		this.updateResult(prizeWork, scheme, periodData);
	}

	protected void updateCompoundResult(PlScheme scheme, PlPeriodData periodData) {
		PrizeWork prizeWork = null;
		try {
			prizeWork = new PrizeWork(scheme.getCompoundContent(), periodData.getResult(), scheme.getMultiple(),
					scheme.getPlayType());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		this.updateResult(prizeWork, scheme, periodData);
	}

	protected void updateResult(PrizeWork prizeWork, PlScheme scheme, PlPeriodData periodData) {
		PlWinUnit plWinUnit = prizeWork.getPlWinUnit();
		if (plWinUnit.isWon()) {
			PlSchemeWonInfo plSchemeBet = schemeManager.getSchemeBet(scheme.getId());
			if (plSchemeBet == null) {
				plSchemeBet = new PlSchemeWonInfo();
				plSchemeBet.setSchemeId(scheme.getId());
			}
			plSchemeBet.setWinUnit(plWinUnit);
			schemeManager.savePlSchemeWonInfo(plSchemeBet);
			try {
				scheme.doUpdateResult(plWinUnit.getWinItemList());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage(), e);
			}

			PlPasscount plPasscount = schemeManager.getPlPasscount(scheme.getId());
			if (plPasscount == null) {
				plPasscount = new PlPasscount();
			}
			super.setSchemePasscountInstance(scheme, plPasscount);
			plPasscount.setPlayType(scheme.getPlayType());
			plPasscount.setWinUnit(plWinUnit);
			schemeManager.savePlPasscount(plPasscount);
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
									ticketPrizeWork = new PrizeWork(getCompoundContent(ticket.getContent()), periodData.getResult(), ticket.getMultiple(),scheme.getPlayType());
								} else {
									String content = ticket.getContent();
									if(content.indexOf(",")!=-1){
										content = content.replaceAll(",","\r\n");
									}
									ticketPrizeWork = new PrizeWork(content, periodData.getResult(), ticket.getMultiple(),
											scheme.getPlayType());
								}
								PlWinUnit ticketPrizeUnit = ticketPrizeWork.getPlWinUnit();
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
			PlPasscount plPasscount = schemeManager.getPlPasscount(scheme.getId());
			if (plPasscount != null) {
				schemeManager.deletePlPasscount(plPasscount);
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
	public Collection<PlCompoundContent> getCompoundContent(String ticketContent) {
		Collection<PlCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				PlCompoundContent.class);
		return c;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.PL;
	}
}
