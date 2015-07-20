package com.cai310.lottery.service.lottery.keno.ssc;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.ssc.SscSchemeDao;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoManagerImpl;
import com.cai310.lottery.utils.RlygQueryPVisitor;
import com.cai310.lottery.utils.RlygUtil;

@Component("sscKenoManagerImpl")
public class SscKenoManagerImpl extends KenoManagerImpl<SscIssueData, SscScheme> {
	@Autowired
	private SscSchemeDao schemeDao;
	@Autowired
	protected SscMissDataControllerServiceImpl sscMissDataControllerServiceImpl;
	
	@Resource(name = "sscKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<SscIssueData, SscScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "sscKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<SscIssueData, SscScheme> kenoService) {
		this.kenoService = kenoService;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.SSC;
	}

	@Override
	protected String autoGetResultData(String issueNumber) throws DataException {
		if(StringUtils.isBlank(issueNumber))return null;
		issueNumber = issueNumber.substring(2);
		RlygQueryPVisitor rlygQueryPVisitor;
		try {
			 rlygQueryPVisitor = RlygUtil.getIssue(Lottery.SSC, Byte.valueOf("0"), issueNumber);
		} catch (Exception e) {
			throw new DataException("开奖号码捉去错误-5");
		}
		String results = rlygQueryPVisitor.getBonuscode();
		if(StringUtils.isNotBlank(results)){
			results=results.trim();
			return processResult(results);
		}
		return null;
	}

	protected String processResult(String afterResult) throws DataException {
		if (afterResult.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER) != -1) {
			String[] afterResultArr = afterResult.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			if (afterResultArr.length != 5) {
				// /处理号码不正确
				throw new DataException("处理开奖号码不正确-5");
			}
			return afterResult;
		} else {
			throw new DataException("处理开奖号码不正确");
		}
	}

	@Override
	public void updateMissData() {
		sscMissDataControllerServiceImpl.updateMissData();		
	}

	@Override
	protected SchemeDao<SscScheme> getSchemeDao() {
		// TODO Auto-generated method stub
		return schemeDao;
	}
}
