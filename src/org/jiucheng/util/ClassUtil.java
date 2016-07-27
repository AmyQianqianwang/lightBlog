/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jiucheng.exception.UncheckedException;

public class ClassUtil {
    
    /**
     * 当前ClassLoader
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    public static String getClasspath() {
        return getClassLoader().getResource("").getPath();
    }
    
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }
    
    public static Class<?> loadClass(String className, boolean isInited) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, isInited, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new UncheckedException(e);
        }
        return clazz;
    }
    
    public static List<Field> listField(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        while(clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
    
    public static <T> List<Method> listMethod(Class<T> clazz) {
        return listMethod(clazz,StringUtil.NULL);
    }
    
    public static <T> List<Method> listMethod(Class<T> clazz, String regex) {
        List<Method> results = new ArrayList<Method>();
        if (regex != null) {
            for (Method method : clazz.getMethods()) {
                if (method.getName().matches(regex)) {
                    results.add(method);
                }
            }
        } else {
             results = Arrays.asList(clazz.getMethods());
        }
        return results;
    }
    
	public static <T> Field getField(Class<T> clazz, String fieldName) {
		List<Field> listF = listField(clazz);
		for(Field field : listF) {
			if(field.getName().equals(fieldName)) {
				field.setAccessible(true);
				return field;
			}
		}
		return ObjectUtil.getNull();
	}
	
    public static boolean isWin() {
    	boolean isWin = false;
    	String osName = System.getProperty("os.name");
    	if(osName.startsWith("win") || osName.startsWith("Win")) {
    		isWin = true;
    	}
		return isWin;
    }
}
