/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.util;

public class StringUtil {
    
    public static final String NULL = null;
    public static final String[] NULL_ARRAY = new String[]{};
    public static final String EMPTY = "";
    public static final String COMMA = ",";
    public static final String UTF_8 = "UTF-8";
    public static final String REGEX_ZH = "[\u4E00-\u9FA5]+";
    public static final String PERCENT_CLASSPATH_PERCENT = "%classpath%";
    
    /**
     * 首字母小写
     * @param value
     * @return
     */
    public static String firstCharToLowerCase(String value) {
    	if(!isBlank(value)) {
    		value = value.substring(0, 1).toLowerCase() + value.substring(1);
    	}
    	return value;
    }
    
    /**
     * 查找text中是否有中文
     * @param text
     * @param beginIndex
     * @return
     */
    public static int indexOfZh(String text,int beginIndex) {
        //[\u4E00-\u9FA5]
        text = text.substring(beginIndex);
//        if(text.equals("")) {
//            return -1;
//        }
        if(text.matches(REGEX_ZH)) {
            return beginIndex;
        }
        if(!text.matches(".*" + REGEX_ZH + ".*")) {
        	return -1;
        }
        String[] tmps = text.split(REGEX_ZH);
        //System.out.println(tmps.length);
        return beginIndex + tmps[0].length();
    }
    
    /**
     * 判断是否含有中文
     * @param value
     * @return
     */
    public static boolean hasZh(String value) {
        if(isNotBlank(value) && value.matches("[\\s\\S]*" + REGEX_ZH + "[\\s\\S]*")) {
        	return true;
        }
        return false;
    }
    
    /**
     * 逆序返回
     * text - > txet
     * @param text
     * @return
     */
    public static String desc(String text) {
        StringBuilder sb = new StringBuilder();
        char[] chs = text.toCharArray();
        for(int i = chs.length - 1; i >= 0; i --) {
            sb.append(chs[i]);
        }
        return sb.toString();
    }
    
    /**
     * 从后向前搜索key在text中 是否有num出现 有返回key的index
     * @param text
     * @param key
     * @param num
     * @return
     */
    public static int lastIndexOf(String text, String key, int num) {
        int index = -1;
        int fromIndex = text.length() - 1;
        for(int i = 0; i < num; i ++) {
            index = text.lastIndexOf(key, fromIndex);
            fromIndex = index - 1;
        }
        return index;
    }
    
    /**
     * 从前向后搜索key在text中 是否有num出现 有返回key的index
     * @param text
     * @param key
     * @param num
     * @return
     */
    public static int indexOf(String text, String key, int num) {
        int index = -1;
        int fromIndex = 0;
        for(int i = 0; i < num; i ++) {
            index = text.indexOf(key, fromIndex);
            fromIndex = index + 1;
        }
        return index;
    }
    
    public static String createText(String text, int count) {
    	StringBuilder sb = new StringBuilder();
    	if(count > 0) {
    		for(int i = 0; i < count; i ++) {
    			sb.append(text);
    		}
    	}
    	return sb.toString();
    }
    
    /**
     * value = null -> true</br>
     * @param text
     * @return
     */
    public static boolean isEmpty(String value) {
        boolean isEmpty = false;
        if(value == StringUtil.NULL) {
            isEmpty = true;
        }
        return isEmpty;
    }
    
    /**
     * value = null -> true</br>
     * value = "" -> true</br>
     * value = " " -> true</br>
     * @param value
     * @return
     */
    public static boolean isBlank(String value) {
        boolean isBlank = false;
        if(value == StringUtil.NULL) {
            isBlank = true;
        }else if(value.trim().isEmpty()) {
            isBlank = true;
        }
        return isBlank;
    }
    
    public static boolean isNotBlank(String value) {
    	return !isBlank(value);
    }
    
    /**
     * replace("jiucheng.org", "com",  9, 12) -> jiucheng.com
     * 
     * @param target
     * @param replaced
     * @param start
     * @param end
     * @return
     */
    public static String replace(String target,String replaced, int start, int end) {
        String r = target.substring(0, start) + replaced + target.substring(end);
        return r;
    }
    
    /**
     * 二进制转换到十六进制字符串
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
    	StringBuilder sb = new StringBuilder();
    	if(ObjectUtil.isNotNull(bytes)) {
        	String tmp;
        	for(int i = 0, l = bytes.length; i < l;  i ++) {
        		tmp = Integer.toHexString(bytes[i] & 0xff);
        		if(tmp.length() == 1) {
        			sb.append("0");
        		}
        		sb.append(tmp);
        	}
    	}
    	return sb.toString();
    }
    
    /**
     * userName -> user_name
     * Password -> _password
     * @param v
     * @return
     */
    public static String toUnderscore(String v) {
    	StringBuilder sb = new StringBuilder();
		char[] chars = v.toCharArray();
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
    
    public static void main(String[] args) {
        System.out.println(replace("jiucheng.org", "com",  9, 12));
        System.out.println(toUnderscore("userName"));
    }
}
