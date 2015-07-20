package com.cai310.lottery.service.lottery.zc.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dto.lottery.zc.SczcSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.lottery.zc.SczcPasscount;
import com.cai310.lottery.entity.lottery.zc.SczcPeriodData;
import com.cai310.lottery.entity.lottery.zc.SczcScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.SchemeServiceImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.PrizeItem;
import com.cai310.lottery.support.zc.SczcCompoundItem;
import com.cai310.lottery.support.zc.ZcCompoundPassWork;
import com.cai310.lottery.support.zc.ZcPrizeWork;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.utils.BigDecimalUtil;

@Service("sczcSchemeServiceImpl")
@Transactional
public class SczcSchemeServiceImpl extends SchemeServiceImpl<SczcScheme, SczcSchemeDTO> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SczcSchemeEntityManagerImpl schemeManager;

	@Autowired
	private SczcPeriodDataEntityManagerImpl periodDataManagerImpl;

	@Override
	protected SchemeEntityManager<SczcScheme> getSchemeEntityManager() {
		return schemeManager;
	}
	@Resource
	protected TicketEntityManager ticketEntityManager;
	@Resource
	protected PrintEntityManager printEntityManager;
	public void updatePrize(long schemeId) {
		SczcScheme scheme = schemeManager.getScheme(schemeId);
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

		SczcPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (null==periodData.getFirstPrize()) {
			throw new ServiceException("未设置中奖奖金！");
		}

		SczcPasscount wonInfo = this.schemeManager.getSczcSchemeWonInfo(scheme.getId());

		if (wonInfo == null || !wonInfo.isWon()) {
			throw new ServiceException("数据异常！");
		}
		ZcPrizeWork prizeWork = new ZcPrizeWork(periodData, null, wonInfo.getLost0(), wonInfo.getLost1(), wonInfo
				.getLost2(), wonInfo.getLost3());
		try {
			scheme.doUpdatePrize(prizeWork.getPrizeItems());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		scheme = schemeManager.saveScheme(scheme);
	}

	public void updateResult(long schemeId) {
		SczcScheme scheme = this.schemeManager.getScheme(schemeId);
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
		SczcPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (StringUtils.isBlank(periodData.getResult())) {
			throw new ServiceException("开奖结果为空！");
		}

		if (!periodData.validateResult()) {
			throw new ServiceException("开奖结果包含有未确定项X.");
		}

		if (scheme.getMode() == SalesMode.COMPOUND) {
			try {
				this.updateCompoundResult(scheme, periodData);
			} catch (DataException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.updateSingleResult(scheme, periodData);
			} catch (DataException e) {
				e.printStackTrace();
			}
		}
	}

	protected void updateCompoundResult(SczcScheme scheme, SczcPeriodData periodData) throws DataException {

		SczcCompoundItem[] betItems = scheme.getCompoundContent();

		String result = periodData.getResult();// 开奖结果
		if (betItems.length != result.length())
			throw new DataException("投注结果与开奖结果个数不符.");

		ZcCompoundPassWork passWork = new ZcCompoundPassWork(betItems, scheme.getMultiple(), result);
		ZcPrizeWork prizeWork = new ZcPrizeWork(periodData, null, passWork.getLost0_count(), passWork.getLost1_count(),
				passWork.getLost2_count(), passWork.getLost3_count());

		if (prizeWork.isWon()) {
			try {
				scheme.doUpdateResult(prizeWork.getWinItems());
				// 保存过关统计信息
				SczcPasscount entity = this.schemeManager.getSczcSchemeWonInfo(scheme.getId());
				if (entity == null) {
					entity = new SczcPasscount();
				}
				super.setSchemePasscountInstance(scheme, entity); 
				entity.setLost0(passWork.getLost0_count());
				entity.setLost1(passWork.getLost1_count());
				entity.setLost2(passWork.getLost2_count());
				entity.setLost3(passWork.getLost3_count());
				entity.setPasscount(passWork.getPasscount());
				schemeManager.saveSczcSchemeWonInfo(entity);
				this.checkTicket(prizeWork,scheme,periodData);
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
		} else {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
			scheme.setWon(false);
			//只统计中奖方案，不中奖 的删掉
			SczcPasscount entity = schemeManager.getSczcSchemeWonInfo(scheme.getId());
			if (entity != null) {
				schemeManager.deleteSczcSchemeWonInfo(entity);
			}
		}
		scheme = schemeManager.saveScheme(scheme);
	}

	protected void updateSingleResult(SczcScheme scheme, SczcPeriodData periodData) throws DataException {

		BufferedReader reader = new BufferedReader(new StringReader(scheme.getContent()));
		String result = periodData.getResult();// 开奖结果

		int lost0 = 0;
		int lost1 = 0;
		int lost2 = 0;
		int lost3 = 0;
		int line_count = 0;
		int passcount = 0;
		StringBuffer content0 = new StringBuffer();
		StringBuffer content1 = new StringBuffer();
		StringBuffer content2 = new StringBuffer();
		StringBuffer content3 = new StringBuffer();
		// boolean finalResult = true;

		try {
			String line = reader.readLine();
			int passcountTemp=0;
			while (line != null) {
				if (StringUtils.isNotBlank(line)) {
					if (line.length() != ZcUtils.getMatchCount(scheme.getLotteryType())
							&& line.length() != result.length())
						throw new DataException("");
					int lost_count = 0;
					int selected_count = 0;
					char bet, rs_bet;// 投注内容及结果存放变量
					for (int i = 0; i < line.length(); i++) {
						bet = line.charAt(i);
						rs_bet = result.charAt(i);
						if (Character.isDigit(bet)) {
							selected_count++;
						}
						if (Character.isDigit(rs_bet)) {
							if (Character.isDigit(bet) && bet != rs_bet) {
								lost_count++;
							}
						} else {
							// finalResult = false;
						}
					}
					if (lost_count == 0) {
						lost0++;
						content0.append(line).append("\r\n");
					}
					if (lost_count == 1) {
						lost1++;
						content1.append(line).append("\r\n");
					}
					if (lost_count == 2) {
						lost2++;
						content2.append(line).append("\r\n");
					}
					if (lost_count == 3) {
						lost3++;
						content3.append(line).append("\r\n");
					}
					// if (selected_count != this.getSelectedItemPerUnit()) {
					// throw new DataException("");
					// }
					passcountTemp=8-lost_count;
					if(passcountTemp>passcount){
						passcount=passcountTemp;
					}
					line_count++;
				}
				line = reader.readLine();
			}

			if (line_count * scheme.getMultiple() * 2 != scheme.getSchemeCost().intValue())
				throw new DataException("方案内容与投注金额不符.");

		} catch (IOException e) {
			e.printStackTrace();
		}
		int multiple = scheme.getMultiple();
		ZcPrizeWork prizeWork = new ZcPrizeWork(periodData, null, lost0 * multiple, lost1 * multiple, lost2 * multiple,
				lost3 * multiple);
		if (prizeWork.isWon()) {
			try {
				scheme.doUpdateResult(prizeWork.getWinItems());
				// 保存过关统计信息
				SczcPasscount entity = this.schemeManager.getSczcSchemeWonInfo(scheme.getId());
				if (entity == null) {
					entity = new SczcPasscount();
				}
				super.setSchemePasscountInstance(scheme, entity); 
				entity.setLost0(lost0);
				entity.setLost1(lost1);
				entity.setLost2(lost2);
				entity.setLost3(lost3);
				entity.setPasscount(passcount);
				schemeManager.saveSczcSchemeWonInfo(entity);
				this.checkTicket(prizeWork,scheme,periodData);
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
		} else {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
			scheme.setWon(false);
			//只统计中奖方案，不中奖 的删掉
			SczcPasscount entity = schemeManager.getSczcSchemeWonInfo(scheme.getId());
			if (entity != null) {
				schemeManager.deleteSczcSchemeWonInfo(entity);
			}
		}
		scheme = schemeManager.saveScheme(scheme);

	}
	protected void checkTicket(ZcPrizeWork prizeWork,SczcScheme scheme, SczcPeriodData periodData) {
		PrintInterface printInterface = printEntityManager.findPrintInterfaceBy(scheme.getSchemeNumber(), scheme.getLotteryType());
		if(null!=printInterface){
				List<Long> ticketIds = ticketEntityManager.findTicketIdByPrintInterfaceId(printInterface.getId());
				Ticket ticket = null;
				for (Long id : ticketIds) {
					 ticket = ticketEntityManager.getTicket(id);
					 ticket.setChecked(true);
					 if(SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())){
						///方案成功才更新出票
						 ZcPrizeWork ticketPrizeWork=null;
							try {
								if (scheme.getMode() == SalesMode.COMPOUND) {
									String[] itemStrs=ticket.getContent().split("-");
									if (itemStrs.length != ZcUtils.getMatchCount(this.getLotteryType())) {
										throw new DataException("方案内容选项个数异常.");
									}
									SczcCompoundItem[] betItems = new SczcCompoundItem[itemStrs.length];
									for (int i = 0; i < itemStrs.length; i++) {
										betItems[i] = new SczcCompoundItem(itemStrs[i]);
									}
									String result = periodData.getResult();// 开奖结果
									if (betItems.length != result.length())
										throw new DataException("投注结果与开奖结果个数不符.");

									ZcCompoundPassWork passWork = new ZcCompoundPassWork(betItems, scheme.getMultiple(), result);
									ticketPrizeWork= new ZcPrizeWork(periodData, null, passWork.getLost0_count(), passWork.getLost1_count(),
											passWork.getLost2_count(), passWork.getLost3_count());
									if(ticketPrizeWork.isWon()){
										ticket.setWon(true);
										ticket.setTicket_synchroned(Boolean.FALSE);
										doUpdateTicketPrize(prizeWork.getPrizeItems(),ticket);
										ticketEntityManager.saveTicket(ticket);
									}
								} else {
									/****************************/
									int bit=0;
									String[] contents=ticket.getContent().split(",");
									for(String content:contents){
										BufferedReader reader = new BufferedReader(new StringReader(content));
										String result = periodData.getResult();// 开奖结果
	
										int lost0 = 0;
										int lost1 = 0;
										int lost2 = 0;
										int lost3 = 0;
										int line_count = 0;
										int passcount = 0;
										StringBuffer content0 = new StringBuffer();
										StringBuffer content1 = new StringBuffer();
										StringBuffer content2 = new StringBuffer();
										StringBuffer content3 = new StringBuffer();
	
										try {
											String line = reader.readLine();
											int passcountTemp=0;
											while (line != null) {
												if (StringUtils.isNotBlank(line)) {
													if (line.length() != ZcUtils.getMatchCount(scheme.getLotteryType())
															&& line.length() != result.length())
														throw new DataException("");
													int lost_count = 0;
													int selected_count = 0;
													char bet, rs_bet;// 投注内容及结果存放变量
													for (int i = 0; i < line.length(); i++) {
														bet = line.charAt(i);
														rs_bet = result.charAt(i);
														if (Character.isDigit(bet)) {
															selected_count++;
														}
														if (Character.isDigit(rs_bet)) {
															if (Character.isDigit(bet) && bet != rs_bet) {
																lost_count++;
															}
														} else {
														}
													}
													if (lost_count == 0) {
														lost0++;
														content0.append(line).append("\r\n");
													}
													if (lost_count == 1) {
														lost1++;
														content1.append(line).append("\r\n");
													}
													if (lost_count == 2) {
														lost2++;
														content2.append(line).append("\r\n");
													}
													if (lost_count == 3) {
														lost3++;
														content3.append(line).append("\r\n");
													}
													// if (selected_count != this.getSelectedItemPerUnit()) {
													// throw new DataException("");
													// }
													passcountTemp=8-lost_count;
													if(passcountTemp>passcount){
														passcount=passcountTemp;
													}
													line_count++;
												}
												line = reader.readLine();
											}
	
											if (line_count * scheme.getMultiple() * 2 != scheme.getSchemeCost().intValue()/contents.length)
												throw new DataException("方案内容与投注金额不符.");
	
										} catch (IOException e) {
											e.printStackTrace();
										}
										
										/*******************************/
										int multiple = scheme.getMultiple();
										ticketPrizeWork = new ZcPrizeWork(periodData, null, lost0 * multiple, lost1 * multiple, lost2 * multiple,
												lost3 * multiple);
										if(ticketPrizeWork.isWon()){
											bit++;
										}
									}
									if(bit>0){
										ticket.setWon(true);
										ticket.setTicket_synchroned(Boolean.FALSE);
										doUpdateTicketPrize(prizeWork.getPrizeItems(),ticket);
										ticketEntityManager.saveTicket(ticket);
									}
								}
							} catch (DataException e) {
								e.printStackTrace();
							}
					 }
					
				}
		}
		
	}

	public void doUpdateTicketPrize(List<PrizeItem> list,Ticket ticket) throws DataException {
		if (list == null || list.isEmpty()) {
			throw new DataException("中奖信息为空！");
		}

		BigDecimal totalPrize = BigDecimal.ZERO;// 总奖金
		BigDecimal totalPrizeAfterTax = BigDecimal.ZERO;// 税后总奖金

		VariableString varPrizeLineText = new VariableString(Constant.PRIZE_LINE_TMPLATE, null);

		StringBuilder sb = new StringBuilder();

		BigDecimal prizeAfterTax;
		for (PrizeItem item : list) {
			varPrizeLineText.setVar("PRIZENAME", item.getWinItem().getWinName());
			varPrizeLineText.setVar("WINUNITS", item.getWinItem().getWinUnit());
			varPrizeLineText.setVar("UNIT_PRIZE", Constant.numFmt.format(item.getUnitPrize()));
			prizeAfterTax = item.getUnitPrizeAfterTax();
			varPrizeLineText.setVar("UNIT_PRIZE_TAXED", Constant.numFmt.format(prizeAfterTax));
			sb.append(varPrizeLineText.toString());

			totalPrize = totalPrize.add(BigDecimalUtil.multiply(item.getUnitPrize(),
					BigDecimalUtil.valueOf(item.getWinItem().getWinUnit())));
			totalPrizeAfterTax = totalPrizeAfterTax.add(BigDecimalUtil.multiply(prizeAfterTax,
					BigDecimalUtil.valueOf(item.getWinItem().getWinUnit())));
		}
		ticket.setTotalPrize(totalPrize.doubleValue());
		ticket.setTotalPrizeAfterTax(totalPrizeAfterTax.doubleValue());
		ticket.setWonDetail(sb.toString());
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.SCZC;
	}
}
