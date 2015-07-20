package com.cai310.lottery.entity.lottery;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.sf.json.JSONObject;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;

@MappedSuperclass
public abstract class MissDataInfo extends IdEntity implements CreateMarkable, UpdateMarkable {

	private static final long serialVersionUID = -4219017414998392460L;
	/**期号**/
	private String periodNumber;
	/**期编号**/
	private Long periodId;
	/**开奖结果**/
	private String result;

	/**存放遗漏数据,json格式**/
	private String content;
	
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	/** 版本号 */
	protected Integer version;
	
	/**
	 * 转换content内容为bean数据
	 * @return
	 */
//	@Transient
//	public abstract MissDataContent getMissDataContent();
	
	/**
	 * 转换content内容为Map
	 */
	@Transient
	public Map<String,Integer> getMissDataMap(){
		return (HashMap<String,Integer>)JSONObject.toBean(JSONObject.fromObject(this.getContent()), HashMap.class);
	}
	
	/**
	 * @return the periodNumber
	 */
	@Column(nullable=false,unique=true)
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the periodNumber to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}



	/**
	 * @return the result
	 */
	@Column
	public String getResult() {
		return result;
	}



	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}



	/**
	 * @return the periodId
	 */
	@Column
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId the periodId to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	@Lob
	@Column
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Version
	@Column(name = "version", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
