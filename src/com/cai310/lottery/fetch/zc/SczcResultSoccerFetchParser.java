package com.cai310.lottery.fetch.zc;

import java.util.Map;

import com.cai310.lottery.dto.lottery.zc.SczcResultSoccerDTO;
import com.cai310.utils.HttpClientUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SczcResultSoccerFetchParser {
	public String getSourceUrl() {
		return "http://www.310win.com/Info/Result/Soccer.aspx?load=ajax&typeID=4&IssueID=";
	}
	
	private static final String CHARSET = "GBK";
	public SczcResultSoccerDTO fetch(String periodNumber) {
		SczcIssueIDFetchParser sczcIssueIDFetchParser=new SczcIssueIDFetchParser();
		Map<String,String> IssueIdMap=sczcIssueIDFetchParser.fetch();
		String issueId=IssueIdMap.get(periodNumber);
		String url = getSourceUrl()+issueId;
		String html = HttpClientUtil.getRemoteSource(url, CHARSET);
		Gson gson=new Gson();
		SczcResultSoccerDTO sczcResultSoccerDTO = gson.fromJson(html, new TypeToken<SczcResultSoccerDTO>(){}.getType());
		return sczcResultSoccerDTO;
	}
	public static void main(String[] args) {
		SczcResultSoccerFetchParser sczcResultSoccerFetchParser=new SczcResultSoccerFetchParser();
		SczcResultSoccerDTO map=sczcResultSoccerFetchParser.fetch("2014185");
		System.out.println(map.getResult());
	}
}
