package com.cai310.lottery.service.lottery.ticket.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.ticket.TicketDatail;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.activity.AvtivityService;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.response.dto.JcTicketOddsList;
import com.cai310.lottery.utils.RlygQueryPVisitor;
import com.cai310.lottery.utils.ZunaoQueryPVisitor;
import com.cai310.lottery.utils.rlyg.jclq.JclqPrintItemObj;
import com.cai310.lottery.utils.rlyg.jclq.JclqSpUtil;
import com.cai310.lottery.utils.rlyg.jczq.JczqPrintItemObj;
import com.cai310.lottery.utils.rlyg.jczq.JczqSpUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

/**
 * 同步前台方案出票状态服务类
 * 
 * @author jack
 *  
 */
@Service("synchronizedTicketStateManager")
@Transactional
public class SynchronizedTicketStateManager {
	@Autowired
	protected TicketThenEntityManager ticketThenEntityManager;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected UserEntityManager userManager;
	@Autowired
	private List<SchemeEntityManager> schemeEntityManagerList;
	
	@Autowired
	protected TicketEntityManager ticketEntityManager;
		
	@Autowired
	protected PrintEntityManager printEntityManager;
	@Autowired
	protected AvtivityService avtivityService;
	
	@Autowired
	private AgentEntityManager agentEntityManager;

	private SchemeEntityManager getSchemeEntityManager(Lottery lotteryType) {
		for (SchemeEntityManager manager : schemeEntityManagerList) {
			if (manager.getLottery().equals(lotteryType))
				return manager;
		}
		return null;
	}
	
	
	/**
	 * 根据方案号进行同步出票状态(只同步成功出票的，未成功的待人工转出票商)
	 * @return
	 * @throws DataException 
	 */
	public void synchronizationState(PrintInterface printInterface,Boolean isSend) throws DataException {
		Long printinterfaceId = printInterface.getId();
		Lottery lotteryType = printInterface.getLotteryType();
		String schemeNumber = printInterface.getSchemeNumber();
		Long schemeId = lotteryType.getSchemeId(schemeNumber);
		if(schemeId==null){
			throw new DataException("更新方案{"+schemeNumber+"}出票状态错误，方案号ID错误！");
		}
		List<Long> ticketList = null;
		
		//该方案总拆单方案个数
		Integer totalCount = ticketEntityManager.findCountByPrintinterfaceId(printinterfaceId);
		//出票失败方案个数
		Integer failedCount = ticketEntityManager.findCountFailedByPrintinterfaceId(printinterfaceId);
		//有一张票失败则整个方案失败
		if(totalCount!=null&&failedCount!=null&&failedCount>0){
			//修改前台状态为失败
			SchemeEntityManager schemeManager = getSchemeEntityManager(lotteryType);
			Scheme scheme = schemeManager.getScheme(schemeId);
			scheme.setSchemePrintState(SchemePrintState.FAILED);
			schemeManager.saveScheme(scheme);
			//更新接口表记录为同步状态
			printInterface.setTicketFinsh(true);
			printEntityManager.savePrintInterface(printInterface);
			//更新ticket记录同步状态
			ticketList = ticketEntityManager.findTicketByPrintInterfaceId(printinterfaceId);
			Ticket ticket=null;
			for(Long id:ticketList){
				ticket = ticketEntityManager.getTicket(id);
				ticket.setSynchroned(true);
				ticketEntityManager.saveTicket(ticket);
			}
			synchronizationTicket(scheme,ticket);
			return;
		}
		
//		//该方案总拆单方案个数
//		Integer totalCount = ticketEntityManager.findCountByPrintinterfaceId(printinterfaceId);
		Integer successCount = ticketEntityManager.findCountSuccessByPrintinterfaceId(printinterfaceId);
		//所有票都成功方案才算成功
		if(totalCount!=null&&successCount!=null&&totalCount.equals(successCount)){
			//修改前台状态为成功
			SchemeEntityManager schemeManager = getSchemeEntityManager(lotteryType);
			Scheme scheme = schemeManager.getScheme(schemeId);
			scheme.setSchemePrintState(SchemePrintState.SUCCESS);
			schemeManager.saveScheme(scheme);
			//更新接口表记录为同步状态
			printInterface.setTicketFinsh(true);
			printEntityManager.savePrintInterface(printInterface);
			//更新ticket记录同步状态
			ticketList = ticketEntityManager.findTicketByPrintInterfaceId(printinterfaceId);
			Ticket ticket=null;
			for(Long id:ticketList){
				ticket = ticketEntityManager.getTicket(id);
				ticket.setSynchroned(true);
				ticketEntityManager.saveTicket(ticket);
			}
			synchronizationTicket(scheme,ticket);
			return;
		}
		
		//同步前台委托状态
		Integer printCount = ticketEntityManager.findCountPrintByPrintinterfaceId(printinterfaceId);
		if(totalCount.equals(printCount)){
			SchemeEntityManager schemeManager = getSchemeEntityManager(lotteryType);
			Scheme scheme = schemeManager.getScheme(schemeId);
			scheme.setSchemePrintState(SchemePrintState.PRINT);
			schemeManager.saveScheme(scheme);
			return;
		}
	}
	
