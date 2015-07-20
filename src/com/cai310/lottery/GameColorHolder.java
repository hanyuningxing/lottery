package com.cai310.lottery;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.service.lottery.GameManager;

public class GameColorHolder {
	private static final String DEFAULT_COLOR = "#0066FF";

	private static GameManager gameManager;

	public void setGameManager(GameManager manager) {
		gameManager = manager;
	}

	public static String getColor(String gameName) {
		String color = null;
		if (StringUtils.isNotBlank(gameName)) {
			Map<String, String> map = gameManager.getGameColor();
			if (map != null) {
				color = map.get(gameName);
			}
		}
		if (color == null)
			color = DEFAULT_COLOR;
		return color;
	}
}
