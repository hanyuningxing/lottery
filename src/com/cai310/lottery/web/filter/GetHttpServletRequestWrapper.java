package com.cai310.lottery.web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cai310.utils.ToolKit;

/**
 * 处理银联DNA支付回调请求编码问题
 * 
 * @author dengyingjun
 * 
 */
public class GetHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private Log log = LogFactory.getLog(this.getClass());
	private String formStr = null;

	public GetHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		this.formStr = null;
	}

	@Override
	public String getQueryString() {
		String str = super.getQueryString();

		if (str != null) {
			try {
				/* 获取GBK编码的查询字符串 */
				str = URLDecoder.decode(str, "GBK");
				/* 把查询字符串由GBK编码转换为UTF-8编码 */
				return this.convert(str);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return str;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new HashMap<String, String[]>();
		String queryStr = this.getQueryString();

		if (null == this.formStr) {
			this.formStr = this.getFormString();
		}

		if (!"get".equalsIgnoreCase(this.getMethod())) {
			queryStr = this.formStr;
		}

		log.info("\n银联回调URL：" + this.getRequestURL() + "?" + queryStr);

		if (null != queryStr) {
			String str = queryStr.replaceAll("&+", "&");
			String[] tmp = ToolKit.split(str, "&");

			for (int i = 0; i < tmp.length; i++) {
				String[] tmp2 = ToolKit.split(tmp[i], "=");
				if (tmp2.length == 2) {
					String[] tmp3 = { tmp2[1] };
					map.put(tmp2[0], tmp3);
				} else if (tmp2.length > 2) {
					String[] tmp4 = { tmp[i].substring(tmp[i].indexOf("=") + 1) };
					map.put(tmp2[0], tmp4);
				}
			}
		}

		return map;
	}

	@Override
	public String getParameter(String name) {
		try {
			Map<String, String[]> map = this.getParameterMap();
			return map.get(name)[0];
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取POST请求数据，并把GBK编码转换为UTF-8编码
	 * 
	 * @return
	 */
	public String getFormString() {
		String str = null;

		try {
			/* request的getInputStream方法，只能调用一次（因为只有在第一次调用时才能获取POST请求的数据） */
			BufferedReader br = new BufferedReader(new InputStreamReader(
					this.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			str = URLDecoder.decode(sb.toString(), "GBK");
			str = this.convert(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * 转换为UTF-8编码
	 * 
	 * @param target
	 * @return
	 */
	public String convert(String target) {
		if (null != target) {
			try {
				/* Jetty7默认的UrlEncoding是UTF-8，所以getBytes中的编码为UTF-8 */
				return new String(target.getBytes("UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return target;
	}

}
