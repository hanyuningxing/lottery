package com.cai310.lottery.fetch.keno;

import java.util.Map;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.fetch.SimpleAbstractFetchParser;
import com.google.common.collect.Maps;

public abstract class kenoAbstractFetchParser extends
		SimpleAbstractFetchParser<Map<String, String>, Object> {

	protected Map<String, String> parserHTML(String html, String charset) {
		// JSONObject jsonobj=JSONObject.fromObject(html);
		// JSONArray arrayConcats = jsonobj.getJSONArray("Table");
		// JSONObject contactrows=null;
		// if(arrayConcats.size()!=0){
		//
		// contactrows=arrayConcats.getJSONObject(0);
		// String IssueNum = contactrows.getString("IssueNum");
		// String Result = contactrows.getString("Result");
		// String AwardTime = contactrows.getString("AwardTime");
		// kenoBaseResult kenobaseresult = new kenoBaseResult();
		// DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		// Date awardDate;
		// try {
		// awardDate = sdf.parse(AwardTime);
		// kenobaseresult.setAwardTime(awardDate);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// kenobaseresult.setIssueNum(IssueNum);
		// kenobaseresult.setResult(Result);
		// return kenobaseresult;
		// }else{
		// return new kenoBaseResult();
		// }
		Map<String, String> itemMap = Maps.newLinkedHashMap();
		// Parser parser = Parser.createParser(html, charset);
		// NodeFilter tableFilter = new NodeClassFilter(Span.class);
		// NodeFilter[] filters = new NodeFilter[] { tableFilter };
		// OrFilter filter = new OrFilter(filters);
		// NodeList nodeList;
		// try {
		// nodeList = parser.parse(filter);
		// } catch (ParserException e) {
		// return null;
		// }
		// for (int i = 0; i <= nodeList.size(); i++) {
		// if (nodeList.elementAt(i) instanceof Span) {
		// Span tag = (Span) nodeList.elementAt(i);
		// if ("spnHidValue".equals(tag.getAttribute("id"))) {
		//
		// String result = tag.getStringText();
		// String[] resultList= result.split("&");
		// for(int j=0;j<resultList.length;j++){
		// String[] s = resultList[i].split("[+]");
		// itemMap.put(s[1], s[2]);
		// }
		// return itemMap;
		// }
		// }
		// }
		//
		// return null;
		String result = getSpan(html, charset);
		String[] resultList = result.split("&");

		for (int i = 1; i < 300; i++) {
			String[] s = resultList[i].split("[+]");
			itemMap.put(s[1], s[2]);
		}
		return itemMap;

	}

	protected String getSpan(String html, String charset) {
		Parser parser = Parser.createParser(html, charset);
		NodeFilter tableFilter = new NodeClassFilter(Span.class);
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			this.logger.warn("解析HTML出错.", e);
			return null;
		}
		for (int i = 0; i <= nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof Span) {
				Span tag = (Span) nodeList.elementAt(i);
				if ("spnHidValue".equals(tag.getAttribute("id"))) {
					return tag.getStringText();
				}
			}
		}

		return null;
	}
}
