/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucheng.util.StringUtil;

/**
 * 
 * @author jiucheng(jiucheng.org@gmail.com)
 *
 */
public class WebUtil {
	
	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<HttpServletResponse>();
	
	public static HttpServletRequest getRequest() {
		return REQUEST.get();
	}
	
	public static void setRequest(HttpServletRequest request) {
		REQUEST.set(request);
	}
	
	public static HttpServletResponse getResponse() {
		return RESPONSE.get();
	}
	
	public static void setResponse(HttpServletResponse response) {
		RESPONSE.set(response);
	}
	
	public static String getPathInfo() {
		return getPathInfo(getRequest(), getResponse());
	}
	
    public static String getPathInfo(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        if(StringUtil.isNotBlank(pathInfo)) {
            return pathInfo;
        }
        return request.getServletPath();
    }
    
    public static String getWebRoot() {
    	return getRequest().getSession().getServletContext().getRealPath("/");
    }
    
    public static void dispatcher(String path) {
        RequestDispatcher rd = getRequest().getRequestDispatcher(path);
        try {
            rd.forward(getRequest(), getResponse());
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void redirect(String location) {
        try {
            getResponse().sendRedirect(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getEncoding() {
        return getRequest().getCharacterEncoding();
    }
    
    public static boolean isIE() {
        if(getRequest().getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 获取IP
     */
    public static String getIp() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
