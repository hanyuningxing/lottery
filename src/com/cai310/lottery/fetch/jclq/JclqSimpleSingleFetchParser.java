package com.cai310.lottery.fetch.jclq;

import java.text.ParseException;
import java.util.Date;
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
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.google.common.collect.Maps;

public abstract class JclqSimpleSingleFetchParser extends JclqAbstractFetchParser {

	@Override
	protected JclqFetchResult parserHTML(String html, String charset) {
		TableTag tag = getTableTag(html, charset);
		if (tag == null) {
			return null;
		}

		try {
			return parser(tag);
		} catch (ParseException e) {
			this.logger.warn("匹配抓取数据出错.", e);
		}

		return null;
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
				if ("tbl".equals(tag.getAttribute("class"))) {
					Node node = tag.getParent();
					if (node != null && node instanceof Div) {
						Div div = (Div) node;
						if ("box-tbl".equals(div.getAttribute("class"))) {
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
			if (row.getColumnCount() > 5) {
				JclqMatch matchDTO = getMatchDTO(row);
				Map<String, Double> itemMap = getItemMap(row);
				String key = JclqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
				matchMap.put(key, matchDTO);
				rateData.put(key, itemMap);
			}
		}
		return new JclqFetchResult(getPlayType(), getPassMode(), matchMap, rateData);
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

	protected JclqMatch getMatchDTO(TableRow row) throws ParseException {
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
				String guest = teamMatcher.group(1);
				String home = teamMatcher.group(2);
				String reducedValueStr = teamMatcher.group(3);

				String matchTimeStr = columns[3].toPlainTextString();
				Date matchTime = DateUtils.parseDate(matchTimeStr, new String[] { "yy-MM-dd HH:mm" });

				Integer matchDate = JclqUtil.getMatchDate(dayOfWeekStr, matchTime);

				JclqMatch matchDTO = new JclqMatch();
				matchDTO.setGameColor(bgcolor);
				matchDTO.setGameName(gameName);
				matchDTO.setHomeTeamName(home);
				matchDTO.setGuestTeamName(guest);
				matchDTO.setMatchTime(matchTime);
				matchDTO.setLineId(Integer.valueOf(lineIdOfDayStr));
				matchDTO.setMatchDate(matchDate);

				handleReducedValue(matchDTO, reducedValueStr);

				return matchDTO;
			}
		}
		return null;
	}

	protected void handleReducedValue(JclqMatch matchDTO, String reducedValueStr) {
	}
}
