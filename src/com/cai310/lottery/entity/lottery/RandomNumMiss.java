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

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "RANDOM_MISS")
@Entity
public class RandomNumMiss extends IdEntity implements CreateMarkable, UpdateMarkable {

	private static final long serialVersionUID = 6900137269857618L;

	/** 彩种类型 **/
	private Lottery lottery;
	/** 期号 **/
	private String lastPeriodNumber;
	
	private String beginPeriodNumber;
	
	/** 期编号 **/
	private Long lastPeriodId;
	
	private String keyWord;
	
	private String content;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	/** 版本号 **/
	private Integer version;
	
	private Integer totalPeriod;
	
	private String content100;
	
	private String content200;
	
	private String content500;
	
	private String content1000;

	/**
	 * 转换content内容为Map
	 */
	@Transient
	public Map<String, HashMap<String,Long>> getMissDataMap() {
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("[\\d|\\s]+", HashMap.class);
		return (HashMap<String, HashMap<String,Long>>) JSONObject.toBean(JSONObject.fromObject(this.getContent()), HashMap.class,map);
	}
	@Transient
	public Map<String, HashMap<String,Long>> getMissData100Map() {
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("[\\d|\\s]+", HashMap.class);
		return (HashMap<String, HashMap<String,Long>>) JSONObject.toBean(JSONObject.fromObject(this.getContent100()), HashMap.class,map);
	}
	@Transient
	public Map<String, HashMap<String,Long>> getMissData200Map() {
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("[\\d|\\s]+", HashMap.class);
		return (HashMap<String, HashMap<String,Long>>) JSONObject.toBean(JSONObject.fromObject(this.getContent200()), HashMap.class,map);
	}
	@Transient
	public Map<String, HashMap<String,Long>> getMissData500Map() {
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("[\\d|\\s]+", HashMap.class);
		return (HashMap<String, HashMap<String,Long>>) JSONObject.toBean(JSONObject.fromObject(this.getContent500()), HashMap.class,map);
	}
	@Transient
	public Map<String, HashMap<String,Long>> getMissData1000Map() {
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("[\\d|\\s]+", HashMap.class);
		return (HashMap<String, HashMap<String,Long>>) JSONObject.toBean(JSONObject.fromObject(this.getContent1000()), HashMap.class,map);
	}
	
	public static void main(String[] args) {
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("[\\d|\\s]+", HashMap.class);
		String str="{\"08 09\":{\"bp\":2011041301,\"ls\":112,\"s\":40,\"mb\":2011041301,\"ep\":2011053008,\"me\":2011053008,\"mx\":715,\"cs\":715},\"05 10\":{\"bp\":2011041301,\"ls\":3,\"s\":51,\"mb\":2011041301,\"ep\":2011053008,\"me\":2011053008,\"mx\":729,\"cs\":729}}";
		HashMap<String, HashMap<String,Long>> re=(HashMap<String, HashMap<String,Long>>) JSONObject.toBean(JSONObject.fromObject(str),HashMap.class,map);
		for (Map.Entry<String, HashMap<String, Long>> entry : re.entrySet()) {
			Long l=Long.valueOf(entry.getValue().get("ls")+0);
			entry.getValue().put("ls",l);
			System.out.println(entry.getValue().get("ls"));
		}
	}
	
//	public static void main(String[] args) {
//		String str="{\"123\":{\"c\":1,\"b\":2},\"234\":{\"c\":1,\"b\":2}}";
//		HashMap map=(HashMap)JSONObject.toBean(JSONObject.fromObject(str), HashMap.class);
//		System.out.println(map);
//	}

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
	@Column
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Column
	public Integer getTotalPeriod() {
		return totalPeriod;
	}

	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
	@Lob
	@Column
	public String getContent100() {
		return content100;
	}

	public void setContent100(String content100) {
		this.content100 = content100;
	}
	@Lob
	@Column
	public String getContent200() {
		return content200;
	}

	public void setContent200(String content200) {
		this.content200 = content200;
	}
	@Lob
	@Column
	public String getContent500() {
		return content500;
	}

	public void setContent500(String content500) {
		this.content500 = content500;
	}
	@Lob
	@Column
	public String getContent1000() {
		return content1000;
	}

	public void setContent1000(String content1000) {
		this.content1000 = content1000;
	}
	/**
	 * @return the beginPeriodNumber
	 */
	@Column
	public String getBeginPeriodNumber() {
		return beginPeriodNumber;
	}
	/**
	 * @param beginPeriodNumber the beginPeriodNumber to set
	 */
	public void setBeginPeriodNumber(String beginPeriodNumber) {
		this.beginPeriodNumber = beginPeriodNumber;
	}
}
