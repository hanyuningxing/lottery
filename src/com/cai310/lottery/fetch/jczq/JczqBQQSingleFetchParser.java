package com.cai310.lottery.fetch.jczq;

import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;

public class JczqBQQSingleFetchParser extends JczqSimpleSingleFetchParser {

	@Override
	public String getSourceUrl() {
		return "http://info.sporttery.cn/football/hafu_vp.php";
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.BQQ;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.SINGLE;
	}

}
