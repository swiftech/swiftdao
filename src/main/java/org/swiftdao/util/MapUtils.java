package org.swiftdao.util;

import java.util.Map;

/**
 *
 * Created by yuxing on 2017/5/17.
 */
public class MapUtils {

    /**
     * 是否包含为 null 的值
     *
     * @param m
     * @return
     */
    public static boolean isContainsEmptyValue(Map m) {
        if (m == null || m.isEmpty()) {
            throw new IllegalArgumentException("");
        }
        for (Object k : m.keySet()) {
            if (m.get(k) == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否存在非 null 的值（即至少有一个非 null 的值）
     *
     * @param m
     * @return
     */
    public static boolean hasValues(Map m) {
        if (m == null || m.isEmpty()) {
            return false;
        }
        for (Object k : m.keySet()) {
            if (m.get(k) != null) {
                return true;
            }
        }
        return false;
    }
}
