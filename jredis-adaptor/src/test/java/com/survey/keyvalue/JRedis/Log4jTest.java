package com.survey.keyvalue.JRedis;

import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {

	private static final Logger logger=Logger.getLogger("redis");
	
	@Test
	public void test4j(){
		logger.info("info...");
		logger.debug("debug...");
		logger.error("error...");
	}
			
}
