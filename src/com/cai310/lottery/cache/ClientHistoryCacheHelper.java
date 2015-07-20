package com.cai310.lottery.cache;

import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("clientHistoryCacheHelper")
public class ClientHistoryCacheHelper {
	private static final Logger logger = LoggerFactory.getLogger(ClientHistoryCacheHelper.class);
	
	@Resource(name="clientHistoryCache")
	private Cache clientHistoryCache;

	/**
	 * 添加缓存对象
	 * 
	 * @param key 缓存KEY
	 * @param cacheObj 缓存对象
	 */
	public void addCache(String key, Object cacheObj) {
		clientHistoryCache.put(new Element(key, cacheObj));
		if (logger.isDebugEnabled()) {
			logger.debug("添加KEY为{}的缓存对象,当前缓存{}个", key, clientHistoryCache.getSize());
		}
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key 缓存KEY
	 * @return 缓存对象
	 */
	public Object getCache(String key) {
		Element element = clientHistoryCache.get(key);
		if (element == null) {
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("获取KEY为{}的缓存对象", key);
		}
		return (Object) element.getValue();

	}

	/**
	 * 获取指定类型缓存对象
	 * 
	 * @param <T> 指定类型
	 * @param key  缓存KEY
	 * @param classz 类型Class
	 * @return 指定类型缓存对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCache(String key, Class<T> classz) {
		if (getCache(key) == null) {
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("获取KEY为{}且类型为{}的缓存对象", key, classz.getName());
		}
		return (T) getCache(key);
	}

	/**
	 * 获取指定类型LIST缓存
	 * 
	 * @param <T>  指定类型
	 * @param key 缓存KEY
	 * @param classz 类型Class
	 * @return 指定类型LIST缓存
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListCache(String key, Class<T> classz) {
		if (getCache(key) == null) {
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("获取KEY为{}且类型为{}的LIST缓存", key, classz.getName());
		}
		return (List<T>) getCache(key);
	}

	/**
	 * 移除指定KEY缓存对象
	 * 
	 * @param key 缓存KEY
	 */
	public void removeCache(String key) {
		if (getCache(key) != null) {
			clientHistoryCache.remove(key);
			if (logger.isDebugEnabled()) {
				logger.debug("移除取KEY为{}的缓存对象", key);
			}
		}
	}
}
