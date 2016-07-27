package org.jiucheng.orm.interceptor;

import java.sql.Connection;
import java.sql.SQLException;

import org.jiucheng.aop.AopException;
import org.jiucheng.aop.Interceptor;
import org.jiucheng.aop.MethodInvocation;
import org.jiucheng.exception.UncheckedException;
import org.jiucheng.ioc.annotation.Impl;
import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.support.DruidDataSourceBuilder;
import org.jiucheng.orm.util.JdbcUtil;

public class Tx implements Interceptor{
	
	@Impl(DruidDataSourceBuilder.class)
	private DruidDataSourceBuilder druidDataSourceBuilder;

	public Object invoke(MethodInvocation mi) throws Throwable {
		Connection conn = druidDataSourceBuilder.getConn();
		conn.setAutoCommit(false);
		try {
			Object r = mi.proceed();
			conn.commit();
			return r;
		}catch(AopException ae) {
			Throwable t = ae.getCause();
			if(t instanceof DataAccessException || t instanceof SQLException) {
				conn.rollback();
			}
			throw new UncheckedException(t);
		}finally {
			conn.setAutoCommit(false);
			JdbcUtil.close(conn);
			druidDataSourceBuilder.clear();
		}
	}

}
