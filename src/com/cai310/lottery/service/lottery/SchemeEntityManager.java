package com.cai310.lottery.service.lottery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.entity.lottery.Baodi;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.orm.Pagination;

/**
 * 方案相关实体的管理接口, 包括方案,认购,保底的管理.
 * 
 * @param <T>
 *            方案对象的类型
 */
public interface SchemeEntityManager<T extends Scheme> {
	/**
	 * 统计出票方案
	 * 
	 * @param userId
	 *            用户编号
	 * @param dto
	 *            查询参数
	 * @return
	 */
	List countMyScheme(Long userId, SchemeQueryDTO dto, Pagination pagination);
	/**
	 * 获取方案
	 * 
	 * @param id
	 *            方案编号
	 * @return 方案对象
	 */
	T getScheme(Long id);

	/**
	 * 获取方案
	 * 
	 * @param schemeNumber
	 *            方案号
	 * @return 方案对象
	 */
	/**
	 * 查询保底的认购数据
	 * @param schemeId
	 * @param state
	 * @return
	 */
	List<Subscription> findSubscriptionsOfBaoDiScheme(long schemeId,long userId,SubscriptionState state);
	
	T getSchemeBy(String schemeNumber);
	
	/**
	 * 获取出票方案
	/**
	 * 
	 * @param orderId 订单号
	 * @param userId 用户Id
	 * @return
	 */
	T getTicketSchemeBy(String orderId,Long userId);

	/**
	 * 保存方案
	 * 
	 * @param scheme
	 *            方案对象
	 * @return 保存后的方案对象
	 */
	T saveScheme(T scheme);

	/**
	 * 获取认购记录
	 * 
	 * @param id
	 *            认购ID
	 * @return 认购记录
	 */
	Subscription getSubscription(Long id);

	/**
	 * 保存认购
	 * 
	 * @param subscription
	 *            认购对象
	 * @return 保存后的认购对象
	 */
	Subscription saveSubscription(Subscription subscription);

	/**
	 * 获取保底记录
	 * 
	 * @param id
	 *            保底ID
	 * @return 保底记录
	 */
	Baodi getBaodi(Long id);

	/**
	 * 保存保底
	 * 
	 * @param subscription
	 *            保底对象
	 * @return 保存后的保底对象
	 */
	Baodi saveBaodi(Baodi baodi);

	/**
	 * 统计未执行【更新中奖】的方案数
	 * 
	 * @param periodId
	 *            销售期编号
	 * @return 未执行【更新中奖】的方案数
	 */
	Integer statUnUpdateWon(long periodId);

	/**
	 * 统计未执行【更新奖金】的方案数
	 * 
	 * @param periodId
	 *            销售期编号
	 * @return 未执行【更新奖金】的方案数
	 */
	Integer statUnUpdatePrize(long periodId);

	/**
	 * 查询未执行【派发奖金】的方案编号
	 * 
	 * @param periodId
	 *            销售期编号
	 * @return 未执行【派发奖金】的方案编号列表
	 */
	List<Long> findSchemeIdOfCanDelivePrize(long periodId);

	/**
	 * 统计未执行【派发奖金】的方案数
	 * 
	 * @param periodId
	 *            销售期编号
	 * @return 未执行【派发奖金】的方案数
	 */
	Integer statUnDelivePrize(long periodId);

	/**
	 * 统计未执行【完成交易】的方案数（分组统计保留和未保留的）
	 * 
	 * @param periodId
	 *            销售期编号
	 * @param salesMode
	 *            彩种号
	 * @return 未执行【完成交易】的方案数
	 */
	List<Map<String, Object>> statUnCommit(long periodId, SalesMode salesMode);

	/**
	 * 查询未完成交易并且没被保留的方案编号
	 * 
	 * @param periodId
	 *            销售期编号
	 * @param lotteryType
	 *            彩种号
	 * @return 查询未完成交易并且没被保留的方案编号列表
	 */
	List<Long> findSchemeIdOfUnCommitAndUnReserved(long periodId, SalesMode salesMode);

