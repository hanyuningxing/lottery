package com.cai310.listener;

import org.mortbay.log.Log;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.cai310.event.WonSchemeUpdateEvent;
import com.cai310.lottery.builder.indexwonscheme.IndexWonSchemeBuilder;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.spring.SpringContextHolder;

/**
 * Description: 获奖消息更新监听<br>
 * 获取发布获奖消息事件，用于更新获奖消息HTML；
 * @Copyright: Copyright (c) 2011<br>
 * @Company: 网络技术有限公司
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class WonSchemeUpdateListener implements ApplicationListener{
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof WonSchemeUpdateEvent) {
			WonSchemeUpdateEvent wonSchemeUpdateEvent = (WonSchemeUpdateEvent) event;
			Log.info("<<<<<<<<<中奖消息首页HTML更新<<<<<<<<<<<<<<<<<");
			IndexWonSchemeBuilder indexWonSchemeBuilder = SpringContextHolder.getBean("indexWonSchemeBuilder");
			try {
				indexWonSchemeBuilder.createNewIndexFile();
				if(wonSchemeUpdateEvent.getLottery().equals(Lottery.DCZC)) {
					Log.info("<<<<<<<<<中奖消息  单场足彩 HTML更新<<<<<<<<<<<<<<<<<");
					indexWonSchemeBuilder.createNewWonDczcSchemeFile();
				}
			} catch (DataException e) {
				e.printStackTrace();
			}
		}		
	}

}
