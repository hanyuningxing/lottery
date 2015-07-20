package com.cai310.lottery.entity.lottery;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;

@MappedSuperclass
public abstract class SchemeWonInfo implements Serializable {
	private static final long serialVersionUID = 6217335316629493571L;

	protected Long schemeId;

	protected Integer version;
	
	@Id
	@Column(name = "schemeId", nullable = false)
	public Long getSchemeId() {
		return this.schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	@Version
	@Column(name = "version", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
}
