/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jiucheng
 *
 */
public class ObjectUtil {

	private static final Object NULL = null;

	public static boolean isNull(Object obj) {
		return obj == NULL;
	}

	public static boolean isNotNull(Object obj) {
		return obj != NULL;
	}

	public static byte[] objectToBytes(Object obj) {
		byte[] bytes = getNull();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutput oo = null;
		try {
			oo = new ObjectOutputStream(baos);
			oo.writeObject(obj);
			oo.flush();
			bytes = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oo != null) {
					oo.close();
				}
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return bytes;
	}
	
	public static Object bytesToObject(byte[] bytes) {
		Object obj = getNull();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInput oi = null;
		try {
			oi = new ObjectInputStream(bais);
			obj = oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ObjectUtil.isNotNull(oi)) {
					oi.close();
				}
				bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getNull() {
		return (T) NULL;
	}
	
	//boolean byte char short int long float double 
	@SuppressWarnings("unchecked")
	public static <T> T toThis(Class<T> clazz, Object obj) {
		if(isNull(obj) || isNull(clazz)) {
			return getNull();
		}
		if(clazz.isInstance(obj)) {
			return (T) obj;
		}
		if(clazz == String.class) {
			return (T) obj.toString();
		}
		if(clazz == Boolean.class) {
			return (T) toBoolean(obj);
		}
		//TODO
		//byte
		//char
		//short
		if(clazz == Integer.class) {
			return (T) Integer.getInteger(obj.toString());
		}
		if(clazz == Long.class) {
			return (T) toLong(obj);
		}
		if(clazz == BigDecimal.class) {
			return (T) toBigDecimal(obj);
		}
		if(clazz == Date.class) {
			return (T) obj;
		}
		return getNull();
	}
	
	public static Boolean toBoolean(Object obj) {
		if(isNull(obj)) {
			return getNull();
		}
		if(obj instanceof Boolean) {
			return (Boolean) obj;
		}
		if("true".equalsIgnoreCase(obj.toString())) {
			return Boolean.TRUE;
		}
		if("false".equalsIgnoreCase(obj.toString())) {
			return Boolean.FALSE;
		}
		return getNull();
	}
	
	public static Long toLong(Object obj) {
		if(isNull(obj)) {
			return getNull();
		}
		if(obj instanceof Long) {
			return (Long) obj;
		}
		if(obj instanceof Integer || obj instanceof Short || obj instanceof Byte) {
			return Long.parseLong(obj.toString());
		}
		String objStr = obj.toString();
		if(objStr.matches("[-+]?[0-9]+")) {
			return Long.parseLong(objStr);
		}
		return getNull();
	}
	
	public static BigDecimal toBigDecimal(Object obj) {
		if(isNull(obj)) {
			return getNull();
		}
		if(obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		if(obj instanceof Integer) {
			return new BigDecimal((Integer)obj);
		}
		if(obj instanceof Long) {
			return new BigDecimal((Long)obj);
		}
		if(obj instanceof Double) {
			return new BigDecimal((Double)obj);
		}
		if(obj.toString().matches(".*[0-9]+.*") && obj.toString().matches("[-+]?[0-9]*.[0-9]*")) {
			return new BigDecimal(obj.toString());
		}
		return getNull();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Map entityToMap(T t) {
		Map map = new HashMap<String, Object>();
		if(ObjectUtil.isNull(t)) {
			return ObjectUtil.getNull();
		}
		List<Field> listField = ClassUtil.listField(t.getClass());
		try {
			for (Field field : listField) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(t));
			}
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T mapToEntity(Map map, Class<T> clazz) {
		if(map == null || map.size() == 0) {
			return ObjectUtil.getNull();
		}
		try {
			T t = clazz.newInstance();
			List<Field> listField = ClassUtil.listField(clazz);
			Object obj;
			for(Field field : listField) {
				obj = map.get(field.getName());
				if(ObjectUtil.isNotNull(obj)) {
					field.setAccessible(true);
					field.set(t, toThis(field.getType(), obj));
				}
			}
			return t;
		}catch (InstantiationException e) {
		}catch (IllegalAccessException e) {
		}
		return ObjectUtil.getNull();
	}
	
	public static void main(String[] args) {
		System.out.println(toLong("+123"));
	}
}
