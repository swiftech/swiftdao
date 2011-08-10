package infrastructure;

import org.logicalcobwebs.proxool.ConnectionInfoIF;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据访问单元测试基础类。
 * 
 * @author Wang Yuxing
 * 
 */
public abstract class BaseDataAccessTest {
	protected boolean keepTestData = true;

	protected Logger logger = null;

	public BaseDataAccessTest() {
		logger = LoggerFactory.getLogger(this.getClass());
	}
	
	protected void sleep(long second) {
		try {
			Thread.currentThread().sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void showConnectionPoolInfo() {
		try {
			SnapshotIF poolSpanShot = ProxoolFacade.getSnapshot("ut");
			if(poolSpanShot == null) {
				System.out.println("Failed to get snapshot of Proxool connection pool.");
			}
			System.out.println("Active Connections: " + poolSpanShot.getActiveConnectionCount());
			
//		Iterator i = ProxoolFacade.getConnectionInfos("ut").iterator();
//		while (i.hasNext()) {
//			ConnectionInfoIF c = (ConnectionInfoIF) i.next();
//			System.out.println("CONN: " + c.getRequester() + ", " + c.getStatus());
//		}
			ConnectionInfoIF[] connInfos = poolSpanShot.getConnectionInfos();
			for (int i = 0; i < connInfos.length; i++) {
				System.out.println("CONN: " + poolSpanShot.getConnectionInfos()[i]);
				
			}
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
	}

}
