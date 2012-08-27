package com.survey.keyvalue.redis;

import java.util.Map;

import org.apache.commons.pool.impl.GenericObjectPool.Config;

import com.survey.keyvalue.config.DbConfig;
import com.survey.keyvalue.config.DbConfig.DbInfo;
import com.survey.keyvalue.redis.shard.ConsistentShardingAlgorithm;
import com.survey.keyvalue.redis.shard.JRedisShardImpl;
import com.survey.keyvalue.redis.shard.RedisShardInfo;
import com.survey.keyvalue.redis.shard.ShardedRedisPool;
import com.survey.keyvalue.redis.shard.ShardingAlgorithm;

public class JRedisFactory {

    public static JRedis createJRedis(DbConfig dbConfig){
        return new JRedisShardImpl(createAlgo(dbConfig));
    }

    private static ShardingAlgorithm createAlgo(DbConfig dbConfig) {
    	
    	Map<String,DbInfo> masterDbInfoMap=dbConfig.getMasterDbInfoMap();
    	
    	Map<String,DbInfo> slaveDbInfoMap=dbConfig.getSlaveDbInfoMap();
        
        Config config = generateConfig(dbConfig);
        
        ShardedRedisPool[] pools = new ShardedRedisPool[masterDbInfoMap.size()];
        int index = 0;
        for(String nodeName:masterDbInfoMap.keySet()){
        	DbInfo masterInfo=masterDbInfoMap.get(nodeName);
        	DbInfo slaveInfo=slaveDbInfoMap.get(nodeName);
        	RedisShardInfo info = new RedisShardInfo(nodeName, masterInfo.getIp(), masterInfo.getPort(),slaveInfo.getIp(), slaveInfo.getPort());
        	pools[index++] = new ShardedRedisPool(config, info);
        }
        return new ConsistentShardingAlgorithm(pools);
    }
    
    private static Config generateConfig(DbConfig dbConfig) {
        Config config = new Config();
        config.maxActive = dbConfig.getMaxActive();
        config.maxWait = dbConfig.getMaxWait();
        config.maxIdle = dbConfig.getMaxIdle();
        config.testWhileIdle = dbConfig.isTestWhileIdle();
        config.timeBetweenEvictionRunsMillis = dbConfig.getTimeBetweenEvictionRunsMillis();
        return config;
    }
    
}
