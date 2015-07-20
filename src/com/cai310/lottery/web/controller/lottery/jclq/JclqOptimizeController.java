package com.cai310.lottery.web.controller.lottery.jclq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.cache.JclqLocalCache;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.dto.lottery.jclq.JclqSchemeDTO;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeService;
import com.cai310.lottery.support.Compound2Single;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.PrizeForecastData;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqSchemeConverWork;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.OptimizeController;
import com.cai310.utils.NumUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 奖金优化控制器
 * @author jack
 *
 */
@Namespace("/" + JclqConstant.KEY)
@Action(value = "optimize")
public class JclqOptimizeController extends OptimizeController<JclqScheme, JclqSchemeDTO, JclqSchemeCreateForm>{
	
	protected transient Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final long serialVersionUID = 5047727349319576107L;
	
	private static final int MAX_OPTIMIZE_MATCHS = 15;//最大的优化场次数
	
	private static final int OPTIMIZE_MAXUNITS=1000;//优化的最大注数

	private PlayType playType;
		
	private PassMode passMode;
		
	@Autowired
	private JclqLocalCache localCache;
	
	@Autowired
	private JclqMatchEntityManager matchEntityManager;
	
	@Autowired
	private JclqSchemeService schemeService;

	@Autowired
	private JclqSchemeEntityManager schemeEntityManager;
	
	
	@Override
	public Lottery getLotteryType() {
		return Lottery.JCLQ;
	}
	
