package com.cai310.lottery.service.lottery.keno.klpk.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.klpk.KlpkSchemeDao;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkIssueData;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.entity.lottery.ticket.LotterySupporter;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoManagerImpl;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.lottery.ticket.protocol.response.dto.IssueInfo;
import com.cai310.lottery.utils.RlygQueryPVisitor;
import com.cai310.lottery.utils.RlygUtil;
import com.cai310.lottery.utils.liangcai.LiangCaiUtil;
import com.cai310.lottery.utils.liangcai.LiangNumResultVisitor;
import com.cai310.lottery.utils.liangcai.LiangResultVisitor;
import com.cai310.utils.DateUtil;

@Component("klpkKenoManagerImpl")
public class KlpkKenoManagerImpl extends KenoManagerImpl<KlpkIssueData, KlpkScheme> {
	@Autowired
	protected KlpkMissDataControllerServiceImpl klpkMissDataControllerServiceImpl;

	@Resource(name = "klpkKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<KlpkIssueData, KlpkScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "klpkKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlpkIssueData, KlpkScheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Override
	public Lottery getLottery() {
		return Lottery.KLPK;
	}
	@Autowired
	private KlpkSchemeDao schemeDao;
	@Override
	protected String autoGetResultData(String issueNumber) throws DataException {
		if(StringUtils.isBlank(issueNumber))return null;
		LiangNumResultVisitor liangNumResultVisitor;
		try {
			 liangNumResultVisitor =  LiangCaiUtil.getResult(getLottery(), Byte.valueOf("0"),issueNumber);
		} catch (Exception e) {
			throw new DataException("开奖号码捉去错误-5");
		}
		if(liangNumResultVisitor.getIsSuccess()){
			String results = liangNumResultVisitor.getResult();
			if(StringUtils.isNotBlank(results)){
				results=results.trim();
				results = results.replaceAll(" ", "");
				return processResult(results);
			}
		}
		return null;
	}
	protected String liangResult(String issueNumber) throws DataException{
		LiangNumResultVisitor liangNumResultVisitor;
		try {
			 liangNumResultVisitor =  LiangCaiUtil.getResult(getLottery(), Byte.valueOf("0"),issueNumber);
		} catch (Exception e) {
			throw new DataException("开奖号码捉去错误-5");
		}
		if(liangNumResultVisitor.getIsSuccess()){
			String results = liangNumResultVisitor.getResult();
			if(StringUtils.isNotBlank(results)){
				results=results.trim();
				results = results.replaceAll(" ", "");
				return processResult(results);
			}
		}
		return null;
	}
	protected String rlygResult(String issueNumber) throws DataException {
		issueNumber = issueNumber.substring(2);
		RlygQueryPVisitor rlygQueryPVisitor;
		try {
			 rlygQueryPVisitor = RlygUtil.getIssue(Lottery.KLPK, Byte.valueOf("0"), issueNumber);
		} catch (Exception e) {
			throw new DataException("开奖号码捉去错误-5");
		}
		String results = rlygQueryPVisitor.getBonuscode();
		if(StringUtils.isNotBlank(results)){
			results=results.trim();
			results = results.replaceAll(",", "");
			return processResult(results);
		}
		return null;
	}
	public static void main(String[] args) throws DataException, NumberFormatException, IOException, DocumentException {
		KlpkKenoManagerImpl KlpkKenoManagerImpl =new KlpkKenoManagerImpl();
		KlpkKenoManagerImpl.liangResult("");
	}
	protected String processResult(String afterResult) throws DataException {
		//302,407,212
		if(afterResult.length()==11){
			String[] num=afterResult.split(",");
			String num1=num[0].substring(0, 1);
			String num2=num[0].replace("0", "").substring(1);
			String num3=num[1].substring(0, 1);
			String num4=num[1].replace("0", "").substring(1);
			String num5=num[2].substring(0, 1);
			String num6=num[2].replace("0", "").substring(1);
			StringBuffer sb = new StringBuffer();
			sb.append(num1+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num2+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num3+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num4+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num5+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num6);
			afterResult =sb.toString();//3,2,4,7,2,12
		}else{
        	throw new DataException("处理开奖号码不正确");
        }
		if(afterResult.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
        	String[] afterResultArr=afterResult.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
        	if(afterResultArr.length!=6){
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

		klpkMissDataControllerServiceImpl.updateMissData();
	}

	@Override
	protected SchemeDao<KlpkScheme> getSchemeDao() {
		// TODO Auto-generated method stub
		return schemeDao;
	}

}
