package org.swiftdao.impl;

import java.lang.annotation.Annotation;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.annotations.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.swiftdao.BaseCrudDao;
import org.swiftdao.util.BeanUtils;
import org.swiftdao.util.HibernateUtils;
import org.swiftdao.util.StringUtil;
import org.swiftdao.pojo.Pojo;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.swiftdao.exception.InvalidParameterException;

/**
 * 基于Hibernate的Crud DAO基础实现，所有使用Hibernate并支持Crud操作的DAO都继承该类。<BR>
 * 所有方法都必须声明为抛出Spring统一的DataAccessException异常，以便于做统一的异常处理。<BR>
 * 可用的异常类如下：
 * 
 * <pre>
 * DataAccessException
 *    CleanupFailureDataAccessException
 *    ConcurrencyFailureException
 *         OptimisticLockingFailureException
 *              ObjectOptimisticLockingFailureException
 *                   HibernateOptimisticLockingFailureException
 *         PessimisticLockingFailureException
 *              CannotAcquireLockException
 *              CannotSerializeTransactionException
 *              DeadlockLoserDataAccessException
 *    DataAccessResourceFailureException
 *         CannotCreateRecordException
 *         CannotGetCciConnectionException
 *         CannotGetJdbcConnectionException
 *    DataIntegrityViolationException
 *    DataRetrievalFailureException
 *         IncorrectResultSetColumnCountException
 *         IncorrectResultSizeDataAccessException
 *              EmptyResultDataAccessException
 *         LobRetrievalFailureException
 *         ObjectRetrievalFailureException
 *              HibernateObjectRetrievalFailureException
 *    DataSourceLookupFailureException
 *    InvalidDataAccessApiUsageException
 *    InvalidDataAccessResourceUsageException
 *         BadSqlGrammarException
 *         CciOperationNotSupportedException
 *         HibernateQueryException
 *         IncorrectUpdateSemanticsDataAccessException
 *              JdbcUpdateAffectedIncorrectNumberOfRowsException
 *         InvalidResultSetAccessException
 *         InvalidResultSetAccessException
 *         RecordTypeNotSupportedException
 *         TypeMismatchDataAccessException
 *    PermissionDeniedDataAccessException
 *    UncategorizedDataAccessException
 *         HibernateJdbcException
 *         HibernateSystemException
 *         SQLWarningException
 *         UncategorizedSQLException
 * </pre>
 * 
 * @author Wang Yuxing
 * @version 1.0
 * @param <E>
 */
public abstract class HibernateCrudDaoImpl<E extends Pojo> extends HibernateDaoSupport implements BaseCrudDao<E> {

	protected final Logger logger = LogManager.getLogger(HibernateCrudDaoImpl.class);
	protected int spExecutionResult = 1;
	protected Class pojoClass;

