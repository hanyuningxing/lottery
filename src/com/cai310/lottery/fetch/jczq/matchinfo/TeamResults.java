package com.cai310.lottery.fetch.jczq.matchinfo;

/**
 * 球队联赛战况
 * @author H
 *
 */
public class TeamResults {
	
	private String ranking;
	
	private String points;
	
	private Integer won;
	
	private Integer draw;
	
	private Integer lose;

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public Integer getWon() {
		return won;
	}

	public void setWon(Integer won) {
		this.won = won;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getLose() {
		return lose;
	}

	public void setLose(Integer lose) {
		this.lose = lose;
	}
	
}
