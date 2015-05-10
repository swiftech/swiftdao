package org.swiftdao.demo;

import org.swiftdao.demo.entity.MockSingleKeyEntity;
import org.swiftdao.impl.HibernateKeyedCrudDaoImpl;

/**
 * 模拟DAO实现（基于Hibernate）
 * @author Wang Yuxing
 */
public class MockOrmDaoImpl extends HibernateKeyedCrudDaoImpl<MockSingleKeyEntity> implements MockOrmDao {

	// For test, no new method is required
}
