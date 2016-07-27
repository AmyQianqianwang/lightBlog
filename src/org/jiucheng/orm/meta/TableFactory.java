package org.jiucheng.orm.meta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jiucheng.orm.annotation.Column;
import org.jiucheng.orm.annotation.PrimaryKey;
import org.jiucheng.orm.annotation.Table;
import org.jiucheng.util.ClassUtil;
import org.jiucheng.util.ObjectUtil;

public class TableFactory {
	private static final Map<String, TableMapping> TABLE_MAPPINGS = new HashMap<String, TableMapping>();

	private static final ColumnMapping DEFAULT_PRIMARY_KEY = new ColumnMapping("id", "id", Long.class);
	
	public static <T> TableMapping getTableMapping(Class<T> clazz) {
		TableMapping tm = TABLE_MAPPINGS.get(clazz.getCanonicalName());
		if(ObjectUtil.isNull(tm)) {
			tm = load(clazz);
			TABLE_MAPPINGS.put(clazz.getCanonicalName(), tm);
		}
		return tm;
	}
	
	private static <T> TableMapping load(Class<T> clazz) {
		TableMapping tableMapping = new TableMapping();
		tableMapping.setClassName(clazz.getCanonicalName());
		tableMapping.setTableName(getTableName(clazz));
		setColumnMapping(tableMapping, clazz);
		return tableMapping;
	}
	
	private static <T> void setColumnMapping(TableMapping tableMapping, Class<T> clazz) {
		List<Field> listField = ClassUtil.listField(clazz);
		List<ColumnMapping> rs = new ArrayList<ColumnMapping>();
		ColumnMapping cm;
		boolean hasPrimaryKey = false;
		for(Field field : listField) {
			if(field.getName().equals("serialVersionUID")) {
				continue;
			}
			cm = new ColumnMapping();
			cm.setColumnName(getColumnName(field));
			cm.setFieldName(field.getName());
			cm.setFieldType(field.getType());
			if(hasPrimaryKey == false && field.getAnnotation(PrimaryKey.class) != null) {
				tableMapping.setPrimaryKey(cm);
			}
			rs.add(cm);
		}
		if(hasPrimaryKey == false) {
			tableMapping.setPrimaryKey(DEFAULT_PRIMARY_KEY);
		}
		tableMapping.setColumnMappings(rs);
	}
	
	private static <T> String getTableName(Class<T> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		if (table != null && !table.value().isEmpty()) {
			return table.value();
		}
		StringBuilder sb = new StringBuilder();
		char[] chars = clazz.getSimpleName().toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		for (char ch : chars) {
			if (ch >= 65 && ch <= 90) {
				sb.append("_");
			}
			sb.append(Character.toLowerCase(ch));
		}
		return sb.toString();
	}
	
	private static String getColumnName(Field field) {
		Column col = field.getAnnotation(Column.class);
		if(col != null && !col.value().isEmpty()) {
			return col.value();
		}
		
		StringBuilder sb = new StringBuilder();
		char[] chars = field.getName().toCharArray();
		// 65-90 A-Z
		// 97-112 a-z
		for (char ch : chars) {
			if (ch >= 65 && ch <= 90) {
				sb.append("_");
			}
			sb.append(Character.toLowerCase(ch));
		}
		return sb.toString();
	}
}
