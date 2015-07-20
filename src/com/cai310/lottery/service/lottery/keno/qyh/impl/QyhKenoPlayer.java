package com.cai310.lottery.service.lottery.keno.qyh.impl;

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
import com.cai310.lottery.entity.lottery.keno.qyh.QyhIssueData;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.keno.QyhPrizeWork;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoPlayerImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.qyh.QyhCompoundContent;
import com.cai310.lottery.support.qyh.QyhContentBeanBuilder;
import com.cai310.lottery.support.qyh.QyhPlayType;
import com.cai310.lottery.support.qyh.QyhWinUnits;
import com.cai310.utils.DateUtil;

@Component("qyhKenoPlayer")
public class QyhKenoPlayer extends KenoPlayerImpl<QyhIssueData, QyhScheme> {
	@Override
	public Lottery getLottery() {
		return Lottery.QYH;
	}
	@Resource
	protected TicketEntityManager ticketEntityManager;
	@Resource(name = "qyhKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<QyhIssueData, QyhScheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Resource
	protected PrintEntityManager printEntityManager;
	public void calculatePrice(QyhScheme scheme, String results) throws DataException {
		QyhPrizeWork prizeWork = null;
		if (SalesMode.COMPOUND.equals(scheme.getMode())) {
			prizeWork = new QyhPrizeWork(scheme.getCompoundContent(), scheme.getMultiple(), results,
					scheme.getBetType());
		} else if (SalesMode.SINGLE.equals(scheme.getMode())) {
			prizeWork = new QyhPrizeWork(scheme.getContent(), scheme.getMultiple(), results, scheme.getBetType());
		}
		if (prizeWork.getQyhWin().isWon()) {
			// 更新
			scheme.setPrize(new BigDecimal(prizeWork.getQyhWin().getPrize()));
			scheme.setPrizeAfterTax(new BigDecimal(prizeWork.getQyhWin().getPrizeAfterTax()));
			scheme.setPrizeDetail(prizeWork.getQyhWin().getPrizeDetail());
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
							 QyhPrizeWork ticketPrizeWork = null;
							try {
								if (ticket.getMode() == SalesMode.COMPOUND) {
									ticketPrizeWork = new QyhPrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
											scheme.getBetType());
								} else {
									String content = ticket.getContent();
									if(content.indexOf(",")!=-1){
										content = content.replaceAll(",","\r\n");
									}
									ticketPrizeWork = new QyhPrizeWork(content, ticket.getMultiple(),results,
											scheme.getBetType());
								}
								QyhWinUnits ticketPrizeUnit = ticketPrizeWork.getQyhWin();
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

	public boolean calculatePrice(SalesMode salesMode, String content, Integer multiple, String betType, String results)
			throws DataException {
		QyhPrizeWork prizeWork = null;
		if (SalesMode.COMPOUND.equals(salesMode)) {
			prizeWork = new QyhPrizeWork(
					JSONArray.toCollection(JSONArray.fromObject(content), QyhCompoundContent.class), multiple, results,
					QyhPlayType.valueOf((betType)));
		} else if (SalesMode.SINGLE.equals(salesMode)) {
			prizeWork = new QyhPrizeWork(content, multiple, results, QyhPlayType.valueOf(betType));
		}
		if (prizeWork.getQyhWin().isWon()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void calculateSchemeUnits(QyhScheme scheme) throws DataException {
		QyhPlayType qyhPlayType = scheme.getBetType();
		ContentBean contentBean = QyhContentBeanBuilder.buildCompoundContentBean(scheme.getContent(), qyhPlayType);
		scheme.setUnits(contentBean.getUnits());

	}

	public QyhIssueData createNowIssueData(String issueNumber) {
		return new QyhIssueData();
	}

	public Date getBeginTimeByIssueNumber(String issueNumber) {
		String strDate = issueNumber.substring(0, 8);
		String strPeriod = issueNumber.substring(9, 12);
		int period = Integer.valueOf(strPeriod);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.strToDate(strDate, "yyyyMMdd"));
		if (period <= 32) {
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.add(Calendar.MINUTE, (period - 1) * getPeriodMinutes());
		} else {
			cal.set(Calendar.HOUR_OF_DAY, 20);
			cal.set(Calendar.MINUTE, 0);
			cal.add(Calendar.MINUTE, (period - 33) * getPeriodMinutes());
		}
		return cal.getTime();
	}

	public String issueNumberAssembly(Date day, int period) {
		NumberFormat numFmt = new DecimalFormat("000");
		String nstr = numFmt.format(period);
		String dTimeStr = DateUtil.getFormatDate("yyyyMMdd", day);
		String issueNumber = dTimeStr + "-" + nstr;
		return issueNumber;
	}
	@Override
	public void resetPrice(QyhScheme scheme, String results, Ticket ticket)
			throws DataException {
		QyhPrizeWork ticketPrizeWork = null;
		try {
			if (ticket.getMode() == SalesMode.COMPOUND) {
				ticketPrizeWork = new QyhPrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
						scheme.getBetType());
			} else {
				String content = ticket.getContent();
				if(content.indexOf(",")!=-1){
					content = content.replaceAll(",","\r\n");
				}
				ticketPrizeWork = new QyhPrizeWork(content, ticket.getMultiple(),results,
						scheme.getBetType());
			}
			QyhWinUnits ticketPrizeUnit = ticketPrizeWork.getQyhWin();
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
	public Collection<QyhCompoundContent> getCompoundContent(String ticketContent) {
		Collection<QyhCompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				QyhCompoundContent.class);
		return c;
	}
	@Override
	public int getMaxPeriod() {
		return 40;
	}

	@Override
	public int getPeriodMinutes() {
		return 15;
	}

	@Override
	public int getBeforeTime() {
		return 5;
	}
	
	@Override
	public int getBeforeSecondsTime() {
		return 0;
	}
}
