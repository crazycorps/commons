package com.survey.tools.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public final class Base64Utils {

	public static final String DEFAULT_CHARSET = "UTF-8";

	private static final Base64 base64 = new Base64();

	public static String encode(String str) {
		return encode(str,DEFAULT_CHARSET);
	}
	
	public static String encode(String str,String charsetName) {
		try {
			byte[] enbytes = base64.encode(str.getBytes(charsetName));
			return new String(enbytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("UnsupportedEncodingException");
		}
	}
	
	public static String decode(String str) {
		return decode(str,DEFAULT_CHARSET);
	}
	
	public static String decode(String encodeStr,String charsetName){
		try {
			byte[] debytes = base64.decode(encodeStr.getBytes(charsetName)); 
			return new String(debytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("UnsupportedEncodingException");
		} 
	}
	
	public static void main(String args[]){
		String str="http://detail.tmall.com/item.htm?id=12443777799&ali_trackid=2:mm_31430021_0_0:1339319879_4k1_91969269";
		String encodeStr=encode(str);
		System.out.println(encodeStr);
		String decodeStr=decode("aHR0cDovL2RldGFpbC50bWFsbC5jb20vaXRlbS5odG0/aWQ9MTI0NDM3Nzc3OTkmYWxpX3RyYWNraWQ9MjptbV8zMTQzMDAyMV8wXzA6MTMzOTMxOTg3OV80azFfOTE5NjkyNjk=");
		System.out.println(decodeStr);
		System.out.println(str.equals(decodeStr));
	}
	
}
