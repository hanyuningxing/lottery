package com.cai310.lottery.service.lottery.keno.el11to5.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.el11to5.El11to5SchemeDao;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.lottery.keno.impl.KenoManagerImpl;
import com.cai310.lottery.utils.CpdyjUtil;
import com.cai310.lottery.utils.KenoResultUtil;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Lists;

@Component("el11to5KenoManagerImpl")
public class El11to5KenoManagerImpl extends KenoManagerImpl<El11to5IssueData, El11to5Scheme> {
	
	@Autowired
	private El11to5SchemeDao schemeDao;
	@Autowired
	protected El11to5MissDataControllerServiceImpl el11to5MissDataControllerServiceImpl;

	@Resource(name = "el11to5KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<El11to5IssueData, El11to5Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "el11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<El11to5IssueData, El11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	@Override
	public Lottery getLottery() {
		return Lottery.EL11TO5;
	}
	@Override
	protected String autoGetResultData(String issueNumber) throws DataException {
		String LotID="23009";
		if(StringUtils.isBlank(issueNumber))return null;
		String url=CpdyjUtil.getReUrl(issueNumber.trim(),LotID, System.currentTimeMillis());
		String results=KenoResultUtil.getKenoResultByXml(url, issueNumber.trim());
		if(StringUtils.isBlank(results)){
			//用备用地址
			url=CpdyjUtil.getBakReUrl(issueNumber.trim(),LotID, System.currentTimeMillis());
			results=KenoResultUtil.getKenoResultByXml(url, issueNumber.trim());
		}
		if(StringUtils.isNotBlank(results)){
			results=results.trim();
			if(results.indexOf(" ")!=-1){
				String[] resultArr=results.split(" ");
				if(resultArr.length!=5){
					///开奖号码不正确
					throw new DataException("捉取大赢家开奖号码不正确-5");
				}
				String afterResult=results.replaceAll(" ", Constant.RESULT_SEPARATOR_FOR_NUMBER);
		        if(afterResult.indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
		        	String[] afterResultArr=afterResult.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		        	if(afterResultArr.length!=5){
						///处理号码不正确
						throw new DataException("处理大赢家开奖号码不正确-5");
					}
		        	return afterResult;
		        }else{
		        	throw new DataException("处理大赢家开奖号码不正确");
		        }
			}else{
				///开奖号码不正确
				throw new DataException("捉取大赢家开奖号码不正确");
			}
		}
		return null;
	}
	protected String get500WanResultData(String issueNumber) throws DataException {
		String url="http://www.500wan.com/static/public/dlc/xml/newlyopenlist.xml";
		String url1="http://jk.trade.500wan.com/static/public/dlc/xml/newlyopenlist.xml";
		String returnString = HttpClientUtil.getRemoteSource(url);
		if(StringUtils.isBlank(returnString)){
			returnString = HttpClientUtil.getRemoteSource(url1);
		}
		if(StringUtils.isNotBlank(returnString)){
			try {
				Document doc=DocumentHelper.parseText(returnString);
				Element root = doc.getRootElement();
				Element row;
				W500WanResultVisitor w500WanResultVisitor;
				List<W500WanResultVisitor> reslutList = Lists.newArrayList();
				for (Iterator i = root.elementIterator("row"); i.hasNext();) {
					row = (Element) i.next();
					w500WanResultVisitor=new W500WanResultVisitor();
					row.accept(w500WanResultVisitor);
					if(StringUtils.isNotBlank(w500WanResultVisitor.getExpect())&&StringUtils.isNotBlank(w500WanResultVisitor.getOpencode())){
						reslutList.add(w500WanResultVisitor);
					}
				}
				for (W500WanResultVisitor w500WanResultVisitor2 : reslutList) {
					if(issueNumber.equals(w500WanResultVisitor2.getExpect())){
						return w500WanResultVisitor2.getOpencode();
					}
				}
			} catch (DocumentException e) {
				throw new DataException("处理远程开奖号码错误");
			}
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
		el11to5MissDataControllerServiceImpl.updateMissData();
	}

	@Override
	protected SchemeDao<El11to5Scheme> getSchemeDao() {
		// TODO Auto-generated method stub
		return schemeDao;
	}
	

}
