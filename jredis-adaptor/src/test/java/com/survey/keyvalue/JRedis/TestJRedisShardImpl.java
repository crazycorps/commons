package com.survey.keyvalue.JRedis;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.survey.keyvalue.config.DbConfig;
import com.survey.keyvalue.redis.JRedis;
import com.survey.keyvalue.redis.JRedisFactory;
import com.survey.keyvalue.redis.StorageKey;

public class TestJRedisShardImpl {

    private JRedis jRedis;
    
    @Before
    public void setUp() throws Exception {
    	String confPath="db_config.xml";
		DbConfig config=new DbConfig(confPath);
        jRedis = JRedisFactory.createJRedis(config);
    }
   
    @Test
    public void testSet() throws InterruptedException {
    	int count = 1000;
        while(count -- > 0){
        	byte[] key=StorageKey.getByteArray(count, (byte)1);
            TimeUnit.MICROSECONDS.sleep(1);
            long start = System.nanoTime();
            jRedis.set(key, ("bar"+count).getBytes());
            System.out.println(count+":"+(System.nanoTime() - start));
        }
    }
    
    @Test
    public void testGet() throws InterruptedException {
    	int count = 1000;
        while(count -- > 0){
        	byte[] key=StorageKey.getByteArray(count, (byte)1);
            TimeUnit.MICROSECONDS.sleep(1);
            long start = System.nanoTime();
            byte[] value=jRedis.get(key);
            System.out.println(count+":"+new String(value));
        }
    }
    
//    @Test
//    public void testCreateConnection() throws InterruptedException{
//        long start = System.currentTimeMillis();
////        for (int i = 0; i < 10; i++) {
//            new Jedis(host, port);
////        }
//        System.err.println(System.currentTimeMillis() - start);
//        
//        int count = 1000;
//        final CountDownLatch latch = new CountDownLatch(count);
//        ExecutorService exec = Executors.newFixedThreadPool(count);
//        start = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            exec.execute(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = 0; j < 10; j++) {
//                        new Jedis(host, port);
//                    }
//                    latch.countDown();
//                }
//            });
//        }
//        latch.await();
//        System.out.println(System.currentTimeMillis() - start);
//        
//    }


}
