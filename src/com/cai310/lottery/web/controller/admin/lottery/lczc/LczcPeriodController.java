package com.cai310.lottery.web.controller.admin.lottery.lczc;

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

import com.cai310.lottery.LczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.dto.lottery.zc.LczcResultSoccerDTO;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.LczcPasscount;
import com.cai310.lottery.entity.lottery.zc.LczcPeriodData;
import com.cai310.lottery.entity.lottery.zc.LczcScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.zc.LczcResultSoccerFetchParser;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.zc.ZcMatchEntityManager;
import com.cai310.lottery.service.lottery.zc.impl.LczcSchemeEntityManagerImpl;
import com.cai310.lottery.support.zc.ZcCompoundPassWork;
import com.cai310.lottery.web.controller.admin.lottery.zc.ZcPeriodController;
import com.cai310.utils.Struts2Utils;

@Namespace("/admin/lottery/" + LczcConstant.KEY)
@Action("period")
@Results( { @Result(name = "edit", location = "/WEB-INF/content/admin/lottery/lczc/period-edit.ftl") })
public class LczcPeriodController extends ZcPeriodController<LczcMatch, LczcPeriodData> {
	private static final long serialVersionUID = -4693805637373277840L;

	private static final int MATCHSCOUNT = 6;// 彩种场次

	@Resource
	private ZcMatchEntityManager<LczcMatch> lczcMatchEntityManagerImpl;

	@Resource
	private PeriodDataEntityManager<LczcPeriodData> lczcPeriodDataEntityManagerImpl;	
	
	@Resource
	private LczcSchemeEntityManagerImpl lczcSchemeEntityManagerImpl;

	@Override
	public Lottery getLotteryType() {
		return Lottery.LCZC;
	}

	@Override
	protected ZcMatchEntityManager<LczcMatch> getZcMatchEntityManager() {
		return this.lczcMatchEntityManagerImpl;
	}
	

	/**
	 * @return {@link #matchs}
	 */
	public LczcMatch[] getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the {@link #matchs} to set
	 */
	public void setMatchs(LczcMatch[] matchs) {
		this.matchs = matchs;
	}

	@Override
	protected void initZcMatchs() {
		matchs = new LczcMatch[MATCHSCOUNT];
		for (int i = 0; i < MATCHSCOUNT; i++) {
			matchs[i] = new LczcMatch();
		}
	}

	@Override
	protected PeriodDataEntityManager<LczcPeriodData> getPeriodDataEntityManager() {
		return this.lczcPeriodDataEntityManagerImpl;
	}

	@Override
	protected void instanceZcPeriodData() {
		this.periodData = new LczcPeriodData();
	}

	@Override
	protected String getMatchResult() {
		StringBuffer result = new StringBuffer();
		Integer halfHomeScore, homeScore, halfGuestScore, guestScore;
		for (LczcMatch match : this.getMatchs()) {
			halfHomeScore = match.getHalfHomeScore();
			homeScore = match.getHomeScore();
			halfGuestScore = match.getHalfGuestScore();
			guestScore = match.getGuestScore();
			if (match.isCancel()) {
				result.append("**");
			} else {
				this.converResult(result, halfHomeScore, halfGuestScore);
				this.converResult(result, homeScore, guestScore);
			}
		}
		return result.toString();
	}

	private void converResult(StringBuffer result, Integer homeScore, Integer guestScore) {
		if (homeScore == null || guestScore == null) {
			result.append('X');
		} else if (homeScore > guestScore) {
			result.append('3');
		} else if (homeScore.equals(guestScore)) {
			result.append('1');
		} else if (homeScore < guestScore) {
			result.append('0');
		} else {
			result.append('X');
		}
	}
	protected void passcount(long periodId){
		List<Long> schemeIds= this.lczcSchemeEntityManagerImpl.findSchemeIdOfCanPasscount(periodId);
		String result = this.getMatchResult();		
		ZcCompoundPassWork passWork = null;
		BufferedReader reader = null;
		for(Long schemeId:schemeIds){
			int lost0 = 0;
			int lost1 = 0;
			int lost2 = 0;
			int lost3 = 0;
			int passcount = 0;
			LczcScheme scheme = this.lczcSchemeEntityManagerImpl.getScheme(schemeId);
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
							passcountTemp=12-lost_count;
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
			LczcPasscount entity = this.lczcSchemeEntityManagerImpl.getLczcSchemeWonInfo(scheme.getId());
			if (entity == null) {
				entity = new LczcPasscount();
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
			this.lczcSchemeEntityManagerImpl.saveLczcSchemeWonInfo(entity);
		}
	}
	/**
	 * 抓取赛果
	 * @return
	 */
	public String fetchResult(){
		Map<String, Object> result = new HashMap<String, Object>();
		LczcResultSoccerFetchParser lczcResultSoccerFetchParser=new LczcResultSoccerFetchParser();
		String periodNumber_="20"+periodNumber;
		try {
			LczcResultSoccerDTO lczcResultSoccerDTO=lczcResultSoccerFetchParser.fetch(periodNumber_);
			result.put("lczcDto", lczcResultSoccerDTO);
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
