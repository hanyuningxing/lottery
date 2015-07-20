package com.cai310.lottery.web.controller.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SczcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcMatchEntityManagerImpl;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.ReflectionUtils;
import com.cai310.utils.Struts2Utils;

public abstract class ResultController<T extends PeriodData> extends BaseController {
	private static final long serialVersionUID = -3004288085663395253L;
	@Autowired
	protected LczcMatchEntityManagerImpl lczcMatchEntityManagerImpl;
  
	@Autowired
	protected SczcMatchEntityManagerImpl sczcMatchEntityManagerImpl;

	@Autowired
	protected SfzcMatchEntityManagerImpl sfzcMatchEntityManagerImpl;
	@Autowired
	private QueryService queryService;
	@Autowired
	@Qualifier("commonQueryCache")
	private Cache commonQueryCache;
	protected Class<T> periodClass;
	protected Lottery lotteryType;
	protected Long id;
	/**
	 * 方案投注的方式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 */
	protected SalesMode salesMode;
	protected PeriodSales singlePeriodSales;
	protected PeriodSales compoundPeriodSales;
	protected Period period;
	protected T periodData;
	protected List<Period> periods;
	protected ZcMatch[] matchArr;
	protected int count;
	private int menuType;
	protected Period newestPeriod;
	protected T newestPeriodData;

	@SuppressWarnings("unchecked")
	public ResultController() {
		this.periodClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
	}

	/**
	 * 销售期相关实体管理实例
	 * 
	 * @see com.cai310.lottery.service.lottery.PeriodEntityManager
	 */
	@Autowired
	protected PeriodEntityManager periodEntityManager;

	public abstract PeriodDataEntityManagerImpl<T> getPeriodDataEntityManagerImpl();

