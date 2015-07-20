package com.cai310.lottery.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.ssq.SsqPasscount;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.utils.DateUtil;
/** 
 * <title>使用XML文件存取可序列化的对象的类</title> 
 * <description>提供保存和读取的方法</description> 
 */ 
public class SsqPasscountToXMLUtil { 
 /** 
  * @param list 过关列表
  * @param playType 玩法
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createSsqPasscountXml(List<SsqPasscount> list,String dir,String fileName){
	 File dirFile = null;
	 File file = null;
	 FileOutputStream fos=null;
	 XMLWriter writer = null;
	 try {
	 	  String context=Constant.ROOTPATH;
		  dirFile = new File(CommonUtil.combinePath(context,dir));
	  	  if (!dirFile.exists()) {
				dirFile.mkdirs();
		  }
		  file = new File(CommonUtil.combinePath(context,dir+fileName));
		  if (!file.exists()) {
				file.createNewFile();
		  }
		  //创建文件输出流 
		  fos = new FileOutputStream(file); 
		  OutputFormat format = OutputFormat.createPrettyPrint();
		    // 默认为Utf-8 编码，可以根据需要改变编码格式
		    // format.setEncoding("GBK");
		    /** 将document中的内容写入文件中 */
		  Document document = initialDocument(list);
		  writer = new XMLWriter(fos, format);
		  writer.write(document);
		  writer.flush();
		  writer.close();
		  fos.close();     
	    }catch (IOException e) {
	 			e.printStackTrace();
	 	} finally {
	 		try {
	 			if(null!=writer){
	 				writer.close();
		 			writer=null;
	 			}
	 			if(null!=fos){
		 			fos.close();
		 			fos=null; 
	 			}
	 	 	}catch (IOException e){
	 	 	    e.printStackTrace();
	 	 	}   
		}
 }  
 /** 
  * @param list 过关列表
  * @param playType 玩法
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createSsqPeriodDataXml(Period period,SsqPeriodData ssqPeriodData,String dir,String fileName){
	 File dirFile = null;
	 File file = null;
	 FileOutputStream fos=null;
	 XMLWriter writer = null;
	 try {
	 	  String context=Constant.ROOTPATH;
		  dirFile = new File(CommonUtil.combinePath(context,dir));
	  	  if (!dirFile.exists()) {
				dirFile.mkdirs();
		  }
		  file = new File(CommonUtil.combinePath(context,dir+fileName));
		  if (!file.exists()) {
				file.createNewFile();
		  }
		  //创建文件输出流 
		  fos = new FileOutputStream(file); 
		  OutputFormat format = OutputFormat.createPrettyPrint();
		    // 默认为Utf-8 编码，可以根据需要改变编码格式
		    // format.setEncoding("GBK");
		    /** 将document中的内容写入文件中 */
		  Document document = initialPeriodDocument(period,ssqPeriodData);
		  writer = new XMLWriter(fos, format);
		  writer.write(document);
		  writer.flush();
		  writer.close();
		  fos.close();     
	    }catch (IOException e) {
	 			e.printStackTrace();
	 	} finally {
	 		try {
	 			if(null!=writer){
	 				writer.close();
		 			writer=null;
	 			}
	 			if(null!=fos){
		 			fos.close();
		 			fos=null; 
	 			}
	 	 	}catch (IOException e){
	 	 	    e.printStackTrace();
	 	 	}   
		}
 }  
 /**
  * <b>方法描述</b>： 初始化xml格式
  * @return 返回初始化完成对象
  */
  private static Document initialPeriodDocument(Period period,SsqPeriodData ssqPeriodData) {
	  Document document = DocumentHelper.createDocument();
		 /** 建立XML文档的根 */
	  Element rootElement = document.addElement("data");
	  addPeriodDocument(rootElement,period, ssqPeriodData);
	  return document;
  }
  private static void addPeriodDocument(Element rootElement,Period period,SsqPeriodData ssqPeriodData){
	     Element periodData = rootElement.addElement("periodData");
	     
		 Element time = rootElement.addElement("time");
		 time.setText(DateUtil.dateToStr(new Date()));
		 
		 Element startTime = rootElement.addElement("startTime");
		 startTime.setText(DateUtil.dateToStr(period.getStartTime()));
		 
		 
		 Element endTime = rootElement.addElement("endTime");
		 endTime.setText(DateUtil.dateToStr(period.getEndedTime()));
		 
		 Element firstPrize = periodData.addElement("firstPrize");
		 firstPrize.setText(PasscountUtil.trimToEmpty(ssqPeriodData.getPrize().getFirstPrize()));
		 
		 Element firstWinUnit = periodData.addElement("firstWinUnit");
		 firstWinUnit.setText(PasscountUtil.trimToEmpty(ssqPeriodData.getWinUnit().getFirstWinUnits()));
		 
		 Element secondPrize = periodData.addElement("secondPrize");
		 secondPrize.setText(PasscountUtil.trimToEmpty(ssqPeriodData.getPrize().getSecondPrize()));
		 
		 Element secondWinUnit = periodData.addElement("secondWinUnit");
		 secondWinUnit.setText(PasscountUtil.trimToEmpty(ssqPeriodData.getWinUnit().getSecondWinUnits()));
		 
		 
		 Element prizePool = periodData.addElement("prizePool");
		 prizePool.setText(PasscountUtil.trimToEmpty(ssqPeriodData.getPrizePool()));
		 
		 Element totalSales = periodData.addElement("totalSales");
		 totalSales.setText(PasscountUtil.trimToEmpty(ssqPeriodData.getTotalSales()));
		 
		 Element result = periodData.addElement("result");
		 result.setText(PasscountUtil.trimToEmpty(ssqPeriodData.getResult()));
		 
  }
