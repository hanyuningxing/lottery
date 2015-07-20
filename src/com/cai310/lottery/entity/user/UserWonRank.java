package com.cai310.lottery.entity.user;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.log4j.Category;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.support.dczc.PassMode;

/**
 * 用户中奖排行实体类
 * 
 * @author jack
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "USER_WON_RANK")
@Entity
public class UserWonRank extends IdEntity implements CreateMarkable, UpdateMarkable{

	private static final long serialVersionUID = 2636948146513833690L;

	public static final String TABLE_NAME = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "USER_WON_RANK";
	
	private User user;//用户
	private Lottery lottery;// 彩种
	private int ptOrdinal;			//玩法序列
	private BigDecimal cost;		// 统计认购金额(非持久化)
	private BigDecimal profit;		// 统计认购盈利(非持久化)
	private BigDecimal cost_7;		// 近7天认购金额
	private BigDecimal profit_7;	// 近7天认购盈利
	private BigDecimal cost_30;		// 近30天认购金额
	private BigDecimal profit_30;	// 近30天认购盈利
	private BigDecimal cost_90;		// 近90天认购金额
	private BigDecimal profit_90;	// 近90天认购盈利
	private boolean success;// 成功-流产
	private boolean fadan;// 发单-跟单
	private Integer rankFlag;//重新统计的标识
	private Date createTime;//创建时间
	private Date lastModifyTime;//最后更新时间

		
	@ManyToOne(targetEntity=User.class)  
    @JoinColumn(name="uid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = PassMode.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public Lottery getLottery() {
		return lottery;
	}
	
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	@Column
	public int getPtOrdinal() {
		return ptOrdinal;
	}

	public void setPtOrdinal(int ptOrdinal) {
		this.ptOrdinal = ptOrdinal;
	}

	@Transient
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Transient
	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	@Column
	public BigDecimal getCost_7() {
		return cost_7;
	}

	public void setCost_7(BigDecimal cost_7) {
		this.cost_7 = cost_7;
	}

	@Column
	public BigDecimal getProfit_7() {
		return profit_7;
	}

	public void setProfit_7(BigDecimal profit_7) {
		this.profit_7 = profit_7;
	}

	@Column
	public BigDecimal getCost_30() {
		return cost_30;
	}

	public void setCost_30(BigDecimal cost_30) {
		this.cost_30 = cost_30;
	}

	@Column
	public BigDecimal getProfit_30() {
		return profit_30;
	}

	public void setProfit_30(BigDecimal profit_30) {
		this.profit_30 = profit_30;
	}

	@Column
	public BigDecimal getCost_90() {
		return cost_90;
	}

	public void setCost_90(BigDecimal cost_90) {
		this.cost_90 = cost_90;
	}

	@Column
	public BigDecimal getProfit_90() {
		return profit_90;
	}

	public void setProfit_90(BigDecimal profit_90) {
		this.profit_90 = profit_90;
	}

	@Column
	public Integer getRankFlag() {
		return rankFlag;
	}

	public void setRankFlag(Integer rankFlag) {
		this.rankFlag = rankFlag;
	}

	@Column(nullable = false)
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Column(nullable = false)
	public boolean isFadan() {
		return fadan;
	}

	public void setFadan(boolean fadan) {
		this.fadan = fadan;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}
