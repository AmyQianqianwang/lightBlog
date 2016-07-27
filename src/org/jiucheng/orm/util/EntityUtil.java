package org.jiucheng.orm.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.dialect.Dialect;
import org.jiucheng.util.ObjectUtil;

public class EntityUtil {

	public static <T> Serializable save(Dialect dialect, Connection conn, T entity) {
		SQLHelper sh = new SQLHelper();
		dialect.forSave(entity, sh);
		return JdbcUtil.save(conn, sh);
	}

	public static <T> Serializable saveOrUpdate(Dialect dialect, Connection conn,
			T entity) {
		Object id = TableUtil.getPrimayKeyValue(entity);
		if (ObjectUtil.isNull(id)) {
			return save(dialect, conn, entity);
		}
		SQLHelper sh = new SQLHelper();
		dialect.forFindById(entity, sh);
		List<T> listT = list(dialect, conn, entity);
		if(listT.size() > 0 ) {
			update(dialect, conn, entity);
			return (Serializable) id;
		}else {
			TableUtil.setPrimayKeyValue(entity, null);
			return save(dialect, conn, entity);
		}
	}

	public static <T> boolean update(Dialect dialect, Connection conn, T entity) {
		SQLHelper sh = new SQLHelper();
		dialect.forUpdateById(entity, sh);
		int count = JdbcUtil.update(conn, sh);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	public static <T> int delete(Dialect dialect, Connection conn, T entity) {
		SQLHelper sh = new SQLHelper();
		dialect.forDelete(entity, sh);
		return JdbcUtil.delete(conn, sh);
	}
	
    @SuppressWarnings("unchecked")
	public static <T> List<T> list(Dialect dialect, Connection conn, T entity) {
        SQLHelper sh = new SQLHelper();
        dialect.forFind(entity, sh);
        ResultSet rs = JdbcUtil.getResultSet(conn, sh);
        return (List<T>) resultSetToListEntity(dialect, rs, entity.getClass());
    }
    
	private static String[] metaDataOfResultSet(ResultSet rs)
			throws DataAccessException {
		String[] result = null;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			result = new String[cols];
			for (int i = 1; i <= cols; i++) {
				result[i - 1] = rsmd.getColumnLabel(i);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return result;
	}

	public static <T> List<T> resultSetToListEntity(Dialect dialect,
			ResultSet rs, Class<T> clazz) throws DataAccessException {
		List<T> result = new ArrayList<T>();
		if (ObjectUtil.isNotNull(rs)) {
			try {
				while (rs.next()) {
					result.add(resultSetToEntity(dialect, rs, clazz));
				}
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T resultSetToEntity(Dialect dialect, ResultSet rs, Class<T> clazz){
		try {
			if (rs.isBeforeFirst() || rs.isAfterLast()) {
				return ObjectUtil.getNull();
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		try {
			String[] metaDatas = metaDataOfResultSet(rs);
			int count = metaDatas.length;
			String colName;
			Object obj = clazz.newInstance();
			Field field;
			for (int i = 1; i <= count; i++) {
				colName = metaDatas[i - 1];
				field = TableUtil.getField(clazz, dialect.forColumnLabel(colName));
				if(ObjectUtil.isNotNull(field)) {
					field.set(obj, rs.getObject(i));
				}
			}
			return (T) obj;
		} catch (InstantiationException e) {
			throw new DataAccessException(e);
		} catch (IllegalAccessException e) {
			throw new DataAccessException(e);
		} catch (IllegalArgumentException e) {
			throw new DataAccessException(e);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}
