/**
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jiucheng.util.StringUtil;

/**
 * 加入容器,同时配合@Transaction支持事务处理
 * @author jiucheng
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
	public String value() default StringUtil.EMPTY;
}
