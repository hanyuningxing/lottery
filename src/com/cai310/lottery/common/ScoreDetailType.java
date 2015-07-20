package com.cai310.lottery.common;

import java.math.BigDecimal;

import com.cai310.lottery.Constant;
import com.cai310.lottery.web.controller.admin.fund.CountFundForm;

/**
 * 资金明细类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum ScoreDetailType {
	/** 中奖赠送 */
	WINNING("中奖","0",0),  //0

	/** 充值 */
	RECHARGE("充值","1",1),
	
	/**兑款*/
	CHANGE("兑款","2",2);
	
	/** 类型名称 */
	private final String typeName;
	
	/** 类目类型名称 */
	private final String superTypeName;
	//0=奖金,1=充值
    private final Integer countType;
	/**
	 * @param typeName {@link #typeName}
	 */
	
	private ScoreDetailType(String typeName,String superTypeName,Integer countType) {
		this.typeName = typeName;
		this.superTypeName = superTypeName;
		this.countType = countType;
	}
	//0=奖金,1=充值
	public static void Settle(ScoreDetailType scoreDetailType,FundMode mode,BigDecimal[] arr,BigDecimal score) {
//		if(arr[1].compareTo(BigDecimal.ZERO)<0){//不可提金额小于0,充值的可用
//			arr[0] = arr[0].subtract(arr[1].abs());//可提金额 - 不可提
//			arr[1] = BigDecimal.ZERO;//可提金额 - 不可提
//		}
		switch (scoreDetailType.countType) {
			case 0:
				arr[1] = arr[1].add(score);
				break;
			case 1:
				arr[1] = arr[1].add(score);
				break;
			case 2:
				arr[0] = arr[0].subtract(score);
				break;
//			case 3:
//				arr[0] = arr[0].subtract(money);
//				break;
//			case 4:
//				arr[0] = arr[0].add(money);
//				break;
//			case 5:
////				if(mode.equals(FundMode.IN)){
////					//收入
////					arr[0] = arr[0].add(money.multiply(BigDecimal.valueOf(0.7)));
////					arr[1] = arr[1].add(money.multiply(BigDecimal.valueOf(0.3)));
////				}
//				arr[1] = arr[1].add(money);
//				break;
//			case 6:
//				arr[1] = arr[1].add(money);
//				break;
//			case 7:
//				arr[0] = arr[0].add(money);
//				break;
//			case 100:
//				switch (fundDetailType) {
//				case ADMIN_OPR:
//					if(mode.equals(FundMode.IN)){
//						//收入
//						arr[1] = arr[1].add(money);
//					}else if(mode.equals(FundMode.OUT)){
//						//支出
//						arr[1] = arr[1].subtract(money);
//					}else{
//						//无
//					}
//					break;
//				case CHASE:
//					if(mode.equals(FundMode.IN)){
//						//收入
//						//追号没收入
//					}else if(mode.equals(FundMode.OUT)){
//						//支出
//						//追好的支出是只是追号类型的 无(第一次发起) .还有认购类型 的无(线程追号)。所以追号统计应该统计这两个实际金额。而且取消追号不统计。
//					}else{
//						//无
//						//追号类型的 无(第一次发起)
//						arr[1] = arr[1].subtract(money);
//					}
//					break;
//				case STOP_CHASE:
//					break;
//				case AGENTOPR:
//					break;
//				
//				}
//				break;
				
		}
	}
	
	
	//0==增加消费,1=减少消费,2=奖金,3=提款,4=提款失败,5=充值,6=活动彩金,7=佣金,100=需要判断
	/**
	 * 
	 * @param countFundForm
	 * @param mode
	 * @param money
	 */
//	public void countFund(CountFundForm countFundForm,FundMode mode,BigDecimal money) {
//		switch (this.countType) {
//			case 0:
//				countFundForm.setUseCost(countFundForm.getUseCost().add(money));
//				break;
//			case 1:
//				countFundForm.setUseCost(countFundForm.getUseCost().subtract(money));
//				break;
//			case 2:
//				countFundForm.setBonusCost(countFundForm.getBonusCost().add(money));
//				break;
//			case 3:
//				countFundForm.setDrawCost(countFundForm.getDrawCost().add(money));
//				break;
//			case 4:
//				countFundForm.setDrawCost(countFundForm.getDrawCost().subtract(money));
//				break;
//			case 5:
//				countFundForm.setPayCost(countFundForm.getPayCost().add(money));
//				break;
//			case 6:
//				countFundForm.setActCost(countFundForm.getActCost().add(money));
//				break;
//			case 7:
//				countFundForm.setRebateCost(countFundForm.getRebateCost().add(money));
//				break;
//			case 100:
//				switch (this) {
//				case ADMIN_OPR:
//					if(mode.equals(FundMode.IN)){
//						//收入
//						countFundForm.setAdminInCost(countFundForm.getAdminInCost().add(money));
//					}else if(mode.equals(FundMode.OUT)){
//						//支出
//						countFundForm.setAdminOutCost(countFundForm.getAdminOutCost().add(money));
//					}else{
//						//无
//					}
//					break;
//				case CHASE:
//					if(mode.equals(FundMode.IN)){
//						//收入
//						//追号没收入
//					}else if(mode.equals(FundMode.OUT)){
//						//支出
//						//追好的支出是只是追号类型的 无(第一次发起) .还有认购类型 的无(线程追号)。所以追号统计应该统计这两个实际金额。而且取消追号不统计。
//					}else{
//						//无
//						//追号类型的 无(第一次发起)
//						countFundForm.setUseCost(countFundForm.getUseCost().add(money));
//					}
//					break;
//				case STOP_CHASE:
//					break;
//				case AGENTOPR:
//					break;
//				
//				}
//				break;
//				
//		}
//	}
	/**
	 * @return {@link #typeName}
	 */
	public String getTypeName() {
		return typeName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

	public String getSuperTypeName() {
		return superTypeName;
	}
	public Integer getCountType() {
		return countType;
	}
}
