package com.cai310.lottery.entity.info;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.MobileInfoType;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "MOBILE_NEWS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MobileNewsData extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 4755138110633824016L;
	private MobileInfoType mobileInfoType;
	//公告标题
	private String title;
	
	private InfoState state;
	// /创建时间
	private Date createTime;
	// /修改时间
	private Date lastModifyTime;
	//内容
	private String lastContent;
	//内容
	private String content;
	//单式推介
	private String singleAnalyse;
	//复式推介
	private String analyse;
	
	private Long periodId;
	
	private Integer lotteryPlayType;//0=pl3 1= pl5
	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;
	@Column(nullable = false, length = 50)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public InfoState getState() {
		return state;
	}

	public void setState(InfoState state) {
		this.state = state;
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
	@Lob
	@Column(nullable = true)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	
	
	
	@Column(length=200)
	public String getSingleAnalyse() {
		return singleAnalyse;
	}

	public void setSingleAnalyse(String singleAnalyse) {
		this.singleAnalyse = singleAnalyse;
	}
	@Column(length=200)
	public String getAnalyse() {
		return analyse;
	}

	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}
	@Lob
	@Column(nullable = true)
	public String getLastContent() {
		return lastContent;
	}

	public void setLastContent(String lastContent) {
		this.lastContent = lastContent;
	}
	@Column(nullable = false)
	public MobileInfoType getMobileInfoType() {
		return mobileInfoType;
	}

	public void setMobileInfoType(MobileInfoType mobileInfoType) {
		this.mobileInfoType = mobileInfoType;
	}
	@Column
	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	@Column
	public Integer getLotteryPlayType() {
		return lotteryPlayType;
	}

	public void setLotteryPlayType(Integer lotteryPlayType) {
		this.lotteryPlayType = lotteryPlayType;
	}
	
}
