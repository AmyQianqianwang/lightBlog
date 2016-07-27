package org.jiucheng.ioc;

import java.lang.annotation.Annotation;
import java.util.List;

import org.jiucheng.util.ObjectUtil;

public class ClassTemplate {

	
    public static List<Class<?>> listClass(String packageName) {
    	ClassScanner classScanner = new AbstractClassScanner() {
    		public boolean accept(Class<?> clazz) {
    			return true;
    		}
    	};
		return classScanner.listClass(packageName);
    }
    
	public static List<Class<?>> listClassByAnnotation(String packageName, final Class<? extends Annotation>... annotationClasses) {
       	ClassScanner classScanner = new AbstractClassScanner() {
    		public boolean accept(Class<?> clazz) {
    			boolean r = false;
    			for(Class<? extends Annotation> annotaion : annotationClasses) {
    				if(ObjectUtil.isNotNull(clazz.getAnnotation(annotaion))) {
    					r = true;
    					break;
    				}
    			}
    			return r;
    		}
    	};
		return classScanner.listClass(packageName);
    }
    
    public static List<Class<?>> listClassBySuper(String packageName, final Class<?>... classes) {
       	ClassScanner classScanner = new AbstractClassScanner() {
    		public boolean accept(Class<?> clazz) {
    			boolean r = false;
    			for(Class<?> cls : classes) {
    				while (ObjectUtil.isNotNull(clazz)) {
						if(clazz == cls) {
							r = true;
							break;
						}
						clazz = clazz.getSuperclass();
					}
    			}
    			return r;
    		}
    	};
		return classScanner.listClass(packageName);
    	
    }
}
