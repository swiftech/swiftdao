package org.swiftdao;

import infrastructure.BaseDaoTest;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.swiftdao.demo.entity.MockSingleKeyEntity;
import org.swiftdao.entity.KeyedPersistable;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Wang Yuxing
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:conf/ac_ds_ut.xml",
		"classpath:conf/dao_mock.xml",
        "classpath:conf/ac_trx_hibernate.xml"})
public class KeyedCrudDaoTest extends BaseDaoTest {

    private ThreadLocal<Long> longId = new ThreadLocal<>();

    private ThreadLocal<String> key = new ThreadLocal<>();

    private ThreadLocal<String> value = new ThreadLocal<>();

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
        MockSingleKeyEntity entity = createDefaultEntity();
        mockOrmDao.create(entity);
        longId.set(entity.getId());
        key.set(entity.getKey());
        value.set(entity.getStrValue());
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testFindAll() {
        List<MockSingleKeyEntity> all = mockOrmDao.findAll();
        Assert.assertTrue(all.size() > 0);
        System.out.println("总数：" + all.size());
        for (MockSingleKeyEntity mockSingleKeyEntity : all) {
            System.out.println("    " + mockSingleKeyEntity.toString());
        }

    }

	/**
	 * Test of delete method, of class MockDao.
	 */
	@Test
	public void testDelete_long() {
		System.out.println("delete");
//		long id = 1L;
//		MockOrmDao instance = new MockOrmDaoImpl();
		mockOrmDao.delete(longId.get());
	}

	/**
	 * Test of delete method, of class MockDao.
	 */
	@Test
	public void testDelete_Serializable() {
		System.out.println("delete");
//		Serializable key = null;
        mockOrmDao.delete(longId.get());
	}

	/**
	 * Test of delete method, of class MockDao.
	 */
	@Test
	public void testDelete_StringArr_ObjectArr() {
		System.out.println("delete");
        String[] keyNames = new String[]{"key", "strValue"};
        Object[] keyValues = new String[]{key.get(), value.get()};
        System.out.println(ArrayUtils.toString(keyValues));
        mockOrmDao.delete(keyNames, keyValues);
    }

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_long() {
		System.out.println("find");
		MockSingleKeyEntity result = mockOrmDao.find(longId.get());
		Assert.assertNotNull(result);
	}

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_Serializable() {
		System.out.println("find");
		MockSingleKeyEntity result = mockOrmDao.find(longId.get());
		assertNotNull(result);
	}

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_StringArr_ObjectArr() {
		System.out.println("find");
		String[] keyNames = null;
		Object[] keyValues = null;
		MockSingleKeyEntity expResult = null;
		MockSingleKeyEntity result = mockOrmDao.find(keyNames, keyValues);
		assertEquals(expResult, result);
	}

	/**
	 * Test of findAndLock method, of class MockDao.
	 */
	@Test
	public void testFindAndLock_long() {
		System.out.println("findAndLock");
		long id = 0L;
		MockSingleKeyEntity expResult = null;
		MockSingleKeyEntity result = mockOrmDao.findAndLock(id);
		assertEquals(expResult, result);
	}

	/**
	 * Test of findAndLock method, of class MockDao.
	 */
	@Test
	public void testFindAndLock_Serializable() {
		System.out.println("findAndLock");
		MockSingleKeyEntity result = mockOrmDao.findAndLock(longId.get());
		assertNotNull(result);
	}

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_Class_long() {
		System.out.println("find");
		Class clazz = MockSingleKeyEntity.class;
		KeyedPersistable result = mockOrmDao.find(clazz, longId.get());
        assertNotNull(result);
	}

	/**
	 * Test of find method, of class MockDao.
	 */
	@Test
	public void testFind_Class_Serializable() {
		System.out.println("find");
		Class clazz = MockSingleKeyEntity.class;
		KeyedPersistable result = mockOrmDao.find(clazz, longId.get());
		assertNotNull(result);
	}

}