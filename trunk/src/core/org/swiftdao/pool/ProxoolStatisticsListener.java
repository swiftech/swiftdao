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
		System.out.println("Pool statistics for :" + alias);
		System.out.println("Average active time and count: " + statistics.getAverageActiveTime() + " " + statistics.getAverageActiveCount());
		System.out.println("Served per second and count: " + statistics.getServedPerSecond() + " " + statistics.getServedCount());
	}

}
