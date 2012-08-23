package com.survey.keyvalue.config;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class TestRedisDbConfig {

	private static final Logger logger=Logger.getLogger("redis");
	
	@Test
	public void testLoadConfig(){
		String confPath="redis_db_config.xml";
		DbConfig config=new DbConfig(confPath);
		Assert.assertTrue(config.getMasterDbInfoMap().size()==config.getSlaveDbInfoMap().size());
	}
			
}
