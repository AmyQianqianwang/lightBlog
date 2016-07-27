package org.jiucheng.exception;

public class CheckedException extends Exception{

    private static final long serialVersionUID = 5488298063230300447L;

    public CheckedException() {
        super();
    }

    public CheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckedException(String message) {
        super(message);
    }

    public CheckedException(Throwable cause) {
        super(cause);
    }

}
