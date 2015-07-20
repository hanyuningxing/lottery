package com.cai310.lottery.service.lottery.keno.xjel11to5.impl;

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
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.keno.SdEl11to5PrizeWork;
import com.cai310.lottery.prizeutils.keno.XjEl11to5PrizeWork;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoPlayerImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.sdel11to5.SdEl11to5WinUnits;
import com.cai310.lottery.support.xjel11to5.XjEl11to5CompoundContent;
import com.cai310.lottery.support.xjel11to5.XjEl11to5ContentBeanBuilder;
import com.cai310.lottery.support.xjel11to5.XjEl11to5PlayType;
import com.cai310.lottery.support.xjel11to5.XjEl11to5WinUnits;
import com.cai310.utils.DateUtil;

@Component("xjel11to5KenoPlayer")
public class XjEl11to5KenoPlayer extends KenoPlayerImpl<XjEl11to5IssueData, XjEl11to5Scheme> {
	@Override
	public Lottery getLottery() {
		return Lottery.XJEL11TO5;
	}

	@Resource(name = "xjel11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<XjEl11to5IssueData, XjEl11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}

	@Resource
	protected TicketEntityManager ticketEntityManager;
	
	@Resource
	protected PrintEntityManager printEntityManager;
	
	public void calculatePrice(XjEl11to5Scheme scheme, String results) throws DataException {
		XjEl11to5PrizeWork xjelPrizeWork = null;
		if (SalesMode.COMPOUND.equals(scheme.getMode())) {
			if (null != scheme.getCompoundContent()) {
				xjelPrizeWork = new XjEl11to5PrizeWork(scheme.getCompoundContent(), scheme.getMultiple(), results,
						scheme.getBetType());
			}
		} else if (SalesMode.SINGLE.equals(scheme.getMode())) {
			if (null != scheme.getContent()) {
				xjelPrizeWork = new XjEl11to5PrizeWork(scheme.getContent(), scheme.getMultiple(), results,
						scheme.getBetType());
			}
		}
		if (xjelPrizeWork.getxjEl11to5Win().isWon()) {
			// 更新
			scheme.setPrize(new BigDecimal(xjelPrizeWork.getxjEl11to5Win().getPrize()));
			scheme.setPrizeAfterTax(new BigDecimal(xjelPrizeWork.getxjEl11to5Win().getPrizeAfterTax()));
			scheme.setPrizeDetail(xjelPrizeWork.getxjEl11to5Win().getPrizeDetail());
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
							 XjEl11to5PrizeWork ticketPrizeWork = null;
							try {
								if (ticket.getMode() == SalesMode.COMPOUND) {
									ticketPrizeWork = new XjEl11to5PrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
											scheme.getBetType());
								} else {
									String content = ticket.getContent();
									if(content.indexOf(",")!=-1){
										content = content.replaceAll(",","\r\n");
									}
									ticketPrizeWork = new XjEl11to5PrizeWork(content, ticket.getMultiple(),results,
											scheme.getBetType());
								}
								XjEl11to5WinUnits ticketPrizeUnit = ticketPrizeWork.getxjEl11to5Win();
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
//		if(null!=prizeWork) {
//			if (prizeWork.getGdEl11to5Win().isWon()) {
//				// 更新
//				scheme.setPrize(new BigDecimal(prizeWork.getGdEl11to5Win().getPrize()));
//				scheme.setPrizeAfterTax(new BigDecimal(prizeWork.getGdEl11to5Win().getPrizeAfterTax()));
//				scheme.setPrizeDetail(prizeWork.getGdEl11to5Win().getPrizeDetail());
//				scheme.setWon(Boolean.TRUE);
//			}
//		}
	}

	@SuppressWarnings("unchecked")
	@Transient
	public Collection<XjEl11to5CompoundContent> getCompoundContent(String ticketContent) {
		Collection<XjEl11to5CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				XjEl11to5CompoundContent.class);
		return c;
	}
	
	public boolean calculatePrice(SalesMode salesMode, String content, Integer multiple, String betType, String results)
			throws DataException {
		XjEl11to5PrizeWork prizeWork = null;
		if (SalesMode.COMPOUND.equals(salesMode)) {
			prizeWork = new XjEl11to5PrizeWork(JSONArray.toCollection(JSONArray.fromObject(content),
					XjEl11to5CompoundContent.class), multiple, results, XjEl11to5PlayType.valueOf((betType)));
		} else if (SalesMode.SINGLE.equals(salesMode)) {
			prizeWork = new XjEl11to5PrizeWork(content, multiple, results, XjEl11to5PlayType.valueOf(betType));
		}
		if (prizeWork.getxjEl11to5Win().isWon()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void calculateSchemeUnits(XjEl11to5Scheme scheme) throws DataException {
		XjEl11to5PlayType xjel11to5PlayType = scheme.getBetType();
		ContentBean contentBean = XjEl11to5ContentBeanBuilder.buildCompoundContentBean(scheme.getContent(),
				xjel11to5PlayType);
		scheme.setUnits(contentBean.getUnits());

	}

	public XjEl11to5IssueData createNowIssueData(String issueNumber) {
		return new XjEl11to5IssueData();
	}

	public Date getBeginTimeByIssueNumber(String issueNumber) {
		String strDate = issueNumber.substring(0, 8);
		String strPeriod = issueNumber.substring(8, 10);
		int period = Integer.valueOf(strPeriod);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.strToDate(strDate, "yyyyMMdd"));
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 50);
		cal.add(Calendar.MINUTE, (period - 1) * 10);
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
		return 97;
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
	public void resetPrice(XjEl11to5Scheme scheme, String results, Ticket ticket)
			throws DataException {
		XjEl11to5PrizeWork ticketPrizeWork = null;
		try {
			if (ticket.getMode() == SalesMode.COMPOUND) {
				ticketPrizeWork = new XjEl11to5PrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
						scheme.getBetType());
			} else {
				String content = ticket.getContent();
				if(content.indexOf(",")!=-1){
					content = content.replaceAll(",","\r\n");
				}
				ticketPrizeWork = new XjEl11to5PrizeWork(content, ticket.getMultiple(),results,
						scheme.getBetType());
			}
			XjEl11to5WinUnits ticketPrizeUnit = ticketPrizeWork.getxjEl11to5Win();
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
