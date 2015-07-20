package com.cai310.lottery.service.lottery;

import java.util.List;

import com.cai310.lottery.entity.info.MatchInfo;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;

public interface MatchInfoService {

	/**
	 * 保存信息
	 * @param list
	 */
	void saveMatchInfo(List<MatchInfo> list);
	
	/**
	 * 填充单场对阵的赔率信息
	 * @param list
	 */
	void fillOddsData(List<DczcMatch> list);
}
