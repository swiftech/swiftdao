package infrastructure;

import org.apache.commons.lang3.RandomUtils;

/**
 * Created by allen on 2017/5/29.
 */
public class IdUtils {

    public static long createIdByTimeAndRandom() {
        return System.currentTimeMillis() + RandomUtils.nextInt(1, 99999);
    }
}
