package com.cai310.lottery.fetch.zc;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;

import com.cai310.lottery.fetch.OkoooAbstractFetchParser;
import com.cai310.utils.HttpClientUtil;

public class SfzcIssueIDFetchParser extends OkoooAbstractFetchParser {
	private static final String URL = "http://www.310win.com/zucai/14changshengfucai/kaijiang_zc_1.html";

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
		SfzcIssueIDFetchParser p = new SfzcIssueIDFetchParser();
		Map<String,String> IssueID = p.fetch();
		System.out.println(IssueID);

	}
}
