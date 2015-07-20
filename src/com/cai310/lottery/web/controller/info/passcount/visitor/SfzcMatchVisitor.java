package com.cai310.lottery.web.controller.info.passcount.visitor;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.web.controller.info.passcount.form.SfzcMatch;

public class SfzcMatchVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private SfzcMatch sfzcMatch;
	
    /**
		    <match>
		      <lineId>0</lineId>
		      <homeTeamName>11</homeTeamName>
		      <guestTeamName>22</guestTeamName>
		      <result>3</result>
		    </match>
     * 
     */
	@Override
	public void visit(Element node){
		if(null==sfzcMatch){
			sfzcMatch=new SfzcMatch();
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("lineId".equals(node.getName())){
				sfzcMatch.setLineId(Integer.valueOf(node.getText()));
			}
			if("homeTeamName".equals(node.getName())){
				sfzcMatch.setHomeTeamName(node.getText().trim());
			}
			if("guestTeamName".equals(node.getName())){
				sfzcMatch.setGuestTeamName(node.getText().trim());
			}
			if("result".equals(node.getName())){
				sfzcMatch.setResult(node.getText().trim());
			}
		}
	}

	/**
	 * @return the sfzcMatch
	 */
	public SfzcMatch getSfzcMatch() {
		return sfzcMatch;
	}

	/**
	 * @param sfzcMatch the sfzcMatch to set
	 */
	public void setSfzcMatch(SfzcMatch sfzcMatch) {
		this.sfzcMatch = sfzcMatch;
	}


	


	
}
