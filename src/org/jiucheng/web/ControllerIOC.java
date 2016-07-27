/**
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jiucheng.exception.UncheckedException;
import org.jiucheng.ioc.BeanFactory;
import org.jiucheng.ioc.ClassTemplate;
import org.jiucheng.ioc.IOC;
import org.jiucheng.util.ClassUtil;
import org.jiucheng.util.DefaultPropertiesConstant;
import org.jiucheng.util.DefaultPropertiesUtil;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.annotation.Controller;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.annotation.Controller.POST;
import org.jiucheng.web.handler.Handler;
import org.jiucheng.web.handler.HandlerMapping;

/**
 * 
 * @author jiucheng(jiucheng.org@gmail.com)
 *
 */
public class ControllerIOC {
	
	private static boolean inited;
	private static List<String> listControllerBeanName = new ArrayList<String>();
	
	public static void init() {
		if(inited) return;
		@SuppressWarnings("unchecked")
		List<Class<?>> listClass = ClassTemplate.listClassByAnnotation(
				DefaultPropertiesUtil.getString(DefaultPropertiesConstant.JIUCHENG_SCANNER_PACKAGE),
				Controller.class);
		Controller controller;
		String beanName;
		for(Class<?> clazz : listClass) {
			controller = clazz.getAnnotation(Controller.class);
			if(ObjectUtil.isNotNull(controller)) {
			    // init hanlder
			    Class<? extends Handler> handlerClass = controller.value();
			    builderHandler(handlerClass);
			    // \init handler
				beanName = IOC.createBeanName(null, clazz);
				try {
					IOC.put(beanName, clazz.newInstance());
					buildHandlerByControllerBeanName(beanName, handlerClass.getCanonicalName());
					listControllerBeanName.add(beanName);
				} catch (InstantiationException e) {
					throw new UncheckedException(e);
				} catch (IllegalAccessException e) {
					throw new UncheckedException(e);
				}
			}
		}
		injectControllerField();
		inited = true;
	}
	
	private static void builderHandler(Class<? extends Handler> clazz) {
	    String beanName = clazz.getCanonicalName();
	    Object rs = BeanFactory.get(beanName);
	    if(ObjectUtil.isNull(rs)) {
	        try {
                IOC.put(beanName, clazz.newInstance());
                IOC.injectFieldByBeanName(beanName);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
	    }
	}
	
	private static void injectControllerField() {
		for(String controllerBeanName : listControllerBeanName) {
			IOC.injectFieldByBeanName(controllerBeanName);
		}
	}
	
    private static void buildHandlerByControllerBeanName(String beanName, String handlerBeanName) {
        if(StringUtil.isBlank(beanName)) return;
        Object controller = BeanFactory.get(beanName);
        List<Method> listMethod = ClassUtil.listMethod(controller.getClass());
        GET get;
        POST post;
        String route;
        for(Method method : listMethod) {
            get = method.getAnnotation(GET.class);
            if(ObjectUtil.isNotNull(get)) {
                route = get.value();
                buildCtrlVo(route, method, RequestType.GET, beanName, handlerBeanName);
                continue;
            }
            post = method.getAnnotation(POST.class);
            if(ObjectUtil.isNotNull(post)) {
                route = post.value();
                buildCtrlVo(route, method, RequestType.POST, beanName, handlerBeanName);
                continue;
            }
        }
    }
    
    private static void buildCtrlVo(String route,Method method, RequestType requestType, String ctrlBeanName, String handlerBeanName) {
        Ctrl ctrlVo = new Ctrl();
        ctrlVo.setRoute(route);
        ctrlVo.setMethod(method);
        ctrlVo.setRequestType(requestType);
        ctrlVo.setBeanName(ctrlBeanName);
        ctrlVo.setHandlerBeanName(handlerBeanName);
    	if(route.matches(".*\\(.+\\).*") || route.indexOf("?") != -1) {
            HandlerMapping.REGEX_CTRL.add(ctrlVo);
    	}else {
    		HandlerMapping.NOT_REGEX_CTRL.put(requestType.toString() + route, ctrlVo);
    	}
    }
}
