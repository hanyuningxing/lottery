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
import com.cai310.lottery.entity.lottery.dczc.BdzcSchemeWonInfo;
/** 
 * <title>使用XML文件存取可序列化的对象的类</title> 
 * <description>提供保存和读取的方法</description> 
 */ 
public class DczcPasscountToXMLUtil { 
 /** 
  * @param list 过关列表
  * @param playType 玩法
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createDczcPasscountXml(List<BdzcSchemeWonInfo> list,String dir,String fileName){
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
		  Document document = initialDczcDocument(list);
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
 private static Document initialDczcDocument(List<BdzcSchemeWonInfo> list) {	
	 Document document = DocumentHelper.createDocument();
	 /** 建立XML文档的根 */
	 Element passcountListElement = document.addElement("list");
	 Element passcountElement;
	 
	 for (BdzcSchemeWonInfo dczcSchemeWonInfo : list) {		 
		 passcountElement = passcountListElement.addElement("pc");
		 addDczcDocument(passcountElement,dczcSchemeWonInfo);    
	 }
	 return document;
 }
 private static void addDczcDocument(Element element,BdzcSchemeWonInfo dczcSchemeWonInfo){	 
	 Element mode = element.addElement("mode");
	 mode.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getMode().ordinal()));
	 
	 Element multiple = element.addElement("multiple");
	 multiple.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getMultiple()));
	 
	 Element periodId = element.addElement("periodId");
	 periodId.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getPeriodId()));
	 
	 Element schemeCost = element.addElement("schemeCost");
	 schemeCost.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getSchemeCost()));
	 
	 Element schemeId = element.addElement("schemeId");
	 schemeId.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getSchemeId()));
	 
	 Element sponsorId = element.addElement("sponsorId");
	 sponsorId.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getSponsorId()));
	 
	 Element sponsorName = element.addElement("sponsorName");
	 sponsorName.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getSponsorName()));
	 
	 Element state = element.addElement("state");
	 state.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getState().ordinal()));
	 
	 Element passMode = element.addElement("passMode");
	 passMode.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getPassMode()));
	 
	 Element passType = element.addElement("passType");
	 passType.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getPassType()));

	 Element betCount= element.addElement("betCount");
	 betCount.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getBetCount()));
	 
	 Element wonCount= element.addElement("wonCount");
	 wonCount.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getWonCount()));
	
	 Element passcount= element.addElement("passcount");
	 passcount.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getPasscount()));
	 
	 Element finsh= element.addElement("finsh");
	 finsh.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getFinsh()));
	 	 
	 Element shareType= element.addElement("shareType");
	 shareType.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getShareType()));
	 
	 Element units = element.addElement("units");
	 units.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getUnits()));
	 
	 Element prize = element.addElement("prize");
	 prize.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getSchemePrize()));
	 
	 Element version = element.addElement("version");
	 version.setText(PasscountUtil.trimToEmpty(dczcSchemeWonInfo.getVersion()));
 }
}

