package com.cai310.lottery.web.controller.lottery.welfare36to7;

import java.util.Map;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.welfare36to7.Welfare36to7ContentBeanBuilder;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;
import com.cai310.lottery.web.controller.lottery.NumberSchemeCreateForm;

public class Welfare36to7SchemeCreateForm extends NumberSchemeCreateForm {

	private Welfare36to7PlayType playType;
	
	
	

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
		return Welfare36to7ContentBeanBuilder.buildCompoundContentBean(getSchemeContent(), playType);
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return Welfare36to7ContentBeanBuilder.buildSingleContentBean(getSchemeContent(), playType);
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
	public Welfare36to7PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Welfare36to7PlayType playType) {
		this.playType = playType;
	}

	@Override
	protected String buildContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
