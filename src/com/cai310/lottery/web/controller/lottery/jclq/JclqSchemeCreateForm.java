package com.cai310.lottery.web.controller.lottery.jclq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.UnitsCountUtils;
import com.cai310.lottery.support.jclq.JclqCompoundContent;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqSchemeConverWork;
import com.cai310.lottery.support.jclq.JclqSingleContent;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jclq.SchemeType;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.utils.DateUtil;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.lottery.web.controller.lottery.SchemeCreateForm;
import com.cai310.utils.NumUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class JclqSchemeCreateForm extends SchemeCreateForm {

	// ------------------------ 复式 -----------------------

	/** 选择的场次内容 */
	private List<JclqMatchItem> items;

	/** 胆码最小命中数 */
	private Integer danMinHit;

	/** 胆码最大命中数 */
	private Integer danMaxHit;

	// --------------------- 单复式共用 --------------------

	/** 过关方式 */
	private List<PassType> passTypes;
	
	/** 方案内容 */
	private String content;

	/** 方案类型 */
	private SchemeType schemeType;
	
	/** 过关类型 */
	private PassMode passMode;

	/** 单式格式转换字符 */
	private List<String> codes;
	
	/** 单式格式赛程标识*/
	private Set<Integer> matchIds;
	
	/** 是否为优化方案*/
	private boolean isOptimize;
		
	//---- 优化方案使用-----------
	/**内容数组同倍数数组相对应*/
	private List<String> contents;
	private List<Integer> multiples;
	private List<String> matchKeys;
	private List<String> playTypes;
	private List<String> sps;//赔率集合对应于items
	private Double bestMinPrize;//最小奖金预测
	private Double bestMaxPrize;//最大奖金预测
	
	// ----------------------------------------------------
	private List<String> comOkoooCodeList;
	private List<String> com500wanCodeList;
	// ----------------------------------------------------

	
	//根据提交的选项动态确定方案混投等属性
	public PlayType confirmSchemePlayTypeAndPassMode(){
		List<JclqMatchItem> items = this.getItems();
		if(items==null)return null;
		Set<PlayType> playTypeSet = Sets.newHashSet();
		for(JclqMatchItem item : items){
			playTypeSet.add(item.getPlayType());
			if(playTypeSet.size()>2){
				break;
			}
		}
		Iterator<PlayType> ptIt = playTypeSet.iterator();
		if(playTypeSet.size()==1){
			passMode = PassMode.PASS;
			return ptIt.next();
		}else{
			passMode = PassMode.MIX_PASS;
			return PlayType.MIX;
		}
	}
	
	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {		
		Collections.sort(this.items);
		PassType minPassType = passTypes.get(0);// 最小的过关方式
		int units = 0;
		double[] minMaxSp = null;
		
		if(PassMode.MIX_PASS.equals(passMode)){
			List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();//场次胆(不同场次)
			List<JclqMatchItem> unDanList = new ArrayList<JclqMatchItem>();//场次非胆(不同场次)
			Map<String,List<JclqMatchItem>> matchItemMap = Maps.newHashMap();
			List<JclqMatchItem> matchItems = null;
			List<String> matchkeys = Lists.newArrayList();
			String matchkey = null;
			for(JclqMatchItem item: items){
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
			this.validateOfDan(minPassType, danList, unDanList);
			List<List<JclqMatchItem>> selectedMatchItemsList = Lists.newArrayList();
			for (PassType passType : this.passTypes) {
				for(int passMatchCount : passType.getPassMatchs()){
					selectedMatchItemsList.addAll(JclqUtil.spliteMatchOfPlayType(passMatchCount, danList, unDanList, matchItemMap, danMinHit, danMaxHit));
				}				
			}
			for(List<JclqMatchItem> selectedMatchItems:selectedMatchItemsList){
				danList.clear();
				unDanList.clear();
				for (JclqMatchItem item : selectedMatchItems) {
					if (item.isDan())
						danList.add(item);
					else
						unDanList.add(item);
				}
				units += UnitsCountUtils.countUnits(selectedMatchItems.size(), danList, unDanList);
				if (units > Constant.MAX_UNITS)
					throw new DataException("复式方案单倍注数不能大于" + Constant.MAX_UNITS + "注.");
			}
			minMaxSp = this.prizeForecastMinMax(passTypes, items, danList, unDanList, danMinHit, danMaxHit);
		}else{
			final List<JclqMatchItem> danList = new ArrayList<JclqMatchItem>();
			final List<JclqMatchItem> unDanList = new ArrayList<JclqMatchItem>();
			for (JclqMatchItem item : items) {
				if (item.isDan())
					danList.add(item);
				else
					unDanList.add(item);
			}

			this.validateOfDan(minPassType, danList, unDanList);

			for (PassType passType : this.passTypes) {
				for (final int needs : passType.getPassMatchs()) {
					units += UnitsCountUtils.countUnits(needs, danList, danMinHit, danMaxHit, unDanList);
					if (units > Constant.MAX_UNITS)
						throw new DataException("复式方案单倍注数不能大于" + Constant.MAX_UNITS + "注.");
				}
			}

			minMaxSp = this.prizeForecastMinMax(passTypes, items, danList, unDanList, danMinHit, danMaxHit);
		}		
		
		JclqCompoundContent content = new JclqCompoundContent();
		content.setItems(items);
		content.setDanMinHit(danMinHit);
		content.setDanMaxHit(danMaxHit);
		content.setBestMinPrize(Double.valueOf(NumUtils.format(minMaxSp[0], 2)));
		content.setBestMaxPrize(Double.valueOf(NumUtils.format(minMaxSp[1], 2)));

		return new ContentBean(units, JSONObject.fromObject(content).toString());
	}
	
	/**
	 * 获取方案内容
	 * 
	 * @return 方案内容
	 * @throws DataException
	 */
	public String getSingleSchemeContent() throws DataException {
		if (isFileUpload())
			return getUploadContent();
		else
			return getContent();
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		StringBuffer betContent = new StringBuffer();
		final String seq = "\r\n";
		if(this.isOptimize){//优化单式方案(主要区别是优化方案后面有相对应的倍数),混合过关的话还需要有playTypes对应各场次
			int contentSize = contents.size();
			
			if(contentSize!=this.multiples.size() ){
				if(PassMode.MIX_PASS.equals(this.passMode) && contents.size()!=this.getPlayTypes().size()){
					throw new DataException("投注内容错误：混合过关的玩法类型没有与投注内容一致");
				}
				throw new DataException("投注内容错误：倍投没有与投注内容一致");
			}
			
			//清理倍数为0的方案内容(前台客户注数调整所致)
			for(int i=contentSize-1;i>=0;i--){
				if(this.multiples.get(i)<=0){
					this.multiples.remove(i);
					this.contents.remove(i);
					this.playTypes.remove(i);
				}
			}
			
			int units = 0;
			for(int i=0;i<contents.size();i++){
				betContent.append(contents.get(i)).append(seq);
				units+=multiples.get(i);
				if (units > Constant.MAX_SINGLE_UNITS)
					throw new DataException("优化方案注数不能大于" + Constant.MAX_SINGLE_UNITS + "注.");
			}
			betContent.delete(betContent.length() - seq.length(), betContent.length());
			JclqSingleContent contentObj = new JclqSingleContent();
			contentObj.setMatchkeys(getMatchKeys());
			contentObj.setItems(items);
			contentObj.setBestMinPrize(bestMinPrize);
			contentObj.setBestMaxPrize(bestMaxPrize);
			contentObj.setContent(betContent.toString());
			contentObj.setOptimize(this.isOptimize);
			contentObj.setPlayTypes(this.getPlayTypes());//分号隔开，逗号相连
			contentObj.setMultiples(multiples);
			units = units/this.getMultiple();//单倍注数
			return new ContentBean(units, JSONObject.fromObject(contentObj).toString());
		}else if (isFileUpload()){//单式上传(需要兼容格式)
			String singleContent = this.getSingleSchemeContent();
			if (StringUtils.isBlank(singleContent))
				throw new DataException("方案内容不能为空.");

			final PassType passType = passTypes.get(0);
			final int codeStrLen = codes.get(0).length();
			String temp = "";
			for (int i = 0; i < codeStrLen; i++) {
				temp += "#";
			}
			final String placeholder = temp;

			singleContent = singleContent.trim();
			String[] arr = singleContent.replaceAll("(\\*)", "#").split("(\r\n|\n)+");
			int units = 0;

			LineResolve resolve=null;
			boolean hasContainsMatchKey = false;
			if (comOkoooCodeList != null && !comOkoooCodeList.isEmpty()) {
				resolve = new ComOkoooLineResolveImpl(passType, codeStrLen, placeholder, comOkoooCodeList);
				hasContainsMatchKey=true;
			} else if (com500wanCodeList != null && !com500wanCodeList.isEmpty()) {
				resolve = new Com500wanLineResolveImpl(passType, codeStrLen, placeholder, com500wanCodeList);
				hasContainsMatchKey=true;
			} else {
				resolve = new SimpleLineResolveImpl(passType, codeStrLen, placeholder);
			}
			
			//获取单式文本的场次Id
			if(hasContainsMatchKey){
				Date currentDate=new Date();
				Map<Integer,String> matchDateMap = Maps.newHashMap(); 
				for (int i = -1; i < 6; i++) {
					Date needDate = DateUtil.addDate(currentDate, i);
					String dateStr = com.cai310.utils.DateUtil.dateToStr(needDate,"yyyyMMdd");
					int weekDay = JclqUtil.getMatchDayOfWeek(Integer.valueOf(dateStr));
					matchDateMap.put(weekDay, dateStr);
				}
				matchIds = Sets.newTreeSet();
				int lineIndex=1;
				for (String line : arr) {
					matchIds.addAll(resolve.getMatchIds(lineIndex,line));
					lineIndex++;
				}
				Iterator<Integer> mIdItor =  matchIds.iterator();
				this.matchKeys = Lists.newArrayList();
				StringBuffer matchKeySb=new StringBuffer();
				while(mIdItor.hasNext()){
					matchKeySb.setLength(0);
					Integer mId = mIdItor.next();
					String mIdStr = mId.toString();
					matchKeySb.append(matchDateMap.get(Integer.valueOf(mIdStr.substring(0,1))));
					matchKeySb.append("-");
					matchKeySb.append(mIdStr.substring(1));
					this.matchKeys.add(matchKeySb.toString());
				}
			}
			
			StringBuilder sb = new StringBuilder();
			int lineIndex=1;
			for (String line : arr) {
				String formatLine = resolve.resolve(lineIndex,line);
				//1、不包含# 2、投注项>过关最小场次 ==小复式投注
				if(formatLine.indexOf("#")<0 && this.getMatchKeys().size()>passType.getMatchCount()){
					units += UnitsCountUtils.countUnits(this.getMatchKeys().size(),passType.getMatchCount());
				}else{
					units++;	
				}				
				if (units > Constant.MAX_SINGLE_UNITS)
					throw new DataException("单式方案单倍注数不能大于" + Constant.MAX_SINGLE_UNITS + "注.");

				sb.append(formatLine).append(seq);
				lineIndex++;
			}
			sb.delete(sb.length() - seq.length(), sb.length());

			JclqSingleContent contentObj = new JclqSingleContent();
			List<String> matchKeysTrim = this.matchKeysTrim();
			contentObj.setMatchkeys(matchKeysTrim);
			contentObj.setContent(sb.toString());
			return new ContentBean(units, JSONObject.fromObject(contentObj).toString());
		}else{
			final PassType passType = passTypes.get(0);
			String singleContent = this.getSingleSchemeContent();
			String[] arr = singleContent.split("(\r\n|\n)+");
			
			StringBuilder sb = new StringBuilder();
			int units = 0;
			for (String line : arr) {
				if(line.indexOf("#")<0 && this.getMatchKeys().size()>passType.getMatchCount()){
					units += UnitsCountUtils.countUnits(this.getMatchKeys().size(),passType.getMatchCount());
				}else{
					units++;
				}
				if (units > Constant.MAX_SINGLE_UNITS)
					throw new DataException("单式方案单倍注数不能大于" + Constant.MAX_SINGLE_UNITS + "注.");

				sb.append(line).append(seq);
			}
			sb.delete(sb.length() - seq.length(), sb.length());
			JclqSingleContent contentObj = new JclqSingleContent();
			List<String> matchKeysTrim = this.matchKeysTrim();
			contentObj.setMatchkeys(matchKeysTrim);
			contentObj.setContent(sb.toString());
			return new ContentBean(units, JSONObject.fromObject(contentObj).toString());
		}		
	}
	
	private List<String> matchKeysTrim(){
		List<String> matchKeysTrim = getMatchKeys();
		for(int i=0;i<matchKeysTrim.size();i++){
			matchKeysTrim.set(i, matchKeysTrim.get(i).trim());
		}
		return matchKeysTrim;
	}
	
	/**
	 * 验证胆码合法性
	 * @param minPassType
	 * @param danList
	 * @param unDanList
	 * @throws DataException
	 */
	private void validateOfDan(PassType minPassType, final List<JclqMatchItem> danList, final List<JclqMatchItem> unDanList) throws DataException{
		if (danList.size() > minPassType.getMatchCount()) {
			throw new DataException("设置的胆码不能大于最小的过关场次.");
		}
		if (danMinHit == null || danMinHit <= 0)
			danMinHit = danList.size();
		else if (danMinHit > danList.size())
			throw new DataException("模糊设胆不正确.");

		if (danMinHit + unDanList.size() < minPassType.getMatchCount())
			throw new DataException("模糊设胆不正确.");

		if (danMaxHit == null || danMaxHit <= 0)
			danMaxHit = danList.size();
	}
	
	/**
	 * 获取选中场次的最大最小赔率
	 * @param items
	 * @return
	 */
	private Map<String,double[]> buildPrizeForecast(List<JclqMatchItem> items){
		/**方案预测最小最大*/
		Map<String,double[]> forcastMinMaxPrizeMap = Maps.newHashMap();
		double[] minMaxSp;
		for(JclqMatchItem item : items){
			minMaxSp = item.findSelectedMinMaxSp();
			
			double[] minMaxSpOfMap = forcastMinMaxPrizeMap.get(item.getMatchKey());
			if(minMaxSpOfMap==null){
				forcastMinMaxPrizeMap.put(item.getMatchKey(), minMaxSp);
			}else{
				if(minMaxSpOfMap[0]>minMaxSp[0]){
					minMaxSpOfMap[0] = minMaxSp[0];
				}
				minMaxSpOfMap[1] += minMaxSp[1];
			}			
		}
		return forcastMinMaxPrizeMap;
	}
	
	/**
	 * 最大最小奖金预测(单倍投)
	 * @param passTypes
	 * @param items
	 * @param danList
	 * @param unDanList
	 * @param danMinHit
	 * @param danMaxHit
	 * @return
	 */
	private double[] prizeForecastMinMax(final List<PassType> passTypes, final List<JclqMatchItem> items,final List<JclqMatchItem> danList,final List<JclqMatchItem> unDanList,final Integer danMinHit,final Integer danMaxHit){
		double[] minMaxPrize = new double[]{0,0};
		Map<String,double[]> forcastMinMaxPrizeMap = this.buildPrizeForecast(items);
		
		//计算预测最小奖金
		int needs = passTypes.get(0).getPassMatchs()[0];//取最小过关数
		JclqSchemeConverWork<JclqMatchItem> work = new JclqSchemeConverWork<JclqMatchItem>(needs, danList, unDanList, danMinHit, danMaxHit);
		List<List<JclqMatchItem>> combList = work.getResultList();		
		for(List<JclqMatchItem> subItems : combList){
			double prize = 2.0d;
			for(JclqMatchItem item : subItems){
				prize = forcastMinMaxPrizeMap.get(item.getMatchKey())[0] * prize;
			}
			if(minMaxPrize[0]==0 || minMaxPrize[0]>prize){
				minMaxPrize[0]=prize;
			}
		}
		
		//计算预测最大奖金
		for (PassType passType : this.passTypes) {
			for (final int needs1 : passType.getPassMatchs()) {
				work = new JclqSchemeConverWork<JclqMatchItem>(needs1, danList, unDanList, danMinHit, danMaxHit);
				combList = work.getResultList();
				for(List<JclqMatchItem> subItems : combList){
					double prize = 2.0d;
					for(JclqMatchItem item : subItems){
						prize = forcastMinMaxPrizeMap.get(item.getMatchKey())[1] * prize;
					}
					minMaxPrize[1]+=prize;
				}				
			}
		}
		
		return minMaxPrize;
	}
	
	// 单式上传格式匹配相关类================================================================

	interface LineResolve {
		String resolve(int lineIndex,String line) throws DataException;
		List<Integer> getMatchIds(int lineIndex,String line)throws DataException;
	}

	abstract class LineResolveImpl implements LineResolve {
		static final char place = '#';
		static final char itemSeq = ',';

		final PassType passType;
		final int codeStrLen;
		final String placeholder;
		final int len;

		private LineResolveImpl(PassType passType, int codeStrLen, String placeholder) {
			super();
			this.passType = passType;
			this.codeStrLen = codeStrLen;
			this.placeholder = placeholder;
			if(matchKeys!=null){
				len = matchKeys.size() * this.codeStrLen;
			}else{
				len = 0;
			}
			
		}
		
		@Override
		public List<Integer> getMatchIds(int lineIndex,String line)throws DataException{return null;};
	}

	
	class SimpleLineResolveImpl extends LineResolveImpl {
		private StringBuilder lineSb = new StringBuilder();

		private SimpleLineResolveImpl(PassType passType, int codeStrLen, String placeholder) {
			super(passType, codeStrLen, placeholder);
		}

		@Override
		public String resolve(int lineIndex,String line) throws DataException {
			line = line.trim().replaceAll("(\\,|-|\\s)", "");
			if(getMatchKeys().size()==passType.getMatchCount()){
				line = line.trim().replaceAll("(#)", "");
			}
			
			if (line.length() < len) {
				throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");
			}
			line = line.substring(0, len);
			int c = 0;
			lineSb.setLength(0);
			for (int i = 0; i < len; i += codeStrLen) {
				String temp = line.substring(i, i + codeStrLen);
				if (placeholder.equals(temp)) {
					lineSb.append(place).append(itemSeq);
					continue;
				} else {
					int index = codes.indexOf(temp);
					if (index < 0)
						throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");

					lineSb.append(index).append(itemSeq);
					c++;
				}
			}
			int placeIndex = lineSb.indexOf(String.valueOf(place));
			if (placeIndex>0 && c != passType.getMatchCount()){
				throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");
			}
			if(placeIndex<0 && c!=getMatchKeys().size()){
				throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");
			}

			lineSb.deleteCharAt(lineSb.length() - 1);
			return lineSb.toString();
		}		
		
	}

	class Com500wanLineResolveImpl extends LineResolveImpl {
		final Pattern pattern = Pattern.compile("\\s*(\\d{1,4})\\s*:\\s*\\[([^\\]]+)\\]\\s*");
		final List<String> codeList;
		private StringBuilder lineSb = new StringBuilder();
		private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		private Com500wanLineResolveImpl(PassType passType, int codeStrLen, String placeholder, List<String> codeList) {
			super(passType, codeStrLen, placeholder);
			this.codeList = codeList;
		}

		@Override
		public String resolve(int lineIndex,String line) throws DataException {
			lineSb.setLength(0);
			map.clear();

			line = line.trim();
			String[] arr = line.split("/");
			List<Integer> matchKeyList = getFormatMatchKeyList();
			for (String str : arr) {
				Matcher matcher = pattern.matcher(str);
				if (matcher.matches()) {
					Integer lineId = Integer.valueOf(matcher.group(1));
					String value = matcher.group(2).trim();

					if (map.keySet().contains(lineId))
						throw new DataException("第"+lineIndex+"行，"+lineId+":场次重复.");
					if (map.values().contains(value))
						throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");
					
					if (!matchKeyList.contains(lineId))
						throw new DataException("第"+lineIndex+"行，场次错误.");

					int index = codeList.indexOf(value);
					if (index < 0)
						throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");

					map.put(lineId, index);
				}
			}
			if (map.size() != passType.getMatchCount())
				throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");

			
			for (Integer lineId : matchKeyList) {
				if (map.containsKey(lineId))
					lineSb.append(map.get(lineId));
				else
					lineSb.append(place);

				lineSb.append(itemSeq);
			}
			lineSb.deleteCharAt(lineSb.length() - 1);
			return lineSb.toString();
		}
		
		@Override
		public List<Integer> getMatchIds(int lineIndex,String line)throws DataException{
			List<Integer> matchIds = Lists.newArrayList();
			lineSb.setLength(0);
			map.clear();
			
			line = line.trim();
			String[] arr = line.split("/");
			for (String str : arr) {
				Matcher matcher = pattern.matcher(str);
				if (matcher.matches()) {
					Integer lineId = Integer.valueOf(matcher.group(1));
					if (matchIds.contains(lineId)){
						throw new DataException("第"+lineIndex+"行，"+lineId+":场次重复.");
					}else{
						matchIds.add(lineId);
					}
				}
			}
			return matchIds;
		}
	}

	class ComOkoooLineResolveImpl extends LineResolveImpl {
		final Pattern pattern = Pattern.compile("\\s*(\\d{1,4})\\s*→\\s*([^\\s]+).*");
		final List<String> codeList;
		private StringBuilder lineSb = new StringBuilder();
		private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		private ComOkoooLineResolveImpl(PassType passType, int codeStrLen, String placeholder, List<String> codeList) {
			super(passType, codeStrLen, placeholder);
			this.codeList = codeList;
		}

		@Override
		public String resolve(int lineIndex,String line) throws DataException {
			lineSb.setLength(0);
			map.clear();
			
			line = line.trim();
			String[] arr = line.split(",");
			List<Integer> matchKeyList = getFormatMatchKeyList();
			
			for (String str : arr) {
				Matcher matcher = pattern.matcher(str);
				if (matcher.matches()) {
					Integer lineId = Integer.valueOf(matcher.group(1));
					String value = matcher.group(2).trim();

					if (map.keySet().contains(lineId))
						throw new DataException("第"+lineIndex+"行，"+lineId+":场次重复.");
					if (map.values().contains(value))
						throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");

					if (!matchKeyList.contains(lineId))
						throw new DataException("第"+lineIndex+"行，场次错误.");

					int index = codeList.indexOf(value);
					if (index < 0)
						throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");

					map.put(lineId, index);
				}
			}
			if (map.size() != passType.getMatchCount())
				throw new DataException("第"+lineIndex+"行，投注内容格式不正确！");

			for (Integer lineId : matchKeyList) {
				if (map.containsKey(lineId))
					lineSb.append(map.get(lineId));
				else
					lineSb.append(place);

				lineSb.append(itemSeq);
			}
			lineSb.deleteCharAt(lineSb.length() - 1);
			return lineSb.toString();
		}
		
		@Override
		public List<Integer> getMatchIds(int lineIndex,String line)throws DataException{
			List<Integer> matchIds = Lists.newArrayList();
			lineSb.setLength(0);
			map.clear();
			
			line = line.trim();
			String[] arr = line.split(",");
			for (String str : arr) {
				Matcher matcher = pattern.matcher(str);
				if (matcher.matches()) {
					Integer lineId = Integer.valueOf(matcher.group(1));
					if (matchIds.contains(lineId)){
						throw new DataException("第"+lineIndex+"行，"+lineId+":场次重复.");
					}else{
						matchIds.add(lineId);
					}
				}
			}
			return matchIds;
		}
	}

	// ================================================================
	/**
	 * 计算单式单倍注数
	 * 
	 * @return 单式单倍注数
	 * @throws DataException
	 */
	public int countSingleUnits() throws DataException {
		return buildSingleContentBean().getUnits();
	}	
	
	
	/**
	 * @return {@link #content}
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return {@link #items}
	 */
	public List<JclqMatchItem> getItems() {
		return items;
	}

	/**
	 * @param items the {@link #items} to set
	 */
	public void setItems(List<JclqMatchItem> items) {
		this.items = items;
	}

	/**
	 * @return {@link #passTypes}
	 */
	public List<PassType> getPassTypes() {
		return passTypes;
	}

	/**
	 * @param passTypes the {@link #passTypes} to set
	 */
	public void setPassTypes(List<PassType> passTypes) {
		this.passTypes = passTypes;
	}

	/**
	 * @return {@link #danMinHit}
	 */
	public Integer getDanMinHit() {
		return danMinHit;
	}

	/**
	 * @param danMinHit the {@link #danMinHit} to set
	 */
	public void setDanMinHit(Integer danMinHit) {
		this.danMinHit = danMinHit;
	}

	/**
	 * @return {@link #danMaxHit}
	 */
	public Integer getDanMaxHit() {
		return danMaxHit;
	}

	/**
	 * @param danMaxHit the {@link #danMaxHit} to set
	 */
	public void setDanMaxHit(Integer danMaxHit) {
		this.danMaxHit = danMaxHit;
	}

	/**
	 * @return the schemeType
	 */
	public SchemeType getSchemeType() {
		return schemeType;
	}

	/**
	 * @param schemeType the schemeType to set
	 */
	public void setSchemeType(SchemeType schemeType) {
		this.schemeType = schemeType;
	}
	public PassMode getPassMode() {
		return passMode;
	}
	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}
	
	public List<String> getMatchKeys() {		
		return matchKeys;
	}
	
	public void setMatchKeys(String matchKeys) {
		String[] matchKeyArr = matchKeys.trim().split(",");
		this.matchKeys = Arrays.asList(matchKeyArr);
	}
	
	/**
	 * 将赛程key转为上传的赛程ID：1001（周一001） 与matchKey 对应List
	 * @return
	 */
	private List<Integer> getFormatMatchKeyList(){
		List<Integer> matchKeyList = Lists.newArrayList();
		String[] matchKeyArr = null;
		int weekDay = 0;
		for(String matchKey : this.matchKeys){
			matchKeyArr = matchKey.split("-");
			weekDay = JclqUtil.getMatchDayOfWeek(Integer.valueOf(matchKeyArr[0].trim()));
			int matchKeyInt = StringUtil.toInt(weekDay+matchKeyArr[1]);
			matchKeyList.add(matchKeyInt);
		}
		return matchKeyList;
	}
	
	/**
	 * @param comOkoooCodeList the {@link #comOkoooCodeList} to set
	 */
	public void setComOkoooCodeList(List<String> comOkoooCodeList) {
		this.comOkoooCodeList = comOkoooCodeList;
	}

	/**
	 * @param com500wanCodeList the {@link #com500wanCodeList} to set
	 */
	public void setCom500wanCodeList(List<String> com500wanCodeList) {
		this.com500wanCodeList = com500wanCodeList;
	}
	
	
	public Double getBestMinPrize() {
		return bestMinPrize;
	}

	public void setBestMinPrize(Double bestMinPrize) {
		this.bestMinPrize = bestMinPrize;
	}

	public Double getBestMaxPrize() {
		return bestMaxPrize;
	}

	public void setBestMaxPrize(Double bestMaxPrize) {
		this.bestMaxPrize = bestMaxPrize;
	}

	public List<String> getSps() {
		return sps;
	}

	public void setSps(List<String> sps) {
		this.sps = sps;
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public List<Integer> getMultiples() {
		return multiples;
	}

	public void setMultiples(List<Integer> multiples) {
		this.multiples = multiples;
	}

	public List<String> getPlayTypes() {
		return playTypes;
	}

	public void setPlayTypes(List<String> playTypes) {
		this.playTypes = playTypes;
	}
	
	public void setOptimize(boolean isOptimize) {
		this.isOptimize = isOptimize;
	}

	/**
	 * 是否为奖金优化方案
	 * @return
	 */
	public boolean isOptimize() {
		return isOptimize;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
	
}
