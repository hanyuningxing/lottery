package com.cai310.lottery.web.controller.lottery.dlt;

import java.util.Map;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.dlt.DltContentBeanBuilder;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeCreateForm;

public class DltSchemeCreateForm extends NumberSchemeCreateForm {

	private DltPlayType playType;
	
	private Boolean dltAdditional;

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
		return DltContentBeanBuilder.buildCompoundContentBean(getSchemeContent(), playType);
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return DltContentBeanBuilder.buildSingleContentBean(getSchemeContent(), playType);
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
	public DltPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(DltPlayType playType) {
		this.playType = playType;
	}

	/**
	 * @return the dltAdditional
	 */
	public Boolean getDltAdditional() {
		return dltAdditional;
	}

	/**
	 * @param dltAdditional the dltAdditional to set
	 */
	public void setDltAdditional(Boolean dltAdditional) {
		this.dltAdditional = dltAdditional;
	}
	/**
	 * 投注格式01,02,03,04,05|01,02(后区)
	 * @throws WebDataException 
	 */
	@Override
	protected String buildContent() throws WebDataException {
		if(null==this.getQuick_content())throw new WebDataException("投注内容为空");
		return this.getUnits()+":"+this.quick_content;
		
	}

}
