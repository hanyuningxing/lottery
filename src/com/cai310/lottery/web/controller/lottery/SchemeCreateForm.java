package com.cai310.lottery.web.controller.lottery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;

public abstract class SchemeCreateForm {
	protected transient Logger logger = LoggerFactory.getLogger(getClass());

	/** 期ID */
	private Long periodId;

	/** 方案描述 */
	private String description;

	/** 方案注数（单倍注数） */
	private Integer units;

	/** 方案倍数 */
	private Integer multiple;

	/** 方案金额 */
	private Integer schemeCost;

	/**
	 * 方案投注的方式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 */
	private SalesMode mode;

	/**
	 * 方案分享类型
	 * 
	 * @see com.cai310.lottery.common.ShareType
	 */
	private ShareType shareType;

	/**
	 * 方案保密类型
	 * 
	 * @see com.cai310.lottery.common.SecretType
	 */
	private SecretType secretType;

	/**
	 * 方案认购许可类型
	 * 
	 * @see com.cai310.lottery.common.SubscriptionLicenseType
	 */
	private SubscriptionLicenseType subscriptionLicenseType;

	/** 方案认购密码 */
	private String subscriptionPassword;

	/** 方案最低认购金额 */
	private BigDecimal minSubscriptionCost;

	/** 方案发起人的佣金率，值应小于1 */
	private Float commissionRate;

	/** 发起人认购金额 */
	private BigDecimal subscriptionCost;

	/** 发起人保底金额 */
	private BigDecimal baodiCost;

	/** 是否先发起后上传方案内容 */
	private boolean aheadOfUploadContent;

	/** 是否文件上传 */
	private boolean fileUpload;

	/** 上传的文件 */
	private File upload;

	/** 上传文件的文件名 */
	private String uploadFileName;

	/** 上传文件的文件类型 */
	private String uploadContentType;
	
	private Integer unitsMoney=2;
	
	/** 免费保存的方案*/
	private boolean fromFreeSave;

