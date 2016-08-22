package com.cai310.lottery.fetch.dczc.win310sp;

import java.util.List;

import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Lists;

public class DczcZjqsSpWin310FetchParser extends OkoooAbstractFetchParser {
	private static final String URL="http://www.310win.com/buy/DanChang.aspx?TypeID=6&issueNum=";
	
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
				String sp0Span=columns[9].getChildrenHTML();
				if(getSpan(sp0Span, CHARSET, null, null).getChild(0)==null){
					continue;
				}
				String sp0=getSpan(sp0Span, CHARSET, null, null).getChild(0).getText();
				sb.append(sp0).append(",");
				
				String sp1Span=columns[10].getChildrenHTML();
				String sp1=getSpan(sp1Span,CHARSET,null,null).getChild(0).getText();
				sb.append(sp1).append(",");
				
				String sp2Span=columns[11].getChildrenHTML();
				String sp2=getSpan(sp2Span, CHARSET, null, null).getChild(0).getText();
				sb.append(sp2).append(",");
				
				String sp3Span=columns[12].getChildrenHTML();
				String sp3=getSpan(sp3Span, CHARSET, null, null).getChild(0).getText();
				sb.append(sp3).append(",");
				
				String sp4Span=columns[13].getChildrenHTML();
				String sp4=getSpan(sp4Span, CHARSET, null, null).getChild(0).getText();
				sb.append(sp4).append(",");
				
				String sp5Span=columns[14].getChildrenHTML();
				String sp5=getSpan(sp5Span, CHARSET, null, null).getChild(0).getText();
				sb.append(sp5).append(",");
				
				String sp6Span=columns[15].getChildrenHTML();
				String sp6=getSpan(sp6Span, CHARSET, null, null).getChild(0).getText();
				sb.append(sp6).append(",");
				
				String sp7Span=columns[16].getChildrenHTML();
				String sp7=getSpan(sp7Span, CHARSET, null, null).getChild(0).getText();
				sb.append(sp7);
				
				ZunaoDczcSp zunaoDczcSp=new ZunaoDczcSp();
				zunaoDczcSp.setMatchId(matchId);
				zunaoDczcSp.setMatchtime(matchTime);
				zunaoDczcSp.setSp(sb.toString());
				list.add(zunaoDczcSp);
			} catch (Exception e) {
				logger.info(e.getMessage()+"zjq");
				continue;
			}
		}
		return list;
	}
	public static void main(String[] args) {
		DczcZjqsSpWin310FetchParser fetch=new DczcZjqsSpWin310FetchParser();
		List<ZunaoDczcSp> list=fetch.fetch("151004");
		System.out.println(list.size());
		System.out.println(list.get(0).getSp());
	}
}
