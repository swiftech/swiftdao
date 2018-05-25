package org.swiftdao.demo.util;

import org.junit.Test;
import org.swiftdao.util.StringUtil;

/**
 * @author Allen 2018-03-09
 **/
public class StringUtilTest {

    @Test
    public void testGenerateUuid() {
        System.out.println(StringUtil.generateUUID());

        System.out.println(StringUtil.generateDigitalUUID());
    }
}
