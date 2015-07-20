package com.cai310.lottery.web.controller.lottery.ssq;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.ssq.SsqContentBeanBuilder;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeCreateForm;

public class SsqSchemeCreateForm extends NumberSchemeCreateForm {

	/** 追号红球胆码 */
	private String redDanOfChase;

	/** 追号蓝球胆码 */
	private String blueDanOfChase;

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
		return SsqContentBeanBuilder.buildCompoundContentBean(getSchemeContent());
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return SsqContentBeanBuilder.buildSingleContentBean(getSchemeContent());
	}

	@Override
	public Map<String, String> getDanMap() throws DataException {
		if (!this.isHasDanOfChase())
			return null;
		if (StringUtils.isBlank(redDanOfChase) && StringUtils.isBlank(blueDanOfChase))
			return null;

		Map<String, String> map = new HashMap<String, String>();
		map.put("red", redDanOfChase);
		map.put("blue", blueDanOfChase);
		return map;
	}

	@Override
	public void setUnitsAndContent(Integer units, String content) {
		this.setUnits(units);
		this.content = content;
	}

	/* ------------------ getter and setter method ------------------ */

	/**
	 * @return {@link #redDanOfChase}
	 */
	public String getRedDanOfChase() {
		return redDanOfChase;
	}

	/**
	 * @param redDanOfChase the {@link #redDanOfChase} to set
	 */
	public void setRedDanOfChase(String redDanOfChase) {
		this.redDanOfChase = redDanOfChase;
	}

	/**
	 * @return {@link #blueDanOfChase}
	 */
	public String getBlueDanOfChase() {
		return blueDanOfChase;
	}

	/**
	 * @param blueDanOfChase the {@link #blueDanOfChase} to set
	 */
	public void setBlueDanOfChase(String blueDanOfChase) {
		this.blueDanOfChase = blueDanOfChase;
	}
	/**
	 * 投注格式01,02,03,04,05,06|01(篮球)
	 * @throws WebDataException 
	 */
	@Override
	protected String buildContent() throws WebDataException {
		if(null==this.getQuick_content())throw new WebDataException("投注内容为空");
		return this.getUnits()+":"+this.quick_content;
		
	}
}
