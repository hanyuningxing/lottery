package com.cai310.lottery.support.jczq;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.Constant;

/**
 * 竞彩足球页面玩法类型(区别于业务逻辑的玩法)
 * 
 */
public enum PlayTypeWeb {
	
	SPF_RQSPF("胜平负/让球胜平负"),

	JQS("进球数"),

	BF("比分"),

	BQQ("半全场"),
	
	MIX("混合过关"),
	
	RENJIU("任九新玩"),
	
	EXY("盘口(生死盘)"),
	
	DGP("半球盘单关配"),
	
	YP("亚盘玩法");
	
	/** 玩法名称 */
	private final String text;

	private PlayTypeWeb(String text) {
		this.text = text;
	}

	/**
	 * @return {@link #text}
	 */
	public String getText() {
		return text;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

	public static PlayTypeWeb valueOfName(String name) {
		if (StringUtils.isNotBlank(name)) {
			for (PlayTypeWeb type : PlayTypeWeb.values()) {
				if (type.name().equals(name))
					return type;
			}
		}
		return null;
	}
	
	/**
	 * 业务玩法到页面玩法转换
	 * @param playType
	 * @return
	 */
	public static PlayTypeWeb playType2Web(PlayType playType){
		if(playType==null){
			return null;
		}
		switch(playType){
		case SPF:
		case RQSPF:
			return SPF_RQSPF;
		case JQS:
			return JQS;
		case BF:
			return BF;
		case BQQ:
			return BQQ;
		case MIX:
			return MIX;
		}
		return null;
	}
}
