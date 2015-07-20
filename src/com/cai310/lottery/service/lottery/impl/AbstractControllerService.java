package com.cai310.lottery.service.lottery.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.CurrentWorkHelper;
import com.cai310.lottery.service.lottery.ControllerService;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;

public abstract class AbstractControllerService implements ControllerService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// ===============================================================================================

	protected static final Map<Lottery, CurrentWorkHelper> workHelperMap = new HashMap<Lottery, CurrentWorkHelper>();

	public static CurrentWorkHelper getCurrentWorkHelper(Lottery lotteryType) {
		return workHelperMap.get(lotteryType);
	}

	@PostConstruct
	protected void initWorkHelperMap() {
		synchronized (workHelperMap) {
			workHelper = workHelperMap.get(lotteryType);
			if (workHelper == null) {
				workHelper = new CurrentWorkHelper();
				workHelperMap.put(lotteryType, workHelper);
			}
		}
	}

	// ===============================================================================================

	
	@Resource
	protected PeriodEntityManager periodManager;

	protected SchemeEntityManager schemeManager;

	protected SchemeService schemeService;
	
	protected Lottery lotteryType;

	protected CurrentWorkHelper workHelper;

	/**
	 * 通过spring配置注入进来
	 * 
	 * @param schemeManager
	 */
	public void setSchemeManager(SchemeEntityManager schemeManager) {
		this.schemeManager = schemeManager;
	}

	/**
	 * 通过spring配置注入进来
	 * 
	 * @param lotteryType the lotteryType to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	 * 通过spring配置注入进来
	 * 
	 * @param schemeService the schemeService to set
	 */
	public void setSchemeService(SchemeService schemeService) {
		this.schemeService = schemeService;
	}

	/**
	 * @return #{@link periodManager}
	 */
	public PeriodEntityManager getPeriodManager() {
		return periodManager;
	}

	protected SchemeEntityManager getSchemeManager() {
		return schemeManager;
	}

	/**
	 * @return #{@link schemeService}
	 */
	protected SchemeService getSchemeService() {
		return schemeService;
	}

	/**
	 * @return #{@link lotteryType}
	 */
	public Lottery getLotteryType() {
		return lotteryType;
	}

}
