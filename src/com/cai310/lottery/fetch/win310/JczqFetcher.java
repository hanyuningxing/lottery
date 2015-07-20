package com.cai310.lottery.fetch.win310;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.football.LetGoal;
@Component
public class JczqFetcher extends JcFetcher{
	private static String yp = "http://www.310win.com/data/yp"+101+".xml?"+System.currentTimeMillis()/1000+"000";
    private static String dx = "http://www.310win.com/data/dx"+101+".xml?"+System.currentTimeMillis()/1000+"000";
    private static String op = "http://www.310win.com/data/yp"+101+".xml?"+System.currentTimeMillis()/1000+"000";
	@Override
	public String getLetGoalSourceUrl() {
		return yp;
	}

	@Override
	public String getStandardSourceUrl() {
		return op;
	}

	@Override
	public String getTotalScoreSourceUrl() {
		return dx;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.JCZQ;
	}
	//@PostConstruct
	@Override
	protected List<LetGoal> getLetGoalData(){
		return super.getLetGoalData();
	}
}