package com.cai310.lottery.web.controller.lottery.welfare3d;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.welfare3d.Welfare3dContentBeanBuilder;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.lottery.web.controller.lottery.SchemeUploadForm;

public class Welfare3dSchemeUploadForm extends SchemeUploadForm {
	/** 方案内容 */
	private String content;

	private Welfare3dPlayType playType;

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
		return Welfare3dContentBeanBuilder.buildCompoundContentBean(getSchemeContent(), playType);
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return Welfare3dContentBeanBuilder.buildSingleContentBean(getSchemeContent(), playType);
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
	public Welfare3dPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Welfare3dPlayType playType) {
		this.playType = playType;
	}

}
