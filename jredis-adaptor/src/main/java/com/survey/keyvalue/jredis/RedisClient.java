/*
 * Copyright (c) 2011 Yoyo Systems. All rights reserved.
 *
 * $Id$
 */
package com.survey.keyvalue.jredis;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.BinaryClient.LIST_POSITION;

@SuppressWarnings({"unused", "unchecked"})
public class RedisClient implements BinaryJedisCommands {
    private ShardingAlgorithm shardingAlgo;

    public RedisClient(ShardingAlgorithm algo) {
        this.shardingAlgo = algo;
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

    private static Method setImplMethod;
    private static Method getImplMethod;
    private static Method existsImplMethod;
    private static Method typeImplMethod;
    private static Method expireImplMethod;
    private static Method expireAtImplMethod;
    private static Method ttlImplMethod;
    private static Method getSetImplMethod;
    private static Method setnxImplMethod;
    private static Method setexImplMethod;
    private static Method decrByImplMethod;
    private static Method decrImplMethod;
    private static Method incrByImplMethod;
    private static Method incrImplMethod;
    private static Method appendImplMethod;
    private static Method substrImplMethod;
    private static Method hsetImplMethod;
    private static Method hgetImplMethod;
    private static Method hsetnxImplMethod;
    private static Method hmsetImplMethod;
    private static Method hmgetImplMethod;
    private static Method hincrByImplMethod;
    private static Method hexistsImplMethod;
    private static Method hdelImplMethod;
    private static Method hlenImplMethod;
    private static Method hkeysImplMethod;
    private static Method hvalsImplMethod;
    private static Method hgetAllImplMethod;
    private static Method rpushImplMethod;
    private static Method lpushImplMethod;
    private static Method llenImplMethod;
    private static Method lrangeImplMethod;
    private static Method ltrimImplMethod;
    private static Method lindexImplMethod;
    private static Method lsetImplMethod;
    private static Method lremImplMethod;
    private static Method lpopImplMethod;
    private static Method rpopImplMethod;
    private static Method saddImplMethod;
    private static Method smembersImplMethod;
    private static Method sremImplMethod;
    private static Method spopImplMethod;
    private static Method scardImplMethod;
    private static Method sismemberImplMethod;
    private static Method srandmemberImplMethod;
    private static Method zaddImplMethod;
    private static Method zrangeImplMethod;
    private static Method zremImplMethod;
    private static Method zincrbyImplMethod;
    private static Method zrankImplMethod;
    private static Method zrevrankImplMethod;
    private static Method zrevrangeImplMethod;
    private static Method zrangeWithScoresImplMethod;
    private static Method zrevrangeWithScoresImplMethod;
    private static Method zcardImplMethod;
    private static Method zscoreImplMethod;
    private static Method sortImplMethod1;
    private static Method sortImplMethod2;
    private static Method zcountImplMethod;
    private static Method zrangeByScoreImplMethod1;
    private static Method zrangeByScoreImplMethod2;
    private static Method zrangeByScoreWithScoresImplMethod1;
    private static Method zrangeByScoreWithScoresImplMethod2;
    private static Method zrevrangeByScoreImplMethod1;
    private static Method zrevrangeByScoreImplMethod2;
    private static Method zrevrangeByScoreWithScoresImplMethod1;
    private static Method zrevrangeByScoreWithScoresImplMethod2;
    private static Method zremrangeByRankImplMethod;
    private static Method zremrangeByScoreImplMethod;
    private static Method linsertImplMethod;

    private static ThreadLocal<Jedis> currentClient = new ThreadLocal<Jedis>();

    // Don't use Proxy mechanism as it is less flexible and slower
    private String setImpl(byte[] key, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.set(key, value);
    }

    private byte[] getImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.get(key);
    }

