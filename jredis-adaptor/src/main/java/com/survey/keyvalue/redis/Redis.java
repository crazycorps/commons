package com.survey.keyvalue.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.Transaction;

public interface Redis {

    boolean del(byte[]... keys);

    boolean set(byte[] key, byte[] value);

    byte[] get(byte[] key);

    List<byte[]> mget(byte[]... keys);

    Boolean exists(byte[] key);

    String type(byte[] key);

    Long expire(byte[] key, int seconds);

    Long expireAt(byte[] key, long unixTime);

    Long ttl(byte[] key);

    byte[] getSet(byte[] key, byte[] value);

    Boolean setnx(byte[] key, byte[] value);

    String setex(byte[] key, int seconds, byte[] value);

    Long decrBy(byte[] key, long integer);

    Long decr(byte[] key);

    Long incrBy(byte[] key, long integer);

    Long incr(byte[] key);

    Long append(byte[] key, byte[] value);

    byte[] substr(byte[] key, int start, int end);

    boolean hset(byte[] key, byte[] field, byte[] value);

    byte[] hget(byte[] key, byte[] field);

    Long hsetnx(byte[] key, byte[] field, byte[] value);

    String hmset(byte[] key, Map<byte[], byte[]> hash);

    List<byte[]> hmget(byte[] key, byte[]... fields);

    Long hincrBy(byte[] key, byte[] field, long value);

    Boolean hexists(byte[] key, byte[] field);

    boolean hdel(byte[] key, byte[] field);

    Long hlen(byte[] key);

    Set<byte[]> hkeys(byte[] key);

    Collection<byte[]> hvals(byte[] key);

    Map<byte[], byte[]> hgetAll(byte[] key);

    Long rpush(byte[] key, byte[] string);

    Long lpush(byte[] key, byte[] string);

    Long llen(byte[] key);

    List<byte[]> lrange(byte[] key, int start, int end);

    String ltrim(byte[] key, int start, int end);

    byte[] lindex(byte[] key, int index);

    String lset(byte[] key, int index, byte[] value);

    Long lrem(byte[] key, int count, byte[] value);

    byte[] lpop(byte[] key);

    byte[] rpop(byte[] key);

    Long sadd(byte[] key, byte[] member);

    Set<byte[]> smembers(byte[] key);

    Set<String> keys(String pattern);

    Long srem(byte[] key, byte[] member);

    byte[] spop(byte[] key);

    Long scard(byte[] key);

    Boolean sismember(byte[] key, byte[] member);

    byte[] srandmember(byte[] key);

    Long zadd(byte[] key, double score, byte[] member);

    Set<byte[]> zrange(byte[] key, int start, int end);

    Long zrem(byte[] key, byte[] member);

    Double zincrby(byte[] key, double score, byte[] member);

    Long zrank(byte[] key, byte[] member);

    Long zrevrank(byte[] key, byte[] member);

    Set<byte[]> zrevrange(byte[] key, int start, int end);

    Set<Tuple> zrangeWithScores(byte[] key, int start, int end);

    Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end);

    Long zcard(byte[] key);

    Double zscore(byte[] key, byte[] member);

    List<byte[]> sort(byte[] key);

    List<byte[]> sort(byte[] key, SortingParams sortingParameters);

    Long zcount(byte[] key, double min, double max);

    Set<byte[]> zrangeByScore(byte[] key, double min, double max);

    Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count);

    Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max);

    Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count);

    Long zremrangeByRank(byte[] key, int start, int end);

    Long zremrangeByScore(byte[] key, double start, double end);

    Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value);

    Set<byte[]> sinter(byte[]... keys);

    /**append method*/
    Set<byte[]> srandomMembers(byte[] key, int count);

    /*transaction support*/
    Transaction multi(byte[] key);

    boolean watch(byte[] key);

    boolean unwatch(byte[] key);

}
