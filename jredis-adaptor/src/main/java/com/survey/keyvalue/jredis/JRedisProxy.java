package com.survey.keyvalue.jredis;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

public class JRedisProxy implements JRedis {
	
	private static final Logger logger=Logger.getLogger("redis");
	
    /******** 使用连接池的初始化方式 *********/
    private ShardingAlgorithm shardingAlgo;

    public JRedisProxy(ShardingAlgorithm algo) {
        this.shardingAlgo = algo;
    }

    public String currentPoolUsage() {
        ShardedRedisPool[] allPools = getAllPools();
        StringBuilder sb = new StringBuilder();
        for (ShardedRedisPool pool : allPools) {
            String shardName = pool.getShardName();
            int numActive = pool.getNumActive();
            int numIdle = pool.getNumIdle();
            sb.append(shardName).append("{active=").append(numActive).append(",idle=").append(numIdle).append("}\r\n");
        }
        return sb.toString();
    }

    private ShardedRedisPool[] getAllPools() {
        return shardingAlgo.getAllPools();
    }

    private ShardedRedisPool getPool(byte[] key) {
        return shardingAlgo.shard(key);
    }

    public void disconnect() throws IOException {
        for (ShardedRedisPool pool : getAllPools()) {
            pool.destroy();
        }
    }

    private boolean convertStateReply(String reply) {
        return "OK".equals(reply);
    }

    private boolean convertStateReply(Number reply) {
        return 0 != reply.intValue();
    }

