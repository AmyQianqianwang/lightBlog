package org.jiucheng.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvocation {
	private Object target;
	private Method method;
	private Object[] args;
	private boolean proceeded;
	private Object data;

	public Object getTarget() {
		return target;
	}
	
	void setMethod(Method method) {
		this.method = method;
	}
	
	public Method getMethod() {
		return method;
	}
	
	void setArgs(Object[] args) {
		this.args = args;
	}
	
	public Object[] getArgs() {
		return args;
	}
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	boolean isProceeded() {
		return proceeded;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
	
	public Object proceed() throws AopException{
		proceeded = true;
		method.setAccessible(true);
		Object r = null;
		try {
			r = method.invoke(target, args);
		} catch (IllegalAccessException e) {
			throw new AopException(e);
		} catch (IllegalArgumentException e) {
			throw new AopException(e);
		} catch (InvocationTargetException e) {
			throw new AopException(e.getCause());
		}
		return r;
	}
}
