package org.jiucheng.log;

public class JdkLogFactory implements ILogFactory{

    public JdkLogFactory() {
    }
    
	public Log getLog(Class<?> clazz) {
		return new JdkLog(clazz);
	}

	public Log getLog(String name) {
		return new JdkLog(name);
	}

}
