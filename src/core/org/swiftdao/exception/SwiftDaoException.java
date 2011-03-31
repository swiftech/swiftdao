package org.swiftdao.exception;

/**
 * 
 * @author Yuxing Wang
 *
 */
@SuppressWarnings("serial")
public class SwiftDaoException extends RuntimeException{

	public SwiftDaoException() {
		super();
	}

	public SwiftDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public SwiftDaoException(String message) {
		super(message);
	}

	public SwiftDaoException(Throwable cause) {
		super(cause);
	}

}
