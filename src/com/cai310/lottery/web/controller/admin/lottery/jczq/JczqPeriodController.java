package com.cai310.lottery.web.controller.admin.lottery.jczq;

import java.text.NumberFormat;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.jczq.JczqMatchDTO;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.fetch.jczq.JczqContextHolder;
import com.cai310.lottery.fetch.jczq.JczqKaijiang;
import com.cai310.lottery.fetch.jczq.JczqKaijiangFetchParser;
import com.cai310.lottery.fetch.jczq.JczqResultFetchParser;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.utils.CommonUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.PeriodController;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
 

@Namespace("/admin/lottery/" + JczqConstant.KEY)
@Action("period")
@Results({ @Result(name = "edit", location = "/WEB-INF/content/admin/lottery/jczq/period-edit.ftl") })
public class JczqPeriodController extends PeriodController {
	private static final long serialVersionUID = -7375629285498640111L;
	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private List<JczqMatch> matchs;
 
	private Integer matchDate;
 
	@Autowired
	private JczqMatchEntityManager matchEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.JCZQ;
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
			totalCount = Days.daysBetween(firstMatchDateTime, dateTime).getDays() + 10;
		} else {
			totalCount = 10;
		}
		this.pagination.setTotalCount(totalCount);
		this.pagination.setPageSize(7);
		List<DateTime> list = Lists.newArrayList();
		dateTime = dateTime.plusDays(30);
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
			List<JczqMatch> matchList = JczqContextHolder.getMatchList();
			if (matchList != null && !matchList.isEmpty()) {
				Map<String, JczqMatch> map = Maps.newTreeMap();
				if (this.matchs != null) {
					for (JczqMatch match : this.matchs) {
						map.put(match.getMatchKey(), match);
					}
				}
				for (JczqMatch matchDTO : matchList) {
					if (!this.matchDate.equals(matchDTO.getMatchDate()))
						continue;
					String matchKey = JczqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
					JczqMatch match = map.get(matchKey);
					if (match == null) {
						match = new JczqMatch();
					}
					match.setMatchKey(matchKey);
					match.setMatchDate(matchDTO.getMatchDate());
					match.setLineId(matchDTO.getLineId());
					match.setGameName(matchDTO.getGameName());
					match.setGameColor(matchDTO.getGameColor());
					match.setGuestTeamName(matchDTO.getGuestTeamName());
					match.setHomeTeamName(matchDTO.getHomeTeamName());
					match.setMatchTime(matchDTO.getMatchTime());
					match.setHandicap(matchDTO.getHandicap());
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
				List<JczqMatch> matchList = this.matchEntityManager.findMatchs(Integer.valueOf(matchDate));
				if (matchList != null && !matchList.isEmpty())
					this.matchs = matchList;
			}
		}
	}

	public String saveMatch() {
		if (this.matchs != null) {
			for (JczqMatch match : this.matchs) {
				match.setPeriodId(this.period.getId());
				match.setPeriodNumber(this.period.getPeriodNumber());
				match.setMatchKey(JczqUtil.buildMatchKey(match.getMatchDate(), match.getLineId()));
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
		   JczqMatch jczqMatch= matchEntityManager.getMatch(id);
		   if (jczqMatch == null)throw new WebDataException("赛事找不到！");
		   jczqMatch = remoteDataQuery.getJczqSpByMatch(jczqMatch);
		   map.put("success", true);
		   map.put("msg", jczqMatch.getSpfResultSp()+","+jczqMatch.getJqsResultSp()+","+jczqMatch.getBfResultSp()+","+jczqMatch.getBqqResultSp());
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
		final double rangeOfSp = 0.01d;//比对sp的误差范围
    	Map<String, Object> map = new HashMap<String, Object>();
		try {
		   AdminUser adminUser = getAdminUser();
		   if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
		   }
		   if (matchDate == null)throw new WebDataException("matchDate找不到！");
		   this.matchs = this.matchEntityManager.findMatchs(this.matchDate);
		   List<String> matchKeysUnEnd = Lists.newArrayList();
		   for(JczqMatch match : matchs){
			   if(!match.isEnded()){
				   matchKeysUnEnd.add(match.getMatchKey().replace("-", ""));
			   }
		   }
		   
		   JczqResultFetchParser jczqResultFetchParser =  new JczqResultFetchParser();
		   JczqKaijiangFetchParser jczqKaijiangFetchParser = new JczqKaijiangFetchParser();
		   Map KaijiangMap = jczqKaijiangFetchParser.fetch(matchDate+"");
		   //System.out.println("matchDate:"+matchDate);
		   List<JczqMatchDTO> spList = jczqResultFetchParser.fetch(matchDate);
		   NumberFormat nf = NumberFormat.getInstance();
		   nf.setGroupingUsed(false);
		   nf.setMaximumIntegerDigits(3);
		   nf.setMinimumIntegerDigits(3);
		   
		   List<JczqMatchDTO> resultMatchDtoList = Lists.newArrayList();
		   for(JczqMatchDTO dto:spList){
			   String key = dto.getMatchDate()+nf.format(dto.getLineId());
			   if(matchKeysUnEnd.contains(key)){//未结束的赛程进行比对
				   if(KaijiangMap.containsKey(key)){				 
					   JczqKaijiang k = (JczqKaijiang) KaijiangMap.get(key);		
					   if(dto.getSpfResultSp()!=null && Math.abs(CommonUtil.floorPrize(Double.valueOf(dto.getSpfResultSp()))-CommonUtil.floorPrize(k.getSpf()))>rangeOfSp){dto.setSpfFlag("有误"+k.getSpf());}
					   if(dto.getRfspfResultSp()!=null && Math.abs(CommonUtil.floorPrize(Double.valueOf(dto.getRfspfResultSp()))-CommonUtil.floorPrize(k.getRqspf()))>rangeOfSp){dto.setRfspfFlag("有误"+k.getRqspf());}
					   if(dto.getJqsResultSp()!=null && Math.abs(CommonUtil.floorPrize(Double.valueOf(dto.getJqsResultSp()))-CommonUtil.floorPrize(k.getZjq()))>rangeOfSp){dto.setJqsFlag("有误"+k.getZjq());}
				       if(dto.getBqqResultSp()!=null && Math.abs(CommonUtil.floorPrize(Double.valueOf(dto.getBqqResultSp()))-CommonUtil.floorPrize(k.getBqc()))>rangeOfSp){dto.setBqqFlag("有误"+k.getBqc());}
				       dto.setBiduiFlag("该行已比对");
				       logger.debug("[胜平负:]"+dto.getSpfResultSp()+" "+k.getSpf()+" "+"[让分胜平负]"+dto.getRfspfResultSp()+" "+k.getRqspf()+"[总进球]"+dto.getJqsResultSp()+" "+k.getZjq());
				   }else{
					   dto.setBiduiFlag("该行全部未验证");					 
				   }
				   resultMatchDtoList.add(dto);
			   }
		   }
		   map.put("success", true);
		   map.put("spList", resultMatchDtoList);
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

	public List<JczqMatch> getMatchs() {
		return matchs;
	}

	public void setMatchs(List<JczqMatch> matchs) {
		this.matchs = matchs;
	}

	public Integer getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Integer matchDate) {
		this.matchDate = matchDate;
	}
	public static void main(String[] args) {
		String  s= "2.2400";
		
		Double d  =Double.valueOf(null);
		System.out.println(d);
	}
	 
}
