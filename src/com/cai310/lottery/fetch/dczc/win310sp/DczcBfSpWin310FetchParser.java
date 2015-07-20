package com.cai310.lottery.fetch.dczc.win310sp;

import java.util.List;

import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Lists;

public class DczcBfSpWin310FetchParser extends OkoooAbstractFetchParser {
	
	private static final String URL="http://www.310win.com/buy/DanChang.aspx?TypeID=8&issueNum=";
	
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
				String tr_id=row.getAttribute("id").split("_")[0];
				if(tr_id.equals("tr")){
					String row_table=row.getChildrenHTML();
					TableTag tag_sp=getTable(row_table, CHARSET,null,null);
					TableRow[] rows_sp = tag_sp.getRows();
					String matchId="";
					StringBuffer sb=new StringBuffer();
					for(TableRow row_:rows_sp){
						TableColumn[] columns = row_.getColumns();
						int j=0;
						for(TableColumn col:columns){
							String val=col.getChild(0).getText();
							j++;
							if(j<2||val.equals("&nbsp;")) continue;
							matchId=col.getAttribute("id").split("_")[1];
							String spHtml=col.getChildrenHTML();
							Span span=getSpan(spHtml, CHARSET,null,null);
							String sp=span.getChild(0).getText();
							sb.append(sp).append(",");
						}
					}
					sb.delete(sb.length()-1, sb.length());
					ZunaoDczcSp zunaoDczcSp=new ZunaoDczcSp();
					zunaoDczcSp.setMatchId(matchId);
					zunaoDczcSp.setMatchtime("0");
					zunaoDczcSp.setSp(sb.toString());
					list.add(zunaoDczcSp);
				}
				continue;
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				continue;
			}
		}
		return list;
	}
	public static void main(String[] args) {
		DczcBfSpWin310FetchParser fetch=new DczcBfSpWin310FetchParser();
		List<ZunaoDczcSp> list=fetch.fetch("141204");
		System.out.println(list.size());
		System.out.println(list.get(0).getSp());
	}
}
