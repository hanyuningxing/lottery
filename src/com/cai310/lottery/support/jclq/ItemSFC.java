package com.cai310.lottery.support.jclq;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.support.Item;

/**
 * 竞彩篮球-胜分差玩法选项
 * 
 */
public enum ItemSFC implements Item {
	/** 客胜1-5 */
	GUEST1_5("01",true, 1, 5),

	/** 客胜6-10 */
	GUEST6_10("02",true, 6, 10),

	/** 客胜11-15 */
	GUEST11_15("03",true, 11, 15),

	/** 客胜16-20 */
	GUEST16_20("04",true, 16, 20),

	/** 客胜21-25 */
	GUEST21_25("05",true, 21, 25),

	/** 客胜26+ */
	GUEST26("06",true, 26, null),

	/** 主胜1-5 */
	HOME1_5("11",false, 1, 5),

	/** 主胜6-10 */
	HOME6_10("12",false, 6, 10),

	/** 主胜11-15 */
	HOME11_15("13",false, 11, 15),

	/** 主胜16-20 */
	HOME16_20("14",false, 16, 20),

	/** 主胜21-25 */
	HOME21_25("15",false, 21, 25),

	/** 主胜26+ */
	HOME26("16",false, 26, null);
	private final String cpdyjValue;
	private final String text;
	private final String value;
	private final Integer min;
	private final Integer max;

	public static final String[] BALANCES = { "1-5", "6-10", "11-15", "16-20", "21-25", "26+" };
	public static final ItemSFC[] GUESTS = { GUEST1_5, GUEST6_10, GUEST11_15, GUEST16_20, GUEST21_25, GUEST26 };
	public static final ItemSFC[] HOMES = { HOME1_5, HOME6_10, HOME11_15, HOME16_20, HOME21_25, HOME26 };

	private ItemSFC(String cpdyjValue , boolean guestWin, Integer min, Integer max) {
		this.cpdyjValue = cpdyjValue;
		this.min = min;
		this.max = max;

		StringBuilder sb = new StringBuilder();
		if (this.max != null) {
			sb.append(this.min).append("-").append(this.max);
		} else {
			sb.append(this.min).append("+");
		}
		this.text = (guestWin ? "客胜" : "主胜") + sb.toString();
		this.value = (guestWin ? "guest" : "home") + sb.toString();
	}

	public String getText() {
		return text;
	}

	public String getValue() {
		return value;
	}

	public boolean check(int balance) {
		balance = Math.abs(balance);
		return balance >= this.min && (this.max == null || balance <= this.max);
	}

	/**
	 * 根据值获取对应的类型,找不到对应的类型返回null.
	 */
	public static ItemSFC valueOfValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ItemSFC type : ItemSFC.values()) {
				if (type.getValue().equalsIgnoreCase(value))
					return type;
			}
		}
		return null;
	}
	/**
	 * 根据值获取对应的类型,找不到对应的类型返回null.
	 */
	public static ItemSFC valueOfName(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ItemSFC type : ItemSFC.values()) {
				if (type.name().equalsIgnoreCase(value))
					return type;
			}
		}
		return null;
	}
	public static ItemSFC valueOf(int balance) {
		ItemSFC[] arr = (balance > 0) ? GUESTS : HOMES;
		balance = Math.abs(balance);
		for (ItemSFC type : arr) {
			if (type.check(balance)) {
				return type;
			}
		}
		return null;
	}
	public static void main(String[] args) {
		for (ItemSFC type : ItemSFC.values()) {
			System.out.println(type.getText()+"===="+type.getValue());
		}
	}

	public String getCpdyjValue() {
		return cpdyjValue;
	}
}
