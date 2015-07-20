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
import com.cai310.lottery.entity.lottery.dlt.DltPasscount;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.utils.DateUtil;
/** 
 * <title>使用XML文件存取可序列化的对象的类</title> 
 * <description>提供保存和读取的方法</description> 
 */ 
public class DltPasscountToXMLUtil { 
 /** 
  * @param list 过关列表
  * @param playType 玩法
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createDltPasscountXml(List<DltPasscount> list,String dir,String fileName){
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
 public static void createDltPeriodDataXml(Period period,DltPeriodData dltPeriodData,String dir,String fileName){
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
		  Document document = initialPeriodDocument(period,dltPeriodData);
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
  private static Document initialPeriodDocument(Period period,DltPeriodData dltPeriodData) {
	  Document document = DocumentHelper.createDocument();
		 /** 建立XML文档的根 */
	  Element rootElement = document.addElement("data");
	  addPeriodDocument(rootElement,period, dltPeriodData);
	  return document;
  }
  private static void addPeriodDocument(Element rootElement,Period period,DltPeriodData dltPeriodData){
	     Element periodData = rootElement.addElement("periodData");
	     
		 Element time = rootElement.addElement("time");
		 time.setText(DateUtil.dateToStr(new Date()));
		 
		 Element startTime = rootElement.addElement("startTime");
		 startTime.setText(DateUtil.dateToStr(period.getStartTime()));
		 
		 
		 Element endTime = rootElement.addElement("endTime");
		 endTime.setText(DateUtil.dateToStr(period.getEndedTime()));
		 
		 Element firstWinUnit = periodData.addElement("firstWinUnit");
		 firstWinUnit.setText(PasscountUtil.trimToEmpty(dltPeriodData.getWinUnit().getFirstWinUnits()));
		 
		 Element firstPrize = periodData.addElement("firstPrize");
		 firstPrize.setText(PasscountUtil.trimToEmpty(dltPeriodData.getPrize().getFirstPrize()));
		 
		 Element secondWinUnit = periodData.addElement("secondWinUnit");
		 secondWinUnit.setText(PasscountUtil.trimToEmpty(dltPeriodData.getWinUnit().getSecondWinUnits()));
		 
		 Element secondPrize = periodData.addElement("secondPrize");
		 secondPrize.setText(PasscountUtil.trimToEmpty(dltPeriodData.getPrize().getSecondPrize()));
		 
		 Element prizePool = periodData.addElement("prizePool");
		 prizePool.setText(PasscountUtil.trimToEmpty(dltPeriodData.getPrizePool()));
		 
		 Element totalSales = periodData.addElement("totalSales");
		 totalSales.setText(PasscountUtil.trimToEmpty(dltPeriodData.getTotalSales()));
		 
		 Element result = periodData.addElement("result");
		 result.setText(PasscountUtil.trimToEmpty(dltPeriodData.getResult()));
		 
  }
/**
 * <b>方法描述</b>： 初始化xml格式
 * @return 返回初始化完成对象
 */
 private static Document initialDocument(List<DltPasscount> list) {
	 Document document = DocumentHelper.createDocument();
	 /** 建立XML文档的根 */
	 Element passcountListElement = document.addElement("list");
	 Element passcountElement;
	 for (DltPasscount dltSchemeWonInfo : list) {
		 passcountElement = passcountListElement.addElement("passcount");
		 addDocument(passcountElement,dltSchemeWonInfo);
	 }
	 return document;
 }
 private static void addDocument(Element element,DltPasscount dltSchemeWonInfo){
	 Element firstWinUnits = element.addElement("firstWinUnits");
	 firstWinUnits.setText(PasscountUtil.trimNumToEmpty(dltSchemeWonInfo.getWinUnit().getFirstWinUnits()));
	 
	 
	 Element secondWinUnits = element.addElement("secondWinUnits");
	 secondWinUnits.setText(PasscountUtil.trimNumToEmpty(dltSchemeWonInfo.getWinUnit().getSecondWinUnits()));
	 
	 Element thirdWinUnits = element.addElement("thirdWinUnits");
	 thirdWinUnits.setText(PasscountUtil.trimNumToEmpty(dltSchemeWonInfo.getWinUnit().getThirdWinUnits()));
	 
	 Element fourthWinUnits = element.addElement("fourthWinUnits");
	 fourthWinUnits.setText(PasscountUtil.trimNumToEmpty(dltSchemeWonInfo.getWinUnit().getFourthWinUnits()));
	 
	 Element fifthWinUnits = element.addElement("fifthWinUnits");
	 fifthWinUnits.setText(PasscountUtil.trimNumToEmpty(dltSchemeWonInfo.getWinUnit().getFifthWinUnits()));
	 
	 Element sixthWinUnits = element.addElement("sixthWinUnits");
	 sixthWinUnits.setText(PasscountUtil.trimNumToEmpty(dltSchemeWonInfo.getWinUnit().getSixthWinUnits()));
	 
	 Element seventhWinUnits = element.addElement("seventhWinUnits");
	 seventhWinUnits.setText(PasscountUtil.trimNumToEmpty(dltSchemeWonInfo.getWinUnit().getSeventhWinUnits()));
	 
	 Element eighthWinUnits = element.addElement("eighthWinUnits");
	 eighthWinUnits.setText(PasscountUtil.trimNumToEmpty(dltSchemeWonInfo.getWinUnit().getEighthWinUnits()));
	 
	 Element select12to2WinUnits = element.addElement("select12to2WinUnits");
	 select12to2WinUnits.setText(PasscountUtil.trimNumToEmpty(dltSchemeWonInfo.getWinUnit().getSelect12to2WinUnits()));
	 
	 Element shareType = element.addElement("shareType");
	 shareType.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getShareType().ordinal()));
	 
	 Element mode = element.addElement("mode");
	 mode.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getMode().ordinal()));
	 
	 Element multiple = element.addElement("multiple");
	 multiple.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getMultiple()));
	 
	 Element periodId = element.addElement("periodId");
	 periodId.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getPeriodId()));
	 
	 Element schemeCost = element.addElement("schemeCost");
	 schemeCost.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getSchemeCost()));
	 
	 Element schemeId = element.addElement("schemeId");
	 schemeId.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getSchemeId()));
	 
	 Element sponsorId = element.addElement("sponsorId");
	 sponsorId.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getSponsorId()));
	 
	 Element sponsorName = element.addElement("sponsorName");
	 sponsorName.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getSponsorName()));
	 
	 Element state = element.addElement("state");
	 state.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getState().ordinal()));
	 
	 Element units = element.addElement("units");
	 units.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getUnits()));
	 
	 Element playType = element.addElement("playType");
	 playType.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getPlayType().ordinal()));
	 
	 Element version = element.addElement("version");
	 version.setText(PasscountUtil.trimToEmpty(dltSchemeWonInfo.getVersion()));
 }
}

