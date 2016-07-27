package org.jiucheng.plugin.db;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.jiucheng.orm.SQLHelper;

public interface IBaseDao {
    public <T> Serializable save(T entity);
    public Serializable saveBySQL(SQLHelper sh);
    public <T> Serializable saveOrUpdate(T entity);
    public <T> boolean update(T entity);
    public boolean updateBySQL(SQLHelper sh);
    public <T> int delete(T entity);
    public boolean deleteBySQL(SQLHelper sh);
    public <T> List<T> list(T entity);
	@SuppressWarnings("rawtypes")
    public List<Map> listBySQL(SQLHelper sh);
    public <T> List<T> listBySQL(Class<T> clazz, SQLHelper sh);
    public Connection getConn();
}
