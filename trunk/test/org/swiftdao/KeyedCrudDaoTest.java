package org.swiftdao;

import org.swiftdao.demo.MockOrmDao;
import org.swiftdao.demo.MockOrmDaoImpl;
import org.swiftdao.demo.entity.MockSingleKeyEntity;
import org.swiftdao.entity.KeyedPersistable;

import java.io.Serializable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Wang Yuxing
 */
public class KeyedCrudDaoTest {

    public KeyedCrudDaoTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

	/**
	 * Test of delete method, of class MockDao.
	 */
	@Test
	public void testDelete_long() {
		System.out.println("delete");
		long id = 0L;
		MockOrmDao instance = new MockOrmDaoImpl();
		instance.delete(id);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of delete method, of class MockDao.
	 */
	@Test
	public void testDelete_Serializable() {
		System.out.println("delete");
		Serializable key = null;
		MockOrmDao instance = new MockOrmDaoImpl();
		instance.delete(key);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of delete method, of class MockDao.
	 */
	@Test
	public void testDelete_StringArr_ObjectArr() {
		System.out.println("delete");
		String[] keyNames = null;
		Object[] keyValues = null;
		MockOrmDao instance = new MockOrmDaoImpl();
		instance.delete(keyNames, keyValues);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_long() {
		System.out.println("find");
		long id = 0L;
		MockOrmDao instance = new MockOrmDaoImpl();
		MockSingleKeyEntity expResult = null;
		MockSingleKeyEntity result = instance.find(id);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_Serializable() {
		System.out.println("find");
		Serializable id = null;
		MockOrmDao instance = new MockOrmDaoImpl();
		MockSingleKeyEntity expResult = null;
		MockSingleKeyEntity result = instance.find(id);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_StringArr_ObjectArr() {
		System.out.println("find");
		String[] keyNames = null;
		Object[] keyValues = null;
		MockOrmDao instance = new MockOrmDaoImpl();
		MockSingleKeyEntity expResult = null;
		MockSingleKeyEntity result = instance.find(keyNames, keyValues);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAndLock method, of class MockDao.
	 */
	@Test
	public void testFindAndLock_long() {
		System.out.println("findAndLock");
		long id = 0L;
		MockOrmDao instance = new MockOrmDaoImpl();
		MockSingleKeyEntity expResult = null;
		MockSingleKeyEntity result = instance.findAndLock(id);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAndLock method, of class MockDao.
	 */
	@Test
	public void testFindAndLock_Serializable() {
		System.out.println("findAndLock");
		Serializable id = null;
		MockOrmDao instance = new MockOrmDaoImpl();
		MockSingleKeyEntity expResult = null;
		MockSingleKeyEntity result = instance.findAndLock(id);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_Class_long() {
		System.out.println("find");
		Class clazz = null;
		long id = 0L;
		MockOrmDao instance = new MockOrmDaoImpl();
		KeyedPersistable expResult = null;
		KeyedPersistable result = instance.find(clazz, id);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_Class_Serializable() {
		System.out.println("find");
		Class clazz = null;
		Serializable id = null;
		MockOrmDao instance = new MockOrmDaoImpl();
		KeyedPersistable expResult = null;
		KeyedPersistable result = instance.find(clazz, id);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

}