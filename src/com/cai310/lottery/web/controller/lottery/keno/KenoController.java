package com.cai310.lottery.web.controller.lottery.keno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SdEl11to5Constant;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.SecurityUserHolder;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.CapacityChaseBean;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.LotteryBaseController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.Struts2Utils;
import com.ibm.icu.util.Calendar;

/**
 * 高频彩投注基类
 */
@Results({
	@Result(name = "showSuccess", location = "/WEB-INF/content/common/keno-scheme-showSuccess.ftl"),
	@Result(name="admin-show",location="/WEB-INF/content/admin/lottery/keno/admin-keno-show.ftl")})
public abstract class KenoController<I extends KenoPeriod, S extends NumberScheme> extends LotteryBaseController {
	private static final long serialVersionUID = -7123991959872456815L;
	private static Map<String, Object> data = new HashMap<String, Object>();
	// private static final long delay = 500; // 时间缓存时间
	protected KenoService<I, S> kenoService;
	protected KenoManager<I, S> kenoManager;
	protected KenoPlayer<I, S> kenoPlayer;

	@Autowired
	@Qualifier("commonEternalCache")
	private Cache commonEternalCache;

	public Cache getCommonEternalCache() {
		return commonEternalCache;
	}
	public void setCommonEternalCache(Cache commonEternalCache) {
		this.commonEternalCache = commonEternalCache;
	}
	
	protected ChasePlan chasePlan;
	protected I period;
	protected List<I> periods;
	protected I resultIssueData;
	protected String resultDate;
	protected List<String> resultDates;
	protected S scheme;
	protected long leftTime; // 距截止时间
	protected int count; // 查询条数
	protected Integer cost;
	protected Integer multiple;
	protected KenoSchemeCreateForm createForm;
	protected Pagination pagination = new Pagination(20);
	protected int menuType;
	// 资讯
	private List<NewsInfoData> forecastList = new ArrayList<NewsInfoData>();
	private List<NewsInfoData> skillsList = new ArrayList<NewsInfoData>();
	private List<NewsInfoData> resultList = new ArrayList<NewsInfoData>();
	private List<NewsInfoData> infoList = new ArrayList<NewsInfoData>();
	private Map<String,String> noticeNewsMap = new HashMap<String,String> ();


	public static final String ISNOMAL = "1";

	protected Long id;

	public abstract void setKenoService(KenoService<I, S> kenoService);

	public abstract void setKenoManager(KenoManager<I, S> kenoManager);

	public abstract void setKenoPlayer(KenoPlayer<I, S> kenoPlayer);

	public abstract Lottery getLotteryType();

	public abstract Integer getLineLimit();

	public abstract Integer getBetTypePrize();

	public abstract String getType();
	
	public abstract S getKenoScheme();

	@Autowired
	protected QueryService queryService;

	public int combination(int m, int n) {
		if (m < 0 || n < 0 || m < n)
			return -1;
		n = n < (m - n) ? n : m - n;
		if (n == 0)
			return 1;
		int result = m;
		for (int i = 2, j = result - 1; i <= n; i++, j--) {
			result = result * j / i;
		}
		return result;
	}

	public List dantuoBig(int danCount, int tuoCount) {
		List list = new ArrayList();
		int level = 5; // 该奖级必须中多少个球
		int betCodeCount = getLineLimit();// 每一个单注里面号码数量
		for (int i = 0; i <= danCount; i++) {
			int tuoBingoCount = level - danCount + i;
			// (拖的数量-拖中奖数量) (一注号码数量 - 胆数量 - 拖中奖个数)
			int contentBetCount = combination(tuoCount - tuoBingoCount, betCodeCount - danCount - tuoBingoCount);
			if (contentBetCount > 0) {
				list.add(contentBetCount);
			}
		}
		return list;
	}

	public List dantuoSmall(int danCount, int tuoCount) {
		List list = new ArrayList();
		int level = getLineLimit(); // 该奖级必须中多少个球
		int betCodeCount = getLineLimit();// 每一个单注里面号码数量
		int i = tuoCount;
		if (i > 5 - danCount) {
			i = 5 - danCount;
		}
		for (; i >= level - danCount; i--) {

			// (拖的数量-拖中奖数量) (一注号码数量 - 胆数量 - 拖中奖个数)
			int contentBetCount = combination(i, betCodeCount - danCount);
			if (contentBetCount > 0) {
				list.add(contentBetCount);
			}
		}
		return list;

	}

	public List compoundRandomBig(int codeCount) {
		List list = new ArrayList();
		int level = 5; // 该奖级必须中多少个球
		int betCodeCount = getLineLimit();// 每一个单注里面号码数量
		// 总共投了多少个球-要中多少个球，一注里面球数量-要中多少个球
		int contentBetCount = combination(codeCount - level, betCodeCount - level);
		if (contentBetCount > 0) {
			list.add(contentBetCount);
		}
		return list;
	}

	public List compoundRandomSmall(int codeCount) {
		int level = getLineLimit(); // 该奖级必须中多少个球
		int betCodeCount = getLineLimit();// 每一个单注里面号码数量
		List list = new ArrayList();
		if (codeCount > 5)
			codeCount = 5;
		for (int i = codeCount; i >= level; i--) {
			int contentBetCount = combination(i, level);
			if (contentBetCount > 0) {
				list.add(contentBetCount);
			}
		}
		return list;
	}

