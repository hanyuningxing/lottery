package com.cai310.lottery.service.lottery.keno.sdel11to5.impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.sdel11to5.SdEl11to5SchemeDao;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.keno.CP36011To5FetchParser;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoManagerImpl;

@Component("sdel11to5KenoManagerImpl")
public class SdEl11to5KenoManagerImpl extends KenoManagerImpl<SdEl11to5IssueData, SdEl11to5Scheme> {
	@Autowired
	private SdEl11to5SchemeDao schemeDao;
	@Autowired
	protected SdEl11to5MissDataControllerServiceImpl sdel11to5MissDataControllerServiceImpl;

	@Resource(name = "sdel11to5KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<SdEl11to5IssueData, SdEl11to5Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "sdel11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<SdEl11to5IssueData, SdEl11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Override
	public Lottery getLottery() {
		return Lottery.SDEL11TO5;
	}
	
	@Override
	protected String autoGetResultData(String issueNumber) throws DataException {
		if(StringUtils.isBlank(issueNumber))return null;
		String issueNumber_360 = issueNumber.substring(4);
		String results="";
		try {
			CP36011To5FetchParser p = new CP36011To5FetchParser();
			Map<String, String[]> map = p.fetch();
			for(Entry<String,String[]> entry:map.entrySet()){
				if(entry.getKey().equals(issueNumber_360)){
					results=entry.getValue()[0];
				}
			}
			if(StringUtils.isBlank(results)){
				Map<String,String> map_spare=p.fetchSpare();
				results=map_spare.get(issueNumber);
				return results;
			}
		} catch (Exception e) {
			throw new DataException("开奖号码捉去错误-5");
		}
		if(StringUtils.isNotBlank(results)){
			results=results.trim();
			results = results.replaceAll(" ", "");
			return processResult(results);
		}
		return null;
	}
	public static void main(String[] args) throws DataException {
		SdEl11to5KenoManagerImpl SdEl11to5KenoManagerImpl =new SdEl11to5KenoManagerImpl();
		SdEl11to5KenoManagerImpl.processResult("0908020111");
	}
	protected String processResult(String afterResult) throws DataException {
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

		sdel11to5MissDataControllerServiceImpl.updateMissData();
	}

	@Override
	protected SchemeDao<SdEl11to5Scheme> getSchemeDao() {
		// TODO Auto-generated method stub
		return schemeDao;
	}

}
