package org.jiucheng.aop;

import org.jiucheng.exception.CheckedException;

public class AopException extends CheckedException{

	private static final long serialVersionUID = 2822306994391371274L;
	
    public AopException() {
        super();
    }

    public AopException(String message, Throwable cause) {
        super(message, cause);
    }

    public AopException(String message) {
        super(message);
    }

    public AopException(Throwable cause) {
        super(cause);
    }
}
