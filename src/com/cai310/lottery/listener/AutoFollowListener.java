package com.cai310.lottery.listener;

import java.util.Map;

import org.hibernate.event.SaveOrUpdateEvent;

import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.entity.lottery.AutoFollowQueue;
import com.cai310.lottery.entity.lottery.AutoFollowQueueId;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.service.lottery.AutoFollowEntityManager;
import com.cai310.orm.hibernate.ExtensionSaveOrUpdateCallBack;
import com.cai310.orm.hibernate.ExtensionSaveOrUpdateEventListener;
import com.cai310.spring.SpringContextHolder;

/**
 * 自动跟单监听器
 * 
 */
public class AutoFollowListener implements ExtensionSaveOrUpdateEventListener {

	protected AutoFollowEntityManager autoFollowEntityManager;

	private AutoFollowEntityManager getAutoFollowEntityManager() {
		init();
		return autoFollowEntityManager;
	}

	@SuppressWarnings("unchecked")
	private void init() {
		if (autoFollowEntityManager == null) {
			Map map = SpringContextHolder.getApplicationContext().getBeansOfType(AutoFollowEntityManager.class);
			if (map != null && !map.isEmpty()) {
				if (map.size() > 1)
					throw new RuntimeException("找到多个不同的[" + AutoFollowEntityManager.class.getName() + "]实例，无法确定使用哪一个.");

				autoFollowEntityManager = (AutoFollowEntityManager) map.values().iterator().next();
			} else {
				throw new RuntimeException("找不到[" + AutoFollowEntityManager.class.getName() + "]实例.");
			}
		}
	}

	public ExtensionSaveOrUpdateCallBack preSaveOrUpdate(SaveOrUpdateEvent event) {
		Object object = event.getObject();
		if (object instanceof Scheme) {
			Scheme scheme = (Scheme) event.getObject();
			if (scheme.getId() == null) {
				if (scheme.getShareType() == ShareType.TOGETHER) {
					boolean hasFollow = getAutoFollowEntityManager().hasAutoFollow(scheme.getSponsorId(),
							scheme.getLotteryType());
					if (hasFollow) {
						scheme.setAutoFollowCompleted(false);// 设置跟单未完成，不允许前台认购方案

						// 创建一条跟单任务到任务表
						return new ExtensionSaveOrUpdateCallBack() {
							public void run(SaveOrUpdateEvent event) {
								Scheme scheme = (Scheme) event.getEntity();

								AutoFollowQueueId id = new AutoFollowQueueId();
								id.setSchemeId(scheme.getId());
								id.setLotteryType(scheme.getLotteryType());
								AutoFollowQueue queue = new AutoFollowQueue();
								queue.setId(id);
								queue = autoFollowEntityManager.saveAutoFollowQueue(queue);
							}
						};
					}
				}
				scheme.setAutoFollowCompleted(true);
			}
		}
		return null;
	}
}
