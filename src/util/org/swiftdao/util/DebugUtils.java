package org.swiftdao.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Wang Yuxing
 * 
 */
public class DebugUtils {

	public static void debugMap(Logger logger, Map map) {
		if (logger.isDebugEnabled()) {
			Iterator it = map.keySet().iterator();
			StringBuilder sb = new StringBuilder();
			sb.append("\r\n{");
			for (; it.hasNext();) {
				Object key = it.next();
				sb.append("\r\n(").append(key).append("=").append(map.get(key)).append(")");
			}
			sb.append("\r\n}");
			logger.debug(sb.toString());
		}
	}
}
