package com.cai310.lottery.fetch.jczq.support;

import org.apache.commons.lang.StringUtils;

public enum AsianCompany {
	/** 胜平负 */
	MACAO("1","澳门"),
	BOYIN("2","波音"),
	SBHUANGGUAN("3","SB/皇冠"),
	LIBO("4", "立博"),
	YUNDING("5","云鼎"),
	BET365("8","Bet365"),
	YISHENGBO("12","易胜博"),
	WEIDE("14","韦德"),
	MINGSHENG("17","明陞"),
	JINBAOBO("23","金宝博"),
	SHABA("24","12bet/沙巴"),
	LETIANTANG("29","乐天堂"),
	LIJI("31","利记"),
	YONGLIGAO("33","永利高"),
	YINGHE("35","盈禾");
	
	private AsianCompany(String id,String name){
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
	
	public static AsianCompany getCompanyByCompanyId(String id) {
		if (StringUtils.isNotBlank(id)) {
			for (AsianCompany company : AsianCompany.values()) {
				if(company.getId().equals(id.trim())){
					return company;
				}
			}
		}
		return null;
	}
	
	public static boolean isContained(String id) {
		if (StringUtils.isNotBlank(id)) {
			for (AsianCompany company : AsianCompany.values()) {
				if (company.getId().equals(id))
					return true;
			}
		}
		return false;
	}
}
