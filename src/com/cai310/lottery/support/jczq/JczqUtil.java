package com.cai310.lottery.support.jczq;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.Compound2Single;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.utils.CombCallBack;
import com.cai310.utils.ExtensionCombCallBack;
import com.cai310.utils.MathUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 竞彩足球工具类
 *  
 */
public final class JczqUtil {
	private static final NumberFormat LINEID_NF = new DecimalFormat("000");
	private static final String MATCH_DATE_FORMAT = "yyyyMMdd";

	/**
	 * 获取用二进制位的第几位表示该类型
	 * 
	 * @param playType
	 *            玩法类型
	 * @param passMode
	 *            过关模式
	 * @return 用二进制位的第几位表示该类型
	 */
	public static int getOpenFlagOffset(PlayType playType, PassMode passMode) {
		int len = PassMode.values().length-1;
	    
		int offset = playType.ordinal() * len + passMode.ordinal();
		return offset;
	} 

	/**
	 * 获取表示该类型的值
	 * 
	 * @param playType
	 *            玩法类型
	 * @param passMode
	 *            过关模式
	 * @return 表示该类型的值
	 */
	public static int getOpenFlag(PlayType playType, PassMode passMode) {
		return 0x1 << getOpenFlagOffset(playType, passMode);
	}

	/**
	 * 格式时场次序号
	 * 
	 * @param lineIdOfDay
	 *            场次序号
	 * @return 已格式的场次序号
	 */
	public static String formatLineId(int lineIdOfDay) {
		return LINEID_NF.format(lineIdOfDay);
	}

	/**
	 * 组装场次标识
	 * 
	 * @param matchDate
	 *            比赛场次的日期
	 * @param lineIdOfDay
	 *            场次序号
	 * @return 场次标识
	 */
	public static String buildMatchKey(int matchDate, int lineIdOfDay) {
		return String.format("%s-%s", matchDate, formatLineId(lineIdOfDay));
	}

	public static Integer getMatchDate(String dayOfWeekStr, Date matchTime) {
		int dayOfWeek = getDayOfWeek(dayOfWeekStr);
		if (dayOfWeek == -1)
			return null;

		DateTime dateTime = new DateTime(matchTime);

		int tryTimes = 3;
		while (dayOfWeek != dateTime.getDayOfWeek() && tryTimes > 0) {
			dateTime = dateTime.plusDays(-1);
			tryTimes--;
		}
		return Integer.valueOf(dateTime.toString(MATCH_DATE_FORMAT));
	}

	/**
	 * Get the day of week field value.
	 * <p>
	 * The values for the day of week are defined in
	 * {@link org.joda.time.DateTimeConstants}.
	 * 
	 * @param dayOfWeekStr
	 *            一、二、三、四、五、六、日
	 * @return the day of week
	 */
	public static int getDayOfWeek(String dayOfWeekStr) {
		if (StringUtils.isNotBlank(dayOfWeekStr)) {
			if ("一".equals(dayOfWeekStr)) {
				return DateTimeConstants.MONDAY;
			} else if ("二".equals(dayOfWeekStr)) {
				return DateTimeConstants.TUESDAY;
			} else if ("三".equals(dayOfWeekStr)) {
				return DateTimeConstants.WEDNESDAY;
			} else if ("四".equals(dayOfWeekStr)) {
				return DateTimeConstants.THURSDAY;
			} else if ("五".equals(dayOfWeekStr)) {
				return DateTimeConstants.FRIDAY;
			} else if ("六".equals(dayOfWeekStr)) {
				return DateTimeConstants.SATURDAY;
			} else if ("日".equals(dayOfWeekStr)) {
				return DateTimeConstants.SUNDAY;
			}
		}
		return -1;
	}

	public static String getDayOfWeekStr(int matchDate) {
		DateTime dateTime = getDateTime(matchDate);
		return dateTime.toString("E", Locale.SIMPLIFIED_CHINESE).replaceAll("星期", "周");
	}

	public static DateTime getDateTime(int matchDate) {
		int year = matchDate / 10000;
		int temp = matchDate % 10000;
		int month = temp / 100;
		int day = temp % 100;
		return new DateTime(year, month, day, 0, 0, 0, 0);
	}
	
	public static String getMatchKeyText(String matchkey) {
		String matchkey_text="";
		if(StringUtil.isEmpty(matchkey))return matchkey_text;
		String[] matchkeyArr = matchkey.split("-");
		if(matchkeyArr.length!=2)return matchkey_text;		
		matchkey_text = JczqUtil.getMatchKeyText(Integer.parseInt(matchkeyArr[0]), Integer.parseInt(matchkeyArr[1]));
		return matchkey_text;
	}

