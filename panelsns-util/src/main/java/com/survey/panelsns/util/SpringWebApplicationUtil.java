package com.survey.panelsns.util;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.MessageSource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 加载web上下文信息
 * 
 */
public class SpringWebApplicationUtil{

	private  static WebApplicationContext context;
	
	static {
		context=ContextLoader.getCurrentWebApplicationContext(); 
	}
	
	public static WebApplicationContext getWebApplicationContext(){
		if(SpringWebApplicationUtil.context==null){
			SpringWebApplicationUtil.context=ContextLoader.getCurrentWebApplicationContext(); 
			if(SpringWebApplicationUtil.context==null){
				throw new RuntimeException("the WebApplicationContext is not init!");
			}
		}
		return SpringWebApplicationUtil.context;
	}
	
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException{
		 return SpringWebApplicationUtil.getWebApplicationContext().getBean(name,requiredType);  
	}
	
	
	public static <T> T getBean(Class<T> requiredType) throws BeansException{
		 return SpringWebApplicationUtil.getWebApplicationContext().getBean(requiredType);  
	}
	
	
	public static boolean containsBean(String name) {  
        return SpringWebApplicationUtil.getWebApplicationContext().containsBean(name);  
    }  
          
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {  
         return SpringWebApplicationUtil.getWebApplicationContext().isSingleton(name);  
    }  
          
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {  
        return SpringWebApplicationUtil.getWebApplicationContext().getType(name);  
    }  
   
    public static  MessageSource getMessageSource(){
	    return SpringWebApplicationUtil.getBean(MessageSource.class);
    }
    
    public static String getLocalMessage(String code){
    	Locale locale=Locale.getDefault();
    	return SpringWebApplicationUtil.getMessageSource().getMessage(code, null, locale);
    }
    
    public static String getLocalMessage(String code,Object[] args){
    	Locale locale=Locale.getDefault();
    	return SpringWebApplicationUtil.getMessageSource().getMessage(code, args, locale);
    }
    
    public static String getMessage(String code,Object[] args,Locale locale){
    	return SpringWebApplicationUtil.getMessageSource().getMessage(code, args, locale);
    }
	

}
