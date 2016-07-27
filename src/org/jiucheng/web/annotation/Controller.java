package org.jiucheng.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jiucheng.web.handler.DefaultHandler;
import org.jiucheng.web.handler.DefaultOut;
import org.jiucheng.web.handler.Handler;
import org.jiucheng.web.handler.Out;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
	public Class<? extends Handler> value() default DefaultHandler.class;
    
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface POST {
        public String value();
        public Class<? extends Out> out() default DefaultOut.class;
    }
    
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GET {
        public String value();
        public Class<? extends Out> out() default DefaultOut.class;
    }
}
