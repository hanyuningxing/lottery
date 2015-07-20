package com.cai310.lottery.fetch.dczc;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.ItemBF;
import com.cai310.lottery.support.dczc.PlayType;
import com.google.common.collect.Maps;

public class DczcBFOkoooFetchParser extends DczcOkoooFetchParser {
	private static final Pattern LI_ID_PATTERN = Pattern.compile("^(c\\d+)_(\\d+)$");

	@Override
	public PlayType getPlayType() {
		return PlayType.BF;
	}

	@Override
	public String getOkoooLotteryType() {
		return "bifen";
	}

	@Override
	protected String getSourceXml() {
		return "http://buy.okooo.com/Upload/xml/47.xml";
	}

	@Override
	protected Map<String, Double> getItemMap(Element childElement) {
		Map<String, Double> itemMap = Maps.newHashMap();
		for (Item item : getPlayType().getAllItems()) {
			String rateStr = childElement.attributeValue(OKOOO_ITEM_TRANSFORM.getOkoooItem(item));
			Double rate = StringUtils.isNotBlank(rateStr) ? Double.valueOf(rateStr) : 0;
			itemMap.put(item.toString(), rate);
		}
		return itemMap;
	}

	protected Map<String, Map<String, Double>> parser(String html) {
		Parser parser = Parser.createParser(html, "UTF-8");
		NodeFilter cssFilter = new CssSelectorNodeFilter(
				"div[class=\"CenterWidth\"] form[name=\"InputForm\"] div div[style=\"display:none\"]");
		NodeFilter hasLiChildFilter = new HasChildFilter(new CssSelectorNodeFilter("li[id]"));
		NodeFilter filter = new AndFilter(cssFilter, hasLiChildFilter);
		NodeList nodes;
		try {
			nodes = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			this.logger.warn("过滤html出错.", e);
			return null;
		}
		if (nodes != null) {
			Map<String, Map<String, Double>> rateData = Maps.newLinkedHashMap();
			for (int i = 0; i < nodes.size(); i++) {
				Div div = (Div) nodes.elementAt(i);

				Map<String, Double> itemMap = Maps.newHashMap();
				Node[] nodeArr = div.getChildrenAsNodeArray();
				Integer lineId = null;
				for (Node node : nodeArr) {
					if (node instanceof Bullet) {
						Bullet bullet = (Bullet) node;
						String idStr = bullet.getAttribute("id");
						Matcher m = LI_ID_PATTERN.matcher(idStr);
						if (m.find()) {
							String okoooItem = m.group(1);
							String matchIndex = m.group(2);
							Integer lid = Integer.valueOf(matchIndex) - 1;
							if (lineId == null)
								lineId = lid;

							if (lineId.equals(lid)) {
								String rateStr = bullet.toPlainTextString().replaceAll("&nbsp;", "");
								Double rate = StringUtils.isNotBlank(rateStr) ? Double.valueOf(rateStr) : 0;
								itemMap.put(OKOOO_ITEM_TRANSFORM.getItem(okoooItem).toString(), rate);
							}
						}
					}
				}
				if (!itemMap.isEmpty())
					rateData.put(getLineKey(lineId), itemMap);
			}
			return rateData;
		}
		return null;
	}

	@Override
	protected Map<String, Double> getItemMap(TableRow row) {
		return null;
	}

	private static final OkoooItemTransform OKOOO_ITEM_TRANSFORM = new SimpleOkoooItemTransform(
			new HashMap<Item, String>() {
				private static final long serialVersionUID = 2102312776251058452L;
				{
					Item[] itemArr = { ItemBF.WIN_OTHER, ItemBF.WIN10, ItemBF.WIN20, ItemBF.WIN21, ItemBF.WIN30,
							ItemBF.WIN31, ItemBF.WIN32, ItemBF.WIN40, ItemBF.WIN41, ItemBF.WIN42, ItemBF.DRAW_OTHER,
							ItemBF.DRAW00, ItemBF.DRAW11, ItemBF.DRAW22, ItemBF.DRAW33, ItemBF.LOSE_OTHER,
							ItemBF.LOSE01, ItemBF.LOSE02, ItemBF.LOSE12, ItemBF.LOSE03, ItemBF.LOSE13, ItemBF.LOSE23,
							ItemBF.LOSE04, ItemBF.LOSE14, ItemBF.LOSE24 };
					for (int i = 0, j = 1, len = itemArr.length; i < len; i++, j += 1) {
						put(itemArr[i], String.format("c%s", j));
					}
				}
			});
}
