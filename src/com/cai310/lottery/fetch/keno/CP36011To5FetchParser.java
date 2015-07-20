package com.cai310.lottery.fetch.keno;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Maps;

/**
 * 
 * <p>Title: CP36011To5FetchParser.java </p>
 * <p>Description:360彩票 -11选5 </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: miracle</p>
 * @author yo
 * @date 2014-10-23 上午08:33:04 
 * @version 1.0
 */
public class CP36011To5FetchParser extends OkoooAbstractFetchParser{
	private static final String URL = "http://cp.360.cn/yun11/?r_a=uuuYRn";
	
	private static final String SD_URL="http://chart.cp.360.cn/zst/syy?lotId=166406&chartType=rxfb&spanType=0&span=100";

	private static final String NEW11TO5_URL = "http://cp.360.cn/dlcjx/?xxdh&r_a=aqiuAn";
	
	private static final String JX_URL="http://chart.cp.360.cn/zst/syy/?lotId=166406&chartType=rxfb&spanType=0&span=100";
	
	private static final String GD11TO5_URL = "http://cp.360.cn/gd11/?r_a=jArU32";
	
	private static final String GD_URL="http://chart.cp.360.cn/zst/gd11/?lotId=165707&chartType=rxfb&spanType=0&span=100";
	
	private static final String XJ11To5_URL="http://cp.360.cn/xj11/?r_a=2qiYJz";
	
	private static final String XJ_URL="http://chart.cp.360.cn/zst/xj11/?lotId=167607&chartType=rxfb&spanType=0&span=100";
	
	private boolean isNew11to5 = Boolean.FALSE;
	
	private boolean isGd11to5=Boolean.FALSE;
	
	private boolean isXj11to5=Boolean.FALSE;

	private static final String CHARSET = "GBK";
	
	private static final String REGEX = "^\\s*(\\d{1,2}(\\D+\\d{1,2}){4})\\s*$";

	public Map<String, String[]> fetch() {
		String url = URL;
		if(isNew11to5){
			url = NEW11TO5_URL;
		}else if(isGd11to5){
			url=GD11TO5_URL;
		}else if(isXj11to5){
			url=XJ11To5_URL;
		}
		String html = HttpClientUtil.getRemoteSource(url, CHARSET);
		return parser(html);
	}
	public Map<String,String> fetchSpare(){
		String url = SD_URL;
		if(isNew11to5){
			url = JX_URL;
		}else if(isGd11to5){
			url=GD_URL;
		}else if(isXj11to5){
			url=XJ_URL;
		}
		String html=HttpClientUtil.getRemoteSource(url, CHARSET);
		return parserSpare(html);
	}
	
	private Map<String, String> parserSpare(String html) {
		Map<String,String> map=Maps.newHashMap();
		String divHtml=getDivBy(html, CHARSET, "id", "chart-tab").getChildrenHTML();
		TableTag table=getTable(divHtml, CHARSET, "class", "chart-table");
		TableRow[] rows = table.getRows();
		for(TableRow tr:rows){
			try {
				TableColumn[] tds= tr.getColumns();
				try {
					String key="";
					String value="";
					String[] dateKey=tds[0].toPlainTextString().split("-");
					key=dateKey[0]+dateKey[1];
					if(key.length()!=10){
						continue;
					}
					String result=tds[2].toPlainTextString().replace(" ", ",");
					boolean b=Pattern.matches(REGEX, result);
					if(b){
						value=result;
						map.put(key, value);
					}
				} catch (Exception e) {
					continue;
				}
			} catch (Exception e) {
				continue;
			}
		}
		return map;
	}
	public Map<String, String[]> parser(String html) {
		Map<String, String[]> map = Maps.newHashMap();
		String divHtml = getDivBy(html, CHARSET, "class", "kpkjcode").getChildrenHTML();
		TableTag table = getTable(divHtml, CHARSET, null, null);
		TableRow[] rows = table.getRows();
		String key = "";
		String val[];
		TableColumn col[];
		for (TableRow row : rows) {
			try {			
				val = new String[2];
				col =	 row.getColumns();
				key = col[0].getChildrenHTML().replaceAll("<.*?>", " ");
				val[0] = col[1].getChildrenHTML().replaceAll("<.*?>", " ").replaceAll("\\s+"," ");		
				val[1] = col[2].getChildrenHTML().replaceAll("<.*?>", " ").replaceAll("\r\n", "").replaceAll("\\s+"," "); 
				map.put(key, val);
			} catch (Exception e) {
				//e.printStackTrace();
			}

		}
		return map;
	}
	public static void main(String[] args) { 
		CP36011To5FetchParser p = new CP36011To5FetchParser();
		Map<String,String> map_sd=p.fetchSpare();
		System.out.println(map_sd.size()+"!!!"+map_sd.values());
		p.setGd11to5(Boolean.TRUE);
		Map<String,String> map_gd=p.fetchSpare();
		System.out.println(map_gd.size()+"!!!"+map_gd.values());
		p.setGd11to5(Boolean.FALSE);
		p.setXj11to5(Boolean.TRUE);
		Map<String,String> map_xj=p.fetchSpare();
		System.out.println(map_xj.size()+"!!!"+map_xj.values());
		Map<String, String[]> map = p.fetch();
		System.out.println("======================11选5=======================================");
		for(Entry<String,String[]> entry:map.entrySet()){
			System.out.println(entry.getKey()+":   "+entry.getValue()[0]+"  "+entry.getValue()[1]+"  ");
		}
		System.out.println("======================下面是新11选5=======================================");
		p.setNew11to5(Boolean.TRUE);
		map = p.fetch();
		for(Entry<String,String[]> entry:map.entrySet()){
			System.out.println(entry.getKey()+":   "+entry.getValue()[0]+"  "+entry.getValue()[1]+"  ");
		}
		
		System.out.println("======================下面是广东11选5=======================================");
		 
		map = p.fetch();
		for(Entry<String,String[]> entry:map.entrySet()){
			System.out.println(entry.getKey()+":   "+entry.getValue()[0]+"  "+entry.getValue()[1]+"  ");
		}
	}
	public boolean isNew11to5() {
		return isNew11to5;
	}
	public void setNew11to5(boolean isNew11to5) {
		this.isNew11to5 = isNew11to5;
	}
	
	public boolean isGd11to5() {
		return isGd11to5;
	}
	public void setGd11to5(boolean isGd11to5) {
		this.isGd11to5 = isGd11to5;
	}
	public boolean isXj11to5() {
		return isXj11to5;
	}
	public void setXj11to5(boolean isXj11to5) {
		this.isXj11to5 = isXj11to5;
	}
}
