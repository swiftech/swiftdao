package org.swiftdao.demo;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.swiftdao.KeyedCrudDao;
import org.swiftdao.demo.entity.MockSingleKeyEntity;

/**
 * 用于测试的模拟DAO接口
 * @author Wang Yuxing
 */
@Transactional(isolation=Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public interface MockOrmDao extends KeyedCrudDao<MockSingleKeyEntity>{

	// For test, no new method is required
}
