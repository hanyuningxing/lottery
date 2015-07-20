package com.cai310.lottery.service.lottery.keno.gdel11to5.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.gdel11to5.GdEl11to5SchemeDao;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoManagerImpl;

@Component("gdel11to5KenoManagerImpl")
public class GdEl11to5KenoManagerImpl extends KenoManagerImpl<GdEl11to5IssueData, GdEl11to5Scheme> {
	
	@Autowired
	private GdEl11to5SchemeDao schemeDao;
	
	@Autowired
	protected GdEl11to5MissDataControllerServiceImpl gdel11to5MissDataControllerServiceImpl;

	@Resource(name = "gdel11to5KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<GdEl11to5IssueData, GdEl11to5Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "gdel11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<GdEl11to5IssueData, GdEl11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Override
	public Lottery getLottery() {
		return Lottery.GDEL11TO5;
	}
	
	@Override
	protected String autoGetResultData(String issueNumber) throws DataException {
		if(StringUtils.isBlank(issueNumber))return null;
		issueNumber = issueNumber.substring(2);
		String results = null;//remoteDataQuery.getIssueResult(getLottery(),issueNumber);
		if(StringUtils.isNotBlank(results)){
			results=results.trim();
			return processResult(results);
		}
		return null;
	}
	protected String processResult(String afterResult) throws DataException {
		if(afterResult.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
        	String[] afterResultArr=afterResult.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
        	if(afterResultArr.length!=5){
				///处理号码不正确
				throw new DataException("处理开奖号码不正确-5");
			}
        	return afterResult;
        }else{
        	throw new DataException("处理开奖号码不正确");
        }
	}

	@Override
	public void updateMissData() {
		gdel11to5MissDataControllerServiceImpl.updateMissData();
	}

	@Override
	protected SchemeDao<GdEl11to5Scheme> getSchemeDao() {
		// TODO Auto-generated method stub
		return schemeDao;
	}

}