	/* ---------------- other method ---------------- */
	/**
	 * 构建格式化好的方案内容和根据内容计算出来的注数
	 */
	public ContentBean buildContentBean() throws DataException {
		switch (this.getMode()) {
		case COMPOUND:
			return buildCompoundContentBean();
		case SINGLE:
			return buildSingleContentBean();
		default:
			throw new DataException("方案投注方式不合法.");
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
			throw new DataException("只允许上传文本格式的文件(.txt).");

		try {
			return StringUtils.trim(IOUtils.toString(new FileInputStream(upload), "GBK"));
		} catch (FileNotFoundException e) {
			logger.warn("读取上传文件发生异常.", e);
			throw new DataException("读取上传文件发生异常.");
		} catch (IOException e) {
			logger.warn("读取上传文件发生异常.", e);
			throw new DataException("读取上传文件发生异常.");
		}
	}

	/* ---------------- getter and setter method ---------------- */

	/**
	 * @return {@link #periodId}
	 */
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId the {@link #periodId} to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	/**
	 * @return {@link #description}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the {@link #description} to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return {@link #units}
	 */
	public Integer getUnits() {
		return units;
	}

	/**
	 * @param units the {@link #units} to set
	 */
	public void setUnits(Integer units) {
		this.units = units;
	}

	/**
	 * @return {@link #multiple}
	 */
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the {@link #multiple} to set
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return {@link #schemeCost}
	 */
	public Integer getSchemeCost() {
		return schemeCost;
	}

	/**
	 * @param schemeCost the {@link #schemeCost} to set
	 */
	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	/**
	 * @return {@link #mode}
	 */
	public SalesMode getMode() {
		return mode;
	}

	/**
	 * @param mode the {@link #mode} to set
	 */
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}

	/**
	 * @return {@link #shareType}
	 */
	public ShareType getShareType() {
		return shareType;
	}

	/**
	 * @param shareType the {@link #shareType} to set
	 */
	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	/**
	 * @return {@link #secretType}
	 */
	public SecretType getSecretType() {
		return secretType;
	}

	/**
	 * @param secretType the {@link #secretType} to set
	 */
	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}

	/**
	 * @return {@link #subscriptionLicenseType}
	 */
	public SubscriptionLicenseType getSubscriptionLicenseType() {
		return subscriptionLicenseType;
	}

	/**
	 * @param subscriptionLicenseType the {@link #subscriptionLicenseType} to
	 *            set
	 */
	public void setSubscriptionLicenseType(SubscriptionLicenseType subscriptionLicenseType) {
		this.subscriptionLicenseType = subscriptionLicenseType;
	}

	/**
	 * @return {@link #subscriptionPassword}
	 */
	public String getSubscriptionPassword() {
		return subscriptionPassword;
	}

	/**
	 * @param subscriptionPassword the {@link #subscriptionPassword} to set
	 */
	public void setSubscriptionPassword(String subscriptionPassword) {
		this.subscriptionPassword = subscriptionPassword;
	}

	/**
	 * @return {@link #minSubscriptionCost}
	 */
	public BigDecimal getMinSubscriptionCost() {
		return minSubscriptionCost;
	}

	/**
	 * @param minSubscriptionCost the {@link #minSubscriptionCost} to set
	 */
	public void setMinSubscriptionCost(BigDecimal minSubscriptionCost) {
		this.minSubscriptionCost = minSubscriptionCost;
	}

	/**
	 * @return {@link #commissionRate}
	 */
	public Float getCommissionRate() {
		return commissionRate;
	}

	/**
	 * @param commissionRate the {@link #commissionRate} to set
	 */
	public void setCommissionRate(Float commissionRate) {
		this.commissionRate = commissionRate;
	}

	/**
	 * @return {@link #subscriptionCost}
	 */
	public BigDecimal getSubscriptionCost() {
		return subscriptionCost;
	}

	/**
	 * @param subscriptionCost the {@link #subscriptionCost} to set
	 */
	public void setSubscriptionCost(BigDecimal subscriptionCost) {
		this.subscriptionCost = subscriptionCost;
	}

	/**
	 * @return {@link #baodiCost}
	 */
	public BigDecimal getBaodiCost() {
		return baodiCost;
	}

	/**
	 * @param baodiCost the {@link #baodiCost} to set
	 */
	public void setBaodiCost(BigDecimal baodiCost) {
		this.baodiCost = baodiCost;
	}

	/**
	 * @return {@link #aheadOfUploadContent}
	 */
	public boolean isAheadOfUploadContent() {
		return aheadOfUploadContent;
	}

	/**
	 * @param aheadOfUploadContent the {@link #aheadOfUploadContent} to set
	 */
	public void setAheadOfUploadContent(boolean aheadOfUploadContent) {
		this.aheadOfUploadContent = aheadOfUploadContent;
	}

	/**
	 * @return {@link #fileUpload}
	 */
	public boolean isFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the {@link #fileUpload} to set
	 */
	public void setFileUpload(boolean fileUpload) {
		this.fileUpload = fileUpload;
	}

	/**
	 * @return {@link #upload}
	 */
	public File getUpload() {
		return upload;
	}

	/**
	 * @param upload the {@link #upload} to set
	 */
	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * @return {@link #uploadFileName}
	 */
	public String getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * @param uploadFileName the {@link #uploadFileName} to set
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * @return {@link #uploadContentType}
	 */
	public String getUploadContentType() {
		return uploadContentType;
	}

	/**
	 * @param uploadContentType the {@link #uploadContentType} to set
	 */
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	/**
	 * @return 每注金额
	 */
	public Integer getUnitsMoney() {
		return unitsMoney;
	}
	/**
	 * @param unitsMoney the unitsMoney to set
	 */
	public void setUnitsMoney(Integer unitsMoney) {
		this.unitsMoney = unitsMoney;
	}

	/**从免费保存的方案中创建*/
	public boolean isFromFreeSave() {
		return fromFreeSave;
	}

	public void setFromFreeSave(boolean fromFreeSave) {
		this.fromFreeSave = fromFreeSave;
	}

	
}
