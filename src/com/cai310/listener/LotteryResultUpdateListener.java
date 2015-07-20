package com.cai310.listener;

import org.mortbay.log.Log;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.cai310.event.LotteryResultUpdateEvent;
import com.cai310.lottery.builder.indexwonscheme.IndexWonSchemeBuilder;
import com.cai310.lottery.builder.lotteryresult.LotteryResultBuilder;
import com.cai310.lottery.exception.DataException;
import com.cai310.spring.SpringContextHolder;

/**
 * Description: 获奖消息更新监听<br>
 * 获取发布获奖消息事件，用于更新获奖消息HTML；
 * Copyright: Copyright (c) 2011<br>
 * Company: 络技术有限公司
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class LotteryResultUpdateListener implements ApplicationListener{

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof LotteryResultUpdateEvent) {
			Log.info("<<<<<<<<<开奖结果消息HTML更新<<<<<<<<<<<<<<<<<");
			LotteryResultBuilder lotteryResultBuilder = SpringContextHolder.getBean("lotteryResultBuilder");
			try {
				lotteryResultBuilder.createNewIndexFile();
			} catch (DataException e) {
				e.printStackTrace();
			}
		}		
	}

}
