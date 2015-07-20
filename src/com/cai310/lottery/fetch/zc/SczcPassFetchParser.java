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
import com.cai310.lottery.entity.lottery.zc.SczcMatch;
import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.lottery.exception.FetchParseException;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SczcPassFetchParser extends ZcAbstractFetchParser {
	@Override
	public String getSourceUrl() {
		return "http://www.310win.com/buy/toto4.aspx?issueNum=";
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
    public static void main(String[] args) {
    	SczcPassFetchParser SczcPassFetchParser =  new SczcPassFetchParser();
    	SczcPassFetchParser.fetch("2011180");
	}
	protected List<ZcMatch> parser(TableTag tag) throws FetchParseException, ParseException {
		TableRow[] rows = tag.getRows();
		int i = 0;
		List<ZcMatch> list = Lists.newLinkedList();
		String lineId = null;
		String gameName = null;
		String matchTime = null;
		String homeTeamName = null;
		String guestTeamName = null;
		String asiaOdd_home = null;
		String asiaOdd = null;
		String asiaOdd_guest = null;
		String odds1 = null;
		String odds2 = null;
		String odds3 = null;
		for (TableRow row : rows) {
			
			TableColumn[] columns = row.getColumns();
			i++;
			if(i==1)continue;
			if(i%2==0){
				 lineId = columns[0].toPlainTextString().trim();
				 gameName = columns[1].toPlainTextString().trim();
				 matchTime = columns[2].toPlainTextString().trim();
				 homeTeamName = columns[3].toPlainTextString().trim();
				
				 asiaOdd_home = columns[4].toPlainTextString().trim();
				 asiaOdd = columns[5].toPlainTextString().trim();
				 asiaOdd_guest = columns[6].toPlainTextString().trim();
				 odds1 = columns[7].toPlainTextString().trim();
				 odds2 = columns[8].toPlainTextString().trim();
				 odds3 = columns[9].toPlainTextString().trim();	
			}else{
				    guestTeamName = columns[0].toPlainTextString().trim();
				    
				    
				    SczcMatch sczcMatch = null;
					if(null!=zcMatchMap.get(Integer.valueOf(lineId))){
						sczcMatch =  (SczcMatch) zcMatchMap.get(Integer.valueOf(lineId));
					}else{
						sczcMatch =  new SczcMatch();	
						sczcMatch.setLineId(Integer.valueOf(lineId));
					}
					sczcMatch.setGameName(gameName);
					sczcMatch.setMatchTimeStr(DateUtil.dateToStr(new Date(), "yyyy")+"-"+matchTime);
					sczcMatch.setHomeTeamName(homeTeamName);
					sczcMatch.setGuestTeamName(guestTeamName);
					list.add(sczcMatch);
			}
		}
		return list;
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
		return "4";
	}

	@Override
	public ZcMatch getZcMatch() {
		return new SczcMatch();
	}

}
