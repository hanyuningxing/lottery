package com.cai310.lottery.entity.lottery.tc22to5;

import java.util.Set;
import java.util.TreeSet;

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
import com.cai310.lottery.support.tc22to5.Tc22to5Support;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TC22TO5_PERIOD_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tc22to5PeriodData extends PeriodData {
	

	private Long prizePool; // 奖池金额
	private Long totalSales; // 销售总额
	private Tc22to5WinUnit winUnit = new Tc22to5WinUnit();
	private Tc22to5Prize prize = new Tc22to5Prize();

	public Tc22to5PeriodData() {
		this.prize.setSecondPrize(Tc22to5Support.SecondPrize);
		this.prize.setThirdPrize(Tc22to5Support.ThirdPrize);
	}

	@Column(name = "prizePool")
	public Long getPrizePool() {
		return this.prizePool;
	}

	public void setPrizePool(Long prizePool) {
		this.prizePool = prizePool;
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
		    		  if(number<10){
		    			  sb.append("0"+number+Constant.RESULT_SEPARATOR_FOR_NUMBER);
		    		  }else{
		    			  sb.append(number+Constant.RESULT_SEPARATOR_FOR_NUMBER);
		    		  }
		    		 
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
	 * @return 开奖号码
	 */
	@Transient
	public Set<Integer> getResults() {
		if (StringUtils.isBlank(this.getResult())) {
			return null;
		}
		String[] resultArr = this.getResult().split(",");
		Set<Integer> results = new TreeSet<Integer>();
		for (String rs : resultArr) {
			results.add(Integer.valueOf(rs));
		}
		return results;
	}

	@Embedded
	public Tc22to5WinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(Tc22to5WinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new Tc22to5WinUnit();
		}
	}

	@Embedded
	public Tc22to5Prize getPrize() {
		return prize;
	}

	public void setPrize(Tc22to5Prize prize) {
		if (prize != null) {
			this.prize = prize;
		} else {
			this.prize = new Tc22to5Prize();
		}
	}

	@Column(name = "totalSales")
	public Long getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
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