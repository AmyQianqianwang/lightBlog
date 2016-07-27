package org.jiucheng.ioc;

import org.jiucheng.exception.UncheckedException;
import org.jiucheng.util.ObjectUtil;

public class BeanFactory {
	static {
		IOC.init();
	}
	
	private BeanFactory() {}
	
	public static Object get(String beanName) {
		return IOC.get(beanName);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String beanName, Class<T> clazz) {
		Object rs = IOC.get(beanName);
		if(ObjectUtil.isNotNull(rs)) {
			if(!clazz.isInstance(rs)) {
				throw new UncheckedException("类型不匹配");
			}
		}
		return (T) rs;
	}
	
	@SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
	    String clazzName = clazz.getCanonicalName();
	    T rs = (T) IOC.get(clazzName);
	    if(ObjectUtil.isNull(rs)) {
	        try {
	            rs = clazz.newInstance();
                IOC.put(clazzName, rs);
                IOC.injectFieldByBeanName(clazzName);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
	    }
        return rs;
	}
}
