package com.jian.tools.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {

	public static String convertStreamToString(InputStream input,String encode) throws Exception{
		if(input==null){
			return null;
		}
		InputStreamReader reader = new InputStreamReader(input, encode);
		BufferedReader bufReader = new BufferedReader(reader);
		String tmp = null, html = "";
		while ((tmp = bufReader.readLine()) != null) {
			html += tmp;
		}
		if (input != null) {
			input.close();
		}
		return html;
	}
}
