package com.cai310.lottery.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.zc.ZcPasscount;
/** 
 * <title>使用XML文件存取可序列化的对象的类</title> 
 * <description>提供保存和读取的方法</description> 
 */ 
public class ZcPasscountToXMLUtil { 
 /** 
  * @param list 过关列表
  * @param playType 玩法
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createZcPasscountXml(List<ZcPasscount> list,String dir,String fileName){
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
		  Document document = initialZcDocument(list);
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
 private static Document initialZcDocument(List<ZcPasscount> list) {
	 Document document = DocumentHelper.createDocument();
	 /** 建立XML文档的根 */
	 Element passcountListElement = document.addElement("list");
	 Element passcountElement;
	 for (ZcPasscount zcSchemeWonInfo : list) {
		 passcountElement = passcountListElement.addElement("passcount");
	     addZcDocument(passcountElement,zcSchemeWonInfo);
	 }
	 return document;
 }
 private static void addZcDocument(Element element,ZcPasscount zcSchemeWonInfo){
	 Element lost0Element = element.addElement("lost0");
	 lost0Element.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getLost0()));
	 
	 Element lost1Element = element.addElement("lost1");
	 lost1Element.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getLost1()));
	 
	 Element lost2Element = element.addElement("lost2");
	 lost2Element.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getLost2()));
	 
	 Element lost3Element = element.addElement("lost3");
	 lost3Element.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getLost3()));
	 
	 Element mode = element.addElement("mode");
	 mode.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getMode().ordinal()));
	 
	 Element multiple = element.addElement("multiple");
	 multiple.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getMultiple()));
	 
	 Element periodId = element.addElement("periodId");
	 periodId.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getPeriodId()));
	 
	 Element schemeCost = element.addElement("schemeCost");
	 schemeCost.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getSchemeCost()));
	 
	 Element schemeId = element.addElement("schemeId");
	 schemeId.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getSchemeId()));
	 
	 Element sponsorId = element.addElement("sponsorId");
	 sponsorId.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getSponsorId()));
	 
	 Element sponsorName = element.addElement("sponsorName");
	 sponsorName.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getSponsorName()));
	 
	 Element state = element.addElement("state");
	 state.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getState().ordinal()));
	 
	 Element units = element.addElement("units");
	 units.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getUnits()));
	 
	 Element prize = element.addElement("prize");
	 prize.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getSchemePrize()));
	 
	 Element passcount = element.addElement("pc");
	 passcount.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getPasscount()));
	 
	 Element version = element.addElement("version");
	 version.setText(PasscountUtil.trimToEmpty(zcSchemeWonInfo.getVersion()));
 }
}

