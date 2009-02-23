package org.swiftdao;

import org.swiftdao.pojo.Pojo;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 所有DAO接口的基础接口，提供了常用增删改查(CRUD)功能。<BR>
 * 实体状态说明：
 * 
 * <pre>
 *     持久化状态：已经被持久化了且与当前Session关联的实体状态。
 *     临时状态：没有被持久化过的实体状态。
 *     游离状态：已经被持久化，但是没有与当前Session关联的实体对象，且有相同标识的对象与当前Session关联。
 * </pre>
 * 
 * @author Wang Yuxing
 * @version 1.0
 */
@Transactional(isolation=Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public interface BaseCrudDao<E extends Pojo> extends BaseSpExecutableDao {

	/**
	 * 新建实体。
	 * 
	 * @param entity 处于临时状态的实体。
	 * @throws DataAccessException
	 */
	void create(E entity) throws DataAccessException;

	/**
	 * 新建多个实体。
	 * 
	 * @param entities 包含实体的集合，如等。
	 * @throws DataAccessException
	 */
	void create(Collection<E> entities) throws DataAccessException;

	/**
	 * 更新实体。
	 * 
	 * @param entity 处于持久化状态的实体。
	 * @throws DataAccessException
	 */
	void update(E entity) throws DataAccessException;

	/**
	 * 更新多个实体。
	 * 
	 * @param entities 处于持久化状态的实体。
	 * @throws DataAccessException
	 */
	void update(Collection<E> entities) throws DataAccessException;

	/**
	 * 新建或者更新实体。
	 * 
	 * @param entity 处于临时或者持久化状态的实体。
	 * @throws DataAccessException
	 */
	void createOrUpdate(E entity) throws DataAccessException;

	/**
	 * 新建或者更新多个实体。
	 * 
	 * @param entities 处于临时或者持久化状态的实体。
	 * @throws DataAccessException
	 */
	void createOrUpdate(Collection<E> entities) throws DataAccessException;

	/**
	 * 保存处于游离状态的实体，更新后实体对象仍然处于游离状态。
	 * 
	 * @param entity 处于游离状态的实体。
	 * @throws DataAccessException
	 */
	void merge(E entity) throws DataAccessException;

	/**
	 * 保存处于游离状态的多个实体，更新后实体对象仍然处于游离状态。
	 * 
	 * @param entities 处于游离状态的实体。
	 * @throws DataAccessException
	 */
	void merge(Collection<E> entities) throws DataAccessException;

	/**
	 * 删除DB中的实体。
	 * 
	 * @param entity 处于持久化状态的实体。
	 * @throws DataAccessException
	 */
	void delete(E entity) throws DataAccessException;

	/**
	 * 删除多个实体。
	 * 
	 * @param entities 处于持久化状态的实体。
	 * @throws DataAccessException
	 */
	void delete(Collection<E> entities) throws DataAccessException;

	/**
	 * 按照指定的类型，属性名和属性值，按照唯一约束查询得到实体对象。
	 * 
	 * @param paramName 实体唯一键属性名
	 * @param value 属性值
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	E findByUniqueParam(String uniqueParamName, String value) throws DataAccessException;

	/**
	 * 按照指定的属性值查找若干实体对象。
	 * 
	 * @param paramName 实体属性名。
	 * @param value 对应实体属性名的属性值。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParam(String paramName, Object value) throws DataAccessException;

	/**
	 * 按照指定属性值查找若干实例，并按照分页条件分页，返回指定页的实体列表。
	 * 
	 * @param paramName 作为查询条件的属性。
	 * @param value 查询条件属性值。
	 * @param pageSize 每页大小。
	 * @param pageNumber 查询的页码，0表示第一页。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParamPagination(String paramName, Object value, int pageSize, int pageNumber)
			throws DataAccessException;

	/**
	 * 按照指定属性值查找若干实例，并按照分页条件分页，返回指定页的实体列表。
	 * 
	 * @param paramName 作为查询条件的属性。
	 * @param value 查询条件属性值。
	 * @param pageSize 每页大小。
	 * @param pageNumber 查询的页码，0表示第一页。
	 * @param orderParam 排序的属性名，null为没有排序条件
	 * @param isDescending 是否是降序排序，当orderParam可用是才有效。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParamPagination(String paramName, Object value, int pageSize, int pageNumber, String orderParam,
			boolean isDescending) throws DataAccessException;

	/**
	 * 按照指定的属性值映射查找若干实体对象。
	 * 
	 * @param paramMap 实体类属性名和属性值的映射。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParams(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 按照指定的条件表达式查询
	 * @param extraCondition 包含形如:param的条件表达式
	 * @param extraParams 条件表达式中的参数值
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParams(String extraCondition, Map<String, Object> extraParams) throws DataAccessException;
	
	/**
	 * 按照指定的属性值映射查找若干实体对象。
	 * 
	 * @param paramMap 实体类属性名和属性值的映射。
	 * @param extraCondition 额外的查询条件，跟在paramMap的后面，类似“ OR XX = :xx”的形式，没有则为null。
	 * @param extraParams 配合extraCondition使用，用于保存extraCondition中的变量。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParams(Map<String, Object> paramMap, String extraCondition, Map<String, Object> extraParams) throws DataAccessException;

	
	/**
	 * 按照指定属性值映射查找若干实例，并按照分页条件分页，返回指定页的实体列表。
	 * @param paramMap 查询条件，
	 * @param pageSize 每页大小。
	 * @param pageNumber 查询的页码，0表示第一页。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParamsPagination(Map<String, Object> paramMap, int pageSize, int pageNumber)
			throws DataAccessException;

	/**
	 * 按照指定属性值映射查找若干实例，并按照分页条件分页，返回指定页的实体列表。
	 * @param condition 查询条件，类似“ WHERE AA = :aa OR XX = :xx”的形式，没有则为null。
	 * @param params 配合extraCondition使用，用于保存extraCondition中的变量。
	 * @param pageSize 每页大小。
	 * @param pageNumber 查询的页码，0表示第一页。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParamsPagination(String condition, Map<String, Object> params, int pageSize, int pageNumber)
			throws DataAccessException;

	/**
	 * 按照指定属性值映射查找若干实例，并按照分页条件分页，返回指定页的实体列表。
	 * 
	 * @param paramMap 查询条件，如果需要更灵活的条件，使用extraCondition。
	 * @param extraCondition 额外的查询条件，跟在paramMap的后面，类似“ OR XX = :xx”的形式，没有则为null。
	 * @param extraParams 配合extraCondition使用，用于保存extraCondition中的变量。
	 * @param pageSize 每页大小。
	 * @param pageNumber 查询的页码，0表示第一页。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParamsPagination(Map<String, Object> paramMap, String extraCondition,
			Map<String, Object> extraParams, int pageSize, int pageNumber) throws DataAccessException;

	/**
	 * 按照指定参数值映射查找若干实例，并按照分页条件分页，返回指定页的实体列表。
	 * 
	 * @param paramMap 查询条件。
	 * @param pageSize 每页大小。
	 * @param pageNumber 查询的页码，0表示第一页。
	 * @param orderParam 排序的属性名，null为没有排序条件
	 * @param isDescending 是否是降序排序，当orderParam可用是才有效。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParamsPagination(Map<String, Object> paramMap, int pageSize, int pageNumber, String orderParam,
			boolean isDescending) throws DataAccessException;
	
	/**
	 * 按照指定参数值映射和额外的查询条件查找若干实例，并按照分页条件分页，返回指定页的实体列表。
	 * @param paramMap 查询条件，跟在paramMap的后面，类似“ OR XX = :xx”的形式，没有则为null。
	 * @param extraCondition 额外查询条件。
	 * @param extraParams 配合extraCondition使用，用于保存extraCondition中的变量。
	 * @param pageSize 每页大小。
	 * @param pageNumber 查询的页码，0表示第一页。
	 * @param orderParam 排序的属性名，null为没有排序条件
	 * @param isDescending 是否是降序排序，当orderParam可用是才有效。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findByParamsPagination(Map<String, Object> paramMap, String extraCondition,
			Map<String, Object> extraParams, int pageSize, int pageNumber, String orderParam, boolean isDescending)
			throws DataAccessException;
	/**
	 * 按照默认实体类型查询得到所有数据实体。
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findAll() throws DataAccessException;
	
	/**
	 * 按照指定实体类型查询得到所有数据实体。
	 * 如果实体类被设置为缓存的，则该方法首先从缓存中获取。
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findAll(Class clazz) throws DataAccessException;
	
	/**
	 * 改方法忽略实体类的缓存配置，直接从DB中获取数据。
	 * @return
	 * @throws DataAccessException
	 */
	List<E> findAllOverCache(Class clazz) throws DataAccessException;


	/**
	 * 查询所有的实体对象，返回指定页码的实体集合。
	 * 
	 * @param pageSize
	 * @param PageNumber
	 * @return
	 */
	@Transactional(readOnly = true)
	List<E> findAllByPagination(int pageSize, int pageNumber) throws DataAccessException;

	/**
	 * 查询所有的实体对象并按指定的排序方式分页，返回指定页码的实体集合。
	 * 
	 * @param pageSize 每页大小。
	 * @param pageNumber 查询的页码，0表示第一页。
	 * @param orderParam 排序的属性名，null为没有排序条件
	 * @param isDescending 是否是降序排序，当orderParam可用是才有效。
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	List<E> findAllByPagination(int pageSize, int pageNumber, String orderParam, boolean isDescending)
			throws DataAccessException;

	/**
	 * 统计所有实体对象的数量。
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	long countAll() throws DataAccessException;

	/**
	 * 按条件统计实体对象的数量。
	 * 
	 * @param paramName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	long countByParam(String paramName, Object value) throws DataAccessException;

	/**
	 * 按多个条件统计实体对象的数量。
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	long countByParams(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 按多个条件统计实体对象的数量。
	 * @param paramMap
	 * @param extraCondition
	 * @param extraParams
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	long countByParams(Map<String, Object> paramMap, String extraCondition, Map<String, Object> extraParams) throws DataAccessException;

	/**
	 * 获得指定Sequence的值
	 * @param seqName
	 * @return
	 * @throws DataAccessException
	 */
	long getSequence(String seqName) throws DataAccessException;
}
