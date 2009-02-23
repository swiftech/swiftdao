package org.swiftdao.util;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;

/**
 * @author Wang Yuxing
 * @since 2008-1-30
 */
public class HibernateUtils {

    /**
     * 取得所有的属性名
     * @param sf SessionFactory
     * @param clazz 对象类
     * @return 属性名数组
     */
    public static String[] getEntityAttributes(SessionFactory sf, Class<?> clazz) {
        ClassMetadata cm = sf.getClassMetadata(clazz);
        String idName = cm.getIdentifierPropertyName();
        String[] attrs = cm.getPropertyNames();
        String[] retArray = new String[attrs.length + 1];
        retArray[0] = idName;
        System.arraycopy(attrs, 0, retArray, 1, attrs.length);
        return retArray;
    }
}