package com.cai310.lottery.web.controller.admin.lottery.sfzc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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

import com.cai310.lottery.SfzcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.dto.lottery.zc.SfzcResultSoccerDTO;
import com.cai310.lottery.entity.lottery.zc.SfzcMatch;
import com.cai310.lottery.entity.lottery.zc.SfzcPasscount;
import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.zc.SfzcResultSoccerFetchParser;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.zc.ZcMatchEntityManager;
import com.cai310.lottery.service.lottery.zc.impl.SfzcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcSchemeEntityManagerImpl;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.SchemeConverWork;
import com.cai310.lottery.support.zc.SfzcCompoundItem;
import com.cai310.lottery.support.zc.ZcCompoundItem;
import com.cai310.lottery.support.zc.ZcCompoundPassWork;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.web.controller.admin.lottery.zc.ZcPeriodController;
import com.cai310.utils.Struts2Utils;

@Namespace("/admin/lottery/" + SfzcConstant.KEY)
@Action("period")
@Results( { @Result(name = "edit", location = "/WEB-INF/content/admin/lottery/sfzc/period-edit.ftl") })
public class SfzcPeriodController extends ZcPeriodController<SfzcMatch, SfzcPeriodData> {
	private static final long serialVersionUID = -4693805637373277840L;

	private static final int MATCHSCOUNT = 14;// 彩种场次

	@Autowired
	private SfzcMatchEntityManagerImpl matchEntityManager;

	@Resource
	private PeriodDataEntityManager<SfzcPeriodData> sfzcPeriodDataEntityManagerImpl;
	
	@Resource
	private SfzcSchemeEntityManagerImpl sfzcSchemeEntityManagerImpl;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SFZC;
	}

	@Override
	protected ZcMatchEntityManager<SfzcMatch> getZcMatchEntityManager() {
		return this.matchEntityManager;
	}
	

	@Override
	protected void initZcMatchs() {
		matchs = new SfzcMatch[MATCHSCOUNT];
		for (int i = 0; i < MATCHSCOUNT; i++) {
			matchs[i] = new SfzcMatch();
		}
	}

	public SfzcMatch[] getMatchs() {
		return matchs;
	}

	public void setMatchs(SfzcMatch[] matchs) {
		this.matchs = matchs;
	}

	@Override
	protected PeriodDataEntityManager<SfzcPeriodData> getPeriodDataEntityManager() {
		return this.sfzcPeriodDataEntityManagerImpl;
	}

	@Override
	protected void instanceZcPeriodData() {
		this.periodData = new SfzcPeriodData();
	}

	@Override
	protected String getMatchResult() {
		StringBuffer result = new StringBuffer();
		Integer homeScore, guestScore;
		for (SfzcMatch match : this.getMatchs()) {
			homeScore = match.getHomeScore();
			guestScore = match.getGuestScore();
			if (match.isCancel()) {
				result.append('*');
			} else if (homeScore == null || guestScore == null) {
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
		return result.toString();
	}
	protected void passcount(long periodId){
		List<Long> schemeIds= this.sfzcSchemeEntityManagerImpl.findSchemeIdOfCanPasscount(periodId);
		String result = this.getMatchResult();
		ZcCompoundPassWork passWork = null;
		BufferedReader reader = null;
		for(Long schemeId:schemeIds){
			int lost0 = 0;
			int lost1 = 0;
			int lost2 = 0;
			int lost3 = 0;
			int passcount = 0;
			SfzcScheme scheme = this.sfzcSchemeEntityManagerImpl.getScheme(schemeId);
			if (scheme.getMode() == SalesMode.COMPOUND) {
				try {
					if (scheme.getPlayType() == PlayType.SFZC9) {// 九场
						SfzcCompoundItem[] betItems = scheme.getCompoundContent();
						List<ZcCompoundItem> danList = new ArrayList<ZcCompoundItem>();
						List<ZcCompoundItem> unDanList = new ArrayList<ZcCompoundItem>();
						for (SfzcCompoundItem item : betItems) {
							if (item.selectedCount() > 0) {
								if (item.isShedan()) {
									danList.add(item);
								} else {
									unDanList.add(item);
								}
							}
						}
						SchemeConverWork<ZcCompoundItem> work = new SchemeConverWork<ZcCompoundItem>(
								ZcUtils.SFZC9_MATCH_MINSELECT_COUNT, danList, unDanList, scheme.getDanMinHit(), -1);
						passWork = new ZcCompoundPassWork(work.getResultList(), scheme.getMultiple(), result);
					} else {
						passWork = new ZcCompoundPassWork(scheme.getCompoundContent(), scheme.getMultiple(), result);
					}

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
							if (scheme.getPlayType() == PlayType.SFZC9) {// 九场
								passcountTemp=9-lost_count;
							}else {
								passcountTemp=14-lost_count;
							}
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
			SfzcPasscount entity = this.sfzcSchemeEntityManagerImpl.getSfzcSchemeWonInfo(scheme.getId());
			if (entity == null) {
				entity = new SfzcPasscount();
			}
			entity.setPlayType(scheme.getPlayType());
			entity.setPeriodId(scheme.getPeriodId());
			entity.setPeriodNumber(scheme.getPeriodNumber());
			entity.setShareType(scheme.getShareType());
			entity.setSchemeId(scheme.getId());
			entity.setPlayType(scheme.getPlayType());
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
			this.sfzcSchemeEntityManagerImpl.saveSfzcSchemeWonInfo(entity);
		}
	}
	/**
	 * 抓取赛果
	 * @return
	 */
	public String fetchResult(){
		Map<String, Object> result = new HashMap<String, Object>();
		SfzcResultSoccerFetchParser sfzcResultSoccerFetchParser=new SfzcResultSoccerFetchParser();
		String periodNumber_="20"+periodNumber;
		try {
			SfzcResultSoccerDTO sfzcResultSoccerDTO=sfzcResultSoccerFetchParser.fetch(periodNumber_);
			result.put("sfzcDto", sfzcResultSoccerDTO);
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
