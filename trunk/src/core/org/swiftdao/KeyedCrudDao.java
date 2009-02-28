package org.swiftdao;

import org.swiftdao.pojo.KeyedPojo;
import java.io.Serializable;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * 具有主键实体的基础DAO接口，提供了常用的通过单一主键进行的操作。
 * 
 * @author Wang Yuxing
 * @version 1.0
 */
@Transactional
public interface KeyedCrudDao<E extends KeyedPojo> extends BaseCrudDao<E> {

	/**
	 * 按照实体类型和实体唯一标识查询实体。
	 * 
	 * @param key
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	E find(long id) throws DataAccessException;

	/**
	 * 按照实体类型和实体唯一标识查询实体。
	 * @param id
	 * @return
	 * @throws org.springframework.dao.DataAccessException
	 */
	@Transactional(readOnly = true)
	E find(Serializable id) throws DataAccessException;

	/**
	 * 通过给定复合主键的各个属性值来查找实体对象。
	 *
	 * @param keyNames
	 * @param keyValues
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	E find(String[] keyNames, Object[] keyValues) throws DataAccessException;

	/**
	 * 按照实体类型和实体唯一标识查询实体，并锁定该实体对象，直到事务结束。
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	E findAndLock(long id) throws DataAccessException;

	/**
	 * 按照实体类型和实体唯一标识查询实体，并锁定该实体对象，直到事务结束。
	 * @param id
	 * @return
	 * @throws org.springframework.dao.DataAccessException
	 */
	@Transactional(readOnly = true)
	E findAndLock(Serializable id) throws DataAccessException;

	/**
	 * 按照给定的实体类型和唯一标识查询实体。通用的查询方法，适用于所有的继承SingleKeyPojo的实体类。
	 * @param clazz
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	KeyedPojo find(Class clazz, long id) throws DataAccessException;

	/**
	 * 按照给定的实体类型和唯一标识查询实体。通用的查询方法，适用于所有的继承SingleKeyPojo的实体类。
	 * @param clazz
	 * @param id
	 * @return
	 * @throws org.springframework.dao.DataAccessException
	 */
	@Transactional(readOnly = true)
	KeyedPojo find(Class clazz, Serializable id) throws DataAccessException;

	// /**
	// * Find entity from DB by long type id.
	// * @param key
	// * @return
	// */
	// E find(Class<E> type, long id)throws DataAccessException;
	//	
	// /**
	// * Find entity from DB by Long type id.
	// * @param type
	// * @param id
	// * @return
	// */
	// E find(Class<E> type, Long id)throws DataAccessException;

	/**
	 * 删除实体主键id标识的实体。
	 *
	 * @param id
	 * @throws DataAccessException
	 */
	void delete(long id) throws DataAccessException;

	/**
	 * 通过复合主键类的实例来删除实体对象。
	 *
	 * @param key
	 * @throws DataAccessException
	 */
	void delete(Serializable key) throws DataAccessException;

	/**
	 * 通过给定复合主键的各个属性值来删除实体对象。
	 *
	 * @param keyNames 主键各个字段名
	 * @param keyValues 主键各个字段值
	 * @throws DataAccessException
	 */
	void delete(String[] keyNames, Object[] keyValues) throws DataAccessException;

}
