package com.goldwind.securitysample.exception;

/**
 * 描述：
 * <p>
 *
 * @author: LiSong
 * @date: 2018/4/11 23:06
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

