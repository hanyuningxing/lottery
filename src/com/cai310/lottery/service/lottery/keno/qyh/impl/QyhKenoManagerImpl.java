package com.cai310.lottery.service.lottery.keno.qyh.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.qyh.QyhSchemeDao;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhIssueData;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.dlt.impl.DltMissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoManagerImpl;

@Component("qyhKenoManagerImpl")
public class QyhKenoManagerImpl extends KenoManagerImpl<QyhIssueData, QyhScheme> {
	
	@Resource(name = "qyhKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<QyhIssueData, QyhScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}
	@Autowired
	private QyhSchemeDao schemeDao;
	@Resource(name = "qyhKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<QyhIssueData, QyhScheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Autowired
	protected QyhMissDataControllerServiceImpl qyhMissDataControllerServiceImpl;
	@Override
	public Lottery getLottery() {
		return Lottery.QYH;
	}

	@Override
	protected String autoGetResultData(String issueNumber) throws DataException {
     	String results =null;//= remoteDataQuery.getIssueResult(Lottery.QYH, issueNumber);
		if (StringUtils.isNotBlank(results)) {
			results = results.trim();
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
		qyhMissDataControllerServiceImpl.updateMissData();
	}

	@Override
	protected SchemeDao<QyhScheme> getSchemeDao() {
		// TODO Auto-generated method stub
		return schemeDao;
	}
}
