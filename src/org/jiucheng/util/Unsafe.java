package org.jiucheng.util;

import java.lang.reflect.Field;

import org.jiucheng.exception.UncheckedException;

public class Unsafe {
    
    private static sun.misc.Unsafe UNSAFE;
    
    public static sun.misc.Unsafe getUnsafe() {
        if(ObjectUtil.isNotNull(UNSAFE)) {
            return UNSAFE;
        }
        try {
            Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (sun.misc.Unsafe) f.get(null);
            return UNSAFE;
        } catch (NoSuchFieldException e) {
            throw new UncheckedException(e);
        } catch (SecurityException e) {
            throw new UncheckedException(e);
        } catch (IllegalArgumentException e) {
            throw new UncheckedException(e);
        } catch (IllegalAccessException e) {
            throw new UncheckedException(e);
        }
    }
    
    /**
     * 实例化一个对象(绕过构造器)
     * @param clazz
     * @return
     */
    public static <T> T allocateInstance(Class<T> clazz) {
        try {
            return (T) getUnsafe().allocateInstance(clazz);
        } catch (InstantiationException e) {
            throw new UncheckedException(e);
        }
    }
    
    public static int arrayBaseOffset(Class<?> clazz) {
        return getUnsafe().arrayBaseOffset(clazz);
    }
}
