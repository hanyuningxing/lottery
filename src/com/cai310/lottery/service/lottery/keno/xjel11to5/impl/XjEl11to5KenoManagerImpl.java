package com.cai310.lottery.service.lottery.keno.xjel11to5.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.xjel11to5.XjEl11to5SchemeDao;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.keno.Xj11to5FetchParser;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoManagerImpl;

@Component("xjel11to5KenoManagerImpl")
public class XjEl11to5KenoManagerImpl extends KenoManagerImpl<XjEl11to5IssueData, XjEl11to5Scheme> {
	@Autowired
	private XjEl11to5SchemeDao schemeDao;
	@Autowired
	protected XjEl11to5MissDataControllerServiceImpl xjel11to5MissDataControllerServiceImpl;

	@Autowired
	protected Xj11to5FetchParser xj11To5FetchParser;
	@Resource(name = "xjel11to5KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<XjEl11to5IssueData, XjEl11to5Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "xjel11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<XjEl11to5IssueData, XjEl11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Override
	public Lottery getLottery() {
		return Lottery.XJEL11TO5;
	}
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	protected String autoGetResultData(String issueNumber) throws DataException {
		if(StringUtils.isBlank(issueNumber))return null;
		try {
			Map<String, String>  kResult =xj11To5FetchParser.fetchSpare();
				String num=kResult.get(issueNumber);
				if(StringUtils.isBlank(num)){
					kResult =xj11To5FetchParser.fetch();
					num=kResult.get(issueNumber);
				}
				if(num==null){
					logger.info("抓取号码为空");
					return null;
				}else{
					return num;
				}
		} catch (Exception e) {
			throw new DataException("开奖号码捉去错误-5");
		}
	}
	protected static String processResult(String afterResult) throws DataException {
		if(afterResult.length()==10){
			String num1 = afterResult.substring(0,2);
			String num2 = afterResult.substring(2,4);
			String num3 = afterResult.substring(4,6);
			String num4 = afterResult.substring(6,8);
			String num5 = afterResult.substring(8);
			StringBuffer sb = new StringBuffer();
			sb.append(num1+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num2+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num3+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num4+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num5);
			afterResult =sb.toString();
		}else{
        	throw new DataException("处理开奖号码不正确");
        }
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
		xjel11to5MissDataControllerServiceImpl.updateMissData();
	}

	@Override
	protected SchemeDao<XjEl11to5Scheme> getSchemeDao() {
		// TODO Auto-generated method stub
		return schemeDao;
	}

}
