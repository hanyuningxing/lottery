package com.cai310.lottery.entity.lottery.seven;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.PeriodData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SEVEN_PERIOD_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SevenPeriodData extends PeriodData {
	private Long prizePool; // 奖池金额
	private Long totalSales; // 销售总额
	private SevenWinUnit winUnit = new SevenWinUnit();
	private SevenPrize prize = new SevenPrize();
	
	@Column(name = "total_sales")
	public Long getTotalSales() {
		return this.totalSales;
	}

	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}
	
	@Column(name = "prizePool")
	public Long getPrizePool() {
		return this.prizePool;
	}

	public void setPrizePool(Long prizePool) {
		this.prizePool = prizePool;
	}
	@Embedded
	public SevenWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(SevenWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new SevenWinUnit();
		}
	}

	@Embedded
	public SevenPrize getPrize() {
		return prize;
	}

	public void setPrize(SevenPrize prize) {
		if (this.prize != null) {
			this.prize = prize;
		} else {
			this.prize = new SevenPrize();
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
	public  String[] getRsultArr() {
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
				if(num<10){
					resultArr[i] = "0"+num;
				}else{
					resultArr[i] = ""+num;
				}
			}
		}
		return resultArr;
	 }
}