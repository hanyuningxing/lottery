package com.cai310.lottery.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.event.SaveOrUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.activity.AvtivityConfig;
import com.cai310.lottery.service.activity.AvtivityService;
import com.cai310.lottery.service.lottery.PrintService;
import com.cai310.orm.hibernate.ExtensionSaveOrUpdateCallBack;
import com.cai310.orm.hibernate.ExtensionSaveOrUpdateEventListener;
import com.cai310.spring.SpringContextHolder;

@SuppressWarnings("unchecked")
public class SendSchemeToPrintListener implements ExtensionSaveOrUpdateEventListener {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private ConcurrentHashMap<Lottery, PrintService> printServiceMap;
	private AvtivityService avtivityService;
	private void init() {
		if (printServiceMap == null) {
			printServiceMap = new ConcurrentHashMap<Lottery, PrintService>();
			Map map = SpringContextHolder.getApplicationContext().getBeansOfType(PrintService.class);
			if (map != null && !map.isEmpty()) {
				for (Object obj : map.values()) {
					PrintService ps = (PrintService) obj;
					if (printServiceMap.containsKey(ps.getLotteryType())) {
						throw new RuntimeException("彩种[" + ps.getLotteryType().getLotteryName() + "]存在一个以上的打印服务实例.");
					}
					printServiceMap.put(ps.getLotteryType(), ps);
				}
			}
		}
	}
	private AvtivityService getAvtivityService() {
		if (avtivityService == null) {
			Map map = SpringContextHolder.getApplicationContext().getBeansOfType(AvtivityService.class);
			if (map != null && !map.isEmpty()) {
				for (Object obj : map.values()) {
					avtivityService =  (AvtivityService)obj;
				}
			}
		}
		return avtivityService;
	}
	private PrintService getPrintService(Lottery lotteryType) {
		this.init();
		return this.printServiceMap.get(lotteryType);
	}

	public ExtensionSaveOrUpdateCallBack preSaveOrUpdate(SaveOrUpdateEvent event) {
		if (event.getObject() instanceof Scheme) {
			Scheme scheme = (Scheme) event.getObject();
			if (scheme.canSendToPrint()) {
				final PrintService ps = getPrintService(scheme.getLotteryType());
				if (ps == null)
					throw new RuntimeException("彩种[" + scheme.getLotteryType().getLotteryName()
							+ "]没有打印服务实例,无法发送方案去打印.");

				scheme.setSendToPrint(true);
				return new ExtensionSaveOrUpdateCallBack() {
					public void run(SaveOrUpdateEvent event) {
						// 以后可以改成异步的方式
						// 以后可以改成异步的方式
						//增加马甲功能
						Scheme scheme = (Scheme) event.getEntity();
						ps.sendToPrint(scheme);
					}
				};
			}
		}
		return null;
	}

}
