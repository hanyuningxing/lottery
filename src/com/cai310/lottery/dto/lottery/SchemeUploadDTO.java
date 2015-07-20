package com.cai310.lottery.dto.lottery;

/**
 * 方案内容上传的数据传输对象
 * 
 */
public class SchemeUploadDTO {
	/** 方案ID */
	private Long schemeId;

	/** 上传的方案内容 */
	private String uploadContent;

	/**
	 * @return {@link #schemeId}
	 */
	public Long getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId the {@link #schemeId} to set
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return {@link #uploadContent}
	 */
	public String getUploadContent() {
		return uploadContent;
	}

	/**
	 * @param uploadContent the {@link #uploadContent} to set
	 */
	public void setUploadContent(String uploadContent) {
		this.uploadContent = uploadContent;
	}
}
