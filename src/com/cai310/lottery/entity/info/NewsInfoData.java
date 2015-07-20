package com.cai310.lottery.entity.info;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoTitleColor;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "NEWS_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NewsInfoData extends IdEntity implements CreateMarkable, UpdateMarkable {
	/** 是否为公告 **/
	public static final Integer NOTICE = 1;
	/** 是否为彩种首页公告 **/
	public static final Integer LotteryNOTICE = 2;
	
	private static final long serialVersionUID = 4755138110633824016L;
	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;
	

	/** 是否为公告  首页显示**/
	private Integer isNotice; 
	
	/** 内容 **/
	private String content;

	/** 标题 **/
	private String title;
	
	private String shortTitle;

	private String keywords;
	
	private String description;

	/** 作者 **/
	private String author;

	/** 标题链接 **/
	private String titleLink;

	/** 类型 **/
	private InfoType type;
	
	/** 子类型 可空 **/
	
	private InfoSubType subType;

	/** 状态 **/
	private InfoState state;
	
	/** 标题颜色 **/
	private InfoTitleColor titleColor;

	/** 级别首页排序 **/
	private Integer level;

	/** 类型 **/
	private Long clickNum;

	/** 标题类型 **/
	private String titleType;

	/** 标题类型链接 **/
	private String titleTypeLink;

	// /创建时间
	private Date createTime;

	// /修改时间
	private Date lastModifyTime;
	//标签
	private String tags;
	
	/**
	 * @return the content
	 */
	@Lob
	@Column(nullable = true)
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
	 * @return the type
	 */
	@Column(nullable = false)
	public InfoType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(InfoType type) {
		this.type = type;
	}
	@Column(nullable = true)
	public InfoSubType getSubType() {
		return subType;
	}

	public void setSubType(InfoSubType subType) {
		this.subType = subType;
	}
	/**
	 * @return the title
	 */
	@Column(nullable = false, length = 500)
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(nullable = false, length = 500)
	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	@Column
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;

	} 

	/**
	 * @return the titleLink
	 */
	@Column(length = 500)
	public String getTitleLink() {
		return titleLink;
	}

	/**
	 * @param titleLink the titleLink to set
	 */
	public void setTitleLink(String titleLink) {
		this.titleLink = titleLink;
	}

	/**
	 * @return the state
	 */
	@Column(nullable = false)
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
	@Column(name = "info_level",nullable = false)
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
	@Column(length = 200)
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
	@Column(length = 200)
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
	 * @return {@link #lotteryType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = true)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the {@link #lotteryType} to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	@Column(nullable = false, length = 100)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(nullable = false)
	public Long getClickNum() {
		return clickNum;
	}

	public void setClickNum(Long clickNum) {
		this.clickNum = clickNum;
	}

	@Column(length = 500)
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	@Column(length = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the state
	 */
	@Column(nullable = true)
	public InfoTitleColor getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(InfoTitleColor titleColor) {
		this.titleColor = titleColor;
	}
	@Transient
	public String getTitleString() {
		if(StringUtils.isNotBlank(this.getTitle())){
			return getTitleStringMethod(this.getTitle(),36);
		}
		return "";
	}
	@Transient
	public String getTitleString(Integer limit) {
		if(StringUtils.isNotBlank(this.getTitle())){
			return getTitleStringMethod(this.getTitle(),limit);
		}
		return "";
	}
	@Transient
	public String getLimitContent(Integer limit) {
		String value = this.content;
		int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        StringBuffer sb = new StringBuffer();
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
                if(valueLength<=limit){
                	sb.append(temp);
                }else{
                	break;
                }
            }
        }
        return sb.toString();
	}
	 /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * 
     * @param value
     *            指定的字符串
     * @return 字符串的长度
     */
	@Transient
    public  String getTitleStringMethod(String value,Integer limit) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        StringBuffer sb = new StringBuffer();
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
            if(valueLength<=limit){
            	sb.append(temp);
            }else{
            	sb.append("...");
            	break;
            }
        }
        return sb.toString();
    }
	@Transient
    public String getFirstImgSrc(){
    	try{
    		 String regex = "<\\s*img\\s+[^>]*src=\"([^\"]+)\"[^>]*\\s*>";
	    	 Pattern p = Pattern.compile(regex);
	    	 Matcher m = p.matcher(this.content);
	    	 if(m.find()){
	    		 String src=m.group(1);
	    		 return src;
	    	 }
    	}catch(Exception e){
    		System.out.print(e);
    	}
    	return null;
    	 
    }
	public Integer getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(Integer isNotice) {
		this.isNotice = isNotice;
	}
	
	@Column(length = 200)
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	
}
