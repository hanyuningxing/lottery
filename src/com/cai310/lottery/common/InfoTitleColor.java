package com.cai310.lottery.common;
/**
 * 
 *
 */
public enum InfoTitleColor {
	BLACK("","黑色"), 
	BLUE("blue","蓝色"), 
	RED("red","红色");
	private String colorStyle;
	private String colorName;
	private InfoTitleColor(String colorStyle,String name) {
		this.colorStyle = colorStyle;
		this.colorName = name;
	}

	public String getColorStyle() {
		return colorStyle;
	}

	public void setColorStyle(String colorStyle) {
		this.colorStyle = colorStyle;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}


}
