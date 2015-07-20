package com.cai310.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	protected static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	private static DefaultHttpClient httpClient = null;
	private static DefaultHttpClient proxyClientHttpClient = null;

	protected  static List<String> useProxy = new ArrayList<String>();

	static {
		try {

			String contenStringt = "58.210.40.101:80"+","+
					"58.248.217.216:80"+","+
					"60.29.255.88:8000"+","+
					"222.83.160.45:8080"+","+
					"124.227.191.77:9000"+","+
					"58.248.217.209:80"+","+
					"58.248.217.195:80"+","+
					"222.92.141.155:8090";
//			String contenStringt = FileUtils.getFileContent(Thread.currentThread().getContextClassLoader()
//					.getResource("useProxy.txt").getPath());
//			String[] contentList = contenStringt.split("(\r\n|\n)+");
			String[] contentList = contenStringt.split(",");
//			System.out.println("代理剩余："+contentList.length);
			for (String content : contentList) {
				if (StringUtils.isNotBlank(content)) {
					useProxy.add(content);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("代理出错");
		}

	}

	public static DefaultHttpClient getHttpProxyClient() {
		if (proxyClientHttpClient == null) {
			synchronized (HttpClientUtil.class){
				HttpParams params = new BasicHttpParams();
				params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000L);//超时设置
				params.setParameter(HttpConnectionParams.SO_TIMEOUT,10000); //超时设置
				params.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,10000);//连接超时 
				ConnManagerParams.setMaxTotalConnections(params, 50);
				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	
				SchemeRegistry schemeRegistry = new SchemeRegistry();
				schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
				proxyClientHttpClient = new DefaultHttpClient(cm, params);
			}
		}
		return proxyClientHttpClient;
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

	public static String getRemoteSource(String url) {
		return getRemoteSource(url, null);
	}

	private static String getUseProxy() {
		if(null!=useProxy&&useProxy.size()>0) {
//			System.out.println("代理剩余："+useProxy.size());
			return  useProxy.get(0); 
		} else{
			logger.error("代理已经用完！！");
		} 
		return null;
	}

	public static String getRemoteSource(String url, String encode) {
		DefaultHttpClient httpclient = getHttpClient();
		HttpUriRequest httpReq = new HttpGet(url);
		HttpContext context = new BasicHttpContext();
		try {
			ResponseHandler<String> responseHandler = new SimpleResponseHandler(encode);
			String resultContent = httpclient.execute(httpReq, responseHandler, context);
			return resultContent;
		} catch (Exception e) {
			logger.error(String.format("请求远程地址失败，URL：[%s]", url));//, e);
		}finally{
			httpReq.abort();
		} 
		return null;
	}

	public static String getRemoteSource(String url, String encode, boolean useProxy) {
		DefaultHttpClient httpclient = getHttpProxyClient();
		HttpUriRequest httpReq = new HttpGet(url);
		HttpContext context = new BasicHttpContext();
		String useProxyIPPort = null;
		try {
				if (useProxy) {
					useProxyIPPort = getUseProxy();
					if (StringUtils.isNotBlank(useProxyIPPort)) {
						// 使用代理
//						System.out.println("useProxyIPPort:" + useProxyIPPort);
						String proxyIP = useProxyIPPort.split(":")[0];
						int proxyPort = Integer.valueOf(useProxyIPPort.split(":")[1]);
						String proxyUserName = "test";
						String proxyPassWord = "123456";
						String proxyScheme = "http";
						httpclient.getCredentialsProvider().setCredentials(new AuthScope(proxyIP, proxyPort),
								new UsernamePasswordCredentials(proxyUserName, proxyPassWord));
						HttpHost proxy = new HttpHost(proxyIP, proxyPort, proxyScheme);
						httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
					}
				}

				ResponseHandler<String> responseHandler = new SimpleResponseHandler(encode);
				String resultContent = httpclient.execute(httpReq, responseHandler, context);
				return resultContent;
 
		} catch (Exception e) {
			logger.error(String.format("请求远程地址失败，URL：[%s]", url));//, e);
			httpReq.abort();
			HttpClientUtil.useProxy.remove(0);
			if (StringUtils.isNotBlank(useProxyIPPort)) {
				HttpClientUtil.useProxy.add(useProxyIPPort);
			}
		}finally{
			httpReq.abort();
			httpclient.getConnectionManager().shutdown();
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
	public static String Utf8HttpClientUtils(String reUrl, Map<String,String> ParamMap)throws ClientProtocolException, IOException  {
		org.apache.commons.httpclient.HttpClient httpClient;
		PostMethod postMethod = null;
		try{
			httpClient = new org.apache.commons.httpclient.HttpClient();
			HttpClientParams p = new HttpClientParams();
			p.setSoTimeout(200000);
			p.setIntParameter(HttpConnectionParams.SO_TIMEOUT,200000); //超时设置
			p.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 200000);//连接超时 
			httpClient.setParams(p);
			postMethod = new UTF8PostMethod(reUrl);
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
			postMethod = null;
		}    
		return null;
    
	}
	
	
	
}
