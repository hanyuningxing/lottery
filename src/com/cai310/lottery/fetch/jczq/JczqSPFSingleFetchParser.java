package com.cai310.lottery.fetch.jczq;

import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;

public class JczqSPFSingleFetchParser extends JczqSimpleSingleFetchParser {

	@Override
	public String getSourceUrl() {
		return "http://info.sporttery.cn/football/had_vp.php";
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.SPF;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.SINGLE;
	}

}
