package com.cai310.lottery.service.lottery;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.WinRecordType;
import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.dto.lottery.SchemeUploadDTO;
import com.cai310.lottery.dto.lottery.SubscribeDTO;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.Subscription;

public interface SchemeService<T extends Scheme, E extends SchemeDTO> {

	/**
	 * 创建方案
	 * 
	 * @param schemeDTO 方案发起数据传输对象
	 * @return 创建的方案
	 */
	T createScheme(E schemeDTO);
	/**
	 * 创建出票
	 * 
	 * @param schemeDTO 方案发起数据传输对象
	 * @return 创建的出票
	 */
	T createTicketScheme(E schemeDTO);
	/**
	 * 认购或保底方案
	 * 
	 * @param schemeId 方案编号
	 * @param userId 操作的用户编号
	 * @param subscriptionCost 认购金额
	 * @param baodiCost 保底金额
	 * @return 操作完的方案对象
	 */
	/**
	 * 认购或保底方案
	 * 
	 * @param dto 认购或保底的相关参数
	 * @return 操作完的方案对象
	 * @see com.cai310.lottery.dto.lottery.SubscribeDTO
	 */
	T subscribe(SubscribeDTO dto);

	/**
	 * 上传方案
	 * 
	 * @param dto 方案上传的相关参数对象
	 * @return 方案对象
	 */
	T uploadScheme(SchemeUploadDTO dto);

	/**
	 * 保底转认购
	 * 
	 * @param baodiId 保底记录ID
	 * @return 认购记录
	 */
	Subscription baodiTransferSubscription(Long baodiId);

	/**
	 * 取消认购
	 * 
	 * @param subscriptionId 认购记录ID
	 */
	void cancelSubscription(Long subscriptionId);

	/**
	 * 取消保底
	 * 
	 * @param baodiId 保底记录ID
	 */
	void cancelBaodi(Long baodiId);

	/**
	 * 发起人撤销方案
	 * 
	 * @param schemeId 方案ID
	 */
	void cancelSchemeBySponsor(Long schemeId);

	/**
	 * 后台撤销方案
	 * 
	 * @param schemeId 方案ID
	 * @param adminUserId 管理员编号
	 */
	void cancelSchemeByAdmin(Long schemeId, Long adminUserId);

	/**
	 * 强制撤销方案
	 * 
	 * @param schemeId 方案ID
	 * @param adminUserId 管理员编号
	 */
	void forceCancelScheme(Long schemeId, Long adminUserId);

	/**
	 * 方案退款
	 * 
	 * @param schemeId 方案ID
	 */
	void refundment(Long schemeId);

	/**
	 * 执行保底（使用或撤销保底）
	 * 
	 * @param schemeId 方案编号
	 */
	void runBaodi(Long schemeId);

	/**
	 * 派放奖金
	 * 
	 * @param schemeId 方案编号
	 */
	void delivePrize(long schemeId);

	/**
	 * 执行完成交易（交易成功或撤销）
	 * 
	 * @param schemeId 方案编号
	 */
	void commitOrCancelTransaction(Long schemeId);

	/**
	 * 更新中奖
	 * 
	 * @param schemeId 方案编号
	 */
	void updateResult(long schemeId);

	/**
	 * 更新奖金
	 * 
	 * @param schemeId 方案编号
	 */
	void updatePrize(long schemeId);

	/**
	 * 方案是否已截止
	 * 
	 * @param schemeId 方案编号
	 * @return 是否已截止
	 */
	boolean isSaleEnded(Long schemeId);

	/**
	 * @return 彩种类型
	 */
	Lottery getLotteryType();
	
	/**
	 * 重置方案为出票、已打印状态
	 * 
	 * @param schemeId 方案编号
	 */
	public void forcePrint(Long schemeId, Long adminUserId);
	
	/**
	 * 派发战绩
	 * 
	 * @param schemeId 方案编号
	 */
	public void deliveWinRecord(long schemeId);
	
	/**
	 * 接票是否重复
	 * 
	 * @param orderId 订单编号
	 * @param userId user编号
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
	 * @param orderId 订单编号列表	
	 * @return 方案列表
	 */
	List<T> findSchemeByOrderId(List<String> orderId);
	
	/**
	 * 
	 * @param sponsorId 发起用户
	 * @param startDate 开始时间	
	 * @param endDate 结束时间
	 * @return
	 */
	List<T> findWonSchemeBySponsorId(Long sponsorId,Date startDate,Date endDate);
	
	/**
	 * 保存交易成功的方案(符合更新中奖结果的方案)
	 * @param scheme
	 */
	public void saveTradeSuccessScheme(T scheme);

}
