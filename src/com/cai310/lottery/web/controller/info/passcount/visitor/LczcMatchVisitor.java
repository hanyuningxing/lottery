package com.cai310.lottery.web.controller.info.passcount.visitor;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.web.controller.info.passcount.form.LczcMatch;

public class LczcMatchVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private LczcMatch lczcMatch;
	
    /**
		    <match>
		      <lineId>0</lineId>
		      <homeTeamName>11</homeTeamName>
		      <guestTeamName>22</guestTeamName>
		      <halfResult>3</halfResult>
		      <fullResult>3</fullResult>
		    </match>
     * 
     */
	@Override
	public void visit(Element node){
		if(null==lczcMatch){
			lczcMatch=new LczcMatch();
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("lineId".equals(node.getName())){
				lczcMatch.setLineId(Integer.valueOf(node.getText()));
			}
			if("homeTeamName".equals(node.getName())){
				lczcMatch.setHomeTeamName(node.getText().trim());
			}
			if("guestTeamName".equals(node.getName())){
				lczcMatch.setGuestTeamName(node.getText().trim());
			}
			if("halfResult".equals(node.getName())){
				lczcMatch.setHalfResult(node.getText().trim());
			}
			if("fullResult".equals(node.getName())){
				lczcMatch.setFullResult(node.getText().trim());
			}
		}
	}

	/**
	 * @return the lczcMatch
	 */
	public LczcMatch getLczcMatch() {
		return lczcMatch;
	}

	/**
	 * @param lczcMatch the lczcMatch to set
	 */
	public void setLczcMatch(LczcMatch lczcMatch) {
		this.lczcMatch = lczcMatch;
	}


	


	
}
