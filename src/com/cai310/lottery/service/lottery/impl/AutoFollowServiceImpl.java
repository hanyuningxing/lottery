package com.cai310.lottery.service.lottery.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.AutoFollowDetailState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.dto.lottery.SubscribeDTO;
import com.cai310.lottery.entity.lottery.AutoFollowDetail;
import com.cai310.lottery.entity.lottery.AutoFollowOrder;
import com.cai310.lottery.entity.lottery.AutoFollowQueue;
import com.cai310.lottery.entity.lottery.AutoFollowQueueId;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.AutoFollowException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.AutoFollowEntityManager;
import com.cai310.lottery.service.lottery.AutoFollowService;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.utils.BigDecimalUtil;

/**
 * 自动跟单服务实现类
 * 
 */
@Service("autoFollowServiceImpl")
@Transactional
public class AutoFollowServiceImpl implements AutoFollowService {

	@Autowired
	protected AutoFollowEntityManager autoFollowEntityManager;

	@Autowired
	protected UserEntityManager userManager;

	private Map<Lottery, SchemeService> schemeServiceMap;

	private Map<Lottery, SchemeEntityManager> schemeEntityManagerMap;

	@Autowired
	public void setSchemeServiceList(List<SchemeService> schemeServiceList) {
		if (schemeServiceList != null && !schemeServiceList.isEmpty()) {
			schemeServiceMap = new HashMap<Lottery, SchemeService>();
			for (SchemeService service : schemeServiceList) {
				schemeServiceMap.put(service.getLotteryType(), service);
			}
		}
	}

	@Autowired
	public void setSchemeEntityManagerList(List<SchemeEntityManager> schemeEntityManagerList) {
		if (schemeEntityManagerList != null && !schemeEntityManagerList.isEmpty()) {
			schemeEntityManagerMap = new HashMap<Lottery, SchemeEntityManager>();
			for (SchemeEntityManager service : schemeEntityManagerList) {
				schemeEntityManagerMap.put(service.getLottery(), service);
			}
		}
	}

	protected SchemeService getSchemeService(Lottery LotteryType) {
		return schemeServiceMap.get(LotteryType);
	}

