package com.cai310.lottery.web.controller.info.passcount.visitor;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.web.controller.info.passcount.form.SfzcData;
import com.cai310.lottery.web.controller.info.passcount.form.SfzcMatch;

public class SfzcDataVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private SfzcData sfzcData;
	private SfzcMatchVisitor sfzcMatchVisitor;
    /**
     <data>
		  <periodData>
		    <firstPrize_9>66666</firstPrize_9>
		    <firstWinUnits_9>0</firstWinUnits_9>
		    <firstPrize_14>10000</firstPrize_14>
		    <firstWinUnits_14>0</firstWinUnits_14>
		    <secondPrize_14>5000</secondPrize_14>
		    <secondWinUnits_14>5000</secondWinUnits_14>
		    
		    <prizePool_9>null</prizePool_9>
		    <prizePool_14>null</prizePool_14>
		    <totalSales_9>null</totalSales_9>
		    <totalSales_14>null</totalSales_14>
		  </periodData>
		  <matchs>
		    <match>
		      <lineId>0</lineId>
		      <homeTeamName>11</homeTeamName>
		      <guestTeamName>22</guestTeamName>
		      <result>3</result>
		    </match>
		    ......
		  </matchs>
		  <time>2010-05-26 22:02</time>
		  <startTime>2010-05-23 22:50</startTime>
		  <endTime>2010-05-22 22:48</endTime>
		</data>
     * 
     */
	@Override
	public void visit(Element node){
		if(null==sfzcData){
			sfzcData=new SfzcData();
		}
		if("match".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getStringValue())){
				if(null==sfzcData.getZcMatchs()){
					sfzcData.setZcMatchs(new SfzcMatch[14]);
				}
				sfzcMatchVisitor=new SfzcMatchVisitor();
				node.accept(sfzcMatchVisitor);
				if(null!=sfzcMatchVisitor.getSfzcMatch()&&null!=sfzcMatchVisitor.getSfzcMatch().getLineId()){
					sfzcData.getZcMatchs()[sfzcMatchVisitor.getSfzcMatch().getLineId()]=sfzcMatchVisitor.getSfzcMatch();
				}
			}
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("firstPrize_9".equals(node.getName())){
				sfzcData.setFirstPrize_9(Integer.valueOf(node.getText()));
			}
			if("firstWinUnits_9".equals(node.getName())){
				sfzcData.setFirstWinUnits_9(Integer.valueOf(node.getText()));
			}
			if("prizePool_9".equals(node.getName())){
				sfzcData.setPrizePool_9(Long.valueOf(node.getText()));
			}
			if("totalSales_9".equals(node.getName())){
				sfzcData.setTotalSales_9(Long.valueOf(node.getText()));
			}
			if("firstPrize_14".equals(node.getName())){
				sfzcData.setFirstPrize_14(Integer.valueOf(node.getText()));
			}
			if("firstWinUnits_14".equals(node.getName())){
				sfzcData.setFirstWinUnits_14(Integer.valueOf(node.getText()));
			}
			if("secondPrize_14".equals(node.getName())){
				sfzcData.setSecondPrize_14(Integer.valueOf(node.getText()));
			}
			if("secondWinUnits_14".equals(node.getName())){
				sfzcData.setSecondWinUnits_14(Integer.valueOf(node.getText()));
			}
			if("prizePool_14".equals(node.getName())){
				sfzcData.setPrizePool_14(Long.valueOf(node.getText()));
			}
			if("totalSales_14".equals(node.getName())){
				sfzcData.setTotalSales_14(Long.valueOf(node.getText()));
			}
			if("time".equals(node.getName())){
				sfzcData.setUpdateTime(node.getText().trim());
			}
			if("startTime".equals(node.getName())){
				sfzcData.setStartTime(node.getText().trim());
			}
			if("endTime".equals(node.getName())){
				sfzcData.setEndTime(node.getText().trim());
			}
			
		}
	}


	


	/**
	 * @return the sfzcData
	 */
	public SfzcData getSfzcData() {
		return sfzcData;
	}


	/**
	 * @param sfzcData the sfzcData to set
	 */
	public void setSfzcData(SfzcData sfzcData) {
		this.sfzcData = sfzcData;
	}


	
}
