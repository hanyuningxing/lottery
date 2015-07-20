package com.cai310.lottery.web.controller.info.passcount.visitor;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.web.controller.info.passcount.form.LczcData;
import com.cai310.lottery.web.controller.info.passcount.form.LczcMatch;

public class LczcDataVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private LczcData lczcData;
	private LczcMatchVisitor lczcMatchVisitor;
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
		      <halfResult>3</halfResult>
		      <fullResult>3</fullResult>
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
		if(null==lczcData){
			lczcData=new LczcData();
		}
		if("match".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getStringValue())){
				if(null==lczcData.getZcMatchs()){
					lczcData.setZcMatchs(new LczcMatch[6]);
				}
				lczcMatchVisitor=new LczcMatchVisitor();
				node.accept(lczcMatchVisitor);
				if(null!=lczcMatchVisitor.getLczcMatch()&&null!=lczcMatchVisitor.getLczcMatch().getLineId()){
					lczcData.getZcMatchs()[lczcMatchVisitor.getLczcMatch().getLineId()]=lczcMatchVisitor.getLczcMatch();
				}
			}
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("firstPrize".equals(node.getName())){
				lczcData.setFirstPrize(Integer.valueOf(node.getText()));
			}
			if("firstWinUnits".equals(node.getName())){
				lczcData.setFirstWinUnits(Integer.valueOf(node.getText()));
			}
			if("prizePool".equals(node.getName())){
				lczcData.setPrizePool(Long.valueOf(node.getText()));
			}
			if("totalSales".equals(node.getName())){
				lczcData.setTotalSales(Long.valueOf(node.getText()));
			}
			if("secondPrize".equals(node.getName())){
				lczcData.setSecondPrize(Integer.valueOf(node.getText()));
			}
			if("secondWinUnits".equals(node.getName())){
				lczcData.setSecondWinUnits(Integer.valueOf(node.getText()));
			}
			if("time".equals(node.getName())){
				lczcData.setUpdateTime(node.getText().trim());
			}
			if("startTime".equals(node.getName())){
				lczcData.setStartTime(node.getText().trim());
			}
			if("endTime".equals(node.getName())){
				lczcData.setEndTime(node.getText().trim());
			}
			
		}
	}


	


	/**
	 * @return the lczcData
	 */
	public LczcData getLczcData() {
		return lczcData;
	}


	/**
	 * @param lczcData the lczcData to set
	 */
	public void setLczcData(LczcData lczcData) {
		this.lczcData = lczcData;
	}


	
}
