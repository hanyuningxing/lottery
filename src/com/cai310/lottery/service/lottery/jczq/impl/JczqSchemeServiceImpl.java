package com.cai310.lottery.service.lottery.jczq.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dto.lottery.jczq.JczqSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jczq.JczqChasePlan;
import com.cai310.lottery.entity.lottery.jczq.JczqChasePlanDetail;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqSchemeMatch;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.MatchNotEndException;
import com.cai310.lottery.service.lottery.impl.SchemeServiceImpl;
import com.cai310.lottery.service.lottery.jczq.JczqChasePlanDetailEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqChasePlanEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeService;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.lottery.support.PrintWonItem;
import com.cai310.lottery.support.PrizeWork;
import com.cai310.lottery.support.jczq.JczqCompoundContent;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqMultiplePassPrizeWork;
import com.cai310.lottery.support.jczq.JczqResult;
import com.cai310.lottery.support.jczq.JczqSchemeConverWork;
import com.cai310.lottery.support.jczq.JczqSimplePrizeWork;
import com.cai310.lottery.support.jczq.JczqSingleContent;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.TicketItem;
import com.cai310.lottery.support.jczq.TicketItemSingle;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.lottery.utils.local.Win310Util;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service("jczqSchemeServiceImpl")
@Transactional
public class JczqSchemeServiceImpl extends
		SchemeServiceImpl<JczqScheme, JczqSchemeDTO> implements
		JczqSchemeService {

	@Autowired
	private JczqSchemeEntityManagerImpl schemeManager;

	@Autowired
	private JczqMatchEntityManager matchEntityManager;

	@Autowired
	private JczqChasePlanDetailEntityManager jczqChasePlanDetailEntityManager;

	@Autowired
	private JczqChasePlanEntityManager jczqChasePlanEntityManager;

	@Resource
	protected TicketEntityManager ticketEntityManager;

	@Resource
	protected PrintEntityManager printEntityManager;

	@Override
	protected SchemeEntityManager<JczqScheme> getSchemeEntityManager() {
		return schemeManager;
	}

	public void updatePrize(long schemeId) {
		updateResult(schemeId);
	}

	public void updateResult(long schemeId) {
		JczqScheme scheme = schemeManager.getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");
		if (scheme.isUpdateWon())
			throw new ServiceException("方案[" + scheme.getSchemeNumber()
					+ "]已更新中奖,不能再更新中奖.");
		else if (scheme.isUpdatePrize())
			throw new ServiceException("方案[" + scheme.getSchemeNumber()
					+ "]已更新奖金,不能再更新中奖.");
		else if (scheme.isPrizeSended())
			throw new ServiceException("方案[" + scheme.getSchemeNumber()
					+ "]已派奖,不能再更新中奖.");

		switch (scheme.getMode()) {
		case COMPOUND:
			doUpdateResultOfCOMPOUND(scheme);
			break;
		case SINGLE:
			doUpdateResultOfSINGLE(scheme);
			break;
		default:
			throw new RuntimeException("数据异常：投注方式不合法.");
		}
	}

	/**
	 * 单式方案更新中奖结果(包括优化方案)
	 * 
	 * @param scheme
	 */
	@SuppressWarnings("unchecked")
	protected void doUpdateResultOfSINGLE(JczqScheme scheme) {
		if (scheme.getState() == SchemeState.REFUNDMENT) {
			scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
			scheme.setWon(false);
			scheme.setWonDetail("");
			scheme.setPrize(BigDecimal.ZERO);
			scheme.setPrizeAfterTax(BigDecimal.ZERO);
			scheme.setPrizeDetail("");
		} else {
			final String lineSeq = "\r\n";// 行信息分隔符
			final JczqSingleContent singleContent = scheme.getSingleContent();
			final List<String> matchKeys = singleContent.getMatchkeys();
			final List<String> playTypes = singleContent.getPlayTypes();// 混合投中使用
			String[] contentArr = singleContent.converContent2Arr();
			List<List<JczqMatchItem>> contentList = Lists.newArrayList();// 方案集合<赛程集合>
			List<JczqMatchItem> itemList = null;// 赛程集合
			PlayType playType = scheme.getPlayType();

			for (int index = 0; index < contentArr.length; index++) {
				String[] ordinalArr = contentArr[index].split(",");
				String[] playTypeArr = null;
				if (playType == PlayType.MIX) {
					if (playTypes.size() == contentArr.length) {
						String playTypeStr = playTypes.get(index);
						if (StringUtil.isEmpty(playTypeStr)) {
							throw new ServiceException("第" + (index + 1)
									+ "注的内容相对应的玩法集合为空");
						} else {
							playTypeArr = playTypeStr.split(",");
						}
					} else {
						throw new ServiceException("投注内容数与相对应的玩法集合数不一致");
					}
				}

				itemList = Lists.newArrayList();
				for (int i = 0; i < ordinalArr.length; i++) {
					String ordinalStr = ordinalArr[i];
					if ("#".equals(ordinalStr))
						continue;

					JczqMatchItem matchItem = new JczqMatchItem();
					int ordinal = Integer.parseInt(ordinalStr);
					if (playType == PlayType.MIX) {
						matchItem.setPlayType(PlayType
								.valueOfName(playTypeArr[i]));
					} else {
						matchItem.setPlayType(playType);
					}
					matchItem.setValue(0x1 << ordinal);
					matchItem.setMatchKey(matchKeys.get(i));
					itemList.add(matchItem);
				}
				contentList.add(itemList);
			}

			// 获取赛程的开奖结果数据
			List<JczqMatch> mathList = matchEntityManager.findMatchs(matchKeys);
			Map<String, JczqResult> drawdMatchMap = Maps.newHashMap();

			List<JczqMatchItem> matchItems = singleContent.getItems();
			Map<String, JczqMatch> matchMap = Maps.newHashMap();
			for (JczqMatch match : mathList) {
				String matchKey = match.getMatchKey();
				if (!match.isEnded() && !match.isCancel())
					throw new ServiceException("方案[" + scheme.getSchemeNumber()
							+ "]-[" + matchKey + "]未结束赛事，不能更新中奖");

				JczqResult result = new JczqResult();
				result.setMatchKey(matchKey);
				result.setCancel(match.isCancel());
				Item rs = null;

				if (PlayType.MIX.equals(playType)) {
					for (JczqMatchItem matchItemT : matchItems) {
						if (matchItemT.getMatchKey().equals(matchKey)) {
							rs = match.getResult(matchItemT.getPlayType());
							break;
						}
					}
				} else {
					rs = match.getResult(playType);
				}
				if (!match.isCancel() && rs == null)
					throw new ServiceException("[" + matchKey + "]未填开奖结果.");

				result.setResultItem(rs);
				drawdMatchMap.put(matchKey, result);
				matchMap.put(matchKey, match);
			}

			// 出票赔率 Map<matchKey, Map<item, Double>>
			List<Map<String, Map<String, Double>>> printAwardList = null;
			List<TicketItemSingle> ticketList = null;
			try {
				ticketList = scheme.getSingleTicketList();
				if (ticketList == null || ticketList.isEmpty()) {
					throw new ServiceException("方案拆票内容为空");
				}
				if (scheme.getState() == SchemeState.CANCEL && !scheme.isRealPrinted()) {// 撤销(流产)取用发起时的赔率作为奖金计算依据
					List<JczqMatchItem> selectedMatchItems = singleContent
							.getItems();
					Map<String, List<String>> selectedMatchItemMap = Maps
							.newHashMap();
					for (JczqMatchItem matchItem : selectedMatchItems) {
						selectedMatchItemMap.put(matchItem.getMatchKey(),
								matchItem.getSps());
					}
					printAwardList = Lists.newArrayList();
					for (TicketItemSingle ticket : ticketList) {
						Map<String, Map<String, Double>> matchAwardMap = Maps
								.newHashMap();
						List<JczqMatchItem> itemsOfContent = contentList
								.get(ticket.getIndex());
						Map<String, Double> itemAwardMap = Maps.newHashMap();
						for (JczqMatchItem matchItem : itemsOfContent) {
							Item[] itemsOfPlayType = matchItem.getPlayType()
									.getAllItems();
							Item selectedItem = null;
							for (Item item : itemsOfPlayType) {
								if (matchItem.hasSelect(item)) {
									selectedItem = item;
									break;
								}
							}
							String sp = null;
							try {
								sp = selectedMatchItemMap.get(
										matchItem.getMatchKey()).get(
										selectedItem.ordinal());
							} catch (Exception e) {
								sp = "0";
							}
							itemAwardMap.put(selectedItem.getValue(),
									Double.valueOf(sp));
							matchAwardMap.put(matchItem.getMatchKey(),
									itemAwardMap);
						}
						printAwardList.add(matchAwardMap);
					}
				} else if (scheme.isRealPrinted()) {
					printAwardList = scheme.getPrintAwardList();
				} else {
					throw new MatchNotEndException("出票SP值未更新.");
				}
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}

			// 开始更新中奖结果
			PassType passType = scheme.getPassTypeList().get(0);
			int passMatchCount = passType.getMatchCount();
			StringBuilder wonDetail = new StringBuilder();
			StringBuilder prizeDetail = new StringBuilder();
			double totalPrize = 0.0d;
			double totalPrizeAfterTax = 0.0d;
			// final Map<String, CombinationBean> combinationMap = new
			// HashMap<String, CombinationBean>();// 存放中奖的组合
			Integer ticket_index = 0;
			final Map<Integer, JczqSimplePrizeWork> ticket_combinationMap = Maps
					.newLinkedHashMap();// 用于出票回查
			for (int i = 0; i < ticketList.size(); i++) {
				List<List<JczqMatchItem>> contentItemLists = null;// 各单式方案选择场次列表的集合
				TicketItemSingle ticketItem = ticketList.get(i);
				List<JczqMatchItem> contentItemList = contentList
						.get(ticketItem.getIndex());// 各方案选择的场次

				// 是否为普通过关，不是则拆解为单式进行中奖处理
				if (contentItemList.size() > passMatchCount) {
					@SuppressWarnings("rawtypes")
					JczqSchemeConverWork converWork = new JczqSchemeConverWork(
							passMatchCount, Lists.newArrayList(),
							contentItemList, -1, -1);
					contentItemLists = converWork.getResultList();
				} else {
					contentItemLists = Lists.newArrayList();
					contentItemLists.add(contentItemList);
				}

				Map<String, Map<String, Double>> matchAwardMap = printAwardList
						.get(i);
				if (matchAwardMap == null)
					throw new ServiceException("出票SP值未更新.");
				// 对方案进行中奖验证
				for (List<JczqMatchItem> selectedItemList : contentItemLists) {
					// List<JczqMatchItem> correctCombList = new
					// ArrayList<JczqMatchItem>();//每个方案过关场次

					// /
					// for(JczqMatchItem selectedItem : selectedItemList){
					// JczqResult result =
					// drawdMatchMap.get(selectedItem.getMatchKey());
					// Item item = result.getResultItem();
					// if(!selectedItem.hasSelect(item)){//没有猜中
					// break;
					// }
					// //获取出票方返回的对应赔率
					// Double resultSp =
					// Double.valueOf(""+matchAwardMap.get(selectedItem.getMatchKey()).get(item.getValue()));
					//
					// if (resultSp-Double.valueOf(0)==0)
					// throw new ServiceException("第" +
					// (selectedItem.getMatchKey()) + "场开奖SP值为0.");
					// result.setResultSp(resultSp);
					// correctCombList.add(selectedItem);
					// }
					// 更新中奖
					List<JcWonMatchItem> correctList = new ArrayList<JcWonMatchItem>();
					// //
					for (JczqMatchItem matchItem : selectedItemList) {
						String matchKey = matchItem.getMatchKey();
						JczqMatch match = matchMap.get(matchKey);

						Map<String, Double> awardMap = matchAwardMap
								.get(matchKey);

						if (match.isCancel()) {
							JcWonMatchItem wonItem = new JcWonMatchItem();
							wonItem.setMatchKey(matchKey);
							wonItem.setSelectCount(matchItem.selectedCount());
							wonItem.setCancel(true);
							wonItem.setAward(1d);
							correctList.add(wonItem);
							continue;
						} else if (match.isEnded()) {
							Item rs = null;
							if (scheme.getPassMode().equals(PassMode.MIX_PASS)) {
								rs = match.getResult(matchItem.getPlayType());
							} else {
								rs = match.getResult(scheme.getPlayType());
							}
							if (rs != null) {
								if (matchItem.hasSelect(rs)) {
									JcWonMatchItem wonItem = new JcWonMatchItem();
									wonItem.setMatchKey(matchKey);
									wonItem.setSelectCount(matchItem
											.selectedCount());
									wonItem.setResultItem(rs);
									Double award = Double.valueOf(""
											+ awardMap.get(rs.getValue()));
									wonItem.setAward(award);
									correctList.add(wonItem);
									continue;
								}
							}
						}
					}
					if (correctList.size() == passMatchCount) {// 猜中场次等于过关场次即为中奖

						JczqSimplePrizeWork prizeWork = new JczqSimplePrizeWork(
								scheme.getPassMode(), ticketItem.getMultiple(),
								passType, correctList);
						wonDetail.append(prizeWork.getPrizeDetail());
						prizeDetail.append(prizeWork.getPrizeDetail());

						totalPrize += prizeWork.getTotalPrize();
						totalPrizeAfterTax += prizeWork.getTotalPrizeAfterTax();
						// combinationMap.put(combination.generateKey(),
						// combination);
						ticket_combinationMap.put(ticket_index, prizeWork);
					}

					ticket_index++;

					// if (correctCombList.size() == passMatchCount)
					// {//猜中场次等于过关场次即为中奖
					//
					// JczqSimplePrizeWork prizeWork = new
					// JczqSimplePrizeWork(scheme.getPassMode(), multiple,
					// passType, correctList);
					//
					// Collections.sort(correctCombList);
					// JczqPrizeItem prizeItem = new
					// JczqPrizeItem(drawdMatchMap,
					// correctCombList,ticketItem.getMultiple());
					//
					// wonDetail.append(prizeItem.getLineWonDetail()).append(lineSeq);
					// prizeDetail.append(prizeItem.getLinePrizeDetail()).append(lineSeq);
					//
					// totalPrize += prizeItem.getPrize();
					// totalPrizeAfterTax += prizeItem.getPrizeAfterTax();
					//
					// CombinationBean combination = new CombinationBean();
					// combination.setPassTypeOrdinal(passType.ordinal());
					// combination.setItems(correctCombList);
					// combination.setPrize(prizeItem.getPrize());
					// combination.setPrizeAfterTax(prizeItem.getPrizeAfterTax());
					//
					// //combinationMap.put(combination.generateKey(),
					// combination);
					// ticket_combinationMap.put(ticketItem.getIndex(),
					// combination);
					// }
				}
			}
			if (totalPrize > 0) {
				wonDetail.delete(wonDetail.length() - lineSeq.length(),
						wonDetail.length());// 删除最后一个分隔符
				prizeDetail.delete(prizeDetail.length() - lineSeq.length(),
						prizeDetail.length());// 删除最后一个分隔符
				try {
					scheme.doUpdateResult(wonDetail.toString());
					scheme.doUpdatePrize(BigDecimal.valueOf(totalPrize),
							BigDecimal.valueOf(totalPrizeAfterTax),
							prizeDetail.toString());

					// 出票回查
					if (null != ticket_combinationMap
							&& !ticket_combinationMap.isEmpty()) {
						PrintInterface printInterface = printEntityManager
								.findPrintInterfaceBy(scheme.getSchemeNumber(),
										scheme.getLotteryType());
						if (null != printInterface) {
							List<Long> ticketIds = ticketEntityManager
									.findTicketIdByPrintInterfaceId(printInterface
											.getId());
							Ticket ticket = null;
							JczqSimplePrizeWork prizeWork = null;
							Integer ticketIndex = null;
							for (Long id : ticketIds) {
								ticket = ticketEntityManager.getTicket(id);
								ticket.setChecked(true);
								if (null != ticket.getTicketIndex()
										&& SchemePrintState.SUCCESS
												.equals(scheme
														.getSchemePrintState())) {
									// /方案成功才更新出票
									for (Map.Entry<Integer, JczqSimplePrizeWork> combinationMap : ticket_combinationMap
											.entrySet()) {
										prizeWork = combinationMap.getValue();
										ticketIndex = combinationMap.getKey();
										if (ticketIndex.equals(ticket
												.getTicketIndex())) {
											//
											ticket.setWon(true);
											ticket.setTotalPrize(prizeWork
													.getTotalPrize());
											ticket.setTotalPrizeAfterTax(prizeWork
													.getTotalPrizeAfterTax());
											ticket.setWonDetail(prizeWork
													.getWonDetail());
											ticket.setTicket_synchroned(Boolean.FALSE);
										}
									}
								}
								ticketEntityManager.saveTicket(ticket);
							}

						}

					}

				} catch (DataException e) {
					throw new ServiceException(e.getMessage());
				}
				// scheme.setExtraPrizeMsg(JczqUtil.genExtraPrizeMsg(combinationMap));//额外的中奖信息(暂时未使用该信息字段)
			} else {
				scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
				scheme.setWon(false);
				scheme.setWonDetail("");
				scheme.setPrize(BigDecimal.ZERO);
				scheme.setPrizeAfterTax(BigDecimal.ZERO);
				scheme.setPrizeDetail("");
			}
		}

		scheme = schemeManager.saveScheme(scheme);
	}

	/**
	 * 复式方案更新中奖结果
	 * 
	 * @param scheme
	 */
	protected void doUpdateResultOfCOMPOUND(JczqScheme scheme) {
		JczqCompoundContent compoundConent = scheme.getCompoundContent();
		List<JczqMatchItem> itemList = compoundConent.getItems();

		List<String> matchKeys = Lists.newArrayList();
		for (JczqMatchItem item : itemList) {
			matchKeys.add(item.getMatchKey());
		}

		List<JczqMatch> mathList = matchEntityManager.findMatchs(matchKeys);
		Map<String, JczqMatch> drawdMatchMap = Maps.newHashMap();
		for (JczqMatch match : mathList) {
			String matchKey = match.getMatchKey();
			if (!match.isEnded() && !match.isCancel())
				throw new ServiceException("[" + matchKey + "]未结束.");
			Item rs = null;
			if (PlayType.MIX.equals(scheme.getPlayType())) {
				for (JczqMatchItem matchItem : itemList) {
					if (matchItem.getMatchKey().equals(match.getMatchKey())) {
						rs = match.getResult(matchItem.getPlayType());
						break;
					}
				}
			} else {
				rs = match.getResult(scheme.getPlayType());
			}
			if (!match.isCancel() && rs == null)
				throw new ServiceException("[" + matchKey + "]未填开奖结果.");
			drawdMatchMap.put(matchKey, match);
		}

		PrizeWork prizeWork;
		List<JcWonMatchItem> correctList = Lists.newArrayList();
		List<PassType> passTypeList = scheme.getPassTypeList();
		switch (scheme.getPassMode()) {
		case SINGLE:
		case PASS:
		case MIX_PASS:
			if (scheme.getState() == SchemeState.REFUNDMENT) {
				prizeWork = null;
			} else {
				List<TicketItem> ticketList = null;
				List<Map<String, Map<String, Double>>> printAwardList = null;
				try {
					ticketList = scheme.getTicketList();
					if (ticketList == null || ticketList.isEmpty()) {
						throw new ServiceException("方案拆票内容为空");
					}
					if (scheme.getState() == SchemeState.CANCEL && !scheme.isRealPrinted()) {// 撤销(流产)且未出票
						List<JczqMatchItem> selectedMatchItems = compoundConent
								.getItems();
						Map<String, List<String>> selectedMatchItemMap = Maps
								.newHashMap();
						for (JczqMatchItem matchItem : selectedMatchItems) {
							selectedMatchItemMap.put(matchItem.getMatchKey(),
									matchItem.getSps());
						}
						printAwardList = Lists.newArrayList();
						Map<String, Map<String, Double>> matchAwardMap = null;
						List<JczqMatchItem> ticketMatchItems = null;
						for (TicketItem ticketItem : ticketList) {
							matchAwardMap = Maps.newHashMap();
							ticketMatchItems = Lists.newArrayList();
							if (ticketItem.getMatchFlag() == 0) {
								ticketMatchItems = selectedMatchItems;
							} else {
								for (int p = 0; p < itemList.size(); p++) {
									if ((ticketItem.getMatchFlag() & (0x1l << p)) > 0)
										ticketMatchItems.add(itemList.get(p));
								}
							}
							Map<String, Double> itemAwardMap = null;
							for (JczqMatchItem matchItem : ticketMatchItems) {
								Item[] itemsOfPlayType = matchItem
										.getPlayType().getAllItems();
								itemAwardMap = Maps.newHashMap();
								String sp = null;
								for (Item item : itemsOfPlayType) {
									if (matchItem.hasSelect(item)) {
										if (matchItem.getSps().size() == 0) {
											return;
										}
										sp = matchItem.getSps().get(
												item.ordinal());
										itemAwardMap.put(item.getValue(),
												Double.valueOf(sp));
									}
								}
								matchAwardMap.put(matchItem.getMatchKey(),
										itemAwardMap);
							}
							printAwardList.add(matchAwardMap);
						}
					} else if (scheme.isRealPrinted()) {
						printAwardList = scheme.getPrintAwardList();
					} else {
						throw new MatchNotEndException("出票SP值未更新.");
					}
				} catch (DataException e) {
					throw new ServiceException(e.getMessage(), e);
				}
				if (printAwardList.size() != ticketList.size())
					throw new ServiceException("出票SP值数据异常.");

				if (ticketList.size() > 1) {
					for (Map<String, Map<String, Double>> map : printAwardList) {
						if (map == null)
							throw new MatchNotEndException("出票SP值未更新.");
					}

					prizeWork = new JczqMultiplePassPrizeWork(
							scheme.getPlayType(), scheme.getPassMode(),
							itemList, ticketList, printAwardList, drawdMatchMap);
				} else {
					Map<String, Map<String, Double>> matchPrintAwardMap = null;
					if (printAwardList != null && printAwardList.size() == 1) {
						matchPrintAwardMap = printAwardList.get(0);
						for (int i = 0; i < itemList.size(); i++) {
							JczqMatchItem matchItem = itemList.get(i);
							String matchKey = matchItem.getMatchKey();

							JcWonMatchItem wonItem = new JcWonMatchItem();
							wonItem.setMatchKey(matchKey);
							wonItem.setSelectCount(matchItem.selectedCount());

							JczqMatch match = drawdMatchMap.get(matchKey);
							boolean won = false;
							if (match.isCancel()) {
								wonItem.setAward(1d);
								wonItem.setCancel(true);
								won = true;
							} else if (match.isEnded()) {
								Map<String, Double> printAwardMap = matchPrintAwardMap
										.get(matchKey);
								Item rs = null;
								if (PlayType.MIX.equals(scheme.getPlayType())) {
									rs = match.getResult(matchItem
											.getPlayType());
								} else {
									rs = match.getResult(scheme.getPlayType());
								}
								Object printAwardObj = null;
								if (matchItem.hasSelect(rs)) {
									printAwardObj = printAwardMap.get(rs
											.getValue());
									if (printAwardObj == null)
										throw new MatchNotEndException(
												"出票SP值未更新.");
									Double printAward = Double.valueOf(""
											+ printAwardObj);
									if (printAward == null || printAward <= 0)
										throw new MatchNotEndException(
												"出票SP值未更新.");

									wonItem.setAward(printAward);
									wonItem.setResultItem(rs);
									won = true;
								}
							}
							if (won)
								correctList.add(wonItem);
						}

						if (passTypeList.size() > 1)
							throw new ServiceException("过关类型异常.");

						prizeWork = new JczqSimplePrizeWork(
								scheme.getPassMode(), scheme.getMultiple(),
								passTypeList.get(0), correctList);
					} else {
						// //没有出票，退款。把场次设为取消
						for (int i = 0; i < itemList.size(); i++) {
							JczqMatchItem matchItem = itemList.get(i);
							String matchKey = matchItem.getMatchKey();

							JcWonMatchItem wonItem = new JcWonMatchItem();
							wonItem.setMatchKey(matchKey);
							wonItem.setSelectCount(matchItem.selectedCount());
							boolean won = true;
							wonItem.setCancel(true);
							if (won)
								correctList.add(wonItem);
						}

						if (passTypeList.size() > 1)
							throw new ServiceException("过关类型异常.");

						prizeWork = new JczqSimplePrizeWork(
								scheme.getPassMode(), scheme.getMultiple(),
								passTypeList.get(0), correctList);
					}
				}
			}
			break;
		default:
			throw new ServiceException("过关模式不合法.");
		}

		if (prizeWork != null && prizeWork.isWon()) {
			try {
				scheme.doUpdateResult(prizeWork.getWonDetail().toString());
				scheme.doUpdatePrize(
						BigDecimal.valueOf(prizeWork.getTotalPrize()),
						BigDecimal.valueOf(prizeWork.getTotalPrizeAfterTax()),
						prizeWork.getPrizeDetail().toString());
				// 出票回查
				if (null != prizeWork.getPrintWonItemList()
						&& !prizeWork.getPrintWonItemList().isEmpty()) {
					PrintInterface printInterface = printEntityManager
							.findPrintInterfaceBy(scheme.getSchemeNumber(),
									scheme.getLotteryType());
					if (null != printInterface) {
						List<Long> ticketIds = ticketEntityManager
								.findTicketIdByPrintInterfaceId(printInterface
										.getId());
						Ticket ticket = null;
						for (Long id : ticketIds) {
							ticket = ticketEntityManager.getTicket(id);
							ticket.setChecked(true);
							if (null != ticket.getTicketIndex()
									//&& SchemePrintState.SUCCESS.equals(scheme
									//		.getSchemePrintState())
											) {
								//只要出票了，就更新出票
								for (PrintWonItem printWonItem : prizeWork
										.getPrintWonItemList()) {
									if (printWonItem.getIndex().equals(
											ticket.getTicketIndex())) {
										//
										ticket.setWon(true);
										ticket.setTotalPrize(printWonItem
												.getTotalPrize());
										ticket.setTotalPrizeAfterTax(printWonItem
												.getTotalPrizeAfterTax());
										ticket.setWonDetail(printWonItem
												.getWonDetail());
										ticket.setTicket_synchroned(Boolean.FALSE);
									}
								}
							}
							ticketEntityManager.saveTicket(ticket);
						}

					}

				}
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
		} else {
			// 出票回查
			PrintInterface printInterface = printEntityManager
					.findPrintInterfaceBy(scheme.getSchemeNumber(),
							scheme.getLotteryType());
			List<Ticket> resetTicket = new ArrayList<Ticket>();
			if (null != printInterface) {
				List<Long> ticketIds = ticketEntityManager
						.findTicketIdByPrintInterfaceId(printInterface.getId());
				Ticket ticket = null;
				for (Long id : ticketIds) {
					ticket = ticketEntityManager.getTicket(id);
					ticket.setChecked(true);
					if (null != ticket.getTicketIndex()
							//&& SchemePrintState.SUCCESS.equals(scheme
							//		.getSchemePrintState())
							) {
						// 只要出票了，就更新出票
						// for (PrintWonItem printWonItem:
						// prizeWork.getPrintWonItemList()) {
						// if(printWonItem.getIndex().equals(ticket.getTicketIndex())){
						// //
						// ticket.setWon(true);
						// ticket.setTotalPrize(printWonItem.getTotalPrize());
						// ticket.setTotalPrizeAfterTax(printWonItem.getTotalPrizeAfterTax());
						// ticket.setWonDetail(printWonItem.getWonDetail());
						// ticket.setTicket_synchroned(Boolean.FALSE);
						// }
						// }
						if (ticket.isWon()) {
							ticket.setWon(false);
							ticket.setTicket_synchroned(Boolean.FALSE);
							ticket.setTotalPrize(Double.valueOf(0));
							ticket.setTotalPrizeAfterTax(Double.valueOf(0));
							ticket.setWonDetail("");
							resetTicket.add(ticket);
						}
					}
					ticketEntityManager.saveTicket(ticket);
				}

			}
			// 调用远程http接口更新
			if(resetTicket!=null&&!resetTicket.isEmpty()){
				try {
					Win310Util.ResetPrize(resetTicket);
				} catch (DataException e) {
					throw new ServiceException("重置奖金失败.");
//					e.printStackTrace();
				} catch (IOException e) {
					throw new ServiceException("重置奖金失败.");
				} catch (DocumentException e) {
					throw new ServiceException("重置奖金失败.");
				}
			}
			scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
			scheme.setWon(false);
			scheme.setWonDetail("");
			scheme.setPrize(BigDecimal.ZERO);
			scheme.setPrizeAfterTax(BigDecimal.ZERO);
			scheme.setPrizeDetail("");
		}
		scheme = getSchemeEntityManager().saveScheme(scheme);
	}

	@Override
	protected JczqScheme newSchemeInstance(JczqSchemeDTO schemeDTO) {
		JczqScheme scheme = super.newSchemeInstance(schemeDTO);
		scheme.setPlayType(schemeDTO.getPlayType());
		scheme.setPlayTypeWeb(schemeDTO.getPlayTypeWeb());
		scheme.setSchemeType(schemeDTO.getSchemeType());
		scheme.setPassMode(schemeDTO.getPassMode());
		scheme.setTicketContent(schemeDTO.getTicketContent());

		long passTypeValue = 0;
		for (PassType passType : schemeDTO.getPassTypes()) {
			passTypeValue |= passType.getValue();
		}
		scheme.setPassType(passTypeValue);

		return scheme;
	}

	@Override
	public JczqScheme createScheme(JczqSchemeDTO schemeDTO) {
		JczqScheme scheme = super.createScheme(schemeDTO);

		SalesMode salesMode = scheme.getMode();
		switch (salesMode) {
		case COMPOUND:
			JczqCompoundContent compoundContent = scheme.getCompoundContent();
			if (compoundContent != null) {
				List<String> matchKeys = Lists.newArrayList();
				String matchKey = null;
				for (JczqMatchItem matchItem : compoundContent.getItems()) {
					matchKey = matchItem.getMatchKey();
					if (!matchKeys.contains(matchKey)) {
						JczqSchemeMatch schemeMatch = new JczqSchemeMatch();
						schemeMatch.setMatchKey(matchKey);
						schemeMatch.setSchemeId(scheme.getId());
						schemeManager.saveSchemeMatch(schemeMatch);
						matchKeys.add(matchKey);
					}
				}
			}
			break;
		case SINGLE:
			List<String> matchkeys = scheme.getSingleContent().getMatchkeys();
			if (matchkeys != null) {
				for (String matchkey : matchkeys) {
					JczqSchemeMatch schemeMatch = new JczqSchemeMatch();
					schemeMatch.setMatchKey(matchkey);
					schemeMatch.setSchemeId(scheme.getId());
					schemeManager.saveSchemeMatch(schemeMatch);
				}
			}
			break;
		default:
			throw new ServiceException("投注方式不合法.");
		}

		// 如果此方案属于追投计划的话 设置追投计划的一些信息
		if (schemeDTO.isChasePlanScheme()) {
			handleChasePlanDetail(scheme, schemeDTO.getJczqChasePlanDetailId());
		}

		return scheme;
	}

	private void handleChasePlanDetail(JczqScheme scheme,
			Long JczqChasePlanDetailId) {
		// 保存追投计划详情记录的方案信息
		JczqChasePlanDetail detail = jczqChasePlanDetailEntityManager
				.getJczqChasePlanDetailBy(JczqChasePlanDetailId);
		detail.setHasPay(true);
		detail.setSchemeId(scheme.getId());
		detail.setSchemeNumber(scheme.getSchemeNumber());
		detail.setSchemeCost(scheme.getSchemeCost());
		jczqChasePlanDetailEntityManager.save(detail);

		// 记录追投计划的累计投注金额
		JczqChasePlan plan = jczqChasePlanEntityManager.getChasePlan(detail
				.getJczqChasePlanId());
		Integer chasedCost = plan.getChasedCost();
		if (chasedCost == null)
			chasedCost = 0;
		plan.setChasedCost(chasedCost + scheme.getSchemeCost());
		jczqChasePlanEntityManager.saveChasePlan(plan);

		// 新建一条追投计划详情记录
		JczqChasePlanDetail newChasePlanDetail = new JczqChasePlanDetail();
		newChasePlanDetail.setCreateTime(new Date());
		newChasePlanDetail.setReturnRateLevel(detail.getReturnRateLevel());
		newChasePlanDetail.setMutiple(detail.getMutiple());
		newChasePlanDetail.setJczqChasePlanId(detail.getJczqChasePlanId());
		newChasePlanDetail.setLotteryType(Lottery.JCZQ);
		newChasePlanDetail.setHasPay(false);
		newChasePlanDetail.setUserId(detail.getUserId());
		newChasePlanDetail.setUserName(detail.getUserName());
		jczqChasePlanDetailEntityManager.save(newChasePlanDetail);
	}

	@Override
	public void ticketOther(Scheme jczqScheme) {
		JczqScheme scheme = (JczqScheme) jczqScheme;
		SalesMode salesMode = scheme.getMode();
		switch (salesMode) {
		case COMPOUND:
			JczqCompoundContent compoundContent = scheme.getCompoundContent();
			if (compoundContent != null) {
				List<String> matchKeys = Lists.newArrayList();
				String matchKey = null;
				for (JczqMatchItem matchItem : compoundContent.getItems()) {
					matchKey = matchItem.getMatchKey();
					if (!matchKeys.contains(matchKey)) {
						JczqSchemeMatch schemeMatch = new JczqSchemeMatch();
						schemeMatch.setMatchKey(matchKey);
						schemeMatch.setSchemeId(scheme.getId());
						schemeManager.saveSchemeMatch(schemeMatch);
						matchKeys.add(matchKey);
					}
				}
			}
			break;
		case SINGLE:
			List<String> matchkeys = scheme.getSingleContent().getMatchkeys();
			if (matchkeys != null) {
				for (String matchkey : matchkeys) {
					JczqSchemeMatch schemeMatch = new JczqSchemeMatch();
					schemeMatch.setMatchKey(matchkey);
					schemeMatch.setSchemeId(scheme.getId());
					schemeManager.saveSchemeMatch(schemeMatch);
				}
			}
			break;
		default:
			throw new ServiceException("投注方式不合法.");
		}
	}

	public JczqScheme destineScheme(JczqSchemeDTO schemeDTO) {
		Period period = periodManager.getPeriod(schemeDTO.getPeriodId());
		checkConformPeriodInitConfig(period, schemeDTO);
		JczqScheme scheme = newSchemeInstance(schemeDTO);
		scheme.setPeriodNumber(period.getPeriodNumber());
		scheme.setSchemePrintState(SchemePrintState.UNPRINT);
		return scheme;
	}

	@Override
	protected void checkConformPeriodSubscriptionConfig(JczqScheme scheme) {
		super.checkConformPeriodSubscriptionConfig(scheme);

		List<JczqMatch> matchList = matchEntityManager.findMatchsOfUnEnd();
		List<String> keyList = Lists.newArrayList();
		if (matchList != null && !matchList.isEmpty()) {
			for (JczqMatch m : matchList) {
				keyList.add(m.getMatchKey());
			}
		}

		switch (scheme.getMode()) {
		case COMPOUND:
			JczqCompoundContent content = scheme.getCompoundContent();
			for (JczqMatchItem item : content.getItems()) {
				if (!keyList.contains(item.getMatchKey()))
					throw new ServiceException("第" + (item.getMatchKey())
							+ "场赛事已经截止销售.");
			}
			break;
		case SINGLE:
			JczqSingleContent singleContent = JsonUtil.getObject4JsonString(
					scheme.getContent(), JczqSingleContent.class);
			List<String> matchKeys = singleContent.getMatchkeys();
			for (String matchKey : matchKeys) {
				if (!keyList.contains(matchKey)) {
					throw new ServiceException("第" + (matchKey) + "场赛事已经截止销售.");
				}
			}
			break;
		default:
			throw new ServiceException("投注方式不正确.");
		}
	}

	@Override
	public boolean isSaleEnded(Long schemeId) {
		boolean isSaleEnded = super.isSaleEnded(schemeId);

		if (!isSaleEnded) {
			List<JczqMatch> matchList = matchEntityManager.findMatchsOfUnEnd();
			List<String> keyList = Lists.newArrayList();
			if (matchList != null && !matchList.isEmpty()) {
				for (JczqMatch m : matchList) {
					keyList.add(m.getMatchKey());
				}
			}
			JczqScheme scheme = getSchemeEntityManager().getScheme(schemeId);
			switch (scheme.getMode()) {
			case COMPOUND:
				JczqCompoundContent content = scheme.getCompoundContent();
				for (JczqMatchItem item : content.getItems()) {
					if (!keyList.contains(item.getMatchKey())) {
						isSaleEnded = true;
						break;
					}
				}
				break;
			case SINGLE:
				JczqSingleContent singleContent = JsonUtil
						.getObject4JsonString(scheme.getContent(),
								JczqSingleContent.class);
				List<String> matchKeys = singleContent.getMatchkeys();
				for (String matchKey : matchKeys) {
					if (!keyList.contains(matchKey)) {
						isSaleEnded = true;
						break;
					}
				}
				break;
			default:
				throw new ServiceException("投注方式不正确.");
			}
		}
		return isSaleEnded;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void checkConformPeriodInitConfig(Period period,
			JczqSchemeDTO schemeDTO) {
		super.checkConformPeriodInitConfig(period, schemeDTO);

		List<JczqMatch> matchList = matchEntityManager.findMatchsOfUnEnd();
		Map<String, JczqMatch> keyMap = Maps.newHashMap();
		List<String> keyList = Lists.newArrayList();
		if (matchList != null && !matchList.isEmpty()) {
			for (JczqMatch m : matchList) {
				keyMap.put(m.getMatchKey(), m);
				keyList.add(m.getMatchKey());
			}
		}
		Date endTime = null;
		switch (schemeDTO.getMode()) {
		case COMPOUND:
			@SuppressWarnings("rawtypes")
			Map classMap = new HashMap();
			classMap.put("items", JczqMatchItem.class);
			JczqCompoundContent content = JsonUtil
					.getObject4JsonString(schemeDTO.getContent(),
							JczqCompoundContent.class, classMap);
			for (JczqMatchItem item : content.getItems()) {
				endTime = this.getOfficialEndTimeAndV(item.getMatchKey(),
						keyMap, keyList, endTime, item.getPlayType());
			}
			schemeDTO.setOfficialEndTime(endTime);
			break;
		case SINGLE:
			@SuppressWarnings("rawtypes")
			Map classMapS = new HashMap();
			classMapS.put("items", JczqMatchItem.class);
			JczqSingleContent singleContent = JsonUtil.getObject4JsonString(
					schemeDTO.getContent(), JczqSingleContent.class, classMapS);
			for (JczqMatchItem item : singleContent.getItems()) {
				endTime = this.getOfficialEndTimeAndV(item.getMatchKey(),
						keyMap, keyList, endTime, item.getPlayType());
			}
			schemeDTO.setOfficialEndTime(endTime);
			break;
		default:
			throw new ServiceException("投注方式不正确.");
		}
	}

	/**
	 * 验证场次在售及获取最早的官方截止时间
	 * 
	 * @param matchKey
	 * @param keyMap
	 * @return
	 */
	private Date getOfficialEndTimeAndV(String matchKey,
			final Map<String, JczqMatch> keyMap, List<String> keyList,
			Date endTime, PlayType playType) {
		if (!keyList.contains(matchKey))
			throw new ServiceException("第" + (matchKey) + "场赛事已经截止销售.");
		JczqMatch match = keyMap.get(matchKey);
		try {
			if (!match.isOpen(playType, PassMode.PASS)) {
				throw new ServiceException("场次{" + match.getMatchKey() + "}玩法{"
						+ playType.getText() + "}没有开售.");
			}
			if (match.isStop()) {
				throw new ServiceException("场次{" + match.getMatchKey()
						+ "}已经停止销售.");
			}
			if (endTime == null
					|| endTime.after(match.getTicketOfficialEndTime()))
				endTime = match.getTicketOfficialEndTime();
		} catch (DataException e) {
			throw new ServiceException("获取比赛场次时间错误.");
		}
		return endTime;
	}

	public boolean handleTransaction(JczqScheme scheme, List<String> endLineIds) {
		boolean anyEnded = false;// 方案是否包含一场已结束的赛事标识
		List<String> matchKeys = scheme.getSelectedMatchKeys();
		for (String endLineId : endLineIds) {
			if (matchKeys.contains(endLineId)) {
				anyEnded = true;
				break;
			}
		}
		if (anyEnded) {
			commitOrCancelTransaction(scheme.getId());
			return true;
		}

		return false;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.JCZQ;
	}

	@Override
	public Byte getPlayType(JczqSchemeDTO schemeDTO) {
		return Byte.valueOf(String.valueOf(schemeDTO.getPlayType().ordinal()));
	}
}
