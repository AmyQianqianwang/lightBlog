/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucheng.util.ObjectUtil;
import org.jiucheng.web.Ctrl;
import org.jiucheng.web.RequestType;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.util.WebUtil;

public class HandlerMapping {
	
	/* regex */
    public static final List<Ctrl> REGEX_CTRL = new ArrayList<Ctrl>();
    public static final Map<String, Ctrl> NOT_REGEX_CTRL = new HashMap<String, Ctrl>();
    
    public static WebWrapper getWebWrapper() {
        return buildHandler(WebUtil.getRequest(), WebUtil.getResponse(), WebUtil.getPathInfo());
    }
    
    private static WebWrapper buildHandler(HttpServletRequest request, HttpServletResponse response, String pathInfo) {
        WebWrapper handler = ObjectUtil.getNull();
        Matcher matcher;
        RequestType currentRequestType = getRequestType(request);
        Ctrl c = NOT_REGEX_CTRL.get(currentRequestType.toString() + pathInfo);
        if(ObjectUtil.isNull(c)) {
            for(Ctrl ctrl : REGEX_CTRL) {
                matcher = Pattern.compile(ctrl.getRoute()).matcher(pathInfo);
                if(matcher.matches()) {
                    if(ctrl.getRequestType() == RequestType.REQUEST || ctrl.getRequestType() == currentRequestType) {
                        handler = new WebWrapper();
                        handler.setCtrl(ctrl);
                        handler.setMatcher(matcher);
                        break;
                    }
                }
            }
        }else {
            handler = new WebWrapper();
            handler.setCtrl(c);
        }
        return handler;
    }
    
    private static RequestType getRequestType(HttpServletRequest request) {
        RequestType requestType;
        String methodString = request.getMethod();
        if(methodString.equalsIgnoreCase(RequestType.GET.toString())) {
            requestType = RequestType.GET;
        }else if(methodString.equalsIgnoreCase(RequestType.POST.toString())) {
            requestType = RequestType.POST;
        }else {
            requestType = RequestType.REQUEST;
        }
        return requestType;
    }
}
