package edu.buaa.nlp.util;

import java.io.UnsupportedEncodingException;

public class EncodeUtil {

	public static String toEncoding(String text, String src, String dest) throws UnsupportedEncodingException{
		return new String(text.getBytes(src), dest);
	}
	
	public static String toUtf8(String text, String src) throws UnsupportedEncodingException{
		return toEncoding(text, src, "utf-8");
	}
	
	public static String fromEncoding(String text, String src) throws UnsupportedEncodingException{
		return new String(text.getBytes(src));
	}
}
