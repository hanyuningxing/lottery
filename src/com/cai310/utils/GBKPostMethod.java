package com.cai310.utils;

import org.apache.commons.httpclient.methods.PostMethod;


  
//Inner class for UTF-8 support  
public class GBKPostMethod extends PostMethod{  
    public GBKPostMethod(String url){  
        super(url);  
    }  
    @Override  
    public String getRequestCharSet() {  
        return "GBK";  
    }  
    
}  