package com.cai310.lottery.cache;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.entity.football.Company;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.fetch.DataBlock;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.fetch.ValueItem;
import com.cai310.lottery.fetch.jczq.JczqContextHolder;
import com.cai310.lottery.service.football.OddsManager;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;

@Component
public class CompanyLocalCache {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private OddsManager oddsManager;

	private List<Company> companyLocalCache;

	@PostConstruct
	public void init() {
		 companyLocalCache = oddsManager.getAllCompanyList();
	}

	public List<Company> getCompanyLocalCache() {
		return companyLocalCache;
	}

	public void setCompanyLocalCache(List<Company> companyLocalCache) {
		this.companyLocalCache = companyLocalCache;
	}
	
}
