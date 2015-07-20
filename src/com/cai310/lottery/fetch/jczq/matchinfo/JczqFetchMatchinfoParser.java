package com.cai310.lottery.fetch.jczq.matchinfo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class JczqFetchMatchinfoParser {

	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

	private static String URL = "http://www.310win.com/handle/MatchidInterface.aspx";
	
	private static String URL_LEAGUE = "http://interface.win007.com/zq/BF_XMLByID.aspx?id=";
	
	private static String URL_POINT = "http://interface.win007.com/zq/jifen.aspx?ID=";
	
	private static String URL_EURO_ODDS = "http://interface.win007.com/zq/1x2.aspx";
	
	private static String URL_BETFAIR = "http://live.sina.aicai.com/pages/bf/jczq.shtml";

	/**
	 * 抓取球探网比赛ID、主客队ID
	 * @return
	 */
	public List<MatchinfoVisitor> fetch() {
		String result = HttpClientUtil.getRemoteSource(URL, "GBK");
		List<MatchinfoVisitor> matchInfoList = Lists.newArrayList();
		if (StringUtils.isNotBlank(result)) {
			try {
				Document doc = DocumentHelper.parseText(result);
				Element root = doc.getRootElement();
				Element e;
				MatchinfoVisitor matchInfo;
				for (Iterator i = root.elementIterator("i"); i.hasNext();) {
					e = (Element) i.next();
					matchInfo = new MatchinfoVisitor();
					e.accept(matchInfo);
					if(matchInfo.getLottery() != null && matchInfo.getLottery() == Lottery.JCZQ) {
						matchInfoList.add(matchInfo);
					}
				}
			} catch (Exception e) {
				this.logger.warn("抓取比赛信息失败。");
			}
		}
		return matchInfoList;
	}

	/**
	 * 根据比赛ID抓取球探网对应的联赛ID
	 * @param matchIds
	 * @return
	 */
	public List<MatchinfoVisitor> fetchLeague(Set<String> matchIds) {
		List<MatchinfoVisitor> matchInfoList = Lists.newArrayList();
		if(matchIds != null && matchIds.size() > 0) {
			String url = URL_LEAGUE;
			for(String matchId : matchIds) {
				url += matchId + ",";
			}
			String result = HttpClientUtil.getRemoteSource(url.substring(0, url.length() - 1), "GBK");
			if (StringUtils.isNotBlank(result)) {
				try {
					Document doc = DocumentHelper.parseText(result);
					Element root = doc.getRootElement();
					Element e;
					MatchinfoVisitor matchInfo;
					for (Iterator i = root.elementIterator("match"); i.hasNext();) {
						e = (Element) i.next();
						matchInfo = new MatchinfoVisitor();
						e.accept(matchInfo);
						matchInfoList.add(matchInfo);
					}
				} catch (Exception e) {
					this.logger.warn("抓取比赛信息失败。");
				}
			}
		}
		return matchInfoList;
	}
	
	/**
	 * 从球探网抓取联赛球队排名和积分
	 * @param leagueId
	 * @return
	 */
	public Map<String, TeamResults> fetchPoint(String leagueId) {
		String result = HttpClientUtil.getRemoteSource(URL_POINT + leagueId, "GBK");
		if(result.trim().startsWith("sub")) {
			String leagueSubId = "";
			if(result.contains("(")) {
				leagueSubId = result.substring(result.indexOf(":") + 1, result.indexOf("(current)")).trim();
			} else {
				leagueSubId = result.substring(result.indexOf(":") + 1).trim();
			}
			if(leagueSubId.contains(",")) {
				leagueSubId = leagueSubId.substring(leagueSubId.lastIndexOf(",") + 1);
			}
			result = HttpClientUtil.getRemoteSource(URL_POINT + leagueId + "&subID=" + leagueSubId, "GBK");
		}
		Map<String, TeamResults> pointMap = Maps.newHashMap();
		if (StringUtils.isNotBlank(result)) {
			String[] values = result.split("\n");
			for(String value : values) {
				if(value.trim().startsWith("var totalScore")) {
					String json = value.trim().split("=")[1].trim();
					json = json.substring(0, json.length() - 1);
					JSONArray jsonArray = JSONArray.fromObject(json);
					JSONArray data = null;
					for(int i = 0; i < jsonArray.size(); i++) {
						data = jsonArray.getJSONArray(i);
						TeamResults teamResults = new TeamResults();
						teamResults.setRanking(data.getString(1));
						teamResults.setPoints(data.getString(16));
						Integer won = 0, draw = 0, lose = 0;
						for(int j = 19; j <= 24; j++) {
							if(data.get(i) != JSONNull.getInstance()) {
								if(data.getInt(j) == 0) {
									won++;
								} else if(data.getInt(j) == 1) {
									draw++;
								} else if(data.getInt(j) == 2) {
									lose++;
								}
							}
						}
						teamResults.setWon(won);
						teamResults.setDraw(draw);
						teamResults.setLose(lose);
						pointMap.put(data.getString(2), teamResults);
					}
					break;
				}
			}
		}
		return pointMap;
	}
	
	/**
	 * 从球探网抓取百家欧赔
	 * @return
	 */
	public List<EuroOddsVisitor> fetchEuroOdds() {
		List<EuroOddsVisitor> euroOddsList = Lists.newArrayList();
		String result = HttpClientUtil.getRemoteSource(URL_EURO_ODDS, "GBK");
		if (StringUtils.isNotBlank(result)) {
			try {
				Document doc = DocumentHelper.parseText(result);
				Element root = doc.getRootElement();
				Element e;
				EuroOddsVisitor euroOdds;
				for (Iterator i = root.elementIterator("h"); i.hasNext();) {
					e = (Element) i.next();
					euroOdds = new EuroOddsVisitor();
					e.accept(euroOdds);
					euroOddsList.add(euroOdds);
				}
			} catch (Exception e) {
				this.logger.warn("抓取百家赔率失败。");
			}
		}
		return euroOddsList;
	}
	
	public List<JczqBetfair> fetchBetfair() {
		List<JczqBetfair> betfairList = Lists.newArrayList();
		String result = HttpClientUtil.getRemoteSource(URL_BETFAIR, "GBK");
		if (StringUtils.isNotBlank(result)) {
			try {
				Parser parser = Parser.createParser(result, "GBK");
				NodeFilter nodeFilter = new NodeClassFilter(Div.class);
				NodeFilter[] filters = new NodeFilter[] {nodeFilter};
				OrFilter filter = new OrFilter(filters);
				NodeList nodeList = null;
				try {
					nodeList = parser.parse(filter);
				} catch (ParserException e) {
					e.printStackTrace();
				}
				for (int i = 0; i <= nodeList.size(); i++) {
					if (nodeList.elementAt(i) instanceof Div) {
						Div tag = (Div) nodeList.elementAt(i);
						//所有必发比赛div
						if ("jq_bf_match_div".equals(tag.getAttribute("id"))) {
							for (SimpleNodeIterator elements = tag.elements(); elements.hasMoreNodes();) {
								Node node = elements.nextNode();
								//单场比赛必发数据div
								if (node instanceof Div && !StringUtil.isEmpty(((Div) node).getAttribute("id")) && ((Div) node).getAttribute("id").startsWith("jq_section_div_")) {
									JczqBetfair betfair = new JczqBetfair();
									Div matchDiv = (Div) node;
									for (SimpleNodeIterator elements2 = matchDiv.elements(); elements2.hasMoreNodes();) {
										Node node2 = elements2.nextNode();
										//必发数据header div
										if (node2 instanceof Div && "md_tit_box".equals(((Div) node2).getAttribute("class"))) {
											Div headDiv = (Div) node2;
											for (SimpleNodeIterator elements3 = headDiv.elements(); elements3.hasMoreNodes();) {
												Node node3 = elements3.nextNode();
												//比赛必发指数div
												if (node3 instanceof Span && StringUtil.isEmpty(((Span) node3).getAttribute("class"))) {
													Span matchKeySpan = (Span) node3;
													for (SimpleNodeIterator elements4 = matchKeySpan.elements(); elements4.hasMoreNodes();) {
														Node node4 = elements4.nextNode();
														//比赛必发指数div
														if (node4 instanceof Span && "c_yellow".equals(((Span) node4).getAttribute("class"))) {
															Span matchKey = (Span) node4;
															betfair.setMatchKeyText(matchKey.toPlainTextString().trim());
														}
													}
												}
											}
										}
										//必发数据CONTENT div
										else if (node2 instanceof Div && "md_con_box".equals(((Div) node2).getAttribute("class"))) {
											Div dataDiv = (Div) node2;
											for (SimpleNodeIterator elements3 = dataDiv.elements(); elements3.hasMoreNodes();) {
												Node node3 = elements3.nextNode();
												//比赛必发指数div
												if (node3 instanceof Div && "data_table".equals(((Div) node3).getAttribute("class"))) {
													Div bfDiv = (Div) node3;
													for (SimpleNodeIterator elements4 = bfDiv.elements(); elements4.hasMoreNodes();) {
														Node node4 = elements4.nextNode();
														//必发指数table
														if (node4 instanceof TableTag) {
															TableTag bfTable = (TableTag) node4;
															TableColumn[] columns = bfTable.getRow(1).getColumns();
															betfair.setWonBF(columns[2].toPlainTextString());
															columns = bfTable.getRow(2).getColumns();
															betfair.setDrawBF(columns[2].toPlainTextString());
															columns = bfTable.getRow(3).getColumns();
															betfair.setLoseBF(columns[2].toPlainTextString());
														}
													}
												} 
												//比赛成交量div
												else if(node3 instanceof Div && "probable".equals(((Div) node3).getAttribute("class")) && ((Div) node3).getAttribute("id").startsWith("jq_cj_")) {
													Div cjDiv = (Div) node3;
													Double sumCJ = new Double(0), wonPercent = new Double(0), drawPercent = new Double(0), losePercent = new Double(0);
													for (SimpleNodeIterator elements4 = cjDiv.elements(); elements4.hasMoreNodes();) {
														Node node4 = elements4.nextNode();
														//必发成交总量div
														if (node4 instanceof Div && "proba_total".equals(((Div) node4).getAttribute("class"))) {
															Div moneyDiv = (Div) node4;
															for (SimpleNodeIterator elements5 = moneyDiv.elements(); elements5.hasMoreNodes();) {
																Node node5 = elements5.nextNode();
																if(node5 instanceof ParagraphTag && node5.toPlainTextString().trim().startsWith("HK")) {
																	String money = node5.toPlainTextString().trim().split("\\$")[1];
																	sumCJ = Double.valueOf(money);
																}
															}
														}
														//必发胜平负占比div
														else if(node4 instanceof Div && "proba_data".equals(((Div) node4).getAttribute("class"))) {
															Div percentDiv = (Div) node4;
															for (SimpleNodeIterator elements5 = percentDiv.elements(); elements5.hasMoreNodes();) {
																Node node5 = elements5.nextNode();
																if(node5 instanceof ParagraphTag) {
																	ParagraphTag p = (ParagraphTag) node5;
																	for (SimpleNodeIterator elements6 = p.elements(); elements6.hasMoreNodes();) {
																		Node node6 = elements6.nextNode();
																		if(node6 instanceof Span) {
																			Span span = (Span) node6;
																			if("c_orange".equals(span.getAttribute("class"))) {
																				String wonText = span.toPlainTextString().trim();
																				wonPercent = Double.valueOf(wonText.substring(0, wonText.length() - 1));
																			} else if("c_green".equals(span.getAttribute("class"))) {
																				String drawText = span.toPlainTextString().trim();
																				drawPercent = Double.valueOf(drawText.substring(0, drawText.length() - 1));
																			} else if("c_blue".equals(span.getAttribute("class"))) {
																				String loseText = span.toPlainTextString().trim();
																				losePercent = Double.valueOf(loseText.substring(0, loseText.length() - 1));
																			}
																		}
																	}
																}
															}
														}
													}
													betfair.setWonCJPercent(String.valueOf(wonPercent));
													betfair.setDrawCJPercent(String.valueOf(drawPercent));
													betfair.setLoseCJPercent(String.valueOf(losePercent));
													betfair.setWonCJ(String.valueOf(EuroOddsUtil.round(sumCJ*wonPercent/100)));
													betfair.setDrawCJ(String.valueOf(EuroOddsUtil.round(sumCJ*drawPercent/100)));
													betfair.setLoseCJ(String.valueOf(EuroOddsUtil.round(sumCJ*losePercent/100)));
													betfairList.add(betfair);
												}
											}
										}
									}
								}
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				this.logger.warn("抓取必发指数失败。");
			}
		}
		return betfairList;
	}
	
	public static void main(String[] args) {
		
	}

}
