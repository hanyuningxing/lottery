package com.cai310.lottery.service.lottery.keno.ahkuai3.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.ahkuai3.AhKuai3IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.ahkuai3.AhKuai3MissDataDao;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3MissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;
/**
 * 遗漏数据管理实现类
 * <p>Title: AhKuai3MissDataEntityManagerImpl.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: miracle</p>
 * @author leo
 * @date 2014-1-6 下午03:51:40 
 * @version 1.0
 */
@Service("ahkuai3MissDataEntityManagerImpl")
@Transactional
public class AhKuai3MissDataEntityManagerImpl 
	extends MissDataEntityManagerImpl<AhKuai3MissDataInfo, AhKuai3IssueData>{
	
	@Autowired
	private AhKuai3IssueDataDao issueDataDao;
	@Autowired
	private AhKuai3MissDataDao missDataDao;
	
	@Override
	public MissDataDao<AhKuai3MissDataInfo> getMissDataDao() {
		return missDataDao;
	}

	@Override
	public IssueDataDao<AhKuai3IssueData> getIssueDataDao() {
		return issueDataDao;
	}

}
