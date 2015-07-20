package com.cai310.lottery.fetch.jclq;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PlayType;
import com.google.common.collect.Maps;

public class JclqSFCSingleFetchParser extends JclqSimpleSingleFetchParser {

	@Override
	public String getSourceUrl() {
		return "http://info.sporttery.cn/basketball/wnm_vp.php";
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.SFC;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.SINGLE;
	}

	@Override
	protected Map<String, Double> getItemMap(TableRow row) {
		TableColumn[] columns = row.getColumns();
		if (columns != null && columns.length > 0) {
			int columnIndex = getRateColumnStartIndex() + 1;
			Map<String, Double> itemMap = Maps.newHashMap();
			for (int i = 0; i < ItemSFC.BALANCES.length; i++) {
				String[] arr = columns[columnIndex].toHtml().split("<br\\s*/?>");
				if (arr.length == 2) {
					Item guestItem = ItemSFC.GUESTS[i];
					Item winItem = ItemSFC.HOMES[i];
					String guestStr = arr[0].replaceAll("<.*?>", "");
					String homeStr = arr[1].replaceAll("<.*?>", "");
					Double guestRate = (StringUtils.isNotBlank(guestStr) && guestStr.matches(DOUBLE_REGEX)) ? Double
							.valueOf(guestStr) : 0;
					Double homeRate = (StringUtils.isNotBlank(homeStr) && homeStr.matches(DOUBLE_REGEX)) ? Double
							.valueOf(homeStr) : 0;
					itemMap.put(guestItem.toString(), guestRate);
					itemMap.put(winItem.toString(), homeRate);
				}
				columnIndex++;
			}
			return itemMap;
		}
		return null;
	}
}