	/**
	 * 查询未执行【更新中奖】的方案编号
	 * 
	 * @param periodId
	 *            销售期编号
	 * @return 未更新中奖的方案编号列表
	 */
	List<Long> findSchemeIdOfCanUpdateWon(long periodId);

	/**
	 * 查询可以进行过关统计的方案编号
	 * 
	 * @param periodId
	 *            销售期编号
	 * @return 可进行过关统计的方案编号列表
	 */
	List<Long> findSchemeIdOfCanPasscount(long periodId);

	/**
	 * 查询未执行【更新奖金】的方案编号
	 * 
	 * @param periodId
	 *            销售期编号
	 * @return 未更新奖金的方案编号列表
	 */
	List<Long> findSchemeIdOfCanUpdatePrize(long periodId);
	
	/**
	 * 查询未执行【派发战绩】的方案编号
	 * 
	 * @param periodId
	 *            销售期编号
	 * @return 未派发战绩的方案编号列表
	 */
    List<Long> findSchemeIdOfCanDeliveWinRecord(long periodId);


	/**
	 * 查询有用户保底的方案编号
	 * 
	 * @param periodId
	 *            销售期编号
	 * @param salesMode
	 *            彩种号
	 * @return 查询有用户保底并且没被保留的方案编号列表
	 */
	List<Long> findSchemeIdOfHasBaodiAndUnReserved(long periodId, SalesMode salesMode);

	/**
	 * 统计未执行【保底】的方案数（分组统计保留和未保留的）
	 * 
	 * @param betIssueId
	 *            销售期编号
	 * @param lotteryType
	 *            彩种号
	 * @return 未执行保底的方案数
	 */
	List<Map<String, Object>> statUnRunBaodi(long periodId, SalesMode salesMode);

	/**
	 * @return 所管理彩种类型
	 * @see com.cai310.lottery.common.Lottery
	 */
	Lottery getLottery();

	/**
	 * 方案是否有他人认购
	 * 
	 * @param schemeId
	 *            方案ID
	 * @return 方案是否有他人认购
	 */
	boolean hasOthersSubscription(Long schemeId);

	/**
	 * 方案是否有他人保底
	 * 
	 * @param schemeId
	 *            方案ID
	 * @return 方案是否有他人保底
	 */
	boolean hasOthersBaodi(Long schemeId);

	/**
	 * 查询正常的保底
	 * 
	 * @param schemeId
	 *            方案ID
	 * @return 正常的保底集合
	 */
	List<Baodi> findNormalBaodi(Long schemeId);

	/**
	 * 查询方案
	 * 
	 * @param userId
	 *            用户编号
	 * @param dto
	 *            查询参数
	 * @return
	 */
	Pagination findMyScheme(Long userId, SchemeQueryDTO dto, Pagination pagination);

	/**
	 * 查询方案的认购数据
	 * 
	 * @param schemeId
	 * @param state
	 *            为空时查出全部状态
	 * @return
	 */
	List<Subscription> findSubscriptionsOfScheme(long schemeId, SubscriptionState state);

	/**
	 * 将旧方案对象里复制到全新的对象
	 * 
	 * @param oldScheme
	 * @return
	 */
	T cloneNewScheme(T oldScheme);

	/**
	 * 将旧认购对象里复制到全新的对象
	 * 
	 * @param oldSubscription
	 * @return
	 */
	Subscription cloneNewSubscription(Subscription oldSubscription);

	/**
	 * 计算用户认购某方案的总金额
	 * 
	 * @param schemeId
	 *            方案ID
	 * @param userId
	 *            用户ID
	 * @return 认购总金额
	 */
	BigDecimal countSubscribedCost(Long schemeId, Long userId);

