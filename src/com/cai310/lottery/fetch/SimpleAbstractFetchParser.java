package com.cai310.lottery.fetch;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;
import com.cai310.utils.HttpClientUtil;

public abstract class SimpleAbstractFetchParser<T, E> implements FetchParser<T, E> {
	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

	public abstract String getCharset();

	public abstract String getSourceUrl();

	@Override
	public T fetch(E param) {
		String sourceUrl = getSourceUrl();
		if (StringUtils.isBlank(sourceUrl)) {
			this.logger.warn("抓取的目标网址为空.");
			return null;
		}

		final String charset = (StringUtils.isNotBlank(getCharset())) ? getCharset() : Constant.DEFAULT_ENCODE;
		String html = fetchHTML(sourceUrl, charset);
		if (StringUtils.isBlank(html)) {
			this.logger.warn("抓取回来的内容为空，目标网址：[{}].", sourceUrl);
			return null;
		}
		return parserHTML(html, charset);
	}

	protected String fetchHTML(String url, String charset) {
		return HttpClientUtil.getRemoteSource(url, charset);
	}

	protected abstract T parserHTML(String html, String charset);
}
