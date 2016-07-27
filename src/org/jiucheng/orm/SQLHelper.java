package org.jiucheng.orm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jiucheng.log.Log;

public class SQLHelper {
	
    private final static Log log = Log.getLog("SQL");
    
	private StringBuilder sql;
	private List<Object> values;
	
	public SQLHelper() {
		sql = new StringBuilder();
		values = new ArrayList<Object>();
	}
	
	public SQLHelper(String sql) {
		this();
		this.sql.append(sql);
	}
	
	public SQLHelper clearSql() {
	    sql = new StringBuilder();
	    return this;
	}
	
	public SQLHelper clearValues() {
	    values = new ArrayList<Object>();
	    return this;
	}
	
	public SQLHelper append(String sql) {
		this.sql.append(sql);
		return this;
	}
	
	public SQLHelper insertValue(Object obj) {
		values.add(obj);
		return this;
	}
	
	public String getSql() {
	    String s = sql.toString().replaceAll("\\s+", " ");
	    if(log.isInfoEnabled()) {
	        log.info(s + " " + Arrays.toString(values.toArray()));
	    }
		return s;
	}
	
	public String getCountSql() {
		String rs = "SELECT COUNT(1) num FROM (" + sql.toString() + ") _tab";
		rs.replaceAll("\\s+", " ");
		return rs;
	}
	
	public void setValues(List<Object> values) {
		this.values = values;
	}
	
	public List<Object> getValues() {
		return values;
	}
}
