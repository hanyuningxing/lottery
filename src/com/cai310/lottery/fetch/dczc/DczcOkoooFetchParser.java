package com.cai310.lottery.fetch.dczc;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.support.Item;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Maps;

public abstract class DczcOkoooFetchParser extends DczcAbstractFetchParser {
	private static final Pattern TR_ID_PATTERN = Pattern.compile("Match_(\\d+)");

	@Override
	public String getCharset() {
		return "GBK";
	}

	@Override
	public String getSourceUrl(String issueNumber) {
		return String.format("http://buy.okooo.com/danchang/%s/%s/",
				getOkoooLotteryType(),issueNumber.trim());
	}

	public abstract String getOkoooLotteryType();

	@Override
	protected DczcFetchResult parserHTML(String periodNumber, String html) {
		boolean periodNumberOK = false;
		try {
			periodNumberOK = checkIssueNumber(periodNumber, html);
		} catch (ParserException e) {
			this.logger.warn("解析html出错.", e);
		}
		if (!periodNumberOK) {
			logger.warn("目标网站的当前期号与要抓取的期号不一致，放弃抓取...");
			return null;
		}

		Map<String, Map<String, Double>> rateData = parser(html);
		Map<String, Map<String, Double>> rateDataFromXml = fetchFromXml();

		if (rateData == null) {
			rateData = rateDataFromXml;
		} else if (rateDataFromXml != null) {
			for (Entry<String, Map<String, Double>> entry : rateDataFromXml.entrySet()) {
				if (entry.getValue() != null && !entry.getValue().isEmpty()) {
					Map<String, Double> itemMap = rateData.get(entry.getKey());
					if (itemMap == null)
						itemMap = entry.getValue();
					else {
						for (Entry<String, Double> itemEntry : entry.getValue()
								.entrySet()) {
							if (itemEntry.getValue() != null
									&& itemEntry.getValue() > 0)
								itemMap.put(itemEntry.getKey(),
										itemEntry.getValue());
						}
					}
					rateData.put(entry.getKey(), itemMap);
				}
			}
		}

		if (rateData == null || rateData.isEmpty())
			return null;

		return new DczcFetchResult(getPlayType(), rateData);
	}

	protected Map<String, Map<String, Double>> parser(String html) {
		TableTag tag = getTableTag(html);
		if (tag == null)
			return null;
		Map<String, Map<String, Double>> rateData = Maps.newLinkedHashMap();
		TableRow[] rows = tag.getRows();
		for (TableRow row : rows) {
			String trId = row.getAttribute("id");
			if (StringUtils.isNotBlank(trId)) {
				Matcher m = TR_ID_PATTERN.matcher(trId);
				boolean find = m.find();
				if (find) {
					String matchIndex = m.group(1);
					int lid = Integer.valueOf(matchIndex) - 1;
					String lineKey = getLineKey(lid);
					Map<String, Double> itemMap = getItemMap(row);
					if (itemMap != null && !itemMap.isEmpty())
						rateData.put(lineKey, itemMap);
				}
			}
		}
		return rateData;
	}

	protected boolean checkIssueNumber(String issueNumber, String html) throws ParserException {
		String issue = null;
		Parser parser = Parser.createParser(html, getCharset());
		NodeFilter selectFilter = new NodeClassFilter(SelectTag.class);
		NodeFilter[] filters = new NodeFilter[] { selectFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList = parser.parse(filter);
		SelectTag select = null;
		for (int i = 0; i <= nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof SelectTag) {
				SelectTag selectNode = (SelectTag) nodeList.elementAt(i);
				if ("SelectLotteryNo".equals(selectNode.getAttribute("id"))) {
					select = selectNode;
					break;
				}
			}
		}
		if (select != null) {
			OptionTag[] opArr = select.getOptionTags();
			OptionTag option = null;
			for (OptionTag op : opArr) {
				String selected = op.getAttribute("selected");
				if ("true".equalsIgnoreCase(selected) || "selected".equalsIgnoreCase(selected)) {
					option = op;
					break;
				}
			}
			if (option == null)
				option = opArr[0];

			issue = option.getAttribute("value");
		}
		if (issue == null || !issueNumber.trim().equals(issue.trim())) {
			return false;
		}
		return true;
	}

	protected TableTag getTableTag(String html) {
		Parser parser = Parser.createParser(html, getCharset());
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
				if ("TableBorder".equals(tag.getAttribute("id")) && "ContentBrim".equals(tag.getAttribute("class"))) {
					return tag;
				}
			}
		}

		return null;
	}

	protected abstract String getSourceXml();

	protected Document getDocument() {
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("gb2312");
		Document doc = null;
		String htmlValue = null;
		try {
			String sourceXml = getSourceXml();
			if (sourceXml.indexOf("&") > 0) {
				sourceXml = String.format("%s&_t=%s", sourceXml, System.currentTimeMillis());
			} else {
				sourceXml = String.format("%s?_t=%s", sourceXml, System.currentTimeMillis());
			}
			htmlValue = HttpClientUtil.getRemoteSource(sourceXml, getCharset());
			if (StringUtils.isBlank(htmlValue)) {
				return null;
			}
			doc = DocumentHelper.parseText(htmlValue);
		} catch (DocumentException e) {
			logger.warn(e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		return doc;
	}

	protected Map<String, Map<String, Double>> fetchFromXml() {
		Document doc = getDocument();
		if (doc == null) {
			return null;
		}
		Element rootElement = doc.getRootElement();
		if (rootElement == null) {
			return null;
		}
		Iterator<?> it = rootElement.elementIterator("w");
		if (it == null) {
			return null;
		}

		Element childElement = null;
		Map<String, Map<String, Double>> rateData = Maps.newLinkedHashMap();
		while (it.hasNext()) {
			childElement = (Element) it.next();
			if (childElement != null) {
				try {
					String n = childElement.attributeValue("n");
					String lineKey;
					if (StringUtils.isNotBlank(n) && n.matches("\\d+")) {
						int lid = Integer.valueOf(n) - 1;
						lineKey = getLineKey(lid);
					} else {
						continue;
					}
					Map<String, Double> itemMap = getItemMap(childElement);
					if (itemMap != null && !itemMap.isEmpty())
						rateData.put(lineKey, itemMap);
				} catch (Exception e) {
					continue;
				}
			}
		}
		return rateData;
	}

	protected abstract Map<String, Double> getItemMap(Element childElement);

	protected abstract Map<String, Double> getItemMap(TableRow row);

	interface OkoooItemTransform {
		String getOkoooItem(Item item);

		Item getItem(String OkoooItem);
	}

	static class SimpleOkoooItemTransform implements OkoooItemTransform {
		private Map<Item, String> item_str_map = Maps.newHashMap();
		private Map<String, Item> str_item_map = Maps.newHashMap();

		public SimpleOkoooItemTransform(Map<Item, String> itemMap) {
			item_str_map.putAll(itemMap);
			for (Entry<Item, String> entry : itemMap.entrySet()) {
				str_item_map.put(entry.getValue(), entry.getKey());
			}
		}

		@Override
		public String getOkoooItem(Item item) {
			return item_str_map.get(item);
		}

		@Override
		public Item getItem(String okoooItem) {
			return str_item_map.get(okoooItem);
		}

	}

}
