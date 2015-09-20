package com.company.impl;

import com.company.NoTransactionDao;
import org.springframework.stereotype.Repository;
import org.swiftdao.demo.entity.MockSingleKeyEntity;
import org.swiftdao.entity.Persistable;
import org.swiftdao.impl.HibernateCrudDaoImpl;

/**
 * Created by allen on 15/9/16.
 */
@Repository
public class NoTransactionDaoImpl extends HibernateCrudDaoImpl implements NoTransactionDao {


	@Override
	protected Class<? extends Persistable> getEntityClass() {
		return MockSingleKeyEntity.class;
	}

	@Override
	public long countAllNoTransaction() {
		return countAll();
	}
}
