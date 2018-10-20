package org.xzh.springboot.encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL编码解码
 *
 */
public class URLUtils {
	
	private static final String ENCODING = "UTF-8";

	/**
	 * 编码
	 * 
	 * @param src
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encoder(String src) throws UnsupportedEncodingException{
		String des = URLEncoder.encode(src, ENCODING);
		return des;
	}
	
	/**
	 * 解码
	 * 
	 * @param src
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decoder(String src) throws UnsupportedEncodingException{
		String des = URLDecoder.decode(src, ENCODING);
		return des;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = "http://baidu.com/对的";
		String encoder = encoder(url);
		System.out.println(encoder);
		
		String decoder = decoder(encoder);
		System.out.println(decoder);
	}
}
