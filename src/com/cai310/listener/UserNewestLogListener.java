package com.cai310.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.cai310.event.UserNewestLogEvent;
import com.cai310.lottery.service.user.UserNewestLogManager;
import com.cai310.lottery.support.Executable;
import com.cai310.lottery.support.ExecuteException;
import com.cai310.lottery.support.ExecutorUtils;

/**
 * 用户最新动态信息
 * @author jack
 *
 */
public class UserNewestLogListener implements ApplicationListener{
	private final static Logger logger = LoggerFactory.getLogger(UserNewestLogListener.class);
	
	@Resource
	private UserNewestLogManager userNewestLogManger;
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof UserNewestLogEvent){
			final UserNewestLogEvent userNewestLogEvent = (UserNewestLogEvent)event;
			new Thread(){
				public void run(){
					try {
						ExecutorUtils.exec(new Executable() {
							public void run() throws ExecuteException {
								userNewestLogManger.saveLog(userNewestLogEvent.getUserNewestLog());
								logger.debug("user event newestLog save!");
							}
						}, 3);
					} catch (ExecuteException e) {
						e.printStackTrace();
						logger.debug("user event newestLog save error!..."+e.getMessage());
					} catch(Exception e){
						e.printStackTrace();
						logger.debug("user event newestLog save error!..."+e.getMessage());
					}
				};
			}.start();
		}
	}
}
