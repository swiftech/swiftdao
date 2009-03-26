package org.swiftdao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 可以执行存储过程的DAO。
 * 
 * @author Wang Yuxing
 * 
 */
public interface BaseExecutableDao extends BaseDao {

	/**
	 * 执行无参数和返回值的存储过程。
	 * 
	 * @param spName 存储过程名。
	 * @throws DataAccessException
	 */
	void execute(String spName) throws DataAccessException;

	/**
	 * 执行无返回值的存储过程
	 * 
	 * @param spName 存储过程名。
	 * @param parameters 存储过程参数。
	 */
	void execute(String spName, Map<String, Object> parameters) throws DataAccessException;

	/**
	 * 
	 * @param spName
	 * @param parameters
	 * @param outParams
	 * @param cusorName
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> executeWithResult(String spName, Map<String, Object> parameters,
			Map<String, Integer> outParams, String cursorName) throws DataAccessException;

	/**
	 * 
	 * @param spName
	 * @param parameters
	 * @param outParams
	 * @param cusorName
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> executeWithResult(Connection conn, String spName, Map<String, Object> parameters,
			Map<String, Integer> outParams, String cursorName) throws DataAccessException;

	/**
	 * TODO 执行存储过程并返回多条记录。
	 * 
	 * @param spName
	 * @return
	 * @throws DataAccessException
	 */
	List executeWithResultset(String spName) throws DataAccessException;

	/**
	 * TODO 执行带参数的存储过程并返回多条记录。
	 * 
	 * @param spName
	 * @param parameters
	 * @return
	 * @throws DataAccessException
	 */
	List executeWithResultset(String spName, Map<String, Object> parameters) throws DataAccessException;

}
