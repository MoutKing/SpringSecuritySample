package com.goldwind.securitysample.exception;

public class UsernameIsExitedException extends BaseException {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9037927276606254273L;

	public UsernameIsExitedException(String msg) {
        super(msg);
    }

    public UsernameIsExitedException(String msg, Throwable t) {
        super(msg, t);
    }
}