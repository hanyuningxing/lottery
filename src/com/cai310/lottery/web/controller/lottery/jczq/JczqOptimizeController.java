package com.cai310.lottery.web.controller.lottery.jczq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.cache.JczqLocalCache;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.dto.lottery.jczq.JczqSchemeDTO;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeService;
import com.cai310.lottery.support.CombinationObj;
import com.cai310.lottery.support.Compound2Single;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.PrizeForecastData;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqSchemeConverWork;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.PlayTypeWeb;
import com.cai310.lottery.utils.CommonUtil;
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
@Namespace("/" + JczqConstant.KEY)
@Action(value = "optimize")
public class JczqOptimizeController extends OptimizeController<JczqScheme, JczqSchemeDTO, JczqSchemeCreateForm>{
	
	protected transient Logger logger = LoggerFactory.getLogger(getClass());	
	
	private static final long serialVersionUID = 5047727349319576107L;
	
	private static final int OPTIMIZE_MULTIPLE_DEFAULE=2;//九场优化默认最小倍投数	

	private static final int MAX_OPTIMIZE_MATCHS = 15;//最大的优化场次数
		
	private static final int OPTIMIZE_MAXUNITS=1000;//优化的最大注数

	private PlayType playType;
	
	private PlayTypeWeb playTypeWeb;
	
	private PassMode passMode;
		
	@Autowired
	private JczqLocalCache localCache;
	
	@Autowired
	private JczqMatchEntityManager matchEntityManager;
	
	@Autowired
	private JczqSchemeService schemeService;

	@Autowired
	private JczqSchemeEntityManager schemeEntityManager;
	
	
	@Override
	public Lottery getLotteryType() {
		return Lottery.JCZQ;
	}
	
