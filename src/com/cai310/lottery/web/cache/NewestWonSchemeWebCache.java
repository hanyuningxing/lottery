package com.cai310.lottery.web.cache;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.cai310.lottery.entity.lottery.TradeSuccessScheme;
import com.cai310.lottery.service.lottery.impl.QueryManagerImpl;

/**
 * 最新中奖方案缓存支持类
 * 
 * @author jack
 *
 */
@Component("newestWonSchemeCache")
public class NewestWonSchemeWebCache extends AbstractWebCacheSupport {
	
	private int querySize = 6;
	
	@Resource
	private QueryManagerImpl successSchemeManager;
	
	/**
	 * 缓存实例的过期时间 单位（微妙 milliseconds）
	 * @return
	 */
	@Override
	protected int getExpiringTime(){
		return 3*60*60*1000;
	}
	
	@Override
	protected String clientId() {
		return "NewestWonScheme";
	}
	
	@Override
	protected Object findObject() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TradeSuccessScheme.class, "s");
		criteria.add(Restrictions.eq("s.won", true));
		criteria.addOrder(Order.desc("lastModifyTime"));
		List<TradeSuccessScheme> wonSchemeList = successSchemeManager.findByDetachedCriteria(criteria, 0, querySize);
		return wonSchemeList;
	}
	
	public List<TradeSuccessScheme> getWonSchemes(int size){
		if(size>0)querySize = size;
		Object object = super.getObjectFromCache(clientId());
		
		return (List<TradeSuccessScheme>) object;
	}

}
