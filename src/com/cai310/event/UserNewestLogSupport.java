package com.cai310.event;

import com.cai310.lottery.common.UserNewestType;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.entity.user.UserNewestLog;
import com.cai310.spring.SpringContextHolder;

/**
 * 用户最新动态日志记录支持类
 * @author jack
 *
 */
public class UserNewestLogSupport {

	/**
	 * 用户发单日志
	 * @param scheme
	 */
	public static void submitScheme(Scheme scheme){
		UserNewestLog log = bulidUserNewestLog(scheme,UserNewestType.SUBMIT);
		log.setMoney(scheme.getSchemeCost());
		SpringContextHolder.publishEvent(new UserNewestLogEvent(log));
	}
	
	/**
	 * 用户中奖日志
	 * @param scheme
	 */
	public static void won(Scheme scheme){
		UserNewestLog log = bulidUserNewestLog(scheme,UserNewestType.WON);
		log.setMoney(scheme.getPrize().floatValue());
		SpringContextHolder.publishEvent(new UserNewestLogEvent(log));
	}
	
	/**
	 * 构建用户最新动态通用日志对象
	 * @param scheme
	 * @param newestType
	 * @return
	 */
	private static UserNewestLog bulidUserNewestLog(Scheme scheme,UserNewestType newestType){
		UserNewestLog log = new UserNewestLog();
		log.setIssueNumber(scheme.getPeriodNumber());
		log.setLottery(scheme.getLotteryType());
		log.setNewestType(newestType);
		log.setPlayTypeName(getPlayTypeName(scheme));
		log.setSchemeNumber(scheme.getSchemeNumber());
		log.setUserId(scheme.getSponsorId());
		log.setUserName(scheme.getSponsorName());
		log.setSaleMode(scheme.getMode());
		log.setShareType(scheme.getShareType());
		return log;
	}
	
	/**
	 * 根据方案获取标识前缀
	 * @param scheme
	 * @return
	 */
	private static String getPlayTypeName(Scheme scheme){
		String playTypeName = null;
		switch(scheme.getLotteryType()){
		case JCZQ:
			JczqScheme jczqScheme = (JczqScheme)scheme;
			playTypeName = jczqScheme.getPlayType().getText();
			break;
		case JCLQ:
			JclqScheme jclqScheme = (JclqScheme)scheme;
			playTypeName = jclqScheme.getPlayType().getText();
			break;
		case DCZC:
			DczcScheme dczcScheme = (DczcScheme)scheme;
			playTypeName = dczcScheme.getPlayType().getText();
			break;
		case SFZC:
			SfzcScheme sfzcScheme = (SfzcScheme)scheme;
			playTypeName = sfzcScheme.getPlayType().getText();
			break;
		case LCZC:
		case SCZC:
			break;
		}
		return playTypeName;
	}
}
