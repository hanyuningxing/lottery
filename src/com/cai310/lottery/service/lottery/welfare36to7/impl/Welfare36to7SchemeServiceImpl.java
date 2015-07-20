package com.cai310.lottery.service.lottery.welfare36to7.impl;

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
import com.cai310.lottery.dto.lottery.welfare36to7.Welfare36to7SchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Passcount;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7PeriodData;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Scheme;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7SchemeWonInfo;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7WinUnit;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.welfare36to7.PrizeWork;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.NumberSchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.NumberSchemeServiceImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.welfare36to7.Welfare36to7CompoundContent;

@Service("welfare36to7SchemeServiceImpl")
@Transactional
public class Welfare36to7SchemeServiceImpl extends NumberSchemeServiceImpl<Welfare36to7Scheme, Welfare36to7SchemeDTO> {

	@Resource
	protected TicketEntityManager ticketEntityManager;
	
	@Autowired
	private Welfare36to7SchemeEntityManagerImpl schemeManager;

	@Autowired
	private Welfare36to7PeriodDataEntityManagerImpl periodDataManagerImpl;

	@Override
	protected NumberSchemeEntityManager<Welfare36to7Scheme> getSchemeEntityManager() {
		return schemeManager;
	}

	@Override
	public Welfare36to7Scheme newSchemeInstance(Welfare36to7SchemeDTO schemeDTO) {
		Welfare36to7Scheme scheme = super.newSchemeInstance(schemeDTO);
		scheme.setPlayType(schemeDTO.getPlayType());
		return scheme;
	}

	public void updatePrize(long schemeId) {
		Welfare36to7Scheme scheme = schemeManager.getScheme(schemeId);
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

		Welfare36to7PeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (periodData.getPrize() == null) {
			throw new ServiceException("未设置中奖奖金！");
		}
		try {
			periodData.getPrize().checkPrize();
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		Welfare36to7WinUnit winUnit = null;
		Welfare36to7SchemeWonInfo bet = this.schemeManager.getSchemeBet(scheme.getId());
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
		Welfare36to7Scheme scheme = this.schemeManager.getScheme(schemeId);
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
		Welfare36to7PeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
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

	protected void updateSingleResult(Welfare36to7Scheme scheme, Welfare36to7PeriodData periodData) {
		PrizeWork prizeWork = null;
		try {
			prizeWork = new PrizeWork(scheme.getContent(), periodData.getResult(), scheme.getMultiple(),
					scheme.getPlayType());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		this.updateResult(prizeWork, scheme, periodData);
	}

	protected void updateCompoundResult(Welfare36to7Scheme scheme, Welfare36to7PeriodData periodData) {
		PrizeWork prizeWork = null;
		try {
			prizeWork = new PrizeWork(scheme.getCompoundContent(), periodData.getResult(), scheme.getMultiple(),
					scheme.getPlayType());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		this.updateResult(prizeWork, scheme, periodData);
	}

	protected void updateResult(PrizeWork prizeWork, Welfare36to7Scheme scheme, Welfare36to7PeriodData periodData) {
		Welfare36to7WinUnit Welfare36to7WinUnit = prizeWork.getWelfare36to7WinUnit();
		if (Welfare36to7WinUnit.isWon()) {
			// 只统计中奖方案，不中奖 的删掉
			Welfare36to7SchemeWonInfo welfare36to7SchemeWonInfo = schemeManager.getSchemeBet(scheme.getId());
			if (welfare36to7SchemeWonInfo == null) {
				welfare36to7SchemeWonInfo = new Welfare36to7SchemeWonInfo();
			}
			welfare36to7SchemeWonInfo.setSchemeId(scheme.getId());
			welfare36to7SchemeWonInfo.setWinUnit(Welfare36to7WinUnit);
			schemeManager.saveWelfare36to7SchemeWonInfo(welfare36to7SchemeWonInfo);
			try {
				scheme.doUpdateResult(Welfare36to7WinUnit.getWinItemList());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage(), e);
			}

			Welfare36to7Passcount welfare36to7Passcount = schemeManager.getWelfare36to7Passcount(scheme.getId());
			if (welfare36to7Passcount == null) {
				welfare36to7Passcount = new Welfare36to7Passcount();
			}
			super.setSchemePasscountInstance(scheme, welfare36to7Passcount);
			welfare36to7Passcount.setWinUnit(Welfare36to7WinUnit);
			welfare36to7Passcount.setPlayType(scheme.getPlayType());
			schemeManager.saveWelfare36to7Passcount(welfare36to7Passcount);
			
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

									ticketPrizeWork = new PrizeWork(content, periodData.getResult(), ticket.getMultiple(),scheme.getPlayType());
								}
								Welfare36to7WinUnit ticketPrizeUnit = ticketPrizeWork.getWelfare36to7WinUnit();
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
			Welfare36to7Passcount welfare36to7Passcount = schemeManager.getWelfare36to7Passcount(scheme.getId());
			if (welfare36to7Passcount != null) {
				schemeManager.deleteWelfare36to7Passcount(welfare36to7Passcount);
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
	public Collection<Welfare36to7CompoundContent> getCompoundContent(String ticketContent) {
		Collection<Welfare36to7CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				Welfare36to7CompoundContent.class);
		return c;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}
}
