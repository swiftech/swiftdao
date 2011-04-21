package org.swiftdao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.hibernate.LockMode;
import org.hibernate.annotations.Cache;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.swiftdao.KeyedCrudDao;
import org.swiftdao.entity.KeyedPersistable;
import org.swiftdao.exception.SwiftDaoException;

/**
 * {@link KeyedCrudDao}的Hibernate实现。
 * 
 * @param <E>
 * @author Wang Yuxing
 * @version 1.0
 */
public class HibernateKeyedCrudDaoImpl<E extends KeyedPersistable> extends HibernateCrudDaoImpl<E> implements
		KeyedCrudDao<E> {

	@SuppressWarnings("unchecked")
	public E find(long id) throws SwiftDaoException {
		return find(new Long(id));
	}

	@SuppressWarnings("unchecked")
	public E find(Serializable id) throws SwiftDaoException {
		Annotation cacheConfiged = getEntityClass().getAnnotation(Cache.class);
		if (cacheConfiged == null) {
			return (E) super.getHibernateTemplate().get(this.getEntityClass(), id);
		} else {
			E entity = null;
			try {
				entity = (E) super.getSession().load(this.getEntityClass(), id);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug(e.getMessage() + e.getStackTrace());
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
	public E find(String[] keyNames, Object[] keyValues) throws SwiftDaoException {
		if (keyNames == null || keyValues == null || keyNames.length != keyValues.length) {
			return null; // 非法参数
		}
		DetachedCriteria dc = DetachedCriteria.forClass(this.getEntityClass(), "A");
		for (int i = 0; i < keyNames.length; i++) {
			dc.add(Restrictions.eq(keyNames[i], keyValues[i]));
		}
		List<E> list = this.getHibernateTemplate().findByCriteria(dc);
		if (list == null || list.isEmpty()) {
			throw new EntityNotFoundException("没有找到满足复合主键条件的实体");
		}
		return list.get(0); // 应该只有一个实体对象。
	}

	@SuppressWarnings("unchecked")
	public E findAndLock(long id) throws SwiftDaoException {
		return findAndLock(new Long(id));
	}

	@SuppressWarnings("unchecked")
	public E findAndLock(Serializable key) throws SwiftDaoException {
		E entity = (E) this.getHibernateTemplate().get(this.getEntityClass(), key, LockMode.UPGRADE_NOWAIT);
		return entity;
	}

//	@SuppressWarnings("unchecked")
//	public E find(Long id) throws SwiftDaoException {
//		return this.find(id);
//	}

	@SuppressWarnings("unchecked")
	public E find(Class clazz, long id) throws SwiftDaoException {
		return find(clazz, new Long(id));
	}

	@SuppressWarnings("unchecked")
	public E find(Class clazz, Serializable id) throws SwiftDaoException {
		// 2008-09-15
		Annotation a = clazz.getAnnotation(Cache.class);
		if (a == null) {
			return (E) super.getHibernateTemplate().get(clazz, id);
		} else {
			E entity = null;
			try {
				entity = (E) super.getSession().load(clazz, id);
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug(e.getMessage() + e.getStackTrace());
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

	public void delete(long id) throws SwiftDaoException {
		E entity = this.find(id);
		if (entity == null) {
			throw new EntityNotFoundException("No entity found for deletion: " + id);
		}
		super.getHibernateTemplate().delete(entity);
	}

	public void delete(String[] keyNames, Object[] keyValues) throws SwiftDaoException {
		E entity = find(keyNames, keyValues);
		this.getHibernateTemplate().delete(entity);
	}

	public void delete(Serializable key) throws SwiftDaoException {
		E entity = find(key);
		this.getHibernateTemplate().delete(entity);
	}
}
