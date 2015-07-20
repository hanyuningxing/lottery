package com.cai310.lottery.task;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.zc.LczcScheme;
import com.cai310.lottery.entity.lottery.zc.SczcScheme;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.entity.user.UserGrade;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.support.user.GradeMedal;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * 用户战绩等相关排行任务执行器
 * @author jack
 *
 */
public class GradeTask {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private JczqSchemeEntityManager jczqSchemeManager;
	@Resource
	private JclqSchemeEntityManager jclqSchemeManager;
	@Resource
	private DczcSchemeEntityManager dczcSchemeManager;
	@Resource
	private SchemeEntityManager<LczcScheme> lczcSchemeEntityManagerImpl;
	@Resource
	private SchemeEntityManager<SczcScheme> sczcSchemeEntityManagerImpl;
	@Resource
	private SchemeEntityManager<SfzcScheme> sfzcSchemeEntityManagerImpl;
	@Resource
	private UserEntityManager userManager;
	
	private static final int maxSize = 500;
	
	/**
	 * 次日凌晨执行
	 * 奖金计算=成功奖金+流产奖金(根据实际情况进行统计)
	 */
	@SuppressWarnings("rawtypes")
	public void work() {
		logger.info("战绩统计任务执行...");

		//由容器查找相关的服务类
		//Map map = SpringContextHolder.getApplicationContext().getBeansOfType(SchemeEntityManager.class);
		
		//手动设置需统计的彩种类别
		SchemeEntityManager[] schemeManagers = new SchemeEntityManager[]{jczqSchemeManager,
																		jclqSchemeManager,
																		dczcSchemeManager,
																		lczcSchemeEntityManagerImpl,
																		sczcSchemeEntityManagerImpl,
																		sfzcSchemeEntityManagerImpl};
		
		
		
		Map<Long,UserGrade> gradeMapOfUser = Maps.newHashMap();
		Map<Long,Map<String,GradeMedal>> ptMedalMapOfUser = Maps.newHashMap();//分彩种玩法统计奖牌	
		Map<Long,Map<String,BigDecimal>> ptPrizeMapOfUser = Maps.newHashMap();//分彩种玩法统计奖金
				
		UserGrade grade = null;
		Map<String,BigDecimal> prizeMapOfPt = null;
		String keyPrefix = null;//标识前缀(彩种_玩法)
		BigDecimal prize = null;//方案奖金
		Map<Long,List<Scheme>> schemeMapOfUser = Maps.newHashMap();
		List<Scheme> schemesOfUser = null;
		for(SchemeEntityManager schemeManager : schemeManagers){
			gradeMapOfUser.clear();
			schemeMapOfUser.clear();
			ptMedalMapOfUser.clear();
			List<Scheme> schemes = schemeManager.findWinUpdateOfUnGrade(maxSize);
			for(Scheme scheme : schemes){
				Long sponsorId = scheme.getSponsorId();
				schemesOfUser = schemeMapOfUser.get(sponsorId);
				if(schemesOfUser==null){
					schemesOfUser = Lists.newArrayList();
					schemesOfUser.add(scheme);
					schemeMapOfUser.put(sponsorId, schemesOfUser);
				}else{
					schemesOfUser.add(scheme);
				}
				keyPrefix = this.buildKeyPrefixByScheme(scheme);
				prize = scheme.getPrize();
				if(prize==null)prize=new BigDecimal(0);
								
				
				/**统计用户发单奖金****************************************/
				grade = gradeMapOfUser.get(sponsorId);
				if(grade==null){
					grade = new UserGrade();
					grade.setTotalPrize(prize);
					gradeMapOfUser.put(sponsorId, grade);
				}else{
					grade.setTotalPrize(grade.getTotalPrize().add(prize));
				}
				
				/**总中奖次数、万元奖、千元奖*******************************************************/				
				int compareFlag=prize.compareTo(BigDecimal.valueOf(0));
				if(compareFlag==1){
					grade.setWonTimes(grade.getWonTimes()+1);
				}				
				compareFlag = prize.compareTo(BigDecimal.valueOf(10000D));
				if(compareFlag==1 || compareFlag==0){
					grade.setWonTimes_wan(grade.getWonTimes_wan()+1);
				}else{
					compareFlag=prize.compareTo(BigDecimal.valueOf(1000D));
					if(compareFlag==1 || compareFlag==0){
						grade.setWonTimes_qian(grade.getWonTimes_qian()+1);
					}
				}
				
				
				/**总发单数*/
				grade.setFadanNums(grade.getFadanNums()+1);
				
				
				/**统计用户成功、流产相关信息*******************************/
				String lotptkey = buildKeyPrefixByScheme(scheme);
				Map<String,GradeMedal> gradeMedalOfPtMap = ptMedalMapOfUser.get(sponsorId);
				if(gradeMedalOfPtMap==null){
					gradeMedalOfPtMap = Maps.newHashMap();
					gradeMedalOfPtMap.put(lotptkey, new GradeMedal());
					ptMedalMapOfUser.put(sponsorId, gradeMedalOfPtMap);
				}else if(gradeMedalOfPtMap.get(lotptkey)==null){
					gradeMedalOfPtMap.put(lotptkey, new GradeMedal());
				}
				GradeMedal gm = gradeMedalOfPtMap.get(lotptkey);
				if(scheme.getState()==SchemeState.SUCCESS){
					grade.setFadanSuccessNums(grade.getFadanSuccessNums()+1);
					//统计奖牌
					gm.statGolden(scheme.getSchemeCost(), prize);
				}else{
					//统计流产奖牌
					gm.statSilvery(scheme.getSchemeCost(), prize);
				}
				
				/**分玩法统计奖金****************************************/
				prizeMapOfPt = ptPrizeMapOfUser.get(sponsorId);
				if(prizeMapOfPt==null){
					prizeMapOfPt = Maps.newHashMap();
					ptPrizeMapOfUser.put(sponsorId, prizeMapOfPt);
				}
				BigDecimal prizeOld = prizeMapOfPt.get(keyPrefix);
				if(prizeOld==null){
					prizeMapOfPt.put(keyPrefix, prize);
				}else{
					prizeMapOfPt.put(keyPrefix,prizeOld.add(prize));
				}
								
			}
			
			
			//组合更新持续化
			Set<Long> userIds = gradeMapOfUser.keySet();
			UserGrade userGrade = null;
			for(Long userId : userIds){
				userGrade = gradeMapOfUser.get(userId);
				String prizesPtJson = JsonUtil.getJsonString4JavaPOJO(ptPrizeMapOfUser.get(userId));
				userGrade.setPrizes_play(prizesPtJson);
				String medalsPtJson = JsonUtil.getJsonString4JavaPOJO(ptMedalMapOfUser.get(userId));
				userGrade.setMedals_play(medalsPtJson);
				this.userManager.updateUserGrade(userId, schemeManager, schemeMapOfUser.get(userId), userGrade);
			}
			
		}
	}
	
	/**
	 * 根据方案获取标识前缀
	 * @param scheme
	 * @return
	 */
	private String buildKeyPrefixByScheme(Scheme scheme){
		StringBuffer sb = new StringBuffer();
		switch(scheme.getLotteryType()){
		case JCZQ:
			JczqScheme jczqScheme = (JczqScheme)scheme;
			sb.append(jczqScheme.getLotteryType()).append("_").append(jczqScheme.getPlayType());
			break;
		case JCLQ:
			JclqScheme jclqScheme = (JclqScheme)scheme;
			sb.append(jclqScheme.getLotteryType()).append("_").append(jclqScheme.getPlayType());
			break;
		case DCZC:
			DczcScheme dczcScheme = (DczcScheme)scheme;
			sb.append(dczcScheme.getLotteryType()).append("_").append(dczcScheme.getPlayType());
			break;
		case SFZC:
			SfzcScheme sfzcScheme = (SfzcScheme)scheme;
			sb.append(sfzcScheme.getLotteryType()).append("_").append(sfzcScheme.getPlayType());
			break;
		case LCZC:
		case SCZC:
			sb.append(scheme.getLotteryType());
			break;
		}
		return sb.toString();
	}
}
