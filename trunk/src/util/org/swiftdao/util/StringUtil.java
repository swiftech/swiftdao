package org.swiftdao.util;

import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;

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
	 * @param seperator 分隔符
	 * @return
	 */
	public static String mergeString(String[] strs, String seperator) {
		StringBuilder sb = new StringBuilder();
		mergeString(strs, seperator, sb);
		return sb.toString();
	}

	/**
	 * 把字符串数组组合成一个以指定分隔符分隔的字符串，并追加到给定的<code>StringBuilder</code>
	 * 
	 * @param strs 字符串数组
	 * @param seperator 分隔符
	 * @return
	 */
	public static void mergeString(String[] strs, String seperator, StringBuilder sb) {
		for (int i = 0; i < strs.length; i++) {
			if (i != 0) {
				sb.append(seperator);
			}
			sb.append(strs[i]);
		}
	}

	/**
	 * 产生UUID。
	 * 
	 * @return
	 */
	public static String generateUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase();
	}

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
			ret += RandomUtils.nextInt(9);
		}
		return ret;
	}
	
	/**
	 * 转换用户输入的HTML和Javascript代码
	 */
	public static String escapeUserInput(String userInput){
		return StringEscapeUtils.escapeJavaScript(StringEscapeUtils.escapeHtml(userInput));
	}
	
	/**
	 * 是否都是相同的字符。
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isAllSame(String text) {
		if (org.apache.commons.lang.StringUtils.isEmpty(text)) {
			return true;
		}
		char c = text.charAt(0);
		for (char cur : text.toCharArray()) {
			if (c != cur) {
				return false;
			}
		}
		return true;
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

	/**
	 * 从身份证号码中取出生日，
	 * 
	 * @param identity
	 * @return
	 */
	public static Date getBirthdayFromIdentity(String identity) {
		try {
			String id = getBirthdayFromIdentityAsString(identity);
			if (StringUtils.isEmpty(id)) {
				return null;
			}
			return DateUtils.parseDate(id, new String[] { "yyyyMMdd" });
		} catch (ParseException e) {
			throw new RuntimeException("您的身份证号码不正确。");
		}
	}

	/**
	 * 返回格式为"yyyyMMdd"的日期字符串
	 * 
	 * @param identity
	 * @return
	 */
	public static String getBirthdayFromIdentityAsString(String identity) {
		if (StringUtils.isEmpty(identity)) {
			return StringUtils.EMPTY;
		}
		if (identity.length() == 15) {
			String time = StringUtils.leftPad(identity.substring(6, 12), 8, "19");
			return time;
		} else if (identity.length() == 18) {
			String time = identity.substring(6, 14);
			return time;
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 检查字符串数组中是否有空的字符串。
	 * @param strs
	 * @return
	 */
	public static boolean isEmpty(String... strs){
		for(String s:strs){
			if(StringUtils.isEmpty(s)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据身份证判断是否成年
	 * @param birthDateStr
	 * @return
	 */
	public static boolean identityIslegal(Date birthDateStr) {
		boolean flag = false;

		Calendar birth = Calendar.getInstance();
		birth.add(Calendar.YEAR, -18);
		Calendar birthIdentity = Calendar.getInstance();
		birthIdentity.setTime(birthDateStr);
		if (birth.after(birthIdentity)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
}
