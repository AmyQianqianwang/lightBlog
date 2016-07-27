/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jiucheng.exception.UncheckedException;

public class PropertiesUtil {
    
    private static final Pattern REPLACE_PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");
    
    public static Properties loadProperties(String propertiesPath) {
        Properties prop = new Properties();
        InputStream is = ClassUtil.getClassLoader().getResourceAsStream(propertiesPath);
        try {
            prop.load(is);
        } catch (Exception e) {
            throw new UncheckedException("加载properties资源失败:" + propertiesPath,e);
        }finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
    
    public static Properties loadFromXml(String xmlPath) {
        Properties prop = new Properties();
        InputStream is = ClassUtil.getClassLoader().getResourceAsStream(xmlPath);
        try {
            prop.loadFromXML(is);
        } catch (Exception e) {
            throw new UncheckedException("加载xml资源失败:" + xmlPath, e);
        }finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
    
    public static String get(Properties prop, String key) {
        return prop.getProperty(key);
    }
    
    public static String get(Properties prop, String key, String defaultValue) {
        String r = get(prop, key);
        if(r == StringUtil.NULL) {
            r = defaultValue;
        }
        return r;
    }
    
    public static String getString(Properties prop, String key) {
        return getString(prop, key, StringUtil.NULL);
    }
    
    public static String getString(Properties prop, String key, String defaultValue) {
        String r = getStringReplaceDollar(prop, key, defaultValue);
        if(!StringUtil.isBlank(r)) {
        	r = r.replace(StringUtil.PERCENT_CLASSPATH_PERCENT, ClassUtil.isWin() ? ClassUtil.getClasspath().substring(1) : ClassUtil.getClasspath());
        }
        return r;
    }
    
    private static String getStringReplaceDollar(Properties prop, String key, String defaultValue) {
        String r = get(prop, key);
        if(r == StringUtil.NULL) {
            r = defaultValue;
        }
        if(r != StringUtil.NULL) {
            Matcher m = REPLACE_PATTERN.matcher(r);
            int start;
            int end;
            String target;
            while(m.find()) {
                start = m.start();
                end = m.end();
                target = m.group(1);
                r = StringUtil.replace(r, get(prop, target, StringUtil.EMPTY), start, end);
                m = REPLACE_PATTERN.matcher(r);
            }
        }
        return r;
    }
    
    public static List<String> list(Properties prop, String key, String separator) {
        return Arrays.asList(array(prop, key, separator));
    }
    
    public static String[] array(Properties prop, String key, String separator) {
        String[] r = new String[]{};
        String str = getString(prop, key);
        if(!StringUtil.isEmpty(str)) {
            r = str.split(separator);
        }
        return r;
    }
    
    public static void main(String[] args) {
		System.out.println(ClassUtil.getClasspath());
	}
}
