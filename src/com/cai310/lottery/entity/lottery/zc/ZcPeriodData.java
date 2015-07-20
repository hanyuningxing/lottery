package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.cai310.lottery.entity.lottery.PeriodData;

/**
 * 足彩期数据实体基类
 * 
 * 
 * 
 */
@MappedSuperclass
public abstract class ZcPeriodData extends PeriodData {
	private static final long serialVersionUID = -6898819515323655567L;

	protected Long totalSales; // 总销售额
	protected Integer firstPrize; // 一等奖奖金
	protected int firstWinUnits;// 一等奖注数
	protected Integer secondPrize; // 二等奖奖金
	protected int secondWinUnits;// 二等奖注数
	protected Long prizePool; // 奖池金额
//	protected String result; // 文本开奖结果
    
	@Column(name = "prizePool")
	public Long getPrizePool() {
		return this.prizePool;
	}

	public void setPrizePool(Long prizePool) {
		this.prizePool = prizePool;
	}

//	@Column(name = "result", length = 30)
//	public String getResult() {
//		return this.result;
//	}
//
//	public void setResult(String result) {
//		this.result = result;
//	}

	/**
	 * @return 验证开奖结果合法性
	 */
	@Transient
	public boolean validateResult() {
		if (!this.result.contains("X")) {
			return true;
		}
		return false;
	}

	@Column(name = "totalSales")
	public Long getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}

	
	@Column(name = "firstPrize")
	public Integer getFirstPrize() {
		return firstPrize;
	}

	public void setFirstPrize(Integer firstPrize) {
		this.firstPrize = firstPrize;
	}

	@Column(name = "firstWinUnits")
	public int getFirstWinUnits() {
		return firstWinUnits;
	}

	public void setFirstWinUnits(int firstWinUnits) {
		this.firstWinUnits = firstWinUnits;
	}

	
	@Column(name = "secondPrize")
	public Integer getSecondPrize() {
		return secondPrize;
	}

	public void setSecondPrize(Integer secondPrize) {
		this.secondPrize = secondPrize;
	}

	@Column(name = "secondWinUnits")
	public int getSecondWinUnits() {
		return secondWinUnits;
	}

	public void setSecondWinUnits(int secondWinUnits) {
		this.secondWinUnits = secondWinUnits;
	}
    ////足彩开奖结果
	@Transient
	public String getRsultString() {
		if (result == null) {
			return null;
		}
		char ch;
		char[] input = result.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer();
	    for (int i = 0; i < len; i++) {
			ch = input[i];
	    	out.append(ch);
	    	out.append("&nbsp;");
    	}
	    return out.toString();
	 }
	 ////足彩开奖结果
	 @Transient
	 public String getRsultSpiltString() {
		if (result == null) {
			return null;
		}
		char ch;
		char[] input = result.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer();
	    for (int i = 0; i < len; i++) {
			ch = input[i];
	    	out.append(ch);
	    	if(len-i>1){
		    	out.append(",");
	    	}
    	}
	    return out.toString();
	 }
}