package com.cai310.lottery.web.controller.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryUtil;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.entity.lottery.MyScheme;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserNewestLog;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.orm.Pagination;
import com.cai310.utils.DateUtil;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SchemeController extends UserBaseController {
	private static final long serialVersionUID = -8474952057278499584L;

	private static final Comparator<MyScheme> MY_SCHEME_COMPARATOR = new Comparator<MyScheme>() {

		@Override
		public int compare(MyScheme o1, MyScheme o2) {
			if (o1 == null || o1.getCreateTime() == null)
				return -1;
			if (o2 == null || o2.getCreateTime() == null)
				return 1;
			return o2.getCreateTime().compareTo(o1.getCreateTime());
		}
	};
	protected Integer playType;// 0排列三 1排列五 /////// 0 胜负彩 1任9
	protected Long periodId;
	protected Period period;
	protected int menuType;
	protected List<Period> periods;
	/**
	 * 销售期相关实体管理实例
	 * 
	 * @see com.cai310.lottery.service.lottery.PeriodEntityManager
	 */
	@Autowired
	protected PeriodEntityManager periodEntityManager;

	private Pagination pagination = new Pagination(20);

	@SuppressWarnings("rawtypes")
	private Map<Lottery, SchemeEntityManager> schemeEntityManagerMap = Maps.newHashMap();

	@Autowired
	@Qualifier("commonQueryCache")
	private Cache commonQueryCache;

	private Lottery lotteryType;
	private SchemeState state;
	private ShareType shareType;
	private int timeFrame;

	@SuppressWarnings("rawtypes")
	private SchemeEntityManager getSchemeEntityManager(Lottery lotteryType) {
		return schemeEntityManagerMap.get(lotteryType);
	}

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setSchemeEntityManagerList(List<SchemeEntityManager> schemeEntityManagerList) {
		for (SchemeEntityManager manager : schemeEntityManagerList) {
			schemeEntityManagerMap.put(manager.getLottery(), manager);
		}
	}

	public String index() {
		return list();
	}

	@SuppressWarnings("unchecked")
	public String list() {
		return "list";
	}

	public String my_list_table() {
		User loginUser = getLoginUser();
		if (loginUser == null)
			return GlobalResults.FWD_LOGIN;

		String key = getRequestKey() + loginUser.getId();
		
			SchemeQueryDTO dto = new SchemeQueryDTO();		
			String onlyDisplayWon = Struts2Utils.getParameter("onlyDisplayWon");
			String startTimeStr = Struts2Utils.getParameter("startTime");
			String endTimeStr = Struts2Utils.getParameter("endTime");
			
			if(StringUtils.isNotBlank(startTimeStr)) {
				Date startTime = DateUtil.strToDate(startTimeStr);
				dto.setStartTime(startTime);
			}
			if(StringUtils.isNotBlank(endTimeStr)) {
				Date endTime = DateUtil.strToDate(endTimeStr);
				dto.setEndTime(endTime);
			}
			dto.setShareType(shareType);
			dto.setState(state);
			
			if(onlyDisplayWon != null && onlyDisplayWon.trim().equals("1")) {
				dto.setWon(true);
			}
			
			if (lotteryType == null) {
				final int maxSize = 100;
				List<MyScheme> allList = Lists.newArrayList();
				pagination.setPageNo(1);
				pagination.setPageSize(maxSize);
				for (Lottery lottery : LotteryUtil.getWebLotteryList()) {
					pagination = getSchemeEntityManager(lottery).findMyScheme(loginUser.getId(), dto, pagination);
					if (pagination != null && pagination.getResult() != null && !pagination.getResult().isEmpty()) {
						allList.addAll(pagination.getResult());
						Collections.sort(allList, MY_SCHEME_COMPARATOR);
						if (allList.size() > maxSize) {
							allList = allList.subList(0, maxSize);
						}
					}
				}
				pagination.setResult(allList);
				pagination.setTotalCount(allList.size());
			} else {

				pagination = getSchemeEntityManager(lotteryType).findMyScheme(loginUser.getId(), dto, pagination);
			}

		return "my_list_table";
	}

	
	@SuppressWarnings("unchecked")
	public String findUserScheme() {
		try{
			User loginUser = getLoginUser();
			String userId = Struts2Utils.getParameter("id");
			if(StringUtil.isEmpty(userId)){
				throw new WebDataException("查看的用户标识id不能为空！");
			}
			User user = userManager.getUser(Long.valueOf(userId));
			if (user == null)
				throw new WebDataException("查看的用户不存在！");
			Struts2Utils.setAttribute("user", user);
			String key = getRequestKey() + user.getId();
			Element el = commonQueryCache.get(key);
			if (el == null) {
				SchemeQueryDTO dto = new SchemeQueryDTO();
				dto.setState(state);
				if(loginUser==null || loginUser.getId()!=user.getId()){
					dto.setWon(true);
				}

				if (lotteryType == null) {
					final int maxSize = 100;
					List<MyScheme> allList = Lists.newArrayList();
					pagination.setPageNo(1);
					pagination.setPageSize(maxSize);
					for (Lottery lottery : LotteryUtil.getWebLotteryList()) {
						pagination = getSchemeEntityManager(lottery).findMyScheme(user.getId(), dto, pagination);
						if (pagination != null && pagination.getResult() != null && !pagination.getResult().isEmpty()) {
							allList.addAll(pagination.getResult());
							Collections.sort(allList, MY_SCHEME_COMPARATOR);
							if (allList.size() > maxSize) {
								allList = allList.subList(0, maxSize);
							}
						}
					}
					pagination.setResult(allList);
					pagination.setTotalCount(allList.size());
				} else {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.HOUR_OF_DAY, 0);
					c.add(Calendar.MINUTE, 0);
					c.add(Calendar.SECOND, 0);
					switch (timeFrame) {
					case 1:
						c.add(Calendar.DAY_OF_MONTH, -15);// 15天前
						break;
					case 2:
						c.add(Calendar.MONTH, -1);// 1个月前
						break;
					case 3:
						c.add(Calendar.MONTH, -3);// 3个月前
						break;
					default:
						c.add(Calendar.DAY_OF_MONTH, -7);// 7天前
					}
					dto.setStartTime(c.getTime());

					pagination = getSchemeEntityManager(lotteryType).findMyScheme(user.getId(), dto, pagination);
				}
				el = new Element(key, this.pagination);
				commonQueryCache.put(el);
			} else {
				pagination = (Pagination) el.getValue();
			}
			this.findUserNewestLog(user,3);
			return "user-list";
		}catch(WebDataException ex){
			addActionError(ex.getMessage());
		}
		return GlobalResults.FWD_ERROR;
	}
	
	/**
	 * 取用户最新动态日志
	 * @param user
	 */
	@Autowired
	protected QueryService queryService;
	private void findUserNewestLog(User user,int size){
		DetachedCriteria criteria = DetachedCriteria.forClass(UserNewestLog.class);
		criteria.add(Restrictions.eq("userId", user.getId()));
		criteria.addOrder(Order.desc("id"));
		List<UserNewestLog> logs= queryService.findByDetachedCriteria(criteria,0,size);
		Struts2Utils.setAttribute("newestLogs", logs);
	}
	
	
	/**
	 * 准备销售期数据
	 * 
	 * @param onlyOnSale 是否只显示在售的
	 */
	protected void preparePeriodsOfList(int periodSize) {
		this.periods = new ArrayList<Period>();

		List<Period> currentPeriods = periodEntityManager.findCurrentPeriods(getLotteryType());
		if (currentPeriods != null)
			this.periods.addAll(currentPeriods);

		int oldSize = periodSize - this.periods.size();
		if (oldSize > 0) {
			List<Period> oldPeriods = periodEntityManager.findOldPeriods(getLotteryType(), oldSize, true);
			if (oldPeriods != null)
				this.periods.addAll(oldPeriods);
		}

		Collections.sort(this.periods, new Comparator<Period>() {
			public int compare(Period o1, Period o2) {
				return o2.getId().compareTo(o1.getId());
			}
		});
		Period firstOnSalePeriod = null;
		for (Period p : periods) {
			if (p.isCurrent()) {
				// /当前销售
				firstOnSalePeriod = p;
				p.setPeriodNumberDisplay(2);
			} else {
				p.setPeriodNumberDisplay(0);
			}
		}
		for (Period p : periods) {
			if (null != firstOnSalePeriod && p.getId().equals(firstOnSalePeriod.getId())) {
				p.setPeriodNumberDisplay(1);
			}
		}
		if (this.period == null && !this.periods.isEmpty())
			this.period = (firstOnSalePeriod != null) ? firstOnSalePeriod : this.periods.get(0);
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public SchemeState getState() {
		return state;
	}

	public void setState(SchemeState state) {
		this.state = state;
	}

	public int getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(int timeFrame) {
		this.timeFrame = timeFrame;
	}

	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Lottery[] getWebLotteryType() {
		return LotteryUtil.getWebLotteryList();
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public List<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}

	public int getMenuType() {
		return menuType;
	}

	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}

	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}
	
}
