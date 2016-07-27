package org.jiucheng.plugin.db;

import java.io.Serializable;
import java.util.List;

import org.jiucheng.aop.Aop;
import org.jiucheng.ioc.annotation.Inject;
import org.jiucheng.ioc.annotation.Service;
import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.interceptor.Close;
import org.jiucheng.orm.util.TableUtil;
import org.jiucheng.util.ObjectUtil;

@Service("baseService")
@Aop(Close.class)
public class BaseServiceImpl implements IBaseService{

	@Inject
	private IBaseDao baseDao;
	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public <T> Serializable save(T entity) {
		return getBaseDao().save(entity);
	}

	public <T> Serializable saveOrUpdate(T entity) {
		return getBaseDao().saveOrUpdate(entity);
	}

	public <T> boolean update(T entity) {
		return getBaseDao().update(entity);
	}

	public <T> int delete(T entity) {
		return getBaseDao().delete(entity);
	}

	public <T> List<T> list(T entity) {
		return getBaseDao().list(entity);
	}

    public <T> T get(Class<T> clazz, Serializable id) {
        T obj = ObjectUtil.getNull();
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new DataAccessException(e);
        } catch (IllegalAccessException e) {
            throw new DataAccessException(e);
        }
        TableUtil.setPrimayKeyValue(obj, id);
        List<T> rs = list(obj);
        if(rs.size() > 0) {
            return rs.get(0);
        }
        return ObjectUtil.getNull();
    }
}
