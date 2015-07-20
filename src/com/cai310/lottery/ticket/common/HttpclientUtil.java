package com.cai310.lottery.ticket.common;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.utils.GBKPostMethod;
import com.cai310.utils.UTF8PostMethod;

public class HttpclientUtil {
	protected static Logger logger = LoggerFactory.getLogger(HttpclientUtil.class);
	/**
	 * 
	 * @param reUrl  请求地址
	 * @param ParamMap 请求参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String Utf8HttpClientUtils(String reUrl, Map<String,String> ParamMap)throws ClientProtocolException, IOException  {
		org.apache.commons.httpclient.HttpClient httpClient;
		PostMethod postMethod = null;
		try{
			httpClient = new org.apache.commons.httpclient.HttpClient();
			HttpClientParams p = new HttpClientParams();
			p.setSoTimeout(10000);
			p.setIntParameter(HttpConnectionParams.SO_TIMEOUT,10000); //超时设置
			p.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时 
			httpClient.setParams(p);
			postMethod = new UTF8PostMethod(reUrl);
		    
			// HTTP参数
			org.apache.commons.httpclient.NameValuePair[] postData = new org.apache.commons.httpclient.NameValuePair[ParamMap.keySet().size()];
			int i =0;
			for (String key : ParamMap.keySet()) {
				String value = ParamMap.get(key);
				postData[i] = new org.apache.commons.httpclient.NameValuePair(key,value);
				i++;
			}
			postMethod.setRequestBody(postData);  
			httpClient.executeMethod(postMethod);
			String reStr = postMethod.getResponseBodyAsString();
			logger.debug("回传数据:"+reStr);
			return reStr;

		}catch(Exception e){
			logger.error("请求出错:"+e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}    
		return null;
    
	}
	/**
	 * 
	 * @param reUrl  请求地址
	 * @param ParamMap 请求参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String GBKHttpClientUtils(String reUrl, Map<String,String> ParamMap)throws ClientProtocolException, IOException  {
		org.apache.commons.httpclient.HttpClient httpClient;
		PostMethod postMethod = null;
		try{
			httpClient = new org.apache.commons.httpclient.HttpClient();
			HttpClientParams p = new HttpClientParams();
			p.setSoTimeout(200000);
			p.setIntParameter(HttpConnectionParams.SO_TIMEOUT,200000); //超时设置
			p.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 200000);//连接超时 
			httpClient.setParams(p);
			postMethod = new GBKPostMethod(reUrl);
			// HTTP参数
			if(null!=ParamMap){
				org.apache.commons.httpclient.NameValuePair[] postData = new org.apache.commons.httpclient.NameValuePair[ParamMap.keySet().size()];
				int i =0;
				for (String key : ParamMap.keySet()) {
					String value = ParamMap.get(key);
					postData[i] = new org.apache.commons.httpclient.NameValuePair(key,value);
					i++;
				}
				postMethod.setRequestBody(postData); 
			}
			httpClient.executeMethod(postMethod);
			String reStr = postMethod.getResponseBodyAsString();
			logger.debug("回传数据:"+reStr);
			postMethod.releaseConnection();
			return reStr;

		}catch(Exception e){
			e.printStackTrace();
			logger.error("请求出错:"+e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}    
		return null;
    
	}
}