	@Override
	protected SchemeService<JczqScheme, JczqSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected JczqSchemeEntityManager getSchemeEntityManager() { 
		return schemeEntityManager;
	}
	
		
	/**
	 * 九场竞猜胜平负，让球胜平负混投 
	 * @return
	 * @throws WebDataException
	 */
	public String bonusOptimizeNine(){
		this.playType=this.createForm.confirmSchemePlayTypeAndPassMode();
		this.playTypeWeb = PlayTypeWeb.RENJIU;
		PassType passType = PassType.P2_1;//九场竞猜为2串1

		List<JczqMatchItem> danList = new ArrayList<JczqMatchItem>();//场次胆(不同场次)
		List<JczqMatchItem> unDanList = new ArrayList<JczqMatchItem>();//场次非胆(不同场次)
		List<JczqMatchItem> items = this.createForm.getItems();
		Map<String,List<JczqMatchItem>> matchItemMap = Maps.newHashMap();
		List<JczqMatchItem> matchItems = null;
		List<String> matchkeys = Lists.newArrayList();
		String matchkey = null;
		for(JczqMatchItem item: items){
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
		try{
			int passMatchCount = passType.getMatchCount();
			List<List<JczqMatchItem>> selectedMatchItemsList = JczqUtil.spliteMatchOfPlayType(passMatchCount, danList, unDanList, matchItemMap, null, null);
			
			List<Object[]> resultList = this.doOptimize(passType, matchkeys, selectedMatchItemsList, items, null);
			
			if(resultList.size()>OPTIMIZE_MAXUNITS){
				throw new WebDataException("单倍注数不能超过"+OPTIMIZE_MAXUNITS+"注。");
			}
			
			this.prizeForecastNew(resultList, passMatchCount, danList, unDanList, matchkeys, matchItemMap);
			
			this.findSpOfMatchItem(items);
			
			addActionMessage("成功！");
			return this.success();
		}catch(WebDataException ex){
			this.addActionError(ex.getMessage());
			return this.faile();
		}
	}
	
	/**
	 * 取相关场次的赔率值(所有选项的值)
	 * @param items
	 * @throws WebDataException
	 */
	private void findSpOfMatchItem(List<JczqMatchItem> items) throws WebDataException{
		String[] sps = new String[items.size()];
		StringBuffer sb = new StringBuffer();
		String matchKey = null;
		PlayType playType = null;
		Map<String,RateItem> rateItemMap = null;
		Item[] itemsOfPlayType = null;
		for(int i=0;i<items.size();i++){
			matchKey = items.get(i).getMatchKey();
			playType = items.get(i).getPlayType();
			rateItemMap = matchKey_RateMap_map.get(this.buildMixSpMapKey(matchKey, playType.toString())).get(matchKey);
			itemsOfPlayType = playType.getAllItems();
			sb.setLength(0);
			for(Item itempt : itemsOfPlayType){
				if(rateItemMap==null || rateItemMap.get(itempt.toString())==null){
					throw new WebDataException("没有相关sp值信息.");
				}
				Double sp = rateItemMap.get(itempt.toString()).getValue();
				sb.append(sp).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sps[i] = sb.toString();
		}
		if(this.isAjaxRequest()){
			jsonMap.put("sps", sps);
		}else{
			Struts2Utils.setAttribute("sps", sps);
		}
	}
	
	
	/**
	 * form提交的奖金优化
	 * @return
	 * @throws WebDataException
	 */
	public String bonusOptimize() {
		try {
			List<JczqMatchItem> selectedMatchItems = createForm.getItems();
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
			if(this.playTypeWeb == null)
				this.playTypeWeb = PlayTypeWeb.playType2Web(this.playType);
			
			this.passMode = createForm.getPassMode();
			if(this.playType==null){
				throw new WebDataException("玩法类型不能为空.");
			}
			
			if(createForm.getJczqChasePlanDetailId() != null)
				Struts2Utils.setAttribute("jczqChasePlanDetailId", createForm.getJczqChasePlanDetailId());
			
				
			List<JczqMatchItem> danList = new ArrayList<JczqMatchItem>();//场次胆(不同场次)
			List<JczqMatchItem> unDanList = new ArrayList<JczqMatchItem>();//场次非胆(不同场次)
			Map<String,List<JczqMatchItem>> matchItemMap = Maps.newHashMap();
			List<JczqMatchItem> matchItems = null;
			List<String> matchkeys = Lists.newArrayList();
			String matchkey = null;
			for(JczqMatchItem item : selectedMatchItems){
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
			List<List<JczqMatchItem>> selectedMatchItemsList = JczqUtil.spliteMatchOfPlayType(passMatchCount, danList, unDanList, matchItemMap, null, null);
			
			//验证优化注数是否合法
			List<JczqMatchItem> danListT = new ArrayList<JczqMatchItem>();//场次胆(不同场次)
			List<JczqMatchItem> unDanListT = new ArrayList<JczqMatchItem>();//场次非胆(不同场次)
			int units = 0;
			for(List<JczqMatchItem> selectedMatchItemsT:selectedMatchItemsList){
				danListT.clear();
				unDanListT.clear();
				for (JczqMatchItem item : selectedMatchItemsT) {
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
			this.prizeForecastNew(resultList, passMatchCount, danList, unDanList, matchkeys, matchItemMap);			
			
			return this.forward(true, "bonusOptimize");
		} catch (WebDataException e) {
			this.addActionMessage(e.getMessage());
			return error();
		}
	}
		
	
	/**
	 * request提交的奖金优化
	 * @return
	 * @throws WebDataException
	 */
	public String bonusOptimizeRequest()throws WebDataException{
		String matchKeys = Struts2Utils.getParameter("matchKeys");
		if(StringUtil.isEmpty(matchKeys)){
			throw new WebDataException("选择场次不能为空.");
		}
		String betvs = Struts2Utils.getParameter("betvs");
		if(StringUtil.isEmpty(betvs)){
			throw new WebDataException("选择场次内容不能为空.");
		}
		String typev = Struts2Utils.getParameter("typev");
		if(StringUtil.isEmpty(typev)){
			throw new WebDataException("过关类型不能为空.");
		}
		String playTypeStr = Struts2Utils.getParameter("pt");
		if(StringUtil.isEmpty(playTypeStr)){
			throw new WebDataException("玩法类型不能为空.");
		}
		String[] keys =  matchKeys.split(",");
		String[] bvs = betvs.split(",");
		if(keys.length!=bvs.length){
			throw new WebDataException("场次与选择内容没有对应.");
		}
		PassType passType = PassType.getSpPassType(StringUtil.toLong(typev));
		this.playType = PlayType.valueOf(playTypeStr);	

		String money = Struts2Utils.getParameter("m");
		if(StringUtil.isEmpty(money)){
			throw new WebDataException("选择方案的金额为空.");
		}
		
		JczqMatchItem matchItem;
		List<JczqMatchItem> selectedMatchItems = new ArrayList<JczqMatchItem>();
		for(int i=0;i<keys.length;i++){
			matchItem = new JczqMatchItem();
			matchItem.setMatchKey(keys[i]);
			matchItem.setValue(Integer.valueOf(bvs[i]));
			matchItem.setDan(Boolean.FALSE);
			selectedMatchItems.add(matchItem);
		}
		
		List<List<JczqMatchItem>> selectedMatchItemsList = Lists.newArrayList();
		selectedMatchItemsList.add(selectedMatchItems);
//		Map<String,JczqMatch> matchMap = this.doOptimize(passType, Arrays.asList(keys), selectedMatchItemsList, Integer.valueOf(money));
//		if(this.prizeForecast){
//			this.prizeForecast(selectedMatchItems,matchMap);
//		}
		
		return this.forward(true, "bonusOptimize");
	}
		
	
	/**
	 * 奖金优化动作
	 * @return
	 */
	private List<Object[]> doOptimize(PassType passType, List<String> matchKeyList, List<List<JczqMatchItem>> selectedMatchItemsList, List<JczqMatchItem> selectedMatchItems, Integer money) throws WebDataException{
		this.salesMode=SalesMode.SINGLE;//优化方案做单式处理
		
		List<JczqMatch> matchs = matchEntityManager.findMatchs(matchKeyList);		
		Map<String,JczqMatch> matchsMap = Maps.newHashMap();
		for(JczqMatch match : matchs){
			matchsMap.put(match.getMatchKey(), match);
		}
		
		List<Object[]> resultList = Lists.newArrayList();
		for(List<JczqMatchItem> matchItems:selectedMatchItemsList){
			List<Object[]> resultArr = this.spliteCompoundContent(matchItems, passType, matchKeyList, matchsMap);
			resultList.addAll(resultArr);
		}
		
		int totalUnits = 0;//方案总注数
		if(money!=null){
			totalUnits = money/this.getCreateForm().getUnitsMoney();
			
		}else{
			totalUnits = resultList.size()*OPTIMIZE_MULTIPLE_DEFAULE;//九场优化没有提供money,由组合后的方案数确定 * 默认倍投数
			money = totalUnits*this.getCreateForm().getUnitsMoney();
			this.createForm.setSchemeCost(money);			
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
			jsonMap.put("playTypeWeb", playTypeWeb);
		}else{
			Struts2Utils.setAttribute("results", resultList);
			Struts2Utils.setAttribute("matchKeys", StringUtil.listToString(matchKeyList));
			Struts2Utils.setAttribute("passType", passType);
			Struts2Utils.setAttribute("playType", playType);
			Struts2Utils.setAttribute("playTypeWeb", playTypeWeb);
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
	private List<Object[]> bulidMatchShowInfo(List<String> matchKeyList, List<JczqMatchItem> selectedMatchItems){
		List<Object[]> selectedMatchList = Lists.newArrayList();//内容选项List
		Map<String,List<JczqMatchItem>> matchItemsOfMatch = Maps.newLinkedHashMap();
		List<JczqMatchItem> matchItemsOfMap = null;
		//方案选择内容信息Object[0]=赛程、Object[1]=选项
		Object[] selectedInfo = null;

		for(JczqMatchItem item : selectedMatchItems){
			matchItemsOfMap = matchItemsOfMatch.get(item.getMatchKey());
			if(matchItemsOfMap==null){
				matchItemsOfMap = Lists.newArrayList();
				matchItemsOfMatch.put(item.getMatchKey(), matchItemsOfMap);
			}
			matchItemsOfMap.add(item);
		}
		
		List<JczqMatch> matchs = matchEntityManager.findMatchs(matchKeyList);
		for(JczqMatch match : matchs){
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
	private Object[][] buildCompound2Single(List<JczqMatchItem> matchItems)throws WebDataException{
		Object[][] matchItemArr = new Object[matchItems.size()][];
		Object[] selectedItemArr;
		int matchIndex = 0;//赛程下标
		PlayType playType = null;
		for (JczqMatchItem item : matchItems) {// 遍历小复式组合单式操作
			selectedItemArr = new Object[item.selectedCount()];
			playType=item.getPlayType();
			int index = 0;//赛程选项下标
			switch (playType) {// 根据玩法不同,拆小复式为单式
			case SPF:
			case RQSPF:
				for (ItemSPF pitem : ItemSPF.values()) {
					if (item.hasSelect(pitem)) {
						selectedItemArr[index] = pitem;
						index++;
					}
				}
				break;
			case JQS:
				for (ItemJQS pitem : ItemJQS.values()) {
					if (item.hasSelect(pitem)) {
						selectedItemArr[index] = pitem;
						index++;
					}
				}
				break;
			case BF:
				for (ItemBF pitem : ItemBF.values()) {
					if (item.hasSelect(pitem)) {
						selectedItemArr[index] = pitem;
						index++;
					}
				}
				break;
			case BQQ:
				for (ItemBQQ pitem : ItemBQQ.values()) {
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
	private List<Object[]> spliteCompoundContent(List<JczqMatchItem> selectedMatchItems,PassType passType,List<String> matchKeyList,Map<String,JczqMatch> matchsMap)throws WebDataException{
		List<JczqMatchItem> danList = new ArrayList<JczqMatchItem>();
		List<JczqMatchItem> unDanList = new ArrayList<JczqMatchItem>();
		Map<String,Map<String,RateItem>> rateMap=null;
		for(JczqMatchItem matchItem : selectedMatchItems){
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
		
		JczqSchemeConverWork<JczqMatchItem> work = new JczqSchemeConverWork<JczqMatchItem>(passType.getMatchCount(), danList, unDanList, danMinMaxHit[0], danMinMaxHit[1]);
		
		List<List<JczqMatchItem>> combList = work.getResultList();		
		//奖金优化后的方案内容列表
		List<Object[]> resultList = Lists.newArrayList();
		for(List<JczqMatchItem> matchItems : combList){
			Object[][] matchItemArr = this.buildCompound2Single(matchItems);
			//组合后的单式投注内容数组
			Object[][] objectsArrays = Compound2Single.assembleArraysToPlanerArray(matchItemArr);
			JczqMatch match = null;//赛程
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
					
					JczqMatchItem matchItem = matchItems.get(i);
					String matchKey = matchItem.getMatchKey();
					int index = matchKeyList.indexOf(matchKey);
					index_value_map.put(index,item.ordinal());	
					
					match = matchsMap.get(matchKey);
					infoArr[0] = JczqUtil.getDayOfWeekStr(match.getMatchDate());//周几
					infoArr[1] = JczqUtil.formatLineId(match.getLineId());//场次序列 001
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
					if(PlayType.RQSPF.equals(matchItem.getPlayType())){
						infoArr[4] = match.getHandicap();
					}
					
				}
				
				//组合投注内容
				for(Integer key:index_value_map.keySet()){
					valueArr[key]=String.valueOf(index_value_map.get(key));
					playTypeArr[key]=index_playType_map.get(key).toString();
				}					
				bonus = bonus*2;
				bonus = CommonUtil.bankRoundPrize(bonus);//单注内容sp乘积
				resultInfoArr[0] = contentItems;//方案内容数组集合
				resultInfoArr[1] = 0;//倍数(根据总投注金额动态优化)
				resultInfoArr[2] = bonus;//计算单注的赔率（即奖金）
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
	private int[] getDanMinMaxHit(final List<JczqMatchItem> danList){
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
	private void prizeForecastNew(final List<Object[]> resultList,int passMatchCount,final List<JczqMatchItem> danList,final List<JczqMatchItem> unDanList,final List<String> matchkeys, Map<String,List<JczqMatchItem>> matchItemMap)throws WebDataException{

//		List<String> danMatchkeys = Lists.newArrayList();
//		List<String> unDanMatchkeys = Lists.newArrayList();
//		for(JczqMatchItem matchItem : danList){
//			danMatchkeys.add(matchItem.getMatchKey());
//		}
//		for(JczqMatchItem matchItem : unDanList){
//			unDanMatchkeys.add(matchItem.getMatchKey());
//		}
//		//按场次纵向分场次过关组合
//		for(int passCount=passMatchCount;passCount<=matchkeys.size();passCount++){
//			int[] danMinMaxHit = getDanMinMaxHit(danList);
//			CombinationObj<String> combMatchkey = new CombinationObj<String>(passCount,danMatchkeys,unDanMatchkeys,danMinMaxHit[0], danMinMaxHit[1]);
//			List<List<String>> matchkeyLists = combMatchkey.getResultList();
//			for(List<String> keys : matchkeyLists){
//				System.out.println(keys);
//			}
//		}
		
		
		
		
		
		
		
		
		
		
		Map<String,double[]> forcastMinMaxPrizeMap = this.buildPrizeForecast(resultList, matchkeys, this.excludePrizeItem(resultList, matchkeys, matchItemMap));
		
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
			JczqSchemeConverWork<JczqMatchItem> work = new JczqSchemeConverWork<JczqMatchItem>(passCount, danList, unDanList, danMinMaxHit[0], danMinMaxHit[1]);
			List<List<JczqMatchItem>> combList = work.getResultList();
			List<JczqMatchItem> danListT = Lists.newArrayList();
			List<JczqMatchItem> unDanListT = Lists.newArrayList();
			
			Double minPrize = null;
			Double maxPrize = null;
			for(List<JczqMatchItem> combItems:combList){//场次组合
				danListT.clear();
				unDanListT.clear();
				for(int i=0;i<passCount;i++){
					JczqMatchItem combItem = combItems.get(i);
					if(combItem.isDan()){
						danListT.add(combItem);
					}else{
						unDanListT.add(combItem);
					}
				}
				
				work = new JczqSchemeConverWork<JczqMatchItem>(passMatchCount, danListT, unDanListT, -1, -1);//最小场次组合
				combList = work.getResultList();				
				
				Double minPrizeS = 0.0d;
				Double maxPrizeS = 0.0d;
				for(List<JczqMatchItem> smallCombItems:combList){
					Collections.sort(smallCombItems, new Comparator<JczqMatchItem>() {
						public int compare(JczqMatchItem o1, JczqMatchItem o2) {
							return o1.getMatchKey().compareTo(o2.getMatchKey());			
						}
					});
					matchKeyCombStr.setLength(0);
					for(JczqMatchItem item : smallCombItems){
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
			List<JczqMatchItem> matchItems = null;
			List<String> matchkeys = Lists.newArrayList();
			List<JczqMatchItem> danList = new ArrayList<JczqMatchItem>();//场次胆(不同场次)
			List<JczqMatchItem> unDanList = new ArrayList<JczqMatchItem>();//场次非胆(不同场次)
			Map<String,List<JczqMatchItem>> matchItemMap = Maps.newHashMap();
			String matchkey = null;
			List<JczqMatchItem> selectedMatchItems = createForm.getItems();
			for(JczqMatchItem item: selectedMatchItems){
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
			
			List<PassType> passTypes = createForm.getPassTypes();
			PassType passType = passTypes.get(0);
			this.prizeForecastNew(resultListOfForecast, passType.getMatchCount(), danList, unDanList, matchkeys, matchItemMap);
			return this.success();
		}catch(WebDataException ex){
			this.addActionError(ex.getMessage());
		}catch(Exception ex){
			this.addActionError(ex.getMessage());
		}
		return this.faile();
	}
	
	/**
	 * 胜平负、让球胜平负   最大排它开奖
	 * @param passCount
	 * @param danList
	 * @param unDanList
	 * @return 
	 */
	private Map<String,List<Item>> excludePrizeItem(final List<Object[]> resultList, final List<String> matchkeys, final Map<String,List<JczqMatchItem>> matchItemMap){
		
		//验证是否有中奖排它性可能(是否包含胜平负、让球胜平负)
		List<String> fitMatchKeys = Lists.newArrayList();
		List<String> matchKeysOfFit = Lists.newArrayList();
		List<JczqMatchItem> items = null;
		for(Entry<String,List<JczqMatchItem>> entry : matchItemMap.entrySet()){
			items = entry.getValue();
			for(JczqMatchItem item : items){
				if(PlayType.SPF.equals(item.getPlayType()) || PlayType.RQSPF.equals(item.getPlayType())){
					if(!matchKeysOfFit.contains(item.getMatchKey())){
						matchKeysOfFit.add(item.getMatchKey());
					}else{
						fitMatchKeys.add(item.getMatchKey());
					}
				}
			}
		}
		if(fitMatchKeys.isEmpty())return null;
		
		//获取赛程的让球信息 homeToGuest = true(主让客)
		List<JczqMatch> matchs = matchEntityManager.findMatchs(fitMatchKeys);
		Map<String,Boolean> handicapOfMatchMap = JczqUtil.getHandicpOfMatchs(matchs);
		
		
		//建立排它项关联
		Map<String,Map<Item,List<Item>>> fitMap = Maps.newHashMap();
		Map<String,List<JczqMatchItem>> fitMatchItemMap = Maps.newHashMap();
		
		boolean homeToGuest;
		for(String matchKey : fitMatchKeys){
			items = matchItemMap.get(matchKey);
			homeToGuest = handicapOfMatchMap.get(matchKey);
			JczqMatchItem itemSPF = null;
			JczqMatchItem itemRQ = null;
			for(JczqMatchItem item : items){
				if(PlayType.SPF.equals(item.getPlayType())){
					itemSPF = item;
				}else if(PlayType.RQSPF.equals(item.getPlayType())){					
					itemRQ = item;
				}
			}
			Map<Item,List<Item>> exclusionMap = JczqUtil.exculdeRuleOfSpf(homeToGuest, itemSPF, itemRQ);
			if(exclusionMap.isEmpty()){
				continue;
			}
			fitMap.put(matchKey, exclusionMap);
			List<JczqMatchItem> matchItems = Lists.newArrayList();
			matchItems.add(itemSPF);
			matchItems.add(itemRQ);
			fitMatchItemMap.put(matchKey, matchItems);
		}
		
		if(fitMap.isEmpty()) return null;
		
		//根据排它项对优化结果统计取最大的一个项组合
		int idx = 0;
		List<List<ItemDetail>> fitItemDetailsList = Lists.newArrayList();
		if(fitMatchKeys.size()==1){//一场
			String matchKey = fitMatchKeys.get(0);
			items = fitMatchItemMap.get(fitMatchKeys.get(0));
			for(JczqMatchItem item : items){
				for(ItemSPF itemSpf : ItemSPF.values()){
					if(item.hasSelect(itemSpf)){
						List<ItemDetail> itemDetails = Lists.newArrayList();
						ItemDetail idl = new ItemDetail(matchKey,item.getPlayType(),itemSpf);
						itemDetails.add(idl);
						fitItemDetailsList.add(itemDetails);
					}
				}
			}			
		}else{//多场
			Map<String,List<ItemDetail>> itemDetailOfMatchMap = Maps.newHashMap();
			Set<String> fitMatchs = fitMap.keySet();
			for(String matchKey : fitMatchs){
				items = fitMatchItemMap.get(matchKey);
				List<ItemDetail> itemDetails = Lists.newArrayList();
				for(JczqMatchItem item : items){
					for(ItemSPF itemSpf : ItemSPF.values()){
						if(item.hasSelect(itemSpf)){
							ItemDetail idl = new ItemDetail(matchKey,item.getPlayType(),itemSpf);
							itemDetails.add(idl);
						}
					}
				}
				itemDetailOfMatchMap.put(matchKey, itemDetails);
			}
			
			Object[][] matchArr = new Object[fitMatchs.size()][];
			Object[] matchItemArr = null;
			idx = 0;
			for(String matchKey : fitMatchs){
				matchItemArr = itemDetailOfMatchMap.get(matchKey).toArray();
				matchArr[idx] = matchItemArr;
				idx++;
			}
			//取符合排它场次的选项组合
			Object[][] objectsArrays = Compound2Single.assembleArraysToPlanerArray(matchArr);
			List<ItemDetail> fitItemDetails = null;
			for (Object[] objArrays : objectsArrays) {
				fitItemDetails = Lists.newArrayList();
				for(Object item: objArrays){
					fitItemDetails.add((ItemDetail)item);
				}
				fitItemDetailsList.add(fitItemDetails);
			}
		}
		
		
		//记录选项组合的排它项
		Map<Integer,Map<String,List<Item>>> excludeInfoMap = Maps.newHashMap();
		Map<String,List<Item>> excludeInfoDetailMap = null;
		PlayType excludePlayType = null;
		idx = 0;
		for(List<ItemDetail> idls : fitItemDetailsList){
			excludeInfoDetailMap =  Maps.newHashMap();
			for(ItemDetail idl : idls){
				Map<Item,List<Item>> excludeItemMap = fitMap.get(idl.getMatchKey());
				List<Item> itemsT = Lists.newArrayList();
				Item itemSPF = idl.getItem();
				if(idl.getPlayType()==PlayType.SPF){
					itemsT = excludeItemMap.get(itemSPF);
					excludePlayType = PlayType.RQSPF;
				}else{
					Iterator<Entry<Item,List<Item>>> itorOfexclude = excludeItemMap.entrySet().iterator();							
					while(itorOfexclude.hasNext()){
						Entry<Item,List<Item>> excludeEntry = itorOfexclude.next();
						List<Item> excludeItems = excludeEntry.getValue();
						if(excludeItems.contains(itemSPF)){
							itemsT.add(excludeEntry.getKey());
						}
					}
					excludePlayType = PlayType.SPF;
				}
				excludeInfoDetailMap.put(idl.getMatchKey()+excludePlayType, itemsT);
			}
			
			excludeInfoMap.put(idx, excludeInfoDetailMap);
			idx++;
		}
		
		//以排它场次选项组合取的最大奖金的组
		boolean needExclude = false;
		boolean contain = false;
		Map<Integer,Double> prizeMap = Maps.newHashMap();
		for(int index=0;index<resultList.size();index++){
			Object[] result = resultList.get(index);
			String values = String.valueOf(result[3]);
			String playTypes = String.valueOf(result[4]);
			String[] valueArr = values.split(",");
			String[] playTypeArr = playTypes.split(",");
			double prize = Double.valueOf(result[2].toString())*Integer.valueOf(result[1].toString());
			//System.out.println("ItemPrize:" + prize);
			
			List<Integer> hitPrizeFlag = Lists.newArrayList();
			int idlIndex = 0;
			for(List<ItemDetail> idls : fitItemDetailsList){
				contain = false;
				needExclude = false; 
				Map<String,List<Item>> excludeInfoDetailMapT = excludeInfoMap.get(idlIndex);
				for(ItemDetail idl : idls){
					for(int i=0;i<valueArr.length;i++){
						String value = valueArr[i];
						if("#".equals(value)){
							continue;
						}
						int valueOfInt = Integer.parseInt(value);
						String matchKey = matchkeys.get(i);
						String playType = playTypeArr[i];
						
						if(!contain && idl.getMatchKey().equals(matchKey) && idl.getPlayType().name().equals(playType) && idl.getItem().ordinal()==valueOfInt){
							contain = true;
						}
						List<Item> excludeItems = excludeInfoDetailMapT.get(matchKey+playType);
						if(excludeItems!=null){
							for(Item item : excludeItems){
								if(item.ordinal()==valueOfInt){
									needExclude = true;
									break;
								}
							}
						}
						if(needExclude)break;
					}
					if(needExclude)break;
				}
				if(contain && !needExclude){
					hitPrizeFlag.add(idlIndex);
				}				
				idlIndex++;
			}
			
			Double hitPrize=null;
			for(Integer flag : hitPrizeFlag){
				hitPrize = prizeMap.get(flag);
				if(hitPrize==null){
					hitPrize = prize;
				}else{
					hitPrize += prize;
				}
				prizeMap.put(flag, hitPrize);
			}
		}
		
		//取最大奖金值项(还有相等的情况出现，待续...........)
		Iterator<Entry<Integer,Double>> itorOfPrize = prizeMap.entrySet().iterator();
		Integer maxPrizeIndex = null;
		Double maxPrize = null;
		while(itorOfPrize.hasNext()){
			Entry<Integer,Double> prizeEntry = itorOfPrize.next();
			Double prize = prizeEntry.getValue();
			if(maxPrize==null || maxPrize<prize){
				maxPrize = prize;
				maxPrizeIndex = prizeEntry.getKey();
			}
			//System.out.println(prizeEntry.getKey()+"各项Prize："+prize);
		}
		if(maxPrizeIndex==null)return null;
		List<ItemDetail> maxDetails = fitItemDetailsList.get(maxPrizeIndex);
		
		
		String max = new String();
		for(ItemDetail item : maxDetails){
			max += item.getMatchKey() + " ";
			max += item.getPlayType().name() + " ";
			max += item.getItem().getText();
		}
		//System.out.println("最大的一项："+max+ " " + maxPrizeIndex);
		
		//取排它选项
		Map<String,List<Item>> excludeMap_max = Maps.newHashMap();
		String matchKey;
		PlayType playType;
		Item itemSPF;
		for(ItemDetail item : maxDetails){
			matchKey = item.getMatchKey();
			playType = item.getPlayType();
			itemSPF = item.getItem();
			Map<Item,List<Item>> excludeItemMap = fitMap.get(matchKey);
			if(PlayType.SPF.equals(playType)){
				excludeMap_max.put(matchKey+PlayType.RQSPF, excludeItemMap.get(itemSPF));
			}else{
				Iterator<Entry<Item,List<Item>>> itorOfexclude = excludeItemMap.entrySet().iterator();
				List<Item> itemsT = Lists.newArrayList();
				while(itorOfexclude.hasNext()){
					Entry<Item,List<Item>> excludeEntry = itorOfexclude.next();
					List<Item> excludeItems = excludeEntry.getValue();
					if(excludeItems.contains(itemSPF)){
						itemsT.add(excludeEntry.getKey());
						excludeMap_max.put(matchKey+PlayType.SPF, itemsT);
					}
				}
			}
		}
		
		return excludeMap_max;
	}
	
	class ItemDetail{
		private String matchKey;
		private PlayType playType;
		private ItemSPF item;		
		public ItemDetail(String matchKey, PlayType playType, ItemSPF item) {
			super();
			this.matchKey = matchKey;
			this.playType = playType;
			this.item = item;
		}
		public String getMatchKey() {
			return matchKey;
		}
		public void setMatchKey(String matchKey) {
			this.matchKey = matchKey;
		}
		public PlayType getPlayType() {
			return playType;
		}
		public void setPlayType(PlayType playType) {
			this.playType = playType;
		}
		public ItemSPF getItem() {
			return item;
		}
		public void setItem(ItemSPF item) {
			this.item = item;
		}
		
	}
	
	
	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public PlayTypeWeb getPlayTypeWeb() {
		return playTypeWeb;
	}

	public void setPlayTypeWeb(PlayTypeWeb playTypeWeb) {
		this.playTypeWeb = playTypeWeb;
	}

	public PassMode getPassMode() {
		return passMode;
	}

	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}

	public JczqSchemeCreateForm getCreateForm() {
		return createForm;
	}

	public void setCreateForm(JczqSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	public void setPrizeForecast(boolean prizeForecast) {
		this.prizeForecast = prizeForecast;
	}
	
}