    private Boolean existsImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.exists(key);
    }

    private String typeImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.type(key);
    }

    private Long expireImpl(byte[] key, int seconds) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.expire(key, seconds);
    }

    private Long expireAtImpl(byte[] key, long unixTime) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.expireAt(key, unixTime);
    }

    private Long ttlImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.ttl(key);
    }

    private byte[] getSetImpl(byte[] key, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.getSet(key, value);
    }

    private Long setnxImpl(byte[] key, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.setnx(key, value);
    }

    private String setexImpl(byte[] key, int seconds, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.setex(key, seconds, value);
    }

    private Long decrByImpl(byte[] key, long integer) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.decrBy(key, integer);
    }

    private Long decrImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.decr(key);
    }

    private Long incrByImpl(byte[] key, long integer) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.incrBy(key, integer);
    }

    private Long incrImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.incr(key);
    }

    private Long appendImpl(byte[] key, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.append(key, value);
    }

    private byte[] substrImpl(byte[] key, int start, int end) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.substr(key, start, end);
    }

    private Long hsetImpl(byte[] key, byte[] field, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hset(key, field, value);
    }

    private byte[] hgetImpl(byte[] key, byte[] field) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hget(key, field);
    }

    private Long hsetnxImpl(byte[] key, byte[] field, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hsetnx(key, field, value);
    }

    private String hmsetImpl(byte[] key, Map<byte[], byte[]> hash) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hmset(key, hash);
    }

    private List<byte[]> hmgetImpl(byte[] key, byte[]... fields) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hmget(key, fields);
    }

    private Long hincrByImpl(byte[] key, byte[] field, long value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hincrBy(key, field, value);
    }

    private Boolean hexistsImpl(byte[] key, byte[] field) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hexists(key, field);
    }

    private Long hdelImpl(byte[] key, byte[] field) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hdel(key, field);
    }

    private Long hlenImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hlen(key);
    }

    private Set<byte[]> hkeysImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hkeys(key);
    }

    private Collection<byte[]> hvalsImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hvals(key);
    }

    private Map<byte[], byte[]> hgetAllImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.hgetAll(key);
    }

    private Long rpushImpl(byte[] key, byte[] string) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.rpush(key, string);
    }

    private Long lpushImpl(byte[] key, byte[] string) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.lpush(key, string);
    }

    private Long llenImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.llen(key);
    }

    private List<byte[]> lrangeImpl(byte[] key, int start, int end) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.lrange(key, start, end);
    }

    private String ltrimImpl(byte[] key, int start, int end) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.ltrim(key, start, end);
    }

    private byte[] lindexImpl(byte[] key, int index) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.lindex(key, index);
    }

    private String lsetImpl(byte[] key, int index, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.lset(key, index, value);
    }

    private Long lremImpl(byte[] key, int count, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.lrem(key, count, value);
    }

    private byte[] lpopImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.lpop(key);
    }

    private byte[] rpopImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.rpop(key);
    }

    private Long saddImpl(byte[] key, byte[] member) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.sadd(key, member);
    }

    private Set<byte[]> smembersImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.smembers(key);
    }

    private Long sremImpl(byte[] key, byte[] member) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.srem(key, member);
    }

    private byte[] spopImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.spop(key);
    }

    private Long scardImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.scard(key);
    }

    private Boolean sismemberImpl(byte[] key, byte[] member) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.sismember(key, member);
    }

    private byte[] srandmemberImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.srandmember(key);
    }

    private Long zaddImpl(byte[] key, double score, byte[] member) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zadd(key, score, member);
    }

    private Set<byte[]> zrangeImpl(byte[] key, int start, int end) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrange(key, start, end);
    }

    private Long zremImpl(byte[] key, byte[] member) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrem(key, member);
    }

    private Double zincrbyImpl(byte[] key, double score, byte[] member) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zincrby(key, score, member);
    }

    private Long zrankImpl(byte[] key, byte[] member) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrank(key, member);
    }

    private Long zrevrankImpl(byte[] key, byte[] member) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrevrank(key, member);
    }

    private Set<byte[]> zrevrangeImpl(byte[] key, int start, int end) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrevrange(key, start, end);
    }

    private Set<Tuple> zrangeWithScoresImpl(byte[] key, int start, int end) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrangeWithScores(key, start, end);
    }

    private Set<Tuple> zrevrangeWithScoresImpl(byte[] key, int start, int end) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrevrangeWithScores(key, start, end);
    }

    private Long zcardImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zcard(key);
    }

    private Double zscoreImpl(byte[] key, byte[] member) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zscore(key, member);
    }

    private List<byte[]> sortImpl(byte[] key) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.sort(key);
    }

    private List<byte[]> sortImpl(byte[] key, SortingParams sortingParameters) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.sort(key, sortingParameters);
    }

    private Long zcountImpl(byte[] key, double min, double max) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zcount(key, min, max);
    }

    private Set<byte[]> zrangeByScoreImpl(byte[] key, double min, double max) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrangeByScore(key, min, max);
    }

    private Set<byte[]> zrangeByScoreImpl(byte[] key, double min, double max, int offset, int count) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrangeByScore(key, min, max, offset, count);
    }

    private Set<Tuple> zrangeByScoreWithScoresImpl(byte[] key, double min, double max) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrangeByScoreWithScores(key, min, max);
    }

    private Set<Tuple> zrangeByScoreWithScoresImpl(byte[] key, double min, double max, int offset, int count) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    // private Set<byte[]> zrevrangeByScoreImpl(byte[] key, double max, double
    // min) {
    // Jedis j = currentClient.get();
    // assert(j != null);
    // return j.zrevrangeByScores(key, max, min);
    // }

    // private Set<byte[]> zrevrangeByScoreImpl(byte[] key, double max, double
    // min,
    // int offset, int count) {
    // Jedis j = currentClient.get();
    // assert(j != null);
    // return j.zrevrange(key, max, min, offset, count);
    // }

    // private Set<Tuple> zrevrangeByScoreWithScoresImpl(byte[] key, double max,
    // double min) {
    // Jedis j = currentClient.get();
    // assert(j != null);
    // return j.zrevrangeByScoreWithScores(key, max, min);
    // }

    // private Set<Tuple> zrevrangeByScoreWithScoresImpl(byte[] key, double max,
    // double min, int offset, int count) {
    // Jedis j = currentClient.get();
    // assert(j != null);
    // return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
    // }

    private Long zremrangeByRankImpl(byte[] key, int start, int end) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zremrangeByRank(key, start, end);
    }

    private Long zremrangeByScoreImpl(byte[] key, double start, double end) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.zremrangeByScore(key, start, end);
    }

    private Long linsertImpl(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
        Jedis j = currentClient.get();
        assert (j != null);
        return j.linsert(key, where, pivot, value);
    }

    static {
        try {
            setImplMethod = RedisClient.class.getDeclaredMethod("setImpl", byte[].class, byte[].class);
            getImplMethod = RedisClient.class.getDeclaredMethod("getImpl", byte[].class);
            existsImplMethod = RedisClient.class.getDeclaredMethod("existsImpl", byte[].class);
            typeImplMethod = RedisClient.class.getDeclaredMethod("typeImpl", byte[].class);
            expireImplMethod = RedisClient.class.getDeclaredMethod("expireImpl", byte[].class, int.class);
            expireAtImplMethod = RedisClient.class.getDeclaredMethod("expireAtImpl", byte[].class, long.class);
            ttlImplMethod = RedisClient.class.getDeclaredMethod("ttlImpl", byte[].class);
            getSetImplMethod = RedisClient.class.getDeclaredMethod("getSetImpl", byte[].class, byte[].class);
            setnxImplMethod = RedisClient.class.getDeclaredMethod("setnxImpl", byte[].class, byte[].class);
            setexImplMethod = RedisClient.class.getDeclaredMethod("setexImpl", byte[].class, int.class, byte[].class);
            decrByImplMethod = RedisClient.class.getDeclaredMethod("decrByImpl", byte[].class, long.class);
            decrImplMethod = RedisClient.class.getDeclaredMethod("decrImpl", byte[].class);
            incrByImplMethod = RedisClient.class.getDeclaredMethod("incrByImpl", byte[].class, long.class);
            incrImplMethod = RedisClient.class.getDeclaredMethod("incrImpl", byte[].class);
            appendImplMethod = RedisClient.class.getDeclaredMethod("appendImpl", byte[].class, byte[].class);
            substrImplMethod = RedisClient.class.getDeclaredMethod("substrImpl", byte[].class, int.class, int.class);
            hsetImplMethod = RedisClient.class.getDeclaredMethod("hsetImpl", byte[].class, byte[].class, byte[].class);
            hgetImplMethod = RedisClient.class.getDeclaredMethod("hgetImpl", byte[].class, byte[].class);
            hsetnxImplMethod = RedisClient.class.getDeclaredMethod("hsetnxImpl", byte[].class, byte[].class,
                    byte[].class);
            hmsetImplMethod = RedisClient.class.getDeclaredMethod("hmsetImpl", byte[].class, Map.class);
            hmgetImplMethod = RedisClient.class.getDeclaredMethod("hmgetImpl", byte[].class, byte[][].class);
            hincrByImplMethod = RedisClient.class.getDeclaredMethod("hincrByImpl", byte[].class, byte[].class,
                    long.class);
            hexistsImplMethod = RedisClient.class.getDeclaredMethod("hexistsImpl", byte[].class, byte[].class);
            hdelImplMethod = RedisClient.class.getDeclaredMethod("hdelImpl", byte[].class, byte[].class);
            hlenImplMethod = RedisClient.class.getDeclaredMethod("hlenImpl", byte[].class);
            hkeysImplMethod = RedisClient.class.getDeclaredMethod("hkeysImpl", byte[].class);
            hvalsImplMethod = RedisClient.class.getDeclaredMethod("hvalsImpl", byte[].class);
            hgetAllImplMethod = RedisClient.class.getDeclaredMethod("hgetAllImpl", byte[].class);
            rpushImplMethod = RedisClient.class.getDeclaredMethod("rpushImpl", byte[].class, byte[].class);
            lpushImplMethod = RedisClient.class.getDeclaredMethod("lpushImpl", byte[].class, byte[].class);
            llenImplMethod = RedisClient.class.getDeclaredMethod("llenImpl", byte[].class);
            lrangeImplMethod = RedisClient.class.getDeclaredMethod("lrangeImpl", byte[].class, int.class, int.class);
            ltrimImplMethod = RedisClient.class.getDeclaredMethod("ltrimImpl", byte[].class, int.class, int.class);
            lindexImplMethod = RedisClient.class.getDeclaredMethod("lindexImpl", byte[].class, int.class);
            lsetImplMethod = RedisClient.class.getDeclaredMethod("lsetImpl", byte[].class, int.class, byte[].class);
            lremImplMethod = RedisClient.class.getDeclaredMethod("lremImpl", byte[].class, int.class, byte[].class);
            lpopImplMethod = RedisClient.class.getDeclaredMethod("lpopImpl", byte[].class);
            rpopImplMethod = RedisClient.class.getDeclaredMethod("rpopImpl", byte[].class);
            saddImplMethod = RedisClient.class.getDeclaredMethod("saddImpl", byte[].class, byte[].class);
            smembersImplMethod = RedisClient.class.getDeclaredMethod("smembersImpl", byte[].class);
            sremImplMethod = RedisClient.class.getDeclaredMethod("sremImpl", byte[].class, byte[].class);
            spopImplMethod = RedisClient.class.getDeclaredMethod("spopImpl", byte[].class);
            scardImplMethod = RedisClient.class.getDeclaredMethod("scardImpl", byte[].class);
            sismemberImplMethod = RedisClient.class.getDeclaredMethod("sismemberImpl", byte[].class, byte[].class);
            srandmemberImplMethod = RedisClient.class.getDeclaredMethod("srandmemberImpl", byte[].class);
            zaddImplMethod = RedisClient.class.getDeclaredMethod("zaddImpl", byte[].class, double.class, byte[].class);
            zrangeImplMethod = RedisClient.class.getDeclaredMethod("zrangeImpl", byte[].class, int.class, int.class);
            zremImplMethod = RedisClient.class.getDeclaredMethod("zremImpl", byte[].class, byte[].class);
            zincrbyImplMethod = RedisClient.class.getDeclaredMethod("zincrbyImpl", byte[].class, double.class,
                    byte[].class);
            zrankImplMethod = RedisClient.class.getDeclaredMethod("zrankImpl", byte[].class, byte[].class);
            zrevrankImplMethod = RedisClient.class.getDeclaredMethod("zrevrankImpl", byte[].class, byte[].class);
            zrevrangeImplMethod = RedisClient.class.getDeclaredMethod("zrevrangeImpl", byte[].class, int.class,
                    int.class);
            zrangeWithScoresImplMethod = RedisClient.class.getDeclaredMethod("zrangeWithScoresImpl", byte[].class,
                    int.class, int.class);
            zrevrangeWithScoresImplMethod = RedisClient.class.getDeclaredMethod("zrevrangeWithScoresImpl",
                    byte[].class, int.class, int.class);
            zcardImplMethod = RedisClient.class.getDeclaredMethod("zcardImpl", byte[].class);
            zscoreImplMethod = RedisClient.class.getDeclaredMethod("zscoreImpl", byte[].class, byte[].class);
            sortImplMethod1 = RedisClient.class.getDeclaredMethod("sortImpl", byte[].class);
            sortImplMethod2 = RedisClient.class.getDeclaredMethod("sortImpl", byte[].class, SortingParams.class);
            zcountImplMethod = RedisClient.class.getDeclaredMethod("zcountImpl", byte[].class, double.class,
                    double.class);
            zrangeByScoreImplMethod1 = RedisClient.class.getDeclaredMethod("zrangeByScoreImpl", byte[].class,
                    double.class, double.class);
            zrangeByScoreImplMethod2 = RedisClient.class.getDeclaredMethod("zrangeByScoreImpl", byte[].class,
                    double.class, double.class, int.class, int.class);
            zrangeByScoreWithScoresImplMethod1 = RedisClient.class.getDeclaredMethod("zrangeByScoreWithScoresImpl",
                    byte[].class, double.class, double.class);
            zrangeByScoreWithScoresImplMethod2 = RedisClient.class.getDeclaredMethod("zrangeByScoreWithScoresImpl",
                    byte[].class, double.class, double.class, int.class, int.class);
            zrevrangeByScoreImplMethod1 = RedisClient.class.getDeclaredMethod("zrevrangeByScoreImpl", byte[].class,
                    double.class, double.class);
            zrevrangeByScoreImplMethod2 = RedisClient.class.getDeclaredMethod("zrevrangeByScoreImpl", byte[].class,
                    double.class, double.class, int.class, int.class);
            zrevrangeByScoreWithScoresImplMethod1 = RedisClient.class.getDeclaredMethod(
                    "zrevrangeByScoreWithScoresImpl", byte[].class, double.class, double.class);
            zrevrangeByScoreWithScoresImplMethod2 = RedisClient.class.getDeclaredMethod(
                    "zrevrangeByScoreWithScoresImpl", byte[].class, double.class, double.class, int.class, int.class);
            zremrangeByRankImplMethod = RedisClient.class.getDeclaredMethod("zremrangeByRankImpl", byte[].class,
                    int.class, int.class);
            zremrangeByScoreImplMethod = RedisClient.class.getDeclaredMethod("zremrangeByScoreImpl", byte[].class,
                    double.class, double.class);
            linsertImplMethod = RedisClient.class.getDeclaredMethod("linsertImpl", byte[].class, LIST_POSITION.class,
                    byte[].class, byte[].class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> T invocationHelper(Class<T> returnType, Method method, ShardedRedisPool pool, Object... args) {
        Jedis j = null;
        try {
            j = pool.getResource();
            currentClient.set(j);
            return (T) method.invoke(this, args);
        } catch (Exception ex) {
            pool.returnBrokenResource(j);
            j = null;
            throw new RuntimeException(ex);
        } finally {
            currentClient.set(null);
            if (j != null) {
                pool.returnResource(j);
            }
        }
    }

    public String set(byte[] key, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(String.class, setImplMethod, pool, key, value);
    }

    public byte[] get(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(byte[].class, getImplMethod, pool, key);
    }

    public Boolean exists(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Boolean.class, existsImplMethod, pool, key);
    }

    public String type(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(String.class, typeImplMethod, pool, key);
    }

    public Long expire(byte[] key, int seconds) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, expireImplMethod, pool, key, seconds);
    }

    public Long expireAt(byte[] key, long unixTime) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, expireAtImplMethod, pool, key, unixTime);
    }

    public Long ttl(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, ttlImplMethod, pool, key);
    }

    public byte[] getSet(byte[] key, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(byte[].class, getSetImplMethod, pool, key, value);
    }

    public Long setnx(byte[] key, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, setnxImplMethod, pool, key, value);
    }

    public String setex(byte[] key, int seconds, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(String.class, setexImplMethod, pool, key, seconds, value);
    }

    public Long decrBy(byte[] key, long integer) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, decrByImplMethod, pool, key, integer);
    }

    public Long decr(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, decrImplMethod, pool, key);
    }

    public Long incrBy(byte[] key, long integer) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, incrByImplMethod, pool, key, integer);
    }

    public Long incr(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, incrImplMethod, pool, key);
    }

    public Long append(byte[] key, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, appendImplMethod, pool, key, value);
    }

    public byte[] substr(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(byte[].class, substrImplMethod, pool, key, start, end);
    }

    public Long hset(byte[] key, byte[] field, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, hsetImplMethod, pool, key, field, value);
    }

    public byte[] hget(byte[] key, byte[] field) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(byte[].class, hgetImplMethod, pool, key, field);
    }

    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, hsetnxImplMethod, pool, key, field, value);
    }

    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(String.class, hmsetImplMethod, pool, key, hash);
    }

    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(List.class, hmgetImplMethod, pool, key, fields);
    }

    public Long hincrBy(byte[] key, byte[] field, long value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, hincrByImplMethod, pool, key, field, value);
    }

    public Boolean hexists(byte[] key, byte[] field) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Boolean.class, hexistsImplMethod, pool, key, field);
    }

    public Long hdel(byte[] key, byte[] field) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, hdelImplMethod, pool, key, field);
    }

    public Long hlen(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, hlenImplMethod, pool, key);
    }

    public Set<byte[]> hkeys(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, hkeysImplMethod, pool, key);
    }

    public Collection<byte[]> hvals(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Collection.class, hvalsImplMethod, pool, key);
    }

    public Map<byte[], byte[]> hgetAll(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Map.class, hgetImplMethod, pool, key);
    }

    public Long rpush(byte[] key, byte[] string) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, rpushImplMethod, pool, key, string);
    }

    public Long lpush(byte[] key, byte[] string) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, lpushImplMethod, pool, key, string);
    }

    public Long llen(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, llenImplMethod, pool, key);
    }

    public List<byte[]> lrange(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(List.class, lrangeImplMethod, pool, key, start, end);
    }

    public String ltrim(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(String.class, ltrimImplMethod, pool, key, start, end);
    }

    public byte[] lindex(byte[] key, int index) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(byte[].class, lindexImplMethod, pool, key, index);
    }

    public String lset(byte[] key, int index, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(String.class, lsetImplMethod, pool, key, index, value);
    }

    public Long lrem(byte[] key, int count, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, lremImplMethod, pool, key, count, value);
    }

    public byte[] lpop(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(byte[].class, lpopImplMethod, pool, key);
    }

    public byte[] rpop(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(byte[].class, rpopImplMethod, pool, key);
    }

    public Long sadd(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, saddImplMethod, pool, key, member);
    }

    public Set<byte[]> smembers(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, smembersImplMethod, pool, key);
    }

    public Long srem(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, sremImplMethod, pool, key, member);
    }

    public byte[] spop(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(byte[].class, spopImplMethod, pool, key);
    }

    public Long scard(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, scardImplMethod, pool, key);
    }

    public Boolean sismember(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Boolean.class, sismemberImplMethod, pool, key, member);
    }

    public byte[] srandmember(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(byte[].class, srandmemberImplMethod, pool, key);
    }

    public Long zadd(byte[] key, double score, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, zaddImplMethod, pool, key, score, member);
    }

    public Set<byte[]> zrange(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrangeImplMethod, pool, key, start, end);
    }

    public Long zrem(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, zremImplMethod, pool, key, member);
    }

    public Double zincrby(byte[] key, double score, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Double.class, zincrbyImplMethod, pool, key, score, member);
    }

    public Long zrank(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, zrankImplMethod, pool, key, member);
    }

    public Long zrevrank(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, zrevrankImplMethod, pool, key, member);
    }

    public Set<byte[]> zrevrange(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrevrangeImplMethod, pool, key, start, end);
    }

    public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrangeWithScoresImplMethod, pool, key, start, end);
    }

    public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrevrangeWithScoresImplMethod, pool, key, start, end);
    }

    public Long zcard(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, zcardImplMethod, pool, key);
    }

    public Double zscore(byte[] key, byte[] member) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Double.class, zscoreImplMethod, pool, key, member);
    }

    public List<byte[]> sort(byte[] key) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(List.class, sortImplMethod1, pool, key);
    }

    public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(List.class, sortImplMethod2, pool, key, sortingParameters);
    }

    public Long zcount(byte[] key, double min, double max) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, zcountImplMethod, pool, key, min, max);
    }

    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrangeByScoreImplMethod1, pool, key, min, max);
    }

    public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrangeByScoreImplMethod2, pool, key, min, max, offset, count);
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrangeByScoreWithScoresImplMethod1, pool, key, min, max);
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrangeByScoreWithScoresImplMethod2, pool, key, min, max, offset, count);
    }

    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrevrangeByScoreImplMethod1, pool, key, min, max);
    }

    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrevrangeByScoreImplMethod2, pool, key, min, max, offset, count);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrevrangeByScoreWithScoresImplMethod1, pool, key, min, max);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Set.class, zrevrangeByScoreWithScoresImplMethod2, pool, key, min, max, offset, count);
    }

    public Long zremrangeByRank(byte[] key, int start, int end) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, zremrangeByRankImplMethod, pool, key, start, end);
    }

    public Long zremrangeByScore(byte[] key, double start, double end) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, zremrangeByScoreImplMethod, pool, key, start, end);
    }

    public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
        ShardedRedisPool pool = getPool(key);
        return invocationHelper(Long.class, linsertImplMethod, pool, key, where, pivot, value);
    }
}
