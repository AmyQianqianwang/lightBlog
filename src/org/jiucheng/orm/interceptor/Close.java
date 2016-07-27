package org.jiucheng.orm.interceptor;

import java.sql.Connection;

import org.jiucheng.aop.Interceptor;
import org.jiucheng.aop.MethodInvocation;
import org.jiucheng.ioc.annotation.Impl;
import org.jiucheng.orm.support.DruidDataSourceBuilder;
import org.jiucheng.orm.util.JdbcUtil;

public class Close implements Interceptor {
	
	@Impl(DruidDataSourceBuilder.class)
	private DruidDataSourceBuilder druidDataSourceBuilder;

	public Object invoke(MethodInvocation mi) throws Throwable {
		try{
			Object r = mi.proceed();
			return r;
		}finally {
			Connection conn = druidDataSourceBuilder.getLocalConn();
			JdbcUtil.close(conn);
			druidDataSourceBuilder.clear();
		}
	}

}
