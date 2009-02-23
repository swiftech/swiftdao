package org.swiftdao;

import org.swiftdao.pojo.Pojo;
import org.springframework.dao.DataAccessException;


/**
 * @author Wang Yuxing
 *
 */
public interface GenericHibernateDao extends KeyedCrudDao {

	/**
	 * 
	 * @param clazz
	 * @throws DataAccessException
	 */
	void clearInCache(Class<? extends Pojo> clazz) throws DataAccessException;
	
//	/**
//	 * 
//	 * @param regionName
//	 * @throws DataAccessException
//	 */
//	void clearInCache(String regionName) throws DataAccessException;
	
	/**
	 * @param regionName
	 * @throws DataAccessException
	 */
	void showEntriesInCache(String regionName) throws DataAccessException;
}
