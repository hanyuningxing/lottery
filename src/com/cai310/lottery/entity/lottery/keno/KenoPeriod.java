package com.cai310.lottery.entity.lottery.keno;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.exception.DataException;

@MappedSuperclass
public abstract class KenoPeriod extends IdEntity implements CreateMarkable, UpdateMarkable {

	private static final long serialVersionUID = 6228896050265028503L;
	

	/** 限号 */
	protected String limitNum;
	
	/** 开奖结果 */
	protected String results;/** 彩种类型 */

	/** 期号 */
	protected String periodNumber;

	/**
	 * 期状态
	 * 
	 * @see com.cai310.lottery.common.PeriodState
	 * 
	 */
	protected IssueState state;

	/** 上一期ID */
	protected Long prevPreriodId;

	/** 下一期ID */
	protected Long nextPreriodId;

	/** 期截止时间 */
	protected Date endedTime;

	/** 开奖时间 */
	protected Date prizeTime;

	/** 期开始时间 */
	protected Date startTime;

	/** 备注 */
	protected String remark;

	/** 版本号 */
	protected Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	/** 期号前台显示 ，非数据库字段*/
	protected Integer periodNumberDisplay;
	
	/** 该期总方案数 */
	private Integer allSchemeCount;
	/** 该期总成功方案数 */
	private Integer allSuccessSchemeCount;
	/** 该期总销量 */
	private Integer allSale;
	/** 该期总中奖数 */
	private Integer allPrize;

	/* ---------------------- getter and setter method ---------------------- */

	/**
	 * @return the periodNumber
	 */
	@Column(length = 15, nullable = false)
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the periodNumber to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return the state
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.keno.IssueState"),
			@Parameter(name = EnumType.TYPE, value = PeriodState.SQL_TYPE) })
	@Column(nullable = false)
	public IssueState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(IssueState state) {
		this.state = state;
	}

	/**
	 * @return the prevPreriodId
	 */
	@Column
	public Long getPrevPreriodId() {
		return prevPreriodId;
	}

	/**
	 * @param prevPreriodId the prevPreriodId to set
	 */
	public void setPrevPreriodId(Long prevPreriodId) {
		this.prevPreriodId = prevPreriodId;
	}

	/**
	 * @return the nextPreriodId
	 */
	@Column
	public Long getNextPreriodId() {
		return nextPreriodId;
	}

	/**
	 * @param nextPreriodId the nextPreriodId to set
	 */
	public void setNextPreriodId(Long nextPreriodId) {
		this.nextPreriodId = nextPreriodId;
	}

	/**
	 * @return the endedTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getEndedTime() {
		return endedTime;
	}

	/**
	 * @param endedTime the endedTime to set
	 */
	public void setEndedTime(Date endedTime) {
		this.endedTime = endedTime;
	}

	/**
	 * @return the prizeTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getPrizeTime() {
		return prizeTime;
	}

	/**
	 * @param prizeTime the prizeTime to set
	 */
	public void setPrizeTime(Date prizeTime) {
		this.prizeTime = prizeTime;
	}

	/**
	 * @return the startTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the remark
	 */
	@Column(length = 200)
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the version
	 */
	@Version
	@Column(nullable = false)
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

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

	/**
	 * 彩种类型
	 * 
	 * @return 彩种类型
	 * @see com.cai310.lottery.common.Lottery
	 */
//	@Type(type = "org.hibernate.type.EnumType", parameters = {
//			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
//			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
//	@Column(nullable = false, updatable = true)
	@Transient
	public abstract Lottery getLotteryType();

//	/**
//	 * 设置彩种类型
//	 * 
//	 * @param lotteryType 彩种类型
//	 */
//	public void setLotteryType(Lottery lotteryType) {
//		this.lotteryType = lotteryType;
//	}

	/* ------------------------- logic method ------------------------- */
//	/**
//	 * 检查是否能发起方案
//	 * 
//	 * @throws DataException
//	 */
//	public void checkIsCanInit() throws DataException {
//		if (isPaused())
//			throw new DataException("销售暂停中,请稍候再试.");
//		else if (isSaleEnded() || isFinished())
//			throw new DataException("已截止销售.");
//		else if (!isOnSale())
//			throw new DataException("该销售期不在销售状态.");
//	}
//
//	/**
//	 * 检查是否能发起方案
//	 * 
//	 * @throws DataException
//	 */
//	public void checkIsCanSubscriptionOrBaodi() throws DataException {
//		if (isPaused())
//			throw new DataException("销售暂停中,请稍候再试.");
//		else if (isSaleEnded() || isFinished())
//			throw new DataException("已截止销售.");
//		else if (!isOnSale())
//			throw new DataException("该销售期不在销售状态.");
//	}

