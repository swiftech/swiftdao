package org.swiftdao.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Yuxing
 * 
 */
public class ErrorCode {

	protected static final Map<Integer, String> errors = new HashMap<Integer, String>();

	public String translate(int code) {
		Object o = errors.get(code);
		if (o == null) {
			return "[Error Message Not Found]";
		} else {
			return o.toString();
		}
	}
}
