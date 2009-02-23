package infrastructure;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
		logger = LogManager.getLogger(this.getClass());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
