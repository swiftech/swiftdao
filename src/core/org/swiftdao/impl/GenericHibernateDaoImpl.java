package org.swiftdao.impl;

import java.util.Iterator;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import org.swiftdao.GenericHibernateDao;
import org.swiftdao.pojo.Pojo;

/**
 * @author Wang Yuxing
 * 
 */
public class GenericHibernateDaoImpl extends HibernateKeyedCrudDaoImpl implements GenericHibernateDao {

	@Override
	protected Class getPojoClass() {
		return null;
	}

	@Override
	public void clearInCache(Class<? extends Pojo> clazz) throws DataAccessException {
		this.getSessionFactory().evict(clazz);
		this.getSessionFactory().evictQueries();
	}

	@Override
	public void showEntriesInCache(String regionName) throws DataAccessException {
		Map cacheEntries = null;
		try {
			cacheEntries = this.getSessionFactory().getStatistics().getSecondLevelCacheStatistics(regionName)
					.getEntries();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cacheEntries == null || cacheEntries.size() == 0) {
			return;
		}
		Iterator it = cacheEntries.keySet().iterator();
		for (; it.hasNext();) {
			Object key = it.next();
			Object entity = cacheEntries.get(key);
		}

	}

}
