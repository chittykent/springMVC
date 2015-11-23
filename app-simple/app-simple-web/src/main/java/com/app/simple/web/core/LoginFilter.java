package com.app.simple.web.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.simple.model.UserModel;


public class LoginFilter implements Filter {

	public void destroy() {
		System.out.println("过滤器销毁...");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String[] strs = { "/index", "/css", "/images", "/js", "/jsp"}; // 路径中包含这些字符串的,可以不用登录直接访问
		StringBuffer url = request.getRequestURL();
		
		// 特殊用途的路径可以直接访问
		if (strs != null && strs.length > 0) {
			for (String str : strs) {
				if (url.indexOf(str) >= 0) {
					chain.doFilter(request, response);
					return;
				}
			}
		}

		UserModel userModel = (UserModel)session.getAttribute("user"); 
//		System.out.println("userModel="+userModel);
		if (userModel == null) {
			session.invalidate();
			response.setContentType("text/html;charset=UTF-8");
			
			String returnUrl = request.getContextPath() + "/index.jsp";
			
//			System.out.println("跳转页面："+returnUrl);
//			System.out.println(("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) || request.getParameter("ajax") != null));
			
			PrintWriter out = response.getWriter();
			if(("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) || request.getParameter("ajax") != null)){
				response.setHeader("sessionTimeout", "true");
				chain.doFilter(request, response);
			}else{
				out.println(
						"<script language=\"javascript\">if(window.opener==null){window.top.location.href=\""
								+ returnUrl
								+ "\";}else{window.opener.top.location.href=\""
								+ returnUrl
								+ "\";window.close();}</script>");
			}

		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