	@Override
	protected SchemeService<JclqScheme, JclqSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected JclqSchemeEntityManager getSchemeEntityManager() { 
		return schemeEntityManager;
	}
	
		
	
	
	/**
	 * form提交的奖金优化
	 * @return
	 * @throws WebDataException
	 */
	public String bonusOptimize() {
		try {
			List<JclqMatchItem> selectedMatchItems = createForm.getItems();
			if(selectedMatchItems==null || selectedMatchItems.isEmpty()){
				throw new WebDataException("选择场次不能为空.");
			}
			List<PassType> passTypes = createForm.getPassTypes();
			if(passTypes==null || passTypes.isEmpty()){
				throw new WebDataException("过关类型不能为空.");
			}
			if(passTypes.size()>1){
				throw new WebDataException("优化的过关类型只能支持一种.");
			}
			PassType passType = passTypes.get(0);
			
			this.playType = createForm.confirmSchemePlayTypeAndPassMode();
			
			this.passMode = createForm.getPassMode();
			if(this.playType==null){
				throw new WebDataException("玩法类型不能为空.");
			}
			
			List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();//场次胆(不同场次)
			List<JclqMatchItem> unDanList = new ArrayList<JclqMatchItem>();//场次非胆(不同场次)
			Map<String,List<JclqMatchItem>> matchItemMap = Maps.newHashMap();
			List<JclqMatchItem> matchItems = null;
			List<String> matchkeys = Lists.newArrayList();
			String matchkey = null;
			for(JclqMatchItem item : selectedMatchItems){
				matchkey = item.getMatchKey();
				matchItems = matchItemMap.get(matchkey);
				if(matchItems!=null){
					matchItems.add(item);
				}else{
					matchItems = Lists.newArrayList();
					matchItems.add(item);
					matchItemMap.put(matchkey, matchItems);
				}
				if(!matchkeys.contains(matchkey)){
					matchkeys.add(matchkey);
					if(item.isDan()){
						danList.add(item);
					}else{
						unDanList.add(item);
					}
				}
			}
			
			if(matchkeys.size()>MAX_OPTIMIZE_MATCHS){
				throw new WebDataException("奖金优化不能超过"+MAX_OPTIMIZE_MATCHS+"场.");
			}
			
			int passMatchCount = passType.getMatchCount();			
			List<List<JclqMatchItem>> selectedMatchItemsList = JclqUtil.spliteMatchOfPlayType(passMatchCount, danList, unDanList, matchItemMap, null, null);
			
			//验证优化注数是否合法
			List<JclqMatchItem> danListT = new ArrayList<JclqMatchItem>();//场次胆(不同场次)
			List<JclqMatchItem> unDanListT = new ArrayList<JclqMatchItem>();//场次非胆(不同场次)
			int units = 0;
			for(List<JclqMatchItem> selectedMatchItemsT:selectedMatchItemsList){
				danListT.clear();
				unDanListT.clear();
				for (JclqMatchItem item : selectedMatchItemsT) {
					if (item.isDan())
						danListT.add(item);
					else
						unDanListT.add(item);
				}
				units += UnitsCountUtils.countUnits(selectedMatchItemsT.size(), danListT, unDanListT);
				if (units > OPTIMIZE_MAXUNITS)
					throw new WebDataException("奖金优化不能超过" + OPTIMIZE_MAXUNITS + "注.");
			}
			
			Integer money = createForm.getSchemeCost();
			List<Object[]> resultList = this.doOptimize(passType, matchkeys, selectedMatchItemsList, selectedMatchItems, money);
			
			this.prizeForecastNew(resultList, passMatchCount, danList, unDanList, matchkeys);
			
			return this.forward(true, "bonusOptimize");
		} catch (WebDataException e) {
			this.addActionMessage(e.getMessage());
			return error();
		}
	}
	
	
	/**
	 * 奖金优化动作
	 * @return
	 */
	private List<Object[]> doOptimize(PassType passType, List<String> matchKeyList, List<List<JclqMatchItem>> selectedMatchItemsList, List<JclqMatchItem> selectedMatchItems, Integer money) throws WebDataException{
		this.salesMode=SalesMode.SINGLE;//优化方案做单式处理
		
		List<JclqMatch> matchs = matchEntityManager.findMatchs(matchKeyList);		
		Map<String,JclqMatch> matchsMap = Maps.newHashMap();
		for(JclqMatch match : matchs){
			matchsMap.put(match.getMatchKey(), match);
		}
		
		List<Object[]> resultList = Lists.newArrayList();
		for(List<JclqMatchItem> matchItems:selectedMatchItemsList){
			List<Object[]> resultArr = this.spliteCompoundContent(matchItems, passType, matchKeyList, matchsMap);
			resultList.addAll(resultArr);
		}
		
		int totalUnits = 0;//方案总注数
		if(money!=null){
			totalUnits = money/this.getCreateForm().getUnitsMoney();			
		}
		if(resultList.size()>MAX_UNITS){
			throw new WebDataException("选择注数不能大于"+MAX_UNITS+"注.");
		}
				
		//奖金优化
		this.optimizeMultiple(resultList, totalUnits);
				
		//根据单式计算出来的注数，累计到场次的注数上
		
		super.loadCrrPeriod();
		
		if(this.isAjaxRequest()){
			jsonMap.put("results", resultList);
			jsonMap.put("matchKeys", StringUtil.listToString(matchKeyList));
			jsonMap.put("passType", passType);
			jsonMap.put("schemeCost", money);
			jsonMap.put("units", totalUnits);
			jsonMap.put("playType", playType);
		}else{
			Struts2Utils.setAttribute("results", resultList);
			Struts2Utils.setAttribute("matchKeys", StringUtil.listToString(matchKeyList));
			Struts2Utils.setAttribute("passType", passType);
			Struts2Utils.setAttribute("playType", playType);
			Struts2Utils.setAttribute("schemeCost", money);
			List<Object[]> selectedMatchList = bulidMatchShowInfo(matchKeyList, selectedMatchItems);
			Struts2Utils.setAttribute("selectedMatchList", selectedMatchList);
		}
		
		//return matchsMap;
		return resultList;
	}
	
