package com.cai310.lottery.fetch.jczq.matchinfo;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.fetch.jczq.local.EuropeOdds;
import com.cai310.lottery.utils.StringUtil;
import com.google.common.collect.Lists;

public class EuroOddsVisitor extends VisitorSupport {

	private String remoteId;
	private List<EuropeOdds> oddsList;

	public void visit(Element node) {
		if ("id".equals(node.getName())) {
			if (StringUtils.isNotBlank(node.getText())) {
				remoteId = node.getText().trim();
			}
		}
		if("odds".equals(node.getName())) {
			oddsList = Lists.newArrayList();
			Element e;
			for(Iterator i = node.elementIterator();i.hasNext();) {
				e = (Element) i.next();
				if("o".equals(e.getName())) {
					if (StringUtils.isNotBlank(e.getText())) {
						String[] oddsArray = e.getText().trim().split(",");
						EuropeOdds europeOdds = new EuropeOdds();
						if(StringUtil.isAllNotEmpty(oddsArray[5])) {
							europeOdds.setWinSp(Double.valueOf(oddsArray[5].trim()));
						} else if(StringUtil.isAllNotEmpty(oddsArray[2])) {
							europeOdds.setWinSp(Double.valueOf(oddsArray[2].trim()));
						}
						if(StringUtil.isAllNotEmpty(oddsArray[6])) {
							europeOdds.setDrawSp(Double.valueOf(oddsArray[6].trim()));
						} else if(StringUtil.isAllNotEmpty(oddsArray[3])) {
							europeOdds.setDrawSp(Double.valueOf(oddsArray[3].trim()));
						}
						if(StringUtil.isAllNotEmpty(oddsArray[7])) {
							europeOdds.setLoseSp(Double.valueOf(oddsArray[7].trim()));
						} else if(StringUtil.isAllNotEmpty(oddsArray[4])) {
							europeOdds.setLoseSp(Double.valueOf(oddsArray[4].trim()));
						}
						oddsList.add(europeOdds);
					}
				}
			}
		}
	}

	public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}

	public List<EuropeOdds> getOddsList() {
		return oddsList;
	}

	public void setOddsList(List<EuropeOdds> oddsList) {
		this.oddsList = oddsList;
	}
	
	public String getPrintString() {
		StringBuilder value = new StringBuilder("比赛：" + remoteId + "，赔率：{");
		for(EuropeOdds odds : oddsList) {
			value.append("[" + odds.getWinSp() + "," + odds.getDrawSp() + "," + odds.getLoseSp() + "]");
		}
		value.append("}");
		return value.toString();
	}

}
