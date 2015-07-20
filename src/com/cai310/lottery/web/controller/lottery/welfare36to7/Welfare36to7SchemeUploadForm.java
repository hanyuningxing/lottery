package com.cai310.lottery.web.controller.lottery.welfare36to7;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.welfare36to7.Welfare36to7ContentBeanBuilder;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;
import com.cai310.lottery.web.controller.lottery.SchemeUploadForm;

public class Welfare36to7SchemeUploadForm extends SchemeUploadForm {
	/** 方案内容 */
	private String content;

	private Welfare36to7PlayType playType;

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
		return Welfare36to7ContentBeanBuilder.buildCompoundContentBean(getSchemeContent(), playType);
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return Welfare36to7ContentBeanBuilder.buildSingleContentBean(getSchemeContent(), playType);
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
	public Welfare36to7PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Welfare36to7PlayType playType) {
		this.playType = playType;
	}

}
