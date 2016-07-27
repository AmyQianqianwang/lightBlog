package org.jiucheng.ioc;

import java.util.List;

public interface ClassScanner {
    public List<Class<?>> listClass(String packageName);
    public boolean accept(Class<?> clazz);
}
