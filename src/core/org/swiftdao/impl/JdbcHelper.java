package org.swiftdao.impl;

import java.util.Date;

/**
 * @author Wang Yuxing
 *
 */
public class JdbcHelper {
	
	public static int translateType(Object o ){
		if(o instanceof String){
			return java.sql.Types.VARCHAR;
		} else if(o instanceof Integer){
			return java.sql.Types.INTEGER;
		} else if(o instanceof Long){
			return java.sql.Types.BIGINT;
		} else if(o instanceof Date){
			return java.sql.Types.DATE;
		}
		return java.sql.Types.VARCHAR;
	}
}
