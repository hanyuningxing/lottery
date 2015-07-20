package com.cai310.lottery.fetch.zc;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.utils.HttpClientUtil;

public class SczcIssueIDFetchParser extends OkoooAbstractFetchParser{
	private static final String URL = "http://www.310win.com/zucai/4changjinqiucai/kaijiang_zc_4.html";

	private static final String CHARSET = "GBK";

	public Map<String,String> fetch() {
		String html = HttpClientUtil.getRemoteSource(URL, CHARSET);
		return parser(html);
	}

	public Map<String,String> parser(String html) {
		String divStr = getDivBy(html, CHARSET, "class", "ste23")
				.getChildrenHTML();
		SelectTag selectTag = getSelectBy(divStr, CHARSET, "name",
				"dropIssueNum");
        OptionTag[] optionTag=selectTag.getOptionTags();
        Map<String,String> map=new HashMap<String, String>();
        for(OptionTag o:optionTag){
        	map.put(o.getOptionText(), o.getAttribute("VALUE"));
        }
		return map;
	}
	public static void main(String[] args) {
		SczcIssueIDFetchParser p = new SczcIssueIDFetchParser();
		Map<String,String> IssueID = p.fetch();
		String periodNumber="2014154";
		String value=IssueID.get(periodNumber);
		System.out.println(IssueID);
		System.out.println(value);

	}
}