	public List bingoCount() {
		Set<Integer> bingoCount = new HashSet<Integer>();
		String[] arr = createForm.getContent().split("(\r\n|\n)+");
		for (String content : arr) {
			boolean hasDan = false;// 是否为含胆码格式
			if (content.indexOf(SdEl11to5Constant.SEPARATOR_DAN_FOR_NUMBER) >= 0) {
				hasDan = true;
			} else {
			}
			String betStr = content.split(":")[1].trim();
			if (hasDan) {
				String[] tempArr = betStr.split(SdEl11to5Constant.SEPARATOR_DAN_FOR_NUMBER);
				int danCount = Arrays.asList(tempArr[0].split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER)).size();
				int tuoCount = Arrays.asList(tempArr[1].split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER)).size();
				if (getLineLimit() < 5) {
					bingoCount.addAll(dantuoSmall(danCount, tuoCount));
				} else {
					bingoCount.addAll(dantuoBig(danCount, tuoCount));
				}

			} else {
				int codeCount = Arrays.asList(betStr.split(SdEl11to5Constant.SEPARATOR_FOR_NUMBER)).size();
				if (getLineLimit() < 5) {
					bingoCount.addAll(compoundRandomSmall(codeCount));
				} else {
					bingoCount.addAll(compoundRandomBig(codeCount));
				}

			}
		}
		List list = new ArrayList(bingoCount);
		Collections.sort(list);
		return list;
	}

	public String getRuleString() {
		StringBuffer sb = new StringBuffer();
		sb.append("每日开奖时间：[");
		if (Lottery.EL11TO5.equals(getLotteryType())) {
			sb.append(" 9:00 - 22:00");
		} 
		if(Lottery.GDEL11TO5.equals(getLotteryType())) {
			sb.append(" 9:00 - 23:00");
		}
		sb.append("每" + kenoPlayer.getPeriodMinutes() + "分钟一期 ],每天开奖" + kenoPlayer.getMaxPeriod() + "次");
		return sb.toString();
	}

	/**
	 * 取得可以追号的总期数
	 * 
	 * @return
	 */
	public String canChaseIssueNum() {
		Date dateNow = kenoPlayer.getDateNow();
		int beforeTime = kenoPlayer.getBeforeTime();
		Integer canChaseIssuenum = kenoService.getCanChaseIssueNum(dateNow, beforeTime);
		data = new HashMap<String, Object>();
		data.put("canChaseIssuenum", canChaseIssuenum);
		Struts2Utils.renderJson(data);
		return null;
	}

	/**
	 * 取得可以追号的期数
	 * 
	 * @return
	 */
	public String canChaseIssue() {
		Date dateNow = kenoPlayer.getDateNow();
		int beforeTime = kenoPlayer.getBeforeTime();
		if (0 == count) {
			count = 5;
		}
		if (null == multiple || 0 == multiple) {
			multiple = 1;
		}
		List<I> canChaseIssue = kenoService.getCanChaseIssue(dateNow, beforeTime, count);
		data = new HashMap<String, Object>();
		data.put("chase_ul", ChaseIssue(canChaseIssue, cost, multiple));
		Struts2Utils.renderJson(data);
		return null;
	}

	public String canCapacityChaseIssue() {
		Date dateNow = kenoPlayer.getDateNow();
		int beforeTime = kenoPlayer.getBeforeTime();
		List<I> canChaseIssue = kenoService.getCanChaseIssue(dateNow, beforeTime, 120);
		List kenoPeriodList = new ArrayList();

		for (KenoPeriod kenoPeriod : canChaseIssue) {
			Map<String, String> kenoPeriodMap = new HashMap<String, String>();
			kenoPeriodMap.put("id", kenoPeriod.getId().toString());
			kenoPeriodMap.put("periodNumber", kenoPeriod.getPeriodNumber());
			kenoPeriodList.add(kenoPeriodMap);
		}
		// 非任选的玩法 命中一注
		String type = getType();
		if (!Lottery.SSC.equals(getLotteryType())&&type.equals(ISNOMAL)) {
			if (createForm.getSalesMode().equals(SalesMode.COMPOUND)) {
				data.put("bingoCountList", bingoCount());
			}
		}
		//时时彩 大小双单 特殊处理
		if (Lottery.SSC.equals(getLotteryType())) {
			List list = new ArrayList();
			for (int i = 1; i <= Integer.valueOf(type); i++) {
				list.add(i);
			}
			data.put("bingoCountList", list);
		}
		data.put("kenoPeriodList", kenoPeriodList);
		Struts2Utils.renderJson(data);
		return null;
	}

	private String ChaseIssue(List<I> canChaseIssue, Integer cost, Integer multiple) {
		StringBuffer sb = new StringBuffer();
		I issue;
		String itemId;
		for (int i = 0; i < canChaseIssue.size(); i++) {
			issue = canChaseIssue.get(i);
			sb.append("<li>");
			itemId = "chase_item_" + i;
			sb.append(getNumString(i + 1));
			sb.append("&nbsp;<input id=\"checkbox_" + itemId + "\" type=\"checkbox\" checked=\"checked\" />");
			if (StringUtils.isNotBlank(issue.getPeriodNumber())) {
				sb.append("&nbsp;第" + issue.getPeriodNumber().trim() + "期");
			}
			sb.append("&nbsp;<input id=\"multiple_"
					+ itemId
					+ "\" type=\"text\" name=\"createForm.multiplesOfChase\" value=\""
					+ multiple
					+ "\" size=\"2\" style=\"IME-MODE: disabled;\" onkeydown=\"number_check(this,event,null)\" onkeyup=\"chgChaseMultiple(this)\" oncontextmenu=\"return false;\" autocomplete=\"off\"/>倍");
			sb.append("&nbsp;<span id=\"cost_" + itemId + "\" style=\"color:#F00\">" + cost + "</span>元");
			sb.append("</li>");
		}
		return sb.toString();
	}

	private String getNumString(int i) {
		if (i < 10)
			return "0" + i;
		return "" + i;
	}

	/**
	 * 同步服务器时间
	 * 
	 * @return
	 */
	public String asyncTime() {
		Date dateNow = kenoPlayer.getDateNow();
		int beforeTime = kenoPlayer.getBeforeTime();
		period = kenoManager.getCurrentData();
		if (period != null) {
			leftTime = period.getEndedTime().getTime() - dateNow.getTime() - beforeTime * 60000;
			Calendar c = Calendar.getInstance();
			c.setTime(period.getEndedTime());
			c.add(Calendar.MINUTE, beforeTime * -1);

			data.put("endTime", period.getEndedTime().getTime());
			data.put("beforeTime", beforeTime);
			data.put("periodId", period.getId());
			data.put("issueNumber", period.getPeriodNumber());
			data.put("stateValue", period.getState().getStateValue());
			data.put("stateName", period.getState().getStateName());
			data.put("leftTime", DateFormatUtils.format(c.getTime(), "MM/dd/yyyy HH:mm:ss"));
			data.put("resultTime", DateFormatUtils.format(period.getEndedTime(), "MM/dd/yyyy HH:mm:ss"));
			data.put("nowTime", DateFormatUtils.format(new Date(), "MM/dd/yyyy HH:mm:ss"));
		}
		Struts2Utils.renderJson(data);
		return null;
	}

	/**
	 * 显示投注页面
	 * 
	 * @return
	 */
	public String index() {
		// 读取当前期数据
		period = kenoManager.getCurrentData();
		Struts2Utils.setAttribute("issueData", period);
		loadNewsList();

		return SUCCESS;
	}

	/**
	 * 构建方案创建数据传输对象
	 * 
	 * @return 方案创建数据传输对象
	 * @throws WebDataException
	 */
	protected KenoSchemeDTO buildNormalSchemeDTO() throws WebDataException {
		if (this.createForm == null)
			throw new WebDataException("表单数据为空.");

		KenoSchemeDTO schemeDTO = new KenoSchemeDTO();
		

		
		
		schemeDTO.setBeforeTime(kenoPlayer.getBeforeTime());

		User user = getLoginUser();
		if (user == null) {
			throw new WebDataException("您还未登录,请登录后再操作.");
		}
		schemeDTO.setSponsorId(user.getId());

		if (this.createForm.getPeriodId() == null) {
			throw new WebDataException("期ID为空.");
		}
		schemeDTO.setPeriodId(this.createForm.getPeriodId());

		I period = kenoService.findIssueDataById(this.createForm.getPeriodId());
		if (null != this.createForm.getStartChasePeriodId()) {
			I startChasePeriod = kenoService.findIssueDataById(this.createForm.getStartChasePeriodId());
			if (startChasePeriod.isSaleEnded(kenoPlayer.getBeforeTime())) {
				throw new WebDataException("购买的期次已经截止.");
			}
		}

		if (period.isSaleEnded(kenoPlayer.getBeforeTime())) {
			throw new WebDataException("购买的期次已经截止.");
		}

		if (this.createForm.getSchemeCost() == null || this.createForm.getSchemeCost() < 2) {
			throw new WebDataException("方案金额为空或不合法.");
		}

		schemeDTO.setSchemeCost(this.createForm.getSchemeCost());

		if (this.createForm.getMultiple() == null || this.createForm.getMultiple() < 1) {
			throw new WebDataException("方案倍数为空或不合法.");
		}
		schemeDTO.setMultiple(this.createForm.getMultiple());

		if (this.createForm.getUnits() == null || this.createForm.getUnits() < 1)
			throw new WebDataException("方案注数为空或不合法.");
		else if (this.createForm.getUnits() > Constant.MAX_UNITS)
			throw new WebDataException("方案注数不能大于" + Constant.MAX_UNITS + "注.");
		schemeDTO.setUnits(this.createForm.getUnits());

		int cost = schemeDTO.getUnits() * schemeDTO.getMultiple() * this.createForm.getUnitsMoney();
		if (cost != schemeDTO.getSchemeCost())
			throw new WebDataException("根据注数计算出来的金额与提交的方案金额不一致.");

		ContentBean contentBean = null;
		try {
			if (SalesMode.COMPOUND.equals(createForm.getSalesMode())) {
				contentBean = buildCompoundContentBean();
			} else if (SalesMode.SINGLE.equals(createForm.getSalesMode())) {
				contentBean = buildSingleContentBean();
			}
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
		if (contentBean == null || StringUtils.isBlank(contentBean.getContent()))
			throw new WebDataException("方案内容为空.");
		else if (!schemeDTO.getUnits().equals(contentBean.getUnits()))
			throw new WebDataException("根据方案内容计算出来的注数与提交的注数不一致.");

		schemeDTO.setContent(contentBean.getContent());
		schemeDTO.setUnitsMoney(this.createForm.getUnitsMoney());
		schemeDTO.setMode(createForm.getSalesMode());

		if (this.createForm.getSecretType() == null)
			throw new WebDataException("方案保密方式为空.");
		schemeDTO.setSecretType(this.createForm.getSecretType());

		if (this.createForm.getSubscriptionLicenseType() == null)
			schemeDTO.setSubscriptionLicenseType(SubscriptionLicenseType.PUBLIC_LICENSE);
		else if (this.createForm.getSubscriptionLicenseType() == SubscriptionLicenseType.PASSWORD_LICENSE) {
			if (StringUtils.isBlank(this.createForm.getSubscriptionPassword()))
				throw new WebDataException("认购密码为空.");
			String pwd = this.createForm.getSubscriptionPassword().trim().toLowerCase();
			if (!pwd.matches("[a-z0-9]{6,10}"))
				throw new WebDataException("密码必须由6至10个英文字母或数字组成.");

			schemeDTO.setSubscriptionPassword(pwd);
			schemeDTO.setSubscriptionLicenseType(SubscriptionLicenseType.PASSWORD_LICENSE);
		} else {
			schemeDTO.setSubscriptionLicenseType(this.createForm.getSubscriptionLicenseType());
		}
		return schemeDTO;
	}

	protected abstract ContentBean buildCompoundContentBean() throws DataException;

	protected abstract ContentBean buildSingleContentBean() throws DataException;

	/**
	 * 计算单式注数
	 */
	public String calcSingleBetUnits() {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (this.createForm == null)
				throw new WebDataException("表单数据为空.");
			ContentBean bean = buildSingleContentBean();
			int betUnits = bean.getUnits();

			map.put("success", true);
			map.put("betUnits", betUnits);
		} catch (DataException e) {
			map.put("success", false);
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			this.logger.warn("计算注数发生异常！", e);
			map.put("success", false);
			map.put("msg", "计算注数发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}

	protected KenoSchemeDTO buildSchemeDTO() throws WebDataException {
		if (this.createForm == null)
			throw new WebDataException("表单数据为空.");

		if (!this.createForm.isChase())
			return buildNormalSchemeDTO();
		if (!this.createForm.isChase())
			throw new WebDataException("不能发起追号投注.");
		if (this.createForm.getPeriodSizeOfChase() == null)
			throw new WebDataException("追号期数不能为空.");
		else if (this.createForm.getPeriodSizeOfChase() < 2)
			throw new WebDataException("追号期数不能小于2.");
		else if (this.createForm.getPeriodSizeOfChase() > Constant.CHASE_MAX_PERIOD_SIZE)
			throw new WebDataException("最多只允许追" + Constant.CHASE_MAX_PERIOD_SIZE + "期.");

		List<Integer> chaseMultiples = this.createForm.getMultiplesOfChase();
		try {
			if (chaseMultiples == null) {
				chaseMultiples = new ArrayList<Integer>(this.createForm.getPeriodSizeOfChase());
				for (int i = 0; i < this.createForm.getPeriodSizeOfChase(); i++) {
					chaseMultiples.add(this.createForm.getMultiple());
				}
			}
			while (chaseMultiples.get(chaseMultiples.size() - 1) == 0) {
				chaseMultiples.remove(chaseMultiples.size() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebDataException("请重新计算投注方案倍数");
		}

		KenoSchemeDTO schemeDTO = buildNormalSchemeDTO();
		
		schemeDTO.setChase(true);
		schemeDTO.setMultiplesOfChase(chaseMultiples);
		schemeDTO.setRandomOfChase(this.createForm.isRandomOfChase());

		if (this.createForm.isWonStopOfChase()) {
			schemeDTO.setWonStopOfChase(true);
			if (this.createForm.getPrizeForWonStopOfChase() != null && this.createForm.getPrizeForWonStopOfChase() >= 0) {
				schemeDTO.setPrizeForWonStopOfChase(this.createForm.getPrizeForWonStopOfChase());
			} else {
				schemeDTO.setPrizeForWonStopOfChase(0);
			}
		}

		// 出号后停止
		if (this.createForm.isOutNumStop()) {
			schemeDTO.setOutNumStop(true);
		} else {
			schemeDTO.setOutNumStop(false);
		}
		// 开始追号期数
		schemeDTO.setStartChasePeriodId(this.createForm.getStartChasePeriodId());

		if (this.createForm.getTotalCostOfChase() == null)
			throw new WebDataException("追号总金额不能为空.");
		int costPerMult = schemeDTO.getUnits() * this.createForm.getUnitsMoney();// 单倍方案金额
		int totalCost = 0;
		for (Integer multiple : schemeDTO.getMultiplesOfChase()) {
			if (multiple != null) {
				if (multiple < 0)
					throw new WebDataException("追号倍数不能小于0.");
				// if (multiple > Constant.MAX_MULTIPLE)
				// throw new WebDataException("追号倍数不能大于" + Constant.MAX_MULTIPLE
				// + ".");
				totalCost += costPerMult * multiple;
			}
		}
		if (totalCost != this.createForm.getTotalCostOfChase())
			throw new WebDataException("系统计算的追号总金额与提交的追号总金额不一致.");
		schemeDTO.setTotalCostOfChase(totalCost);
		
		
		// TODO 设置追号收益信息
		CapacityChaseBean capacityChaseBean = new CapacityChaseBean();
		capacityChaseBean.setStartChasePeriodNum(this.createForm.getStartChasePeriodNum());
		capacityChaseBean.setPeriodSizeOfChase(String.valueOf(this.createForm.getPeriodSizeOfChase()));
		capacityChaseBean.setStartMultiple(this.createForm.getStartMultiple());
		capacityChaseBean.setHasInvested(this.createForm.getHasInvested());
		capacityChaseBean.setExpectedHit(this.createForm.getExpectedHit());
		if ("0".equals(Struts2Utils.getRequest().getParameter("lucreradio"))) {
			capacityChaseBean.setAllafterlucre(this.createForm.getAllafterlucre());
		} 
		if ("1".equals(Struts2Utils.getRequest().getParameter("lucreradio"))) {
			capacityChaseBean.setBefortermmember(this.createForm.getBefortermmember());
			capacityChaseBean.setBeforelc(this.createForm.getBeforelc());
			capacityChaseBean.setAferlc(this.createForm.getAferlc());
		}
		if ("0".equals(Struts2Utils.getRequest().getParameter("lucrepradio"))) {
			capacityChaseBean.setAll_lucrep_select(this.createForm.getAll_lucrep_select());
		} 
		if ("1".equals(Struts2Utils.getRequest().getParameter("lucrepradio"))) {
			capacityChaseBean.setBefortermmemberp(this.createForm.getBefortermmemberp());
			capacityChaseBean.setBefore_lcp_select(this.createForm.getBefore_lcp_select());
			capacityChaseBean.setAferlcp_select(this.createForm.getAferlcp_select());
		}
		schemeDTO.setCapacityProfit(JSONObject.fromObject(capacityChaseBean).toString());

		return schemeDTO;
	}
	
	
	protected KenoSchemeDTO quick_buildSchemeDTO() throws WebDataException {
		if (this.createForm == null)
			throw new WebDataException("表单数据为空.");

		if (!this.createForm.isChase())
			return quick_buildNormalSchemeDTO();
		if (!this.createForm.isChase())
			throw new WebDataException("不能发起追号投注.");
		if (this.createForm.getPeriodSizeOfChase() == null)
			throw new WebDataException("追号期数不能为空.");
		else if (this.createForm.getPeriodSizeOfChase() < 2)
			throw new WebDataException("追号期数不能小于2.");
		else if (this.createForm.getPeriodSizeOfChase() > Constant.CHASE_MAX_PERIOD_SIZE)
			throw new WebDataException("最多只允许追" + Constant.CHASE_MAX_PERIOD_SIZE + "期.");

		//List<Integer> chaseMultiples = this.createForm.getMultiplesOfChase();
		List<Integer> chaseMultiples = null;
		try {
			if (chaseMultiples == null) {
				chaseMultiples = new ArrayList<Integer>(createForm.getPeriodSizeOfChase());
				for (int i = 0; i < createForm.getPeriodSizeOfChase(); i++) {
					chaseMultiples.add(1);
				}
			}
			while (chaseMultiples.get(chaseMultiples.size() - 1) == 0) {
				chaseMultiples.remove(chaseMultiples.size() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebDataException("请重新计算投注方案倍数");
		}

		KenoSchemeDTO schemeDTO = quick_buildNormalSchemeDTO();
		schemeDTO.setChase(true);
		schemeDTO.setMultiplesOfChase(chaseMultiples);
		schemeDTO.setRandomOfChase(this.createForm.isRandomOfChase());

		if (this.createForm.isWonStopOfChase()) {
			schemeDTO.setWonStopOfChase(true);
			if (this.createForm.getPrizeForWonStopOfChase() != null && this.createForm.getPrizeForWonStopOfChase() >= 0) {
				schemeDTO.setPrizeForWonStopOfChase(this.createForm.getPrizeForWonStopOfChase());
			} else {
				schemeDTO.setPrizeForWonStopOfChase(0);
			}
		}

		// 出号后停止
		if (this.createForm.isOutNumStop()) {
			schemeDTO.setOutNumStop(true);
		} else {
			schemeDTO.setOutNumStop(false);
		}
		// 开始追号期数
		schemeDTO.setStartChasePeriodId(this.createForm.getStartChasePeriodId());

		if (this.createForm.getTotalCostOfChase() == null)
			throw new WebDataException("追号总金额不能为空.");
		int costPerMult = schemeDTO.getUnits() * this.createForm.getUnitsMoney();// 单倍方案金额
		int totalCost = 0;
		for (Integer multiple : schemeDTO.getMultiplesOfChase()) {
			if (multiple != null) {
				if (multiple < 0)
					throw new WebDataException("追号倍数不能小于0.");
				// if (multiple > Constant.MAX_MULTIPLE)
				// throw new WebDataException("追号倍数不能大于" + Constant.MAX_MULTIPLE
				// + ".");
				totalCost += costPerMult * multiple;
			}
		}
		if (totalCost != this.createForm.getTotalCostOfChase())
			throw new WebDataException("系统计算的追号总金额与提交的追号总金额不一致.");
		schemeDTO.setTotalCostOfChase(totalCost);
		return schemeDTO;
	}
	
	
	protected KenoSchemeDTO quick_buildNormalSchemeDTO() throws WebDataException {
		if (this.createForm == null)
			throw new WebDataException("表单数据为空.");

		KenoSchemeDTO schemeDTO = new KenoSchemeDTO();
		schemeDTO.setBeforeTime(kenoPlayer.getBeforeTime());

		User user = getLoginUser();
		if (user == null) {
			throw new WebDataException("您还未登录,请登录后再操作.");
		}
		schemeDTO.setSponsorId(user.getId());

		if (this.createForm.getPeriodId() == null) {
			throw new WebDataException("期ID为空.");
		}
		schemeDTO.setPeriodId(this.createForm.getPeriodId());

		I period = kenoService.findIssueDataById(this.createForm.getPeriodId());
		if (null != this.createForm.getStartChasePeriodId()) {
			I startChasePeriod = kenoService.findIssueDataById(this.createForm.getStartChasePeriodId());
			if (startChasePeriod.isSaleEnded(kenoPlayer.getBeforeTime())) {
				throw new WebDataException("购买的期次已经截止.");
			}
		}

		if (period.isSaleEnded(kenoPlayer.getBeforeTime())) {
			throw new WebDataException("购买的期次已经截止.");
		}
		schemeDTO.setMode(SalesMode.COMPOUND);
		
		this.createForm.setSalesMode(SalesMode.COMPOUND);///匹配方案需要的参数
		
		schemeDTO.setSchemeCost(2);

		schemeDTO.setMultiple(1);

		schemeDTO.setUnits(1);
		this.createForm.setUnits(1);
		schemeDTO.setShareType(ShareType.SELF);
		schemeDTO.setSecretType(SecretType.FULL_SECRET);
		schemeDTO.setSubscriptionLicenseType(SubscriptionLicenseType.PUBLIC_LICENSE);
		int cost = schemeDTO.getUnits() * schemeDTO.getMultiple() * this.createForm.getUnitsMoney();
		if (cost != schemeDTO.getSchemeCost())
			throw new WebDataException("根据注数计算出来的金额与提交的方案金额不一致.");
		this.createForm.setContent(this.createForm.buildContent());
		ContentBean contentBean = null;
		try {
			if (SalesMode.COMPOUND.equals(createForm.getSalesMode())) {
				contentBean = buildCompoundContentBean();
			} else if (SalesMode.SINGLE.equals(createForm.getSalesMode())) {
				contentBean = buildSingleContentBean();
			}
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
		if (contentBean == null || StringUtils.isBlank(contentBean.getContent()))
			throw new WebDataException("方案内容为空.");
		else if (!schemeDTO.getUnits().equals(contentBean.getUnits()))
			throw new WebDataException("根据方案内容计算出来的注数与提交的注数不一致.");

		schemeDTO.setContent(contentBean.getContent());
		schemeDTO.setUnitsMoney(this.createForm.getUnitsMoney());
		schemeDTO.setMode(createForm.getSalesMode());

		
		return schemeDTO;
	}
	public long getLeftTime() {
		return leftTime;
	}

	public void setLeftTime(long leftTime) {
		this.leftTime = leftTime;
	}

	public KenoSchemeCreateForm getCreateForm() {
		return createForm;
	}

	public void setCreateForm(KenoSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	/**
	 * 用户投注
	 * 
	 * @return
	 */
	public String bet() {
		try {
			KenoSchemeDTO schemeDTO = buildSchemeDTO();
			S scheme = kenoService.createScheme(schemeDTO);
			addActionMessage("投注成功！");
			if (Struts2Utils.isAjaxRequest())
				if (null != scheme) {
					this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(scheme));
				}
			return show();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return this.error();
	}
	protected String getSchemeUrl(S scheme) {
		return Struts2Utils.getRequest().getContextPath() + "/" + scheme.getLotteryType().getKey()
				+ "/scheme!show.action?id=" + scheme.getId();
	}

	protected String getChaseUrl() {
		return Struts2Utils.getRequest().getContextPath() + "/lottery/chase!list.action";
	}
	protected String getChaseUrl(S scheme) {
		if(null!=scheme&&null!=scheme.getChaseId()){
			return Struts2Utils.getRequest().getContextPath() + "/" + scheme.getLotteryType().getKey() +"/chase!show.action?id="+scheme.getChaseId();
		}else{
			return getChaseUrl();
		}
			
	}

	/**
	 * 显示方案发起成功页面
	 * 
	 * @return
	 */
	public String showSuccess() {
		if (this.id != null)
			this.scheme = kenoService.getScheme(this.id);
		this.period = kenoService.findIssueDataById(scheme.getPeriodId());
		return "showSuccess";
	}

	/**
	 * 方案详细
	 * 
	 * @return
	 * @throws WebDataException
	 */
	public AdminUser getAdminUser() {
		Object o = SecurityUserHolder.getCurrentUser();
		if (o instanceof AdminUser) {
			return (AdminUser) o;
		} else {
			return null;
		}
	}

	public String show() throws WebDataException {
		try {
			AdminUser adminUser = getAdminUser();
			if (null != adminUser) {
				// /后台用户
				if (null != id) {
					this.scheme = kenoService.getScheme(id);
				} else {
					String schemeNumber = Struts2Utils.getRequest().getParameter("schemeNumber");
					if (StringUtils.isBlank(schemeNumber))
						throw new WebDataException("方案号为空.");
					Long schemeId = getLotteryType().getSchemeId(schemeNumber);
					if (schemeId == null)
						throw new ServiceException("方案号不正确.");
					this.scheme = kenoService.getScheme(schemeId);
				}
				if (this.scheme == null)
					throw new WebDataException("方案不存在.");
				if (scheme.getPeriodId() == null)
					throw new WebDataException("方案期号不存在.");
				period = kenoService.findIssueDataById(scheme.getPeriodId());
				Struts2Utils.setAttribute("canViewDetail", true);
				return "admin-show";
			} else {
				User user = getLoginUser();
				if (user == null)
					throw new WebDataException("您还未登录,请登录后再操作.");
				if (null != id) {
					this.scheme = kenoService.getScheme(id);
				} else {
					String schemeNumber = Struts2Utils.getRequest().getParameter("schemeNumber");
					if (StringUtils.isBlank(schemeNumber))
						throw new WebDataException("方案号为空.");
					Long schemeId = getLotteryType().getSchemeId(schemeNumber);
					if (schemeId == null)
						throw new ServiceException("方案号不正确.");
					this.scheme = kenoService.getScheme(schemeId);
				}
				if (this.scheme == null)
					throw new WebDataException("方案不存在.");
				if (!user.getId().equals(this.scheme.getSponsorId()))
					throw new WebDataException("登录用户不是该方案发起人.");
				if (scheme.getPeriodId() == null)
					throw new WebDataException("方案期号不存在.");
				period = kenoService.findIssueDataById(scheme.getPeriodId());
				boolean canViewDetail = "true".equalsIgnoreCase(canViewDetail(scheme, period, user));
				Struts2Utils.setAttribute("canViewDetail", canViewDetail);
				return "show";
			}
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return this.error();
	}

	public String canViewDetail(S scheme, KenoPeriod period, User user) {
		if (user != null && user.getId().equals(scheme.getSponsorId()))
			return "true";
		switch (scheme.getSecretType()) {
		case FULL_PUBLIC:
			return "true";
		case DRAWN_PUBLIC:
			if (period.isDrawed())
				return "true";
			else
				return SecretType.DRAWN_PUBLIC.getSecretName();
		}
		return "方案保密";
	}

	/**
	 * 投注记录
	 * 
	 * @return
	 * @throws WebDataException
	 */
	public String list() throws WebDataException {
		User user = getLoginUser();
		if (user == null) {
			addActionError("您还未登录,请登录后再操作.");
			return this.error();
		}
		Class<S> cls = kenoService.getSchemeClass();
		XDetachedCriteria criteria = new XDetachedCriteria(cls);
		if (scheme != null && !StringUtils.isBlank(scheme.getPeriodNumber())) {
			criteria.add(Restrictions.like("periodNumber", scheme.getPeriodNumber(), MatchMode.START));
		}
		criteria.add(Restrictions.eq("sponsorId", user.getId()));
		criteria.setMaxResults(count);
		criteria.addOrder(Order.desc("id"));
		pagination = kenoService.findByCriteriaAndPagination(criteria, pagination);

		this.loadNewsList();
		return "list";
	}

	/**
	 * 开奖号
	 * 
	 * @return
	 */
	public String note() {
		Class<I> cls = kenoService.getIssueDataClass();
		XDetachedCriteria criteria = new XDetachedCriteria(cls);
		if (period != null && !StringUtils.isBlank(period.getPeriodNumber())) {
			criteria.add(Restrictions.like("periodNumber", period.getPeriodNumber(), MatchMode.START));
		}
		criteria.add(Restrictions.gt("state", IssueState.ISSUE_SATE_RESULT));
		criteria.add(Restrictions.isNotNull("results"));
		// criteria.setMaxResults(count);
		criteria.addOrder(Order.desc("id"));
		// zhuhui motify by 2011-05-03 近20 30 50 期 改为分页pagesize 20 30 50
		// 与低频彩保持一致
		if (count > 0) {
			pagination.setPageSize(count);
		}
		pagination = kenoService.findByCriteriaAndPagination(criteria, pagination);

		loadNewsList();

		return "note";
	}

	/**
	 * 开奖号
	 * 
	 * @return
	 */
	public String result() {
		try {
			Class<I> cls = kenoService.getIssueDataClass();
			DetachedCriteria criteria = DetachedCriteria.forClass(cls);
			criteria.add(Restrictions.isNotNull("results"));
			if (StringUtils.isBlank(resultDate)) {
				resultDate = DateUtil.getTodaySixStr();
			}
			criteria.add(Restrictions.gt("prizeTime", DateUtil.getTodaySixDate(resultDate)));
			criteria.add(Restrictions.lt("prizeTime", DateUtil.getNextdaySixDate(DateUtil.getTodaySixDate(resultDate))));
			criteria.addOrder(Order.desc("id"));
			periods = kenoService.findKenoPeriodByCriteria(criteria, null);
			resultDates = DateUtil.getSixList();
			return "result-info";
		} catch (ServiceException e) {
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}

	public String getDateFormat() {
		return DateUtil.getLocalDate("yyyy-MM-dd") + " " + DateUtil.getWeekStr(new Date());
	}

	/**
	 * 加载预测跟技巧新闻
	 */
	protected void loadNewsList() {
		// 预测
		XDetachedCriteria criteria = new XDetachedCriteria(NewsInfoData.class);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("type", InfoType.FORECAST));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		criteria.setCacheable(true);
		Pagination pg = new Pagination(10);
		pg = queryService.findByCriteriaAndPagination(criteria, pg);
		forecastList = pg.getResult();
		// 技巧
		criteria = new XDetachedCriteria(NewsInfoData.class);
		pg = new Pagination(10);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("type", InfoType.SKILLS));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		criteria.setCacheable(true);
		pg = queryService.findByCriteriaAndPagination(criteria, pg);
		skillsList = pg.getResult();
		// 开奖结果
		criteria = new XDetachedCriteria(NewsInfoData.class);
		pg = new Pagination(10);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("type", InfoType.NOTICE));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		criteria.setCacheable(true);
		pg = queryService.findByCriteriaAndPagination(criteria, pg);
		resultList = pg.getResult();
		// 新闻
		criteria = new XDetachedCriteria(NewsInfoData.class);
		pg = new Pagination(10);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("type", InfoType.INFO));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		criteria.setCacheable(true);
		pg = queryService.findByCriteriaAndPagination(criteria, pg);
		
		for(Lottery lottery:Lottery.values()){
			String key = lottery+ Constant.channelNoticeNewsCacheKey;
			Element el = commonEternalCache.get(key);
			if(null!=el) {
			String channelNoticeNews = (String)el.getValue();
			noticeNewsMap.put(lottery.toString(), channelNoticeNews);
			}
		}
		infoList = pg.getResult();
	}

	/**
	 * 玩法介绍
	 */
	public String introduction() throws WebDataException {
		return "introduction";
	}

	/**
	 * 合买规则
	 */
	public String protocol() throws WebDataException {
		return "protocol";
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the cost
	 */
	public Integer getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(Integer cost) {
		this.cost = cost;
	}

	/**
	 * @return the multiple
	 */
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple
	 *            the multiple to set
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the menuType
	 */
	public Integer getMenuType() {
		return menuType;
	}

	/**
	 * @param menuType
	 *            the menuType to set
	 */
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public I getPeriod() {
		return period;
	}

	public void setPeriod(I period) {
		this.period = period;
	}

	public S getScheme() {
		return scheme;
	}

	public void setScheme(S scheme) {
		this.scheme = scheme;
	}

	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}

	public String getResultDate() {
		return resultDate;
	}

	public void setResultDate(String resultDate) {
		this.resultDate = resultDate;
	}

	public List<I> getPeriods() {
		return periods;
	}

	public void setPeriods(List<I> periods) {
		this.periods = periods;
	}

	public List<String> getResultDates() {
		return resultDates;
	}

	public void setResultDates(List<String> resultDates) {
		this.resultDates = resultDates;
	}

	public I getResultIssueData() {
		return resultIssueData;
	}

	public void setResultIssueData(I resultIssueData) {
		this.resultIssueData = resultIssueData;
	}

	public List<NewsInfoData> getForecastList() {
		return forecastList;
	}

	public void setForecastList(List<NewsInfoData> forecastList) {
		this.forecastList = forecastList;
	}

	public List<NewsInfoData> getSkillsList() {
		return skillsList;
	}

	public void setSkillsList(List<NewsInfoData> skillsList) {
		this.skillsList = skillsList;
	}

	public List<NewsInfoData> getResultList() {
		return resultList;
	}

	public void setResultList(List<NewsInfoData> resultList) {
		this.resultList = resultList;
	}

	public List<NewsInfoData> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<NewsInfoData> infoList) {
		this.infoList = infoList;
	}
	public Map<String, String> getNoticeNewsMap() {
		return noticeNewsMap;
	}
	public void setNoticeNewsMap(Map<String, String> noticeNewsMap) {
		this.noticeNewsMap = noticeNewsMap;
	}

}
