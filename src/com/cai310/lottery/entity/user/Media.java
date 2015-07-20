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

/**
 * 媒体信息
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "MEDIA")
public class Media extends IdEntity implements Serializable {
	private static final long serialVersionUID = 5895486389359222746L;
	/** 名称 */
	private String name;
	/** 联系方式 */
	private String contact;
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
    
}
