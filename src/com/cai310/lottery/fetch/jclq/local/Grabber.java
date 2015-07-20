package com.cai310.lottery.fetch.jclq.local;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.cai310.utils.HttpClientUtil;
import com.cai310.utils.JsonUtil;

public class Grabber {

	private Map<String, String> headers = new HashMap<String, String>();

	private String charset = "utf-8";

	private int requestTryTime = 0; //请求失败时，尝试次数

	private final static int MAXREQUESTTIME = 3;  //最大尝试次数
	
	private static DefaultHttpClient httpClient = null;
//	public Grabber(OnGrabberListener listener) {
//		this.listener = listener;
//	}
	
	public Grabber(){
		
	}
	public static DefaultHttpClient getHttpClient() {
		if (httpClient == null) {
			synchronized (HttpClientUtil.class){
				HttpParams params = new BasicHttpParams();
				params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000L);//超时设置
				params.setParameter(HttpConnectionParams.SO_TIMEOUT,20000); //超时设置
				params.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,20000);//连接超时 
				ConnManagerParams.setMaxTotalConnections(params, 50);
				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				SchemeRegistry schemeRegistry = new SchemeRegistry();
				schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
				httpClient = new DefaultHttpClient(cm, params);
			}
		}
		return httpClient;
	}
	
	public String grabberJson(String url) {
 
		HttpGet get = new HttpGet(url);
		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				get.addHeader(entry.getKey(), entry.getValue());
			}
		}
		DefaultHttpClient httpClient = getHttpClient();
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = IOUtils.toString(response.getEntity().getContent(), charset);
//				if (listener != null) {
//					listener.onSuccess(content);
//				}
				 
				return content;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (requestTryTime++ < MAXREQUESTTIME) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
				}
				return grabber(url);
			} else {
//				if (listener != null) {
//					listener.onFailed(e.getMessage());
//				}
			}
		}finally{
			get.abort();
		}
		return null;
	}

	public String grabber(String url) {
		return HttpClientUtil.getRemoteSource(url, "gbk");
//		HttpGet get = new HttpGet(url);
//		if (headers != null && !headers.isEmpty()) {
//			for (Entry<String, String> entry : headers.entrySet()) {
//				get.addHeader(entry.getKey(), entry.getValue());
//			}
//		}
//		DefaultHttpClient httpClient = getHttpClient();
//		try {
//			HttpResponse response = httpClient.execute(get);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				String content = IOUtils.toString(response.getEntity().getContent(), charset);
////				if (listener != null) {
////					listener.onSuccess(content);
////				}
//				 
//				return content;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (requestTryTime++ < MAXREQUESTTIME) {
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e1) {
//				}
//				return grabber(url);
//			} else {
////				if (listener != null) {
////					listener.onFailed(e.getMessage());
////				}
//			}
//		}
//		return null;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void addHeaders(String name, String value) {
		headers.put(name, value);
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	public static void main(String[] args) {
		Grabber g = new Grabber();
		String url ="http://www.okooo.com/ajax/?method=odds.sporttery.endodds&format=json&LotteryNo=2013-09-08&act=get_yapan&index=5&LotteryType=SportterySoccerMix&v=dfe36fceb8d3c6905df7e54fdd180c2e1378690007";
		String content = g.grabber(url);
		Map obj =  JsonUtil.getMap4Json(content);
		String temp = String.valueOf(obj.get("sporttery_endodds_response"));
		Map<String,String> obj1 = JsonUtil.getMap4Json(temp);
		for (Map.Entry<String, String> odds : obj1.entrySet()) {
			
		String matchKey = odds.getKey();
		String odd = String.valueOf(odds.getValue());
		if(StringUtils.isNotBlank(odd)){
				String[] items = JsonUtil.getStringArray4Json(odd);
				int i =0;
			}	 
		}
 
		 
	}
}
