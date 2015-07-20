package com.cai310.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;


public class WriteHTMLUtil {
	protected static Logger logger = LoggerFactory.getLogger(WriteHTMLUtil.class);
    public static void writeHtm(String dir,String fileName,String value,String charString){
 		File dirFile = null;
 		File file = null;
 		FileOutputStream fos=null;
 		BufferedOutputStream bos=null;
 		DataOutputStream dos =null;
 		String context=Constant.ROOTPATH;
 		try {
 				    	dirFile = new File(combinePath(context,dir));
 				    	if (!dirFile.exists()) {
 							dirFile.mkdirs();
 						}
 				 		file = new File(combinePath(context,dir+fileName));
 				 		if (!file.exists()) {
 				 			file.createNewFile();
 						}
 						fos=new FileOutputStream(file);
 						bos=new BufferedOutputStream(fos);
 						dos=new DataOutputStream(bos);
 					    dos.write(value.getBytes(charString));
 					    dos.flush();
 		}
 		catch (IOException e) {
 			e.printStackTrace();
 			logger.error(e.getMessage());
 		} finally {
 			try {
 				if(null!=dos)dos.flush();
 				if(null!=fos)fos.close();
 				if(null!=bos)bos.close();
 				if(null!=dos)dos.close();
 	 		}
 	 	    catch (IOException e){
 	 			e.printStackTrace();
 	 			logger.error(e.getMessage());
 	 		}   
	   }
 		
 	}
    public static String readFile(String dir,String fileName){
 		File file = null;
 		BufferedReader reader = null;
 		String context=Constant.ROOTPATH;
 		try {
 				file = new File(combinePath(context,dir+fileName));
 				if (!file.exists()) {
 				 	return null;
 				}
 				reader = new BufferedReader(new FileReader(file));
 				String tempString = null;
 				// 一次读入一行，直到读入null为文件结束
 				StringBuffer sb = new StringBuffer();
 				while ((tempString = reader.readLine()) != null) {
 					sb.append(tempString);
 					
 			    }
 			    reader.close();
 			    return sb.toString();
 		}
 		catch (IOException e) {
 			e.printStackTrace();
 			logger.error(e.getMessage());
 		} finally {
 			try {
 				if(null!=reader)reader.close();
 	 		}
 	 	    catch (IOException e){
 	 			e.printStackTrace();
 	 			logger.error(e.getMessage());
 	 		}   
	   }
 		return null;
 		
 	}
	public static final String combinePath(String base,String child){
        base = base.trim();
        child= child.trim();
        if(base.endsWith("/")||base.endsWith("\\"))
        {
            if(child.startsWith("/")||child.startsWith("\\"))
                return base+ child.substring(1);
            else
                return base+child;
        }
        else{
            if(child.startsWith("/")||child.startsWith("\\") )
                return base+child;
            else
                return base+'/'+child;
        }
    }	
		
}