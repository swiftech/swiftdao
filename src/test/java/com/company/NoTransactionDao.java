package com.company;

import org.swiftdao.CrudDao;
import org.swiftdao.entity.BaseKeyedEntity;

/**
 * 测试没有事务管理的 Dao
 * Created by allen on 15/9/16.
 */
public interface NoTransactionDao<E extends BaseKeyedEntity> extends CrudDao<E>{


	 long countAllNoTransaction();
}
