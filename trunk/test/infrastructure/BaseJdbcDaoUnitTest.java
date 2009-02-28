package infrastructure;

import org.swiftdao.MockJdbcDao;
import java.util.Map;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 
 * @author Wang Yuxing
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ac-ut-encrypt.xml",
		"classpath:spring/ac-ut-ds.xml",
		"classpath:spring/ac-ut-dao.xml",
		"classpath:spring/ac-ut.xml"})
public class BaseJdbcDaoUnitTest extends BaseDaoUnitTest {

	MockJdbcDao jdbcDao = null;

	public BaseJdbcDaoUnitTest() {
//		jdbcDao = (TestJdbcDao) ac.getBean("jdbcDao");
	}

//	/**
//	 * TODO Need init data for unit test.
//	 */
//	public void testExecuteSp() {
//
//		Map<String, Object> p = new TreeMap<String, Object>();
//		p.put("s_key", "12003041727340");
//
//		Map<String, Object> result = jdbcDao.executeWithResult("myspInOut", p);
//		TestCase.assertTrue("No test data found for SP", result.size() > 0);
//		Set<String> keys = result.keySet();
//		for (String key : keys) {
//			System.out.println("testExecuteSp: " + result.get(key));
//		}
//	}
//
//	public void testExecuteSpNoIn() {
//		Map<String, Object> result = jdbcDao.executeWithResult("myspNoIn");
//		TestCase.assertTrue("No test data found for SP", result.size() > 0);
//		Set<String> keys = result.keySet();
//		for (String key : keys) {
//			System.out.println("testExecuteSpNoIn: " + result.get(key));
//		}
//	}

	public void testExecuteSpNoInOut() {
		jdbcDao.execute("myspNoInOut");
	}

	public void testExecuteSpNoOut() {
		Map<String, Object> p = new TreeMap<String, Object>();
		p.put("s_key", "12003041727340");
		jdbcDao.execute("myspNoOut", p);
	}

	public void testExecuteSpRecordsetReturned() {
	}
}
