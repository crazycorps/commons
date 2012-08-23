package com.survey.keyvalue.jredis;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

public class JRedisFactory {

    /**
     * @param masterUrls  nodeName1:host:port,nodeName2:host:port
     * @param slaveUrls   host:port,host:port
     * @return
     */
    public static JRedis createJRedis(String masterUrls, String slaveUrls){
        return new JRedisProxy(createAlgo(masterUrls, slaveUrls));
    }

    private static ShardingAlgorithm createAlgo(String masterUrls, String slaveUrls) {
        String[] masters = masterUrls.split(",");
        String[] slaves = slaveUrls.split(",");
        
        if(masters.length != slaves.length){
            throw new IllegalArgumentException("masters must has the same number slaves");
        }
        
        Config config = initConfig();
        
        ShardedRedisPool[] pools = new ShardedRedisPool[masters.length];
        int idx = 0;
        for (String master : masters) {
            String[] splitted = master.split(":");
            String slave = slaves[idx];
            String[] slaveSplitted = slave.split(":");
            if (splitted.length != 3 || slaveSplitted.length != 2) {
                throw new IllegalArgumentException(master + " is not a valid redis server address");
            }
            
            RedisShardInfo info = new RedisShardInfo(splitted[0], splitted[1], Integer.parseInt(splitted[2]),
                                        slaveSplitted[0], Integer.parseInt(slaveSplitted[1]));
            pools[idx++] = new ShardedRedisPool(config, info);
        }
        return new ConsistentShardingAlgorithm(pools);
    }
    
    /**
     * @return
     */
    private static Config initConfig() {
        Config config = new Config();
        config.timeBetweenEvictionRunsMillis = 60 * 1000;
        config.maxActive = 100;
        config.maxIdle = 50;
        config.maxWait = GenericObjectPool.WHEN_EXHAUSTED_GROW;
        config.testWhileIdle = true;
        return config;
    }
    
    public static void main(String[] args) {
        String masterUrls = "node1:10.21.8.163:6385,node2:10.21.8.163:6386,node3:10.21.8.163:6388,node4:10.21.83.122:6385,node5:10.21.83.122:6386,node6:10.21.83.122:6387,node7:10.21.83.122:6388";
        String slaveUrls = "10.21.8.163:6390,10.21.8.163:6391,10.21.8.163:6392,10.21.8.163:6393,10.21.83.122:6390,10.21.83.122:6391,10.21.83.122:6392";
        
        /*
        32228:100000577543029:userId =32228,User is null
        32267:100001774065712:userId =32267,User is null
        32551:1814086072:userId =32551,User is null
        32588:1339039709:userId =32588,User is null
        32704:100000152243735:userId =32704,User is null
        */
        ShardingAlgorithm algo = createAlgo(masterUrls, slaveUrls);
        
        ShardedRedisPool shard = algo.shard(32228 / 1024);
        for (int i = 0; i < 49; i++) {
            shard.getResource();
        }
        shard.getResource();
        shard.getResource();
        
        System.err.println(shard.getShardName());
        System.err.println(algo.shard(32267 / 1024).getShardName());
        System.err.println(algo.shard(32551 / 1024).getShardName());
        System.err.println(algo.shard(32588 / 1024).getShardName());
        System.err.println(algo.shard(32704 / 1024).getShardName());

        /*
        HashMap<String, Integer> distribution = new HashMap<String, Integer>();
        for (int cage = 0; cage < 150; ++cage) {
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
        */
    }
    
}
