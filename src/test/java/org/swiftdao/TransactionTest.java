package org.swiftdao;

import com.company.NoTransactionDao;
import infrastructure.BaseDaoTest;
import org.apache.commons.lang3.RandomUtils;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.swiftdao.demo.entity.MockSingleKeyEntity;

import java.util.List;

/**
 * Created by allen on 15/9/16.
 */
public class TransactionTest extends BaseDaoTest {

	private static Long longId1;
	private static Long longId2;

	@Autowired
	NoTransactionDao noTransactionDao;

	@BeforeClass
	public static void setUpClass() throws Exception {


	}

	@AfterClass
	public static void tearDownClass() throws Exception {

	}

	@Before
	public void setUp() {
		List<MockSingleKeyEntity> keyedEntities = createKeyedEntities(8);

		for (MockSingleKeyEntity keyedEntity : keyedEntities) {
			keyedEntity.setId(System.currentTimeMillis() + RandomUtils.nextInt(1, 1000));
			mockOrmDao.create(keyedEntity);
		}

		System.out.printf("创建 %d 个实体%n", keyedEntities.size());
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testNoTransaction() {
		long count;

		for (int i = 0; i < 15; i++) {

			count = noTransactionDao.countAllNoTransaction();

			System.out.println("   记录数: " + count);

			showConnectionPoolInfo();

		}
	}

	@Test
	public void testHibernateSession() {

		long count;

		for (int i = 0; i < 15; i++) {

			count = mockOrmDao.countAll();

			System.out.println("   记录数: " + count);

			showConnectionPoolInfo();
		}


	}
}
