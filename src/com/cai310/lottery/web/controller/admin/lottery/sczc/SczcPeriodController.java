package com.cai310.lottery.web.controller.admin.lottery.sczc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.dto.lottery.zc.SczcResultSoccerDTO;
import com.cai310.lottery.entity.lottery.zc.SczcMatch;
import com.cai310.lottery.entity.lottery.zc.SczcPasscount;
import com.cai310.lottery.entity.lottery.zc.SczcPeriodData;
import com.cai310.lottery.entity.lottery.zc.SczcScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.zc.SczcResultSoccerFetchParser;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.zc.ZcMatchEntityManager;
import com.cai310.lottery.service.lottery.zc.impl.SczcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SczcSchemeEntityManagerImpl;
import com.cai310.lottery.support.zc.ZcCompoundPassWork;
import com.cai310.lottery.web.controller.admin.lottery.zc.ZcPeriodController;
import com.cai310.utils.Struts2Utils;

@Namespace("/admin/lottery/" + SczcConstant.KEY)
@Action("period")
@Results( { @Result(name = "edit", location = "/WEB-INF/content/admin/lottery/sczc/period-edit.ftl") })
public class SczcPeriodController extends ZcPeriodController<SczcMatch, SczcPeriodData> {
	private static final long serialVersionUID = -4693805637373277840L;

	private static final int MATCHSCOUNT = 4;// 彩种场次

	@Autowired
	private SczcMatchEntityManagerImpl matchEntityManager;

	@Resource
	private PeriodDataEntityManager<SczcPeriodData> sczcPeriodDataEntityManagerImpl;
	
	@Resource
	private SczcSchemeEntityManagerImpl sczcSchemeEntityManagerImpl;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SCZC;
	}

	@Override
	protected ZcMatchEntityManager<SczcMatch> getZcMatchEntityManager() {
		return this.matchEntityManager;
	}
	

	/**
	 * @return {@link #matchs}
	 */
	public SczcMatch[] getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the {@link #matchs} to set
	 */
	public void setMatchs(SczcMatch[] matchs) {
		this.matchs = matchs;
	}

	@Override
	protected void initZcMatchs() {
		matchs = new SczcMatch[MATCHSCOUNT];
		for (int i = 0; i < MATCHSCOUNT; i++) {
			matchs[i] = new SczcMatch();
		}
	}

	@Override
	protected PeriodDataEntityManager<SczcPeriodData> getPeriodDataEntityManager() {
		return this.sczcPeriodDataEntityManagerImpl;
	}

	@Override
	protected void instanceZcPeriodData() {
		this.periodData = new SczcPeriodData();
	}

	@Override
	protected String getMatchResult() {
		StringBuffer result = new StringBuffer();
		Integer homeScore, guestScore;
		for (SczcMatch match : this.getMatchs()) {
			homeScore = match.getHomeScore();
			guestScore = match.getGuestScore();
			if (match.isCancel()) {
				result.append('*');
			} else {
				this.converResult(result, homeScore);
				this.converResult(result, guestScore);
			}
		}
		return result.toString();
	}

	private void converResult(StringBuffer result, Integer score) {
		if (score == null || score < 0) {
			result.append('X');
		} else if (score >= 3) {
			result.append('3');
		} else {
			result.append(score);
		}
	}
	protected void passcount(long periodId){
		List<Long> schemeIds= this.sczcSchemeEntityManagerImpl.findSchemeIdOfCanPasscount(periodId);
		String result = this.getMatchResult();
		ZcCompoundPassWork passWork = null;
		BufferedReader reader = null;
		for(Long schemeId:schemeIds){
			int lost0 = 0;
			int lost1 = 0;
			int lost2 = 0;
			int lost3 = 0;
			int passcount = 0;
			SczcScheme scheme = this.sczcSchemeEntityManagerImpl.getScheme(schemeId);
			if (scheme.getMode() == SalesMode.COMPOUND) {
				try {
					passWork = new ZcCompoundPassWork(scheme.getCompoundContent(), scheme.getMultiple(), result);
					lost0=passWork.getLost0_count();
					lost1=passWork.getLost1_count();
					lost2=passWork.getLost2_count();
					lost3=passWork.getLost3_count();
					passcount=passWork.getPasscount();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}else{
				reader = new BufferedReader(new StringReader(scheme.getContent()));
				try {
					String line = reader.readLine();
					int passcountTemp=0;
					while (line != null) {
						if (StringUtils.isNotBlank(line)) {
							int lost_count = 0;
							char bet, rs_bet;// 投注内容及结果存放变量
							for (int i = 0; i < line.length(); i++) {
								bet = line.charAt(i);
								rs_bet = result.charAt(i);
								if (Character.isDigit(rs_bet)) {
									if (Character.isDigit(bet) && bet != rs_bet) {
										lost_count++;
									}
								} else {
									// finalResult = false;
								}
							}
							if (lost_count == 0) {
								lost0++;
							}
							if (lost_count == 1) {
								lost1++;
							}
							if (lost_count == 2) {
								lost2++;
							}
							if (lost_count == 3) {
								lost3++;
							}
							passcountTemp=8-lost_count;
							if(passcountTemp>passcount){
								passcount=passcountTemp;
							}
						}
						line = reader.readLine();
					}
					int multiple = scheme.getMultiple();
		            lost0=lost0 * multiple;
		            lost1=lost1 * multiple;
		            lost2=lost2 * multiple;
		            lost3=lost3 * multiple;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			// 保存过关统计信息
			SczcPasscount entity = this.sczcSchemeEntityManagerImpl.getSczcSchemeWonInfo(scheme.getId());
			if (entity == null) {
				entity = new SczcPasscount();
			}
			entity.setPeriodId(scheme.getPeriodId());
			entity.setPeriodNumber(scheme.getPeriodNumber());
			entity.setShareType(scheme.getShareType());
			entity.setSchemeId(scheme.getId());
			entity.setPeriodId(scheme.getPeriodId());
			entity.setSponsorId(scheme.getSponsorId());
			entity.setSponsorName(scheme.getSponsorName());
			entity.setUnits(scheme.getUnits());
			entity.setMultiple(scheme.getMultiple());
			entity.setSchemeCost(scheme.getSchemeCost());
			entity.setState(scheme.getState());
			entity.setMode(scheme.getMode());
			entity.setSchemePrize(scheme.getPrize());
			entity.setPasscount(passcount);
			entity.setLost0(lost0);
			entity.setLost1(lost1);
			entity.setLost2(lost2);
			entity.setLost3(lost3);
			this.sczcSchemeEntityManagerImpl.saveSczcSchemeWonInfo(entity);
		}
	}
	/**
	 * 抓取赛果
	 * @return
	 */
	public String fetchResult(){
		Map<String, Object> result = new HashMap<String, Object>();
		SczcResultSoccerFetchParser sczcResultSoccerFetchParser=new SczcResultSoccerFetchParser();
		String periodNumber_="20"+periodNumber;
		try {
			SczcResultSoccerDTO sczcResultSoccerDTO=sczcResultSoccerFetchParser.fetch(periodNumber_);
			result.put("sczcDto", sczcResultSoccerDTO);
			result.put("success", true);
			result.put("msg", "抓取成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			result.put("success", false);
			result.put("msg", "捉取发生异常！");
		}
		Struts2Utils.renderJson(result);
		return null;
	}
}
