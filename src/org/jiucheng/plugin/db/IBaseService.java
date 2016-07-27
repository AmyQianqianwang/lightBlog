package org.jiucheng.plugin.db;

import java.io.Serializable;
import java.util.List;

public interface IBaseService {
	public IBaseDao getBaseDao();
    public <T> Serializable save(T entity);
    public <T> Serializable saveOrUpdate(T entity);
    public <T> boolean update(T entity);
    public <T> int delete(T entity);
    public <T> List<T> list(T entity);
    public <T> T get(Class<T> clazz, Serializable id);
}
