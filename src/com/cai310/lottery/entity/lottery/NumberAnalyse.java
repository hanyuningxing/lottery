package com.cai310.lottery.entity.lottery;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.sf.json.JSONObject;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "NUMBER_ANALYSE")
@Entity
public class NumberAnalyse extends IdEntity implements CreateMarkable, UpdateMarkable {
	
	private static final long serialVersionUID = 449904355353184070L;
	
	/**彩种类型**/
	private Lottery lottery;
	/**期号**/
	private String lastPeriodNumber;
	/**期编号**/
	private Long lastPeriodId;

	/**存放冷热数据,json格式**/
	private String content;
	
	private String contentFor30;
	
	private String contentFor50;
	
	private String contentFor100;
	
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	/**版本号**/
	private Integer version;
	
	
	/**
	 * 转换content内容为Map
	 */
	@Transient
	public Map<String,Integer> getMissContentAllMap(){
		return (HashMap<String,Integer>)JSONObject.toBean(JSONObject.fromObject(this.getContent()), HashMap.class);
	}
	@Transient
	public Map<String,Integer> getMissContent30Map(){
		return (HashMap<String,Integer>)JSONObject.toBean(JSONObject.fromObject(this.getContentFor30()), HashMap.class);
	}
	@Transient
	public Map<String,Integer> getMissContent50Map(){
		return (HashMap<String,Integer>)JSONObject.toBean(JSONObject.fromObject(this.getContentFor50()), HashMap.class);
	}
	@Transient
	public Map<String,Integer> getMissContent100Map(){
		return (HashMap<String,Integer>)JSONObject.toBean(JSONObject.fromObject(this.getContentFor100()), HashMap.class);
	}
	
	/**
	 * @return the lottery
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = true)
	public Lottery getLottery() {
		return lottery;
	}

	/**
	 * @param lottery the lottery to set
	 */
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	/**
	 * @return the lastPeriodNumber
	 */
	@Column(nullable=false)
	public String getLastPeriodNumber() {
		return lastPeriodNumber;
	}

	/**
	 * @param lastPeriodNumber the lastPeriodNumber to set
	 */
	public void setLastPeriodNumber(String lastPeriodNumber) {
		this.lastPeriodNumber = lastPeriodNumber;
	}

	/**
	 * @return the lastPeriodId
	 */
	@Column(nullable=false)
	public Long getLastPeriodId() {
		return lastPeriodId;
	}

	/**
	 * @param lastPeriodId the lastPeriodId to set
	 */
	public void setLastPeriodId(Long lastPeriodId) {
		this.lastPeriodId = lastPeriodId;
	}

	/**
	 * @return the content
	 */
	@Column(nullable=false,length=2000)
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the version
	 */
	@Version
	@Column(nullable = false)
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the contentFor30
	 */
	@Column(nullable=false,length=2000)
	public String getContentFor30() {
		return contentFor30;
	}

	/**
	 * @param contentFor30 the contentFor30 to set
	 */
	public void setContentFor30(String contentFor30) {
		this.contentFor30 = contentFor30;
	}

	/**
	 * @return the contentFor50
	 */
	@Column(nullable=false,length=2000)
	public String getContentFor50() {
		return contentFor50;
	}

	/**
	 * @param contentFor50 the contentFor50 to set
	 */
	public void setContentFor50(String contentFor50) {
		this.contentFor50 = contentFor50;
	}

	/**
	 * @return the contentFor100
	 */
	@Column(nullable=false,length=2000)
	public String getContentFor100() {
		return contentFor100;
	}

	/**
	 * @param contentFor100 the contentFor100 to set
	 */
	public void setContentFor100(String contentFor100) {
		this.contentFor100 = contentFor100;
	}
	
}
