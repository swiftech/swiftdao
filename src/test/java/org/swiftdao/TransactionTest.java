package org.swiftdao;

import com.company.NoTransactionDao;
import infrastructure.BaseDaoTest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yuxing on 15/9/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:conf/ac_ds_ut.xml",
		"classpath:conf/dao_mock.xml"})
public class TransactionTest extends BaseDaoTest {

	private static Long longId1;
	private static Long longId2;

	@Autowired
	private
	NoTransactionDao noTransactionDao; // 这个 DAO 所属的 package 未配置事务处理

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
	 * 不受 Spring 事务管理，这个测试方法应该会抛出异常并中止。
	 */
	@Test
	public void testNoTransaction() {
		long count;

		for (int i = 0; i < 15; i++) {

			count = noTransactionDao.countAllNoTransaction();

			System.out.println("   记录数: " + count);

			showConnectionPoolInfo();

		}
	}

	/**
	 * Spring 事务管理
	 */
	@Test
	public void testTransaction() {
// 创建测试数据
//		List<MockSingleKeyEntity> keyedEntities = createKeyedEntities(8);
//
//		for (MockSingleKeyEntity keyedEntity : keyedEntities) {
//			keyedEntity.setId(System.currentTimeMillis() + RandomUtils.nextInt(1, 1000));
//			mockOrmDao.create(keyedEntity);
//		}
//
//		System.out.printf("创建 %d 个实体%n", keyedEntities.size());

		long count;

		for (int i = 0; i < 1; i++) {

			count = mockOrmDao.countAll();

			System.out.println("   记录数: " + count);

			showConnectionPoolInfo();
		}


	}
}
