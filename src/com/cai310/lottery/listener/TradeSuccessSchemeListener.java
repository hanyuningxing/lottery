package com.cai310.lottery.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.event.SaveOrUpdateEvent;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.orm.hibernate.ExtensionSaveOrUpdateCallBack;
import com.cai310.orm.hibernate.ExtensionSaveOrUpdateEventListener;
import com.cai310.spring.SpringContextHolder;

/**
 * 记录交易成功方案的监听器
 * @author jack
 *
 */
public class TradeSuccessSchemeListener implements ExtensionSaveOrUpdateEventListener {

	protected SchemeService schemeService;
	protected KenoManager kenoManager;
	private ConcurrentHashMap<Lottery, SchemeService> schemeServiceMap;
	private ConcurrentHashMap<Lottery, KenoManager> kenoManagerMap;

	private void init() {
		if (schemeServiceMap == null) {
			schemeServiceMap = new ConcurrentHashMap<Lottery, SchemeService>();
			Map map = SpringContextHolder.getApplicationContext().getBeansOfType(SchemeService.class);
			if (map != null && !map.isEmpty()) {
				for (Object obj : map.values()) {
					SchemeService ss = (SchemeService) obj;
					if (schemeServiceMap.containsKey(ss.getLotteryType())) {
						throw new RuntimeException("彩种[" + ss.getLotteryType().getLotteryName() + "]存在一个以上的方案服务实例.");
					}
					schemeServiceMap.put(ss.getLotteryType(), ss);
				}
			}
		}		
	}
	
	private void init_keno(){
		if (kenoManagerMap == null) {
			kenoManagerMap = new ConcurrentHashMap<Lottery, KenoManager>();
			Map map = SpringContextHolder.getApplicationContext().getBeansOfType(KenoManager.class);
			if (map != null && !map.isEmpty()) {
				for (Object obj : map.values()) {
					KenoManager km = (KenoManager) obj;
					if (kenoManagerMap.containsKey(km.getLottery())) {
						throw new RuntimeException("彩种[" + km.getLottery().getLotteryName() + "]存在一个以上的方案服务实例.");
					}
					kenoManagerMap.put(km.getLottery(), km);
				}
			}
		}
	}
	
	private SchemeService getSchemeService(Lottery lotteryType) {
		this.init();
		return this.schemeServiceMap.get(lotteryType);
	}
	
	private KenoManager getKenoManager(Lottery lotteryType){
		this.init_keno();
		return this.kenoManagerMap.get(lotteryType);
	}

	public ExtensionSaveOrUpdateCallBack preSaveOrUpdate(SaveOrUpdateEvent event) {
		Object object = event.getObject();
//		if (object instanceof Scheme) {
//			Scheme scheme = (Scheme) event.getObject();
//			if(Lottery.isKeno(scheme.getLotteryType())){
//				KenoManager km = getKenoManager(scheme.getLotteryType());
//				if(km==null){
//					throw new RuntimeException("彩种[" + scheme.getLotteryType().getLotteryName() + "]没有方案服务实例,无法保存交易成功的方案.");
//				}
//				//高频彩不分状态，都进行记录(高频一发起就是满员)
//				if(true){
//					return new ExtensionSaveOrUpdateCallBack() {
//						public void run(SaveOrUpdateEvent event) {
//							NumberScheme scheme = (NumberScheme) event.getEntity();
//							getKenoManager(scheme.getLotteryType()).saveTradeSuccessScheme(scheme);
//						}
//					};
//				}
//			}else{
//				SchemeService ss = getSchemeService(scheme.getLotteryType());
//				if(ss==null){
//					throw new RuntimeException("彩种[" + scheme.getLotteryType().getLotteryName() + "]没有方案服务实例,无法保存交易成功的方案.");
//				}
//				//方案成功且已更新中奖
//				if (SchemeState.SUCCESS.equals(scheme.getState()) && !WinningUpdateStatus.NONE.equals(scheme.getWinningUpdateStatus())) {
//					return new ExtensionSaveOrUpdateCallBack() {
//						public void run(SaveOrUpdateEvent event) {
//							Scheme scheme = (Scheme) event.getEntity();
//							getSchemeService(scheme.getLotteryType()).saveTradeSuccessScheme(scheme);
//						}
//					};
//				}
//			}
//		}
		return null;
	}
}
