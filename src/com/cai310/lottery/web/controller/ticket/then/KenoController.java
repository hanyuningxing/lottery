package com.cai310.lottery.web.controller.ticket.then;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.dto.lottery.PeriodDataDTO;
import com.cai310.lottery.dto.lottery.PeriodDataInfoDTO;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.entity.lottery.ticket.TicketLogger;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Executable;
import com.cai310.lottery.support.ExecuteException;
import com.cai310.lottery.support.ExecutorUtils;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.ReflectionUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 高频彩投注基类
 */
public abstract class KenoController<T extends NumberScheme, E extends KenoSchemeDTO, I extends KenoPeriod>
		extends NumberController<T, E> {

	protected KenoService<I, T> kenoService;
	protected KenoManager<I, T> kenoManager;
	protected KenoPlayer<I, T> kenoPlayer;
	@Autowired
	private QueryService queryService;

	public abstract void setKenoService(KenoService<I, T> kenoService);

	public abstract void setKenoManager(KenoManager<I, T> kenoManager);

	public abstract void setKenoPlayer(KenoPlayer<I, T> kenoPlayer);

	protected Class<E> schemeDTOClass;

	@SuppressWarnings("unchecked")
	public KenoController() {
		this.schemeDTOClass = ReflectionUtils.getSuperClassGenricType(
				getClass(), 1);
	}

	@Override
	public String resultInfoTicket() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		StringBuffer sb = new StringBuffer();
		try {
			checkTicket();
			if (StringUtils.isNotBlank(this.wParam)) {
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					String periodNumber = null == wParam_map
							.get("periodNumber") ? null : String
							.valueOf(wParam_map.get("periodNumber"));
					if (StringUtils.isNotBlank(periodNumber)) {
						this.setPeriodNumber(periodNumber);
					}
					if (null == this.getwLotteryId()) {
						throw new WebDataException("10-彩种为空");
					}
					if (null == this.getPeriodNumber()) {
						throw new WebDataException("11-期号为空");
					}
					I period = this.kenoService.loadPeriod(this
							.getPeriodNumber());
					if (null == period) {
						throw new WebDataException("7-期号Id错误");
					}
					PeriodDataInfoDTO periodDataInfoDTO = new PeriodDataInfoDTO();
					periodDataInfoDTO.setLotteryType(period.getLotteryType());
					periodDataInfoDTO.setPeriodId(period.getId());
					periodDataInfoDTO.setResult(period.getResults());
					periodDataInfoDTO.setPeriodNumber(period.getPeriodNumber());
					periodDataInfoDTO.setPeriodTitle(period.getLotteryType()
							.getTitle());
					periodDataInfoDTO.setPrizeTime(DateUtil.dateToStr(
							period.getPrizeTime(), "yyyy-MM-dd HH:mm:ss"));
					periodDataInfoDTO.setEndCashTime(DateUtil.dateToStr(
							DateUtils.addDays(period.getPrizeTime(), 60),
							"yyyy-MM-dd"));
					map.put("periodData", periodDataInfoDTO);
					jsonConfig.setExcludes(new String[] { "winItemList", "won",
							"generalAdditional", "maxHit" });

				}
			}
			map.put("processId", "0");
		} catch (WebDataException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		}
		renderJson(map, jsonConfig);
		return null;
	}

	/**
	 * 
	 */
	@Override
	public String schemeInfo() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		List<Map<String, String>> flag = Lists.newArrayList();
		try {
			check();
			String id = null;
			if (StringUtils.isNotBlank(wParam)) {
				Map<String, Object> wParamMap = JsonUtil.getMap4Json(wParam);
				if (null != wParamMap) {
					id = String.valueOf(wParamMap.get("id"));
				}
			}
			if (StringUtils.isBlank(id))
				throw new WebDataException("5-orderId参数解析错误");
			Lottery lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
			Scheme scheme = kenoService.getScheme(Long.valueOf(id));
			scheme.setSubscriptions(null);
			I periodData = this.kenoService.findByIssueNumber(scheme
					.getPeriodNumber().trim());
			if (null != periodData
					&& StringUtils.isNotBlank(periodData.getResults())) {
				scheme.setRemark(periodData.getResults());
				map.put("result", periodData.getResults());
			}
			if (null == scheme.getPrizeSendTime()) {
				if (null != periodData && null != periodData.getPrizeTime()) {
					scheme.setPrizeSendTime(periodData.getPrizeTime());
				}
			}
			if (lottery.equals(Lottery.SSC)) {
				scheme = (SscScheme) scheme;
			} else if (lottery.equals(Lottery.SDEL11TO5)) {
				scheme = (SdEl11to5Scheme) scheme;
			}else if(lottery.equals(Lottery.XJEL11TO5)){
				scheme=(XjEl11to5Scheme)scheme;
			}
			map.put("processId", "0");

			map.put("scheme", scheme);
			jsonConfig.setExcludes(new String[] { "createTime",
					"prizeSendTime", "userBetCost", "uploaded", "updateWon",
					"updatePrize", "unSuccessWon", "unFullState",
					"transactionId", "totalProgressRate", "successWon",
					"together", "spareBaodiCost", "lotteryPlayType", "keepTop",
					"keepBottom", "compoundMode", "compoundContent",
					"baodiProgressRate", "contentText", "subscriptions",
					"ticketSchemeState", "compoundContentText",
					"compoundContentTextForList", "commitTime", "uploadTime",
					"orderPriorityText", "lastModifyTime", "wonStatusHtml" });

		} catch (WebDataException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		}
		renderJson(map, jsonConfig);
		return null;
	}

	protected E buildNormalSchemeDTO() throws WebDataException {
		E schemeDTO = null;
		try {
			schemeDTO = this.schemeDTOClass.newInstance();
		} catch (InstantiationException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("6-创建数据传输对象发生异常.");
		} catch (IllegalAccessException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("6-创建数据传输对象发生异常.");
		}
		schemeDTO.setBeforeTime(kenoPlayer.getBeforeTime());

		if (this.ticketPlatformInfo != null) {
			if (orderId == null)
				throw new WebDataException("6-订单号为空.");
			schemeDTO.setSponsorId(ticketPlatformInfo.getId());
			this.shareType = ShareType.SELF.ordinal();
		} else {
			if (user == null)
				throw new WebDataException("6-您还未登录,请登录后再操作.");

			schemeDTO.setSponsorId(user.getId());
		}
		I period = kenoService.findByIssueNumber(this.getPeriodNumber());
		if (period == null) {
			throw new WebDataException("6-期ID为空.");
		}
		if (period.getId() == null) {
			throw new WebDataException("6-期ID为空.");
		}
		schemeDTO.setPeriodId(period.getId());
		if (period.isSaleEnded(kenoPlayer.getBeforeTime())) {
			throw new WebDataException("6-购买的期次已经截止.");
		}

		if (this.getSchemeCost() == null || this.getSchemeCost() < 2) {
			throw new WebDataException("6-方案金额为空或不合法.");
		}

		schemeDTO.setSchemeCost(this.getSchemeCost());

		if (this.getMultiple() == null || this.getMultiple() < 1) {
			throw new WebDataException("6-方案倍数为空或不合法.");
		}
		schemeDTO.setMultiple(this.getMultiple());

		if (this.getUnits() == null || this.getUnits() < 1)
			throw new WebDataException("6-方案注数为空或不合法.");
		else if (this.getUnits() > Constant.MAX_UNITS)
			throw new WebDataException("6-方案注数不能大于" + Constant.MAX_UNITS + "注.");
		schemeDTO.setUnits(this.getUnits());

		int cost = schemeDTO.getUnits() * schemeDTO.getMultiple()
				* this.getUnitsMoney();
		if (cost != schemeDTO.getSchemeCost())
			throw new WebDataException("6-根据注数计算出来的金额与提交的方案金额不一致.");
		schemeDTO.setSponsorSubscriptionCost(BigDecimal
				.valueOf(getSchemeCost()));
		ContentBean contentBean = null;
		if (SalesMode.COMPOUND.equals(buildSalesMode())) {
			contentBean = buildCompoundContentBean();
		} else if (SalesMode.SINGLE.equals(buildSalesMode())) {
			contentBean = buildSingleContentBean();
		}
		if (contentBean == null
				|| StringUtils.isBlank(contentBean.getContent()))
			throw new WebDataException("6-方案内容为空.");
		else if (!schemeDTO.getUnits().equals(contentBean.getUnits()))
			throw new WebDataException("6-根据方案内容计算出来的注数与提交的注数不一致.");

		schemeDTO.setContent(contentBean.getContent());
		schemeDTO.setUnitsMoney(this.getUnitsMoney());
		schemeDTO.setMode(buildSalesMode());
		schemeDTO.setShareType(ShareType.SELF);
		schemeDTO.setSecretType(SecretType.FULL_SECRET);// /接票的都设为保密

		schemeDTO
				.setSubscriptionLicenseType(SubscriptionLicenseType.PUBLIC_LICENSE);// /加入方式都设为公共加入
		return schemeDTO;
	}

	@Override
	protected E buildSchemeDTO() throws WebDataException {
		if (!this.isChase)
			return buildNormalSchemeDTO();
		if (this.periodSizeOfChase == null)
			throw new WebDataException("6-追号期数不能为空.");
		else if (this.periodSizeOfChase < 2)
			throw new WebDataException("6-追号期数不能小于2.");
		else if (this.periodSizeOfChase > Constant.CHASE_MAX_PERIOD_SIZE)
			throw new WebDataException("6-最多只允许追"
					+ Constant.CHASE_MAX_PERIOD_SIZE + "期.");

		// List<Integer> chaseMultiples = this.createForm.getMultiplesOfChase();
		List<Integer> chaseMultiples = null;
		if (chaseMultiples == null) {
			chaseMultiples = new ArrayList<Integer>(this.periodSizeOfChase);
			for (int i = 0; i < this.periodSizeOfChase; i++) {
				chaseMultiples.add(this.getMultiple());
			}
		} else {
			int size = chaseMultiples.size();
			for (int i = chaseMultiples.size() - 1; i >= 0; i--) {
				Integer mul = chaseMultiples.get(i);
				if (mul != null && mul > 0) {
					size = i + 1;
					break;
				}
			}
			chaseMultiples = chaseMultiples.subList(0, size);
			if (chaseMultiples.size() < 2)
				throw new WebDataException("6-追号期数不能小于2.");
		}
		while (chaseMultiples.get(chaseMultiples.size() - 1) == 0) {
			chaseMultiples.remove(chaseMultiples.size() - 1);
		}

		E schemeDTO = buildNormalSchemeDTO();
		schemeDTO.setChase(true);
		schemeDTO.setMultiplesOfChase(chaseMultiples);
		schemeDTO.setRandomOfChase(false);
		if (null != wonStopOfChase && wonStopOfChase) {
			schemeDTO.setWonStopOfChase(wonStopOfChase);
			schemeDTO.setPrizeForWonStopOfChase(0);
		}
		if (this.totalCostOfChase == null) {
			throw new WebDataException("6-追号总金额不能为空.");
		}
		int costPerMult = schemeDTO.getUnits() * this.unitsMoney;// 单倍方案金额
		int totalCost = 0;
		for (Integer multiple : schemeDTO.getMultiplesOfChase()) {
			if (multiple != null) {
				if (multiple < 0)
					throw new WebDataException("6-追号倍数不能小于0.");
				if (multiple > Constant.MAX_MULTIPLE)
					throw new WebDataException("6-追号倍数不能大于"
							+ Constant.MAX_MULTIPLE + ".");

				totalCost += costPerMult * multiple;
			}
		}
		if (totalCost != this.totalCostOfChase) {
			throw new WebDataException("6-系统计算的追号总金额与提交的追号总金额不一致.");
		}
		schemeDTO.setTotalCostOfChase(totalCost);
		return schemeDTO;
	}

	/**
	 * 保存新方案_接票
	 */
	@Override
	public String createTicket() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();

		TicketLogger ticketLogger = null;
		List<Map<String, String>> flag = Lists.newArrayList();
		ReqParamVisitor reqParamVisitor_flag;
		try {
			ticketLogger = ticketLog(null, null);
			checkTicket();
			E schemeDTO;
			Map<String, String> temp = null;
			Long time_all = System.currentTimeMillis();
			for (ReqParamVisitor reqParamVisitor : ticketList) {
				reqParamVisitor_flag = reqParamVisitor;
				System.out.println(reqParamVisitor.getOrderId()
						+ "-----------------------开始");
				Long time = System.currentTimeMillis();
				try {
					if (ticketPlatformInfo == null)
						throw new WebDataException("1-找不到用户ID对应的用户.");
					if (ticketPlatformInfo.isLocked())
						throw new WebDataException("2-帐号被冻结");
					// //验证用户
					buildReqParamVisitor(reqParamVisitor);
					System.out.println("-----------------------解析"
							+ (System.currentTimeMillis() - time));
					time = System.currentTimeMillis();
					orderId = reqParamVisitor.getOrderId();

					schemeDTO = buildSchemeDTO();
					schemeDTO.setOrderId(orderId);
					System.out.println("-----------------------构建"
							+ (System.currentTimeMillis() - time));
					time = System.currentTimeMillis();
					// //////////////////////////////////////
					synchronized (ticketPlatformInfo.getId()) {
						Boolean isRepeatOrder = ticketThenEntityManager
								.isRepeatOrder(schemeDTO.getOrderId(),
										ticketPlatformInfo.getId());
						System.out.println("-----------------------检查"
								+ (System.currentTimeMillis() - time));
						time = System.currentTimeMillis();
						if (isRepeatOrder) {
							throw new WebDataException("2-已有此订单");
						}
						checkTicketPlatformInfo(ticketPlatformInfo, schemeDTO);
						schemeDTO.setIsTicket(true);
						schemeDTO.setOutOrderNumber(reqParamVisitor
								.getOutOrderNumber());
						if (null != this.platformInfo) {
							schemeDTO.setPlatform(platformInfo);
						}
						final E schemeDTOTemp = schemeDTO;
						ExecutorUtils.exec(new Executable() {
							public void run() throws ExecuteException {
								scheme = kenoService
										.createTicketScheme(schemeDTOTemp);
							}
						}, 3);
						System.out.println("-----------------------方案"
								+ (System.currentTimeMillis() - time));
						time = System.currentTimeMillis();
					}
					temp = Maps.newHashMap();
					temp.put("process", "0");
					temp.put("orderId", schemeDTO.getOrderId() + "");
					flag.add(temp);
					System.out.println("-----------------------完毕"
							+ (System.currentTimeMillis() - time));
					time = System.currentTimeMillis();
				} catch (WebDataException e) {
					temp = Maps.newHashMap();
					String process = "4";
					if (null != e.getMessage()
							&& e.getMessage().indexOf("-") != -1) {
						String tempStr = e.getMessage().split("-")[0];
						try {
							process = "" + Integer.valueOf(tempStr);
						} catch (Exception ex) {
						}// /如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					} else {
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					temp.put("orderId", orderId);
					flag.add(temp);
					continue;
				} catch (ServiceException e) {
					temp = Maps.newHashMap();
					String process = "4";
					if (null != e.getMessage()
							&& e.getMessage().indexOf("-") != -1) {
						String tempStr = e.getMessage().split("-")[0];
						try {
							process = "" + Integer.valueOf(tempStr);
						} catch (Exception ex) {
						}// /如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					} else {
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					temp.put("orderId", orderId);
					flag.add(temp);
					continue;
				} catch (Exception e) {
					temp = Maps.newHashMap();
					String process = "4";
					if (null != e.getMessage()
							&& e.getMessage().indexOf("-") != -1) {
						String tempStr = e.getMessage().split("-")[0];
						try {
							process = "" + Integer.valueOf(tempStr);
						} catch (Exception ex) {
						}// /如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					} else {
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					temp.put("orderId", orderId);
					flag.add(temp);
					continue;
				} catch (Throwable e) {
					temp = Maps.newHashMap();
					String process = "4";
					if (null != e.getMessage()
							&& e.getMessage().indexOf("-") != -1) {
						String tempStr = e.getMessage().split("-")[0];
						try {
							process = "" + Integer.valueOf(tempStr);
						} catch (Exception ex) {
						}// /如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					} else {
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					temp.put("orderId", orderId);
					flag.add(temp);
					continue;
				}
			}
			System.out.println("-----------------------完毕"
					+ (System.currentTimeMillis() - time_all));
			map.put("processId", "0");
			map.put("ticket", flag);
		} catch (WebDataException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			} else {
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			} else {
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			} else {
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Throwable e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			} else {
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		}
		renderJson(map, jsonConfig);
		ticketLogger = ticketLog(flag.toString(), ticketLogger);
		return null;
	}

	/**
	 * 保存新方案
	 */
	@Override
	public String create() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();

		TicketLogger ticketLogger = null;
		List<Map<String, String>> flag = Lists.newArrayList();
		ReqParamVisitor reqParamVisitor_flag;
		try {
			check();
			E schemeDTO;
			Map<String, String> temp = null;
			Long time_all = System.currentTimeMillis();
			for (ReqParamVisitor reqParamVisitor : ticketList) {
				reqParamVisitor_flag = reqParamVisitor;
				System.out.println(reqParamVisitor.getOrderId()
						+ "-----------------------开始");
				Long time = System.currentTimeMillis();
				try {
					user = userManager.getUser(Long.valueOf(reqParamVisitor
							.getUserId()));
					if (user == null)
						throw new WebDataException("1-找不到用户ID对应的用户.");
					if (user.isLocked())
						throw new WebDataException("2-帐号被冻结");
					// //验证用户
					checkUser(reqParamVisitor.getUserPwd(), user);
					buildReqParamVisitor(reqParamVisitor);
					System.out.println("-----------------------解析"
							+ (System.currentTimeMillis() - time));
					time = System.currentTimeMillis();
					schemeDTO = buildSchemeDTO();
					System.out.println("-----------------------构建"
							+ (System.currentTimeMillis() - time));
					time = System.currentTimeMillis();
					// //////////////////////////////////////
					synchronized (Constant.THENLOCK) {
						// Boolean isRepeatOrder =
						// getSchemeService().isRepeatOrder(orderId,schemeDTO.getSponsorId());
						System.out.println("-----------------------检查"
								+ (System.currentTimeMillis() - time));
						time = System.currentTimeMillis();
						// if(isRepeatOrder){
						// throw new WebDataException("2-已有此订单");
						// }
						checkUser(user, schemeDTO);
						if (null != this.platformInfo) {
							schemeDTO.setPlatform(platformInfo);
						}
						scheme = kenoService.createScheme(schemeDTO);
						// scheme = getSchemeService().createScheme(schemeDTO);
						System.out.println("-----------------------方案"
								+ (System.currentTimeMillis() - time));
						time = System.currentTimeMillis();
					}
					temp = Maps.newHashMap();
					temp.put("process", "0");
					temp.put("orderId", scheme.getId() + "");
					flag.add(temp);
					System.out.println("-----------------------完毕"
							+ (System.currentTimeMillis() - time));
					time = System.currentTimeMillis();
				} catch (WebDataException e) {
					temp = Maps.newHashMap();
					String process = "4";
					if (null != e.getMessage()
							&& e.getMessage().indexOf("-") != -1) {
						String tempStr = e.getMessage().split("-")[0];
						try {
							process = "" + Integer.valueOf(tempStr);
						} catch (Exception ex) {
						}// /如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					} else {
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					temp.put("orderId", scheme.getId() + "");
					flag.add(temp);
					continue;
				} catch (ServiceException e) {
					temp = Maps.newHashMap();
					String process = "4";
					if (null != e.getMessage()
							&& e.getMessage().indexOf("-") != -1) {
						String tempStr = e.getMessage().split("-")[0];
						try {
							process = "" + Integer.valueOf(tempStr);
						} catch (Exception ex) {
						}// /如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					} else {
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					temp.put("orderId", scheme.getId() + "");
					flag.add(temp);
					continue;
				} catch (Exception e) {
					temp = Maps.newHashMap();
					String process = "4";
					if (null != e.getMessage()
							&& e.getMessage().indexOf("-") != -1) {
						String tempStr = e.getMessage().split("-")[0];
						try {
							process = "" + Integer.valueOf(tempStr);
						} catch (Exception ex) {
						}// /如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					} else {
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					temp.put("orderId", scheme.getId() + "");
					flag.add(temp);
					continue;
				} catch (Throwable e) {
					temp = Maps.newHashMap();
					String process = "4";
					if (null != e.getMessage()
							&& e.getMessage().indexOf("-") != -1) {
						String tempStr = e.getMessage().split("-")[0];
						try {
							process = "" + Integer.valueOf(tempStr);
						} catch (Exception ex) {
						}// /如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					} else {
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					temp.put("orderId", scheme.getId() + "");
					flag.add(temp);
					continue;
				}
			}
			System.out.println("-----------------------完毕"
					+ (System.currentTimeMillis() - time_all));
			map.put("processId", "0");
			map.put("ticket", flag);
		} catch (WebDataException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			} else {
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			} else {
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			} else {
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Throwable e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
			} else {
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		}
		renderJson(map, jsonConfig);
		return null;
	}

	@Override
	protected SchemeService<T, E> getSchemeService() {
		return null;
	}

	public String resultList() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();
		StringBuffer sb = new StringBuffer();
		try {
			// check();
			checkTicket();
			Lottery lotteryType = null;
			if (StringUtils.isNotBlank(wLotteryId)) {
				try {
					lotteryType = Lottery.values()[Integer.valueOf(wLotteryId
							.trim())];
				} catch (Exception e) {
					throw new WebDataException("12-彩种错误");
				}
				if (null == lotteryType) {
					throw new WebDataException("12-彩种错误");
				}
			}
			// if (StringUtils.isBlank(start)){
			// throw new WebDataException("9-起始标志为空");
			// }
			// try{
			// Integer.valueOf(start);
			// } catch (Exception e) {
			// throw new WebDataException("9-起始标志错误");
			// }
			// if (StringUtils.isBlank(count)){
			// throw new WebDataException("10-条数标志为空");
			// }
			// try{
			// Integer.valueOf(count);
			// } catch (Exception e) {
			// throw new WebDataException("10-条数标志错误");
			// }
			Class<I> cls = this.kenoService.getIssueDataClass();
			DetachedCriteria criteria = DetachedCriteria.forClass(cls);
			criteria.add(Restrictions.gt("state", IssueState.ISSUE_SATE_RESULT));
			if (!"".equals(periodNumber)) {
				criteria.add(Restrictions.eq("periodNumber", periodNumber));
			}
			criteria.add(Restrictions.isNotNull("results"));
			criteria.addOrder(Order.desc("id"));
			List<I> list = null;
			if ("".equals(periodNumber)) {
				list = queryService.findByDetachedCriteria(criteria, 0, 1);
			} else {
				list = queryService.findByDetachedCriteria(criteria);
			}
			List<PeriodDataDTO> resultList = Lists.newArrayList();
			if (null != list && !list.isEmpty()) {
				for (I kenoPeriod : list) {
					if (null != kenoPeriod) {
						PeriodDataDTO periodDataDTO = new PeriodDataDTO();
						periodDataDTO.setLotteryType(kenoPeriod
								.getLotteryType());
						periodDataDTO.setPeriodId(kenoPeriod.getId());
						periodDataDTO.setPeriodNumber(kenoPeriod
								.getPeriodNumber());
						periodDataDTO.setPeriodTitle(kenoPeriod
								.getLotteryType().getTitle());
						periodDataDTO.setPrizeTime(DateUtil.dateToStr(
								kenoPeriod.getPrizeTime(),
								"yyyy-MM-dd HH:mm:ss"));
						periodDataDTO.setResult(kenoPeriod.getResults());
						resultList.add(periodDataDTO);
					}
				}
			}
			map.put("processId", "0");

			map.put("resultList", resultList);
		} catch (WebDataException e) {
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId = "7";
			if (null != e.getMessage() && e.getMessage().indexOf("-") != -1) {
				String temp = e.getMessage().split("-")[0];
				try {
					processId = "" + Integer.valueOf(temp);
				} catch (Exception ex) {
				}// /如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			} else {
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}
			map.put("processId", processId);

			map.put("errorMsg", e.getMessage());
		}
		renderJson(map, jsonConfig);
		return null;
	}

}
