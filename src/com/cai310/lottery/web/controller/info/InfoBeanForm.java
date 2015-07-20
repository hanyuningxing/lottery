package com.cai310.lottery.web.controller.info;

import java.util.Date;

import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoTitleColor;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.MobileInfoType;

public class InfoBeanForm {
	private Long id;
	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;
	
	/** 内容 **/
	private String content;
	
	/** 标题 **/
	private String title;
	
	/** 短标题 **/
	private String shortTitle;
	
	/** 作者 **/
	private String author;
	
	/** 标题链接 **/
	private String titleLink;
	
	public void setTitleLink(String titleLink) {
		this.titleLink = titleLink;
	}

	/** 类型 **/
	private InfoType type;

	/** 状态 **/
	private InfoState state;
	
	/** 是否为公告 **/
	private Integer isNotice;
	//过期时间
	private Date expireDate;
	//优先级
	private int orderNo;
	
	private MobileInfoType mobileInfoType;
	   //上期推介
		private String lastContent;
		//单式推介
		private String singleAnalyse;
		//复式推介
		private String analyse;
		private Long periodId;
		
		private Integer lotteryPlayType;//0=pl3 1= pl5
	public Integer getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(Integer isNotice) {
		this.isNotice = isNotice;
	}

	/** 类型 **/
	private Integer level;
	
	/** 类型 **/
	private Long clickNum;
	
	/** 标题类型 **/
	private String titleType;

	/** 标题类型链接 **/
	private String titleTypeLink;

	///创建时间
	private Date createTime;
	
	///修改时间
	private Date lastModifyTime;
	
	private String keywords;
	
	private String description;
	
	private InfoTitleColor titleColor;
	
    /** 子类型 可空 **/
	
	private InfoSubType subType;
	//时间过滤类型 分为按一天，一周，一月
	private String fiterType;
	
	private String tags;
	
	private String tags1;
	private String tags2;
	private String tags3;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the titleLink
	 */
	public String getTitleLink() {
		return titleLink;
	}

	/**
	 * @param titleLink the titleLink to set
	 */
	public void ShortTitle(String titleLink) {
		this.titleLink = titleLink;
	}

	/**
	 * @return the type
	 */
	public InfoType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(InfoType type) {
		this.type = type;
	}

	/**
	 * @return the state
	 */
	public InfoState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(InfoState state) {
		this.state = state;
	}

	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * @return the titleType
	 */
	public String getTitleType() {
		return titleType;
	}

	/**
	 * @param titleType the titleType to set
	 */
	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	/**
	 * @return the titleTypeLink
	 */
	public String getTitleTypeLink() {
		return titleTypeLink;
	}

	/**
	 * @param titleTypeLink the titleTypeLink to set
	 */
	public void setTitleTypeLink(String titleTypeLink) {
		this.titleTypeLink = titleTypeLink;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastModifyTime
	 */
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	/**
	 * @param lastModifyTime the lastModifyTime to set
	 */
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getClickNum() {
		return clickNum;
	}

	public void setClickNum(Long clickNum) {
		this.clickNum = clickNum;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public InfoTitleColor getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(InfoTitleColor titleColor) {
		this.titleColor = titleColor;
	}

	public InfoSubType getSubType() {
		return subType;
	}

	public void setSubType(InfoSubType subType) {
		this.subType = subType;
	}
	
	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getFiterType() {
		return fiterType;
	}

	public void setFiterType(String fiterType) {
		this.fiterType = fiterType;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTags1() {
		return tags1;
	}

	public void setTags1(String tags1) {
		this.tags1 = tags1;
	}

	public String getTags2() {
		return tags2;
	}

	public void setTags2(String tags2) {
		this.tags2 = tags2;
	}

	public String getTags3() {
		return tags3;
	}

	public void setTags3(String tags3) {
		this.tags3 = tags3;
	}

	public MobileInfoType getMobileInfoType() {
		return mobileInfoType;
	}

	public void setMobileInfoType(MobileInfoType mobileInfoType) {
		this.mobileInfoType = mobileInfoType;
	}

	public String getLastContent() {
		return lastContent;
	}

	public void setLastContent(String lastContent) {
		this.lastContent = lastContent;
	}

	public String getSingleAnalyse() {
		return singleAnalyse;
	}

	public void setSingleAnalyse(String singleAnalyse) {
		this.singleAnalyse = singleAnalyse;
	}

	public String getAnalyse() {
		return analyse;
	}

	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public Integer getLotteryPlayType() {
		return lotteryPlayType;
	}

	public void setLotteryPlayType(Integer lotteryPlayType) {
		this.lotteryPlayType = lotteryPlayType;
	}

	

	
	
}
