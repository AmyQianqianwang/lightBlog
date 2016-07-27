package org.jiucheng.orm;

import org.jiucheng.exception.UncheckedException;

public class DataAccessException extends UncheckedException{

	private static final long serialVersionUID = -3455807895317113815L;

	public DataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
