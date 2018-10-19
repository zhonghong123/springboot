package org.xzh.springboot.encrypt;

import java.io.UnsupportedEncodingException;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Base64;

/**
 * base64编码解码。
 * 在jdk1.8之前，用sun.misc.Base64Encoder和sun.misc.Base64Decoder，能力较差。
 * 在jdk1.8之前，通常用Apache的commons-codec.jar中Base64来做编码。
 * 
 * 在jdk1.8，jdk提供了java.util.Base64.Decoder和java.util.Base64.Encoder做编码
 * 结论：如果是jdk1.8之前，用Apache来做编码；
 * 		如果是jdk1.8，用jdk提供包来做编码
 *
 */
public class Base64Utils {
	
	public static String encode(byte[] bytes){
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(bytes);
	}
	
	public static byte[] decode(String data){
		Decoder decoder = Base64.getDecoder();
		return decoder.decode(data);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String data = "base64";
		
		String base = encode(data.getBytes("UTF-8"));
		System.out.println(base);
		
		System.out.println(new String(decode(base), "UTF-8"));
	}
}
