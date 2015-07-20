package com.cai310.lottery.service.lottery.seven.impl;

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
import com.cai310.lottery.dto.lottery.seven.SevenSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.seven.SevenPasscount;
import com.cai310.lottery.entity.lottery.seven.SevenPeriodData;
import com.cai310.lottery.entity.lottery.seven.SevenScheme;
import com.cai310.lottery.entity.lottery.seven.SevenSchemeWonInfo;
import com.cai310.lottery.entity.lottery.seven.SevenWinUnit;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.seven.PrizeWork;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.NumberSchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.NumberSchemeServiceImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.seven.SevenCompoundContent;

@Service("sevenSchemeServiceImpl")
@Transactional
public class SevenSchemeServiceImpl extends NumberSchemeServiceImpl<SevenScheme, SevenSchemeDTO> {

	@Autowired
	private SevenSchemeEntityManagerImpl schemeManager;

	@Autowired
	private SevenPeriodDataEntityManagerImpl periodDataManagerImpl;
	@Resource
	protected TicketEntityManager ticketEntityManager;
	@Override
	protected NumberSchemeEntityManager<SevenScheme> getSchemeEntityManager() {
		return schemeManager;
	}

	public void updatePrize(long schemeId) {
		SevenScheme scheme = schemeManager.getScheme(schemeId);
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

		SevenPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
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
		SevenWinUnit winUnit = null;
		SevenSchemeWonInfo bet = this.schemeManager.getSchemeBet(scheme.getId());
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
		SevenScheme scheme = this.schemeManager.getScheme(schemeId);
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
		SevenPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (null == periodData || StringUtils.isBlank(periodData.getResult())) {
			throw new ServiceException("开奖结果为空！");
		}

		if (scheme.getMode() == SalesMode.COMPOUND) {
			this.updateCompoundResult(scheme, periodData);
		} else {
			this.updateSingleResult(scheme, periodData);
		}
	}

	protected void updateSingleResult(SevenScheme scheme, SevenPeriodData periodData) {
		PrizeWork prizeWork = null;
		try {
			prizeWork = new PrizeWork(scheme.getContent(), periodData.getResult(), scheme.getMultiple());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		this.updateResult(prizeWork, scheme, periodData);
	}

	protected void updateCompoundResult(SevenScheme scheme, SevenPeriodData periodData) {
		PrizeWork prizeWork = null;
		try {
			prizeWork = new PrizeWork(scheme.getCompoundContent(), periodData.getResult(), scheme.getMultiple());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		this.updateResult(prizeWork, scheme, periodData);
	}

	private void updateResult(PrizeWork prizeWork, SevenScheme scheme, SevenPeriodData periodData) {
		SevenWinUnit sevenWinUnit = prizeWork.getSevenWinUnit();
		if (sevenWinUnit.isWon()) {
			SevenSchemeWonInfo sevenSchemeWonInfo = schemeManager.getSchemeBet(scheme.getId());
			if (sevenSchemeWonInfo == null) {
				sevenSchemeWonInfo = new SevenSchemeWonInfo();
				sevenSchemeWonInfo.setSchemeId(scheme.getId());
			}
			sevenSchemeWonInfo.setWinUnit(sevenWinUnit);
			schemeManager.saveSevenSchemeWonInfo(sevenSchemeWonInfo);
			try {
				scheme.doUpdateResult(sevenWinUnit.getWinItemList());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage(), e);
			}

			SevenPasscount sevenPasscount = schemeManager.getSevenPasscount(scheme.getId());
			if (sevenPasscount == null) {
				sevenPasscount = new SevenPasscount();
			}
			super.setSchemePasscountInstance(scheme, sevenPasscount);
			sevenPasscount.setWinUnit(sevenWinUnit);
			schemeManager.saveSevenPasscount(sevenPasscount);
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
								SevenWinUnit ticketPrizeUnit = ticketPrizeWork.getSevenWinUnit();
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
			SevenPasscount sevenPasscount = schemeManager.getSevenPasscount(scheme.getId());
			if (sevenPasscount != null) {
				schemeManager.deleteSevenPasscount(sevenPasscount);
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
	public Collection<SevenCompoundContent> getCompoundContent(String ticketContent) {
		Collection<SevenCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				SevenCompoundContent.class);
		return c;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVEN;
	}

}
