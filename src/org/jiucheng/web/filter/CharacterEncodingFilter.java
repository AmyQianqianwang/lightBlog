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

import org.jiucheng.util.StringUtil;

/**
 * 
 * @author jiucheng
 *
 */
public class CharacterEncodingFilter implements Filter{

    private String encoding;
    
    public void destroy() {
        
    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        res.setCharacterEncoding(encoding);
        chain.doFilter(req, res);
    }

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding");
        if(StringUtil.isBlank(encoding)) {
            encoding = StringUtil.UTF_8;
        }
    }
    
}
