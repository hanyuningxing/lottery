package com.cai310.lottery.service.lottery.keno.klsf.impl;

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
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfIssueData;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfScheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.keno.KlsfPrizeWork;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoPlayerImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.klsf.KlsfCompoundContent;
import com.cai310.lottery.support.klsf.KlsfContentBeanBuilder;
import com.cai310.lottery.support.klsf.KlsfPlayType;
import com.cai310.lottery.support.klsf.KlsfWinUnits;
import com.cai310.utils.DateUtil;

@Component("klsfKenoPlayer")
public class KlsfKenoPlayer extends KenoPlayerImpl<KlsfIssueData, KlsfScheme> {
	@Override
	public Lottery getLottery() {
		return Lottery.KLSF;
	}
	@Resource
	protected PrintEntityManager printEntityManager;
	@Resource
	protected TicketEntityManager ticketEntityManager;
	@Resource(name = "klsfKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlsfIssueData, KlsfScheme> kenoService) {
		this.kenoService = kenoService;
	}

	public void calculatePrice(KlsfScheme scheme, String results) throws DataException {
		KlsfPrizeWork klsfPrizeWork = null;
		if(SalesMode.COMPOUND.equals(scheme.getMode())){
			klsfPrizeWork = new KlsfPrizeWork(scheme.getCompoundContent(), scheme.getMultiple(), results,scheme.getBetType());
		}else if(SalesMode.SINGLE.equals(scheme.getMode())){
			klsfPrizeWork = new KlsfPrizeWork(scheme.getContent(), scheme.getMultiple(), results,scheme.getBetType());
		}
		if (klsfPrizeWork.getKlsfWin().isWon()) {
			// 更新
			scheme.setPrize(new BigDecimal(klsfPrizeWork.getKlsfWin().getPrize()));
			scheme.setPrizeAfterTax(new BigDecimal(klsfPrizeWork.getKlsfWin().getPrizeAfterTax()));
			scheme.setPrizeDetail(klsfPrizeWork.getKlsfWin().getPrizeDetail());
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
							 KlsfPrizeWork ticketPrizeWork = null;
							try {
								if (ticket.getMode() == SalesMode.COMPOUND) {
									ticketPrizeWork = new KlsfPrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
											scheme.getBetType());
								} else {
									String content = ticket.getContent();
									if(content.indexOf(",")!=-1){
										content = content.replaceAll(",","\r\n");
									}
									ticketPrizeWork = new KlsfPrizeWork(content, ticket.getMultiple(),results,
											scheme.getBetType());
								}
								KlsfWinUnits ticketPrizeUnit = ticketPrizeWork.getKlsfWin();
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

	public void calculateSchemeUnits(KlsfScheme scheme) throws DataException {
		KlsfPlayType klsfPlayType = scheme.getBetType();
		ContentBean contentBean = KlsfContentBeanBuilder.buildCompoundContentBean(scheme.getContent(), klsfPlayType);
		scheme.setUnits(contentBean.getUnits());

	}

	public KlsfIssueData createNowIssueData(String issueNumber) {
		return new KlsfIssueData();
	}

	public Date getBeginTimeByIssueNumber(String issueNumber) {
		String strDate = issueNumber.substring(0, 8);
		String strPeriod = issueNumber.substring(8, 10);
		int period = Integer.valueOf(strPeriod);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.strToDate(strDate, "yyyyMMdd"));
		int startHour = 9;
		int offsetMinute = (period - 1) * 10;
		int currHour = startHour + (offsetMinute / 60);
		int leftMinute = offsetMinute % 60;
		cal.set(Calendar.HOUR_OF_DAY, currHour);
		cal.set(Calendar.MINUTE, leftMinute);
		return cal.getTime();
	}

	public String issueNumberAssembly(Date day, int period) {
		NumberFormat numFmt = new DecimalFormat("00");
		String nstr = numFmt.format(period);
		String dTimeStr = DateUtil.getFormatDate("yyyyMMdd", day);
		String issueNumber = dTimeStr + nstr;
		return issueNumber;
	}

	@Override
	public int getMaxPeriod() {
		return 84;
	}


	@Override
	public int getPeriodMinutes() {
		return 10;
	}

	@Override
	public boolean calculatePrice(SalesMode salesMode, String content,
			Integer multiple, String betType, String results)
			throws DataException {
		return false;
	}

	@Override
	public int getBeforeTime() {
		return 1;
	}

	@Override
	public int getBeforeSecondsTime() {
		return 30;
	}
	@Override
	public void resetPrice(KlsfScheme scheme, String results, Ticket ticket)
			throws DataException {
		KlsfPrizeWork ticketPrizeWork = null;
		try {
			if (ticket.getMode() == SalesMode.COMPOUND) {
				ticketPrizeWork = new KlsfPrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
						scheme.getBetType());
			} else {
				String content = ticket.getContent();
				if(content.indexOf(",")!=-1){
					content = content.replaceAll(",","\r\n");
				}
				ticketPrizeWork = new KlsfPrizeWork(content, ticket.getMultiple(),results,
						scheme.getBetType());
			}
			KlsfWinUnits ticketPrizeUnit = ticketPrizeWork.getKlsfWin();
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
	
	
	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<KlsfCompoundContent> getCompoundContent(String ticketContent) {
		Collection<KlsfCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				KlsfCompoundContent.class);
		return c;
	}
}
