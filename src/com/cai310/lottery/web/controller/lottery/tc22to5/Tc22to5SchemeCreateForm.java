package com.cai310.lottery.web.controller.lottery.tc22to5;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.tc22to5.Tc22to5ContentBeanBuilder;
import com.cai310.lottery.web.controller.lottery.NumberSchemeCreateForm;

public class Tc22to5SchemeCreateForm extends NumberSchemeCreateForm {
	
	/** 追号胆码 */
	private String danOfChase;

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
		return Tc22to5ContentBeanBuilder.buildCompoundContentBean(getSchemeContent());
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return Tc22to5ContentBeanBuilder.buildSingleContentBean(getSchemeContent());
	}

	@Override
	public Map<String, String> getDanMap() throws DataException {
		if (!this.isHasDanOfChase())
			return null;
		if (StringUtils.isBlank(danOfChase))
			return null;

		Map<String, String> map = new HashMap<String, String>();
		map.put("dan", danOfChase);
		return map;
	}

	@Override
	public void setUnitsAndContent(Integer units, String content) {
		this.setUnits(units);
		this.content = content;
	}

	/* ------------------ getter and setter method ------------------ */
	
	/**
	 * @return {@link #danOfChase}
	 */
	public String getDanOfChase() {
		return danOfChase;
	}

	/**
	 * @param danOfChase the {@link #danOfChase} to set
	 */
	public void setDanOfChase(String danOfChase) {
		this.danOfChase = danOfChase;
	}

	@Override
	protected String buildContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
