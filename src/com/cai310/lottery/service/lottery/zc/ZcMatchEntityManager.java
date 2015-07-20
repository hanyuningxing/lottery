package com.cai310.lottery.service.lottery.zc;

import com.cai310.lottery.entity.lottery.zc.ZcMatch;

public interface ZcMatchEntityManager<T extends ZcMatch> {

	T getMatch(Long id);

	T[] findMatchs(Long periodId);

	T[] findMatchs(String periodNumber);

	T saveMatch(T match);

	void saveMatchs(T[] matchs);

}
