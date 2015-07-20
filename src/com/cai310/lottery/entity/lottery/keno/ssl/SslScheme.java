package com.cai310.lottery.entity.lottery.keno.ssl;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.keno.KenoScheme;

@Entity
@Table(name = "LOTTERY_SSL_SCHEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SslScheme extends KenoScheme {

	@Override
	public Lottery getLotteryType() {
		return Lottery.SSL;
	}

	@Override
	public String getContentText() {
		return "";
	}
	
}
