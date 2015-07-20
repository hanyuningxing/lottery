package com.cai310.lottery.web.controller.lottery.welfare3d;

import java.util.Map;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.welfare3d.Welfare3dContentBeanBuilder;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeCreateForm;

public class Welfare3dSchemeCreateForm extends NumberSchemeCreateForm {

	private Welfare3dPlayType playType;

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
		return Welfare3dContentBeanBuilder.buildCompoundContentBean(getSchemeContent(), playType);
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return Welfare3dContentBeanBuilder.buildSingleContentBean(getSchemeContent(), playType);
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
	public Welfare3dPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Welfare3dPlayType playType) {
		this.playType = playType;
	}
	/**
	 * 投注格式01-01-02
	 * @throws WebDataException 
	 */
	@Override
	protected String buildContent() throws WebDataException {
		if(null==this.getQuick_content())throw new WebDataException("投注内容为空");
		return this.getUnits()+":"+this.quick_content;
		
	}

}
