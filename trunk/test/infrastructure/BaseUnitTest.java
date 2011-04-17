package infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单元测试基础类。
 * 
 * @author Wang Yuxing
 * 
 */
public class BaseUnitTest {
	protected boolean keepTestData = true;

	protected Logger logger = null;

	public BaseUnitTest() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
