/*
 * Copyright (C) 2011 The ExtremeTalk Project. All rights reserved.
 * Original Author: Yuxing Wang(awsksupport@gmail.com)
 * 
 * This file is part of ExtremeTalk.
 * 
 * ExtremeTalk is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ExtremeTalk is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ExtremeTalk.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.swiftdao.impl.dummy;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.swiftdao.KeyedCrudDao;
import org.swiftdao.pojo.KeyedPojo;

/**
 * Dummy keyed CRUD DAO implementation, used for unit test.
 * To get better test functionality and performance, use HSQL DB instead.
 * TODO not implemented all methods for now.
 * @param <E>
 * @author Yuxing Wang
 * @since 
 */
public class DummyKeyedCrudDaoImpl<E extends KeyedPojo> implements KeyedCrudDao<E> {

	protected Map<Object, E> table = new MultiValueMap();

	public E find(long id) throws DataAccessException {
		return table.get(Long.valueOf(id));
	}

	public E find(Serializable id) throws DataAccessException {
		return table.get(id);
	}

	public E find(String[] keyNames, Object[] keyValues) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public E findAndLock(long id) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public E findAndLock(Serializable id) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public E find(Class clazz, long id) throws DataAccessException {
		return find(clazz, Long.valueOf(id));
	}

	public E find(Class clazz, Serializable id) throws DataAccessException {
		Iterator it = findAll().iterator();
		while (it.hasNext()) {
			E pojo = (E) it.next();
			if (pojo.getClass().equals(clazz) && pojo.getId().equals(id)) {
				return pojo;
			}
		}
		return null;
	}

	public void delete(long id) throws DataAccessException {
		table.remove(Long.valueOf(id));
	}

	public void delete(Serializable key) throws DataAccessException {
		table.remove(key);
	}

	public void delete(String[] keyNames, Object[] keyValues) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void create(E entity) throws DataAccessException {
		if (table.containsKey(entity.getId())) {
			throw new DuplicateKeyException("Entity " + entity.toString() + " is already existed");
		}
		table.put(entity.getId(), entity);
	}

	public void create(Collection<E> entities) throws DataAccessException {
		for (E pojo : entities) {
			create(pojo);
		}
	}

	public void update(E entity) throws DataAccessException {
		table.put(entity.getId(), entity);
	}

	public void update(Collection<E> entities) throws DataAccessException {
		for (E pojo : entities) {
			update(pojo);
		}
	}

	public void createOrUpdate(E entity) throws DataAccessException {
		table.put(entity.getId(), entity);
	}

	public void createOrUpdate(Collection<E> entities) throws DataAccessException {
		for (E pojo : entities) {
			createOrUpdate(pojo);
		}
	}

	public void merge(E entity) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void merge(Collection<E> entities) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void delete(E entity) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void delete(Collection<E> entities) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public E findByUniqueParam(String uniqueParamName, String value) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParam(String paramName, Object value) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParamPagination(String paramName, Object value, int pageSize, int pageNumber) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParamPagination(String paramName, Object value, String orderParam, boolean isDescending, int pageSize, int pageNumber) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParams(Map<String, Object> paramMap) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParams(String extraCondition, Map<String, Object> extraParams) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParams(Map<String, Object> paramMap, String extraCondition, Map<String, Object> extraParams) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParamsPagination(Map<String, Object> paramMap, int pageSize, int pageNumber) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParamsPagination(String condition, Map<String, Object> params, int pageSize, int pageNumber) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParamsPagination(Map<String, Object> paramMap, String extraCondition, Map<String, Object> extraParams, int pageSize, int pageNumber) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParamsPagination(Map<String, Object> paramMap, String orderParam, boolean isDescending, int pageSize, int pageNumber) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findByParamsPagination(Map<String, Object> paramMap, String extraCondition, Map<String, Object> extraParams, String orderParam, boolean isDescending, int pageSize, int pageNumber) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findAll() throws DataAccessException {
		return new ArrayList(table.values());
	}

	public List<E> findAll(Class clazz) throws DataAccessException {
		List<E> ret = new ArrayList<E>();
		Iterator it = findAll().iterator();
		while (it.hasNext()) {
			E pojo = (E) it.next();
			if (pojo.getClass().equals(clazz)) {
				ret.add(pojo);
			}
		}
		return ret;
	}

	public List<E> findAllOverCache(Class clazz) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findAllByPagination(int pageSize, int pageNumber) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<E> findAllByPagination(String orderParam, boolean isDescending, int pageSize, int pageNumber) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public long countAll() throws DataAccessException {
		return table.size();
	}

	public long countByParam(String paramName, Object value) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public long countByParams(Map<String, Object> paramMap) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public long countByParams(Map<String, Object> paramMap, String extraCondition, Map<String, Object> extraParams) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public long getSequence(String seqName) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void execute(String spName) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void execute(String spName, Map<String, Object> parameters) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Map<String, Object> executeWithResult(String spName, Map<String, Object> parameters, Map<String, Integer> outParams, String cursorName) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Map<String, Object> executeWithResult(Connection conn, String spName, Map<String, Object> parameters, Map<String, Integer> outParams, String cursorName) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List executeWithResultset(String spName) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List executeWithResultset(String spName, Map<String, Object> parameters) throws DataAccessException {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
