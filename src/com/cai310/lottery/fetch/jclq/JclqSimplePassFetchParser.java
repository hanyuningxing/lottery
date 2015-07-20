package com.cai310.lottery.fetch.jclq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.fetch.jclq.local.Grabber;
import com.cai310.lottery.fetch.jclq.local.JclqMatchDto;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class JclqSimplePassFetchParser extends JclqAbstractFetchParser {

	public static final String REDUCED_VALUE_KEY = "REDUCED_VALUE";

//	@Override
//	protected JclqFetchResult parserHTML(String html, String charset) {
//		TableTag tag = getTableTag(html, charset);
//		if (tag == null) {
//			return null;
//		}
//
//		try {
//			return parser(tag);
//		} catch (ParseException e) {
//			this.logger.error("匹配抓取数据出错.", e);
//		}
//
//		return null;
//	}
	/**
	 * 抓取的json数据解析
	 * Add By Suixinhang
	 */
	@Override
	protected JclqFetchResult parserHTML(String html, String charset) {
		String sourceUrl = getSourceUrl();
		if (StringUtils.isBlank(sourceUrl)) {
			this.logger.error("抓取的目标网址为空.");
			return null;
		}
		try {
			Grabber g = new Grabber();
			String content = g.grabberJson(sourceUrl);
			
			if (StringUtils.isBlank(content)) {
				this.logger.error("抓取回来的内容为空，目标网址：[{}].", sourceUrl);
				return null;
			}
			List<JclqMatchDto> resultList = getMatchDtos(content);
			SortedMap<String, Map<String, Double>> rateData = Maps.newTreeMap();
			SortedMap<String, JclqMatch> matchMap = Maps.newTreeMap();
			
			for (JclqMatchDto JclqMatchDTO : resultList) {
				JclqMatch matchDTO = getMatchDTO(JclqMatchDTO);
				Map<String, Double> itemMap = getItemMap(JclqMatchDTO);
				String key = JclqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
				matchMap.put(key, matchDTO);
				rateData.put(key, itemMap);
			}
			return new JclqFetchResult(getPlayType(), getPassMode(), matchMap, rateData);
		} catch (ParseException e) {
			logger.error("数据抓取错误，地址："+sourceUrl);
			return null;
		}
		
	}
	
	/**
	 * 抓取的json数据rate解析
	 * Add By Suixinhang
	 */
	public abstract Map<String, Double> getItemMap(JclqMatchDto matchDto);
	/**
	 * 抓取的json数据JclqMatch解析
	 * Add By Suixinhang
	 */
	protected JclqMatch getMatchDTO(JclqMatchDto matchDto) throws ParseException{
		JclqMatch match = new JclqMatch();
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
		if(StringUtils.isNotBlank(matchDto.getSingleHandicap())){
			if(matchDto.getSingleHandicap().startsWith("+")){
				match.setSingleHandicap(Float.valueOf(matchDto.getSingleHandicap().substring(1)));
			} else {
				match.setSingleHandicap(Float.valueOf(matchDto.getSingleHandicap()));
			}
		}
		if(StringUtils.isNotBlank(matchDto.getSingleTotalScore())){
			if(matchDto.getSingleTotalScore().startsWith("+")){
				match.setSingleTotalScore(Float.valueOf(matchDto.getSingleTotalScore().substring(1)));
			} else {
				match.setSingleTotalScore(Float.valueOf(matchDto.getSingleTotalScore()));
			}
		}
		match.setOpenFlag(matchDto.getOpenFlag());
		return match;
	}
	/**
	 * 抓取的json数据JclqMatchDto解析
	 * Add By Suixinhang
	 */
	protected List<JclqMatchDto> getMatchDtos(String html){
		html=html.split("\\(")[1].split("\\)")[0];
		Map obj =  JsonUtil.getMap4Json(html);
		String temp = String.valueOf(obj.get("data"));
		Map<String,String> map = JsonUtil.getMap4Json(temp);
		Map<String,String> matchMap;
		Map<Integer,JclqMatchDto> resultMap = Maps.newHashMap();
		Iterator it = map.entrySet().iterator();
		Map<String,String> oddsMap,hhadMap,hadMap,ttgMap,hafuMap,crsMap;
		String mnl,wnm,hdc,hilo,odds;
		StringBuilder sb;
		JclqMatchDto dto;
		String mapValue;
		List<JclqMatchDto> resultList = Lists.newArrayList();
		for(Map.Entry<String, String> entry : map.entrySet()){ 
			sb = new StringBuilder();
			mapValue= String.valueOf(entry.getValue()); 
			matchMap = JsonUtil.getMap4Json(mapValue);
			dto = new JclqMatchDto();
			dto.setGameColor(matchMap.get("l_background_color"));
			dto.setGameName(matchMap.get("l_cn"));
			dto.setHomeTeamName(matchMap.get("h_cn"));
			dto.setGuestTeamName(matchMap.get("a_cn"));
			dto.setMatchTime(matchMap.get("date")+" "+matchMap.get("time"));
			dto.setNum(matchMap.get("num"));
			dto.setMatchDate(matchMap.get("b_date"));
			
			//是否支持单关
			int flag = JclqUtil.getOpenFlag(this.getPlayType(), this.getPassMode());
			Integer openFlag = dto.getOpenFlag();
			if (openFlag == null){
				openFlag = 0;
			}
			openFlag |= flag;
			
			int singleFlag = JclqUtil.getOpenFlag(this.getPlayType(), PassMode.SINGLE);
			//胜负
			if(matchMap.get("mnl")!=null){
			    hhadMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("mnl")));
				sb.append(hhadMap.get("a")+",").append(hhadMap.get("h"));
				if( hhadMap.get("single") != null && "1".equals(String.valueOf(hhadMap.get("single"))) ){
					openFlag |= singleFlag;
				}
			} 
			//让分胜负
			else if(matchMap.get("hdc")!=null){
				hadMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("hdc")));
				sb.append(hadMap.get("a")+",").append(hadMap.get("h"));
				dto.setSingleHandicap(hadMap.get("fixedodds"));
				if( hadMap.get("single") != null && "1".equals(String.valueOf(hadMap.get("single"))) ){
					openFlag |= singleFlag;
				}
			} 
			//胜分差
			else if(matchMap.get("wnm")!=null){
				ttgMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("wnm")));
				sb.append(ttgMap.get("l1")+",").append(ttgMap.get("l2")+",").append(ttgMap.get("l3")+",").append(ttgMap.get("l4")+",").
							append(ttgMap.get("l5")+",").append(ttgMap.get("l6")+",").append(ttgMap.get("w1")+",").append(ttgMap.get("w2")+",")
							.append(ttgMap.get("w3")+",").append(ttgMap.get("w4")+",").append(ttgMap.get("w5")+",").append(ttgMap.get("w6"));
				if( ttgMap.get("single") != null && "1".equals(String.valueOf(ttgMap.get("single"))) ){
					openFlag |= singleFlag;
				}
			} 
			//大小分
			else if(matchMap.get("hilo")!=null){
				hafuMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("hilo")));
				sb.append(hafuMap.get("h")+",").append(hafuMap.get("l"));
				dto.setSingleTotalScore(hafuMap.get("fixedodds"));//总分
				if( hafuMap.get("single") != null && "1".equals(String.valueOf(hafuMap.get("single"))) ){
					openFlag |= singleFlag;
				}
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
			this.logger.error("解析HTML出错.", e);
			return null;
		}

		for (int i = 0; i <= nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
				if ("tbl".equals(tag.getAttribute("class"))) {
					Node node = tag.getParent();
					if (node != null && node instanceof Div) {
						Div div = (Div) node;
						if ("box-tbl-a".equals(div.getAttribute("class"))) {
							return tag;
						}
					}
				}
			}
		}

		return null;
	}

	protected JclqFetchResult parser(TableTag tag) throws ParseException {
		SortedMap<String, Map<String, Double>> rateData = Maps.newTreeMap();
		SortedMap<String, JclqMatch> matchMap = Maps.newTreeMap();

		TableRow[] rows = tag.getRows();
		for (TableRow row : rows) {
			JclqMatch matchDTO = getMatchDTO(row);
			if (matchDTO != null) {
				Map<String, Double> itemMap = getItemMap(row);
				if (itemMap != null) {
					String key = JclqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
					matchMap.put(key, matchDTO);
					rateData.put(key, itemMap);
				}
			}
		}
		return new JclqFetchResult(getPlayType(), getPassMode(), matchMap, rateData);
	}

	protected Map<String, Double> getItemMap(TableRow row) {
		TableColumn[] columns = row.getColumns();
		if (columns != null && columns.length > 0) {
			int columnIndex = getRateColumnStartIndex();
			Map<String, Double> itemMap = Maps.newHashMap();
			for (Item item : getPlayType().getAllItems()) {
				String rateStr = columns[columnIndex].toPlainTextString();
				Double rate = (StringUtils.isNotBlank(rateStr) && rateStr.matches(DOUBLE_REGEX)) ? Double
						.valueOf(rateStr) : 0;
				itemMap.put(item.toString(), rate);
				columnIndex++;
			}
			Matcher teamMatcher = TEAM_PATTERN.matcher(columns[2].toPlainTextString());
			if (teamMatcher.find()) {
				String reducedValueStr = teamMatcher.group(3);
				handleReducedValue(itemMap, reducedValueStr);
			}
			return itemMap;
		}
		return null;
	}

	protected int getRateColumnStartIndex() {
		return 4;
	}

	protected JclqMatch getMatchDTO(TableRow row) throws ParseException {
		final Calendar now = Calendar.getInstance();

		Calendar c = Calendar.getInstance();
		TableColumn[] columns = row.getColumns();
		if (columns != null && columns.length > 0) {
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
					String guest = teamMatcher.group(1);
					String home = teamMatcher.group(2);
					String singleHandicapeStr =singleHandicapeStr(teamMatcher.group(3));
					String singleTotalScore=singleTotalScore(teamMatcher.group(3));
					String matchTimeStr = columns[3].toPlainTextString();
					Date time = DateUtils.parseDate(matchTimeStr, new String[] { "yy-MM-dd HH:mm" });
					c.setTime(time);
					if (now.getTimeInMillis() - c.getTimeInMillis() > CHECK_DAY) {
						c.add(Calendar.YEAR, 1);
					}
					Date matchTime = c.getTime();

					Integer matchDate = JclqUtil.getMatchDate(dayOfWeekStr, matchTime);

					JclqMatch matchDTO = new JclqMatch();
					if(singleHandicapeStr!=null){
						matchDTO.setSingleHandicap(Float.valueOf(singleHandicapeStr));
					}
					if(singleTotalScore!=null){
						matchDTO.setSingleTotalScore(Float.valueOf(singleTotalScore));
					}
					matchDTO.setGameColor(bgcolor);
					matchDTO.setGameName(gameName);
					matchDTO.setHomeTeamName(home);
					matchDTO.setGuestTeamName(guest);
					matchDTO.setMatchTime(matchTime);
					matchDTO.setLineId(Integer.valueOf(lineIdOfDayStr));
					matchDTO.setMatchDate(matchDate);

					return matchDTO;
				}
			}
		}
		return null;
	}

	protected void handleReducedValue(Map<String, Double> itemMap, String reducedValueStr) {

	}
	
	protected String singleHandicapeStr(String value){
		return null;
	}
	protected String singleTotalScore(String value){
		return null;
	}
}
