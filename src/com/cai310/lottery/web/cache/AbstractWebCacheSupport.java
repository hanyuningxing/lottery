package com.cai310.lottery.web.cache;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.cache.ClientHistoryCacheHelper;
import com.cai310.lottery.cache.ExpiringCacheEntry;

/**
 * 客户端缓存支持类，提供缓存的支持
 * 
 * @author jack
 *
 */
public abstract class AbstractWebCacheSupport {
	private final static Logger logger = LoggerFactory.getLogger(AbstractWebCacheSupport.class);
	
	@Resource(name="clientHistoryCacheHelper")
	private ClientHistoryCacheHelper clientHistoryCacheHelper;
	
	/**
	 * 缓存实例的过期时间 单位（微妙 milliseconds）
	 * @return
	 */
	protected int getExpiringTime(){
		return 3*60*60*1000;
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	protected abstract Object findObject();
	protected abstract String clientId();
	
	protected Object getObjectFromCache(String clientId){
		if (clientId == null) {
			return null;
		}
		
		Object obj = null;
		ExpiringCacheEntry cacheEntry = clientHistoryCacheHelper.getCache(clientId,ExpiringCacheEntry.class);
		if (cacheEntry != null) {
			obj = cacheEntry.getValue();
			if(obj==null){
				clientHistoryCacheHelper.removeCache(clientId);
			}else{
				return obj;
			}
		}
		
		// 第一次将实例存入缓存
		if(obj==null){
			obj = this.findObject();
			ExpiringCacheEntry newEntry = new ExpiringCacheEntry(obj, getExpiringTime());
			clientHistoryCacheHelper.addCache(clientId(), newEntry);
		}		
		return obj;		
	}
	
}