	/**
	 * 构建赛程选择项的显示内容
	 */
	private List<Object[]> bulidMatchShowInfo(List<String> matchKeyList, List<JclqMatchItem> selectedMatchItems){
		List<Object[]> selectedMatchList = Lists.newArrayList();//内容选项List
		Map<String,List<JclqMatchItem>> matchItemsOfMatch = Maps.newLinkedHashMap();
		List<JclqMatchItem> matchItemsOfMap = null;
		//方案选择内容信息Object[0]=赛程、Object[1]=选项
		Object[] selectedInfo = null;
		
		for(JclqMatchItem item : selectedMatchItems){
			matchItemsOfMap = matchItemsOfMatch.get(item.getMatchKey());
			if(matchItemsOfMap==null){
				matchItemsOfMap = Lists.newArrayList();
				matchItemsOfMatch.put(item.getMatchKey(), matchItemsOfMap);
			}
			matchItemsOfMap.add(item);
		}
		
		List<JclqMatch> matchs = matchEntityManager.findMatchs(matchKeyList);
		for(JclqMatch match : matchs){
			//用于选项内容的显示
			selectedInfo = new Object[2];
			selectedInfo[0] = match;
			selectedInfo[1] = matchItemsOfMatch.get(match.getMatchKey());
			selectedMatchList.add(selectedInfo);
		}
		return selectedMatchList;
	}
		
	
	/**
	 * 为小复式转单式组装数组参数
	 * @param matchItems
	 * @return
	 * @throws WebDataException
	 */
	private Object[][] buildCompound2Single(List<JclqMatchItem> matchItems)throws WebDataException{
		Object[][] matchItemArr = new Object[matchItems.size()][];
		Object[] selectedItemArr;
		int matchIndex = 0;//赛程下标
		PlayType playType = null;
		for (JclqMatchItem item : matchItems) {// 遍历小复式组合单式操作
			selectedItemArr = new Object[item.selectedCount()];
			playType=item.getPlayType();
			int index = 0;//赛程选项下标
			switch (playType) {// 根据玩法不同,拆小复式为单式
			case SF:
				for (ItemSF pitem : ItemSF.values()) {
					if (item.hasSelect(pitem)) {
						selectedItemArr[index] = pitem;
						index++;
					}
				}
				break;
			case RFSF:
				for (ItemRFSF pitem : ItemRFSF.values()) {
					if (item.hasSelect(pitem)) {
						selectedItemArr[index] = pitem;
						index++;
					}
				}
				break;
			case SFC:
				for (ItemSFC pitem : ItemSFC.values()) {
					if (item.hasSelect(pitem)) {
						selectedItemArr[index] = pitem;
						index++;
					}
				}
				break;
			case DXF:
				for (ItemDXF pitem : ItemDXF.values()) {
					if (item.hasSelect(pitem)) {
						selectedItemArr[index] = pitem;
						index++;
					}
				}
				break;
			default:
				throw new WebDataException("玩法类型不合法.");
			}
			matchItemArr[matchIndex] = selectedItemArr;
			matchIndex++;
		}
		return matchItemArr;
	}
	
