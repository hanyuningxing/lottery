package com.cai310.lottery.web.controller.info.passcount.visitor;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.web.controller.info.passcount.form.SevenSchemeWonInfo;

public class SevenSchemeWonInfoVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private Lottery lottery;//彩种
	private Integer playType;//玩法
	private SevenSchemeWonInfo sevenSchemeWonInfo;

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
	    <units>21</units>
	    <version>0</version>
	  </passcount>

	 */
	@Override
	public void visit(Element node){
		if(null==sevenSchemeWonInfo){
			sevenSchemeWonInfo=new SevenSchemeWonInfo();
			sevenSchemeWonInfo.setLottery(this.getLottery());
			sevenSchemeWonInfo.setPlayType(this.getPlayType());
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("firstWinUnits".equals(node.getName())){
				sevenSchemeWonInfo.setFirstWinUnits(Integer.valueOf(node.getText()));
			}
			if("secondWinUnits".equals(node.getName())){
				sevenSchemeWonInfo.setSecondWinUnits(Integer.valueOf(node.getText()));
			}
			if("thirdWinUnits".equals(node.getName())){
				sevenSchemeWonInfo.setThirdWinUnits(Integer.valueOf(node.getText()));
			}
			if("fourthWinUnits".equals(node.getName())){
				sevenSchemeWonInfo.setFourthWinUnits(Integer.valueOf(node.getText()));
			}
			if("fifthWinUnits".equals(node.getName())){
				sevenSchemeWonInfo.setFifthWinUnits(Integer.valueOf(node.getText()));
			}
			if("sixthWinUnits".equals(node.getName())){
				sevenSchemeWonInfo.setSixthWinUnits(Integer.valueOf(node.getText()));
			}
			if("sevenWinUnits".equals(node.getName())){
				sevenSchemeWonInfo.setSevenWinUnits(Integer.valueOf(node.getText()));
			}
			if("multiple".equals(node.getName())){
				sevenSchemeWonInfo.setMultiple(Integer.valueOf(node.getText()));
			}
			if("periodId".equals(node.getName())){
				sevenSchemeWonInfo.setPeriodId(Long.valueOf(node.getText()));
			}
			if("schemeCost".equals(node.getName())){
				sevenSchemeWonInfo.setSchemeCost(Integer.valueOf(node.getText()));
			}
			if("schemeId".equals(node.getName())){
				sevenSchemeWonInfo.setSchemeId(Long.valueOf(node.getText()));
			}
			if("sponsorId".equals(node.getName())){
				sevenSchemeWonInfo.setSponsorId(Long.valueOf(node.getText()));
			}
			if("sponsorName".equals(node.getName())){
				sevenSchemeWonInfo.setSponsorName(node.getText().trim());
			}
			if("units".equals(node.getName())){
				sevenSchemeWonInfo.setUnits(Integer.valueOf(node.getText()));
			}
			if("mode".equals(node.getName())){
				sevenSchemeWonInfo.setMode(SalesMode.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
			if("state".equals(node.getName())){
				sevenSchemeWonInfo.setState(SchemeState.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
			if("shareType".equals(node.getName())){
				sevenSchemeWonInfo.setShareType(ShareType.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
		}
	}


	/**
	 * @return the sevenSchemeWonInfo
	 */
	public SevenSchemeWonInfo getSevenSchemeWonInfo() {
		return sevenSchemeWonInfo;
	}


	/**
	 * @param sevenSchemeWonInfo the sevenSchemeWonInfo to set
	 */
	public void setSsqSchemeWonInfo(SevenSchemeWonInfo sevenSchemeWonInfo) {
		this.sevenSchemeWonInfo = sevenSchemeWonInfo;
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
