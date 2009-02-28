package infrastructure;

import org.swiftdao.MockSingleKeyEntity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.LogManager;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 所有测试DAO的单元测试类。
 * 
 * @author Wang Yuxing
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ac-ut-ds-encrypt.xml",
		"classpath:spring/ac-ut-ds.xml",
		"classpath:spring/ac-ut-dao.xml"})
public abstract class BaseDaoUnitTest extends BaseUnitTest {

	protected List<String> springConfigLocations = new ArrayList<String>();
	protected static ApplicationContext ac = null;

	public BaseDaoUnitTest() {
		logger = LogManager.getLogger(this.getClass());
//		this.initConfigLocations();
		String[] ss = springConfigLocations.toArray(new String[springConfigLocations.size()]);
		if (ac == null) {
			ac = new ClassPathXmlApplicationContext(ss);
		}
	}

	protected List<MockSingleKeyEntity> createKeyedTestPojo(int count) {
		List<MockSingleKeyEntity> list = new ArrayList<MockSingleKeyEntity>();
		for (int i = 0; i < count; i++) {
			MockSingleKeyEntity pojo = new MockSingleKeyEntity(Calendar.getInstance().getTimeInMillis() + "" + i);
			pojo.setStrValue("" + i % 2);
			pojo.setIntValue(i % 2);
			pojo.setCreationTime(Calendar.getInstance());
			list.add(pojo);
		}
		return list;
	}
}