	/**
	 * 拆解复式为单式内容
	 * @param selectedMatchItems
	 * @param passType
	 * @param matchKeyList
	 * @param matchsMap
	 * @return
	 * @throws WebDataException
	 */
	private List<Object[]> spliteCompoundContent(List<JclqMatchItem> selectedMatchItems,PassType passType,List<String> matchKeyList,Map<String,JclqMatch> matchsMap)throws WebDataException{
		List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();
		List<JclqMatchItem> unDanList = new ArrayList<JclqMatchItem>();
		Map<String,Map<String,RateItem>> rateMap=null;
		for(JclqMatchItem matchItem : selectedMatchItems){
			if(matchItem.isDan()){
				danList.add(matchItem);
			}else{
				unDanList.add(matchItem);
			}
			PlayType playType = matchItem.getPlayType();
			String matchKey = matchItem.getMatchKey();
			
			String key = buildMixSpMapKey(matchKey,playType.toString());
			rateMap = matchKey_RateMap_map.get(key);
			if(rateMap==null){
				rateMap = localCache.getRateData(playType, PassMode.PASS);
				matchKey_RateMap_map.put(key, rateMap);
			}		
		}
		
		int[] danMinMaxHit = getDanMinMaxHit(danList);
		
		JclqSchemeConverWork<JclqMatchItem> work = new JclqSchemeConverWork<JclqMatchItem>(passType.getMatchCount(), danList, unDanList, danMinMaxHit[0], danMinMaxHit[1]);
		
		List<List<JclqMatchItem>> combList = work.getResultList();		
		//奖金优化后的方案内容列表
		List<Object[]> resultList = Lists.newArrayList();
		for(List<JclqMatchItem> matchItems : combList){
			Object[][] matchItemArr = this.buildCompound2Single(matchItems);
			//组合后的单式投注内容数组
			Object[][] objectsArrays = Compound2Single.assembleArraysToPlanerArray(matchItemArr);
			JclqMatch match = null;//赛程
			Object[] infoArr = null;//方案内容数组
			Map<String,RateItem> itemRateMap = null;//赔率值项
			Double bonus = null;//每注奖金(赔率乘积)
			Object[] resultInfoArr= null;//最终优化方案总信息数组(内容、倍数、总金额)
			List<Object[]> contentItems = null;//每注的投注项
			for (Object[] objArrays : objectsArrays) {
				resultInfoArr = new Object[5];
				contentItems = Lists.newArrayList();
				bonus = 1.0;
				Map<Integer,Integer> index_value_map = Maps.newHashMap();
				Map<Integer,PlayType> index_playType_map = Maps.newHashMap();
				String[] valueArr = new String[matchKeyList.size()];
				String[] playTypeArr = new String[matchKeyList.size()];
				for(int i=0;i<valueArr.length;i++){
					valueArr[i]="#";
					playTypeArr[i]="#";
				}
				for(int i=0;i<objArrays.length;i++){
					infoArr = new Object[5];
					Item item = (Item)objArrays[i];
					
					JclqMatchItem matchItem = matchItems.get(i);
					String matchKey = matchItem.getMatchKey();
					int index = matchKeyList.indexOf(matchKey);
					index_value_map.put(index,item.ordinal());	
					
					match = matchsMap.get(matchKey);
					infoArr[0] = JclqUtil.getDayOfWeekStr(match.getMatchDate());//周几
					infoArr[1] = JclqUtil.formatLineId(match.getLineId());//场次序列 001
					infoArr[2] = (item).getText();//选择内容 WIN
					itemRateMap = matchKey_RateMap_map.get(this.buildMixSpMapKey(matchKey, matchItem.getPlayType().toString())).get(matchKey);	
					index_playType_map.put(index,matchItem.getPlayType());
					Object sp = itemRateMap==null?1.0:itemRateMap.get(item.toString()).getValue();
					infoArr[3] = sp==null||(Double)sp<=0?1.0:sp;//选择内容sp值
					
					//更新选择项sp(九场需要同步过来，先前开发暂不修改，加多一个判断是否为空)
					if(matchItem.getSps()!=null){
						matchItem.getSps().set(item.ordinal(), String.valueOf(infoArr[3]));
					}					
					
					contentItems.add(infoArr);
					bonus = bonus * (Double)infoArr[3];
					infoArr[4] = match.getSingleHandicap();
				}
				
				//组合投注内容
				for(Integer key:index_value_map.keySet()){
					valueArr[key]=String.valueOf(index_value_map.get(key));
					playTypeArr[key]=index_playType_map.get(key).toString();
				}					
				bonus = Double.valueOf(Constant.numFmt.format(bonus));//单注内容sp乘积
				resultInfoArr[0] = contentItems;//方案内容数组集合
				resultInfoArr[1] = 0;//倍数(根据总投注金额动态优化)
				resultInfoArr[2] = bonus*2;//计算单注的赔率（即奖金）
				resultInfoArr[3] = StringUtils.join(valueArr,",");
				resultInfoArr[4] = StringUtils.join(playTypeArr,",");
				resultList.add(resultInfoArr);				
				//System.out.println(resultInfoArr[3]);
			}
		}
		return resultList;
	}	
	
	
	
	/**
	 * 获取模糊设胆的最大最小胆码
	 * @param danList
	 * @return
	 */
	private int[] getDanMinMaxHit(final List<JclqMatchItem> danList){
		int danMinHit = -1;
		int danMaxHit = -1;
		if(createForm.getDanMinHit()!=null && createForm.getDanMinHit() > 0 && createForm.getDanMinHit()<danList.size()){
			danMinHit = createForm.getDanMinHit();
		}else{
			danMinHit = danList.size()>0?danList.size():-1;
		}
		if(createForm.getDanMaxHit()!=null && createForm.getDanMaxHit() > 0 && createForm.getDanMaxHit()<danList.size()){
			danMaxHit = createForm.getDanMaxHit();
		}else{
			danMaxHit = danList.size()>0?danList.size():-1;
		}
		
		return new int[]{danMinHit,danMaxHit};
	}
	
