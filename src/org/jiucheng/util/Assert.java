package org.jiucheng.util;

import org.jiucheng.exception.UncheckedException;

public class Assert {
    
    public static String blank(String value) {
        if(StringUtil.isBlank(value)) {
            throw new UncheckedException("内容为空");
        }
        return value;
    }
}
