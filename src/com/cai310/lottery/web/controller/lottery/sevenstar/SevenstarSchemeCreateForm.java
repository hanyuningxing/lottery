package com.cai310.lottery.web.controller.lottery.sevenstar;

import java.util.Map;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.sevenstar.SevenstarContentBeanBuilder;
import com.cai310.lottery.web.controller.lottery.NumberSchemeCreateForm;

public class SevenstarSchemeCreateForm extends NumberSchemeCreateForm {
	/* -------------------- other method -------------------- */
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

	@Override
	public Map<String, String> getDanMap() throws DataException {
		return null;
	}

	@Override
	public void setUnitsAndContent(Integer units, String content) {
		this.setUnits(units);
		this.content = content;
	}

	@Override
	protected String buildContent() {
		// TODO Auto-generated method stub
		return null;
	}

	/* ------------------ getter and setter method ------------------ */
}
