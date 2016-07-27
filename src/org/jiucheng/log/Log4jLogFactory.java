package org.jiucheng.log;

public class Log4jLogFactory implements ILogFactory{

    public Log4jLogFactory() {
    }
    
	public Log getLog(Class<?> clazz) {
		return new Log4jLog(clazz);
	}

	public Log getLog(String name) {
		return new Log4jLog(name);
	}
}
