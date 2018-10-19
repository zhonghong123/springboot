package org.xzh.springboot.encrypt;

import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * SHA1withRSA做签名，先进行SHA1签名，再对签名结果在做RSA加密。
 * 类似还有MD5withRSA.
 *
 */
public class SHA1withRSAUtils {

	private static final String KEY_ALGORITHM = "SHA1withRSA";
	//private static final String KEY_ALGORITHM = "MD5withRSA";
	
	private static final String ENCODING = "UTF-8";
	
	/**
	 * 签名
	 * 
	 * @param data
	 * @param rsaPrivateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(String data, RSAPrivateKey rsaPrivateKey) throws Exception{
		//获取Signature对象
		Signature sign = Signature.getInstance(KEY_ALGORITHM);
		//初始化sign对象
		sign.initSign(rsaPrivateKey);
		//签名
		sign.update(data.getBytes(ENCODING));
		byte[] bytes = sign.sign();
		
		return bytes;
	}
	
	public static boolean verify(byte[] bytes, String data, RSAPublicKey rsaPublicKey) throws Exception{
		//获取Signature对象
		Signature sign = Signature.getInstance(KEY_ALGORITHM);
		//初始化sign对象
		sign.initVerify(rsaPublicKey);
		//验签
		sign.update(data.getBytes(ENCODING));
		return sign.verify(bytes);
	}
	
	public static void main(String[] args) throws Exception {
		//密钥对生产：http://web.chacuo.net/netrsakeypair
		String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANaUmncbkw7F8FiHtf7ZGOl1CRPkPo4UpqkKzHBViYs61iATv1xuyhLodpAxRHMtwlqiyN81j04YTuQlkp/ct7O/t1U7/SET0EIQkh4iCvCjrJb4rKYc3RxpXs1LAlHDWjvcussakFjKvjixakUDpW5kNwSGsCwid0MuKi2SeIXvAgMBAAECgYAzeuVwC+qFEdHuu2hbYuRKJizuW8TyO1wclB2mGa9s+smiWnp/h4jtbl37jAZbB7BoJEPC4UunNce/jpAV0FM12RKd5PSymRswUHPkbSmvYDF/DUspNp7ANSuTKHrDmvJUh6Fi+JIqed+EGI4JYKhoNPvA5dKHYgESjkgep17goQJBAPKsyMc14BHdNBpfMjgNoDCP6tuud7ZnDhgFl8c6jNTT40JR+IfUoVDaZsPcu4j9zzeh1Z6RCDZRGYQoHgPksxECQQDiXOcnN9WIu/t81JYXYa7rSmd0u4PVt0Bab/MRSZVUBu7BW2mrcmo3zUlSYmhNNy5qcDFW8glCpbS0oQ3rt6j/AkBdBvV0wGdkEbsefO3AZSDKXPE4Q5pqlvjlJoe3fO9tysgsrUWrtEwetYp9fMl7S2i7N9OvFIPkZpR0f/h5n5sxAkAMh/AT77dWdnGZm3sjmDjCtb+8apUFw7tARmpCTryBhlC5S4IyB4dxx1+DvAbiH2fmZIeWtJdmpR230iKfJe+pAkEA2IhfdTOLb9z8dhrf0klhwxwoUsFEilAz54GpMJA+xqrPCH2nOcVDyTg1L+NnKDH0UleuwnJNVSytijQFB5ZlgA==";
		String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWlJp3G5MOxfBYh7X+2RjpdQkT5D6OFKapCsxwVYmLOtYgE79cbsoS6HaQMURzLcJaosjfNY9OGE7kJZKf3Lezv7dVO/0hE9BCEJIeIgrwo6yW+KymHN0caV7NSwJRw1o73LrLGpBYyr44sWpFA6VuZDcEhrAsIndDLiotkniF7wIDAQAB";
	
		String message = "rsa";
		
		RSAPrivateKey rsaPrivateKey = RSAUtils.getRSAPrivateKey(priKey);
		RSAPublicKey rsaPublicKey = RSAUtils.getRSAPublicKey(pubKey);
		
		byte[] bytes = sign(message, rsaPrivateKey);
		boolean verify = verify(bytes, message, rsaPublicKey);
		System.out.println(verify);
	}
}
