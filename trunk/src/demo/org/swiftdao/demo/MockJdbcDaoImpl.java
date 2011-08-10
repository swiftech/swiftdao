package org.swiftdao.demo;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.logicalcobwebs.proxool.admin.StatisticsListenerIF;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.swiftdao.impl.JdbcDaoImpl;
import org.swiftdao.pool.ProxoolStatisticsListener;

/**
 * Used to test database connection.
 * @author Yuxing Wang
 * @since 
 */
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
		defReader.loadBeanDefinitions(new ClassPathResource("spring/ac_ds.xml"));
		
		PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
		cfg.setLocation(new ClassPathResource("spring/ds_h2.properties"));
		cfg.postProcessBeanFactory(beanFactory);

		DataSource ds = (DataSource)beanFactory.getBean("dataSource");
		this.setDataSource(ds);
		
		try {
			System.out.println(ds.getConnection());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}


		try {
			SnapshotIF snapshot = ProxoolFacade.getSnapshot("ut");
			System.out.println("Active connection: " + snapshot.getActiveConnectionCount());
			
			StatisticsListenerIF myStatisticsListener = new ProxoolStatisticsListener();
			ProxoolFacade.addStatisticsListener("ut", myStatisticsListener);
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
	}


}
