package com.cai310.utils;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;

import com.cai310.lottery.service.QueryService;
import com.cai310.spring.SpringContextHolder;
public class ExcelUtil {
	public static QueryService getQueryService() {
		if (queryService == null) {
			queryService = (QueryService) SpringContextHolder.getBean("queryService");
		}
		return queryService;
	}

	public static void setQueryService(QueryService queryService) {
		ExcelUtil.queryService = queryService;
	}

	private static QueryService queryService;
	
	 public static String excel(String name,LinkedList<String> biaotou,DetachedCriteria criteria,LinkedList<String> col) throws Exception {
	        //第一步，创建一个webbook，对应一个Excel文件
	        HSSFWorkbook wb = new HSSFWorkbook();
	        //第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
	        HSSFSheet sheet = wb.createSheet(name);
	        //第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
	        HSSFRow row = sheet.createRow((int)0);
	        //第四步，创建单元格，并设置值表头  设置表头居中
	        HSSFCellStyle style = wb.createCellStyle();
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建一个居中格式
	        
	     
	        for (int i = 0; i < biaotou.size(); i++) {
				String biaotouStr = biaotou.get(i);
				HSSFCell cell = row.createCell((short)i);
		        cell.setCellValue(biaotouStr); 
		        cell.setCellStyle(style);
				
			}
	        //第五步，写入实体数据 实际应用中这些数据从数据库得到，
	        List list = getQueryService().findByDetachedCriteria(criteria);
	        for (int j = 0; j < list.size(); j++) {
	        	Object object = list.get(j);
        		row = sheet.createRow(j+1);
	        	 for (int i = 0; i < col.size(); i++) {
	        		String colName = col.get(i); 
	        		colName ="get"+colName.substring(0,1).toUpperCase()+colName.substring(1);
	        		Method method = object.getClass().getMethod(colName);
	        		Object value = method.invoke(object);
	        		if(value instanceof String){ 
	    				row.createCell((short)i).setCellValue((String)value);
	    			}else if(value instanceof Double){ 
	    				row.createCell((short)i).setCellValue((Double)value);
	    			}else if(value instanceof Short){ 
	    				row.createCell((short)i).setCellValue((Short)value);
	    			}else if(value instanceof Integer){ 
	    				row.createCell((short)i).setCellValue((Integer)value);
	    			}else if(value instanceof Long){ 
	    				row.createCell((short)i).setCellValue((Long)value);
	    			}else if(value instanceof BigDecimal){ 
	    				row.createCell((short)i).setCellValue(value.toString());
	    			}else{
	    				if(null==value){
	    					row.createCell((short)i).setCellValue("");
	    				}else{
		    				row.createCell((short)i).setCellValue(value.toString());
	    				}
	    			}
				}
			}
	        //第六步，将文件存到指定位置
	        try {
	        	    HttpServletResponse response = Struts2Utils.getResponse();
		            String filename =  name+".xls" ; //设置下载时客户端Excel的名称   
		             // 请见：http://zmx.javaeye.com/blog/622529   
		            response.setContentType( "application/vnd.ms-excel" );   
		            response.setHeader( "Content-disposition" ,  "attachment;filename="  + filename);   
		            OutputStream ouputStream = response.getOutputStream();   
		            wb.write(ouputStream);   
		            ouputStream.flush();   
		            ouputStream.close();   
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return null;
	    }
}
