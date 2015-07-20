package com.cai310.lottery.web.controller.lottery.pl;

import java.util.Map;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.pl.PlContentBeanBuilder;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.web.controller.lottery.NumberSchemeCreateForm;

public class PlSchemeCreateForm extends NumberSchemeCreateForm {

	private PlPlayType playType;

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
		return PlContentBeanBuilder.buildCompoundContentBean(getSchemeContent(), playType);
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return PlContentBeanBuilder.buildSingleContentBean(getSchemeContent(), playType);
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

	/* ------------------ getter and setter method ------------------ */

	

	/**
	 * @return the playType
	 */
	public PlPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(PlPlayType playType) {
		this.playType = playType;
	}

	@Override
	protected String buildContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
