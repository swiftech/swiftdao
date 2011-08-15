package org.swiftdao;

import infrastructure.BaseDaoTest;

import org.junit.Test;

/**
 * 
 * @author yuxing
 *
 */
public class CommonTest extends BaseDaoTest{
	
	@Test
	public void testDaoBeans() {
		// 检查DAO bean是否被AOP增强?
		System.out.println(mockOrmDao);
		System.out.println(mockJdbcDao);
	}

}
