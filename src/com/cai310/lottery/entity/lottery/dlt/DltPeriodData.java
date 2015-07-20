package com.cai310.lottery.entity.lottery.dlt;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.hibernate.annotations.Type;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.PeriodData;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "DLT_PERIOD_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DltPeriodData extends PeriodData {
	private static final long serialVersionUID = -6898819515323655567L;

	private Long prizePool; // 奖池金额
//	private String result; // 文本开奖结果
	private Long totalSales; // 销售总额
	private DltWinUnit winUnit = new DltWinUnit();
	private DltPrize prize = new DltPrize();
    ////追加信息
	private Integer firstAddWinUnits;
	private Integer secondAddWinUnits;
	private Integer thirdAddWinUnits;
	private Integer fourthAddWinUnits;
	private Integer fifthAddWinUnits;
	private Integer sixthAddWinUnits;
	private Integer seventhAddWinUnits;
	private Integer eighthAddWinUnits;
	private Integer select12to2AddWinUnits;
	
	private Integer firstAddPrize;
	private Integer secondAddPrize;
	private Integer thirdAddPrize;
	private Integer fourthAddPrize;
	private Integer fifthAddPrize;
	private Integer sixthAddPrize ;
	private Integer seventhAddPrize ;
	private Integer eighthAddPrize ;
	private Integer select12to2AddPrize ;
	
	
	private Boolean sunday;
	private Boolean holiday;

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
	public DltWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(DltWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new DltWinUnit();
		}
	}

	@Embedded
	public DltPrize getPrize() {
		return prize;
	}

	public void setPrize(DltPrize prize) {
		if (prize != null) {
			this.prize = prize;
		} else {
			this.prize = new DltPrize();
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
	 * @return the firstAddWinUnits
	 */
	@Column
	public Integer getFirstAddWinUnits() {
		return firstAddWinUnits;
	}

	/**
	 * @param firstAddWinUnits the firstAddWinUnits to set
	 */
	public void setFirstAddWinUnits(Integer firstAddWinUnits) {
		this.firstAddWinUnits = firstAddWinUnits;
	}

	/**
	 * @return the secondAddWinUnits
	 */
	@Column
	public Integer getSecondAddWinUnits() {
		return secondAddWinUnits;
	}

	/**
	 * @param secondAddWinUnits the secondAddWinUnits to set
	 */
	public void setSecondAddWinUnits(Integer secondAddWinUnits) {
		this.secondAddWinUnits = secondAddWinUnits;
	}

	/**
	 * @return the thirdAddWinUnits
	 */
	@Column
	public Integer getThirdAddWinUnits() {
		return thirdAddWinUnits;
	}

	/**
	 * @param thirdAddWinUnits the thirdAddWinUnits to set
	 */
	public void setThirdAddWinUnits(Integer thirdAddWinUnits) {
		this.thirdAddWinUnits = thirdAddWinUnits;
	}

	/**
	 * @return the fourthAddWinUnits
	 */
	@Column
	public Integer getFourthAddWinUnits() {
		return fourthAddWinUnits;
	}

	/**
	 * @param fourthAddWinUnits the fourthAddWinUnits to set
	 */
	public void setFourthAddWinUnits(Integer fourthAddWinUnits) {
		this.fourthAddWinUnits = fourthAddWinUnits;
	}

	/**
	 * @return the fifthAddWinUnits
	 */
	@Column
	public Integer getFifthAddWinUnits() {
		return fifthAddWinUnits;
	}

	/**
	 * @param fifthAddWinUnits the fifthAddWinUnits to set
	 */
	public void setFifthAddWinUnits(Integer fifthAddWinUnits) {
		this.fifthAddWinUnits = fifthAddWinUnits;
	}

	/**
	 * @return the sixthAddWinUnits
	 */
	@Column
	public Integer getSixthAddWinUnits() {
		return sixthAddWinUnits;
	}

	/**
	 * @param sixthAddWinUnits the sixthAddWinUnits to set
	 */
	public void setSixthAddWinUnits(Integer sixthAddWinUnits) {
		this.sixthAddWinUnits = sixthAddWinUnits;
	}

	/**
	 * @return the seventhAddWinUnits
	 */
	@Column
	public Integer getSeventhAddWinUnits() {
		return seventhAddWinUnits;
	}

	/**
	 * @param seventhAddWinUnits the seventhAddWinUnits to set
	 */
	public void setSeventhAddWinUnits(Integer seventhAddWinUnits) {
		this.seventhAddWinUnits = seventhAddWinUnits;
	}

	/**
	 * @return the eighthAddWinUnits
	 */
	@Column
	public Integer getEighthAddWinUnits() {
		return eighthAddWinUnits;
	}

	/**
	 * @param eighthAddWinUnits the eighthAddWinUnits to set
	 */
	public void setEighthAddWinUnits(Integer eighthAddWinUnits) {
		this.eighthAddWinUnits = eighthAddWinUnits;
	}

	/**
	 * @return the select12to2AddWinUnits
	 */
	@Column
	public Integer getSelect12to2AddWinUnits() {
		return select12to2AddWinUnits;
	}

	/**
	 * @param select12to2AddWinUnits the select12to2AddWinUnits to set
	 */
	public void setSelect12to2AddWinUnits(Integer select12to2AddWinUnits) {
		this.select12to2AddWinUnits = select12to2AddWinUnits;
	}

	/**
	 * @return the firstAddPrize
	 */
	
	@Column
	public Integer getFirstAddPrize() {
		return firstAddPrize;
	}

	/**
	 * @param firstAddPrize the firstAddPrize to set
	 */
	public void setFirstAddPrize(Integer firstAddPrize) {
		this.firstAddPrize = firstAddPrize;
	}

	/**
	 * @return the secondAddPrize
	 */
	
	@Column
	public Integer getSecondAddPrize() {
		return secondAddPrize;
	}

	/**
	 * @param secondAddPrize the secondAddPrize to set
	 */
	public void setSecondAddPrize(Integer secondAddPrize) {
		this.secondAddPrize = secondAddPrize;
	}

	/**
	 * @return the thirdAddPrize
	 */
	
	@Column
	public Integer getThirdAddPrize() {
		return thirdAddPrize;
	}

	/**
	 * @param thirdAddPrize the thirdAddPrize to set
	 */
	public void setThirdAddPrize(Integer thirdAddPrize) {
		this.thirdAddPrize = thirdAddPrize;
	}

	/**
	 * @return the fourthAddPrize
	 */
	
	@Column
	public Integer getFourthAddPrize() {
		if(null==fourthAddPrize){
			return 1500;
		}
		return fourthAddPrize;
	}

	/**
	 * @param fourthAddPrize the fourthAddPrize to set
	 */
	public void setFourthAddPrize(Integer fourthAddPrize) {
		this.fourthAddPrize = fourthAddPrize;
	}

	/**
	 * @return the fifthAddPrize
	 */
	
	@Column
	public Integer getFifthAddPrize() {
		if(null==fifthAddPrize){
			return 300;
		}
		return fifthAddPrize;
	}

	/**
	 * @param fifthAddPrize the fifthAddPrize to set
	 */
	public void setFifthAddPrize(Integer fifthAddPrize) {
		this.fifthAddPrize = fifthAddPrize;
	}

	/**
	 * @return the sixthAddPrize
	 */
	
	@Column
	public Integer getSixthAddPrize() {
		if(null==sixthAddPrize){
			return 50;
		}
		return sixthAddPrize;
	}

	/**
	 * @param sixthAddPrize the sixthAddPrize to set
	 */
	public void setSixthAddPrize(Integer sixthAddPrize) {
		this.sixthAddPrize = sixthAddPrize;
	}

	/**
	 * @return the seventhAddPrize
	 */
	
	@Column
	public Integer getSeventhAddPrize() {
		if(null==seventhAddPrize){
			return 5;
		}
		return seventhAddPrize;
	}

	/**
	 * @param seventhAddPrize the seventhAddPrize to set
	 */
	public void setSeventhAddPrize(Integer seventhAddPrize) {
		this.seventhAddPrize = seventhAddPrize;
	}

	/**
	 * @return the eighthAddPrize
	 */
	
	@Column
	public Integer getEighthAddPrize() {
		return eighthAddPrize;
	}

	/**
	 * @param eighthAddPrize the eighthAddPrize to set
	 */
	public void setEighthAddPrize(Integer eighthAddPrize) {
		this.eighthAddPrize = eighthAddPrize;
	}

	/**
	 * @return the select12to2AddPrize
	 */
	
	@Column
	public Integer getSelect12to2AddPrize() {
		return select12to2AddPrize;
	}

	/**
	 * @param select12to2AddPrize the select12to2AddPrize to set
	 */
	public void setSelect12to2AddPrize(Integer select12to2AddPrize) {
		this.select12to2AddPrize = select12to2AddPrize;
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
	/**
	 * @return 开奖号码号码
	 */
	@Transient
	public String getIndexPrizePool() {
	    final NumberFormat poolFormat = new DecimalFormat("0000000000");
		if (prizePool == null) {
			return null;
		}
		String prizePoolSrt = poolFormat.format(prizePool);
		
		char[] input = prizePoolSrt.toCharArray();
		StringBuffer out = new StringBuffer();
		out.append("<li>"+input[0]+"</li>");
		out.append("<li>"+input[1]+"</li>");
		out.append("<li></li>");
		out.append("<li style=\" padding-left:3px;\">"+input[2]+"</li>");
		out.append("<li>"+input[3]+"</li>");
		out.append("<li>"+input[4]+"</li>");
		out.append("<li>"+input[5]+"</li>");
		out.append("<li></li>");
		out.append("<li style=\" padding-left:3px;\">"+input[6]+"</li>");
		out.append("<li>"+input[7]+"</li>");
		out.append("<li>"+input[8]+"</li>");
		out.append("<li>"+input[9]+"</li>");
		return out.toString();
	 }

}