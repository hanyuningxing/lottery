package com.cai310.lottery.entity.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.MessageType;
import com.cai310.lottery.common.PopuType;

/**
 *  随机手机发送验证码
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "RANDOM_MESSAGE")
public class RandomMessage extends IdEntity implements CreateMarkable , UpdateMarkable , Serializable {
	private static final long serialVersionUID = 5895486389359222746L;
	/** 用户id */
	private Long userId;
	
	private String userName;
	/** 电话号码 */
	private String mobile;
	/** 随机号 */
	private Integer randomWord;
	/** 类型 */
	private MessageType messageType;
	/** 是否确认了随机号 */
	private Boolean enable = Boolean.FALSE;
	/** 创建时间 */
	private Date createTime;
	/** 最后更新时间 */
	private Date lastModifyTime;
	public RandomMessage(){
		
	}
	public RandomMessage(User user,String mobile,Integer randomWord,MessageType messageType){
		if(null!=user){
			this.userId=user.getId();
			this.userName=user.getUserName();
		}
		this.mobile = mobile;
		this.randomWord = randomWord;
		this.messageType = messageType;
	}
	/**
	 * @return the createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	@Column
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column
	public Integer getRandomWord() {
		return randomWord;
	}

	public void setRandomWord(Integer randomWord) {
		this.randomWord = randomWord;
	}
	@Column
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	@Column
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
    
}
