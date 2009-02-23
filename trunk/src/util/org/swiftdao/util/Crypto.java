package org.swiftdao.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * 加密用的帮助类
 * @author Wang Yuxing
 * 
 */
public class Crypto {

	/**
	 * 使用MD5加密
	 * @param signature
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String signature) throws RuntimeException {
		return encrypt(signature, true);
	}


	/**
	 * 使用MD5加密
	 * @param signature
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String signature, boolean isUpperCase) throws RuntimeException {
		if(signature == null){
			return null;
		}
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("加密算法错误");
		}
		String ret = null;
		byte[] plainText = signature.getBytes();
		md5.update(plainText);
		ret = bytes2HexString(md5.digest(), isUpperCase);
		return ret;
	}

	/**
	 * 二行制转字符串
	 * 
	 * @param bytes 字节数组
	 * @return 二进制字符串
	 */
	public static String bytes2HexString(byte[] bytes, boolean isUpperCase) {
		String hs = "";
		if (bytes != null) {
			for (byte b : bytes) {
				String tmp = (Integer.toHexString(b & 0XFF));
				if (tmp.length() == 1) {
					hs += "0" + tmp;
				} else {
					hs += tmp;
				}
			}
		}
		if(isUpperCase){
			return hs.toUpperCase();
		} else {
			return hs;
		}
		
	}
	
	/**
	 * BASE64转码
	 * @param plain
	 * @return
	 */
	public static String base64encode(String plain){
		if(plain == null || plain.equals("")){
			return plain;
		}
		BASE64Encoder b = new BASE64Encoder();
		String crypto = b.encode(plain.getBytes());
		return crypto;
	}
	

}
