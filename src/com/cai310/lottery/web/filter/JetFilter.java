package com.cai310.lottery.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 处理银联DNA支付回调请求编码问题
 * 
 * @author dengyingjun
 * 
 */
public class JetFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		 // 设置请求响应字符编码
        request.setCharacterEncoding("UTF-8");
        // 新增加的代码
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getMethod().equalsIgnoreCase("get")) {
            req = new GetHttpServletRequestWrapper(req);
        }
        chain.doFilter(req, response);

	}

	@Override
	public void destroy() {
	}

}
