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
import com.cai310.lottery.entity.lottery.Period;
/** 
 * <title>使用XML文件存取可序列化的对象的类</title> 
 * <description>提供保存和读取的方法</description> 
 */ 
public class DczcIssueListToXMLUtil { 
 /** 
  * @param list 期列表
  * @param dir 目录
  * @param fileName 文件名 
  */ 
 public static void createDczcIssueXml(List<Period> list,String dir,String fileName){
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
 * <b>方法描述</b>： 初始化北单的xml格式
 * @return 返回初始化完成对象
 */
 private static Document initialDocument(List<Period> list) {
	 Document document = DocumentHelper.createDocument();
	 /** 建立XML文档的根 */
	 Element rootElement = document.addElement("list");
	 for (Period period : list) {
		 addDocument(rootElement,period);
	 }
	 return document;
 }
 private static void addDocument(Element element,Period period){
	 Element periodNumber = element.addElement("periodNumber");
	 periodNumber.setText(PasscountUtil.trimToEmpty(period.getPeriodNumber()));
 }
 
 
}

