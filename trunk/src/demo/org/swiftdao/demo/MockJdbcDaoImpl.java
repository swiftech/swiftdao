package org.swiftdao.demo;

import javax.sql.DataSource;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.swiftdao.impl.JdbcDaoImpl;

/**
 * Used to test database connection.
 * @author Yuxing Wang
 * @since 
 */
public class MockJdbcDaoImpl extends JdbcDaoImpl{

	public static void main(String[] args) {
		MockJdbcDaoImpl mockDao = new MockJdbcDaoImpl();
		boolean isDbAvailable = mockDao.checkDatabaseAvailable();
		System.out.println(isDbAvailable);
	}

	public MockJdbcDaoImpl() {
		ListableBeanFactory beanfFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader defReader = new XmlBeanDefinitionReader((DefaultListableBeanFactory)beanfFactory);
		defReader.loadBeanDefinitions(new ClassPathResource("spring/hsqldb_ds.xml"));
		DataSource ds = (DataSource)beanfFactory.getBean("dataSource");
		this.setDataSource(ds);
	}


}