	public static String getMatchKeyText(int matchDate, int lineId) {
		return String.format("%s%s", getDayOfWeekStr(matchDate), formatLineId(lineId));
	}
	public static int getMatchDayOfWeek(int matchDate) {
		DateTime dateTime = getDateTime(matchDate);
		String  dayOfWeekStr = dateTime.toString("E", Locale.SIMPLIFIED_CHINESE).replaceAll("星期", "");
		
		if (StringUtils.isNotBlank(dayOfWeekStr)) {
			if ("一".equals(dayOfWeekStr)) {
				return DateTimeConstants.MONDAY;
			} else if ("二".equals(dayOfWeekStr)) {
				return DateTimeConstants.TUESDAY;
			} else if ("三".equals(dayOfWeekStr)) {
				return DateTimeConstants.WEDNESDAY;
			} else if ("四".equals(dayOfWeekStr)) {
				return DateTimeConstants.THURSDAY;
			} else if ("五".equals(dayOfWeekStr)) {
				return DateTimeConstants.FRIDAY;
			} else if ("六".equals(dayOfWeekStr)) {
				return DateTimeConstants.SATURDAY;
			} else if ("日".equals(dayOfWeekStr)) {
				return DateTimeConstants.SUNDAY;
			}
		}
		return -1;
	}
	public static void main(String[] args) {
		
		System.out.println(JczqUtil.getMatchDayOfWeek(Integer.valueOf("20130922".trim())));
	}
    public static Integer getMatchWeek(String matchKey){
    	Calendar cal = Calendar.getInstance();
    	String[] matchKeyArr  = matchKey.split("-");
    	return getMatchDayOfWeek(Integer.valueOf(matchKeyArr[0].trim()));
    }
    public static String getLineId(String matchKey){
    	String[] matchKeyArr  = matchKey.split("-");
    	return LINEID_NF.format(Integer.valueOf(matchKeyArr[1]));
    }
	/**
	 * 拆分倍数
	 * 
	 * @param multiple
	 *            原始倍数
	 * @return 拆分后的倍数集合
	 */
	public static List<Integer> splitMultiple(int betUnits, int multiple) {
		if (betUnits <= 0 || multiple <= 0)
			return null;
		int betCostPerMul = betUnits * 2;
		int ticketMaxMultiple = JczqConstant.TICKET_MAX_BETCOST / betCostPerMul;
		if (ticketMaxMultiple > JczqConstant.TICKET_MAX_MULTIPLE) {
			ticketMaxMultiple = JczqConstant.TICKET_MAX_MULTIPLE;
		}
		List<Integer> list = Lists.newArrayList();
		while (multiple > ticketMaxMultiple) {
			list.add(ticketMaxMultiple);
			multiple -= ticketMaxMultiple;
		}
		if (multiple > 0)
			list.add(multiple);
		return list;
	}

	/**
	 * 多选过关拆票
	 * 
	 * @param size
	 *            选择场次
	 * @param pass
	 *            过关数
	 * @param maxMatchSize
	 *            最大场次
	 * @param call
	 *            回调函数
	 */
	public static void split(final int size, final int pass, final int maxMatchSize, final SplitTicketCallBack call) {
		final int maxSame = pass - 1;
		List<PassType> passTypeList = PassType.findPassTypes(pass, maxMatchSize);
		Collections.sort(passTypeList, new Comparator<PassType>() {

			public int compare(PassType o1, PassType o2) {
				if (o1.getMatchCount() > o2.getMatchCount())
					return 1;
				else if (o1.getMatchCount() < o2.getMatchCount())
					return -1;
				return 0;
			}
		});
		final Set<boolean[]> set = Sets.newHashSet();
		for (int i = passTypeList.size() - 1; i > -1; i--) {
			final PassType passType = passTypeList.get(i);
			MathUtils.efficientComb(size, passType.getMatchCount(), new CombCallBack() {

				public boolean check(boolean[] newComb) {
					for (boolean[] comb : set) {
						int same = countSame(newComb, comb);
						if (same > maxSame)
							return false;
					}
					return true;
				}

				public int countSame(boolean[] comb1, boolean[] comb2) {
					int count = 0;
					for (int i = 0; i < comb1.length; i++) {
						if (comb1[i] && comb2[i])
							count++;
					}
					return count;
				}

				public boolean callback(final boolean[] comb, final int m) {
					if (check(comb)) {
						set.add(comb.clone());
						call.callback(comb, m, passType);
					}
					return false;
				}

			});
		}
	}

