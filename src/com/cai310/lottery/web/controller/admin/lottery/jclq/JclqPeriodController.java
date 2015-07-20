package com.cai310.lottery.web.controller.admin.lottery.jclq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.jclq.JclqMatchDTO;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.fetch.jclq.JclqContextHolder;
import com.cai310.lottery.fetch.jclq.JclqResultFetchParser;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.PeriodController;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Namespace("/admin/lottery/" + JclqConstant.KEY)
@Action("period")
@Results({ @Result(name = "edit", location = "/WEB-INF/content/admin/lottery/jclq/period-edit.ftl") })
public class JclqPeriodController extends PeriodController {
	private static final long serialVersionUID = -7375629285498640111L;

	private List<JclqMatch> matchs;

	private Integer matchDate;

	@Autowired
	private JclqMatchEntityManager matchEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.JCLQ;
	}

	public String matchManage() {
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotBlank(id) && id.matches(RegexUtils.Number)) {
			this.period = this.periodManager.getPeriod(Long.valueOf(id));
		}
		DateTime dateTime = new DateTime();
		Integer firstMatchDate = this.matchEntityManager.getFirstMatchDate();
		int totalCount;
		if (firstMatchDate != null) {
			int year = firstMatchDate / 10000;
			int temp = firstMatchDate % 10000;
			int month = temp / 100;
			int day = temp % 100;
			DateTime firstMatchDateTime = new DateTime(year, month, day, 0, 0, 0, 0);
			totalCount = Days.daysBetween(firstMatchDateTime, dateTime).getDays() + 6;
		} else {
			totalCount = 6;
		}
		this.pagination.setTotalCount(totalCount);
		this.pagination.setPageSize(7);
		List<DateTime> list = Lists.newArrayList();
		dateTime = dateTime.plusDays(6);
		for (int i = 0; i < this.pagination.getPageSize(); i++) {
			list.add(dateTime.plusDays(-this.pagination.getFirst() - i - 1));
		}
		this.pagination.setResult(list);
		return "match-manage";
	}

	public String matchEdit() {
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotBlank(id) && id.matches(RegexUtils.Number)) {
			this.period = periodManager.getPeriod(Long.valueOf(id));
			this.matchs = this.matchEntityManager.findMatchs(this.matchDate);
		}
		return "match-edit";
	}

	public String importMatch() {
		try {
			String id = Struts2Utils.getParameter("id");
			if (StringUtils.isNotBlank(id) && id.matches(RegexUtils.Number)) {
				this.period = periodManager.getPeriod(Long.valueOf(id));
			}
			List<JclqMatch> matchList = JclqContextHolder.getMatchList();
			if (matchList != null && !matchList.isEmpty()) {
				Map<String, JclqMatch> map = Maps.newTreeMap();
				if (this.matchs != null) {
					for (JclqMatch match : this.matchs) {
						map.put(match.getMatchKey(), match);
					}
				}
				for (JclqMatch matchDTO : matchList) {
					if (!this.matchDate.equals(matchDTO.getMatchDate()))
						continue;
					String matchKey = JclqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
					JclqMatch match = map.get(matchKey);
					if (match == null) {
						match = new JclqMatch();
					}
					match.setMatchKey(matchKey);
					match.setMatchDate(matchDTO.getMatchDate());
					match.setLineId(matchDTO.getLineId());
					match.setGameName(matchDTO.getGameName());
					match.setGameColor(matchDTO.getGameColor());
					match.setGuestTeamName(matchDTO.getGuestTeamName());
					match.setHomeTeamName(matchDTO.getHomeTeamName());
					match.setMatchTime(matchDTO.getMatchTime());
					match.setSingleHandicap(matchDTO.getSingleHandicap());
					match.setSingleTotalScore(matchDTO.getSingleTotalScore());
					match.setOpenFlag(matchDTO.getOpenFlag());
					map.put(matchKey, match);
				}
				this.matchs = Lists.newArrayList(map.values());
				addActionMessage("抓取完成.");
			} else {
				addActionMessage("抓取不到对阵，请检查目标网站是否有对阵.");
			}
		} catch (Exception e) {
			this.logger.warn(e.getMessage(), e);
			addActionError(e.getMessage());
		}
		return "match-edit";
	}

	public void prepareSaveMatch() throws Exception {
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotBlank(id) && id.matches(RegexUtils.Number)) {
			this.period = this.periodManager.getPeriod(Long.valueOf(id));
			String matchDate = Struts2Utils.getParameter("matchDate");
			if (this.period != null && StringUtils.isNotBlank(matchDate) && matchDate.matches(RegexUtils.Number)) {
				List<JclqMatch> matchList = this.matchEntityManager.findMatchs(Integer.valueOf(matchDate));
				if (matchList != null && !matchList.isEmpty())
					this.matchs = matchList;
			}
		}
	}

	public String saveMatch() {
		if (this.matchs != null) {
			for (JclqMatch match : this.matchs) {
				match.setPeriodId(this.period.getId());
				match.setPeriodNumber(this.period.getPeriodNumber());
				match.setMatchKey(JclqUtil.buildMatchKey(match.getMatchDate(), match.getLineId()));
				// 记录操作日志
				eventLogManager.saveSimpleEventLog(period,getLotteryType(), adminUser, EventLogType.UpdateMatch, "场次:【"+match.getMatchKey()+"】,场次实体："+match.toString());
			}
			this.matchEntityManager.saveMatchs(this.matchs);
			addActionMessage("保存成功");
		}
		return "match-edit";
	}
	public String odds() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
		try {
		   AdminUser adminUser = getAdminUser();
		   if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
		   }
		   if (id == null)throw new WebDataException("赛事ID找不到！");
		   JclqMatch jclqMatch= matchEntityManager.getMatch(id);
		   if (jclqMatch == null)throw new WebDataException("赛事找不到！");
		   jclqMatch = remoteDataQuery.getJclqSpByMatch(jclqMatch);
		   map.put("success", true);
		   map.put("msg", jclqMatch.getSfResultSp()+","+jclqMatch.getRfsfResultSp()+","+jclqMatch.getSfcResultSp()+","+jclqMatch.getDxfResultSp());
		} catch (WebDataException e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", e.getMessage());
		 }catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", "更新sp发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}
	public String odds_all() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
		try {
		   AdminUser adminUser = getAdminUser();
		   if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
		   }
		   if (matchDate == null)throw new WebDataException("matchDate找不到！");
		   JclqResultFetchParser jclqResultFetchParser =  new JclqResultFetchParser();
		   List<JclqMatchDTO> spList = jclqResultFetchParser.fetch(matchDate);
		   map.put("success", true);
		   map.put("spList", spList);
		} catch (WebDataException e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", e.getMessage());
		 }catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", "更新sp发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}
	@Override
	public void updateRemoteIssueData() {
		
	}

	public List<JclqMatch> getMatchs() {
		return matchs;
	}

	public void setMatchs(List<JclqMatch> matchs) {
		this.matchs = matchs;
	}

	public Integer getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Integer matchDate) {
		this.matchDate = matchDate;
	}

}
