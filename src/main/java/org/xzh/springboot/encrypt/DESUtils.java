package org.xzh.springboot.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 对称加密，已经被破解，不再使用。
 * 3DES是DES升级版
 *
 */
public class DESUtils {
	
	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

	private static final String KEY_ALGORITHM = "DES";
	
	private static final String ENCODING = "UTF-8";
	
	public static byte[] aesEncrypt(final String message, final String key) throws Exception{
		//获取cipher对象
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		//初始化cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key), new SecureRandom());
		//执行操作
		byte[] bytes = cipher.doFinal(message.getBytes(ENCODING));
		return bytes;
	}
	
	public static String aesDecrypt(final byte[] bytes, final String key) throws Exception{
		//获取cipher对象
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		//初始化cipher对象
		cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key), new SecureRandom());
		//执行操作
		byte[] results = cipher.doFinal(bytes);
		return new String(results, ENCODING);
	}
	
	private static SecretKey getSecretKey(final String key) throws Exception{
		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key.getBytes(ENCODING));
		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey secretKey = factory.generateSecret(dks);
		return secretKey;
	}
	
	public static void main(String[] args) throws Exception {
		String message = "des";
		String key = "12345678";//密码一定要是8的倍数
		byte[] bytes = aesEncrypt(message, key);
		System.out.println(new String(bytes, ENCODING));
		
		String result = aesDecrypt(bytes, key);
		System.out.println(result);
	}
}
