package com.jian.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
// locations={"classpath:baobaotao-dao.xml","classpath:baobaotao-service.xml"}
@ContextConfiguration(locations={"classpath:application-test.xml"})  
public class BaseTest {

}
