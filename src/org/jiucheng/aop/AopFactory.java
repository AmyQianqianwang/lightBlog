package org.jiucheng.aop;

import java.util.HashMap;
import java.util.Map;

import org.jiucheng.ioc.IOC;
import org.jiucheng.log.Log;
import org.jiucheng.util.ObjectUtil;

public class AopFactory {
	private static final Log log = Log.getLog(AopFactory.class);
	private static final Map<String, Object> interceptors = new HashMap<String, Object>();
	
	public static <T extends Interceptor> Interceptor getInterceptor(Class<T> clazz) {
		String className = clazz.getCanonicalName();
		Interceptor interceptor = (Interceptor) interceptors.get(className);
		if(ObjectUtil.isNull(interceptor)) {
			try {
				interceptor = clazz.newInstance();
				interceptors.put(className, interceptor);
				IOC.injectFieldByBeanName(interceptors, className);
			} catch (InstantiationException e) {
				if(log.isErrorEnabled()) {
					log.error("", e);
				}
			} catch (IllegalAccessException e) {
				if(log.isErrorEnabled()) {
					log.error("", e);
				}
			}
		}
		return interceptor;
	}
}
