package org.jiucheng.log;

public interface ILogFactory {
	
	public Log getLog(Class<?> clazz);
	
	public Log getLog(String name);
}
