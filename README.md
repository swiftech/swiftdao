## This project has been deprecated because the Spring Data Jpa provides similar but more powerful functionalities.

SwiftDAO is a lightweight generic DAO layer based on Hibernate and Spring, implemented many basic CRUD operations.

SwiftDAO是一个轻量级的，基于Hibernate框架的泛型DAO层。如果您使用 Hibernate 和 Spring 框架做开发，想要快速为项目添加DAO层但又不想在这上面花太多时间，那么SwiftDAO实现了常见的CRUD操作，利用它可以省去很多简单重复代码的编写。
使用SwiftDAO必须对Hibernate和Spring框架相当熟悉并且理解数据库基本概念。


#### 支持：

###### v0.2.x
* JDK 1.7+
* Spring 4.3.x
* Hibernate 4.3.x

###### v0.1.2
* JDK 1.6+
* Hibernate 3.6.x
* Spring 3.2.x

#### 使用方法：

###### 1. 创建实体类：  
  实现 org.swiftdao.entity.KeyedPersistable 接口（接口泛型为主键类型），并使用 annotation 方式做映射。
  例如：  
```java
@Entity()
@Table(name = "USER")
public class User implements KeyedPersistable<Long>{

	@Id()
	@Column(name = "USER_ID")
	protected Long id;

	@Column(name = "USER_NAME", length = 32, nullable = false)
	protected String name;
	......
	// Getter and Setter
}
```

###### 2. 创建DAO接口以及接口的实现类：  
  DAO接口，继承 org.swiftdao.KeyedCrudDao 接口：  
  ```java
  public interface UserDao extends KeyedCrudDao<User> {

  }
  ```
  DAO接口实现类，继承org.swiftdao.impl.HibernateKeyedCrudDaoImpl类：
  ```java
  public class UserDaoImpl extends HibernateKeyedCrudDaoImpl<User> implements UserDao {

  }
  ```

