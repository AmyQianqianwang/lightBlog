package org.jiucheng.log;

public class NullLogFactory implements ILogFactory{

    NullLogFactory() {
    }
    
	public Log getLog(Class<?> clazz) {
		return log;
	}

	public Log getLog(String name) {
		return log;
	}

	private static final Log log = new Log() {
		
		@Override
		public void warn(String message, Throwable t) {
			
		}
		
		@Override
		public void warn(String message) {
			
		}
		
		@Override
		public boolean isWarnEnabled() {
			return false;
		}
		
		@Override
		public boolean isInfoEnabled() {
			return false;
		}
		
		@Override
		public boolean isFatalEnabled() {
			return false;
		}
		
		@Override
		public boolean isErrorEnabled() {
			return false;
		}
		
		@Override
		public boolean isDebugEnabled() {
			return false;
		}
		
		@Override
		public void info(String message, Throwable t) {
		}
		
		@Override
		public void info(String message) {
		}
		
		@Override
		public void fatal(String message, Throwable t) {
		}
		
		@Override
		public void fatal(String message) {
		}
		
		@Override
		public void error(String message, Throwable t) {
		}
		
		@Override
		public void error(String message) {
		}
		
		@Override
		public void debug(String message, Throwable t) {
		}
		
		@Override
		public void debug(String message) {
		}
	};
}
