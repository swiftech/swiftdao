package org.swiftdao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.swiftdao.exception.SwiftDaoException;
import org.swiftdao.impl.JdbcDaoImpl;

/**
 * 所有基于JDBC的DAO接口的基础接口。
 * 
 * @author Wang Yuxing
 * @version 1.0
 * @see JdbcDaoImpl
 */
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public interface JdbcDao extends ExecutableDao{
	
	/**
	 * 查找单一主键的对象。
	 * @param pkName
	 * @param id
	 * @return
	 */
//	E find(String pkName, Long id);

	/**
	 * 批量执行插入或者更新操作。
	 * @param sql 批量更新语句，变量值用”?“表示，如”UPDATE SET NAME=? WHERE ID = ?“。
	 * @param parameters
	 * @return
	 * @throws DataAccessException
	 */
	int[] batchUpdate(String sql, List<List<Object>> parameters) throws SwiftDaoException;

}
