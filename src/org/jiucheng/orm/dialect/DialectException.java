package org.jiucheng.orm.dialect;

import org.jiucheng.exception.UncheckedException;

public class DialectException extends UncheckedException{

	private static final long serialVersionUID = -9068007729525824924L;

	public DialectException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DialectException(String msg) {
		super(msg);
	}

	public DialectException(Throwable cause) {
		super(cause);
	}

}
