/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.orm.dialect.impl;

import java.lang.reflect.Field;
import java.util.List;

import org.jiucheng.log.Log;
import org.jiucheng.orm.SQLHelper;
import org.jiucheng.orm.dialect.Dialect;
import org.jiucheng.orm.dialect.DialectException;
import org.jiucheng.orm.meta.ColumnMapping;
import org.jiucheng.orm.meta.TableMapping;
import org.jiucheng.orm.util.TableUtil;
import org.jiucheng.util.ClassUtil;
import org.jiucheng.util.ObjectUtil;

/**
 * 
 * @author jiucheng(jiucheng.org@gmail.com)
 * @version 2014/11/09 0.0.1
 * 
 */
public class MySQLDialect implements Dialect {

    private static final Log LOG = Log.getLog(MySQLDialect.class);

    public <T> void forSave(T entity, SQLHelper sh) {
        Class<?> clazz = entity.getClass();
        TableMapping tableMapping = TableUtil.getTableMapping(clazz);
        sh.append("INSERT INTO `").append(tableMapping.getTableName()).append("`");
        Object result = null;
        List<ColumnMapping> cms = tableMapping.getColumnMappings();
        StringBuilder before = new StringBuilder();
        StringBuilder after = new StringBuilder();
        for (ColumnMapping cm : cms) {
            result = getFieldValue(entity, cm.getFieldName());
            if (result != null) {
                before.append("`").append(cm.getColumnName()).append("`")
                        .append(",");
                after.append("?,");
                sh.insertValue(result);
            }
        }
        if (before.length() > 0 && after.length() > 0) {
            before.deleteCharAt(before.length() - 1);
            after.deleteCharAt(after.length() - 1);
        } else {
            throw new DialectException("This object's attributes is null");
        }
        sh.append("(").append(before.toString()).append(")");
        sh.append(" VALUES(").append(after.toString()).append(")");
    }

    public <T> void forDelete(T entity, SQLHelper sh) {
        sh.append("DELETE FROM `").append(TableUtil.getTableName(entity.getClass())).append("`");
        handleAfterWhere(entity, sh);
    }

    public <T> void forDeleteById(T entity, SQLHelper sh) {
    	Class<?> clazz = entity.getClass();
		TableMapping tableMapping = TableUtil.getTableMapping(clazz);
        sh.append("DELETE FROM `");
        sh.append(tableMapping.getTableName());
        sh.append("` WHERE `");
        sh.append(tableMapping.getPrimaryKey().getColumnName());
        sh.append("`=?");
        sh.insertValue(getFieldValue(entity, tableMapping.getPrimaryKey().getFieldName()));
    }

    public <T> void forDeleteById(Class<T> clazz,
            StringBuilder sql, List<Object> attrs) {
        sql.append("DELETE FROM `").append(TableUtil.getTableName(clazz))
                .append("` WHERE `").append(TableUtil.getPrimaryKeyName(clazz))
                .append("`=?");
    }

    public <T> void forUpdateById(T entity, SQLHelper sh) {
        Class<?> clazz = entity.getClass();
        TableMapping tableMapping = TableUtil.getTableMapping(clazz);
        String tableName = tableMapping.getTableName();
        String tableKey = tableMapping.getPrimaryKey().getColumnName();
        // UPDATE tableName set=?, set=?, ... where tableKey = ?
        sh.append("UPDATE ").append("`").append(tableName).append("`").append(" ");
        Object result = null;
        List<ColumnMapping> cms = tableMapping.getColumnMappings();
        
        int i = 0;
        try {
            for (ColumnMapping cm : cms) {
                if (!tableKey.equals(cm.getColumnName())) {
                    result = getFieldValue(entity, cm.getFieldName());
                    if (result != null) {
                        sh.insertValue(result);
                        if (i != 0) {
                            sh.append(",").append("`")
                                    .append(cm.getColumnName()).append("`")
                                    .append("=?");
                        } else {
                            sh.append(" SET ").append("`")
                                    .append(cm.getColumnName()).append("`")
                                    .append("=?");
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            if(LOG.isErrorEnabled()) {
                LOG.error("", e);
            }
        }
        sh.append(" WHERE ").append("`").append(tableKey).append("`").append("=?");
        sh.insertValue(getFieldValue(entity, TableUtil.getPrimaryKey(clazz).getFieldName()));
    }

    public <T> void forFind(T entity, SQLHelper sh) {
        sh.append("SELECT * FROM ").append("`").append(TableUtil.getTableName(entity.getClass())).append("`");
        handleAfterWhere(entity, sh);
    }
    
    private <T> Object getFieldValue(T entity, String fieldName) {
    	Class<?> clazz = entity.getClass();
        Field field = ClassUtil.getField(clazz, fieldName);
		Object fieldValue = ObjectUtil.getNull();
		try {
			fieldValue = field.get(entity);
		} catch (IllegalArgumentException e) {
			if(LOG.isErrorEnabled()) {
				LOG.error("", e);
			}
		} catch (IllegalAccessException e) {
			if(LOG.isErrorEnabled()) {
				LOG.error("", e);
			}
		}
		return fieldValue;
    }

    public <T> void forFindById(T entity, SQLHelper sh) {
    	Class<?> clazz = entity.getClass();
    	TableMapping tableMapping = TableUtil.getTableMapping(clazz);
        sh.append("SELECT * FROM `");
        sh.append(tableMapping.getTableName());
        sh.append("` WHERE `");
        sh.append(tableMapping.getPrimaryKey().getColumnName());
        sh.append("`=?");
        sh.insertValue(getFieldValue(entity, TableUtil.getPrimaryKey(clazz).getFieldName()));
    }

    private static <T> void handleAfterWhere(T entity, SQLHelper sh){
        Class<?> clazz = entity.getClass();
        TableMapping tableMapping = TableUtil.getTableMapping(clazz);
        try {
            boolean hasWhere = false;
            Object result;
            String tableColumn;
            Field field;
            for (ColumnMapping cm : tableMapping.getColumnMappings()) {
                tableColumn = cm.getColumnName();
                field = ClassUtil.getField(clazz, cm.getFieldName());
                result = field.get(entity);
                if (ObjectUtil.isNotNull(result)) {
                    if (hasWhere) {
                        sh.append(" AND ").append("`").append(tableColumn)
                                .append("`").append("=?");
                    } else {
                        sh.append(" WHERE ").append("`").append(tableColumn)
                                .append("`").append("=?");
                        hasWhere = true;
                    }
                    sh.insertValue(result);
                }
            }
        }catch (Exception e) {
            if(LOG.isErrorEnabled()) {
                LOG.error("", e);
            }
        }
    }

    public String forColumnLabel(String columnLabel) {
        return columnLabel;
    }
}
