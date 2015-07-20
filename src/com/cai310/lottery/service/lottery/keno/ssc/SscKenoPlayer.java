package com.cai310.lottery.service.lottery.keno.ssc;

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

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.keno.SscPrizeWork;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoPlayerImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.ssc.SscCompoundContent;
import com.cai310.lottery.support.ssc.SscContentBeanBuilder;
import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.lottery.support.ssc.SscWinUnits;
import com.cai310.utils.DateUtil;

@Component("sscKenoPlayer")
public class SscKenoPlayer extends KenoPlayerImpl<SscIssueData, SscScheme> {
	@Override
	public Lottery getLottery() {
		return Lottery.SSC;
	}
	@Resource
	protected PrintEntityManager printEntityManager;
	@Resource
	protected TicketEntityManager ticketEntityManager;
	@Resource(name = "sscKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<SscIssueData, SscScheme> kenoService) {
		this.kenoService = kenoService;
	}

	public void calculatePrice(SscScheme scheme, String results) throws DataException {
		SscPrizeWork prizeWork = null;
		if (SalesMode.COMPOUND.equals(scheme.getMode())) {
			prizeWork = new SscPrizeWork(scheme.getCompoundContent(), scheme.getMultiple(), results,
					scheme.getBetType());
		} else if (SalesMode.SINGLE.equals(scheme.getMode())) {
			prizeWork = new SscPrizeWork(scheme.getContent(), scheme.getMultiple(), results, scheme.getBetType());
		}
		if (prizeWork.getSscWin().isWon()) {
			// 更新
			scheme.setPrize(new BigDecimal(prizeWork.getSscWin().getPrize()));
			scheme.setPrizeAfterTax(new BigDecimal(prizeWork.getSscWin().getPrizeAfterTax()));
			scheme.setPrizeDetail(prizeWork.getSscWin().getPrizeDetail());
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
							 SscPrizeWork ticketPrizeWork = null;
							try {
								if (ticket.getMode() == SalesMode.COMPOUND) {
									ticketPrizeWork = new SscPrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
											scheme.getBetType());
								} else {
									String content = ticket.getContent();
									if(content.indexOf(",")!=-1){
										content = content.replaceAll(",","\r\n");
									}
									ticketPrizeWork = new SscPrizeWork(content, ticket.getMultiple(),results,
											scheme.getBetType());
								}
								SscWinUnits ticketPrizeUnit = ticketPrizeWork.getSscWin();
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

	public void calculateSchemeUnits(SscScheme scheme) throws DataException {
		SscPlayType sscPlayType = scheme.getBetType();
		ContentBean contentBean = SscContentBeanBuilder.buildCompoundContentBean(scheme.getContent(), sscPlayType);
		scheme.setUnits(contentBean.getUnits());

	}

	public SscIssueData createNowIssueData(String issueNumber) {
		return new SscIssueData();
	}

	//开奖时间：白天10点至22点 夜场22点至凌晨2点 	开奖频率：白天10分钟 夜场5分钟
	//每日期数：白天72期，夜场48期，共120期 	返奖率：50%
	
	
	public  Date getBeginTimeByIssueNumber(String issueNumber) {
		String strDate = issueNumber.substring(0, 8);
		String strPeriod = issueNumber.substring(8, 11);
		int period = Integer.valueOf(strPeriod);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.strToDate(strDate, "yyyyMMdd"));
		if (period <= 24) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.add(Calendar.MINUTE, (period-1) * 5);
		} else if (period <= 96&&period>24){
			cal.set(Calendar.HOUR_OF_DAY, 10);
			cal.set(Calendar.MINUTE, 0);
			cal.add(Calendar.MINUTE, (period -25) * 10);
		} if (period > 96){
			cal.set(Calendar.HOUR_OF_DAY, 22);
			cal.set(Calendar.MINUTE, 0);
			cal.add(Calendar.MINUTE, (period -97) * 5);
		}
		return cal.getTime();
	}

	public String issueNumberAssembly(Date day, int period) {
		NumberFormat numFmt = new DecimalFormat("000");
		String nstr = numFmt.format(period);
		String dTimeStr = DateUtil.getFormatDate("yyyyMMdd", day);
		String issueNumber = dTimeStr + nstr;
		return issueNumber;
	}

	@Override
	public int getMaxPeriod() {
		return 120;
	}

	@Override
	public int getPeriodMinutes() {
		return 5;
	}
	/*
	 *开奖时间：白天10点至22点 夜场22点至凌晨2点 	开奖频率：白天10分钟 夜场5分钟
	 *每日期数：白天72期，夜场48期，共120期 	返奖率：50% 
	 *24期1：55--10：00 485分钟
	 *
	 **/
	public int getPeriodMinutes(String issueNumber) {
		String strPeriod = issueNumber.substring(8, 11);
		int period = Integer.valueOf(strPeriod);
		if (period < 24) {
			return 5;
		} 
		else if (period == 24) {
			return 485;
		}
		 else if (period <= 96&&period>24){
			return 10;
		} else {
			return 5;
		}
	}

	@Override    
	public int getBeforeTime() {
		return 1;
	}

	@Override
	public boolean calculatePrice(SalesMode salesMode, String content, Integer multiple, String betType, String results)
			throws DataException {
		SscPrizeWork prizeWork = null;
		if (SalesMode.COMPOUND.equals(salesMode)) {
			prizeWork = new SscPrizeWork(JSONArray.toCollection(JSONArray.fromObject(content),
					SscCompoundContent.class), multiple, results, SscPlayType.valueOf((betType)));
		} else if (SalesMode.SINGLE.equals(salesMode)) {
			prizeWork = new SscPrizeWork(content, multiple, results, SscPlayType.valueOf(betType));
		}
		if (prizeWork.getSscWin().isWon()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public Date getEndTimeByIssueNumber(String issueNumber) {
		return DateUtils.addMinutes(this.getBeginTimeByIssueNumber(issueNumber), this.getPeriodMinutes(issueNumber));
	}
	
	@Override 
	public int getBeforeSecondsTime() {
		return 0;
	}
	@Override
	public void resetPrice(SscScheme scheme, String results, Ticket ticket)
			throws DataException {
		SscPrizeWork ticketPrizeWork = null;
		try {
			if (ticket.getMode() == SalesMode.COMPOUND) {
				ticketPrizeWork = new SscPrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
						scheme.getBetType());
			} else {
				String content = ticket.getContent();
				if(content.indexOf(",")!=-1){
					content = content.replaceAll(",","\r\n");
				}
				ticketPrizeWork = new SscPrizeWork(content, ticket.getMultiple(),results,
						scheme.getBetType());
			}
			SscWinUnits ticketPrizeUnit = ticketPrizeWork.getSscWin();
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
	public Collection<SscCompoundContent> getCompoundContent(String ticketContent) {
		Collection<SscCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				SscCompoundContent.class);
		return c;
	}
}
