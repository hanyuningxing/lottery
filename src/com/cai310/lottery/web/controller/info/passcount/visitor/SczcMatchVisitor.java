package com.cai310.lottery.web.controller.info.passcount.visitor;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.web.controller.info.passcount.form.SczcMatch;

public class SczcMatchVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	private SczcMatch sczcMatch;
	
    /**
		    <match>
		      <lineId>0</lineId>
		      <homeTeamName>11</homeTeamName>
		      <guestTeamName>22</guestTeamName>
		      <homeResult>3</homeResult>
		      <guestResult>3</guestResult>
		    </match>
     * 
     */
	@Override
	public void visit(Element node){
		if(null==sczcMatch){
			sczcMatch=new SczcMatch();
		}
		if(StringUtils.isNotBlank(node.getText())){
			if("lineId".equals(node.getName())){
				sczcMatch.setLineId(Integer.valueOf(node.getText()));
			}
			if("homeTeamName".equals(node.getName())){
				sczcMatch.setHomeTeamName(node.getText().trim());
			}
			if("guestTeamName".equals(node.getName())){
				sczcMatch.setGuestTeamName(node.getText().trim());
			}
			if("homeResult".equals(node.getName())){
				sczcMatch.setHomeResult(node.getText().trim());
			}
			if("guestResult".equals(node.getName())){
				sczcMatch.setGuestResult(node.getText().trim());
			}
		}
	}

	/**
	 * @return the sczcMatch
	 */
	public SczcMatch getSczcMatch() {
		return sczcMatch;
	}

	/**
	 * @param sczcMatch the sczcMatch to set
	 */
	public void setSczcMatch(SczcMatch sczcMatch) {
		this.sczcMatch = sczcMatch;
	}


	


	
}
