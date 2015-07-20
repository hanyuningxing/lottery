package com.cai310.lottery.web.controller.info.passcount.visitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.web.controller.info.passcount.form.ZcSchemeWonInfo;

public class ZcPassCountSchemeVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	
	private ZcSchemeWonInfo zcSchemeWonInfo;
	
	private Lottery lottery;//彩种
	
	private Integer playType;//玩法
	
	private Boolean won;
	
	private SchemeState state;
	
	private SalesMode mode;
	
	private String searchStr;
	
	private List<ZcSchemeWonInfo> zcSchemeWonInfoList;

	private ZcSchemeWonInfoVisitor zcSchemeWonInfoVisitor;
	
	private Integer count=0;
	@Override
	public void visit(Element node){
		if("passcount".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getStringValue())){
				if(null==zcSchemeWonInfoList){
					zcSchemeWonInfoList=new ArrayList<ZcSchemeWonInfo>();
				}
				zcSchemeWonInfoVisitor=new ZcSchemeWonInfoVisitor();
				zcSchemeWonInfoVisitor.setLottery(this.getLottery());
				zcSchemeWonInfoVisitor.setPlayType(this.getPlayType());
				node.accept(zcSchemeWonInfoVisitor);
				zcSchemeWonInfo=zcSchemeWonInfoVisitor.getZcSchemeWonInfo();
				Boolean canInsert=Boolean.TRUE;//符合条件
				if(null!=zcSchemeWonInfo){
					if(StringUtils.isNotBlank(searchStr)){
						if(StringUtils.isBlank(zcSchemeWonInfo.getSponsorName())){
							canInsert=Boolean.FALSE;
						}
						if(!searchStr.trim().equals(zcSchemeWonInfo.getSponsorName().trim())){
							canInsert=Boolean.FALSE;
						}
					}
					if(null!=state){
						//有选择状态
						if(!state.equals(zcSchemeWonInfo.getState())){
							canInsert=Boolean.FALSE;
						}
					}
					if(null!=won){
						if(won){
							if(!zcSchemeWonInfo.getWon()){
								canInsert=Boolean.FALSE;
							}
						}
					}
					if(null!=mode){
						if(!mode.equals(zcSchemeWonInfo.getMode())){
							canInsert=Boolean.FALSE;
						}
					}
					if(canInsert){
						count++;
						zcSchemeWonInfo.setId(count);
						zcSchemeWonInfoList.add(zcSchemeWonInfo);
					}
				}
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
	 * @return the zcSchemeWonInfoList
	 */
	public List<ZcSchemeWonInfo> getZcSchemeWonInfoList() {
		return zcSchemeWonInfoList;
	}


	/**
	 * @param zcSchemeWonInfoList the zcSchemeWonInfoList to set
	 */
	public void setZcSchemeWonInfoList(List<ZcSchemeWonInfo> zcSchemeWonInfoList) {
		this.zcSchemeWonInfoList = zcSchemeWonInfoList;
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
