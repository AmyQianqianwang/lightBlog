package org.jiucheng.aop;

public interface Interceptor {
	public Object invoke(MethodInvocation mi) throws Throwable;
}
