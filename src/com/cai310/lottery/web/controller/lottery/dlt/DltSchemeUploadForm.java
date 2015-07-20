package com.cai310.lottery.web.controller.lottery.dlt;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.dlt.DltContentBeanBuilder;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.web.controller.lottery.SchemeUploadForm;

public class DltSchemeUploadForm extends SchemeUploadForm {
	/** 方案内容 */
	private String content;

	private DltPlayType playType;

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
		return DltContentBeanBuilder.buildCompoundContentBean(getSchemeContent(), playType);
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return DltContentBeanBuilder.buildSingleContentBean(getSchemeContent(), playType);
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

	/**
	 * @return the playType
	 */
	public DltPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(DltPlayType playType) {
		this.playType = playType;
	}

}
