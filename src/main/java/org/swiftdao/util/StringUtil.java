package org.swiftdao.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.StringTokenizer;
import java.util.UUID;

/**
 * @author Wang Yuxing
 *
 */
public class StringUtil {

	/**
	 * 解析一个带 token 分隔符的字符串，这个方法的效率比直接调用String的split()方法快大约1倍。
	 *
	 * @param tokenedStr
	 * @param token
	 * @return String[]
	 */
	public static String[] splitString(String tokenedStr, String token) {
		String[] ids = null;
		if (tokenedStr != null) {
			StringTokenizer st = new StringTokenizer(tokenedStr, token);
			final int arraySize = st.countTokens();
			if (arraySize > 0) {
				ids = new String[arraySize];
				int counter = 0;
				while (st.hasMoreTokens()) {
					ids[counter++] = st.nextToken();
				}
			}
		}
		return ids;
	}

	/**
	 * 把字符串数组组合成一个以指定分隔符分隔的字符串。
	 *
	 * @param strs 字符串数组
	 * @param separator 分隔符
	 * @return
	 */
	public static String mergeString(String[] strs, String separator) {
		StringBuilder sb = new StringBuilder();
		mergeString(strs, separator, sb);
		return sb.toString();
	}

	/**
	 * 把字符串数组组合成一个以指定分隔符分隔的字符串，并追加到给定的<code>StringBuilder</code>
	 *
	 * @param strs 字符串数组
	 * @param separator 分隔符
	 */
	public static void mergeString(String[] strs, String separator, StringBuilder sb) {
		for (int i = 0; i < strs.length; i++) {
			if (i != 0) {
				sb.append(separator);
			}
			sb.append(strs[i]);
		}
	}

	/**
	 * 随机产生UUID。
	 *
	 * @return
	 */
	public static String generateUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase();
	}

    /**
     * 随机产生全为数字的UUID
     * TODO
     * @return
     */
	public static String generateDigitalUUID() {
		UUID uuid = UUID.randomUUID();
		// System.out.println(uuid.toString());
		long high = uuid.getMostSignificantBits();
		if (high < 0) {
			high += Long.MAX_VALUE;
		}
		long low = uuid.getLeastSignificantBits();
		if (low < 0) {
			low += Long.MAX_VALUE;
		}
		return String.valueOf(high) + String.valueOf(low);
	}

	/**
	 * 返回指定长度的随机的数字字符串。
	 *
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) {
		String ret = "";
		for (int i = 0; i < length; i++) {
			ret += RandomUtils.nextInt(0, 9);
		}
		return ret;
	}

	/**
	 * 转换用户输入的HTML和Javascript代码
	 */
	public static String escapeUserInput(String userInput){
		return StringEscapeUtils.escapeEcmaScript(StringEscapeUtils.escapeHtml4(userInput));
	}


	/**
	 * 是否是降序或者升序
	 * @param str
	 * @param ascOrDesc
	 * @return
	 */
	public static boolean isAscDesc(String str, int ascOrDesc) {
		// console.log("check: " + str + " for " + ascOrDesc);
		char last = (char) ((int) str.charAt(0) - ascOrDesc);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// console.log(c + " and " + last);
			if (c != (last + ascOrDesc)) {
				return false;
			}
			last = c;
		}
		return true;
	}



}
