package org.jiucheng.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jLog extends Log{

    private static final String callerFQCN = Log4jLog.class.getName();
    
	private Logger log;
	
	public Log4jLog() {
    }
	
	Log4jLog(Class<?> clazz) {
		log = Logger.getLogger(clazz);
	}
	
	Log4jLog(String name) {
		log = Logger.getLogger(name);
	}
	
	@Override
	public void info(String message) {
		log.log(callerFQCN, Level.INFO, message, null);
	}
	
	@Override
	public void info(String message, Throwable t) {
		log.log(callerFQCN, Level.INFO, message, t);
	}
	
	@Override
	public void debug(String message) {
		log.log(callerFQCN, Level.DEBUG, message, null);
	}
	
	@Override
	public void debug(String message, Throwable t) {
		log.log(callerFQCN, Level.DEBUG, message, t);
	}
	
	@Override
	public void warn(String message) {
		log.log(callerFQCN, Level.WARN, message, null);
	}
	
	@Override
	public void warn(String message, Throwable t) {
		log.log(callerFQCN, Level.WARN, message, t);
	}
	
	@Override
	public void error(String message) {
		log.log(callerFQCN, Level.ERROR, message, null);
	}
	
	@Override
	public void error(String message, Throwable t) {
		log.log(callerFQCN, Level.ERROR, message, t);
	}
	
	@Override
	public void fatal(String message) {
		log.log(callerFQCN, Level.FATAL, message, null);
	}
	
	@Override
	public void fatal(String message, Throwable t) {
		log.log(callerFQCN, Level.FATAL, message, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return log.isEnabledFor(Level.WARN);
	}

	@Override
	public boolean isErrorEnabled() {
		return log.isEnabledFor(Level.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return log.isEnabledFor(Level.FATAL);
	}

}
