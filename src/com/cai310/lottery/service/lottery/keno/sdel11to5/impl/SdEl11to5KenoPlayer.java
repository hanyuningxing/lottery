package com.cai310.lottery.service.lottery.keno.sdel11to5.impl;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.keno.SdEl11to5PrizeWork;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoPlayerImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.sdel11to5.SdEl11to5CompoundContent;
import com.cai310.lottery.support.sdel11to5.SdEl11to5ContentBeanBuilder;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.lottery.support.sdel11to5.SdEl11to5WinUnits;
import com.cai310.utils.DateUtil;

@Component("sdel11to5KenoPlayer")
public class SdEl11to5KenoPlayer extends KenoPlayerImpl<SdEl11to5IssueData, SdEl11to5Scheme> {
	
	@Resource
	protected TicketEntityManager ticketEntityManager;
	
	@Resource
	protected PrintEntityManager printEntityManager;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Lottery getLottery() {
		return Lottery.SDEL11TO5;
	}

	@Resource(name = "sdel11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<SdEl11to5IssueData, SdEl11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}

	public void calculatePrice(SdEl11to5Scheme scheme, String results) throws DataException {
		SdEl11to5PrizeWork sdelPrizeWork = null;
		if (SalesMode.COMPOUND.equals(scheme.getMode())) {
			if (null != scheme.getCompoundContent()) {
				sdelPrizeWork = new SdEl11to5PrizeWork(scheme.getCompoundContent(), scheme.getMultiple(), results,
						scheme.getBetType());
			}
		} else if (SalesMode.SINGLE.equals(scheme.getMode())) {
			if (null != scheme.getContent()) {
				sdelPrizeWork = new SdEl11to5PrizeWork(scheme.getContent(), scheme.getMultiple(), results,
						scheme.getBetType());
			}
		}
		if (sdelPrizeWork.getSdEl11to5Win().isWon()) {
			// 更新
			scheme.setPrize(new BigDecimal(sdelPrizeWork.getSdEl11to5Win().getPrize()));
			scheme.setPrizeAfterTax(new BigDecimal(sdelPrizeWork.getSdEl11to5Win().getPrizeAfterTax()));
			scheme.setPrizeDetail(sdelPrizeWork.getSdEl11to5Win().getPrizeDetail());
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
							 SdEl11to5PrizeWork ticketPrizeWork = null;
							try {
								if (ticket.getMode() == SalesMode.COMPOUND) {
									ticketPrizeWork = new SdEl11to5PrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
											scheme.getBetType());
								} else {
									String content = ticket.getContent();
									if(content.indexOf(",")!=-1){
										content = content.replaceAll(",","\r\n");
									}
									ticketPrizeWork = new SdEl11to5PrizeWork(content, ticket.getMultiple(),results,
											scheme.getBetType());
								}
								SdEl11to5WinUnits ticketPrizeUnit = ticketPrizeWork.getSdEl11to5Win();
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
		
//		if(null!=sdelPrizeWork) {
//			if (sdelPrizeWork.getSdEl11to5Win().isWon()) {
//				// 更新
//				scheme.setPrize(new BigDecimal(sdelPrizeWork.getSdEl11to5Win().getPrize()));
//				scheme.setPrizeAfterTax(new BigDecimal(sdelPrizeWork.getSdEl11to5Win().getPrizeAfterTax()));
//				scheme.setPrizeDetail(sdelPrizeWork.getSdEl11to5Win().getPrizeDetail());
//				scheme.setWon(Boolean.TRUE);
//			}
//		}
	}
	/**
	 * 返回复式内容解析后的集合
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Collection<SdEl11to5CompoundContent> getCompoundContent(String ticketContent) {
		Collection<SdEl11to5CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				SdEl11to5CompoundContent.class);
		return c;
	}
	public boolean calculatePrice(SalesMode salesMode, String content, Integer multiple, String betType, String results)
			throws DataException {
		SdEl11to5PrizeWork klsfPrizeWork = null;
		if (SalesMode.COMPOUND.equals(salesMode)) {
			klsfPrizeWork = new SdEl11to5PrizeWork(JSONArray.toCollection(JSONArray.fromObject(content),
					SdEl11to5CompoundContent.class), multiple, results, SdEl11to5PlayType.valueOf((betType)));
		} else if (SalesMode.SINGLE.equals(salesMode)) {
			klsfPrizeWork = new SdEl11to5PrizeWork(content, multiple, results, SdEl11to5PlayType.valueOf(betType));
		}
		if (klsfPrizeWork.getSdEl11to5Win().isWon()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void calculateSchemeUnits(SdEl11to5Scheme scheme) throws DataException {
		SdEl11to5PlayType sdel11to5PlayType = scheme.getBetType();
		ContentBean contentBean = SdEl11to5ContentBeanBuilder.buildCompoundContentBean(scheme.getContent(),
				sdel11to5PlayType);
		scheme.setUnits(contentBean.getUnits());

	}

	public SdEl11to5IssueData createNowIssueData(String issueNumber) {
		return new SdEl11to5IssueData();
	}

	public Date getBeginTimeByIssueNumber(String issueNumber) {
		String strDate = issueNumber.substring(0, 8);//2014062078
		String strPeriod = issueNumber.substring(8, 10);
		int period = Integer.valueOf(strPeriod);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.strToDate(strDate, "yyyyMMdd"));
		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 55);
		cal.set(Calendar.SECOND, 20);
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
		return 78;
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
	public void resetPrice(SdEl11to5Scheme scheme, String results, Ticket ticket)
			throws DataException {
		SdEl11to5PrizeWork ticketPrizeWork = null;
		try {
			if (ticket.getMode() == SalesMode.COMPOUND) {
				ticketPrizeWork = new SdEl11to5PrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
						scheme.getBetType());
			} else {
				String content = ticket.getContent();
				if(content.indexOf(",")!=-1){
					content = content.replaceAll(",","\r\n");
				}
				ticketPrizeWork = new SdEl11to5PrizeWork(content, ticket.getMultiple(),results,
						scheme.getBetType());
			}
			SdEl11to5WinUnits ticketPrizeUnit = ticketPrizeWork.getSdEl11to5Win();
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
