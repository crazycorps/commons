package com.survey.tools.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletUtils {

	/**
	 * 默认不创建session
	 * @param request
	 * @return
	 */
	public static HttpSession getSession(HttpServletRequest request){
		return ServletUtils.getSession(request, true);
	}
	
	public static HttpSession getSession(HttpServletRequest request,boolean isCreateNew){
		return request.getSession(isCreateNew);
	}
	
}
