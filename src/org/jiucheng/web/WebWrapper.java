/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web;

import java.util.regex.Matcher;

public class WebWrapper {
    
    private Matcher matcher;
    private Ctrl ctrl;
    
    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }
    
    public Matcher getMatcher() {
        return matcher;
    }
    
    public void setCtrl(Ctrl ctrl) {
        this.ctrl = ctrl;
    }
    
    public Ctrl getCtrl() {
        return ctrl;
    }
}
