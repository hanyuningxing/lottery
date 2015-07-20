package com.cai310.lottery.web.controller.lottery.sevenstar;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.sevenstar.SevenstarContentBeanBuilder;
import com.cai310.lottery.web.controller.lottery.SchemeUploadForm;

public class SevenstarSchemeUploadForm extends SchemeUploadForm {
	/** 方案内容 */
	private String content;

	/**
	 * 获取方案内容
	 * 
	 * @return 方案内容
	 * @throws DataException
	 */
	private String getSchemeContent() throws DataException {
		if (isFileUpload())
			return getUploadContent();
		else
			return getContent();
	}

	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		return SevenstarContentBeanBuilder.buildCompoundContentBean(getSchemeContent());
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return SevenstarContentBeanBuilder.buildSingleContentBean(getSchemeContent());
	}

	/**
	 * @return {@link #content}
	 */
	protected String getContent() {
		return content;
	}

	/**
	 * @param content the {@link #content} to set
	 */
	protected void setContent(String content) {
		this.content = content;
	}

}
