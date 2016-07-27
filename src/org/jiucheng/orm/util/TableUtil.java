/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.orm.util;

import java.lang.reflect.Field;

import org.jiucheng.exception.UncheckedException;
import org.jiucheng.orm.meta.ColumnMapping;
import org.jiucheng.orm.meta.TableFactory;
import org.jiucheng.orm.meta.TableMapping;
import org.jiucheng.util.ClassUtil;
import org.jiucheng.util.ObjectUtil;

public class TableUtil {

	public static <T> String getTableName(Class<T> clazz) {
		return getTableMapping(clazz).getTableName();
	}
	
	public static <T> ColumnMapping getPrimaryKey(Class<T> clazz) {
		return getTableMapping(clazz).getPrimaryKey();
	}
	
	public static <T> String getPrimaryKeyName(Class<T> clazz) {
		return getPrimaryKey(clazz).getColumnName();
	}
	
	public static <T> TableMapping getTableMapping(Class<T> clazz) {
		return TableFactory.getTableMapping(clazz);
	}
	
	public static <T> Field getField(Class<T> clazz, String columnName) {
		TableMapping tableMapping = getTableMapping(clazz);
		for(ColumnMapping columnMapping : tableMapping.getColumnMappings()) {
			if(columnMapping.getColumnName().equals(columnName)) {
				return ClassUtil.getField(clazz, columnMapping.getFieldName());
			}
		}
		return ObjectUtil.getNull();
	}
	
	public static <T> Object getPrimayKeyValue(T entity) {
		Class<?> clazz = entity.getClass();
		ColumnMapping columnMapping = getPrimaryKey(clazz);
		Field idField = ClassUtil.getField(clazz, columnMapping.getFieldName());
		try {
			return idField.get(entity);
		} catch (IllegalArgumentException e) {
			throw new UncheckedException(e);
		} catch (IllegalAccessException e) {
			throw new UncheckedException(e);
		}
	}
	
	public static <T> void setPrimayKeyValue(T entity, Object value) {
		Class<?> clazz = entity.getClass();
		ColumnMapping columnMapping = getPrimaryKey(clazz);
		Field idField = ClassUtil.getField(clazz, columnMapping.getFieldName());
		try {
			idField.set(entity, value);
		} catch (IllegalArgumentException e) {
			throw new UncheckedException(e);
		} catch (IllegalAccessException e) {
			throw new UncheckedException(e);
		}
	}
}
