package com.cai310.lottery.fetch.zc;

import java.util.Map;

import com.cai310.lottery.dto.lottery.zc.SfzcResultSoccerDTO;
import com.cai310.utils.HttpClientUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SfzcResultSoccerFetchParser {
	
	public String getSourceUrl() {
		return "http://www.310win.com/Info/Result/Soccer.aspx?load=ajax&typeID=1&IssueID=";
	}
	
	private static final String CHARSET = "GBK";
	public SfzcResultSoccerDTO fetch(String periodNumber) {
		SfzcIssueIDFetchParser sfzcIssueIDFetchParser=new SfzcIssueIDFetchParser();
		Map<String,String> IssueIdMap=sfzcIssueIDFetchParser.fetch();
		String issueId=IssueIdMap.get(periodNumber);
		String url = getSourceUrl()+issueId;
		String html = HttpClientUtil.getRemoteSource(url, CHARSET);
		Gson gson=new Gson();
		SfzcResultSoccerDTO sfzcResultSoccerDTO = gson.fromJson(html, new TypeToken<SfzcResultSoccerDTO>(){}.getType());
		return sfzcResultSoccerDTO;
	}
	public static void main(String[] args) {
		SfzcResultSoccerFetchParser sfzcResultSoccerFetchParser=new SfzcResultSoccerFetchParser();
		SfzcResultSoccerDTO map=sfzcResultSoccerFetchParser.fetch("2014168");
		System.out.println(map.toString());
	}
}
