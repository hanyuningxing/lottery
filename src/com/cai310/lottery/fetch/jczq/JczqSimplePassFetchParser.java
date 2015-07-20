package com.cai310.lottery.fetch.jczq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.fetch.jczq.local.Grabber;
import com.cai310.lottery.fetch.jczq.local.JczqMatchDto;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class JczqSimplePassFetchParser extends JczqAbstractFetchParser {

//	@Override
//	protected JczqFetchResult parserHTML(String html, String charset) {
//		TableTag tag = getTableTag(html, charset);
//		if (tag == null) {
//			return null;
//		}
//
//		try {
//			return parser(tag);
//		} catch (ParseException e) {
//			this.logger.warn("匹配抓取数据出错.", e);
//		}
//
//		return null;
//	}


	/**
	 * 抓取的json数据解析
	 * Add By Suixinhang
	 */
	@Override
	protected JczqFetchResult parserHTML(String html, String charset) {
		String sourceUrl = getSourceUrl();
		if (StringUtils.isBlank(sourceUrl)) {
			this.logger.warn("抓取的目标网址为空.");
			return null;
		}
		try {
			Grabber g = new Grabber();
			String content = g.grabberJson(sourceUrl);
			
			if (StringUtils.isBlank(content)) {
				this.logger.warn("抓取回来的内容为空，目标网址：[{}].", sourceUrl);
				return null;
			}
			List<JczqMatchDto> resultList = getMatchDtos(content);
			SortedMap<String, Map<String, Double>> rateData = Maps.newTreeMap();
			SortedMap<String, JczqMatch> matchMap = Maps.newTreeMap();
			
			for (JczqMatchDto JczqMatchDTO : resultList) {
				JczqMatch matchDTO = getMatchDTO(JczqMatchDTO);
				Map<String, Double> itemMap = getItemMap(JczqMatchDTO);
				String key = JczqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
				matchMap.put(key, matchDTO);
				rateData.put(key, itemMap);
			}
			return new JczqFetchResult(getPlayType(), getPassMode(), matchMap, rateData);
		} catch (ParseException e) {
			logger.warn("数据抓取错误，地址："+sourceUrl);
			return null;
		}
		
	}

	/**
	 * 抓取的json数据rate解析
	 * Add By Suixinhang
	 */
	public abstract Map<String, Double> getItemMap(JczqMatchDto matchDto);
	/**
	 * 抓取的json数据JczqMatch解析
	 * Add By Suixinhang
	 */
	protected JczqMatch getMatchDTO(JczqMatchDto matchDto) throws ParseException{
		JczqMatch match = new JczqMatch();
		match.setGameColor(matchDto.getGameColor());
		match.setGameName(matchDto.getGameName());
		match.setHomeTeamName(matchDto.getHomeTeamName());
		match.setGuestTeamName(matchDto.getGuestTeamName());
		SimpleDateFormat SDF_STANDARD = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		match.setMatchTime(SDF_STANDARD.parse(matchDto.getMatchTime()));
		match.setLineId(Integer.valueOf(matchDto.getNum().substring(2, 5)));
		SimpleDateFormat SDF_STANDARD_DATE = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date matchDate = SDF_STANDARD_DATE.parse(matchDto.getMatchDate());
		match.setMatchDate(Integer.parseInt(DateUtil.getDate(matchDate)));
		if(StringUtils.isNotBlank(matchDto.getHandicap())){
			if(matchDto.getHandicap().startsWith("+")){
				match.setHandicap(Byte.valueOf(matchDto.getHandicap().substring(1, 2)));
			} else {
				match.setHandicap(Byte.valueOf(matchDto.getHandicap()));
			}
		}
		match.setOpenFlag(matchDto.getOpenFlag());
		return match;
	}
	/**
	 * 抓取的json数据JczqMatchDto解析
	 * Add By Suixinhang
	 */
	protected List<JczqMatchDto> getMatchDtos(String html){
		Map obj =  JsonUtil.getMap4Json(html);
		String temp = String.valueOf(obj.get("data"));
		Map<String,String> map = JsonUtil.getMap4Json(temp);
		Map<String,String> matchMap;
		Map<Integer,JczqMatchDto> resultMap = Maps.newHashMap();
		Iterator it = map.entrySet().iterator();
		Map<String,String> oddsMap,hhadMap,hadMap,ttgMap,hafuMap,crsMap;
		String hhad,had,ttg,hafu,crs,odds;
		StringBuilder sb;
		JczqMatchDto dto;
		String mapValue;
		List<JczqMatchDto> resultList = Lists.newArrayList();
		for(Map.Entry<String, String> entry : map.entrySet()){ 
			sb = new StringBuilder();
			mapValue= String.valueOf(entry.getValue()); 
			matchMap = JsonUtil.getMap4Json(mapValue);
			dto = new JczqMatchDto();
			dto.setGameColor(matchMap.get("l_background_color"));
			dto.setGameName(matchMap.get("l_cn"));
			dto.setHomeTeamName(matchMap.get("h_cn"));
			dto.setGuestTeamName(matchMap.get("a_cn"));
			dto.setMatchTime(matchMap.get("date")+" "+matchMap.get("time"));
			dto.setNum(matchMap.get("num"));
			dto.setMatchDate(matchMap.get("b_date"));
			
			//是否支持单关
			int flag = JczqUtil.getOpenFlag(this.getPlayType(), this.getPassMode());
			Integer openFlag = dto.getOpenFlag();
			if (openFlag == null){
				openFlag = 0;
			}
			openFlag |= flag;
			
			int singleFlag = JczqUtil.getOpenFlag(this.getPlayType(), PassMode.SINGLE);
			
			if(matchMap.get("hhad")!=null){
			    hhadMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("hhad")));
				sb.append(hhadMap.get("a")+",").append(hhadMap.get("d")+",").append(hhadMap.get("h"));
				dto.setHandicap(hhadMap.get("fixedodds"));
				if( hhadMap.get("single") != null && "1".equals(String.valueOf(hhadMap.get("single"))) ){
					openFlag |= singleFlag;
				}
			} 
			else if(matchMap.get("had")!=null){
				hadMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("had")));
				sb.append(hadMap.get("a")+",").append(hadMap.get("d")+",").append(hadMap.get("h"));
				if( hadMap.get("single") != null && "1".equals(String.valueOf(hadMap.get("single"))) ){
					openFlag |= singleFlag;
				}
			} 
			else if(matchMap.get("ttg")!=null){
				ttgMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("ttg")));
				sb.append(ttgMap.get("s0")+",").append(ttgMap.get("s1")+",").append(ttgMap.get("s2")+",").append(ttgMap.get("s3")+",").
							append(ttgMap.get("s4")+",").append(ttgMap.get("s5")+",").append(ttgMap.get("s6")+",").append(ttgMap.get("s7"));
				if( ttgMap.get("single") != null && "1".equals(String.valueOf(ttgMap.get("single"))) ){
					openFlag |= singleFlag;
				}
			} 
			else if(matchMap.get("hafu")!=null){
				hafuMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("hafu")));
				sb.append(hafuMap.get("aa")+",").append(hafuMap.get("ad")+",").append(hafuMap.get("ah")+",").
				append(hafuMap.get("da")+",").append(hafuMap.get("dd")+",").append(hafuMap.get("dh")+",").
				append(hafuMap.get("ha")+",").append(hafuMap.get("hd")+",").append(hafuMap.get("hh"));
				if( hafuMap.get("single") != null && "1".equals(String.valueOf(hafuMap.get("single"))) ){
					openFlag |= singleFlag;
				}
			} 
			else if(matchMap.get("crs")!=null){
				crsMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("crs")));