	/**
	 * 单关拆分
	 * 
	 * @param callback
	 *            拆分回调函数
	 * @param totalMultiple
	 *            总倍数
	 * @param matchItemList
	 *            投注选项
	 */
	public static void singleSplit(final TicketSplitCallback callback, final int totalMultiple,
			List<JczqMatchItem> matchItemList) {
		final PassType passType = PassType.P1;
		int m = JczqConstant.TICKET_SINGLE_MAX_MATCH_SIZE;
		int l = matchItemList.size();
		for (int f = 0, t = (f + m > l) ? l : f + m; f < l; f += m, t = (t + m > l) ? l : t + m) {
			List<JczqMatchItem> list = matchItemList.subList(f, t);
			splitWithMultiple(callback, totalMultiple, passType, list);
		}
	}

	/**
	 * 没有设胆的多选过关拆分
	 * 
	 * @param callback
	 *            拆分回调函数
	 * @param totalMultiple
	 *            总倍数
	 * @param playTypeMaxMatchSize
	 *            玩法允许的最大场次数目
	 * @param passTypes
	 *            过关类型
	 * @param matchItemList
	 *            投注选项
	 */
	public static void undanMultiplePassSplit(final TicketSplitCallback callback, final int totalMultiple,
			final int playTypeMaxMatchSize, final List<PassType> passTypes, final List<JczqMatchItem> matchItemList) {
		SplitTicketCallBack call = new SplitTicketCallBack() {

			@Override
			public void callback(boolean[] comb, int m, PassType passType) {
				List<JczqMatchItem> combList = Lists.newArrayList();
				for (int i = 0; i < comb.length; i++) {
					if (comb[i]) {
						combList.add(matchItemList.get(i));
						if (combList.size() == m)
							break;
					}
				}
				splitWithMultiple(callback, totalMultiple, passType, combList);
			}
		};
		int matchSize = matchItemList.size();
		for (PassType passType : passTypes) {
			int pass = passType.getPassMatchs()[0];
			JczqUtil.split(matchSize, pass, playTypeMaxMatchSize, call);
		}
	}
	
	
	/**
	 * 
	 * @param callback
	 * @param totalMultiple
	 * @param playTypeMaxMatchSize
	 * @param passType
	 * @param matchItemList
	 */
	public static void multiplePassSplitSingle(final TicketSplitCallbackSingle callback, final int totalMultiple,
			final int playTypeMaxMatchSize, final PassType passType, final String content, final int index) {
		
		SplitTicketCallBack call = new SplitTicketCallBack() {
			@Override
			public void callback(boolean[] comb, int m, PassType passType) {
				splitWithMultipleSingle(callback, totalMultiple, passType, content, index);
			}
		};
		int matchSize = content.split(",").length;
		int pass = passType.getPassMatchs()[0];
		JczqUtil.split(matchSize, pass, playTypeMaxMatchSize, call);
	}

