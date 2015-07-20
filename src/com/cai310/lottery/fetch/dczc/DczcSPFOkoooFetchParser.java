package com.cai310.lottery.fetch.dczc;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.htmlparser.Node;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LabelTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.ItemSPF;
import com.cai310.lottery.support.dczc.PlayType;
import com.google.common.collect.Maps;

public class DczcSPFOkoooFetchParser extends DczcOkoooFetchParser {

	@Override
	public PlayType getPlayType() {
		return PlayType.SPF;
	}

	@Override
	public String getOkoooLotteryType() {
		return "";
	}

	@Override
	protected String getSourceXml() {
		return "http://buy.okooo.com/Upload/xml/44.xml";
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

	@Override
	protected Map<String, Double> getItemMap(TableRow row) {
		TableColumn[] columns = row.getColumns();
		Map<String, Double> itemMap = Maps.newHashMap();
		Item[] itemArr = getPlayType().getAllItems();
		for (int i = 0, k = 11, len = itemArr.length; i < len; i++, k += 1) {
			Item item = itemArr[i];
			TableColumn column = columns[k];
			Node[] nodeArr = column.getChildrenAsNodeArray();
			for (Node node : nodeArr) {
				if (node instanceof LabelTag) {
					LabelTag label = (LabelTag) node;
					Node[] nodeArr2 = label.getChildrenAsNodeArray();
					for (Node node2 : nodeArr2) {
						if (node2 instanceof Div) {
							Div div = (Div) node2;
							if (OKOOO_ITEM_TRANSFORM.getOkoooItem(item).equals(div.getAttribute("name"))) {
								String rateStr = div.toPlainTextString().replaceAll("&nbsp;", "");
								Double rate = StringUtils.isNotBlank(rateStr) ? Double.valueOf(rateStr) : 0;
								itemMap.put(item.toString(), rate);
							}
						}
					}
				}
			}
		}
		return itemMap;
	}

	private static final OkoooItemTransform OKOOO_ITEM_TRANSFORM = new SimpleOkoooItemTransform(
			new HashMap<Item, String>() {
				private static final long serialVersionUID = 2102312776251058452L;
				{
					Item[] itemArr = ItemSPF.values();
					for (int i = 0, j = 1, len = itemArr.length; i < len; i++, j += 2) {
						put(itemArr[i], String.format("c%s", j));
					}
				}
			});
}
