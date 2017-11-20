package org.swiftdao.demo;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.swiftdao.impl.JdbcDaoImpl;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Used to test database connection.
 * @author Yuxing Wang
 * @since 
 */
@Repository
public class MockJdbcDaoImpl extends JdbcDaoImpl implements MockJdbcDao{

	public static void main(String[] args) {
		MockJdbcDaoImpl mockDao = new MockJdbcDaoImpl();
//		boolean isDbAvailable = mockDao.checkDatabaseAvailable();
//		System.out.println(isDbAvailable);
		try {
			Thread.currentThread().sleep(1000 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public MockJdbcDaoImpl() {
		ConfigurableListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader defReader = new XmlBeanDefinitionReader((DefaultListableBeanFactory)beanFactory);
		defReader.loadBeanDefinitions(new ClassPathResource("conf/ac_ds_ut.xml"));
		
		PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
		cfg.setLocation(new ClassPathResource("conf/ds_h2.properties"));
		cfg.postProcessBeanFactory(beanFactory);

		DataSource ds = (DataSource)beanFactory.getBean("dataSource");
		this.setDataSource(ds);
		
		try {
			System.out.println(ds.getConnection());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

// Proxool 输出信息
//		try {
//			SnapshotIF snapshot = ProxoolFacade.getSnapshot("ut");
//			System.out.println("Active connection: " + snapshot.getActiveConnectionCount());
//
//			StatisticsListenerIF myStatisticsListener = new ProxoolStatisticsListener();
//			ProxoolFacade.addStatisticsListener("ut", myStatisticsListener);
//		} catch (ProxoolException e) {
//			e.printStackTrace();
//		}
	}


}
