package com.survey.keyvalue.redis.shard;

import java.util.Arrays;

import com.survey.keyvalue.redis.StorageKey;

public abstract class ShardingAlgorithm {
    private static final int DEFAULT_USERS_PER_CAGE = 1024;
    private int usersPerCage;
    private ShardedRedisPool[] pools;

    protected ShardingAlgorithm(ShardedRedisPool[] pools) {
        this(DEFAULT_USERS_PER_CAGE, pools);
    }

    protected ShardingAlgorithm(int usersPerCage, ShardedRedisPool[] pools) {
        this.usersPerCage = usersPerCage;
        this.pools = pools;
    }

    public ShardedRedisPool shard(byte[] key) {
        int userId = StorageKey.getHash(key);
        return shard(getCage(userId));
    }

    protected abstract ShardedRedisPool shard(int cage);

    private final int getCage(int userId) {
        assert (userId >= 0);
        return userId / usersPerCage;
    }

    protected ShardedRedisPool[] getAllPools() {
        return Arrays.copyOf(pools, pools.length);
    }
}
