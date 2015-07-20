package com.cai310.lottery.fetch.dczc.win310sp;

import java.util.List;

import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Lists;

public class DczcSpfSpWin310FetchParser extends OkoooAbstractFetchParser {

	private static final String URL="http://www.310win.com/buy/DanChang.aspx?TypeID=5&issueNum=";
	private static final String CHARSET = "GBK";
	
	public List<ZunaoDczcSp> fetch(String periodNumber){
		String html = HttpClientUtil.getRemoteSource(URL+periodNumber, CHARSET);
		return parser(html);
	}
	public List<ZunaoDczcSp> parser(String html) {
		String div=getDivBy(html,CHARSET,"id","lottery_container").getChildrenHTML();
		TableTag tag=getTable(div, CHARSET,"id","MatchTable");
		TableRow[] rows = tag.getRows();
		int i = 0;
		List<ZunaoDczcSp> list=Lists.newArrayList();
		for (TableRow row : rows) {
			i++;
			if(i<4){
				continue;
			}
			try {
				TableColumn[] columns = row.getColumns();
				String matchId=columns[0].getChild(1).getText();
				String matchTime=columns[2].getChild(0).getText();
				StringBuffer sb=new StringBuffer();
				String spWinSpan=columns[13].getChildrenHTML();
				String spWin=getSpan(spWinSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(spWin).append(",");
				
				String spDrawSpan=columns[14].getChildrenHTML();
				String spDraw=getSpan(spDrawSpan,CHARSET,null,null).getChild(0).getText();
				sb.append(spDraw).append(",");
				
				String spLoseSpan=columns[15].getChildrenHTML();
				String spLose=getSpan(spLoseSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(spLose);
				
				ZunaoDczcSp zunaoDczcSp=new ZunaoDczcSp();
				zunaoDczcSp.setMatchId(matchId);
				zunaoDczcSp.setMatchtime(matchTime);
				zunaoDczcSp.setSp(sb.toString());
				list.add(zunaoDczcSp);
			} catch (Exception e) {
				logger.error(e.getMessage());
				continue;
			}
		}
		return list;
	}
	public static void main(String[] args) {
		DczcSpfSpWin310FetchParser DczcSpfSpWin310FetchParser=new DczcSpfSpWin310FetchParser();
		List<ZunaoDczcSp> list=DczcSpfSpWin310FetchParser.fetch("141204");
		System.out.println(list.size());
	}
}
