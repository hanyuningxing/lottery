package com.cai310.lottery.fetch.keno;

import java.util.List;
import java.util.Map;

import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.springframework.stereotype.Repository;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Repository("xj11To5FetchParser")
public class Xj11to5FetchParser extends OkoooAbstractFetchParser {
	public String getSourceUrl() {
//		return "http://chart.cp.360.cn/zst/xj11/?lotId=167607&chartType=rxfb&spanType=0&span=100";//最近100期
		return "http://chart.cp.360.cn/zst/xj11/?lotId=167607&chartType=rxfb&spanType=1&span=3&r=0.7504499182105064#roll_132";//近3天
	}

	private static final String CHARSET = "GBK";

	public Map<String, String> fetch() throws DataException {
		String html = HttpClientUtil.getRemoteSource(getSourceUrl(), CHARSET);
		return parser(html);
	}

	public Map<String, String> fetchSpare() throws DataException {
		String htmlParse = HttpClientUtil
				.getRemoteSource(
						"http://www.wozhongla.com/sp2/act/inter.info.action?wAgent=8848&wPassword=888888&wReturnFmt=2&&wAction=1014&wParam=lotId=23547_pageno=1_pagesize=30_startIssue=_endIssue=&d=1421117301972",
						CHARSET);
		return parserSpare(htmlParse);
	}

	public Map<String, String> parserSpare(String html) {
		Map<String, String> map = Maps.newHashMap();
		int count=html.indexOf("}");
		String resultHtml=html.substring(count+2);
		Gson gson = new Gson();
		List<WZLXjResult> resultList=gson.fromJson(resultHtml,new TypeToken<List<WZLXjResult>>() {}.getType());
		for(WZLXjResult rs:resultList){
			String key=rs.getLotIssue();
			String value=rs.getKjCode().replace(" ", ",");
			map.put(key, value);
		}
		return map;
	}

	public Map<String, String> parser(String html) throws DataException {
		Map<String, String> map = Maps.newHashMap();
		String divHtml = getDivBy(html, CHARSET, "class", "chart-tab nonum")
				.getChildrenHTML();
		TableTag table = getTable(divHtml, CHARSET, "class", "chart-table");
		TableRow[] rows = table.getRows();
		for (TableRow row : rows) {
			try {
				TableColumn[] td = row.getColumns();
				String key = td[0].toPlainTextString().replace("-", "");
				if (key.length() != 10) {
					continue;
				}
				String value = td[2].toPlainTextString().replace(" ", ",");
				map.put(key, value);
			} catch (Exception e) {
				continue;
			}

		}
		return map;
	}

	public static void main(String[] args) throws DataException {
		Xj11to5FetchParser p = new Xj11to5FetchParser();
		Map<String, String> IssueID = p.fetch();
		// String periodNumber="2014154";
		// String value=IssueID.get(periodNumber);
		System.out.println(IssueID.values());
		System.out.println(IssueID.get("2015012240"));
		// System.out.println(value);
	}
}
