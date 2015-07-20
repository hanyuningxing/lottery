package com.cai310.lottery.service.lottery.keno.ahkuai3.impl;

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
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.keno.AhKuai3PrizeWork;
import com.cai310.lottery.prizeutils.keno.GdEl11to5PrizeWork;
import com.cai310.lottery.prizeutils.keno.SdEl11to5PrizeWork;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoPlayerImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.ahkuai3.AhKuai3CompoundContent;
import com.cai310.lottery.support.ahkuai3.AhKuai3ContentBeanBuilder;
import com.cai310.lottery.support.ahkuai3.AhKuai3PlayType;
import com.cai310.lottery.support.ahkuai3.AhKuai3WinUnits;
import com.cai310.lottery.support.sdel11to5.SdEl11to5WinUnits;
import com.cai310.utils.DateUtil;

@Component("ahkuai3KenoPlayer")
public class AhKuai3KenoPlayer extends KenoPlayerImpl<AhKuai3IssueData, AhKuai3Scheme> {
	
//	@Autowired
//	AvtivityService avtivityService;
	
	@Resource(name = "ahkuai3KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<AhKuai3IssueData, AhKuai3Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Resource
	protected TicketEntityManager ticketEntityManager;
	
	@Resource
	protected PrintEntityManager printEntityManager;
	/**
	 * 计算方案注数
	 * @param scheme
	 * @throws DataException
	 */
	public void calculateSchemeUnits(AhKuai3Scheme scheme) throws DataException {
		AhKuai3PlayType ahkuai3PlayType = scheme.getBetType();
		ContentBean contentBean = AhKuai3ContentBeanBuilder.buildCompoundContentBean(scheme.getContent(), ahkuai3PlayType);
		scheme.setUnits(contentBean.getUnits());
	}
	
	@Override
	public String issueNumberAssembly(Date day, int period) {
		NumberFormat numFmt = new DecimalFormat("00");
		String nstr = numFmt.format(period);
		String dTimeStr = DateUtil.getFormatDate("yyyyMMdd", day);
		String issueNumber = dTimeStr + nstr;
		return issueNumber;
	}

	@Override
	public Date getBeginTimeByIssueNumber(String issueNumber) {
		String strDate = issueNumber.substring(0, 8);
		String strPeriod = issueNumber.substring(8, 10);
		int period = Integer.valueOf(strPeriod);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.strToDate(strDate, "yyyyMMdd"));
		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 40);
		cal.add(Calendar.MINUTE, (period - 1) * 10);
		return cal.getTime();
	}

	@Override
	public AhKuai3IssueData createNowIssueData(String issueNumber) {
		return new AhKuai3IssueData();
	}
	/**
	 * 计算方案中奖金额
	 */
	@Override
	public void calculatePrice(AhKuai3Scheme scheme, String results) throws DataException {
		AhKuai3PrizeWork ahKuai3PrizeWork=null;
//		if (SalesMode.COMPOUND.equals(scheme.getMode())) {
			if(null!=scheme.getCompoundContent()){
				//进行奖金计算
				ahKuai3PrizeWork= 
					new AhKuai3PrizeWork(scheme.getCompoundContent(), scheme.getMultiple(), results, scheme.getBetType());
			}
//		}else{
//			if (null != scheme.getContent()) {
//				ahKuai3PrizeWork = new AhKuai3PrizeWork(scheme.getContent(), scheme.getMultiple(), results,scheme.getBetType());
//			}
//		}

//		if (null != ahKuai3PrizeWork) {
//			if (ahKuai3PrizeWork.getAhKuai3Win().isWon()) {
//				// 更新中奖奖金，奖金描述，税后奖金
//				scheme.setPrize(new BigDecimal(ahKuai3PrizeWork.getAhKuai3Win().getPrize()));
//				scheme.setPrizeAfterTax(new BigDecimal(ahKuai3PrizeWork.getAhKuai3Win().getPrizeAfterTax()));
//				scheme.setPrizeDetail(ahKuai3PrizeWork.getAhKuai3Win().getPrizeDetail());
//				scheme.setWon(Boolean.TRUE);
//				//
////				avtivityService.ahAddPrize(ahKuai3PrizeWork.getAhKuai3Win(), scheme);
//			}
//		}
			if (ahKuai3PrizeWork.getAhKuai3Win().isWon()) {
				// 更新
				scheme.setPrize(new BigDecimal(ahKuai3PrizeWork.getAhKuai3Win().getPrize()));
				scheme.setPrizeAfterTax(new BigDecimal(ahKuai3PrizeWork.getAhKuai3Win().getPrizeAfterTax()));
				scheme.setPrizeDetail(ahKuai3PrizeWork.getAhKuai3Win().getPrizeDetail());
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
								AhKuai3PrizeWork ticketPrizeWork = null;
								try {
//									if (ticket.getMode() == SalesMode.COMPOUND) {
										ticketPrizeWork = new AhKuai3PrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
												scheme.getBetType());
//									} else {
//										String content = ticket.getContent();
//										if(content.indexOf(",")!=-1){
//											content = content.replaceAll(",","\r\n");
//										}
//										ticketPrizeWork = new AhKuai3PrizeWork(content, ticket.getMultiple(),results,
//												scheme.getBetType());
//									}
									AhKuai3WinUnits ticketPrizeUnit = ticketPrizeWork.getAhKuai3Win();
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

	@SuppressWarnings("unchecked")
	@Transient
	public Collection<AhKuai3CompoundContent> getCompoundContent(String ticketContent) {
		Collection<AhKuai3CompoundContent> c = JSONArray.toCollection(JSONArray.fromObject(ticketContent),
				AhKuai3CompoundContent.class);
		return c;
	}
	
	@Override
	public boolean calculatePrice(SalesMode salesMode, String content, 
			Integer multiple, String betType, String results) throws DataException {
		AhKuai3PrizeWork ahkuai3PrizeWork = null;
			ahkuai3PrizeWork = 
				new AhKuai3PrizeWork(JSONArray.toCollection(JSONArray.fromObject(content), AhKuai3CompoundContent.class), 
						multiple, results, AhKuai3PlayType.valueOf((betType)));
		if (ahkuai3PrizeWork.getAhKuai3Win().isWon()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.AHKUAI3;
	}

	@Override
	public int getMaxPeriod() {
		return 80;
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
	public void resetPrice(AhKuai3Scheme scheme, String results, Ticket ticket)
			throws DataException {
		AhKuai3PrizeWork ticketPrizeWork = null;
		try {
			if (ticket.getMode() == SalesMode.COMPOUND) {
				ticketPrizeWork = new AhKuai3PrizeWork(getCompoundContent(ticket.getContent()), ticket.getMultiple(),results,
						scheme.getBetType());
			} 
//			else {
//				String content = ticket.getContent();
//				if(content.indexOf(",")!=-1){
//					content = content.replaceAll(",","\r\n");
//				}
//				ticketPrizeWork = new AhKuai3PrizeWork(content, ticket.getMultiple(),results,
//						scheme.getBetType());
//			}
			AhKuai3WinUnits ticketPrizeUnit = ticketPrizeWork.getAhKuai3Win();
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
