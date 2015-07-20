package com.cai310.lottery.fetch.jczq;

import java.util.regex.Pattern;

import com.cai310.lottery.fetch.SimpleAbstractFetchParser;
import com.cai310.lottery.fetch.jczq.local.Grabber;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.utils.HttpClientUtil;

public abstract class JczqAbstractFetchParser extends SimpleAbstractFetchParser<JczqFetchResult, Object> {
	protected static final long CHECK_DAY = 30 * 24 * 3600 * 1000l;
	protected static final String DOUBLE_REGEX = "\\s*\\d+(\\.\\d+)?\\s*";
	protected static final Pattern MATCH_ID_PATTERN = Pattern.compile("\\s*周(一|二|三|四|五|六|日)(\\d{3})\\s*");
	protected static final Pattern TEAM_PATTERN = Pattern
			.compile("\\s*([^\\s\\(\\)\\+]+)(?:\\(((?:-|\\+)?\\d+)\\))?\\s+VS\\s+([^\\s]+)\\s*");

	@Override
	public String getCharset() {
		return "GBK";
	}
	@Override
	protected String fetchHTML(String url, String charset) { 
		Grabber g = new Grabber();
		return g.grabberJson(url);
	}
	public abstract PlayType getPlayType();

	public abstract PassMode getPassMode();
}
