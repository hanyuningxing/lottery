package com.cai310.lottery.fetch.dczc.win310sp;

import java.util.List;

import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Lists;

public class DczcSxdsSpWin310FetchParser extends OkoooAbstractFetchParser {
	private static final String URL="http://www.310win.com/buy/DanChang.aspx?TypeID=7&issueNum=";
	
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
			if(i<3){
				continue;
			}
			try {
				TableColumn[] columns = row.getColumns();
				String matchId=columns[0].getChild(1).getText();
				if(columns.length<3){
					continue;
				}
				String matchTime=columns[2].getChild(0).getText();
				StringBuffer sb=new StringBuffer();
				String ssSpan=columns[9].getChildrenHTML();
				if(getSpan(ssSpan, CHARSET, null, null).getChild(0)==null){
					continue;
				}
				String ssSp=getSpan(ssSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(ssSp).append(",");

				String spSpan=columns[10].getChildrenHTML();
				String spSp=getSpan(spSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(spSp).append(",");
				
				String sfSpan=columns[11].getChildrenHTML();
				String sfSp=getSpan(sfSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(sfSp).append(",");
				
				String psSpan=columns[12].getChildrenHTML();
				String psSp=getSpan(psSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(psSp);
				
				ZunaoDczcSp zunaoDczcSp=new ZunaoDczcSp();
				zunaoDczcSp.setMatchId(matchId);
				zunaoDczcSp.setMatchtime(matchTime);
				zunaoDczcSp.setSp(sb.toString());
				list.add(zunaoDczcSp);
				
			} catch (Exception e) {
				logger.info(e.getMessage()+"sxds");
				continue;
			}
		}
		return list;
	}
	public static void main(String[] args) {
		DczcSxdsSpWin310FetchParser fetch=new DczcSxdsSpWin310FetchParser();
		List<ZunaoDczcSp> list=fetch.fetch("151004");
		System.out.println(list.size());
		System.out.println(list.get(0).getSp());
	}
}
