package com.cai310.lottery.entity.lottery.ssq;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.lottery.entity.lottery.MissDataInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SSQ_MISS")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SsqMissDataInfo extends MissDataInfo {

	private static final long serialVersionUID = -6940024647174099892L;

//	@Override
//	@Transient
//	public MissDataContent getMissDataContent() {
//		SsqMissDataContent c=(SsqMissDataContent)JSONObject.toBean(JSONObject.fromObject(this.getContent()), SsqMissDataContent.class);
//		if(c==null){
//			c=new SsqMissDataContent();
//		}
//		return c;
//	}
	
	
}
