package org.swiftdao;

/**
 * 所有DAO接口的基础接口。
 * 要在IoC容器中是使用DAO,需要将Hibernate的Session Factory注入（如果采用Hibernate实现的话）
 * 注入方法为<code> setSessionFactory()}</code>
 * @author Wang Yuxing
 * @version 1.0
 */
public interface Dao {

	/**
	 * Check whether database available.
	 * @return
	 */
	public boolean checkDatabaseAvailable();
}
