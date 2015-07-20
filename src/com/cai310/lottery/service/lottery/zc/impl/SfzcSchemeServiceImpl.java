package com.cai310.lottery.service.lottery.zc.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.cai310.lottery.dto.lottery.zc.SfzcSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.lottery.zc.SfzcPasscount;
import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.SchemeServiceImpl;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.PrizeItem;
import com.cai310.lottery.support.zc.CombinationBean;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.SchemeConverWork;
import com.cai310.lottery.support.zc.SfzcCompoundItem;
import com.cai310.lottery.support.zc.ZcCompoundItem;
import com.cai310.lottery.support.zc.ZcCompoundPassWork;
import com.cai310.lottery.support.zc.ZcPrizeWork;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.utils.JsonUtil;

@Service("sfzcSchemeServiceImpl")
@Transactional
public class SfzcSchemeServiceImpl extends SchemeServiceImpl<SfzcScheme, SfzcSchemeDTO> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected TicketEntityManager ticketEntityManager;
	
	@Autowired
	private SfzcSchemeEntityManagerImpl schemeManager;

	@Autowired
	private SfzcPeriodDataEntityManagerImpl periodDataManagerImpl;

	@Override
	protected SchemeEntityManager<SfzcScheme> getSchemeEntityManager() {
		return schemeManager;
	}
	
	@Resource
	protected PrintEntityManager printEntityManager;
	public void updatePrize(long schemeId) {
		SfzcScheme scheme = schemeManager.getScheme(schemeId);
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

		SfzcPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
		if (null==periodData.getFirstPrize() || null==periodData.getFirstPrize_14()|| null==periodData.getSecondPrize_14()) {
			throw new ServiceException("未设置中奖奖金！");
		}

		SfzcPasscount wonInfo = this.schemeManager.getSfzcSchemeWonInfo(scheme.getId());

		if (wonInfo == null || !wonInfo.isWon()) {
			throw new ServiceException("数据异常！");
		}
		ZcPrizeWork prizeWork = new ZcPrizeWork(periodData, scheme.getPlayType(), wonInfo.getLost0(), wonInfo
				.getLost1(), wonInfo.getLost2(), wonInfo.getLost3());
		try {
			scheme.doUpdatePrize(prizeWork.getPrizeItems());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		scheme = schemeManager.saveScheme(scheme);
	}

	public void updateResult(long schemeId) {
		SfzcScheme scheme = this.schemeManager.getScheme(schemeId);
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
		SfzcPeriodData periodData = periodDataManagerImpl.getPeriodData(scheme.getPeriodId());
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
				throw new ServiceException("更新中奖出错方案号{"+schemeId+"}！"+ e.getMessage());
			}
		} else {
			try {
				this.updateSingleResult(scheme, periodData);
			} catch (DataException e) {
				throw new ServiceException("更新中奖出错方案号{"+schemeId+"}！"+ e.getMessage());
			}
		}
	}

	protected void updateCompoundResult(SfzcScheme scheme, SfzcPeriodData periodData) throws DataException {

		SfzcCompoundItem[] betItems = scheme.getCompoundContent();
		String result = periodData.getResult();// 开奖结果

		if (betItems.length != result.length())
			throw new DataException("投注结果与开奖结果个数不符.");

		List<ZcCompoundItem> danList = new ArrayList<ZcCompoundItem>();
		List<ZcCompoundItem> unDanList = new ArrayList<ZcCompoundItem>();
		for (SfzcCompoundItem item : betItems) {
			if (item.selectedCount() > 0) {
				if (item.isShedan()) {
					danList.add(item);
				} else {
					unDanList.add(item);
				}
			}
		}

		ZcCompoundPassWork passWork = null;

		if (scheme.getPlayType() == PlayType.SFZC9) {// 九场
			SchemeConverWork<ZcCompoundItem> work = new SchemeConverWork<ZcCompoundItem>(
					ZcUtils.SFZC9_MATCH_MINSELECT_COUNT, danList, unDanList, scheme.getDanMinHit(), -1);
			passWork = new ZcCompoundPassWork(work.getResultList(), scheme.getMultiple(), result);
			// 获取组合额外中奖信息
			scheme.setExtraPrizeMsg(this.getExtraPrizeMsg(passWork.getCombinationMap(), periodData));

		} else {
			passWork = new ZcCompoundPassWork(betItems, scheme.getMultiple(), result);
		}

		ZcPrizeWork prizeWork = new ZcPrizeWork(periodData, scheme.getPlayType(), passWork.getLost0_count(), passWork
				.getLost1_count(), passWork.getLost2_count(), passWork.getLost3_count());

		if (prizeWork.isWon()) {
			try {
				scheme.doUpdateResult(prizeWork.getWinItems());

				// 保存过关统计信息
				SfzcPasscount entity = this.schemeManager.getSfzcSchemeWonInfo(scheme.getId());
				if (entity == null) {
					entity = new SfzcPasscount();
				}
				super.setSchemePasscountInstance(scheme, entity); 
				entity.setPlayType(scheme.getPlayType());
				entity.setLost0(passWork.getLost0_count());
				entity.setLost1(passWork.getLost1_count());
				entity.setLost2(passWork.getLost2_count());
				entity.setLost3(passWork.getLost3_count());
				entity.setPasscount(passWork.getPasscount());
				schemeManager.saveSfzcSchemeWonInfo(entity);
				this.checkTicket(prizeWork,scheme,periodData);
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
		} else {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
			scheme.setWon(false);
			//只统计中奖方案，不中奖 的删掉
			SfzcPasscount entity = schemeManager.getSfzcSchemeWonInfo(scheme.getId());
			if (entity != null) {
				schemeManager.deleteSfzcSchemeWonInfo(entity);
			}
		}
		
		scheme = schemeManager.saveScheme(scheme);
	}

	protected void updateSingleResult(SfzcScheme scheme, SfzcPeriodData periodData) throws DataException {

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
//		boolean finalResult = true;

		try {
			String line = reader.readLine();
			int passcountTemp=0;
			while (line != null) {
				if (StringUtils.isNotBlank(line)) {
					if (line.length() != ZcUtils.getMatchCount(scheme.getLotteryType())
							&& line.length() != result.length())
						throw new DataException("单式选项不符");
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
//							finalResult = false;
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
					if (scheme.getPlayType() == PlayType.SFZC9) {// 九场
						passcountTemp=9-lost_count;
					}else {
						passcountTemp=14-lost_count;
					}
					if(passcountTemp>passcount){
						passcount=passcountTemp;
					}
					line_count++;
				}
				line = reader.readLine();
			}

			int multiple = scheme.getMultiple();
            lost0=lost0 * multiple;
            lost1=lost1 * multiple;
            lost2=lost2 * multiple;
            lost3=lost3 * multiple;
            
			if (line_count * scheme.getMultiple() * 2 != scheme.getSchemeCost().intValue())
				throw new DataException("方案内容与投注金额不符.");

		} catch (IOException e) {
			e.printStackTrace();
		}
		ZcPrizeWork prizeWork = new ZcPrizeWork(periodData, scheme.getPlayType(), lost0, lost1, lost2, lost3);
		if (prizeWork.isWon()) {
			try {
				scheme.doUpdateResult(prizeWork.getWinItems());
				// 保存过关统计信息
				SfzcPasscount entity = this.schemeManager.getSfzcSchemeWonInfo(scheme.getId());
				if (entity == null) {
					entity = new SfzcPasscount();
				}
				super.setSchemePasscountInstance(scheme, entity); 
				entity.setPlayType(scheme.getPlayType());
				entity.setLost0(lost0);
				entity.setLost1(lost1);
				entity.setLost2(lost2);
				entity.setLost3(lost3);
				entity.setPasscount(passcount);
				schemeManager.saveSfzcSchemeWonInfo(entity);
				this.checkTicket(prizeWork,scheme,periodData);
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
		} else {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
			scheme.setWon(false);
			SfzcPasscount entity = schemeManager.getSfzcSchemeWonInfo(scheme.getId());
			if (entity != null) {
				schemeManager.deleteSfzcSchemeWonInfo(entity);
			}
			
		}
		scheme = schemeManager.saveScheme(scheme);
	}

	@Override
	protected SfzcScheme newSchemeInstance(SfzcSchemeDTO schemeDTO) {
		SfzcScheme scheme = super.newSchemeInstance(schemeDTO);
		scheme.setPlayType(schemeDTO.getPlayType());
		return scheme;
	}

	/**
	 * 获取中奖组合额外信息
	 * 
	 */
	private String getExtraPrizeMsg(Map<String, CombinationBean> combinationMap, SfzcPeriodData periodData) {
		CombinationBean value = null;
		for (Map.Entry<String, CombinationBean> entry : combinationMap.entrySet()) {
			value = (CombinationBean) entry.getValue();
			value.setPrize(value.getWonUnits() * periodData.getFirstPrize() * 1.0d);
			value.setPrizeAfterTax(value.getWonUnits() * Constant.countPrizeAfterTax(periodData.getFirstPrize()));
		}
		return JsonUtil.getJsonString4JavaPOJO(combinationMap);
	}
	protected void checkTicket(ZcPrizeWork prizeWork,SfzcScheme scheme, SfzcPeriodData periodData) {
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
								SfzcCompoundItem[] betItems = new SfzcCompoundItem[itemStrs.length];
								for (int i = 0; i < itemStrs.length; i++) {
									betItems[i] = new SfzcCompoundItem(itemStrs[i], i);
								}
								String result = periodData.getResult();// 开奖结果
								if (betItems.length != result.length())
									throw new DataException("投注结果与开奖结果个数不符.");
								
								List<ZcCompoundItem> danList = new ArrayList<ZcCompoundItem>();
								List<ZcCompoundItem> unDanList = new ArrayList<ZcCompoundItem>();
								for (SfzcCompoundItem item : betItems) {
									if (item.selectedCount() > 0) {
										if (item.isShedan()) {
											danList.add(item);
										} else {
											unDanList.add(item);
										}
									}
								}
								ZcCompoundPassWork passWork = null;

								if (scheme.getPlayType() == PlayType.SFZC9) {// 九场
									SchemeConverWork<ZcCompoundItem> work = new SchemeConverWork<ZcCompoundItem>(
											ZcUtils.SFZC9_MATCH_MINSELECT_COUNT, danList, unDanList, scheme.getDanMinHit(), -1);
									passWork = new ZcCompoundPassWork(work.getResultList(), scheme.getMultiple(), result);
									// 获取组合额外中奖信息
									scheme.setExtraPrizeMsg(this.getExtraPrizeMsg(passWork.getCombinationMap(), periodData));

								} else {
									passWork = new ZcCompoundPassWork(betItems, scheme.getMultiple(), result);
								}

								ticketPrizeWork= new ZcPrizeWork(periodData, scheme.getPlayType(), passWork.getLost0_count(), passWork
										.getLost1_count(), passWork.getLost2_count(), passWork.getLost3_count());
								if (ticketPrizeWork.isWon()) {
									ticket.setWon(true);
									ticket.setTicket_synchroned(Boolean.FALSE);
									doUpdateTicketPrize(prizeWork.getPrizeItems(),
											ticket);
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
									boolean finalResult = true;
	
									try {
										String line = reader.readLine();
										int passcountTemp=0;
										while (line != null) {
											if (StringUtils.isNotBlank(line)) {
												if (line.length() != ZcUtils.getMatchCount(scheme.getLotteryType())
														&& line.length() != result.length())
													throw new DataException("方案"+scheme.getId()+"单式选项不符");
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
														finalResult = false;
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
												if (scheme.getPlayType() == PlayType.SFZC9) {// 九场
													passcountTemp=9-lost_count;
												}else {
													passcountTemp=14-lost_count;
												}
												if(passcountTemp>passcount){
													passcount=passcountTemp;
												}
												line_count++;
											}
											line = reader.readLine();
										}
	
										int multiple = scheme.getMultiple();
							            lost0=lost0 * multiple;
							            lost1=lost1 * multiple;
							            lost2=lost2 * multiple;
							            lost3=lost3 * multiple;
							            
										if (line_count * scheme.getMultiple() * 2 != scheme.getSchemeCost().intValue()/contents.length)
											throw new DataException("方案内容与投注金额不符.");
									} catch (IOException e) {
										e.printStackTrace();
									}
									
									/*******************************/
									ticketPrizeWork = new ZcPrizeWork(periodData,scheme.getPlayType(), lost0, lost1, lost2, lost3);
									if(ticketPrizeWork.isWon()){
										bit++;
									}
								}
								if (bit>0) {
									ticket.setWon(true);
									ticket.setTicket_synchroned(Boolean.FALSE);
									doUpdateTicketPrize(prizeWork.getPrizeItems(),
											ticket);
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
		return Lottery.SFZC;
	}

	 
}
