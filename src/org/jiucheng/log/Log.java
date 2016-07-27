package org.jiucheng.log;

import org.jiucheng.util.DefaultPropertiesConstant;
import org.jiucheng.util.DefaultPropertiesUtil;

public abstract class Log {

	static {
		init();
	}

	private static ILogFactory logFactory;
	
	private static boolean hasLog;
	
	public static void init() {
		if(logFactory != null) {
			return;
		}
        if(!DefaultPropertiesUtil.getIsLoaded()) {
            DefaultPropertiesUtil.loadFromXml(DefaultPropertiesConstant.DEFAULT_CFG);
        }
		String has = DefaultPropertiesUtil.getString(DefaultPropertiesConstant.JIUCHENG_LOG, "F");
		if("F".equalsIgnoreCase(has) || "FALSE".equalsIgnoreCase(has)) {
			hasLog = false;
		}else {
			hasLog = true;
		}
		if(!hasLog) {
			logFactory = new NullLogFactory();
			return;
		}
		try {
			Class.forName("org.apache.log4j.Logger");
			Class<?> clazz = Class.forName("org.jiucheng.log.Log4jLogFactory");
			logFactory = (ILogFactory) clazz.newInstance();
		} catch (Exception e) {
			logFactory = new JdkLogFactory();
		}
	}
	
	public static Log getLog(Class<?> clazz) {
		return logFactory.getLog(clazz);
	}
	
	public static Log getLog(String name) {
		return logFactory.getLog(name);
	}
	
	public abstract void debug(String message);
	
	public abstract void debug(String message, Throwable t);
	
	public abstract void info(String message);
	
	public abstract void info(String message, Throwable t);
	
	public abstract void warn(String message);
	
	public abstract void warn(String message, Throwable t);
	
	public abstract void error(String message);
	
	public abstract void error(String message, Throwable t);
	
	public abstract void fatal(String message);
	
	public abstract void fatal(String message, Throwable t);
	
	public abstract boolean isDebugEnabled();

	public abstract boolean isInfoEnabled();

	public abstract boolean isWarnEnabled();

	public abstract boolean isErrorEnabled();
	
	public abstract boolean isFatalEnabled();
}
