package com.cai310.lottery.web.controller.info.passcount.visitor;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.web.controller.info.passcount.form.DltSchemeWonInfo;

public class DltSchemeWonInfoVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private Lottery lottery;//彩种
	private DltSchemeWonInfo dltSchemeWonInfo;

	/**
	 * <passcount>
	    <firstWinUnits>1</firstWinUnits>
	    <secondWinUnits>0</secondWinUnits>
	    <thirdWinUnits>0</thirdWinUnits>
	    <fourthWinUnits>10</fourthWinUnits>
	    <fifthWinUnits>0</fifthWinUnits>
	    <sixthWinUnits>10</sixthWinUnits>
	    <seventhWinUnits>0</seventhWinUnits>
	    <eighthWinUnits>0</eighthWinUnits>
	    <select12to2WinUnits>0</select12to2WinUnits>
	    <mode>0</mode>
	    <multiple>1</multiple>
	    <periodId>352</periodId>
	    <schemeCost>42</schemeCost>
	    <schemeId>32</schemeId>
	    <sponsorId>2</sponsorId>
	    <sponsorName>DLT32</sponsorName>
	    <state>2</state>
	    <playType>2</playType>
	    <units>21</units>
	    <version>0</version>
	  </passcount>

	 */
	@Override
	public void visit(Element node){
		if(null==dltSchemeWonInfo){
			dltSchemeWonInfo=new DltSchemeWonInfo();
			dltSchemeWonInfo.setLottery(this.getLottery());
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("firstWinUnits".equals(node.getName())){
				dltSchemeWonInfo.setFirstWinUnits(Integer.valueOf(node.getText()));
			}
			if("secondWinUnits".equals(node.getName())){
				dltSchemeWonInfo.setSecondWinUnits(Integer.valueOf(node.getText()));
			}
			if("thirdWinUnits".equals(node.getName())){
				dltSchemeWonInfo.setThirdWinUnits(Integer.valueOf(node.getText()));
			}
			if("fourthWinUnits".equals(node.getName())){
				dltSchemeWonInfo.setFourthWinUnits(Integer.valueOf(node.getText()));
			}
			if("fifthWinUnits".equals(node.getName())){
				dltSchemeWonInfo.setFifthWinUnits(Integer.valueOf(node.getText()));
			}
			if("sixthWinUnits".equals(node.getName())){
				dltSchemeWonInfo.setSixthWinUnits(Integer.valueOf(node.getText()));
			}
			if("seventhWinUnits".equals(node.getName())){
				dltSchemeWonInfo.setSeventhWinUnits(Integer.valueOf(node.getText()));
			}
			if("eighthWinUnits".equals(node.getName())){
				dltSchemeWonInfo.setEighthWinUnits(Integer.valueOf(node.getText()));
			}
			if("select12to2WinUnits".equals(node.getName())){
				dltSchemeWonInfo.setSelect12to2WinUnits(Integer.valueOf(node.getText()));
			}
			if("multiple".equals(node.getName())){
				dltSchemeWonInfo.setMultiple(Integer.valueOf(node.getText()));
			}
			if("periodId".equals(node.getName())){
				dltSchemeWonInfo.setPeriodId(Long.valueOf(node.getText()));
			}
			if("schemeCost".equals(node.getName())){
				dltSchemeWonInfo.setSchemeCost(Integer.valueOf(node.getText()));
			}
			if("schemeId".equals(node.getName())){
				dltSchemeWonInfo.setSchemeId(Long.valueOf(node.getText()));
			}
			if("sponsorId".equals(node.getName())){
				dltSchemeWonInfo.setSponsorId(Long.valueOf(node.getText()));
			}
			if("sponsorName".equals(node.getName())){
				dltSchemeWonInfo.setSponsorName(node.getText().trim());
			}
			if("units".equals(node.getName())){
				dltSchemeWonInfo.setUnits(Integer.valueOf(node.getText()));
			}
			if("mode".equals(node.getName())){
				dltSchemeWonInfo.setMode(SalesMode.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
			if("playType".equals(node.getName())){
				dltSchemeWonInfo.setPlayType(com.cai310.lottery.support.dlt.DltPlayType.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
			if("state".equals(node.getName())){
				dltSchemeWonInfo.setState(SchemeState.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
			if("shareType".equals(node.getName())){
				dltSchemeWonInfo.setShareType(ShareType.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
		}
	}


	/**
	 * @return the dltSchemeWonInfo
	 */
	public DltSchemeWonInfo getDltSchemeWonInfo() {
		return dltSchemeWonInfo;
	}


	/**
	 * @param dltSchemeWonInfo the dltSchemeWonInfo to set
	 */
	public void setDltSchemeWonInfo(DltSchemeWonInfo dltSchemeWonInfo) {
		this.dltSchemeWonInfo = dltSchemeWonInfo;
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


	


	
}
