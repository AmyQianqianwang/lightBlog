package org.jiucheng.json;

public class JSON {
	
	public static String toJSONString(Object obj) {
	    if(obj == null) {
	        return "null";
	    }
	    Class<?> clazz = obj.getClass();
	    if(clazz == String.class) {
	        return "\"" + obj.toString() + "\"";
	    }
	    if(clazz == Long.class || clazz == long.class || 
	            clazz == Integer.class || clazz == int.class || 
	            clazz == Short.class || clazz == short.class || 
	            clazz == Byte.class || clazz == byte.class) {
	        String.valueOf(obj);
	    }
	    if(clazz == Float.class || clazz == float.class || 
	            clazz == Double.class || clazz == double.class) {
	        return String.valueOf(obj);
	    }
	    if(clazz == Character.class || clazz == char.class) {
	        return "\"" + String.valueOf(obj) + "\"";
	    }
	    if(clazz == Boolean.class || clazz == boolean.class) {
	        return String.valueOf(obj);
	    }
	    return null;
	}
	
	public static <T> T fromJSONString(Class<T> clazz, String jsonString) {
	    //TODO
		throw new UnsupportedOperationException("/TODO");
	}
}
