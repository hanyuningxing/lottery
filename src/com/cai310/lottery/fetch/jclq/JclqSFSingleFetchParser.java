package com.cai310.lottery.fetch.jclq;

import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PlayType;

public class JclqSFSingleFetchParser extends JclqSimpleSingleFetchParser {

	@Override
	public String getSourceUrl() {
		return "http://info.sporttery.cn/basketball/mnl_vp.php";
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.SF;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.SINGLE;
	}
}