	public HibernateCrudDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			pojoClass = (Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}
	}

	/**
	 * 获得DAO类对应的实体类型，所有继承者必须实现该方法并给出相应的实体类型。
	 */
	protected Class<? extends Pojo> getPojoClass() {
		return pojoClass;
	//return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void create(E entity) throws DataAccessException {
		super.getHibernateTemplate().save(entity);
	}

	public void create(Collection<E> entities) throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<E> it = entities.iterator();
			for (E e = null; it.hasNext();) {
				e = it.next();
				create(e);
			}
		}
	}

	public void delete(E entity) throws DataAccessException {
		super.getHibernateTemplate().delete(entity);
	}

	public void delete(Collection<E> entities) throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<E> it = entities.iterator();
			for (E e = null; it.hasNext();) {
				e = it.next();
				delete(e);
			}
		}
	}

	public void update(E entity) throws DataAccessException {
		super.getHibernateTemplate().update(entity);
	}

	public void update(Collection<E> entities) throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<E> it = entities.iterator();
			for (E e = null; it.hasNext();) {
				e = it.next();
				update(e);
			}
		}
	}

	public void createOrUpdate(E entity) throws DataAccessException {
		super.getHibernateTemplate().saveOrUpdate(entity);
	}

	public void createOrUpdate(Collection<E> entities) throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<E> it = entities.iterator();
			for (E e = null; it.hasNext();) {
				e = it.next();
				createOrUpdate(e);
			}
		}
	}

	public void merge(E entity) throws DataAccessException {
		super.getHibernateTemplate().merge(entity);
	}

	public void merge(Collection<E> entities) throws DataAccessException {
		if (entities != null && entities.size() > 0) {
			Iterator<E> it = entities.iterator();
			for (E e = null; it.hasNext();) {
				e = it.next();
				merge(e);
			}
		}
	}

	public E findByUniqueParam(String uniqueParamName, String value) throws DataAccessException {
		List<E> result = this.findByParam(uniqueParamName, value);
		if (result == null || result.size() == 0) {
			return null;
		}
		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() throws DataAccessException {
		return this.findAll(this.getPojoClass());
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll(final Class clazz) throws DataAccessException {
		List<E> entities = new ArrayList<E>();
		entities = (List<E>) super.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Annotation a = clazz.getAnnotation(Cache.class);
				if (a == null) {
					return session.createCriteria(clazz).list();
				} else {
					List l = session.createCriteria(clazz).setCacheable(true).list();
					if (l == null || l.size() == 0) {
						l = session.createCriteria(clazz).list();
					}
					return l;
				}
			}
		});
		return entities;
	}

	@Override
	public List<E> findAllOverCache(final Class clazz) throws DataAccessException {
		return this.getSession().createCriteria(clazz).list();
	}

	@SuppressWarnings("unchecked")
	public List<E> findByParam(String paramName, Object value) throws DataAccessException {
		if (StringUtils.isEmpty(paramName) || value == null) {
			return null;
		}
		StrBuilder buf = new StrBuilder();
		buf.append("FROM ").append(this.getPojoClass().getSimpleName()).append(" WHERE ").append(paramName).append(" = :condition");
		List<E> entities = this.getHibernateTemplate().findByNamedParam(buf.toString(), "condition", value);
		return entities;
	}

	public List<E> findByParamPagination(String paramName, Object value, 
			int pageSize, int pageNumber) throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(paramName, value);
		return findByParamsPagination(paramMap, null, null, pageSize, pageNumber);
	}

	public List<E> findByParamPagination(String paramName, Object value,
			String orderParam, boolean isDescending,
			int pageSize, int pageNumber) throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(paramName, value);
		return findByParamsPagination(paramMap, orderParam, isDescending, pageSize, pageNumber);
	}

	public List<E> findByParams(Map<String, Object> paramMap) {
		return findByParams(paramMap, null, null);
	}

	public List<E> findByParams(String extraCondition, Map<String, Object> extraParams) throws DataAccessException {
		return this.findByParams(null, extraCondition, extraParams);
	}

	@SuppressWarnings("unchecked")
	public List<E> findByParams(Map<String, Object> paramMap, 
			String extraCondition, Map<String, Object> extraParams) throws DataAccessException {
		if ((paramMap == null || paramMap.size() == 0) && (extraCondition == null || extraParams == null || extraParams.size() == 0)) {
			return this.findAll();
		}
		StringBuilder hqlSb = new StringBuilder("FROM ");
		hqlSb.append(this.getPojoClass().getSimpleName());
		hqlSb.append(" WHERE ");
		if (paramMap != null && paramMap.size() > 0) {
			Iterator<String> mapKeys = paramMap.keySet().iterator();
			for (int i = 0; mapKeys.hasNext(); i++) {
				if (i != 0) {
					hqlSb.append(" AND ");
				}
				String paramName = mapKeys.next();
				hqlSb.append(paramName + " =:" + paramName);
			}
		}
		if (extraCondition != null && extraCondition.length() > 0) {
			hqlSb.append(" ").append(extraCondition);
		}

		String hql = hqlSb.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("Query by : " + hql);
		}
		// 设置参数名与参数值
		int paramsSize = ((paramMap == null) ? 0 : paramMap.size()) + ((extraParams == null) ? 0 : extraParams.size());
		if (logger.isDebugEnabled()) {
			logger.debug("Query by : " + hql + " with " + paramsSize + " parameters");
		}
		String[] queryParamNames = new String[paramsSize];
		Object[] queryParams = new Object[paramsSize];
		int i = 0;
		if (paramMap != null && paramMap.size() > 0) {
			Iterator<String> mapKeys = paramMap.keySet().iterator();
			for (; mapKeys.hasNext(); i++) {
				Object curKey = mapKeys.next();
				queryParamNames[i] = curKey.toString();
				queryParams[i] = paramMap.get(curKey);
			}
		}
		if (extraCondition != null && extraParams != null && extraParams.size() > 0) {
			Iterator<String> mapKeys = extraParams.keySet().iterator();
			for (; mapKeys.hasNext(); i++) {
				Object curKey = mapKeys.next();
				queryParamNames[i] = curKey.toString();
				queryParams[i] = extraParams.get(curKey);
			}
		}
		List<E> entities = this.getHibernateTemplate().findByNamedParam(hql, queryParamNames, queryParams);
		return entities;
	}

	public List<E> findByParamsPagination(Map<String, Object> paramMap, 
			int pageSize, int pageNumber) throws DataAccessException {
		return this.findByParamsPagination(paramMap, null, null, pageSize, pageNumber);
	}

	public List<E> findByParamsPagination(String condition, Map<String, Object> params, 
			int pageSize, int pageNumber) throws DataAccessException {
		return this.findByParamsPagination(null, condition, params, pageSize, pageNumber);
	}

	@SuppressWarnings("unchecked")
	public List<E> findByParamsPagination(Map<String, Object> paramMap, 
			String extraCondition, Map<String, Object> extraParams,
			int pageSize, int pageNumber) throws DataAccessException {
		StringBuilder hqlSb = new StringBuilder(" FROM ");
		hqlSb.append(this.getPojoClass().getSimpleName());// 实体名称
		if (paramMap != null && paramMap.size() > 0) {
			// 增加查询条件
			hqlSb.append(" WHERE ");
			Iterator<String> keys = paramMap.keySet().iterator();
			for (int i = 0; keys.hasNext(); i++) {
				if (i != 0) {
					hqlSb.append(" AND ");
				}
				String paramName = keys.next();
				hqlSb.append(paramName).append(" =:").append(paramName);
			}
		}
		// 增加额外的查询条件
		if (extraCondition != null && extraCondition.length() > 0) {
			hqlSb.append(extraCondition);
		}
		// 开始查询
		Query q = this.getSession().createQuery(hqlSb.toString());
		q.setMaxResults(pageSize);
		q.setFirstResult(pageNumber * pageSize);
		if (paramMap != null && paramMap.size() > 0) {
			// 设值
			Iterator<String> keys = paramMap.keySet().iterator();
			for (int i = 0; keys.hasNext(); i++) {
				String key = keys.next();
				q.setParameter(key, paramMap.get(key));
			}
		}
		if (extraCondition != null && extraParams != null && extraParams.size() > 0) {
			// 设值
			Iterator<String> keys = extraParams.keySet().iterator();
			for (int i = 0; keys.hasNext(); i++) {
				String key = keys.next();
				q.setParameter(key, extraParams.get(key));
			}
		}
		return q.list();
	}

	public List<E> findByParamsPagination(Map<String, Object> paramMap,
			String orderParam, boolean isDescending,
			int pageSize, int pageNumber) throws DataAccessException {
		return this.findByParamsPagination(paramMap, null, null, orderParam, isDescending, pageSize, pageNumber);
	}

	@SuppressWarnings("unchecked")
	public List<E> findByParamsPagination(Map<String, Object> paramMap,
			String extraCondition, Map<String, Object> extraParams,
			String orderParam, boolean isDescending,
			int pageSize, int pageNumber) throws DataAccessException {
		String[] attributeNames = HibernateUtils.getEntityAttributes(this.getSessionFactory(), this.getPojoClass());
		StringBuilder hqlSb = new StringBuilder(" SELECT ");
		StringUtil.mergeString(attributeNames, ",", hqlSb);
		hqlSb.append(" FROM ");
		hqlSb.append(this.getPojoClass().getSimpleName());// 实体名称
		if (paramMap != null && paramMap.size() > 0) {
			// 增加查询条件
			hqlSb.append(" WHERE ");
			Iterator<String> keys = paramMap.keySet().iterator();
			for (int i = 0; keys.hasNext(); i++) {
				if (i != 0) {
					hqlSb.append(" AND ");
				}
				String paramName = keys.next();
				hqlSb.append(paramName).append(" =:").append(paramName);
			}
		}
		// 增加额外的查询条件
		if (extraCondition != null && extraCondition.length() > 0) {
			hqlSb.append(extraCondition);
		}
		if (orderParam != null) {// 排序
			hqlSb.append(" ORDER BY ").append(orderParam);
			if (isDescending) {
				hqlSb.append(" DESC"); // 降序
			} else {
				hqlSb.append(" ASC"); // 升序
			}
		}
		Query q = this.getSession().createQuery(hqlSb.toString());
		if (paramMap != null && paramMap.size() > 0) {
			// 查询条件设值
			Iterator<String> keys = paramMap.keySet().iterator();
			for (int i = 0; keys.hasNext(); i++) {
				String key = keys.next();
				q.setParameter(key, paramMap.get(key));
			}
		}
		if (extraCondition != null && extraParams != null && extraParams.size() > 0) {
			// 设值
			Iterator<String> keys = extraParams.keySet().iterator();
			for (int i = 0; keys.hasNext(); i++) {
				String key = keys.next();
				q.setParameter(key, extraParams.get(key));
			}
		}
		List<E> pagedList = new ArrayList<E>();
		ScrollableResults entities = q.scroll();
		// 按照分页条件取一页的实体返回
		if (entities.first() == true) {
			entities.scroll(pageSize * pageNumber);
			for (int i = 0; i < pageSize; i++, entities.scroll(1)) {
				Object[] row = entities.get();
				if (row == null) {
					break;
				}
				E e = null;
				try {
					e = (E) this.getPojoClass().getConstructor().newInstance();
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new ObjectRetrievalFailureException("初始化实体类" + this.getPojoClass().getName() + "失败", e1);
				}
				try {
					for (int j = 0; j < row.length; j++) {
						BeanUtils.forceSetProperty(e, attributeNames[j], row[j]);
					}
				} catch (NoSuchFieldException e1) {
					logger.info("set attributes error", e1);
				}
				pagedList.add(e);
			}
		}
		return pagedList;
	}

	public List<E> findAllByPagination(int pageSize, int PageNumber) throws DataAccessException {
		return findByParamPagination("", "", pageSize, PageNumber);
	}

	@SuppressWarnings("unchecked")
	public List<E> findAllByPagination(String orderParam, boolean isDescending, 
			int pageSize, int pageNumber) throws DataAccessException {
		return this.findByParamsPagination(null, orderParam, isDescending, pageSize, pageNumber);
	}

	public long countAll() throws DataAccessException {
		return this.countByParams(null);
	}

	public long countByParam(String paramName, Object value) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(paramName, value);
		return this.countByParams(params);
	}

	public long countByParams(Map<String, Object> paramMap) throws DataAccessException {
		return this.countByParams(paramMap, null, null);
	}

	@SuppressWarnings("unchecked")
	public long countByParams(Map<String, Object> paramMap, 
			String extraCondition, Map<String, Object> extraParams) throws DataAccessException {
		String hql = "SELECT COUNT(*) FROM ";
		Iterator<Long> it = null;
		if ((paramMap == null || paramMap.size() == 0) && extraCondition == null && (extraParams == null || extraParams.size() == 0)) {
			hql = hql + this.getPojoClass().getSimpleName();
			it = this.getSession().createQuery(hql).list().iterator();
		} else {
			StringBuilder hqlSb = new StringBuilder(hql);
			hqlSb.append(this.getPojoClass().getSimpleName());
			hqlSb.append(" WHERE ");
			Iterator<String> mapKeys = null;
			if (paramMap != null && paramMap.size() > 0) {
				mapKeys = paramMap.keySet().iterator();
				for (int i = 0; mapKeys.hasNext(); i++) {
					if (i != 0) {
						hqlSb.append(" AND ");
					}
					String paramName = mapKeys.next();
					hqlSb.append(paramName + " =:" + paramName);
				}
			}
			if (extraCondition != null && extraCondition.length() > 0) {
				hqlSb.append(" ").append(extraCondition);
			}
			hql = hqlSb.toString();
			logger.debug("Query by : " + hql);
			// 设置参数名与参数值
			String[] queryParamNames = null;
			Object[] queryParams = null;
			int paramsSize = ((paramMap == null) ? 0 : paramMap.size()) + ((extraParams == null) ? 0 : extraParams.size());
			queryParamNames = new String[paramsSize];
			queryParams = new Object[paramsSize];
			int i = 0;
			if (paramMap != null && paramMap.size() > 0) {
				mapKeys = paramMap.keySet().iterator();
				for (; mapKeys.hasNext(); i++) {
					Object curKey = mapKeys.next();
					queryParamNames[i] = curKey.toString();
					queryParams[i] = paramMap.get(curKey);
				}
			}
			// Extra conditions
			if (extraCondition != null && extraParams != null && extraParams.size() > 0) {
				mapKeys = extraParams.keySet().iterator();
				for (; mapKeys.hasNext(); i++) {
					Object curKey = mapKeys.next();
					queryParamNames[i] = curKey.toString();
					queryParams[i] = extraParams.get(curKey);
				}
			}
			// for (int j = 0; j < paramsSize; j++) {
			// logger.debug(" " + queryParamNames[j]);
			// logger.debug(" " + queryParams[j]);
			// }
			it = this.getHibernateTemplate().findByNamedParam(hql, queryParamNames, queryParams).iterator();
		// } else {
		// hql = hql + this.getPojoClass().getSimpleName();
		// it = this.getSession().createQuery(hql).list().iterator();
		}
		if (it.hasNext()) {
			return Long.parseLong(it.next().toString());
		}
		return 0;
	}

	/**
	 * TODO
	 * 
	 * @param tableName
	 * @throws DataAccessException
	 */
	public void truncateTable(String tableName) throws DataAccessException {
		logger.debug("Truncate table " + tableName);
		String sql = "TRUNCATE TABLE " + tableName;
		int result = this.executeUpdateSqlStatement(sql);
		logger.debug(" with " + result + " records.");
	}

	/*
	 * TODO
	 */
	@SuppressWarnings("deprecation")
	protected int executeUpdateSqlStatement(String sql) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		try {
			PreparedStatement ps = session.connection().prepareStatement(sql);
			int result = ps.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * TODO
	 */
	@SuppressWarnings("deprecation")
	protected Object executeQuerySqlStatement(String sql, int i) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		try {
			PreparedStatement ps = session.connection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Object result = null;
			if (rs.next()) {
				result = rs.getObject(i);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 组成存储过程调用语句。
	 * 
	 * @param spName
	 * @param parameters
	 * @return
	 */
	protected String concatSpSql(String spName, Map<String, Object> parameters) {
		StringBuilder sb = new StringBuilder();
		sb.append("{call ").append(spName).append(" (");
		if (parameters != null) {
			Iterator it = parameters.entrySet().iterator();
			for (int i = 0; i < parameters.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")}");
		}
		return sb.toString();
	}

	public void execute(String spName, Map<String, Object> parameters) throws DataAccessException {
		String sql = concatSpSql(spName, parameters);
		try {
			CallableStatement cstmt = this.getSession().connection().prepareCall(sql);
			cstmt.execute();
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	public void execute(String spName) throws DataAccessException {
		String sql = "{call " + spName + " ()}";
		try {
			CallableStatement cstmt = this.getSession().connection().prepareCall(sql);
			cstmt.execute();
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
	}

	public Map<String, Object> executeWithResult(String spName) throws DataAccessException {
		return executeWithResult(spName, null);
	}

	public List executeWithResultset(String spName, Map<String, Object> parameters) throws DataAccessException {
		throw new RuntimeException("Not implemented yet");
	}

	public List executeWithResultset(String spName) throws DataAccessException {
		throw new RuntimeException("Not implemented yet");
	}

	// TODO Do not invoke until Oracle fix the problem.
	public Map<String, Object> executeWithResult(String spName, Map<String, Object> parameters)
			throws DataAccessException {
		String sql = concatSpSql(spName, parameters);
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			CallableStatement cs = this.getSession().connection().prepareCall(sql);
			ParameterMetaData pmd = cs.getParameterMetaData();
			int pcount = pmd.getParameterCount();
			for (int i = 0; i < pcount; i++) {
				// getParameterMode() method is not supported by recent Oracle JDBC driver.
				int mode = pmd.getParameterMode(i + 1);
				int type = pmd.getParameterType(i + 1);
				if (mode == ParameterMetaData.parameterModeOut || mode == ParameterMetaData.parameterModeInOut) {
					cs.registerOutParameter(i + 1, type);
				}
			}
			if (parameters != null) {
				Iterator it = parameters.entrySet().iterator();
				for (int i = 1; it.hasNext(); i++) {
					Object key = it.next();
					Object value = parameters.get(key);
					cs.setObject(key.toString(), value, JdbcHelper.translateType(value));
				}
			}
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				int count = rs.getMetaData().getColumnCount();
				for (int i = 0; i < count; i++) {
					String name = rs.getMetaData().getColumnName(i + 1);
					Object o = rs.getObject(i + 1);
					ret.put(name, o);
				}
			}
		} catch (Throwable e) {
			throw new DataAccessResourceFailureException("执行存储过程" + spName + "失败", e);
		}
		return ret;
	}

	public Map<String, Object> executeWithResult(Connection conn, String spName, Map<String, Object> parameters,
			Map<String, Integer> outParams, String cusorName) throws DataAccessException {
		throw new RuntimeException("Not Implemented Yet");
	}

	//TODO 需要从JdbcDaoImpl移植过来
	public Map<String, Object> executeWithResult(String spName, Map<String, Object> parameters,
			Map<String, Integer> outParams, String cursorName) throws DataAccessException {
		if (spName == null) {
			throw new InvalidParameterException("Invalid stored procedure name");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Call SP: " + spName);
			logger.debug("executeWithResult(...) - parameters=" + parameters); //$NON-NLS-1$
		}

		CallableStatement cmt = null;
		Connection conn = this.getSession().connection();
		int pCount = ((parameters == null) ? 0 : parameters.size()) + ((outParams == null) ? 0 : outParams.size());
		StringBuilder buf = new StringBuilder(40);

		buf.append("{ call ").append(spName).append("(");
		for (int i = 0; i < pCount; i++) {
			if (i != 0) {
				buf.append(",");
			}
			buf.append("?");
		}
		buf.append(") }");

		String sql = buf.toString();
		logger.debug("执行：" + sql);
		Map<String, Object> ret = new TreeMap<String, Object>();
		try {
			cmt = conn.prepareCall(sql);
			// 输入参数
			if (parameters != null && parameters.size() > 0) {
				Set<String> keys = parameters.keySet();
				for (String key : keys) {
					Object value = parameters.get(key);
					cmt.setObject(key, value, JdbcHelper.translateType(value));
				}
			}
			// 注册输出参数
			if (outParams != null && outParams.size() > 0) {
				Set<String> keys = outParams.keySet();
				for (String key : keys) {
					Integer value = outParams.get(key);
					// logger.debug("Register Out Parameter: " + key + " " + value);
					cmt.registerOutParameter(key, value.intValue());
				}
			}
			try {
				cmt.execute();
			} catch (RuntimeException e) {
				logger.error(e.getMessage() + e.getStackTrace());
				return null;
			}
			int resultCode = cmt.getInt("piResult");
			if (resultCode != 1) {
				ret.put("piResult", resultCode);
				if (outParams.get("psErrDesc") != null) {
					ret.put("psErrDesc", cmt.getString("psErrDesc"));
				}
				return ret;
			}
			if (outParams != null && outParams.size() > 0) {
				Set<String> keys = outParams.keySet();
				for (String key : keys) {
					// logger.debug("Out param key:" + key);
					if (key.equals(cursorName)) {
						continue;
					}
					Object value = cmt.getObject(key);
					if (value != null) {
						// logger.debug("Type of value: " + value.getClass());
						ret.put(key, value);
					}
				}
			}
			// 打开游标并将游标返回的数据集读入List<Map>中
			if (cursorName != null) {
				ResultSet rs;
				try {
					rs = (ResultSet) cmt.getObject(cursorName);
				} catch (SQLException e) {
					logger.warn("存储过程没有游标可以打开");
					return ret;
				}
				if (rs != null) {
					List<Map> data = new ArrayList<Map>();
					while (rs.next()) {
						Map<String, Object> row = new HashMap<String, Object>();
						int n = rs.getMetaData().getColumnCount();
						for (int i = 0; i < n; i++) {
							row.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1));
						}
						data.add(row);
					}
					ret.put(cursorName, data);
				}
			}
		} catch (Throwable e) {
			throw new DataRetrievalFailureException("ERROR", e);
		} finally {
			try {
				if (cmt != null) {
					cmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();//关闭Statement异常
			}
		}
		return ret;
	}

//	public void commit() throws DataAccessException {
//		this.getSession().getTransaction().begin();
//		System.out.print("->>>>" + this.getSession().getTransaction().isActive());
//	}

	@SuppressWarnings("deprecation")
	@Override
	public long getSequence(String seqName) throws DataAccessException {
		StringBuilder buf = new StringBuilder();
		buf.append("select ").append(seqName).append(".nextval from dual");
		Connection con = this.getSession().connection();
		try {
			PreparedStatement ps = con.prepareStatement(buf.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new DataRetrievalFailureException("取序列号失败", e);
		}
		throw new DataRetrievalFailureException("取序列号失败");
	}


	//TODO
	public void clearInCache() throws DataAccessException {
		this.getSessionFactory().evict(pojoClass);
		this.getSessionFactory().evictQueries();
	}

	//TODO
	public void showEntriesInCache(String regionName) throws DataAccessException {
		Map cacheEntries = null;
		try {
			cacheEntries = this.getSessionFactory().getStatistics().getSecondLevelCacheStatistics(regionName).getEntries();
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
			System.out.println(entity);
		}

	}
}
