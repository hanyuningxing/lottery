package com.cai310.lottery.service.lottery.dczc.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.DczcCompoundContent;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.DczcSingleContent;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;

@Service("dczcPrintServiceImpl")
@Transactional
public class DczcPrintServiceImpl extends PrintServiceImpl<DczcScheme> {

	@Autowired
	private DczcSchemeEntityManagerImpl schemeEntityManager;

	@Autowired
	private DczcMatchEntityManager matchEntityManager;

	@Override
	protected SchemeEntityManager<DczcScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.DCZC;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PrintInterface doSendToPrint(PrintInterface pi, DczcScheme scheme) {
		pi = super.doSendToPrint(pi, scheme);
		pi.setBetType((byte) scheme.getPlayType().ordinal());// 玩法

		// ----------------------- 重新组装方案内容 -----------------------
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("passType", scheme.getPassType());

		Map<Integer, DczcMatch> matchMap = matchEntityManager.findMatchMap(scheme.getPeriodId());

		Date endTime = null;
		switch (scheme.getMode()) {
		case COMPOUND:
			contentMap.put("passMode", scheme.getPassMode().ordinal());

			DczcCompoundContent compoundContent = scheme.getCompoundContent();
			contentMap.put("danMinHit", compoundContent.getDanMinHit());
			contentMap.put("danMaxHit", compoundContent.getDanMaxHit());

			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			for (DczcMatchItem item : compoundContent.getItems()) {
				Map<String, Object> itemMap = new HashMap<String, Object>();
				itemMap.put("lineId", item.getLineId());
				itemMap.put("isDan", item.isDan());
				itemMap.put("value", item.getValue());

				DczcMatch match = matchMap.get(item.getLineId());

				if (endTime == null || endTime.after(match.getMatchTime()))
					endTime = match.getMatchTime();

				itemMap.put("matchTime", DateUtil.dateToStr(match.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
				itemMap.put("homeTeamName", match.getHomeTeamName());
				itemMap.put("guestTeamName", match.getGuestTeamName());
				itemMap.put("handicap", match.getHandicap());

				items.add(itemMap);
			}
			contentMap.put("items", items);
			break;
		case SINGLE:
			DczcSingleContent singleContent = scheme.getSingleContent();
			StringBuilder contentSb = new StringBuilder();
			StringBuilder lineSb = new StringBuilder();
			Item[] allItems = scheme.getPlayType().getAllItems();
			final char lineSeq = ',';
			final String placeholder = allItems[0].getValue().replaceAll(".{1}", "#");// 不选场次的占位符
			String[] contentArr = singleContent.getContent().split("\r\n");
			for (String content : contentArr) {
				lineSb.setLength(0);
				String[] ordinalArr = content.split(",");
				for (String ordinalStr : ordinalArr) {
					if (!"#".equals(ordinalStr)) {
						int ordinal = Integer.parseInt(ordinalStr);
						Item item = allItems[ordinal];
						lineSb.append(item.getValue());
					} else {
						lineSb.append(placeholder);
					}

				}
				contentSb.append(lineSb).append(lineSeq);
			}
			contentSb.deleteCharAt(contentSb.length() - 1);// 删除最后的lineSeq
			contentMap.put("content", contentSb.toString());

			List<Map<String, Object>> selectedMatchs = new ArrayList<Map<String, Object>>();
			for (Integer lineId : singleContent.getLineIds()) {
				Map<String, Object> map = new HashMap();
				DczcMatch match = matchMap.get(lineId);

				if (endTime == null || endTime.after(match.getMatchTime()))
					endTime = match.getMatchTime();

				map.put("lineId", lineId);
				map.put("matchTime", DateUtil.dateToStr(match.getMatchTime(), "yyyy-MM-dd HH:mm:ss"));
				map.put("homeTeamName", match.getHomeTeamName());
				map.put("guestTeamName", match.getGuestTeamName());
				map.put("handicap", match.getHandicap());

				selectedMatchs.add(map);
			}
			contentMap.put("matchs", selectedMatchs);
			break;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		c.add(Calendar.MINUTE, -5);
		endTime = c.getTime();
		if (endTime.before(pi.getOfficialEndTime()))
			pi.setOfficialEndTime(endTime);

		pi.setContent(JsonUtil.getJsonString4JavaPOJO(contentMap));
		return pi;
	}
}
