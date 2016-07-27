package org.jiucheng.aop;

public class EmptyInterceptor implements Interceptor{
	public Object invoke(MethodInvocation mi) throws Throwable {
		return null;
	}
}
