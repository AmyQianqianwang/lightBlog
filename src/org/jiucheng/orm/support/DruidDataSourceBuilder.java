package org.jiucheng.orm.support;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jiucheng.exception.UncheckedException;
import org.jiucheng.log.Log;
import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.PrototypeDataSource;
import org.jiucheng.util.ClassUtil;
import org.jiucheng.util.DefaultPropertiesUtil;
import org.jiucheng.util.ObjectUtil;

public class DruidDataSourceBuilder {

	private static final Log LOG = Log.getLog(DruidDataSourceBuilder.class);
	private static final ThreadLocal<Connection> CONN = new ThreadLocal<Connection>();
	
	private DataSource dataSource;
	
	public DruidDataSourceBuilder() {
		try {
			Class.forName("com.alibaba.druid.pool.DruidDataSource");
			Class<?> clazz = ClassUtil.loadClass("org.jiucheng.orm.support.DruidDataSourceFacotry");
			IDataSourceFactory dataSourceFactory = (IDataSourceFactory) clazz.newInstance();
			dataSource = dataSourceFactory.getDataSource();
		} catch (ClassNotFoundException e) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				PrototypeDataSource prototypeDataSource = new PrototypeDataSource();
				prototypeDataSource.setUrl(DefaultPropertiesUtil.getString("jdbc.url"));
				prototypeDataSource.setUsername(DefaultPropertiesUtil.getString("jdbc.username"));
				prototypeDataSource.setPassword(DefaultPropertiesUtil.getString("jdbc.password"));
				dataSource = prototypeDataSource;
			} catch (ClassNotFoundException e1) {
				throw new UncheckedException(e1);
			}
		} catch (InstantiationException e) {
			if(LOG.isErrorEnabled()) {
				LOG.error("",e);
			}
		} catch (IllegalAccessException e) {
			if(LOG.isErrorEnabled()) {
				LOG.error("",e);
			}
		}
	}

	public Connection getLocalConn() {
		return CONN.get();
	}

	public Connection getConn() {
		Connection c = CONN.get();
		if (ObjectUtil.isNull(c)) {
			c = newConn();
			CONN.set(c);
		}
		return c;
	}

	public void clear() {
		CONN.remove();
		CONN.set(null);
	}

	private Connection newConn() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}
