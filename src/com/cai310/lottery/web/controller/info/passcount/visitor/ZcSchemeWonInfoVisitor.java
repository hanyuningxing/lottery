package com.cai310.lottery.web.controller.info.passcount.visitor;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.info.passcount.form.ZcSchemeWonInfo;

public class ZcSchemeWonInfoVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private Lottery lottery;//彩种
	private Integer playType;//玩法
	private ZcSchemeWonInfo zcSchemeWonInfo;

	/**
	 * <passcount>
	    <lost0>1</lost0>
	    <lost1>3</lost1>
	    <lost2>2</lost2>
	    <lost3>0</lost3>
	    <mode>0</mode>
	    <multiple>1</multiple>
	    <periodId>66</periodId>
	    <schemeCost>12</schemeCost>
	    <schemeId>82</schemeId>
	    <sponsorId>2</sponsorId>
	    <sponsorName>111</sponsorName>
	    <state>2</state>
	    <prize>2</prize>
	    <units>6</units>
	    <passcount>6</passcount>
	    <version>0</version>
	  </passcount>
	 */
	@Override
	public void visit(Element node){
		if(null==zcSchemeWonInfo){
			zcSchemeWonInfo=new ZcSchemeWonInfo();
			zcSchemeWonInfo.setLottery(this.getLottery());
			zcSchemeWonInfo.setPlayType(this.getPlayType());
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("lost0".equals(node.getName())){
				zcSchemeWonInfo.setLost0(Integer.valueOf(node.getText()));
			}
			if("lost1".equals(node.getName())){
				zcSchemeWonInfo.setLost1(Integer.valueOf(node.getText()));
			}
			if("lost2".equals(node.getName())){
				zcSchemeWonInfo.setLost2(Integer.valueOf(node.getText()));
			}
			if("lost3".equals(node.getName())){
				zcSchemeWonInfo.setLost3(Integer.valueOf(node.getText()));
			}
			if("multiple".equals(node.getName())){
				zcSchemeWonInfo.setMultiple(Integer.valueOf(node.getText()));
			}
			if("periodId".equals(node.getName())){
				zcSchemeWonInfo.setPeriodId(Long.valueOf(node.getText()));
			}
			if("schemeCost".equals(node.getName())){
				zcSchemeWonInfo.setSchemeCost(Integer.valueOf(node.getText()));
			}
			if("schemeId".equals(node.getName())){
				zcSchemeWonInfo.setSchemeId(Long.valueOf(node.getText()));
			}
			if("sponsorId".equals(node.getName())){
				zcSchemeWonInfo.setSponsorId(Long.valueOf(node.getText()));
			}
			if("sponsorName".equals(node.getName())){
				zcSchemeWonInfo.setSponsorName(node.getText().trim());
			}
			if("units".equals(node.getName())){
				zcSchemeWonInfo.setUnits(Integer.valueOf(node.getText()));
			}
			if("mode".equals(node.getName())){
				zcSchemeWonInfo.setMode(SalesMode.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
			if("state".equals(node.getName())){
				zcSchemeWonInfo.setState(SchemeState.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
			if("prize".equals(node.getName())){
				zcSchemeWonInfo.setPrize(BigDecimalUtil.valueOf(Double.valueOf(node.getText())));
			}
			if("pc".equals(node.getName())){
				zcSchemeWonInfo.setPasscount(Integer.valueOf(node.getText()));
			}
		}
	}


	/**
	 * @return the zcSchemeWonInfo
	 */
	public ZcSchemeWonInfo getZcSchemeWonInfo() {
		return zcSchemeWonInfo;
	}


	/**
	 * @param zcSchemeWonInfo the zcSchemeWonInfo to set
	 */
	public void setZcSchemeWonInfo(ZcSchemeWonInfo zcSchemeWonInfo) {
		this.zcSchemeWonInfo = zcSchemeWonInfo;
	}


	/**
	 * @return the lottery
	 */
	public Lottery getLottery() {
		return lottery;
	}


	/**
	 * @param lottery the lottery to set
	 */
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}


	/**
	 * @return the playType
	 */
	public Integer getPlayType() {
		return playType;
	}


	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Integer playType) {
		this.playType = playType;
	}


	
}
