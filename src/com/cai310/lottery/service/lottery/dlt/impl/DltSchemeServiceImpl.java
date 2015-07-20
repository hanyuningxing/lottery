package com.cai310.lottery.service.lottery.dlt.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dto.lottery.dlt.DltSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.dlt.DltPasscount;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.entity.lottery.dlt.DltSchemeWonInfo;
import com.cai310.lottery.entity.lottery.dlt.DltWinUnit;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.dlt.PrizeWork;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.NumberSchemeEntityManager;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.impl.NumberSchemeServiceImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.PrintWonItem;
import com.cai310.lottery.support.PrizeItem;
import com.cai310.lottery.support.dlt.DltCompoundContent;
import com.cai310.lottery.utils.BigDecimalUtil;

@Service("dltSchemeServiceImpl")
@Transactional
public class DltSchemeServiceImpl extends NumberSchemeServiceImpl<DltScheme, DltSchemeDTO> {

	@Resource
	protected TicketEntityManager ticketEntityManager;
	
	@Autowired
	private DltSchemeEntityManagerImpl schemeManager;

	@Autowired
	private DltPeriodDataEntityManagerImpl periodDataManagerImpl;

	@Override
	protected NumberSchemeEntityManager<DltScheme> getSchemeEntityManager() {
		return schemeManager;
	}
	
	@Resource
	protected PrintEntityManager printEntityManager;
	protected DltScheme newSchemeInstance(DltSchemeDTO schemeDTO) {
		DltScheme scheme = super.newSchemeInstance(schemeDTO);
		// //附加
		scheme.setPlayType(schemeDTO.getPlayType());
		return scheme;
	}

	public void updatePrize(long schemeId) {
		DltScheme scheme = schemeManager.getScheme(schemeId);
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

		DltPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
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
		DltWinUnit winUnit = null;
		DltSchemeWonInfo bet = this.schemeManager.getDltSchemeWonInfo(scheme.getId());
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
		DltScheme scheme = this.schemeManager.getScheme(schemeId);
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
		DltPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (StringUtils.isBlank(periodData.getResult())) {
			throw new ServiceException("开奖结果为空！");
		}

		if (scheme.getMode() == SalesMode.COMPOUND) {
			this.updateCompoundResult(scheme, periodData);
		} else {
			this.updateSingleResult(scheme, periodData);
		}
	}
	
	protected void updateSingleResult(DltScheme scheme, DltPeriodData periodData) {
		PrizeWork prizeWork = null;
		try {
			prizeWork = new PrizeWork(scheme.getContent(), periodData.getResult(), scheme.getMultiple(),
					scheme.getPlayType());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		this.updateResult(prizeWork, scheme, periodData);
	}

	protected void updateCompoundResult(DltScheme scheme, DltPeriodData periodData) {
		PrizeWork prizeWork = null;
		try {
			prizeWork = new PrizeWork(scheme.getCompoundContent(), periodData.getResult(), scheme.getMultiple(),
					scheme.getPlayType());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}

		this.updateResult(prizeWork, scheme, periodData);
	}

	protected void updateResult(PrizeWork prizeWork, DltScheme scheme, DltPeriodData periodData) {
		DltWinUnit dltWinUnit = prizeWork.getDltWinUnit();

		if (dltWinUnit.isWon()) {
			DltSchemeWonInfo dltSchemeBet = schemeManager.getDltSchemeWonInfo(scheme.getId());
			if (dltSchemeBet == null) {
				dltSchemeBet = new DltSchemeWonInfo();
				dltSchemeBet.setSchemeId(scheme.getId());
			}
			dltSchemeBet.setWinUnit(dltWinUnit);
			schemeManager.saveDltSchemeWonInfo(dltSchemeBet);
			try {
				scheme.doUpdateResult(dltWinUnit.getWinItemList());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage(), e);
			}

			DltPasscount dltPasscount = schemeManager.getDltPasscount(scheme.getId());
			if (dltPasscount == null) {
				dltPasscount = new DltPasscount();
			}
			super.setSchemePasscountInstance(scheme, dltPasscount);
			dltPasscount.setPlayType(scheme.getPlayType());
			dltPasscount.setWinUnit(dltWinUnit);
			schemeManager.saveDltPasscount(dltPasscount);
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
									ticketPrizeWork = new PrizeWork(getCompoundContent(ticket.getContent()), periodData.getResult(), ticket.getMultiple(),
												scheme.getPlayType());
								} else {
									String content = ticket.getContent();
									if(content.indexOf(",")!=-1){
										content = content.replaceAll(",","\r\n");
									}
									ticketPrizeWork = new PrizeWork(content, periodData.getResult(), ticket.getMultiple(),
											scheme.getPlayType());
								}
								DltWinUnit ticketPrizeUnit = ticketPrizeWork.getDltWinUnit();
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
			DltPasscount dltPasscount = schemeManager.getDltPasscount(scheme.getId());
			if (dltPasscount != null) {
				schemeManager.deleteDltPasscount(dltPasscount);
				// 扣除战绩
			}

		}

		scheme = this.schemeManager.saveScheme(scheme);
	}
//	@PostConstruct修补出票同步失败的方法。去除
//	public void updateTicketPrize() {
//		logger.error("开始=====================");
//		Long periodId = 7L;
//		Period period = this.periodManager.getPeriod(periodId);
//		if (!period.isDrawed()) {
//			logger.error("还未开奖，不能执行更新中奖");
//			return;
//		}
//		DltPeriodData periodData = periodDataManagerImpl.getPeriodData(periodId);
//		if (StringUtils.isBlank(periodData.getResult())) {
//			logger.error("开奖结果为空");
//			return;
//		}
//		List<Long> schemeList = schemeManager.findSchemeIdOfCanDeliveWinRecord(periodId);
//		for (Long  schemeId: schemeList) {
//			DltScheme scheme = schemeManager.getScheme(schemeId);
//			//出票回查
//			PrintInterface printInterface = printEntityManager.findPrintInterfaceBy(scheme.getSchemeNumber(), scheme.getLotteryType());
//			if(null!=printInterface){
//					List<Long> ticketIds = ticketEntityManager.findTicketIdByPrintInterfaceId(printInterface.getId());
//					Ticket ticket = null;
//					for (Long id : ticketIds) {
//						 ticket = ticketEntityManager.getTicket(id);
//						 ticket.setChecked(true);
//						 if(SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())){
//							///方案成功才更新出票
//							PrizeWork ticketPrizeWork = null;
//							try {
//								if (scheme.getMode() == SalesMode.COMPOUND) {
//									ticketPrizeWork = new PrizeWork(getCompoundContent(ticket.getContent()), periodData.getResult(), ticket.getMultiple(),
//												scheme.getPlayType());
//								} else {
//									ticketPrizeWork = new PrizeWork(ticket.getContent(), periodData.getResult(), ticket.getMultiple(),
//											scheme.getPlayType());
//								}
//								DltWinUnit ticketPrizeUnit = ticketPrizeWork.getDltWinUnit();
//								if(ticketPrizeUnit.isWon()){
//									//
//									ticket.setWon(true);
//									ticket.setTicket_synchroned(Boolean.FALSE);
//									doUpdateTicketPrize(periodData.getPrize().getPrizeItemList(ticketPrizeUnit),ticket);
//									ticketEntityManager.saveTicket(ticket);
//								}
//							} catch (DataException e) {
//								e.printStackTrace();
//							}
//						 }
//						
//					}
//			}
//		}
//	}

	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<DltCompoundContent> getCompoundContent(String ticketContent) {
		Collection<DltCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				DltCompoundContent.class);
		return c;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.DLT;
	}
}
