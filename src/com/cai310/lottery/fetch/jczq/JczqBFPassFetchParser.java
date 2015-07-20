package com.cai310.lottery.fetch.jczq;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.exception.FetchParseException;
import com.cai310.lottery.fetch.jczq.local.JczqMatchDto;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class JczqBFPassFetchParser extends JczqSimplePassFetchParser {
	protected static final Pattern RATE_PATTERN = Pattern
			.compile("<td[^>]*>.*(?:[0-5]:[0-5]|[胜平负]?其[他它])\\s*<br\\s*/?>\\s*([^<\\s]+)\\s*</td>");
	protected static final Pattern MATCH_PATTERN = Pattern
			.compile("周(一|二|三|四|五|六|日)(\\d{3})\\s*\\|\\s*([^\\|\\s]+)\\s*\\|\\s*([^\\|]+)\\s*VS\\s*([^\\|]+)\\s*\\|\\s*[^\\d]*(\\d{2}-\\d{2}\\s\\d{2}:\\d{2})");
 
	@Override
	public String getSourceUrl() {
//		return "http://info.sporttery.cn/lotterygame/crs_list.php";
//		return "http://info.sporttery.cn/football/cal_crs.htm";
		return "http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&poolcode[]=crs";
	}

//	@Override
//	protected JczqFetchResult parserHTML(String html, String charset) {
//		Div tag = getDiv(html, charset);
//		if (tag == null) {
//			return null;
//		}
//
//		try {
//			return parser(tag);
//		} catch (FetchParseException e) {
//			this.logger.warn("匹配抓取数据出错.", e);
//		} catch (ParseException e) {
//			this.logger.warn("匹配抓取数据出错.", e);
//		}
//
//		return null;
//	}

	protected JczqFetchResult parser(Div tag) throws FetchParseException, ParseException {
		SortedMap<String, Map<String, Double>> rateData = Maps.newTreeMap();
		SortedMap<String, JczqMatch> matchMap = Maps.newTreeMap();

		List<Div> titleList = Lists.newArrayList();
		List<Span> contentList = Lists.newArrayList();
		for (int i = 0, clildCount = tag.getChildCount(); i < clildCount; i++) {
			Node node = tag.childAt(i);
			if (node instanceof Div) {
				titleList.add((Div) node);
			} else if (node instanceof Span) {
				contentList.add((Span) node);
			}
		}
		if (titleList.isEmpty() || titleList.size() != contentList.size()) {
			throw new FetchParseException("解析抓取失败，可能目标网页已改版。");
		}
		for (int i = 0, len = titleList.size(); i < len; i++) {
			try{
				Div title = titleList.get(i);
				Span content = contentList.get(i);
				JczqMatch matchDTO = getMatchDTO(title);
				Map<String, Double> itemMap = getItemMap(content);
				String key = JczqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
				matchMap.put(key, matchDTO);
				rateData.put(key, itemMap);
			}catch(Exception e){
				this.logger.warn("解析数据{"+contentList.get(i)+"}出错.", e);
				continue;
			}
		}
		return new JczqFetchResult(getPlayType(), getPassMode(), matchMap, rateData);
	}

	private Map<String, Double> getItemMap(Span content) throws FetchParseException {
		TableTag table = null;
		for (int i = 0, len = content.getChildCount(); i < len; i++) {
			Node node = content.getChild(i);
			if (node instanceof TableTag) {
				table = (TableTag) node;
				break;
			}
		}
		if (table == null) {
			throw new FetchParseException("解析抓取失败，可能目标网页已改版。");
		}
		ItemBF[][] arr = { ItemBF.WINS, ItemBF.DRAWS, ItemBF.LOSES };
		Map<String, Double> itemMap = Maps.newLinkedHashMap();
		for (int i = 0; i < arr.length; i++) {
			ItemBF[] items = arr[i];
			TableColumn[] columns = table.getRow(i).getColumns();
			int k = 0;
			for (int j = 0; j < columns.length; j++) {
				String html = columns[j].toHtml();
				Matcher m = RATE_PATTERN.matcher(html);
				if (m.find()) {
					String rateStr = m.group(1);
					Double rate = (StringUtils.isNotBlank(rateStr) && rateStr.matches(DOUBLE_REGEX)) ? Double
							.valueOf(rateStr) : 0;
					itemMap.put(items[k].toString(), rate);
					k++;
				}
			}
		}
		if (itemMap.size() != ItemBF.values().length)
			throw new FetchParseException("解析抓取失败，可能目标网页已改版。");
		return itemMap;
	}

	private JczqMatch getMatchDTO(Div title) throws ParseException {
		String text = title.toPlainTextString().trim();
		Matcher m = MATCH_PATTERN.matcher(text);
		if (m.find()) {
			String dayOfWeekStr = m.group(1);
			String lineIdOfDayStr = m.group(2);
			String gameName = m.group(3);
			String homeTeamName = m.group(4);
			String guestTeamName = m.group(5);
			String matchTimeStr = m.group(6);

			final Calendar now = Calendar.getInstance();
			final int curYear = Calendar.getInstance().get(Calendar.YEAR);
			Date time = DateUtils.parseDate(String.format("%s-%s", curYear, matchTimeStr),
					new String[] { "yyyy-MM-dd HH:mm" });
			Calendar c = Calendar.getInstance();
			c.setTime(time);
			if (now.getTimeInMillis() - c.getTimeInMillis() > CHECK_DAY) {
				c.add(Calendar.YEAR, 1);
			}
			Date matchTime = c.getTime();

			Integer matchDate = JczqUtil.getMatchDate(dayOfWeekStr, matchTime);

			JczqMatch matchDTO = new JczqMatch();
			matchDTO.setGameName(gameName);
			matchDTO.setHomeTeamName(homeTeamName);
			matchDTO.setGuestTeamName(guestTeamName);
			matchDTO.setMatchTime(matchTime);
			matchDTO.setLineId(Integer.valueOf(lineIdOfDayStr));
			matchDTO.setMatchDate(matchDate);
			return matchDTO;
		}
		return null;
	}
	protected Div getDiv(String html, String charset) {
		Parser parser = Parser.createParser(html, charset);
		NodeFilter tableFilter = new NodeClassFilter(Div.class);
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
			if (nodeList.elementAt(i) instanceof Div) {
				Div tag = (Div) nodeList.elementAt(i);
				if ("mainBody".equals(tag.getAttribute("class"))) {
					return tag;
				}
			}
		}

		return null;
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.BF;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.PASS;
	}
	/**
	 * 抓取的json数据rate解析
	 * Add By Suixinhang
	 */
	@Override
	public Map<String, Double> getItemMap(JczqMatchDto matchDto) {
		Map<String, Double> itemMap = Maps.newHashMap();
		String odds = matchDto.getOdds();
		if(StringUtils.isBlank(odds)){
			for (Item item : getPlayType().getAllItems()) {
				itemMap.put(item.toString(), 0.0);
			}
			return itemMap;
		}
		String[] odd = odds.split(",");
		if(odd.length == 0){
			for (Item item : getPlayType().getAllItems()) {
				itemMap.put(item.toString(), 0.0);
			}
			return itemMap;
		}
		int i = 0;
		for (Item item : getPlayType().getAllItems()) {
			String rateStr = odd[i];
			Double rate = (StringUtils.isNotBlank(rateStr) && rateStr.matches(DOUBLE_REGEX)) ? Double.valueOf(rateStr)
					: 0;
			itemMap.put(item.toString(), rate);
			i++;
		}
		return itemMap;
	}

}
