package org.jiucheng.web.handler;

import javax.servlet.ServletException;

import org.jiucheng.web.WebWrapper;

public abstract class AbstractHandler implements Handler{
    
    public boolean before(WebWrapper webWrapper) throws ServletException {
        return true;
    }
    
    public boolean after(WebWrapper webWrapper) throws ServletException {
        return true;
    }
}
