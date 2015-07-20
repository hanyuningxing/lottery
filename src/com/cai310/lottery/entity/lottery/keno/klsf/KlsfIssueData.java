package com.cai310.lottery.entity.lottery.keno.klsf;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX +"KLSF_ISSUE_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KlsfIssueData extends KenoPeriod {

	private static final long serialVersionUID = 2403929707663114183L;
	@Transient
	public String getResultFormat(){
		  try {
		     if(StringUtils.isBlank(this.getResults()))return null;
		     if(this.getResults().indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
		    	 String[] resultArr=this.getResults().split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
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

	@Override
	@Transient
	public Lottery getLotteryType() {
		return Lottery.SSC;
	}
}