//			sb.append("|"+crsMap.get("-1-a")+",").append(crsMap.get("-1-d")+",").append(crsMap.get("-1-h")+",").
//						append(crsMap.get("0000")+",").append(crsMap.get("0001")+",").append(crsMap.get("0002")+",").append(crsMap.get("0003")+",").
//						append(crsMap.get("0004")+",").append(crsMap.get("0005")+",").
//						append(crsMap.get("0100")+",").append(crsMap.get("0101")+",").append(crsMap.get("0102")+",").append(crsMap.get("0103")+",").
//						append(crsMap.get("0104")+",").append(crsMap.get("0105")+",").
//						append(crsMap.get("0200")+",").append(crsMap.get("0201")+",").append(crsMap.get("0202")+",").append(crsMap.get("0203")+",").
//						append(crsMap.get("0204")+",").append(crsMap.get("0205")+",").
//						append(crsMap.get("0300")+",").append(crsMap.get("0301")+",").append(crsMap.get("0302")+",").append(crsMap.get("0303")+",").
//						append(crsMap.get("0400")+",").append(crsMap.get("0401")+",").append(crsMap.get("0402")+",").
//						append(crsMap.get("0500")+",").append(crsMap.get("0501")+",").append(crsMap.get("0502"));
			
			if( crsMap.get("single") != null && "1".equals(String.valueOf(crsMap.get("single"))) ){
				openFlag |= singleFlag;
			}
			sb.append(crsMap.get("0100")+",").append(crsMap.get("0200")+",").append(crsMap.get("0201")+",").
						append(crsMap.get("0300")+",").append(crsMap.get("0301")+",").append(crsMap.get("0302")+",").
						append(crsMap.get("0400")+",").append(crsMap.get("0401")+",").append(crsMap.get("0402")+",").
						append(crsMap.get("0500")+",").append(crsMap.get("0501")+",").append(crsMap.get("0502")+",").
						append(crsMap.get("-1-h")+",").
						append(crsMap.get("0000")+",").append(crsMap.get("0101")+",").append(crsMap.get("0202")+",").append(crsMap.get("0303")+",").
						append(crsMap.get("-1-d")+",").
						append(crsMap.get("0001")+",").append(crsMap.get("0002")+",").append(crsMap.get("0102")+",").
						append(crsMap.get("0003")+",").append(crsMap.get("0103")+",").append(crsMap.get("0203")+",").
						append(crsMap.get("0004")+",").append(crsMap.get("0104")+",").append(crsMap.get("0204")+",").
						append(crsMap.get("0005")+",").append(crsMap.get("0105")+",").append(crsMap.get("0205")+",").
						append(crsMap.get("-1-a"));
			}
			dto.setOpenFlag(openFlag);
			dto.setOdds(sb.toString());
			//resultMap.put(Integer.valueOf(matchMap.get("id")),dto);
			resultList.add(dto);
		}
		return resultList;
	}
	
	protected TableTag getTableTag(String html, String charset) {
		Parser parser = Parser.createParser(html, charset);
		NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			this.logger.warn("解析HTML出错.", e);
			return null;
		}

		for (int i = 0; i <= nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
				if ("tabContent".equals(tag.getAttribute("class"))) {
					return tag;
				}
			}
		}

		return null;
	}

	protected JczqFetchResult parser(TableTag tag) throws ParseException {
		SortedMap<String, Map<String, Double>> rateData = Maps.newTreeMap();
		SortedMap<String, JczqMatch> matchMap = Maps.newTreeMap();

		TableRow[] rows = tag.getRows();
		for (TableRow row : rows) {
			JczqMatch matchDTO = getMatchDTO(row);
			Map<String, Double> itemMap = getItemMap(row);
			String key = JczqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
			matchMap.put(key, matchDTO);
			rateData.put(key, itemMap);
		}
		return new JczqFetchResult(getPlayType(), getPassMode(), matchMap, rateData);
	}

	protected Map<String, Double> getItemMap(TableRow row) {
		TableColumn[] columns = row.getColumns();

		int columnIndex = getRateColumnStartIndex();
		Map<String, Double> itemMap = Maps.newHashMap();
		for (Item item : getPlayType().getAllItems()) {
			String rateStr = columns[columnIndex].toPlainTextString();
			Double rate = (StringUtils.isNotBlank(rateStr) && rateStr.matches(DOUBLE_REGEX)) ? Double.valueOf(rateStr)
					: 0;
			itemMap.put(item.toString(), rate);
			columnIndex++;
		}
		return itemMap;
	}

	protected int getRateColumnStartIndex() {
		return 4;
	}

	protected JczqMatch getMatchDTO(TableRow row) throws ParseException {
		final Calendar now = Calendar.getInstance();
		final int curYear = Calendar.getInstance().get(Calendar.YEAR);

		Calendar c = Calendar.getInstance();
		TableColumn[] columns = row.getColumns();
		Matcher matchIdMatcher = MATCH_ID_PATTERN.matcher(columns[0].toPlainTextString());
		if (matchIdMatcher.find()) {
			String dayOfWeekStr = matchIdMatcher.group(1);
			String lineIdOfDayStr = matchIdMatcher.group(2);

			String bgcolor = columns[1].getAttribute("bgcolor");
			if (StringUtils.isBlank(bgcolor)) {
				bgcolor = columns[1].getAttribute("bgColor");
			}
			String gameName = columns[1].toPlainTextString().trim();

			Matcher teamMatcher = TEAM_PATTERN.matcher(columns[2].toPlainTextString());
			if (teamMatcher.find()) {
				String home = teamMatcher.group(1);
				String handicapStr = teamMatcher.group(2);
				Byte handicap = (StringUtils.isNotBlank(handicapStr)) ? Byte.valueOf(handicapStr.replaceAll("\\+", ""))
						: 0;
				String guest = teamMatcher.group(3);

				String matchTimeStr = columns[3].toPlainTextString();
				Date time = DateUtils.parseDate(String.format("%s-%s", curYear, matchTimeStr),
						new String[] { "yyyy-MM-dd HH:mm" });
				c.setTime(time);
				if (now.getTimeInMillis() - c.getTimeInMillis() > CHECK_DAY) {
					c.add(Calendar.YEAR, 1);
				}
				Date matchTime = c.getTime();

				Integer matchDate = JczqUtil.getMatchDate(dayOfWeekStr, matchTime);

				JczqMatch matchDTO = new JczqMatch();
				matchDTO.setGameColor(bgcolor);
				matchDTO.setGameName(gameName);
				matchDTO.setHomeTeamName(home);
				matchDTO.setGuestTeamName(guest);
				matchDTO.setMatchTime(matchTime);
				matchDTO.setLineId(Integer.valueOf(lineIdOfDayStr));
				matchDTO.setMatchDate(matchDate);
				matchDTO.setHandicap(handicap);
				return matchDTO;
			}
		}
		return null;
	}
}
