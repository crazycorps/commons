package com.survey.keyvalue.JRedis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.survey.keyvalue.redis.StorageKey;

public class StorageKeyTest {
    private long start;
    private String printName;
    private int executeTimes = 1000000;

    @Before
    public void setUp() throws Exception {
        start = System.currentTimeMillis();
    }

    @Test
    public void testGetByteArrayLongByte() {
        printName = "get bytes by user id";
        for (int i = 0; i < executeTimes; i++) {
            StorageKey.getByteArray(10005, (byte)24);
        }
    }

    @Test
    public void testGetByteArrayStringByte() {
        printName = "get bytes by platform id";
        for (int i = 0; i < executeTimes; i++) {
            StorageKey.getByteArray("hoolaisagno1234", (byte)24);
        }
    }

    @Test
    public void testGetHash() {
        byte[] bytes = StorageKey.getByteArray("hoolaisango!#$", (byte)24);
        printName = "get hash by platform id";
        for (int i = 0; i < executeTimes; i++) {
            StorageKey.getHash(bytes);
        }
    }

    @Test
    public void testGetUserId() {
        byte[] bytes = StorageKey.getByteArray(10005, (byte)24);
        
        printName = "get userId id";
        for (int i = 0; i < executeTimes; i++) {
            StorageKey.getBusiId(bytes);
        }
    }

    @Test
    public void testGetPlatformId() {
        byte[] bytes = StorageKey.getByteArray("hoolaigsango!@#", (byte)24);
        
        printName = "get platform id";
        for (int i = 0; i < executeTimes; i++) {
            StorageKey.getBusiName(bytes);
        }
    }

    @After
    public void after() {
        System.out.println(printName + " : " + (System.currentTimeMillis() - start));
    }

}