	/**
	 * 设胆的多选过关拆分
	 * 
	 * @param callback
	 *            拆分回调函数
	 * @param totalMultiple
	 *            总倍数
	 * @param passTypes
	 *            过关类型
	 * @param danList
	 *            设胆投注选项
	 * @param undanList
	 *            没有设胆的投注选项
	 * @param danMitHit
	 *            胆码最小命中数
	 * @param danMaxHit
	 *            胆码最大命中数
	 */
	public static void danMultiplePassSplit(final TicketSplitCallback callback, final int totalMultiple,
			final List<PassType> passTypes, final List<JczqMatchItem> danList, final List<JczqMatchItem> undanList,
			int danMitHit, int danMaxHit) {
		int danSize = danList.size();
		int undanSize = undanList.size();
		for (final PassType passType : passTypes) {
			MathUtils.efficientCombExtension(passType.getMatchCount(), danSize, danMitHit, danMaxHit, undanSize,
					new ExtensionCombCallBack() {

						public boolean run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
							List<JczqMatchItem> combList = Lists.newArrayList();
							for (int i = 0; i < comb1.length; i++) {
								if (comb1[i])
									combList.add(danList.get(i));
							}
							for (int i = 0; i < comb2.length; i++) {
								if (comb2[i])
									combList.add(undanList.get(i));
							}

							splitWithMultiple(callback, totalMultiple, passType, combList);
							return false;
						}
					});
		}
	}

	/**
	 * 根据倍数拆分
	 * 
	 * @param callback
	 *            拆分回调函数
	 * @param totalMultiple
	 *            总倍数
	 * @param passType
	 *            过关类型
	 * @param matchItemList
	 *            投注选项
	 */
	public static void splitWithMultiple(final TicketSplitCallback callback, final int totalMultiple,
			final PassType passType, List<JczqMatchItem> matchItemList) {
		final int units = UnitsCountUtils.countUnits(Lists.newArrayList(matchItemList), passType.getPassMatchs());
		final List<Integer> multipleList = JczqUtil.splitMultiple(units, totalMultiple);
		for (int multiple : multipleList) {
			callback.handle(matchItemList, passType, multiple);
		}
	}
	
	/**
	 * 根据倍数拆分（单式处理）
	 * 
	 * @param callback
	 *            拆分回调函数
	 * @param totalMultiple
	 *            总倍数
	 * @param passType
	 *            过关类型
	 * @param matchItemList
	 *            投注选项
	 */
	public static void splitWithMultipleSingle(final TicketSplitCallbackSingle callback, final int totalMultiple,
			final PassType passType, final String content, int index) {
		String[] values = content.split(",");
		int selectedCount = 0;
		for(String value:values){
			if(!"#".equals(value)){
				selectedCount++;
			}
		}
		final int units = UnitsCountUtils.countUnits(selectedCount,passType.getMatchCount());
		final List<Integer> multipleList = JczqUtil.splitMultiple(units, totalMultiple);		
		for (int multiple : multipleList) {
			callback.handle(index, passType, multiple);
		}
		
	}

	public static long chg2flag(List<JczqMatchItem> subList, List<JczqMatchItem> allList) {
		long flag = 0;
		for (JczqMatchItem item : subList) {
			int p = allList.indexOf(item);
			flag |= 0x1l << p;
		}
		return flag;
	}
	
	/**
	 * 
	 * @param ticketItem  出票已经拆单
	 * @param allList 总选择的列表
	 * @return 通过出票拆单标志返回选择的列表;
	 */
	public static List<JczqMatchItem> getSelectByTicketItem(TicketItem ticketItem, List<JczqMatchItem> matchItemList) {
		boolean isAll = ticketItem.getMatchFlag() == 0;
		List<JczqMatchItem> itemList = new ArrayList<JczqMatchItem>();
		for (int p = 0; p < matchItemList.size(); p++) {
			if (isAll || (ticketItem.getMatchFlag() & (0x1l << p)) > 0) {
				JczqMatchItem matchItem = matchItemList.get(p);
				itemList.add(matchItem);
			}
		}
		return itemList;
	}
    /**
     * @param ticketContent
     * @return 出票列表
     * @throws DataException
     */
	public static List<TicketItem> getTicketList(String ticketContent) throws DataException {
		List<TicketItem> list = Lists.newLinkedList();
		String[] arr = ticketContent.trim().split(TicketItem.ITEM_AND);
		for (String content : arr) {
			list.add(TicketItem.valueOf(content));
		}
		return list;
	}
	public static Map<String, Map<String, Double>> getPrintAwardMap(List<JcMatchOdds> list) {
		if (null==list||list.isEmpty())
			return null;
		
		Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
		
		
		for (int i = 0; i < list.size(); i++) {
			JcMatchOdds jcMatchOdds = list.get(i);
			Map<String, Double> awardMap = jcMatchOdds.getOptions();
			String matchKey = jcMatchOdds.getMatchKey();
			map.put(matchKey, awardMap);
		}
		
//		for (int i = 0; i < jsonArr.size(); i++) {
//			JSONObject jsonObj = jsonArr.getJSONObject(i);
//			JSONArray options = jsonObj.getJSONArray("options");
//			Map<String, Double> awardMap = new HashMap<String, Double>();
//			for (int j = 0; j < options.size(); j++) {
//				JSONObject option = options.getJSONObject(j);
//				String value = option.getString("value");
//				Double award = option.getDouble("award");
//				awardMap.put(value, award);
//			}
//			String matchKey = jsonObj.getString("matchKey");
//			map.put(matchKey, awardMap);
//		}
		return map;
	}
	public static JcMatchOddsList bulidPrintAwardMap(Map<String,Map<String, Double>> map) {
		if (map == null)
			return null;
		JcMatchOddsList jcMatchOddsList = new JcMatchOddsList();
		Map<String, Double> awardMap ;
		JcMatchOdds jcMatchOdds ;
		List<JcMatchOdds> list = Lists.newArrayList();
		for (String matchKey : map.keySet()) {
			jcMatchOdds = new JcMatchOdds();
			jcMatchOdds.setMatchKey(matchKey);
			awardMap = map.get(matchKey);
			jcMatchOdds.setOptions(awardMap);
 			list.add(jcMatchOdds);
		}
		jcMatchOddsList.setJcMatchOdds(list);
		return jcMatchOddsList;
		
	}
	public static Item[] getItemByPlayType(PlayType playType) {
		Item[] itemArr=null;
		switch (playType) {
		case SPF: itemArr =  ItemSPF.values();break;
		case JQS: itemArr =  ItemJQS.values();break;
		case BF:  itemArr =  ItemBF.values();break;
		case BQQ: itemArr =  ItemBQQ.values();break;
		default:
			break;
		}
		return itemArr;
	}
	public static String getItemTextByValue(Item item,PlayType playType) {
		switch (playType) {
		case SPF:
			for (ItemSPF type : ItemSPF.values()) {
				if (item.equals(type)) {
					return type.getValue();
				}
			}
			break;
		case JQS:
			for (ItemJQS type : ItemJQS.values()) {
				if (item.equals(type)) {
					return type.getText();
				}
			}
			break;
		case BF:
			for (ItemBF type : ItemBF.values()) {
				if (item.equals(type)) {
					if(type.equals(ItemBF.WIN_OTHER)){
						return "90";
					}else if(type.equals(ItemBF.DRAW_OTHER)){
						return "99";
					}else if(type.equals(ItemBF.LOSE_OTHER)){
						return "09";
					}else{
						return type.getValue();
					}
				}
			}
			break;
		case BQQ:
			for (ItemBQQ type : ItemBQQ.values()) {
				if (item.equals(type)) {
					return type.getValue();
				}
			}
			break;
		default:
			throw new RuntimeException("玩法不正确.");
		}
		return null;
	}
	public static String getAwardValueBySpText(String sp,PlayType playType){
		String value = null;
		if(StringUtils.isNotBlank(sp)){
			if(sp.indexOf("^")!=-1){
				String[] arr = sp.split("\\^");
				String  betValue = arr[0].trim();
				switch (playType) {
				case SPF:
					for (ItemSPF type : ItemSPF.values()) {
						if(betValue.equals(type.getValue())){
							value = type.getValue();
						}
					}
					break;
				case JQS:
					for (ItemJQS type : ItemJQS.values()) {
						if(betValue.equals(type.getText())){
							value = type.getValue();
						}
					}
					break;
				case BF:
						if("90".equals(betValue)){
								value = ItemBF.WIN_OTHER.getValue();
						}else if("99".equals(betValue)){
								value = ItemBF.DRAW_OTHER.getValue();
						}else if("09".equals(betValue)){
								value = ItemBF.LOSE_OTHER.getValue();
						}else{
							for (ItemBF type : ItemBF.values()) {
								if(type.getValue().equals(betValue)){
									value = type.getValue();
								}
							}
						}
					break;
				case BQQ:
					
						if("3".equals(betValue)){
							///03
							value = ItemBQQ.LOSE_WIN.getValue();
						}else if("1".equals(betValue)){
							///01
							value = ItemBQQ.LOSE_DRAW.getValue();
						}else if("0".equals(betValue)){
							///00
							value = ItemBQQ.LOSE_LOSE.getValue();
						}else{
							for (ItemBQQ type : ItemBQQ.values()) {
								if(type.getValue().equals(betValue)){
									value = type.getValue();
								}
							}
						}
					break;
				default:
					throw new RuntimeException("玩法不正确.");
				}
			}
		}
		return value;
	}
	public static Double getAwardBySpText(String sp){
		Double award = null;
		if(StringUtils.isNotBlank(sp)){
			if(sp.indexOf("^")!=-1){
				String[] arr = sp.split("\\^");
				award = Double.valueOf(arr[1]);
			}
		}
		return award;
	}
	
	public static String genExtraPrizeMsg(Map<String, CombinationBean> combinationMap) {
		if (combinationMap == null || combinationMap.isEmpty())
			return null;

		NumberFormat format = new DecimalFormat("0.00");
		StringBuilder sb = new StringBuilder();
		final char c = ':';
		final char c2 = '_';
		final char c3 = ';';
		for (CombinationBean bean : combinationMap.values()) {
			sb.append(bean.generateKey()).append(c).append(format.format(bean.getPrize())).append(c2).append(
					format.format(bean.getPrizeAfterTax())).append(c3);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	/**
	 * 分玩法拆解方案内容
	 * @param passMatchCount 串几
	 * @param danList 胆码列表
	 * @param unDanList 非胆码列表
	 * @param matchItemMap 各场次不同玩法Map
	 * @param danMinHit 胆码最小命中 null=-1
	 * @param danMaxHit 胆码最大命中 null=-1
	 * @return 拆解后的串几赛程组合，一个赛程有多个选项
	 */
	public static List<List<JczqMatchItem>> spliteMatchOfPlayType(int passMatchCount,List<JczqMatchItem> danList,List<JczqMatchItem> unDanList,Map<String,List<JczqMatchItem>> matchItemMap, Integer danMinHit, Integer danMaxHit){
		if(danMinHit==null)danMinHit=-1;
		if(danMaxHit==null)danMaxHit=-1;
		//按场次纵向过关分解
		JczqSchemeConverWork<JczqMatchItem> work = new JczqSchemeConverWork<JczqMatchItem>(passMatchCount, danList, unDanList, danMinHit, danMaxHit);
		List<List<JczqMatchItem>> combList = work.getResultList();
		List<List<JczqMatchItem>> selectedMatchItemsList = Lists.newArrayList();
		List<JczqMatchItem> selectedMatchItems = null;
		String matchkey = null;
		List<JczqMatchItem> matchItems = null;
		for(List<JczqMatchItem> combItems:combList){
			Object[][] matchArr = new Object[passMatchCount][];
			Object[] matchItemArr = null;
			for(int i=0;i<passMatchCount;i++){
				matchkey = combItems.get(i).getMatchKey();
				matchItems = matchItemMap.get(matchkey);
				matchItemArr = matchItems.toArray();
				matchArr[i] = matchItemArr;
			}
			
			//组合后投注内容数组
			Object[][] objectsArrays = Compound2Single.assembleArraysToPlanerArray(matchArr);
			for (Object[] objArrays : objectsArrays) {
				selectedMatchItems = Lists.newArrayList();
				for(Object item: objArrays){
					JczqMatchItem matchItem = (JczqMatchItem)item;
					selectedMatchItems.add(matchItem);
					//System.out.print(matchItem.getMatchKey()+":"+matchItem.getPlayType()+" ");
				}
				selectedMatchItemsList.add(selectedMatchItems);
			}
		}
		return selectedMatchItemsList;
	}
	
	public static List<List<JczqMatchItem>> spliteMatchItem(int passMatchCount,List<JczqMatchItem> danList,List<JczqMatchItem> unDanList,Map<String,List<JczqMatchItem>> matchItemMap, Integer danMinHit, Integer danMaxHit){
		if(danMinHit==null)danMinHit=-1;
		if(danMaxHit==null)danMaxHit=-1;
		//按场次纵向过关分解
		JczqSchemeConverWork<JczqMatchItem> work = new JczqSchemeConverWork<JczqMatchItem>(passMatchCount, danList, unDanList, danMinHit, danMaxHit);
		List<List<JczqMatchItem>> combList = work.getResultList();
		List<List<JczqMatchItem>> selectedMatchItemsList = Lists.newArrayList();
		List<JczqMatchItem> selectedMatchItems = null;
		String matchkey = null;
		List<JczqMatchItem> matchItems = null;
		for(List<JczqMatchItem> combItems:combList){
			Object[][] matchArr = new Object[passMatchCount][];
			Object[] matchItemArr = null;
			for(int i=0;i<passMatchCount;i++){
				matchkey = combItems.get(i).getMatchKey();
				matchItems = matchItemMap.get(matchkey);
				matchItemArr = matchItems.toArray();
				matchArr[i] = matchItemArr;
			}
			
			//组合后投注内容数组
			Object[][] objectsArrays = Compound2Single.assembleArraysToPlanerArray(matchArr);
			for (Object[] objArrays : objectsArrays) {
				selectedMatchItems = Lists.newArrayList();
				for(Object item: objArrays){
					JczqMatchItem matchItem = (JczqMatchItem)item;
					selectedMatchItems.add(matchItem);
				}
				selectedMatchItemsList.add(selectedMatchItems);
			}
		}
		return selectedMatchItemsList;
	}
	
	/**
	 * 排它规则如下
	 * 
	 * 主让客 {胜：负；             平：胜、平；     负-胜、平}
	 * 客让主 {胜：平、负；   平：平、负；     负：胜}
	 *
	 *
	 * 胜负、让球胜负 排它规则
	 * @param homeToGuest 是否主让客
	 * @param itemSPF 胜平负
	 * @param itemRQ 让球胜平负
	 * @return Map<Item,List<Item>> 胜平负-让球 排他项对应
	 */
	public static Map<Item,List<Item>> exculdeRuleOfSpf(boolean homeToGuest,JczqMatchItem itemSPF,JczqMatchItem itemRQ){
		Map<Item,List<Item>> exclusionMap = new HashMap<Item,List<Item>>();
		if(itemSPF==null || itemRQ==null)return exclusionMap;
		List<Item> exclusionItems;
		if(homeToGuest){
			if(itemSPF.hasSelect(ItemSPF.WIN) && itemRQ.hasSelect(ItemSPF.LOSE)){
				exclusionItems = Lists.newArrayList();
				exclusionItems.add(ItemSPF.LOSE);
				exclusionMap.put(ItemSPF.WIN, exclusionItems);
			}
			if(itemSPF.hasSelect(ItemSPF.DRAW) && itemRQ.hasSelect(ItemSPF.WIN) || itemRQ.hasSelect(ItemSPF.DRAW)){
				exclusionItems = Lists.newArrayList();
				if(itemRQ.hasSelect(ItemSPF.WIN)){
					exclusionItems.add(ItemSPF.WIN);
				}
				if(itemRQ.hasSelect(ItemSPF.DRAW)){
					exclusionItems.add(ItemSPF.DRAW);
				}
				exclusionMap.put(ItemSPF.DRAW, exclusionItems);
			}
			if(itemSPF.hasSelect(ItemSPF.LOSE) && itemRQ.hasSelect(ItemSPF.WIN) || itemRQ.hasSelect(ItemSPF.DRAW)){
				exclusionItems = Lists.newArrayList();
				if(itemRQ.hasSelect(ItemSPF.WIN)){
					exclusionItems.add(ItemSPF.WIN);
				}
				if(itemRQ.hasSelect(ItemSPF.DRAW)){
					exclusionItems.add(ItemSPF.DRAW);
				}
				exclusionMap.put(ItemSPF.LOSE, exclusionItems);
			}
		}else{
			if(itemSPF.hasSelect(ItemSPF.WIN) && (itemRQ.hasSelect(ItemSPF.DRAW) || itemRQ.hasSelect(ItemSPF.LOSE))){
				exclusionItems = Lists.newArrayList();
				if(itemRQ.hasSelect(ItemSPF.DRAW)){
					exclusionItems.add(ItemSPF.DRAW);
				}
				if(itemRQ.hasSelect(ItemSPF.LOSE)){
					exclusionItems.add(ItemSPF.LOSE);
				}
				exclusionMap.put(ItemSPF.WIN, exclusionItems);
			}
			if(itemSPF.hasSelect(ItemSPF.DRAW) && (itemRQ.hasSelect(ItemSPF.DRAW) || itemRQ.hasSelect(ItemSPF.LOSE))){
				exclusionItems = Lists.newArrayList();
				if(itemRQ.hasSelect(ItemSPF.DRAW)){
					exclusionItems.add(ItemSPF.DRAW);
				}
				if(itemRQ.hasSelect(ItemSPF.LOSE)){
					exclusionItems.add(ItemSPF.LOSE);
				}
				exclusionMap.put(ItemSPF.DRAW, exclusionItems);
			}
			if(itemSPF.hasSelect(ItemSPF.LOSE) && itemRQ.hasSelect(ItemSPF.WIN)){
				exclusionItems = Lists.newArrayList();
				exclusionItems.add(ItemSPF.WIN);
				exclusionMap.put(ItemSPF.LOSE, exclusionItems);
			}
		}
		return exclusionMap;
	}
	
	/**
	 * 查找真实需要排除的项
	 * @param fitMatchKeys
	 * @param handicapOfMatchMap
	 * @param matchItemMap
	 * @return
	 */
	public static Map<String,List<Item>> findExcludeMapOfReal(final List<String> fitMatchKeys,final Map<String,Boolean> handicapOfMatchMap,final Map<String,List<JczqMatchItem>> matchItemMap){
		Map<String,List<Item>> excludeMap_max = Maps.newHashMap();
		List<JczqMatchItem> items = null;
		
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
		
		if(fitMap.isEmpty()) return excludeMap_max;
		
		Map<Item,List<Item>> excludeItemMap = null;
		Set<String> fitMatchs = fitMap.keySet();
		JczqMatchItem matchItemSpf = null;
		JczqMatchItem matchItemRq = null;
		
		//取得每个场次的排除项
		for(String matchKey : fitMatchs){
			items = fitMatchItemMap.get(matchKey);
			excludeItemMap = fitMap.get(matchKey);
			if(excludeItemMap==null)continue;
			
			matchItemSpf = items.get(0);
			matchItemRq = items.get(1);
			Item[] allItems = PlayType.SPF.getAllItems();
			
			Item maxSpItemOfSPF = null;
			Item maxSpItemOfRq = null;
			double maxSpOfMatch = 0.0;
			double maxSpOfRq = 0.0;			
			for(Item item : allItems){
				double spOfItem = 0.0;
				if(matchItemSpf.hasSelect(item)){
					String sp = matchItemSpf.getSps().get(item.ordinal());
					spOfItem += Double.valueOf(sp);
					List<Item> excludeItems = excludeItemMap.get(item);
					double[] minMaxSp = matchItemRq.findSelectedMinMaxSp(excludeItems);
					maxSpOfRq = minMaxSp[1];
					spOfItem += maxSpOfRq;
				}
				if(maxSpOfMatch<spOfItem){
					maxSpOfMatch=spOfItem;
					maxSpItemOfSPF = item;
				}
			}
			
			double maxSpOfMatchRq = 0.0;
			double maxSpOfSpf = 0.0;
			List<Item> itemsT = null;
			List<Item> excludeSpf = null;
			for(Item item : allItems){
				double spOfItem = 0.0;
				if(matchItemRq.hasSelect(item)){
					String sp = matchItemRq.getSps().get(item.ordinal());
					spOfItem += Double.valueOf(sp);
					itemsT = Lists.newArrayList();
					Iterator<Entry<Item,List<Item>>> itorOfexclude = excludeItemMap.entrySet().iterator();							
					while(itorOfexclude.hasNext()){
						Entry<Item,List<Item>> excludeEntry = itorOfexclude.next();
						List<Item> excludeItems = excludeEntry.getValue();
						if(excludeItems.contains(item)){
							itemsT.add(excludeEntry.getKey());
						}
					}
					double[] minMaxSp = matchItemSpf.findSelectedMinMaxSp(itemsT);
					maxSpOfSpf = minMaxSp[1];
					spOfItem += maxSpOfSpf;
				}
				if(maxSpOfMatchRq<spOfItem){
					maxSpOfMatchRq=spOfItem;
					maxSpItemOfRq = item;
					excludeSpf = itemsT;
				}
			}
			
			//找最大的sp
			if(maxSpOfMatch>maxSpOfMatchRq){
				//排除spf
				excludeSpf = Lists.newArrayList();
				for(Item item : allItems){
					if(matchItemSpf.hasSelect(item) && !maxSpItemOfSPF.equals(item)){
						excludeSpf.add(item);
					}
				}
				excludeMap_max.put(matchKey+PlayType.SPF,excludeSpf);
				//排除rq
				List<Item> excludeRqSpf = excludeItemMap.get(maxSpItemOfSPF);
				for(Item item : allItems){
					if(matchItemRq.hasSelect(item) && !excludeRqSpf.contains(item) && Double.valueOf(matchItemRq.getSps().get(item.ordinal()))!=maxSpOfRq){
						excludeRqSpf.add(item);
					}
				}				
				excludeMap_max.put(matchKey+PlayType.RQSPF,excludeRqSpf);
			}else{
				//排除rq
				List<Item> excludeRqSpf = Lists.newArrayList();
				for(Item item : allItems){
					if(matchItemRq.hasSelect(item) && !maxSpItemOfRq.equals(item)){
						excludeRqSpf.add(item);
					}
				}
				excludeMap_max.put(matchKey+PlayType.RQSPF,excludeRqSpf);								
				excludeMap_max.put(matchKey+PlayType.SPF,excludeSpf);
			}
		}
		return excludeMap_max;
	}
	
	/**
	 * 根据赛程提取让球信息
	 * @param matchs
	 * @return Map<String,Boolean>
	 */
	public static Map<String,Boolean> getHandicpOfMatchs(List<JczqMatch> matchs){
		Map<String,Boolean> handicapOfMatchMap = Maps.newHashMap();
		for(JczqMatch match : matchs){
			boolean homeToGuest = true;//主让客(true) 客让主(false)
			if(match.getHandicap()==1){
				homeToGuest = false;
			}
			handicapOfMatchMap.put(match.getMatchKey(), homeToGuest);
		}
		return handicapOfMatchMap;
	}
	
	/**
	 * 为小复式转单式构建数组参数
	 * @param matchItems
	 * @return
	 * @throws WebDataException
	 */
	public static Object[][] buildCompound2Single(List<JczqMatchItem> matchItems)throws WebDataException{
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
}
