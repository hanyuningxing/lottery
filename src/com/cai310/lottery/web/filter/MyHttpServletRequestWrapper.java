package com.cai310.lottery.web.filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

import com.cai310.utils.HtmlTagFilter;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public MyHttpServletRequestWrapper(HttpServletRequest request) {
           super(request);
    }
	//获取表单上的值，并封装到map里
	public Map getParameterMap() {
	    Map map = new HashMap();
	    Enumeration en = this.getParameterNames();
	    while(en.hasMoreElements()){
		    String param = en.nextElement().toString();
		    if(StringUtils.isNotBlank(param)){
		    	String[] value = this.getParameterValues(param); 
		    	if(!"infoBeanForm.content".equals(param)){
		    		map.put(param, formatStringArr(value));
		    	}else{
		    		map.put(param, value);
		    	}
		    }
	    }
	    return map;
	}
	public String[] formatStringArr(String[] arr){
		 String[] newArr = new String[arr.length]; 
		 for (int i = 0; i < arr.length; i++) {
			 newArr[i] = HtmlTagFilter.Html2Text(arr[i]);
		 }
		 return newArr;
	}
}
