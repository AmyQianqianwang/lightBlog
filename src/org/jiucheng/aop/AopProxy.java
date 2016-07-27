package org.jiucheng.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * @author jiucheng
 *
 */
public class AopProxy implements InvocationHandler{
	
	private Object target;
	
	private AopProxy(Object target) {
		this.target = target;
	}
	
	public static Object getProxyObject(Object target) {
		AopProxy aopProxy = new AopProxy(target);
		return Proxy.newProxyInstance(aopProxy.getClass().getClassLoader(), target.getClass().getInterfaces(), aopProxy);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Class<?> clazz = target.getClass();
		Method m = clazz.getMethod(method.getName(), method.getParameterTypes());
		Aop methodAop = m.getAnnotation(Aop.class);
		MethodInvocation mi = new MethodInvocation();
		mi.setTarget(target);
		mi.setMethod(method);
		mi.setArgs(args);
		if(methodAop != null) {
			return result(methodAop, mi);
		}
		Aop classAop = target.getClass().getAnnotation(Aop.class);
		if(classAop != null) {
			return result(classAop, mi);
		}
		return mi.proceed();
	}
	
	private Object result(Aop aop, MethodInvocation mm) throws Throwable {
		Object r = null;
		Class<? extends Interceptor>[] interceptorClasses = aop.value();
		Interceptor interceptor;
		for(Class<? extends Interceptor> interceptorClass : interceptorClasses) {
			interceptor = getInterceptor(interceptorClass);
			r = interceptor.invoke(mm);
			if(mm.isProceeded()) {
				break;
			}
		}
		if(!mm.isProceeded()) {
			r = mm.proceed();
		}
		return r;
	}
	
	public <T extends Interceptor> Interceptor getInterceptor(Class<T> clazz) {
		return AopFactory.getInterceptor(clazz);
	}
}
