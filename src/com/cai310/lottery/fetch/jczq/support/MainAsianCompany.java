package com.cai310.lottery.fetch.jczq.support;

import org.apache.commons.lang.StringUtils;

public enum MainAsianCompany {
	/** 胜平负 */
	MACAO("1","澳门"),
	SBHUANGGUAN("3","SB/皇冠"),
	LIBO("4", "立博"),
	BET365("8","Bet365"),
	YISHENGBO("12","易胜博"),
	JINBAOBO("23","金宝博");
	
	private MainAsianCompany(String id,String name){
		this.id = id;
		this.name = name;
	}
	
	private String id;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static MainAsianCompany getCompanyByCompanyId(String id) {
		if (StringUtils.isNotBlank(id)) {
			for (MainAsianCompany company : MainAsianCompany.values()) {
				if(company.getId().equals(id.trim())){
					return company;
				}
			}
		}
		return null;
	}
	
	public static boolean isContained(String id) {
		if (StringUtils.isNotBlank(id)) {
			for (MainAsianCompany company : MainAsianCompany.values()) {
				if (company.getId().equals(id))
					return true;
			}
		}
		return false;
	}
}
