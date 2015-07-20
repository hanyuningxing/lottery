package com.cai310.lottery.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.zc.LczcScheme;
import com.cai310.lottery.entity.lottery.zc.SczcScheme;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserWonRank;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.service.user.impl.UserWonRankManagerImpl;
import com.cai310.lottery.utils.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * 用户战绩等相关排行任务执行器
 * @author jack
 *
 */
public class RankTask {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JczqSchemeEntityManager jczqSchemeManager;
	@Autowired
	private JclqSchemeEntityManager jclqSchemeManager;
	@Autowired
	private DczcSchemeEntityManager dczcSchemeManager;
	@Resource
	private SchemeEntityManager<LczcScheme> lczcSchemeEntityManagerImpl;
	@Resource
	private SchemeEntityManager<SczcScheme> sczcSchemeEntityManagerImpl;
	@Resource
	private SchemeEntityManager<SfzcScheme> sfzcSchemeEntityManagerImpl;
	@Resource
	private UserWonRankManagerImpl userWonRankEntityManager;
	@Resource
	protected UserEntityManager userEntityManager;
	
	private static final int maxSize = 500;
	private static Integer resetFlag = null;
	
	/**
	 * 次日凌晨执行
	 */
	@SuppressWarnings("rawtypes")
	public void work() {
		logger.info("排行榜统计任务执行...");

		Integer currDateToInt = Integer.valueOf(DateUtil.getDate());
		//复位所有的排行榜记录
		if(resetFlag==null || !currDateToInt.equals(resetFlag)){
//			userWonRankEntityManager.resetUserWonRank();
			resetFlag = currDateToInt;
			logger.info("重置排行榜数据...");
		}
		
		
		//由容器查找相关的服务类
		//Map map = SpringContextHolder.getApplicationContext().getBeansOfType(SchemeEntityManager.class);
		
		//手动设置需统计的彩种类别
		SchemeEntityManager[] schemeManagers = new SchemeEntityManager[]{jczqSchemeManager,
																		jclqSchemeManager,
																		dczcSchemeManager,
																		lczcSchemeEntityManagerImpl,
																		sczcSchemeEntityManagerImpl,
																		sfzcSchemeEntityManagerImpl};
		for(SchemeEntityManager schemeManager : schemeManagers){
			this.statProfitOfDate(schemeManager,currDateToInt);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void statProfitOfDate(SchemeEntityManager schemeManager,final Integer currDateToInt){	
		//7天
		Date startDate = formatStatDate(new Date(),-7);
		Date endDate = formatStatDate(new Date(),0);
		this.statProfitOfUserForLottery(schemeManager,startDate,endDate,currDateToInt,7);
		//30天
		startDate = formatStatDate(new Date(),-30);
		endDate = formatStatDate(new Date(),-7);
		this.statProfitOfUserForLottery(schemeManager,startDate,endDate,currDateToInt,30);
		//90天
		startDate = formatStatDate(new Date(),-90);
		endDate = formatStatDate(new Date(),-30);
		this.statProfitOfUserForLottery(schemeManager,startDate,endDate,currDateToInt,90);
	}
	
	/**
	 * 格式化起始，结束日期
	 * @param date
	 * @param num
	 * @return
	 */
	private Date formatStatDate(Date date, int num){
		Date newDate = DateUtil.addDate(date, num);
		newDate = com.cai310.utils.DateUtil.strToDate(com.cai310.utils.DateUtil.dateToStr(newDate,"yyyy-MM-dd")+" 00:00");
		return newDate;
	}
	
	/**
	 * 按彩种统计用户的战绩、排行等相关信息
	 * @param schemeManager 方案管理服务类
	 * @param startDate 统计开始时间
	 * @param endDate 统计的结束时间
	 * @param currDateToInt 当前统计的日期标记
	 * @param statDays 统计的时间段，用于标识记录字段
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void statProfitOfUserForLottery(SchemeEntityManager schemeManager,Date startDate, Date endDate, final Integer currDateToInt, int statDays){
		
		List<Scheme> schemes = schemeManager.findWonOfUnRank(currDateToInt,startDate,endDate, maxSize);
		if(schemes==null || schemes.isEmpty())return;
		
		//按彩种的玩法分别统计
		Map<Integer,Object[]> statMapOfPlayType = Maps.newHashMap();
		Object[] profitMapArrOfType = null;//【成功发单、成功跟单、流产发单、流产跟单】
		int playTypeOrdinal = 0;
		for(Scheme scheme : schemes){
			switch(scheme.getLotteryType()){
			case DCZC:
				playTypeOrdinal = ((DczcScheme)scheme).getPlayType().ordinal();
				break;
			case SFZC:
				playTypeOrdinal = ((SfzcScheme)scheme).getPlayType().ordinal();
				break;
			case JCZQ:
				playTypeOrdinal = ((JczqScheme)scheme).getPlayType().ordinal();
				break;
			case JCLQ:
				playTypeOrdinal = ((JclqScheme)scheme).getPlayType().ordinal();
				break;
			}
			profitMapArrOfType = statMapOfPlayType.get(playTypeOrdinal);
			if(profitMapArrOfType==null){
				profitMapArrOfType = new Object[4];
				statMapOfPlayType.put(playTypeOrdinal, profitMapArrOfType);
			}
			Object[] statDataArr = statDataOfScheme(scheme,schemeManager);
			Map<Long,BigDecimal[]> fadanMapOfPlay = null;
			Map<Long,BigDecimal[]> gendanMapOfPlay = null;
			if(scheme.getState()==SchemeState.SUCCESS){//成功的方案盈利
				fadanMapOfPlay = (Map<Long,BigDecimal[]>)profitMapArrOfType[0];
				gendanMapOfPlay = (Map<Long,BigDecimal[]>)profitMapArrOfType[1];
				if(fadanMapOfPlay==null){
					fadanMapOfPlay = Maps.newHashMap();
					profitMapArrOfType[0] = fadanMapOfPlay;
				}
				if(gendanMapOfPlay==null){
					gendanMapOfPlay = Maps.newHashMap();
					profitMapArrOfType[1] = gendanMapOfPlay;
				}
				this.doStatOfPlayType(fadanMapOfPlay, (Map<Long,BigDecimal[]>)statDataArr[0]);
				this.doStatOfPlayType(gendanMapOfPlay, (Map<Long,BigDecimal[]>)statDataArr[1]);
			}else{
				fadanMapOfPlay = (Map<Long,BigDecimal[]>)profitMapArrOfType[2];
				gendanMapOfPlay = (Map<Long,BigDecimal[]>)profitMapArrOfType[3];
				if(fadanMapOfPlay==null){
					fadanMapOfPlay = Maps.newHashMap();
					profitMapArrOfType[2] = fadanMapOfPlay;
				}
				if(gendanMapOfPlay==null){
					gendanMapOfPlay = Maps.newHashMap();
					profitMapArrOfType[3] = gendanMapOfPlay;
				}
				this.doStatOfPlayType(fadanMapOfPlay, (Map<Long,BigDecimal[]>)statDataArr[0]);
				this.doStatOfPlayType(gendanMapOfPlay, (Map<Long,BigDecimal[]>)statDataArr[1]);
			}
		}
		logger.debug(schemeManager.getLottery().getLotteryName()+schemes.size());
		
		List<UserWonRank> userWonRanks = this.buildUserWonRankData(statMapOfPlayType);
		userWonRankEntityManager.saveUserWonRank(userWonRanks, schemeManager, schemes, currDateToInt, statDays);
	}
	
	/**
	 * 将方案的统计数据并入以玩法为单位统计中
	 * @param mapOfPlay 玩法统计
	 * @param mapOfScheme 方案统计
	 */
	private void doStatOfPlayType(Map<Long,BigDecimal[]> mapOfPlay, Map<Long,BigDecimal[]> mapOfScheme){		
		if(!mapOfScheme.isEmpty()){
			Iterator<Entry<Long, BigDecimal[]>> it = mapOfScheme.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Long, BigDecimal[]> entry = (Entry<Long, BigDecimal[]>) it.next();
				Long userId = entry.getKey();
				BigDecimal[] wonDataArr = mapOfPlay.get(userId);
				if(wonDataArr==null){
					mapOfPlay.put(userId, entry.getValue());
				}else{
					wonDataArr[0] = wonDataArr[0].add(entry.getValue()[0]);
					wonDataArr[1] = wonDataArr[1].add(entry.getValue()[1]);
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object[] statDataOfScheme(Scheme scheme,SchemeEntityManager schemeManager){
		Map<Long,BigDecimal[]> gendanMap = Maps.newHashMap();//统计一个方案的盈利信息：[0]跟单金额、[1]跟单盈利金额
		
		//跟单加入金额及盈利(包含发起人)
		List<Subscription> subscriptions = schemeManager.findSubscriptionsOfScheme(scheme.getId(),null);
		for(Subscription subscription : subscriptions){
			Long userId = subscription.getUserId();
			BigDecimal[] gendanInfoArr = gendanMap.get(userId);
			if(gendanInfoArr==null){
				gendanInfoArr=new BigDecimal[]{new BigDecimal(0) ,new BigDecimal(0)};
				gendanMap.put(userId, gendanInfoArr);
			}else{
				gendanInfoArr[0]=gendanInfoArr[0].add(subscription.getCost());
				BigDecimal bonus = subscription.getBonus();
				if(bonus!=null){
					gendanInfoArr[1]=gendanInfoArr[1].add(subscription.getBonus());
				}
			}
		}
		
		Map<Long,BigDecimal[]> fadanOfMap = Maps.newHashMap();//发单(针对发起人，方案金额及盈利)
		Map<Long,BigDecimal[]> gendanOfMap = Maps.newHashMap();//跟单(针对加入认购)
				
		Long sponsorId = scheme.getSponsorId();//发起人ID
		//统计发单
		BigDecimal[] fadanArr = fadanOfMap.get(sponsorId);
		if(fadanArr==null){
			fadanArr = new BigDecimal[]{new BigDecimal(scheme.getSchemeCost()) ,scheme.getPrize()};
			fadanOfMap.put(sponsorId, fadanArr);
		}else{
			fadanArr[0] = fadanArr[0].add(new BigDecimal(scheme.getSchemeCost()));
			fadanArr[1] = fadanArr[1].add(scheme.getPrize());
		}
		//统计跟单
		gendanMap.remove(sponsorId);
		Iterator<Entry<Long, BigDecimal[]>> it = gendanMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Long, BigDecimal[]> entry = (Entry<Long, BigDecimal[]>) it.next();
			Long userId = entry.getKey();
			BigDecimal[] statArr = entry.getValue();
			BigDecimal[] gendanArr = gendanOfMap.get(userId);
			if(gendanArr==null){
				gendanArr = new BigDecimal[]{statArr[0], statArr[1]};
				gendanOfMap.put(userId, gendanArr);
			}else{
				gendanArr[0] = gendanArr[0].add(statArr[0]);
				gendanArr[1] = gendanArr[1].add(statArr[1]);
			}
		}
		return new Object[]{fadanOfMap,gendanOfMap};
	}	

	/**
	 * 
	 * @param statDataMapOfPlay 以玩法分别统计的排行数据Map
	 * @return List<UserWonRank> 排行榜集合
	 */
	@SuppressWarnings("unchecked")
	private List<UserWonRank> buildUserWonRankData(Map<Integer,Object[]> statDataMapOfPlay) {
		if(statDataMapOfPlay==null || statDataMapOfPlay.isEmpty()){
			return null;
		}
		List<UserWonRank> userWonRanks = Lists.newArrayList();
		UserWonRank userWonRank = null;
		User user = null;
		
		Object[] rankDataArrOfPlay = null;
		Map<Long, BigDecimal[]> rankDataMap = null;
		boolean fadan=false;
		boolean success=false;

		Iterator<Entry<Integer, Object[]>> it = statDataMapOfPlay.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Object[]> entry = (Entry<Integer, Object[]>) it.next();
			rankDataArrOfPlay = entry.getValue();
			for(int i=0;i<rankDataArrOfPlay.length;i++){
				switch(i){
				case 0:
					fadan = true;
					success = true;
					break;
				case 1:
					fadan = false;
					success = true;
					break;
				case 2:
					fadan = true;
					success = false;
					break;
				case 3:
					fadan = false;
					success = false;
					break;
				}
				Object rankData = rankDataArrOfPlay[i];
				if(rankData!=null){
					rankDataMap = (Map<Long, BigDecimal[]>)rankData;
					Iterator<Entry<Long, BigDecimal[]>> itT = rankDataMap.entrySet().iterator();
					while (itT.hasNext()) {
						Entry<Long, BigDecimal[]> entryT = (Entry<Long, BigDecimal[]>) itT.next();
						userWonRank = new UserWonRank();
						user = userEntityManager.getUser(entryT.getKey());
						userWonRank.setUser(user);
						userWonRank.setPtOrdinal(entry.getKey());
						userWonRank.setSuccess(success);
						userWonRank.setFadan(fadan);
						userWonRank.setCost(entryT.getValue()[0]);
						userWonRank.setProfit(entryT.getValue()[1]);
						userWonRanks.add(userWonRank);
					}
				}
			}
		}
		return userWonRanks;
	}
	
}
