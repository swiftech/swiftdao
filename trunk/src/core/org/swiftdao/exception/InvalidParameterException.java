package org.swiftdao.exception;

import java.sql.SQLWarning;

import org.springframework.jdbc.SQLWarningException;

/**
 * @author Wang Yuxing
 * 
 */
public class InvalidParameterException extends SQLWarningException {

	private static final long serialVersionUID = 1L;

	public InvalidParameterException(String msg) {
		super(msg, null);
	}

	public InvalidParameterException(String msg, SQLWarning ex) {
		super(msg, ex);
	}

}
