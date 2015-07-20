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
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPasscount;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.utils.DateUtil;

/** 
 * <title>使用XML文件存取可序列化的对象的类</title> 
 * <description>提供保存和读取的方法</description> 
 */ 
public class Welfare3dPasscountToXMLUtil { 
 /** 
  * @param list 过关列表
  * @param playType 玩法
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createWelfare3dPasscountXml(List<Welfare3dPasscount> list,String dir,String fileName){
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
 public static void createWelfare3dPeriodDataXml(Period period,Welfare3dPeriodData welfare3dPeriodData,String dir,String fileName){
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
		  Document document = initialPeriodDocument(period,welfare3dPeriodData);
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
  private static Document initialPeriodDocument(Period period,Welfare3dPeriodData welfare3dPeriodData) {
	  Document document = DocumentHelper.createDocument();
		 /** 建立XML文档的根 */
	  Element rootElement = document.addElement("data");
	  addPeriodDocument(rootElement,period, welfare3dPeriodData);
	  return document;
  }
  private static void addPeriodDocument(Element rootElement,Period period,Welfare3dPeriodData welfare3dPeriodData){
	     Element periodData = rootElement.addElement("periodData");
	     
		 Element time = rootElement.addElement("time");
		 time.setText(DateUtil.dateToStr(new Date()));
		 
		 Element startTime = rootElement.addElement("startTime");
		 startTime.setText(DateUtil.dateToStr(period.getStartTime()));		 
		 
		 Element endTime = rootElement.addElement("endTime");
		 endTime.setText(DateUtil.dateToStr(period.getEndedTime()));	
		 
		 Element totalSales = periodData.addElement("totalSales");
		 totalSales.setText(PasscountUtil.trimToEmpty(welfare3dPeriodData.getTotalSales()));
		 
		 Element result = periodData.addElement("result");
		 result.setText(PasscountUtil.trimToEmpty(welfare3dPeriodData.getResult()));
		 
  }
/**
 * <b>方法描述</b>： 初始化xml格式
 * @return 返回初始化完成对象
 */
 private static Document initialDocument(List<Welfare3dPasscount> list) {
	 Document document = DocumentHelper.createDocument();
	 /** 建立XML文档的根 */
	 Element passcountListElement = document.addElement("list");
	 Element passcountElement;
	 for (Welfare3dPasscount welfare3dSchemeWonInfo : list) {
		 passcountElement = passcountListElement.addElement("passcount");
		 addDocument(passcountElement,welfare3dSchemeWonInfo);
	 }
	 return document;
 }
 private static void addDocument(Element element,Welfare3dPasscount welfare3dSchemeWonInfo){
	 
	 Element winUnits = element.addElement("winUnits");
	 winUnits.setText(PasscountUtil.trimNumToEmpty(welfare3dSchemeWonInfo.getWinUnit().getWinUnits()));
	 
	 Element g3WinUnits = element.addElement("g3WinUnits");
	 g3WinUnits.setText(PasscountUtil.trimNumToEmpty(welfare3dSchemeWonInfo.getWinUnit().getG3WinUnits()));
	 
	 Element g6WinUnits = element.addElement("g6WinUnits");
	 g6WinUnits.setText(PasscountUtil.trimNumToEmpty(welfare3dSchemeWonInfo.getWinUnit().getG6WinUnits()));
	 
	 Element shareType = element.addElement("shareType");
	 shareType.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getShareType().ordinal()));
	 
	 Element mode = element.addElement("mode");
	 mode.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getMode().ordinal()));
	 
	 Element multiple = element.addElement("multiple");
	 multiple.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getMultiple()));
	 
	 Element periodId = element.addElement("periodId");
	 periodId.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getPeriodId()));
	 
	 Element schemeCost = element.addElement("schemeCost");
	 schemeCost.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getSchemeCost()));
	 
	 Element schemeId = element.addElement("schemeId");
	 schemeId.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getSchemeId()));
	 
	 Element sponsorId = element.addElement("sponsorId");
	 sponsorId.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getSponsorId()));
	 
	 Element sponsorName = element.addElement("sponsorName");
	 sponsorName.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getSponsorName()));
	 
	 Element state = element.addElement("state");
	 state.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getState().ordinal()));
	 
	 Element units = element.addElement("units");
	 units.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getUnits()));
	 
	 Element playType = element.addElement("playType");
	 playType.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getPlayType()));
	 
	 Element version = element.addElement("version");
	 version.setText(PasscountUtil.trimToEmpty(welfare3dSchemeWonInfo.getVersion()));
 }
}

