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
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.LczcPeriodData;
import com.cai310.utils.DateUtil;
public class LczcDataToXMLUtil { 
 /** 
  * @param list 期列表
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createLczcPeriodDataXml(Period period,LczcPeriodData lczcPeriodData,LczcMatch[] lczcMatchs,String dir,String fileName){
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
		  Document document = initialDocument(period,lczcPeriodData,lczcMatchs);
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
 private static Document initialDocument(Period period,LczcPeriodData lczcPeriodData,LczcMatch[] lczcMatchs) {
	 Document document = DocumentHelper.createDocument();
	 /** 建立XML文档的根 */
	 Element rootElement = document.addElement("data");
	 addDocument(period,rootElement,lczcPeriodData,lczcMatchs);
	 return document;
 }
 private static void addDocument(Period period,Element rootElement,LczcPeriodData lczcPeriodData,LczcMatch[] lczcMatchs){
	 Element periodData = rootElement.addElement("periodData");
	 Element matchs = rootElement.addElement("matchs");
	 Element time = rootElement.addElement("time");
	 time.setText(DateUtil.dateToStr(new Date()));
	 
	 Element startTime = rootElement.addElement("startTime");
	 startTime.setText(DateUtil.dateToStr(period.getStartTime()));
	 
	 Element endTime = rootElement.addElement("endTime");
	 endTime.setText(DateUtil.dateToStr(period.getEndedTime()));
	 
	 Element firstPrize = periodData.addElement("firstPrize");
	 firstPrize.setText(PasscountUtil.trimToEmpty(lczcPeriodData.getFirstPrize()));
	 
	 Element firstWinUnits = periodData.addElement("firstWinUnits");
	 firstWinUnits.setText(PasscountUtil.trimToEmpty(lczcPeriodData.getFirstWinUnits()));
	 
	 
	 Element prizePool = periodData.addElement("prizePool");
	 prizePool.setText(PasscountUtil.trimToEmpty(lczcPeriodData.getPrizePool()));
	 
	 Element totalSales = periodData.addElement("totalSales");
	 totalSales.setText(PasscountUtil.trimToEmpty(lczcPeriodData.getTotalSales()));
	 
	 for (LczcMatch lczcMatch : lczcMatchs) {
		 Element match = matchs.addElement("match");
		 
		 Element lineId = match.addElement("lineId");
		 lineId.setText(PasscountUtil.trimToEmpty(lczcMatch.getLineId()));
		 
		 Element homeTeamName = match.addElement("homeTeamName");
		 homeTeamName.setText(PasscountUtil.trimToEmpty(lczcMatch.getHomeTeamName()));
		 
		 Element guestTeamName = match.addElement("guestTeamName");
		 guestTeamName.setText(PasscountUtil.trimToEmpty(lczcMatch.getGuestTeamName()));
		 
		 Element halfResult = match.addElement("halfResult");
		 halfResult.setText(PasscountUtil.trimToEmpty(lczcMatch.getHalfResult()));
		 
		 Element fullResult = match.addElement("fullResult");
		 fullResult.setText(PasscountUtil.trimToEmpty(lczcMatch.getResult()));
	}
 }
 
}

