package com.cai310.lottery.web.controller.info.analyse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.AhKuai3Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.keno.ahkuai3.impl.AhKuai3MissDataControllerServiceImpl;

/**
 * 快3走势
 * <p>
 * Title: AhKuai3AnalyseController.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: miracle
 * </p>
 * 
 * @author leo
 * @date 2014-1-8 上午10:05:50
 * @version 1.0
 */
@Namespace("/" + AhKuai3Constant.KEY)
@Action(value = "analyse")
@Results({
		@Result(name = "index", location = "/WEB-INF/content/analyse/"
				+ AhKuai3Constant.KEY + "/index.ftl"),
		@Result(name = "hezhi", location = "/WEB-INF/content/analyse/"
				+ AhKuai3Constant.KEY + "/hezhi.ftl") })
public class AhKuai3AnalyseController extends AnalyseController {

	private static final long serialVersionUID = -7987486330454408440L;

	@Autowired
	private AhKuai3MissDataControllerServiceImpl ahKuai3MissDataControllerServiceImpl;

	@Override
	protected MissDataControllerService getSchemeService() {
		return ahKuai3MissDataControllerServiceImpl;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.AHKUAI3;
	}

}
