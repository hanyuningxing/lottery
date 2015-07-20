package com.cai310.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.Constant;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MessageUtil {
	protected static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	/**
	 * 短信发送状态.<br/>
	 * 注：添加类型只能在后面添加，不能插入中间
	 * 
	 */
	public enum SendState {
		/** 成功 */
		 SUCCESS("发送成功"),
		 
		 /** 失败 */
		 FAILD("发送失败");

		private final String stateName;

		/**
		 * @param stateName {@link #stateName}
		 */
		private SendState(String stateName) {
			this.stateName = stateName;
		}

		/**
		 * @return {@link #stateName}
		 */
		public String getStateName() {
			return stateName;
		}

	}
	private static final String _url = "http://116.204.66.74:8080/ema/http/SendSms";
	// 密码加密
	private static final String account = "9763";
	private static final String pass = MD5Encode("jfcw_k838");
	
	private static final String singleaccount = "9762";
	private static final String singlepass = MD5Encode("jfcw_k838");
	private static final String single_url = "http://116.204.66.74:8080/ema/http/SendSms";
	/**
	 * =========深圳米域高信息科技有限公司=============
验证码
新开账号：9762
账号密码：my9762
充值条数：50
平台网址：http://116.204.66.74:8080/ema/terminal/frame.jsp
 
 
=========深圳米域高信息科技有限公司=============
营销
新开账号：9763
账号密码：my9763
充值条数：50
平台网址：http://116.204.66.74:8080/ema/terminal/frame.jsp
	 */
	/**
	 * 
	 * @param numberList 发送手机列表。最多只能发送100个
	 * @param content 发送短信内容
	 * @return Map<String(手机号码),SendState(发送状态)>
	 */
	public static Map<String,SendState> sendMessage(List<String> numberList,String content) {
		Map<String,SendState> returnMap = Maps.newHashMap();
		//初始化所有都是失败
		for (String num : numberList) {
			returnMap.put(num, SendState.FAILD);
		}
		if(numberList.size()>100)return returnMap;
		try {
				// 用UTF-8编码执行URLEncode
				content = java.net.URLEncoder.encode(content, "UTF-8");
				String numStr = StringUtils.join(numberList,",");
				String _param = "Account="+account+"&Password=" + pass
						+ "&Phone="+numStr+"&Content=" + content
						+ "&SubCode=&SendTime=";
				URL url = null;
				HttpURLConnection urlConn = null;
			
				if(numberList.size()==1){
					_param = "Account="+singleaccount+"&Password=" + singlepass
							+ "&Phone="+numStr+"&Content=" + content
							+ "&SubCode=&SendTime=";
					url = new URL(single_url);
				}else{
					url = new URL(_url);
				}

				
				urlConn = (HttpURLConnection) url.openConnection();
				urlConn.setRequestMethod("POST");
				urlConn.setDoOutput(true);
				OutputStream out = urlConn.getOutputStream();
				out.write(_param.getBytes("GBK"));
				out.flush();
				out.close();
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream(), "GBK"));
				StringBuffer sb = new StringBuffer();
				int ch;
				while ((ch = rd.read()) > -1) {
					sb.append((char) ch);
				}
				rd.close();
				String xml = sb.toString();
				Document doc = DocumentHelper.parseText(xml);
				Element root = doc.getRootElement(); 
				String response = root.selectSingleNode("response").getText();
				if(Integer.valueOf(response)>0){
					for(Iterator i = root.elementIterator("sms"); i.hasNext();) {
						try {
							Element element = (Element)i.next();
							String phone = element.selectSingleNode("phone").getText();
							returnMap.put(String.valueOf(phone).trim(), SendState.SUCCESS);
						}catch (Exception e) {
							logger.error("解析XML失败");
						    continue;
						}
					}
				}
		} catch (Exception e) {
			logger.error("发送信息失败"+e.getMessage());
		}
		return returnMap;

	}
    public static void main(String[] args) {
    	List<String> num = Lists.newArrayList();
    	num.add("13560944614");
    	//num.add("13059485742");

    	MessageUtil.sendMessage(num, "001111这个账号您可以发送这样的内容");
	}
	public static String MD5Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public static final String byte2hexString(byte[] bytes) {
		StringBuffer bf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 0x10) {
				bf.append("0");
			}
			bf.append(Long.toString(bytes[i] & 0xff, 16));
		}
		return bf.toString();
	}
}

