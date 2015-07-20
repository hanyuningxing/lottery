package com.cai310.lottery.entity.lottery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.exception.DataException;

/**
 * 数字类型彩票方案基类
 * 
 */
@MappedSuperclass
public abstract class NumberScheme extends Scheme {
	private static final long serialVersionUID = -9020101774927424090L;

	/** 追号编号 */
	private Long chaseId;

	@Column(updatable = false)
	public Long getChaseId() {
		return chaseId;
	}

	public void setChaseId(Long chaseId) {
		this.chaseId = chaseId;
	}

	/**
	 * 为追号重置方案
	 */
	public void reset(Period period, ChasePlan chasePlan, long tranId) throws DataException {

		if (this.getSchemeCost().intValue() % this.getMultiple().intValue() != 0) {
			throw new DataException("方案数据异常：方案金额除不尽方案倍数！");
		}

		int schemeCostPerMul = this.getSchemeCost().intValue() / this.getMultiple().intValue();// 单倍金额

		Integer nextMultiple = chasePlan.getNextMultiple();// 新的方案倍数
		if (nextMultiple == null || nextMultiple.intValue() <= 0) {
			throw new DataException("追号方案倍数异常！");
		}
		this.setWon(Boolean.FALSE);
		this.setCommitTime(null);
		this.setPrize(null);
		this.setPrizeAfterTax(null);
		this.setPrizeDetail(null);
		this.setPrizeSended(Boolean.FALSE);
		this.setMultiple(nextMultiple);
		this.setSchemeCost(schemeCostPerMul * nextMultiple);
		this.setSubscribedCost(BigDecimal.valueOf(schemeCostPerMul * nextMultiple));
		this.setPeriodId(period.getId());
		this.setPeriodNumber(period.getPeriodNumber());
		this.setTransactionId(tranId);
		this.setSendToPrint(Boolean.FALSE);
		this.setRemark(null);
		this.setWinningUpdateStatus(WinningUpdateStatus.NONE);
		this.setCreateTime(null);
		this.setLastModifyTime(null);
		this.setPrizeSendTime(null);
		this.setReserved(Boolean.FALSE);
		this.setUploadTime(new Date());
		this.setSchemePrintState(SchemePrintState.UNPRINT);
		this.setOrderPriority(Constant.ORDER_PRIORITY_DEFAULT);
		this.setState(SchemeState.FULL);
	}
	@Transient
	public String getBetListString(List<String> list,List<Integer> result) {
		if (list != null && list.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				if(result.contains(Integer.valueOf(list.get(i)))){
					sb.append("<span style=\"text-decoration:underline\">"+list.get(i)+"</span>");
				}else{
					sb.append(list.get(i));
				}
				sb.append(SsqConstant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length() - SsqConstant.SEPARATOR_FOR_NUMBER.length());
			return sb.toString();
		}
		return "";
	}
	@Transient
	public String getBetListString(List<String> list) {
		if (list != null && list.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i));
				sb.append(SsqConstant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length() - SsqConstant.SEPARATOR_FOR_NUMBER.length());
			return sb.toString();
		}
		return "";
	}
}