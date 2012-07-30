package com.survey.tools.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class ValidateUtils {
	
	public static String[] NOT_ALLOW_SQL_KEYS = new String[]{"insert","delete","update"}; 
	
	public static boolean isEmail(String email) {
		return  EmailValidator.getInstance().isValid(email);
	}
	
	/**
	 * 验证sql中是否包含不允许的关键字
	 */
	
	public static boolean isValidationSql(String sql){
		boolean flag=false;
		if(StringUtils.isNotEmpty(sql)){
			sql=StringUtils.trim(sql);
			String[] keys=sql.split(" ");
			String key=StringUtils.trim(keys[0].toLowerCase());
			for(String noAllowSql:NOT_ALLOW_SQL_KEYS){
				if(key.equalsIgnoreCase(noAllowSql)){
					return false;
				}else{
					flag=true;
				}
			}
		}
		return flag;
		
	}
	
}
