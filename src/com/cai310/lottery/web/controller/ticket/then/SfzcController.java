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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.cai310.lottery.SfzcConstant;
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
import com.cai310.lottery.dto.lottery.zc.SfzcSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.zc.SfzcMatch;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.zc.ZcMatchEntityManager;
import com.cai310.lottery.service.lottery.zc.impl.SfzcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcSchemeServiceImpl;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.JcWonMatchItem;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.jclq.JclqCompoundContent;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.SchemeConverWork;
import com.cai310.lottery.support.zc.SfzcCompoundItem;
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
import com.google.common.collect.Sets;
import com.ibm.icu.util.Calendar;

public class SfzcController extends
                      TicketBaseController<SfzcScheme, SfzcSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;
	@Autowired
	@Qualifier("sfzcMatchCache")
	private Cache matchCache;
	@Resource
	private ZcMatchEntityManager<SfzcMatch> sfzcMatchEntityManagerImpl;
	/** 14场 0 */

	/** 9场  1*/
	private Integer danMinHit = -1;
	private PlayType playType;
	/**
	 *  占位符用*补足14位
		复式均以“,”相隔，例如：
		任选九场格式1
		{"danMinHit":0,"items":[{"dan":false,"matchKey":"1","value":3},{"dan":false,"matchKey":"2","value":2},...]}
    	任选九场格式2
		31,*,10,3,1,1,0,*,1,3,31,*,*,*
		
		单式每注用“+”相隔，例如：
		31*103**13*01*+31*103**13*01*+31*103**13*01*

	 */
	private String schemeValue;
	@Autowired
	private SfzcSchemeServiceImpl schemeService;
	@Autowired
	private SfzcMatchEntityManagerImpl matchEntityManager;
	@Override
	protected SchemeService<SfzcScheme, SfzcSchemeDTO> getSchemeService() {
		return schemeService;
	}
	
	
	@Override
	protected SfzcSchemeDTO buildSchemeDTO() throws WebDataException {
		if (playType == null)
			throw new WebDataException("5-玩法类型不能为空.");
		SfzcSchemeDTO dto = super.buildSchemeDTO();
		dto.setPlayType(playType);
		return dto;
	}
	protected Integer buildDanMinHit(Map<String, Object> map) throws WebDataException{
		 try{
			 Integer danMinHit =  -1;
			 if(null!=map.get("danMinHit")&&StringUtils.isNotBlank(String.valueOf(map.get("danMinHit")))){
				 danMinHit = Integer.valueOf(String.valueOf(map.get("danMinHit")));
			 }
	    	 return danMinHit;
		 }catch(Exception e){
				logger.warn("最小命中解析错误."+e.getMessage());
				throw new WebDataException("5-最小命中解析错误.");
		 }
   }
	@SuppressWarnings("unchecked")
	protected SfzcCompoundItem[] buildMatchItemList(Map<String, Object> value) throws WebDataException{
		try{
			String[] items = JsonUtil.getStringArray4Json(String.valueOf(value.get("items")));
			Map<Integer,SfzcCompoundItem> correctMap = Maps.newHashMap();
			Map<String, Object> map;
			List<Integer> matchKeyList=Lists.newArrayList();
			Integer matchKey = null;
			Boolean isDan = Boolean.FALSE;
			for (String itemStr : items) {
				map = JsonUtil.getMap4Json(itemStr);
				matchKey = Integer.valueOf(String.valueOf(map.get("matchKey")));
				matchKeyList.add(matchKey);
				isDan = Boolean.valueOf(String.valueOf(map.get("dan")));
				
				SfzcCompoundItem sfzcCompoundItem = new SfzcCompoundItem();
				sfzcCompoundItem.setLineId(matchKey);
				sfzcCompoundItem.setShedan(isDan);
				sfzcCompoundItem = getBetValue(sfzcCompoundItem,String.valueOf(map.get("value")));
				correctMap.put(matchKey,sfzcCompoundItem);
			}
			SfzcCompoundItem[] sfzcCompoundItems = new SfzcCompoundItem[14];
			SfzcCompoundItem sfzcCompoundItem = null;
			for (int i = 0; i < 14; i++) {
				sfzcCompoundItem = correctMap.get(i);
				if(null==sfzcCompoundItem){
					sfzcCompoundItem = new SfzcCompoundItem();
					sfzcCompoundItem.setLineId(i);
				}
				sfzcCompoundItems[i]=sfzcCompoundItem;
			}
			if (sfzcCompoundItems.length != ZcUtils.getMatchCount(this.getLottery())) {
				throw new WebDataException("5-方案内容选项个数异常.");
			}
			return sfzcCompoundItems;
		}catch(Exception e){
			logger.warn("投注内容分析错误."+e.getMessage());
			throw new WebDataException("5-投注内容分析错误.");
		}
    }
	@Transient
	public SfzcCompoundItem getBetValue(SfzcCompoundItem sfzcCompoundItem,String value) {
		List<String> list = Lists.newArrayList();
		if(value.indexOf("^")!=-1){
			String[] arr = value.split("\\^");
			for (String str : arr) {
				list.add(str);
			}
		}else{
			list.add(value);
		}
		for (String bet : list) {
			if (bet.equals("3"))
				sfzcCompoundItem.setHomeWin(true);
			if (bet.equals("1"))
				sfzcCompoundItem.setDraw(true);
			if (bet.equals("0"))
				sfzcCompoundItem.setGuestWin(true);
		}
		return sfzcCompoundItem;
	}
	// ----------------------------------------------------
	public SfzcCompoundItem[] getCompoundContent() throws WebDataException {
		if (schemeValue == null) {
			throw new WebDataException("5-方案内容获取为空.");
		}
		Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
		if (null != wParam_map) {
				String v = null == wParam_map.get("v") ? null
						: String.valueOf(wParam_map.get("v"));//如果v=1的时候格式是原始，当v为空的时候格式是json
				if (StringUtils.isBlank(v)&&schemeValue.indexOf("{")!=-1){
					if (playType == PlayType.SFZC9) {
						Map<String, Object> map;
						try{
							map = JsonUtil.getMap4Json(schemeValue);
						}catch(Exception e){
							logger.warn("投注内容分析错误."+e.getMessage());
							throw new WebDataException("5-投注内容分析错误.");
						}
						this.danMinHit = buildDanMinHit(map);
						return buildMatchItemList(map);
					} else {
							Map<String, Object> map;
							try{
								map = JsonUtil.getMap4Json(schemeValue);
							}catch(Exception e){
								logger.warn("投注内容分析错误."+e.getMessage());
								throw new WebDataException("5-投注内容分析错误.");
							}
							this.danMinHit = buildDanMinHit(map);
							return buildMatchItemList(map);
					}
				}else{
					//原来的代码
					String[] itemStrs = null;
					if (playType == PlayType.SFZC9) {
						if(schemeValue.indexOf(";")!=-1){
							this.danMinHit = Integer.valueOf(schemeValue.split(";")[0]);
							itemStrs = schemeValue.split(";")[1].split(",");
						}else{
							itemStrs = schemeValue.split(",");
						}
					} else {
						itemStrs = schemeValue.split(",");
					}
					try {
						if (itemStrs.length != ZcUtils.getMatchCount(this.getLottery())) {
							throw new WebDataException("5-方案内容选项个数异常.");
						}
					} catch (DataException e) {
						throw new WebDataException("5-方案内容选项个数异常.");
					}
					SfzcCompoundItem[] items = new SfzcCompoundItem[itemStrs.length];
					for (int i = 0; i < itemStrs.length; i++) {
						items[i] = new SfzcCompoundItem(itemStrs[i].trim(), i);
						if (playType == PlayType.SFZC9) {
							
						}else{
							if(Byte.valueOf("0").equals(items[i].toByte())){
								throw new WebDataException("5-方案内容选项异常.");
							}
						}
					}
					return items;
				}
		}
		throw new WebDataException("5-投注内容分析错误.");
	}
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		StringBuffer content = new StringBuffer();
		int units = 0;
		SfzcCompoundItem[]  items = getCompoundContent();
		if (playType == PlayType.SFZC9) {
			content.append(danMinHit).append(ZcUtils.getDanMinHitContentSpiltCode());
			List<SfzcCompoundItem> danList = new ArrayList<SfzcCompoundItem>();
			List<SfzcCompoundItem> unDanList = new ArrayList<SfzcCompoundItem>();
			for (SfzcCompoundItem item : items) {
				if (item.selectedCount() > 0) {
					if (item.isShedan()) {
						danList.add(item);
					} else {
						unDanList.add(item);
					}
				}
			}

			SchemeConverWork<SfzcCompoundItem> work = new SchemeConverWork<SfzcCompoundItem>(
					ZcUtils.SFZC9_MATCH_MINSELECT_COUNT, danList, unDanList, danMinHit, -1);
			for (List<SfzcCompoundItem> itemList : work.getResultList()) {
				units += ZcUtils.calcBetUnits(itemList.toArray(new SfzcCompoundItem[9]));
			}
		} else {
			units = ZcUtils.calcBetUnits(items);
		}

		for (SfzcCompoundItem item : items) {
			content.append(item.toByte()).append(ZcUtils.getContentSpiltCode());
		}
		return new ContentBean(units, content.substring(0, content.toString().length() - 1));
	}

	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		if (StringUtils.isBlank(schemeValue))
			throw new WebDataException("5-方案内容不能为空.");
		StringBuffer betContnet = new StringBuffer();
		String[] schemeValueArr = schemeValue.split("\\+");
		int units = 0;
		for (String line : schemeValueArr) {
			if (line.length() > 1024)
				throw new WebDataException("5-方案内容太大.");
			line = this.formatSingleResults(line);//格式化内容,去除分隔符
			this.validateSingleResults(line);//验证格式合法性
			if (StringUtils.isNotBlank(line)) {
				units++;
				betContnet.append(line);
				betContnet.append("\r\n");
			}
		}
		return new ContentBean(units, betContnet.toString());
	}
	/**
	 * 验证单式内容的合法性
	 * @param line
	 * @throws DataException
	 */
	private void validateSingleResults(String line) throws WebDataException{
		try {
			if (line.length() != ZcUtils.getMatchCount(Lottery.SFZC))
				throw new WebDataException("5-投注内容格式不正确.");
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
		if (playType == PlayType.SFZC14) {
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (c == '0' || c == '1' || c == '3')
					continue;
				throw new WebDataException("5-投注内容格式不正确.");
			}
		} else if (playType == PlayType.SFZC9) {
			int codeCount = 0;
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (c == '0' || c == '1' || c == '3' || c == ZcUtils.getSfzc9NoSelectedCode()) {
					if (c != ZcUtils.getSfzc9NoSelectedCode())
						codeCount++;
					continue;
				}
				throw new WebDataException("5-投注内容格式不正确.");
			}
			if (codeCount != 9)
				throw new WebDataException("5-投注内容格式不正确.");
		} else {
			throw new WebDataException("5-玩法匹配异常.");
		}
	}
	/**
	 * 格式化单式投注项内容
	 * @param line
	 * @return
	 */
	private String formatSingleResults(String line){
		final String converStr ="-|,";//单式方案兼容"-"或","的投注项分隔符
		line = line.trim();
		line = StringUtils.deleteWhitespace(line);
		line = line.replaceAll("\\"+converStr, "");
		line = line.replaceAll("\\*", String.valueOf(ZcUtils.getSfzc9NoSelectedCode()));
		return line;
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.SFZC;
	}

	@Override
	protected void buildReqParamVisitor(ReqParamVisitor reqParamVisitor) throws WebDataException {
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
		if(null!=reqParamVisitor.getPlayType()){
			try{
				this.playType = PlayType.values()[(Integer.valueOf(reqParamVisitor.getPlayType()))];
			}catch(Exception e){
				throw new WebDataException("5-玩法类型不能为空.");
			}
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


	public PlayType getPlayType() {
		return playType;
	}


	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}


	public String getSchemeValue() {
		return schemeValue;
	}


	public void setSchemeValue(String schemeValue) {
		this.schemeValue = schemeValue;
	}


	@Override
	protected List buildMatchList() throws WebDataException {

		List list = Lists.newArrayList();
    	Period period = periodManager.loadPeriod(this.getLottery(), this.periodNumber);
    	if (period == null)
			throw new WebDataException("8-期数据不存在.");
    	
    	SfzcMatch[] matchs = matchEntityManager.findMatchs(period.getId());
		if (matchs == null || matchs.length==0)
			throw new WebDataException("9-对阵不存在.");
		Map<Integer, SfzcMatch> matchMap = new HashMap<Integer, SfzcMatch>();
		for (SfzcMatch m : matchs) {
			matchMap.put(m.getLineId(), m);
		}
		SfzcMatch sfzcMatch = null;
		ZcMatchDTO zcMatchDTO = null;
		for (Integer lineId : matchMap.keySet()) {
			zcMatchDTO = new ZcMatchDTO();
			sfzcMatch = matchMap.get(lineId);
			zcMatchDTO.setCancel(sfzcMatch.isCancel());
			zcMatchDTO.setEnded(!period.isOnSale());
			zcMatchDTO.setGameColor(sfzcMatch.getGameColor());
			zcMatchDTO.setGameName(sfzcMatch.getGameName());
			zcMatchDTO.setGuestTeamName(sfzcMatch.getGuestTeamName());
			zcMatchDTO.setHandicap(Float.valueOf(0));
			zcMatchDTO.setHomeTeamName(sfzcMatch.getHomeTeamName());
			zcMatchDTO.setMatchKey(""+(sfzcMatch.getLineId()));
			zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(sfzcMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			zcMatchDTO.setOpen(!sfzcMatch.isCancel());
			Map<String, RateItem> spMap = new HashMap<String, RateItem>();
			RateItem rateItem = new RateItem();
			rateItem.setChg(0);
			rateItem.setKey("asiaOdd");
			rateItem.setValue(sfzcMatch.getAsiaOdd());
			spMap.put("asiaOdd", rateItem);
			rateItem = new RateItem();
			rateItem.setChg(0);
			rateItem.setKey("odds1");
			rateItem.setValue(sfzcMatch.getOdds1());
			spMap.put("odds1", rateItem);
			rateItem = new RateItem();
			rateItem.setChg(0);
			rateItem.setKey("odds2");
			rateItem.setValue(sfzcMatch.getOdds2());
			spMap.put("odds2", rateItem);
			rateItem = new RateItem();
			rateItem.setChg(0);
			rateItem.setKey("odds3");
			rateItem.setValue(sfzcMatch.getOdds3());
			spMap.put("odds3", rateItem);
			if(null!=spMap){
				zcMatchDTO.setSp(spMap);
			}
			list.add(zcMatchDTO);
		}
		return list;
	
	}


	public Integer getDanMinHit() {
		return danMinHit;
	}


	public void setDanMinHit(Integer danMinHit) {
		this.danMinHit = danMinHit;
	}

	protected SfzcMatch[] findMatchsOfCacheable(Long periodId) {
		Element el = matchCache.get(periodId);
		if (el == null) {
			SfzcMatch[] matchs = sfzcMatchEntityManagerImpl.findMatchs(periodId);
			if (matchs != null)
				matchCache.put(new Element(periodId, matchs));

			return matchs;
		} else {
			return (SfzcMatch[]) el.getValue();
		}
	}
	@Override
	protected SchemeInfoDTO getSchemeMatchDTO(Scheme scheme)
			throws WebDataException {
		SchemeInfoDTO schemeInfoDTO = new SchemeInfoDTO();
		
		SfzcScheme sfzcScheme = (SfzcScheme) scheme;
		SfzcMatch[] matchs = findMatchsOfCacheable(scheme.getPeriodId());
		SfzcCompoundItem[] betItems;
		try {
			betItems = sfzcScheme.getCompoundContent();
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
		Map<Integer, SfzcCompoundItem> sfzcCompoundItemMap = Maps.newLinkedHashMap();
		for (SfzcCompoundItem sfzcCompoundItem : betItems) {
			sfzcCompoundItemMap.put(sfzcCompoundItem.getLineId(), sfzcCompoundItem);
		}
		List<SchemeMatchDTO> list = Lists.newArrayList();
		for (SfzcMatch sfzcMatch : matchs) {
			SchemeMatchDTO schemeMatchDTO = new SchemeMatchDTO();
			schemeMatchDTO.setMatchKey(""+(sfzcMatch.getLineId()+1));///id从零开始
			schemeMatchDTO.setCancel(sfzcMatch.isCancel());
			schemeMatchDTO.setDan(Boolean.FALSE);
			SfzcCompoundItem sfzcCompoundItem = sfzcCompoundItemMap.get(sfzcMatch.getLineId());
			StringBuffer sb = new StringBuffer();
			if(sfzcMatch.isCancel()){
				schemeMatchDTO.setEnded(true);
				String bet = sfzcCompoundItem.toBetString();
				for (int i = 0; i < bet.length(); i++) {
					char c = bet.charAt(i);
					sb.append("<font color='#ff0000'>"+c+"</font>").append(",");
				}
			}else{
				String result = sfzcMatch.getResult();
				schemeMatchDTO.setEnded(StringUtils.isNotBlank(result));
				String bet = sfzcCompoundItem.toBetString();
				if(null!=sfzcCompoundItem){
					if(StringUtils.isBlank(result)){
						for (int i = 0; i < bet.length(); i++) {
							char c = bet.charAt(i);
							sb.append(c).append(",");
						}
					}else{
						for (int i = 0; i < bet.length(); i++) {
							char c = bet.charAt(i);
							if (Character.isDigit(c)) {
								boolean pass;
								try {
									pass = sfzcCompoundItem.checkPass(c);
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
			schemeMatchDTO.setGameColor(sfzcMatch.getGameColor());
			schemeMatchDTO.setGameName(MatchNameUtil.getShotName(sfzcMatch.getGameName()));
			schemeMatchDTO.setGuestTeamName(sfzcMatch.getGuestTeamName());
			schemeMatchDTO.setHomeTeamName(sfzcMatch.getHomeTeamName());
			schemeMatchDTO.setHomeHalfScore(null);
			schemeMatchDTO.setHomeScore(sfzcMatch.getHomeScore());
			schemeMatchDTO.setGuestHalfScore(null);
			schemeMatchDTO.setGuestScore(sfzcMatch.getGuestScore());
			schemeMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(sfzcMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			schemeMatchDTO.setResult(sfzcMatch.getResult());
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			schemeMatchDTO.setBet(sb.toString());
			list.add(schemeMatchDTO);
		}
		schemeInfoDTO.setItems(list);
		try {
			schemeInfoDTO.setDanMinHit(sfzcScheme.getDanMinHit());
		} catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
		return schemeInfoDTO;
	}


	@Override
	protected List buildMatchResultList() throws WebDataException {
		List list = Lists.newArrayList();
		Period period = periodManager.loadPeriod(this.getLottery(), this.periodNumber.trim());
		SfzcMatch[] matchs = findMatchsOfCacheable(period.getId());
		ZcMatchResultDTO zcMatchDTO=null;
		for (SfzcMatch sfzcMatch : matchs) {
			zcMatchDTO = new ZcMatchResultDTO();
			zcMatchDTO.setCancel(sfzcMatch.isCancel());
			if(sfzcMatch.isCancel()){
				zcMatchDTO.setEnded(true);
				
			}else{
				String result = sfzcMatch.getResult();
				zcMatchDTO.setEnded(StringUtils.isNotBlank(result));
			}
			zcMatchDTO.setResult(sfzcMatch.getResult());
			zcMatchDTO.setGameColor(sfzcMatch.getGameColor());
			zcMatchDTO.setGameName(MatchNameUtil.getShotName(sfzcMatch.getGameName()));
			zcMatchDTO.setGuestTeamName(sfzcMatch.getGuestTeamName());
			zcMatchDTO.setHomeTeamName(sfzcMatch.getHomeTeamName());
			zcMatchDTO.setMatchKey(""+(sfzcMatch.getLineId()+1));
			zcMatchDTO.setMatchTime(com.cai310.utils.DateUtil.dateToStr(sfzcMatch.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
			zcMatchDTO.setHomeScore(sfzcMatch.getHomeScore());
			zcMatchDTO.setGuestScore(sfzcMatch.getGuestScore());
			list.add(zcMatchDTO);
		}
		return list;
	}


}
