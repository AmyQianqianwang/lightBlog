/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucheng.ioc.BeanFactory;
import org.jiucheng.ioc.IOC;
import org.jiucheng.util.DefaultPropertiesConstant;
import org.jiucheng.util.DefaultPropertiesUtil;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.web.ControllerIOC;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.handler.Handler;
import org.jiucheng.web.handler.HandlerMapping;
import org.jiucheng.web.util.WebUtil;

/**
 * 
 * @author jiucheng(jiucheng.org@gmail.com)
 *
 */
public class DispatcherFilter implements Filter {

    public void destroy() {
        //TODO
    }

    public void doFilter(ServletRequest request,
            ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	if(!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
    		throw new ServletException("Illegal Access");//非法访问
    	}
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // init WebUtil
        WebUtil.setRequest(req);
        WebUtil.setResponse(res);
        // \init WebUtil
        WebWrapper webWrapper = HandlerMapping.getWebWrapper();
        if(ObjectUtil.isNotNull(webWrapper)) {
            Handler handler = BeanFactory.get(webWrapper.getCtrl().getHandlerBeanName(), Handler.class);
            try {
                if(handler.before(webWrapper)) {
                    handler.invoke(webWrapper);
                }
            }finally {
                handler.after(webWrapper);
            }
        }else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fc) throws ServletException {
//        String cfg = fc.getInitParameter("cfg");
//        if(StringUtil.isBlank(cfg)) {
//            cfg = DefaultPropertiesConstant.DEFAULT_CFG;
//        }
        if(!DefaultPropertiesUtil.getIsLoaded()) {
            DefaultPropertiesUtil.loadFromXml(DefaultPropertiesConstant.DEFAULT_CFG);
        }
    	IOC.init();
    	ControllerIOC.init();
    }
}
