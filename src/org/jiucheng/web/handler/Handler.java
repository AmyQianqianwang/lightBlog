/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web.handler;

import javax.servlet.ServletException;

import org.jiucheng.web.WebWrapper;

public interface Handler {
	public boolean before(WebWrapper webWrapper) throws ServletException;
	public void invoke(WebWrapper webWrapper) throws ServletException;
	public boolean after(WebWrapper webWrapper) throws ServletException;
}