	/**
	 * 正在使用的奖金预测，使用于优化方案
	 * @param passMatchCount
	 * @param danList
	 * @param unDanList
	 * @param matchkeys
	 * @throws WebDataException
	 */
	private void prizeForecastNew(final List<Object[]> resultList,int passMatchCount,final List<JclqMatchItem> danList,final List<JclqMatchItem> unDanList,final List<String> matchkeys)throws WebDataException{
		
		Map<String,double[]> forcastMinMaxPrizeMap = this.buildPrizeForecast(resultList, matchkeys, null);
		
		Map<Integer,PrizeForecastData> prizeForecastDataMap = Maps.newHashMap();
		StringBuffer matchKeyCombStr = new StringBuffer();
		
		//按场次纵向分场次过关组合
		for(int passCount=passMatchCount;passCount<=matchkeys.size();passCount++){
			PrizeForecastData prizeForecastData = prizeForecastDataMap.get(passCount);
			if(prizeForecastData==null){
				prizeForecastData = new PrizeForecastData();
				prizeForecastDataMap.put(passCount, prizeForecastData);
			}
			
			int[] danMinMaxHit = getDanMinMaxHit(danList);
			
			//中passCount场的组合
			JclqSchemeConverWork<JclqMatchItem> work = new JclqSchemeConverWork<JclqMatchItem>(passCount, danList, unDanList, danMinMaxHit[0], danMinMaxHit[1]);
			List<List<JclqMatchItem>> combList = work.getResultList();
			List<JclqMatchItem> danListT = Lists.newArrayList();
			List<JclqMatchItem> unDanListT = Lists.newArrayList();
			
			Double minPrize = null;
			Double maxPrize = null;
			for(List<JclqMatchItem> combItems:combList){//场次组合
				danListT.clear();
				unDanListT.clear();
				for(int i=0;i<passCount;i++){
					JclqMatchItem combItem = combItems.get(i);
					if(combItem.isDan()){
						danListT.add(combItem);
					}else{
						unDanListT.add(combItem);
					}
				}
				
				work = new JclqSchemeConverWork<JclqMatchItem>(passMatchCount, danListT, unDanListT, -1, -1);//最小场次组合
				combList = work.getResultList();				
				
				Double minPrizeS = 0.0d;
				Double maxPrizeS = 0.0d;
				for(List<JclqMatchItem> smallCombItems:combList){
					Collections.sort(smallCombItems, new Comparator<JclqMatchItem>() {
						public int compare(JclqMatchItem o1, JclqMatchItem o2) {
							return o1.getMatchKey().compareTo(o2.getMatchKey());			
						}
					});
					matchKeyCombStr.setLength(0);
					for(JclqMatchItem item : smallCombItems){
						matchKeyCombStr.append(item.getMatchKey());
					}
					String mapMatchsKey = matchKeyCombStr.toString();
					double[] minMaxPrize = forcastMinMaxPrizeMap.get(mapMatchsKey);
					minPrizeS+=minMaxPrize[0];
					maxPrizeS+=minMaxPrize[1];
				}
				if(minPrize==null || minPrize>minPrizeS){
					minPrize = minPrizeS;
				}
				if(maxPrize==null || maxPrize<maxPrizeS){
					maxPrize = maxPrizeS;
				}
				prizeForecastData.setMinPrize(minPrize);
				prizeForecastData.setMaxPrize(maxPrize);
			}			
		}
		
//		for(Integer passCount:prizeForecastDataMap.keySet()){
//			PrizeForecastData pfd = prizeForecastDataMap.get(passCount);
//			System.out.println("minPrize="+pfd.getMinPrize()+" maxPrize="+pfd.getMaxPrize());
//		}
		
		Double bestMinPrize = null;//最小奖金预测
		Double bestMaxPrize = null;//最大奖金预测
		if(this.isAjaxRequest()){
			Integer mapCount = prizeForecastDataMap.size();
			Integer maxIndex = mapCount+passMatchCount-1;
			List<Object[]> prizeInfo = Lists.newArrayList();
			
			for(int i=maxIndex;i>=passMatchCount;i--){
				Object[] info = new Object[5];
				info[0]=i;
				PrizeForecastData data = prizeForecastDataMap.get(i);
				String minPrize = NumUtils.format(data.getMinPrize(), 2, 2);
				String maxPrize = NumUtils.format(data.getMaxPrize(), 2, 2);
				if(data.getMinPrize()<data.getMaxPrize()){
					info[1]=minPrize;
					info[2]=maxPrize;
				}else{
					info[1]=maxPrize;
					info[2]=minPrize;
				}
				info[3]=Double.valueOf(String.valueOf(info[1]))/this.createForm.getSchemeCost()*100;
				info[4]=Double.valueOf(String.valueOf(info[2]))/this.createForm.getSchemeCost()*100;
				info[3] = NumUtils.format((Double)info[3], 2)+"%";
				info[4] = NumUtils.format((Double)info[4], 2)+"%";
				prizeInfo.add(info);
				if(bestMinPrize==null || bestMinPrize>data.getMinPrize()){
					bestMinPrize = data.getMinPrize();
				}
				if(bestMaxPrize==null || bestMaxPrize<data.getMaxPrize()){
					bestMaxPrize = data.getMaxPrize();
				}
			}
			jsonMap.put("prizeForecast", prizeInfo);
			jsonMap.put("bestMinPrize", bestMinPrize);
			jsonMap.put("bestMaxPrize", bestMaxPrize);
		}else{
			List<Entry<Integer,PrizeForecastData>> prizeForecastDatas = new ArrayList<Entry<Integer,PrizeForecastData>>(prizeForecastDataMap.entrySet());    
			Collections.sort(prizeForecastDatas, new Comparator<Map.Entry<Integer,PrizeForecastData>>() {
                public int compare(Map.Entry<Integer,PrizeForecastData> o1, Map.Entry<Integer,PrizeForecastData> o2) {
                        return (o2.getKey() - o1.getKey());
                }
			});
			PrizeForecastData pfd = null;
			Double percent = null;
			for(Entry<Integer,PrizeForecastData> data : prizeForecastDatas){
				pfd = data.getValue();
				percent = pfd.getMinPrize()/this.createForm.getSchemeCost()*100;
				pfd.setMinPercent(NumUtils.format(percent, 2)+"%");
				percent = pfd.getMaxPrize()/this.createForm.getSchemeCost()*100;
				pfd.setMaxPercent(NumUtils.format(percent, 2)+"%");
				if(bestMinPrize==null || bestMinPrize>pfd.getMinPrize()){
					bestMinPrize = pfd.getMinPrize();
				}
				if(bestMaxPrize==null || bestMaxPrize<pfd.getMaxPrize()){
					bestMaxPrize = pfd.getMaxPrize();
				}
			}
			Struts2Utils.setAttribute("bestMinPrize", bestMinPrize);
			Struts2Utils.setAttribute("bestMaxPrize", bestMaxPrize);
			Struts2Utils.setAttribute("prizeForecasts", prizeForecastDatas);
		}
	}
	
