package com.cai310.lottery.fetch.jclq;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;

import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PlayType;
import com.google.common.collect.Maps;

public class JclqDXFSingleFetchParser extends JclqSimpleSingleFetchParser {

	@Override
	public String getSourceUrl() {
		return "http://info.sporttery.cn/basketball/hilo_vp.php";
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.DXF;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.SINGLE;
	}

	protected Map<String, Double> getItemMap(TableRow row) {
		TableColumn[] columns = row.getColumns();

		int columnIndex = getRateColumnStartIndex() + 1;
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

				String matchTimeStr = columns[3].toPlainTextString();
				Date matchTime = DateUtils.parseDate(matchTimeStr, new String[] { "yy-MM-dd HH:mm" });

				Integer matchDate = JclqUtil.getMatchDate(dayOfWeekStr, matchTime);

				String totalScoreStr = columns[4].toPlainTextString();
				Float totalScore = (StringUtils.isNotBlank(totalScoreStr) && totalScoreStr.matches(DOUBLE_REGEX)) ? Float
						.valueOf(totalScoreStr) : 0f;

				JclqMatch matchDTO = new JclqMatch();
				matchDTO.setGameColor(bgcolor);
				matchDTO.setGameName(gameName);
				matchDTO.setHomeTeamName(home);
				matchDTO.setGuestTeamName(guest);
				matchDTO.setMatchTime(matchTime);
				matchDTO.setLineId(Integer.valueOf(lineIdOfDayStr));
				matchDTO.setMatchDate(matchDate);
				matchDTO.setSingleTotalScore(totalScore);

				return matchDTO;
			}
		}
		return null;
	}
}
