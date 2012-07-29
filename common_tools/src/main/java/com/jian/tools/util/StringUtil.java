package com.jian.tools.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public final class StringUtil {
	
	public static final String TESHUFUHAO = "[【|(*^__^*)|(^\\-^)|(^@^)|】|(\\&\\&；)|●|『|』|㊣|▲|◆|(|▍)|★|≮|(\\&bull；)|(\\&rsquo；)|(\\&mdash；)|(\\&lsquo；)|(\\&bull；)|▓|☆|♂|♀|〓|(◢◤)|(◥◣)|◇|■|◣|◢|◥|→|〖|〗|※|①]";
	
	// 默认空格
	public static final String DEFAULT_EMPTY = "";

	public static String concatString(String[] strArr,String split){
		if(strArr==null){
			return null;
		}
		StringBuffer sb=new StringBuffer();
		for(String str:strArr){
			sb.append(str);
			if(split!=null){
				sb.append(split);
			}
		}
		return sb.toString();
	}
	
	public static Map<String, String> parseHttpParams(String request_token) {
        Map<String, String> tokens = new HashMap<String, String>();
        request_token += "&";
        while (request_token.length() > 0) {
            String key_value = request_token.substring(0, request_token.indexOf("&"));
            String key = key_value.substring(0, key_value.indexOf("="));
            String value = key_value.substring(key_value.indexOf("=") + 1, key_value.length());
            tokens.put(key, value);
            request_token = request_token.substring(request_token.indexOf("&") + 1, request_token.length());
        }
        return tokens;
    }
	
	public static String replaceTeshu(String str) {
		try {
			return str.replaceAll(TESHUFUHAO, DEFAULT_EMPTY);
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}
	
	public static float parse2Float(String nub, float defaultValue) {
		if (StringUtils.isBlank(nub)) {
			return defaultValue;
		}
		nub = nub.replaceAll(",", "");
		try {
			return Float.valueOf(nub);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	/**
	 * 解码
	 * 
	 * @param s
	 * @param enc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String s, String enc) {
		try {
			return URLDecoder.decode(s, enc);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * 编码
	 * 
	 * @param s
	 * @param enc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String s, String enc) {
		try {
			return URLEncoder.encode(s, enc);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * 返回字符串的真实长度
	 * 
	 * @param str
	 * @return
	 */
	public static int realLength(String str) {
		int length = StringUtils.isBlank(str) ? 0 : str.length();
		int len = 0;
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) > 256) {
				len += 2;
			} else {
				len++;
			}
		}

		return len;
	}

	/**
	 * @param s_value
	 * @param delim
	 * @return
	 */
	public static synchronized String[] splitByStr(String s_value, String delim) {
		int pos = 0;
		String s_list[];

		if (s_value != null && delim != null) {

			ArrayList<String> list = new ArrayList<String>();

			pos = s_value.indexOf(delim);
			int len = delim.length();

			while (pos >= 0) {
				if (pos > 0)
					list.add(s_value.substring(0, pos));
				if ((pos + len) < s_value.length())
					s_value = s_value.substring(len + pos);
				else
					s_value = null;
				if (s_value != null)
					pos = s_value.indexOf(delim);
				else
					pos = -1;
			}
			if (s_value != null)
				list.add(s_value);
			s_list = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				s_list[i] = list.get(i);
			}
		} else {
			s_list = new String[0];
		}
		return s_list;
	}

	/**
	 * 编码是否有效
	 * 
	 * @param text
	 * @return
	 */
	public static boolean utf8codeCheck(String text) {
		String sign = "";
		if (text.startsWith("%e"))
			for (int i = 0, p = 0; p != -1; i++) {
				p = text.indexOf("%", p);
				if (p != -1)
					p++;
				sign += p;
			}
		return sign.equals("147-1");
	}

	/**
	 * 判断中英文。true：英文；false:中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validCode(String str) {
		int length = StringUtils.isBlank(str) ? 0 : str.length();
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) > 256) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * @author guojianjiong
	 * @param <T>
	 * @param values  需要解析的集合
	 * @param aroundSymbolBegin  集合中的值，用什么来包含开始
	 * @param aroundSymbolEnd  集合中的值，用什么来包含结束
	 * @param compartSymbol 集合中的值，用什么来分隔
	 * @param quotationMark  集合中的值，用什么来引主
	 * @return
	 */
	public static <T> String getCompartValues(T[] values,String aroundSymbolBegin,String aroundSymbolEnd,String compartSymbol,String quotationMark){
		StringBuffer ret=new StringBuffer("");
		if(values!=null){
			for(int i=0;i<values.length;i++){
				T temp=values[i];
				if(i==0){
					ret.append(aroundSymbolBegin);
				}
				if(StringUtils.isNotEmpty(quotationMark)){
					ret.append(quotationMark);
					ret.append(temp);
					ret.append(quotationMark);
				}else{
					ret.append(temp);
				}
				if(i!=values.length-1){
					ret.append(compartSymbol);
				}
				if(i==values.length-1){
					ret.append(aroundSymbolEnd);
				}
				
			}
		}
		return ret.toString();
	}
	/**
	 * 
	 * @param <T>
	 * @param aroundSymbolBegin
	 * @param aroundSymbolEnd
	 * @param compartSymbol
	 * @param values
	 * @return
	 */
	public static <T> String getCompartValues(String aroundSymbolBegin,String aroundSymbolEnd,String compartSymbol,String quotationMark,T... values){
		StringBuffer ret=new StringBuffer("");
		if(values!=null){
			for(int i=0;i<values.length;i++){
				T temp=values[i];
				if(i==0){
					ret.append(aroundSymbolBegin);
				}
				if(StringUtils.isNotEmpty(quotationMark)){
					ret.append(quotationMark);
					ret.append(temp);
					ret.append(quotationMark);
				}else{
					ret.append(temp);
				}
				if(i!=values.length-1){
					ret.append(compartSymbol);
				}
				if(i==values.length-1){
					ret.append(aroundSymbolEnd);
				}
			}
		}
		return ret.toString();
	}
	
	public static <T> boolean isNotEmptyCollection(Collection<T> coll){
		boolean flag=true;
		if(coll==null){
			flag=false;
		}else{
			if(coll.isEmpty()){
				flag=false;
			}
		}
		return flag;
	}
	
	public static <T> T[] convertCollectionToArray(List<T> array,T[] ret){
		if(array!=null&&ret!=null){
			ret=array.toArray(ret);
		}
		return ret;
	}
	
	public static <T> List<T> convertArrayToCollection(T[] array){
		List<T> ret=new ArrayList<T>();
		if(array!=null){
			for(T t:array){
				ret.add(t);
			}
		}
		return ret;
	}
	

	/**
	 * 过滤 标签
	 * 
	 * @param inputString
	 * @return
	 */
	public static String processorHtmlTags(String inputString) {
		if (StringUtils.isBlank(inputString)) {
			return "";
		}

		// 处理html标签
		inputString = inputString.replaceAll("<[^>]+>", "");
		return inputString;
	}

	public static String processorJavascript(String inputString) {
		if (StringUtils.isEmpty(inputString))
			return inputString;

		return inputString.replaceAll("<script[^>]+>", "").replaceAll(
				"</script>", "");
	}

	/**
	 * 过滤 标签
	 * 
	 * @param inputString
	 * @return
	 */
	public static String processorTags(String inputString) {
		if (StringUtils.isBlank(inputString)) {
			return "";
		}
		//
		String particular_str = "&gt;|&amp;|&nbsp;|&quot;";
		// 处理html标签
		inputString = processorHtmlTags(inputString);
		//
		inputString = inputString.replaceAll(particular_str, "");
		// 处理非正常
		inputString = inputString.replaceAll("<|>", "");
		inputString = inputString.replaceAll("\\s", "");
		return inputString;
	}
}
