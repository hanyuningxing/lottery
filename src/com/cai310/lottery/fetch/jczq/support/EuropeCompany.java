package com.cai310.lottery.fetch.jczq.support;

import org.apache.commons.lang.StringUtils;

public enum EuropeCompany {
	/** 胜平负 */
	LIBO("4", "立博"),
	BET365("8","Bet365"),
	MACAO("1","澳门"),
	WILLIAM("9","威廉"),
	INTERWETTEN("19","Interwetten"),
	SNAI("7","SNAI");
	
	private EuropeCompany(String id,String name){
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
	
	public static EuropeCompany getCompanyByCompanyId(String id) {
		if (StringUtils.isNotBlank(id)) {
			for (EuropeCompany company : EuropeCompany.values()) {
				if(company.getId().equals(id.trim())){
					return company;
				}
			}
		}
		return null;
	}
	
	public static boolean isContained(String id) {
		if (StringUtils.isNotBlank(id)) {
			for (EuropeCompany company : EuropeCompany.values()) {
				if (company.getId().equals(id))
					return true;
			}
		}
		return false;
	}
}
