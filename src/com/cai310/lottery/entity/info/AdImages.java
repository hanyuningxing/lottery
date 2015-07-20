package com.cai310.lottery.entity.info;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AD_IMAGES")
public class AdImages extends IdEntity implements CreateMarkable,UpdateMarkable {

	private static final long serialVersionUID = -6380516365102466395L;
	
	private Integer pos;

	private String name;
	
	private String word;

	private String link;

	private Date lastModifyTime;

	private Date createTime;

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


	@Column
	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}


	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}


	@Column
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	

}
