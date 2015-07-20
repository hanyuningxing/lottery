package com.cai310.lottery.service.lottery;

import java.util.Map;

import com.cai310.lottery.entity.lottery.GameColor;

public interface GameManager {

	Map<String,String> getGameColor();
	
	void saveGameColor(GameColor gameColor);
}
