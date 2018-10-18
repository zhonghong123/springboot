package org.xzh.springboot.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 最常用的对称加密算法
 *
 */
public class AESUtils {
	
	/**
	 * 加密和解密必须用相同
	 * 
	 * AES：加密算法
	 * ECB：工作模式
	 * PKCS5Padding：填充方式
	 */
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	private static final String KEY_ALGORITHM = "AES";
	
	private static final String ENCODING = "UTF-8";

	/**
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] aesEncrypt(final String message, final String key) throws Exception{
		//获取Cipher对象
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		//使用密钥初始化Cipher，设置opmode=1(解密)
		cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));
		//执行操作
		byte[] bytes = cipher.doFinal(message.getBytes("UTF-8"));
		
		return bytes;
	}
	
	/**
	 * @param bytes
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(final byte[] bytes, final String key) throws Exception{
		//获取cipher对象
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		//使用密钥初始化cipher，设置opmode=2(解密)
		cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
		//执行操作
		byte[] results = cipher.doFinal(bytes);
		//byte数组转字符串
		String message = new String(results, ENCODING);
		return message;
	}
	
	private static SecretKeySpec getSecretKey(final String key) throws Exception {
		//返回生成指定算法密钥生成器的 KeyGenerator 对象
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
		//AES 要求密钥长度为 128
		keyGenerator.init(128, new SecureRandom(key.getBytes(ENCODING)));
		//生成一个密钥
		SecretKey secretKey = keyGenerator.generateKey();
		
		return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
	}
	
	public static void main(String[] args) throws Exception {
		String message = "aes";
		String key = "1";
		byte[] bytes = aesEncrypt(message, key);
		System.out.println(new String(bytes, "UTF-8"));
		
		String result = aesDecrypt(bytes, key);
		System.out.println(result);
	}
}
