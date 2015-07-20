package com.cai310.lottery.web.controller.info.passcount.visitor;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.web.controller.info.passcount.form.SsqSchemeWonInfo;

public class SsqSchemeWonInfoVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private Lottery lottery;//彩种
	private Integer playType;//玩法
	private SsqSchemeWonInfo ssqSchemeWonInfo;

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
		if(null==ssqSchemeWonInfo){
			ssqSchemeWonInfo=new SsqSchemeWonInfo();
			ssqSchemeWonInfo.setLottery(this.getLottery());
			ssqSchemeWonInfo.setPlayType(this.getPlayType());
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("firstWinUnits".equals(node.getName())){
				ssqSchemeWonInfo.setFirstWinUnits(Integer.valueOf(node.getText()));
			}
			if("secondWinUnits".equals(node.getName())){
				ssqSchemeWonInfo.setSecondWinUnits(Integer.valueOf(node.getText()));
			}
			if("thirdWinUnits".equals(node.getName())){
				ssqSchemeWonInfo.setThirdWinUnits(Integer.valueOf(node.getText()));
			}
			if("fourthWinUnits".equals(node.getName())){
				ssqSchemeWonInfo.setFourthWinUnits(Integer.valueOf(node.getText()));
			}
			if("fifthWinUnits".equals(node.getName())){
				ssqSchemeWonInfo.setFifthWinUnits(Integer.valueOf(node.getText()));
			}
			if("sixthWinUnits".equals(node.getName())){
				ssqSchemeWonInfo.setSixthWinUnits(Integer.valueOf(node.getText()));
			}
			if("multiple".equals(node.getName())){
				ssqSchemeWonInfo.setMultiple(Integer.valueOf(node.getText()));
			}
			if("periodId".equals(node.getName())){
				ssqSchemeWonInfo.setPeriodId(Long.valueOf(node.getText()));
			}
			if("schemeCost".equals(node.getName())){
				ssqSchemeWonInfo.setSchemeCost(Integer.valueOf(node.getText()));
			}
			if("schemeId".equals(node.getName())){
				ssqSchemeWonInfo.setSchemeId(Long.valueOf(node.getText()));
			}
			if("sponsorId".equals(node.getName())){
				ssqSchemeWonInfo.setSponsorId(Long.valueOf(node.getText()));
			}
			if("sponsorName".equals(node.getName())){
				ssqSchemeWonInfo.setSponsorName(node.getText().trim());
			}
			if("units".equals(node.getName())){
				ssqSchemeWonInfo.setUnits(Integer.valueOf(node.getText()));
			}
			if("mode".equals(node.getName())){
				ssqSchemeWonInfo.setMode(SalesMode.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
			if("state".equals(node.getName())){
				ssqSchemeWonInfo.setState(SchemeState.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
			if("shareType".equals(node.getName())){
				ssqSchemeWonInfo.setShareType(ShareType.valueOfOrdinal(Integer.valueOf(node.getText())));
			}
		}
	}


	/**
	 * @return the ssqSchemeWonInfo
	 */
	public SsqSchemeWonInfo getSsqSchemeWonInfo() {
		return ssqSchemeWonInfo;
	}


	/**
	 * @param ssqSchemeWonInfo the ssqSchemeWonInfo to set
	 */
	public void setSsqSchemeWonInfo(SsqSchemeWonInfo ssqSchemeWonInfo) {
		this.ssqSchemeWonInfo = ssqSchemeWonInfo;
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
