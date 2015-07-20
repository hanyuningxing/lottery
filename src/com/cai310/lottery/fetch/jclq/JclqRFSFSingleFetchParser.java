package com.cai310.lottery.fetch.jclq;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PlayType;

public class JclqRFSFSingleFetchParser extends JclqSimpleSingleFetchParser {

	@Override
	public String getSourceUrl() {
		return "http://info.sporttery.cn/basketball/hdc_vp.php";
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.RFSF;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.SINGLE;
	}

	@Override
	protected void handleReducedValue(JclqMatch matchDTO, String reducedValueStr) {
		Float value;
		if (StringUtils.isNotBlank(reducedValueStr)) {
			reducedValueStr = reducedValueStr.trim().replaceAll("\\+", "");
			value = Float.valueOf(reducedValueStr);
		} else {
			value = 0f;
		}
		matchDTO.setSingleHandicap(value);
	}
}
