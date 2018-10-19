package org.xzh.springboot.encrypt;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 非对称加密：RSA
 *
 */
public class RSAUtils {
	
	private static final String DEFAULT_CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	
	private static final String KEY_ALGORITHM = "RSA";
	
	private static final String ENCODING = "UTF-8";
	
	/**
	 * 根据公钥字符串生产公钥
	 * 
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey getRSAPublicKey(String publicKey) throws Exception{
		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(publicKey));
		RSAPublicKey rsaPublicKey = (RSAPublicKey) factory.generatePublic(spec);
		return rsaPublicKey;
	}
	
	/**
	 * 根据私钥字符串生产私钥
	 * 
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey getRSAPrivateKey(String privateKey) throws Exception{
		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) factory.generatePrivate(keySpec);
		return rsaPrivateKey;
	}
	
	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param rsaPublicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] publicEncrypt(String data, RSAPublicKey rsaPublicKey) throws Exception{
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
		
		int maxBlock = rsaPublicKey.getModulus().bitLength()/8 -11;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int offset = 0;
		byte[] buff;
		int i = 0;
		byte[] datas = data.getBytes(ENCODING);
		while(datas.length > offset){
			if(datas.length-offset > maxBlock){
                buff = cipher.doFinal(datas, offset, maxBlock);
            }else{
                buff = cipher.doFinal(datas, offset, datas.length-offset);
            }
			byteArrayOutputStream.write(buff, 0, buff.length);
            i++;
            offset = i * maxBlock;
		}
		byte[] resultDatas = byteArrayOutputStream.toByteArray();
		return resultDatas;
	}
	
	/**
	 * 私钥解密
	 * 
	 * @param datas
	 * @param rsaPrivateKey
	 * @return
	 */
	public static byte[] privateDecrypt(byte[] datas, RSAPrivateKey rsaPrivateKey) throws Exception{
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
		
		int maxBlock = rsaPrivateKey.getModulus().bitLength()/8;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int offset = 0;
		byte[] buff;
		int i = 0;
		while(datas.length > offset){
			if(datas.length-offset > maxBlock){
                buff = cipher.doFinal(datas, offset, maxBlock);
            }else{
                buff = cipher.doFinal(datas, offset, datas.length-offset);
            }
			byteArrayOutputStream.write(buff, 0, buff.length);
            i++;
            offset = i * maxBlock;
		}
		byte[] resultDatas = byteArrayOutputStream.toByteArray();
		return resultDatas;
	}
	
	//------------------------------------------------------------------------------
	
	/**
	 * 私钥加密
	 * 
	 * @param data
	 * @param rsaPrivateKey
	 * @return
	 */
	public static byte[] privateEncrypt(String data, RSAPrivateKey rsaPrivateKey) throws Exception{
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
		
		int maxBlock = rsaPrivateKey.getModulus().bitLength()/8 -11;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int offset = 0;
		byte[] buff;
		int i = 0;
		byte[] datas = data.getBytes(ENCODING);
		while(datas.length > offset){
			if(datas.length-offset > maxBlock){
                buff = cipher.doFinal(datas, offset, maxBlock);
            }else{
                buff = cipher.doFinal(datas, offset, datas.length-offset);
            }
			byteArrayOutputStream.write(buff, 0, buff.length);
            i++;
            offset = i * maxBlock;
		}
		byte[] resultDatas = byteArrayOutputStream.toByteArray();
		return resultDatas;
	}
	
	/**
	 * 公钥解密
	 * 
	 * @param datas
	 * @param rsaPublicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] publicDecrypt(byte[] datas, RSAPublicKey rsaPublicKey) throws Exception{
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
		
		int maxBlock = rsaPublicKey.getModulus().bitLength()/8;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int offset = 0;
		byte[] buff;
		int i = 0;
		while(datas.length > offset){
			if(datas.length-offset > maxBlock){
                buff = cipher.doFinal(datas, offset, maxBlock);
            }else{
                buff = cipher.doFinal(datas, offset, datas.length-offset);
            }
			byteArrayOutputStream.write(buff, 0, buff.length);
            i++;
            offset = i * maxBlock;
		}
		byte[] resultDatas = byteArrayOutputStream.toByteArray();
		return resultDatas;
	}
	
	public static void main(String[] args) throws Exception {
		//密钥对生产：http://web.chacuo.net/netrsakeypair
		String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANaUmncbkw7F8FiHtf7ZGOl1CRPkPo4UpqkKzHBViYs61iATv1xuyhLodpAxRHMtwlqiyN81j04YTuQlkp/ct7O/t1U7/SET0EIQkh4iCvCjrJb4rKYc3RxpXs1LAlHDWjvcussakFjKvjixakUDpW5kNwSGsCwid0MuKi2SeIXvAgMBAAECgYAzeuVwC+qFEdHuu2hbYuRKJizuW8TyO1wclB2mGa9s+smiWnp/h4jtbl37jAZbB7BoJEPC4UunNce/jpAV0FM12RKd5PSymRswUHPkbSmvYDF/DUspNp7ANSuTKHrDmvJUh6Fi+JIqed+EGI4JYKhoNPvA5dKHYgESjkgep17goQJBAPKsyMc14BHdNBpfMjgNoDCP6tuud7ZnDhgFl8c6jNTT40JR+IfUoVDaZsPcu4j9zzeh1Z6RCDZRGYQoHgPksxECQQDiXOcnN9WIu/t81JYXYa7rSmd0u4PVt0Bab/MRSZVUBu7BW2mrcmo3zUlSYmhNNy5qcDFW8glCpbS0oQ3rt6j/AkBdBvV0wGdkEbsefO3AZSDKXPE4Q5pqlvjlJoe3fO9tysgsrUWrtEwetYp9fMl7S2i7N9OvFIPkZpR0f/h5n5sxAkAMh/AT77dWdnGZm3sjmDjCtb+8apUFw7tARmpCTryBhlC5S4IyB4dxx1+DvAbiH2fmZIeWtJdmpR230iKfJe+pAkEA2IhfdTOLb9z8dhrf0klhwxwoUsFEilAz54GpMJA+xqrPCH2nOcVDyTg1L+NnKDH0UleuwnJNVSytijQFB5ZlgA==";
		String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWlJp3G5MOxfBYh7X+2RjpdQkT5D6OFKapCsxwVYmLOtYgE79cbsoS6HaQMURzLcJaosjfNY9OGE7kJZKf3Lezv7dVO/0hE9BCEJIeIgrwo6yW+KymHN0caV7NSwJRw1o73LrLGpBYyr44sWpFA6VuZDcEhrAsIndDLiotkniF7wIDAQAB";
	
		String message = "rsa";
		
		RSAPrivateKey rsaPrivateKey = getRSAPrivateKey(priKey);
		RSAPublicKey rsaPublicKey = getRSAPublicKey(pubKey);
		
		byte[] bytes1 = publicEncrypt(message, rsaPublicKey);
		byte[] datas1 = privateDecrypt(bytes1, rsaPrivateKey);
		System.out.println(new String(datas1, ENCODING));
		
		byte[] bytes2 = privateEncrypt(message, rsaPrivateKey);
		byte[] datas2 = publicDecrypt(bytes2, rsaPublicKey);
		System.out.println(new String(datas2, ENCODING));
	}

}
