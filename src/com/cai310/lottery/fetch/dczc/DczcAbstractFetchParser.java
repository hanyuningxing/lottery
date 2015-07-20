package com.cai310.lottery.fetch.dczc;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.fetch.FetchParser;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.utils.HttpClientUtil;

public abstract class DczcAbstractFetchParser implements FetchParser<DczcFetchResult, String> {
	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String LINE_KEY_PREFIX = "lid";

	public abstract String getCharset();

	public abstract String getSourceUrl(String issueNumber);

	public abstract PlayType getPlayType();

	protected String getLineKey(Integer lid) {
		return String.format("%s%s", LINE_KEY_PREFIX, lid);
	}

	public static Integer getLineId(String lineKey) {
		if (StringUtils.isBlank(lineKey))
			return null;
		return Integer.valueOf(lineKey.replaceAll(LINE_KEY_PREFIX, ""));
	}

	@Override
	public DczcFetchResult fetch(String issueNumber) {
		String sourceUrl = getSourceUrl(issueNumber);
		if (StringUtils.isBlank(sourceUrl)) {
			this.logger.warn("抓取的目标网址为空.");
			return null;
		}

		String html = fetchHTML(sourceUrl);
		if (StringUtils.isBlank(html)) {
			this.logger.warn("抓取回来的内容为空，目标网址：[{}].", sourceUrl);
			return null;
		}
		return parserHTML(issueNumber, html);
	}

	protected String fetchHTML(String url) {
		if (url.indexOf("&") > 0) {
			url = String.format("%s&_t=%s", url, System.currentTimeMillis());
		} else {
			url = String.format("%s?_t=%s", url, System.currentTimeMillis());
		}
		return com.cai310.utils.HttpClientUtil.getRemoteSource(url, getCharset(),true);
	}

	protected abstract DczcFetchResult parserHTML(String issueNumber, String html);
}
