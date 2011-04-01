package org.swiftdao.demo;

import org.swiftdao.impl.HibernateKeyedCrudDaoImpl;

/**
 * 模拟DAO实现（基于Hibernate）
 * @author Wang Yuxing
 */
public class MockDaoImpl extends HibernateKeyedCrudDaoImpl<MockSingleKeyEntity> implements MockDao {

	// For test, no new method is required
}
