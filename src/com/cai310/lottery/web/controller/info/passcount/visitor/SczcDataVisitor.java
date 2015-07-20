package com.cai310.lottery.web.controller.info.passcount.visitor;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.web.controller.info.passcount.form.SczcData;
import com.cai310.lottery.web.controller.info.passcount.form.SczcMatch;

public class SczcDataVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private SczcData sczcData;
	private SczcMatchVisitor sczcMatchVisitor;
    /**
     <data>
		  <periodData>
		    <firstPrize>66666</firstPrize>
		    <firstWinUnits>0</firstWinUnits>
		    <secondPrize>5000</secondPrize>
		    <secondWinUnits>5000</secondWinUnits>
		    <prizePool></prizePool>
		    <totalSales></totalSales>
		  </periodData>
		  <matchs>
		    <match>
		      <lineId>0</lineId>
		      <homeTeamName>11</homeTeamName>
		      <guestTeamName>22</guestTeamName>
		      <homeResult>3</homeResult>
		      <guestResult>3</guestResult>
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
		if(null==sczcData){
			sczcData=new SczcData();
		}
		if("match".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getStringValue())){
				if(null==sczcData.getZcMatchs()){
					sczcData.setZcMatchs(new SczcMatch[4]);
				}
				sczcMatchVisitor=new SczcMatchVisitor();
				node.accept(sczcMatchVisitor);
				if(null!=sczcMatchVisitor.getSczcMatch()&&null!=sczcMatchVisitor.getSczcMatch().getLineId()){
					sczcData.getZcMatchs()[sczcMatchVisitor.getSczcMatch().getLineId()]=sczcMatchVisitor.getSczcMatch();
				}
			}
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("firstPrize".equals(node.getName())){
				sczcData.setFirstPrize(Integer.valueOf(node.getText()));
			}
			if("firstWinUnits".equals(node.getName())){
				sczcData.setFirstWinUnits(Integer.valueOf(node.getText()));
			}
			if("prizePool".equals(node.getName())){
				sczcData.setPrizePool(Long.valueOf(node.getText()));
			}
			if("totalSales".equals(node.getName())){
				sczcData.setTotalSales(Long.valueOf(node.getText()));
			}
			if("secondPrize".equals(node.getName())){
				sczcData.setSecondPrize(Integer.valueOf(node.getText()));
			}
			if("secondWinUnits".equals(node.getName())){
				sczcData.setSecondWinUnits(Integer.valueOf(node.getText()));
			}
			if("time".equals(node.getName())){
				sczcData.setUpdateTime(node.getText().trim());
			}
			if("startTime".equals(node.getName())){
				sczcData.setStartTime(node.getText().trim());
			}
			if("endTime".equals(node.getName())){
				sczcData.setEndTime(node.getText().trim());
			}
			
		}
	}


	


	/**
	 * @return the sczcData
	 */
	public SczcData getSczcData() {
		return sczcData;
	}


	/**
	 * @param sczcData the sczcData to set
	 */
	public void setSczcData(SczcData sczcData) {
		this.sczcData = sczcData;
	}


	
}
