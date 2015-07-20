package com.cai310.lottery.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.zc.SfzcMatch;
import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.utils.DateUtil;
public class SfzcDataToXMLUtil { 
 /** 
  * @param list 期列表
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createSfzcPeriodDataXml(Period period,SfzcPeriodData sfzcPeriodData,SfzcMatch[] sfzcMatchs,String dir,String fileName){
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
		  Document document = initialDocument(period,sfzcPeriodData,sfzcMatchs);
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
 * <b>方法描述</b>： 初始化足彩的xml格式
 * @return 返回初始化完成对象
 */
 private static Document initialDocument(Period period,SfzcPeriodData sfzcPeriodData,SfzcMatch[] sfzcMatchs) {
	 Document document = DocumentHelper.createDocument();
	 /** 建立XML文档的根 */
	 Element rootElement = document.addElement("data");
	 addDocument(period,rootElement,sfzcPeriodData,sfzcMatchs);
	 return document;
 }
 private static void addDocument(Period period,Element rootElement,SfzcPeriodData sfzcPeriodData,SfzcMatch[] sfzcMatchs){
	 Element periodData = rootElement.addElement("periodData");
	 Element matchs = rootElement.addElement("matchs");
	 Element time = rootElement.addElement("time");
	 time.setText(DateUtil.dateToStr(new Date()));
	 
	 Element startTime = rootElement.addElement("startTime");
	 startTime.setText(DateUtil.dateToStr(period.getStartTime()));
	 
	 Element endTime = rootElement.addElement("endTime");
	 endTime.setText(DateUtil.dateToStr(period.getEndedTime()));
	 
	 Element firstPrize = periodData.addElement("firstPrize_9");
	 firstPrize.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getFirstPrize()));
	 
	 Element firstWinUnits = periodData.addElement("firstWinUnits_9");
	 firstWinUnits.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getFirstWinUnits()));
	 
	 Element firstPrize_14 = periodData.addElement("firstPrize_14");
	 firstPrize_14.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getFirstPrize_14()));
	 
	 Element firstWinUnits_14 = periodData.addElement("firstWinUnits_14");
	 firstWinUnits_14.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getFirstWinUnits_14()));
	 
	 Element secondPrize_14 = periodData.addElement("secondPrize_14");
	 secondPrize_14.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getSecondPrize_14()));
	 
	 Element secondWinUnits_14 = periodData.addElement("secondWinUnits_14");
	 secondWinUnits_14.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getSecondWinUnits_14()));
	 
	 Element prizePool = periodData.addElement("prizePool_9");
	 prizePool.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getPrizePool()));
	 
	 Element prizePool_14 = periodData.addElement("prizePool_14");
	 prizePool_14.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getPrizePool_14()));
	 
	 Element totalSales = periodData.addElement("totalSales_9");
	 totalSales.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getTotalSales()));
	 
	 Element totalSales_14 = periodData.addElement("totalSales_14");
	 totalSales_14.setText(PasscountUtil.trimToEmpty(sfzcPeriodData.getTotalSales_14()));
	 for (SfzcMatch sfzcMatch : sfzcMatchs) {
		 Element match = matchs.addElement("match");
		 
		 Element lineId = match.addElement("lineId");
		 lineId.setText(PasscountUtil.trimToEmpty(sfzcMatch.getLineId()));
		 
		 Element homeTeamName = match.addElement("homeTeamName");
		 homeTeamName.setText(PasscountUtil.trimToEmpty(sfzcMatch.getHomeTeamName()));
		 
		 Element guestTeamName = match.addElement("guestTeamName");
		 guestTeamName.setText(PasscountUtil.trimToEmpty(sfzcMatch.getGuestTeamName()));
		 
		 Element result = match.addElement("result");
		 result.setText(PasscountUtil.trimToEmpty(sfzcMatch.getResult()));
	}
 }
 
}

