/**
 * 
 */
package com.monitor.app.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * 加密后的密码保存到数据库
 * 
 * @author ibm
 *
 */
public class PassMd5Util {

	private static final Md5PasswordEncoder md5Encoder = new Md5PasswordEncoder();
	
	/**
	 * @param rawPass
	 * @param salt 用户登录名
	 * @return
	 */
	public static String encodeMd5WithSalt(String rawPass, String salt){ 
		String saltedPass = md5Encoder.encodePassword(rawPass, salt);
		
		return saltedPass;
	}
}
