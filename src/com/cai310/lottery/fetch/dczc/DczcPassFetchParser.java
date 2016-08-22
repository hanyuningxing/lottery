package com.cai310.lottery.fetch.dczc;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.exception.FetchParseException;
import com.cai310.lottery.fetch.SimpleAbstractFetchParser;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;

public class DczcPassFetchParser extends SimpleAbstractFetchParser<List<DczcMatch>, String> {
	@Override
	public String getSourceUrl() {
//		return "http://www.310win.com/buy/DanChang.aspx?TypeID=5&issueNum=";
		return "http://www.310win.com/buy/DanChang.aspx?all=1&TypeID=5&issueNum=";
	}
	@Override
	public List<DczcMatch> fetch(String issueNum) {
		String sourceUrl = getSourceUrl();
		sourceUrl = sourceUrl+issueNum;
		if (StringUtils.isBlank(sourceUrl)) {
			this.logger.warn("抓取的目标网址为空.");
			return null;
		}
		final String charset = (StringUtils.isNotBlank(getCharset())) ? getCharset() : Constant.DEFAULT_ENCODE;
		String html = fetchHTML(sourceUrl, charset);
		if (StringUtils.isBlank(html)) {
			this.logger.warn("抓取回来的内容为空，目标网址：[{}].", sourceUrl);
			return null;
		}
		return parserHTML(html, charset);
	}
	@Override
	protected List<DczcMatch> parserHTML(String html, String charset) {
		TableTag tag = getTableTag(html, charset);
		if (tag == null) {
			return null;
		}
		try {
			return parser(tag);
		} catch (FetchParseException e) {
			this.logger.warn("匹配抓取数据出错.", e);
		} catch (ParseException e) {
			this.logger.warn("匹配抓取数据出错.", e);
		}

		return null;
	}
	protected static final Pattern ENDTIME_PATTERN = Pattern.compile("\\s*(\\d{4}-\\d{2}-\\d{2})\\s*");
	protected List<DczcMatch> parser(TableTag tag) throws FetchParseException, ParseException {
		TableRow[] rows = tag.getRows();
		int i = 0;
		List<DczcMatch> list = Lists.newLinkedList();
		for (TableRow row : rows) {
			i++;
			if(i<4)continue;
			try{
				TableColumn[] columns = row.getColumns();
					String lineId = columns[0].toPlainTextString().trim();
					String gameName = columns[1].toPlainTextString().trim();
					String matchTime = columns[2].toPlainTextString().trim();
					String matchDay = columns[3].getAttribute("title");
					Matcher m = ENDTIME_PATTERN.matcher(matchDay);
					if (m.find()) {
						matchDay = m.group(1);
					}
					///由于对方的时间错
					//TODO
					if(StringUtils.isNotBlank(matchDay)&&matchDay.indexOf("-")!=-1){
						Date date = DateUtil.strToDate(matchDay,"yyyy-MM-dd");
						//date = DateUtils.addDays(date, 6);
//						if("00:00".equals(matchTime)){
//							date = DateUtils.addDays(date, 1);
//						}
						if (matchTime.compareToIgnoreCase("10:00") >= 0 && matchTime.compareToIgnoreCase("24:00") <= 0) {
							
						} else{
							date = DateUtils.addDays(date, 1);
						}
						
						matchDay = DateUtil.dateToStr(date,"yyyy-MM-dd");
					}
					String homeTeamName = columns[4].toPlainTextString().trim();
					String guestTeamName = columns[7].toPlainTextString().trim();
					String handicap = columns[5].toPlainTextString().trim();
					
					String odds1 = columns[9].toPlainTextString().trim();
					String odds2 = columns[10].toPlainTextString().trim();
					String odds3 = columns[11].toPlainTextString().trim();	
					DczcMatch dczcMatch = new DczcMatch();
					dczcMatch.setLineId(Integer.valueOf(lineId));
					dczcMatch.setGameName(gameName);
					if(StringUtils.isNotBlank(handicap)){
						dczcMatch.setHandicap(Byte.valueOf(handicap.replace("+","")));
					}
					dczcMatch.setMatchTimeStr(matchDay+" "+matchTime);
					dczcMatch.setHomeTeamName(homeTeamName);
					dczcMatch.setGuestTeamName(guestTeamName);
					//dczcMatch.setAsiaOdd(Double.valueOf(asiaOdd));
	//				dczcMatch.oddsData(Double.valueOf(odds1));
	//				dczcMatch.setOdds2(Double.valueOf(odds2));
	//				dczcMatch.setOdds3(Double.valueOf(odds3));
					list.add(dczcMatch);
			}catch(Exception e){
				logger.warn(e.getMessage());
				continue;
			}
		}
		return list;
	}
	 public static void main(String[] args) {
		 DczcPassFetchParser DczcPassFetchParser =  new DczcPassFetchParser();
		 DczcPassFetchParser.fetch("111212");
	}
	protected TableTag getTableTag(String html, String charset) {
		Parser parser = Parser.createParser(html, charset);
		NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			this.logger.warn("解析HTML出错.", e);
			return null;
		}

		for (int i = 0; i <= nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
			    if ("socai".equals(tag.getAttribute("class"))) {
					return tag;
				}
			}
		}
		return null;
	}

	@Override
	public String getCharset() {
		return "GBK";
	}

}
