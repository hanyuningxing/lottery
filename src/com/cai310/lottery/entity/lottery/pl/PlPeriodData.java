package com.cai310.lottery.entity.lottery.pl;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.PeriodData;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "PL_PERIOD_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlPeriodData extends PeriodData {

//	private String result; // 文本开奖结果
	private Long totalSales; // 销售总额
	private Long p5TotalSales; // 销售总额
	private PlWinUnit winUnit = new PlWinUnit();
	private PlPrize prize = new PlPrize();

//	@Column(name = "result", length = 50)
//	public String getResult() {
//		return this.result;
//	}
//
//	public void setResult(String result) {
//		this.result = result;
//	}

	@Column(name = "total_sales")
	public Long getTotalSales() {
		return this.totalSales;
	}

	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}
	@Column(name = "p5_total_sales")
	public Long getP5TotalSales() {
		return this.p5TotalSales;
	}

	public void setP5TotalSales(Long p5TotalSales) {
		this.p5TotalSales = p5TotalSales;
	}

	@Embedded
	public PlWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(PlWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new PlWinUnit();
		}
	}

	@Embedded
	public PlPrize getPrize() {
		return prize;
	}

	public void setPrize(PlPrize prize) {
		if (this.prize != null) {
			this.prize = prize;
		} else {
			this.prize = new PlPrize();
		}
	}
	@Transient
	public String getResultFormat(){
		  try {
		     if(StringUtils.isBlank(this.getResult()))return null;
		     if(this.getResult().indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
		    	 String[] resultArr=this.getResult().split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		    	 Integer number;
		    	 StringBuffer sb = new StringBuffer();
		    	 for (String result : resultArr) {
		    		  number = Integer.parseInt(result);
		    		  sb.append(number+Constant.RESULT_SEPARATOR_FOR_NUMBER);
				 }
		    	 sb.delete(sb.length()-Constant.RESULT_SEPARATOR_FOR_NUMBER.length(), sb.length());
		    	 return sb.toString();
		     }
		     return null;
		 } catch (Exception e) {
	    	 e.printStackTrace();
	    	 return null;
		}
	}
	/**
	 * @return 开奖号码号码
	 */
	@Transient
	public String[] getRsultArr() {
		if (result == null) {
			return null;
		}
		String[] resultArr=null;
		if(result.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
			String[] resultStrArr = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			resultArr = new String[resultStrArr.length];
			Integer num;
			for (int i = 0; i < resultStrArr.length; i++) {
				num = Integer.valueOf(resultStrArr[i]);
				resultArr[i] = ""+num;
			}
		}
		return resultArr;
	 }

}