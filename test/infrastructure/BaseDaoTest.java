package infrastructure;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.swiftdao.demo.MockJdbcDao;
import org.swiftdao.demo.MockOrmDao;
import org.swiftdao.demo.entity.MockSingleKeyEntity;

/**
 * 所有测试DAO的单元测试类。
 * 加载数据源，加载ORM和JDBC的DAO bean。
 * @author Wang Yuxing
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/ac_ds.xml", "classpath:spring/dao_mock.xml", "classpath:spring/ac_ds_encrypt.xml" })
public abstract class BaseDaoTest extends BaseDataAccessTest {

	protected List<String> springConfigLocations = new ArrayList<String>();
	protected static ApplicationContext beanFactory = null;
	protected MockOrmDao mockOrmDao = null;
	protected MockJdbcDao mockJdbcDao = null;
	
	public BaseDaoTest() {
		logger = LoggerFactory.getLogger(this.getClass());
		// this.initConfigLocations();
		String[] configPaths = springConfigLocations.toArray(new String[springConfigLocations.size()]);
		if (beanFactory == null) {
			beanFactory = new ClassPathXmlApplicationContext(configPaths);
		}
	}

	/**
	 * 创建一个指定ID的模拟entity
	 * @param id
	 * @return
	 */
	protected MockSingleKeyEntity createDefaultEntity(Long id) {
		MockSingleKeyEntity entity = new MockSingleKeyEntity();
		id = generateLongEntityID();
		System.out.println("ID 1: " + id);
		entity.setId(id);
		entity.setKey("key");
		entity.setStrValue("value");
		return entity;
	}

	/**
	 * 创建指定数量count的，ID为当前时间值的模拟entity
	 * @param count
	 * @return
	 */
	protected List<MockSingleKeyEntity> createKeyedEntities(int count) {
		List<MockSingleKeyEntity> list = new ArrayList<MockSingleKeyEntity>();
		for (int i = 0; i < count; i++) {
			MockSingleKeyEntity entity = new MockSingleKeyEntity(generateLongEntityID() + "" + i);
			entity.setStrValue("" + i % 2);
			entity.setIntValue(i % 2);
			entity.setCreationTime(Calendar.getInstance());
			list.add(entity);
		}
		return list;
	}

	/**
	 * Generate entity ID
	 * 
	 * @return
	 */
	protected long generateLongEntityID() {
		return Calendar.getInstance().getTimeInMillis();
	}
	
	public MockOrmDao getMockOrmDao() {
		return mockOrmDao;
	}
	
	public MockJdbcDao getMockJdbcDao() {
		return mockJdbcDao;
	}

	@Autowired
	public void setMockOrmDao(MockOrmDao keyedDao) {
		this.mockOrmDao = keyedDao;
	}
	
	@Autowired
	public void setMockJdbcDao(MockJdbcDao mockJdbcDao) {
		this.mockJdbcDao = mockJdbcDao;
	}

}
