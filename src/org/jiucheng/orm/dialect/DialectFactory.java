package org.jiucheng.orm.dialect;

import java.util.EnumMap;

import org.jiucheng.orm.dialect.impl.MySQLDialect;
import org.jiucheng.util.ObjectUtil;

public class DialectFactory {
	
	private static final EnumMap<DialectType, Dialect> DIALECT_POOL = new EnumMap<DialectType, Dialect>(DialectType.class);
	
	public static Dialect getDialect(DialectType dialectType) {
		Dialect dialect = DIALECT_POOL.get(dialectType);
		if(ObjectUtil.isNull(dialect)) {
			dialect = createDialect(dialectType);
			DIALECT_POOL.put(dialectType, dialect);
		}
		return dialect;
	}
	
	private static Dialect createDialect(DialectType dialectType) {
		Dialect dialect = new MySQLDialect();
		switch (dialectType) {
		case MYSQL:
			dialect = new MySQLDialect();
			break;
		default:
			break;
		}
		return dialect;
	}
}
