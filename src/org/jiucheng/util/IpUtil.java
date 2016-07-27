package org.jiucheng.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {
    
    private static final long[] MASK = {0xFF000000,0x00FF0000,0x0000FF00,0x000000FF};
    private static final Pattern PATTERN = Pattern.compile("(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
    
    public static boolean isIp(String ip) {
        if(ObjectUtil.isNull(ip)) {
            return false;
        }
        Matcher matcher = PATTERN.matcher(ip);
        return matcher.matches();
    }
    
    public static boolean isIp(Long ip) {
        if(ObjectUtil.isNull(ip) || ip.compareTo(Long.valueOf(0L)) < 0 || ip.compareTo(Long.valueOf(4294967295L)) > 0) {
            return false;
        }
        return true;
    }
    
    public static Long toLong(String ip) {
        if(!isIp(ip)) {
            return ObjectUtil.getNull();
        }
        String[] is = ip.split("\\.");
        long rs = 0L;
        for(int i = 0, len = is.length; i < len; i ++) {
            rs += Math.pow(256,(len - i - 1)) * Long.valueOf(is[i]);
        }
        return rs;
    }
    
    public static String frLong(Long ip) {
        if(!isIp(ip)) {
            return ObjectUtil.getNull();
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0,len = MASK.length; i < len; i ++) {
            sb.append(Long.toString((ip & MASK[i]) >> (len - i - 1) * 8)).append(".");
        }
        return sb.substring(0, sb.length() - 1);
    }
    
}
