package com.cai310.lottery.web.cache;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.lottery.TradeSuccessScheme;
import com.cai310.lottery.service.lottery.impl.QueryManagerImpl;

/**
 * 累计中奖缓存支持类
 * 
 * @author xing
 *
 */
@Component("bonusAccumulatorCache")
public class BonusAccumulatorWebCache extends AbstractWebCacheSupport {
	@Resource(name="successSchemeManager")
	private QueryManagerImpl successSchemeManager;
	
	
	@Override
	protected String clientId() {
		return "BonusAccumulator";
	}
	
	/**
	 * 缓存实例的过期时间 单位（微妙 milliseconds）
	 * @return
	 */
	@Override
	protected int getExpiringTime(){
		return 24*60*60*1000;
	}
	
	@Override
	protected Object findObject() {	
		//网站保底金额
		long baodi = 5000000;
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TradeSuccessScheme.class, "m");
		criteria.add(Restrictions.eq("m.won", true));
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.sum("m.schemePrizeAfterTax"), "schemePrizeAfterTax");
		criteria.setProjection(prop);
		criteria.setResultTransformer(Transformers.aliasToBean(TradeSuccessScheme.class));
		List<TradeSuccessScheme> wonSchemeList = successSchemeManager.findByDetachedCriteria(criteria, 0, 1);
		if(wonSchemeList.get(0).getSchemePrizeAfterTax()!=null){
			return wonSchemeList.get(0).getSchemePrizeAfterTax().longValue()+baodi;
		} else {
			return Long.valueOf(baodi);
		}
		
	}
	
	public Long getBonusAccumulator(){
		Object object = super.getObjectFromCache(clientId());
		
		return (Long) object;
	}
}