	/**
	 * 同步出票赔率及状态到接票表中
	 * @param scheme 
	 * @param ticket
	 */
	private void synchronizationTicket(Scheme scheme,Ticket ticket){
		if (scheme == null)throw new ServiceException("方案不存在.");
		if(null!=scheme.getOrderId()){
			TicketThen ticketThen = this.ticketThenEntityManager.getTicketThenByScheme(scheme);
			if(null==ticketThen)throw new ServiceException("出票队列不存在.");
			TicketDatail ticketDatail = this.ticketThenEntityManager.getTicketDatailByTicketThen(ticketThen);
			if(null==ticketDatail)throw new ServiceException("出票队列不存在.");
			//接票方案
			if(SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())){
				///出票成功
				final String seq = "\r\n";
				final char seq2 = ',';
				ticketThen.setState(TicketSchemeState.SUCCESS);
				if(scheme.getLotteryType().getCategory().equals(LotteryCategory.JC)){
					if(scheme.getLotteryType().equals(Lottery.JCZQ)){
						JczqScheme jczqScheme = (JczqScheme) scheme;
						try {
							List<com.cai310.lottery.support.jczq.PassType> passTypeList = jczqScheme.getPassTypeList();
							if(null==passTypeList)throw new ServiceException("过关方式数据错误.");
							if(!com.cai310.lottery.support.jczq.PassType.P1.equals(passTypeList.get(0))){
								List<Map<String, Map<String, Double>>> spList = jczqScheme.getPrintAwardList();
								Map<String, Map<String, Double>> spMap = spList.get(0);
								StringBuffer sb  = new StringBuffer();
								Item[] allItem = jczqScheme.getPlayType().getAllItems();
								for (JczqMatchItem matchItem : jczqScheme.getCompoundContent().getItems()) {
									Map<String,Double> sp  =  spMap.get(matchItem.getMatchKey());
									sb.append("[").append(matchItem.getMatchKey()).append("]：");
									if(com.cai310.lottery.support.jczq.PlayType.MIX.equals(jczqScheme.getPlayType()))allItem = matchItem.getPlayType().getAllItems();
									for (Item item : allItem) {
										if ((matchItem.getValue() & (1 << item.ordinal())) > 0) {
											sb.append(item.getText()).append("=").append(Double.valueOf(""+sp.get(item.getValue()))).append(seq2);
										}
									}
									sb.deleteCharAt(sb.length() - 1);
									sb.append(seq);
								}
								ticketDatail.setPrintAwards(sb.toString());
							}else{
								try{
//									if(com.cai310.lottery.support.jczq.PlayType.BF.equals(jczqScheme.getPlayType())||com.cai310.lottery.support.jczq.PlayType.SPF.equals(jczqScheme.getPlayType())){
										List<Map<String, Map<String, Double>>> spList = jczqScheme.getPrintAwardList();
										Map<String, Map<String, Double>> spMap = spList.get(0);
										StringBuffer sb  = new StringBuffer();
										Item[] allItem = jczqScheme.getPlayType().getAllItems();
										for (JczqMatchItem matchItem : jczqScheme.getCompoundContent().getItems()) {
											Map<String,Double> sp  =  spMap.get(matchItem.getMatchKey());
											sb.append("[").append(matchItem.getMatchKey()).append("]：");
											if(com.cai310.lottery.support.jczq.PlayType.MIX.equals(jczqScheme.getPlayType()))allItem = matchItem.getPlayType().getAllItems();
											for (Item item : allItem) {
												if ((matchItem.getValue() & (1 << item.ordinal())) > 0) {
													sb.append(item.getText()).append("=").append(Double.valueOf(""+sp.get(item.getValue()))).append(seq2);
												}
											}
											sb.deleteCharAt(sb.length() - 1);
											sb.append(seq);
										}
										ticketDatail.setPrintAwards(sb.toString());
//									}
								}catch(Exception e){
									//单关可能会获取失败
									logger.error(TicketSupporter.LIANG.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
								}
							}
						} catch (DataException e) {
							throw new ServiceException("竞彩sp错误");
						}
					}else if(scheme.getLotteryType().equals(Lottery.JCLQ)){
						JclqScheme jclqScheme = (JclqScheme) scheme;
						try {
							
							List<com.cai310.lottery.support.jclq.PassType> passTypeList = jclqScheme.getPassTypeList();
							if(null==passTypeList)throw new ServiceException("过关方式数据错误.");
							if(!com.cai310.lottery.support.jclq.PassType.P1.equals(passTypeList.get(0))){
								List<Map<String, Map<String, Double>>> spList = jclqScheme.getPrintAwardList();
								Map<String, Map<String, Double>> spMap = spList.get(0);
								StringBuffer sb  = new StringBuffer();
								Item[] allItem = jclqScheme.getPlayType().getAllItems();
								for (JclqMatchItem matchItem : jclqScheme.getCompoundContent().getItems()) {
									Map<String,Double> sp  =  spMap.get(matchItem.getMatchKey());
									sb.append("[").append(matchItem.getMatchKey()).append("]：");
									if(com.cai310.lottery.support.jclq.PlayType.MIX.equals(jclqScheme.getPlayType()))allItem = matchItem.getPlayType().getAllItems();
									for (Item item : allItem) {
										if ((matchItem.getValue() & (1 << item.ordinal())) > 0) {
											sb.append(item.getText()).append("=").append(Double.valueOf(""+sp.get(item.getValue()))).append(seq2);
										}
									}
									sb.deleteCharAt(sb.length() - 1);
									sb.append(seq);
								}
								ticketDatail.setPrintAwards(sb.toString());
							}else{
								//单关
								try{
//									if(com.cai310.lottery.support.jclq.PlayType.SFC.equals(jclqScheme.getPlayType())){
										List<Map<String, Map<String, Double>>> spList = jclqScheme.getPrintAwardList();
										Map<String, Map<String, Double>> spMap = spList.get(0);
										StringBuffer sb  = new StringBuffer();
										Item[] allItem = jclqScheme.getPlayType().getAllItems();
										for (JclqMatchItem matchItem : jclqScheme.getCompoundContent().getItems()) {
											Map<String,Double> sp  =  spMap.get(matchItem.getMatchKey());
											sb.append("[").append(matchItem.getMatchKey()).append("]：");
											if(com.cai310.lottery.support.jclq.PlayType.MIX.equals(jclqScheme.getPlayType()))allItem = matchItem.getPlayType().getAllItems();
											for (Item item : allItem) {
												if ((matchItem.getValue() & (1 << item.ordinal())) > 0) {
													sb.append(item.getText()).append("=").append(Double.valueOf(""+sp.get(item.getValue()))).append(seq2);
												}
											}
											sb.deleteCharAt(sb.length() - 1);
											sb.append(seq);
										}
										ticketDatail.setPrintAwards(sb.toString());
//									}
								}catch(Exception e){
									//单关可能会获取失败
									logger.error(TicketSupporter.LIANG.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
								}
							}
						} catch (DataException e) {
							throw new ServiceException("竞彩sp错误");
						}
					}else{
						throw new ServiceException("竞彩玩法错误");
					}
					this.ticketThenEntityManager.saveTicketDatail(ticketDatail);
				}
				ticketThen.setTicketFinsh(true);
				if(null!=ticket.getTicketTime()){
					ticketThen.setTicketTime(ticket.getTicketTime());
				}else{
					ticketThen.setTicketTime(new Date());
				}
				ticketThen.setRemoteTicketId(ticket.getRemoteTicketId());
			}else if(SchemePrintState.FAILED.equals(scheme.getSchemePrintState())){
				///出票失败
				ticketThen.setState(TicketSchemeState.FAILD);
				ticketThen.setCancelTime(new Date());
				ticketThen.setTicketFinsh(false);
			}
			this.ticketThenEntityManager.saveTicketThen(ticketThen);
		}
	}
	
	/**
	 * 根据方案号进行同步出票状态
	 * @return
	 * @throws DataException 
	 */
	public void synchronizationState_jc(PrintInterface printInterface,Boolean isSend) throws DataException {
		Long printinterfaceId = printInterface.getId();
		Lottery lotteryType = printInterface.getLotteryType();
		String schemeNumber = printInterface.getSchemeNumber();
		Long schemeId = lotteryType.getSchemeId(schemeNumber);
		if(schemeId==null){
			throw new DataException("更新方案{"+schemeNumber+"}出票状态错误，方案号ID错误！");
		}
		List<Long> ticketList = null;
		
		//该方案总拆单方案个数
		Integer totalCount = ticketEntityManager.findCountByPrintinterfaceId(printinterfaceId);
		Integer successCount = ticketEntityManager.findCountSuccessByPrintinterfaceId(printinterfaceId);
		Integer failedCount = ticketEntityManager.findCountFailedByPrintinterfaceId(printinterfaceId);
		//全部出票失败算方案失败
		if(totalCount!=null && failedCount!=null && (failedCount.equals(totalCount))){
			//设为已打印
			printInterface.setTicketFinsh(false);
			printEntityManager.savePrintInterface(printInterface);
			Scheme cancelScheme=null;
			if(printInterface.getLotteryType().equals(Lottery.JCZQ)){
				JczqScheme scheme = (JczqScheme) getSchemeEntityManager(lotteryType).getScheme(schemeId);
				if (scheme == null)throw new ServiceException("方案不存在.");
				scheme.setSchemePrintState(SchemePrintState.FAILED);
				scheme.setRealPrinted(false);
				scheme.setCancelTime(new Date());
				getSchemeEntityManager(lotteryType).saveScheme(scheme);	
				cancelScheme = scheme;
			}else if(printInterface.getLotteryType().equals(Lottery.JCLQ)){
				JclqScheme scheme = (JclqScheme) getSchemeEntityManager(lotteryType).getScheme(schemeId);
				if (scheme == null)throw new ServiceException("方案不存在.");
				scheme.setSchemePrintState(SchemePrintState.FAILED);
				scheme.setRealPrinted(false);
				scheme.setCancelTime(new Date());
				getSchemeEntityManager(lotteryType).saveScheme(scheme);
				cancelScheme = scheme;
			}else{
				throw new DataException("竞彩玩法错误");
			}
			
			//匹配后台撤单功能(非撤销及退款)
			if (!SchemeState.CANCEL.equals(cancelScheme.getState()) && !SchemeState.REFUNDMENT.equals(cancelScheme.getState())){				
				try {
					//方案撤单
					cancelScheme.forceCancel();
				} catch (DataException e) {
					throw new ServiceException(e.getMessage());
				}
				StringBuilder cause = new StringBuilder(50);
				if(null!=cancelScheme.getOrderId()){
					cause.append( "系统" ).append("撤销订单号[").append(cancelScheme.getOrderId()).append("].撤销彩票[").append(cancelScheme.getSchemeNumber()).append("].");
				}else{
					cause.append( "系统" ).append("撤销方案[").append(cancelScheme.getSchemeNumber()).append("].");
				}
				userManager.cancelTransaction(cancelScheme.getTransactionId(), FundDetailType.CANCEL_SCHEME,
						cause.toString());
			}
			//更新ticket记录同步状态
			ticketList = ticketEntityManager.findTicketByPrintInterfaceId(printinterfaceId);
			Ticket ticket=null;
			for(Long id:ticketList){
				ticket = ticketEntityManager.getTicket(id);
				ticket.setSynchroned(true);
				ticketEntityManager.saveTicket(ticket);
			}
			synchronizationTicket(cancelScheme,ticket);
			return;
		}
		if(totalCount!=null&&successCount!=null&&successCount>0&&totalCount.equals(successCount)){
			//修改前台状态为成功
			printInterface.setTicketFinsh(true);
			printEntityManager.savePrintInterface(printInterface);
			
			if(printInterface.getLotteryType().equals(Lottery.JCZQ)){				
				//更新ticket记录同步状态
				ticketList = ticketEntityManager.findTicketByPrintInterfaceId(printinterfaceId);
				JcTicketOddsList jcTicketOddsList = new JcTicketOddsList();
				jcTicketOddsList.setSchemeId(schemeId);
				List<JcMatchOddsList> awardList = Lists.newArrayList();
				Ticket ticket=null;
				Date lastPrintTime =null;
				for(Long id:ticketList){
					ticket = ticketEntityManager.getTicket(id);
					ticket.setSynchroned(true);
					if(StringUtils.isNotBlank(ticket.getExtension())){
						@SuppressWarnings("rawtypes")
						Map classMap = new HashMap();
						classMap.put("list", JcMatchOdds.class);
						JcMatchOddsList jcMatchOddsList = JsonUtil.getObject4JsonString(ticket.getExtension(), JcMatchOddsList.class, classMap);
						if(null==jcTicketOddsList.getAwardList()){
							awardList.add(jcMatchOddsList);
						}
					}
					if(null!=ticket.getTicketTime()){
						if (lastPrintTime == null || lastPrintTime.before(ticket.getTicketTime()))
							lastPrintTime = ticket.getTicketTime();
					}
					ticketEntityManager.saveTicket(ticket);
				}
				jcTicketOddsList.setAwardList(awardList);
				String awardString = JsonUtil.getJsonString4JavaPOJO(jcTicketOddsList);
				////更新方案状态
				JczqScheme scheme = (JczqScheme) getSchemeEntityManager(lotteryType).getScheme(schemeId);
				scheme.setSchemePrintState(SchemePrintState.SUCCESS);
				scheme.setPrintAwards(awardString);
				scheme.setRealPrinted(true);
				scheme.setRealPrintTime(lastPrintTime);
				getSchemeEntityManager(lotteryType).saveScheme(scheme);	
				synchronizationTicket(scheme,ticket);
				return;
			}else if(printInterface.getLotteryType().equals(Lottery.JCLQ)){
				//更新ticket记录同步状态
				ticketList = ticketEntityManager.findTicketByPrintInterfaceId(printinterfaceId);
				JcTicketOddsList jcTicketOddsList = new JcTicketOddsList();
				jcTicketOddsList.setSchemeId(schemeId);
				List<JcMatchOddsList> awardList = Lists.newArrayList();
				Ticket ticket=null;
				Date lastPrintTime =null;
				for(Long id:ticketList){
					ticket = ticketEntityManager.getTicket(id);
					ticket.setSynchroned(true);
					if(StringUtils.isNotBlank(ticket.getExtension())){
						@SuppressWarnings("rawtypes")
						Map classMap = new HashMap();
						classMap.put("list", JcMatchOdds.class);
						JcMatchOddsList jcMatchOddsList = JsonUtil.getObject4JsonString(ticket.getExtension(), JcMatchOddsList.class, classMap);
						if(null==jcTicketOddsList.getAwardList()){
							awardList.add(jcMatchOddsList);
						}
					}
					if(null!=ticket.getTicketTime()){
						if (lastPrintTime == null || lastPrintTime.before(ticket.getTicketTime()))
							lastPrintTime = ticket.getTicketTime();
					}
					ticketEntityManager.saveTicket(ticket);
				}
				jcTicketOddsList.setAwardList(awardList);
				String awardString = JsonUtil.getJsonString4JavaPOJO(jcTicketOddsList);
			    ////更新方案状态
				JclqScheme scheme = (JclqScheme) getSchemeEntityManager(lotteryType).getScheme(schemeId);
				scheme.setSchemePrintState(SchemePrintState.SUCCESS);
				scheme.setPrintAwards(awardString);
				scheme.setRealPrintTime(lastPrintTime);
				scheme.setRealPrinted(true);
				getSchemeEntityManager(lotteryType).saveScheme(scheme);	
				synchronizationTicket(scheme,ticket);
				return;
			}
		}
		
		Integer printCount = ticketEntityManager.findCountPrintByPrintinterfaceId(printinterfaceId);
		if(totalCount.equals(printCount)){
			//修改前台状态为委托
			Scheme scheme = getSchemeEntityManager(lotteryType).getScheme(schemeId);
			scheme.setSchemePrintState(SchemePrintState.PRINT);
			getSchemeEntityManager(lotteryType).saveScheme(scheme);
			return;
		}
	}
	
	public static int QUERY_MAXRESULTS = 1;//查询返回的最大记录数
	/**
	 * 根据方案号进行同步出票状态
	 * @return
	 * @throws IOException 
	 * @throws DataException 
	 */
	public void query_method(List<Long> ticketList,Lottery lottery){
		try{
			Ticket ticket = null;
			for (Long id : ticketList) {
				try{
					ticket = new Ticket();
					ticket.setId(id);
					ticket.setLotteryType(lottery);
					ticket = ticketEntityManager.getTicket(ticket.getId());
					if(ticket.getTicketSupporter().equals(TicketSupporter.ZUNAO)){
						ZunaoQueryPVisitor queryPVisitor = com.cai310.lottery.utils.ZunaoUtil.confirmTicket(ticket);
						queryTicket(queryPVisitor,ticket);
					}else if(ticket.getTicketSupporter().equals(TicketSupporter.RLYG)){
						RlygQueryPVisitor queryPVisitor = com.cai310.lottery.utils.RlygUtil.confirmTicket(ticket);
						queryTicket(queryPVisitor,ticket);
					}
				}catch (Exception e) {
					logger.error("彩票｛"+ticket.getId()+"｝查询出错"+e.getMessage());
					continue;
				}
			}
		}catch (Exception e) {
			logger.error("彩票查询出错"+e.getMessage());
		}
	}
	/**
	 * 查询彩票
	 * @param
	 * @param 
	 * @return
	 * @throws IOException
	 */
	private void queryTicket(ZunaoQueryPVisitor queryPVisitor,Ticket ticket) throws IOException{
		if(queryPVisitor.getIsSuccess()){
			ticket = ticketEntityManager.getTicket(ticket.getId());
			logger.debug(TicketSupporter.ZUNAO.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			System.out.println(TicketSupporter.ZUNAO.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			ticket.setStateCode("1");
			ticket.setTicketSupporter(TicketSupporter.ZUNAO);
			ticket.setStateModifyTime(new Date());
			ticket.setRemoteTicketId(queryPVisitor.getTicketCode());
			ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
		    if(ticket.getLotteryType().equals(Lottery.JCZQ)){
		    	@SuppressWarnings("rawtypes")
				Map classMap = new HashMap();
				classMap.put("items", JczqMatchItem.class);
				com.cai310.lottery.utils.zunao.jczq.JczqPrintItemObj jczqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), com.cai310.lottery.utils.zunao.jczq.JczqPrintItemObj.class, classMap);
				if(null!=jczqPrintItemObj){
					if(!com.cai310.lottery.support.jczq.PassType.P1.equals(
							 com.cai310.lottery.support.jczq.PassType.values()[Integer.valueOf(""+jczqPrintItemObj.getPassType())])){
						JcMatchOddsList jcMatchOddsList = com.cai310.lottery.utils.zunao.jczq.JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jczqPrintItemObj,ticket);
				    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
							jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
							jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
						}
					}else{
						//单关
						try{
							if(com.cai310.lottery.support.jczq.PlayType.BF.equals(
									com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf(""+jczqPrintItemObj.getPlayTypeOrdinal())])||
									com.cai310.lottery.support.jczq.PlayType.SPF.equals(
											com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf(""+jczqPrintItemObj.getPlayTypeOrdinal())])){
									JcMatchOddsList jcMatchOddsList = com.cai310.lottery.utils.zunao.jczq.JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jczqPrintItemObj,ticket);
							    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
										jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
										jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
										String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
										if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
									}
							}else{
								JcMatchOddsList jcMatchOddsList =new JcMatchOddsList();
								jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
								if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
							}
						}catch(Exception e){
							//单关可能会获取失败
							logger.error(TicketSupporter.ZUNAO.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
						}
					}
				}

		   }else if(ticket.getLotteryType().equals(Lottery.JCLQ)){
			    @SuppressWarnings("rawtypes")
				Map classMap = new HashMap();
				classMap.put("items", JclqMatchItem.class);
				com.cai310.lottery.utils.zunao.jclq.JclqPrintItemObj jclqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), com.cai310.lottery.utils.zunao.jclq.JclqPrintItemObj.class, classMap);
				if(null!=jclqPrintItemObj){
					if(!com.cai310.lottery.support.jclq.PassType.P1.equals(
							 com.cai310.lottery.support.jclq.PassType.values()[Integer.valueOf(""+jclqPrintItemObj.getPassType())])){
				    	JcMatchOddsList jcMatchOddsList = com.cai310.lottery.utils.zunao.jclq.JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jclqPrintItemObj,ticket);
				    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
							jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
							jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
						}
					}else{
						try{
							if(com.cai310.lottery.support.jclq.PlayType.SFC.equals(
									com.cai310.lottery.support.jclq.PlayType.values()[Integer.valueOf(""+jclqPrintItemObj.getPlayTypeOrdinal())])){
								JcMatchOddsList jcMatchOddsList = com.cai310.lottery.utils.zunao.jclq.JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),jclqPrintItemObj,ticket);
						    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
									jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
									jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
									String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
									if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
								}
							}else{
								JcMatchOddsList jcMatchOddsList =new JcMatchOddsList();
								jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
								if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
							}
						}catch(Exception e){
							//单关可能会获取失败	
							logger.error(TicketSupporter.ZUNAO.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
						}
					}
				}
						
		   }
		   ticketEntityManager.saveTicket(ticket);
		}else{
			if("000".equals(queryPVisitor.getResultId())||"004".equals(queryPVisitor.getResultId())){
				///撤单
				ticket = ticketEntityManager.getTicket(ticket.getId());
				System.out.println(TicketSupporter.ZUNAO.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
				ticket.setStateCode("2");
				ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
				ticket.setStateModifyTime(new Date());
				ticketEntityManager.saveTicket(ticket);
			}
		}
	}
	/**
	 * 查询彩票
	 * @param
	 * @param 
	 * @return
	 * @throws IOException
	 */
	private void queryTicket(RlygQueryPVisitor queryPVisitor,Ticket ticket) throws IOException{
		if(queryPVisitor.getIsSuccess()){
			ticket = ticketEntityManager.getTicket(ticket.getId());
			logger.debug(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			System.out.println(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
			ticket.setStateCode("1");
			ticket.setStateModifyTime(new Date());
			ticket.setRemoteTicketId(queryPVisitor.getTicketCode());
			ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
		    if(ticket.getLotteryType().equals(Lottery.JCZQ)){
		    	@SuppressWarnings("rawtypes")
				Map classMap = new HashMap();
				classMap.put("items", JczqMatchItem.class);
				JczqPrintItemObj jczqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JczqPrintItemObj.class, classMap);
				if(null!=jczqPrintItemObj){
					if(!com.cai310.lottery.support.jczq.PassType.P1.equals(
							 com.cai310.lottery.support.jczq.PassType.values()[Integer.valueOf(""+jczqPrintItemObj.getPassType())])){
						JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),queryPVisitor.getBetValue(),jczqPrintItemObj,ticket);
				    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
							jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
							jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
						}
					}else{
						//单关
						try{
							if(com.cai310.lottery.support.jczq.PlayType.BF.equals(
									com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf(""+jczqPrintItemObj.getPlayTypeOrdinal())])
									||com.cai310.lottery.support.jczq.PlayType.SPF.equals(
											com.cai310.lottery.support.jczq.PlayType.values()[Integer.valueOf(""+jczqPrintItemObj.getPlayTypeOrdinal())])){
									JcMatchOddsList jcMatchOddsList = JczqSpUtil.parseResponseSp(queryPVisitor.getAwards(),queryPVisitor.getBetValue(),jczqPrintItemObj,ticket);
							    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
										jcMatchOddsList.setTicketIndex(jczqPrintItemObj.getTicketIndex());
										jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
										String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
										if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
									}
							}else{
								JcMatchOddsList jcMatchOddsList =new JcMatchOddsList();
								jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
								if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
							}
						}catch(Exception e){
							//单关可能会获取失败
							logger.error(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
						}
					}
				}

		   }else if(ticket.getLotteryType().equals(Lottery.JCLQ)){
			    @SuppressWarnings("rawtypes")
				Map classMap = new HashMap();
				classMap.put("items", JclqMatchItem.class);
				JclqPrintItemObj jclqPrintItemObj = JsonUtil.getObject4JsonString(ticket.getContent(), JclqPrintItemObj.class, classMap);
				if(null!=jclqPrintItemObj){
					if(!com.cai310.lottery.support.jclq.PassType.P1.equals(
							 com.cai310.lottery.support.jclq.PassType.values()[Integer.valueOf(""+jclqPrintItemObj.getPassType())])){
				    	JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),queryPVisitor.getBetValue(),jclqPrintItemObj,ticket);
				    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
							jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
							jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
							String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
							if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
						}
					}else{
						try{
							if(com.cai310.lottery.support.jclq.PlayType.SFC.equals(
									com.cai310.lottery.support.jclq.PlayType.values()[Integer.valueOf(""+jclqPrintItemObj.getPlayTypeOrdinal())])){
								JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp(queryPVisitor.getAwards(),queryPVisitor.getBetValue(),jclqPrintItemObj,ticket);
						    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
									jcMatchOddsList.setTicketIndex(jclqPrintItemObj.getTicketIndex());
									jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
									String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
									if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
								}
							}else{
								JcMatchOddsList jcMatchOddsList =new JcMatchOddsList();
								jcMatchOddsList.setTicketCode(queryPVisitor.getTicketCode());
								String awardString = JsonUtil.getJsonString4JavaPOJO(jcMatchOddsList);
								if(StringUtils.isNotBlank(awardString))ticket.setExtension(awardString);	
							}
						}catch(Exception e){
							//单关可能会获取失败	
							logger.error(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}单关可能会获取失败");
						}
					}
				}
						
		   }
		   ticketEntityManager.saveTicket(ticket);
		}else{
			if("N".equals(queryPVisitor.getResultId())){
				///撤单
				ticket = ticketEntityManager.getTicket(ticket.getId());
				System.out.println(TicketSupporter.RLYG.getSupporterName()+"{"+ticket.getId()+"}查票票成功，返回代码："+queryPVisitor.getResultId());
				ticket.setStateCode("2");
				ticket.setTicketTime(StringUtils.isBlank(queryPVisitor.getOperateTime())?null:DateUtil.strToDate(queryPVisitor.getOperateTime(), "yyyy-MM-dd hh:mm:ss"));
				ticket.setStateModifyTime(new Date());
				ticketEntityManager.saveTicket(ticket);
			}
		}
	}
	public void synchronization_Sp_Jc(PrintInterface printInterface) throws IOException, DataException {
		Long printinterfaceId = printInterface.getId();
		//该方案总拆单方案个数
		List<Long> ticketList = ticketEntityManager.findTicketByPrintInterfaceId(printinterfaceId);
		Ticket ticket=null;
		List<Long> ticketListTemp = Lists.newArrayList();
		for (Long id:ticketList) {
			ticket = ticketEntityManager.getTicket(id);
			if(ticket.getLotteryType().equals(Lottery.JCZQ)||ticket.getLotteryType().equals(Lottery.JCLQ)){
				ticketListTemp.add(id);
			}
			if(ticketListTemp.size()%QUERY_MAXRESULTS==0){
				///够最大查询数
				query_method(ticketListTemp,printInterface.getLotteryType());
				ticketListTemp.clear();
			}
		}
		if(ticketListTemp.size()>0){
			query_method(ticketListTemp,printInterface.getLotteryType());
			ticketListTemp.clear();
		}
	    synchronizationState_jc(printInterface,false);
	}
	public List<Map<String, Map<String, Double>>> getPrintAwardList(JcTicketOddsList jcTicketOddsList1) throws DataException {
		if (null==jcTicketOddsList1)
			return null;

		int ticketNum =jcTicketOddsList1.getAwardList().size();
		if (ticketNum == 0)ticketNum = 1;
		List<Map<String, Map<String, Double>>> list = new ArrayList<Map<String, Map<String, Double>>>();
		for (int i = 0; i < ticketNum; i++) {
			list.add(null);
		}
		String awardString = JsonUtil.getJsonString4JavaPOJO(jcTicketOddsList1);
		@SuppressWarnings("rawtypes")
		Map classMap = new HashMap();
		classMap.put("awardList", JcMatchOddsList.class);
		classMap.put("jcMatchOdds", JcMatchOdds.class);
		JcTicketOddsList jcTicketOddsList = JsonUtil.getObject4JsonString(awardString, JcTicketOddsList.class, classMap);
		List<JcMatchOddsList> jcMatchOdds = jcTicketOddsList.getAwardList();
		
		for (int i = 0; i < jcMatchOdds.size(); i++) {
			JcMatchOddsList jcMatchOddsList = jcMatchOdds.get(i);
			Integer ticketIndex=jcMatchOddsList.getTicketIndex();
			if (ticketIndex>= list.size())
				throw new DataException("出票SP值数据异常.");
			Map<String, Map<String, Double>> map = JczqUtil.getPrintAwardMap(jcMatchOddsList.getJcMatchOdds());
			list.set(ticketIndex, map);
		}
		return list;
	}
	
}
