package com.cai310.lottery.entity.lottery.keno;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.cai310.lottery.Constant;
import com.cai310.lottery.El11to5Constant;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.exception.DataException;

@MappedSuperclass
public abstract class KenoScheme extends NumberScheme {
	@Transient
	public BigDecimal getZoomSchemeCost() {
		return BigDecimal.valueOf(schemeCost);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -7694588297631930815L;
	
	/**
	 * 为追号重置方案
	 */
	public void reset(KenoPeriod period, ChasePlan chasePlan, long tranId) throws DataException {
		int schemeCostPerMul = 0;
		if(null!=this.getSchemeCost()) {
			if (this.getSchemeCost().intValue() % this.getMultiple().intValue() != 0) {
				throw new DataException("方案数据异常：方案金额除不尽方案倍数！");
			}
			 schemeCostPerMul = this.getSchemeCost().intValue() / this.getMultiple().intValue();// 单倍金额
		} else {
			schemeCostPerMul = chasePlan.getSchemeCost();//智能追号 单倍金额
		}

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
	/**
	 * 把号码转换成通俗的文字
	 * 
	 * @param list 号码
	 * @param playType 玩法
	 * @return 对应的通俗文字
	 */
	@Transient
	protected String getBetListStringMethod(List<String> list,List<String> danList) {
		StringBuffer sb = new StringBuffer();
		if(danList!=null&&!danList.isEmpty()){
			sb.append("胆码:");
			for (String ball : danList) {
				sb.append(ball);
				sb.append(El11to5Constant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length()-El11to5Constant.SEPARATOR_FOR_NUMBER.length());
			sb.append("拖码:");
			for (String ball : list) {
				sb.append(ball);
				sb.append(El11to5Constant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length() - El11to5Constant.SEPARATOR_FOR_NUMBER.length());
			return sb.toString();
		}else{
			if (list != null && !list.isEmpty()) {
				sb.append("号码:");
				for (String ball : list) {
					sb.append(ball);
					sb.append(El11to5Constant.SEPARATOR_FOR_NUMBER);
				}
				sb.deleteCharAt(sb.length() - El11to5Constant.SEPARATOR_FOR_NUMBER.length());
				return sb.toString();
			}
		}
		return "";
	}
	protected String getBetListStringMethod2(List<String> betList,List<String> disList){
		if(betList!=null&&!betList.isEmpty()&&disList!=null&&!disList.isEmpty()){
		StringBuffer sb = new StringBuffer();
		sb.append("同号:");
		for (String ball : betList) {
			sb.append(ball);
			sb.append(El11to5Constant.SEPARATOR_FOR_NUMBER);
		}
		sb.deleteCharAt(sb.length()-El11to5Constant.SEPARATOR_FOR_NUMBER.length());
		sb.append("不同号:");
		for (String ball : disList) {
			sb.append(ball);
			sb.append(El11to5Constant.SEPARATOR_FOR_NUMBER);
		}
		sb.deleteCharAt(sb.length() - El11to5Constant.SEPARATOR_FOR_NUMBER.length());
		return sb.toString();
		}
		return "";
	}
	@Transient
	protected String getBetListStringMethod(List<String> list,Integer pos) {
		if (list != null && !list.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			sb.append("第"+pos+"位:");
			for (String ball : list) {
				sb.append(ball);
				sb.append(El11to5Constant.SEPARATOR_FOR_NUMBER);
			}
			sb.deleteCharAt(sb.length() - El11to5Constant.SEPARATOR_FOR_NUMBER.length());
			return sb.toString();
		}
		return "";
	}

}
