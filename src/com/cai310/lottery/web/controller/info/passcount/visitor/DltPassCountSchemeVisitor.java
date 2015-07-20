package com.cai310.lottery.web.controller.info.passcount.visitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.web.controller.info.passcount.form.DltSchemeWonInfo;

public class DltPassCountSchemeVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	
	private DltSchemeWonInfo dltSchemeWonInfo;
	
	private Lottery lottery;//彩种
	
	//大乐透追加 0=全部 1=普通方案 2=追加方案
	private Integer dltAdd;
	
	private Boolean won;
	
	private SchemeState state;
	
	private SalesMode mode;
	
	private String searchStr;
	
	private List<DltSchemeWonInfo> dltSchemeWonInfoList;

	private DltSchemeWonInfoVisitor dltSchemeWonInfoVisitor;
	
	private Integer count=0;
	@Override
	public void visit(Element node){
		if("passcount".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getStringValue())){
				if(null==dltSchemeWonInfoList){
					dltSchemeWonInfoList=new ArrayList<DltSchemeWonInfo>();
				}
				dltSchemeWonInfoVisitor=new DltSchemeWonInfoVisitor();
				dltSchemeWonInfoVisitor.setLottery(this.getLottery());
				node.accept(dltSchemeWonInfoVisitor);
				dltSchemeWonInfo=dltSchemeWonInfoVisitor.getDltSchemeWonInfo();
				Boolean canInsert=Boolean.TRUE;//符合条件
				if(null!=dltSchemeWonInfo){
					if(StringUtils.isNotBlank(searchStr)){
						if(StringUtils.isBlank(dltSchemeWonInfo.getSponsorName())){
							canInsert=Boolean.FALSE;
						}
						if(!searchStr.trim().equals(dltSchemeWonInfo.getSponsorName().trim())){
							canInsert=Boolean.FALSE;
						}
					}
					if(null!=state){
						//有选择状态
						if(!state.equals(dltSchemeWonInfo.getState())){
							canInsert=Boolean.FALSE;
						}
					}
					if(null!=won){
						if(won){
							if(!dltSchemeWonInfo.getWon()){
								canInsert=Boolean.FALSE;
							}
						}
					}
					if(null!=mode){
						if(!mode.equals(dltSchemeWonInfo.getMode())){
							canInsert=Boolean.FALSE;
						}
					}
					if(null!=dltAdd){
						if(Integer.valueOf(1).equals(dltAdd)){
							///传进来是普通
							if(null!=dltSchemeWonInfo.getPlayType()){
								if(DltPlayType.GeneralAdditional.equals(dltSchemeWonInfo.getPlayType())){
									canInsert=Boolean.FALSE;
								}
							}
						}else if(Integer.valueOf(2).equals(dltAdd)){
							///传进来是追加
							if(null!=dltSchemeWonInfo.getPlayType()){
								if(DltPlayType.General.equals(dltSchemeWonInfo.getPlayType())||DltPlayType.Select12to2.equals(dltSchemeWonInfo.getPlayType())){
									canInsert=Boolean.FALSE;
								}
							}
						}
					}
					if(canInsert){
						count++;
						dltSchemeWonInfo.setId(count);
						dltSchemeWonInfoList.add(dltSchemeWonInfo);
					}
				}
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
	 * @return the dltSchemeWonInfoList
	 */
	public List<DltSchemeWonInfo> getDltSchemeWonInfoList() {
		return dltSchemeWonInfoList;
	}


	/**
	 * @param dltSchemeWonInfoList the dltSchemeWonInfoList to set
	 */
	public void setDltSchemeWonInfoList(List<DltSchemeWonInfo> dltSchemeWonInfoList) {
		this.dltSchemeWonInfoList = dltSchemeWonInfoList;
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
	 * @return the dltAdd
	 */
	public Integer getDltAdd() {
		return dltAdd;
	}


	/**
	 * @param dltAdd the dltAdd to set
	 */
	public void setDltAdd(Integer dltAdd) {
		this.dltAdd = dltAdd;
	}


	public Boolean getWon() {
		return won;
	}


	public void setWon(Boolean won) {
		this.won = won;
	}


	
	
}