	private Pagination pagination = new Pagination(20);
	private List<NewsInfoData> forecastAndSkills=new ArrayList<NewsInfoData>();
	private List<NewsInfoData> resultAndInfo=new ArrayList<NewsInfoData>();
	public String index() {
		try { 
			if (count > 0) {
				pagination.setPageSize(count);
			}
			StringBuffer buf = new StringBuffer();
			buf.append("Select p as period,d as periodData ");
			buf.append("From Period p," + periodClass.getName() + " d ");
			buf.append("Where p.drawed=:drawed And p.id = d.periodId ");
			buf.append("Order by p.periodNumber desc");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("drawed", true);
			pagination = queryService.findByHql(buf.toString(), map, pagination, Criteria.ALIAS_TO_ENTITY_MAP);
			loadCrrPeriod();
			searchNewestData();
			loadForecastAndSkillsNewsList();
			loadResultAndInfoNewsList();
			return "list";
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}
	/**
	 * 加载预测跟技巧新闻
	 */
	protected void loadForecastAndSkillsNewsList(){
		XDetachedCriteria criteria = new XDetachedCriteria(NewsInfoData.class);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.or(Restrictions.eq("type", InfoType.FORECAST), Restrictions.eq("type", InfoType.SKILLS)));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		Pagination pg = new Pagination(10);
		pg = queryService.findByCriteriaAndPagination(criteria, pg);
		forecastAndSkills = pg.getResult();
	}
	/**
	 * 加载开奖结果跟新闻
	 */
	protected void loadResultAndInfoNewsList(){
		XDetachedCriteria criteria = new XDetachedCriteria(NewsInfoData.class);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.or(Restrictions.eq("type", InfoType.NOTICE), Restrictions.eq("type", InfoType.INFO)));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		Pagination pg = new Pagination(10);
		pg = queryService.findByCriteriaAndPagination(criteria, pg);
		resultAndInfo = pg.getResult();
		
	}
	/**
	 * 加载当前期数据
	 */
	public void loadCrrPeriod() {
		this.preparePeriods(true);
		if (salesMode == null)
			salesMode = SalesMode.COMPOUND;

		String shareTypeStr = Struts2Utils.getParameter("shareType");
		ShareType shareType = null;
		try {
			shareType = ShareType.valueOf(shareTypeStr);
		} catch (Exception e) {
		}
		if (shareType == null)
			shareType = ShareType.SELF;

		Struts2Utils.setAttribute("shareType", shareType);
	}

	/**
	 * 准备销售期数据
	 * 
	 * @param onlyOnSale
	 *            是否只显示在售的
	 */
	protected void preparePeriods(boolean onlyOnSale) {
		List<Period> currentPeriods = periodEntityManager.findCurrentPeriods(getLotteryType());
		if (currentPeriods != null && !currentPeriods.isEmpty()) {
			this.periods = new ArrayList<Period>();
			String periodNumber = Struts2Utils.getRequest().getParameter("periodNumber");
			for (Period p : currentPeriods) {
				if (onlyOnSale && !(p.isOnSale() || p.isPaused()))
					continue;

				this.periods.add(p);
				if (this.period == null && StringUtils.isNotBlank(periodNumber)
						&& p.getPeriodNumber().equals(periodNumber))
					this.period = p;
			}

			if (this.periods.isEmpty())
				this.periods.add(currentPeriods.get(currentPeriods.size() - 1));
			if (this.period == null)
				this.period = this.periods.get(0);

			this.singlePeriodSales = periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(),
					SalesMode.SINGLE));
			this.compoundPeriodSales = periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(),
					SalesMode.COMPOUND));
		}
	}

	/**
	 * 最新开奖号码和信息
	 */
	protected void searchNewestData() throws WebDataException {
		newestPeriodData = getPeriodDataEntityManagerImpl().getNewestResultPeriodData();
		if (null != newestPeriodData) {
			newestPeriod = periodEntityManager.getPeriod(newestPeriodData.getPeriodId());
		}
	}

	public String resultInfo() {
		try {
			if (this.id == null) {
				periodData = getPeriodDataEntityManagerImpl().getNewestResultPeriodData();
				period = periodEntityManager.getPeriod(periodData.getPeriodId());
			} else {
				period = periodEntityManager.getPeriod(this.getId());
			}
			if (period == null)
				throw new WebDataException("期号不存在.");
			periodData = getPeriodDataEntityManagerImpl().getPeriodData(period.getId());
			if (periodData == null)
				throw new WebDataException("期数据不存在.");
			//zhuhui motify 2011-08-25   修改
			periods = periodEntityManager.getDrawPeriodList(this.getLotteryType(), 20);
			switch (this.getLotteryType()) {
			case LCZC:
				matchArr = lczcMatchEntityManagerImpl.findMatchs(period.getId());
				break;
			case SFZC:
				matchArr = sfzcMatchEntityManagerImpl.findMatchs(period.getId());
				break;
			case SCZC:
				matchArr = sczcMatchEntityManagerImpl.findMatchs(period.getId());
				break;
			}
			return "info";
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	/**
	 * 取得已经开奖期数
	 * 
	 * @return
	 */
	public String drawedIssue() {
		Map<String, Object> data = new HashMap<String, Object>();
		List periodHistoryList = getPeriodHistory();
		if(null==periodHistoryList||periodHistoryList.isEmpty()){
			List list = new ArrayList();
			Map<String,String> subMap = new HashMap<String,String>();
			subMap.put("word", "选择开奖期号");
			subMap.put("value", "-1");
			list.add(subMap);
			data.put("options", list);
		}else{
			data.put("options", drawedIssueOption(pagination.getResult()));
		}
		Struts2Utils.renderJson(data);
		return null;
	}

	private List  getPeriodHistory() {
		StringBuffer buf = new StringBuffer();
		buf.append("Select p.id as ID, p.periodNumber as PERIODNUMBER ");
		buf.append("From Period p," + periodClass.getName() + " d ");
		buf.append("Where p.drawed=:drawed And p.id = d.periodId ");
		buf.append("Order by p.id desc");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("drawed", true);
		return queryService.findByHql(buf.toString(), paramMap, pagination, Criteria.ALIAS_TO_ENTITY_MAP).getResult();
	}
	private List drawedIssueOption(List periods) {
		List list = new ArrayList();
		Map<String,String> subMap = new HashMap<String,String>();
		subMap.put("word", "选择开奖期号");
		subMap.put("value", "-1");
		list.add(subMap);
		for (int i = 0; i < periods.size(); i++) {
			Map map = (Map) periods.get(i);
			String ID = map.get("ID").toString();
			String PERIODNUMBER = (String) map.get("PERIODNUMBER").toString();
			subMap = new HashMap();
			subMap.put("word", PERIODNUMBER);
			subMap.put("value", ID);
			list.add(subMap);
		}
		return list;
	}

	public String getDateFormat() {
		return DateUtil.getLocalDate("yyyy-MM-dd") + " " + DateUtil.getWeekStr(new Date());
	}

	public static String getZcRsultString(String result) {
		if (result == null) {
			return null;
		}
		char ch;
		char[] input = result.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < len; i++) {
			ch = input[i];
			out.append(ch);
			if (i != len - 1) {
				out.append("&nbsp;");
			}
		}
		return out.toString();
	}

	public static String getRsultString(String result, Integer star, Integer end, String color) {
		if (result == null) {
			return null;
		}
		if (star > end) {
			return null;
		}
		StringBuffer out = new StringBuffer();
		out.append("<font color='" + color + "'>");
		if (result.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER) != -1) {
			String[] resultArr = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			if (resultArr.length < end) {
				return null;
			}
			for (; star < end; star++) {
				out.append(getBallStr(Integer.valueOf(resultArr[star])));
				out.append("&nbsp;");
			}
		}
		out.append("</font>");
		return out.toString();
	}

	private static String getBallStr(Integer val) {
		if (val < 10)
			return "0" + val;
		else {
			return "" + val;
		}
	}

	public abstract Lottery getLotteryType();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public int getMenuType() {
		return menuType;
	}

	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public T getPeriodData() {
		return periodData;
	}

	public void setPeriodData(T periodData) {
		this.periodData = periodData;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public List<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}

	public ZcMatch[] getMatchArr() {
		return matchArr;
	}

	public void setMatchArr(ZcMatch[] matchArr) {
		this.matchArr = matchArr;
	}

	public Period getNewestPeriod() {
		return newestPeriod;
	}

	public void setNewestPeriod(Period newestPeriod) {
		this.newestPeriod = newestPeriod;
	}

	public T getNewestPeriodData() {
		return newestPeriodData;
	}

	public void setNewestPeriodData(T newestPeriodData) {
		this.newestPeriodData = newestPeriodData;
	}

	public SalesMode getSalesMode() {
		return salesMode;
	}

	public void setSalesMode(SalesMode salesMode) {
		this.salesMode = salesMode;
	}

	public PeriodSales getSinglePeriodSales() {
		return singlePeriodSales;
	}

	public void setSinglePeriodSales(PeriodSales singlePeriodSales) {
		this.singlePeriodSales = singlePeriodSales;
	}

	public PeriodSales getCompoundPeriodSales() {
		return compoundPeriodSales;
	}

	public void setCompoundPeriodSales(PeriodSales compoundPeriodSales) {
		this.compoundPeriodSales = compoundPeriodSales;
	}

	public String resultInfoBylotteryTypeAndPeriod() {
		try {
			if (this.id == null) {
				periodData = getPeriodDataEntityManagerImpl().getNewestResultPeriodData();
				period = periodEntityManager.getPeriod(periodData.getPeriodId());
			} else {
				period = periodEntityManager.getPeriod(this.getId());
			}
			if (period == null)
				throw new WebDataException("期号不存在.");
			periodData = getPeriodDataEntityManagerImpl().getPeriodData(period.getId());
			if (periodData == null)
				throw new WebDataException("期数据不存在.");
			periods = periodEntityManager.getDrawPeriodList(period.getLotteryType(), 10);
			switch (this.getLotteryType()) {
			case LCZC:
				matchArr = lczcMatchEntityManagerImpl.findMatchs(period.getId());
				break;
			case SFZC:
				matchArr = sfzcMatchEntityManagerImpl.findMatchs(period.getId());
				break;
			case SCZC:
				matchArr = sczcMatchEntityManagerImpl.findMatchs(period.getId());
				break;
			}
			Map<String, Object> data = new HashMap<String, Object>();
			String msg = createResultHtmlInfoByLottery(this.getLotteryType());
			data.put("msg", msg);
			Struts2Utils.renderJson(data);
			return null;
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return null;
	}

	private String createResultHtmlInfoByLottery(Lottery lottery) {
		StringBuffer msg = new StringBuffer();
		msg.append("<span id='periodDataDiv'>").append("<div style='padding:8px 0px;'>");
		if (lottery.equals(Lottery.SSQ)) {
			SsqPeriodData ssqPeriodData = (SsqPeriodData) periodData;
			msg.append("<ul class='kjball'>")
					.append("<li class='redballsingle'>" + ssqPeriodData.getRsultArr()[0] + "</li>")
					.append("<li class='redballsingle'>" + ssqPeriodData.getRsultArr()[1] + "</li>")
					.append("<li class='redballsingle'>" + ssqPeriodData.getRsultArr()[2] + "</li>")
					.append("<li class='redballsingle'>" + ssqPeriodData.getRsultArr()[3] + "</li>")
					.append("<li class='redballsingle'>" + ssqPeriodData.getRsultArr()[4] + "</li>")
					.append("<li class='redballsingle'>" + ssqPeriodData.getRsultArr()[5] + "</li>")
					.append("<li class='blueballsingle'>" + ssqPeriodData.getRsultArr()[6] + "</li>")
					.append("</ul>")
					.append("</div>")
					.append("<div class='kong5'></div>")
					.append("<div class=' cleanboth top5px lineh20'> 本期销量：" + (ssqPeriodData.getTotalSales()==null?"":ssqPeriodData.getTotalSales())
							+ " 元<br />")
					.append("<span class='rebchar'>奖池累积： " + (ssqPeriodData.getPrizePool()==null?"":ssqPeriodData.getPrizePool()) + "元</span><br />")
					.append("一等奖：" + (ssqPeriodData.getPrize().getFirstPrize()==null?"":ssqPeriodData.getPrize().getFirstPrize()) + "元/注<br />")
					.append("二等奖：" + (ssqPeriodData.getPrize().getSecondPrize()==null?"":ssqPeriodData.getPrize().getSecondPrize()) + "元/注<br />").append("</div>")
					.append("</span>");
		}
		if (lottery.equals(Lottery.DLT)) {
			DltPeriodData dltPeriodData = (DltPeriodData) periodData;
			msg.append("<ul class='kjball'>")
					.append("<li class='redballsingle'>" + dltPeriodData.getRsultArr()[0] + "</li>")
					.append("<li class='redballsingle'>" + dltPeriodData.getRsultArr()[1] + "</li>")
					.append("<li class='redballsingle'>" + dltPeriodData.getRsultArr()[2] + "</li>")
					.append("<li class='redballsingle'>" + dltPeriodData.getRsultArr()[3] + "</li>")
					.append("<li class='redballsingle'>" + dltPeriodData.getRsultArr()[4] + "</li>")
					.append("<li class='blueballsingle'>" + dltPeriodData.getRsultArr()[5] + "</li>")
					.append("<li class='blueballsingle'>" + dltPeriodData.getRsultArr()[6] + "</li>")
					.append("</ul>")
					.append("</div>")
					.append("<div class='kong5'></div>")
					.append("<div class=' cleanboth top5px lineh20'> 本期销量：" + (dltPeriodData.getTotalSales()==null?0:dltPeriodData.getTotalSales())
							+ " 元<br />")
					.append("<span class='rebchar'>奖池累积： " + (dltPeriodData.getPrizePool()==null?"":dltPeriodData.getPrizePool()) + "元</span><br />")
					.append("一等奖：" + (dltPeriodData.getPrize().getFirstPrize()==null?"":dltPeriodData.getPrize().getFirstPrize()) + "元/注<br />")
					.append("二等奖：" + (dltPeriodData.getPrize().getSecondPrize()==null?"":dltPeriodData.getPrize().getSecondPrize()) + "元/注<br />").append("</div>")
					.append("</span>");
		}
		if (lottery.equals(Lottery.PL)) {
			PlPeriodData plPeriodData = (PlPeriodData) periodData;
			msg.append("排列三：");
			msg.append("<ul class='kjball'>")
					.append("<li class='redballsingle'>" + plPeriodData.getRsultArr()[0] + "</li>")
					.append("<li class='redballsingle'>" + plPeriodData.getRsultArr()[1] + "</li>")
					.append("<li class='redballsingle'>" + plPeriodData.getRsultArr()[2] + "</li>")
					.append("<div class='kong5'></div>");
			msg.append("排列五：");
			msg.append("<ul class='kjball'>")
					.append("<li class='redballsingle'>" + plPeriodData.getRsultArr()[0] + "</li>")
					.append("<li class='redballsingle'>" + plPeriodData.getRsultArr()[1] + "</li>")
					.append("<li class='redballsingle'>" + plPeriodData.getRsultArr()[2] + "</li>")
					.append("<li class='redballsingle'>" + plPeriodData.getRsultArr()[3] + "</li>")
					.append("<li class='redballsingle'>" + plPeriodData.getRsultArr()[4] + "</li>").append("</ul>")
					.append("</div>").append("<div class='kong5'></div>");
			if (null != plPeriodData.getTotalSales() && !"".equals(plPeriodData.getTotalSales())&&null != plPeriodData.getP5TotalSales() && !"".equals(plPeriodData.getP5TotalSales())) {
				msg.append("<div class=' cleanboth top5px lineh20'> 本期排列三销量：" + plPeriodData.getTotalSales() + " 元<br />");
				msg.append("<div class=' cleanboth top5px lineh20'> 本期排列五销量：" + plPeriodData.getP5TotalSales() + " 元<br />");
			} else {
				msg.append("<div class=' cleanboth top5px lineh20'> 本期销量： 元<br />");
			}
		}
		if (lottery.equals(Lottery.WELFARE3D)) {
			Welfare3dPeriodData welfare3dPeriodData = (Welfare3dPeriodData) periodData;
			msg.append("<ul class='kjball'>")
					.append("<li class='redballsingle'>" + welfare3dPeriodData.getRsultArr()[0] + "</li>")
					.append("<li class='redballsingle'>" + welfare3dPeriodData.getRsultArr()[1] + "</li>")
					.append("<li class='redballsingle'>" + welfare3dPeriodData.getRsultArr()[2] + "</li>")
					.append("</ul>").append("</div>").append("<div class='kong5'></div>");
			if (null != welfare3dPeriodData.getTotalSales() && !"".equals(welfare3dPeriodData.getTotalSales())) {
				msg.append("<div class=' cleanboth top5px lineh20'> 本期销量：" + welfare3dPeriodData.getTotalSales()
						+ " 元<br />");
			} else {
				msg.append("<div class=' cleanboth top5px lineh20'> 本期销量： 元<br />");
			}
		}

		if (lottery.equals(Lottery.SFZC)) {
			SfzcPeriodData sfzcPeriodData = (SfzcPeriodData) periodData;
			msg.append("<span class='rebchar font14char boldchar'>" + sfzcPeriodData.getRsultSpiltString() + "</span>")
					.append("</div>").append("<div class='kong5'></div>");
			if (null != sfzcPeriodData.getTotalSales() && !"".equals(sfzcPeriodData.getTotalSales())) {
				msg.append("<div class=' cleanboth top5px lineh20'> 本期销量：" + sfzcPeriodData.getTotalSales()
						+ " 元<br />");
			} else {
				msg.append("<div class=' cleanboth top5px lineh20'> 本期销量： 元<br />");
			}
			msg.append("</div>").append("</span>");
		}

		return msg.toString();
	}
	public List<NewsInfoData> getForecastAndSkills() {
		return forecastAndSkills;
	}
	public void setForecastAndSkills(List<NewsInfoData> forecastAndSkills) {
		this.forecastAndSkills = forecastAndSkills;
	}
	public List<NewsInfoData> getResultAndInfo() {
		return resultAndInfo;
	}
	public void setResultAndInfo(List<NewsInfoData> resultAndInfo) {
		this.resultAndInfo = resultAndInfo;
	}
	
}
