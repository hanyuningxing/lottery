package com.cai310.lottery.fetch.zc;

import java.util.Map;

import com.cai310.lottery.dto.lottery.zc.LczcResultSoccerDTO;
import com.cai310.utils.HttpClientUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LczcResultSoccerFetchParser {
	public String getSourceUrl() {
		return "http://www.310win.com/Info/Result/Soccer.aspx?load=ajax&typeID=3&IssueID=";
	}
	
	private static final String CHARSET = "GBK";
	public LczcResultSoccerDTO fetch(String periodNumber) {
		LczcIssueIDFetchParser lczcIssueIDFetchParser=new LczcIssueIDFetchParser();
		Map<String,String> IssueIdMap=lczcIssueIDFetchParser.fetch();
		String issueId=IssueIdMap.get(periodNumber);
		String url = getSourceUrl()+issueId;
		String html = HttpClientUtil.getRemoteSource(url, CHARSET);
		Gson gson=new Gson();
		LczcResultSoccerDTO lczcResultSoccerDTO = gson.fromJson(html, new TypeToken<LczcResultSoccerDTO>(){}.getType());
		return lczcResultSoccerDTO;
	}
	public static void main(String[] args) {
		LczcResultSoccerFetchParser lczcResultSoccerFetchParser=new LczcResultSoccerFetchParser();
		LczcResultSoccerDTO map=lczcResultSoccerFetchParser.fetch("2014178");
		System.out.println(map.getResult());
	}
}
