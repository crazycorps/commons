package com.survey.panelsns.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 加载spring上下文信息
 * 
 */
@Component
public class SpringApplicationUtil implements ApplicationContextAware{

	private  ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context=applicationContext;
	}  
	
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException{
		 return this.context.getBean(name,requiredType);  
	}
	
	public <T> T getBean(Class<T> requiredType) throws BeansException{
		 return this.context.getBean(requiredType);  
	}
	
	public boolean containsBean(String name) {  
        return this.context.containsBean(name);  
    }  
          
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {  
         return this.context.isSingleton(name);  
    }  
          
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {  
        return this.context.getType(name);  
    }

}