//	/**
//	 * 判断是否在售
//	 * 
//	 * @return 是否在售
//	 */
//	@Transient
//	public boolean isOnSale() {
//		return state == PeriodState.Started;
//	}
//
//	@Transient
//	public boolean isPaused() {
//		return this.state == PeriodState.Paused;
//	}

	@Transient
	public boolean isSaleEnded() {
		return this.state.ordinal() >= PeriodState.SaleEnded.ordinal()||endedTime.before(new Date());
	}	
	
	@Transient
	public boolean isSaleEnded(int beforeTime) {
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MINUTE, beforeTime);
		Date time=c.getTime();
		return this.state.ordinal() >= PeriodState.SaleEnded.ordinal()||endedTime.before(time);
	}

	@Transient
	public boolean isFinished() {
		return this.state.ordinal() >= PeriodState.Finished.ordinal();
	}

	@Transient
	public boolean isStarted() {
		return this.state.ordinal() >= PeriodState.Started.ordinal();
	}

	/**
	 * @return the periodNumberDisplay
	 */
	@Transient
	public Integer getPeriodNumberDisplay() {
		return periodNumberDisplay;
	}

	/**
	 * @param periodNumberDisplay the periodNumberDisplay to set
	 */
	public void setPeriodNumberDisplay(Integer periodNumberDisplay) {
		this.periodNumberDisplay = periodNumberDisplay;
	}

	/**
	 * @return the limitNum
	 */
	@Column
	public String getLimitNum() {
		return limitNum;
	}

	/**
	 * @param limitNum the limitNum to set
	 */
	public void setLimitNum(String limitNum) {
		this.limitNum = limitNum;
	}

	/**
	 * @return the results
	 */
	@Column
	public String getResults() {
		return results;
	}

	/**
	 * @param results the 2 to set
	 */
	public void setResults(String results) {
		this.results = results;
	}

	/**
	 * @return the allSchemeCount
	 */
	@Column
	public Integer getAllSchemeCount() {
		return allSchemeCount;
	}

	/**
	 * @param allSchemeCount the allSchemeCount to set
	 */
	public void setAllSchemeCount(Integer allSchemeCount) {
		this.allSchemeCount = allSchemeCount;
	}

	/**
	 * @return the allSuccessSchemeCount
	 */
	@Column
	public Integer getAllSuccessSchemeCount() {
		return allSuccessSchemeCount;
	}

	/**
	 * @param allSuccessSchemeCount the allSuccessSchemeCount to set
	 */
	public void setAllSuccessSchemeCount(Integer allSuccessSchemeCount) {
		this.allSuccessSchemeCount = allSuccessSchemeCount;
	}

	/**
	 * @return the allSale
	 */
	@Column
	public Integer getAllSale() {
		return allSale;
	}

	/**
	 * @param allSale the allSale to set
	 */
	public void setAllSale(Integer allSale) {
		this.allSale = allSale;
	}

	/**
	 * @return the allPrize
	 */
	@Column
	public Integer getAllPrize() {
		return allPrize;
	}

	/**
	 * @param allPrize the allPrize to set
	 */
	public void setAllPrize(Integer allPrize) {
		this.allPrize = allPrize;
	}	
	
	@Transient
	public boolean isDrawed(){
		return this.state.getStateValue()>=IssueState.ISSUE_SATE_RESULT.getStateValue();
	}
	
	@Transient
	public String[] getRsultArr() {
		if (results == null) {
			return null;
		}
		String[] resultArr=null;
		if(results.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
			String[] resultStrArr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			resultArr = new String[resultStrArr.length];
			Integer num;
			for (int i = 0; i < resultStrArr.length; i++) {
				num = Integer.valueOf(resultStrArr[i]);
				if(this.getLotteryType().equals(Lottery.EL11TO5)||this.getLotteryType().equals(Lottery.SDEL11TO5)||this.getLotteryType().equals(Lottery.QYH)||this.getLotteryType().equals(Lottery.GDEL11TO5)||this.getLotteryType().equals(Lottery.XJEL11TO5)){
					if(num<10){
						resultArr[i] = "0"+num;
					}else{
						resultArr[i] = ""+num;
					}
				}else{
					resultArr[i] = ""+num;
				}
			}
		}
		return resultArr;
	 }
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
		    		  if(this.getLotteryType().equals(Lottery.EL11TO5)||this.getLotteryType().equals(Lottery.SDEL11TO5)||this.getLotteryType().equals(Lottery.QYH)||this.getLotteryType().equals(Lottery.GDEL11TO5 )||this.getLotteryType().equals(Lottery.XJEL11TO5)){
		    			  if(number<10){
			    			  sb.append("0"+number+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			    		  }else{
			    			  sb.append(number+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			    		  }
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
	@Transient
	public String getPrizeTimeFormat() {
		if(null==this.prizeTime){
			return "";
		}
		return DateFormatUtils.format(this.prizeTime, "HH:mm");
	}
	@Transient
	public boolean isPaused() {
		return this.state == IssueState.ISSUE_SATE_END;
	}
	/**
	 * 判断是否在售
	 * 
	 * @return 是否在售
	 */
	@Transient
	public boolean isOnSale() {
		return state == IssueState.ISSUE_SATE_END;
	}

	/**
	 * 检查是否能发起方案
	 * 
	 * @throws DataException
	 */
	public void checkIsCanSubscriptionOrBaodi() throws DataException {
		if (isPaused())
			throw new DataException("销售暂停中,请稍候再试.");
		else if (isSaleEnded() || isFinished())
			throw new DataException("已截止销售.");
		else if (isOnSale())
			throw new DataException("该销售期不在销售状态.");
	}
	@Transient
	public String getEndTimeFormat() {
		if(null==this.endedTime){
			return "";
		}
		return DateFormatUtils.format(this.endedTime, "HH:mm");
	}
}
