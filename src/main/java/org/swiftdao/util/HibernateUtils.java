package org.swiftdao.util;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Yuxing
 * @since 2008-1-30
 */
public class HibernateUtils {

	/**
	 * 取得所有实体的属性名
	 *
	 * @param sf    SessionFactory
	 * @param clazz 实体对象类
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

    /**
     * 取得所有实体的属性名（排除容器属性）
     *
     * @param sf    SessionFactory
     * @param clazz 实体对象类
     * @return 属性名数组
     */
    public static String[] getEntityAttributesNoCollections(SessionFactory sf, Class<?> clazz) {
        ClassMetadata cm = sf.getClassMetadata(clazz);
        String idName = cm.getIdentifierPropertyName();
        String[] attrs = cm.getPropertyNames();

        List<String> ret = new ArrayList<>();
        ret.add(idName);
        for (String attr : attrs) {
            Type propType = cm.getPropertyType(attr);
            if (!propType.isCollectionType()) {
                ret.add(attr);
            }
        }
        return ret.toArray(new String[0]);
    }


}