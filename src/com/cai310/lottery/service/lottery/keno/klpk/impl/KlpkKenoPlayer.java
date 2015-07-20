package com.cai310.lottery.service.lottery.keno.klpk.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkIssueData;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.keno.KlpkPrizeWork;
import com.cai310.lottery.prizeutils.keno.KlpkPrizeWork;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoPlayerImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.klpk.KlpkCompoundContent;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.lottery.support.klpk.KlpkWinUnits;
import com.cai310.lottery.support.klpk.KlpkCompoundContent;
import com.cai310.lottery.support.klpk.KlpkContentBeanBuilder;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.utils.DateUtil;

@Component("klpkKenoPlayer")
public class KlpkKenoPlayer extends KenoPlayerImpl<KlpkIssueData, KlpkScheme> {
	@Resource
	protected TicketEntityManager ticketEntityManager;
	@Override
	public Lottery getLottery() {
		return Lottery.KLPK;
	}
	@Resource
	protected PrintEntityManager printEntityManager;
	@Resource(name = "klpkKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlpkIssueData, KlpkScheme> kenoService) {
		this.kenoService = kenoService;
	}

	public void calculatePrice(KlpkScheme scheme, String results) throws DataException {
		KlpkPrizeWork klsfPrizeWork = null;
//		if (SalesMode.COMPOUND.equals(scheme.getMode())) {
			if (null != scheme.getCompoundContent()) {
				klsfPrizeWork = new KlpkPrizeWork(scheme.getCompoundContent(), scheme.getMultiple(), results,
						scheme.getBetType());
			}
//		} 
		if(null!=klsfPrizeWork) {
			if (klsfPrizeWork.getKlpkWin().isWon()) {
				// 更新
				scheme.setPrize(new BigDecimal(klsfPrizeWork.getKlpkWin().getPrize()));
				scheme.setPrizeAfterTax(new BigDecimal(klsfPrizeWork.getKlpkWin().getPrizeAfterTax()));
				scheme.setPrizeDetail(klsfPrizeWork.getKlpkWin().getPrizeDetail());
				scheme.setWon(Boolean.TRUE);
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
								 KlpkPrizeWork ticketPrizeWork = null;
								try {
//									if (ticket.getMode() == SalesMode.COMPOUND) {
										ticketPrizeWork = new KlpkPrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
												scheme.getBetType());
//									} 
									KlpkWinUnits ticketPrizeUnit = ticketPrizeWork.getKlpkWin();
									if(ticketPrizeUnit.isWon()){
										//
										ticket.setWon(true);
										ticket.setTicket_synchroned(Boolean.FALSE);
										ticket.setTotalPrize(ticketPrizeUnit.getPrize());
										ticket.setTotalPrizeAfterTax(ticketPrizeUnit.getPrizeAfterTax());
										ticket.setWonDetail(ticketPrizeUnit.getPrizeDetail());
										ticketEntityManager.saveTicket(ticket);
									}
								} catch (DataException e) {
									e.printStackTrace();
								}
							 }
							
						}
				}
			}
		}
	}
	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<KlpkCompoundContent> getCompoundContent(String ticketContent) {
		Collection<KlpkCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				KlpkCompoundContent.class);
		return c;
	}
	public boolean calculatePrice(SalesMode salesMode, String content, Integer multiple, String betType, String results)
			throws DataException {
		KlpkPrizeWork klsfPrizeWork = null;
		if (SalesMode.COMPOUND.equals(salesMode)) {
			klsfPrizeWork = new KlpkPrizeWork(JSONArray.toCollection(JSONArray.fromObject(content),
					KlpkCompoundContent.class), multiple, results, KlpkPlayType.valueOf((betType)));
		} 
		if (klsfPrizeWork.getKlpkWin().isWon()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void calculateSchemeUnits(KlpkScheme scheme) throws DataException {
		KlpkPlayType klpkPlayType = scheme.getBetType();
		ContentBean contentBean = KlpkContentBeanBuilder.buildCompoundContentBean(scheme.getContent(),
				klpkPlayType);
		scheme.setUnits(contentBean.getUnits());

	}

	public KlpkIssueData createNowIssueData(String issueNumber) {
		return new KlpkIssueData();
	}

	public Date getBeginTimeByIssueNumber(String issueNumber) {
		String strDate = issueNumber.substring(0, 6);
		String strPeriod = issueNumber.substring(6, 8);
		int period = Integer.valueOf(strPeriod);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.strToDate(strDate, "yyMMdd"));
		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 50);
		cal.add(Calendar.MINUTE, (period - 1) * 10);
		return cal.getTime();
	}

	public String issueNumberAssembly(Date day, int period) {
		NumberFormat numFmt = new DecimalFormat("00");
		String nstr = numFmt.format(period);
		String dTimeStr = DateUtil.getFormatDate("yyMMdd", day);
		String issueNumber = dTimeStr + nstr;
		return issueNumber;
	}

	@Override
	public int getMaxPeriod() {
		return 79;
	}

	@Override
	public int getPeriodMinutes() {
		return 10;
	}

	@Override
	public int getBeforeTime() {
		return 1;
	}
	@Override
	public int getBeforeSecondsTime() {
		return 0;
	}

	@Override
	public void resetPrice(KlpkScheme scheme, String results, Ticket ticket)
			throws DataException {
		KlpkPrizeWork ticketPrizeWork = null;
		try {
			if (ticket.getMode() == SalesMode.COMPOUND) {
				ticketPrizeWork = new KlpkPrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
						scheme.getBetType());
			} 
			KlpkWinUnits ticketPrizeUnit = ticketPrizeWork.getKlpkWin();
			if(ticketPrizeUnit.isWon()){
				ticket.setChecked(true);
				ticket.setWon(true);
				ticket.setTicket_synchroned(Boolean.FALSE);
				ticket.setTotalPrize(scheme.getPrize().doubleValue());
				ticket.setTotalPrizeAfterTax(scheme.getPrizeAfterTax().doubleValue());
				ticket.setWonDetail(scheme.getPrizeDetail());
				ticketEntityManager.saveTicket(ticket);
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
		
	}
}
