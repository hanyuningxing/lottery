package com.cai310.lottery.fetch.jczq.matchinfo;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.common.Lottery;

public class MatchinfoVisitor extends VisitorSupport {

	private Lottery lottery;
	private String matchKey;
	private String remoteId;
	private String remoteHomeId;
	private String remoteGuestId;
	private Boolean ifTurn;
	
	private String leagueId;

	public void visit(Element node) {
		if ("LotteryName".equals(node.getName())) {
			if (StringUtils.isNotBlank(node.getText())) {
				if (node.getText().trim().equals("竞彩足球")) {
					lottery = Lottery.JCZQ;
				} else {
					lottery = null;
				}
			}
		}
		if ("ID".equals(node.getName())) {
			if (StringUtils.isNotBlank(node.getText())) {
				matchKey = node.getText().trim();
			}
		}
		if ("ID_bet007".equals(node.getName()) || "a".equals(node.getName())) {
			if (StringUtils.isNotBlank(node.getText())) {
				remoteId = node.getText().trim();
			}
		}
		if ("HomeID".equals(node.getName())) {
			if (StringUtils.isNotBlank(node.getText())) {
				remoteHomeId = node.getText().trim();
			}
		}
		if ("AwayID".equals(node.getName())) {
			if (StringUtils.isNotBlank(node.getText())) {
				remoteGuestId = node.getText().trim();
			}
		}
		if ("Turn".equals(node.getName())) {
			if (StringUtils.isNotBlank(node.getText())) {
				if (node.getText().trim().equalsIgnoreCase("true")) {
					ifTurn = true;
				} else {
					ifTurn = false;
				}
			}
		}
		
		if("c".equals(node.getName())) {
			if (StringUtils.isNotBlank(node.getText())) {
				String[] values = node.getText().trim().split(",");
				leagueId = values[3].trim();
			}
		}
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}

	public String getRemoteHomeId() {
		return remoteHomeId;
	}

	public void setRemoteHomeId(String remoteHomeId) {
		this.remoteHomeId = remoteHomeId;
	}

	public String getRemoteGuestId() {
		return remoteGuestId;
	}

	public void setRemoteGuestId(String remoteGuestId) {
		this.remoteGuestId = remoteGuestId;
	}

	public Boolean getIfTurn() {
		return ifTurn;
	}

	public void setIfTurn(Boolean ifTurn) {
		this.ifTurn = ifTurn;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

}
