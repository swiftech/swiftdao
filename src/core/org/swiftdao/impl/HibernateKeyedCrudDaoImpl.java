package org.swiftdao.impl;

import org.swiftdao.KeyedCrudDao;
import org.swiftdao.pojo.KeyedPojo;
import java.lang.annotation.Annotation;

import org.hibernate.LockMode;
import org.hibernate.annotations.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * <code>KeyedCrudDao</code>的基础实现。
 * 
 * @author Wang Yuxing
 * @version 1.0
 */
public abstract class HibernateKeyedCrudDaoImpl<E extends KeyedPojo> extends HibernateCrudDaoImpl<E> implements
		KeyedCrudDao<E> {

	@SuppressWarnings("unchecked")
	public E find(long id) throws DataAccessException {
		return find(new Long(id));
	}

	@SuppressWarnings("unchecked")
	public E find(Serializable id) throws DataAccessException {
		Annotation a = getPojoClass().getAnnotation(Cache.class);
		if (a == null) {
			return (E) super.getHibernateTemplate().get(this.getPojoClass(), id);
		} else {
			E entity = null;
			try {
				entity = (E) super.getSession().load(this.getPojoClass(), id);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage() + e.getStackTrace());
				}
				return null;
			}
			try {
				entity.getId();
			} catch (Exception e) {
				return null;
			}
			return entity;
		}
	}

	@SuppressWarnings("unchecked")
	public E find(String[] keyNames, Object[] keyValues) throws DataAccessException {
		if (keyNames == null || keyValues == null || keyNames.length != keyValues.length) {
			return null; // 非法参数
		}
		DetachedCriteria dc = DetachedCriteria.forClass(this.getPojoClass(), "A");
		for (int i = 0; i < keyNames.length; i++) {
			dc.add(Restrictions.eq(keyNames[i], keyValues[i]));
		}
		List<E> list = this.getHibernateTemplate().findByCriteria(dc);
		if (list == null || list.size() == 0) {
			throw new DataRetrievalFailureException("没有找到满足复合主键条件的实体");
		}
		return list.get(0); // 应该只有一个实体对象。
	}

	@SuppressWarnings("unchecked")
	public E findAndLock(long id) throws DataAccessException {
		return findAndLock(new Long(id));
	}

	@SuppressWarnings("unchecked")
	public E findAndLock(Serializable key) throws DataAccessException {
		E pojo = (E) this.getHibernateTemplate().get(this.getPojoClass(), key, LockMode.UPGRADE_NOWAIT);
		return pojo;
	}

//	@SuppressWarnings("unchecked")
//	public E find(Long id) throws DataAccessException {
//		return this.find(id);
//	}

	@SuppressWarnings("unchecked")
	public KeyedPojo find(Class clazz, long id) throws DataAccessException {
		return find(clazz, new Long(id));
	}

	@SuppressWarnings("unchecked")
	public KeyedPojo find(Class clazz, Serializable id) throws DataAccessException {
		// 2008-09-15
		Annotation a = clazz.getAnnotation(Cache.class);
		if (a == null) {
			return (KeyedPojo) super.getHibernateTemplate().get(clazz, id);
		} else {
			KeyedPojo entity = null;
			try {
				entity = (KeyedPojo) super.getSession().load(clazz, id);
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage() + e.getStackTrace());
				}
				return null;
			}
			try {
				entity.getId();
			} catch (Exception e) {
				return null;
			}
			return entity;
		}
	}

	public void delete(long id) throws DataAccessException {
		E entity = this.find(id);
		if (entity == null) {
			throw new DataRetrievalFailureException("No entity found for deletion: " + id);
		}
		super.getHibernateTemplate().delete(entity);
	}

	public void delete(String[] keyNames, Object[] keyValues) throws DataAccessException {
		E pojo = find(keyNames, keyValues);
		this.getHibernateTemplate().delete(pojo);
	}

	public void delete(Serializable key) throws DataAccessException {
		E pojo = find(key);
		this.getHibernateTemplate().delete(pojo);
	}
}
