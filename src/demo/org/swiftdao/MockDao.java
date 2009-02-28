package org.swiftdao;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于测试的模拟DAO接口
 * @author Wang Yuxing
 */
@Transactional(isolation=Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public interface MockDao extends KeyedCrudDao<MockSingleKeyEntity>{

}
