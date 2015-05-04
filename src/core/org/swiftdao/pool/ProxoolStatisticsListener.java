package org.swiftdao.pool;

import org.logicalcobwebs.proxool.admin.StatisticsIF;
import org.logicalcobwebs.proxool.admin.StatisticsListenerIF;

/**
 * TODO
 * @author yuxing
 * 
 */
public class ProxoolStatisticsListener implements StatisticsListenerIF {

	@Override
	public void statistics(String alias, StatisticsIF statistics) {
		System.out.println(java.lang.String.format("Pool statistics for :%s", alias));
		System.out.println(java.lang.String.format("Average active time and count: %s %s", statistics.getAverageActiveTime(), statistics.getAverageActiveCount()));
		System.out.println(java.lang.String.format("Served per second and count: %s %d", statistics.getServedPerSecond(), statistics.getServedCount()));
	}

}
