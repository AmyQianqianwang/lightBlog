/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.orm.meta;

import java.io.Serializable;

/**
 * 
 * @author jiucheng(jiiucheng.org@gmail.com)
 *
 */
public class ColumnMapping implements Serializable {

	private static final long serialVersionUID = -486857487832323296L;
	private String columnName;
	private String fieldName;
	private Class<?> fieldType;
	
	public ColumnMapping() {
		
	}
	
	public ColumnMapping(String columnName, String fieldName, Class<?> field) {
		this.columnName = columnName;
		this.fieldName = fieldName;
		this.fieldType = Long.class;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}
}
