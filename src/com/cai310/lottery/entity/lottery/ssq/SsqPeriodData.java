package com.cai310.lottery.entity.lottery.ssq;

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
import com.cai310.lottery.support.ssq.SsqSupport;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SSQ_PERIOD_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SsqPeriodData extends PeriodData {
	private static final long serialVersionUID = -6898819515323655567L;

	private Long prizePool; // 奖池金额
	private Long totalSales; // 销售总额
	private SsqWinUnit winUnit = new SsqWinUnit();
	private SsqPrize prize = new SsqPrize();

	private Boolean sunday;
	private Boolean holiday;

	public SsqPeriodData() {
		this.prize.setThirdPrize(SsqSupport.ThirdPrize);
		this.prize.setFourthPrize(SsqSupport.FourthPrize);
		this.prize.setFifthPrize(SsqSupport.FifthPrize);
		this.prize.setSixthPrize(SsqSupport.SixthPrize);
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
	 * @return 开奖号码的基本号码
	 */
	@Transient
	public Set<Integer> getCommonResults() {
		if (StringUtils.isBlank(this.getResult())) {
			return null;
		}
		String[] resultArr = this.getResult().split("_");
		String[] commonResultArr = resultArr[0].split(",");
		Set<Integer> commonResults = new TreeSet<Integer>();
		for (String rs : commonResultArr) {
			commonResults.add(Integer.valueOf(rs));
		}
		return commonResults;
	}

	@Embedded
	public SsqWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(SsqWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new SsqWinUnit();
		}
	}

	@Embedded
	public SsqPrize getPrize() {
		return prize;
	}

	public void setPrize(SsqPrize prize) {
		if (prize != null) {
			this.prize = prize;
		} else {
			this.prize = new SsqPrize();
		}
	}

	@Column(name = "totalSales")
	public Long getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}

	@Column(name = "sunday")
	public Boolean getSunday() {
		return sunday;
	}

	public void setSunday(Boolean sunday) {
		this.sunday = sunday;
	}

	@Column(name = "holiday")
	public Boolean getHoliday() {
		return holiday;
	}

	public void setHoliday(Boolean holiday) {
		this.holiday = holiday;
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