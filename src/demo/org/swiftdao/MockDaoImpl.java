package org.swiftdao;

import org.swiftdao.impl.HibernateKeyedCrudDaoImpl;

/**
 * 
 * @author Wang Yuxing
 */
public class MockDaoImpl extends HibernateKeyedCrudDaoImpl<SingleKeyEntity> implements MockDao {

//	@Override
//	protected Class<? extends Pojo> getPojoClass() {
//		return SingleKeyEntity.class;
//	}
}
