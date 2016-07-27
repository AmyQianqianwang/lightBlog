/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.exception;

public class UncheckedException extends RuntimeException{
    
    private static final long serialVersionUID = -1957696880857519363L;
    
    public UncheckedException(String msg) {
        super(msg);
    }
    
    public UncheckedException(Throwable cause) {
        super(cause);
    }
    
    public UncheckedException(String msg,Throwable cause) {
        super(msg, cause);
    }
}
