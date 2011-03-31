package org.swiftdao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.swiftdao.exception.SwiftDaoException;

/**
 * 可以执行存储过程的DAO。
 * 
 * @author Wang Yuxing
 * 
 */
public interface ExecutableDao extends Dao {

	/**
	 * 执行无参数和返回值的存储过程。
	 * 
	 * @param spName 存储过程名。
	 * @throws SwiftDaoException	
	 */
	void execute(String spName) throws SwiftDaoException;

	/**
	 * 执行无返回值的存储过程
	 * 
	 * @param spName 存储过程名。
	 * @param parameters 存储过程参数。
	 * @throws SwiftDaoException
	 */
	void execute(String spName, Map<String, Object> parameters) throws SwiftDaoException;

	/**
	 * 
	 * @param spName
	 * @param parameters
	 * @param outParams
	 * @param cursorName
	 * @return
	 * @throws SwiftDaoException
	 */
	Map<String, Object> executeWithResult(String spName, Map<String, Object> parameters,
			Map<String, Integer> outParams, String cursorName) throws SwiftDaoException;

	/**
	 * 
	 * @param conn
	 * @param spName
	 * @param parameters
	 * @param outParams
	 * @param cursorName
	 * @return
	 * @throws SwiftDaoException
	 */
	Map<String, Object> executeWithResult(Connection conn, String spName, Map<String, Object> parameters,
			Map<String, Integer> outParams, String cursorName) throws SwiftDaoException;

	/**
	 * TODO 执行存储过程并返回多条记录。
	 * 
	 * @param spName
	 * @return
	 * @throws SwiftDaoException
	 */
	List executeWithResultset(String spName) throws SwiftDaoException;

	/**
	 * TODO 执行带参数的存储过程并返回多条记录。
	 * 
	 * @param spName
	 * @param parameters
	 * @return
	 * @throws SwiftDaoException
	 */
	List executeWithResultset(String spName, Map<String, Object> parameters) throws SwiftDaoException;

}
