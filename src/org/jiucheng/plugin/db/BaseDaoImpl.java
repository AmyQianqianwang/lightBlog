package org.jiucheng.plugin.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.jiucheng.ioc.annotation.Impl;
import org.jiucheng.ioc.annotation.Repository;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.dialect.Dialect;
import org.jiucheng.orm.dialect.impl.MySQLDialect;
import org.jiucheng.orm.support.DruidDataSourceBuilder;
import org.jiucheng.orm.util.EntityUtil;
import org.jiucheng.orm.util.JdbcUtil;

@Repository("baseDao")
public class BaseDaoImpl implements IBaseDao {
    
    @Impl(MySQLDialect.class)
    private Dialect dialect;
    
    @Impl(DruidDataSourceBuilder.class)
    private DruidDataSourceBuilder druidDataSourceBuilder;
    
    public <T> Serializable save(T entity) {
        return EntityUtil.save(getDialect(), getConn(), entity);
    }
    
    public <T> Serializable saveOrUpdate(T entity) {
        return EntityUtil.saveOrUpdate(getDialect(), getConn(), entity);
    }
    
    public <T> boolean update(T entity) {
        return EntityUtil.update(getDialect(), getConn(), entity);
    }

    public <T> int delete(T entity) {
        return EntityUtil.delete(getDialect(), getConn(), entity);
    }

    public <T> List<T> list(T entity) {
        return EntityUtil.list(getDialect(), getConn(), entity);
    }
    
    private Dialect getDialect() {
        return dialect;
    }
    
    public Connection getConn() {
        return druidDataSourceBuilder.getConn();
    }

    public Serializable saveBySQL(SQLHelper sh) {
        return JdbcUtil.save(getConn(), sh);
    }

    public boolean updateBySQL(SQLHelper sh) {
        if(JdbcUtil.update(getConn(), sh) > 0 ) {
            return true;
        }
        return false;
    }

    public boolean deleteBySQL(SQLHelper sh) {
        if(JdbcUtil.delete(getConn(), sh) > 0) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public List<Map> listBySQL(SQLHelper sh) {
        return JdbcUtil.list(getConn(), sh);
    }
    
    public <T> List<T> listBySQL(Class<T> clazz, SQLHelper sh) {
    	ResultSet rs = JdbcUtil.getResultSet(getConn(), sh);
    	return EntityUtil.resultSetToListEntity(dialect, rs, clazz);
    }
}
