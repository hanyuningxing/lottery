package com.cai310.lottery.service.lottery.keno.ahkuai3.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.dczc.DczcSchemeDao;
import com.cai310.lottery.dao.lottery.jclq.JclqMatchDao;
import com.cai310.lottery.dao.lottery.keno.ahkuai3.AhKuai3SchemeDao;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.keno.KuaisanFetchParser;
import com.cai310.lottery.fetch.keno.kenoBaseResult;
import com.cai310.lottery.fetch.keno.kuaisanFetchResult;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoManagerImpl;
import com.cai310.lottery.utils.RlygQueryPVisitor;
import com.cai310.lottery.utils.RlygUtil;
import com.cai310.lottery.utils.liangcai.LiangCaiUtil;
import com.cai310.lottery.utils.liangcai.LiangNumResultVisitor;
import com.cai310.utils.RandomUtils;

@Component("ahkuai3KenoManagerImpl")
public class AhKuai3KenoManagerImpl extends KenoManagerImpl<AhKuai3IssueData, AhKuai3Scheme>{
	
//	@Autowired
//	protected LotterySupporterEntityManager lotterySupporterEntityManager;//彩种出票商设置实体操作类
	
	@Autowired
	private AhKuai3SchemeDao schemeDao;
	@Autowired
	protected AhKuai3MissDataControllerServiceImpl ahkuai3MissDataControllerServiceImpl;
	
	@Autowired
	protected KuaisanFetchParser kuaisanfetchparser;
	
	@Resource(name = "ahkuai3KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<AhKuai3IssueData, AhKuai3Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "ahkuai3KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<AhKuai3IssueData, AhKuai3Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	
	@Override
	public Lottery getLottery() {
		return Lottery.AHKUAI3;
	}
	
	/**
	 * 自动读取开奖号码
	 */
	@Override
	protected String autoGetResultData(String issueNumber) {

		Map<String, String> kResult = (Map<String, String>) kuaisanfetchparser.fetch(null);
		issueNumber= issueNumber.substring(2, issueNumber.length());
		issueNumber=issueNumber.substring(0,issueNumber.length()-2)+"0"+issueNumber.substring(issueNumber.length()-2,issueNumber.length());
			
		String num=kResult.get(issueNumber);
			if(num==null){
				logger.info("抓取号码为空");
				return null;
			}else{
				return num;
			}

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
			 rlygQueryPVisitor = RlygUtil.getIssue(Lottery.AHKUAI3, Byte.valueOf("0"), issueNumber);
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
		List<String> resultList=new ArrayList<String>();
		for(int i=0;i<3;i++){
			resultList.add(getRandomResult());
		}
		Collections.sort(resultList);
		StringBuilder sb = new StringBuilder();  
		for (String str : resultList) {  
		    sb.append(str).append(",");  
		}
		System.out.println(sb.deleteCharAt(sb.length()-1).toString());
	}
	private static String getRandomResult() {
		StringBuffer sb = new StringBuffer();
		Random random = new Random(); 
		List<Integer> strList=new ArrayList<Integer>();
		for(int i=1;i<7;i++){
			strList.add(i);
		}
		sb.append(strList.get(random.nextInt(strList.size())));
		return sb.toString();
	}

	//处理开奖号码
	protected String processResult(String afterResult) throws DataException {
		if(afterResult.length()==6){
			String num1 = afterResult.substring(0,2);
			String num2 = afterResult.substring(2,4);
			String num3 = afterResult.substring(4);
			StringBuffer sb = new StringBuffer();
			sb.append(num1+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num2+Constant.RESULT_SEPARATOR_FOR_NUMBER);
			sb.append(num3);
			afterResult =sb.toString();
		}else{
        	throw new DataException("处理开奖号码不正确");
        }
		if(afterResult.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
        	String[] afterResultArr=afterResult.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
        	if(afterResultArr.length!=3){
				///处理号码不正确
				throw new DataException("处理开奖号码不正确-3");
			}
        	return afterResult;
        }else{
        	throw new DataException("处理开奖号码不正确");
        }
	}
	
	@Override
	public void updateMissData() {
		ahkuai3MissDataControllerServiceImpl.updateMissData();
	}

	@Override
	protected SchemeDao<AhKuai3Scheme> getSchemeDao() {
		// TODO Auto-generated method stub
		return schemeDao;
	}
	
}
