package com.cai310.utils;

import org.apache.commons.httpclient.methods.PostMethod;


  
//Inner class for UTF-8 support  
public class UTF8PostMethod extends PostMethod{  
    public UTF8PostMethod(String url){  
        super(url);  
    }  
    @Override  
    public String getRequestCharSet() {  
        return "UTF-8";  
    }  
    
}  