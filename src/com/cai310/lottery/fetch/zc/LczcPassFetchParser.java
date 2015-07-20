package com.cai310.lottery.fetch.zc;

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

import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.lottery.exception.FetchParseException;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;

public class LczcPassFetchParser extends ZcAbstractFetchParser {
	@Override
	public String getSourceUrl() {
		return "http://www.310win.com/buy/toto6.aspx?issueNum=";
	}

	@Override
	protected List<ZcMatch> parserHTML(String html, String charset) {
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
	protected List<ZcMatch> parser(TableTag tag) throws FetchParseException, ParseException {
		TableRow[] rows = tag.getRows();
		int i = 0;
		List<ZcMatch> list = Lists.newLinkedList();
		for (TableRow row : rows) {
			i++;
			if(i==1)continue;
			TableColumn[] columns = row.getColumns();
			if(i%2==0){
				String lineId = columns[0].toPlainTextString().trim();
				String gameName = columns[1].toPlainTextString().trim();
				String matchTime = columns[2].toPlainTextString().trim();
				String homeTeamName = columns[3].toPlainTextString().trim();
				String guestTeamName = columns[4].toPlainTextString().trim();
				String odds1 = columns[8].toPlainTextString().trim();
				String odds2 = columns[9].toPlainTextString().trim();
				String odds3 = columns[10].toPlainTextString().trim();	
				LczcMatch lczcMatch = null;
				if(null!=zcMatchMap.get(Integer.valueOf(lineId))){
					lczcMatch =  (LczcMatch) zcMatchMap.get(Integer.valueOf(lineId));
				}else{
					lczcMatch =  new LczcMatch();	
					lczcMatch.setLineId(Integer.valueOf(lineId));
				}
				lczcMatch.setGameName(gameName);
				lczcMatch.setMatchTimeStr(DateUtil.dateToStr(new Date(), "yyyy")+"-"+matchTime);
				lczcMatch.setHomeTeamName(homeTeamName);
				lczcMatch.setGuestTeamName(guestTeamName);
				//lczcMatch.setAsiaOdd(Double.valueOf(asiaOdd));
				list.add(lczcMatch);
			}
		}
		return list;
	}
	 public static void main(String[] args) {
		 LczcPassFetchParser LczcPassFetchParser =  new LczcPassFetchParser();
		 LczcPassFetchParser.fetch("12194");
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

	@Override
	public String getType() {
		return "3";
	}

	@Override
	public ZcMatch getZcMatch() {
		return new LczcMatch();
	}

}
