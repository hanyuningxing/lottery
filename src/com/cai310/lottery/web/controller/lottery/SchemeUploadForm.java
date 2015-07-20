package com.cai310.lottery.web.controller.lottery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;

public abstract class SchemeUploadForm {
	protected transient Logger logger = LoggerFactory.getLogger(getClass());

	/** 方案ID */
	private Long schemeId;

	/** 是否文件上传 */
	private boolean fileUpload;

	/** 上传的文件 */
	private File upload;

	/** 上传文件的文件名 */
	private String uploadFileName;

	/** 上传文件的文件类型 */
	private String uploadContentType;

	/* ---------------- other method ---------------- */

	/**
	 * 获取上传文件的内容
	 * 
	 * @return 上传文件的内容
	 * @throws DataException
	 */
	public String getUploadContent() throws DataException {
		if (!fileUpload)
			throw new DataException("非文件上传方式,不能获取文件内容.");
		if (upload == null)
			throw new DataException("上传文件不存在.");
		if (!uploadContentType.equals("text/plain"))
			throw new DataException("上传文件不存在.");

		try {
			return StringUtils.trim(IOUtils.toString(new FileInputStream(upload)));
		} catch (FileNotFoundException e) {
			logger.warn("读取上传文件发生异常.", e);
			throw new DataException("读取上传文件发生异常.");
		} catch (IOException e) {
			logger.warn("读取上传文件发生异常.", e);
			throw new DataException("读取上传文件发生异常.");
		}
	}

	/**
	 * 构造复式方案内容和计算注数
	 * 
	 */
	protected abstract ContentBean buildCompoundContentBean() throws DataException;

	/**
	 * 构造单式方案内容和计算注数
	 * 
	 */
	protected abstract ContentBean buildSingleContentBean() throws DataException;

	/* ---------------- getter and setter method ---------------- */

	

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


	public boolean isFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(boolean fileUpload) {
		this.fileUpload = fileUpload;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	
}
