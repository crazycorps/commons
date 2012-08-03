package com.survey.dao.common;

import org.apache.commons.lang3.StringUtils;

public enum OrderType {
	ASC,DESC;
	
	public static OrderType instanceOf(String type){
		if(StringUtils.isEmpty(type)){
			return null;
		}
		String tempType=type.toUpperCase();
		for(OrderType ot:OrderType.values()){
			if(ot.name().equals(tempType)){
				return ot;
			}
		}
		return null;
	}
}