	/**
	 * ajax请求
	 * 根据已经优化的结果获取奖金预测
	 * @return
	 */
	public String prizeForecastOfAjax(){
		try{			
			List<Object[]> resultListOfForecast = Lists.newArrayList();
			List<String> contents = this.createForm.getContents();
			List<String> playTypes = this.createForm.getPlayTypes();
			List<Integer> multiples = this.createForm.getMultiples();
			if(contents.size()!=multiples.size() || contents.size()!=this.itemPrizes.size()){
				throw new WebDataException("提供的预测数据不一致！");
			}
			
			Object[] resultArr;
			int contentSize = contents.size();
			for(int i=0;i<contentSize;i++){
				resultArr = new Object[5];
				resultArr[1]=multiples.get(i);
				resultArr[2]=this.itemPrizes.get(i);
				resultArr[3]=contents.get(i);
				resultArr[4]=playTypes.get(i);
				resultListOfForecast.add(resultArr);
			}			
			List<String> matchkeys = Lists.newArrayList();
			List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();//场次胆(不同场次)
			List<JclqMatchItem> unDanList = new ArrayList<JclqMatchItem>();//场次非胆(不同场次)
			String matchkey = null;
			List<JclqMatchItem> selectedMatchItems = createForm.getItems();
			for(JclqMatchItem item: selectedMatchItems){
				matchkey = item.getMatchKey();
				if(!matchkeys.contains(matchkey)){
					matchkeys.add(matchkey);
					if(item.isDan()){
						danList.add(item);
					}else{
						unDanList.add(item);
					}
				}
			}
			List<PassType> passTypes = createForm.getPassTypes();
			PassType passType = passTypes.get(0);
			this.prizeForecastNew(resultListOfForecast, passType.getMatchCount(), danList, unDanList, matchkeys);
			return this.success();
		}catch(WebDataException ex){
			this.addActionError(ex.getMessage());
		}catch(Exception ex){
			this.addActionError(ex.getMessage());
		}
		return this.faile();
	}
		
	
	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public PassMode getPassMode() {
		return passMode;
	}

	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}

	public JclqSchemeCreateForm getCreateForm() {
		return createForm;
	}

	public void setCreateForm(JclqSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	public void setPrizeForecast(boolean prizeForecast) {
		this.prizeForecast = prizeForecast;
	}
	
}
