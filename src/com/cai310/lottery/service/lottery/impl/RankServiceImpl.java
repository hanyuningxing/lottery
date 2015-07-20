package com.cai310.lottery.service.lottery.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.service.lottery.SchemeEntityManager;

/**
 * 用户战绩、排行榜相关操作服务类
 * @author jack
 *
 */

@Service("rankService")
@Transactional
public class RankServiceImpl {
	
	public void countUserProfit(List schemeList,List profitOfUser,SchemeEntityManager schemeEntityManager){
		
	}

}
