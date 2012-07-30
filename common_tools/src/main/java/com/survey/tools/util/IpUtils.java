package com.survey.tools.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;


public class IpUtils {

	public static String getIp(HttpServletRequest request){
		String ip=request.getHeader("X-Real-IP");
		if(StringUtils.isEmpty(ip)){
			ip=request.getHeader("X-Forwarded-For");
		}
		if(StringUtils.isEmpty(ip)){
			ip=request.getRemoteAddr();
		}
		return ip;
	}
}
