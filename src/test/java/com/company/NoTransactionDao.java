package com.company;

import org.springframework.stereotype.Repository;
import org.swiftdao.CrudDao;
import org.swiftdao.entity.BaseKeyedEntity;

/**
 * Created by allen on 15/9/16.
 */
@Repository
public interface NoTransactionDao<E extends BaseKeyedEntity> extends CrudDao<E>{


	 long countAllNoTransaction();
}