/**
 * <b>方法描述</b>： 初始化xml格式
 * @return 返回初始化完成对象
 */
 private static Document initialDocument(List<SsqPasscount> list) {
	 Document document = DocumentHelper.createDocument();
	 /** 建立XML文档的根 */
	 Element passcountListElement = document.addElement("list");
	 Element passcountElement;
	 for (SsqPasscount ssqSchemeWonInfo : list) {
		 passcountElement = passcountListElement.addElement("passcount");
		 addDocument(passcountElement,ssqSchemeWonInfo);
	 }
	 return document;
 }
 private static void addDocument(Element element,SsqPasscount ssqSchemeWonInfo){
	 Element firstWinUnits = element.addElement("firstWinUnits");
	 firstWinUnits.setText(PasscountUtil.trimNumToEmpty(ssqSchemeWonInfo.getWinUnit().getFirstWinUnits()));
	 
	 
	 Element secondWinUnits = element.addElement("secondWinUnits");
	 secondWinUnits.setText(PasscountUtil.trimNumToEmpty(ssqSchemeWonInfo.getWinUnit().getSecondWinUnits()));
	 
	 Element thirdWinUnits = element.addElement("thirdWinUnits");
	 thirdWinUnits.setText(PasscountUtil.trimNumToEmpty(ssqSchemeWonInfo.getWinUnit().getThirdWinUnits()));
	 
	 Element fourthWinUnits = element.addElement("fourthWinUnits");
	 fourthWinUnits.setText(PasscountUtil.trimNumToEmpty(ssqSchemeWonInfo.getWinUnit().getFourthWinUnits()));
	 
	 Element fifthWinUnits = element.addElement("fifthWinUnits");
	 fifthWinUnits.setText(PasscountUtil.trimNumToEmpty(ssqSchemeWonInfo.getWinUnit().getFifthWinUnits()));
	 
	 Element sixthWinUnits = element.addElement("sixthWinUnits");
	 sixthWinUnits.setText(PasscountUtil.trimNumToEmpty(ssqSchemeWonInfo.getWinUnit().getSixthWinUnits()));
	 
	 
	 Element shareType = element.addElement("shareType");
	 shareType.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getShareType().ordinal()));
	 
	 
	 Element mode = element.addElement("mode");
	 mode.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getMode().ordinal()));
	 
	 Element multiple = element.addElement("multiple");
	 multiple.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getMultiple()));
	 
	 Element periodId = element.addElement("periodId");
	 periodId.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getPeriodId()));
	 
	 Element schemeCost = element.addElement("schemeCost");
	 schemeCost.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getSchemeCost()));
	 
	 Element schemeId = element.addElement("schemeId");
	 schemeId.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getSchemeId()));
	 
	 Element sponsorId = element.addElement("sponsorId");
	 sponsorId.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getSponsorId()));
	 
	 Element sponsorName = element.addElement("sponsorName");
	 sponsorName.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getSponsorName()));
	 
	 Element state = element.addElement("state");
	 state.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getState().ordinal()));
	 
	 Element units = element.addElement("units");
	 units.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getUnits()));
	 
	 Element version = element.addElement("version");
	 version.setText(PasscountUtil.trimToEmpty(ssqSchemeWonInfo.getVersion()));
 }
}

