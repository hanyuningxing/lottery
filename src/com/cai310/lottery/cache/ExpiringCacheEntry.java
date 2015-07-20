/*
 * 缓存中的实例过期封装类
 * 
 *
 */
package com.cai310.lottery.cache;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 将实例封装于其中，包含了存入开始时间，以及判断是否属于过期实例
 * @author Linj
 * 
 */
public class ExpiringCacheEntry implements Serializable {	
    
	private static final long serialVersionUID = -7436745241139455428L;
	private final static Logger logger = LoggerFactory.getLogger(ExpiringCacheEntry.class);
	
	private Object value;
    private long timeCached = -1;
    private long timeout = 0;
    
    
    public ExpiringCacheEntry(Object value, long timeout) {
        this.value = value;
        
        // make sure that we don't support negative values
        if(timeout > 0) {
            this.timeout = timeout;
        }
        
        this.timeCached = System.currentTimeMillis();
    }
    
    
    public long getTimeCached() {
        return this.timeCached;
    }
    
    
    public long getTimeout() {
        return this.timeout;
    }
    
    
    /**
     * 查询缓存中的实例
     * 
     * 如果过期返回空
     */
    public Object getValue() {
        if(this.hasExpired()) {
            return null;
        } else {
            return this.value;
        }
    }
    
    
    /**
     * 判断实例是否过期
     */
    public boolean hasExpired() {
        
        long now = System.currentTimeMillis();
        logger.debug("=====timeCacheed:"+timeCached +" timeout:"+ this.timeout + " now:" +now);
        
        return ((this.timeCached + this.timeout) < now);
    }
    
}