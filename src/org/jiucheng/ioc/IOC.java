package org.jiucheng.ioc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jiucheng.aop.AopProxy;
import org.jiucheng.ioc.annotation.Component;
import org.jiucheng.ioc.annotation.Impl;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.ioc.annotation.Repository;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.log.Log;
import org.jiucheng.util.ClassUtil;
import org.jiucheng.util.DefaultPropertiesConstant;
import org.jiucheng.util.DefaultPropertiesUtil;
import org.jiucheng.util.ObjectUtil;
import org.jiucheng.util.StringUtil;

public class IOC {
	private static final Log log = Log.getLog(IOC.class);
	private static final Map<String, Object> beans = new HashMap<String, Object>();
	private static final Map<String, Object> services = new HashMap<String, Object>();
	private static final Map<String, Object> daos = new HashMap<String, Object>();
	private static final Map<String, Object> components = new HashMap<String, Object>();
	private static boolean inited;
	
	@SuppressWarnings("unchecked")
	public static void init() {
		if(inited) return;
        inited = true;
		try {
			initIOC(DefaultPropertiesUtil.getString(DefaultPropertiesConstant.JIUCHENG_SCANNER_PACKAGE),
					Component.class, Repository.class, Service.class);
			injectField();
		}catch(Throwable t) {
			if(log.isErrorEnabled()) {
				log.error("", t);
			}
		}
	}
	
	//注入
	private static void injectField() {
		Set<String> daosName = daos.keySet();
		for(String daoName : daosName) {
			injectFieldByBeanName(daoName);
		}
		for(String daoName : daosName) {
			beans.put(daoName, AopProxy.getProxyObject(beans.get(daoName)));
		}
		Set<String> servicesName = services.keySet();
		for(String serviceName :servicesName) {
			injectFieldByBeanName(serviceName);
		}
		for(String serviceName : servicesName) {
			beans.put(serviceName, AopProxy.getProxyObject(beans.get(serviceName)));
		}
        Set<String> componentsName = components.keySet();
        for(String componentName : componentsName) {
            injectFieldByBeanName(componentName);
        }
	}
	
    public static void injectFieldByBeanName(String beanName) {
    	injectFieldByBeanName(beans, beanName);
    }
    
    public static void injectFieldByBeanName(Map<String, Object> container, String beanName) {
		Object obj = container.get(beanName);
		List<Field> listField = ClassUtil.listField(obj.getClass());
		Inject inject;
		Impl impl;
		for(Field field : listField) {
			inject = field.getAnnotation(Inject.class);
			if(ObjectUtil.isNotNull(inject)) {
				buildIOCByName(inject.value(), field, obj);
			}
			impl = field.getAnnotation(Impl.class);
			if(ObjectUtil.isNotNull(impl)) {
				buildIOCByName(getBeanNameByImpl(impl), field, obj);
			}
		}
    }
    
    //TODO 有待完善
    private static String getBeanNameByImpl(Impl impl) {
    	Class<?> clazz = impl.value();
    	String beanName = createBeanName(StringUtil.NULL, clazz);
    	Object bean = beans.get(beanName);
    	if(ObjectUtil.isNull(bean)) {
    		try {
				bean = clazz.newInstance();
				beans.put(beanName, bean);
				injectFieldByBeanName(beans, beanName);
			} catch (InstantiationException e) {
				if(log.isErrorEnabled()) {
					log.error("",e);
				}
			} catch (IllegalAccessException e) {
				if(log.isErrorEnabled()) {
					log.error("",e);
				}
			}
    	}
    	return beanName;
    }
    
    private static void buildIOCByName(String name,Field field, Object obj) {
		if(StringUtil.isBlank(name)) {
			name = field.getName();
		}
		field.setAccessible(true);
		try {
			field.set(obj, beans.get(name));
		} catch (IllegalArgumentException e) {
			if(log.isErrorEnabled()) {
				log.error("",e);
			}
		} catch (IllegalAccessException e) {
			if(log.isErrorEnabled()) {
				log.error("",e);
			}
		}
    }
	
	private static void initIOC(String packageName, Class<? extends Annotation>... annoClasses) throws InstantiationException, IllegalAccessException {
		List<Class<?>> classes = ClassTemplate.listClassByAnnotation(packageName, annoClasses);
		Service service;
		Repository repository;
		Component component;
		String beanName = null;
		Object target = null;
		for(Class<?> clazz : classes) {
			component = clazz.getAnnotation(Component.class);
			if(ObjectUtil.isNotNull(component)) {
				beanName = createBeanName(component.value(), clazz);
				target = clazz.newInstance();
				components.put(beanName, target);
				beans.put(beanName, target);
				continue;
			}
			repository = clazz.getAnnotation(Repository.class);
			if(ObjectUtil.isNotNull(repository)) {
				beanName = createBeanName(repository.value(), clazz);
				target = clazz.newInstance();
				daos.put(beanName, target);
				beans.put(beanName, target);
				continue;
			}
			service = clazz.getAnnotation(Service.class);
			if(ObjectUtil.isNotNull(service)) {
				beanName = createBeanName(service.value(), clazz);
				target = clazz.newInstance();
				services.put(beanName, target);
				beans.put(beanName, target);
				continue;
			}
		}
	}
	
	static Object get(String beanName) {
		return beans.get(beanName);
	}
	
	public static Object put(String beanName, Object obj) {
		return beans.put(beanName, obj);
	}
	
	public static String createBeanName(String value, Class<?> clazz) {
		if(StringUtil.isNotBlank(value)) {
			return value;
		}
		return clazz.getCanonicalName();
	}
}
