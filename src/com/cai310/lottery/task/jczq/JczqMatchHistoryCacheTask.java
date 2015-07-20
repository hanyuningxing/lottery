package com.cai310.lottery.task.jczq;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.utils.HttpClientUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
 

public class JczqMatchHistoryCacheTask {
	@Resource(name="matchHisCache")
	private Cache matchHisCache;
	
	private  DecimalFormat df = new  DecimalFormat("000");
	
	public void hitCache(){
		
	
	}
	public static List<String> getKaisaiMatchKey() throws ClientProtocolException, IOException{
		return null;
	}
	
	
}