    @Override
    public boolean del(byte[]... keys) {
        if (keys == null || keys.length == 0)
            throw new IllegalArgumentException("null input when jRedisProxy del keys");
        ShardedRedisPool pool = getPool(keys[0]);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return convertStateReply(jedis.del(keys));
        } catch (Exception ex) {
            broken = true;
            logger.error("del"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long append(byte[] key, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.append(key, value);
        } catch (Exception ex) {
            broken = true;
            logger.error("append"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long decr(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.decr(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("decr"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long decrBy(byte[] key, long integer) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.decrBy(key, integer);
        } catch (Exception ex) {
            broken = true;
            logger.error("decrBy"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Boolean exists(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.exists(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("exists"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long expire(byte[] key, int seconds) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.expire(key, seconds);
        } catch (Exception ex) {
            broken = true;
            logger.error("expire"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long expireAt(byte[] key, long unixTime) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.expireAt(key, unixTime);
        } catch (Exception ex) {
            broken = true;
            logger.error("expireAt"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public byte[] get(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.get(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("get"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public List<byte[]> mget(byte[]... keys) {
        int len = keys.length;
        List<byte[]> res = new ArrayList<byte[]>();
        for (int i = 0; i < len; i++) {
            byte[] bytes = this.get(keys[i]);
            if (bytes != null) {
                res.add(bytes);
            }
        }
        return res;
    }

    @Override
    public byte[] getSet(byte[] key, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.getSet(key, value);
        } catch (Exception ex) {
            broken = true;
            logger.error("getSet"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public boolean hdel(byte[] key, byte[] field) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return convertStateReply(jedis.hdel(key, field));
        } catch (Exception ex) {
            broken = true;
            logger.error("hdel"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Boolean hexists(byte[] key, byte[] field) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hexists(key, field);
        } catch (Exception ex) {
            broken = true;
            logger.error("hexists"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public byte[] hget(byte[] key, byte[] field) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hget(key, field);
        } catch (Exception ex) {
            broken = true;
            logger.error("hget"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Map<byte[], byte[]> hgetAll(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hgetAll(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("hgetAll"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long hincrBy(byte[] key, byte[] field, long value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hincrBy(key, field, value);
        } catch (Exception ex) {
            broken = true;
            logger.error("hincrBy"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<byte[]> hkeys(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hkeys(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("hkeys"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long hlen(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hlen(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("hlen"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hmget(key, fields);
        } catch (Exception ex) {
            broken = true;
            logger.error("hmget"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hmset(key, hash);
        } catch (Exception ex) {
            broken = true;
            logger.error("hmset"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public boolean hset(byte[] key, byte[] field, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            jedis.hset(key, field, value);
            return true;
        } catch (Exception ex) {
            broken = true;
            logger.error("hset"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hsetnx(key, field, value);
        } catch (Exception ex) {
            broken = true;
            logger.error("hsetnx"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Collection<byte[]> hvals(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.hvals(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("hvals"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long incr(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.incr(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("incr"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long incrBy(byte[] key, long integer) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.incrBy(key, integer);
        } catch (Exception ex) {
            broken = true;
            logger.error("incrBy"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public byte[] lindex(byte[] key, int index) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.lindex(key, index);
        } catch (Exception ex) {
            broken = true;
            logger.error("lindex"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.linsert(key, where, pivot, value);
        } catch (Exception ex) {
            broken = true;
            logger.error("linsert"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long llen(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.llen(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("llen"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public byte[] lpop(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.lpop(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("lpop"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long lpush(byte[] key, byte[] string) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.lpush(key, string);
        } catch (Exception ex) {
            broken = true;
            logger.error("lpush"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<String> keys(String pattern) {
        ShardedRedisPool pool = getAllPools()[0];
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.keys(pattern);
        } catch (Exception ex) {
            broken = true;
            logger.error("keys"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public List<byte[]> lrange(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.lrange(key, start, end);
        } catch (Exception ex) {
            broken = true;
            logger.error("lrange"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long lrem(byte[] key, int count, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.lrem(key, count, value);
        } catch (Exception ex) {
            broken = true;
            logger.error("lrem"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public String lset(byte[] key, int index, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.lset(key, index, value);
        } catch (Exception ex) {
            broken = true;
            logger.error("lset"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public String ltrim(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.ltrim(key, start, end);
        } catch (Exception ex) {
            broken = true;
            logger.error("ltrim"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public byte[] rpop(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.rpop(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("rpop"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long rpush(byte[] key, byte[] string) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.rpush(key, string);
        } catch (Exception ex) {
            broken = true;
            logger.error("rpush"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long sadd(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.sadd(key, member);
        } catch (Exception ex) {
            broken = true;
            logger.error("sadd"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long scard(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.scard(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("scard"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return convertStateReply(jedis.set(key, value));
        } catch (Exception ex) {
            broken = true;
            logger.error("set"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public String setex(byte[] key, int seconds, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.setex(key, seconds, value);
        } catch (Exception ex) {
            broken = true;
            logger.error("setex"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Boolean setnx(byte[] key, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.setnx(key, value).intValue() == 1;
        } catch (Exception ex) {
            broken = true;
            logger.error("setnx"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Boolean sismember(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.sismember(key, member);
        } catch (Exception ex) {
            broken = true;
            logger.error("sismember"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<byte[]> smembers(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.smembers(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("smembers"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public List<byte[]> sort(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.sort(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("sort"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.sort(key, sortingParameters);
        } catch (Exception ex) {
            broken = true;
            logger.error("sort"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public byte[] spop(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.spop(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("spop"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public byte[] srandmember(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.srandmember(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("srandmember"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long srem(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.srem(key, member);
        } catch (Exception ex) {
            broken = true;
            logger.error("srem"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public byte[] substr(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.substr(key, start, end);
        } catch (Exception ex) {
            broken = true;
            logger.error("substr"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long ttl(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.ttl(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("ttl"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public String type(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.type(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("type"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long zadd(byte[] key, double score, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zadd(key, score, member);
        } catch (Exception ex) {
            broken = true;
            logger.error("zadd"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long zcard(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zcard(key);
        } catch (Exception ex) {
            broken = true;
            logger.error("zcard"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long zcount(byte[] key, double min, double max) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zcount(key, min, max);
        } catch (Exception ex) {
            broken = true;
            logger.error("zcount"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zincrby(key, score, member);
        } catch (Exception ex) {
            broken = true;
            logger.error("zincrby"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<byte[]> zrange(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrange(key, start, end);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrange"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrangeByScore"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrangeByScore"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrangeByScoreWithScores(key, min, max);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrangeByScoreWithScores"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrangeByScoreWithScores"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrangeWithScores(key, start, end);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrangeWithScores"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long zrank(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrank(key, member);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrank"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long zrem(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrem(key, member);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrem"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long zremrangeByRank(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zremrangeByRank(key, start, end);
        } catch (Exception ex) {
            broken = true;
            logger.error("zremrangeByRank"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long zremrangeByScore(byte[] key, double start, double end) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zremrangeByScore(key, start, end);
        } catch (Exception ex) {
            broken = true;
            logger.error("zremrangeByScore"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<byte[]> zrevrange(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrevrange(key, start, end);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrevrange"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrevrangeWithScores(key, start, end);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrevrangeWithScores"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Long zrevrank(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zrevrank(key, member);
        } catch (Exception ex) {
            broken = true;
            logger.error("zrevrank"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Double zscore(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.zscore(key, member);
        } catch (Exception ex) {
            broken = true;
            logger.error("zscore"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public Set<byte[]> srandomMembers(byte[] key, int count) {
        long len = this.scard(key);
        if (len == 0) {
            return null;
        }

        if (count >= len) {
            return this.smembers(key);
        }

        if (count / (double) len > 0.7) {
            return randomNeedAllMembers(key, count);
        }

        return randomMembers(key, count);
    }

    private Set<byte[]> randomMembers(byte[] key, int count) {
        Set<byte[]> members = new HashSet<byte[]>(count * 4 / 3);
        for (int i = 0; i < count;) {
            byte[] member = this.srandmember(key);
            if (!contains(members, member) && members.add(member)) {
                i++;
            }
        }
        return members;
    }

    private boolean contains(Collection<byte[]> members, byte[] member) {
        for (byte[] m : members) {
            if (Arrays.equals(m, member)) {
                return true;
            }
        }
        return false;
    }

    private Set<byte[]> randomNeedAllMembers(byte[] key, int count) {
        Set<byte[]> members = this.smembers(key);
        Set<byte[]> res = new HashSet<byte[]>();
        for (byte[] bs : members) {
            res.add(bs);
            if (res.size() >= count) {
                return res;
            }
        }
        return res;
    }

    @Override
    public Set<byte[]> sinter(byte[]... keys) {
        ShardedRedisPool pool = getPool(keys[0]);
        Jedis jedis = pool.getResource();
        try {
            return jedis.sinter(keys);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public Transaction multi(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return jedis.multi();
        } catch (Exception ex) {
            broken = true;
            logger.error("multi"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public boolean unwatch(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return convertStateReply(jedis.unwatch());
        } catch (Exception ex) {
            broken = true;
            logger.error("unwatch"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    @Override
    public boolean watch(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        Jedis jedis = pool.getResource();
        boolean broken = false;
        try {
            return convertStateReply(jedis.watch(key));
        } catch (Exception ex) {
            broken = true;
            logger.error("watch"+"\t"+pool.getShardName()+"\t"+ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            if (broken) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

}
