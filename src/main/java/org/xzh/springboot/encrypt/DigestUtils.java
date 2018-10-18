package org.xzh.springboot.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * 信息摘要:MD5与SHA1
 * 分别用JDK和Apache的commons-codec.jar实现
 *
 */
public class DigestUtils {

	/**
	 * 用“jdk的实现”生成MD5摘要，在rt.jar中java.security.MessageDigest
	 * 使用Apache提供commons-codec.jar的Hex做编码转换
	 * MD5生成128位摘要，用32位的十六进制表示
	 * 
	 * @param message
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public static String md5Sum(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] bytes = digest.digest(message.getBytes("UTF-8"));
		String md5 = Hex.encodeHexString(bytes);
		return md5;
	}
	
	/**
	 * 使用Apache提供commons-codec.jar做md5摘要
	 * 
	 * @param message
	 * @return
	 */
	public static String md5Sum2(String message){
		return org.apache.commons.codec.digest.DigestUtils.md5Hex(message);
	}
	
	/**
	 * 使用jdk提供生成SHA1摘要。
	 * 使用Apache提供commons-codec.jar的Hex做编码转换
	 * SHA1生成160位摘要，SHA256生成256位摘要，SHA384生成384位摘要，SHA512生成512位摘要
	 * 
	 * @param message
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String sha1Sum(String message) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		byte[] bytes = digest.digest(message.getBytes());
		String sha1 = Hex.encodeHexString(bytes);
		return sha1;
	}
	
	/**
	 * 使用Apache提供commons-codec.jar做sha1摘要
	 * 
	 * @param message
	 * @return
	 */
	public static String sha1Sum2(String message){
		return org.apache.commons.codec.digest.DigestUtils.sha1Hex(message);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String md5 = "md5";
		System.out.println(md5Sum(md5));
		System.out.println(md5Sum2(md5));
		
		System.out.println(sha1Sum(md5));
		System.out.println(sha1Sum2(md5));
	}
}
