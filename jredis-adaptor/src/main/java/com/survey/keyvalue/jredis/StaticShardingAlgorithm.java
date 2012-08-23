/*
 * Copyright (c) 2011 Yoyo Systems. All rights reserved.
 *
 * $Id$
 */
package com.survey.keyvalue.jredis;

import org.apache.commons.pool.impl.GenericObjectPool.Config;

public class StaticShardingAlgorithm extends ShardingAlgorithm {
    private int numPools;
    private ShardedRedisPool[] pools;

    public StaticShardingAlgorithm(ShardedRedisPool[] pools) {
        super(pools);
        this.pools = pools;
        numPools = pools.length;
    }

    protected ShardedRedisPool shard(int cage) {
        return pools[cage % numPools];
    }

    public static void main(String[] args) throws Exception {
        if (args.length <= 0) {
            throw new IllegalArgumentException("No valid redis backend specified");
        }
        Config config = new Config();

        ShardedRedisPool[] pools = new ShardedRedisPool[args.length];
        int idx = 0;
        for (String arg : args) {
            String[] splitted = arg.split(":");
            if (splitted.length != 2) {
                throw new IllegalArgumentException(arg + " is not a valid redis server address");
            }
            RedisShardInfo info = new RedisShardInfo(arg, splitted[0], Integer.parseInt(splitted[1]), splitted[0],
                    Integer.parseInt(splitted[1]));
            pools[idx++] = new ShardedRedisPool(config, info);
        }

        RedisClient client = new RedisClient(new StaticShardingAlgorithm(pools));
        byte[] k1 = StorageKey.getByteArray(100, (byte) 1);
        byte[] k2 = StorageKey.getByteArray(1021, (byte) 2);
        byte[] k3 = StorageKey.getByteArray(1023, (byte) 3);
        byte[] k4 = StorageKey.getByteArray(1024, (byte) 4);
        byte[] k5 = StorageKey.getByteArray(1124, (byte) 5);

        client.set(k1, "value1".getBytes("UTF-8"));
        client.set(k2, "value2".getBytes("UTF-8"));
        client.set(k3, "value3".getBytes("UTF-8"));
        client.set(k4, "value4".getBytes("UTF-8"));
        client.set(k5, "value5".getBytes("UTF-8"));

        client.disconnect();
    }
}
