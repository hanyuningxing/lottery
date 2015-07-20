package com.cai310.lottery.entity.lottery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "GAME_COLOR")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GameColor {
	private String gameName;
	private String gameColor;

	@Id
	@Column(name = "gameName", nullable = false, length = 50)
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	@Column(name = "gameColor", nullable = false, length = 7)
	public String getGameColor() {
		return gameColor;
	}

	public void setGameColor(String gameColor) {
		this.gameColor = gameColor;
	}

}
