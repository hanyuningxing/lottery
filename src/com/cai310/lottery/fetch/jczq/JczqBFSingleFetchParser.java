package com.cai310.lottery.fetch.jczq;

import java.text.ParseException;
import java.util.Date;
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
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.exception.FetchParseException;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.google.common.collect.Maps;

public class JczqBFSingleFetchParser extends JczqAbstractFetchParser {
	protected static final Pattern RATE_PATTERN = Pattern
			.compile("<td[^>]*>.*<label[^>]*>\\s*(?:<span[^>]*>)?(?:[0-5]:[0-5]|[胜平负]?其[他它])(?:</span>)?\\s*</label>\\s*(?:<span[^>]*>\\s*)?([^<\\s]+)(?:\\s*</span>)?\\s*.*</td>");
	protected static final Pattern MATCH_PATTERN = Pattern
			.compile("周(一|二|三|四|五|六|日)(\\d{3})[^\\|]*\\|\\s*([^\\|\\s]+)\\s*\\|\\s*([^\\|\\s]+)\\s*VS\\s*([^\\|\\s]+)\\s*\\|\\s*[^\\d]*(\\d{2}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2})");


	@Override
	public String getSourceUrl() {
		return "http://info.sporttery.cn/football/crs_vp.php";
	}

	@Override
	protected JczqFetchResult parserHTML(String html, String charset) {
		Div tag = getDiv(html, charset);
		if (tag == null) {
			return null;
		}

		try {
			return parser(tag);
		} catch (FetchParseException e) {
			this.logger.warn("匹配抓取数据出错.", e);
		} catch (ParseException e) {
			this.logger.warn("匹配抓取数据出错.", e);
		}

		return null;
	}

	protected JczqFetchResult parser(Div tag) throws FetchParseException, ParseException {
		SortedMap<String, Map<String, Double>> rateData = Maps.newTreeMap();
		SortedMap<String, JczqMatch> matchMap = Maps.newTreeMap();

		for (SimpleNodeIterator elements = tag.elements(); elements.hasMoreNodes();) {
			Node node = elements.nextNode();
			if (node instanceof Div && "titleScore".equals(((Div) node).getAttribute("class"))) {
				Div div = (Div) node;
				JczqMatch matchDTO = null;
				Map<String, Double> itemMap = null;
				for (SimpleNodeIterator elements2 = div.elements(); elements2.hasMoreNodes();) {
					Node el = (Node) elements2.nextNode();
					if (el instanceof HeadingTag) {
						matchDTO = getMatchDTO((HeadingTag) el);
					} else if (el instanceof TableTag) {
						itemMap = getItemMap((TableTag) el);
					}
				}
				if (matchDTO != null && itemMap != null) {
					String key = JczqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
					matchMap.put(key, matchDTO);
					rateData.put(key, itemMap);
				}
			}
		}
		return new JczqFetchResult(getPlayType(), getPassMode(), matchMap, rateData);
	}

	private Map<String, Double> getItemMap(TableTag table) throws FetchParseException {
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

	private JczqMatch getMatchDTO(HeadingTag el) throws ParseException {
		String text = el.toPlainTextString().trim();
		Matcher m = MATCH_PATTERN.matcher(text);
		if (m.find()) {
			String dayOfWeekStr = m.group(1);
			String lineIdOfDayStr = m.group(2);
			String gameName = m.group(3);
			String homeTeamName = m.group(4);
			String guestTeamName = m.group(5);
			String matchTimeStr = m.group(6);

			Date matchTime = DateUtils.parseDate(matchTimeStr, new String[] { "yy-MM-dd HH:mm" });
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
				if ("jumpTable".equals(tag.getAttribute("id"))) {
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
		return PassMode.SINGLE;
	}

}
