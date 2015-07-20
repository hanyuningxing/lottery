package com.cai310.listener;

import org.mortbay.log.Log;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.cai310.event.NewsInfoUpdateEvent;
import com.cai310.lottery.builder.indexnews.IndexNewsBuilder;
import com.cai310.lottery.builder.indexwonscheme.IndexWonSchemeBuilder;
import com.cai310.lottery.builder.lotteryresult.LotteryResultBuilder;
import com.cai310.lottery.builder.newsinfo.NewsInfoBuilder;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.exception.DataException;
import com.cai310.spring.SpringContextHolder;

/**
 * Description: 新闻消息更新监听<br>
 * 获取发布消息新闻事件，用于更新新闻HTML； 消息体包括 数据源：ApplicationEvent Copyright: Copyright (c)
 * 2011<br>
 * Company: 网络技术有限公司
 * 
 * @author zhuhui 2011-03-16 编写
 * @version 1.0
 */  
public class NewsInfoUpdateListener implements ApplicationListener {

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof NewsInfoUpdateEvent) {
			NewsInfoUpdateEvent newsInfoUpdateEvent = (NewsInfoUpdateEvent) event;
			NewsInfoBuilder newsInfoBuilder = SpringContextHolder.getBean("newsInfoBuilder");
			IndexNewsBuilder indexNewsBuilder = SpringContextHolder.getBean("indexNewsBuilder");
			try {
				Lottery lottery = newsInfoUpdateEvent.getNewsInfoData().getLotteryType();
				InfoType infoType = newsInfoUpdateEvent.getNewsInfoData().getType();
				InfoSubType infoSubType = newsInfoUpdateEvent.getNewsInfoData().getSubType();
				Integer isNotice = newsInfoUpdateEvent.getNewsInfoData().getIsNotice();
				// 更新彩种模块——预测
				if (infoType.equals(infoType.FORECAST)) {
					Log.info("<<<<<<<<<彩种模块——预测HTML更新<<<<<<<<<<<<<<<<<");
					newsInfoBuilder.createIndexNewsFile(lottery);
				}
				// 更新新手成长之路--技巧
				if (infoType.equals(infoType.SKILLS)) {
					Log.info("<<<<<<<<<新手成长之路--技巧HTML更新<<<<<<<<<<<<<<<<<");
					newsInfoBuilder.createIndexSkillsFile();
				}
				//更新公告通知--公告
				if (null != isNotice && isNotice.intValue() == NewsInfoData.NOTICE) {
					Log.info("<<<<<<<<<公告信息HTML更新<<<<<<<<<<<<<<<<<");
					indexNewsBuilder.createNewIndexFile();
				}
				
				//更新北京单场 频道新闻    条件：彩种为Lottery.DCZC 、类型为infoType.FORECAST 或infoType.INFO
				if (null != lottery && lottery.equals(Lottery.DCZC)&&(infoType.equals(infoType.FORECAST)||infoType.equals(infoType.INFO))) {
					Log.info("<<<<<<<<<更新北京单场 频道新闻HTML更新<<<<<<<<<<<<<<<<<");
					newsInfoBuilder.createDCZCFile(infoType,null);
				}
			} catch (DataException e) {
				e.printStackTrace();
			}
		}
	}

}
