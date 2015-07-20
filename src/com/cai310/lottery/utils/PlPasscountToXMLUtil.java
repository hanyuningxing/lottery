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
import com.cai310.lottery.entity.lottery.pl.PlPasscount;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.utils.DateUtil;

/** 
 * <title>使用XML文件存取可序列化的对象的类</title> 
 * <description>提供保存和读取的方法</description> 
 */ 
public class PlPasscountToXMLUtil { 
 /** 
  * @param list 过关列表
  * @param playType 玩法
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createPlPasscountXml(List<PlPasscount> list,String dir,String fileName){
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
 public static void createPlPeriodDataXml(Period period,PlPeriodData plPeriodData,String dir,String fileName){
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
		  Document document = initialPeriodDocument(period,plPeriodData);
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
  private static Document initialPeriodDocument(Period period,PlPeriodData plPeriodData) {
	  Document document = DocumentHelper.createDocument();
		 /** 建立XML文档的根 */
	  Element rootElement = document.addElement("data");
	  addPeriodDocument(rootElement,period, plPeriodData);
	  return document;
  }
  private static void addPeriodDocument(Element rootElement,Period period,PlPeriodData plPeriodData){
	     Element periodData = rootElement.addElement("periodData");
	     
		 Element time = rootElement.addElement("time");
		 time.setText(DateUtil.dateToStr(new Date()));
		 
		 Element startTime = rootElement.addElement("startTime");
		 startTime.setText(DateUtil.dateToStr(period.getStartTime()));
		 		 
		 Element endTime = rootElement.addElement("endTime");
		 endTime.setText(DateUtil.dateToStr(period.getEndedTime()));		 
		 
		 Element totalSales = periodData.addElement("totalSales");
		 totalSales.setText(PasscountUtil.trimToEmpty(plPeriodData.getTotalSales()));
		 
		 Element p5TotalSales = periodData.addElement("p5TotalSales");
		 p5TotalSales.setText(PasscountUtil.trimToEmpty(plPeriodData.getP5TotalSales()));
		 
		 Element result = periodData.addElement("result");
		 result.setText(PasscountUtil.trimToEmpty(plPeriodData.getResult()));
		 
  }
/**
 * <b>方法描述</b>： 初始化xml格式
 * @return 返回初始化完成对象
 */
 private static Document initialDocument(List<PlPasscount> list) {
	 Document document = DocumentHelper.createDocument();
	 /** 建立XML文档的根 */
	 Element passcountListElement = document.addElement("list");
	 Element passcountElement;
	 for (PlPasscount plSchemeWonInfo : list) {
		 passcountElement = passcountListElement.addElement("passcount");
		 addDocument(passcountElement,plSchemeWonInfo);
	 }
	 return document;
 }
 private static void addDocument(Element element,PlPasscount plSchemeWonInfo){
	 if(plSchemeWonInfo.getPlayType().equals(PlPlayType.P5Direct)){
		 Element p5WinUnits = element.addElement("p5WinUnits");
		 p5WinUnits.setText(PasscountUtil.trimNumToEmpty(plSchemeWonInfo.getWinUnit().getP5WinUnits()));
		
		 Element shareType = element.addElement("shareType");
		 shareType.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getShareType().ordinal()));
		 
		 Element mode = element.addElement("mode");
		 mode.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getMode().ordinal()));
		 
		 Element multiple = element.addElement("multiple");
		 multiple.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getMultiple()));
		 
		 Element periodId = element.addElement("periodId");
		 periodId.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getPeriodId()));
		 
		 Element schemeCost = element.addElement("schemeCost");
		 schemeCost.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getSchemeCost()));
		 
		 Element schemeId = element.addElement("schemeId");
		 schemeId.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getSchemeId()));
		 
		 Element sponsorId = element.addElement("sponsorId");
		 sponsorId.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getSponsorId()));
		 
		 Element sponsorName = element.addElement("sponsorName");
		 sponsorName.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getSponsorName()));
		 
		 Element state = element.addElement("state");
		 state.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getState().ordinal()));
		 
		 Element units = element.addElement("units");
		 units.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getUnits()));
		 
		 Element playType = element.addElement("playType");
		 playType.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getPlayType()));
			 
		 Element version = element.addElement("version");
		 version.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getVersion()));
		 
	 }else if(plSchemeWonInfo.getPlayType().equals(PlPlayType.P3Direct)|plSchemeWonInfo.getPlayType().equals(PlPlayType.Group3)
			 |plSchemeWonInfo.getPlayType().equals(PlPlayType.Group6)|plSchemeWonInfo.getPlayType().equals(PlPlayType.DirectSum)
			 |plSchemeWonInfo.getPlayType().equals(PlPlayType.DirectSum)){
		 
		 Element p3WinUnits = element.addElement("p3WinUnits");
		 p3WinUnits.setText(PasscountUtil.trimNumToEmpty(plSchemeWonInfo.getWinUnit().getP3WinUnits()));
		 
		 Element p3G3WinUnits = element.addElement("p3G3WinUnits");
		 p3G3WinUnits.setText(PasscountUtil.trimNumToEmpty(plSchemeWonInfo.getWinUnit().getP3G3WinUnits())); 
		 
		 Element p3G6WinUnits = element.addElement("p3G6WinUnits");
		 p3G6WinUnits.setText(PasscountUtil.trimNumToEmpty(plSchemeWonInfo.getWinUnit().getP3G6WinUnits())); 
	
		 Element shareType = element.addElement("shareType");
		 shareType.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getShareType().ordinal()));
		 
		 Element mode = element.addElement("mode");
		 mode.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getMode().ordinal()));
		 
		 Element multiple = element.addElement("multiple");
		 multiple.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getMultiple()));
		 
		 Element periodId = element.addElement("periodId");
		 periodId.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getPeriodId()));
		 
		 Element schemeCost = element.addElement("schemeCost");
		 schemeCost.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getSchemeCost()));
		 
		 Element schemeId = element.addElement("schemeId");
		 schemeId.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getSchemeId()));
		 
		 Element sponsorId = element.addElement("sponsorId");
		 sponsorId.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getSponsorId()));
		 
		 Element sponsorName = element.addElement("sponsorName");
		 sponsorName.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getSponsorName()));
		 
		 Element state = element.addElement("state");
		 state.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getState().ordinal()));
		 
		 Element units = element.addElement("units");
		 units.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getUnits()));
		 
		 Element playType = element.addElement("playType");
		 playType.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getPlayType()));
			 
		 Element version = element.addElement("version");
		 version.setText(PasscountUtil.trimToEmpty(plSchemeWonInfo.getVersion()));
	 }	 	 
 }
}

