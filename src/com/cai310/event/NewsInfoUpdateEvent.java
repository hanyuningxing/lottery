package com.cai310.event;

import org.springframework.context.ApplicationEvent;

import com.cai310.lottery.entity.info.NewsInfoData;

/**
 * Description: 新闻消息更新事件<br>
 * 发布消息新闻事件，用于更新新闻HTML；
 * 消息体包括  数据源：NewsInfoData 
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class NewsInfoUpdateEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private NewsInfoData newsInfoData;

	public NewsInfoUpdateEvent(NewsInfoData source) {
		super(source);
		newsInfoData=source;
	}
	
	public NewsInfoData getNewsInfoData() {
		return newsInfoData;
	}

	public void setNewsInfoData(NewsInfoData newsInfoData) {
		this.newsInfoData = newsInfoData;
	}

}
