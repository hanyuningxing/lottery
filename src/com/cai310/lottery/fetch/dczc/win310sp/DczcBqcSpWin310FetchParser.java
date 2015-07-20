package com.cai310.lottery.fetch.dczc.win310sp;

import java.util.List;

import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Lists;

public class DczcBqcSpWin310FetchParser extends OkoooAbstractFetchParser {
	private static final String URL="http://www.310win.com/buy/DanChang.aspx?TypeID=9&issueNum=";
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
				String matchTime=columns[2].getChild(0).getText();
				StringBuffer sb=new StringBuffer();
				String ssSpan=columns[9].getChildrenHTML();
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
				sb.append(psSp).append(",");
				
				String ppSpan=columns[13].getChildrenHTML();
				String ppSp=getSpan(ppSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(ppSp).append(",");
				
				String pfSpan=columns[14].getChildrenHTML();
				String pfSp=getSpan(pfSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(pfSp).append(",");
				
				String fsSpan=columns[15].getChildrenHTML();
				String fsSp=getSpan(fsSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(fsSp).append(",");
				
				String fpSpan=columns[16].getChildrenHTML();
				String fpSp=getSpan(fpSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(fpSp).append(",");
				
				String ffSpan=columns[17].getChildrenHTML();
				String ffSp=getSpan(ffSpan, CHARSET, null, null).getChild(0).getText();
				sb.append(ffSp);
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
		DczcBqcSpWin310FetchParser fetch=new DczcBqcSpWin310FetchParser();
		List<ZunaoDczcSp> list=fetch.fetch("141204");
		System.out.println(list.size());
		System.out.println(list.get(0).getSp());
	}
}
