package com.cai310.lottery.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.user.agent.AgentRebate;
import com.cai310.lottery.support.agent.UserRebateLimit;
import com.cai310.lottery.support.pl.PlPlayType;
import com.google.common.collect.Lists;

/**
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum AgentLotteryType {
	EL11TO5("11选5(VIP)","11选5",4.7D,6D,10D),
	PL3D("排三五/3D(VIP)","排三五/3D",4.7D,6D,10D),
	NUMBER("福体彩返点(VIP)","福体彩数字",4.7D,6D,10D),
	DCZC("北单返点(VIP)","北单",4.7D,6D,10D),
	ZC("足彩返点(VIP)","足彩",4.7D,6D,10D),
	JC("竞彩返点(VIP)","竞彩",4.7D,6D,10D),
	KENO("高频彩返点(VIP)","高频",4.7D,6D,10D);
	
	/** 类型名称 */
	private final String typeName;
	private final String dName;
	private final Double minRebate;
	private final Double maxRebate;
	private final Double adminMaxRebate;
	private List<String> rebateList=Lists.newArrayList();
	private String choose;
	/**
	 * @param typeName {@link #typeName}
	 */
	
	private AgentLotteryType(String typeName,String dName,Double minRebate,Double maxRebate,Double adminMaxRebate) {
		NumberFormat TWO_NF = new DecimalFormat("0.0");
		this.typeName = typeName;
		this.dName = dName;
		this.minRebate = minRebate;
		this.maxRebate = maxRebate;
		this.adminMaxRebate = adminMaxRebate;
		
		for (Double i = 0D; i <minRebate;) {
			rebateList.add(TWO_NF.format(i));
			i = i+Double.valueOf("0.5");
		}
		for (Double i = minRebate; i <=maxRebate;) {
			rebateList.add(TWO_NF.format(i));
			i = i+Double.valueOf("0.1");
		}
	}
	/**
	 * @return {@link #typeName}
	 */
	public String getTypeName() {
		return typeName;
	}
	public static AgentLotteryType getAgentLotteryType(AgentRebate userRebate,String name,Double choose) {
		NumberFormat TWO_NF = new DecimalFormat("0.0");
		AgentLotteryType agentLotteryType = AgentLotteryType.valueOf(name);
		if(null!=choose){
			agentLotteryType.setChoose(TWO_NF.format(choose));
		}
		List<String> rebateList=Lists.newArrayList();
		for (Double i = 0D; i <=userRebate.getRebate();) {
			rebateList.add(TWO_NF.format(i));
			i = i+Double.valueOf("0.1");
		}
		agentLotteryType.setRebateList(rebateList);
		return agentLotteryType;
    }
	public static AgentLotteryType getAgentLotteryType(Lottery lottery) {
			if(lottery.getCategory().equals(LotteryCategory.NUMBER)){
				if(lottery.equals(Lottery.PL)){
					return PL3D;
				}else if(lottery.equals(Lottery.WELFARE3D)){
					return PL3D;
				}else{
					return NUMBER;
				}
			}else if(lottery.getCategory().equals(LotteryCategory.DCZC)){
				return DCZC;
			}else if(lottery.getCategory().equals(LotteryCategory.ZC)){
				return ZC;
			}else if(lottery.getCategory().equals(LotteryCategory.FREQUENT)){
				if(lottery.equals(Lottery.SDEL11TO5)||lottery.equals(Lottery.GDEL11TO5)||lottery.equals(Lottery.EL11TO5)){
					return EL11TO5;
				}else{
					return KENO;
				}
			}else if(lottery.getCategory().equals(LotteryCategory.JC)){
					return JC;
			}else{
				return null;
			}
	}
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
	
	public List<String> getRebateList() {
		return rebateList;
	}
	public String getChoose() {
		return choose;
	}
	public void setChoose(String choose) {
		this.choose = choose;
	}
	public String getdName() {
		return dName;
	}
	public Double getMinRebate() {
		return minRebate;
	}
	public Double getMaxRebate() {
		return maxRebate;
	}
	public void setRebateList(List<String> rebateList) {
		this.rebateList = rebateList;
	}
	public Double getAdminMaxRebate() {
		return adminMaxRebate;
	}
}