###### 3. 这时你的DAO就已经拥有基本的CRUD功能了，可以直接使用了。如果有更加复杂的持久层的操作则可以在这个子DAO接口中实现。  
  以下为SwiftDAO已经实现的CRUD方法集：
  ```java
	// 持久化一个实体。
	void create(E entity) throws DataAccessException;

	// 持久化多个实体。
	void create(Collection<E> entities) throws DataAccessException;

	// 持久化或者更新实体。
	void createOrUpdate(E entity) throws DataAccessException;

	// 持久化或者更新多个实体。
	void createOrUpdate(Collection<E> entities) throws DataAccessException;

	// 更新实体。
	void update(E entity) throws DataAccessException;

	// 更新多个实体。
	void update(Collection<E> entities) throws DataAccessException;

	// 更新处于游离状态的实体，更新后实体对象仍然处于游离状态。
	void merge(E entity) throws DataAccessException;

	// 保存处于游离状态的多个实体，更新后实体对象仍然处于游离状态。
	void merge(Collection<E> entities) throws DataAccessException;

	// 删除一个持久化的实体。
	void delete(E entity) throws DataAccessException;

	// 删除多个持久化的实体。
	void delete(Collection<E> entities) throws DataAccessException;

	// 删除实体主键id标识的实体。
	void delete(long id) throws DataAccessException;

	// 通过复合主键类的实例来删除实体对象。
	void delete(Serializable key) throws DataAccessException;

	// 通过给定复合主键的各个属性值来删除实体对象。
	void delete(String[] keyNames, Object[] keyValues) throws DataAccessException;

	// 按照实体类型和实体唯一标识查询实体。
	E find(long id) throws DataAccessException;

	// 按照实体类型和实体唯一标识查询实体。
	E find(Serializable id) throws DataAccessException;

	// 通过给定复合主键的各个属性值来查找实体对象。
	E find(String[] keyNames, Object[] keyValues) throws DataAccessException;

	// 按照实体类型和实体唯一标识查询实体，并锁定该实体对象，直到事务结束。
	E findAndLock(long id) throws DataAccessException;

	// 按照实体类型和实体唯一标识查询实体，并锁定该实体对象，直到事务结束。
	E findAndLock(Serializable id) throws DataAccessException;

	// 按照给定的实体类型和唯一标识查询实体。通用的查询方法，适用于所有的实现KeyedPersistable接口的实体类。
	KeyedPersistable find(Class clazz, long id) throws DataAccessException;

	// 按照给定的实体类型和唯一标识查询实体。通用的查询方法，适用于所有的实现KeyedPersistable接口的实体类。
	KeyedPersistable find(Class clazz, Serializable id) throws DataAccessException;

	// 按照唯一的（Unique）属性名和属性值，查询得到一个实体对象。
	E findByUniqueParam(String uniqueParamName, String value) throws DataAccessException;

	// 按照指定的属性值查询多个实体对象。
	List<E> findByParam(String paramName, Object value) throws DataAccessException;

	// 按照指定属性值查找多个实例，并按照分页条件分页，返回指定页的实体集合。
	List<E> findByParamPagination(String paramName, Object value, int pageSize, int pageNumber) throws DataAccessException;

	// 按照指定属性值、排序条件和分页条件进行查找指定页的多个实例。
	List<E> findByParamPagination(String paramName, Object value, String orderParam,
			boolean isDescending, int pageSize, int pageNumber) throws DataAccessException;

	// 按照指定的属性值映射查找多个实体对象。
	List<E> findByParams(Map<String, Object> paramMap) throws DataAccessException;

	// 按照指定的条件表达式查找多个实体对象。
	List<E> findByParams(String extraCondition, Map<String, Object> extraParams) throws DataAccessException;

	// 按照指定的属性值映射查找多个实体对象。
	List<E> findByParams(Map<String, Object> paramMap, String extraCondition, Map<String, Object> extraParams) throws DataAccessException;

	// 按照指定属性值映射、分页条件查找多个实例。
	List<E> findByParamsPagination(Map<String, Object> paramMap, int pageSize, int pageNumber) throws DataAccessException;

	// 按照指定属性值映射查找多个实例，并按照分页条件分页，返回指定页的实体列表。
	List<E> findByParamsPagination(String condition, Map<String, Object> params, int pageSize, int pageNumber) throws DataAccessException;

	// 按照指定属性值映射查找多个实例，并按照分页条件分页，返回指定页的实体列表。
	List<E> findByParamsPagination(Map<String, Object> paramMap, String extraCondition,
			Map<String, Object> extraParams, int pageSize, int pageNumber) throws DataAccessException;

	// 按照指定属性值映射、排序条件和分页条件进行查找指定页的多个实例。
	List<E> findByParamsPagination(Map<String, Object> paramMap, String orderParam, boolean isDescending,
			int pageSize, int pageNumber) throws DataAccessException;

	// 按照指定参数值映射和额外的查询条件、排序条件和分页条件查找多个实例。
	List<E> findByParamsPagination(Map<String, Object> paramMap, String extraCondition,
			Map<String, Object> extraParams, String orderParam, boolean isDescending,
			int pageSize, int pageNumber) throws DataAccessException;

	// 按照泛型的实体类型查询得到所有持久化实体。
	// 如果实体类被设置为缓存的，则该方法首先从缓存中获取。
	List<E> findAll() throws DataAccessException;

	// 按照指定实体类型查询得到所有持久化实体。
	// 如果实体类被设置为缓存的，则该方法首先从缓存中获取。
	List<E> findAll(Class clazz) throws DataAccessException;

	// 忽略实体类的缓存配置，直接查询所有持久化实体。
	List<E> findAllOverCache(Class clazz) throws DataAccessException;

	// 在所有的持久化实体中查询指定页的实体集合。
	List<E> findAllByPagination(int pageSize, int pageNumber) throws DataAccessException;

	// 在所有的持久化实体中按照排序方式查询指定页的实体集合。
	List<E> findAllByPagination(String orderParam, boolean isDescending, int pageSize, int pageNumber) throws DataAccessException;

	// 统计所有持久化实体对象的数量。
	long countAll() throws DataAccessException;

	// 按条件统计持久化实体对象的数量。
	long countByParam(String paramName, Object value) throws DataAccessException;

	// 按给定的限制条件统计持久化实体对象的数量。
	long countByParams(Map<String, Object> paramMap) throws DataAccessException;

	// 按给定的限制条件统计实体对象的数量。
	long countByParams(Map<String, Object> paramMap, String extraCondition, Map<String, Object> extraParams) throws DataAccessException;

	// 获得指定Sequence的值(仅用于Oracle)
	long getSequence(String seqName) throws DataAccessException;
```


#### 更新记录
* 0.2.4
	* 修复了 find**Pagination() 方法查询一对多和多对多实体类的错误
