package org.swiftdao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import infrastructure.BaseDaoTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.swiftdao.demo.MockOrmDao;
import org.swiftdao.demo.entity.MockSingleKeyEntity;

/**
 * Test CRUD functionality with H2 in-memory database.
 * @author Wang Yuxing
 */
public class CrudDaoTest extends BaseDaoTest {

	private static Long longId1;
	private static Long longId2;


	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		
	}

	@Before
	public void setUp() {
		// 
	}

	@After
	public void tearDown() {
//		super.sleep(1);
		showConnectionPoolInfo();
	}
	
	@Test
	public void testSomething() {
		for (int i = 0; i < 30; i++) {
			System.out.print("*");
			mockOrmDao.findByUniqueParam("UT_KEY", "jack" + i);
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testGetDatabaseInfo() {
		System.out.println("testGetDatabaseInfo()");
		System.out.println(mockOrmDao.getDatabaseInfo());
		for (int i = 0; i < 30; i++) {
			System.out.println(mockOrmDao.getDatabaseInfo());	
		}
	}

	/**
	 * Test of create method, of class MockDao.
	 */
	@Test
	public void testCreate_null() {
		System.out.println("testCreate_null");
		MockSingleKeyEntity entity = new MockSingleKeyEntity();
		longId1 = Calendar.getInstance().getTimeInMillis();
		entity.setId(longId1);
		entity.setKey("key");
		entity.setStrValue("value");
		mockOrmDao.create(entity);
	}

	/**
	 * Test of create method, of class MockDao.
	 */
	@Test
	public void testCreate_Collection() {
		System.out.println("testCreate_Collection");
		Collection<MockSingleKeyEntity> newEntities = new ArrayList<MockSingleKeyEntity>();
		MockSingleKeyEntity entity1 = new MockSingleKeyEntity();
		longId1 = Calendar.getInstance().getTimeInMillis();
		System.out.println("ID 1: " + longId1);
		entity1.setId(longId1);
		entity1.setKey("key");
		entity1.setStrValue("value");
		try {
			Thread.sleep(100);
		} catch (InterruptedException ex) {
			Logger.getLogger(CrudDaoTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		MockSingleKeyEntity entity2 = new MockSingleKeyEntity();
		longId2 = Calendar.getInstance().getTimeInMillis();
		System.out.println("ID 2: " + longId2);
		entity2.setId(longId2);
		entity2.setKey("key");
		entity2.setStrValue("value");
		newEntities.add(entity1);
		newEntities.add(entity2);
		mockOrmDao.create(newEntities);
	}

	/**
	 * Test of update method, of class MockDao.
	 */
	@Test
	public void testUpdate_null() {
		System.out.println("testUpdate_null");
		MockSingleKeyEntity entity = mockOrmDao.find(longId1);
		entity.setStrValue("testUpdate_null");
		mockOrmDao.update(entity);
	}

	/**
	 * Test of update method, of class MockDao.
	 */
	@Test
	public void testUpdate_Collection() {
		System.out.println("update");
		Collection<MockSingleKeyEntity> entities = mockOrmDao.findAll();
		for (MockSingleKeyEntity en : entities) {
			en.setStrValue("testUpdate_Collection");
		}
		mockOrmDao.update(entities);
	}

	/**
	 * Test of createOrUpdate method, of class MockDao.
	 */
	@Test
	public void testCreateOrUpdate_null() {
		System.out.println("createOrUpdate");
		MockSingleKeyEntity entity = new MockSingleKeyEntity();
		longId1 = Calendar.getInstance().getTimeInMillis();
		entity.setId(longId1);
		entity.setKey("key");
		entity.setStrValue("value");
		mockOrmDao.createOrUpdate(entity);
		// Update
		entity = mockOrmDao.find(longId1);
		mockOrmDao.createOrUpdate(entity);
	}

	/**
	 * Test of createOrUpdate method, of class MockDao.
	 */
	@Test
	public void testCreateOrUpdate_Collection() {
		System.out.println("testCreateOrUpdate_Collection");
		Collection<MockSingleKeyEntity> entities = new ArrayList();
		MockSingleKeyEntity entity1 = new MockSingleKeyEntity();
		longId1 = Calendar.getInstance().getTimeInMillis();
		System.out.println("ID 1: " + longId1);
		entity1.setId(longId1);
		entity1.setKey("key");
		entity1.setStrValue("value");
		try {
			Thread.sleep(100);
		} catch (InterruptedException ex) {
			Logger.getLogger(CrudDaoTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		MockSingleKeyEntity entity2 = new MockSingleKeyEntity();
		longId2 = Calendar.getInstance().getTimeInMillis();
		System.out.println("ID 2: " + longId2);
		entity2.setId(longId2);
		entity2.setKey("key");
		entity2.setStrValue("value");
		entities.add(entity1);
		entities.add(entity2);
		mockOrmDao.createOrUpdate(entities);

		// Update
		List ens = mockOrmDao.findAll();
		mockOrmDao.createOrUpdate(ens);
	}

	/**
	 * Test of merge method, of class MockDao.
	 */
	@Test
	public void testMerge_null() {
		System.out.println("testMerge_null");
		MockSingleKeyEntity entity = createDefaultEntity(longId1);
		mockOrmDao.create(entity);
		mockOrmDao.update(entity);
		mockOrmDao.merge(entity);
	}

	/**
	 * Test of merge method, of class MockDao.
	 */
	@Test
	public void testMerge_Collection() {
		System.out.println("testMerge_Collection");
		MockSingleKeyEntity entity1 = createDefaultEntity(longId1);
		MockSingleKeyEntity entity2 = createDefaultEntity(longId2);
		Collection<MockSingleKeyEntity> entities = new ArrayList();
		;
		entities.add(entity1);
		entities.add(entity2);
		mockOrmDao.merge(entities);
	}

	/**
	 * Test of findByUniqueParam method, of class MockDao.
	 */
	@Test
	public void testFindByUniqueParam() {
		System.out.println("findByUniqueParam");
		String uniqueParamName = "key";
		String value = "key";
		MockSingleKeyEntity result = mockOrmDao.findByUniqueParam(uniqueParamName, value);
		assertNotNull(result);
	}

	/**
	 * Test of findByParam method, of class MockDao.
	 */
	@Test
	public void testFindByParam() {
		System.out.println("findByParam");
		String paramName = "strValue";
		Object value = "value";
		List<MockSingleKeyEntity> result = mockOrmDao.findByParam(paramName, value);
		assertFalse(CollectionUtils.isEmpty(result));
		for (MockSingleKeyEntity e : result) {
			System.out.println("#" + e);
		}
	}

	/**
	 * Test of findByParamPagination method, of class MockDao.
	 */
	@Test
	public void testFindByParamPagination_4args() {
		System.out.println("testFindByParamPagination_4args");
		String paramName = "";
		Object value = "";
		int pageSize = 0;
		int pageNumber = 0;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParamPagination(paramName, value, pageSize, pageNumber);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findByParamPagination method, of class MockDao.
	 */
	@Test
	public void testFindByParamPagination_6args() {
		System.out.println("findByParamPagination");
		String paramName = "";
		Object value = null;
		int pageSize = 0;
		int pageNumber = 0;
		String orderParam = "";
		boolean isDescending = false;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParamPagination(paramName, value, orderParam, isDescending, pageSize, pageNumber);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findByParams method, of class MockDao.
	 */
	@Test
	public void testFindByParams_Map() {
		System.out.println("findByParams");
		Map<String, Object> paramMap = null;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParams(paramMap);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findByParams method, of class MockDao.
	 */
	@Test
	public void testFindByParams_String_Map() {
		System.out.println("findByParams");
		String extraCondition = "";
		Map<String, Object> extraParams = null;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParams(extraCondition, extraParams);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findByParams method, of class MockDao.
	 */
	@Test
	public void testFindByParams_3args() {
		System.out.println("findByParams");
		Map<String, Object> paramMap = null;
		String extraCondition = "";
		Map<String, Object> extraParams = null;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParams(paramMap, extraCondition, extraParams);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findByParamsPagination method, of class MockDao.
	 */
	@Test
	public void testFindByParamsPagination_3args() {
		System.out.println("findByParamsPagination");
		Map<String, Object> paramMap = null;
		int pageSize = 0;
		int pageNumber = 0;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParamsPagination(paramMap, pageSize, pageNumber);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findByParamsPagination method, of class MockDao.
	 */
	@Test
	public void testFindByParamsPagination_4args() {
		System.out.println("findByParamsPagination");
		String condition = "";
		Map<String, Object> params = null;
		int pageSize = 0;
		int pageNumber = 0;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParamsPagination(condition, params, pageSize, pageNumber);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findByParamsPagination method, of class MockDao.
	 */
	@Test
	public void testFindByParamsPagination_5args_1() {
		System.out.println("findByParamsPagination");
		Map<String, Object> paramMap = null;
		String extraCondition = "";
		Map<String, Object> extraParams = null;
		int pageSize = 0;
		int pageNumber = 0;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParamsPagination(paramMap, extraCondition, extraParams, pageSize, pageNumber);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findByParamsPagination method, of class MockDao.
	 */
	@Test
	public void testFindByParamsPagination_5args_2() {
		System.out.println("findByParamsPagination");
		Map<String, Object> paramMap = null;
		int pageSize = 0;
		int pageNumber = 0;
		String orderParam = "";
		boolean isDescending = false;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParamsPagination(paramMap, orderParam, isDescending, pageSize, pageNumber);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findByParamsPagination method, of class MockDao.
	 */
	@Test
	public void testFindByParamsPagination_7args() {
		System.out.println("findByParamsPagination");
		Map<String, Object> paramMap = null;
		String extraCondition = "";
		Map<String, Object> extraParams = null;
		int pageSize = 0;
		int pageNumber = 0;
		String orderParam = "";
		boolean isDescending = false;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findByParamsPagination(paramMap, extraCondition, extraParams, orderParam, isDescending, pageSize, pageNumber);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAll method, of class MockDao.
	 */
	@Test
	public void testFindAll_0args() {
		System.out.println("findAll");

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findAll();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAll method, of class MockDao.
	 */
	@Test
	public void testFindAll_Class() {
		System.out.println("findAll");
		Class clazz = null;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findAll(clazz);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAllOverCache method, of class MockDao.
	 */
	@Test
	public void testFindAllOverCache() {
		System.out.println("findAllOverCache");
		Class clazz = null;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findAllOverCache(clazz);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAllByPagination method, of class MockDao.
	 */
	@Test
	public void testFindAllByPagination_int_int() {
		System.out.println("findAllByPagination");
		int pageSize = 0;
		int pageNumber = 0;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findAllByPagination(pageSize, pageNumber);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAllByPagination method, of class MockDao.
	 */
	@Test
	public void testFindAllByPagination_4args() {
		System.out.println("findAllByPagination");
		int pageSize = 0;
		int pageNumber = 0;
		String orderParam = "";
		boolean isDescending = false;

		List<MockSingleKeyEntity> expResult = null;
		List<MockSingleKeyEntity> result = mockOrmDao.findAllByPagination(orderParam, isDescending, pageSize, pageNumber);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of countAll method, of class MockDao.
	 */
	@Test
	public void testCountAll() {
		System.out.println("countAll");

		long expResult = 0L;
		long result = mockOrmDao.countAll();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of countByParam method, of class MockDao.
	 */
	@Test
	public void testCountByParam() {
		System.out.println("countByParam");
		String paramName = "";
		Object value = null;

		long expResult = 0L;
		long result = mockOrmDao.countByParam(paramName, value);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of countByParams method, of class MockDao.
	 */
	@Test
	public void testCountByParams_Map() {
		System.out.println("countByParams");
		Map<String, Object> paramMap = null;

		long expResult = 0L;
		long result = mockOrmDao.countByParams(paramMap);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of countByParams method, of class MockDao.
	 */
	@Test
	public void testCountByParams_3args() {
		System.out.println("countByParams");
		Map<String, Object> paramMap = null;
		String extraCondition = "";
		Map<String, Object> extraParams = null;

		long expResult = 0L;
		long result = mockOrmDao.countByParams(paramMap, extraCondition, extraParams);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getSequence method, of class MockDao.
	 */
	@Test
	public void testGetSequence() {
		System.out.println("getSequence");
		String seqName = "";

		long expResult = 0L;
		long result = mockOrmDao.getSequence(seqName);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

//	/**
//	 * Test of delete method, of class MockDao.
//	 */
//	@Test
//	public void testDelete_null() {
//		System.out.println("testDelete_null");
//		SingleKeyEntity entity = dao.find(longId1);
//		dao.delete(entity);
//		SingleKeyEntity en = dao.find(longId1);
//		TestCase.assertNull(en);
//	}
//
//	/**
//	 * Test of delete method, of class MockDao.
//	 */
//	@Test
//	public void testDelete_Collection() {
//		System.out.println("delete");
//		Collection<SingleKeyEntity> entities = dao.findAll();
//		if (CollectionUtils.isEmpty(entities)) {
//			TestCase.fail("No entities to delete");
//		}
//		dao.delete(entities);
//		Collection<SingleKeyEntity> ens = dao.findAll();
//		TestCase.assertEquals(0, ens.size());
//	}

}