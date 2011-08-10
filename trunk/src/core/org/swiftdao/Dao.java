package org.swiftdao;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 所有DAO接口的基础接口。
 * 要在IoC容器中是使用DAO,需要将Hibernate的Session Factory注入（如果采用Hibernate实现的话）
 * 注入方法为<code> setSessionFactory()}</code>
 * @author Wang Yuxing
 * @version 1.0
 */
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public interface Dao {
	
	/**
	 * Get database information.
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getDatabaseInfo();

	/**
	 * Check whether database available.
	 * @return
	 */
	@Transactional(readOnly=true)
	public boolean checkDatabaseAvailable();
}
