package com.survey.keyvalue.redis.shard;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.pool.impl.GenericObjectPool.Config;


public class ConsistentShardingAlgorithm extends ShardingAlgorithm {
    private final static int DEFAULT_VIRTUAL_SHARDS_PER_SHARD = 40;
    private final SortedMap<Integer, ShardedRedisPool> circle = new TreeMap<Integer, ShardedRedisPool>();
    private int virtualShardsPerShard;

    public ConsistentShardingAlgorithm(ShardedRedisPool[] pools) {
        this(DEFAULT_VIRTUAL_SHARDS_PER_SHARD, pools);
    }

    public ConsistentShardingAlgorithm(int virtualShardsPerShard, ShardedRedisPool[] pools) {
        super(pools);
        this.virtualShardsPerShard = virtualShardsPerShard;
        for (ShardedRedisPool pool : pools) {
            add(pool);
        }
    }

    private void add(ShardedRedisPool pool) {
        StringBuilder sb = new StringBuilder("VS-");
        int len = sb.length();
        for (int idx = 0; idx < virtualShardsPerShard; ++idx) {
            sb.setLength(len);
            sb.append(idx);
            sb.append("-");
            sb.append(pool.getShardName());
            int hashCode = hash(sb.toString());
            circle.put(hashCode, pool);
        }
    }

    @SuppressWarnings("unused")
    private void remove(ShardedRedisPool pool) {
        int hashCode = hash(pool.getShardName());
        if (!circle.get(hashCode).equals(pool)) {
            throw new IllegalArgumentException("Invalid pool in hash circle");
        } else {
            circle.remove(hashCode);
        }
    }

    public ShardedRedisPool shard(int cage) {
        if (circle.isEmpty()) {
            throw new IllegalArgumentException("No pool exist");
        }
        cage = hash(cage);
        if (!circle.containsKey(cage)) {
            SortedMap<Integer, ShardedRedisPool> tailMap = circle.tailMap(cage);
            cage = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(cage);
    }

    private static final int hash(int cage) {
        return Math.abs((int) (cage * 2654435761l)) % 0x7FFFFFFF;
    }

    private static final int hash(String shardName) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] result = md.digest(shardName.getBytes("UTF-8"));
            ByteBuffer buffer = ByteBuffer.wrap(result);
            return Math.abs(buffer.getInt());
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("<ConsistentSharding>\n");
        Set<Map.Entry<Integer, ShardedRedisPool>> entries = circle.entrySet();
        Integer lastInteger = 0;
        for (Map.Entry<Integer, ShardedRedisPool> entry : entries) {
            sb.append("\t");
            sb.append("<Range start='");
            sb.append(lastInteger);
            sb.append("' end='");
            sb.append(entry.getKey());
            sb.append("'>");
            sb.append(entry.getValue().getShardName());
            sb.append("</Range>\n");
            lastInteger = entry.getKey();
        }
        if (lastInteger != 0x7FFFFFFF) {
            sb.append("\t");
            sb.append("<Range start='");
            sb.append(lastInteger);
            sb.append("' end='2147483647'>");
            sb.append(circle.get(circle.firstKey()).getShardName());
            sb.append("</Range>\n");
        }
        sb.append("</ConsistentSharding>");
        return sb.toString();
    }

    public static void main(String[] args) {
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

        ConsistentShardingAlgorithm algo = new ConsistentShardingAlgorithm(pools);

        System.out.println(algo);

        HashMap<String, Integer> distribution = new HashMap<String, Integer>();
        for (int cage = 0; cage < 100; ++cage) {
            String shard = algo.shard(cage).getShardName();
            System.out.println("Cage " + cage + " is going to use shard " + shard);
            Integer count = distribution.get(shard);
            if (count == null) {
                count = 0;
            }
            count += 1;
            distribution.put(shard, count);
        }

        System.out.println(distribution);
    }
}
