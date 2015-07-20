package com.cai310.lottery.common;

import java.math.BigDecimal;

import com.cai310.lottery.Constant;
import com.cai310.lottery.web.controller.admin.fund.CountFundForm;

/**
 * 资金明细类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum FundDetailType {
	/** 认购 */
	SUBSCRIPTION("认购","1",0),  //0

	/** 撤销认购 */
	CANCEL_SUBSCRIPTION("撤销认购","1",1), //1

	/** 保底 */
	BAODI("保底","1",0), //2

	/** 撤销保底 */
	CANCEL_BAODI("撤销保底","1",1), //3

	/** 方案撤单 */
	CANCEL_SCHEME("方案撤单","1",1),  //4

	/** 方案退款 */
	REFUNDMENT_SCHEME("方案退款","1",1),//5

	/** 方案佣金 */
	SCHEME_COMMISSION("方案佣金","1",2),//6

	/** 奖金分红 */
	SCHEME_BONUS("奖金分红","3",2),//7

	/** 管理员手动调整额度 */
	ADMIN_OPR("手动调整额度","8",100),

	/** 追号 */
	CHASE("追号","1",100),

	/** 用户提款 */
	DRAWING("用户提款","2",3),

	/** 停止追号 */
	STOP_CHASE("停止追号","1",100),

	/** 提款失败返还资金 */
	DRAWINGFAIL("提款失败返还资金","2",4),

	/** 在线充值 */
	PAY("在线充值","4",5),
	
	/** 后台通过充值 */
	ADMINPAY("后台通过充值","8",5),
	
	/** 赠送彩金 */
	RECHARGEACTIVITY("活动赠送彩金","8",6),
	
	/** 代理商操作 */
	AGENTOPR("代理商操作","8",100),
	
	/** 佣金 */
	REBATE("佣金","8",7), 
	
	TENSENDONE("10送1","8",6);
	
	/** 类型名称 */
	private final String typeName;
	
	/** 类目类型名称 */
	private final String superTypeName;
	//0==增加消费,1=减少消费,2=奖金,3=提款,4=提款失败,5=充值,6=活动彩金,7=佣金,100=需要判断x
    private final Integer countType;
	/**
	 * @param typeName {@link #typeName}
	 */
	
	private FundDetailType(String typeName,String superTypeName,Integer countType) {
		this.typeName = typeName;
		this.superTypeName = superTypeName;
		this.countType = countType;
	}
	//0==增加消费,1=减少消费,2=奖金,3=提款,4=提款失败,5=充值,6=活动彩金,7=佣金,100=需要判断
	/**
	 * 
	 * @param countFundForm
	 * @param mode
	 * @param money
	 */
	public void countFund(CountFundForm countFundForm,FundMode mode,BigDecimal money) {
		switch (this.countType) {
			case 0:
				countFundForm.setUseCost(countFundForm.getUseCost().add(money));
				break;
			case 1:
				countFundForm.setUseCost(countFundForm.getUseCost().subtract(money));
				break;
			case 2:
				countFundForm.setBonusCost(countFundForm.getBonusCost().add(money));
				break;
			case 3:
				countFundForm.setDrawCost(countFundForm.getDrawCost().add(money));
				break;
			case 4:
				countFundForm.setDrawCost(countFundForm.getDrawCost().subtract(money));
				break;
			case 5:
				countFundForm.setPayCost(countFundForm.getPayCost().add(money));
				break;
			case 6:
				countFundForm.setActCost(countFundForm.getActCost().add(money));
				break;
			case 7:
				countFundForm.setRebateCost(countFundForm.getRebateCost().add(money));
				break;
			case 100:
				switch (this) {
				case ADMIN_OPR:
					if(mode.equals(FundMode.IN)){
						//收入
						countFundForm.setAdminInCost(countFundForm.getAdminInCost().add(money));
					}else if(mode.equals(FundMode.OUT)){
						//支出
						countFundForm.setAdminOutCost(countFundForm.getAdminOutCost().add(money));
					}else{
						//无
					}
					break;
				case CHASE:
					if(mode.equals(FundMode.IN)){
						//收入
						//追号没收入
					}else if(mode.equals(FundMode.OUT)){
						//支出
						//追好的支出是只是追号类型的 无(第一次发起) .还有认购类型 的无(线程追号)。所以追号统计应该统计这两个实际金额。而且取消追号不统计。
					}else{
						//无
						//追号类型的 无(第一次发起)
						countFundForm.setUseCost(countFundForm.getUseCost().add(money));
					}
					break;
				case STOP_CHASE:
					break;
				case AGENTOPR:
					break;
				
				}
				break;
				
		}
	}
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
