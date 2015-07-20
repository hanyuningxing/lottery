package com.cai310.lottery.service.activity.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.ClientProtocolException;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LuckDetailType;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dao.user.LuckDetailDao;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.user.LuckDetail;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.activity.AvtivityConfig;
import com.cai310.lottery.service.activity.AvtivityService;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.jczq.JczqCompoundContent;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.TicketItem;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.response.dto.JcTicketOddsList;
import com.cai310.lottery.utils.local.jclq.JclqSpUtil;
import com.cai310.lottery.utils.local.jczq.JczqSpUtil;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

@Service("avtivityService")
@Transactional
public class AvtivityServiceImpl implements AvtivityService {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected LuckDetailDao luckDetailDao;
	@Autowired
	private UserEntityManager userEntityManager;
	@Autowired
	private List<SchemeEntityManager> schemeEntityManagerList;
	private SchemeEntityManager getSchemeEntityManager(Lottery lotteryType) {
		for (SchemeEntityManager manager : schemeEntityManagerList) {
			if (manager.getLottery().equals(lotteryType))
				return manager;
		}
		return null;
	}
	@Autowired
	private AgentEntityManager agentEntityManager;
	/**
	 * 马甲
	 * @throws DataException 
	 */
	public void noSendAvtivity(Scheme scheme) throws DataException{
//		if(AvtivityConfig.noSendAvtivity.contains(scheme.getSponsorId())){
//			 Lottery lotteryType = scheme.getLotteryType();
//			 if(scheme.getLotteryType().equals(Lottery.JCZQ)){
//					JczqScheme jczqScheme = (JczqScheme) getSchemeEntityManager(lotteryType).getScheme(scheme.getId());
//					JczqCompoundContent jczqCompoundContent = jczqScheme.getCompoundContent();
//				    List<TicketItem> ticketItemList  = jczqScheme.getTicketList();
//					List<JcMatchOddsList> awardList = Lists.newArrayList();
//				    for (TicketItem ticketItem : ticketItemList) {
//				    	    PassType passType = ticketItem.getPassType();
//							int units = 0;
//							List<JczqMatchItem> selectList = JczqUtil.getSelectByTicketItem(ticketItem,jczqCompoundContent.getItems());
//							if(!com.cai310.lottery.support.jczq.PassType.P1.equals(
//									 com.cai310.lottery.support.jczq.PassType.values()[Integer.valueOf(""+jczqScheme.getPassType())])){
//								JcMatchOddsList jcMatchOddsList = null;
//								try {
//									jcMatchOddsList = JczqSpUtil.parseResponseSp_local(selectList,jczqScheme.getPlayType());
//									jcMatchOddsList.setTicketIndex(ticketItemList.indexOf(ticketItem));
//								} catch (Exception e) {
//									logger.error("{"+scheme.getId()+"}获取失败");
//								}
//						    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
//								    awardList.add(jcMatchOddsList);
//								}
//							}
//				    }
//				    if(!awardList.isEmpty()){
//				    	JcTicketOddsList jcTicketOddsList = new JcTicketOddsList();
//						jcTicketOddsList.setSchemeId(scheme.getId());
//				    	jcTicketOddsList.setAwardList(awardList);
//						String awardString = JsonUtil.getJsonString4JavaPOJO(jcTicketOddsList);
//						////更新方案状态
//						jczqScheme.setSchemePrintState(SchemePrintState.SUCCESS);
//						jczqScheme.setPrintAwards(awardString);
//						jczqScheme.setRealPrinted(true);
//						jczqScheme.setRealPrintTime(new Date());
//					}
//			   }else if(scheme.getLotteryType().equals(Lottery.JCLQ)){JcTicketOddsList jcTicketOddsList = new JcTicketOddsList();
//				jcTicketOddsList.setSchemeId(scheme.getId());
//				JclqScheme jclqScheme = (JclqScheme) getSchemeEntityManager(lotteryType).getScheme(scheme.getId());
//					if(!com.cai310.lottery.support.jclq.PassType.P1.equals(
//							 com.cai310.lottery.support.jclq.PassType.values()[Integer.valueOf(""+jclqScheme.getPassType())])){
//						JcMatchOddsList jcMatchOddsList = null;
//						try {
//							jcMatchOddsList = JclqSpUtil.parseResponseSp_local(jclqScheme);
//						}catch (Exception e) {
//							logger.error("{"+scheme.getId()+"}获取失败");
//						}
//						List<JcMatchOddsList> awardList = Lists.newArrayList();
//				    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
//						    awardList.add(jcMatchOddsList);
//						}
//				    	jcTicketOddsList.setAwardList(awardList);
//						String awardString = JsonUtil.getJsonString4JavaPOJO(jcTicketOddsList);
//						////更新方案状态
//						jclqScheme.setSchemePrintState(SchemePrintState.SUCCESS);
//						jclqScheme.setPrintAwards(awardString);
//						jclqScheme.setRealPrinted(true);
//						jclqScheme.setRealPrintTime(new Date());
//					}else{
//						//单关
//						try{
//							if(com.cai310.lottery.support.jclq.PlayType.SFC.equals(jclqScheme.getPlayType())){
//								    JcMatchOddsList jcMatchOddsList = JclqSpUtil.parseResponseSp_local(jclqScheme);
//								    List<JcMatchOddsList> awardList = Lists.newArrayList();
//							    	if(null!=jcMatchOddsList&&null!=jcMatchOddsList.getJcMatchOdds()&&!jcMatchOddsList.getJcMatchOdds().isEmpty()){
//									    awardList.add(jcMatchOddsList);
//									}
//							    	jcTicketOddsList.setAwardList(awardList);
//									String awardString = JsonUtil.getJsonString4JavaPOJO(jcTicketOddsList);
//									////更新方案状态
//									jclqScheme.setSchemePrintState(SchemePrintState.SUCCESS);
//									jclqScheme.setPrintAwards(awardString);
//									jclqScheme.setRealPrinted(true);
//									jclqScheme.setRealPrintTime(new Date());
//							}
//						}catch(Exception e){
//							//单关可能会获取失败
//							logger.error("{"+scheme.getId()+"}单关可能会获取失败");
//						}
//				}
//			}else{
//				////更新方案状态
//				scheme.setSchemePrintState(SchemePrintState.SUCCESS);
//			}
//			if (SchemeState.SUCCESS.equals(scheme.getState()) && !WinningUpdateStatus.NONE.equals(scheme.getWinningUpdateStatus())) {
//				agentEntityManager.returnRebate(scheme.getId(), scheme.getLotteryType());
//			}
//		}
	}
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();
		System.out.print(DateUtil.dateToStr(d));
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal countLuckDetailMoney(final Date createTime, final Long userId,final LuckDetailType type,final FundMode mode) {
		return (BigDecimal) luckDetailDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.sum("money"));
				criteria.add(Restrictions.eq("userId", userId));
		        criteria.add(Restrictions.ge("createTime", createTime));
				criteria.add(Restrictions.eq("type",type));
				criteria.add(Restrictions.eq("mode", mode));
				return criteria.uniqueResult();
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<LuckDetail> getLuckDetail(final Date createTime, final Long userId,final LuckDetailType type,final FundMode mode) {
		return (List<LuckDetail>) luckDetailDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("userId", userId));
				if(null!=createTime){
					criteria.add(Restrictions.ge("createTime", createTime));
				}
				criteria.add(Restrictions.eq("type",type));
				criteria.add(Restrictions.eq("mode", mode));
				return criteria.list();
			}
		});
	}
	/**
     * 活动时间：10月1日-10月15日
活动细则：

1、单个投注方案每满10元，网站赠送彩金1元（满10元才送，不足10元不送。示例：方案金额22元，赠送2元）。每位用户每天（以出票时间为准）最多赠送2999元彩金。

2、支持所有代购、合买成功出票的方案，合买方案赠送彩金归发单人所有。

3、赠送彩金将在出票成功后即时发放到鸿彩账户中。

4、赠送彩金只限于购彩，不可提现。

5、关闭用户szpp888、蓝色妖姬、我爱螺蛳粉、lingweiqun  、ym98 参与此项活动的权限。

     */
	public static final Date startTenSendOneDate = DateUtil.strToDate("2013-10-21 00:00", null);//活动结束时间
	public static final Date endTenSendOneDate = DateUtil.strToDate("2013-10-28 00:00", null);//活动结束时间
	/**开关 **/
	public static Boolean TENSENDONE = Boolean.TRUE;
    @Override
    public void TenSendOne(Scheme scheme) {
//			Date now = scheme.getCreateTime();
//			if (TENSENDONE&& now.after(startTenSendOneDate)&& now.before(endTenSendOneDate)) {
//				logger.error(scheme.getId()+"========================活动10开始");
//		    	User user = userEntityManager.getUser(scheme.getSponsorId());
//					// 判断是否代理用户发起的方案
//				if(user.getAgentId()==1) {
//					logger.error(scheme.getId()+"========================活动11开始");
//					if (null != user) {
//						    logger.error(scheme.getId()+"========================活动12开始");
//							//不参加普通加奖特别用户   lingweiqun  、ym98  竞彩2串1 10%加奖 4个 a20c101、张0321、波尔图圣战、翟子祺 参加返点9%.2串1 10%加奖特别用户 3个 SZPP888 12375L 蓝色妖姬  12374L 我爱螺蛳粉 12372
//						    if(AvtivityConfig.unCommonUserId.contains(user.getId()))return;
//						    logger.error(scheme.getId()+"========================活动13开始");
//							BigDecimal cost = BigDecimal.valueOf(scheme.getSchemeCost());
//					    	///单个投注方案每满10元，网站赠送彩金1元（满10元才送，不足10元不送。示例：方案金额22元，赠送2元）。每位用户每天（以出票时间为准）最多赠送2999元彩金。
//					    	if(cost.compareTo(BigDecimal.valueOf(10))<0)return;//不足10元不送
//					    	 logger.error(scheme.getId()+"========================活动14开始");
//					    	Double costDouble = cost.doubleValue();
//					    	Integer addPrizeInteger = (int) (costDouble/10);//单个投注方案每满10元，网站赠送彩金1元
//					    	BigDecimal addPrize = BigDecimal.valueOf(addPrizeInteger);
//					    	if(addPrize.compareTo(BigDecimal.valueOf(0))<=0)return;//不足1元不送
//					    	 logger.error(scheme.getId()+"========================活动15开始");
//					    	//每位用户每天（以出票时间为准）最多赠送2999元彩金
//					    	Calendar cal = Calendar.getInstance();
//							cal.set(Calendar.HOUR_OF_DAY, 0);
//					        cal.set(Calendar.SECOND, 0);
//					        cal.set(Calendar.MINUTE, 0);
//					        cal.set(Calendar.MILLISECOND, 0);
//					    	BigDecimal totleToday = this.countLuckDetailMoney(cal.getTime(), user.getId(), LuckDetailType.TENSENDONE, FundMode.IN);
//					    	logger.error(scheme.getId()+"========================活动16开始"+totleToday);
////					    	if(null!=totleToday&&totleToday.compareTo(BigDecimal.valueOf(2999))>=0)return;//每位用户每天（以出票时间为准）最多赠送2999元彩金
////					    	logger.error(scheme.getId()+"========================活动17开始"+totleToday);
//								synchronized (Constant.Luck) {
//									logger.error(scheme.getId()+"========================活动18开始"+totleToday);
////									if(null!=totleToday&&null!=addPrize&&(totleToday.add(addPrize)).compareTo(BigDecimal.valueOf(2999))>=0){
////										///如果已经赠送金额+即将赠送金额大于2999
////										///赠送金额 = 2999 - 已经赠送金额;
////										addPrize = BigDecimal.valueOf(2999).subtract(totleToday);
////										if(addPrize.compareTo(BigDecimal.valueOf(addPrizeInteger))>0)return;///扣除后比原来还要多
////									}
//									userEntityManager.oprUserMoney(user, addPrize,
//											"买十送一活动，认购方案["+scheme.getSchemeNumber()+"],方案金额["+cost+"]，奖励["+addPrize+"]", FundMode.IN,
//											FundDetailType.TENSENDONE, null);
//									LuckDetail luckDetail = new LuckDetail(user,
//											addPrize, "买十送一活动，认购方案["+scheme.getSchemeNumber()+"],方案金额["+cost+"]，奖励["+addPrize+"]", FundMode.IN,
//											LuckDetailType.TENSENDONE);
//									userEntityManager.saveLuckDetail(luckDetail);
//								}
//				    }
//					}
//			}
    }
}
