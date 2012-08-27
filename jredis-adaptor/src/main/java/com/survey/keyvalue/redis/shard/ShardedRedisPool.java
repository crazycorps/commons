package com.survey.keyvalue.redis.shard;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

public class ShardedRedisPool {

    private final GenericObjectPool internalPool;
    
    private RedisShardInfo shard;

    public String getShardName() {
        return shard.getShardName();
    }

    public ShardedRedisPool(final GenericObjectPool.Config poolConfig,
            RedisShardInfo shard) {
        this.internalPool = new GenericObjectPool(new ShardedRedisFactory(shard), poolConfig);
        this.shard = shard;
    }

    public Jedis getResource() {
        try {
            return (Jedis) internalPool.borrowObject();
        } catch (Exception e) {
            throw new JedisConnectionException(
                    "Could not get a resource from the pool", e);
        }
    }

    public void returnResource(final Jedis resource) {
        try {
            internalPool.returnObject(resource);
        } catch (Exception e) {
            throw new JedisException(
                    "Could not return the resource to the pool", e);
        }
    }

    public void returnBrokenResource(final Jedis resource) {
        try {
            internalPool.invalidateObject(resource);
        } catch (Exception e) {
            throw new JedisException(
                    "Could not return the resource to the pool", e);
        }
    }

    public void destroy() {
        try {
            internalPool.close();
        } catch (Exception e) {
            throw new JedisException("Could not destroy the pool", e);
        }
    }
    
    private static class ShardedRedisFactory extends BasePoolableObjectFactory {
        private RedisShardInfo shardInfo;

        public ShardedRedisFactory(RedisShardInfo shard) {
            this.shardInfo = shard;
        }

        public Object makeObject() throws Exception {
            return new Jedis(shardInfo.getMasterHost(), shardInfo.getMasterPort(), shardInfo.getTimeout());
        }

        public void destroyObject(final Object obj) throws Exception {
            if ((obj != null) && (obj instanceof Jedis)) {
                Jedis jedis = (Jedis) obj;
                try {
                    jedis.quit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    jedis.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean validateObject(final Object obj) {
            try {
                Jedis jedis = (Jedis) obj;
                return jedis.ping().equals("PONG");
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }
    
    public int getNumActive(){
        return internalPool.getNumActive();
    }
    
    public int getNumIdle(){
        return internalPool.getNumIdle();
    }
    
    
}
