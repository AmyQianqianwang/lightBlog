package org.jiucheng.web.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucheng.ioc.BeanFactory;
import org.jiucheng.util.ClassUtil;
import org.jiucheng.util.DefaultPropertiesConstant;
import org.jiucheng.util.DefaultPropertiesUtil;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;
import org.jiucheng.web.WebWrapper;
import org.jiucheng.web.annotation.Controller.GET;
import org.jiucheng.web.annotation.Controller.POST;
import org.jiucheng.web.annotation.Param;
import org.jiucheng.web.util.WebUtil;

public class DefaultHandler extends AbstractHandler {
	public void invoke(WebWrapper webWrapper) throws ServletException {
        Object ctrl = BeanFactory.get(webWrapper.getCtrl().getBeanName());
        Method method = webWrapper.getCtrl().getMethod();
        method.setAccessible(true);
        try {
			Object obj = method.invoke(ctrl, getArgs(method, webWrapper).toArray());
			GET get = method.getAnnotation(GET.class);
			Out rs;
			if(ObjectUtil.isNotNull(get)) {
			    rs = BeanFactory.getInstance(get.out());
			}else {
			    POST post = method.getAnnotation(POST.class);
			    rs = BeanFactory.getInstance(post.out());
			}
			rs.invoke(webWrapper, obj);
		} catch (IllegalAccessException e) {
			throw new ServletException(e);
		} catch (IllegalArgumentException e) {
			throw new ServletException(e);
		} catch (InvocationTargetException e) {
        	Throwable t;
        	if(e instanceof InvocationTargetException) {
        		t = ((InvocationTargetException) e).getTargetException();
        	}else {
        		t = e;
        	}
        	throw new ServletException(t);
		}
	}
	
   public List<Object> getArgs(Method method, WebWrapper webWrapper) {
        List<Object> args = new ArrayList<Object>();
        Class<?>[] classes = method.getParameterTypes();
        Annotation[][] anns = method.getParameterAnnotations();
        for(int i = 0; i < classes.length; i ++) {
            args.add(getObjectByClass(anns[i], classes[i], webWrapper));
        }
        return args;
    }
   
   private Object getObjectByClass(Annotation[] ann, Class<?> clazz, WebWrapper webWrapper) {
       Param param = ObjectUtil.getNull();
       if(ObjectUtil.isNotNull(ann)) {
           for(Annotation a : ann) {
               if(a instanceof Param) {
                   param = (Param) a;
               }
           }
       }
       if(ObjectUtil.isNotNull(param)) {
           return getParamValue(clazz);
       }
       Object r = ObjectUtil.getNull();
       if(clazz.equals(StringUtil.NULL_ARRAY.getClass())) {
           Matcher matcher = webWrapper.getMatcher();
           if(ObjectUtil.isNotNull(matcher)) {
               int groupCount = matcher.groupCount();
               String[] stres = new String[groupCount + 1];
               for( int i = 0; i <= groupCount; i ++) {
                   stres[i] = matcher.group(i);
               }
               r = stres;
           }else {
               r = new String[]{webWrapper.getCtrl().getRoute()};
           }
       }else if(clazz.equals(Map.class)) {
           r = WebUtil.getRequest().getParameterMap();
       }else if(clazz.equals(HttpServletRequest.class)) {
           r = WebUtil.getRequest();
       }else if(clazz.equals(HttpServletResponse.class)) {
           r = WebUtil.getResponse();
       }
       return r;
   }
   
   private Object getParamValue(Class<?> clazz) {
       String name = clazz.getName();
       if(clazz == String.class) {
           return WebUtil.getRequest().getParameter(name);
       }
       if(clazz == StringUtil.NULL_ARRAY.getClass()) {
           return WebUtil.getRequest().getParameterValues(name);
       }
       if(clazz.getCanonicalName().startsWith(DefaultPropertiesUtil.getString(DefaultPropertiesConstant.JIUCHENG_SCANNER_PACKAGE))) {
           Object obj = ObjectUtil.getNull(); 
           try {
                obj = clazz.newInstance();
                List<Field> fieldList = ClassUtil.listField(clazz);
                Object value;
                for(Field field : fieldList) {
                    value = WebUtil.getRequest().getParameter(field.getName());
                    if(ObjectUtil.isNotNull(value)) {
                        field.setAccessible(true);
                        field.set(obj, ObjectUtil.toThis(field.getType(), value));
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return obj;
       }
       return null;
   }
}
