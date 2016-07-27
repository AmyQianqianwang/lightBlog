package org.jiucheng.util;

import java.util.List;
import java.util.Properties;

public class DefaultPropertiesUtil {
    private static Properties PROP;
    private static boolean isLoaded;
    
    public static boolean getIsLoaded() {
        return isLoaded;
    }
    
    public static void loadFromXml(String path) {
        isLoaded = true;
        PROP = PropertiesUtil.loadFromXml(path);
    }

    public static void load(String path) {
        isLoaded = true;
        PROP = PropertiesUtil.loadProperties(path);
    }
    
    public static Properties getProperties() {
    	return new Properties(PROP);
    }
    
    public static String get(String key) {
        return PropertiesUtil.get(PROP, key);
    }
    
    public static String get(String key, String defaultValue) {
        return PropertiesUtil.get(PROP, key, defaultValue);
    }
    
    public static String getString(String key) {
        return PropertiesUtil.getString(PROP, key);
    }
    
    public static String getString(String key, String defaultValue) {
        return PropertiesUtil.getString(PROP, key, defaultValue);
    }
    
    public static List<String> list(String key) {
        return PropertiesUtil.list(PROP, key, StringUtil.COMMA);
    }
    
    public static String[] array(String key) {
        return PropertiesUtil.array(PROP, key, StringUtil.COMMA);
    }
    
}

