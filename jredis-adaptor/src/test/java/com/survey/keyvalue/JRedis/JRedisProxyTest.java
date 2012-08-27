package com.survey.keyvalue.JRedis;

import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.survey.keyvalue.redis.Redis;

public class JRedisProxyTest {

    private Redis jRedis;
    
    @Before
    public void setUp() throws Exception {
        //jRedis = JRedisFactory.createJRedis("masters=localhost:6379", "slaves=localhost:6379");
    }

    @Test
    public void testJRedisProxy() {
        fail("Not yet implemented");
    }
    
    private String host = "localhost";
    private int port = 6379;
   
    @Test
    public void testTimeOut() throws InterruptedException {
        Jedis jedis = new Jedis(host, port);
        int count = 1000;
        while(count -- > 0){
            TimeUnit.MICROSECONDS.sleep(1);
            long start = System.nanoTime();
            jedis.set(("abc"+count).getBytes(), "bar".getBytes());
            System.out.println(System.nanoTime() - start);
        }
    }
    
    @Test
    public void testCreateConnection() throws InterruptedException{
        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10; i++) {
            new Jedis(host, port);
//        }
        System.err.println(System.currentTimeMillis() - start);
        
        int count = 1000;
        final CountDownLatch latch = new CountDownLatch(count);
        ExecutorService exec = Executors.newFixedThreadPool(count);
        start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        new Jedis(host, port);
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.out.println(System.currentTimeMillis() - start);
        
    }

    @Test
    public void testJRedisProxyString() {
        fail("Not yet implemented");
    }

    @Test
    public void testJRedisProxyStringInt() {
        fail("Not yet implemented");
    }

    @Test
    public void testJRedisProxyStringIntString() {
        fail("Not yet implemented");
    }

    @Test
    public void testJRedisProxyStringIntStringInt() {
        fail("Not yet implemented");
    }

    @Test
    public void testDel() {
        fail("Not yet implemented");
    }

    @Test
    public void testAppend() {
        fail("Not yet implemented");
    }

    @Test
    public void testDecr() {
        fail("Not yet implemented");
    }

    @Test
    public void testDecrBy() {
        fail("Not yet implemented");
    }

    @Test
    public void testExists() {
        fail("Not yet implemented");
    }

    @Test
    public void testExpire() {
        fail("Not yet implemented");
    }

    @Test
    public void testExpireAt() {
        fail("Not yet implemented");
    }

    @Test
    public void testGet() {
        fail("Not yet implemented");
    }

    @Test
    public void testMget() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetSet() {
        fail("Not yet implemented");
    }

    @Test
    public void testHdel() {
        fail("Not yet implemented");
    }

    @Test
    public void testHexists() {
        fail("Not yet implemented");
    }

    @Test
    public void testHget() {
        byte[] hget = jRedis.hget("googlecode".getBytes(), "activity".getBytes());
        System.out.println(hget);
    }

    @Test
    public void testHgetAll() {
        fail("Not yet implemented");
    }

    @Test
    public void testHincrBy() {
        fail("Not yet implemented");
    }

    @Test
    public void testHkeys() {
        fail("Not yet implemented");
    }

    @Test
    public void testHlen() {
        fail("Not yet implemented");
    }

    @Test
    public void testHmget() {
        fail("Not yet implemented");
    }

    @Test
    public void testHmset() {
        fail("Not yet implemented");
    }

    @Test
    public void testHset() {
        fail("Not yet implemented");
    }

    @Test
    public void testHsetnx() {
        fail("Not yet implemented");
    }

    @Test
    public void testHvals() {
        fail("Not yet implemented");
    }

    @Test
    public void testIncr() {
        fail("Not yet implemented");
    }

    @Test
    public void testIncrBy() {
        fail("Not yet implemented");
    }

    @Test
    public void testLindex() {
        fail("Not yet implemented");
    }

    @Test
    public void testLinsert() {
        fail("Not yet implemented");
    }

    @Test
    public void testLlen() {
        fail("Not yet implemented");
    }

    @Test
    public void testLpop() {
        fail("Not yet implemented");
    }

    @Test
    public void testLpush() {
        fail("Not yet implemented");
    }

    @Test
    public void testLrange() {
        fail("Not yet implemented");
    }

    @Test
    public void testLrem() {
        fail("Not yet implemented");
    }

    @Test
    public void testLset() {
        fail("Not yet implemented");
    }

    @Test
    public void testLtrim() {
        fail("Not yet implemented");
    }

    @Test
    public void testRpop() {
        fail("Not yet implemented");
    }

    @Test
    public void testRpush() {
        fail("Not yet implemented");
    }

    @Test
    public void testSadd() {
        fail("Not yet implemented");
    }

    @Test
    public void testScard() {
        fail("Not yet implemented");
    }

    @Test
    public void testSet() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetex() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetnx() {
        fail("Not yet implemented");
    }

    @Test
    public void testSismember() {
        fail("Not yet implemented");
    }

    @Test
    public void testSmembers() {
        fail("Not yet implemented");
    }

    @Test
    public void testSortByteArray() {
        fail("Not yet implemented");
    }

    @Test
    public void testSortByteArraySortingParams() {
        fail("Not yet implemented");
    }

    @Test
    public void testSpop() {
        fail("Not yet implemented");
    }

    @Test
    public void testSrandmember() {
        fail("Not yet implemented");
    }

    @Test
    public void testSrem() {
        fail("Not yet implemented");
    }

    @Test
    public void testSubstr() {
        fail("Not yet implemented");
    }

    @Test
    public void testTtl() {
        fail("Not yet implemented");
    }

    @Test
    public void testType() {
        fail("Not yet implemented");
    }

    @Test
    public void testZadd() {
        fail("Not yet implemented");
    }

    @Test
    public void testZcard() {
        fail("Not yet implemented");
    }

    @Test
    public void testZcount() {
        fail("Not yet implemented");
    }

    @Test
    public void testZincrby() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrange() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrangeByScoreByteArrayDoubleDouble() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrangeByScoreByteArrayDoubleDoubleIntInt() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrangeByScoreWithScoresByteArrayDoubleDouble() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrangeByScoreWithScoresByteArrayDoubleDoubleIntInt() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrangeWithScores() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrank() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrem() {
        fail("Not yet implemented");
    }

    @Test
    public void testZremrangeByRank() {
        fail("Not yet implemented");
    }

    @Test
    public void testZremrangeByScore() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrevrange() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrevrangeWithScores() {
        fail("Not yet implemented");
    }

    @Test
    public void testZrevrank() {
        fail("Not yet implemented");
    }

    @Test
    public void testZscore() {
        fail("Not yet implemented");
    }

}
