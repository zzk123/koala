package org.openkoala.koala.auth.impl;

import java.security.MessageDigest;
import java.util.logging.Logger;

import org.openkoala.koala.auth.password.PasswordEncoder;

/**
 * EJB部署时用的密码加密实现
 * @author zhuyuanbiao
 * @date Dec 29, 2013 4:40:47 PM
 *
 */
public class EJBPasswordEncoder implements PasswordEncoder {

	private static final String ENCODING_UTF8 = "utf-8";

	private static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"a", "b", "c", "d", "e", "f" };

	private Object salt;
	private String algorithm;
	
	private static final Logger LOGGER = Logger.getLogger("PasswordEncoder");

	public void setSalt(Object salt) {
		this.salt = salt;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * 加密
	 * @param rawPass
	 * @return
	 */
	public String encode(String rawPass) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			// 加密后的字符串
			result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes(ENCODING_UTF8)));
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		return result;
	}

	/**
	 * 密码是否正确
	 * @param encPass
	 * @param rawPass
	 * @return
	 */
	public boolean isPasswordValid(String encPass, String rawPass) {
		String pass1 = "" + encPass;
		String pass2 = encode(rawPass);
		return pass1.equals(pass2);
	}

	/**
	 * 合并密码和盐值
	 * @param password
	 * @return
	 */
	private String mergePasswordAndSalt(String password) {
		if ((salt == null) || "".equals(salt)) {
			return password;
		}
		return password + "{" + salt.toString() + "}";
	}

	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param b 字节数组
	 * @return 	16进制字串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将字节转成16进制字符串
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

}