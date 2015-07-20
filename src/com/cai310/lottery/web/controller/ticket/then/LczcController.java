package com.cai310.lottery.web.controller.ticket.then;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.persistence.Transient;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Constant;
import com.cai310.lottery.LczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.MatchNameUtil;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.dto.lottery.SchemeInfoDTO;
import com.cai310.lottery.dto.lottery.SchemeMatchDTO;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.lottery.ZcMatchDTO;
import com.cai310.lottery.dto.lottery.ZcMatchResultDTO;
import com.cai310.lottery.dto.lottery.zc.LczcSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.LczcScheme;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.LczcScheme;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.fetch.ValueItem;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.zc.ZcMatchEntityManager;
import com.cai310.lottery.service.lottery.zc.impl.LczcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcSchemeServiceImpl;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.SchemeConverWork;
import com.cai310.lottery.support.zc.LczcCompoundItem;
import com.cai310.lottery.support.zc.LczcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.SchemeBaseController;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ibm.icu.util.Calendar;

public class LczcController extends
                      TicketBaseController<LczcScheme, LczcSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;
	@Autowired
	@Qualifier("lczcMatchCache")
	private Cache matchCache;
	@Resource
	private ZcMatchEntityManager<LczcMatch> lczcMatchEntityManagerImpl;
	/**
		复式均以“,”相隔，例如：
		31,1,10,3,1,1,0,0,1,1,0,0
		单式每注用“+”相隔，例如：
		111111111111+333333333333

	 */
	private String schemeValue;
	@Autowired
	private LczcSchemeServiceImpl schemeService;
	@Autowired
	private LczcMatchEntityManagerImpl matchEntityManager;

	@Override
	protected SchemeService<LczcScheme, LczcSchemeDTO> getSchemeService() {
		return schemeService;
	}
	
	
	@Override
	protected LczcSchemeDTO buildSchemeDTO() throws WebDataException {
		LczcSchemeDTO dto = super.buildSchemeDTO();
		return dto;
	}

	// ----------------------------------------------------
	public LczcCompoundItem[] getCompoundContent() throws WebDataException {
		if (schemeValue == null) {
			throw new WebDataException("5-方案内容获取为空.");
		}
		String[] itemStrs = null;

		itemStrs = schemeValue.split(",");

		try {
			if (itemStrs.length != ZcUtils.getMatchCount(this.getLottery()) * 2) {
				throw new WebDataException("5-方案内容选项个数异常.");
			}
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
		LczcCompoundItem[] items = new LczcCompoundItem[itemStrs.length];
		for (int i = 0; i < itemStrs.length; i++) {
			items[i] = new LczcCompoundItem(itemStrs[i].trim());
			if(Byte.valueOf("0").equals(items[i].toByte())){
				throw new WebDataException("5-方案内容选项异常.");
			}
		}
		return items;
	}
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		StringBuffer content = new StringBuffer();
		LczcCompoundItem[] items = getCompoundContent();
		int units = ZcUtils.calcBetUnits(items);
		for (LczcCompoundItem item : items) {
			content.append(item.toByte()).append(ZcUtils.getContentSpiltCode());
		}
		return new ContentBean(units, content.substring(0, content.toString().length() - 1));
	}

	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		final String converStr ="-|,";//单式方案兼容"-"或","的投注项分隔符
		if (StringUtils.isBlank(schemeValue))
			throw new WebDataException("5-方案内容不能为空.");
		StringBuffer betContnet = new StringBuffer();
		String[] schemeValueArr = schemeValue.split("\\+");
		int units = 0;
		for (String line : schemeValueArr) {
			if (line.length() > 1024)
				throw new WebDataException("5-方案内容太大.");

			line = StringUtils.deleteWhitespace(line);
			line = line.replaceAll("\\"+converStr, "");
			
			try {
				if (line.length() != ZcUtils.getMatchCount(Lottery.LCZC) * 2)
					throw new WebDataException("5-投注内容格式不正确.");
			} catch (DataException e) {
				throw new WebDataException(e.getMessage());
			}
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (c == '0' || c == '1'  || c == '3')
					continue;
				throw new WebDataException("5-投注内容格式不正确.");
			}
			if (StringUtils.isNotBlank(line)) {
				units++;
				betContnet.append(line);
				betContnet.append("\r\n");
			}
		}
		return new ContentBean(units, betContnet.toString());
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.LCZC;
	}

	public String getSchemeValue() {
		return schemeValue;
	}


	public void setSchemeValue(String schemeValue) {
		this.schemeValue = schemeValue;
	}


	@Override
	protected void buildReqParamVisitor(
			com.cai310.lottery.web.controller.ticket.then.ReqParamVisitor reqParamVisitor) {
		if(null!=reqParamVisitor.getOrderId()){
			this.orderId = reqParamVisitor.getOrderId().trim();
		}
		if(null!=reqParamVisitor.getMode()){
			this.mode = Integer.valueOf(reqParamVisitor.getMode());
		}
		if(null!=reqParamVisitor.getPeriodNumber()){
			this.periodNumber =reqParamVisitor.getPeriodNumber();
		}
		if(null!=reqParamVisitor.getCost()){
			this.schemeCost = Integer.valueOf(reqParamVisitor.getCost());
		}
		if(null!=reqParamVisitor.getMultiple()){
			this.multiple = Integer.valueOf(reqParamVisitor.getMultiple());
		}
		if(null!=reqParamVisitor.getUnits()){
			this.units = Integer.valueOf(reqParamVisitor.getUnits());
		}
		if(null!=reqParamVisitor.getValue()){
			this.schemeValue = String.valueOf(reqParamVisitor.getValue());
		}
		if(null!=reqParamVisitor.getShareType()){
			this.shareType = Integer.valueOf(reqParamVisitor.getShareType());
		}
		if(null!=reqParamVisitor.getSubscriptionCost()){
			this.subscriptionCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getSubscriptionCost()));
		}
		if(null!=reqParamVisitor.getBaodiCost()){
			this.baodiCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getBaodiCost()));
		}
		if(null!=reqParamVisitor.getCommissionRate()){
			this.commissionRate = Float.valueOf(reqParamVisitor.getCommissionRate());
		}
		if(null!=reqParamVisitor.getMinSubscriptionCost()){
			this.minSubscriptionCost = BigDecimal.valueOf(Double.valueOf(reqParamVisitor.getMinSubscriptionCost()));
		}
		if(null!=reqParamVisitor.getSecretType()){
			this.secretType = Integer.valueOf(reqParamVisitor.getSecretType());
		}
	}


	@Override
	protected List buildMatchList() throws WebDataException {
		List list = Lists.newArrayList();
    	Period period = periodManager.loadPeriod(this.getLottery(), this.periodNumber);
    	if (period == null)
			throw new WebDataException("8-期数据不存在.");
    	
    	LczcMatch[] matchs = matchEntityManager.findMatchs(period.getId());
		if (matchs == null || matchs.length==0)
			throw new WebDataException("9-对阵不存在.");
		Map<Integer, LczcMatch> matchMap = new HashMap<Integer, LczcMatch>();
		for (LczcMatch m : matchs) {
			matchMap.put(m.getLineId(), m);
		}
		LczcMatch lczcMatch = null;
		ZcMatchDTO zcMatchDTO = null;
		for (Integer lineId : matchMap.keySet()) {
			zcMatchDTO = new ZcMatchDTO();
			lczcMatch = matchMap.get(lineId);
			zcMatchDTO.setCancel(lczcMatch.isCancel());
			zcMatchDTO.setEnded(!period.isOnSale());
			zcMatchDTO.setGameColor(lczcMatch.getGameColor());
			zcMatchDTO.setGameName(MatchNameUtil.getShotName(lczcMatch.getGameName()));
			zcMatchDTO.setGuestTeamName(lczcMatch.getGuestTeamName());
			zcMatchDTO.setHandicap(Float.valueOf(0));
			zcMatchDTO.setHomeTeamName(lczcMatch.getHomeTeamName());
			zcMatchDTO.setMatchKey(""+(lczcMatch.getLineId()));
			zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(lczcMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			zcMatchDTO.setOpen(!lczcMatch.isCancel());
			Map<String, RateItem> spMap = new HashMap<String, RateItem>();
			RateItem rateItem = new RateItem();
			rateItem.setChg(0);
			rateItem.setKey("asiaOdd");
			rateItem.setValue(lczcMatch.getAsiaOdd());
			spMap.put("asiaOdd", rateItem);
			rateItem = new RateItem();
			rateItem.setChg(0);
			rateItem.setKey("odds1");
			rateItem.setValue(lczcMatch.getOdds1());
			spMap.put("odds1", rateItem);
			rateItem = new RateItem();
			rateItem.setChg(0);
			rateItem.setKey("odds2");
			rateItem.setValue(lczcMatch.getOdds2());
			spMap.put("odds2", rateItem);
			rateItem = new RateItem();
			rateItem.setChg(0);
			rateItem.setKey("odds3");
			rateItem.setValue(lczcMatch.getOdds3());
			spMap.put("odds3", rateItem);
			if(null!=spMap){
				zcMatchDTO.setSp(spMap);
			}
			list.add(zcMatchDTO);
		}
		return list;
	}
	protected LczcMatch[] findMatchsOfCacheable(Long periodId) {
		Element el = matchCache.get(periodId);
		if (el == null) {
			LczcMatch[] matchs = lczcMatchEntityManagerImpl.findMatchs(periodId);
			if (matchs != null)
				matchCache.put(new Element(periodId, matchs));

			return matchs;
		} else {
			return (LczcMatch[]) el.getValue();
		}
	}

	@Override
	protected SchemeInfoDTO getSchemeMatchDTO(Scheme scheme)
			throws WebDataException {
SchemeInfoDTO schemeInfoDTO = new SchemeInfoDTO();
		
		LczcScheme lczcScheme = (LczcScheme) scheme;
		LczcMatch[] matchs = findMatchsOfCacheable(scheme.getPeriodId());
		LczcCompoundItem[] betItems;
		try {
			betItems = lczcScheme.getCompoundContent();
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
		for (LczcCompoundItem lczcCompoundItem : betItems) {
			
		}
		
		
		
		
		Map<Integer, LczcCompoundItem> lczcCompoundItemMap = Maps.newLinkedHashMap();
		int num = 0;
		for (LczcCompoundItem lczcCompoundItem : betItems) {
			lczcCompoundItemMap.put(num, lczcCompoundItem);
			num++;
		}
		List<SchemeMatchDTO> list = Lists.newArrayList();
		for (LczcMatch lczcMatch : matchs) {
			SchemeMatchDTO schemeMatchDTO = new SchemeMatchDTO();
			schemeMatchDTO.setMatchKey(""+lczcMatch.getLineId()+1);///id从零开始
			schemeMatchDTO.setCancel(lczcMatch.isCancel());
			schemeMatchDTO.setDan(Boolean.FALSE);
			LczcCompoundItem lczcCompoundItem = lczcCompoundItemMap.get(lczcMatch.getLineId());
			StringBuffer sb = new StringBuffer();
			if(lczcMatch.isCancel()){
				schemeMatchDTO.setEnded(true);
				String bet = lczcCompoundItem.toBetString();
				for (int i = 0; i < bet.length(); i++) {
					char c = bet.charAt(i);
					sb.append("<font color='#ff0000'>"+c+"</font>").append(",");
				}
			}else{
				String result = lczcMatch.getResult();
				schemeMatchDTO.setEnded(StringUtils.isNotBlank(result));
				String bet = lczcCompoundItem.toBetString();
				if(null!=lczcCompoundItem){
					if(StringUtils.isBlank(result)){
						for (int i = 0; i < bet.length(); i++) {
							char c = bet.charAt(i);
							sb.append(c).append(",");
						}
					}else{
						schemeMatchDTO.setResult(lczcMatch.getHalfResult()+"-"+lczcMatch.getResult());
						for (int i = 0; i < bet.length(); i++) {
							char c = bet.charAt(i);
							if (Character.isDigit(c)) {
								boolean pass;
								try {
									pass = lczcCompoundItem.checkPass(c);
								} catch (DataException e) {
									throw new WebDataException(e.getMessage());
								}
								if(pass){
									sb.append("<font color='#ff0000'>"+c+"</font>").append(",");
								}else{
									sb.append(c).append(",");
								}
							}else{
								sb.append(c).append(",");
							}
						}
					}
				}
			}
			schemeMatchDTO.setGameColor(lczcMatch.getGameColor());
			schemeMatchDTO.setGameName(MatchNameUtil.getShotName(lczcMatch.getGameName()));
			schemeMatchDTO.setGuestTeamName(lczcMatch.getGuestTeamName());
			schemeMatchDTO.setHomeTeamName(lczcMatch.getHomeTeamName());
			schemeMatchDTO.setHomeHalfScore(null);
			schemeMatchDTO.setHomeScore(lczcMatch.getHomeScore());
			schemeMatchDTO.setGuestHalfScore(null);
			schemeMatchDTO.setGuestScore(lczcMatch.getGuestScore());
			schemeMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(lczcMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			schemeMatchDTO.setBet(sb.toString());
			list.add(schemeMatchDTO);
		}
		schemeInfoDTO.setItems(list);
		return schemeInfoDTO;
	}


	@Override
	protected List buildMatchResultList() throws WebDataException {
		List list = Lists.newArrayList();
		Period period = periodManager.loadPeriod(this.getLottery(), this.periodNumber.trim());
		LczcMatch[] matchs = findMatchsOfCacheable(period.getId());
		ZcMatchResultDTO zcMatchDTO=null;
		for (LczcMatch lczcMatch : matchs) {
			zcMatchDTO = new ZcMatchResultDTO();
			zcMatchDTO.setCancel(lczcMatch.isCancel());
			if(lczcMatch.isCancel()){
				zcMatchDTO.setEnded(true);
				
			}else{
				String result = lczcMatch.getResult();
				zcMatchDTO.setEnded(StringUtils.isNotBlank(result));
			}
			zcMatchDTO.setResult(lczcMatch.getResult());
			zcMatchDTO.setGameColor(lczcMatch.getGameColor());
			zcMatchDTO.setGameName(MatchNameUtil.getShotName(lczcMatch.getGameName()));
			zcMatchDTO.setGuestTeamName(lczcMatch.getGuestTeamName());
			zcMatchDTO.setHomeTeamName(lczcMatch.getHomeTeamName());
			zcMatchDTO.setMatchKey(""+(lczcMatch.getLineId()+1));
			zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(lczcMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			zcMatchDTO.setHomeScore(lczcMatch.getHomeScore());
			zcMatchDTO.setGuestScore(lczcMatch.getGuestScore());
			list.add(zcMatchDTO);
		}
		return list;
	}


}
