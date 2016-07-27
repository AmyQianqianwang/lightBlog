package org.jiucheng.orm.meta;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiucheng(jiucheng.org@gmail.com)
 *
 */
public class TableMapping implements Serializable {

	private static final long serialVersionUID = -4812702066527169079L;
	private String tableName;
	private String className;
	private ColumnMapping primaryKey;
	private List<ColumnMapping> columnMappings;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public ColumnMapping getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(ColumnMapping primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ColumnMapping> getColumnMappings() {
		return columnMappings;
	}

	public void setColumnMappings(List<ColumnMapping> columnMappings) {
		this.columnMappings = columnMappings;
	}
}