	/**
	 * 修改方案排序优先级
	 * 
	 * @param schemeId
	 *            方案ID
	 * @param orderPriority
	 *            排序优先级
	 */
	void changeOrderPriority(Long schemeId, byte orderPriority);

	/**
	 * 修改方案保留状态
	 * 
	 * @param schemeId
	 *            方案ID
	 * @param reserved
	 *            是否保留
	 */
	void changeReserved(Long schemeId, boolean reserved);

	/**
	 * 重置方案为未开奖状态
	 * 
	 * @param schemeId
	 *            方案ID
	 */
	void resetUnUpdateWon(Long schemeId);

	/**
	 * 取置顶方案
	 * 
	 * @param star
	 *            开始
	 * @param star
	 *            要取的字段
	 */
	List<T> getTopScheme(Integer star, Integer count);

	/**
	 * @param periodId
	 *            销售期编号
	 * @return
	 */
	List<Long> findSchemeIdOfSaleAnalyse(long periodId);

	/**
	 * 重置方案为已更新中奖状态
	 * 
	 * @param schemeId
	 *            方案ID
	 */
	void resetPriceUpdated(Long schemeId);
	
	/**
	 * 
	 * @param ticketSchemeState 出票状态
	 * @param schemeState 
	 * @return
	 */
	List<T> findSchemeByState(SchemePrintState schemePrintState,SchemeState schemeState,boolean sendToPrint);
	
	/**
	 * 
	 * @param ticketSchemeState 出票状态
	 * @param schemeState 
	 * @return
	 */
	List<T> countSchemeByState(SchemePrintState schemePrintState,SchemeState schemeState,boolean sendToPrint);
	
	/**
	 * 
	 * @param restrictions
	 * @param prop
	 * @return
	 */

	List<T> countSchemeByCriterion(List<Criterion> restrictions,
			ProjectionList prop);
	
	/**
	 * 
	 * @param restrictions
	 * @param prop
	 * @return
	 */
	List<T> findSchemeByCriterion(List<Criterion> restrictions);

	List<Subscription> getTop10SaleAnalyse();
	List<Subscription> getTopWon(Lottery lottery);
	/**
	 * 接票是否重复
	 * 
	 * @param orderId 订单编号
	 * @param userId 用户编号
	 * @return 是否已有该订单
	 */
	boolean isRepeatOrder(String orderId,Long userId);
	
	/**
	 * 接票
	 * 
	 * @param orderId 订单编号
	 * @return 方案
	 */
	T findSchemeByOrderId(String orderId);
	
	/**
	 * 接票
	 * 
	 * @param orderId 订单编号
	 * @return 方案
	 */
	List<T> findSchemeByOrderId(List<String> orderId);
	
	/**
	 * 接票
	 * 
	 * @param orderId 订单编号
	 * @return 方案
	 */
	TicketThen findTicketThenByOrderId(String orderId);
	/**
	 * 
	 * @param sponsorId 发起用户
	 * @param startDate 开始时间	
	 * @param endDate 结束时间
	 * @return
	 */
	List<T> findWonSchemeBySponsorId(Long sponsorId, Date startDate,
			Date endDate);
	

	/**
	 * 
	 * @return 最新中奖
	 */
	List<Subscription> getNewestWon();
	List<Subscription> getNewestWonOfLottery(Lottery lottery);
	List<T> findSubsriptionByCriterion(List<Criterion> restrictions,List<Order> orders, Integer start, Integer count);
	
	/**
	 * 查找符合排行榜统计的方案(成功+撤销+退款)
	 * @param currDateToInt 统计标识
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param maxSize 一次获取数
	 * @return
	 */
	List<T> findWonOfUnRank(Integer currDateToInt, Date startDate, Date endDate, int maxSize);
	
	/**
	 * 查找未统计的已更新中奖的方案
	 * @param maxSize
	 * @return
	 */
	List<T> findWinUpdateOfUnGrade(int maxSize);
	
}