	protected SchemeEntityManager getSchemeEntityManager(Lottery LotteryType) {
		return schemeEntityManagerMap.get(LotteryType);
	}
	public void autoFollow(AutoFollowQueueId queueId, Long orderId) {
		AutoFollowQueue queue = autoFollowEntityManager.getAutoFollowQueue(queueId);
		if (queue == null)
			throw new ServiceException("跟单任务[#" + queueId.toString() + "]不存在.");

		AutoFollowOrder order = autoFollowEntityManager.getAutoFollowOrder(orderId);
		if (order == null)
			throw new ServiceException("跟单订单[#" + orderId + "]不存在.");

		SchemeEntityManager schemeEntityManager = getSchemeEntityManager(queue.getId().getLotteryType());
		if (schemeEntityManager == null)
			throw new ServiceException("找不到与彩种对应的" + SchemeEntityManager.class.getName() + "实例.");

		Scheme scheme = schemeEntityManager.getScheme(queue.getId().getSchemeId());
		if (scheme == null)
			throw new ServiceException("方案[#" + queue.getId().getSchemeId() + "]不存在.");
		else if (!scheme.getSponsorId().equals(order.getSponsorUserId()))
			throw new ServiceException("跟单方案不正确.");
		else if (scheme.getLotteryType() != order.getLotteryType()
				|| (scheme.getLotteryPlayType() == null && order.getLotteryPlayType() != null)
				|| (scheme.getLotteryPlayType() != null && order.getLotteryPlayType() == null)
				|| (scheme.getLotteryPlayType() != null && !scheme.getLotteryPlayType().equals(
						order.getLotteryPlayType())))
			throw new ServiceException("跟单彩种不正确.");

		boolean isAlreadyFollow = autoFollowEntityManager.isAlreadyFollow(orderId, scheme.getId());
		if (isAlreadyFollow)
			return;

		AutoFollowDetail detail = new AutoFollowDetail();
		detail.setFollowOrderId(order.getId());
		detail.setFollowUserId(order.getFollowUserId());
		detail.setFollowUserName(order.getFollowUserName());
		detail.setSponsorUserId(order.getSponsorUserId());
		detail.setSponsorUserName(order.getSponsorUserName());
		detail.setLotteryType(order.getLotteryType());
		detail.setPeriodId(scheme.getPeriodId());
		detail.setPeriodNumber(scheme.getPeriodNumber());
		detail.setSchemeId(scheme.getId());

		try {
			if (scheme.getSubscriptionLicenseType() == SubscriptionLicenseType.PASSWORD_LICENSE)
				throw new AutoFollowException("发起人设置了跟单密码,不能自动跟单.");

			User user = userManager.getUser(order.getFollowUserId());
			if (user == null) {
				throw new ServiceException("跟单用户[#" + order.getFollowUserId() + "]不存在.");
			} 
			BigDecimal allAccountBalance = user.getRemainMoney();
			if (allAccountBalance == null) {
				throw new ServiceException("跟单用户[#" + order.getFollowUserId() + "]资金账户不存在.");
			} 
			if (allAccountBalance.doubleValue() == 0) {
				throw new AutoFollowException("账户余额为零.");
			}
			

			StringBuilder sb = new StringBuilder();
			BigDecimal schemeRemainingCost = scheme.getRemainingCost();
			sb.append("方案当前剩余金额为[").append(Constant.MONEY_FORMAT.format(schemeRemainingCost)).append("元]");
			sb.append(",当前账户余额为[").append(Constant.MONEY_FORMAT.format(allAccountBalance)).append("元]");

			BigDecimal followCost = null; // 计算跟单金额
			switch (order.getFollowType()) {
			case FUND:
				followCost = order.getFollowCost();
				sb.append(",当前跟单设置为[").append(order.getFollowType().getTypeName()).append(",跟单金额：")
						.append(Constant.MONEY_FORMAT.format(followCost)).append("元]");
				break;
			case PERCEND:
				double percent = order.getFollowPercent().doubleValue() / 100;
				followCost = BigDecimalUtil.valueOf(scheme.getSchemeCost() * percent);
				sb.append(",当前跟单设置为[").append(order.getFollowType().getTypeName()).append(",跟单百分比：")
						.append(order.getFollowPercent()).append("%]");
				break;
			default:
				throw new AutoFollowException("自动跟单类型不合法.");
			}

			// 格式化跟单金额
			followCost = followCost.setScale(Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);

			if (followCost.compareTo(allAccountBalance) == 1)
				followCost = allAccountBalance.setScale(Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);

			if (followCost.compareTo(schemeRemainingCost) == 1)
				followCost = schemeRemainingCost.setScale(Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);

			if (order.getPeriodMaxFollowCost() != null && order.getPeriodMaxFollowCost().doubleValue() > 0) {
				sb.append(",当前设置了一期跟单金额上限为[").append(Constant.MONEY_FORMAT.format(order.getPeriodMaxFollowCost()))
						.append("元]");

				BigDecimal areadlyFollowCost = autoFollowEntityManager.countFollowCost(order.getId(),
						scheme.getPeriodId());
				if (areadlyFollowCost == null)
					areadlyFollowCost = BigDecimal.ZERO;

				sb.append(",当期已跟单金额为[").append(Constant.MONEY_FORMAT.format(areadlyFollowCost)).append("元]");

				BigDecimal remainFollowCost = order.getPeriodMaxFollowCost().subtract(areadlyFollowCost);
				if (remainFollowCost.doubleValue() <= 0)
					throw new AutoFollowException("当期跟单金额已达到上限," + sb.toString() + ".");

				if (remainFollowCost.compareTo(followCost) < 0)
					followCost = remainFollowCost.setScale(Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);
			}

			if (scheme.getMinSubscriptionCost() != null && scheme.getMinSubscriptionCost().doubleValue() > 0) {
				if (scheme.getMinSubscriptionCost().compareTo(followCost) == 1
						&& followCost.compareTo(schemeRemainingCost) != 0) {
					throw new AutoFollowException("发起人设置了最低认购金额["
							+ Constant.MONEY_FORMAT.format(scheme.getMinSubscriptionCost()) + "元]," + sb.toString()
							+ ".");
				}
			} else {
				if (followCost.compareTo(new BigDecimal(0.01)) < 0) {
					throw new AutoFollowException("您的跟单条件不符合!");
				}
			}

			if (followCost.compareTo(BigDecimal.ZERO) <= 0) {
				throw new AutoFollowException("您的跟单条件不符合!");
			}

			SchemeService schemeService = getSchemeService(queue.getId().getLotteryType());
			if (schemeService == null)
				throw new ServiceException("找不到与彩种对应的方案服务实例.");

			SubscribeDTO dto = new SubscribeDTO();
			dto.setSchemeId(scheme.getId());
			dto.setUserId(order.getFollowUserId());
			dto.setSubscriptionCost(followCost);
			dto.setWay(SubscriptionWay.AUTOFOLLOW);
			try {
				int tryTimes = 0;
				boolean subscribe = true;
				while (subscribe) {
					try {
						schemeService.subscribe(dto);
						subscribe = false;
					} catch (StaleObjectStateException e) {
						tryTimes++;
						subscribe = tryTimes < 3;
						if (!subscribe)
							throw new RuntimeException("系统繁忙.");
					}
				}

				detail.setFollowCost(followCost);
				detail.setRemark("自动跟单成功," + sb.toString());
				detail.setState(AutoFollowDetailState.SUCCESS);
			} catch (ServiceException e) {
				throw new AutoFollowException("自动跟单失败：" + e.getMessage() + "." + sb.toString());
			}
		} catch (AutoFollowException e) {
			detail.setRemark(e.getMessage());
			detail.setState(AutoFollowDetailState.FAIL);
		}

		detail = autoFollowEntityManager.saveAutoFollowDetail(detail);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findAutoFollowOrderId(AutoFollowQueueId queueId) {
		AutoFollowQueue queue = autoFollowEntityManager.getAutoFollowQueue(queueId);
		if (queue == null)
			throw new ServiceException("跟单任务[#" + queueId.toString() + "]不存在.");

		SchemeEntityManager schemeEntityManager = getSchemeEntityManager(queue.getId().getLotteryType());
		if (schemeEntityManager == null)
			throw new ServiceException("找不到与彩种对应的" + SchemeEntityManager.class.getName() + "实例.");

		Scheme scheme = schemeEntityManager.getScheme(queue.getId().getSchemeId());
		if (scheme == null)
			throw new ServiceException("方案[#" + queue.getId().getSchemeId() + "]不存在.");

		List idList = autoFollowEntityManager.findAutoFollowOrderId(scheme.getSponsorId(), scheme.getLotteryType(),
				scheme.getLotteryPlayType());
		List<Long> alreadyIdList = autoFollowEntityManager.findFollowOrderId(scheme.getId());
		idList.removeAll(alreadyIdList);
		return idList;
	}

	public void autoFollowFinish(AutoFollowQueueId queueId) {
		AutoFollowQueue queue = autoFollowEntityManager.getAutoFollowQueue(queueId);
		if (queue == null)
			throw new ServiceException("跟单任务[#" + queueId.toString() + "]不存在.");

		SchemeEntityManager schemeEntityManager = getSchemeEntityManager(queue.getId().getLotteryType());
		if (schemeEntityManager == null)
			throw new ServiceException("找不到与彩种对应的" + SchemeEntityManager.class.getName() + "实例.");

		Scheme scheme = schemeEntityManager.getScheme(queue.getId().getSchemeId());
		if (scheme == null)
			throw new ServiceException("方案[#" + queue.getId().getSchemeId() + "]不存在.");

		scheme.setAutoFollowCompleted(true);
		scheme = schemeEntityManager.saveScheme(scheme);

		autoFollowEntityManager.removeAutoFollowQueue(queue.getId());

	}
}
