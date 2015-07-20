package com.cai310.lottery.web.controller.info.passcount.visitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.web.controller.info.passcount.form.SsqSchemeWonInfo;

public class SsqPassCountSchemeVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	
	private SsqSchemeWonInfo ssqSchemeWonInfo;
	
	private Lottery lottery;//彩种
	
	private Integer playType;//玩法
	
	private SchemeState state;
	
	private SalesMode mode;
	
	private Boolean won;
	
	private String searchStr;
	
	private List<SsqSchemeWonInfo> ssqSchemeWonInfoList;

	private SsqSchemeWonInfoVisitor ssqSchemeWonInfoVisitor;
	
	private Integer count=0;
	@Override
	public void visit(Element node){
		if("passcount".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getStringValue())){
				if(null==ssqSchemeWonInfoList){
					ssqSchemeWonInfoList=new ArrayList<SsqSchemeWonInfo>();
				}
				ssqSchemeWonInfoVisitor=new SsqSchemeWonInfoVisitor();
				ssqSchemeWonInfoVisitor.setLottery(this.getLottery());
				ssqSchemeWonInfoVisitor.setPlayType(this.getPlayType());
				node.accept(ssqSchemeWonInfoVisitor);
				ssqSchemeWonInfo=ssqSchemeWonInfoVisitor.getSsqSchemeWonInfo();
				Boolean canInsert=Boolean.TRUE;//符合条件
				if(null!=ssqSchemeWonInfo){
					if(StringUtils.isNotBlank(searchStr)){
						if(StringUtils.isBlank(ssqSchemeWonInfo.getSponsorName())){
							canInsert=Boolean.FALSE;
						}
						if(!searchStr.trim().equals(ssqSchemeWonInfo.getSponsorName().trim())){
							canInsert=Boolean.FALSE;
						}
					}
					if(null!=won){
						if(won){
							if(!ssqSchemeWonInfo.getWon()){
								canInsert=Boolean.FALSE;
							}
						}
					}
					if(null!=state){
						//有选择状态
						if(!state.equals(ssqSchemeWonInfo.getState())){
							canInsert=Boolean.FALSE;
						}
					}
					if(null!=mode){
						if(!mode.equals(ssqSchemeWonInfo.getMode())){
							canInsert=Boolean.FALSE;
						}
					}
					if(canInsert){
						count++;
						ssqSchemeWonInfo.setId(count);
						ssqSchemeWonInfoList.add(ssqSchemeWonInfo);
					}
				}
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
	 * @return the ssqSchemeWonInfoList
	 */
	public List<SsqSchemeWonInfo> getSsqSchemeWonInfoList() {
		return ssqSchemeWonInfoList;
	}


	/**
	 * @param ssqSchemeWonInfoList the ssqSchemeWonInfoList to set
	 */
	public void setSsqSchemeWonInfoList(List<SsqSchemeWonInfo> ssqSchemeWonInfoList) {
		this.ssqSchemeWonInfoList = ssqSchemeWonInfoList;
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
	 * @return the state
	 */
	public SchemeState getState() {
		return state;
	}


	/**
	 * @param state the state to set
	 */
	public void setState(SchemeState state) {
		this.state = state;
	}


	/**
	 * @return the mode
	 */
	public SalesMode getMode() {
		return mode;
	}


	/**
	 * @param mode the mode to set
	 */
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}


	/**
	 * @return the searchStr
	 */
	public String getSearchStr() {
		return searchStr;
	}


	/**
	 * @param searchStr the searchStr to set
	 */
	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
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


	public Boolean getWon() {
		return won;
	}


	public void setWon(Boolean won) {
		this.won = won;
	}


	
	
}
