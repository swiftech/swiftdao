package org.swiftdao.exception;

import org.springframework.dao.DataAccessException;

/**
 * TODO May be replaced with spring exceptions.
 * @author Wang Yuxing
 *
 */
public class EntityNotFoundException extends DataAccessException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String msg) {
		super(msg);
	}

	public EntityNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
