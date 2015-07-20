package com.cai310.lottery.entity.lottery;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
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

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "GROUP_MISS")
@Entity
public class GroupNumMiss extends IdEntity implements CreateMarkable, UpdateMarkable {

	private static final long serialVersionUID = 69000137269857618L;

	/** 彩种类型 **/
	private Lottery lottery;
	/** 期号 **/
	private String lastPeriodNumber;
	/** 期编号 **/
	private Long lastPeriodId;

	/** 存放组三号码遗漏数据,json格式 **/
	private String g3Content;

	/** 存放组三号码遗漏数据,json格式 **/
	private String g6Content;
	/** 组三历史最大遗漏 **/
	private String g3MaxMissContent;
	/** 组六历史最大遗漏 **/
	private String g6MaxMissContent;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	/** 版本号 **/
	private Integer version;

	/**
	 * 转换content内容为Map
	 */
	@Transient
	public Map<String, Integer> getG3MissDataMap() {
		return (HashMap<String, Integer>) JSONObject.toBean(JSONObject.fromObject(this.getG3Content()), HashMap.class);
	}

	@Transient
	public Map<String, Integer> getG6MissDataMap() {
		return (HashMap<String, Integer>) JSONObject.toBean(JSONObject.fromObject(this.getG6Content()), HashMap.class);
	}

	@Transient
	public Map<String, Integer> getG6MaxMissDataMap() {
		return (HashMap<String, Integer>) JSONObject.toBean(JSONObject.fromObject(this.getG6MaxMissContent()),
				HashMap.class);
	}

	@Transient
	public Map<String, Integer> getG3MaxMissDataMap() {
		return (HashMap<String, Integer>) JSONObject.toBean(JSONObject.fromObject(this.getG3MaxMissContent()),
				HashMap.class);
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
	@Column(nullable = false)
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
	@Column(nullable = false)
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
	 * @return the g3Content
	 */
	@Lob
	@Column(nullable = false)
	public String getG3Content() {
		return g3Content;
	}

	/**
	 * @param g3Content the g3Content to set
	 */
	public void setG3Content(String g3Content) {
		this.g3Content = g3Content;
	}

	/**
	 * @return the g6Content
	 */
	@Lob
	@Column(nullable = false)
	public String getG6Content() {
		return g6Content;
	}

	/**
	 * @param g6Content the g6Content to set
	 */
	public void setG6Content(String g6Content) {
		this.g6Content = g6Content;
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

	@Lob
	@Column(nullable = false)
	public String getG3MaxMissContent() {
		return g3MaxMissContent;
	}

	public void setG3MaxMissContent(String g3MaxMissContent) {
		this.g3MaxMissContent = g3MaxMissContent;
	}

	@Lob
	@Column(nullable = false)
	public String getG6MaxMissContent() {
		return g6MaxMissContent;
	}

	public void setG6MaxMissContent(String g6MaxMissContent) {
		this.g6MaxMissContent = g6MaxMissContent;
	}
}
