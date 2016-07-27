/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jiucheng.util.StringUtil;

/**
 * 
 * @author jiucheng
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
	public String value() default StringUtil.EMPTY;
}
