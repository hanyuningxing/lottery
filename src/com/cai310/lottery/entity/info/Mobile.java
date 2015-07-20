package com.cai310.lottery.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.entity.IdEntity;
@Entity
@Table(name = "zj_mobile")
public class Mobile extends IdEntity implements Serializable{

	private static final long serialVersionUID = 444025571952943945L;
	
	/** 手机号码*/
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
